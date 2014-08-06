package com.mk.system.entity;

import java.util.List;

public class UserManageRangePam {
	private String userguid;
	private List<UserManageRange> list;

	public String getUserguid() {
		return userguid;
	}

	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}

	public List<UserManageRange> getList() {
		return list;
	}

	public void setList(List<UserManageRange> list) {
		this.list = list;
	}

}
