package com.mk.system.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.company.service.CompanyService;
import com.mk.company.tree.CompanyTree;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.department.service.DepartmentService;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.employee.entity.Position;
import com.mk.framework.constance.Constance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.NoUserContext;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.quota.dao.QuotaDao;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.Function;
import com.mk.system.entity.Role;
import com.mk.system.entity.User;
import com.mk.system.entity.UserAddressCompany;
import com.mk.system.entity.UserAddressCompanyPam;
import com.mk.system.entity.UserDepartment;
import com.mk.system.entity.UserDepartmentPam;
import com.mk.system.entity.UserManageRange;
import com.mk.system.entity.UserManageRangePam;
import com.mk.system.entity.UserRole;
import com.mk.system.entity.UserRolePam;
import com.mk.system.tree.RoleTree;
import com.mk.system.tree.UserDepartmentTree;
import com.mk.system.tree.UserTree;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CodeConvertNameService codeConvertNameService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private DepartmentService departmentService;

	/**
	 * 登陆加载session
	 */
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException, DataAccessException {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		User user = mapper.getUserByLoginName(name);
		if (user == null) {
			NoUserContext ncontext = new NoUserContext();
			return ncontext;
		}
		UserContext context = new UserContext(user);

		// 取员工信息
		String employid = user.getEmployeeid();
		if (StringUtils.isNotEmpty(employid)) {
			Employee model = employeeDao.getEmployeeById(employid);
			if (model != null) {
				// 员工任职
				List<Position> positions = employeeDao.getPositionByEmployeeIdAndIsstaff(employid);
				if (!positions.isEmpty()) {
					Position position = positions.get(0);
					position.convertOneCodeToName(companyDao, departmentDao, postDao, quotaDao, optionDao);

					// 公司部门岗位
					context.setCompanyid(position.getCompanyid());
					context.setCompanyname(position.getCompanyname());
					context.setDeptid(position.getDeptid());
					context.setDeptname(position.getDeptname());
					context.setPostname(position.getPostname());
				}
				// 用户名
				context.setUsername(model.getName());
				context.setMobile(model.getMobile());
			}
		} else {
			List<Company> companys = companyDao.getAllCompanys(Constance.VALID_YES);
			if (!companys.isEmpty()) {
				Company company = companys.get(0);
				context.setCompanyid(company.getCompanyid());
				context.setCompanyname(company.getCompanyname());
			}

			context.setUsername("管理员");
			context.setDeptname("系统管理员组");
			context.setPostname("系统管理岗");
		}

		// 系統权限
		List<Function> menus = null;
		if (context.isAdmin()) {
			menus = mapper.getAllFunctions();
			context.initUserOpration(menus);
		} else {
			menus = mapper.getUserFunctions(user.getUserguid());
			context.initUserOpration(menus);
		}
		return context;
	}

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	public void searchUser(GridServerHandler grid) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countUser(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<User> list = mapper.searchUser(grid);
		// 角色
		Map<String, String> roleMap = codeConvertNameService.getAllRoleMap();
		for (User model : list) {
			// 用户角色
			model.setRolename(roleMap.get(model.getUserguid()));
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}
	
	/**
	 * 通过角色搜索
	 * 
	 * @param grid
	 */
	public void searchUserByRole(GridServerHandler grid) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countUserByRole(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<User> list = mapper.searchUserByRole(grid);
		// 角色
		Map<String, String> roleMap = codeConvertNameService.getAllRoleMap();
		for (User model : list) {
			// 用户角色
			model.setRolename(roleMap.get(model.getUserguid()));
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}

	/**
	 * 全部公司部门树
	 * 
	 * @return
	 */
	public List<TreeNode> buildMyDepartmentTree() {
		// 数据
		List<Company> compays = companyService.getHasCompanys(Constance.VALID_YES);
		List<Department> depts = departmentService.getHasDepartment(Constance.VALID_YES);

		// 成树
		UserTree tree = new UserTree();
		return tree.doBuildDept(compays, depts);
	}

	/**
	 * 全部
	 * 
	 * @param valid
	 * @return
	 */
	public List<User> asyncUserTree(String deptid) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		Date postdate_s = new Date(System.currentTimeMillis());
		Date postdate_e = new Date(System.currentTimeMillis());
		return mapper.asyncUserTree(deptid, postdate_s, postdate_e);
	}

	/**
	 * 保存
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateUser(User model) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);

		model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		model.setModiuser(ContextFacade.getUserContext().getLoginname());
		if (StringUtils.isEmpty(model.getUserguid())) {
			model.setModimemo(Constance.add);
			model.setUserguid(UUIDGenerator.randomUUID());
			mapper.insertUser(model);
		} else {
			model.setModimemo(Constance.edit);
			mapper.updateUser(model);
		}
	}

	/**
	 * 重置密码
	 * 
	 * @param userguid
	 * @param pwd
	 */
	@Transactional
	public void updateUserPwd(String userguid, String pwd) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		User model = mapper.getUserByUserId(userguid);
		if (model != null) {
			model.setPassword(pwd);
			mapper.updateUser(model);
		}
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public User getUserById(String id) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		User model = mapper.getUserByUserId(id);
		if (model != null)
			model.convertOneCodeToName(employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param name
	 * @return
	 */
	public User getUserByLoginName(String name) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		return mapper.getUserByLoginName(name);
	}

	/**
	 * 删除
	 * 
	 * @param userguid
	 */
	@Transactional
	public void delUserById(String userguid) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		mapper.delUserManageRangeByUserId(userguid);
		mapper.delUserRoleByUserId(userguid);
		mapper.delUserByUserId(userguid);
	}

	/**
	 * 有效无效
	 * 
	 * @param userguid
	 * @param valid
	 */
	@Transactional
	public void validUserById(String userguid, Integer state) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		User model = mapper.getUserByUserId(userguid);
		if (model != null) {
			model.setState(state);
			mapper.updateUser(model);
		}
	}

	// ===============用户对角色赋权====================
	/**
	 * 用户对角色树
	 * 
	 * @param userguid
	 * @return
	 */
	public List<TreeNode> buildRoleCheckTree(String userguid) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);

		// 数据
		List<Role> roles = mapper.getAllRole();
		List<UserRole> checks = mapper.getUserRoleByUserId(userguid);

		// 构造成树
		RoleTree tree = new RoleTree();
		return tree.doUserCheckBuild(roles, checks);
	}

	/**
	 * 保存赋权
	 * 
	 * @param data
	 */
	@Transactional
	public void saveUserRole(UserRolePam data) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);

		mapper.delUserRoleByUserId(data.getUserguid());

		List<UserRole> list = data.getList();
		if (list != null && !list.isEmpty())
			for (UserRole model : list) {
				mapper.insertUserRole(model);
			}
	}

	// ================用户对公司赋权===================
	/**
	 * 用户对公司树
	 * 
	 * @param userguid
	 * @return
	 */
	public List<TreeNode> buildCompanyCheckTree(String userguid) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);

		// 数据
		List<Company> companys = companyDao.getAllCompanys(Constance.VALID_YES);
		List<UserManageRange> checks = mapper.getUserManageRangeByUserId(userguid);

		// 构造成树
		CompanyTree tree = new CompanyTree();
		return tree.doUserCheckBuild(companys, checks);
	}

	/**
	 * 保存赋权
	 * 
	 * @param data
	 */
	@Transactional
	public void saveUserCompany(UserManageRangePam data) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);

		mapper.delUserManageRangeByUserId(data.getUserguid());
		List<UserManageRange> list = data.getList();
		if (list != null && !list.isEmpty())
			for (UserManageRange model : list) {
				mapper.insertUserManageRange(model);
			}

	}

	/**
	 * 当前有权限的用户
	 * 
	 * @param state
	 * @return
	 */
	public List<User> getHasUsers(Integer state) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		// 数据
		UserContext uc = ContextFacade.getUserContext();
		List<User> list = null;
		if (uc.isAdmin()) {
			list = mapper.getAllUser();
		} else {
			list = mapper.getHasUsers(state, uc.getUserId());
		}

		// 角色
		Map<String, String> roleMap = codeConvertNameService.getAllRoleMap();
		// 批量
		if (list.size() >= Constance.ConvertCodeNum) {
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 職務
			Map<String, String> jobMap = codeConvertNameService.getAllJobMap();
			// 職級
			Map<String, String> rankMap = codeConvertNameService.getAllRankMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 編制
			Map<String, String> quotaMap = codeConvertNameService.getAllQuotaMap();

			for (User model : list) {
				model.convertManyCodeToName(employeeDao, companyMap, deptMap, jobMap, rankMap, postMap, quotaMap, roleMap);
			}
		} else {
			for (User model : list) {
				model.convertOneCodeToName(employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, roleMap);
			}
		}
		return list;

	}

	// ================用户对通讯录赋权===================

	/**
	 * 用户对通讯录
	 * 
	 * @param userguid
	 * @return
	 */
	public List<TreeNode> buildAddressCompanyCheckTree(String userguid) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);

		// 数据
		List<Company> companys = companyDao.getAllCompanys(Constance.VALID_YES);

		// 有權限的公司
		List<UserAddressCompany> checks = mapper.getUserAddressCompanyByUserId(userguid);
		if (!checks.isEmpty() && checks.size() == 1) {
			UserAddressCompany tmp = checks.get(0);
			if (StringUtils.isEmpty(tmp.getCompanyid())) {
				checks = new ArrayList<UserAddressCompany>();
				for (Company company : companys) {
					UserAddressCompany model = new UserAddressCompany(company, userguid);
					checks.add(model);
				}
			}
		}

		// 构造成树
		CompanyTree tree = new CompanyTree();
		return tree.doUserCheckAddressBuild(companys, checks);
	}

	/**
	 * 保存赋权
	 * 
	 * @param data
	 */
	@Transactional
	public void saveUserAddressCompany(UserAddressCompanyPam data) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		mapper.delUserAddressCompanyByUserId(data.getUserguid());
		List<UserAddressCompany> list = data.getList();
		if (list != null && !list.isEmpty())
			for (UserAddressCompany model : list) {
				mapper.insertUserAddressCompany(model);
			}

	}

	/**
	 * 用户对部门树
	 * 
	 * @param userguid
	 * @return
	 */

	public List<TreeNode> buildDepartmentCheckTree(String userguid) {
		DepartmentDao departmentMapper = sqlSession.getMapper(DepartmentDao.class);
		SystemDao userDeptMapper = sqlSession.getMapper(SystemDao.class);

		List<Company> compays = companyService.getHasCompanys(Constance.VALID_YES);
		List<Department> depts = departmentService.getHasDepartment(Constance.VALID_YES);
		List<UserDepartment> checks = userDeptMapper.getUserDepartmentByUserId(userguid);
		// 构造成树
		UserDepartmentTree tree = new UserDepartmentTree();
		return tree.doBuild(compays, depts, checks);
	}

	/**
	 * 保存赋权
	 * 
	 * @param userDepartment
	 */
	@Transactional
	public void saveUserDepartment(UserDepartmentPam date) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		mapper.delUserDepartmentByUserId(date.getUserguid());
		List<UserDepartment> list = date.getList();
		if (list != null && !list.isEmpty()) {
			for (UserDepartment model : list) {
				mapper.insertUserDepartment(model);
			}
		}
	}
}
