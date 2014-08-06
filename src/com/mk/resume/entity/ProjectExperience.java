package com.mk.resume.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;

public class ProjectExperience {
	private String projectexperienceguid;
	private String webuserguid;
	private String itemname;
	private Date startdate;
	private Date enddate;
	private String jobdescription;
	private Timestamp modtime;
	private String rmk;

	public String getProjectexperienceguid() {
		return projectexperienceguid;
	}

	public void setProjectexperienceguid(String projectexperienceguid) {
		this.projectexperienceguid = projectexperienceguid;
	}

	public String getWebuserguid() {
		return webuserguid;
	}

	public void setWebuserguid(String webuserguid) {
		this.webuserguid = webuserguid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
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
		return "ProjectExperience [projectexperienceguid=" + projectexperienceguid + ", webuserguid=" + webuserguid + ", itemname=" + itemname + ", startdate=" + startdate + ", enddate=" + enddate
				+ ", jobdescription=" + jobdescription + ", modtime=" + modtime + ", rmk=" + rmk + "]";
	}

	
	
}
