package com.mk.thirdpartner.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class ThirdPartyAssess {

	private String thirdpartyassessguid;
	private String thirdpartnerguid;
	private Integer year;
	private Integer thirdpartyassesslevel;
	private String thirdpartyassessresult;
	private Timestamp thirdpartyassessdate;
	private String rmk;

	// 临时
	private String thirdpartyassesslevelname;

	// 转码
	public void convertCodeToName(OptionDao option) {
		if (this.thirdpartyassesslevel != null) {
			OptionList opt = option.getOptionListByTypeAndCode(OptionConstance.THIRDPARTYSSESSLEVEL, this.getThirdpartyassesslevel());
			if (opt != null)
				this.setThirdpartyassesslevelname(opt.getName());
		}
	}

	public String getThirdpartyassessguid() {
		return thirdpartyassessguid;
	}

	public void setThirdpartyassessguid(String thirdpartyassessguid) {
		this.thirdpartyassessguid = thirdpartyassessguid;
	}

	public String getThirdpartnerguid() {
		return thirdpartnerguid;
	}

	public void setThirdpartnerguid(String thirdpartnerguid) {
		this.thirdpartnerguid = thirdpartnerguid;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getThirdpartyassesslevel() {
		return thirdpartyassesslevel;
	}

	public void setThirdpartyassesslevel(Integer thirdpartyassesslevel) {
		this.thirdpartyassesslevel = thirdpartyassesslevel;
	}

	public String getThirdpartyassessresult() {
		return thirdpartyassessresult;
	}

	public void setThirdpartyassessresult(String thirdpartyassessresult) {
		this.thirdpartyassessresult = thirdpartyassessresult;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getThirdpartyassessdate() {
		return thirdpartyassessdate;
	}

	public void setThirdpartyassessdate(Timestamp thirdpartyassessdate) {
		this.thirdpartyassessdate = thirdpartyassessdate;
	}

	public String getThirdpartyassesslevelname() {
		return thirdpartyassesslevelname;
	}

	public void setThirdpartyassesslevelname(String thirdpartyassesslevelname) {
		this.thirdpartyassesslevelname = thirdpartyassesslevelname;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	@Override
	public String toString() {
		return "ThirdPartyAssess [thirdpartyassessguid=" + thirdpartyassessguid + ", thirdpartnerguid=" + thirdpartnerguid + ", year=" + year + ", thirdpartyassesslevel=" + thirdpartyassesslevel
				+ ", thirdpartyassessresult=" + thirdpartyassessresult + ", thirdpartyassessdate=" + thirdpartyassessdate + ", rmk=" + rmk + ", thirdpartyassesslevelname=" + thirdpartyassesslevelname
				+ "]";
	}

}
