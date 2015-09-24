package com.thd.system.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.thd.base.model.BaseModel;

@Entity
public class Operator extends BaseModel {

	// 用户性别类型
	public enum GenderType {
		MAN("男"), WOMEN("女");
		private final String text;

		private GenderType(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}
	}

	// 用户类型
	public enum OperatorType {
		MANAGER("主管"), NORMAL("普通职员");
		private final String text;

		private OperatorType(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}
	}

	// 用户职位，最后OTHERJOB指系统管理员等人的职位
	public enum PositionType {
		ACCEPTANCE("受理"), QC("质检"), SCHEDULING("调度"), HANDLE("处理"), AUDIT("审核"), CLOSECASE(
				"结案"), CALLBACK("回访"), OTHERJOB("其他");
		private final String text;

		private PositionType(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}
	}

	/**
	 * 登陆账号
	 */
	@Column(length = 60, updatable = false)
	private String operAccountNo;

	/**
	 * 登陆密码
	 */
	@Column(length = 32)
	private String operPassword;

	/**
	 * 用户姓名
	 */
	@Column(length = 60)
	private String operName;

	/**
	 * 用户性别
	 */
	@Column
	@Enumerated(EnumType.ORDINAL)
	private GenderType operGender;

	/**
	 * 邮件
	 */
	@Column(length = 60)
	private String operMail;

	/**
	 * 联系电话
	 */
	@Column(length = 60)
	private String operPhone;

	/**
	 * 用户类型
	 */
	@Column
	@Enumerated(EnumType.ORDINAL)
	private OperatorType operType;

	/**
	 * 用户职位
	 */
	@Column
	@Enumerated(EnumType.ORDINAL)
	private PositionType positionType;

	/**
	 * 机构List
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	//@JoinTable(name = "OPER_ORG_ASS", joinColumns = { @JoinColumn(name = "FK_OPER") }, inverseJoinColumns = { @JoinColumn(name = "FK_ORG") })
	@Cascade(value = CascadeType.SAVE_UPDATE)
	@Fetch(FetchMode.SELECT)
	private List<Organization> organizations = new ArrayList<Organization>();

	/**
	 * 角色List
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	//@JoinTable(name = "OPER_ROLE_ASS", joinColumns = { @JoinColumn(name = "FK_OPER") }, inverseJoinColumns = { @JoinColumn(name = "FK_ROLE") })
	@Cascade(value = CascadeType.SAVE_UPDATE)
	@Fetch(FetchMode.SELECT)
	public List<Role> roles = new ArrayList<Role>();

	public String getOperAccountNo() {
		return operAccountNo;
	}

	public void setOperAccountNo(String operAccountNo) {
		this.operAccountNo = operAccountNo;
	}

	public String getOperPassword() {
		return operPassword;
	}

	public void setOperPassword(String operPassword) {
		this.operPassword = operPassword;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public GenderType getOperGender() {
		return operGender;
	}

	public String getOperGenderText() {
		return operGender != null ? operGender.getText() : "";
	}

	public void setOperGender(GenderType operGender) {
		this.operGender = operGender;
	}

	public String getOperMail() {
		return operMail;
	}

	public void setOperMail(String operMail) {
		this.operMail = operMail;
	}

	public String getOperPhone() {
		return operPhone;
	}

	public void setOperPhone(String operPhone) {
		this.operPhone = operPhone;
	}

	public OperatorType getOperType() {
		return operType;
	}

	public String getOperTypeText() {
		return operType != null ? operType.getText() : "";
	}

	public void setOperType(OperatorType operType) {
		this.operType = operType;
	}

	public PositionType getPositionType() {
		return positionType;
	}

	public String getPositionTypeText() {
		return positionType != null ? positionType.getText() : "";
	}

	public void setPositionType(PositionType positionType) {
		this.positionType = positionType;
	}

	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public void addOrganization(Organization org) {
		if (org != null && !org.getUsers().contains(this)) {
			org.getUsers().add(this);
		}
		if (!this.getOrganizations().contains(org)) {
			this.getOrganizations().add(org);
		}
	}

	public void clearOrganizations() {
		for (Organization org : this.getOrganizations()) {
			org.getUsers().remove(this);
		}
		this.getOrganizations().clear();
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		if (role != null && !role.getUsers().contains(this)) {
			role.getUsers().add(this);
		}
		if (!this.getRoles().contains(role)) {
			this.getRoles().add(role);
		}
	}

	public void clearRoles() {
		for (Role role : this.getRoles()) {
			role.getUsers().remove(this);
		}
		this.getRoles().clear();
	}

	public void removeRole(Role role) {
		if (role != null) {
			role.getUsers().remove(this);
			this.getRoles().remove(role);
		}
	}

	public void clear() {
		for (Organization org : this.getOrganizations()) {
			org.getUsers().remove(this);
		}
		this.getOrganizations().clear();

		for (Role role : this.getRoles()) {
			role.getUsers().remove(this);
		}
		this.getRoles().clear();
	}
}
