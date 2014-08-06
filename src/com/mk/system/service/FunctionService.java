package com.mk.system.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.constance.Constance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.Function;
import com.mk.system.entity.FunctionRolePam;
import com.mk.system.entity.Role;
import com.mk.system.entity.RoleRight;
import com.mk.system.tree.RoleTree;

@Service
public class FunctionService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 保存
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateFunction(Function model) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		if (StringUtils.isEmpty(model.getFunid())) {
			String maxId = mapper.getMaxFunctionByFunpid(model.getFunpid());
			String id = Constance.setSortCode(model.getFunpid(), maxId);
			model.setFunid(id);
			mapper.saveFunction(model);
		} else {
			mapper.updateFunction(model);
		}
	}

	/**
	 * 得到
	 * 
	 * @param funid
	 * @return
	 */
	public Function getFunctionById(String funid) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		return mapper.getFunctionById(funid);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	public void delFunctionById(String id) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		mapper.delFunctionRoleByFunctionId(id);
		mapper.delFunctionById(id);
	}

	/**
	 * 用户菜单
	 * 
	 * @return
	 */
	public List<Function> getUserFunctions() {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		UserContext uc = ContextFacade.getUserContext();
		if (uc.isAdmin())
			return mapper.getAllFunctions();
		if (uc != null && uc.getUserId() != null)
			return mapper.getUserFunctions(uc.getUserId());
		return null;
	}

	/**
	 * 全部菜单
	 * 
	 * @return
	 */
	public List<Function> getAllFunctions() {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		return mapper.getAllFunctions();
	}

	// ===============菜单对角色赋权====================
	/**
	 * 菜单对角色树
	 * 
	 * @param userguid
	 * @return
	 */
	public List<TreeNode> buildRoleCheckTree(String funid) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);

		// 数据
		List<Role> roles = mapper.getAllRole();
		List<RoleRight> checks = mapper.getFunctionRoleByFunctionId(funid);

		// 构造成树
		RoleTree tree = new RoleTree();
		return tree.doFunctionCheckBuild(roles, checks);
	}

	/**
	 * 保存赋权
	 * 
	 * @param data
	 */
	@Transactional
	public void saveFunctionRole(FunctionRolePam data) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		mapper.delFunctionRoleByFunctionId(data.getFunid());
		List<RoleRight> list = data.getList();
		if (list != null && !list.isEmpty())
			for (RoleRight model : list) {
				mapper.insertFunctionRole(model);
			}
	}

}
