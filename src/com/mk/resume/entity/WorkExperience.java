package com.mk.resume.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;

public class WorkExperience {
	private String workexperienceguid;
	private String webuserguid;
	private String workunit;
	private String posation;
	private String references;
	private String referencesphone;
	private Date startdate;
	private Date enddate;
	private String jobdescription;
	private Timestamp modtime;
	private String rmk;

	public String getReferences() {
		return references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public String getReferencesphone() {
		return referencesphone;
	}

	public void setReferencesphone(String referencesphone) {
		this.referencesphone = referencesphone;
	}

	public String getWorkexperienceguid() {
		return workexperienceguid;
	}

	public void setWorkexperienceguid(String workexperienceguid) {
		this.workexperienceguid = workexperienceguid;
	}

	public String getWebuserguid() {
		return webuserguid;
	}

	public void setWebuserguid(String webuserguid) {
		this.webuserguid = webuserguid;
	}

	public String getWorkunit() {
		return workunit;
	}

	public void setWorkunit(String workunit) {
		this.workunit = workunit;
	}

	public String getPosation() {
		return posation;
	}

	public void setPosation(String posation) {
		this.posation = posation;
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

	public String getJobdescription() {
		return jobdescription;
	}

	public void setJobdescription(String jobdescription) {
		this.jobdescription = jobdescription;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getModtime() {
		return modtime;
	}

	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	@Override
	public String toString() {
		return "WorkExperience [workexperienceguid=" + workexperienceguid + ", webuserguid=" + webuserguid + ", workunit=" + workunit + ", posation=" + posation + ", references=" + references
				+ ", referencesphone=" + referencesphone + ", startdate=" + startdate + ", enddate=" + enddate + ", jobdescription=" + jobdescription + ", modtime=" + modtime + ", rmk=" + rmk + "]";
	}

}
