package com.mk.resume.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.company.entity.Company;
import com.mk.department.entity.Department;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.util.DateUtil;
import com.mk.framework.grid.util.StringUtils;
import com.mk.fuzhu.service.NameConvertCodeService;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class Resume {
	private String webuserguid;
	private String keyword;
	private Integer mark;
	private String name;
	private Integer sex;
	private Date birthday;
	private String mobile;
	private String email;
	private String homeplace;
	private Integer workage;
	private Integer culture;
	private String photo;
	private String recommenduserguid;
	private Timestamp createtime;
	private Timestamp modtime;
	private String rmk;
	private String valuation;
	private String occupation;
	private String salary;
	private String situation;
	private String industry;
	private String nativeplace;

	// 临时
	private Integer birthdayname;
	private String sexname;
	private String culturename;
	private String workagename;
	private Integer candidatestype;
	private String resumefilename;
	private String resumefilePath;
	private String recruitpostguid;
	private String content;
	private boolean isrecommend;

	private String recommendcompanyid;
	private String recommenddeptid;
	private String recommendpostguid;

	// 亲属表
	private List<Relatives> relatives;

	// 数据导入
	private Integer checkstate;
	private String msg;
	
	
	public String getNativeplace() {
		return nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
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

	public Integer getCandidatestype() {
		return candidatestype;
	}

	public void setCandidatestype(Integer candidatestype) {
		this.candidatestype = candidatestype;
	}

	public List<Relatives> getRelatives() {
		return relatives;
	}

	public void setRelatives(List<Relatives> relatives) {
		this.relatives = relatives;
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

	public String getRecommendpostguid() {
		return recommendpostguid;
	}

	public void setRecommendpostguid(String recommendpostguid) {
		this.recommendpostguid = recommendpostguid;
	}

	public boolean isIsrecommend() {
		return isrecommend;
	}

	public void setIsrecommend(boolean isrecommend) {
		this.isrecommend = isrecommend;
	}

	public String getRecommenduserguid() {
		return recommenduserguid;
	}

	public void setRecommenduserguid(String recommenduserguid) {
		this.recommenduserguid = recommenduserguid;
	}

	public Integer getBirthdayname() {
		return birthdayname;
	}

	public void setBirthdayname(Integer birthdayname) {
		this.birthdayname = birthdayname;
	}

	public String getRecruitpostguid() {
		return recruitpostguid;
	}

	public void setRecruitpostguid(String recruitpostguid) {
		this.recruitpostguid = recruitpostguid;
	}

	public String getResumefilename() {
		return resumefilename;
	}

	public void setResumefilename(String resumefilename) {
		this.resumefilename = resumefilename;
	}

	public String getResumefilePath() {
		return resumefilePath;
	}

	public void setResumefilePath(String resumefilePath) {
		this.resumefilePath = resumefilePath;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public String getWorkagename() {
		return workagename;
	}

	public void setWorkagename(String workagename) {
		this.workagename = workagename;
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

	public String getHomeplace() {
		return homeplace;
	}

	public void setHomeplace(String homeplace) {
		this.homeplace = homeplace;
	}

	public Integer getWorkage() {
		return workage;
	}

	public void setWorkage(Integer workage) {
		this.workage = workage;
	}

	public Integer getCulture() {
		return culture;
	}

	public void setCulture(Integer culture) {
		this.culture = culture;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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

	public String getWebuserguid() {
		return webuserguid;
	}

	public void setWebuserguid(String webuserguid) {
		this.webuserguid = webuserguid;
	}

	public String getValuation() {
		return valuation;
	}

	public void setValuation(String valuation) {
		this.valuation = valuation;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/**
	 * 批量翻译
	 * 
	 * @param cultureMap
	 * @param sexMap
	 * @param workageMap
	 */
	public void convertManyCodeToName(Map<Integer, String> resumetypeMap, Map<Integer, String> cultureMap, Map<Integer, String> sexMap, Map<Integer, String> workageMap, MyCandidatesDao myCandidatesDao) {
		// 判断是否可推荐
		MyCandidates myCandidates = myCandidatesDao.getMyCandidatesByWebUserGuid(this.getWebuserguid());
		if (myCandidates != null) {
			this.setIsrecommend(true);
		} else {
			this.setIsrecommend(false);
		}

		// 计算年龄
		if (this.getBirthday() != null) {
			int age = DateUtil.getAge(this.getBirthday());
			this.setBirthdayname(age);
		}

		// 要求学历
		if (this.getCulture() != null) {
			String name = cultureMap.get(this.getCulture());
			this.setCulturename(name);
		}

		// 性别
		if (this.getSex() != null) {
			String name = sexMap.get(this.getSex());
			this.setSexname(name);
		}

		// 工作年限
		if (this.getWorkage() != null) {
			String name = workageMap.get(this.getWorkage());
			this.setWorkagename(name);
		}
	}

	/**
	 * code转名称
	 * 
	 * @param optionDao
	 */
	public void convertOneCodeToName(OptionDao optionDao, MyCandidatesDao myCandidatesDao) {
		// 判断是否可推荐
		MyCandidates myCandidates = myCandidatesDao.getMyCandidatesByWebUserGuid(this.getWebuserguid());
		if (myCandidates != null) {
			this.setIsrecommend(true);
		} else {
			this.setIsrecommend(false);
		}

		// 计算年龄
		if (this.getBirthday() != null) {
			int age = DateUtil.getAge(this.getBirthday());
			this.setBirthdayname(age);
		}

		// 最高学历
		if (this.getCulture() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CULTURE, this.getCulture());
			if (opt != null)
				this.setCulturename(opt.getName());
		}

		// 性别
		if (this.getSex() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.SEX, this.getSex());
			if (opt != null)
				this.setSexname(opt.getName());
		}

		// 工作年限
		if (this.getWorkage() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.WORKAGE, this.getWorkage());
			if (opt != null)
				this.setWorkagename(opt.getName());
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 导入
	 * 
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public Resume(NameConvertCodeService nameService, Map<String, Integer> columnTitle, HSSFRow row) {
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
		}

		// 出生日期
		String birthday = map.get(columnTitle.get("出生日期"));
		if (StringUtils.isNotEmpty(birthday)) {
			try {
				this.setBirthday(Date.valueOf(birthday));
			} catch (Exception e) {
				msg += "[出生日期：" + birthday + "]转换日期错误；";
			}
		}

		// 手机
		String mobile = map.get(columnTitle.get("手机"));
		if (StringUtils.isNotEmpty(mobile)) {
			this.setMobile(mobile);
		}

		// 电子邮箱
		String email = map.get(columnTitle.get("电子邮箱"));
		if (StringUtils.isNotEmpty(email)) {
			this.setEmail(email);
		} else
			msg += "[电子邮箱]不能为空；";

		// 工作年限
		String workagename = map.get(columnTitle.get("工作年限"));
		if (StringUtils.isNotEmpty(workagename)) {
			this.setWorkagename(workagename);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.WORKAGE, workagename);
			if (code == null) {
				msg += "[工作年限：" + workagename + "]不能转成系统内码；";
			} else
				this.setWorkage(code);
		}

		// 学历情况
		String culturename = map.get(columnTitle.get("学历情况"));
		if (StringUtils.isNotEmpty(culturename)) {
			this.setCulturename(culturename);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturename);
			if (code == null) {
				msg += "[学历情况：" + culturename + "]不能转成系统内码；";
			} else
				this.setCulture(code);
		}
		
		// 籍贯
		String nativeplace = map.get(columnTitle.get("籍贯"));
		if (StringUtils.isNotEmpty(nativeplace)) {
			this.setNativeplace(nativeplace);
		}

		// 现居住地
		String homeplace = map.get(columnTitle.get("现居住地"));
		if (StringUtils.isNotEmpty(homeplace)) {
			this.setHomeplace(homeplace);
		}

		// 备注
		String rmk = map.get(columnTitle.get("备注"));
		if (StringUtils.isNotEmpty(rmk)) {
			this.setRmk(rmk);
		}

		// 异常
		if (StringUtils.isNotEmpty(this.getMsg()))
			this.setCheckstate(Constance.VALID_YES);

	}

	/**
	 * 批量导入
	 * 
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public Resume(Map<String, Integer> cultureMap, Map<String, Integer> sexMap, Map<String, Integer> workageMap, NameConvertCodeService nameService, Map<String, Integer> columnTitle, HSSFRow row) {
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
		}

		// 出生日期
		String birthday = map.get(columnTitle.get("出生日期"));
		if (StringUtils.isNotEmpty(birthday)) {
			try {
				this.setBirthday(Date.valueOf(birthday));
			} catch (Exception e) {
				msg += "[出生日期：" + birthday + "]转换日期错误；";
			}
		}

		// 手机
		String mobile = map.get(columnTitle.get("手机"));
		if (StringUtils.isNotEmpty(mobile)) {
			this.setMobile(mobile);
		}

		// 电子邮箱
		String email = map.get(columnTitle.get("电子邮箱"));
		if (StringUtils.isNotEmpty(email)) {
			this.setEmail(email);
		}
		
		// 籍贯
		String nativeplace = map.get(columnTitle.get("籍贯"));
		if (StringUtils.isNotEmpty(nativeplace)) {
			this.setNativeplace(nativeplace);
		}

		// 现居住地
		String homeplace = map.get(columnTitle.get("现居住地"));
		if (StringUtils.isNotEmpty(homeplace)) {
			this.setHomeplace(homeplace);
		}

		// 学历情况
		String culturename = map.get(columnTitle.get("学历情况"));
		if (StringUtils.isNotEmpty(culturename)) {
			this.setCulturename(culturename);
			Integer code = cultureMap.get(culturename);
			if (code == null) {
				msg += "[学历情况：" + culturename + "]不能转成系统内码；";
			} else
				this.setCulture(code);
		}

		// 工作年限
		String workagename = map.get(columnTitle.get("工作年限"));
		if (StringUtils.isNotEmpty(workagename)) {
			this.setWorkagename(workagename);
			Integer code = workageMap.get(workagename);
			if (code == null) {
				msg += "[工作年限：" + workagename + "]不能转成系统内码；";
			} else
				this.setWorkage(code);
		}

		// 备注
		String rmk = map.get(columnTitle.get("备注"));
		if (StringUtils.isNotEmpty(rmk)) {
			this.setRmk(rmk);
		}

		// 异常
		if (StringUtils.isNotEmpty(this.getMsg()))
			this.setCheckstate(Constance.VALID_YES);
	}

	public Resume() {

	}

	@Override
	public String toString() {
		return "Resume [webuserguid=" + webuserguid + ", keyword=" + keyword + ", mark=" + mark + ", name=" + name + ", sex=" + sex + ", birthday=" + birthday + ", mobile=" + mobile + ", email="
				+ email + ", homeplace=" + homeplace + ", workage=" + workage + ", culture=" + culture + ", photo=" + photo + ", recommenduserguid=" + recommenduserguid + ", createtime=" + createtime
				+ ", modtime=" + modtime + ", rmk=" + rmk + ", valuation=" + valuation + ", occupation=" + occupation + ", salary=" + salary + ", situation=" + situation + ", industry=" + industry
				+ ", birthdayname=" + birthdayname + ", sexname=" + sexname + ", culturename=" + culturename + ", workagename=" + workagename + ", resumefilename=" + resumefilename
				+ ", resumefilePath=" + resumefilePath + ", recruitpostguid=" + recruitpostguid + "]";
	}

}
