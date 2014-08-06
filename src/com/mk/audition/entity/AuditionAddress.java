package com.mk.audition.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;

public class AuditionAddress {
	private String auditionaddressguid;
	private String auditionaddress;
	private Integer state;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;

	public String getAuditionaddressguid() {
		return auditionaddressguid;
	}

	public void setAuditionaddressguid(String auditionaddressguid) {
		this.auditionaddressguid = auditionaddressguid;
	}

	public String getAuditionaddress() {
		return auditionaddress;
	}

	public void setAuditionaddress(String auditionaddress) {
		this.auditionaddress = auditionaddress;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	@Override
	public String toString() {
		return "AuditionAddress [auditionaddressguid=" + auditionaddressguid + ", auditionaddress=" + auditionaddress + ", state=" + state + ", memo=" + memo + ", modiuser=" + modiuser
				+ ", moditimestamp=" + moditimestamp + ", modimemo=" + modimemo + "]";
	}
}
