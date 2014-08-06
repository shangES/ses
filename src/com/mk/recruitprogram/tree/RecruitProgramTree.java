package com.mk.recruitprogram.tree;

import java.util.ArrayList;
import java.util.List;

import com.mk.recruitprogram.entity.RecruitProgram;
import com.mk.framework.tree.TreeNode;

public class RecruitProgramTree {

	/**
	 * 计划管理树
	 * 
	 * @param list
	 * @return
	 */
	public List<TreeNode> dobuild(List<RecruitProgram> data) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("招聘计划管理");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (RecruitProgram model : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getRecruitprogramguid());
			treeNode.setName(model.getDeptname()+ "/" + model.getPostname());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}
}
