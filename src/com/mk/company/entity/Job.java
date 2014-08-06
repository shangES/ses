package com.mk.company.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;

public class Job {
	private String jobid;
	private String companyid;
	private String jobname;
	private String description;
	private Integer state;
	private String dorder;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;

	public String getJobid() {
		return jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getDorder() {
		return dorder;
	}

	public void setDorder(String dorder) {
		this.dorder = dorder;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getModiuser() {
		return modiuser;
	}

	public void setModiuser(String modiuser) {
		this.modiuser = modiuser;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getModitimestamp() {
		return moditimestamp;
	}

	public void setModitimestamp(Timestamp moditimestamp) {
		this.moditimestamp = moditimestamp;
	}

	public String getModimemo() {
		return modimemo;
	}

	public void setModimemo(String modimemo) {
		this.modimemo = modimemo;
	}

	@Override
	public String toString() {
		return "Job [jobid=" + jobid + ", companyid=" + companyid + ", jobname=" + jobname + ", description=" + description + ", state=" + state + ", dorder=" + dorder + ", memo=" + memo
				+ ", modiuser=" + modiuser + ", moditimestamp=" + moditimestamp + ", modimemo=" + modimemo + "]";
	}

}
