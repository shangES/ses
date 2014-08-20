package com.mk.system.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.framework.grid.GridServerHandler;
import com.mk.system.entity.Function;
import com.mk.system.entity.ResumeFilter;
import com.mk.system.entity.Role;
import com.mk.system.entity.RoleRight;
import com.mk.system.entity.User;
import com.mk.system.entity.UserAddressCompany;
import com.mk.system.entity.UserDepartment;
import com.mk.system.entity.UserFilter;
import com.mk.system.entity.UserManageRange;
import com.mk.system.entity.UserRole;

public interface SystemDao {

	// ============用户===========================

	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countUser(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<User> searchUser(GridServerHandler grid);

	/**
	 * 全部
	 * 
	 * @param state
	 * @return
	 */
	List<User> getAllUser();

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertUser(User model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateUser(User model);

	/**
	 * 删除
	 * 
	 * @param userguid
	 */
	void delUserByUserId(String userguid);

	/**
	 * 删除
	 * 
	 * @param userguid
	 */
	void delUserByEmployeeId(String employeeid);

	/**
	 * 得到
	 * 
	 * @param userguid
	 * @return
	 */
	User getUserByUserId(String userguid);

	/**
	 * 得到
	 * 
	 * @param employeeid
	 * @return
	 */
	User getUserByUserEmployeeId(String employeeid);

	/**
	 * 登陆
	 * 
	 * @param name
	 * @return
	 */
	User getUserByLoginName(String loginname);

	/**
	 * 当前有权限的用户
	 * 
	 * @param state
	 * @param userId
	 * @return
	 */
	List<User> getHasUsers(@Param(value = "state") Integer state, @Param(value = "userguid") String userguid);

	/**
	 * 動態樹
	 * 
	 * @param state
	 * @return
	 */
	List<User> asyncUserTree(@Param(value = "deptid") String deptid, @Param(value = "postdate_s") Date postdate_s, @Param(value = "postdate_e") Date postdate_e);

	/**
	 * 得到招聘专员用户
	 * 
	 * @param validYes
	 * @return
	 */
	List<User> getRecruiterUser(@Param(value = "rolename") String rolename);

	// ================角色=====================
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertRole(Role model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateRole(Role model);

	/**
	 * 得到
	 * 
	 * @param userguid
	 * @return
	 */
	List<Role> getRoleByUserId(String userguid);

	/**
	 * 得到
	 * 
	 * @param rolename
	 * @return
	 */
	Role getRoleByRoleName(String rolename);

	/**
	 * 得到全部
	 * 
	 * @return
	 */
	List<Role> getAllRole();
	
	/**
	 * 得到部份
	 * 
	 * @return
	 */
	List<Role> getOtherRole();

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Role getRoleById(String id);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delRoleById(String id);

	// =============用户角色==========================
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertUserRole(UserRole model);

	/**
	 * 删除
	 * 
	 * @param userguid
	 */
	void delUserRoleByUserId(String userguid);
	
	/**
	 * 删除
	 * 
	 * @param userguid
	 * @param roleid
	 */
	void delUserRoleByUserId(String userguid,String roleid);

	/**
	 * 删除
	 * 
	 * @param roleguid
	 */
	void delUserRoleByRoleId(String roleguid);

	/**
	 * 删除
	 * 
	 * @param employeeid
	 */
	void delUserRoleByEmployeeId(String employeeid);

	/**
	 * 删除
	 * 
	 * @param model
	 */
	void delUserRoleByUserRole(UserRole model);

	/**
	 * 得到
	 * 
	 * @param userguid
	 * @return
	 */
	List<UserRole> getUserRoleByUserId(String userguid);

	/**
	 * 得到
	 * 
	 * @param roleguid
	 * @return
	 */
	List<UserRole> getUserRoleByRoleId(String roleguid);

	/**
	 * 得到
	 * 
	 * @return
	 */
	List<UserRole> getAllUserRole();

	/**
	 * 得到
	 * 
	 * @param roleguid
	 * @param userguid
	 * @return
	 */
	UserRole getUserRoleByRoleAndUsergGuid(@Param(value = "roleguid") String roleguid, @Param(value = "userguid") String userguid);

	// ==============用户公司=========================
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertUserManageRange(UserManageRange model);

	/**
	 * 删除
	 * 
	 * @param userguid
	 */
	void delUserManageRangeByUserId(String userguid);

	/**
	 * 删除
	 * 
	 * @param roleguid
	 */
	void delUserManageRangeByCompanyId(String companyid);

	/**
	 * 删除
	 * 
	 * @param employeeid
	 */
	void delUserManageRangeByEmployeeId(String employeeid);

	/**
	 * 得到
	 * 
	 * @param userguid
	 * @return
	 */
	List<UserManageRange> getUserManageRangeByUserId(String userguid);

	/**
	 * 得到
	 * 
	 * @param roleguid
	 * @return
	 */
	List<UserManageRange> getUserManageRangeByCompanyId(String companyid);

	/**
	 * 得到
	 * 
	 * @param companyid
	 * @param userguid
	 * @return
	 */
	UserManageRange getUserManageRangeByUserIdAndCompanyId(@Param(value = "companyid") String companyid, @Param(value = "userguid") String userguid);

	// ============用户对通讯录===========================
	/**
	 * 保存
	 * 
	 * @param user
	 */
	void saveUserAddressCompany(User user);

	/**
	 * 得到
	 * 
	 * @param userguid
	 * @return
	 */
	List<UserAddressCompany> getUserAddressCompanyByUserId(String userguid);

	/**
	 * 得到
	 * 
	 * @param companyid
	 * @return
	 */
	List<UserAddressCompany> getUserAddressCompanyByCompanyId(String companyid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertUserAddressCompany(UserAddressCompany model);

	/**
	 * 删除
	 * 
	 * @param userguid
	 */
	void delUserAddressCompanyByUserId(String userguid);

	/**
	 * 删除
	 * 
	 * 
	 * @param companyid
	 */
	void delUserAddressCompanyByCompanyId(String companyid);

	/**
	 * 删除
	 * 
	 * @param employeeid
	 */
	void delUserAddressCompanyByEmployeeId(String employeeid);

	// ============菜单===========================
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void saveFunction(Function model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateFunction(Function model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delFunctionById(String id);

	/**
	 * 得到
	 * 
	 * @param funid
	 */
	Function getFunctionById(String funid);

	/**
	 * 得到
	 * 
	 * @param userId
	 * @return
	 */
	List<Function> getUserFunctions(String userId);

	/**
	 * 得到
	 * 
	 * @param pfunid
	 */
	String getMaxFunctionByFunpid(@Param(value = "pid") String pid);

	/**
	 * 全部
	 * 
	 * @return
	 */
	List<Function> getAllFunctions();

	// =============菜单角色关系==========================
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertFunctionRole(RoleRight model);

	/**
	 * 删除
	 * 
	 * @param funid
	 */
	void delFunctionRoleByFunctionId(String funid);

	/**
	 * 删除
	 * 
	 * @param roleid
	 */
	void delFunctionRoleByRoleId(String roleid);

	/**
	 * 得到
	 * 
	 * @param funid
	 * @return
	 */
	List<RoleRight> getFunctionRoleByFunctionId(String funid);

	/**
	 * 得到
	 * 
	 * @param roleid
	 * @return
	 */
	List<RoleRight> getFunctionRoleByRoleId(String roleid);

	// =====================用户部门赋权=============================

	/**
	 * 得到
	 * 
	 * @param userguid
	 * @return
	 */
	List<UserDepartment> getUserDepartmentByUserId(@Param(value = "userguid") String userguid);

	/**
	 * 删除
	 * 
	 * @param userguid
	 */
	void delUserDepartmentByUserId(@Param(value = "userguid") String userguid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertUserDepartment(UserDepartment model);

	/**
	 * 删除
	 * 
	 * @param employeeid
	 */
	void delUserDepartmentByEmployeeId(String employeeid);

	// =====================用户对部门筛选条件赋权=============================

	/**
	 * 得到
	 * 
	 * @return
	 */
	List<ResumeFilter> getAllResumeFilter();

	/**
	 * 得到
	 * 
	 * @param userid
	 * @return
	 */
	List<ResumeFilter> getResumefilterByUserid(@Param(value = "userguid") String userid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	ResumeFilter getResumeFilterById(String filterguid);

	/**
	 * 搜索
	 * 
	 */
	List<ResumeFilter> searchResumeFilter(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertResumeFilter(ResumeFilter model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateResumeFilter(ResumeFilter model);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertUserFilter(UserFilter model);

	/**
	 * 得到
	 * 
	 * @param userguid
	 * @return
	 */
	List<UserFilter> getUserFilterByUserId(@Param(value = "userguid") String userguid);

	/**
	 * 删除
	 * 
	 * @param userguid
	 */
	void delUserFilterByUserId(@Param(value = "userguid") String userguid);

	/**
	 * 删除
	 * 
	 */
	void delUserFilterByEmployeeId(String employeeid);

	// =====================用户对部门筛选取消=============================

	/*
	 * 统计
	 */
	Integer countUserFilter(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * 
	 * @param grid
	 * @return
	 */
	List<User> searchUserFilter(GridServerHandler grid);
	
	/**
	 * 通过角色搜索
	 * 
	 * 
	 * @param grid
	 * @return
	 */
	List<User> searchUserByRole(GridServerHandler grid);
	/**
	 * 通过角色搜索
	 * 
	 * 
	 * @param grid
	 * @return
	 */
	Integer countUserByRole(GridServerHandler grid);


}
