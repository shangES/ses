package com.mk.report.entity;

import java.util.Map;

public class PCD0301 {
	private String ASSESSHIERARCHYNAME;
	private String DEPTID;
	private String DEPTCODE;
	private String POSTID;
	private String POSTCODE;
	private int BUDGETNUMBER;
	private String DEPTNAME_1;
	private String DEPTNAME_2;
	private String POSTNAME;
	private int EMPLOYEENUMBER;
	private int QBNUMBER;
	private String BUDGETTYPE;
	private int POSTNUM;

	// 临时
	private String BUDGETTYPENAME;

	public String getASSESSHIERARCHYNAME() {
		return ASSESSHIERARCHYNAME;
	}

	public void setASSESSHIERARCHYNAME(String aSSESSHIERARCHYNAME) {
		ASSESSHIERARCHYNAME = aSSESSHIERARCHYNAME;
	}

	public String getDEPTID() {
		return DEPTID;
	}

	public void setDEPTID(String dEPTID) {
		DEPTID = dEPTID;
	}

	public String getDEPTCODE() {
		return DEPTCODE;
	}

	public void setDEPTCODE(String dEPTCODE) {
		DEPTCODE = dEPTCODE;
	}

	public String getPOSTID() {
		return POSTID;
	}

	public void setPOSTID(String pOSTID) {
		POSTID = pOSTID;
	}

	public String getPOSTCODE() {
		return POSTCODE;
	}

	public void setPOSTCODE(String pOSTCODE) {
		POSTCODE = pOSTCODE;
	}

	public int getBUDGETNUMBER() {
		return BUDGETNUMBER;
	}

	public void setBUDGETNUMBER(int bUDGETNUMBER) {
		BUDGETNUMBER = bUDGETNUMBER;
	}

	public String getDEPTNAME_1() {
		return DEPTNAME_1;
	}

	public void setDEPTNAME_1(String dEPTNAME_1) {
		DEPTNAME_1 = dEPTNAME_1;
	}

	public String getDEPTNAME_2() {
		return DEPTNAME_2;
	}

	public void setDEPTNAME_2(String dEPTNAME_2) {
		DEPTNAME_2 = dEPTNAME_2;
	}

	public String getPOSTNAME() {
		return POSTNAME;
	}

	public void setPOSTNAME(String pOSTNAME) {
		POSTNAME = pOSTNAME;
	}

	public int getEMPLOYEENUMBER() {
		return EMPLOYEENUMBER;
	}

	public void setEMPLOYEENUMBER(int eMPLOYEENUMBER) {
		EMPLOYEENUMBER = eMPLOYEENUMBER;
	}

	public int getQBNUMBER() {
		return QBNUMBER;
	}

	public void setQBNUMBER(int qBNUMBER) {
		QBNUMBER = qBNUMBER;
	}

	public String getBUDGETTYPE() {
		return BUDGETTYPE;
	}

	public void setBUDGETTYPE(String bUDGETTYPE) {
		BUDGETTYPE = bUDGETTYPE;
	}

	public int getPOSTNUM() {
		return POSTNUM;
	}

	public void setPOSTNUM(int pOSTNUM) {
		POSTNUM = pOSTNUM;
	}

	public String getBUDGETTYPENAME() {
		return BUDGETTYPENAME;
	}

	public void setBUDGETTYPENAME(String bUDGETTYPENAME) {
		BUDGETTYPENAME = bUDGETTYPENAME;
	}

	/**
	 * 批量code转名称
	 * 
	 * @param budgettypemap
	 */
	public void convertManyCodeToName(Map<String, String> budgettypemap) {
		// 编制类型
		if (this.getBUDGETTYPE() != null) {
			String name = budgettypemap.get(this.getBUDGETTYPE());
			this.setBUDGETTYPENAME(name);
		}
	}

}
