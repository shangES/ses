package com.mk.recruitment.entity;

import java.sql.Date;

public class News {
	private String newsguid;
	private String moduleguid;
	private String title;
	private String subtitle;
	private String source;
	private String keyword;
	private Integer valid;
	private Integer dorder;
	private Integer isaudited;
	private Integer traffic;
	private String titlepic;
	private String audituser;
	private String pubuser;
	private Date modtime;
	private String rmk;

	// 正文
	private NewsContent newscontent;

	public Integer getDorder() {
		return dorder;
	}

	public void setDorder(Integer dorder) {
		this.dorder = dorder;
	}

	public NewsContent getNewscontent() {
		return newscontent;
	}

	public void setNewscontent(NewsContent newscontent) {
		this.newscontent = newscontent;
	}

	public String getNewsguid() {
		return newsguid;
	}

	public void setNewsguid(String newsguid) {
		this.newsguid = newsguid;
	}

	public String getModuleguid() {
		return moduleguid;
	}

	public void setModuleguid(String moduleguid) {
		this.moduleguid = moduleguid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getIsaudited() {
		return isaudited;
	}

	public void setIsaudited(Integer isaudited) {
		this.isaudited = isaudited;
	}

	public Integer getTraffic() {
		return traffic;
	}

	public void setTraffic(Integer traffic) {
		this.traffic = traffic;
	}

	public String getTitlepic() {
		return titlepic;
	}

	public void setTitlepic(String titlepic) {
		this.titlepic = titlepic;
	}

	public String getAudituser() {
		return audituser;
	}

	public void setAudituser(String audituser) {
		this.audituser = audituser;
	}

	public String getPubuser() {
		return pubuser;
	}

	public void setPubuser(String pubuser) {
		this.pubuser = pubuser;
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

}
