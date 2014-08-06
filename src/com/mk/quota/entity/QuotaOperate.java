package com.mk.quota.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;

public class QuotaOperate {
	private String quotaoperateguid;
	private String quotaid;
	private Integer operatestate;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;
	private Integer operatenum;

	public QuotaOperate() {

	}

	public QuotaOperate(String quotaoperateguid, String quotaid, Integer operatestate, String modiuser, Timestamp moditimestamp, String modimemo,Integer operatenum) {
		this.quotaoperateguid = quotaoperateguid;
		this.quotaid = quotaid;
		this.operatestate = operatestate;
		this.modiuser = modiuser;
		this.moditimestamp = moditimestamp;
		this.modimemo = modimemo;
		this.setOperatenum(operatenum);
	}

	public Integer getOperatenum() {
		return operatenum;
	}

	public void setOperatenum(Integer operatenum) {
		this.operatenum = operatenum;
	}

	public String getQuotaoperateguid() {
		return quotaoperateguid;
	}

	public void setQuotaoperateguid(String quotaoperateguid) {
		this.quotaoperateguid = quotaoperateguid;
	}

	public String getQuotaid() {
		return quotaid;
	}

	public void setQuotaid(String quotaid) {
		this.quotaid = quotaid;
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

	@Override
	public String toString() {
		return "QuotaOperate [quotaoperateguid=" + quotaoperateguid + ", quotaid=" + quotaid + ", operatestate=" + operatestate + ", modiuser=" + modiuser + ", moditimestamp=" + moditimestamp
				+ ", modimemo=" + modimemo + "]";
	}

}
