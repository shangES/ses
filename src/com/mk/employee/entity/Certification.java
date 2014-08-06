package com.mk.employee.entity;

import java.sql.Date;

import com.mk.framework.constance.OptionConstance;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class Certification {
	private String certificationid;
	private String employeeid;
	private Date authdate;
	private String authorg;
	private Integer authname;
	private String authlevel;
	private String description;

	// 临时
	private String authnamelabel;

	public String getAuthnamelabel() {
		return authnamelabel;
	}

	public void setAuthnamelabel(String authnamelabel) {
		this.authnamelabel = authnamelabel;
	}

	public String getCertificationid() {
		return certificationid;
	}

	public void setCertificationid(String certificationid) {
		this.certificationid = certificationid;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public Date getAuthdate() {
		return authdate;
	}

	public void setAuthdate(Date authdate) {
		this.authdate = authdate;
	}

	public String getAuthorg() {
		return authorg;
	}

	public void setAuthorg(String authorg) {
		this.authorg = authorg;
	}

	public Integer getAuthname() {
		return authname;
	}

	public void setAuthname(Integer authname) {
		this.authname = authname;
	}

	public String getAuthlevel() {
		return authlevel;
	}

	public void setAuthlevel(String authlevel) {
		this.authlevel = authlevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * code转名称
	 * 
	 * @param optionDao
	 */
	public void convertOneCodeToName(OptionDao optionDao) {

		// 职称认证
		if (this.getAuthname() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.AUTHNAME, this.getAuthname());
			if (opt != null) {
				this.setAuthnamelabel(opt.getName());
			}
		}

	}

}
