package com.mk.person.entity;

import java.sql.Date;

public class TmpTrain {

	private String trainid;
	private String mycandidatesguid;
	private Date traindate;
	private Integer trainlength;
	private String trainorg;
	private String content;
	private String result;
	public String getTrainid() {
		return trainid;
	}
	public void setTrainid(String trainid) {
		this.trainid = trainid;
	}
	public String getMycandidatesguid() {
		return mycandidatesguid;
	}
	public void setMycandidatesguid(String mycandidatesguid) {
		this.mycandidatesguid = mycandidatesguid;
	}
	public Date getTraindate() {
		return traindate;
	}
	public void setTraindate(Date traindate) {
		this.traindate = traindate;
	}
	public Integer getTrainlength() {
		return trainlength;
	}
	public void setTrainlength(Integer trainlength) {
		this.trainlength = trainlength;
	}
	public String getTrainorg() {
		return trainorg;
	}
	public void setTrainorg(String trainorg) {
		this.trainorg = trainorg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
