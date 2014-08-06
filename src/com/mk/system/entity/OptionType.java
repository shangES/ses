package com.mk.system.entity;

public class OptionType {
	private String optiontypeguid;
	private String code;
	private String name;
	private Integer dorder;
	private Integer reserved;
	private String rmk;

	public String getOptiontypeguid() {
		return optiontypeguid;
	}

	public void setOptiontypeguid(String optiontypeguid) {
		this.optiontypeguid = optiontypeguid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
