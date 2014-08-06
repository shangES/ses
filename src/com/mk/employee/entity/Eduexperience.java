package com.mk.employee.entity;

import java.sql.Date;
import java.util.Map;

import com.mk.framework.constance.OptionConstance;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class Eduexperience {
	private String eduexperienceid;
	private String employeeid;
	private String eduorg;
	private Date startdate;
	private Date enddate;
	private String professional;
	private Integer culture;
	private String description;
	private Integer learningtype;

	// 临时
	private String learningtypename;
	private String culturename;
	
	
	public Integer getCulture() {
		return culture;
	}

	public void setCulture(Integer culture) {
		this.culture = culture;
	}

	public String getCulturename() {
		return culturename;
	}

	public void setCulturename(String culturename) {
		this.culturename = culturename;
	}

	public Integer getLearningtype() {
		return learningtype;
	}

	public void setLearningtype(Integer learningtype) {
		this.learningtype = learningtype;
	}

	public String getLearningtypename() {
		return learningtypename;
	}

	public void setLearningtypename(String learningtypename) {
		this.learningtypename = learningtypename;
	}

	public String getEduexperienceid() {
		return eduexperienceid;
	}

	public void setEduexperienceid(String eduexperienceid) {
		this.eduexperienceid = eduexperienceid;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getEduorg() {
		return eduorg;
	}

	public void setEduorg(String eduorg) {
		this.eduorg = eduorg;
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
		// 学习形式
		if (this.getLearningtype() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.LEARNINGTYPE, this.getLearningtype());
			if (opt != null)
				this.setLearningtypename(opt.getName());
		}
		
		// 学历情况
		if (this.getCulture() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CULTURE,this.getCulture());
			if (opt != null)
				this.setCulturename(opt.getName());
		}

	}

	/**
	 * 批量编译
	 * 
	 * 
	 * @param learningtypeMap
	 */
	public void convertManyCodeToName(Map<Integer, String> learningtypeMap,Map<Integer, String> cultureMap) {
		// 学习形式
		if (this.getLearningtype() != null) {
			String name = learningtypeMap.get(this.getLearningtype());
			this.setLearningtypename(name);
		}
		
		// 学历情况
		if (this.getCulture() != null) {
			String name = cultureMap.get(this.getCulture());
			this.setCulturename(name);
		}

	}

}
