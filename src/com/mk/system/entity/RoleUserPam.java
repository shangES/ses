package com.mk.system.entity;

import java.util.List;

public class RoleUserPam {
	private String roleid;
	private List<UserRole> list;

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public List<UserRole> getList() {
		return list;
	}

	public void setList(List<UserRole> list) {
		this.list = list;
	}

}
