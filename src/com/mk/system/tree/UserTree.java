package com.mk.system.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mk.company.entity.Company;
import com.mk.department.entity.Department;
import com.mk.framework.constance.Constance;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.system.entity.User;

public class UserTree {

	/**
	 * 用户部门树
	 * 
	 * @param compays
	 * @param depts
	 * @return
	 */
	public List<TreeNode> doBuildDept(List<Company> compays, List<Department> depts) {
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

	// ===========================================================

	/**
	 * 动态用户树
	 * 
	 * @param list
	 * @return
	 */
	public List<TreeNode> doAsyncbuild(List<User> list) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		// 加载节点
		for (User model : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getUserguid());
			treeNode.setName(model.getUsername());
			treeNode.setCode("user");
			treeNode.setEname(String.valueOf(model.getState()));

			// 无效
			if (model.getState().equals(Constance.VALID_NO)) {
				treeNode.setIconSkin("remove");
			} else
				treeNode.setIconSkin("user");

			nodes.add(treeNode);
		}
		return nodes;
	}

}
