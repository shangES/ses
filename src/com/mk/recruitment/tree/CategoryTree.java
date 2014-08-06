package com.mk.recruitment.tree;

import java.util.ArrayList;
import java.util.List;

import com.mk.framework.tree.TreeNode;
import com.mk.recruitment.entity.Category;

public class CategoryTree {
	/**
	 * 职位类别树
	 * 
	 * @param list
	 * @return
	 */
	public List<TreeNode> dobuildAllCategory(List<Category> list) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("职位类别");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (Category model : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getCategoryguid());
			treeNode.setName(model.getCategoryname());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}
}
