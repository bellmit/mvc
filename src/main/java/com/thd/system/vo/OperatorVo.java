package com.thd.system.vo;

import java.util.ArrayList;
import java.util.List;

import com.thd.system.model.Organization;
import com.thd.system.model.Role;
import com.thd.system.model.Operator.GenderType;
import com.thd.system.model.Operator.OperatorType;

@SuppressWarnings("unchecked")
public class OperatorVo {

	private String id;

	private String operAccountNo;

	private String operPassword;

	private String operName;

	private GenderType operGender;

	private String operMail;

	private String operPhone;

	private OperatorType operType = OperatorType.NORMAL;

	private List<Organization> orgList = new ArrayList();

	public List<Role> roleList = new ArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

	public void setOperGender(GenderType operGender) {
		this.operGender = operGender;
	}

	public String getOperMail() {
		return operMail;
	}

	public void setOperMail(String operMail) {
		this.operMail = operMail;
	}

	public OperatorType getOperType() {
		return operType;
	}

	public String getOperPhone() {
		return operPhone;
	}

	public void setOperPhone(String operPhone) {
		this.operPhone = operPhone;
	}

	public void setOperType(OperatorType operType) {
		this.operType = operType;
	}

	public List<Organization> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Organization> orgList) {
		this.orgList = orgList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}
