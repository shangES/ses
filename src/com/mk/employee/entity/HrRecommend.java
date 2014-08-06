package com.mk.employee.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class HrRecommend {
	private String recommendguid;
	private String employeeid;
	private String name;
	private Integer sex;
	private String mobile;
	private String situation;
	private String recommendpostname;
	private Integer workage;
	private String email;
	private Timestamp moditimestamp;

	// 临时
	private String sexname;
	private String workagename;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getModitimestamp() {
		return moditimestamp;
	}

	public void setModitimestamp(Timestamp moditimestamp) {
		this.moditimestamp = moditimestamp;
	}

	public String getRecommendpostname() {
		return recommendpostname;
	}

	public void setRecommendpostname(String recommendpostname) {
		this.recommendpostname = recommendpostname;
	}

	public Integer getWorkage() {
		return workage;
	}

	public void setWorkage(Integer workage) {
		this.workage = workage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkagename() {
		return workagename;
	}

	public void setWorkagename(String workagename) {
		this.workagename = workagename;
	}

	public String getRecommendguid() {
		return recommendguid;
	}

	public void setRecommendguid(String recommendguid) {
		this.recommendguid = recommendguid;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getSexname() {
		return sexname;
	}

	public void setSexname(String sexname) {
		this.sexname = sexname;
	}

	/**
	 * 翻译
	 * 
	 * @param optionDao
	 */
	public void convertOneCodeToName(OptionDao optionDao) {
		// 性别
		if (this.getSex() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.SEX, this.getSex());
			if (opt != null)
				this.setSexname(opt.getName());
		}
		
		//工作年限
		if(this.getWorkage()!=null){
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.WORKAGE, this.getWorkage());
			if (opt != null)
				this.setWorkagename(opt.getName());
		}

	}

}
