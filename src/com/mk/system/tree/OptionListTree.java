package com.mk.system.tree;

import java.util.ArrayList;
import java.util.List;

import com.mk.framework.tree.TreeNode;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.OptionType;


public class OptionListTree {

	/**
	 * 选项树
	 * @param data
	 * @param type
	 * @return
	 */
	public List<TreeNode> doBuild(List<OptionList> data, OptionType type) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName(type.getName());
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (OptionList model : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getCode().toString());
			treeNode.setName(model.getName());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}
}
