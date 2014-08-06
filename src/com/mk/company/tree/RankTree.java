package com.mk.company.tree;

import java.util.ArrayList;
import java.util.List;

import com.mk.company.entity.Company;
import com.mk.company.entity.Rank;
import com.mk.framework.tree.TreeNode;

public class RankTree {
	public List<TreeNode> doBulid(Company company, List<Rank> data) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName(company.getCompanyname());
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (Rank model : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getRankid());
			treeNode.setName(model.getLevelname());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}
}
