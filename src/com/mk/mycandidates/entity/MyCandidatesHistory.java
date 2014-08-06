package com.mk.mycandidates.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.custom.CustomDateSerializer;

public class MyCandidatesHistory {
	private String mycandidateshistoryguid;
	private String mycandidatesguid;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;

	public String getMycandidateshistoryguid() {
		return mycandidateshistoryguid;
	}

	public void setMycandidateshistoryguid(String mycandidateshistoryguid) {
		this.mycandidateshistoryguid = mycandidateshistoryguid;
	}

	public String getMycandidatesguid() {
		return mycandidatesguid;
	}

	public void setMycandidatesguid(String mycandidatesguid) {
		this.mycandidatesguid = mycandidatesguid;
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
