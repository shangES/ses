package com.mk.system.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mk.department.entity.Department;
import com.mk.framework.tree.TreeNode;

public class UserDepartment {

	private String userguid;
	private String deptguid;
	
	/**
	 * 用户对角色赋权
	 * 
	 * @param data
	 * @param checks
	 * @return
	 */
	public List<TreeNode> doUserCheckBuild(List<Department> data, List<UserDepartment> checks) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		Department model = null;
		TreeNode rootNode = new TreeNode();
		rootNode.setName("全部部门");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 打勾
		Map<String, UserDepartment> map = new HashMap<String, UserDepartment>();
		if (checks != null) {
			for (UserDepartment userDepartment : checks) {
				map.put(userDepartment.getUserguid(), userDepartment);
			}
			rootNode.setChecked(checks.isEmpty() ? false : true);
		}

		// 加载节点
		for (Iterator<Department> iter = data.iterator(); iter.hasNext();) {
			model = (Department) iter.next();
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getDeptid());
			treeNode.setName(model.getDeptid());
			treeNode.setIconSkin("role");

			// 打勾
			if (map.get(model.getDeptid()) != null)
				treeNode.setChecked(true);
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}

	public String getUserguid() {
		return userguid;
	}

	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}

	public String getDeptguid() {
		return deptguid;
	}

	public void setDeptguid(String deptguid) {
		this.deptguid = deptguid;
	}

}
