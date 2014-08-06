package com.mk.resume.tree;

import java.util.ArrayList;
import java.util.List;

import com.mk.framework.tree.TreeNode;
import com.mk.recruitment.entity.RecruitPost;
import com.mk.system.entity.User;

public class RecruiterTree {
	/**
	 * 招聘职位树
	 * 
	 * @param list
	 * @return
	 */
	public List<TreeNode> dobuild(List<User> list) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("招聘专员");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (User model : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getUserguid());
			treeNode.setName(model.getUsername());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}
	
	/**
	 * 职位类别树
	 * 
	 * @param list
	 * @return
	 */
	public List<TreeNode> dobuildPostNameTree(List<RecruitPost> list) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("所有职务");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (RecruitPost model : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setName(model.getPostname());
			treeNode.setId(model.getRecruitpostguid());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}
}
