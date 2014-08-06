package com.mk.department.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.OrgPathUtil;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.model.ColumnInfo;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class Department {
	private String deptid;
	private String pdeptid;
	private String companyid;
	private String deptname;
	private String deptcode;
	private String deptfunction;
	private String deptduty;
	private String description;
	private Integer depttype;
	private String interfacecode;
	private Integer state;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;
	private Integer assesshierarchy;

	// 临时
	private String assesshierarchyname;
	private String companyname;
	private String deptfullname;
	private String depttypename;

	public Integer getAssesshierarchy() {
		return assesshierarchy;
	}

	public void setAssesshierarchy(Integer assesshierarchy) {
		this.assesshierarchy = assesshierarchy;
	}

	public String getAssesshierarchyname() {
		return assesshierarchyname;
	}

	public void setAssesshierarchyname(String assesshierarchyname) {
		this.assesshierarchyname = assesshierarchyname;
	}

	public String getDeptfullname() {
		return deptfullname;
	}

	public void setDeptfullname(String deptfullname) {
		this.deptfullname = deptfullname;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getDepttypename() {
		return depttypename;
	}

	public void setDepttypename(String depttypename) {
		this.depttypename = depttypename;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getPdeptid() {
		return pdeptid;
	}

	public void setPdeptid(String pdeptid) {
		this.pdeptid = pdeptid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public String getDeptfunction() {
		return deptfunction;
	}

	public void setDeptfunction(String deptfunction) {
		this.deptfunction = deptfunction;
	}

	public String getDeptduty() {
		return deptduty;
	}

	public void setDeptduty(String deptduty) {
		this.deptduty = deptduty;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDepttype() {
		return depttype;
	}

	public void setDepttype(Integer depttype) {
		this.depttype = depttype;
	}

	public String getInterfacecode() {
		return interfacecode;
	}

	public void setInterfacecode(String interfacecode) {
		this.interfacecode = interfacecode;
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
		return "Department [deptid=" + deptid + ", pdeptid=" + pdeptid + ", companyid=" + companyid + ", deptname=" + deptname + ", deptcode=" + deptcode + ", deptfunction=" + deptfunction
				+ ", deptduty=" + deptduty + ", description=" + description + ", depttype=" + depttype + ", interfacecode=" + interfacecode + ", state=" + state + ", memo=" + memo + ", modiuser="
				+ modiuser + ", moditimestamp=" + moditimestamp + ", modimemo=" + modimemo + ", companyname=" + companyname + ", deptfullname=" + deptfullname + ", depttypename=" + depttypename + "]";
	}

	// code转型
	public void convertOneCodeToName(CompanyDao companyDao, OptionDao option) {
		// 公司名称
		if (this.getCompanyid() != null) {
			Company company = OrgPathUtil.getOneCompanyFullPath(this.getCompanyid(), companyDao);
			if (company != null)
				this.setCompanyname(company.getCompanyfullname());
		}

		// 部门类型
		if (this.getDepttype() != null) {
			OptionList opt = option.getOptionListByTypeAndCode(OptionConstance.H_O_DEPARTMENT_DEPTTYPE, this.getDepttype());
			if (opt != null) {
				this.setDepttypename(opt.getName());
			}
		}
		
		//体系
		if (this.getAssesshierarchy() != null) {
			OptionList opt = option.getOptionListByTypeAndCode(OptionConstance.ASSESSHIERARCHY, this.getAssesshierarchy());
			if (opt != null) {
				this.setAssesshierarchyname(opt.getName());
			}
		}
	}

	// code转型map
	public void convertManyCodeToName(Map<String, Company> companyMap,Map<Integer, String> depttypeMap,Map<Integer, String> assesshierarchyMap) {
		// 公司
		if (this.getCompanyid() != null) {
			Company company = companyMap.get(this.getCompanyid());
			if (company != null)
				this.setCompanyname(company.getCompanyfullname());
		}

		// 部门类型
		if (this.getDepttype() != null) {
			String name = depttypeMap.get(this.getDepttype());
			this.setDepttypename(name);
		}
		
		//体系
		if(this.getAssesshierarchy()!=null){
			String name=assesshierarchyMap.get(this.getAssesshierarchy());
			this.setAssesshierarchyname(name);
		}

	}

	/**
	 * 导出
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
		c.setId("deptcode");
		c.setHeader("部门代码");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("deptfunction");
		c.setHeader("部门职能");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("deptduty");
		c.setHeader("部门职责");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("description");
		c.setHeader("部门简介");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("depttypename");
		c.setHeader("部门类型");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("memo");
		c.setHeader("备注");
		columns.add(c);

		return columns;
	}
}
