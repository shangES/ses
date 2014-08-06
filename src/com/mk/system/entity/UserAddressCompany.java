package com.mk.system.entity;

import com.mk.company.entity.Company;

public class UserAddressCompany {
	private String userguid;
	private String companyid;

	public UserAddressCompany() {

	}

	public UserAddressCompany(Company company, String userId) {
		this.setUserguid(userId);
		this.setCompanyid(company.getCompanyid());
	}

	public String getUserguid() {
		return userguid;
	}

	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

}
