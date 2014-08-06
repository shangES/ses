package com.mk.webservice.entity;

public class WSEmployee {
	private String jobnumber;
	private String name;
	private String deptid;
	private String deptname;
	private String pdeptid;
	private String pdeptidname;
	private String deptcode;
	private String postid;
	private String postname;
	private String postcode;
	private String rankid;
	private String rankidname;
	private String quotaid;
	private String quotaidname;
	private String cardnumber;
	private String birthday;
	private String sex;
	private String sexname;
	private String joinworkdate;
	private String joindate;
	private String eduorg;
	private String culture;
	private String culturename;
	private String professional;
	private String authname;
	private String auth;

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getJobnumber() {
		return jobnumber;
	}

	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getPdeptid() {
		return pdeptid;
	}

	public void setPdeptid(String pdeptid) {
		this.pdeptid = pdeptid;
	}

	public String getPdeptidname() {
		return pdeptidname;
	}

	public void setPdeptidname(String pdeptidname) {
		this.pdeptidname = pdeptidname;
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getRankid() {
		return rankid;
	}

	public void setRankid(String rankid) {
		this.rankid = rankid;
	}

	public String getRankidname() {
		return rankidname;
	}

	public void setRankidname(String rankidname) {
		this.rankidname = rankidname;
	}

	public String getQuotaid() {
		return quotaid;
	}

	public void setQuotaid(String quotaid) {
		this.quotaid = quotaid;
	}

	public String getQuotaidname() {
		return quotaidname;
	}

	public void setQuotaidname(String quotaidname) {
		this.quotaidname = quotaidname;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSexname() {
		return sexname;
	}

	public void setSexname(String sexname) {
		this.sexname = sexname;
	}

	public String getJoinworkdate() {
		return joinworkdate;
	}

	public void setJoinworkdate(String joinworkdate) {
		this.joinworkdate = joinworkdate;
	}

	public String getJoindate() {
		return joindate;
	}

	public void setJoindate(String joindate) {
		this.joindate = joindate;
	}

	public String getEduorg() {
		return eduorg;
	}

	public void setEduorg(String eduorg) {
		this.eduorg = eduorg;
	}

	public String getCulture() {
		return culture;
	}

	public void setCulture(String culture) {
		this.culture = culture;
	}

	public String getCulturename() {
		return culturename;
	}

	public void setCulturename(String culturename) {
		this.culturename = culturename;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public String getAuthname() {
		return authname;
	}

	public void setAuthname(String authname) {
		this.authname = authname;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "WSEmployee [jobnumber=" + jobnumber + ", name=" + name + ", deptid=" + deptid + ", deptname=" + deptname + ", pdeptid=" + pdeptid + ", pdeptidname=" + pdeptidname + ", deptcode="
				+ deptcode + ", postid=" + postid + ", postname=" + postname + ", postcode=" + postcode + ", rankid=" + rankid + ", rankidname=" + rankidname + ", quotaid=" + quotaid
				+ ", quotaidname=" + quotaidname + ", cardnumber=" + cardnumber + ", birthday=" + birthday + ", sex=" + sex + ", sexname=" + sexname + ", joinworkdate=" + joinworkdate + ", joindate="
				+ joindate + ", eduorg=" + eduorg + ", culture=" + culture + ", culturename=" + culturename + ", professional=" + professional + ", authname=" + authname + ", auth=" + auth + "]";
	}

}
