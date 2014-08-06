package com.mk.resume.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;

public class ResumeEamilFile {

	private String resumeeamilfileguid;
	private String resumeeamilguid;
	private String filename;
	private String filepath;
	private Timestamp modtime;
	private String rmk;

	public String getResumeeamilfileguid() {
		return resumeeamilfileguid;
	}

	public void setResumeeamilfileguid(String resumeeamilfileguid) {
		this.resumeeamilfileguid = resumeeamilfileguid;
	}

	public String getResumeeamilguid() {
		return resumeeamilguid;
	}

	public void setResumeeamilguid(String resumeeamilguid) {
		this.resumeeamilguid = resumeeamilguid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
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
		return "ResumeEamilFile [resumeeamilguid=" + resumeeamilguid + ", filename=" + filename + ", filepath=" + filepath + "]";
	}

}
