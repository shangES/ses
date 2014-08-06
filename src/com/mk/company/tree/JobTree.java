package com.mk.company.tree;

import java.util.ArrayList;
import java.util.List;

import com.mk.company.entity.Company;
import com.mk.company.entity.Job;
import com.mk.framework.tree.TreeNode;

public class JobTree {

	public List<TreeNode> doBulid(Company company, List<Job> data) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName(company.getCompanyname());
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (Job model : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getJobid());
			treeNode.setName(model.getJobname());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}

}
