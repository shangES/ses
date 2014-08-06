package com.mk.employee.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.OrgPathUtil;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.company.entity.Job;
import com.mk.company.entity.Rank;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.framework.constance.Constance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.quota.dao.QuotaDao;
import com.mk.quota.entity.Quota;
import com.mk.system.dao.OptionDao;

public class Position {

	private String postionguid;
	private String employeeid;
	private String companyid;
	private String deptid;
	private String postid;
	private String jobid;
	private String quotaid;
	private String rankid;
	private int isstaff;
	private Date startdate;
	private Date enddate;
	private Integer state;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;

	// 临时
	private String companyname;
	private String deptname;
	private String postname;
	private String jobname;
	private String quotaname;
	private String rankname;
	private String isstaffname;

	public Position() {

	}

	public Position(Employee model) {
		this.setPostionguid(UUIDGenerator.randomUUID());
		this.setEmployeeid(model.getEmployeeid());
		this.setCompanyid(model.getCompanyid());
		this.setDeptid(model.getDeptid());
		this.setPostid(model.getPostid());
		this.setJobid(model.getJobid());
		this.setQuotaid(model.getQuotaid());
		this.setRankid(model.getRankid());
		// 是否占编制(0:不占编制,1:占编制)
		this.setIsstaff(Constance.IsStaff_Yes);
		this.setStartdate(model.getStartdate());
		this.setEnddate(model.getEnddate());
		this.setState(Constance.VALID_YES);
		this.setMemo(model.getMemo());
		this.setModiuser(model.getModiuser());
		this.setModitimestamp(model.getModitimestamp());
		this.setModimemo(model.getModimemo());
	}

	public String getIsstaffname() {
		return Constance.getIsStaffName(this.getIsstaff());
	}

	public void setIsstaffname(String isstaffname) {
		this.isstaffname = isstaffname;
	}

	public String getRankid() {
		return rankid;
	}

	public void setRankid(String rankid) {
		this.rankid = rankid;
	}

	public String getRankname() {
		return rankname;
	}

	public void setRankname(String rankname) {
		this.rankname = rankname;
	}

	public int getIsstaff() {
		return isstaff;
	}

	public void setIsstaff(int isstaff) {
		this.isstaff = isstaff;
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

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getQuotaname() {
		return quotaname;
	}

	public void setQuotaname(String quotaname) {
		this.quotaname = quotaname;
	}

	public String getPostionguid() {
		return postionguid;
	}

	public void setPostionguid(String postionguid) {
		this.postionguid = postionguid;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
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

	public String getJobid() {
		return jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
	}

	public String getQuotaid() {
		return quotaid;
	}

	public void setQuotaid(String quotaid) {
		this.quotaid = quotaid;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getModiuser() {
		return modiuser;
	}

	public void setModiuser(String modiuser) {
		this.modiuser = modiuser;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getModitimestamp() {
		return moditimestamp;
	}

	public void setModitimestamp(Timestamp moditimestamp) {
		this.moditimestamp = moditimestamp;
	}

	public String getModimemo() {
		return modimemo;
	}

	public void setModimemo(String modimemo) {
		this.modimemo = modimemo;
	}

	@Override
	public String toString() {
		return "Position [postionguid=" + postionguid + ", employeeid=" + employeeid + ", companyid=" + companyid + ", deptid=" + deptid + ", postid=" + postid + ", jobid=" + jobid + ", quotaid="
				+ quotaid + ", isstaff=" + isstaff + ", startdate=" + startdate + ", enddate=" + enddate + ", state=" + state + ", memo=" + memo + ", modiuser=" + modiuser + ", moditimestamp="
				+ moditimestamp + ", modimemo=" + modimemo + ", companyname=" + companyname + ", deptname=" + deptname + ", postname=" + postname + ", jobname=" + jobname + ", quotaname=" + quotaname
				+ ", isstaffname=" + isstaffname + "]";
	}

	/**
	 * code转名称
	 * 
	 * @param companyDao
	 * @param departmentDao
	 */
	public void convertOneCodeToName(CompanyDao companyDao, DepartmentDao departmentDao, PostDao postDao, QuotaDao quotaDao, OptionDao optionDao) {

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

		// 职务
		if (this.getJobid() != null) {
			Job job = companyDao.getJobById(this.getJobid());
			if (job != null)
				this.setJobname(job.getJobname());
		}

		// 职级
		if (this.getRankid() != null) {
			Rank rank = companyDao.getRankById(this.getRankid());
			if (rank != null)
				this.setRankname(rank.getLevelname());
		}

		// 编制
		if (this.getQuotaid() != null) {
			Quota quota = quotaDao.getQuotaById(this.getQuotaid());
			if (quota != null) {
				quota.convertBudgettype(companyDao);
				this.setQuotaname(quota.getBudgettypename());
			}

		}

	}

	/**
	 * 批量code转名称
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param jobMap
	 * @param postMap
	 * @param quotaMap
	 */
	public void convertManyCodeToName(Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, String> jobMap, Map<String, String> rankMap, Map<String, Post> postMap,
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

		// 职务
		if (this.getJobid() != null) {
			String name = jobMap.get(this.getJobid());
			this.setJobname(name);
		}

		// 职级
		if (this.getRankid() != null) {
			String name = rankMap.get(this.getRankid());
			this.setRankname(name);
		}

		// 编制
		if (this.getQuotaid() != null) {
			String name = jobMap.get(this.getJobid());
			this.setQuotaname(name);

		}

	}

}
