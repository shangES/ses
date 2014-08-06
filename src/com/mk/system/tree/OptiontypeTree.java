package com.mk.system.tree;

import java.util.List;

import com.mk.framework.tree.TreeNode;
import com.mk.system.entity.OptionType;


public class OptiontypeTree {
	
	/**
	 * 构建全部的选项类型的树
	 * @param list
	 * @return
	 */
	public Object doBuild(List<OptionType> list){
		TreeNode treeNode=new TreeNode();
		treeNode.setName("选项类型");
		treeNode.setOpen(true);
		treeNode.setIconSkin("root");
		
		//加载节点
		for(OptionType model : list){
			TreeNode rootNode = new TreeNode();
			rootNode.setId(model.getOptiontypeguid());
			rootNode.setName(model.getName());
			treeNode.getChildren().add(rootNode);
		}
		return treeNode;
	}
}
