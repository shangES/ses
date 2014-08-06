package com.mk.quota.tree;

import java.util.ArrayList;
import java.util.List;

import com.mk.company.entity.Budgetype;
import com.mk.framework.tree.TreeNode;
import com.mk.quota.entity.Quota;


public class TQuotaTree {

	/**
	 * 岗位编制树
	 * 
	 * @param list
	 * @return
	 */
	public List<TreeNode> dobuild(List<Quota> data) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("所有编制");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (Quota model : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getQuotaid());
			//编号+编制类型+缺编人数
			treeNode.setName(model.getQuotacode()+"--"+model.getBudgettypename()+"--("+model.getVacancynumber()+")");
			treeNode.setCode(model.getQuotacode());
			treeNode.setpId(model.getPostid());
			treeNode.setEname(model.getPostname());
			treeNode.setNum(model.getBudgetnumber());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}
	
	/**
	 * 公司下的编制树
	 * 
	 * @param list
	 * @return
	 */
	public List<TreeNode> buildQuotaTreeByCompanyid(List<Budgetype> data) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("所有编制");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (Budgetype model : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getBudgettypeid());
			treeNode.setName(model.getBudgettypename());
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}
}
