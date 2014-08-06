package com.mk.resume.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;

public class TrainingExperience {
	private String trainingexperienceguid;
	private String webuserguid;
	private Date startdate;
	private Date enddate;
	private String traininginstitutions;
	private String certificate;
	private String trainingcontent;
	private Timestamp modtime;
	private String rmk;

	public String getTrainingexperienceguid() {
		return trainingexperienceguid;
	}

	public void setTrainingexperienceguid(String trainingexperienceguid) {
		this.trainingexperienceguid = trainingexperienceguid;
	}

	public String getWebuserguid() {
		return webuserguid;
	}

	public void setWebuserguid(String webuserguid) {
		this.webuserguid = webuserguid;
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

	public String getTraininginstitutions() {
		return traininginstitutions;
	}

	public void setTraininginstitutions(String traininginstitutions) {
		this.traininginstitutions = traininginstitutions;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getTrainingcontent() {
		return trainingcontent;
	}

	public void setTrainingcontent(String trainingcontent) {
		this.trainingcontent = trainingcontent;
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
		return "TrainingExperience [trainingexperienceguid=" + trainingexperienceguid + ", webuserguid=" + webuserguid + ", startdate=" + startdate + ", enddate=" + enddate
				+ ", traininginstitutions=" + traininginstitutions + ", certificate=" + certificate + ", trainingcontent=" + trainingcontent + ", modtime=" + modtime + ", rmk=" + rmk + "]";
	}

}
