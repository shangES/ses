package com.mk.recruitment.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;

public class WorkPlace {
	private String workplaceguid;
	private String workplacename;
	private Integer valid;
	private Timestamp modtime;
	private String rmk;
	private Integer dorder;
	
	

	public Integer getDorder() {
		return dorder;
	}

	public void setDorder(Integer dorder) {
		this.dorder = dorder;
	}

	public String getWorkplaceguid() {
		return workplaceguid;
	}

	public void setWorkplaceguid(String workplaceguid) {
		this.workplaceguid = workplaceguid;
	}

	public String getWorkplacename() {
		return workplacename;
	}

	public void setWorkplacename(String workplacename) {
		this.workplacename = workplacename;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
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

}
