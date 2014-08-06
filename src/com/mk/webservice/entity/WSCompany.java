package com.mk.webservice.entity;

import java.io.Serializable;
import java.util.List;

public class WSCompany implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String companyid;
	private String pcompanyid;
	private String companyname;
	private Integer companytype;
	private Integer state;
	private String interfacecode;

	// 临时
	private List<WSDepartment> depts;

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getPcompanyid() {
		return pcompanyid;
	}

	public void setPcompanyid(String pcompanyid) {
		this.pcompanyid = pcompanyid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public Integer getCompanytype() {
		return companytype;
	}

	public void setCompanytype(Integer companytype) {
		this.companytype = companytype;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getInterfacecode() {
		return interfacecode;
	}

	public void setInterfacecode(String interfacecode) {
		this.interfacecode = interfacecode;
	}

	public List<WSDepartment> getDepts() {
		return depts;
	}

	public void setDepts(List<WSDepartment> depts) {
		this.depts = depts;
	}

}
