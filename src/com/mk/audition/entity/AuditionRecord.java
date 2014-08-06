package com.mk.audition.entity;

import java.sql.Timestamp;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.company.dao.CompanyDao;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.quota.dao.QuotaDao;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.User;

public class AuditionRecord {
	private String auditionrecordguid;
	private String mycandidatesguid;
	private String maininterviewerguid;
	private Timestamp auditiondate;
	private String auditionaddress;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;
	private Integer state;
	private String recommendguid;

	// 临时属性
	private String maininterviewerguidname;
	private Integer auditionstate;

	// 临时
	private Integer isrelease;
	private Integer resulttype;
	private String resulttypename;
	private Integer resultcontent;
	private String resultcontentname;
	private String hrresultcontent;
	private String array;
	private String userguid;

	// 是否发送邮件&短信
	private Integer isemail;
	private Integer ismsg;

	public String getRecommendguid() {
		return recommendguid;
	}

	public void setRecommendguid(String recommendguid) {
		this.recommendguid = recommendguid;
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

	private String employeemobile;
	private String employeeemail;

	public Integer getResultcontent() {
		return resultcontent;
	}

	public String getResultcontentname() {
		return resultcontentname;
	}

	public void setResultcontentname(String resultcontentname) {
		this.resultcontentname = resultcontentname;
	}

	public void setResultcontent(Integer resultcontent) {
		this.resultcontent = resultcontent;
	}

	public String getHrresultcontent() {
		return hrresultcontent;
	}

	public void setHrresultcontent(String hrresultcontent) {
		this.hrresultcontent = hrresultcontent;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUserguid() {
		return userguid;
	}

	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}

	public String getArray() {
		return array;
	}

	public void setArray(String array) {
		this.array = array;
	}

	public Integer getIsrelease() {
		return isrelease;
	}

	public void setIsrelease(Integer isrelease) {
		this.isrelease = isrelease;
	}

	public Integer getResulttype() {
		return resulttype;
	}

	public void setResulttype(Integer resulttype) {
		this.resulttype = resulttype;
	}

	public String getResulttypename() {
		return resulttypename;
	}

	public void setResulttypename(String resulttypename) {
		this.resulttypename = resulttypename;
	}

	public Integer getAuditionstate() {
		return auditionstate;
	}

	public void setAuditionstate(Integer auditionstate) {
		this.auditionstate = auditionstate;
	}

	public String getMaininterviewerguidname() {
		return maininterviewerguidname;
	}

	public void setMaininterviewerguidname(String maininterviewerguidname) {
		this.maininterviewerguidname = maininterviewerguidname;
	}

	public String getAuditionrecordguid() {
		return auditionrecordguid;
	}

	public void setAuditionrecordguid(String auditionrecordguid) {
		this.auditionrecordguid = auditionrecordguid;
	}

	public String getMycandidatesguid() {
		return mycandidatesguid;
	}

	public void setMycandidatesguid(String mycandidatesguid) {
		this.mycandidatesguid = mycandidatesguid;
	}

	public String getMaininterviewerguid() {
		return maininterviewerguid;
	}

	public void setMaininterviewerguid(String maininterviewerguid) {
		this.maininterviewerguid = maininterviewerguid;
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

	/**
	 * code转名称
	 * 
	 * @param optionDao
	 */
	public void convertOneCodeToName(SystemDao systemDao, EmployeeDao employeeDao, CompanyDao companyDao, DepartmentDao departmentDao, PostDao postDao, QuotaDao quotaDao, OptionDao optionDao,
			Map<String, String> roleMap) {
		// 面试结果
		if (this.getResulttype() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RESULTTYPE, this.getResulttype());
			if (opt != null)
				this.setResulttypename(opt.getName());
		}

		// 面试官
		if (this.getMaininterviewerguid() != null) {
			User user = systemDao.getUserByUserId(this.getMaininterviewerguid());
			Employee employee = employeeDao.getEmployeeById(user.getEmployeeid());
			// if (user != null)
			// user.convertOneCodeToName(employeeDao, companyDao, departmentDao,
			// postDao, quotaDao, optionDao, roleMap);
			// this.setMaininterviewerguidname(user.getUsername());
			this.setMaininterviewerguidname(employee.getName());
			this.setEmployeemobile(employee.getMobile());
			this.setEmployeeemail(employee.getEmail());
		}

		// 面试评语
		if (this.getResultcontent() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RESULTCONTENT, this.getResultcontent());
			if (opt != null)
				this.setResultcontentname(opt.getName());
		}
	}

	@Override
	public String toString() {
		return "AuditionRecord [auditionrecordguid=" + auditionrecordguid + ", mycandidatesguid=" + mycandidatesguid + ", maininterviewerguid=" + maininterviewerguid + ", auditiondate="
				+ auditiondate + ", auditionaddress=" + auditionaddress + ", modiuser=" + modiuser + ", moditimestamp=" + moditimestamp + ", modimemo=" + modimemo + ", maininterviewerguidname="
				+ maininterviewerguidname + ", auditionstate=" + auditionstate + ", isrelease=" + isrelease + ", resulttype=" + resulttype + ", resulttypename=" + resulttypename + ", resultcontent="
				+ resultcontent + ", array=" + array + ", userguid=" + userguid + "]";
	}

}
