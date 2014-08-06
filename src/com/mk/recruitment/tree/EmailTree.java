package com.mk.recruitment.tree;

import java.util.ArrayList;
import java.util.List;

import com.mk.framework.tree.TreeNode;
import com.mk.recruitment.entity.WebUser;

public class EmailTree {
	
	public List<TreeNode> doBuild(List<WebUser> data) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("邮箱选择");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);
		// 加载节点
		for (WebUser model : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getWebuserguid());
			treeNode.setName(model.getEmail());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}
	
}
