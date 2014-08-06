package com.mk.addresslist.entity;

import java.sql.Timestamp;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.OrgPathUtil;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class WebAddressList {
	private String addresslistguid;
	private String employeeid;
	private String employeecode;
	private String companyid;
	private String companycode;
	private String deptid;
	private String deptcode;
	private String postid;
	private String postcode;
	private Integer dorder;
	private String jobnumber;
	private String name;
	private Integer sex;
	private String innerphone;
	private String extphone;
	private String mobilephone;
	private String mobilephone2;
	private String email;
	private String officeaddress;
	private Timestamp refreshtimestamp;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;

	// 临时
	private String sexname;
	private String companyname;
	private String deptname;
	private String postname;

	public String getMobilephone2() {
		return mobilephone2;
	}

	public void setMobilephone2(String mobilephone2) {
		this.mobilephone2 = mobilephone2;
	}

	public Integer getDorder() {
		return dorder;
	}

	public void setDorder(Integer dorder) {
		this.dorder = dorder;
	}

	public String getSexname() {
		return sexname;
	}

	public void setSexname(String sexname) {
		this.sexname = sexname;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getAddresslistguid() {
		return addresslistguid;
	}

	public void setAddresslistguid(String addresslistguid) {
		this.addresslistguid = addresslistguid;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getEmployeecode() {
		return employeecode;
	}

	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getJobnumber() {
		return jobnumber;
	}

	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
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

	public String getInnerphone() {
		return innerphone;
	}

	public void setInnerphone(String innerphone) {
		this.innerphone = innerphone;
	}

	public String getExtphone() {
		return extphone;
	}

	public void setExtphone(String extphone) {
		this.extphone = extphone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficeaddress() {
		return officeaddress;
	}

	public void setOfficeaddress(String officeaddress) {
		this.officeaddress = officeaddress;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getRefreshtimestamp() {
		return refreshtimestamp;
	}

	public void setRefreshtimestamp(Timestamp refreshtimestamp) {
		this.refreshtimestamp = refreshtimestamp;
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

	@Override
	public String toString() {
		return "AddressList [addresslistguid=" + addresslistguid + ", employeeid=" + employeeid + ", employeecode=" + employeecode + ", companyid=" + companyid + ", companycode=" + companycode
				+ ", deptid=" + deptid + ", deptcode=" + deptcode + ", postid=" + postid + ", postcode=" + postcode + ", jobnumber=" + jobnumber + ", name=" + name + ", sex=" + sex + ", innerphone="
				+ innerphone + ", extphone=" + extphone + ", mobilephone=" + mobilephone + ", email=" + email + ", officeaddress=" + officeaddress + ", refreshtimestamp=" + refreshtimestamp
				+ ", memo=" + memo + ", modiuser=" + modiuser + ", moditimestamp=" + moditimestamp + ", modimemo=" + modimemo + ", getAddresslistguid()=" + getAddresslistguid() + ", getEmployeeid()="
				+ getEmployeeid() + ", getEmployeecode()=" + getEmployeecode() + ", getCompanyid()=" + getCompanyid() + ", getCompanycode()=" + getCompanycode() + ", getDeptid()=" + getDeptid()
				+ ", getDeptcode()=" + getDeptcode() + ", getPostid()=" + getPostid() + ", getPostcode()=" + getPostcode() + ", getJobnumber()=" + getJobnumber() + ", getName()=" + getName()
				+ ", getSex()=" + getSex() + ", getInnerphone()=" + getInnerphone() + ", getExtphone()=" + getExtphone() + ", getMobilephone()=" + getMobilephone() + ", getEmail()=" + getEmail()
				+ ", getOfficeaddress()=" + getOfficeaddress() + ", getRefreshtimestamp()=" + getRefreshtimestamp() + ", getMemo()=" + getMemo() + ", getModiuser()=" + getModiuser()
				+ ", getModitimestamp()=" + getModitimestamp() + ", getModimemo()=" + getModimemo() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	/**
	 * 批量code转名称
	 * 
	 * 
	 * @param mapper
	 * @param companyMap
	 * @param deptMap
	 * @param jobMap
	 * @param postMap
	 * @param sexMap
	 */
	public void convertManyCodeToName(Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, Post> postMap, Map<Integer, String> sexMap) {
		// 公司
		if (this.getCompanyid() != null) {
			Company company = companyMap.get(this.getCompanyid());
			if (company != null)
				this.setCompanyname(company.getCompanyfullname());
		}

		// 部门
		if (this.getDeptid() != null) {
			Department dept = deptMap.get(this.getDeptid());
			if (dept != null)
				this.setDeptname(dept.getDeptfullname());
		}

		// 岗位
		if (this.getPostid() != null) {
			Post post = postMap.get(this.getPostid());
			if (post != null)
				this.setPostname(post.getPostname());
		}

		// 性别
		if (this.getSex() != null) {
			String name = sexMap.get(this.getSex());
			this.setSexname(name);
		}

	}

	/**
	 * code轉名稱
	 * 
	 * @param mapper
	 * @param companyDao
	 * @param departmentDao
	 * @param postDao
	 * @param optionDao
	 */
	public void convertOneCodeToName(CompanyDao companyDao, DepartmentDao departmentDao, PostDao postDao, OptionDao optionDao) {
		// 公司
		if (this.getCompanyid() != null) {
			Company company = OrgPathUtil.getOneCompanyFullPath(this.getCompanyid(), companyDao);
			if (company != null)
				this.setCompanyname(company.getCompanyname());
		}

		// 部门
		if (this.getDeptid() != null) {
			Department dept = OrgPathUtil.getOneDepartmentFullPath(this.getDeptid(), departmentDao);
			if (dept != null)
				this.setDeptname(dept.getDeptfullname());
		}

		// 岗位
		if (this.getPostid() != null) {
			Post post = postDao.getPostByPostId(this.getPostid());
			if (post != null)
				this.setPostname(post.getPostname());
		}

		// 性别
		if (this.getSex() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.SEX, this.getSex());
			if (opt != null)
				this.setSexname(opt.getName());
		}

	}

}
