package com.mk.person.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.OrgPathUtil;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.company.entity.Rank;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.util.StringUtils;
import com.mk.quota.dao.QuotaDao;
import com.mk.quota.entity.Quota;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.User;

public class Person {

	private String mycandidatesguid;
	private String companyid;
	private String deptid;
	private String quotaid;
	private String rankid;
	private String postid;
	private Timestamp joindate;
	private String regisuserguid;
	private String regisaddress;
	private String officeaddress;
	private Integer employtype;
	private String social;
	private String jobnumber;
	private String email;
	private String name;
	private Integer sex;
	private Date birthday;
	private String cardnumber;
	private Integer culture;
	private Integer nation;
	private String mobile;
	private String addressmobile;
	private String residenceplace;
	private String homephone;
	private String homeplace;
	private Integer bloodtype;
	private Integer domicilplace;
	private String nativeplace;
	private Integer politics;
	private Integer married;
	private String privateemail;
	private String photo;
	private String contactname;
	private String contactphone;
	private Integer contactrelationship;
	private Date joinworkdate;
	private String memo;
	private String modiuser;
	private Date moditimestamp;
	private String modimemo;

	// 后来加的7.23
	private String height;
	private Date societydate;
	private String legaladdress;
	private int payment;
	private Date starttime;
	private Date endtime;
	private String skills;
	private String hobbies;

	// 临时
	private String companyname;
	private String deptname;
	private String postname;
	private String quotaname;
	private String rankname;
	private String employtypename;
	private String regisusername;
	private String sexname;
	private String culturename;
	private String nationname;
	private String bloodtypename;
	private String politicsname;
	private String marriedname;
	private String contactRelationshipname;
	private String domicilplacename;
	private String timeformat;
	private String regisuseremail;
	private String regisusermobile;

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Date getSocietydate() {
		return societydate;
	}

	public void setSocietydate(Date societydate) {
		this.societydate = societydate;
	}

	public String getLegaladdress() {
		return legaladdress;
	}

	public void setLegaladdress(String legaladdress) {
		this.legaladdress = legaladdress;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public String getRegisuseremail() {
		return regisuseremail;
	}

	public void setRegisuseremail(String regisuseremail) {
		this.regisuseremail = regisuseremail;
	}

	public String getRegisusermobile() {
		return regisusermobile;
	}

	public void setRegisusermobile(String regisusermobile) {
		this.regisusermobile = regisusermobile;
	}

	public String getTimeformat() {
		return timeformat;
	}

	public void setTimeformat(String timeformat) {
		this.timeformat = timeformat;
	}

	public String getDomicilplacename() {
		return domicilplacename;
	}

	public void setDomicilplacename(String domicilplacename) {
		this.domicilplacename = domicilplacename;
	}

	public String getJobnumber() {
		return jobnumber;
	}

	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmploytypename() {
		return employtypename;
	}

	public void setEmploytypename(String employtypename) {
		this.employtypename = employtypename;
	}

	public Integer getEmploytype() {
		return employtype;
	}

	public void setEmploytype(Integer employtype) {
		this.employtype = employtype;
	}

	public String getSocial() {
		return social;
	}

	public void setSocial(String social) {
		this.social = social;
	}

	public String getRegisuserguid() {
		return regisuserguid;
	}

	public void setRegisuserguid(String regisuserguid) {
		this.regisuserguid = regisuserguid;
	}

	public String getRegisaddress() {
		return regisaddress;
	}

	public void setRegisaddress(String regisaddress) {
		this.regisaddress = regisaddress;
	}

	public String getOfficeaddress() {
		return officeaddress;
	}

	public void setOfficeaddress(String officeaddress) {
		this.officeaddress = officeaddress;
	}

	public String getRegisusername() {
		return regisusername;
	}

	public void setRegisusername(String regisusername) {
		this.regisusername = regisusername;
	}

	public String getQuotaname() {
		return quotaname;
	}

	public void setQuotaname(String quotaname) {
		this.quotaname = quotaname;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getRankname() {
		return rankname;
	}

	public void setRankname(String rankname) {
		this.rankname = rankname;
	}

	public String getQuotaid() {
		return quotaid;
	}

	public void setQuotaid(String quotaid) {
		this.quotaid = quotaid;
	}

	public String getRankid() {
		return rankid;
	}

	public void setRankid(String rankid) {
		this.rankid = rankid;
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getJoindate() {
		return joindate;
	}

	public void setJoindate(Timestamp joindate) {
		this.joindate = joindate;
	}

	public String getMycandidatesguid() {
		return mycandidatesguid;
	}

	public void setMycandidatesguid(String mycandidatesguid) {
		this.mycandidatesguid = mycandidatesguid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public Integer getCulture() {
		return culture;
	}

	public void setCulture(Integer culture) {
		this.culture = culture;
	}

	public Integer getNation() {
		return nation;
	}

	public void setNation(Integer nation) {
		this.nation = nation;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddressmobile() {
		return addressmobile;
	}

	public void setAddressmobile(String addressmobile) {
		this.addressmobile = addressmobile;
	}

	public String getResidenceplace() {
		return residenceplace;
	}

	public void setResidenceplace(String residenceplace) {
		this.residenceplace = residenceplace;
	}

	public String getHomephone() {
		return homephone;
	}

	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	public String getHomeplace() {
		return homeplace;
	}

	public void setHomeplace(String homeplace) {
		this.homeplace = homeplace;
	}

	public Integer getBloodtype() {
		return bloodtype;
	}

	public void setBloodtype(Integer bloodtype) {
		this.bloodtype = bloodtype;
	}

	public Integer getDomicilplace() {
		return domicilplace;
	}

	public void setDomicilplace(Integer domicilplace) {
		this.domicilplace = domicilplace;
	}

	public String getNativeplace() {
		return nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	public Integer getPolitics() {
		return politics;
	}

	public void setPolitics(Integer politics) {
		this.politics = politics;
	}

	public Integer getMarried() {
		return married;
	}

	public void setMarried(Integer married) {
		this.married = married;
	}

	public String getPrivateemail() {
		return privateemail;
	}

	public void setPrivateemail(String privateemail) {
		this.privateemail = privateemail;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public String getContactphone() {
		return contactphone;
	}

	public void setContactphone(String contactphone) {
		this.contactphone = contactphone;
	}

	public Integer getContactrelationship() {
		return contactrelationship;
	}

	public void setContactrelationship(Integer contactrelationship) {
		this.contactrelationship = contactrelationship;
	}

	public Date getJoinworkdate() {
		return joinworkdate;
	}

	public void setJoinworkdate(Date joinworkdate) {
		this.joinworkdate = joinworkdate;
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

	public String getSexname() {
		return sexname;
	}

	public void setSexname(String sexname) {
		this.sexname = sexname;
	}

	public String getCulturename() {
		return culturename;
	}

	public void setCulturename(String culturename) {
		this.culturename = culturename;
	}

	public String getNationname() {
		return nationname;
	}

	public void setNationname(String nationname) {
		this.nationname = nationname;
	}

	public String getBloodtypename() {
		return bloodtypename;
	}

	public void setBloodtypename(String bloodtypename) {
		this.bloodtypename = bloodtypename;
	}

	public String getPoliticsname() {
		return politicsname;
	}

	public void setPoliticsname(String politicsname) {
		this.politicsname = politicsname;
	}

	public String getMarriedname() {
		return marriedname;
	}

	public void setMarriedname(String marriedname) {
		this.marriedname = marriedname;
	}

	public String getContactRelationshipname() {
		return contactRelationshipname;
	}

	public void setContactRelationshipname(String contactRelationshipname) {
		this.contactRelationshipname = contactRelationshipname;
	}

	@Override
	public String toString() {
		return "Person [mycandidatesguid=" + mycandidatesguid + ", companyid=" + companyid + ", deptid=" + deptid + ", name=" + name + ", sex=" + sex + ", birthday=" + birthday + ", cardnumber="
				+ cardnumber + ", culture=" + culture + ", nation=" + nation + ", mobile=" + mobile + ", addressmobile=" + addressmobile + ", residenceplace=" + residenceplace + ", homephone="
				+ homephone + ", homeplace=" + homeplace + ", bloodtype=" + bloodtype + ", domicilplace=" + domicilplace + ", nativeplace=" + nativeplace + ", politics=" + politics + ", married="
				+ married + ", privateemail=" + privateemail + ", photo=" + photo + ", contactname=" + contactname + ", contactphone=" + contactphone + ", contactrelationship=" + contactrelationship
				+ ", joinworkdate=" + joinworkdate + ", memo=" + memo + ", modiuser=" + modiuser + ", moditimestamp=" + moditimestamp + ", modimemo=" + modimemo + ", companyname=" + companyname
				+ ", deptname=" + deptname + ", sexname=" + sexname + ", culturename=" + culturename + ", nationname=" + nationname + ", bloodtypename=" + bloodtypename + ", politicsname="
				+ politicsname + ", marriedname=" + marriedname + ", contactRelationshipname=" + contactRelationshipname + "]";
	}

	/**
	 * code转名称
	 * 
	 * @param optionDao
	 * @param comDao
	 * @param deptDao
	 */
	public void convertOneCodeToName(OptionDao optionDao, CompanyDao comDao, DepartmentDao deptDao, PostDao postDao, QuotaDao quotaDao, SystemDao systemDao, EmployeeDao employeeDao) {

		// 公司名称
		if (this.getCompanyid() != null) {
			Company company = OrgPathUtil.getOneCompanyFullPath(this.getCompanyid(), comDao);
			if (company != null)
				this.setCompanyname(company.getCompanyfullname());
		}

		// 部门名称
		if (this.getDeptid() != null) {
			Department dept = OrgPathUtil.getOneDepartmentFullPath(this.getDeptid(), deptDao);
			if (dept != null) {
				this.setDeptname(dept.getDeptfullname());
			}
		}

		// 岗位
		if (this.getPostid() != null) {
			Post post = postDao.getPostByPostId(this.getPostid());
			if (post != null)
				this.setPostname(post.getPostname());
		}

		// 职级
		if (this.getRankid() != null) {
			Rank rank = comDao.getRankById(this.getRankid());
			if (rank != null)
				this.setRankname(rank.getLevelname());
		}

		// 编制
		if (this.getQuotaid() != null) {
			Quota quota = quotaDao.getQuotaById(this.getQuotaid());
			if (quota != null) {
				quota.convertBudgettype(comDao);
				this.setQuotaname(quota.getBudgettypename());
			}
		}

		// 报到人
		if (this.getRegisuserguid() != null) {
			User user = systemDao.getUserByUserId(this.getRegisuserguid());
			if (user != null) {
				if (StringUtils.isNotEmpty(user.getEmployeeid())) {
					Employee employee = employeeDao.getEmployeeById(user.getEmployeeid());
					if (employee != null)
						this.setRegisusername(employee.getName());
					this.setRegisuseremail(employee.getEmail());
					this.setRegisusermobile(employee.getMobile());
				}
			}
		}

		// 性别
		if (this.getSex() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.SEX, this.getSex());
			if (opt != null)
				this.setSexname(opt.getName());
		}

		// 文化程度
		if (this.getCulture() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CULTURE, this.getCulture());
			if (opt != null) {
				this.setCulturename(opt.getName());
			}
		}

		// 民族
		if (this.getNation() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.NATION, this.getNation());
			if (opt != null) {
				this.setNationname(opt.getName());

			}
		}

		// 血型
		if (this.getBloodtype() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.BLOODTYPE, this.getBloodtype());
			if (opt != null) {
				this.setBloodtypename(opt.getName());

			}
		}

		// 政治面貌
		if (this.getPolitics() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.POLITICS, this.getPolitics());
			if (opt != null) {
				this.setPoliticsname(opt.getName());

			}
		}

		// 婚姻状况
		if (this.getMarried() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.MARRIED, this.getMarried());
			if (opt != null) {
				this.setMarriedname(opt.getName());

			}
		}

		// 与本人关系
		if (this.getContactrelationship() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RELATIONSHIP, this.getContactrelationship());
			if (opt != null) {
				this.setContactRelationshipname(opt.getName());
			}
		}

		// 用工性质
		if (this.getEmploytype() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.EMPLOYTYPE, this.getEmploytype());
			if (opt != null)
				this.setEmploytypename(opt.getName());
		}

		// 户籍类型
		if (this.getDomicilplace() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.DOMICILPLACE, this.getDomicilplace());
			if (opt != null)
				this.setDomicilplacename(opt.getName());
		}

	}

	/**
	 * 批量转
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param examinationstateMap
	 * @param sexMap
	 * @param cultureMap
	 * @param nationMap
	 * @param bloodtypeMap
	 * @param politicsMap
	 * @param marriedMap
	 * @param contactrelationshipMap
	 */

	public void convertManyCodeToName(Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, String> rankMap, Map<String, Post> postMap, Map<String, String> quotaMap,
			Map<Integer, String> sexMap, Map<Integer, String> cultureMap, Map<Integer, String> nationMap, Map<Integer, String> bloodtypeMap, Map<Integer, String> politicsMap,
			Map<Integer, String> marriedMap, Map<Integer, String> contactrelationshipMap, Map<Integer, String> employtypeMap, Map<String, String> userMap, Map<Integer, String> domicilplaceMap) {

		// 部门
		if (this.getDeptid() != null) {
			Department dept = deptMap.get(this.getDeptid());
			if (dept != null)
				this.setDeptname(dept.getDeptfullname());
		}

		// 公司
		if (this.getCompanyid() != null) {
			Company company = companyMap.get(this.getCompanyid());
			this.setCompanyname(company.getCompanyfullname());
		}

		// 岗位
		if (this.getPostid() != null) {
			Post post = postMap.get(this.getPostid());
			if (post != null)
				this.setPostname(post.getPostname());
		}

		// 职级
		if (this.getRankid() != null) {
			String name = rankMap.get(this.getRankid());
			this.setRankname(name);
		}

		// 编制
		if (this.getQuotaid() != null) {
			String name = quotaMap.get(this.getQuotaid());
			this.setQuotaname(name);
		}

		// 性别
		if (this.getSex() != null) {
			String name = sexMap.get(this.getSex());
			this.setSexname(name);
		}

		// 文化程度
		if (this.getCulture() != null) {
			String name = cultureMap.get(this.getCulture());
			this.setCulturename(name);

		}

		// 籍贯
		if (this.getNation() != null) {
			String name = nationMap.get(this.getNation());
			this.setNationname(name);
		}

		// 血型
		if (this.getBloodtype() != null) {
			String name = bloodtypeMap.get(this.getBloodtype());
			this.setBloodtypename(name);
		}

		// 政治面貌
		if (this.getPolitics() != null) {
			String name = politicsMap.get(this.getPolitics());
			this.setPoliticsname(name);
		}

		// 婚姻状态
		if (this.getMarried() != null) {
			String name = marriedMap.get(this.getMarried());
			this.setMarriedname(name);
		}

		// 与本人关系
		if (this.getContactrelationship() != null) {
			String name = contactrelationshipMap.get(this.getContactrelationship());
			this.setContactRelationshipname(name);
		}

		// 用工性质
		if (this.getEmploytype() != null) {
			String name = employtypeMap.get(this.getEmploytype());
			this.setEmploytypename(name);
		}

		// 报到人
		if (this.getRegisuserguid() != null) {
			String name = userMap.get(this.getRegisuserguid());
			this.setRegisusername(name);
		}
		
		// 户籍类型
		if (this.getDomicilplace() != null) {
			String name = domicilplaceMap.get(this.getDomicilplace());
			this.setDomicilplacename(name);
		}
	}

}
