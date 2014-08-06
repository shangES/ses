package com.mk.recruitment.entity;

import java.sql.Date;

import com.mk.framework.constance.Constance;
import com.mk.framework.utils.UUIDGenerator;

public class WebUser {
	private String webuserguid;
	private String thirdpartnerguid;
	private String email;
	private String username;
	private String password;
	private Integer isadmin;
	private Integer valid;
	private String code;
	private String mobile;
	private Date modtime;
	private String rmk;
	
	
	
	//临时
	private String mycandidatesguid;

	public WebUser() {

	}

	public WebUser(String email, String username) {
		this.setEmail(email);
		this.setUsername(username);
		this.setPassword(Constance.pwd);
		this.setIsadmin(Constance.User0);
		this.setValid(Constance.VALID_YES);
		this.setCode(UUIDGenerator.randomUUID());
		this.setModtime(new Date(System.currentTimeMillis()));
	}

	public String getWebuserguid() {
		return webuserguid;
	}

	public void setWebuserguid(String webuserguid) {
		this.webuserguid = webuserguid;
	}

	public String getThirdpartnerguid() {
		return thirdpartnerguid;
	}

	public void setThirdpartnerguid(String thirdpartnerguid) {
		this.thirdpartnerguid = thirdpartnerguid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(Integer isadmin) {
		this.isadmin = isadmin;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getModtime() {
		return modtime;
	}

	public void setModtime(Date modtime) {
		this.modtime = modtime;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	
	
	

	public String getMycandidatesguid() {
		return mycandidatesguid;
	}

	public void setMycandidatesguid(String mycandidatesguid) {
		this.mycandidatesguid = mycandidatesguid;
	}

	@Override
	public String toString() {
		return "WebUser [webuserguid=" + webuserguid + ", thirdpartnerguid=" + thirdpartnerguid + ", email=" + email + ", username=" + username + ", password=" + password + ", isadmin=" + isadmin
				+ ", valid=" + valid + ", code=" + code + ", mobile=" + mobile + ", modtime=" + modtime + ", rmk=" + rmk + "]";
	}

}
