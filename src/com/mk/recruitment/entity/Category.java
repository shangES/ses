package com.mk.recruitment.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;

public class Category {
	private String categoryguid;
	private String categoryname;
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

	public String getCategoryguid() {
		return categoryguid;
	}

	public void setCategoryguid(String categoryguid) {
		this.categoryguid = categoryguid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
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
