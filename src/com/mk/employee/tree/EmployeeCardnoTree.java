package com.mk.employee.tree;

import java.util.ArrayList;
import java.util.List;

import com.mk.employee.entity.Employee;
import com.mk.framework.tree.TreeNode;

public class EmployeeCardnoTree {

	/**
	 * 员工选择
	 * 
	 * @param data
	 * @return
	 */
	public List<TreeNode> doBuild(List<Employee> data) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("员工选择");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);
		// 加载节点
		for (Employee model : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getEmployeeid());
			treeNode.setName(model.getName() + "(" + model.getDeptname()+"："+model.getPostname()+ ")");
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}

	/**
	 * 用户树
	 * 
	 * @param data
	 * @return
	 */
	public List<TreeNode> doBuildUserTree(List<Employee> data) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("用户选择");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);
		// 加载节点
		for (Employee model : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getUserguid());
			treeNode.setpId(model.getCompanyid());
			treeNode.setMarker(model.getDeptid());
			treeNode.setCompanyname(model.getCompanyname());
			treeNode.setDeptname(model.getDeptname());
			treeNode.setName(model.getName() + "(" + model.getDeptname()+"："+model.getPostname()+ ")");
			treeNode.setEname(model.getName());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}

}
