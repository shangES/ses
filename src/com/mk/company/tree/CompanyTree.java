package com.mk.company.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mk.company.entity.Company;
import com.mk.framework.constance.Constance;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.system.entity.UserAddressCompany;
import com.mk.system.entity.UserManageRange;

public class CompanyTree {

	public List<TreeNode> doBuild(List<Company> list) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("全部公司");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		Map<String, TreeNode> map = new HashMap<String, TreeNode>();
		// 加载节点
		for (Company model : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getCompanyid());
			treeNode.setName(model.getCompanyname());
			treeNode.setMarker(model.getCompanycode());
			treeNode.setCode("company");
			treeNode.setEname(String.valueOf(model.getState()));
			// 无效
			if (model.getState().equals(Constance.VALID_NO)) {
				treeNode.setIconSkin("remove");
			} else {
				treeNode.setIconSkin(model.isIsaudit() ? "company" : "companylock");
			}

			treeNode.setOpen(false);
			treeNode.setDrag(true);
			treeNode.setIsaudit(model.isIsaudit());
			map.put(model.getCompanyid(), treeNode);
		}

		TreeNode ptree = null;
		for (Company model : list) {
			String idValue = model.getCompanyid();
			String pidValue = model.getPcompanyid();

			// 根
			if (StringUtils.isEmpty(pidValue)) {
				rootNode.getChildren().add(map.get(idValue));
			} else {
				ptree = (TreeNode) map.get(pidValue);

				if (ptree != null)
					ptree.getChildren().add(map.get(idValue));
			}
		}

		return nodes;
	}

	/**
	 * 用户对公司赋权
	 * 
	 * @param companys
	 * @param checks
	 * @return
	 */
	public List<TreeNode> doUserCheckBuild(List<Company> list, List<UserManageRange> checks) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("全部公司");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 打勾
		Map<String, UserManageRange> checkMap = new HashMap<String, UserManageRange>();
		if (checks != null) {
			for (UserManageRange model : checks) {
				checkMap.put(model.getCompanyid(), model);
			}
		}

		Map<String, TreeNode> map = new HashMap<String, TreeNode>();
		// 加载节点
		for (Company model : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getCompanyid());
			treeNode.setName(model.getCompanyname());
			treeNode.setCode("company");
			treeNode.setEname(String.valueOf(model.getState()));
			treeNode.setIconSkin(model.isIsaudit() ? "company" : "companylock");

			// 打勾
			if (checkMap.get(model.getCompanyid()) != null)
				treeNode.setChecked(true);

			treeNode.setOpen(true);
			map.put(model.getCompanyid(), treeNode);
		}

		TreeNode ptree = null;
		for (Company model : list) {
			String idValue = model.getCompanyid();
			String pidValue = model.getPcompanyid();

			// 根
			if (StringUtils.isEmpty(pidValue)) {
				rootNode.getChildren().add(map.get(idValue));
			} else {
				ptree = (TreeNode) map.get(pidValue);

				if (ptree != null)
					ptree.getChildren().add(map.get(idValue));
			}
		}

		return nodes;
	}

	/**
	 * 用户对通讯录
	 * 
	 * @param list
	 * @param checks
	 * @return
	 */
	public List<TreeNode> doUserCheckAddressBuild(List<Company> list, List<UserAddressCompany> checks) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("全部公司");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 打勾
		Map<String, UserAddressCompany> checkMap = new HashMap<String, UserAddressCompany>();
		if (checks != null) {
			for (UserAddressCompany model : checks) {
				checkMap.put(model.getCompanyid(), model);
			}
			rootNode.setChecked(true);
		}

		Map<String, TreeNode> map = new HashMap<String, TreeNode>();
		// 加载节点
		for (Company model : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getCompanyid());
			treeNode.setName(model.getCompanyname());
			treeNode.setCode("company");
			treeNode.setEname(String.valueOf(model.getState()));
			treeNode.setIconSkin(model.isIsaudit() ? "company" : "companylock");

			// 打勾
			if (checkMap.get(model.getCompanyid()) != null)
				treeNode.setChecked(true);

			treeNode.setOpen(true);
			map.put(model.getCompanyid(), treeNode);
		}

		TreeNode ptree = null;
		for (Company model : list) {
			String idValue = model.getCompanyid();
			String pidValue = model.getPcompanyid();

			// 根
			if (StringUtils.isEmpty(pidValue)) {
				rootNode.getChildren().add(map.get(idValue));
			} else {
				ptree = (TreeNode) map.get(pidValue);

				if (ptree != null)
					ptree.getChildren().add(map.get(idValue));
			}
		}

		return nodes;
	}
}
