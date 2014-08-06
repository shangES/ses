package com.mk.company.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.model.ColumnInfo;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class Company {
	private String companyid;
	private String pcompanyid;
	private String companyname;
	private String companycode;
	private Date createdate;
	private Double regcapital;
	private Integer companytype;
	private String legalperson;
	private String officeaddress;
	private String businessscope;
	private String description;
	private Integer state;
	private String interfacecode;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;

	// 临时
	private String companytypename;
	private String companyfullname;
	private boolean isaudit;

	public boolean isIsaudit() {
		return isaudit;
	}

	public void setIsaudit(boolean isaudit) {
		this.isaudit = isaudit;
	}

	public String getCompanyfullname() {
		return companyfullname;
	}

	public void setCompanyfullname(String companyfullname) {
		this.companyfullname = companyfullname;
	}

	public String getCompanytypename() {
		return companytypename;
	}

	public void setCompanytypename(String companytypename) {
		this.companytypename = companytypename;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getPcompanyid() {
		return pcompanyid;
	}

	public void setPcompanyid(String pcompanyid) {
		this.pcompanyid = pcompanyid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Double getRegcapital() {
		return regcapital;
	}

	public void setRegcapital(Double regcapital) {
		this.regcapital = regcapital;
	}

	public Integer getCompanytype() {
		return companytype;
	}

	public void setCompanytype(Integer companytype) {
		this.companytype = companytype;
	}

	public String getLegalperson() {
		return legalperson;
	}

	public void setLegalperson(String legalperson) {
		this.legalperson = legalperson;
	}

	public String getOfficeaddress() {
		return officeaddress;
	}

	public void setOfficeaddress(String officeaddress) {
		this.officeaddress = officeaddress;
	}

	public String getBusinessscope() {
		return businessscope;
	}

	public void setBusinessscope(String businessscope) {
		this.businessscope = businessscope;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getInterfacecode() {
		return interfacecode;
	}

	public void setInterfacecode(String interfacecode) {
		this.interfacecode = interfacecode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	/**
	 * code轉名稱
	 * 
	 * @param optionDao
	 */
	public void convertCodeToName(OptionDao optionDao) {
		// 公司类型
		if (this.getCompanytype() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.COMPANYTYPE, this.getCompanytype());
			if (opt != null)
				this.setCompanytypename(opt.getName());
		}
	}

	/**
	 * 导出的xls列头
	 * 
	 * @return
	 */
	public static List<ColumnInfo> initExcelColumn() {
		List<ColumnInfo> columns = new ArrayList<ColumnInfo>();

		ColumnInfo c = new ColumnInfo();

		c.setId("companyname");
		c.setHeader("公司名称");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("companycode");
		c.setHeader("公司代码");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("createdate");
		c.setHeader("创立时间");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("regcapital");
		c.setHeader("注册资金(万元)");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("legalperson");
		c.setHeader("法人代表");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("officeaddress");
		c.setHeader("办公地址");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("businessscope");
		c.setHeader("经营范围");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("description");
		c.setHeader("公司简介");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("memo");
		c.setHeader("备注");
		columns.add(c);

		return columns;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyid == null) ? 0 : companyid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (companyid == null) {
			if (other.companyid != null)
				return false;
		} else if (!companyid.equals(other.companyid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Company [companyid=" + companyid + ", pcompanyid=" + pcompanyid + ", companyname=" + companyname + ", companycode=" + companycode + ", createdate=" + createdate + ", regcapital="
				+ regcapital + ", companytype=" + companytype + ", legalperson=" + legalperson + ", officeaddress=" + officeaddress + ", businessscope=" + businessscope + ", description="
				+ description + ", state=" + state + ", interfacecode=" + interfacecode + ", memo=" + memo + ", modiuser=" + modiuser + ", moditimestamp=" + moditimestamp + ", modimemo=" + modimemo
				+ ", companytypename=" + companytypename + ", companyfullname=" + companyfullname + ", isaudit=" + isaudit + "]";
	}

}
