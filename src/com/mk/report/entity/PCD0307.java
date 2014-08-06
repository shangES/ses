package com.mk.report.entity;

import java.util.Map;

import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.quota.dao.QuotaDao;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.User;

public class PCD0307 {
	private String MAININTERVIEWERGUID;
	private String RECOMMENDPOSTGUID;
	private String D1;
	private String D2;
	private String PASSNUMBER;
	private String NUMBERS;
	private String PASSPCT;

	// 临时
	private String RECOMMENDPOSTNAME;
	private String RECOMMENDDEPTNAME;
	private String MAININTERVIEWERNAME;

	public String getMAININTERVIEWERGUID() {
		return MAININTERVIEWERGUID;
	}

	public void setMAININTERVIEWERGUID(String mAININTERVIEWERGUID) {
		MAININTERVIEWERGUID = mAININTERVIEWERGUID;
	}

	public String getRECOMMENDPOSTGUID() {
		return RECOMMENDPOSTGUID;
	}

	public void setRECOMMENDPOSTGUID(String rECOMMENDPOSTGUID) {
		RECOMMENDPOSTGUID = rECOMMENDPOSTGUID;
	}

	public String getD1() {
		return D1;
	}

	public void setD1(String d1) {
		D1 = d1;
	}

	public String getD2() {
		return D2;
	}

	public void setD2(String d2) {
		D2 = d2;
	}

	public String getPASSNUMBER() {
		return PASSNUMBER;
	}

	public void setPASSNUMBER(String pASSNUMBER) {
		PASSNUMBER = pASSNUMBER;
	}

	public String getNUMBERS() {
		return NUMBERS;
	}

	public void setNUMBERS(String nUMBERS) {
		NUMBERS = nUMBERS;
	}

	public String getPASSPCT() {
		return PASSPCT;
	}

	public void setPASSPCT(String pASSPCT) {
		PASSPCT = pASSPCT;
	}

	public String getRECOMMENDPOSTNAME() {
		return RECOMMENDPOSTNAME;
	}

	public void setRECOMMENDPOSTNAME(String rECOMMENDPOSTNAME) {
		RECOMMENDPOSTNAME = rECOMMENDPOSTNAME;
	}

	public String getRECOMMENDDEPTNAME() {
		return RECOMMENDDEPTNAME;
	}

	public void setRECOMMENDDEPTNAME(String rECOMMENDDEPTNAME) {
		RECOMMENDDEPTNAME = rECOMMENDDEPTNAME;
	}

	public String getMAININTERVIEWERNAME() {
		return MAININTERVIEWERNAME;
	}

	public void setMAININTERVIEWERNAME(String mAININTERVIEWERNAME) {
		MAININTERVIEWERNAME = mAININTERVIEWERNAME;
	}

	/**
	 * 翻译
	 * 
	 * @param departmentDao
	 * @param companyDao
	 * @param postDao
	 */
	public void convertOneCodeToName(DepartmentDao departmentDao, CompanyDao companyDao, PostDao postDao, SystemDao systemDao, EmployeeDao employeeDao, QuotaDao quotaDao, OptionDao optionDao) {
		if (this.getMAININTERVIEWERGUID() != null) {
			User user = systemDao.getUserByUserId(this.getMAININTERVIEWERGUID());
			if (user != null) {
				user.convertOneCodeToName(employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
				this.setMAININTERVIEWERNAME(user.getUsername());
				this.setRECOMMENDDEPTNAME(user.getDeptname());
				this.setRECOMMENDPOSTNAME(user.getPostname());
			}
		}
	}

	/***
	 * 批量翻译
	 * 
	 * @param employeeDao
	 * @param companyMap
	 * @param deptMap
	 * @param jobMap
	 * @param rankMap
	 * @param postMap
	 * @param quotaMap
	 */
	public void convertManyCodeToName(EmployeeDao employeeDao, Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, String> jobMap, Map<String, String> rankMap,
			Map<String, Post> postMap, Map<String, String> quotaMap, SystemDao systemDao) {
		if (this.getMAININTERVIEWERGUID() != null) {
			User user = systemDao.getUserByUserId(this.getMAININTERVIEWERGUID());
			user.convertManyCodeToName(employeeDao, companyMap, deptMap, jobMap, rankMap, postMap, quotaMap, null);
			this.setMAININTERVIEWERNAME(user.getUsername());
			this.setRECOMMENDDEPTNAME(user.getDeptname());
			this.setRECOMMENDPOSTNAME(user.getPostname());
		}
	}

}
