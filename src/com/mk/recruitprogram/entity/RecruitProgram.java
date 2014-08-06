package com.mk.recruitprogram.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.OrgPathUtil;
import com.mk.bpmn.entity.AuditHistory;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.company.entity.Rank;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.util.StringUtils;
import com.mk.fuzhu.service.NameConvertCodeService;
import com.mk.quota.dao.QuotaDao;
import com.mk.quota.entity.Quota;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class RecruitProgram {

	private String recruitprogramguid;
	private String recruitprogramauditguid;
	private String recruitprogramcode;
	private String quotaid;
	private String companyid;
	private String deptid;
	private String postid;
	private String rankid;
	private Date applydate;
	private Date hillockdate;
	private Integer postnum;
	private Integer state;
	private String processinstanceid;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;
	private String interfacecode;

	// 临时属性
	private String companyname;
	private String quotaname;
	private String deptname;
	private String postname;
	private String rankname;
	private String quotacode;
	private Date validtime;
	private Integer employednumber;
	private int vacancynumber;
	private Integer operatenum;
	private Integer budgetnumber;
	private Integer quotanumber;
	private Integer interviewnumber;
	private String statename;
	private String pdeptid;
	private String pdeptname;
	private String timeformat;
	private int remainnum;
	private boolean ischeck;

	// 导入需要的临时属性
	private Integer checkstate;
	private String msg;

	// 流程
	private String assignee;
	private String taskid;
	private String taskcreatetime;
	private boolean runprocess;
	private Map<String, Object> variables;
	private List<AuditHistory> audithistorys;

	public Integer getQuotanumber() {
		return quotanumber;
	}

	public void setQuotanumber(Integer quotanumber) {
		this.quotanumber = quotanumber;
	}

	public Integer getInterviewnumber() {
		return interviewnumber;
	}

	public void setInterviewnumber(Integer interviewnumber) {
		this.interviewnumber = interviewnumber;
	}

	public boolean isIscheck() {
		return ischeck;
	}

	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}

	public int getRemainnum() {
		return remainnum;
	}

	public void setRemainnum(int remainnum) {
		this.remainnum = remainnum;
	}

	public String getTimeformat() {
		return timeformat;
	}

	public void setTimeformat(String timeformat) {
		this.timeformat = timeformat;
	}

	public String getPdeptid() {
		return pdeptid;
	}

	public void setPdeptid(String pdeptid) {
		this.pdeptid = pdeptid;
	}

	public String getPdeptname() {
		return pdeptname;
	}

	public void setPdeptname(String pdeptname) {
		this.pdeptname = pdeptname;
	}

	public String getRecruitprogramauditguid() {
		return recruitprogramauditguid;
	}

	public void setRecruitprogramauditguid(String recruitprogramauditguid) {
		this.recruitprogramauditguid = recruitprogramauditguid;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public List<AuditHistory> getAudithistorys() {
		return audithistorys;
	}

	public void setAudithistorys(List<AuditHistory> audithistorys) {
		this.audithistorys = audithistorys;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getTaskcreatetime() {
		return taskcreatetime;
	}

	public void setTaskcreatetime(String taskcreatetime) {
		this.taskcreatetime = taskcreatetime;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public boolean isRunprocess() {
		return runprocess;
	}

	public void setRunprocess(boolean runprocess) {
		this.runprocess = runprocess;
	}

	public Integer getBudgetnumber() {
		return budgetnumber;
	}

	public void setBudgetnumber(Integer budgetnumber) {
		this.budgetnumber = budgetnumber;
	}

	public Integer getEmployednumber() {
		return employednumber;
	}

	public void setEmployednumber(Integer employednumber) {
		this.employednumber = employednumber;
	}

	public int getVacancynumber() {
		return vacancynumber;
	}

	public void setVacancynumber(int vacancynumber) {
		this.vacancynumber = vacancynumber;
	}

	public Integer getOperatenum() {
		return operatenum;
	}

	public void setOperatenum(Integer operatenum) {
		this.operatenum = operatenum;
	}

	public String getRecruitprogramcode() {
		return recruitprogramcode;
	}

	public void setRecruitprogramcode(String recruitprogramcode) {
		this.recruitprogramcode = recruitprogramcode;
	}

	public Date getValidtime() {
		return validtime;
	}

	public void setValidtime(Date validtime) {
		this.validtime = validtime;
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

	public String getQuotacode() {
		return quotacode;
	}

	public void setQuotacode(String quotacode) {
		this.quotacode = quotacode;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getQuotaname() {
		return quotaname;
	}

	public void setQuotaname(String quotaname) {
		this.quotaname = quotaname;
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

	public String getRankname() {
		return rankname;
	}

	public void setRankname(String rankname) {
		this.rankname = rankname;
	}

	public String getRecruitprogramguid() {
		return recruitprogramguid;
	}

	public void setRecruitprogramguid(String recruitprogramguid) {
		this.recruitprogramguid = recruitprogramguid;
	}

	public String getQuotaid() {
		return quotaid;
	}

	public void setQuotaid(String quotaid) {
		this.quotaid = quotaid;
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

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	public String getRankid() {
		return rankid;
	}

	public void setRankid(String rankid) {
		this.rankid = rankid;
	}

	public Date getApplydate() {
		return applydate;
	}

	public void setApplydate(Date applydate) {
		this.applydate = applydate;
	}

	public Date getHillockdate() {
		return hillockdate;
	}

	public void setHillockdate(Date hillockdate) {
		this.hillockdate = hillockdate;
	}

	public Integer getPostnum() {
		return postnum;
	}

	public void setPostnum(Integer postnum) {
		this.postnum = postnum;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getProcessinstanceid() {
		return processinstanceid;
	}

	public void setProcessinstanceid(String processinstanceid) {
		this.processinstanceid = processinstanceid;
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

	public RecruitProgram() {

	}

	public String getInterfacecode() {
		return interfacecode;
	}

	public void setInterfacecode(String interfacecode) {
		this.interfacecode = interfacecode;
	}

	/**
	 * 数据导入
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public RecruitProgram(Map<String, Company> companyMap, Map<String, Department> deptMap, NameConvertCodeService nameService, Map<String, Integer> columnTitle, HSSFRow row) {
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
		this.setState(Constance.State_Tobe);

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
			if (dept != null)
				this.setDeptid(dept.getDeptid());
			else
				msg += "[部门名称：" + deptname + "]不能转成系统内码；";
		} else
			msg += "[部门名称]不能为空；";

		// 岗位名称
		String postname = map.get(columnTitle.get("岗位名称"));
		if (StringUtils.isNotEmpty(postname)) {
			this.setPostname(postname);
			Post post = nameService.getPostByName(this.getCompanyid(), this.getDeptid(), postname);
			if (post != null)
				this.setPostid(post.getPostid());
			else
				msg += "[岗位名称：" + postname + "]不能转成系统内码；";
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

		// 申请时间
		String applydate = map.get(columnTitle.get("申请时间"));
		if (StringUtils.isNotEmpty(applydate)) {
			try {
				this.setApplydate(Date.valueOf(applydate));
			} catch (Exception e) {
				msg += "[申请时间：" + applydate + "]转换日期错误；";
			}
		} else
			msg += "[申请时间]不能为空；";

		// 编制类型
		String quotaname = map.get(columnTitle.get("编制类型"));
		if (StringUtils.isNotEmpty(quotaname)) {
			this.setQuotaname(quotaname);
			Quota quota = nameService.getQuotaByName(this.getPostid(), quotaname);
			if (quota != null)
				this.setQuotaid(quota.getQuotaid());
			else
				msg += "[编制类型：" + quotaname + "]不能转成系统内码；";
		}

		// 招聘人数
		String postnum = map.get(columnTitle.get("招聘人数"));
		if (StringUtils.isNotEmpty(postnum)) {
			this.setPostnum(Integer.parseInt(postnum));
		} else {
			msg += "[招聘人数]不能为空；";
		}

		// 计划到岗时间
		String hillockdate = map.get(columnTitle.get("计划到岗时间"));
		if (StringUtils.isNotEmpty(hillockdate)) {
			try {
				this.setHillockdate(Date.valueOf(hillockdate));
			} catch (Exception e) {
				msg += "[计划到岗时间：" + hillockdate + "]转换日期错误；";
			}
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
	 * map数据导入
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param rankMap
	 * @param postMap
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public RecruitProgram(Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, String> rankMap, Map<String, String> postMap, NameConvertCodeService nameService,
			Map<String, String> quotaMap, Map<String, Integer> columnTitle, HSSFRow row) {
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
		this.setState(Constance.State_Tobe);

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
			if (dept != null)
				this.setDeptid(dept.getDeptid());
			else
				msg += "[部门名称：" + deptname + "]不能转成系统内码；";
		} else
			msg += "[部门名称]不能为空；";

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
			if (postid != null)
				this.setPostid(postid);
			else
				msg += "[岗位名称：" + postname + "]不能转成系统内码；";
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

		// 申请时间
		String applydate = map.get(columnTitle.get("申请时间"));
		if (StringUtils.isNotEmpty(applydate)) {
			try {
				this.setApplydate(Date.valueOf(applydate));
			} catch (Exception e) {
				msg += "[申请时间：" + applydate + "]转换日期错误；";
			}
		} else
			msg += "[申请时间]不能为空；";

		// 招聘人数
		String postnum = map.get(columnTitle.get("招聘人数"));
		if (StringUtils.isNotEmpty(postnum)) {
			this.setPostnum(Integer.parseInt(postnum));
		} else {
			msg += "[招聘人数]不能为空；";
		}

		// 计划到岗时间
		String hillockdate = map.get(columnTitle.get("计划到岗时间"));
		if (StringUtils.isNotEmpty(hillockdate)) {
			try {
				this.setHillockdate(Date.valueOf(hillockdate));
			} catch (Exception e) {
				msg += "[计划到岗时间：" + hillockdate + "]转换日期错误；";
			}
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
	 * 转型
	 * 
	 * @param companyDao
	 * @param departmentDao
	 * @param postDao
	 */
	// code转型
	public void convertOneCodeToName(CompanyDao companyDao, DepartmentDao departmentDao, PostDao postDao, QuotaDao quotaDao, OptionDao optionDao) {
		// 公司
		if (this.getCompanyid() != null) {
			Company company = OrgPathUtil.getOneCompanyFullPath(this.getCompanyid(), companyDao);
			if (company != null)
				this.setCompanyname(company.getCompanyfullname());
		}

		// 部门
		if (this.getDeptid() != null) {
			Department dept = OrgPathUtil.getOneDepartmentFullPath(this.getDeptid(), departmentDao);
			if (dept != null) {
				this.setDeptname(dept.getDeptfullname());

				// 查找当前部门下的编制数
				int num = quotaDao.sumQuotaNumByDeptCode(dept.getDeptcode());
				this.setQuotanumber(num);

				//
				int num1 = quotaDao.countEmployeeByDeptCode(dept.getDeptcode());
				this.setEmployednumber(num1);
			}
		}

		// 查找面试通过的人数
		if (this.getRecruitprogramguid() != null) {
			int num = quotaDao.sumInterviewNumberByRecruitprogramId(this.getRecruitprogramguid());
			this.setInterviewnumber(num);
		}

		// 岗位
		if (this.getPostid() != null) {
			Post post = postDao.getPostByPostId(this.getPostid());
			if (post != null)
				this.setPostname(post.getPostname());
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
				this.setQuotacode(quota.getQuotacode());
			}
			// 编制人数
			this.setBudgetnumber(quota.getBudgetnumber());

			Integer count = quotaDao.countEmployeeByQuotaId(this.getQuotaid());
			// 在岗人数
			this.setEmployednumber(count);

			// 锁定人数
			Integer operatenum = quotaDao.sumQuotaOperateByQoutaId(this.getQuotaid());
			this.setOperatenum(operatenum);

			// 缺编人数
			this.setVacancynumber(this.getBudgetnumber() - this.getEmployednumber() - this.getOperatenum());
		}

		// 招聘计划状态
		if (this.getState() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.RecruitProgram_state, this.getState());
			if (opt != null) {
				this.setStatename(opt.getName());
			}
		}
	}

	/**
	 * map转型
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param postMap
	 * @param quotaMap
	 * @param rankMap
	 */
	public void convertManyCodeToName(Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, Post> postMap, Map<String, String> quotaMap, Map<String, String> rankMap,
			QuotaDao quotaDao, Map<Integer, String> stateMap) {
		// 公司
		if (this.getCompanyid() != null) {
			Company company = companyMap.get(this.getCompanyid());
			this.setCompanyname(company.getCompanyfullname());
		}

		// 部门
		if (this.getDeptid() != null) {
			Department dept = deptMap.get(this.getDeptid());
			if (dept != null) {
				this.setDeptname(dept.getDeptfullname());
			}

			// 查找当前部门下的编制数
			int num = quotaDao.sumQuotaNumByDeptCode(dept.getDeptcode());
			this.setQuotanumber(num);
		}

		// 查找面试通过的人数
		if (this.getRecruitprogramguid() != null) {
			int num = quotaDao.sumInterviewNumberByRecruitprogramId(this.getRecruitprogramguid());
			this.setInterviewnumber(num);
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

			Quota quota = quotaDao.getQuotaById(this.getQuotaid());

			// 编制人数
			this.setBudgetnumber(quota.getBudgetnumber());

			Integer count = quotaDao.countEmployeeByQuotaId(this.getQuotaid());
			// 在岗人数
			this.setEmployednumber(count);

			// 锁定人数
			Integer operatenum = quotaDao.sumQuotaOperateByQoutaId(this.getQuotaid());
			this.setOperatenum(operatenum);

			// 缺编人数
			this.setVacancynumber(this.getBudgetnumber() - this.getEmployednumber() - this.getOperatenum());
		}

		// 招聘计划状态
		if (this.getState() != null) {
			String name = stateMap.get(this.getState());
			this.setStatename(name);
		}
	}

	@Override
	public String toString() {
		return "RecruitProgram [recruitprogramguid=" + recruitprogramguid + ", recruitprogramcode=" + recruitprogramcode + ", quotaid=" + quotaid + ", companyid=" + companyid + ", deptid=" + deptid
				+ ", postid=" + postid + ", rankid=" + rankid + ", applydate=" + applydate + ", hillockdate=" + hillockdate + ", postnum=" + postnum + ", state=" + state + ", processinstanceid="
				+ processinstanceid + ", memo=" + memo + ", modiuser=" + modiuser + ", moditimestamp=" + moditimestamp + ", modimemo=" + modimemo + "]";
	}

}
