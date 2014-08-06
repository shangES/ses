package com.mk.system.entity;

import java.util.List;

public class UserRolePam {
	private String userguid;
	private List<UserRole> list;

	public String getUserguid() {
		return userguid;
	}

	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}

	public List<UserRole> getList() {
		return list;
	}

	public void setList(List<UserRole> list) {
		this.list = list;
	}

}
