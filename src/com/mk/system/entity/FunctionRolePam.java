package com.mk.system.entity;

import java.util.List;

public class FunctionRolePam {
	private String funid;
	private List<RoleRight> list;

	public String getFunid() {
		return funid;
	}

	public void setFunid(String funid) {
		this.funid = funid;
	}

	public List<RoleRight> getList() {
		return list;
	}

	public void setList(List<RoleRight> list) {
		this.list = list;
	}

}
