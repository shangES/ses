package com.mk.mycandidates.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.audition.dao.AuditionDao;
import com.mk.audition.entity.AuditionRecord;
import com.mk.audition.entity.AuditionResult;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.util.DateUtil;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.person.dao.PersonDao;
import com.mk.person.entity.ExaminationRecord;
import com.mk.quota.dao.QuotaDao;
import com.mk.recruitment.dao.RecruitmentDao;
import com.mk.recruitment.dao.WebUserDao;
import com.mk.recruitment.entity.RecruitPost;
import com.mk.recruitment.entity.WebUser;
import com.mk.recruitprogram.dao.RecruitprogramDao;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.EducationExperience;
import com.mk.resume.entity.Resume;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.OptionList;
import com.mk.thirdpartner.dao.ThirdPartnerDao;

public class MyCandidates {

	private String mycandidatesguid;
	private String recruitpostguid;
	private String webuserguid;
	private Integer candidatesstate;
	private Integer candidatestype;
	private Integer progress;
	private Date candidatestime;
	private String matchuser;
	private Timestamp matchtime;
	private String matchmemo;
	private String holduser;
	private Timestamp holdtime;
	private String holdmemo;
	private String recruitpostname;
	private String resumeeamilguid;
	private Integer readtype;
	private String employeeid;
	private Integer posttype;
	private String recruitprogramoperateguid;

	// 临时属性
	private String culturename;
	private String name;
	private Integer sex;
	private Integer mark;
	private Date birthday;
	private Integer birthdayname;
	private Integer workage;
	private String workagename;
	private String webusername;
	private String recommendcompanyid;
	private String recommenddeptid;
	private String recommendpostguid;
	private Integer recommendstate;
	private Timestamp auditiontime;
	private String recommendcompanyname;
	private String recommenddeptname;
	private String recommendpostname;
	private String candidatesstatename;
	private String progressname;
	private String candidatestypename;
	private String employeename;
	private String nativeplace;
	private String sexname;
	private String school;
	private String specialty;
	private String salary;

	// 匹配度
	private String match;
	// 推荐
	private String recommendguid;

	// 是否已读
	private boolean isreadtype;

	// 面试结果
	private Integer resulttype;
	private Integer resultcontent;
	private String resultcontentname;
	private String hrresultcontent;
	private String resulttypename;
	private Timestamp auditiondate;
	private String auditionaddress;

	// 面试官
	private String maininterviewerguid;
	private String maininterviewerguidname;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;
	private String userguid;
	private String username;

	// 临时属性
	private String thirdpartnerguidname;
	private String resumetypename;
	private String postname;
	private String companyname;
	private String deptid;
	private String deptname;
	private String workplacename;

	private String thirdpartnerguid;
	private String auditionrecordguid;
	private Date examinationdate;

	private String timeformat;
	private String msg;
	private Integer countrecommend;
	private Integer countzrecommend;
	private Integer counthold;
	private Integer countauditionrecord;
	private Integer countresumeassess;
	private Integer countmsg;
	private Integer countsend;

	private String mobile;
	private String email;
	private String employeemobile;
	private String employeeemail;
	private String tempdate;

	// 是否发送邮件&短信
	private Integer isemail;
	private Integer ismsg;

	public MyCandidates() {

	}

	public MyCandidates(String mycandidatesguid, String recruitpostguid, Integer candidatesstate, String webuserguid, Integer progress, Date candidatestime, Integer readtype) {
		this.mycandidatesguid = mycandidatesguid;
		this.recruitpostguid = recruitpostguid;
		this.candidatesstate = candidatesstate;
		this.webuserguid = webuserguid;
		this.progress = progress;
		this.candidatestime = candidatestime;
	}

	public Integer getRecommendstate() {
		return recommendstate;
	}

	public void setRecommendstate(Integer recommendstate) {
		this.recommendstate = recommendstate;
	}

	public String getRecruitprogramoperateguid() {
		return recruitprogramoperateguid;
	}

	public void setRecruitprogramoperateguid(String recruitprogramoperateguid) {
		this.recruitprogramoperateguid = recruitprogramoperateguid;
	}

	public boolean isIsreadtype() {
		return isreadtype;
	}

	public void setIsreadtype(boolean isreadtype) {
		this.isreadtype = isreadtype;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getSexname() {
		return sexname;
	}

	public void setSexname(String sexname) {
		this.sexname = sexname;
	}

	public String getNativeplace() {
		return nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	public Integer getPosttype() {
		return posttype;
	}

	public void setPosttype(Integer posttype) {
		this.posttype = posttype;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

	public Integer getIsemail() {
		return isemail;
	}

	public void setIsemail(Integer isemail) {
		this.isemail = isemail;
	}

	public Integer getIsmsg() {
		return ismsg;
	}

	public void setIsmsg(Integer ismsg) {
		this.ismsg = ismsg;
	}

	public String getResultcontentname() {
		return resultcontentname;
	}

	public void setResultcontentname(String resultcontentname) {
		this.resultcontentname = resultcontentname;
	}

	public Integer getCountzrecommend() {
		return countzrecommend;
	}

	public void setCountzrecommend(Integer countzrecommend) {
		this.countzrecommend = countzrecommend;
	}

	public String getHrresultcontent() {
		return hrresultcontent;
	}

	public void setHrresultcontent(String hrresultcontent) {
		this.hrresultcontent = hrresultcontent;
	}

	public String getRecommendguid() {
		return recommendguid;
	}

	public void setRecommendguid(String recommendguid) {
		this.recommendguid = recommendguid;
	}

	public Date getExaminationdate() {
		return examinationdate;
	}

	public void setExaminationdate(Date examinationdate) {
		this.examinationdate = examinationdate;
	}

	public Integer getReadtype() {
		return readtype;
	}

	public void setReadtype(Integer readtype) {
		this.readtype = readtype;
	}

	public String getResulttypename() {
		return resulttypename;
	}

	public void setResulttypename(String resulttypename) {
		this.resulttypename = resulttypename;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserguid() {
		return userguid;
	}

	public void setUserguid(String userguid) {
		this.userguid = userguid;
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

	public String getRecruitpostname() {
		return recruitpostname;
	}

	public void setRecruitpostname(String recruitpostname) {
		this.recruitpostname = recruitpostname;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public String getWebusername() {
		return webusername;
	}

	public void setWebusername(String webusername) {
		this.webusername = webusername;
	}

	public String getWebuserguid() {
		return webuserguid;
	}

	public void setWebuserguid(String webuserguid) {
		this.webuserguid = webuserguid;
	}

	public String getMaininterviewerguidname() {
		return maininterviewerguidname;
	}

	public void setMaininterviewerguidname(String maininterviewerguidname) {
		this.maininterviewerguidname = maininterviewerguidname;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getAuditiondate() {
		return auditiondate;
	}

	public void setAuditiondate(Timestamp auditiondate) {
		this.auditiondate = auditiondate;
	}

	public String getAuditionaddress() {
		return auditionaddress;
	}

	public void setAuditionaddress(String auditionaddress) {
		this.auditionaddress = auditionaddress;
	}

	public String getCandidatestypename() {
		return candidatestypename;
	}

	public void setCandidatestypename(String candidatestypename) {
		this.candidatestypename = candidatestypename;
	}

	public Integer getCandidatestype() {
		return candidatestype;
	}

	public void setCandidatestype(Integer candidatestype) {
		this.candidatestype = candidatestype;
	}

	public String getThirdpartnerguid() {
		return thirdpartnerguid;
	}

	public void setThirdpartnerguid(String thirdpartnerguid) {
		this.thirdpartnerguid = thirdpartnerguid;
	}

	public String getThirdpartnerguidname() {
		return thirdpartnerguidname;
	}

	public void setThirdpartnerguidname(String thirdpartnerguidname) {
		this.thirdpartnerguidname = thirdpartnerguidname;
	}

	public String getRecommendcompanyname() {
		return recommendcompanyname;
	}

	public void setRecommendcompanyname(String recommendcompanyname) {
		this.recommendcompanyname = recommendcompanyname;
	}

	public String getRecommenddeptname() {
		return recommenddeptname;
	}

	public void setRecommenddeptname(String recommenddeptname) {
		this.recommenddeptname = recommenddeptname;
	}

	public String getRecommendcompanyid() {
		return recommendcompanyid;
	}

	public void setRecommendcompanyid(String recommendcompanyid) {
		this.recommendcompanyid = recommendcompanyid;
	}

	public String getRecommenddeptid() {
		return recommenddeptid;
	}

	public void setRecommenddeptid(String recommenddeptid) {
		this.recommenddeptid = recommenddeptid;
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

	public String getMaininterviewerguid() {
		return maininterviewerguid;
	}

	public void setMaininterviewerguid(String maininterviewerguid) {
		this.maininterviewerguid = maininterviewerguid;
	}

	public String getRecommendpostname() {
		return recommendpostname;
	}

	public void setRecommendpostname(String recommendpostname) {
		this.recommendpostname = recommendpostname;
	}

	public String getProgressname() {
		return progressname;
	}

	public void setProgressname(String progressname) {
		this.progressname = progressname;
	}

	public String getResumetypename() {
		return resumetypename;
	}

	public void setResumetypename(String resumetypename) {
		this.resumetypename = resumetypename;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public String getCandidatesstatename() {
		return candidatesstatename;
	}

	public void setCandidatesstatename(String candidatesstatename) {
		this.candidatesstatename = candidatesstatename;
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

	public String getWorkplacename() {
		return workplacename;
	}

	public void setWorkplacename(String workplacename) {
		this.workplacename = workplacename;
	}

	public Integer getBirthdayname() {
		return birthdayname;
	}

	public void setBirthdayname(Integer birthdayname) {
		this.birthdayname = birthdayname;
	}

	public String getWorkagename() {
		return workagename;
	}

	public void setWorkagename(String workagename) {
		this.workagename = workagename;
	}

	public String getCulturename() {
		return culturename;
	}

	public void setCulturename(String culturename) {
		this.culturename = culturename;
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

	public Integer getWorkage() {
		return workage;
	}

	public void setWorkage(Integer workage) {
		this.workage = workage;
	}

	public String getMycandidatesguid() {
		return mycandidatesguid;
	}

	public void setMycandidatesguid(String mycandidatesguid) {
		this.mycandidatesguid = mycandidatesguid;
	}

	public String getRecruitpostguid() {
		return recruitpostguid;
	}

	public void setRecruitpostguid(String recruitpostguid) {
		this.recruitpostguid = recruitpostguid;
	}

	public Integer getCandidatesstate() {
		return candidatesstate;
	}

	public void setCandidatesstate(Integer candidatesstate) {
		this.candidatesstate = candidatesstate;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public Date getCandidatestime() {
		return candidatestime;
	}

	public void setCandidatestime(Date candidatestime) {
		this.candidatestime = candidatestime;
	}

	public String getMatchuser() {
		return matchuser;
	}

	public void setMatchuser(String matchuser) {
		this.matchuser = matchuser;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getMatchtime() {
		return matchtime;
	}

	public void setMatchtime(Timestamp matchtime) {
		this.matchtime = matchtime;
	}

	public String getRecommendpostguid() {
		return recommendpostguid;
	}

	public void setRecommendpostguid(String recommendpostguid) {
		this.recommendpostguid = recommendpostguid;
	}

	public String getMatchmemo() {
		return matchmemo;
	}

	public void setMatchmemo(String matchmemo) {
		this.matchmemo = matchmemo;
	}

	public String getHolduser() {
		return holduser;
	}

	public void setHolduser(String holduser) {
		this.holduser = holduser;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getHoldtime() {
		return holdtime;
	}

	public void setHoldtime(Timestamp holdtime) {
		this.holdtime = holdtime;
	}

	public String getHoldmemo() {
		return holdmemo;
	}

	public void setHoldmemo(String holdmemo) {
		this.holdmemo = holdmemo;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getAuditiontime() {
		return auditiontime;
	}

	public void setAuditiontime(Timestamp auditiontime) {
		this.auditiontime = auditiontime;
	}

	public String getAuditionrecordguid() {
		return auditionrecordguid;
	}

	public void setAuditionrecordguid(String auditionrecordguid) {
		this.auditionrecordguid = auditionrecordguid;
	}

	public String getResumeeamilguid() {
		return resumeeamilguid;
	}

	public void setResumeeamilguid(String resumeeamilguid) {
		this.resumeeamilguid = resumeeamilguid;
	}

	public String getTimeformat() {
		return timeformat;
	}

	public void setTimeformat(String timeformat) {
		this.timeformat = timeformat;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCountrecommend() {
		return countrecommend;
	}

	public void setCountrecommend(Integer countrecommend) {
		this.countrecommend = countrecommend;
	}

	public Integer getCounthold() {
		return counthold;
	}

	public void setCounthold(Integer counthold) {
		this.counthold = counthold;
	}

	public Integer getCountauditionrecord() {
		return countauditionrecord;
	}

	public void setCountauditionrecord(Integer countauditionrecord) {
		this.countauditionrecord = countauditionrecord;
	}

	public Integer getCountresumeassess() {
		return countresumeassess;
	}

	public void setCountresumeassess(Integer countresumeassess) {
		this.countresumeassess = countresumeassess;
	}

	public Integer getCountmsg() {
		return countmsg;
	}

	public void setCountmsg(Integer countmsg) {
		this.countmsg = countmsg;
	}

	public Integer getCountsend() {
		return countsend;
	}

	public void setCountsend(Integer countsend) {
		this.countsend = countsend;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmployeemobile() {
		return employeemobile;
	}

	public void setEmployeemobile(String employeemobile) {
		this.employeemobile = employeemobile;
	}

	public String getEmployeeemail() {
		return employeeemail;
	}

	public void setEmployeeemail(String employeeemail) {
		this.employeeemail = employeeemail;
	}

	public String getTempdate() {
		return tempdate;
	}

	public void setTempdate(String tempdate) {
		this.tempdate = tempdate;
	}

	/**
	 * code转型 （数据量小的时候）
	 * 
	 * @param recruitmentDao
	 * @param optionDao
	 */
	public void convertOneCodeToName(RecruitmentDao recruitmentDao, OptionDao optionDao, DepartmentDao departmentDao, CompanyDao companyDao, WebUserDao webUserDao, PostDao postDao,
			ThirdPartnerDao thirdPartnerDao, EmployeeDao employeeDao, ResumeDao resumeDao, MyCandidatesDao mapper, SystemDao systemDao, PersonDao personDao, RecruitprogramDao recruitprogramDao,
			QuotaDao quotaDao, AuditionDao auditionDao) {
		// 来源
		if (this.getCandidatestype() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RESUMETYPE, this.getCandidatestype());
			if (opt != null) {
				if (this.getEmployeeid() != null) {
					Employee employee = employeeDao.getEmployeeById(this.getEmployeeid());
					if (employee != null) {
						this.setCandidatestypename(opt.getName() + "_" + employee.getName());
					} else {
						this.setCandidatestypename(opt.getName());
					}
				} else {
					this.setCandidatestypename(opt.getName());
				}
			}

		}

		// 职位名称
		if (this.getRecruitpostguid() != null) {
			RecruitPost recruitPost = recruitmentDao.getRecruitPostByRecruitPostId(this.getRecruitpostguid());
			if (recruitPost != null) {
				recruitPost.convertOneCodeToName(recruitmentDao, departmentDao, optionDao, companyDao, recruitprogramDao, postDao, quotaDao);
				// 公司 部门 工作地点 职位
				this.setCompanyname(recruitPost.getCompanyname());
				this.setDeptname(recruitPost.getDeptname());
				this.setPostname(recruitPost.getPostname());
				this.setDeptid(recruitPost.getDeptid());
				this.setWorkplacename(recruitPost.getWorkplacename());

			} else {
				// 邮箱传过来的职位名称
				if (this.getRecruitpostname() != null) {
					this.setPostname(this.getRecruitpostname());
				}
			}
		} else {
			// 邮箱传过来的职位名称
			if (this.getRecruitpostname() != null) {
				this.setPostname(this.getRecruitpostname());
			}
		}

		// 简历信息
		if (this.getWebuserguid() != null) {
			Resume resume = resumeDao.getResumeById(this.getWebuserguid());
			if (resume != null) {
				resume.convertOneCodeToName(optionDao, mapper);
				// 姓名 出生日期 年龄 学历 工作年限
				this.setName(resume.getName());
				this.setSexname(resume.getSexname());
				this.setBirthdayname(resume.getBirthdayname());
				this.setBirthday(resume.getBirthday());
				this.setCulturename(resume.getCulturename());
				this.setWorkagename(resume.getWorkagename());
				this.setMark(resume.getMark());
				this.setMobile(resume.getMobile());
				this.setEmail(resume.getEmail());
				this.setNativeplace(resume.getNativeplace());
				this.setSalary(resume.getSalary());
			}

			// 学校 专业
			List<EducationExperience> edulist = resumeDao.getAllEducationExperienceByWebuserId(this.getWebuserguid());
			if (!edulist.isEmpty() && edulist != null) {
				EducationExperience edu = edulist.get(0);
				this.setSchool(edu.getSchool());
				this.setSpecialty(edu.getSpecialty());
			}

			// 简历评价信息次数统计
			this.setCountresumeassess(resumeDao.countResumeAssessByWebuserGuid(this.getWebuserguid()));
			// 投递次数
			this.setCountsend(mapper.countSend(this.getWebuserguid()));
		}

		// 转义公司 岗位 部门
		if (this.getMycandidatesguid() != null) {
			// 是否被此用户已读
			UserContext uc = ContextFacade.getUserContext();
			CandidatesOperate operate = mapper.getCandidatesOperateByCandidatesGuidAndUserGuid(this.getMycandidatesguid(), uc.getUserId());
			if (operate != null) {
				this.setIsreadtype(true);
			} else {
				this.setIsreadtype(false);
			}

			if (this.getRecommendguid() != null) {
				Recommend recommend = mapper.getRecommendById(this.getRecommendguid());
				if (recommend != null) {
					recommend.convertOneCodeToName(departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, optionDao);
					this.setRecommendcompanyname(recommend.getRecommendcompanyname());
					this.setRecommenddeptname(recommend.getRecommenddeptname());
					this.setRecommendpostname(recommend.getRecommendpostname());
					this.setUserguid(recommend.getUserguid());
					this.setRecommendstate(recommend.getState());
					this.setAuditiontime(recommend.getAuditiontime());
					this.setTimeformat(DateUtil.formatDateMDHHmm(recommend.getModitimestamp()));
					// 取失效时间
					Calendar ca = Calendar.getInstance();
					ca.setTime(recommend.getModitimestamp());
					ca.add(Calendar.DATE, Constance.VALIDTIME);
					this.setMsg(DateUtil.formatDateMDHHmm(ca.getTime()));
				}
			}

			// 体检记录
			ExaminationRecord examination = personDao.getExaminationRecordByMyCandidatesGuidAndState(this.getMycandidatesguid());
			if (examination != null) {
				examination.convertOneCodeToName(thirdPartnerDao, optionDao);
				this.setThirdpartnerguidname(examination.getThirdpartnerguidname());
				this.setExaminationdate(examination.getExaminationdate());
			}

			// 面试记录
			AuditionRecord auditionRecord = auditionDao.getAuditionRecordByMycandidatesguidAndState(this.getMycandidatesguid());
			if (auditionRecord != null) {
				auditionRecord.convertOneCodeToName(systemDao, employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
				this.setAuditionrecordguid(auditionRecord.getAuditionrecordguid());
				this.setAuditiondate(auditionRecord.getAuditiondate());
				this.setMaininterviewerguid(auditionRecord.getMaininterviewerguid());
				this.setMaininterviewerguidname(auditionRecord.getMaininterviewerguidname());
				this.setAuditionaddress(auditionRecord.getAuditionaddress());
				this.setEmployeemobile(auditionRecord.getEmployeemobile());
				this.setEmployeeemail(auditionRecord.getEmployeeemail());
				this.setModiuser(auditionRecord.getModiuser());

				// 面试结果
				AuditionResult auditionResult = auditionDao.getAuditionResultByAuditionRecordId(auditionRecord.getAuditionrecordguid());
				if (auditionResult != null) {
					auditionResult.convertOneCodeToName(optionDao);
					this.setResulttype(auditionResult.getResulttype());
					this.setResultcontent(auditionResult.getResultcontent());
					this.setResultcontentname(auditionResult.getResultcontentname());
					this.setResulttypename(auditionResult.getResulttypename());
				}

			}

			// 推荐次数翻译
			this.setCountrecommend(mapper.countRecommendByMyCandidatesGuid(this.getMycandidatesguid()));

			// 转推荐次数
			this.setCountzrecommend(mapper.countZRecommendByMyCandidatesGuid(this.getMycandidatesguid()));

			// 认定次数翻译
			this.setCounthold(mapper.countHoldByMyCandidatesGuid(this.getMycandidatesguid()));

			// 安排面试次数翻译
			this.setCountauditionrecord(mapper.countAuditionrecordByMyCandidatesGuid(this.getMycandidatesguid()));

		}

		// 应聘状态
		if (this.getCandidatesstate() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CANDIDATESSTATE, this.getCandidatesstate());
			if (opt != null) {
				this.setCandidatesstatename(opt.getName());
			}
		}

		// 申请人
		if (this.getWebuserguid() != null) {
			WebUser webuser = webUserDao.getWebUserById(this.getWebuserguid());
			if (webuser != null) {
				this.setWebusername(webuser.getUsername());
			}
		}

	}

	/**
	 * 批量翻译
	 * 
	 * @param recruitpostMap
	 * @param workageMap
	 * @param cultureMap
	 * @param companyMap
	 * @param deptMap
	 * @param workplaceMap
	 * @param candidatesMap
	 * @param resumetypeMap
	 * @param recruitmentDao
	 * @param postMap
	 * @param webUserMap
	 * @param thirdpartnerMap
	 * @param mapper
	 * @param userMap
	 * @param personDao
	 * @param sexMap
	 * @param resumeDao
	 */
	public void convertManyCodeToName(Map<String, RecruitPost> recruitpostMap, Map<Integer, String> workageMap, Map<Integer, String> cultureMap, Map<String, Company> companyMap,
			Map<String, Department> deptMap, Map<String, String> workplaceMap, Map<Integer, String> candidatesMap, Map<Integer, String> resumetypeMap, RecruitmentDao recruitmentDao,
			Map<String, Post> postMap, Map<String, String> webUserMap, Map<String, String> thirdpartnerMap, MyCandidatesDao mapper, PersonDao personDao, Map<Integer, String> sexMap,
			ResumeDao resumeDao, AuditionDao auditionDao, OptionDao optionDao, DepartmentDao departmentDao, CompanyDao companyDao, PostDao postDao, SystemDao systemDao, EmployeeDao employeeDao,
			QuotaDao quotaDao, ThirdPartnerDao thirdPartnerDao) {

		// 来源
		if (this.getCandidatestype() != null) {
			String name = resumetypeMap.get(this.getCandidatestype());
			if (this.getEmployeeid() != null) {
				Employee employee = employeeDao.getEmployeeById(this.getEmployeeid());
				if (employee != null) {
					this.setCandidatestypename(name + "_" + employee.getName());
				} else {
					this.setCandidatestypename(name);
				}
			} else {
				this.setCandidatestypename(name);
			}
		}

		// 简历信息
		if (this.getWebuserguid() != null) {
			Resume resume = resumeDao.getResumeById(this.getWebuserguid());
			if (resume != null) {
				resume.convertOneCodeToName(optionDao, mapper);
				// 姓名 出生日期 年龄 学历 工作年限 人才库
				this.setName(resume.getName());
				this.setSexname(resume.getSexname());
				this.setBirthdayname(resume.getBirthdayname());
				this.setBirthday(resume.getBirthday());
				this.setCulturename(resume.getCulturename());
				this.setWorkagename(resume.getWorkagename());
				this.setMark(resume.getMark());
				this.setMobile(resume.getMobile());
				this.setEmail(resume.getEmail());
				this.setNativeplace(resume.getNativeplace());
				this.setSalary(resume.getSalary());
			}

			// 学校 专业
			List<EducationExperience> edulist = resumeDao.getAllEducationExperienceByWebuserId(this.getWebuserguid());
			if (!edulist.isEmpty() && edulist != null) {
				EducationExperience edu = edulist.get(0);
				this.setSchool(edu.getSchool());
				this.setSpecialty(edu.getSpecialty());
			}

			// 简历评价信息次数统计
			this.setCountresumeassess(resumeDao.countResumeAssessByWebuserGuid(this.getWebuserguid()));

			// 投递次数
			this.setCountsend(mapper.countSend(this.getWebuserguid()));
		}

		// 职位名称
		if (this.getRecruitpostguid() != null) {
			RecruitPost recruitpost = recruitmentDao.getRecruitPostByRecruitPostId(this.getRecruitpostguid());
			if (recruitpost != null) {
				this.setPostname(recruitpost.getPostname());
				this.setDeptid(recruitpost.getDeptid());

				// 公司名称
				Company company = companyMap.get(recruitpost.getCompanyid());
				if (company != null) {
					this.setCompanyname(company.getCompanyfullname());
				}

				// 部门名称
				Department dept = deptMap.get(recruitpost.getDeptid());
				if (dept != null) {
					this.setDeptname(dept.getDeptfullname());
				}

				// 工作地点
				String name = workplaceMap.get(recruitpost.getWorkplaceguid());
				if (name != null) {
					this.setWorkplacename(name);
				}

			} else {
				// 邮箱传过来的职位名称
				if (this.getRecruitpostname() != null) {
					this.setPostname(this.getRecruitpostname());
				}
			}
		} else {
			// 邮箱传过来的职位名称
			if (this.getRecruitpostname() != null) {
				this.setPostname(this.getRecruitpostname());
			}
		}

		if (this.getMycandidatesguid() != null) {
			// 应聘信息操作表
			UserContext uc = ContextFacade.getUserContext();
			CandidatesOperate operate = mapper.getCandidatesOperateByCandidatesGuidAndUserGuid(this.getMycandidatesguid(), uc.getUserId());
			if (operate != null) {
				this.setIsreadtype(true);
			} else {
				this.setIsreadtype(false);
			}

			// 翻译推荐信息表信息
			if (this.getRecommendguid() != null) {
				Recommend recommend = mapper.getRecommendById(this.getRecommendguid());
				recommend.convertOneCodeToName(departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, optionDao);
				this.setRecommendcompanyname(recommend.getRecommendcompanyname());
				this.setRecommenddeptname(recommend.getRecommenddeptname());
				this.setRecommendpostname(recommend.getRecommendpostname());
				this.setRecommendstate(recommend.getState());
				this.setAuditiontime(recommend.getAuditiontime());
				this.setUserguid(recommend.getUserguid());
				this.setTimeformat(DateUtil.formatDateMDHHmm(recommend.getModitimestamp()));
				// 取失效时间
				Calendar ca = Calendar.getInstance();
				ca.setTime(recommend.getModitimestamp());
				ca.add(Calendar.DATE, Constance.VALIDTIME);
				this.setMsg(DateUtil.formatDateMDHHmm(ca.getTime()));
			}

			// 体检机构翻译
			ExaminationRecord examination = personDao.getExaminationRecordByMyCandidatesGuidAndState(this.getMycandidatesguid());
			if (examination != null) {
				examination.convertOneCodeToName(thirdPartnerDao, optionDao);
				this.setThirdpartnerguidname(examination.getThirdpartnerguidname());
				this.setExaminationdate(examination.getExaminationdate());
			}

			// 面试记录
			AuditionRecord auditionRecord = auditionDao.getAuditionRecordByMycandidatesguidAndState(this.getMycandidatesguid());
			if (auditionRecord != null) {
				auditionRecord.convertOneCodeToName(systemDao, employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
				this.setAuditionrecordguid(auditionRecord.getAuditionrecordguid());
				this.setAuditiondate(auditionRecord.getAuditiondate());
				this.setMaininterviewerguid(auditionRecord.getAuditionrecordguid());
				this.setMaininterviewerguidname(auditionRecord.getMaininterviewerguidname());
				this.setAuditionaddress(auditionRecord.getAuditionaddress());
				this.setMaininterviewerguid(auditionRecord.getMaininterviewerguid());
				this.setEmployeemobile(auditionRecord.getEmployeemobile());
				this.setEmployeeemail(auditionRecord.getEmployeeemail());
				this.setModiuser(auditionRecord.getModiuser());

				// 面试结果
				AuditionResult auditionResult = auditionDao.getAuditionResultByAuditionRecordId(auditionRecord.getAuditionrecordguid());
				if (auditionResult != null) {
					auditionResult.convertOneCodeToName(optionDao);
					this.setResulttype(auditionResult.getResulttype());
					this.setResultcontent(auditionResult.getResultcontent());
					this.setResultcontentname(auditionResult.getResultcontentname());
					this.setResulttypename(auditionResult.getResulttypename());
				}
			}

			// 推荐次数翻译
			this.setCountrecommend(mapper.countRecommendByMyCandidatesGuid(this.getMycandidatesguid()));

			// 转推荐次数
			this.setCountzrecommend(mapper.countZRecommendByMyCandidatesGuid(this.getMycandidatesguid()));

			// 认定次数翻译
			this.setCounthold(mapper.countHoldByMyCandidatesGuid(this.getMycandidatesguid()));

			// 安排面试次数翻译
			this.setCountauditionrecord(mapper.countAuditionrecordByMyCandidatesGuid(this.getMycandidatesguid()));
		}

		// 应聘状态
		if (this.getCandidatesstate() != null) {
			String name = candidatesMap.get(this.getCandidatesstate());
			this.setCandidatesstatename(name);
		}

	}

	@Override
	public String toString() {
		return "MyCandidates [mycandidatesguid=" + mycandidatesguid + ", recruitpostguid=" + recruitpostguid + ", webuserguid=" + webuserguid + ", candidatesstate=" + candidatesstate
				+ ", candidatestype=" + candidatestype + ", progress=" + progress + ", candidatestime=" + candidatestime + ", matchuser=" + matchuser + ", matchtime=" + matchtime + ", matchmemo="
				+ matchmemo + ", holduser=" + holduser + ", holdtime=" + holdtime + ", holdmemo=" + holdmemo + ", recruitpostname=" + recruitpostname + ", culturename=" + culturename + ", name="
				+ name + ", sex=" + sex + ", mark=" + mark + ", birthday=" + birthday + ", birthdayname=" + birthdayname + ", workage=" + workage + ", workagename=" + workagename + ", webusername="
				+ webusername + ", recommendcompanyid=" + recommendcompanyid + ", recommenddeptid=" + recommenddeptid + ", recommendpostguid=" + recommendpostguid + ", auditiontime=" + auditiontime
				+ ", recommendcompanyname=" + recommendcompanyname + ", recommenddeptname=" + recommenddeptname + ", recommendpostname=" + recommendpostname + ", candidatesstatename="
				+ candidatesstatename + ", progressname=" + progressname + ", candidatestypename=" + candidatestypename + ", match=" + match + ", resulttype=" + resulttype + ", resultcontent="
				+ resultcontent + ", auditiondate=" + auditiondate + ", auditionaddress=" + auditionaddress + ", maininterviewerguid=" + maininterviewerguid + ", maininterviewerguidname="
				+ maininterviewerguidname + ", modiuser=" + modiuser + ", moditimestamp=" + moditimestamp + ", modimemo=" + modimemo + ", userguid=" + userguid + ", username=" + username
				+ ", thirdpartnerguidname=" + thirdpartnerguidname + ", resumetypename=" + resumetypename + ", postname=" + postname + ", companyname=" + companyname + ", deptid=" + deptid
				+ ", deptname=" + deptname + ", workplacename=" + workplacename + ", thirdpartnerguid=" + thirdpartnerguid + ", auditionrecordguid=" + auditionrecordguid + "]";
	}

}
