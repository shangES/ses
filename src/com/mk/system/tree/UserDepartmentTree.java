package com.mk.system.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mk.company.entity.Company;
import com.mk.department.entity.Department;
import com.mk.framework.constance.Constance;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.system.entity.Role;
import com.mk.system.entity.UserAddressCompany;
import com.mk.system.entity.UserDepartment;
import com.mk.system.entity.UserRole;

public class UserDepartmentTree {

	/**
	 * 公司部门树
	 * 
	 * @param compays
	 * @param depts
	 * @return
	 */
	public List<TreeNode> doBuild(List<Company> compays, List<Department> depts, List<UserDepartment> checks) {
		if (compays == null || depts == null)
			return null;
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

		// 打勾
				Map<String, UserDepartment> checkMap = new HashMap<String, UserDepartment>();
				if (checks != null) {
					for (UserDepartment model : checks) {
						checkMap.put(model.getDeptguid(), model);
					}
					rootNode.setChecked(true);
				}
		
		// 加载节点
		for (Company model : compays) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getCompanyid());
			treeNode.setName(model.getCompanyname());
			treeNode.setCode("company");
			treeNode.setEname(model.getState().toString());
			treeNode.setCompanycode(model.getCompanycode());
			treeNode.setIconSkin(model.isIsaudit() ? "company" : "companylock");
			treeNode.setOpen(false);
			treeNode.setDrag(false);
			treeNode.setIsaudit(model.isIsaudit());
			map.put(model.getCompanyid(), treeNode);

			// 挂部门
			if (model.isIsaudit())
				initDeptTree(model, deptMap.get(model.getCompanyid()), treeNode,checkMap);
		
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
	private void initDeptTree(Company company, List<Department> data, TreeNode rootNode,Map<String, UserDepartment> checkMap) {
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
			treeNode.setDrag(true);
			
			// 打勾
			if (checkMap.get(dept.getDeptid()) != null)
				treeNode.setChecked(true);

				treeNode.setOpen(true);
					map.put(dept.getDeptid(), treeNode);
					
			// 无效
			if (dept.getState().equals(Constance.VALID_NO)) {
				treeNode.setIconSkin("remove");
			} else
				treeNode.setIconSkin("dept");
			map.put(dept.getDeptid(), treeNode);
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
}
