package com.thd.system.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import com.thd.system.model.Function;

@SuppressWarnings("unchecked")
public class RoleVo {
	private String id;
	private String roleName;
	private String roleMemo;
	private final List<Function> functionList = LazyList.decorate(new ArrayList(),
			FactoryUtils.instantiateFactory(Function.class));

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

	public List<Function> getFunctionList() {
		return functionList;
	}

}
