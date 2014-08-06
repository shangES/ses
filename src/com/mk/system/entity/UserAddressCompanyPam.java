package com.mk.system.entity;

import java.util.List;

public class UserAddressCompanyPam {
	private String userguid;
	private List<UserAddressCompany> list;
	
	public String getUserguid() {
		return userguid;
	}
	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}
	public List<UserAddressCompany> getList() {
		return list;
	}
	public void setList(List<UserAddressCompany> list) {
		this.list = list;
	}
	
	
}
