package com.mk.thirdpartner.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class ThirdPartner {

	private String thirdpartnerguid;
	private Integer thirdpartytype;
	private String thirdpartnername;
	private String rmk;
	private Timestamp modtime;
	private String name;
	private String email;
	private String mobile;

	/* 临时 */
	private String thirdPartyTypeName;

	// 转码
	public void convertCodeToName(OptionDao option) {
		if (this.getThirdpartytype() != null) {
			OptionList opt = option.getOptionListByTypeAndCode(OptionConstance.THIRDPARTNERTYPE, this.getThirdpartytype());
			if (opt != null) {
				this.setThirdPartyTypeName(opt.getName());
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getModtime() {
		return modtime;
	}

	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}

	public String getThirdpartnerguid() {
		return thirdpartnerguid;
	}

	public void setThirdpartnerguid(String thirdpartnerguid) {
		this.thirdpartnerguid = thirdpartnerguid;
	}

	public Integer getThirdpartytype() {
		return thirdpartytype;
	}

	public void setThirdpartytype(Integer thirdpartytype) {
		this.thirdpartytype = thirdpartytype;
	}

	public String getThirdpartnername() {
		return thirdpartnername;
	}

	public void setThirdpartnername(String thirdpartnername) {
		this.thirdpartnername = thirdpartnername;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	public String getThirdPartyTypeName() {
		return thirdPartyTypeName;
	}

	public void setThirdPartyTypeName(String thirdPartyTypeName) {
		this.thirdPartyTypeName = thirdPartyTypeName;
	}

	@Override
	public String toString() {
		return "ThirdPartner [thirdpartnerguid=" + thirdpartnerguid + ", thirdpartytype=" + thirdpartytype + ", thirdpartnername=" + thirdpartnername + ", rmk=" + rmk + ", modtime=" + modtime
				+ ", thirdPartyTypeName=" + thirdPartyTypeName + "]";
	}

}
