package com.mk.system.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mk.framework.constance.Constance;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.system.entity.Function;
import com.mk.system.entity.RoleRight;
import com.mk.system.entity.User;
import com.mk.system.entity.UserRole;

public class FunctionTree {
	public List<TreeNode> doBuild(List<Function> list) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("系统菜单");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		Map<String, TreeNode> map = new HashMap<String, TreeNode>();
		// 加载节点
		for (Function model : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getFunid());
			treeNode.setName(model.getLabelname());
			treeNode.setCode("fun");
			if (model.getFuntype().equals(Constance.funTypeMenu))
				treeNode.setIconSkin("fun");
			else
				treeNode.setIconSkin("fun_button");
			treeNode.setOpen(true);
			map.put(model.getFunid(), treeNode);
		}

		TreeNode ptree = null;
		for (Function model : list) {
			String idValue = model.getFunid();
			String pidValue = model.getFunpid();

			// 根
			if (StringUtils.isEmpty(pidValue)) {
				rootNode.getChildren().add(map.get(idValue));
			} else {
				ptree = (TreeNode) map.get(pidValue);

				// 关闭操作按钮
				if (model.getFuntype().equals(Constance.funTypeButton))
					ptree.setOpen(false);

				if (ptree != null)
					ptree.getChildren().add(map.get(idValue));
			}
		}

		return nodes;
	}

	public List<TreeNode> doCheckedBuild(List<Function> list, List<Function> checkeds) {
		// 缓存打勾节点
		Map<String, Function> checkedMap = new HashMap<String, Function>();
		for (Function model : checkeds) {
			checkedMap.put(model.getFunid(), model);
		}

		// 初始化
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("系统菜单");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		Map<String, TreeNode> map = new HashMap<String, TreeNode>();
		// 加载节点
		for (Function model : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getFunid());
			treeNode.setName(model.getLabelname());
			treeNode.setCode("fun");
			if (model.getFuntype().equals(Constance.funTypeMenu))
				treeNode.setIconSkin("fun");
			else
				treeNode.setIconSkin("fun_button");
			treeNode.setOpen(true);
			treeNode.setChecked(checkedMap.get(model.getFunid()) != null);
			map.put(model.getFunid(), treeNode);
		}

		TreeNode ptree = null;
		for (Function model : list) {
			String idValue = model.getFunid();
			String pidValue = model.getFunpid();

			// 根
			if (StringUtils.isEmpty(pidValue)) {
				rootNode.getChildren().add(map.get(idValue));
			} else {
				ptree = (TreeNode) map.get(pidValue);

				// 关闭操作按钮
				if (model.getFuntype().equals(Constance.funTypeButton))
					ptree.setOpen(false);

				if (ptree != null)
					ptree.getChildren().add(map.get(idValue));
			}
		}

		return nodes;
	}

	/**
	 * 角色对菜单
	 * @param funs
	 * @param checks
	 * @return
	 */
	public List<TreeNode> dobuildCheck(List<Function> funs, List<RoleRight> checks) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName("系统菜单");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);
		
		// 打钩
		Map<String, RoleRight> map = new HashMap<String, RoleRight>();
		if (checks != null) {
			for (RoleRight role : checks) {
				map.put(role.getFunid(), role);
			}
			rootNode.setChecked(checks.isEmpty() ? false : true);
		}

		Map<String, TreeNode> mapp = new HashMap<String, TreeNode>();
		// 加载节点
		for (Function model : funs) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getFunid());
			treeNode.setName(model.getLabelname());
			treeNode.setCode("fun");
			if (model.getFuntype().equals(Constance.funTypeMenu)){
				treeNode.setIconSkin("fun");
			}
			else{
				treeNode.setIconSkin("fun_button");
			}
			
			// 打勾
			if (map.get(model.getFunid()) != null) {
				treeNode.setChecked(true);
			}
				
			treeNode.setOpen(true);
			mapp.put(model.getFunid(), treeNode);
		}

		TreeNode ptree = null;
		for (Function model : funs) {
			String idValue = model.getFunid();
			String pidValue = model.getFunpid();

			// 根
			if (StringUtils.isEmpty(pidValue)) {
				rootNode.getChildren().add(mapp.get(idValue));
			} else {
				ptree = (TreeNode) mapp.get(pidValue);

				// 关闭操作按钮
				if (model.getFuntype().equals(Constance.funTypeButton))
					ptree.setOpen(false);

				if (ptree != null)
					ptree.getChildren().add(mapp.get(idValue));
			}
		}

		return nodes;
	}
}
