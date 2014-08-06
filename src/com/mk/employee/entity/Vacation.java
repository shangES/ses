package com.mk.employee.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.util.StringUtils;
import com.mk.fuzhu.service.NameConvertCodeService;
import com.mk.quota.dao.QuotaDao;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class Vacation {

	private String vacationid;
	private String employeeid;
	private String reason;
	private Integer vacationtype;
	private Date startdate;
	private double datenumber;
	private Date enddate;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;
	private Integer annualyear;

	// 临时属性
	private String vacationtypename;

	private String jobnumber;
	private String companyid;
	private String companyname;
	private String deptid;
	private String deptname;
	private String name;
	private String cardnumber;
	private String postid;
	private String postname;

	// 数据导入
	private Integer checkstate;
	private String msg;

	public Vacation() {

	}

	public Integer getAnnualyear() {
		return annualyear;
	}

	public void setAnnualyear(Integer annualyear) {
		this.annualyear = annualyear;
	}

	/**
	 * 请假信息导入
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public Vacation(NameConvertCodeService nameService, Map<String, Integer> columnTitle, HSSFRow row) {
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
		String name = map.get(columnTitle.get("请假人"));
		// 工号
		String jobnumber = map.get(columnTitle.get("员工工号"));
		if (StringUtils.isNotEmpty(jobnumber) && StringUtils.isNotEmpty(name)) {
			// 工号唯一
			Employee model = nameService.getEmployeeByJobnumber(jobnumber, name);
			if (model != null) {
				this.setEmployeeid(model.getEmployeeid());
				this.setJobnumber(model.getJobnumber());
				this.setName(model.getName());
				this.setCompanyname(model.getCompanyname());
				this.setDeptname(model.getDeptname());
				this.setPostname(model.getPostname());
			} else {
				msg += "员工信息不存在;";
			}
		} else
			msg += "[工号]不能为空；";

		// 请假类型
		String vacationtypename = map.get(columnTitle.get("请假类型"));
		if (StringUtils.isNotEmpty(vacationtypename)) {
			this.setVacationtypename(vacationtypename);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.VACATIONTYPE, vacationtypename);
			if (code == null) {
				msg += "[请假类型：" + vacationtypename + "]不能转成系统内码；";
			} else
				this.setVacationtype(code);
		} else
			msg += "[请假类型]不能为空；";
		
		// 请假年度
		String annualyear = map.get(columnTitle.get("所属年份"));
		if (StringUtils.isNotEmpty(annualyear)) {
			this.setAnnualyear(Integer.valueOf(annualyear));
		} else
			msg += "[所属年份]不能为空；";
		

		// 请假天数
		String datenumber = map.get(columnTitle.get("请假天数"));
		if (StringUtils.isNotEmpty(datenumber)) {
			this.setDatenumber(Double.valueOf(datenumber));
		} else
			msg += "[请假天数]不能为空；";

		// 请假开始时间
		String startdate = map.get(columnTitle.get("请假开始时间"));
		if (StringUtils.isNotEmpty(startdate)) {
			this.setStartdate(Date.valueOf(startdate));
		} else
			msg += "[请假开始时间]不能为空；";

		// 请假结束时间
		String enddate = map.get(columnTitle.get("请假结束时间"));
		if (StringUtils.isNotEmpty(enddate)) {
			this.setEnddate(Date.valueOf(enddate));
		}

		// 请假原因
		String reason = map.get(columnTitle.get("请假原因"));
		if (StringUtils.isNotEmpty(reason)) {
			this.setReason(reason);
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
	 * 请假数据的导入
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param postMap
	 * @param vacationtypeMap
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public Vacation(Map<String, Integer> vacationtypeMap, NameConvertCodeService nameService, Map<String, Integer> columnTitle, HSSFRow row) {
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
		String name = map.get(columnTitle.get("请假人"));
		// 工号
		String jobnumber = map.get(columnTitle.get("员工工号"));
		if (StringUtils.isNotEmpty(jobnumber) && StringUtils.isNotEmpty(name)) {
			Employee model = nameService.getEmployeeByJobnumber(jobnumber, name);
			if (model != null) {
				this.setEmployeeid(model.getEmployeeid());
				this.setJobnumber(model.getJobnumber());
				this.setName(model.getName());
				this.setCompanyname(model.getCompanyname());
				this.setDeptname(model.getDeptname());
				this.setPostname(model.getPostname());
			} else {
				msg += "员工信息不存在;";
			}
		} else
			msg += "[工号]不能为空；";

		// 请假类型
		String vacationtypename = map.get(columnTitle.get("请假类型"));
		if (StringUtils.isNotEmpty(vacationtypename)) {
			this.setVacationtypename(vacationtypename);
			Integer code = vacationtypeMap.get(vacationtypename);
			if (code == null) {
				msg += "[请假类型：" + vacationtypename + "]不能转成系统内码；";
			} else
				this.setVacationtype(code);
		} else
			msg += "[请假类型]不能为空；";

		// 请假天数
		String datenumber = map.get(columnTitle.get("请假天数"));
		if (StringUtils.isNotEmpty(datenumber)) {
			this.setDatenumber(Double.valueOf(datenumber));
		} else
			msg += "[请假天数]不能为空；";
		
		
		// 请假年度
		String annualyear = map.get(columnTitle.get("所属年份"));
		if (StringUtils.isNotEmpty(annualyear)) {
			this.setAnnualyear(Integer.valueOf(annualyear));
		} else
			msg += "[所属年份]不能为空；";

		// 请假开始时间
		String startdate = map.get(columnTitle.get("请假开始时间"));
		if (StringUtils.isNotEmpty(startdate)) {
			this.setStartdate(Date.valueOf(startdate));
		} else
			msg += "[请假开始时间]不能为空；";

		// 请假结束时间
		String enddate = map.get(columnTitle.get("请假结束时间"));
		if (StringUtils.isNotEmpty(enddate)) {
			this.setEnddate(Date.valueOf(enddate));
		}

		// 请假原因
		String reason = map.get(columnTitle.get("请假原因"));
		if (StringUtils.isNotEmpty(reason)) {
			this.setReason(reason);
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

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
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

	public String getJobnumber() {
		return jobnumber;
	}

	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getVacationtypename() {
		return vacationtypename;
	}

	public void setVacationtypename(String vacationtypename) {
		this.vacationtypename = vacationtypename;
	}

	public String getVacationid() {
		return vacationid;
	}

	public void setVacationid(String vacationid) {
		this.vacationid = vacationid;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getVacationtype() {
		return vacationtype;
	}

	public void setVacationtype(Integer vacationtype) {
		this.vacationtype = vacationtype;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public double getDatenumber() {
		return datenumber;
	}

	public void setDatenumber(double datenumber) {
		this.datenumber = datenumber;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
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

	// code转型
	public void convertOneCodeToName(OptionDao option, EmployeeDao emDao, CompanyDao companyDao, DepartmentDao departmentDao, PostDao postDao, QuotaDao quotaDao) {
		// 请假类型
		if (this.vacationtype != null) {
			OptionList opt = option.getOptionListByTypeAndCode(OptionConstance.VACATIONTYPE, this.getVacationtype());
			if (opt != null) {
				this.setVacationtypename(opt.getName());
			}
		}

		if (this.getEmployeeid() != null) {
			List<Position> positions = emDao.getPositionByEmployeeIdAndIsstaff(this.getEmployeeid());
			Employee employee = emDao.getEmployeeById(this.getEmployeeid());
			if (!positions.isEmpty()) {
				Position position = positions.get(0);
				position.convertOneCodeToName(companyDao, departmentDao, postDao, quotaDao, option);

				this.setCompanyname(position.getCompanyname());
				this.setDeptname(position.getDeptname());
				this.setPostname(position.getPostname());
			}

			if (employee != null) {
				this.setName(employee.getName());
				this.setJobnumber(employee.getJobnumber());
				this.setCardnumber(employee.getCardnumber());
			}

		}
	}

	/**
	 * code转型 map
	 * 
	 * @param vacationtypeMap
	 */
	public void convertManyCodeToName(Map<Integer, String> vacationtypeMap, Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, String> jobMap, Map<String, String> rankMap,
			Map<String, Post> postMap, Map<String, String> quotaMap, EmployeeDao emDao) {
		// 请假类型
		if (this.vacationtype != null) {
			String name = vacationtypeMap.get(this.getVacationtype());
			this.setVacationtypename(name);
		}

		// 员工任职
		List<Position> positions = emDao.getPositionByEmployeeIdAndIsstaff(this.getEmployeeid());
		if (!positions.isEmpty()) {
			Employee employee = emDao.getEmployeeById(this.getEmployeeid());
			Position position = positions.get(0);
			position.convertManyCodeToName(companyMap, deptMap, jobMap, rankMap, postMap, quotaMap);

			// 公司部门岗位
			this.setCompanyname(position.getCompanyname());
			this.setDeptname(position.getDeptname());
			this.setPostname(position.getPostname());

			if (employee != null) {
				this.setName(employee.getName());
				this.setJobnumber(employee.getJobnumber());
				this.setCardnumber(employee.getCardnumber());
			}
		}
	}

	@Override
	public String toString() {
		return "Vacation [vacationid=" + vacationid + ", employeeid=" + employeeid + ", reason=" + reason + ", vacationtype=" + vacationtype + ", startdate=" + startdate + ", datenumber="
				+ datenumber + ", enddate=" + enddate + ", memo=" + memo + ", modiuser=" + modiuser + ", moditimestamp=" + moditimestamp + ", modimemo=" + modimemo + ", vacationtypename="
				+ vacationtypename + "]";
	}

}
