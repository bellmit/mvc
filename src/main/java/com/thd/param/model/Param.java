package com.thd.param.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.thd.base.model.BaseModel;

@Entity
public class Param extends BaseModel {

	// 参数类型
	public enum ParamType {
		/**
		 * 0----COMPLAINT_INSURANCE("投诉险种");1----CERT_TYPE("证件类型");2----CUSTOMER_TYPE("投诉人类型")
		 * 3----COMPLAINT_MODE("投诉方式");4----COMPLAINT_OBJ_TYPE("被投诉对象类型");5----COMPLAINT_POINT("投诉要点")
		 * 6----SUPPLIER_CLASSIFY("供应商分类");7----AREA_CODE("地区编号");8----HANDLING_WAY("处理方式")
		 * 9----CUSTOMER_FEEDBACK("客户反馈");10----ZERO_COMPLAINTCASE("零分件原因");11----POLICYCONTENT_RECORDS("保单内容记录")
		 * 12----CPLINFO_RECORDS("投诉人信息记录");13----ORGSELECT_RECORDS("被投诉机构选择");14----CPLREASON_RECORDS("投诉原因选择")
		 * 15----CPLCONTENT_RECORDS("投诉内容记录");16----COMPLAINT_EFFECTIVELY("投诉有效标志");17----VERIFY_CPL_POINT("查实投诉要点")
		 * 18----TAPE_SERVICEATTITUDE("录音质量评价-服务态度 ");19----TAPE_SERVICETERMS("录音质量评价-服务用语 ");20----TAPE_SERVICESKILLS("录音质量评价-服务技巧   ")
		 * 21----DISPOSE_OBJ("沟通对象");22----SYSTEM_PATH("系统路径")
		 */
		COMPLAINT_INSURANCE("投诉险种"), CERT_TYPE("证件类型"), CUSTOMER_TYPE("投诉人类型"), COMPLAINT_MODE("投诉方式"), COMPLAINT_OBJ_TYPE(
				"被投诉对象类型"), COMPLAINT_POINT("投诉要点"), SUPPLIER_CLASSIFY("供应商分类"), AREA_CODE("地区编号"), HANDLING_WAY("处理方式"), CUSTOMER_FEEDBACK(
				"客户反馈"), ZERO_COMPLAINTCASE("零分件原因"), POLICYCONTENT_RECORDS("保单内容记录"), CPLINFO_RECORDS("投诉人信息记录"), ORGSELECT_RECORDS(
				"被投诉机构选择"), CPLREASON_RECORDS("投诉原因选择"), CPLCONTENT_RECORDS("投诉内容记录"), COMPLAINT_EFFECTIVELY("投诉有效标志"), VERIFY_CPL_POINT(
				"核实投诉要点"), TAPE_SERVICEATTITUDE("录音质量评价-服务态度 "), TAPE_SERVICETERMS("录音质量评价-服务用语 "), TAPE_SERVICESKILLS(
				"录音质量评价-服务技巧   "), DISPOSE_OBJ("沟通对象"), SYSTEM_PATH("系统路径");
		private final String text;

		private ParamType(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}
	}

	/**
	 * 系统参数类型
	 */
	@Column
	@Enumerated(EnumType.ORDINAL)
	private ParamType type;

	/**
	 * 系统参数值
	 */
	@Column
	private String value;

	/**
	 * 系统参数显示
	 */
	@Column
	private String text;

	/**
	 * 系统参数排序
	 */
	@Column
	private Integer sortNo;

	@ManyToOne
	@Cascade(value = CascadeType.SAVE_UPDATE)
	//@JoinColumn(name = "FK_PARENT_PARAM_ID")
	private Param parentParam;

	@OneToMany(mappedBy = "parentParam", fetch = FetchType.EAGER)
	@Cascade(value = CascadeType.SAVE_UPDATE)
	@Fetch(FetchMode.SELECT)
	private List<Param> childParams = new ArrayList<Param>();

	public ParamType getType() {
		return type;
	}

	public String getParamTypeText() {
		return type != null ? type.getText() : "";
	}

	public void setType(ParamType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Param getParentParam() {
		return parentParam;
	}

	public void setParentParam(Param parentParam) {
		this.parentParam = parentParam;
	}

	public List<Param> getChildParams() {
		return childParams;
	}

	public void setChildParams(List<Param> childParams) {
		this.childParams = childParams;
	}

	public void addChildParam(Param childParam) {
		if (childParam != null) {
			childParam.setParentParam(this);
			if (!this.getChildParams().contains(childParam)) {
				this.getChildParams().add(childParam);
			}
		}
	}

	public void clear() {
		for (Param fun : this.getChildParams()) {
			fun.setParentParam(null);
		}
		this.getChildParams().clear();

		if (this.getParentParam() != null) {
			this.getParentParam().getChildParams().remove(this);
			this.setParentParam(null);
		}
	}
}
