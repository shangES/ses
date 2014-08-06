package com.mk.recruitment.tree;

import java.util.ArrayList;
import java.util.List;

import com.mk.audition.entity.AuditionAddress;
import com.mk.framework.tree.TreeNode;
import com.mk.recruitment.entity.WorkPlace;

public class WorkPlaceTree {
	/**
	 * 工作地点树
	 * 
	 * @param list
	 * @return
	 */
	public List<TreeNode> dobuildAllWorkPlace(List<WorkPlace> list) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("工作地点");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (WorkPlace model : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getWorkplaceguid());
			treeNode.setName(model.getWorkplacename());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}

	/**
	 * 面试地点树
	 * 
	 * @param list
	 * @return
	 */
	public List<TreeNode> dobuildAllAuditionAddress(List<AuditionAddress> list) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("面试地点");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (AuditionAddress model : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getAuditionaddressguid());
			treeNode.setName(model.getAuditionaddress());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}
}
