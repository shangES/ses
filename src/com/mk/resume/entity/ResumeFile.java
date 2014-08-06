package com.mk.resume.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;

public class ResumeFile {
	private String resumefileguid;
	private String webuserguid;
	private String resumefilename;
	private String resumefilepath;
	private Integer resumefilesize;
	private Timestamp modtime;
	private String content;

	public ResumeFile() {

	}

	public ResumeFile(String resumefileguid, String webuserguid, String resumefilename, String resumefilepath, Integer resumefilesize, Timestamp modtime) {
		this.resumefileguid = resumefileguid;
		this.webuserguid = webuserguid;
		this.resumefilename = resumefilename;
		this.resumefilepath = resumefilepath;
		this.resumefilesize = resumefilesize;
		this.modtime = modtime;
	}

	public String getResumefileguid() {
		return resumefileguid;
	}

	public void setResumefileguid(String resumefileguid) {
		this.resumefileguid = resumefileguid;
	}

	public String getWebuserguid() {
		return webuserguid;
	}

	public void setWebuserguid(String webuserguid) {
		this.webuserguid = webuserguid;
	}

	public String getResumefilename() {
		return resumefilename;
	}

	public void setResumefilename(String resumefilename) {
		this.resumefilename = resumefilename;
	}

	public String getResumefilepath() {
		return resumefilepath;
	}

	public void setResumefilepath(String resumefilepath) {
		this.resumefilepath = resumefilepath;
	}

	public Integer getResumefilesize() {
		return resumefilesize;
	}

	public void setResumefilesize(Integer resumefilesize) {
		this.resumefilesize = resumefilesize;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getModtime() {
		return modtime;
	}

	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
