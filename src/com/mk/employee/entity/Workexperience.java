package com.mk.employee.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;

public class Workexperience {
	private String workexperienceid;
	private String employeeid;
	private String workunit;
	private Date startdate;
	private Date enddate;
	private String job;
	private String description;
	private String email;
	private String reporterphone;
	private String reporter;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReporterphone() {
		return reporterphone;
	}

	public void setReporterphone(String reporterphone) {
		this.reporterphone = reporterphone;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getWorkexperienceid() {
		return workexperienceid;
	}

	public void setWorkexperienceid(String workexperienceid) {
		this.workexperienceid = workexperienceid;
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

}
