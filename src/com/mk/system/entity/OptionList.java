package com.mk.system.entity;

public class OptionList {

	private String optionid;
	private String optiontypeguid;
	private Integer code;
	private String name;
	private int isdefault;
	private Integer dorder;
	private int reserved;
	private String rmk;
	
	
	
	public void setIsdefault(int isdefault) {
		this.isdefault = isdefault;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}

	public String getOptionid() {
		return optionid;
	}

	public void setOptionid(String optionid) {
		this.optionid = optionid;
	}

	public String getOptiontypeguid() {
		return optiontypeguid;
	}

	public void setOptiontypeguid(String optiontypeguid) {
		this.optiontypeguid = optiontypeguid;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}

	public Integer getDorder() {
		return dorder;
	}

	public void setDorder(Integer dorder) {
		this.dorder = dorder;
	}

	public Integer getReserved() {
		return reserved;
	}

	public void setReserved(Integer reserved) {
		this.reserved = reserved;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	@Override
	public String toString() {
		return "Optionlist [optionid=" + optionid + ", optiontypeguid=" + optiontypeguid + ", code=" + code + ", name=" + name + ", isdefault=" + isdefault + ", dorder=" + dorder + ", reserved="
				+ reserved + ", rmk=" + rmk + "]";
	}

}
