package com.mk.employee.tree;

import java.util.ArrayList;
import java.util.List;

import com.mk.department.entity.Post;
import com.mk.employee.entity.Position;
import com.mk.framework.tree.TreeNode;

public class PositionTree {

	/**
	 * 用户树
	 * 
	 * @param data
	 * @return
	 */
	public List<TreeNode> doBuildUserTree(List<Post> data) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("岗位选择");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);
		// 加载节点
		for (Post model : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getPostid());
			treeNode.setName(model.getPostname());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}
}
