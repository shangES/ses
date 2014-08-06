package com.mk.mycandidates.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.OrgPathUtil;
import com.mk.audition.entity.AuditionRecord;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.person.entity.ExaminationRecord;
import com.mk.quota.dao.QuotaDao;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.User;

public class Recommend {
	private String recommendguid;
	private String mycandidatesguid;
	private String recommendcompanyid;
	private String recommenddeptid;
	private String recommendpostguid;
	//20140313 11:15分钟改的多加岗位名称字段
	private String recommendpostname;
	private String userguid;
	private Timestamp auditiontime;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;
	private Integer state;
	
	
	private List<AuditionRecord> recordResult;
	private List<ExaminationRecord> examinations;

	// 临时
	private String recommendcompanyname;
	private String recommenddeptname;
	private String username;
	private String arrayList;
	private Integer matchstate;
	private String usermobile;
	private String useremail;
	private String usernameAddress;
	private String employeeid;
	private String addresslistguid;

	// 是否发送邮件&短信
	private Integer isemail;
	private Integer ismsg;

	public List<ExaminationRecord> getExaminations() {
		return examinations;
	}

	public void setExaminations(List<ExaminationRecord> examinations) {
		this.examinations = examinations;
	}

	public List<AuditionRecord> getRecordResult() {
		return recordResult;
	}

	public void setRecordResult(List<AuditionRecord> recordResult) {
		this.recordResult = recordResult;
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

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getUsernameAddress() {
		return usernameAddress;
	}

	public void setUsernameAddress(String usernameAddress) {
		this.usernameAddress = usernameAddress;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUsermobile() {
		return usermobile;
	}

	public void setUsermobile(String usermobile) {
		this.usermobile = usermobile;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getArrayList() {
		return arrayList;
	}

	public void setArrayList(String arrayList) {
		this.arrayList = arrayList;
	}

	public Integer getMatchstate() {
		return matchstate;
	}

	public void setMatchstate(Integer matchstate) {
		this.matchstate = matchstate;
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

	public String getRecommendpostname() {
		return recommendpostname;
	}

	public void setRecommendpostname(String recommendpostname) {
		this.recommendpostname = recommendpostname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRecommendguid() {
		return recommendguid;
	}

	public void setRecommendguid(String recommendguid) {
		this.recommendguid = recommendguid;
	}

	public String getMycandidatesguid() {
		return mycandidatesguid;
	}

	public void setMycandidatesguid(String mycandidatesguid) {
		this.mycandidatesguid = mycandidatesguid;
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

	public String getUserguid() {
		return userguid;
	}

	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getAuditiontime() {
		return auditiontime;
	}

	public void setAuditiontime(Timestamp auditiontime) {
		this.auditiontime = auditiontime;
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

	public String getAddresslistguid() {
		return addresslistguid;
	}

	public void setAddresslistguid(String addresslistguid) {
		this.addresslistguid = addresslistguid;
	}

	@Override
	public String toString() {
		return "Recommend [recommendguid=" + recommendguid + ", mycandidatesguid=" + mycandidatesguid + ", recommendcompanyid=" + recommendcompanyid + ", recommenddeptid=" + recommenddeptid
				+ ", recommendpostguid=" + recommendpostguid + ", userguid=" + userguid + ", auditiontime=" + auditiontime + ", modiuser=" + modiuser + ", moditimestamp=" + moditimestamp
				+ ", modimemo=" + modimemo + "]";
	}

	/**
	 * 翻译
	 * 
	 * @param departmentDao
	 * @param companyDao
	 * @param postDao
	 */
	public void convertOneCodeToName(DepartmentDao departmentDao, CompanyDao companyDao, PostDao postDao, SystemDao systemDao, EmployeeDao employeeDao, QuotaDao quotaDao, OptionDao optionDao) {
		// 推荐公司
		if (this.getRecommendcompanyid() != null) {
			Company company = OrgPathUtil.getOneCompanyFullPath(this.getRecommendcompanyid(), companyDao);
			if (company != null)
				this.setRecommendcompanyname(company.getCompanyfullname());
		}

		// 推荐部门
		if (this.getRecommenddeptid() != null) {
			Department dept = OrgPathUtil.getOneDepartmentFullPath(this.getRecommenddeptid(), departmentDao);
			if (dept != null)
				this.setRecommenddeptname(dept.getDeptfullname());
		}

		// 推荐岗位
		if (this.getRecommendpostguid() != null) {
			Post post = postDao.getPostByPostId(this.getRecommendpostguid());
			if (post != null) {
				this.setRecommendpostname(post.getPostname());
			}
		}

		// 推荐人
		if (this.getUserguid() != null) {
			User user = systemDao.getUserByUserId(this.getUserguid());
			if (user != null) {
				user.convertOneCodeToName(employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
				this.setUsername(user.getUsername());
				this.setEmployeeid(user.getEmployeeid());
				if (user.getEmployeeid() != null) {
					Employee employee = employeeDao.getEmployeeById(user.getEmployeeid());
					if(employee!=null){
						this.setUsermobile(employee.getMobile());
						this.setUseremail(employee.getEmail());
					}
				}
				this.setUsernameAddress(user.getUsername() + "(" + this.getUsermobile() + "," + this.getUseremail() + ")");
			}

		}
	}

	/**
	 * 批量翻译
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param postMap
	 */
	public void convertManyCodeToName(Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, Post> postMap, Map<String, String> userMap) {
		// 推荐公司
		if (this.getRecommendcompanyid() != null) {
			Company company = companyMap.get(this.getRecommendcompanyid());
			if (company != null)
				this.setRecommendcompanyname(company.getCompanyfullname());
		}

		// 推荐部门
		if (this.getRecommenddeptid() != null) {
			Department dept = deptMap.get(this.getRecommenddeptid());
			if (dept != null)
				this.setRecommenddeptname(dept.getDeptfullname());
		}

		// 推荐岗位
		if (this.getRecommendpostguid() != null) {
			Post post = postMap.get(this.getRecommendpostguid());
			if (post != null)
				this.setRecommendpostname(post.getPostname());
		}

		// 推荐人
		if (this.getUserguid() != null) {
			String name = userMap.get(this.getUserguid());
			this.setUsername(name);
		}
	}

}
