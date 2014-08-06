package com.mk.person.entity;

import java.sql.Date;

import com.mk.framework.constance.OptionConstance;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class TmpWorkExperience {
	private String workexperienceid;
	private String mycandidatesguid;
	private String workunit;
	private Date startdate;
	private Date enddate;
	private String job;
	private String description;
	private String reporter;
	private String email;
	private String reporterphone;
	private Integer resignationreason;
	private String money;

	// 临时
	private String resignationreasonname;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkexperienceid() {
		return workexperienceid;
	}

	public void setWorkexperienceid(String workexperienceid) {
		this.workexperienceid = workexperienceid;
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

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getReporterphone() {
		return reporterphone;
	}

	public void setReporterphone(String reporterphone) {
		this.reporterphone = reporterphone;
	}

	public Integer getResignationreason() {
		return resignationreason;
	}

	public void setResignationreason(Integer resignationreason) {
		this.resignationreason = resignationreason;
	}

	public String getResignationreasonname() {
		return resignationreasonname;
	}

	public void setResignationreasonname(String resignationreasonname) {
		this.resignationreasonname = resignationreasonname;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	/**
	 * code转名称
	 * 
	 * @param optionDao
	 */
	public void convertOneCodeToName(OptionDao optionDao) {
		// 离职原因
		if (this.getResignationreason() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RESIGNATIONREASON, this.getResignationreason());
			if (opt != null)
				this.setResignationreasonname(opt.getName());
		}
	}

}
