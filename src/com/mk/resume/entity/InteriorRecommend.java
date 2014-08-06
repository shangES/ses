package com.mk.resume.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.addresslist.dao.AddressListDao;
import com.mk.addresslist.entity.AddressList;
import com.mk.company.dao.CompanyDao;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.employee.dao.EmployeeDao;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.quota.dao.QuotaDao;
import com.mk.resume.dao.ResumeDao;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.User;

public class InteriorRecommend {
	private String mycandidatesguid;
	private String userguid;
	private String webuserguid;
	private Timestamp moditimestamp;

	// 临时
	private String username;
	private String addresslistguid;
	private String name;
	private Integer sex;
	private String culturename;
	private String workagename;
	private Date birthday;
	private Integer birthdayname;
	private String mobile;
	private Integer candidatesstate;
	private String candidatesstatename;
	
	public Integer getCandidatesstate() {
		return candidatesstate;
	}

	public void setCandidatesstate(Integer candidatesstate) {
		this.candidatesstate = candidatesstate;
	}

	public String getCandidatesstatename() {
		return candidatesstatename;
	}

	public void setCandidatesstatename(String candidatesstatename) {
		this.candidatesstatename = candidatesstatename;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getCulturename() {
		return culturename;
	}

	public void setCulturename(String culturename) {
		this.culturename = culturename;
	}

	public String getWorkagename() {
		return workagename;
	}

	public void setWorkagename(String workagename) {
		this.workagename = workagename;
	}

	public Integer getBirthdayname() {
		return birthdayname;
	}

	public void setBirthdayname(Integer birthdayname) {
		this.birthdayname = birthdayname;
	}

	public String getAddresslistguid() {
		return addresslistguid;
	}

	public void setAddresslistguid(String addresslistguid) {
		this.addresslistguid = addresslistguid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMycandidatesguid() {
		return mycandidatesguid;
	}

	public void setMycandidatesguid(String mycandidatesguid) {
		this.mycandidatesguid = mycandidatesguid;
	}

	public String getUserguid() {
		return userguid;
	}

	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}

	public String getWebuserguid() {
		return webuserguid;
	}

	public void setWebuserguid(String webuserguid) {
		this.webuserguid = webuserguid;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getModitimestamp() {
		return moditimestamp;
	}

	public void setModitimestamp(Timestamp moditimestamp) {
		this.moditimestamp = moditimestamp;
	}

	/**
	 * 翻译
	 * 
	 * @param departmentDao
	 * @param companyDao
	 * @param postDao
	 */
	public void convertOneCodeToName(DepartmentDao departmentDao, CompanyDao companyDao, PostDao postDao, SystemDao systemDao, EmployeeDao employeeDao, QuotaDao quotaDao, OptionDao optionDao,
			AddressListDao addressListDao, MyCandidatesDao myCandidatesDao,ResumeDao resumeDao) {
		// 推荐人
		if (this.getUserguid() != null) {
			User user = systemDao.getUserByUserId(this.getUserguid());
			if (user != null) {
				user.convertOneCodeToName(employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
				this.setUsername(user.getUsername());
				if (user.getEmployeeid() != null) {
					List<AddressList> addressLists = addressListDao.getAddressListByEmployeeId(user.getEmployeeid());
					if (!addressLists.isEmpty()) {
						AddressList address = addressLists.get(0);
						this.setAddresslistguid(address.getAddresslistguid());
					}
				}

			}
		}
		
		//应聘的状态
		if (this.getMycandidatesguid() != null) {
			MyCandidates myCandidates = myCandidatesDao.getMyCandidatesById(this.getMycandidatesguid());
			if(myCandidates!=null){
				if (myCandidates.getCandidatesstate() != null) {
					OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CANDIDATESSTATE, myCandidates.getCandidatesstate());
					if (opt != null) {
						this.setCandidatesstatename(opt.getName());
					}
				}
			}
		}
		
		
		
		//推荐谁的简历情况
		if (this.getWebuserguid() != null) {
			Resume resume = resumeDao.getResumeById(this.getWebuserguid());
			if (resume != null) {
				resume.convertOneCodeToName(optionDao, myCandidatesDao);
				// 姓名 出生日期 年龄 学历 工作年限 手机
				this.setName(resume.getName());
				this.setBirthdayname(resume.getBirthdayname());
				this.setBirthday(resume.getBirthday());
				this.setCulturename(resume.getCulturename());
				this.setWorkagename(resume.getWorkagename());
				this.setMobile(resume.getMobile());
			}
		}
		
	}
}
