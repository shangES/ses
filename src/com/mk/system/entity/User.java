package com.mk.system.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.employee.entity.Position;
import com.mk.framework.constance.Constance;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.model.ColumnInfo;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.quota.dao.QuotaDao;
import com.mk.system.dao.OptionDao;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userguid;
	private String employeeid;
	private String loginname;
	private String password;
	private Integer isadmin;
	private Integer state;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;

	// 临时
	private String companyname;
	private String deptname;
	private String postname;
	private String username;
	private String rolename;
	private String jobnumber;

	public User() {

	}

	public User(Employee model) {
		this.setUserguid(UUIDGenerator.randomUUID());
		this.setEmployeeid(model.getEmployeeid());
		this.setLoginname(model.getJobnumber());
		this.setPassword("123456");
		this.setIsadmin(Constance.isAdmin0);
		this.setState(Constance.WORKSTATE_Normal);
		this.setMemo(model.getMemo());
		this.setModiuser(model.getModiuser());
		this.setModitimestamp(model.getModitimestamp());
		this.setModimemo(model.getModimemo());
	}

	public String getJobnumber() {
		return jobnumber;
	}

	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
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

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(Integer isadmin) {
		this.isadmin = isadmin;
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
	 * code转名称
	 * 
	 * @param employeeDao
	 */
	public void convertOneCodeToName(EmployeeDao employeeDao, CompanyDao companyDao, DepartmentDao departmentDao, PostDao postDao,QuotaDao quotaDao, OptionDao optionDao, Map<String, String> roleMap) {

		// 员工信息
		if (this.getEmployeeid() != null) {
			Employee employee = employeeDao.getEmployeeById(this.getEmployeeid());
			if (employee != null) {
				// 用戶名
				this.setUsername(employee.getName());
				List<Position> positions = employeeDao.getPositionByEmployeeIdAndIsstaff(this.getEmployeeid());
				if (!positions.isEmpty()) {
					Position position = positions.get(0);
					position.convertOneCodeToName(companyDao, departmentDao, postDao,quotaDao, optionDao);

					// 公司部门
					this.setCompanyname(position.getCompanyname());
					this.setDeptname(position.getDeptname());
					this.setPostname(position.getPostname());
				}
			}

		}
		// 用户角色
		if (roleMap != null) {
			this.setRolename(roleMap.get(this.getUserguid()));
		}
	}

	/**
	 * 批量轉code
	 * 
	 * @param employeeDao
	 * @param companyMap
	 * @param deptMap
	 * @param jobMap
	 * @param postMap
	 * @param quotaMap
	 */
	public void convertManyCodeToName(EmployeeDao employeeDao, Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, String> jobMap, Map<String, String> rankMap,
			Map<String, Post> postMap, Map<String, String> quotaMap, Map<String, String> roleMap) {
		// 员工信息
		if (this.getEmployeeid() != null) {
			Employee employee = employeeDao.getEmployeeById(this.getEmployeeid());
			if (employee != null) {
				// 用戶名
				this.setUsername(employee.getName());

				List<Position> positions = employeeDao.getPositionByEmployeeIdAndIsstaff(this.getEmployeeid());
				if (!positions.isEmpty()) {
					Position position = positions.get(0);
					position.convertManyCodeToName(companyMap, deptMap, jobMap, rankMap, postMap, quotaMap);

					// 公司部门
					this.setCompanyname(position.getCompanyname());
					this.setDeptname(position.getDeptname());
					this.setPostname(position.getPostname());
				}
			}
		}

		// 用户角色
		if (roleMap != null) {
			this.setRolename(roleMap.get(this.getUserguid()));
		}

	}

	/**
	 * 
	 * 导出的xls列头
	 * 
	 * @return
	 */
	public static List<ColumnInfo> initExcelColumn() {
		List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
		ColumnInfo c = new ColumnInfo();

		c.setId("companyname");
		c.setHeader("公司名称");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("deptname");
		c.setHeader("部门名称");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("username");
		c.setHeader("用户名");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("loginname");
		c.setHeader("登陆名");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("password");
		c.setHeader("密码");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("rolename");
		c.setHeader("角色");
		columns.add(c);

		c = new ColumnInfo();
		c.setId("memo");
		c.setHeader("备注");
		columns.add(c);

		return columns;
	}

}
