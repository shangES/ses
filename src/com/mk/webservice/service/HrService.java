package com.mk.webservice.service;

import java.util.List;

import javax.jws.WebService;

import com.mk.webservice.entity.WSCompany;
import com.mk.webservice.entity.WSDepartment;
import com.mk.webservice.entity.WSEmployee;
import com.mk.webservice.entity.WSPost;
import com.mk.webservice.entity.WSPostNum;

@WebService
public interface HrService {

	/**
	 * 组织架构
	 * 
	 * @param valid
	 * @return
	 */
	List<WSCompany> getAllCompanyDeptList(Integer valid);
	
	
	/**
	 * 通过员工工号查员工信息
	 * 
	 * @param valid
	 * @return
	 */
	List<WSEmployee> getWSEmployeeList(String jobnumber);

	
	/**
	 * 通过岗位id查员工信息
	 * 
	 * @param valid
	 * @return
	 */
	List<WSEmployee> getWSEmployeeListByPostId(String postid);
	
	
	
	/**
	 * 根据岗位编码返回部门名称
	 * 
	 * @return
	 */
	List<WSDepartment> getWSDepartmentByPostId(String postid);
	
	
	
	/**
	 * 通过员工工号查当前部门与二级部门下的所有人的信息
	 * 
	 * @param valid
	 * @return
	 */
	List<WSEmployee> getWSEmployeeListByDepartment(String jobnumber);
	
	
	/**
	 * 根据岗位code取数量
	 * 
	 * @param code
	 * @return
	 */
	List<WSPostNum> getNumByPostCode(String code);
	
	
	/**
	 * 根据部门编码返回该部门下（包括下级部门）所有的岗位编码
	 * 
	 * @return
	 */
	List<WSPost> getPostCodeByDeptCode(String deptcode);
}
