package com.mk.recruitment.entity;

import java.sql.Timestamp;

public class About {
	private String aboutguid;
	private Integer dorder;
	private String aboutname;
	private Timestamp modtime;
	private String rmk;

	// 临时
	private String aboutcontent;

	public String getAboutcontent() {
		return aboutcontent;
	}

	public void setAboutcontent(String aboutcontent) {
		this.aboutcontent = aboutcontent;
	}

	public String getAboutguid() {
		return aboutguid;
	}

	public void setAboutguid(String aboutguid) {
		this.aboutguid = aboutguid;
	}

	public Integer getDorder() {
		return dorder;
	}

	public void setDorder(Integer dorder) {
		this.dorder = dorder;
	}

	public String getAboutname() {
		return aboutname;
	}

	public void setAboutname(String aboutname) {
		this.aboutname = aboutname;
	}

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
