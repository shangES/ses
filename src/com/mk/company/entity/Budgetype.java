package com.mk.company.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;

public class Budgetype {
	private String companyid;
	private String budgettypeid;
	private String budgettypename;
	private Integer state;
	private String dorder;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getBudgettypeid() {
		return budgettypeid;
	}

	public void setBudgettypeid(String budgettypeid) {
		this.budgettypeid = budgettypeid;
	}

	public String getBudgettypename() {
		return budgettypename;
	}

	public void setBudgettypename(String budgettypename) {
		this.budgettypename = budgettypename;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getDorder() {
		return dorder;
	}

	public void setDorder(String dorder) {
		this.dorder = dorder;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getModiuser() {
		return modiuser;
	}

	public void setModiuser(String modiuser) {
		this.modiuser = modiuser;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getModitimestamp() {
		return moditimestamp;
	}

	public void setModitimestamp(Timestamp moditimestamp) {
		this.moditimestamp = moditimestamp;
	}

	public String getModimemo() {
		return modimemo;
	}

	public void setModimemo(String modimemo) {
		this.modimemo = modimemo;
	}

}
