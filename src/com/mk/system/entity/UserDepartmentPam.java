package com.mk.system.entity;

import java.util.List;

public class UserDepartmentPam {
	private String userguid;
	private List<UserDepartment> list;

	public String getUserguid() {
		return userguid;
	}

	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}

	public List<UserDepartment> getList() {
		return list;
	}

	public void setList(List<UserDepartment> list) {
		this.list = list;
	}

}
