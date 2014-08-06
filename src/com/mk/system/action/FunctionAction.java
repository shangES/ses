package com.mk.system.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.tree.TreeNode;
import com.mk.system.entity.Function;
import com.mk.system.entity.FunctionRolePam;
import com.mk.system.service.FunctionService;
import com.mk.system.tree.FunctionTree;

@Controller
public class FunctionAction {
	@Autowired
	private FunctionService functionService;

	/**
	 * 加载全部菜单
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/system/buildFunctionTree.do")
	@ResponseBody
	public List<TreeNode> buildFunctionTree() throws Exception {
		List<Function> data = functionService.getAllFunctions();
		FunctionTree tree = new FunctionTree();
		return tree.doBuild(data);
	}

	/**
	 * 登陆菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/system/getUserFunctions.do")
	@ResponseBody
	public List<Function> getUserFunctions() throws Exception {
		return functionService.getUserFunctions();
	}

	/**
	 * 得到
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/system/getFunctionById.do")
	@ResponseBody
	public Function getFunctionById(String id) throws Exception {
		Function data = functionService.getFunctionById(id);
		return data;
	}

	/**
	 * 保存
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/saveOrUpdateFunction.do")
	@ResponseBody
	public Function saveOrUpdateFunction(Function model) throws Exception {
		functionService.saveOrUpdateFunction(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/system/delFunctionById.do")
	@ResponseBody
	public void delFunctionById(String id) throws Exception {
		functionService.delFunctionById(id);
	}

	// ===============用户对角色赋权====================

	/**
	 * 用户对角色树
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/system/buildFuntionRoleCheckTree.do")
	@ResponseBody
	public List<TreeNode> buildRoleCheckTree(String funid) throws Exception {
		return functionService.buildRoleCheckTree(funid);
	}

	/**
	 * 保存赋权
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/saveFunctionRole.do")
	@ResponseBody
	public void saveFunctionRole(@RequestBody FunctionRolePam data) throws Exception {
		functionService.saveFunctionRole(data);
	}
}
