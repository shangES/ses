package com.mk.employee.entity;

import com.mk.framework.constance.OptionConstance;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class Family {
	private String familyid;
	private String employeeid;
	private String workunit;
	private String name;
	private String job;
	private String mobile;
	private Integer contactrelationship;

	// 临时
	private String contactrelationshipname;

	public String getContactrelationshipname() {
		return contactrelationshipname;
	}

	public void setContactrelationshipname(String contactrelationshipname) {
		this.contactrelationshipname = contactrelationshipname;
	}

	public Integer getContactrelationship() {
		return contactrelationship;
	}

	public void setContactrelationship(Integer contactrelationship) {
		this.contactrelationship = contactrelationship;
	}

	public String getFamilyid() {
		return familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getWorkunit() {
		return workunit;
	}

	public void setWorkunit(String workunit) {
		this.workunit = workunit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * code转名称
	 * 
	 */
	public void convertOneCodeToName(OptionDao optionDao) {
		// 家属关系
		if (this.getContactrelationship() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RELATIONSHIP, this.getContactrelationship());
			if (opt != null)
				this.setContactrelationshipname(opt.getName());
		}

	}

}
