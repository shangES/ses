package com.mk.department.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mk.company.entity.Company;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.framework.constance.Constance;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;

public class PostTree {

	/**
	 * 岗位树
	 * 
	 * @param dept
	 * @param data
	 * @return
	 */
	public List<TreeNode> doBulid(Department dept, List<Post> data) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName(dept.getDeptname());
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (Post model : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getPostid());
			treeNode.setName(model.getPostname());
			treeNode.setPostcode(model.getPostcode());
			treeNode.setIconSkin("post");
			treeNode.setCode("post");
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}

	/**
	 * 岗位树
	 * 
	 * @param dept
	 * @param data
	 * @return
	 */
	public List<TreeNode> doBulidByDept(Department dept, List<Post> data) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode rootNode = new TreeNode();
		rootNode.setName(dept.getDeptname());
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (Post model : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getPostid());
			treeNode.setName(model.getPostname() + "(" + model.getDeptname() + ")");
			treeNode.setPostcode(model.getPostcode());
			treeNode.setIconSkin("post");
			treeNode.setCode("post");
			rootNode.getChildren().add(treeNode);
		}
		return nodes;
	}

	/**
	 * 部门岗位树(拉已部门、子部门下的岗位)
	 * 
	 * @param dept
	 * @param data
	 * @return
	 */
	public List<TreeNode> doBulidByCode(String deptid, List<Department> depts, List<Post> posts) {
		// 按部门分组岗位
		Map<String, List<Post>> postMap = new HashMap<String, List<Post>>();
		for (Post model : posts) {
			List<Post> tmplist = postMap.get(model.getDeptid());
			if (tmplist == null) {
				tmplist = new ArrayList<Post>();
				tmplist.add(model);
				postMap.put(model.getDeptid(), tmplist);
			} else {
				tmplist.add(model);
			}
		}

		// 构造成树
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		Map<String, TreeNode> map = new HashMap<String, TreeNode>();

		TreeNode rootNode = new TreeNode();
		rootNode.setName("所有部门");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (Department dept : depts) {
			TreeNode treeNode = new TreeNode();
			//treeNode.setId(dept.getDeptid());
			treeNode.setName(dept.getDeptname());
			treeNode.setpId(dept.getPdeptid());
			treeNode.setState(dept.getState().toString());
			treeNode.setOpen(true);
			treeNode.setIconSkin("dept");
			map.put(dept.getDeptid(), treeNode);
			// rootNode.getChildren().add(treeNode);

			// 挂岗位
			initPostTree(dept, postMap.get(dept.getDeptid()), treeNode);
		}

		TreeNode ptree = null;
		for (Department model : depts) {
			String idValue = model.getDeptid();
			String pidValue = model.getPdeptid();

			// 根
			if (idValue.equals(deptid)) {
				rootNode.getChildren().add(map.get(idValue));
			} else {
				ptree = (TreeNode) map.get(pidValue);
				if (ptree != null)
					ptree.getChildren().add(map.get(idValue));
			}
		}
		return nodes;
	}

	// ============================================================

	/**
	 * 公司部门岗位树
	 * 
	 * @param compays
	 * @param depts
	 * @param posts
	 * @return
	 */
	public List<TreeNode> doCompanyDeptPostBuild(List<Company> compays, List<Department> depts, List<Post> posts) {
		// 部门按公司分组
		Map<String, List<Department>> deptMap = new HashMap<String, List<Department>>();
		for (Department model : depts) {
			List<Department> tmplist = deptMap.get(model.getCompanyid());
			if (tmplist == null) {
				tmplist = new ArrayList<Department>();
				tmplist.add(model);
				deptMap.put(model.getCompanyid(), tmplist);
			} else {
				tmplist.add(model);
			}
		}

		// 按部门分组
		Map<String, List<Post>> postMap = new HashMap<String, List<Post>>();
		for (Post model : posts) {
			List<Post> tmplist = postMap.get(model.getDeptid());
			if (tmplist == null) {
				tmplist = new ArrayList<Post>();
				tmplist.add(model);
				postMap.put(model.getDeptid(), tmplist);
			} else {
				tmplist.add(model);
			}
		}

		// ==================================

		// 构造成树
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		Map<String, TreeNode> map = new HashMap<String, TreeNode>();

		TreeNode rootNode = new TreeNode();
		rootNode.setName("全部公司");
		rootNode.setOpen(true);
		rootNode.setIconSkin("root");
		nodes.add(rootNode);

		// 加载节点
		for (Company model : compays) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getCompanyid());
			treeNode.setName(model.getCompanyname());
			treeNode.setCode("company");
			treeNode.setEname(model.getState().toString());
			treeNode.setIconSkin(model.isIsaudit() ? "company" : "companylock");
			treeNode.setOpen(false);
			map.put(model.getCompanyid(), treeNode);

			// 挂部门
			if (model.isIsaudit())
				initDeptTree(model.getCompanyid(), deptMap.get(model.getCompanyid()), treeNode, postMap);
		}

		TreeNode ptree = null;
		for (Company model : compays) {
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
	 * 加载部门树
	 * 
	 * @param regionid
	 * @param data
	 * @param rootNode
	 */
	private void initDeptTree(String regionid, List<Department> data, TreeNode rootNode, Map<String, List<Post>> postMap) {
		if (data == null || data.isEmpty())
			return;

		Map<String, TreeNode> map = new HashMap<String, TreeNode>();
		// 加载节点
		for (Department dept : data) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(dept.getDeptid());
			treeNode.setName(dept.getDeptname());
			treeNode.setpId(dept.getPdeptid());
			treeNode.setEname(dept.getCompanyid());
			treeNode.setState(dept.getState().toString());
			treeNode.setCode("dept");
			// 无效
			if (dept.getState().equals(Constance.VALID_NO)) {
				treeNode.setIconSkin("remove");
			} else
				treeNode.setIconSkin("dept");
			map.put(dept.getDeptid(), treeNode);

			// 挂岗位
			initPostTree(dept, postMap.get(dept.getDeptid()), treeNode);

		}

		TreeNode ptree = null;
		for (Department dept : data) {
			String idValue = dept.getDeptid();
			String pidValue = dept.getPdeptid();

			// 根
			if (StringUtils.isEmpty(pidValue)) {
				TreeNode treeNode = map.get(idValue);
				rootNode.getChildren().add(treeNode);
			} else {
				ptree = (TreeNode) map.get(pidValue);
				if (ptree != null)
					ptree.getChildren().add(map.get(idValue));
			}
		}

	}

	/**
	 * 挂岗位树
	 * 
	 * @param deptid
	 * @param list
	 * @param treeNode
	 */
	private void initPostTree(Department dept, List<Post> list, TreeNode rootNode) {
		if (list == null || list.isEmpty())
			return;
		// 加载节点
		for (Post model : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(model.getPostid());
			treeNode.setName(model.getPostname());
			treeNode.setPostcode(model.getPostcode());
			treeNode.setEname(model.getCompanyid());
			treeNode.setState(String.valueOf(model.getState()));
			// 无效
			if (model.getState() == Constance.VALID_NO) {
				treeNode.setIconSkin("remove");
			} else {
				treeNode.setIconSkin("post");
			}
			treeNode.setCode("post");
			treeNode.setDrag(true);
			rootNode.getChildren().add(treeNode);
		}

	}

}
