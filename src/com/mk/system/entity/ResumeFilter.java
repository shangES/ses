package com.mk.system.entity;

public class ResumeFilter {
	private String filterguid;
	private String filterconditions;
	private String tablecolumnname;
	private String code;
	private String memo;

	//判定选中
	private boolean checked;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getFilterguid() {
		return filterguid;
	}

	public void setFilterguid(String filterguid) {
		this.filterguid = filterguid;
	}

	public String getFilterconditions() {
		return filterconditions;
	}

	public void setFilterconditions(String filterconditions) {
		this.filterconditions = filterconditions;
	}

	public String getTablecolumnname() {
		return tablecolumnname;
	}

	public void setTablecolumnname(String tablecolumnname) {
		this.tablecolumnname = tablecolumnname;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
