package com.mk.todo.entity;

public class MsgData {

	// 招聘计划审批
	private int recruitprograms;
	// 待安排的面试
	private int affirms;
	// 待安排的面试
	private int interviews;
	// 面试结果待反馈
	private int affirmauditionresults;
	// 待安排的体检
	private int examinations;
	// 待入职的应聘者
	private int entryoktodos;
	//待发布的面试结果
	private int releases;
	// 总的操作数据
	private int news;
	
	private int audit;

	// 最新的一条操作数据
	private String newsmsg;

	// 操作权限
	private boolean role;

	//最新的一条数据编号
	private Integer hotnews;
	
	public int getReleases() {
		return releases;
	}

	public void setReleases(int releases) {
		this.releases = releases;
	}

	public boolean isRole() {
		return role;
	}

	public void setRole(boolean role) {
		this.role = role;
	}

	public int getNews() {
		return news;
	}

	public void setNews(int news) {
		this.news = news;
	}

	public int getAffirms() {
		return affirms;
	}

	public void setAffirms(int affirms) {
		this.affirms = affirms;
	}

	public int getRecruitprograms() {
		return recruitprograms;
	}

	public void setRecruitprograms(int recruitprograms) {
		this.recruitprograms = recruitprograms;
	}

	public int getInterviews() {
		return interviews;
	}

	public void setInterviews(int interviews) {
		this.interviews = interviews;
	}

	public int getAffirmauditionresults() {
		return affirmauditionresults;
	}

	public void setAffirmauditionresults(int affirmauditionresults) {
		this.affirmauditionresults = affirmauditionresults;
	}

	public int getExaminations() {
		return examinations;
	}

	public void setExaminations(int examinations) {
		this.examinations = examinations;
	}

	public int getEntryoktodos() {
		return entryoktodos;
	}

	public void setEntryoktodos(int entryoktodos) {
		this.entryoktodos = entryoktodos;
	}

	public String getNewsmsg() {
		return newsmsg;
	}

	public void setNewsmsg(String newsmsg) {
		this.newsmsg = newsmsg;
	}

	public Integer getHotnews() {
		return hotnews;
	}

	public void setHotnews(Integer hotnews) {
		this.hotnews = hotnews;
	}

	public int getAudit() {
		return audit;
	}

	public void setAudit(int audit) {
		this.audit = audit;
	}

}
