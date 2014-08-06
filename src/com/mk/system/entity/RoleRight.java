package com.mk.system.entity;

public class RoleRight {
	private String roleid;
	private String funid;
	
	public RoleRight() {
		
	}
	
	public RoleRight(String roleid, String funid) {
		this.roleid = roleid;
		this.funid = funid;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getFunid() {
		return funid;
	}
	public void setFunid(String funid) {
		this.funid = funid;
	}
	
}
