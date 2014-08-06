package com.mk.recruitment.entity;

import java.sql.Date;

public class Carousel {
	private String carouselguid;
	private String title;
	private String pubuser;
	private Integer isaudited;
	private String titlepic;
	private String audituser;
	private Integer valid;
	private Date modtime;
	private String rmk;
	private Integer dorder;

	// 临时
	private String pubusername;
	private String auditusername;

	// 正文
	private CarouselContent carouselcontent;

	public Integer getDorder() {
		return dorder;
	}

	public void setDorder(Integer dorder) {
		this.dorder = dorder;
	}

	public CarouselContent getCarouselcontent() {
		return carouselcontent;
	}

	public void setCarouselcontent(CarouselContent carouselcontent) {
		this.carouselcontent = carouselcontent;
	}

	public String getPubusername() {
		return pubusername;
	}

	public void setPubusername(String pubusername) {
		this.pubusername = pubusername;
	}

	public String getAuditusername() {
		return auditusername;
	}

	public void setAuditusername(String auditusername) {
		this.auditusername = auditusername;
	}

	public String getCarouselguid() {
		return carouselguid;
	}

	public void setCarouselguid(String carouselguid) {
		this.carouselguid = carouselguid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPubuser() {
		return pubuser;
	}

	public void setPubuser(String pubuser) {
		this.pubuser = pubuser;
	}

	public Integer getIsaudited() {
		return isaudited;
	}

	public void setIsaudited(Integer isaudited) {
		this.isaudited = isaudited;
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

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
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
