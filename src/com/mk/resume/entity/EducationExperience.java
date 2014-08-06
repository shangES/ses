package com.mk.resume.entity;

import java.sql.Date;
import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class EducationExperience {
	private String educationexperienceguid;
	private String webuserguid;
	private Date startdate;
	private Date enddate;
	private Integer culture;
	private String school;
	private String specialty;
	private String majordescription;
	private Timestamp modtime;
	private String rmk;

	// 临时
	private String culturename;

	public String getCulturename() {
		return culturename;
	}

	public void setCulturename(String culturename) {
		this.culturename = culturename;
	}

	public String getEducationexperienceguid() {
		return educationexperienceguid;
	}

	public void setEducationexperienceguid(String educationexperienceguid) {
		this.educationexperienceguid = educationexperienceguid;
	}

	public String getWebuserguid() {
		return webuserguid;
	}

	public void setWebuserguid(String webuserguid) {
		this.webuserguid = webuserguid;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Integer getCulture() {
		return culture;
	}

	public void setCulture(Integer culture) {
		this.culture = culture;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getMajordescription() {
		return majordescription;
	}

	public void setMajordescription(String majordescription) {
		this.majordescription = majordescription;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getModtime() {
		return modtime;
	}

	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	/**
	 * code转名称
	 * 
	 * @param optionDao
	 */
	public void convertOneCodeToName(OptionDao optionDao) {

		// 学历情况
		if (this.getCulture() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CULTURE, this.getCulture());
			if (opt != null)
				this.setCulturename(opt.getName());
		}

	}

	@Override
	public String toString() {
		return "EducationExperience [educationexperienceguid=" + educationexperienceguid + ", webuserguid=" + webuserguid + ", startdate=" + startdate + ", enddate=" + enddate + ", culture="
				+ culture + ", school=" + school + ", specialty=" + specialty + ", majordescription=" + majordescription + ", modtime=" + modtime + ", rmk=" + rmk + ", culturename=" + culturename
				+ "]";
	}

}
