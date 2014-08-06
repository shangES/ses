package com.mk.report.entity;

import com.mk.company.dao.CompanyDao;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.employee.dao.EmployeeDao;
import com.mk.framework.constance.OptionConstance;
import com.mk.quota.dao.QuotaDao;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.OptionList;

public class PCD0306 {
	private Integer CANDIDATESTYPE;
	private String DEPTNAME_1;
	private String DEPTNAME_2;
	private String POSTNAME;
	private String RECOMMENDNUM;
	private String PERSONNUM_15;

	// 临时
	private String CANDIDATESTYPENAME;

	public Integer getCANDIDATESTYPE() {
		return CANDIDATESTYPE;
	}

	public void setCANDIDATESTYPE(Integer cANDIDATESTYPE) {
		CANDIDATESTYPE = cANDIDATESTYPE;
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

	public String getRECOMMENDNUM() {
		return RECOMMENDNUM;
	}

	public void setRECOMMENDNUM(String rECOMMENDNUM) {
		RECOMMENDNUM = rECOMMENDNUM;
	}

	public String getPERSONNUM_15() {
		return PERSONNUM_15;
	}

	public void setPERSONNUM_15(String pERSONNUM_15) {
		PERSONNUM_15 = pERSONNUM_15;
	}

	public String getCANDIDATESTYPENAME() {
		return CANDIDATESTYPENAME;
	}

	public void setCANDIDATESTYPENAME(String cANDIDATESTYPENAME) {
		CANDIDATESTYPENAME = cANDIDATESTYPENAME;
	}

	/**
	 * 翻译
	 * 
	 * @param departmentDao
	 * @param companyDao
	 * @param postDao
	 */
	public void convertOneCodeToName(DepartmentDao departmentDao, CompanyDao companyDao, PostDao postDao, SystemDao systemDao, EmployeeDao employeeDao, QuotaDao quotaDao, OptionDao optionDao) {
		// // 推荐公司
		// if (this.getRECOMMENDCOMPANYID() != null) {
		// Company company =
		// OrgPathUtil.getOneCompanyFullPath(this.getRECOMMENDCOMPANYID(),
		// companyDao);
		// if (company != null)
		// this.setRECOMMENDCOMPANYNAME(company.getCompanyfullname());
		// }
		//
		// // 推荐部门
		// if (this.getRECOMMENDDEPTID() != null) {
		// Department dept =
		// OrgPathUtil.getOneDepartmentFullPath(this.getRECOMMENDDEPTID(),
		// departmentDao);
		// if (dept != null)
		// this.setRECOMMENDDEPTNAME(dept.getDeptfullname());
		// }
		//
		// // 推荐岗位
		// if (this.getRECOMMENDPOSTGUID() != null) {
		// Post post = postDao.getPostByPostId(this.getRECOMMENDPOSTGUID());
		// if (post != null) {
		// this.setRECOMMENDPOSTNAME(post.getPostname());
		// }
		// }

		// 来源渠道
		if (this.getCANDIDATESTYPE() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RESUMETYPE, this.getCANDIDATESTYPE());
			if (opt != null)
				this.setCANDIDATESTYPENAME(opt.getName());
		}
	}

}
