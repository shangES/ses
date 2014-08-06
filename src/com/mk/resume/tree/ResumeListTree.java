package com.mk.resume.tree;

import java.util.ArrayList;
import java.util.List;

import com.mk.framework.tree.TreeNode;
import com.mk.resume.entity.Resume;

public class ResumeListTree {

	/**
	 * 简历信息树
	 * 
	 * @param data
	 * @param type
	 * @return
	 */
	public List<TreeNode> doBuild(List<Resume> data) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("人员信息树");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (Resume model : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getMobile());
			treeNode.setName(model.getName());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}
}
