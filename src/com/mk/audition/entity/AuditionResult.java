package com.mk.audition.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class AuditionResult {

	private String auditionrecordguid;
	private int isrelease;
	private Integer resulttype;
	private Integer resultcontent;
	private String hrresultcontent;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;

	// 临时
	private String resulttypename;
	private String mycandidatesguid;
	private String resultcontentname;

	public AuditionResult() {

	}

	public String getHrresultcontent() {
		return hrresultcontent;
	}

	public void setHrresultcontent(String hrresultcontent) {
		this.hrresultcontent = hrresultcontent;
	}

	public String getMycandidatesguid() {
		return mycandidatesguid;
	}

	public void setMycandidatesguid(String mycandidatesguid) {
		this.mycandidatesguid = mycandidatesguid;
	}

	public String getAuditionrecordguid() {
		return auditionrecordguid;
	}

	public void setAuditionrecordguid(String auditionrecordguid) {
		this.auditionrecordguid = auditionrecordguid;
	}

	public int getIsrelease() {
		return isrelease;
	}

	public void setIsrelease(int isrelease) {
		this.isrelease = isrelease;
	}

	public Integer getResulttype() {
		return resulttype;
	}

	public void setResulttype(Integer resulttype) {
		this.resulttype = resulttype;
	}

	public Integer getResultcontent() {
		return resultcontent;
	}

	public void setResultcontent(Integer resultcontent) {
		this.resultcontent = resultcontent;
	}

	public String getResultcontentname() {
		return resultcontentname;
	}

	public void setResultcontentname(String resultcontentname) {
		this.resultcontentname = resultcontentname;
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

	public String getResulttypename() {
		return resulttypename;
	}

	public void setResulttypename(String resulttypename) {
		this.resulttypename = resulttypename;
	}

	@Override
	public String toString() {
		return "AuditionResult [auditionrecordguid=" + auditionrecordguid + ", isrelease=" + isrelease + ", resulttype=" + resulttype + ", resultcontent=" + resultcontent + ", modiuser=" + modiuser
				+ ", moditimestamp=" + moditimestamp + ", modimemo=" + modimemo + "]";
	}

	/**
	 * 翻译
	 * 
	 * @param optionDao
	 */
	public void convertOneCodeToName(OptionDao optionDao) {
		// 面试结果
		if (this.getResulttype() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RESULTTYPE, this.getResulttype());
			if (opt != null)
				this.setResulttypename(opt.getName());
		}
		
		// 面试评语
		if (this.getResultcontent() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RESULTCONTENT, this.getResultcontent());
			if (opt != null)
				this.setResultcontentname(opt.getName());
		}
	}

}
