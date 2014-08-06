package com.mk.resume.entity;

import java.sql.Timestamp;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;
import com.mk.resume.dao.ResumeDao;

public class ResumeEamil {
	private String resumeeamilguid;
	private String webuserguid;
	private String personal;
	private String email;
	private String subject;
	private String content;
	private Timestamp modtime;
	private Integer readtype;
	private String rmk;
	private String interfacecode;

	// 临时
	List<ResumeEamilFile> resumeeamilfiles;

	public String getInterfacecode() {
		return interfacecode;
	}

	public void setInterfacecode(String interfacecode) {
		this.interfacecode = interfacecode;
	}

	public void setResumeeamilguid(String resumeeamilguid) {
		this.resumeeamilguid = resumeeamilguid;
	}

	public List<ResumeEamilFile> getResumeeamilfiles() {
		return resumeeamilfiles;
	}

	public void setResumeeamilfiles(List<ResumeEamilFile> resumeeamilfiles) {
		this.resumeeamilfiles = resumeeamilfiles;
	}

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

	public String getResumeeamilguid() {
		return resumeeamilguid;
	}

	public void setResumeeamilguid1(String resumeeamilguid) {
		this.resumeeamilguid = resumeeamilguid;
	}

	public String getWebuserguid() {
		return webuserguid;
	}

	public void setWebuserguid(String webuserguid) {
		this.webuserguid = webuserguid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Integer getReadtype() {
		return readtype;
	}

	public void setReadtype(Integer readtype) {
		this.readtype = readtype;
	}

	/**
	 * code转名称
	 * 
	 * @param mapper
	 */
	public void convertOneCodeToName(ResumeDao mapper) {
		// 附件
		List<ResumeEamilFile> list = mapper.getResumeEamilFileByResumeeamilId(this.getResumeeamilguid());
		if (!list.isEmpty()) {
			this.setResumeeamilfiles(list);
		}

	}

}
