package com.thd.param.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.thd.base.model.BaseModel;

@Entity
public class Supplier extends BaseModel {

	/**
	 * 供应商编号
	 */
	@Column(nullable = false)
	private String supCode;

	/**
	 * 供应商分类
	 */
	@Column(nullable = false)
	private String supClassify;

	/**
	 * 所属机构,保存机构的id
	 */
	@Column(nullable = false)
	private String orgId;

	/**
	 * 供应商名称
	 */
	@Column(nullable = false)
	private String supName;

	/**
	 * 供应商地址
	 */
	@Column(length = 1000)
	private String supAddress;

	/**
	 * 供应商联系电话
	 */
	@Column(length = 60)
	private String supPhone;

	/**
	 * 供应商邮编
	 */
	@Column(length = 60)
	private String supPostCode;

	// 显示机构名称,不保存数据库
	@Transient
	private String orgText;

	public String getSupCode() {
		return supCode;
	}

	public void setSupCode(String supCode) {
		this.supCode = supCode;
	}

	public String getSupClassify() {
		return supClassify;
	}

	public void setSupClassify(String supClassify) {
		this.supClassify = supClassify;
	}

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public String getSupAddress() {
		return supAddress;
	}

	public void setSupAddress(String supAddress) {
		this.supAddress = supAddress;
	}

	public String getSupPhone() {
		return supPhone;
	}

	public void setSupPhone(String supPhone) {
		this.supPhone = supPhone;
	}

	public String getSupPostCode() {
		return supPostCode;
	}

	public void setSupPostCode(String supPostCode) {
		this.supPostCode = supPostCode;
	}

	public String getOrgText() {
		return orgText;
	}

	public void setOrgText(String orgText) {
		this.orgText = orgText;
	}

}
