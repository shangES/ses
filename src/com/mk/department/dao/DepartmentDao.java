package com.mk.department.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.department.entity.Department;

public interface DepartmentDao {

	/**
	 * 全部
	 * 
	 * @param state
	 * @return
	 */
	List<Department> getAllDepartment(@Param(value = "state") Integer state);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertDepartment(Department model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateDepartment(Department model);

	/**
	 * 删除
	 * 
	 * @param deptid
	 */
	void delDepartmentByDepartmentCode(String deptcode);

	/**
	 * 得到
	 * 
	 * @param deptid
	 * @return
	 */
	Department getDepartmentByDepartmentId(String deptid);

	/**
	 * 得到
	 * 
	 * @param companyid
	 * @param deptname
	 * @return
	 */
	List<Department> getDeptByDepartmentName(@Param(value = "companyid") String companyid, @Param(value = "deptname") String deptname);

	
	/**
	 * 得到
	 * 
	 * @param companyid
	 * @param deptname
	 * @return
	 */
	List<Department> getDepartmentByPDeptid(@Param(value = "companyid") String companyid, @Param(value = "pdeptid") String pdeptid);
	
	
	/**
	 * 得到
	 * 
	 * @param state
	 * @return
	 */
	List<Department> getHasDepartment(@Param(value = "state") Integer state, @Param(value = "userguid") String userguid);

	/**
	 * 得到
	 * 
	 * @param state
	 * @param userId
	 * @return
	 */
	List<Department> getHasWebDepartment(@Param(value = "state") Integer state, @Param(value = "userguid") String userguid);

	/**
	 * 得到
	 * 
	 * @param pid
	 * @return
	 */
	String getMaxDepartmentCodeByPDepartmentId(@Param(value = "pdeptid") String pdeptid);

	/**
	 * 得到
	 * 
	 * @param deptcode
	 * @return
	 */
	List<Department> getDepartmentByDepartmentCode(@Param(value = "deptcode") String deptcode);

	/**
	 * 得到
	 * 
	 * @param companycode
	 * @return
	 */
	List<Department> getDepartmentByCompanyCode(String companycode);

	/**
	 * 得到
	 * 
	 * @param companyid
	 * @return
	 */
	List<Department> getDepartmentByCompanyId(String companyid);
	
	/**
	 * 一级部门名称查询
	 * 
	 * @param departmentname
	 * @return
	 */
	Department getDepartmentbyDepartmentName(@Param(value = "departmentname") String departmentname,@Param(value = "companyid") String companyid);

	/**
	 * 二级部门名称查询
	 * 
	 * @param departmentname
	 * @return
	 */
	Department getPDepartmentbyDepartmentName(@Param(value = "departmentname") String departmentname,@Param(value = "companyid") String companyid,@Param(value = "pdeptid") String pdeptid);
	
	/**
	 * 根据岗位代码返回部门
	 * 
	 * @param postid
	 * @return
	 */
	List<Department> getWSDepartmentByPostId(@Param(value = "interfacecode") String interfacecode);
	
	
	/**
	 * 根据岗位代码返回部门
	 * 
	 * @param postid
	 * @return
	 */
	Department getDepartmentByPostcode(@Param(value = "interfacecode") String interfacecode);

}
