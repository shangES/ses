package com.mk.system.entity;

import java.util.List;

public class UserRolePam {
	private String userguid;
	private String roleid;
	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	private String ids;
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

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
