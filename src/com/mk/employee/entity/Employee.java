package com.mk.employee.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.OrgPathUtil;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.company.entity.Job;
import com.mk.company.entity.Rank;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.department.service.DepartmentService;
import com.mk.department.service.PostService;
import com.mk.employee.dao.EmployeeDao;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.util.DateUtil;
import com.mk.framework.grid.util.StringUtils;
import com.mk.fuzhu.service.NameConvertCodeService;
import com.mk.quota.dao.QuotaDao;
import com.mk.quota.entity.Quota;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class Employee {

	private String employeeid;
	private String name;
	private Integer sex;
	private Date birthday;
	private String cardnumber;
	private Integer culture;
	private Integer nation;
	private String mobile;
	private String mobile2;
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
	private Date joinworkdate;
	private Date joingroupdate;
	private Integer workstate;
	private String jobnumber;
	private Integer secrecy;
	private Integer dorder;
	private Integer classification;
	private Integer employtype;
	private String checknumber;
	private Date joindate;
	private String officephone;
	private String email;
	private String social;
	private String shortphone;
	private String officeaddress;
	private Integer studymonth;
	private Date officialdateplan;
	private Date officialdate;
	private String officialmemo;
	private Date resignationdate;
	private Integer resignationreason;
	private String interfacecode;
	private String contactphone;
	private Integer contactrelationship;
	private String contactname;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
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

	// 臨時
	private String postionguid;
	private String companyid;
	private String companyname;
	private String deptid;
	private String deptname;
	private String postid;
	private String postname;
	private String jobid;
	private String jobname;
	private String rankid;
	private String rankname;
	private String quotaid;
	private String quotaname;
	private int state;
	private int isstaff;
	private String isstaffname;
	private Date startdate;
	private Date enddate;

	private String secrecyname;
	private String workstatename;
	private Integer birthdayname;
	private String sexname;
	private String culturename;
	private String nationname;
	private String bloodtypename;
	private String politicsname;
	private String marriedname;
	private String resignationreasonname;
	private String classificationname;
	private String employtypename;
	private String contactrelationshipname;
	private String domicilplacename;

	// 员工任职
	private Position position;

	private String userguid;

	// 数据导入
	private Integer checkstate;
	private String msg;

	public Employee() {

	}

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

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getUserguid() {
		return userguid;
	}

	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}

	/**
	 * 员工信息导入
	 * 
	 * @param 状态
	 *            | 公司名称| 部门名称| 职务| 岗位名称 |编制类别| 排序号|姓名 |性别| 出生日期 |身份证号码| 学历情况|
	 *            民族| 户口所在地| 手机 |家庭住址| 家庭电话| 血型 |户籍类型| 籍贯| 政治面貌| 婚姻状况| 私人邮箱|
	 *            紧急联系人| 与紧急联系关系| 紧急联系人电话| 参加工作时间 |加入集团时间| 工号 |员工信息保密| 员工类别
	 *            |用工性质| 考勤号| 入职时间 |聘任开始时间 |岗位聘任结束时间 |办公电话| 公司邮箱 |手机内网号码| 办公地址
	 *            |试用期| 计划转正时间| 转正时间| 转正备注| 离职时间 |离职原因| 备注
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public Employee(Map<String, Company> companyMap, Map<String, Department> deptMap, NameConvertCodeService nameService, Map<String, Integer> columnTitle, HSSFRow row,DepartmentService departmentService,PostService postService) {
		HSSFCell cell = null;
		msg = "";

		// 缓存列
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (int m = row.getFirstCellNum(); m <= row.getLastCellNum(); m++) {
			cell = row.getCell(m);
			if (cell != null) {
				map.put(m, Constance.getCellValue(cell).trim());
			}
		}

		// 录入时期
		UserContext uc = ContextFacade.getUserContext();
		this.setModiuser(uc.getUsername());

		// 状态
		String workstatename = map.get(columnTitle.get("状态"));
		if (StringUtils.isNotEmpty(workstatename)) {
			this.setWorkstatename(workstatename);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.WORKSTATE, workstatename);
			if (code == null) {
				msg += "[状态：" + workstatename + "]不能转成系统内码；";
			} else
				this.setWorkstate(code);
		} else
			msg += "[状态]不能为空；";

		// 公司名称
		String companyname = map.get(columnTitle.get("公司名称"));
		if (StringUtils.isNotEmpty(companyname)) {
			this.setCompanyname(companyname);
			Company company = companyMap.get(companyname);
			if (company != null)
				this.setCompanyid(company.getCompanyid());
			else
				msg += "[公司名称：" + companyname + "]不能转成系统内码；";

		} else
			msg += "[公司名称]不能为空；";

		// 部门名称
		String deptname = map.get(columnTitle.get("部门名称"));
		if (StringUtils.isNotEmpty(deptname)) {
			this.setDeptname(deptname);
			Department dept = deptMap.get(this.getCompanyid() + "/" + deptname);

			if (dept != null){
				this.setDeptid(dept.getDeptid());
			}else{
				Department wdept=departmentService.getDepartmentByName(this.getCompanyid(),deptname);
				this.setDeptid(wdept.getDeptid());
				deptMap.put(wdept.getCompanyid()+"/"+deptname, wdept);
			}
//			else
//				msg += "[部门名称：" + deptname + "]不能转成系统内码；";

		} else
			msg += "[部门名称]不能为空；";

		// 职务
		String jobname = map.get(columnTitle.get("职务"));
		if (StringUtils.isNotEmpty(jobname)) {
			this.setJobname(jobname);
			Job job = nameService.getJobByName(this.getCompanyid(), jobname);
			if (job != null)
				this.setJobid(job.getJobid());
			else
				msg += "[职务：" + jobname + "]不能转成系统内码；";

		}

		// 职级
		String rankname = map.get(columnTitle.get("职级"));
		if (StringUtils.isNotEmpty(rankname)) {
			this.setRankname(rankname);
			Rank rank = nameService.getRankByName(this.getCompanyid(), rankname);
			if (rank != null)
				this.setRankid(rank.getRankid());
			else
				msg += "[职级：" + rankname + "]不能转成系统内码；";

		}

		// 岗位名称
		String postname = map.get(columnTitle.get("岗位名称"));
		if (StringUtils.isNotEmpty(postname)) {
			this.setPostname(postname);
			Post post = nameService.getPostByName(this.getCompanyid(), this.getDeptid(), postname);
			if (post != null){
				this.setPostid(post.getPostid());
			}else{
				Post wpost=postService.getPostByPostName(this.getCompanyid(),this.getDeptid(),postname);
				this.setPostid(wpost.getPostid());
			}
//			else
//				msg += "[岗位名称：" + postname + "]不能转成系统内码；";
		}

		// 编制类别
		String quotaname = map.get(columnTitle.get("编制类别"));
		if (StringUtils.isNotEmpty(quotaname)) {
			this.setQuotaname(quotaname);
			Quota quota = nameService.getQuotaByName(this.getPostid(), quotaname);
			if (quota != null)
				this.setQuotaid(quota.getQuotaid());
			else
				msg += "[编制类别：" + quotaname + "]不能转成系统内码；";
		}

		// 排序号
		String dorder = map.get(columnTitle.get("排序号"));
		if (StringUtils.isNotEmpty(dorder)) {
			try {
				// 定的为number形后面会出现.0截掉
				if (dorder.indexOf(".") != -1) {
					dorder = dorder.substring(0, dorder.lastIndexOf("."));
				}
				this.setDorder(Integer.valueOf(dorder));
			} catch (Exception e) {
				msg += "[排序号：" + dorder + "]转换数字错误；";
			}
		} else
			msg += "[排序号]不能为空；";

		// 姓名
		String name = map.get(columnTitle.get("姓名"));
		if (StringUtils.isNotEmpty(name)) {
			this.setName(name);
		} else
			msg += "[姓名]不能为空；";

		// 性别
		String sexname = map.get(columnTitle.get("性别"));
		if (StringUtils.isNotEmpty(sexname)) {
			this.setSexname(sexname);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.SEX, sexname);
			if (code == null) {
				msg += "[性别：" + sexname + "]不能转成系统内码；";
			} else
				this.setSex(code);
		} else
			msg += "[性别]不能为空；";

		// 出生日期
		String birthday = map.get(columnTitle.get("出生日期"));
		if (StringUtils.isNotEmpty(birthday)) {
			try {
				this.setBirthday(Date.valueOf(birthday));
			} catch (Exception e) {
				msg += "[出生日期：" + birthday + "]转换日期错误；";
			}
		} else
			msg += "[出生日期]不能为空；";

		// 身份证号码
		String cardnumber = map.get(columnTitle.get("身份证号码"));
		if (StringUtils.isNotEmpty(cardnumber)) {
			this.setCardnumber(cardnumber);
		} else
			msg += "[身份证号码]不能为空；";

		// 学历情况
		String culturename = map.get(columnTitle.get("学历情况"));
		if (StringUtils.isNotEmpty(culturename)) {
			this.setCulturename(culturename);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturename);
			if (code == null) {
				msg += "[学历情况：" + culturename + "]不能转成系统内码；";
			} else
				this.setCulture(code);
		} else
			msg += "[学历情况]不能为空；";

		// 民族
		String nationname = map.get(columnTitle.get("民族"));
		if (StringUtils.isNotEmpty(nationname)) {
			this.setNationname(nationname);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.NATION, nationname);
			if (code == null) {
				msg += "[民族：" + nationname + "]不能转成系统内码；";
			} else
				this.setNation(code);
		}

		// 户口所在地
		String residenceplace = map.get(columnTitle.get("户口所在地"));
		if (StringUtils.isNotEmpty(residenceplace)) {
			this.setResidenceplace(residenceplace);
		} else
			msg += "[户口所在地]不能为空；";

		// 手机
		String mobile = map.get(columnTitle.get("手机"));
		if (StringUtils.isNotEmpty(mobile)) {
			this.setMobile(mobile);
		}

		// 通讯录手机
		String addressmobile = map.get(columnTitle.get("通讯录手机"));
		if (StringUtils.isNotEmpty(addressmobile)) {
			this.setAddressmobile(addressmobile);
		}

		// 家庭住址
		String homeplace = map.get(columnTitle.get("家庭住址"));
		if (StringUtils.isNotEmpty(homeplace)) {
			this.setHomeplace(homeplace);
		} else
			msg += "[家庭住址]不能为空；";

		// 法律文书送达地址
		String legaladdress = map.get(columnTitle.get("法律文书送达地址"));
		if (StringUtils.isNotEmpty(legaladdress)) {
			this.setLegaladdress(legaladdress);
		} else
			msg += "[法律文书送达地址]不能为空；";
		
		// 社保归属地
		String social = map.get(columnTitle.get("社保归属地"));
		if (StringUtils.isNotEmpty(social)) {
			this.setSocial(social);
		}
		
		// 家庭电话
		String homephone = map.get(columnTitle.get("家庭电话"));
		if (StringUtils.isNotEmpty(homephone)) {
			this.setHomephone(homephone);
		}

		// 血型
		String bloodtypename = map.get(columnTitle.get("血型"));
		if (StringUtils.isNotEmpty(bloodtypename)) {
			this.setBloodtypename(bloodtypename);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.BLOODTYPE, bloodtypename);
			if (code == null) {
				msg += "[血型：" + bloodtypename + "]不能转成系统内码；";
			} else
				this.setBloodtype(code);
		}

		// 户籍类型
		String domicilplacename = map.get(columnTitle.get("户籍类型"));
		if (StringUtils.isNotEmpty(domicilplacename)) {
			this.setDomicilplacename(domicilplacename);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.DOMICILPLACE, domicilplacename);
			if (code == null) {
				msg += "[户籍类型 ：" + bloodtypename + "]不能转成系统内码；";
			} else
				this.setDomicilplace(code);
		}

		// 籍贯
		String nativeplace = map.get(columnTitle.get("籍贯"));
		if (StringUtils.isNotEmpty(nativeplace)) {
			this.setNativeplace(nativeplace);
		}

		// 政治面貌
		String politicsname = map.get(columnTitle.get("政治面貌"));
		if (StringUtils.isNotEmpty(politicsname)) {
			this.setPoliticsname(politicsname);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.POLITICS, politicsname);
			if (code == null) {
				msg += "[政治面貌：" + politicsname + "]不能转成系统内码；";
			} else
				this.setPolitics(code);
		}

		// 婚姻状况
		String marriedname = map.get(columnTitle.get("婚姻状况"));
		if (StringUtils.isNotEmpty(marriedname)) {
			this.setMarriedname(marriedname);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.MARRIED, marriedname);
			if (code == null) {
				msg += "[婚姻状况：" + marriedname + "]不能转成系统内码；";
			} else
				this.setMarried(code);
		}

		// 私人邮箱
		String privateemail = map.get(columnTitle.get("私人邮箱"));
		if (StringUtils.isNotEmpty(privateemail)) {
			this.setPrivateemail(privateemail);
		}

		// 紧急联系人
		String contactname = map.get(columnTitle.get("紧急联系人"));
		if (StringUtils.isNotEmpty(contactname)) {
			this.setContactname(contactname);
		}

		// 与紧急联系关系
		String contactrelationshipname = map.get(columnTitle.get("与紧急联系关系"));
		if (StringUtils.isNotEmpty(contactrelationshipname)) {
			this.setContactrelationshipname(contactrelationshipname);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.MARRIED, contactrelationshipname);
			if (code == null) {
				msg += "[与紧急联系关系：" + contactrelationshipname + "]不能转成系统内码；";
			} else
				this.setContactrelationship(code);
		}

		// 紧急联系人电话
		String contactphone = map.get(columnTitle.get("紧急联系人电话"));
		if (StringUtils.isNotEmpty(contactphone)) {
			this.setContactphone(contactphone);
		}

		// 参加工作时间
		String joinworkdate = map.get(columnTitle.get("参加工作时间"));
		if (StringUtils.isNotEmpty(joinworkdate)) {
			try {
				this.setJoinworkdate(Date.valueOf(joinworkdate));
			} catch (Exception e) {
				msg += "[参加工作时间：" + joinworkdate + "]转换日期错误；";
			}
		}

		// 加入集团时间
		String joingroupdate = map.get(columnTitle.get("加入集团时间"));
		if (StringUtils.isNotEmpty(joingroupdate)) {
			try {
				this.setJoingroupdate(Date.valueOf(joingroupdate));
			} catch (Exception e) {
				msg += "[加入集团时间：" + joingroupdate + "]转换日期错误；";
			}
		}

		// 工号
		String jobnumber = map.get(columnTitle.get("工号"));
		if (StringUtils.isNotEmpty(jobnumber)) {
			// 工号唯一
			boolean hasjobnumber = nameService.checkEmployeeByJobnumber(jobnumber);
			if (hasjobnumber) {
				msg += "[工号：" + jobnumber + "]已经存在；";
			}
			this.setJobnumber(jobnumber);
		} else
			msg += "[工号]不能为空；";

		// 员工信息保密
		String secrecyname = map.get(columnTitle.get("员工信息保密"));
		if (StringUtils.isNotEmpty(secrecyname)) {
			this.setSecrecyname(secrecyname);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.SECRETLEVEL, secrecyname);
			if (code == null) {
				msg += "[员工信息保密：" + secrecyname + "]不能转成系统内码；";
			} else
				this.setSecrecy(code);
		} else
			msg += "[员工信息保密]不能为空；";

		// 员工类别
		String classificationname = map.get(columnTitle.get("员工类别"));
		if (StringUtils.isNotEmpty(classificationname)) {
			this.setClassificationname(classificationname);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.CLASSIFICATION, classificationname);
			if (code == null) {
				msg += "[员工类别：" + classificationname + "]不能转成系统内码；";
			} else
				this.setClassification(code);
		} else
			msg += "[员工类别]不能为空；";

		// 用工性质
		String employtypename = map.get(columnTitle.get("用工性质"));
		if (StringUtils.isNotEmpty(employtypename)) {
			this.setEmploytypename(employtypename);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.CLASSIFICATION, employtypename);
			if (code == null) {
				msg += "[用工性质：" + employtypename + "]不能转成系统内码；";
			} else
				this.setEmploytype(code);
		}

		// 考勤号
		String checknumber = map.get(columnTitle.get("工号"));
		if (StringUtils.isNotEmpty(checknumber)) {
			this.setChecknumber(checknumber);
		}

		// 入职时间
		String joindate = map.get(columnTitle.get("入职时间"));
		if (StringUtils.isNotEmpty(joindate)) {
			try {
				this.setJoindate(Date.valueOf(joindate));
			} catch (Exception e) {
				msg += "[入职时间：" + joindate + "]转换日期错误；";
			}
		} else
			msg += "[入职时间]不能为空；";

		// 聘任开始时间
		String startdate = map.get(columnTitle.get("聘任开始时间"));
		if (StringUtils.isNotEmpty(startdate)) {
			try {
				this.setStartdate(Date.valueOf(startdate));
			} catch (Exception e) {
				msg += "[聘任开始时间：" + startdate + "]转换日期错误；";
			}
		} else
			msg += "[聘任开始时间]不能为空；";

		// // 岗位聘任结束时间
		// String enddate = map.get(columnTitle.get("岗位聘任结束时间"));
		// if (StringUtils.isNotEmpty(enddate)) {
		// try {
		// this.setEnddate(Date.valueOf(enddate));
		// } catch (Exception e) {
		// msg += "[岗位聘任结束时间：" + enddate + "]转换日期错误；";
		// }
		// }

		// 办公电话
		String officephone = map.get(columnTitle.get("办公电话"));
		if (StringUtils.isNotEmpty(officephone)) {
			this.setOfficephone(officephone);
		}

		// 公司邮箱
		String email = map.get(columnTitle.get("公司邮箱"));
		if (StringUtils.isNotEmpty(email)) {
			this.setEmail(email);
		} else
			msg += "[公司邮箱]不能为空；";

		// 手机内网号码
		String shortphone = map.get(columnTitle.get("手机内网号码"));
		if (StringUtils.isNotEmpty(shortphone)) {
			this.setShortphone(shortphone);
		}

		// 办公地址
		String officeaddress = map.get(columnTitle.get("办公地址"));
		if (StringUtils.isNotEmpty(officeaddress)) {
			this.setOfficeaddress(officeaddress);
		}

		// 试用期
		String studymonth = map.get(columnTitle.get("试用期"));
		if (StringUtils.isNotEmpty(studymonth)) {
			try {
				// 定的为number形后面会出现.0截掉
				if (studymonth.indexOf(".") != -1) {
					studymonth = studymonth.substring(0, studymonth.lastIndexOf("."));
				}
				this.setStudymonth(Integer.valueOf(studymonth));
			} catch (Exception e) {
				msg += "[试用期：" + studymonth + "]转换数字错误；";
			}
		} else
			msg += "[试用期]不能为空；";

		// 计划转正时间
		String officialdateplan = map.get(columnTitle.get("计划转正时间"));
		if (StringUtils.isNotEmpty(officialdateplan)) {
			try {
				this.setOfficialdateplan(Date.valueOf(officialdateplan));
			} catch (Exception e) {
				msg += "[计划转正时间：" + officialdateplan + "]转换日期错误；";
			}
		}

		// 转正时间
		String officialdate = map.get(columnTitle.get("转正时间"));
		if (StringUtils.isNotEmpty(officialdate)) {
			try {
				this.setOfficialdate(Date.valueOf(officialdate));
			} catch (Exception e) {
				msg += "[转正时间：" + officialdate + "]转换日期错误；";
			}
		}

		// 转正备注
		String officialmemo = map.get(columnTitle.get("转正备注"));
		if (StringUtils.isNotEmpty(officialmemo)) {
			this.setOfficialmemo(officialmemo);
		}

		// 离职时间
		String resignationdate = map.get(columnTitle.get("离职时间"));
		if (StringUtils.isNotEmpty(resignationdate)) {
			try {
				this.setResignationdate(Date.valueOf(resignationdate));
			} catch (Exception e) {
				msg += "[离职时间：" + resignationdate + "]转换日期错误；";
			}
		}

		// 离职原因
		String resignationreasonname = map.get(columnTitle.get("离职原因"));
		if (StringUtils.isNotEmpty(resignationreasonname)) {
			this.setResignationreasonname(resignationreasonname);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.RESIGNATIONREASON, resignationreasonname);
			if (code == null) {
				msg += "[离职原因：" + resignationreasonname + "]不能转成系统内码；";
			} else
				this.setResignationreason(code);
		}

		// 备注
		String memo = map.get(columnTitle.get("备注"));
		if (StringUtils.isNotEmpty(memo)) {
			this.setMemo(memo);
		}

		// 异常
		if (StringUtils.isNotEmpty(this.getMsg()))
			this.setCheckstate(Constance.VALID_YES);
	}

	/**
	 * 批量数据导入
	 * 
	 * @param 状态
	 *            | 公司名称| 部门名称| 职务| 岗位名称 |编制类别| 姓名 |性别| 出生日期 |身份证号码| 学历情况| 民族|
	 *            户口所在地| 手机 |家庭住址| 家庭电话| 血型 |户籍类型| 籍贯| 政治面貌| 婚姻状况| 私人邮箱| 紧急联系人|
	 *            与紧急联系关系| 紧急联系人电话| 参加工作时间 |加入集团时间| 工号 |员工信息保密| 员工类别 |用工性质| 考勤号|
	 *            入职时间 |聘任开始时间 |岗位聘任结束时间 |办公电话| 公司邮箱 |手机内网号码| 办公地址 |试用期| 计划转正时间|
	 *            转正时间| 转正备注| 离职时间 |离职原因| 备注
	 * @param companyMap
	 * @param deptMap
	 * @param jobMap
	 * @param postMap
	 * @param quotaMap
	 * @param stateMap
	 * @param sexMap
	 * @param cultureMap
	 * @param nationMap
	 * @param bloodtypeMap
	 * @param politicsMap
	 * @param marriedMap
	 * @param classificationMap
	 * @param employtypeMap
	 * @param resignationreasonMap
	 * @param contactrelationshipMap
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public Employee(Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, String> jobMap, Map<String, String> rankMap, Map<String, String> postMap,
			Map<String, String> quotaMap, Map<String, Integer> stateMap, Map<String, Integer> sexMap, Map<String, Integer> cultureMap, Map<String, Integer> nationMap,
			Map<String, Integer> bloodtypeMap, Map<String, Integer> politicsMap, Map<String, Integer> marriedMap, Map<String, Integer> classificationMap, Map<String, Integer> employtypeMap,
			Map<String, Integer> resignationreasonMap, Map<String, Integer> contactrelationshipMap, Map<String, Integer> secrecyMap, Map<String, Integer> domicilplaceMap,
			NameConvertCodeService nameService, Map<String, Integer> columnTitle, HSSFRow row,DepartmentService departmentService,PostService postService) {
		HSSFCell cell = null;
		msg = "";

		// 缓存列
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (int m = row.getFirstCellNum(); m <= row.getLastCellNum(); m++) {
			cell = row.getCell(m);
			if (cell != null) {
				map.put(m, Constance.getCellValue(cell).trim());
			}
		}

		// 录入时期
		UserContext uc = ContextFacade.getUserContext();
		this.setModiuser(uc.getUsername());

		// 状态
		String workstatename = map.get(columnTitle.get("状态"));
		if (StringUtils.isNotEmpty(workstatename)) {
			this.setWorkstatename(workstatename);
			Integer code = stateMap.get(workstatename);
			if (code == null) {
				msg += "[状态：" + workstatename + "]不能转成系统内码；";
			} else
				this.setWorkstate(code);
		} else
			msg += "[状态]不能为空；";

		// 公司名称
		String companyname = map.get(columnTitle.get("公司名称"));
		if (StringUtils.isNotEmpty(companyname)) {
			this.setCompanyname(companyname);
			Company company = companyMap.get(companyname);
			if (company != null)
				this.setCompanyid(company.getCompanyid());
			else
				msg += "[公司名称：" + companyname + "]不能转成系统内码；";

		} else
			msg += "[公司名称]不能为空；";

		// 部门名称
		String deptname = map.get(columnTitle.get("部门名称"));
		if (StringUtils.isNotEmpty(deptname)) {
			this.setDeptname(deptname);
			Department dept = deptMap.get(this.getCompanyid() + "/" + deptname);
			if (dept != null){
				this.setDeptid(dept.getDeptid());
			}else{
				//msg += "[部门名称：" + deptname + "]不能转成系统内码；";
				Department wdept=departmentService.getDepartmentByName(this.getCompanyid(),deptname);
				this.setDeptid(wdept.getDeptid());
				deptMap.put(wdept.getCompanyid()+"/"+deptname, wdept);
			}
				//msg += "[部门名称：" + deptname + "]不能转成系统内码；";

		} else
			msg += "[部门名称]不能为空；";

		// 职务
		String jobname = map.get(columnTitle.get("职务"));
		if (StringUtils.isNotEmpty(jobname)) {
			this.setJobname(jobname);
			String jobid = jobMap.get(this.getCompanyid() + "/" + jobname);
			if (jobid != null)
				this.setJobid(jobid);
			else
				msg += "[职务：" + jobname + "]不能转成系统内码；";

		}

		// 职级
		String rankname = map.get(columnTitle.get("职级"));
		if (StringUtils.isNotEmpty(rankname)) {
			this.setRankname(rankname);
			String rankid = rankMap.get(this.getCompanyid() + "/" + rankname);
			if (rankid != null)
				this.setRankid(rankid);
			else
				msg += "[职级：" + rankname + "]不能转成系统内码；";

		}

		// 岗位名称
		String postname = map.get(columnTitle.get("岗位名称"));
		if (StringUtils.isNotEmpty(postname)) {
			this.setPostname(postname);
			String postid = postMap.get(this.getDeptid() + "/" + postname);
			if (postid != null){
				this.setPostid(postid);
			}else{
				//msg += "[岗位名称：" + postname + "]不能转成系统内码；";
				Post wpost=postService.getPostByPostName(this.getCompanyid(),this.getDeptid(),postname);
				this.setPostid(wpost.getPostid());
				postMap.put(this.getDeptid()+"/"+postname, wpost.getPostid());
			}
//			else
//				msg += "[岗位名称：" + postname + "]不能转成系统内码；";
		}

		// 编制类别
		String quotaname = map.get(columnTitle.get("编制类别"));
		if (StringUtils.isNotEmpty(quotaname)) {
			this.setQuotaname(quotaname);
			String quotaid = quotaMap.get(this.getPostid() + "/" + quotaname);
			if (quotaid != null)
				this.setQuotaid(quotaid);
			else
				msg += "[编制类别：" + quotaname + "]不能转成系统内码；";
		}

		// 排序号
		String dorder = map.get(columnTitle.get("排序号"));
		if (StringUtils.isNotEmpty(dorder)) {
			try {
				// 定的为number形后面会出现.0截掉
				if (dorder.indexOf(".") != -1) {
					dorder = dorder.substring(0, dorder.lastIndexOf("."));
				}
				this.setDorder(Integer.valueOf(dorder));
			} catch (Exception e) {
				msg += "[排序号：" + dorder + "]转换数字错误；";
			}
		} else
			msg += "[排序号]不能为空；";

		// 姓名
		String name = map.get(columnTitle.get("姓名"));
		if (StringUtils.isNotEmpty(name)) {
			this.setName(name);
		} else
			msg += "[姓名]不能为空；";

		// 性别
		String sexname = map.get(columnTitle.get("性别"));
		if (StringUtils.isNotEmpty(sexname)) {
			this.setSexname(sexname);
			Integer code = sexMap.get(sexname);
			if (code == null) {
				msg += "[性别：" + sexname + "]不能转成系统内码；";
			} else
				this.setSex(code);
		} else
			msg += "[性别]不能为空；";

		// 出生日期
		String birthday = map.get(columnTitle.get("出生日期"));
		if (StringUtils.isNotEmpty(birthday)) {
			try {
				this.setBirthday(Date.valueOf(birthday));
			} catch (Exception e) {
				msg += "[出生日期：" + birthday + "]转换日期错误；";
			}
		} else
			msg += "[出生日期]不能为空；";

		// 身份证号码
		String cardnumber = map.get(columnTitle.get("身份证号码"));
		if (StringUtils.isNotEmpty(cardnumber)) {
			this.setCardnumber(cardnumber);
		} else
			msg += "[身份证号码]不能为空；";

		// 学历情况
		String culturename = map.get(columnTitle.get("学历情况"));
		if (StringUtils.isNotEmpty(culturename)) {
			this.setCulturename(culturename);
			Integer code = cultureMap.get(culturename);
			if (code == null) {
				msg += "[学历情况：" + culturename + "]不能转成系统内码；";
			} else
				this.setCulture(code);
		} else
			msg += "[学历情况]不能为空；";

		// 民族
		String nationname = map.get(columnTitle.get("民族"));
		if (StringUtils.isNotEmpty(nationname)) {
			this.setNationname(nationname);
			Integer code = nationMap.get(nationname);
			if (code == null) {
				msg += "[民族：" + nationname + "]不能转成系统内码；";
			} else
				this.setNation(code);
		}

		// 户口所在地
		String residenceplace = map.get(columnTitle.get("户口所在地"));
		if (StringUtils.isNotEmpty(residenceplace)) {
			this.setResidenceplace(residenceplace);
		} else
			msg += "[户口所在地]不能为空；";

		// 手机
		String mobile = map.get(columnTitle.get("手机"));
		if (StringUtils.isNotEmpty(mobile)) {
			this.setMobile(mobile);
		}

		// 通讯录手机
		String addressmobile = map.get(columnTitle.get("通讯录手机"));
		if (StringUtils.isNotEmpty(addressmobile)) {
			this.setAddressmobile(addressmobile);
		}

		// 家庭住址
		String homeplace = map.get(columnTitle.get("家庭住址"));
		if (StringUtils.isNotEmpty(homeplace)) {
			this.setHomeplace(homeplace);
		} else
			msg += "[家庭住址]不能为空；";
		
		// 法律文书送达地址
		String legaladdress = map.get(columnTitle.get("法律文书送达地址"));
		if (StringUtils.isNotEmpty(legaladdress)) {
			this.setLegaladdress(legaladdress);
		} else
			msg += "[法律文书送达地址]不能为空；";
		
		// 社保归属地
		String social = map.get(columnTitle.get("社保归属地"));
		if (StringUtils.isNotEmpty(social)) {
			this.setSocial(social);
		}

		// 家庭电话
		String homephone = map.get(columnTitle.get("家庭电话"));
		if (StringUtils.isNotEmpty(homephone)) {
			this.setHomephone(homephone);
		}

		// 血型
		String bloodtypename = map.get(columnTitle.get("血型"));
		if (StringUtils.isNotEmpty(bloodtypename)) {
			this.setBloodtypename(bloodtypename);
			Integer code = bloodtypeMap.get(bloodtypename);
			if (code == null) {
				msg += "[血型：" + bloodtypename + "]不能转成系统内码；";
			} else
				this.setBloodtype(code);
		}

		// 户籍类型
		String domicilplacename = map.get(columnTitle.get("户籍类型"));
		if (StringUtils.isNotEmpty(domicilplacename)) {
			this.setDomicilplacename(domicilplacename);
			Integer code = domicilplaceMap.get(domicilplacename);
			if (code == null) {
				msg += "[户籍类型：" + domicilplacename + "]不能转成系统内码；";
			} else
				this.setDomicilplace(code);
		}

		// 籍贯
		String nativeplace = map.get(columnTitle.get("籍贯"));
		if (StringUtils.isNotEmpty(nativeplace)) {
			this.setNativeplace(nativeplace);
		}

		// 政治面貌
		String politicsname = map.get(columnTitle.get("政治面貌"));
		if (StringUtils.isNotEmpty(politicsname)) {
			this.setPoliticsname(politicsname);
			Integer code = politicsMap.get(politicsname);
			if (code == null) {
				msg += "[政治面貌：" + politicsname + "]不能转成系统内码；";
			} else
				this.setPolitics(code);
		}

		// 婚姻状况
		String marriedname = map.get(columnTitle.get("婚姻状况"));
		if (StringUtils.isNotEmpty(marriedname)) {
			this.setMarriedname(marriedname);
			Integer code = marriedMap.get(marriedname);
			if (code == null) {
				msg += "[婚姻状况：" + marriedname + "]不能转成系统内码；";
			} else
				this.setMarried(code);
		}

		// 私人邮箱
		String privateemail = map.get(columnTitle.get("私人邮箱"));
		if (StringUtils.isNotEmpty(privateemail)) {
			this.setPrivateemail(privateemail);
		}

		// 紧急联系人
		String contactname = map.get(columnTitle.get("紧急联系人"));
		if (StringUtils.isNotEmpty(contactname)) {
			this.setContactname(contactname);
		}

		// 与紧急联系关系
		String contactrelationshipname = map.get(columnTitle.get("与紧急联系关系"));
		if (StringUtils.isNotEmpty(contactrelationshipname)) {
			this.setContactrelationshipname(contactrelationshipname);
			Integer code = contactrelationshipMap.get(contactrelationshipname);
			if (code == null) {
				msg += "[与紧急联系关系：" + contactrelationshipname + "]不能转成系统内码；";
			} else
				this.setContactrelationship(code);
		}

		// 紧急联系人电话
		String contactphone = map.get(columnTitle.get("紧急联系人电话"));
		if (StringUtils.isNotEmpty(contactphone)) {
			this.setContactphone(contactphone);
		}

		// 参加工作时间
		String joinworkdate = map.get(columnTitle.get("参加工作时间"));
		if (StringUtils.isNotEmpty(joinworkdate)) {
			try {
				this.setJoinworkdate(Date.valueOf(joinworkdate));
			} catch (Exception e) {
				msg += "[参加工作时间：" + joinworkdate + "]转换日期错误；";
			}
		}

		// 加入集团时间
		String joingroupdate = map.get(columnTitle.get("加入集团时间"));
		if (StringUtils.isNotEmpty(joingroupdate)) {
			try {
				this.setJoingroupdate(Date.valueOf(joingroupdate));
			} catch (Exception e) {
				msg += "[加入集团时间：" + joingroupdate + "]转换日期错误；";
			}
		}

		// 工号
		String jobnumber = map.get(columnTitle.get("工号"));
		if (StringUtils.isNotEmpty(jobnumber)) {
			// 工号唯一
			boolean hasjobnumber = nameService.checkEmployeeByJobnumber(jobnumber);
			if (hasjobnumber) {
				msg += "[工号：" + jobnumber + "]已经存在；";
			}
			this.setJobnumber(jobnumber);
		} else
			msg += "[工号]不能为空；";

		// 员工信息保密
		String secrecyname = map.get(columnTitle.get("员工信息保密"));
		if (StringUtils.isNotEmpty(secrecyname)) {
			this.setSecrecyname(secrecyname);
			Integer code = secrecyMap.get(secrecyname);
			if (code == null) {
				msg += "[员工信息保密：" + secrecyname + "]不能转成系统内码；";
			} else
				this.setSecrecy(code);
		} else
			msg += "[员工信息保密]不能为空；";

		// 员工类别
		String classificationname = map.get(columnTitle.get("员工类别"));
		if (StringUtils.isNotEmpty(classificationname)) {
			this.setClassificationname(classificationname);
			Integer code = classificationMap.get(classificationname);
			if (code == null) {
				msg += "[员工类别：" + classificationname + "]不能转成系统内码；";
			} else
				this.setClassification(code);
		} else
			msg += "[员工类别]不能为空；";

		// 用工性质
		String employtypename = map.get(columnTitle.get("用工性质"));
		if (StringUtils.isNotEmpty(employtypename)) {
			this.setEmploytypename(employtypename);
			Integer code = employtypeMap.get(employtypename);
			if (code == null) {
				msg += "[用工性质：" + employtypename + "]不能转成系统内码；";
			} else
				this.setEmploytype(code);
		}

		// 考勤号
		String checknumber = map.get(columnTitle.get("工号"));
		if (StringUtils.isNotEmpty(checknumber)) {
			this.setChecknumber(checknumber);
		}

		// 入职时间
		String joindate = map.get(columnTitle.get("入职时间"));
		if (StringUtils.isNotEmpty(joindate)) {
			try {
				this.setJoindate(Date.valueOf(joindate));
			} catch (Exception e) {
				msg += "[入职时间：" + joindate + "]转换日期错误；";
			}
		} else
			msg += "[入职时间]不能为空；";

		// 聘任开始时间
		String startdate = map.get(columnTitle.get("聘任开始时间"));
		if (StringUtils.isNotEmpty(startdate)) {
			try {
				this.setStartdate(Date.valueOf(startdate));
			} catch (Exception e) {
				msg += "[聘任开始时间：" + startdate + "]转换日期错误；";
			}
		} else
			msg += "[聘任开始时间]不能为空；";

		// // 岗位聘任结束时间
		// String enddate = map.get(columnTitle.get("岗位聘任结束时间"));
		// if (StringUtils.isNotEmpty(enddate)) {
		// try {
		// this.setEnddate(Date.valueOf(enddate));
		// } catch (Exception e) {
		// msg += "[岗位聘任结束时间：" + enddate + "]转换日期错误；";
		// }
		// }

		// 办公电话
		String officephone = map.get(columnTitle.get("办公电话"));
		if (StringUtils.isNotEmpty(officephone)) {
			this.setOfficephone(officephone);
		}

		// 公司邮箱
		String email = map.get(columnTitle.get("公司邮箱"));
		if (StringUtils.isNotEmpty(email)) {
			this.setEmail(email);
		} else
			msg += "[公司邮箱]不能为空；";

		// 手机内网号码
		String shortphone = map.get(columnTitle.get("手机内网号码"));
		if (StringUtils.isNotEmpty(shortphone)) {
			this.setShortphone(shortphone);
		}

		// 办公地址
		String officeaddress = map.get(columnTitle.get("办公地址"));
		if (StringUtils.isNotEmpty(officeaddress)) {
			this.setOfficeaddress(officeaddress);
		}

		// 试用期
		String studymonth = map.get(columnTitle.get("试用期"));
		if (StringUtils.isNotEmpty(studymonth)) {
			try {
				// 定的为number形后面会出现.0截掉
				if (studymonth.indexOf(".") != -1) {
					studymonth = studymonth.substring(0, studymonth.lastIndexOf("."));
				}
				this.setStudymonth(Integer.valueOf(studymonth));
			} catch (Exception e) {
				msg += "[试用期：" + studymonth + "]转换数字错误；";
			}
		} else
			msg += "[试用期]不能为空；";

		// 计划转正时间
		String officialdateplan = map.get(columnTitle.get("计划转正时间"));
		if (StringUtils.isNotEmpty(officialdateplan)) {
			try {
				this.setOfficialdateplan(Date.valueOf(officialdateplan));
			} catch (Exception e) {
				msg += "[计划转正时间：" + officialdateplan + "]转换日期错误；";
			}
		}

		// 转正时间
		String officialdate = map.get(columnTitle.get("转正时间"));
		if (StringUtils.isNotEmpty(officialdate)) {
			try {
				this.setOfficialdate(Date.valueOf(officialdate));
			} catch (Exception e) {
				msg += "[转正时间：" + officialdate + "]转换日期错误；";
			}
		}

		// 转正备注
		String officialmemo = map.get(columnTitle.get("转正备注"));
		if (StringUtils.isNotEmpty(officialmemo)) {
			this.setOfficialmemo(officialmemo);
		}

		// 离职时间
		String resignationdate = map.get(columnTitle.get("离职时间"));
		if (StringUtils.isNotEmpty(resignationdate)) {
			try {
				this.setResignationdate(Date.valueOf(resignationdate));
			} catch (Exception e) {
				msg += "[离职时间：" + resignationdate + "]转换日期错误；";
			}
		}

		// 离职原因
		String resignationreasonname = map.get(columnTitle.get("离职原因"));
		if (StringUtils.isNotEmpty(resignationreasonname)) {
			this.setResignationreasonname(resignationreasonname);
			Integer code = resignationreasonMap.get(resignationreasonname);
			if (code == null) {
				msg += "[离职原因：" + resignationreasonname + "]不能转成系统内码；";
			} else
				this.setResignationreason(code);
		}

		// 备注
		String memo = map.get(columnTitle.get("备注"));
		if (StringUtils.isNotEmpty(memo)) {
			this.setMemo(memo);
		}

		// 异常
		if (StringUtils.isNotEmpty(this.getMsg()))
			this.setCheckstate(Constance.VALID_YES);
	}

	public String getDomicilplacename() {
		return domicilplacename;
	}

	public void setDomicilplacename(String domicilplacename) {
		this.domicilplacename = domicilplacename;
	}

	public String getAddressmobile() {
		return addressmobile;
	}

	public void setAddressmobile(String addressmobile) {
		this.addressmobile = addressmobile;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getPostionguid() {
		return postionguid;
	}

	public void setPostionguid(String postionguid) {
		this.postionguid = postionguid;
	}

	public String getIsstaffname() {
		return Constance.getIsStaffName(this.getIsstaff());
	}

	public void setIsstaffname(String isstaffname) {
		this.isstaffname = isstaffname;
	}

	public int getIsstaff() {
		return isstaff;
	}

	public void setIsstaff(int isstaff) {
		this.isstaff = isstaff;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getSocial() {
		return social;
	}

	public void setSocial(String social) {
		this.social = social;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	public String getJobid() {
		return jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
	}

	public String getQuotaid() {
		return quotaid;
	}

	public void setQuotaid(String quotaid) {
		this.quotaid = quotaid;
	}

	public Integer getCheckstate() {
		return checkstate;
	}

	public void setCheckstate(Integer checkstate) {
		this.checkstate = checkstate;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getSecrecy() {
		return secrecy;
	}

	public void setSecrecy(Integer secrecy) {
		this.secrecy = secrecy;
	}

	public String getSecrecyname() {
		return secrecyname;
	}

	public void setSecrecyname(String secrecyname) {
		this.secrecyname = secrecyname;
	}

	public String getContactrelationshipname() {
		return contactrelationshipname;
	}

	public void setContactrelationshipname(String contactrelationshipname) {
		this.contactrelationshipname = contactrelationshipname;
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

	public String getRankid() {
		return rankid;
	}

	public void setRankid(String rankid) {
		this.rankid = rankid;
	}

	public String getRankname() {
		return rankname;
	}

	public void setRankname(String rankname) {
		this.rankname = rankname;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public Integer getBirthdayname() {
		return birthdayname;
	}

	public void setBirthdayname(Integer birthdayname) {
		this.birthdayname = birthdayname;
	}

	public String getWorkstatename() {
		return workstatename;
	}

	public void setWorkstatename(String workstatename) {
		this.workstatename = workstatename;
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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getClassificationname() {
		return classificationname;
	}

	public void setClassificationname(String classificationname) {
		this.classificationname = classificationname;
	}

	public String getEmploytypename() {
		return employtypename;
	}

	public void setEmploytypename(String employtypename) {
		this.employtypename = employtypename;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getQuotaname() {
		return quotaname;
	}

	public void setQuotaname(String quotaname) {
		this.quotaname = quotaname;
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

	public void setBloodtypename(String bloodtypename) {
		this.bloodtypename = bloodtypename;
	}

	public String getBloodtypename() {
		return bloodtypename;
	}

	public void politicsname(String bloodtypename) {
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

	public String getResignationreasonname() {
		return resignationreasonname;
	}

	public void setResignationreasonname(String resignationreasonname) {
		this.resignationreasonname = resignationreasonname;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
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

	public Date getJoinworkdate() {
		return joinworkdate;
	}

	public void setJoinworkdate(Date joinworkdate) {
		this.joinworkdate = joinworkdate;
	}

	public Date getJoingroupdate() {
		return joingroupdate;
	}

	public void setJoingroupdate(Date joingroupdate) {
		this.joingroupdate = joingroupdate;
	}

	public Integer getWorkstate() {
		return workstate;
	}

	public void setWorkstate(Integer workstate) {
		this.workstate = workstate;
	}

	public String getJobnumber() {
		return jobnumber;
	}

	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}

	public Integer getDorder() {
		return dorder;
	}

	public void setDorder(Integer dorder) {
		this.dorder = dorder;
	}

	public Integer getClassification() {
		return classification;
	}

	public void setClassification(Integer classification) {
		this.classification = classification;
	}

	public Integer getEmploytype() {
		return employtype;
	}

	public void setEmploytype(Integer employtype) {
		this.employtype = employtype;
	}

	public String getChecknumber() {
		return checknumber;
	}

	public void setChecknumber(String checknumber) {
		this.checknumber = checknumber;
	}

	public Date getJoindate() {
		return joindate;
	}

	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}

	public String getOfficephone() {
		return officephone;
	}

	public void setOfficephone(String officephone) {
		this.officephone = officephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getShortphone() {
		return shortphone;
	}

	public void setShortphone(String shortphone) {
		this.shortphone = shortphone;
	}

	public String getOfficeaddress() {
		return officeaddress;
	}

	public void setOfficeaddress(String officeaddress) {
		this.officeaddress = officeaddress;
	}

	public Integer getStudymonth() {
		return studymonth;
	}

	public void setStudymonth(Integer studymonth) {
		this.studymonth = studymonth;
	}

	public Date getOfficialdateplan() {
		return officialdateplan;
	}

	public void setOfficialdateplan(Date officialdateplan) {
		this.officialdateplan = officialdateplan;
	}

	public Date getOfficialdate() {
		return officialdate;
	}

	public void setOfficialdate(Date officialdate) {
		this.officialdate = officialdate;
	}

	public String getOfficialmemo() {
		return officialmemo;
	}

	public void setOfficialmemo(String officialmemo) {
		this.officialmemo = officialmemo;
	}

	public Date getResignationdate() {
		return resignationdate;
	}

	public void setResignationdate(Date resignationdate) {
		this.resignationdate = resignationdate;
	}

	public Integer getResignationreason() {
		return resignationreason;
	}

	public void setResignationreason(Integer resignationreason) {
		this.resignationreason = resignationreason;
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

	@Override
	public String toString() {
		return "Employee [employeeid=" + employeeid + ", name=" + name + ", sex=" + sex + ", birthday=" + birthday + ", cardnumber=" + cardnumber + ", culture=" + culture + ", nation=" + nation
				+ ", mobile=" + mobile + ", residenceplace=" + residenceplace + ", homephone=" + homephone + ", homeplace=" + homeplace + ", bloodtype=" + bloodtype + ", domicilplace=" + domicilplace
				+ ", nativeplace=" + nativeplace + ", politics=" + politics + ", married=" + married + ", privateemail=" + privateemail + ", photo=" + photo + ", joinworkdate=" + joinworkdate
				+ ", joingroupdate=" + joingroupdate + ", workstate=" + workstate + ", jobnumber=" + jobnumber + ", secrecy=" + secrecy + ", dorder=" + dorder + ", classification=" + classification
				+ ", employtype=" + employtype + ", checknumber=" + checknumber + ", joindate=" + joindate + ", officephone=" + officephone + ", email=" + email + ", shortphone=" + shortphone
				+ ", officeaddress=" + officeaddress + ", studymonth=" + studymonth + ", officialdateplan=" + officialdateplan + ", officialdate=" + officialdate + ", officialmemo=" + officialmemo
				+ ", resignationdate=" + resignationdate + ", resignationreason=" + resignationreason + ", interfacecode=" + interfacecode + ", contactphone=" + contactphone
				+ ", contactrelationship=" + contactrelationship + ", contactname=" + contactname + ", memo=" + memo + ", modiuser=" + modiuser + ", moditimestamp=" + moditimestamp + ", modimemo="
				+ modimemo + ", companyname=" + companyname + ", deptname=" + deptname + ", postname=" + postname + ", jobname=" + jobname + ", quotaname=" + quotaname + ", secrecyname="
				+ secrecyname + ", workstatename=" + workstatename + ", birthdayname=" + birthdayname + ", sexname=" + sexname + ", culturename=" + culturename + ", nationname=" + nationname
				+ ", bloodtypename=" + bloodtypename + ", politicsname=" + politicsname + ", marriedname=" + marriedname + ", resignationreasonname=" + resignationreasonname + ", classificationname="
				+ classificationname + ", employtypename=" + employtypename + ", contactrelationshipname=" + contactrelationshipname + ", position=" + position + ", checkstate=" + checkstate
				+ ", msg=" + msg + "]";
	}

	/**
	 * code轉名稱
	 * 
	 * @param mapper
	 * 
	 * @param optionDao
	 */
	public void convertOneCodeToName(EmployeeDao mapper, CompanyDao companyDao, DepartmentDao departmentDao, PostDao postDao, OptionDao optionDao, QuotaDao quotaDao) {

		// 公司
		if (this.getCompanyid() != null) {
			Company company = OrgPathUtil.getOneCompanyFullPath(this.getCompanyid(), companyDao);
			if (company != null)
				this.setCompanyname(company.getCompanyfullname());
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

		// 职务
		if (this.getJobid() != null) {
			Job job = companyDao.getJobById(this.getJobid());
			if (job != null)
				this.setJobname(job.getJobname());
		}

		// 职级
		if (this.getRankid() != null) {
			Rank rank = companyDao.getRankById(this.getRankid());
			if (rank != null)
				this.setRankname(rank.getLevelname());
		}

		// 编制
		if (this.getQuotaid() != null) {
			Quota quota = quotaDao.getQuotaById(this.getQuotaid());
			if (quota != null) {
				quota.convertBudgettype(companyDao);
				this.setQuotaname(quota.getBudgettypename());
			}

		}

		// =========================================

		// 员工信息保密级别
		if (this.getSecrecy() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.SECRETLEVEL, this.getSecrecy());
			if (opt != null)
				this.setSecrecyname(opt.getName());
		}

		// 状态
		if (this.getWorkstate() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.WORKSTATE, this.getWorkstate());
			if (opt != null)
				this.setWorkstatename(opt.getName());
		}

		// 计算年龄
		if (this.getBirthday() != null) {
			int age = DateUtil.getAge(this.getBirthday());
			this.setBirthdayname(age);
		}

		// 性别
		if (this.getSex() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.SEX, this.getSex());
			if (opt != null)
				this.setSexname(opt.getName());
		}

		// 学历情况
		if (this.getCulture() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CULTURE, this.getCulture());
			if (opt != null)
				this.setCulturename(opt.getName());
		}
		// 民族
		if (this.getNation() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.NATION, this.getNation());
			if (opt != null)
				this.setNationname(opt.getName());
		}

		// 血型
		if (this.getBloodtype() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.BLOODTYPE, this.getBloodtype());
			if (opt != null)
				this.setBloodtypename(opt.getName());
		}

		// 政治面貌
		if (this.getPolitics() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.POLITICS, this.getPolitics());
			if (opt != null)
				this.setPoliticsname(opt.getName());
		}

		// 婚姻状况
		if (this.getMarried() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.MARRIED, this.getMarried());
			if (opt != null)
				this.setMarriedname(opt.getName());
		}

		// 员工类别
		if (this.getClassification() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CLASSIFICATION, this.getClassification());
			if (opt != null)
				this.setClassificationname(opt.getName());
		}

		// 用工性质
		if (this.getEmploytype() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.EMPLOYTYPE, this.getEmploytype());
			if (opt != null)
				this.setEmploytypename(opt.getName());
		}

		// 离职原因
		if (this.getResignationreason() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RESIGNATIONREASON, this.getResignationreason());
			if (opt != null)
				this.setResignationreasonname(opt.getName());
		}

		// 紧急联系人关系
		if (this.getContactrelationship() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RELATIONSHIP, this.getContactrelationship());
			if (opt != null)
				this.setContactrelationshipname(opt.getName());
		}

		// 户籍类型
		if (this.getDomicilplace() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.DOMICILPLACE, this.getDomicilplace());
			if (opt != null)
				this.setDomicilplacename(opt.getName());
		}

	}

	/**
	 * 批量code转名称
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param jobMap
	 * @param postMap
	 * @param quotaMap
	 * @param stateMap
	 * @param sexMap
	 * @param cultureMap
	 * @param nationMap
	 * @param bloodtypeMap
	 * @param politicsMap
	 * @param marriedMap
	 * @param classificationMap
	 * @param employtypeMap
	 * @param resignationreasonMap
	 * @param relationshipMap
	 * @param postdate_s
	 * @param postdate_e
	 */
	public void convertManyCodeToName(EmployeeDao mapper, Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, String> jobMap, Map<String, String> rankMap,
			Map<String, Post> postMap, Map<String, String> quotaMap, Map<Integer, String> stateMap, Map<Integer, String> sexMap, Map<Integer, String> cultureMap, Map<Integer, String> nationMap,
			Map<Integer, String> bloodtypeMap, Map<Integer, String> politicsMap, Map<Integer, String> marriedMap, Map<Integer, String> classificationMap, Map<Integer, String> employtypeMap,
			Map<Integer, String> resignationreasonMap, Map<Integer, String> contactrelationshipMap, Map<Integer, String> domicilplaceMap) {

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

		// 职务
		if (this.getJobid() != null) {
			String name = jobMap.get(this.getJobid());
			this.setJobname(name);
		}

		// 职级
		if (this.getRankid() != null) {
			String name = rankMap.get(this.getRankid());
			this.setRankname(name);
		}

		// 编制
		if (this.getQuotaid() != null) {
			String name = jobMap.get(this.getJobid());
			this.setQuotaname(name);

		}

		// ======================================

		// 状态
		if (this.getWorkstate() != null) {
			String name = stateMap.get(this.getWorkstate());
			this.setWorkstatename(name);
		}

		// 计算年龄
		if (this.getBirthday() != null) {
			int age = DateUtil.getAge(this.getBirthday());
			this.setBirthdayname(age);
		}

		// 性别
		if (this.getSex() != null) {
			String name = sexMap.get(this.getSex());
			this.setSexname(name);
		}

		// 学历情况
		if (this.getCulture() != null) {
			String name = cultureMap.get(this.getCulture());
			this.setCulturename(name);
		}
		// 民族
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

		// 婚姻状况
		if (this.getMarried() != null) {
			String name = marriedMap.get(this.getMarried());
			this.setMarriedname(name);
		}

		// 员工类别
		if (this.getClassification() != null) {
			String name = classificationMap.get(this.getClassification());
			this.setClassificationname(name);
		}

		// 用工性质
		if (this.getEmploytype() != null) {
			String name = employtypeMap.get(this.getEmploytype());
			this.setEmploytypename(name);
		}

		// 离职原因
		if (this.getResignationreason() != null) {
			String name = resignationreasonMap.get(this.getResignationreason());
			this.setResignationreasonname(name);
		}

		// 紧急联系人关系
		if (this.getContactrelationship() != null) {
			String name = contactrelationshipMap.get(this.getContactrelationship());
			this.setContactrelationshipname(name);
		}

		// 户籍类型
		if (this.getDomicilplace() != null) {
			String name = domicilplaceMap.get(this.getDomicilplace());
			this.setDomicilplacename(name);
		}

	}

	// ===============================================
	/**
	 * 确定员工信息
	 * 
	 * @param mapper
	 * @param companyDao
	 * @param departmentDao
	 * @param postDao
	 * @param optionDao
	 */
	public void convertMakOneCodeToName(EmployeeDao mapper, CompanyDao companyDao, DepartmentDao departmentDao, PostDao postDao, QuotaDao quotaDao, OptionDao optionDao, String postionguid) {

		// 确定员工任职，如果传进来postionguid直接定位
		// 否则截日期确定当前时间的任职
		// 员工任职
		Position position = null;
		if (StringUtils.isNotEmpty(postionguid)) {
			position = mapper.getPositionById(postionguid);

		} else {
			List<Position> positions = mapper.getPositionByEmployeeIdAndIsstaff(this.getEmployeeid());
			if (!positions.isEmpty()) {
				position = positions.get(0);
			}
		}
		if (position != null) {
			position.convertOneCodeToName(companyDao, departmentDao, postDao, quotaDao, optionDao);
			this.setPosition(position);

			// 公司部门岗位
			this.setCompanyname(position.getCompanyname());
			this.setDeptname(position.getDeptname());
			this.setPostname(position.getPostname());

			this.setJobname(position.getJobname());
			this.setQuotaname(position.getQuotaname());
			this.setRankname(position.getRankname());
			this.setIsstaff(position.getIsstaff());
			this.setIsstaffname(position.getIsstaffname());
			this.setStartdate(position.getStartdate());
			this.setEnddate(position.getEnddate());
		}

		// =========================================

		// 员工信息保密级别
		if (this.getSecrecy() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.SECRETLEVEL, this.getSecrecy());
			if (opt != null)
				this.setSecrecyname(opt.getName());
		}

		// 状态
		if (this.getWorkstate() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.WORKSTATE, this.getWorkstate());
			if (opt != null)
				this.setWorkstatename(opt.getName());
		}

		// 计算年龄
		if (this.getBirthday() != null) {
			int age = DateUtil.getAge(this.getBirthday());
			this.setBirthdayname(age);
		}

		// 性别
		if (this.getSex() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.SEX, this.getSex());
			if (opt != null)
				this.setSexname(opt.getName());
		}

		// 学历情况
		if (this.getCulture() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CULTURE, this.getCulture());
			if (opt != null)
				this.setCulturename(opt.getName());
		}
		// 民族
		if (this.getNation() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.NATION, this.getNation());
			if (opt != null)
				this.setNationname(opt.getName());
		}

		// 血型
		if (this.getBloodtype() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.BLOODTYPE, this.getBloodtype());
			if (opt != null)
				this.setBloodtypename(opt.getName());
		}

		// 政治面貌
		if (this.getPolitics() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.POLITICS, this.getPolitics());
			if (opt != null)
				this.setPoliticsname(opt.getName());
		}

		// 婚姻状况
		if (this.getMarried() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.MARRIED, this.getMarried());
			if (opt != null)
				this.setMarriedname(opt.getName());
		}

		// 员工类别
		if (this.getClassification() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CLASSIFICATION, this.getClassification());
			if (opt != null)
				this.setClassificationname(opt.getName());
		}

		// 用工性质
		if (this.getEmploytype() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.EMPLOYTYPE, this.getEmploytype());
			if (opt != null)
				this.setEmploytypename(opt.getName());
		}

		// 离职原因
		if (this.getResignationreason() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RESIGNATIONREASON, this.getResignationreason());
			if (opt != null)
				this.setResignationreasonname(opt.getName());
		}

		// 紧急联系人关系
		if (this.getContactrelationship() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RELATIONSHIP, this.getContactrelationship());
			if (opt != null)
				this.setContactrelationshipname(opt.getName());
		}

		// 户籍类型
		if (this.getDomicilplace() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.DOMICILPLACE, this.getDomicilplace());
			if (opt != null)
				this.setDomicilplacename(opt.getName());
		}

	}
}
