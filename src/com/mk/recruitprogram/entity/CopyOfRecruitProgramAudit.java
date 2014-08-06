package com.mk.recruitprogram.entity;

import java.sql.Date;
import java.util.Map;

import com.mk.OrgPathUtil;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.company.entity.Rank;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.framework.grid.util.StringUtils;
import com.mk.quota.dao.QuotaDao;
import com.mk.quota.entity.Quota;
import com.mk.system.dao.OptionDao;

public class CopyOfRecruitProgramAudit {

	private String recruitprogramauditguid;
	private String recruitprogramguid;
	private String companyid;
	private String deptid;
	private String postid;
	private String rankid;
	private Integer postnum;
	private Date startdate;
	private Date enddate;
	private String auditresult;
	private Integer state;
	private String quotaid;
	private String companyname;
	private String deptname;
	private String postname;
	private String rankname;
	private String interfacecode;
	private Integer remainnum;
	//期望到岗时间
	private Date hillockdate;

	// 临时属性
	private String quotaname;
	private String quotacode;
	private String recommenddeptname;
	private String name;
	private String timeformat;
	private String wordcode;

	public Date getHillockdate() {
		return hillockdate;
	}

	public void setHillockdate(Date hillockdate) {
		this.hillockdate = hillockdate;
	}

	public Integer getRemainnum() {
		return remainnum;
	}

	public void setRemainnum(Integer remainnum) {
		this.remainnum = remainnum;
	}

	public String getRecruitprogramauditguid() {
		return recruitprogramauditguid;
	}

	public void setRecruitprogramauditguid(String recruitprogramauditguid) {
		this.recruitprogramauditguid = recruitprogramauditguid;
	}

	public String getRecruitprogramguid() {
		return recruitprogramguid;
	}

	public void setRecruitprogramguid(String recruitprogramguid) {
		this.recruitprogramguid = recruitprogramguid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	public String getRankid() {
		return rankid;
	}

	public void setRankid(String rankid) {
		this.rankid = rankid;
	}

	public Integer getPostnum() {
		return postnum;
	}

	public void setPostnum(Integer postnum) {
		this.postnum = postnum;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getAuditresult() {
		return auditresult;
	}

	public void setAuditresult(String auditresult) {
		this.auditresult = auditresult;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getQuotaid() {
		return quotaid;
	}

	public void setQuotaid(String quotaid) {
		this.quotaid = quotaid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getRankname() {
		return rankname;
	}

	public void setRankname(String rankname) {
		this.rankname = rankname;
	}

	public String getQuotaname() {
		return quotaname;
	}

	public void setQuotaname(String quotaname) {
		this.quotaname = quotaname;
	}

	public String getQuotacode() {
		return quotacode;
	}

	public void setQuotacode(String quotacode) {
		this.quotacode = quotacode;
	}

	public String getInterfacecode() {
		return interfacecode;
	}

	public void setInterfacecode(String interfacecode) {
		this.interfacecode = interfacecode;
	}

	public String getRecommenddeptname() {
		return recommenddeptname;
	}

	public void setRecommenddeptname(String recommenddeptname) {
		this.recommenddeptname = recommenddeptname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTimeformat() {
		return timeformat;
	}

	public void setTimeformat(String timeformat) {
		this.timeformat = timeformat;
	}

	public String getWordcode() {
		return wordcode;
	}

	public void setWordcode(String wordcode) {
		this.wordcode = wordcode;
	}

	/**
	 * code转型
	 * 
	 * @param companyDao
	 * @param departmentDao
	 * @param postDao
	 */
	public void convertOneCodeToName(CompanyDao companyDao, DepartmentDao departmentDao, PostDao postDao, QuotaDao quotaDao, OptionDao optionDao) {
		// 编制
		if (this.getQuotaid() != null) {
			Quota quota = quotaDao.getQuotaById(this.getQuotaid());
			if (quota != null) {
				quota.convertBudgettype(companyDao);
				this.setQuotaname(quota.getBudgettypename());
				// this.setQuotacode(quota.getQuotacode());
			}
		}

		// 公司
		if (this.getCompanyid() != null) {
			Company company = OrgPathUtil.getOneCompanyFullPath(this.getCompanyid(), companyDao);
			if (company != null)
				this.setCompanyname(company.getCompanyfullname());
		}

		// 部门
		if (this.getDeptid() != null) {
			Department dept = OrgPathUtil.getOneDepartmentFullPath(this.getDeptid(), departmentDao);
			if (dept != null)
				this.setDeptname(dept.getDeptfullname());
		}

		// 岗位
		if (this.getPostid() != null) {
			Post post = postDao.getPostByPostId(this.getPostid());
			if (post != null)
				this.setPostname(post.getPostname());
		}

//		// 职级
//		if (this.getRankid() != null) {
//			Rank rank = companyDao.getRankById(this.getRankid());
//			if (rank != null) {
//				this.setRankname(rank.getLevelname());
//			}
//		} else {
//			if (!StringUtils.isEmpty(this.getRankname())) {
//				Rank rank = companyDao.getRankByInterfacecode(this.getRankname(),this.getCompanyid());
//				if (rank != null) {
//					this.setRankname(rank.getLevelname());
//				}
//			}
//		}
	}

	/**
	 * code转型
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param postMap
	 * @param quotaMap
	 * @param rankMap
	 */
	public void convertManyCodeToName(CompanyDao companyDao, Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, Post> postMap, Map<String, String> rankMap,
			Map<String, String> quotaMap) {
		// 公司
		if (this.getCompanyid() != null) {
			Company company = companyMap.get(this.getCompanyid());
			if (company != null)
				this.setCompanyname(company.getCompanyfullname());
		}

		// 部门
		if (this.getDeptid() != null) {
			Department dept = deptMap.get(this.getDeptid());
			if (dept != null)
				this.setDeptname(dept.getDeptfullname());
		}

		// 岗位
		if (this.getPostid() != null) {
			Post post = postMap.get(this.getPostid());
			if (post != null)
				this.setPostname(post.getPostname());
		}

//		// 职级
//		if (this.getRankid() != null) {
//			String name = rankMap.get(this.getRankid());
//			this.setRankname(name);
//		} else {
//			if (!StringUtils.isEmpty(this.getRankname())) {
//				Rank rank = companyDao.getRankByInterfacecode(this.getRankname(),this.getCompanyid());
//				if (rank != null) {
//					this.setRankname(rank.getLevelname());
//				}
//			}
//		}

		// 编制类型
		if (this.getQuotaid() != null) {
			String name = quotaMap.get(this.getQuotaid());
			this.setQuotaname(name);
		}

	}

	@Override
	public String toString() {
		return "RecruitProgramAudit [recruitprogramauditguid=" + recruitprogramauditguid + ", recruitprogramguid=" + recruitprogramguid + ", companyid=" + companyid + ", deptid=" + deptid
				+ ", postid=" + postid + ", rankid=" + rankid + ", postnum=" + postnum + ", startdate=" + startdate + ", enddate=" + enddate + ", auditresult=" + auditresult + ", state=" + state
				+ ", quotaid=" + quotaid + ", companyname=" + companyname + ", deptname=" + deptname + ", postname=" + postname + ", rankname=" + rankname + ", interfacecode=" + interfacecode
				+ ", quotaname=" + quotaname + ", quotacode=" + quotacode + "]";
	}

}
