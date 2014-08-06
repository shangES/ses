package com.mk.report.entity;

import java.sql.Date;

public class PCD0111 {
	private String COMPANYNAME;
	private String JOBNUMBER;
	private String NAME;
	private String DEPTNAME;
	private String POSTNAME;
	private Date JOINDATE;
	private Date JOINGROUPDATE;
	private String GROUPWORKAGE;
	private Date JOINWORKDATE;
	private String WORKAGE;

	public String getCOMPANYNAME() {
		return COMPANYNAME;
	}

	public void setCOMPANYNAME(String cOMPANYNAME) {
		COMPANYNAME = cOMPANYNAME;
	}

	public String getJOBNUMBER() {
		return JOBNUMBER;
	}

	public void setJOBNUMBER(String jOBNUMBER) {
		JOBNUMBER = jOBNUMBER;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getDEPTNAME() {
		return DEPTNAME;
	}

	public void setDEPTNAME(String dEPTNAME) {
		DEPTNAME = dEPTNAME;
	}

	public String getPOSTNAME() {
		return POSTNAME;
	}

	public void setPOSTNAME(String pOSTNAME) {
		POSTNAME = pOSTNAME;
	}

	public Date getJOINDATE() {
		return JOINDATE;
	}

	public void setJOINDATE(Date jOINDATE) {
		JOINDATE = jOINDATE;
	}

	public Date getJOINGROUPDATE() {
		return JOINGROUPDATE;
	}

	public void setJOINGROUPDATE(Date jOINGROUPDATE) {
		JOINGROUPDATE = jOINGROUPDATE;
	}

	public String getGROUPWORKAGE() {
		return GROUPWORKAGE;
	}

	public void setGROUPWORKAGE(String gROUPWORKAGE) {
		GROUPWORKAGE = gROUPWORKAGE;
	}

	public Date getJOINWORKDATE() {
		return JOINWORKDATE;
	}

	public void setJOINWORKDATE(Date jOINWORKDATE) {
		JOINWORKDATE = jOINWORKDATE;
	}

	public String getWORKAGE() {
		return WORKAGE;
	}

	public void setWORKAGE(String wORKAGE) {
		WORKAGE = wORKAGE;
	}

}
