package com.mk.webservice.entity;


public class WSDepartment {
	private String deptid;
	private String pdeptid;
	private String companyid;
	private String deptname;
	private Integer depttype;
	private String interfacecode;
	private Integer state;

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

	@Override
	public String toString() {
		return "WSDepartment [deptid=" + deptid + ", pdeptid=" + pdeptid + ", companyid=" + companyid + ", deptname=" + deptname + ", depttype=" + depttype + ", interfacecode=" + interfacecode
				+ ", state=" + state + "]";
	}
	
	

}
