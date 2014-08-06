package com.mk.webservice.entity;

public class WSPostNum {
	private Integer employeenum;
	private Integer budgetnumber;
	private String budgettype;
	private String Budgettypename;

	public String getBudgettypename() {
		return Budgettypename;
	}

	public void setBudgettypename(String budgettypename) {
		Budgettypename = budgettypename;
	}

	public Integer getEmployeenum() {
		return employeenum;
	}

	public void setEmployeenum(Integer employeenum) {
		this.employeenum = employeenum;
	}

	public Integer getBudgetnumber() {
		return budgetnumber;
	}

	public void setBudgetnumber(Integer budgetnumber) {
		this.budgetnumber = budgetnumber;
	}

	public String getBudgettype() {
		return budgettype;
	}

	public void setBudgettype(String budgettype) {
		this.budgettype = budgettype;
	}

	@Override
	public String toString() {
		return "WSPostNum [employeenum=" + employeenum + ", budgetnumber=" + budgetnumber + ", budgettype=" + budgettype + ", Budgettypename=" + Budgettypename + "]";
	}

}
