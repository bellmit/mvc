package com.thd.system.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Role extends BaseModel {

	/**
	 * 角色名称
	 */
	@Column(nullable = false)
	private String roleName;

	/**
	 * 角色说明
	 */
	@Column(length = 3000)
	private String roleMemo;

	@ManyToMany(mappedBy = "roles")
	@Cascade(value = CascadeType.SAVE_UPDATE)
	//	@Column
	public List<Operator> users = new ArrayList<Operator>();

	@ManyToMany(fetch = FetchType.EAGER)
	//@JoinTable(name = "ROLE_FUN_ASS", joinColumns = { @JoinColumn(name = "FK_ROLE") }, inverseJoinColumns = { @JoinColumn(name = "FK_FUN") })
	@Cascade(value = CascadeType.SAVE_UPDATE)
	@Fetch(FetchMode.SELECT)
	//	@Column
	public List<Function> functions = new ArrayList<Function>();

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleMemo() {
		return roleMemo;
	}

	public void setRoleMemo(String roleMemo) {
		this.roleMemo = roleMemo;
	}

	public List<Operator> getUsers() {
		return users;
	}

	public void setUsers(List<Operator> users) {
		this.users = users;
	}

	public void addUser(Operator user) {
		if (user != null && !user.getRoles().contains(this)
				&& !this.getUsers().contains(user)) {
			user.getRoles().add(this);
			this.getUsers().add(user);
		}
	}

	public List<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}

	public void addFunction(Function fun) {
		if (fun != null && !fun.getRoles().contains(this)) {
			fun.getRoles().add(this);
		}
		if (!this.getFunctions().contains(fun)) {
			this.getFunctions().add(fun);
		}
	}

	public void removeFunction(Function fun) {
		if (fun != null) {
			fun.getRoles().remove(this);
			this.getFunctions().remove(fun);
		}
	}

	public void clearFunctions() {
		for (Function fun : this.getFunctions()) {
			fun.getRoles().remove(this);
		}
		this.getFunctions().clear();
	}

	public void clear() {
		for (Operator user : this.getUsers()) {
			user.getRoles().remove(this);
		}
		this.getUsers().clear();

		for (Function fun : this.getFunctions()) {
			fun.getRoles().remove(this);
		}
		this.getFunctions().clear();
	}

}
