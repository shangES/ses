package com.mk.system.entity;

public class Role {
	private String roleid;
	private String rolename;
	private Integer state;
	private Integer dorder;
	private String description;
	
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getDorder() {
		return dorder;
	}
	public void setDorder(Integer dorder) {
		this.dorder = dorder;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Role [roleid=" + roleid + ", rolename=" + rolename + ", state=" + state + ", dorder=" + dorder + ", description=" + description + "]";
	}
	
}
