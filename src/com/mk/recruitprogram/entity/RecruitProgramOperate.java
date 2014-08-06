package com.mk.recruitprogram.entity;

import java.sql.Timestamp;

public class RecruitProgramOperate {
	private String recruitprogramoperateguid;
	private String recruitprogramguid;
	private Integer operatestate;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;
	private Integer operatenum;

	public String getRecruitprogramoperateguid() {
		return recruitprogramoperateguid;
	}

	public void setRecruitprogramoperateguid(String recruitprogramoperateguid) {
		this.recruitprogramoperateguid = recruitprogramoperateguid;
	}

	public String getRecruitprogramguid() {
		return recruitprogramguid;
	}

	public void setRecruitprogramguid(String recruitprogramguid) {
		this.recruitprogramguid = recruitprogramguid;
	}

	public Integer getOperatestate() {
		return operatestate;
	}

	public void setOperatestate(Integer operatestate) {
		this.operatestate = operatestate;
	}

	public String getModiuser() {
		return modiuser;
	}

	public void setModiuser(String modiuser) {
		this.modiuser = modiuser;
	}

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

	public Integer getOperatenum() {
		return operatenum;
	}

	public void setOperatenum(Integer operatenum) {
		this.operatenum = operatenum;
	}

}
