package com.mk.employee.entity;

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
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.util.StringUtils;
import com.mk.fuzhu.service.NameConvertCodeService;
import com.mk.quota.dao.QuotaDao;
import com.mk.system.dao.OptionDao;

public class Annual {
	private String annualguid;
	private String employeeid;
	private Integer annualyear;
	private double offnumday;
	private String modiuser;
	private Timestamp moditimestamp;

	// 临时
	private String jobnumber;
	private String companyid;
	private String companyname;
	private String deptid;
	private String deptname;
	private String name;
	private String cardnumber;
	private String postid;
	private String postname;

	// 已休年假数、剩余年假数
	private double alreadynumberday;
	private double leavenumberday;

	// 数据导入
	private Integer checkstate;
	private String msg;

	public Annual() {

	}

	/**
	 * 年休信息导入
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public Annual(NameConvertCodeService nameService, Map<String, Integer> columnTitle, HSSFRow row) {
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
		String name = map.get(columnTitle.get("员工姓名"));
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

		// 请假年度
		String annualyear = map.get(columnTitle.get("年度"));
		if (StringUtils.isNotEmpty(annualyear)) {
			this.setAnnualyear(Integer.valueOf(annualyear));
		} else
			msg += "[年度]不能为空；";
		

		// 可休年休假天数
		String offnumday = map.get(columnTitle.get("可休年休假数"));
		if (StringUtils.isNotEmpty(offnumday)) {
			this.setOffnumday(Double.valueOf(offnumday));
		} else
			msg += "[可休年休假数]不能为空；";

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

	public double getAlreadynumberday() {
		return alreadynumberday;
	}

	public void setAlreadynumberday(double alreadynumberday) {
		this.alreadynumberday = alreadynumberday;
	}

	public double getLeavenumberday() {
		return leavenumberday;
	}

	public void setLeavenumberday(double leavenumberday) {
		this.leavenumberday = leavenumberday;
	}

	public Integer getAnnualyear() {
		return annualyear;
	}

	public void setAnnualyear(Integer annualyear) {
		this.annualyear = annualyear;
	}

	public String getJobnumber() {
		return jobnumber;
	}

	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
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

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
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

	public String getAnnualguid() {
		return annualguid;
	}

	public void setAnnualguid(String annualguid) {
		this.annualguid = annualguid;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public double getOffnumday() {
		return offnumday;
	}

	public void setOffnumday(double offnumday) {
		this.offnumday = offnumday;
	}

	// code转型
	public void convertOneCodeToName(OptionDao option, EmployeeDao emDao, CompanyDao companyDao, DepartmentDao departmentDao, PostDao postDao, QuotaDao quotaDao) {

		if (this.getEmployeeid() != null) {

			// 已休年假数
			double num = emDao.countdaysByEmployeeIdForAnnual(this.getEmployeeid(), String.valueOf(this.getAnnualyear()));
			this.setAlreadynumberday(num);

			// 剩余年假数
			this.setLeavenumberday(this.getOffnumday() - num);

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
	public void convertManyCodeToName(Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, String> jobMap, Map<String, String> rankMap, Map<String, Post> postMap,
			Map<String, String> quotaMap, EmployeeDao emDao) {

		if (this.getEmployeeid() != null) {
			// 已休年假数
			double num = emDao.countdaysByEmployeeIdForAnnual(this.getEmployeeid(), String.valueOf(this.getAnnualyear()));
			this.setAlreadynumberday(num);

			// 剩余年假数
			this.setLeavenumberday(this.getOffnumday() - num);

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

	}

}
