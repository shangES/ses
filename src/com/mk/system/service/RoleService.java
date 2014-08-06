package com.mk.system.service;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.company.entity.Company;
import com.mk.company.service.CompanyService;
import com.mk.department.entity.Department;
import com.mk.department.service.DepartmentService;
import com.mk.framework.constance.Constance;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.Function;
import com.mk.system.entity.Role;
import com.mk.system.entity.RoleFunctionPam;
import com.mk.system.entity.RoleRight;
import com.mk.system.entity.RoleUserPam;
import com.mk.system.entity.User;
import com.mk.system.entity.UserRole;
import com.mk.system.tree.FunctionTree;
import com.mk.system.tree.RoleTree;

@Service
public class RoleService {

	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private DepartmentService departmentService;

	/**
	 * 全部
	 * 
	 * @return
	 */
	public List<Role> getAllRole() {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		return mapper.getAllRole();
	}

	/**
	 * 选择部分
	 * 
	 * @return
	 */
	public List<Role> getOtherRole() {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		return mapper.getOtherRole();
	}
	/**
	 * 保存
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateRole(Role model) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		if (StringUtils.isEmpty(model.getRoleid())) {
			model.setRoleid(UUIDGenerator.randomUUID());
			mapper.insertRole(model);
		} else {
			mapper.updateRole(model);
		}
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Role getRoleById(String id) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		Role model = mapper.getRoleById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public void delRoleById(String id) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		mapper.delFunctionRoleByRoleId(id);
		mapper.delUserRoleByRoleId(id);
		mapper.delRoleById(id);
	}

	// ===================角色对菜单赋权===================
	/**
	 * 角色对菜单树
	 * 
	 * @param roleid
	 * @return
	 */
	public List<TreeNode> buildRoleRightTree(String roleid) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		List<Function> funs = mapper.getAllFunctions();
		List<RoleRight> checks = mapper.getFunctionRoleByRoleId(roleid);
		FunctionTree tree = new FunctionTree();
		return tree.dobuildCheck(funs, checks);
	}

	/**
	 * 赋权
	 * 
	 * @param data
	 */
	public void saveRoleFunction(RoleFunctionPam data) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		mapper.delFunctionRoleByRoleId(data.getRoleid());
		List<RoleRight> checks = data.getList();
		if (checks != null && !checks.isEmpty()) {
			for (RoleRight model : checks) {
				mapper.insertFunctionRole(model);
			}
		}
	}

	// ===================角色对用户赋权===================
	/**
	 * 角色对用户树
	 * 
	 * @param roleid
	 * @return
	 */
	public List<TreeNode> buildRoleUserTree() {
		// 数据
		List<Company> compays = companyService.getHasCompanys(Constance.VALID_YES);
		List<Department> depts = departmentService.getHasDepartment(Constance.VALID_YES);

		RoleTree tree = new RoleTree();

		return tree.dobuildRoleUserCheck(compays, depts);
	}

	/**
	 * 赋权
	 * 
	 * @param data
	 */
	@Transactional
	public void saveRoleUser(RoleUserPam data) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		List<UserRole> checks = data.getList();
		if (checks != null && !checks.isEmpty()) {
			for (UserRole model : checks) {
				mapper.delUserRoleByUserRole(model);

				if (model.isChecked()) {
					mapper.insertUserRole(model);
				}
			}
		}
	}

	/**
	 * 全部
	 * 
	 * @param valid
	 * @return
	 */
	public List<TreeNode> asyncRoleUserTree(String deptid, String roleid) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);

		Date postdate_s = new Date(System.currentTimeMillis());
		Date postdate_e = new Date(System.currentTimeMillis());
		List<User> users = mapper.asyncUserTree(deptid, postdate_s, postdate_e);
		List<UserRole> checks = mapper.getUserRoleByRoleId(roleid);

		RoleTree tree = new RoleTree();
		return tree.doAsyncbuild(users, checks);
	}

}
