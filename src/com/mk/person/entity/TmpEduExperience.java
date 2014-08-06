package com.mk.person.entity;

import java.sql.Date;

import com.mk.framework.constance.OptionConstance;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class TmpEduExperience {

	private String eduexperienceid;
	private String mycandidatesguid;
	private String eduorg;
	private Integer learningtype;
	private Date startdate;
	private Date enddate;
	private String professional;
	private String degree;
	private String description;
	private Integer culture;

	// 临时
	private String culturename;
	private String learningtypename;

	public String getCulturename() {
		return culturename;
	}

	public void setCulturename(String culturename) {
		this.culturename = culturename;
	}

	public String getLearningtypename() {
		return learningtypename;
	}

	public void setLearningtypename(String learningtypename) {
		this.learningtypename = learningtypename;
	}

	public Integer getCulture() {
		return culture;
	}

	public void setCulture(Integer culture) {
		this.culture = culture;
	}

	public String getEduexperienceid() {
		return eduexperienceid;
	}

	public void setEduexperienceid(String eduexperienceid) {
		this.eduexperienceid = eduexperienceid;
	}

	public String getMycandidatesguid() {
		return mycandidatesguid;
	}

	public void setMycandidatesguid(String mycandidatesguid) {
		this.mycandidatesguid = mycandidatesguid;
	}

	public String getEduorg() {
		return eduorg;
	}

	public void setEduorg(String eduorg) {
		this.eduorg = eduorg;
	}

	public Integer getLearningtype() {
		return learningtype;
	}

	public void setLearningtype(Integer learningtype) {
		this.learningtype = learningtype;
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

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
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
		// 学历情况
		if (this.getCulture() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CULTURE, this.getCulture());
			if (opt != null)
				this.setCulturename(opt.getName());
		}

		// 学习形式
		if (this.getLearningtype() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.LEARNINGTYPE, this.getLearningtype());
			if (opt != null)
				this.setLearningtypename(opt.getName());
		}
	}

}
