package com.mk.resume.entity;

import java.sql.Timestamp;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class ResumeAssess {

	private String assessguid;
	private String webuserguid;
	private Integer assesslevel;
	private Integer assesshierarchy;
	private String assessresult;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;

	// 临时属性
	private String assesslevelname;
	private String assesshierarchyname;

	public String getAssesshierarchyname() {
		return assesshierarchyname;
	}

	public void setAssesshierarchyname(String assesshierarchyname) {
		this.assesshierarchyname = assesshierarchyname;
	}

	public Integer getAssesshierarchy() {
		return assesshierarchy;
	}

	public void setAssesshierarchy(Integer assesshierarchy) {
		this.assesshierarchy = assesshierarchy;
	}

	public String getAssessguid() {
		return assessguid;
	}

	public void setAssessguid(String assessguid) {
		this.assessguid = assessguid;
	}

	public String getWebuserguid() {
		return webuserguid;
	}

	public void setWebuserguid(String webuserguid) {
		this.webuserguid = webuserguid;
	}

	public Integer getAssesslevel() {
		return assesslevel;
	}

	public void setAssesslevel(Integer assesslevel) {
		this.assesslevel = assesslevel;
	}

	public String getAssessresult() {
		return assessresult;
	}

	public void setAssessresult(String assessresult) {
		this.assessresult = assessresult;
	}

	public String getModiuser() {
		return modiuser;
	}

	public void setModiuser(String modiuser) {
		this.modiuser = modiuser;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getModitimestamp() {
		return moditimestamp;
	}

	public void setModitimestamp(Timestamp moditimestamp) {
		this.moditimestamp = moditimestamp;
	}

	public String getModimemo() {
		return modimemo;
	}

	public void setModimemo(String modimemo) {
		this.modimemo = modimemo;
	}

	public String getAssesslevelname() {
		return assesslevelname;
	}

	public void setAssesslevelname(String assesslevelname) {
		this.assesslevelname = assesslevelname;
	}

	/**
	 * code转型
	 * 
	 * @param optionDao
	 */
	public void convertOneCodeToName(OptionDao optionDao) {
		// 评价等级
		if (this.getAssesslevel() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.ASSESSLEVEL, this.getAssesslevel());
			if (opt != null)
				this.setAssesslevelname(opt.getName());
		}
		
		// 评价体系
		if (this.getAssesshierarchy() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.ASSESSHIERARCHY,this.getAssesshierarchy());
			if (opt != null)
				this.setAssesshierarchyname(opt.getName());
		}
	}

	/**
	 * code转型map
	 * 
	 * @param assesslevelMap
	 */
	public void convertManyCodeToName(Map<Integer, String> assesslevelMap,Map<Integer, String> assesshierarchyMap) {
		// 评价等级
		if (this.getAssesslevel() != null) {
			String name = assesslevelMap.get(this.getAssesslevel());
			this.setAssesslevelname(name);
		}
		
		// 评价等级
		if (this.getAssesshierarchy() != null) {
			String name = assesshierarchyMap.get(this.getAssesshierarchy());
			this.setAssesshierarchyname(name);
		}
	}

}
