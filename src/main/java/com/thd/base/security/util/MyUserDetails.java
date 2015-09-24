package com.thd.base.security.util;

import java.util.ArrayList;
import java.util.List;

import com.thd.system.model.Operator.GenderType;
import com.thd.system.model.Operator.OperatorType;
import com.thd.system.model.Operator.PositionType;
import com.thd.system.model.Organization;
import com.thd.system.model.Role;

public class MyUserDetails {
	/**
	 * 登陆账号
	 */
	private String userIp;

	/**
	 * 登陆账号
	 */
	private String operAccountNo;

	/**
	 * 用户姓名
	 */
	private String operName;

	/**
	 * 用户性别
	 */
	private GenderType operGender;

	/**
	 * 邮件
	 */
	private String operMail;

	/**
	 * 联系电话
	 */
	private String operPhone;

	/**
	 * 用户类型
	 */
	private OperatorType operType;

	/**
	 * 用户职位
	 */
	private PositionType positionType;

	/**
	 * 机构List
	 */
	private List<Organization> organizations = new ArrayList<Organization>();

	/**
	 * 角色List
	 */
	public List<Role> roles = new ArrayList<Role>();

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getOperAccountNo() {
		return operAccountNo;
	}

	public void setOperAccountNo(String operAccountNo) {
		this.operAccountNo = operAccountNo;
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

	public void setOperType(OperatorType operType) {
		this.operType = operType;
	}

	public PositionType getPositionType() {
		return positionType;
	}

	public void setPositionType(PositionType positionType) {
		this.positionType = positionType;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

}