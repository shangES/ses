package com.mk.department.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.OrgPathUtil;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.company.entity.Job;
import com.mk.department.dao.DepartmentDao;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.model.ColumnInfo;
import com.mk.quota.entity.Quota;

public class Post {

	private String postid;
	private String companyid;
	private String deptid;
	private String jobid;
	private String postname;
	private String postcode;
	private String postduty;
	private int state;
	private String interfacecode;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;

	// 临时属性
	private String jobidname;
	private String companyname;
	private String deptname;
	private Quota quota;

	// 编制
	private String budgettype1;
	private Integer budgetnumber1;
	private String budgettype2;
	private Integer budgetnumber2;

	public String getBudgettype1() {
		return budgettype1;
	}

	public void setBudgettype1(String budgettype1) {
		this.budgettype1 = budgettype1;
	}

	public Integer getBudgetnumber1() {
		return budgetnumber1;
	}

	public void setBudgetnumber1(Integer budgetnumber1) {
		this.budgetnumber1 = budgetnumber1;
	}

	public String getBudgettype2() {
		return budgettype2;
	}

	public void setBudgettype2(String budgettype2) {
		this.budgettype2 = budgettype2;
	}

	public Integer getBudgetnumber2() {
		return budgetnumber2;
	}

	public void setBudgetnumber2(Integer budgetnumber2) {
		this.budgetnumber2 = budgetnumber2;
	}

	public String getInterfacecode() {
		return interfacecode;
	}

	public void setInterfacecode(String interfacecode) {
		this.interfacecode = interfacecode;
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

	public String getJobidname() {
		return jobidname;
	}

	public void setJobidname(String jobidname) {
		this.jobidname = jobidname;
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
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

	public String getJobid() {
		return jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPostduty() {
		return postduty;
	}

	public void setPostduty(String postduty) {
		this.postduty = postduty;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
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

	public Quota getQuota() {
		return quota;
	}

	public void setQuota(Quota quota) {
		this.quota = quota;
	}

	// code转型
	public void convertOneCodeToName(CompanyDao comDao, DepartmentDao deptDao) {

		// 公司名称
		if (this.getCompanyid() != null) {
			Company company = OrgPathUtil.getOneCompanyFullPath(this.getCompanyid(), comDao);
			if (company != null)
				this.setCompanyname(company.getCompanyfullname());
		}

		// 部门名称
		if (this.getDeptid() != null) {
			Department dept = OrgPathUtil.getOneDepartmentFullPath(this.getDeptid(), deptDao);
			if (dept != null) {
				this.setDeptname(dept.getDeptfullname());
			}
		}

		// 职务类型
		if (this.getJobid() != null) {
			Job model = comDao.getJobById(this.getJobid());
			if (model != null) {
				this.setJobidname(model.getJobname());
			}
		}
	}

	/**
	 * code转型map
	 * 
	 * @param jobidMap
	 */
	public void convertManyCodeToName(Map<String, Company> companyMap, Map<String, Department> deptMap, Map<Integer, String> jobidMap) {
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

		// 编制类型
		if (this.getJobid() != null) {
			String name = jobidMap.get(this.getJobid());
			this.setJobidname(name);
		}
	}

	@Override
	public String toString() {
		return "Post [postid=" + postid + ", companyid=" + companyid + ", deptid=" + deptid + ", jobid=" + jobid + ", postname=" + postname + ", postcode=" + postcode + ", postduty=" + postduty
				+ ", state=" + state + ", interfacecode=" + interfacecode + ", memo=" + memo + ", modiuser=" + modiuser + ", moditimestamp=" + moditimestamp + ", modimemo=" + modimemo
				+ ", jobidname=" + jobidname + ", companyname=" + companyname + ", deptname=" + deptname + "]";
	}

	/**
	 * 导出树
	 * 
	 * @return
	 */
	public static List<ColumnInfo> initExcelColumn() {
		List<ColumnInfo> columns = new ArrayList<ColumnInfo>();

		ColumnInfo c = new ColumnInfo();
		c.setId("companyname");
		c.setHeader("公司名称");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("deptname");
		c.setHeader("部门名称");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("postname");
		c.setHeader("岗位名称");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("postcode");
		c.setHeader("岗位代码");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("jobidname");
		c.setHeader("职务");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("postduty");
		c.setHeader("描述");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("memo");
		c.setHeader("备注");
		columns.add(c);

		return columns;
	}

}
