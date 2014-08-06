package com.mk.audition.entity;

public class Interviewer {
	private String interviewerguid;
	private String auditionrecordguid;
	private String userguid;

	private String username;

	public String getInterviewerguid() {
		return interviewerguid;
	}

	public void setInterviewerguid(String interviewerguid) {
		this.interviewerguid = interviewerguid;
	}

	public String getAuditionrecordguid() {
		return auditionrecordguid;
	}

	public void setAuditionrecordguid(String auditionrecordguid) {
		this.auditionrecordguid = auditionrecordguid;
	}

	public String getUserguid() {
		return userguid;
	}

	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Interviewer [interviewerguid=" + interviewerguid + ", auditionrecordguid=" + auditionrecordguid + ", userguid=" + userguid + ", username=" + username + "]";
	}

}
