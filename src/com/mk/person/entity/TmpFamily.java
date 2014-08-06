package com.mk.person.entity;

import com.mk.framework.constance.OptionConstance;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class TmpFamily {
	private String familyid;
	private String mycandidatesguid;
	private String workunit;
	private String name;
	private String job;
	private String mobile;
	private Integer contactrelationship;

	private String contactrelationshipname;

	public String getContactrelationshipname() {
		return contactrelationshipname;
	}

	public void setContactrelationshipname(String contactrelationshipname) {
		this.contactrelationshipname = contactrelationshipname;
	}

	public String getFamilyid() {
		return familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid;
	}

	public String getMycandidatesguid() {
		return mycandidatesguid;
	}

	public void setMycandidatesguid(String mycandidatesguid) {
		this.mycandidatesguid = mycandidatesguid;
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

	public Integer getContactrelationship() {
		return contactrelationship;
	}

	public void setContactrelationship(Integer contactrelationship) {
		this.contactrelationship = contactrelationship;
	}

	/**
	 * code转名称
	 * 
	 * 
	 * @param optionDao
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
