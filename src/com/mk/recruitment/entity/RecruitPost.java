package com.mk.recruitment.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ctc.wstx.util.DataUtil;
import com.mk.OrgPathUtil;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.util.DateUtil;
import com.mk.quota.dao.QuotaDao;
import com.mk.recruitment.dao.RecruitmentDao;
import com.mk.recruitprogram.dao.RecruitprogramDao;
import com.mk.recruitprogram.entity.RecruitProgram;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class RecruitPost {
	private String recruitpostguid;
	private String workplaceguid;
	private String categoryguid;
	private String deptid;
	private String functions;
	private String postname;
	private String keyword;
	private Integer postnum;
	private Integer workage;
	private Integer language;
	private Integer educational;
	private Date validdate;
	private Integer traffic;
	private Integer collection;
	private Date releasedate;
	private String pubuser;
	private String audituser;
	private Integer isaudited;
	private Timestamp modtime;
	private String rmk;
	private Integer dorder;
	private Integer posttype;
	private int isurgent;

	private String recruitprogramguid;
	private String companyid;

	private PostContent postcontent;

	// 临时
	private String categoryname;
	private String workplacename;
	private String deptname;
	private String workagename;
	private String languagename;
	private String educationalname;
	private String posttypename;
	private String recruitprogramname;
	private String companyname;

	// 统计时间
	private Integer totlecandidates;
	private Integer yesterdaycandidates;

	// 临时
	private Integer candidatesstate;
	private String candidatesstatename;
	private Timestamp moditimestamp;

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

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getModitimestamp() {
		return moditimestamp;
	}

	public void setModitimestamp(Timestamp moditimestamp) {
		this.moditimestamp = moditimestamp;
	}

	public String getRecruitprogramname() {
		return recruitprogramname;
	}

	public void setRecruitprogramname(String recruitprogramname) {
		this.recruitprogramname = recruitprogramname;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getRecruitprogramguid() {
		return recruitprogramguid;
	}

	public void setRecruitprogramguid(String recruitprogramguid) {
		this.recruitprogramguid = recruitprogramguid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getPosttypename() {
		return posttypename;
	}

	public void setPosttypename(String posttypename) {
		this.posttypename = posttypename;
	}

	public String getLanguagename() {
		return languagename;
	}

	public void setLanguagename(String languagename) {
		this.languagename = languagename;
	}

	public String getEducationalname() {
		return educationalname;
	}

	public void setEducationalname(String educationalname) {
		this.educationalname = educationalname;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getWorkplacename() {
		return workplacename;
	}

	public void setWorkplacename(String workplacename) {
		this.workplacename = workplacename;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public PostContent getPostcontent() {
		return postcontent;
	}

	public void setPostcontent(PostContent postcontent) {
		this.postcontent = postcontent;
	}

	public Integer getDorder() {
		return dorder;
	}

	public void setDorder(Integer dorder) {
		this.dorder = dorder;
	}

	public String getRecruitpostguid() {
		return recruitpostguid;
	}

	public void setRecruitpostguid(String recruitpostguid) {
		this.recruitpostguid = recruitpostguid;
	}

	public String getWorkplaceguid() {
		return workplaceguid;
	}

	public void setWorkplaceguid(String workplaceguid) {
		this.workplaceguid = workplaceguid;
	}

	public String getCategoryguid() {
		return categoryguid;
	}

	public void setCategoryguid(String categoryguid) {
		this.categoryguid = categoryguid;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getFunctions() {
		return functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getPostnum() {
		return postnum;
	}

	public void setPostnum(Integer postnum) {
		this.postnum = postnum;
	}

	public Integer getWorkage() {
		return workage;
	}

	public void setWorkage(Integer workage) {
		this.workage = workage;
	}

	public String getWorkagename() {
		return workagename;
	}

	public void setWorkagename(String workagename) {
		this.workagename = workagename;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public Integer getEducational() {
		return educational;
	}

	public void setEducational(Integer educational) {
		this.educational = educational;
	}

	public Integer getPosttype() {
		return posttype;
	}

	public void setPosttype(Integer posttype) {
		this.posttype = posttype;
	}

	public int getIsurgent() {
		return isurgent;
	}

	public void setIsurgent(int isurgent) {
		this.isurgent = isurgent;
	}

	public Date getValiddate() {
		return validdate;
	}

	public void setValiddate(Date validdate) {
		this.validdate = validdate;
	}

	public Integer getTraffic() {
		return traffic;
	}

	public void setTraffic(Integer traffic) {
		this.traffic = traffic;
	}

	public Integer getCollection() {
		return collection;
	}

	public void setCollection(Integer collection) {
		this.collection = collection;
	}

	public String getPubuser() {
		return pubuser;
	}

	public void setPubuser(String pubuser) {
		this.pubuser = pubuser;
	}

	public String getAudituser() {
		return audituser;
	}

	public void setAudituser(String audituser) {
		this.audituser = audituser;
	}

	public Integer getIsaudited() {
		return isaudited;
	}

	public void setIsaudited(Integer isaudited) {
		this.isaudited = isaudited;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getModtime() {
		return modtime;
	}

	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	public Date getReleasedate() {
		return releasedate;
	}

	public void setReleasedate(Date releasedate) {
		this.releasedate = releasedate;
	}

	public Integer getTotlecandidates() {
		return totlecandidates;
	}

	public void setTotlecandidates(Integer totlecandidates) {
		this.totlecandidates = totlecandidates;
	}

	public Integer getYesterdaycandidates() {
		return yesterdaycandidates;
	}

	public void setYesterdaycandidates(Integer yesterdaycandidates) {
		this.yesterdaycandidates = yesterdaycandidates;
	}

	/**
	 * code转名称
	 * 
	 * @param mapper
	 * @param departmentDao
	 * @param optionDao
	 */
	public void convertOneCodeToName(RecruitmentDao mapper, DepartmentDao departmentDao, OptionDao optionDao, CompanyDao companyDao, RecruitprogramDao recruitprogramDao, PostDao postDao,
			QuotaDao quotaDao) {
		// 职位类别
		if (this.getCategoryguid() != null) {
			Category model = mapper.getCategoryById(this.getCategoryguid());
			if (model != null) {
				this.setCategoryname(model.getCategoryname());
			}
		}

		// 工作地点
		if (this.getWorkplaceguid() != null) {
			WorkPlace model = mapper.getWorkPlaceById(this.getWorkplaceguid());
			if (model != null) {
				this.setWorkplacename(model.getWorkplacename());
			}
		}

		// 公司名称
		if (this.getCompanyid() != null) {
			Company company = OrgPathUtil.getOneCompanyFullPath(this.getCompanyid(), companyDao);
			if (company != null){
				this.setCompanyname(company.getCompanyfullname());
			}
		}

		// 部门名称
		if (this.getDeptid() != null) {
			Department dept = OrgPathUtil.getOneDepartmentFullPath(this.getDeptid(), departmentDao);
			if (dept != null){
				this.setDeptname(dept.getDeptname());
			}
		}

		// 要求学历
		if (this.getEducational() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CULTURE, this.getEducational());
			if (opt != null)
				this.setEducationalname(opt.getName());
		}

		// 工作年限
		if (this.getWorkage() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.WORKAGE, this.getWorkage());
			if (opt != null)
				this.setWorkagename(opt.getName());
		}

		// 招聘类别
		if (this.getPosttype() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.POSTTYPE, this.getPosttype());
			if (opt != null)
				this.setPosttypename(opt.getName());
		}

		// 语言要求
		if (this.getLanguage() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.LANGUAGE, this.getLanguage());
			if (opt != null)
				this.setLanguagename(opt.getName());
		}

		// 招聘计划
		if (this.getRecruitprogramguid() != null) {
			RecruitProgram model = recruitprogramDao.getRecruitprogramById(this.getRecruitprogramguid());
			if (model != null) {
				model.convertOneCodeToName(companyDao, departmentDao, postDao, quotaDao, optionDao);
				this.setRecruitprogramname(model.getDeptname() + "/" + model.getPostname());
			}
		}
		// 统计投递数量
		if (this.getRecruitpostguid() != null) {
			Integer _totlecandidates = mapper.countMyCandidatesByRecruitPostGuid(this.getRecruitpostguid());
			if (_totlecandidates != null) {
				this.setTotlecandidates(_totlecandidates);
			}
			Date date = new Date(System.currentTimeMillis());
			Calendar ca = Calendar.getInstance();
			ca.setTime(date);
			ca.add(Calendar.DATE, Constance.State_Tobe);
			StringBuffer _datas = new StringBuffer();
			_datas.append(DateUtil.formatDateYMD(ca.getTime()));
			_datas.append(" 01:00:00");

			StringBuffer _datae = new StringBuffer();
			_datae.append(DateUtil.formatDateYMD(ca.getTime()));
			_datae.append(" 23:59:59");
			Integer _yesterdaycandidates = mapper.countMyCandidatesByRecruitPostGuidAndTime(this.getRecruitpostguid(), _datas.toString(), _datae.toString());
			if (_yesterdaycandidates != null) {
				this.setYesterdaycandidates(_yesterdaycandidates);
			}
		}
		
		// 应聘状态
		if (this.getCandidatesstate() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CANDIDATESSTATE, this.getCandidatesstate());
			if (opt != null) {
				this.setCandidatesstatename(opt.getName());
			}
		}
	}

	/**
	 * 批量翻译
	 * 
	 * @param deptMap
	 * @param workplaceMap
	 * @param categoryMap
	 * @param educationalMap
	 * @param jobyearMap
	 * @param posttypeMap
	 * @param languageMap
	 */
	public void convertManyCodeToName(Map<String, Department> deptMap, Map<String, String> workplaceMap, Map<String, String> categoryMap, Map<Integer, String> educationalMap,
			Map<Integer, String> jobyearMap, Map<Integer, String> posttypeMap, Map<Integer, String> languageMap, Map<String, Company> companyMap, RecruitprogramDao recruitprogramDao,
			Map<String, Post> postMap, Map<String, RecruitProgram> recruitProgramMap, Map<Integer, String> stateMap, RecruitmentDao mapper) {
		// 部门
		if (this.getDeptid() != null) {
			Department dept = deptMap.get(this.getDeptid());
			if (dept != null){
				this.setDeptname(dept.getDeptfullname());
			}
		}

		// 公司
		if (this.getCompanyid() != null) {
			Company company = companyMap.get(this.getCompanyid());
			if(company!=null){
				this.setCompanyname(company.getCompanyfullname());
			}
		}

		// 工作地点
		if (this.getWorkplaceguid() != null) {
			String name = workplaceMap.get(this.getWorkplaceguid());
			this.setWorkplacename(name);
		}

		// 职位类别
		if (this.getCategoryguid() != null) {
			String name = categoryMap.get(this.getCategoryguid());
			this.setCategoryname(name);
		}

		// 要求学历
		if (this.getEducational() != null) {
			String name = educationalMap.get(this.getEducational());
			this.setEducationalname(name);
		}

		// 工作年限
		if (this.getWorkage() != null) {
			String name = jobyearMap.get(this.getWorkage());
			this.setWorkagename(name);
		}

		// 招聘类别
		if (this.getPosttype() != null) {
			String name = posttypeMap.get(this.getPosttype());
			this.setPosttypename(name);
		}

		// 语言要求
		if (this.getLanguage() != null) {
			String name = languageMap.get(this.getLanguage());
			this.setLanguagename(name);
		}

		// 招聘计划
		if (this.getRecruitprogramguid() != null) {
			RecruitProgram model = recruitProgramMap.get(this.getRecruitprogramguid());
			if (model != null) {
				model.convertManyCodeToName(companyMap, deptMap, postMap, null, null, null, stateMap);
				this.setRecruitprogramname(model.getDeptname() + "/" + model.getPostname());
			}
		}

		// 统计投递数量
		if (this.getRecruitpostguid() != null) {
			Integer _totlecandidates = mapper.countMyCandidatesByRecruitPostGuid(this.getRecruitpostguid());
			if (_totlecandidates != null) {
				this.setTotlecandidates(_totlecandidates);
			}
			Date date = new Date(System.currentTimeMillis());
			Calendar ca = Calendar.getInstance();
			ca.setTime(date);
			ca.add(Calendar.DATE, Constance.State_Tobe);
			StringBuffer _datas = new StringBuffer();
			_datas.append(DateUtil.formatDateYMD(ca.getTime()));
			_datas.append(" 01:00:00");

			StringBuffer _datae = new StringBuffer();
			_datae.append(DateUtil.formatDateYMD(ca.getTime()));
			_datae.append(" 23:59:59");
			Integer _yesterdaycandidates = mapper.countMyCandidatesByRecruitPostGuidAndTime(this.getRecruitpostguid(), _datas.toString(), _datae.toString());
			if (_yesterdaycandidates != null) {
				this.setYesterdaycandidates(_yesterdaycandidates);
			}
		}
	}

	@Override
	public String toString() {
		return "RecruitPost [recruitpostguid=" + recruitpostguid + ", workplaceguid=" + workplaceguid + ", categoryguid=" + categoryguid + ", deptid=" + deptid + ", functions=" + functions
				+ ", postname=" + postname + ", keyword=" + keyword + ", postnum=" + postnum + ", workage=" + workage + ", language=" + language + ", educational=" + educational + ", validdate="
				+ validdate + ", traffic=" + traffic + ", collection=" + collection + ", releasedate=" + releasedate + ", pubuser=" + pubuser + ", audituser=" + audituser + ", isaudited=" + isaudited
				+ ", modtime=" + modtime + ", rmk=" + rmk + ", dorder=" + dorder + ", posttype=" + posttype + ", isurgent=" + isurgent + ", postcontent=" + postcontent + ", categoryname="
				+ categoryname + ", workplacename=" + workplacename + ", deptname=" + deptname + ", workagename=" + workagename + ", languagename=" + languagename + ", educationalname="
				+ educationalname + ", posttypename=" + posttypename + ", getPosttypename()=" + getPosttypename() + ", getLanguagename()=" + getLanguagename() + ", getEducationalname()="
				+ getEducationalname() + ", getCategoryname()=" + getCategoryname() + ", getWorkplacename()=" + getWorkplacename() + ", getDeptname()=" + getDeptname() + ", getPostcontent()="
				+ getPostcontent() + ", getDorder()=" + getDorder() + ", getRecruitpostguid()=" + getRecruitpostguid() + ", getWorkplaceguid()=" + getWorkplaceguid() + ", getCategoryguid()="
				+ getCategoryguid() + ", getDeptid()=" + getDeptid() + ", getFunctions()=" + getFunctions() + ", getPostname()=" + getPostname() + ", getKeyword()=" + getKeyword() + ", getPostnum()="
				+ getPostnum() + ", getWorkage()=" + getWorkage() + ", getWorkagename()=" + getWorkagename() + ", getLanguage()=" + getLanguage() + ", getEducational()=" + getEducational()
				+ ", getPosttype()=" + getPosttype() + ", getIsurgent()=" + getIsurgent() + ", getValiddate()=" + getValiddate() + ", getTraffic()=" + getTraffic() + ", getCollection()="
				+ getCollection() + ", getPubuser()=" + getPubuser() + ", getAudituser()=" + getAudituser() + ", getIsaudited()=" + getIsaudited() + ", getModtime()=" + getModtime() + ", getRmk()="
				+ getRmk() + ", getReleasedate()=" + getReleasedate() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
