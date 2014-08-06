package com.mk.system.entity;

import java.util.List;

public class RoleFunctionPam {
	private String roleid;
	private List<RoleRight> list;

	

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public List<RoleRight> getList() {
		return list;
	}

	public void setList(List<RoleRight> list) {
		this.list = list;
	}

}
