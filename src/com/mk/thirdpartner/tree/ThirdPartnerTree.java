package com.mk.thirdpartner.tree;

import java.util.ArrayList;
import java.util.List;

import com.mk.framework.constance.Constance;
import com.mk.framework.tree.TreeNode;
import com.mk.recruitment.entity.WebUser;
import com.mk.thirdpartner.entity.ThirdPartner;

public class ThirdPartnerTree {

	/**
	 * 第三方机构管理树
	 * 
	 * @param list
	 * @return
	 */
	public List<TreeNode> doBuild(List<ThirdPartner> list) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("全部机构");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 体检机构
		TreeNode treeNode1 = new TreeNode();
		treeNode1.setName("体检机构");
		treeNode1.setCode(String.valueOf(Constance.THIRDPARTNERTYPE_FIRST));
		treeNode1.setEname("company");
		treeNode1.setOpen(true);
		treeNode1.setIconSkin("company");
		rootNode.getChildren().add(treeNode1);

		// 猎头公司
		TreeNode treeNode2 = new TreeNode();
		treeNode2.setName("猎头公司");
		treeNode2.setCode(String.valueOf(Constance.THIRDPARTNERTYPE_SECOND));
		treeNode2.setEname("company");
		treeNode2.setOpen(true);
		treeNode2.setIconSkin("company");
		rootNode.getChildren().add(treeNode2);

		for (ThirdPartner model : list) {
			TreeNode treeNod = new TreeNode();
			treeNod.setId(model.getThirdpartnerguid());
			treeNod.setName(model.getThirdpartnername());
			treeNod.setEname("dept");
			treeNod.setIconSkin("dept");
			treeNod.setCode(String.valueOf(model.getThirdpartytype()));
			//treeNod.setCode(String.valueOf(model.getThirdpartytype()));

			// 体检机构
			if (model.getThirdpartytype().equals(Constance.THIRDPARTNERTYPE_FIRST)) {
				treeNode1.getChildren().add(treeNod);
			} else if (model.getThirdpartytype().equals(Constance.THIRDPARTNERTYPE_SECOND)) {
				// 猎头公司
				treeNode2.getChildren().add(treeNod);
			}
		}

		return nodes;
	}

	public List<TreeNode> doThirdPartnerWebUserBuild(List<ThirdPartner> list, List<WebUser> webList) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("全部机构");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 体检机构
		TreeNode treeNode1 = new TreeNode();
		treeNode1.setName("体检机构");
		treeNode1.setCode(String.valueOf(Constance.THIRDPARTNERTYPE_FIRST));
		treeNode1.setOpen(true);
		treeNode1.setIconSkin("company");
		rootNode.getChildren().add(treeNode1);

		// 猎头公司
		TreeNode treeNode2 = new TreeNode();
		treeNode2.setName("猎头公司");
		treeNode2.setCode(String.valueOf(Constance.THIRDPARTNERTYPE_SECOND));
		treeNode2.setOpen(true);
		treeNode2.setIconSkin("company");
		rootNode.getChildren().add(treeNode2);

		for (ThirdPartner model : list) {
			TreeNode treeNod = new TreeNode();
			treeNod.setId(model.getThirdpartnerguid());
			treeNod.setName(model.getThirdpartnername());
			treeNod.setCode(String.valueOf(model.getThirdpartytype()));
			initDeptTree(model, webList, treeNod);

			// 体检机构
			if (model.getThirdpartytype().equals(Constance.THIRDPARTNERTYPE_FIRST)) {
				treeNode1.getChildren().add(treeNod);
			} else if (model.getThirdpartytype().equals(Constance.THIRDPARTNERTYPE_SECOND)) {
				// 猎头公司
				treeNode2.getChildren().add(treeNod);
			}
		}

		return nodes;
	}

	/**
	 * 加载用户树
	 * 
	 * @param model
	 * @param webList
	 * @param rootNode
	 */
	public void initDeptTree(ThirdPartner model, List<WebUser> webList, TreeNode rootNode) {
		if (webList == null || webList.isEmpty())
			return;
		for (WebUser webUser : webList) {
			TreeNode treeNod = new TreeNode();
			treeNod.setId(webUser.getWebuserguid());
			treeNod.setName(webUser.getUsername());
			treeNod.setCode(webUser.getThirdpartnerguid());
			if (model.getThirdpartnerguid().equals(webUser.getThirdpartnerguid())) {
				rootNode.getChildren().add(treeNod);
			}
		}
	}

	/**
	 * 体检机构管理树
	 * 
	 * @param list
	 * @return
	 */
	public List<TreeNode> doBuildPartner(List<ThirdPartner> list) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("全部体检机构");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);
		for (ThirdPartner model : list) {
			TreeNode treeNod = new TreeNode();
			treeNod.setId(model.getThirdpartnerguid());
			treeNod.setName(model.getThirdpartnername());
			treeNod.setCode(String.valueOf(model.getThirdpartytype()));

			// 体检机构
			if (model.getThirdpartytype().equals(Constance.THIRDPARTNERTYPE_FIRST)) {
				rootNode.getChildren().add(treeNod);
			}
		}

		return nodes;
	}
}
