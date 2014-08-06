package com.mk.system.entity;

import java.util.List;
import java.util.Map;

import com.mk.addresslist.entity.AddressList;
import com.mk.company.dao.CompanyDao;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.employee.entity.Position;
import com.mk.quota.dao.QuotaDao;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;

public class UserFilter {
	private String userguid;
	private String filterguid;

	// 临时
	private String username;
	private String companyname;
	private String deptname;
	private String postname;

	public String getUserguid() {
		return userguid;
	}

	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}

	public String getFilterguid() {
		return filterguid;
	}

	public void setFilterguid(String filterguid) {
		this.filterguid = filterguid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	/**
	 * code转名称
	 * 
	 * @param employeeDao
	 */
	public void convertOneCodeToName(SystemDao systemDao,EmployeeDao employeeDao, CompanyDao companyDao, DepartmentDao departmentDao, PostDao postDao,QuotaDao quotaDao, OptionDao optionDao) {
		if (this.getUserguid() != null) {
			User user = systemDao.getUserByUserId(this.getUserguid());
			if (user != null) {
				if (user.getEmployeeid() != null) {
					Employee employee = employeeDao.getEmployeeById(user.getEmployeeid());
					if(employee!=null){
						this.setUsername(employee.getName());
						List<Position> positions = employeeDao.getPositionByEmployeeIdAndIsstaff(employee.getEmployeeid());
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

			}
		}
	}
}
