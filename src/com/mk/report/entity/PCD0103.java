package com.mk.report.entity;

import java.sql.Date;

public class PCD0103 {
	private String CLASSIFICATION;
	private String JOBNUMBER;
	private String COMPANYNAME;
	private String DEPTNAME;
	private String POSTNAME;
	private String LEVELNAME;
	private String NAME;
	private String SEX;
	private Date JOINDATE;
	private String BUDGETTYPE;
	private Date CONTRACT_ENDDATE;

	public String getCLASSIFICATION() {
		return CLASSIFICATION;
	}

	public void setCLASSIFICATION(String cLASSIFICATION) {
		CLASSIFICATION = cLASSIFICATION;
	}

	public String getJOBNUMBER() {
		return JOBNUMBER;
	}

	public void setJOBNUMBER(String jOBNUMBER) {
		JOBNUMBER = jOBNUMBER;
	}

	public String getCOMPANYNAME() {
		return COMPANYNAME;
	}

	public void setCOMPANYNAME(String cOMPANYNAME) {
		COMPANYNAME = cOMPANYNAME;
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

	public String getLEVELNAME() {
		return LEVELNAME;
	}

	public void setLEVELNAME(String lEVELNAME) {
		LEVELNAME = lEVELNAME;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getSEX() {
		return SEX;
	}

	public void setSEX(String sEX) {
		SEX = sEX;
	}

	public Date getJOINDATE() {
		return JOINDATE;
	}

	public void setJOINDATE(Date jOINDATE) {
		JOINDATE = jOINDATE;
	}

	public String getBUDGETTYPE() {
		return BUDGETTYPE;
	}

	public void setBUDGETTYPE(String bUDGETTYPE) {
		BUDGETTYPE = bUDGETTYPE;
	}

	public Date getCONTRACT_ENDDATE() {
		return CONTRACT_ENDDATE;
	}

	public void setCONTRACT_ENDDATE(Date cONTRACT_ENDDATE) {
		CONTRACT_ENDDATE = cONTRACT_ENDDATE;
	}

}
