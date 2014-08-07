package com.mk.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.constance.Constance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.model.ColumnInfo;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.tree.TreeNode;
import com.mk.system.entity.User;
import com.mk.system.entity.UserAddressCompanyPam;
import com.mk.system.entity.UserDepartmentPam;
import com.mk.system.entity.UserManageRangePam;
import com.mk.system.entity.UserRolePam;
import com.mk.system.service.UserService;
import com.mk.system.tree.UserTree;

@Controller
public class UserAction {
	@Autowired
	private UserService userService;

	/**
	 * 导出用户
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/system/exportUser.do")
	public void exportCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		List<User> list = userService.getHasUsers(Constance.VALID_YES);
		// 参数设置
		Map<Object, Object> parameterMap = new HashMap<Object, Object>();
		parameterMap.put("exportFileName", new String[] { "用户列表.xls" });
		grid.setParameterMap(parameterMap);

		// 列
		List<ColumnInfo> columns = User.initExcelColumn();
		grid.setColumnInfo(columns);

		// 数据
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (User model : list) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);

		ReportUtil report = new ReportUtil();
		report.setTitle("用户列表");
		report.reportGrid(grid);
	}

	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/searchUser.do")
	public void searchUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		userService.searchUser(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("系统用户列表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();

	}
	
	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/searchUserByRole.do")
	public void searchUserByRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		userService.searchUserByRole(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("系统用户列表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();

	}
	
	/**
	 * 全部公司部门树
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/system/buildMyDepartmentTree.do")
	@ResponseBody
	public List<TreeNode> buildMyDepartmentTree() throws Exception {
		return userService.buildMyDepartmentTree();
	}

	/**
	 * 动态用户树
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/system/asyncUserTree.do")
	@ResponseBody
	public List<TreeNode> asyncUserTree(String deptid) throws Exception {
		List<User> users = userService.asyncUserTree(deptid);
		UserTree tree = new UserTree();
		return tree.doAsyncbuild(users);
	}

	/**
	 * 保存
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/saveOrUpdateUser.do")
	@ResponseBody
	public User saveOrUpdateUser(User model) throws Exception {
		userService.saveOrUpdateUser(model);
		return model;
	}

	/**
	 * 重置密码
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/updateUserPwd.do")
	@ResponseBody
	public void updateUserPwd(String userguid, String pwd) throws Exception {
		if (ContextFacade.getUserContext().getUserId().equals(userguid))
			userService.updateUserPwd(userguid, pwd);
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/getUserByLoginName.do")
	@ResponseBody
	public User getUserByLoginName(String name) throws Exception {
		return userService.getUserByLoginName(name);
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/getUserById.do")
	@ResponseBody
	public User getUserById(String id) throws Exception {
		User model = userService.getUserById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/delUserById.do")
	@ResponseBody
	public void delUserById(String userguid) throws Exception {
		userService.delUserById(userguid);
	}

	/**
	 * 有效无效
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/validUserById.do")
	@ResponseBody
	public void validUserById(String userguid, Integer valid) throws Exception {
		userService.validUserById(userguid, valid);
	}

	// ===============用户对角色赋权====================

	/**
	 * 用户对角色树
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/system/buildRoleCheckTree.do")
	@ResponseBody
	public List<TreeNode> buildRoleCheckTree(String userguid) throws Exception {
		return userService.buildRoleCheckTree(userguid);
	}

	/**
	 * 保存赋权
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/saveUserRole.do")
	@ResponseBody
	public void saveUserRole(@RequestBody UserRolePam data) throws Exception {
		userService.saveUserRole(data);
	}

	// ==================用户对公司赋权===================

	/**
	 * 用户对公司树
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/system/buildCompanyCheckTree.do")
	@ResponseBody
	public List<TreeNode> buildCompanyCheckTree(String userguid) throws Exception {
		return userService.buildCompanyCheckTree(userguid);
	}

	/**
	 * 保存赋权
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/saveUserCompany.do")
	@ResponseBody
	public void saveUserCompany(@RequestBody UserManageRangePam data) throws Exception {
		userService.saveUserCompany(data);
	}

	// ==================用户对通讯录赋权===================
	/**
	 * 用户对通讯录
	 * 
	 * @param userguid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/system/buildAddressCompanyCheckTree.do")
	@ResponseBody
	public List<TreeNode> buildAddressCompanyCheckTree(String userguid) throws Exception {
		return userService.buildAddressCompanyCheckTree(userguid);
	}

	/**
	 * 保存赋权
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/saveUserAddressCompany.do")
	@ResponseBody
	public void saveUserAddressCompany(@RequestBody UserAddressCompanyPam data) throws Exception {
		userService.saveUserAddressCompany(data);
	}
	
	// ==================用户对部门赋权===================

		/**
		 * 用户对部门树
		 * 
		 * @param pid
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/system/buildUserDepartmentCheckTree.do")
		@ResponseBody
		public List<TreeNode> buildUserDepartmentCheckTree(String userguid) throws Exception {
			return userService.buildDepartmentCheckTree(userguid);
		}
		
		/**
		 * 保存赋权
		 * 
		 * @param data
		 * @throws Exception
		 */
		@RequestMapping("/system/saveUserDepartment.do")
		@ResponseBody
		public void saveUserDepartment(@RequestBody UserDepartmentPam data)throws Exception{
			userService.saveUserDepartment(data);
		}

}
