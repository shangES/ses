package com.mk.quota.entity;

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
import com.mk.company.entity.Budgetype;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.framework.constance.Constance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.util.StringUtils;
import com.mk.fuzhu.service.NameConvertCodeService;
import com.mk.quota.dao.QuotaDao;

public class Quota {
	private String quotaid;
	private String quotacode;
	private String postid;
	private String budgettype;
	private Integer budgetnumber;
	private Date budgetdate;
	private Integer state;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;
	private String processinstanceid;

	// 临时
	private String budgettypename;
	private String companyname;
	private String deptname;
	private String postname;
	private Integer employednumber;
	private Integer vacancynumber;
	private Integer operatenum;
	private Integer postnumber;
	private Integer operatestate;
	private String companyid;
	private String deptid;
	private String quotaname;

	// 数据导入
	private Integer checkstate;
	private String msg;

	// 流程
	private String assignee;
	private String taskid;
	private String taskcreatetime;
	private boolean runprocess;
	private Map<String, Object> variables;
	private List<AuditHistory> audithistorys;

	public Quota() {

	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
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

	public boolean isRunprocess() {
		return runprocess;
	}

	public void setRunprocess(boolean runprocess) {
		this.runprocess = runprocess;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public List<AuditHistory> getAudithistorys() {
		return audithistorys;
	}

	public void setAudithistorys(List<AuditHistory> audithistorys) {
		this.audithistorys = audithistorys;
	}

	public String getProcessinstanceid() {
		return processinstanceid;
	}

	public void setProcessinstanceid(String processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

	public String getQuotaname() {
		return quotaname;
	}

	public void setQuotaname(String quotaname) {
		this.quotaname = quotaname;
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

	public Integer getOperatestate() {
		return operatestate;
	}

	public void setOperatestate(Integer operatestate) {
		this.operatestate = operatestate;
	}

	public Integer getEmployednumber() {
		return employednumber;
	}

	public void setEmployednumber(Integer employednumber) {
		this.employednumber = employednumber;
	}

	public Integer getVacancynumber() {
		return vacancynumber;
	}

	public void setVacancynumber(Integer vacancynumber) {
		this.vacancynumber = vacancynumber;
	}

	public Integer getOperatenum() {
		return operatenum;
	}

	public void setOperatenum(Integer operatenum) {
		this.operatenum = operatenum;
	}

	public Integer getPostnumber() {
		return postnumber;
	}

	public void setPostnumber(Integer postnumber) {
		this.postnumber = postnumber;
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

	public String getBudgettypename() {
		return budgettypename;
	}

	public void setBudgettypename(String budgettypename) {
		this.budgettypename = budgettypename;
	}

	public String getQuotaid() {
		return quotaid;
	}

	public void setQuotaid(String quotaid) {
		this.quotaid = quotaid;
	}

	public String getQuotacode() {
		return quotacode;
	}

	public void setQuotacode(String quotacode) {
		this.quotacode = quotacode;
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	public String getBudgettype() {
		return budgettype;
	}

	public void setBudgettype(String budgettype) {
		this.budgettype = budgettype;
	}

	public Integer getBudgetnumber() {
		return budgetnumber;
	}

	public void setBudgetnumber(Integer budgetnumber) {
		this.budgetnumber = budgetnumber;
	}

	public Date getBudgetdate() {
		return budgetdate;
	}

	public void setBudgetdate(Date budgetdate) {
		this.budgetdate = budgetdate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	/**
	 * code的转型
	 * 
	 * @param option
	 */
	public void convertOneCodeToName(QuotaDao mapper, CompanyDao comDao, DepartmentDao deptDao, PostDao postdao) {
		if (this.getPostid() != null) {
			Post post = postdao.getPostByPostId(this.getPostid());
			if (post != null) {
				this.setPostname(post.getPostname());
				// 公司
				Company company = OrgPathUtil.getOneCompanyFullPath(post.getCompanyid(), comDao);
				if (company != null) {
					this.setCompanyname(company.getCompanyfullname());

					// 部门
					Department dept = OrgPathUtil.getOneDepartmentFullPath(post.getDeptid(), deptDao);
					if (dept != null)
						this.setDeptname(dept.getDeptfullname());
				}
			}
		}

		if (this.getQuotaid() != null) {
			Integer count = mapper.countEmployeeByQuotaId(this.getQuotaid());
			// 在岗人数
			this.setEmployednumber(count);

			// 招聘人数
			Integer postnum = mapper.sumRecruitprogramByQoutaId(this.getQuotaid());
			this.setPostnumber(postnum);

			// 锁定人数
			Integer operatenum = mapper.sumQuotaOperateByQoutaId(this.getQuotaid());
			this.setOperatenum(operatenum);

			// 缺编人数
			this.setVacancynumber(this.getBudgetnumber() - this.getEmployednumber() - this.getOperatenum());
		}

		// 编制类型
		if (this.getBudgettype() != null) {
			Budgetype model = comDao.getBudgetypeById(this.getBudgettype());
			if (model != null) {
				this.setBudgettypename(model.getBudgettypename());
			}
		}
	}

	/**
	 * 批量code转名称
	 * 
	 * @param budgettypemap
	 */
	public void convertManyCodeToName(QuotaDao mapper, Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, Post> postMap, Map<String, String> budgettypemap) {
		// 公司 部门 岗位 职务

		// 岗位
		if (this.getPostid() != null) {
			Post post = postMap.get(this.getPostid());
			if (post != null) {
				this.setPostname(post.getPostname());
				// 公司
				Company company = companyMap.get(post.getCompanyid());
				if (company != null) {
					this.setCompanyname(company.getCompanyfullname());

					// 部门
					Department dept = deptMap.get(post.getDeptid());
					if (dept != null)
						this.setDeptname(dept.getDeptfullname());
				}
			}
		}

		// 在岗人数
		if (this.getQuotaid() != null) {
			Integer count = mapper.countEmployeeByQuotaId(this.getQuotaid());
			this.setEmployednumber(count);

			// 招聘人数
			Integer postnum = mapper.sumRecruitprogramByQoutaId(this.getQuotaid());
			this.setPostnumber(postnum);

			// 锁定人数
			Integer operatenum = mapper.sumQuotaOperateByQoutaId(this.getQuotaid());
			this.setOperatenum(operatenum);

			// 缺编人数
			this.setVacancynumber(this.getBudgetnumber() - this.getEmployednumber() - this.getOperatenum());
		}

		// 编制类型
		if (this.getBudgettype() != null) {
			String name = budgettypemap.get(this.getBudgettype());
			this.setBudgettypename(name);
		}
	}

	/**
	 * 转编制类型
	 * 
	 * @param companyDao
	 */
	public void convertBudgettype(CompanyDao companyDao) {
		// 编制类型
		if (this.getBudgettype() != null) {
			Budgetype model = companyDao.getBudgetypeById(this.getBudgettype());
			if (model != null) {
				this.setBudgettypename(model.getBudgettypename());
			}
		}
	}

	/**
	 * 转编制类型
	 * 
	 * @param companyDao
	 */
	public void convertManyBudgettype(Map<String, String> budgettypemap) {
		// 编制类型
		if (this.getBudgettype() != null) {
			String name = budgettypemap.get(this.getBudgettype());
			this.setBudgettypename(name);
		}
	}

	/**
	 * 导入
	 * 
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public Quota(Map<String, Company> companyMap, Map<String, Department> deptMap, NameConvertCodeService nameService, Map<String, Integer> columnTitle, HSSFRow row) {
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
		} else
			msg += "[岗位名称]不能为空；";

		// 编制类别
		String budgettypename = map.get(columnTitle.get("编制类别"));
		if (StringUtils.isNotEmpty(budgettypename)) {
			this.setBudgettypename(budgettypename);
			Budgetype tbudgettype = nameService.getBudgettypeByName(this.getCompanyid(), budgettypename);
			if (tbudgettype != null)
				this.setBudgettype(tbudgettype.getBudgettypeid());
			else
				msg += "[编制类别：" + budgettypename + "]不能转成系统内码；";
		} else
			msg += "[编制类别]不能为空；";

		// 编制人数
		String budgetnumber = map.get(columnTitle.get("编制人数"));
		if (StringUtils.isNotEmpty(budgetnumber)) {
			try {
				// 定的为number形后面会出现.0截掉
				if (budgetnumber.indexOf(".") != -1) {
					budgetnumber = budgetnumber.substring(0, budgetnumber.lastIndexOf("."));
				}
				this.setBudgetnumber(Integer.valueOf(budgetnumber));
			} catch (Exception e) {
				msg += "[编制人数：" + budgetnumber + "]转换数字错误；";
			}
		} else
			msg += "[编制人数]不能为空；";

		// 编制日期
		String budgetdate = map.get(columnTitle.get("编制日期"));
		if (StringUtils.isNotEmpty(budgetdate)) {
			try {
				this.setBudgetdate(Date.valueOf(budgetdate));
			} catch (Exception e) {
				msg += "[编制日期：" + budgetdate + "]转换日期错误；";
			}
		} else
			msg += "[编制日期]不能为空；";

		// 用户
		UserContext uc = ContextFacade.getUserContext();
		this.setModiuser(uc.getUsername());
		// 状态
		this.setState(Constance.VALID_YES);

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
	 * 批量导入
	 * 
	 * @param postMap
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public Quota(Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, String> postMap, Map<String, String> budgettypeMap, NameConvertCodeService nameService,
			Map<String, Integer> columnTitle, HSSFRow row) {
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

		// 用户
		UserContext uc = ContextFacade.getUserContext();
		this.setModiuser(uc.getUsername());
		// 状态
		this.setState(Constance.VALID_YES);

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
			String postid = postMap.get(this.getDeptid() + "/" + postname);
			if (postid != null)
				this.setPostid(postid);
			else
				msg += "[岗位名称：" + postname + "]不能转成系统内码；";
		} else
			msg += "[岗位名称]不能为空；";

		// 编制类别
		String budgettypename = map.get(columnTitle.get("编制类别"));
		if (StringUtils.isNotEmpty(budgettypename)) {
			this.setBudgettypename(budgettypename);
			String budgettype = budgettypeMap.get(this.getCompanyid() + "/" + budgettypename);
			if (budgettype != null)
				this.setBudgettype(budgettype);
			else
				msg += "[编制类别：" + budgettypename + "]不能转成系统内码；";
		} else
			msg += "[编制类别]不能为空；";

		// 编制人数
		String budgetnumber = map.get(columnTitle.get("编制人数"));
		if (StringUtils.isNotEmpty(budgetnumber)) {
			try {
				// 定的为number形后面会出现.0截掉
				if (budgetnumber.indexOf(".") != -1) {
					budgetnumber = budgetnumber.substring(0, budgetnumber.lastIndexOf("."));
				}
				this.setBudgetnumber(Integer.valueOf(budgetnumber));
			} catch (Exception e) {
				msg += "[编制人数：" + budgetnumber + "]转换数字错误；";
			}
		} else
			msg += "[编制人数]不能为空；";

		// 编制日期
		String budgetdate = map.get(columnTitle.get("编制日期"));
		if (StringUtils.isNotEmpty(budgetdate)) {
			try {
				this.setBudgetdate(Date.valueOf(budgetdate));
			} catch (Exception e) {
				msg += "[编制日期：" + budgetdate + "]转换日期错误；";
			}
		} else
			msg += "[编制日期]不能为空；";

		// 备注
		String memo = map.get(columnTitle.get("备注"));
		if (StringUtils.isNotEmpty(memo)) {
			this.setMemo(memo);
		}

		// 异常
		if (StringUtils.isNotEmpty(this.getMsg()))
			this.setCheckstate(Constance.VALID_YES);
	}

	@Override
	public String toString() {
		return "Quota [quotaid=" + quotaid + ", quotacode=" + quotacode + ", postid=" + postid + ", budgettype=" + budgettype + ", budgetnumber=" + budgetnumber + ", budgetdate=" + budgetdate
				+ ", state=" + state + ", memo=" + memo + ", modiuser=" + modiuser + ", moditimestamp=" + moditimestamp + ", modimemo=" + modimemo + ", budgettypename=" + budgettypename + "]";
	}

}
