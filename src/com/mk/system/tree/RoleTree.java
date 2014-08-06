package com.mk.system.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mk.company.entity.Company;
import com.mk.department.entity.Department;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.system.entity.Role;
import com.mk.system.entity.RoleRight;
import com.mk.system.entity.User;
import com.mk.system.entity.UserRole;

public class RoleTree {
	/**
	 * 普通
	 * 
	 * @param data
	 * @return
	 */
	public List<TreeNode> doBuild(List<Role> data) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("全部角色");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);
		// 加载节点
		for (Role model : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getRoleid());
			treeNode.setName(model.getRolename());
			treeNode.setIconSkin("role");
			treeNode.setCode("role");
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}

	// ===================================================
	/**
	 * 用户对角色赋权
	 * 
	 * @param data
	 * @param checks
	 * @return
	 */
	public List<TreeNode> doUserCheckBuild(List<Role> data, List<UserRole> checks) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		Role model = null;
		TreeNode rootNode = new TreeNode();
		rootNode.setName("全部角色");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 打勾
		Map<String, UserRole> map = new HashMap<String, UserRole>();
		if (checks != null) {
			for (UserRole role : checks) {
				map.put(role.getRoleid(), role);
			}
			rootNode.setChecked(checks.isEmpty() ? false : true);
		}

		// 加载节点
		for (Iterator<Role> iter = data.iterator(); iter.hasNext();) {
			model = (Role) iter.next();
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getRoleid());
			treeNode.setName(model.getRolename());
			treeNode.setIconSkin("role");

			// 打勾
			if (map.get(model.getRoleid()) != null)
				treeNode.setChecked(true);
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}

	// ===================================================

	/**
	 * 菜单对角色赋权
	 * 
	 * @param data
	 * @param checks
	 * @return
	 */
	public List<TreeNode> doFunctionCheckBuild(List<Role> data, List<RoleRight> checks) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		Role model = null;
		TreeNode rootNode = new TreeNode();
		rootNode.setName("全部角色");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 打勾
		Map<String, RoleRight> map = new HashMap<String, RoleRight>();
		if (checks != null) {
			for (RoleRight role : checks) {
				map.put(role.getRoleid(), role);
			}
			rootNode.setChecked(checks.isEmpty() ? false : true);
		}

		// 加载节点
		for (Iterator<Role> iter = data.iterator(); iter.hasNext();) {
			model = (Role) iter.next();
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getRoleid());
			treeNode.setName(model.getRolename());
			treeNode.setIconSkin("role");

			// 打勾
			if (map.get(model.getRoleid()) != null)
				treeNode.setChecked(true);
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}

	// ===================================================

	/**
	 * 
	 * @param compays
	 * @param depts
	 * @param checks
	 * @return
	 */
	public List<TreeNode> dobuildRoleUserCheck(List<Company> compays, List<Department> depts) {
		// 公司分组
		Map<String, List<Department>> deptMap = new HashMap<String, List<Department>>();
		for (Department model : depts) {
			List<Department> tmplist = deptMap.get(model.getCompanyid());
			if (tmplist == null) {
				tmplist = new ArrayList<Department>();
				tmplist.add(model);
				deptMap.put(model.getCompanyid(), tmplist);
			} else {
				tmplist.add(model);
			}
		}

		// ==================================

		// 构造成树
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		Map<String, TreeNode> map = new HashMap<String, TreeNode>();

		TreeNode rootNode = new TreeNode();
		rootNode.setName("全部公司");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (Company model : compays) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getCompanyid());
			treeNode.setName(model.getCompanyname());
			treeNode.setCode("company");
			treeNode.setEname(model.getState().toString());
			treeNode.setIconSkin(model.isIsaudit() ? "company" : "companylock");
			treeNode.setOpen(true);
			map.put(model.getCompanyid(), treeNode);

			// 挂部门
			// 且有公司权限
			if (model.isIsaudit())
				initDeptTree(model, deptMap.get(model.getCompanyid()), treeNode);
		}

		TreeNode ptree = null;
		for (Company model : compays) {
			String idValue = model.getCompanyid();
			String pidValue = model.getPcompanyid();

			// 根
			if (StringUtils.isEmpty(pidValue)) {
				rootNode.getChildren().add(map.get(idValue));
			} else {
				ptree = (TreeNode) map.get(pidValue);
				if (ptree != null)
					ptree.getChildren().add(map.get(idValue));
			}
		}
		return nodes;
	}

	/**
	 * 加载部门树
	 * 
	 * @param regionid
	 * @param data
	 * @param rootNode
	 */
	private void initDeptTree(Company company, List<Department> data, TreeNode rootNode) {
		if (data == null || data.isEmpty())
			return;
		Map<String, TreeNode> map = new HashMap<String, TreeNode>();

		// 加载节点
		for (Department dept : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(dept.getDeptid());
			treeNode.setName(dept.getDeptname());
			treeNode.setpId(dept.getPdeptid());
			treeNode.setEname(dept.getCompanyid());
			treeNode.setMarker(company.getCompanyname());

			treeNode.setCompanycode(company.getCompanycode());
			treeNode.setDeptcode(dept.getDeptcode());

			treeNode.setState(dept.getState().toString());
			treeNode.setCode("dept");
			treeNode.setIconSkin("dept");

			treeNode.setOpen(false);
			map.put(dept.getDeptid(), treeNode);

			// 全部用户
			// 动态加载数据
			initUserTree(dept, treeNode);
		}

		TreeNode ptree = null;
		for (Department dept : data) {
			String idValue = dept.getDeptid();
			String pidValue = dept.getPdeptid();

			// 根
			if (StringUtils.isEmpty(pidValue)) {
				TreeNode treeNode = map.get(idValue);
				rootNode.getChildren().add(treeNode);
			} else {
				ptree = (TreeNode) map.get(pidValue);
				if (ptree != null)
					ptree.getChildren().add(map.get(idValue));
			}
		}
	}

	/**
	 * 用户节点
	 * 
	 * @param dept
	 * @param rootNode
	 */
	private void initUserTree(Department dept, TreeNode rootNode) {
		TreeNode treeNode = new TreeNode();
		treeNode.setName("用户");

		treeNode.setId(dept.getDeptid());
		treeNode.setpId(dept.getPdeptid());
		treeNode.setEname(dept.getCompanyid());

		treeNode.setIconSkin("groupuser");
		treeNode.setParent(true);

		rootNode.getChildren().add(treeNode);
	}

	/**
	 * 动态用户树
	 * 
	 * @param list
	 * @return
	 */
	public List<TreeNode> doAsyncbuild(List<User> list, List<UserRole> checks) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();

		// 缓存
		Map<String, UserRole> map = new HashMap<String, UserRole>();
		for (UserRole model : checks) {
			map.put(model.getUserguid(), model);
		}

		// 加载节点
		for (User model : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getUserguid());
			treeNode.setName(model.getUsername());
			treeNode.setCode("user");
			treeNode.setIconSkin("user");

			// 打勾
			if (map.get(model.getUserguid()) != null)
				treeNode.setChecked(true);

			nodes.add(treeNode);
		}
		return nodes;
	}

}
