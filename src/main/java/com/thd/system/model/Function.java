package com.thd.system.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.thd.base.model.BaseModel;

@Entity
public class Function extends BaseModel {

	/**
	 * 系统功能全称
	 */
	@Column(length = 180)
	private String funName;

	/**
	 * 系统功能描述
	 */
	@Column(length = 1000)
	private String funMemo;

	/**
	 * 系统功能URL
	 */
	@Column(length = 180)
	private String funUrl;

	/**
	 * 功能排序值
	 */
	@Column
	private Long funOrder;

	@ManyToOne
	@Cascade(value = CascadeType.SAVE_UPDATE)
	//@JoinColumn(name = "FK_PARENT_FUN_ID")
	private Function parentFunction;

	@OneToMany(mappedBy = "parentFunction")
	@Cascade(value = CascadeType.SAVE_UPDATE)
	private List<Function> childFuns = new ArrayList<Function>();

	@ManyToMany(mappedBy = "functions")
	@Cascade(value = CascadeType.SAVE_UPDATE)
	public List<Role> roles = new ArrayList<Role>();

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

	public String getFunMemo() {
		return funMemo;
	}

	public void setFunMemo(String funMemo) {
		this.funMemo = funMemo;
	}

	public String getFunUrl() {
		return funUrl;
	}

	public void setFunUrl(String funUrl) {
		this.funUrl = funUrl;
	}

	public Long getFunOrder() {
		return funOrder;
	}

	public void setFunOrder(Long funOrder) {
		this.funOrder = funOrder;
	}

	public Function getParentFunction() {
		return parentFunction;
	}

	public void setParentFunction(Function parentFunction) {
		this.parentFunction = parentFunction;
	}

	public List<Function> getChildFuns() {
		return childFuns;
	}

	public void setChildFuns(List<Function> childFuns) {
		this.childFuns = childFuns;
	}

	public void addChildFun(Function childFun) {
		if (childFun != null) {
			childFun.setParentFunction(this);
			if (!this.getChildFuns().contains(childFun)) {
				this.getChildFuns().add(childFun);
			}
		}
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		if (role != null && !role.getFunctions().contains(this)
				&& !this.getRoles().contains(role)) {
			role.getFunctions().add(this);
			this.getRoles().add(role);
		}
	}

	public void clear() {
		for (Role role : this.roles) {
			role.getFunctions().remove(this);
		}
		this.roles.clear();

		for (Function fun : this.getChildFuns()) {
			fun.setParentFunction(null);
		}
		this.getChildFuns().clear();

		if (this.getParentFunction() != null) {
			this.getParentFunction().getChildFuns().remove(this);
			this.setParentFunction(null);
		}
	}

}
