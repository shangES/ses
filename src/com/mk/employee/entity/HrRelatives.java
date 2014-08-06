package com.mk.employee.entity;

import com.mk.framework.constance.OptionConstance;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class HrRelatives {
	private String relativesguid;
	private String employeeid;
	private String companyname;
	private String deptname;
	private String postname;
	private String employeename;
	private Integer contactrelationship;

	// 临时
	private String contactrelationshipname;

	public Integer getContactrelationship() {
		return contactrelationship;
	}

	public void setContactrelationship(Integer contactrelationship) {
		this.contactrelationship = contactrelationship;
	}

	public String getContactrelationshipname() {
		return contactrelationshipname;
	}

	public void setContactrelationshipname(String contactrelationshipname) {
		this.contactrelationshipname = contactrelationshipname;
	}

	public String getRelativesguid() {
		return relativesguid;
	}

	public void setRelativesguid(String relativesguid) {
		this.relativesguid = relativesguid;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

	
	/**
	 * code转名称
	 * 
	 */
	public void convertOneCodeToName(OptionDao optionDao) {
		// 关系
		if (this.getContactrelationship() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RELATIONSHIP, this.getContactrelationship());
			if (opt != null)
				this.setContactrelationshipname(opt.getName());
		}
	}
}
