package com.mk.system.entity;

public class UserRole {
	private String roleid;
	private String userguid;
	private boolean checked;

	// 临时
	private String rolename;

	public UserRole() {

	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public UserRole(String rid, String usertid) {
		this.setRoleid(rid);
		this.setUserguid(usertid);
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getUserguid() {
		return userguid;
	}

	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}

}
