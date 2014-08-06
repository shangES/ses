package com.mk.person.entity;

import java.sql.Date;
import java.util.Map;

import com.mk.framework.constance.OptionConstance;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;
import com.mk.thirdpartner.dao.ThirdPartnerDao;
import com.mk.thirdpartner.entity.ThirdPartner;

public class ExaminationRecord {

	private String examinationrecordguid;
	private String recommendguid;
	private String mycandidatesguid;
	private String thirdpartnerguid;
	private String webuserguid;
	private Date examinationdate;
	private String examinationaddress;
	private String examinationresult;
	private String modiuser;
	private Integer examinationstate;
	private Date moditimestamp;
	private String modimemo;
	private Integer state;
	private Integer examinationtype;

	// 体检报告
	private String filepath;

	// 临时
	private String thirdpartnerguidname;
	private String examinationstatename;

	public Integer getExaminationtype() {
		return examinationtype;
	}

	public void setExaminationtype(Integer examinationtype) {
		this.examinationtype = examinationtype;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getRecommendguid() {
		return recommendguid;
	}

	public void setRecommendguid(String recommendguid) {
		this.recommendguid = recommendguid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getExaminationstatename() {
		return examinationstatename;
	}

	public void setExaminationstatename(String examinationstatename) {
		this.examinationstatename = examinationstatename;
	}

	public Integer getExaminationstate() {
		return examinationstate;
	}

	public void setExaminationstate(Integer examinationstate) {
		this.examinationstate = examinationstate;
	}

	public ExaminationRecord() {

	}

	public String getThirdpartnerguidname() {
		return thirdpartnerguidname;
	}

	public void setThirdpartnerguidname(String thirdpartnerguidname) {
		this.thirdpartnerguidname = thirdpartnerguidname;
	}

	public String getThirdpartnerguid() {
		return thirdpartnerguid;
	}

	public void setThirdpartnerguid(String thirdpartnerguid) {
		this.thirdpartnerguid = thirdpartnerguid;
	}

	public String getExaminationrecordguid() {
		return examinationrecordguid;
	}

	public void setExaminationrecordguid(String examinationrecordguid) {
		this.examinationrecordguid = examinationrecordguid;
	}

	public String getMycandidatesguid() {
		return mycandidatesguid;
	}

	public void setMycandidatesguid(String mycandidatesguid) {
		this.mycandidatesguid = mycandidatesguid;
	}

	public String getWebuserguid() {
		return webuserguid;
	}

	public void setWebuserguid(String webuserguid) {
		this.webuserguid = webuserguid;
	}

	public Date getExaminationdate() {
		return examinationdate;
	}

	public void setExaminationdate(Date examinationdate) {
		this.examinationdate = examinationdate;
	}

	public String getExaminationaddress() {
		return examinationaddress;
	}

	public void setExaminationaddress(String examinationaddress) {
		this.examinationaddress = examinationaddress;
	}

	public String getExaminationresult() {
		return examinationresult;
	}

	public void setExaminationresult(String examinationresult) {
		this.examinationresult = examinationresult;
	}

	public String getModiuser() {
		return modiuser;
	}

	public void setModiuser(String modiuser) {
		this.modiuser = modiuser;
	}

	public Date getModitimestamp() {
		return moditimestamp;
	}

	public void setModitimestamp(Date moditimestamp) {
		this.moditimestamp = moditimestamp;
	}

	public String getModimemo() {
		return modimemo;
	}

	public void setModimemo(String modimemo) {
		this.modimemo = modimemo;
	}

	/**
	 * 转码
	 * 
	 * @param optionDao
	 * @param comDao
	 * @param deptDao
	 */
	public void convertOneCodeToName(ThirdPartnerDao thirdPartnerDao, OptionDao optionDao) {
		// 体检机构
		if (this.getThirdpartnerguid() != null) {
			ThirdPartner model = thirdPartnerDao.getThirdPartnerById(this.getThirdpartnerguid());
			this.setThirdpartnerguidname(model.getThirdpartnername());
		}

		// 体检状态
		if (this.getExaminationstate() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.EXAMINATIONSTATE, this.getExaminationstate());
			if (opt != null)
				this.setExaminationstatename(opt.getName());
		}
	}

	/**
	 * 批量转
	 * 
	 * @param examinationstateMap
	 */
	public void convertManyCodeToName(Map<String, String> thirdpartnerMap, Map<Integer, String> exminationMap) {
		// 体检机构
		if (this.getThirdpartnerguid() != null) {
			String name = thirdpartnerMap.get(this.getThirdpartnerguid());
			this.setThirdpartnerguidname(name);
		}

		// 体检状态
		if (this.getExaminationstate() != null) {
			String name = exminationMap.get(this.getExaminationstate());
			if (name != null)
				this.setExaminationstatename(name);
		}
	}

}
