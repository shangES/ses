package com.mk.system.entity;

import com.mk.company.entity.Company;
import com.mk.framework.constance.Constance;

public class UserManageRange {
	private String userguid;
	private String companyid;
	private Integer isdefault;

	public UserManageRange() {

	}

	public UserManageRange(Company model, String userId) {
		this.setUserguid(userId);
		this.setCompanyid(model.getCompanyid());
		this.setIsdefault(Constance.VALID_YES);
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

	public Integer getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}

}
