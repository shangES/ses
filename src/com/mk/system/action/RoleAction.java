package com.mk.system.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.tree.TreeNode;
import com.mk.system.entity.Role;
import com.mk.system.entity.RoleFunctionPam;
import com.mk.system.entity.RoleUserPam;
import com.mk.system.service.RoleService;
import com.mk.system.tree.RoleTree;
@Controller
public class RoleAction {

	@Autowired
	private RoleService roleService;

	/**
	 * 所有
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/buildRoleTree.do")
	@ResponseBody
	public List<TreeNode> buildRoleTree() {
		List<Role> data = roleService.getAllRole();
		RoleTree tree = new RoleTree();
		return tree.doBuild(data);
	}
	
	/**
	 * 选择部分
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/buildRoleTree1.do")
	@ResponseBody
	public List<TreeNode> buildRoleTree1() {
		List<Role> data = roleService.getOtherRole();
		RoleTree tree = new RoleTree();
		return tree.doBuild(data);
	}

	/**
	 * 保存
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/system/saveOrUpdateRole.do")
	@ResponseBody
	public Role saveOrUpdateRole(Role model) throws Exception {
		roleService.saveOrUpdateRole(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/system/getRoleById.do")
	@ResponseBody
	public Role getRoleById(String id) {
		Role model = roleService.getRoleById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@RequestMapping("/system/delRoleById.do")
	@ResponseBody
	public void delRoleById(String id) {
		roleService.delRoleById(id);
	}

	// ===================角色对菜单赋权===================
	/**
	 * 角色对菜单树
	 * 
	 * @param roleid
	 * @return
	 */
	@RequestMapping("/system/buildRoleRightTree.do")
	@ResponseBody
	public List<TreeNode> buildRoleRightTree(String roleid) {
		return roleService.buildRoleRightTree(roleid);
	}

	/**
	 * 保存赋权
	 * 
	 * @param data
	 * @throws Exception
	 */
	@RequestMapping("/system/saveRoleFunction.do")
	@ResponseBody
	public void saveRoleFunction(@RequestBody RoleFunctionPam data) throws Exception {
		roleService.saveRoleFunction(data);
	}

	// ===================角色对用户赋权===================
	/**
	 * 角色对用户
	 * 
	 * @param roleid
	 * @return
	 */
	@RequestMapping("/system/buildRoleUserTree.do")
	@ResponseBody
	public List<TreeNode> buildRoleUserTree() {
		return roleService.buildRoleUserTree();
	}

	/**
	 * 动态用户树
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/system/asyncRoleUserTree.do")
	@ResponseBody
	public List<TreeNode> asyncRoleUserTree(String deptid, String roleid) throws Exception {
		return roleService.asyncRoleUserTree(deptid, roleid);
	}

	/**
	 * 保存赋权
	 * 
	 * @param data
	 * @throws Exception
	 */
	@RequestMapping("/system/saveRoleUser.do")
	@ResponseBody
	public void saveRoleUser(@RequestBody RoleUserPam data) throws Exception {
		roleService.saveRoleUser(data);
	}
}
