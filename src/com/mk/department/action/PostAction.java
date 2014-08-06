package com.mk.department.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.department.entity.Post;
import com.mk.department.service.PostService;
import com.mk.framework.constance.Constance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.model.ColumnInfo;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.tree.TreeNode;

@Controller
public class PostAction {
	@Autowired
	private PostService postService;

	/**
	 * 导出部门岗位树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("department/exportPost.do")
	public void exportPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		List<Post> list = postService.getHasPost(Constance.VALID_YES);

		// 参数设置
		Map<Object, Object> parameterMap = new HashMap<Object, Object>();
		parameterMap.put("exportFileName", new String[] { "岗位列表.xls" });
		grid.setParameterMap(parameterMap);

		// 列
		List<ColumnInfo> columns = Post.initExcelColumn();
		grid.setColumnInfo(columns);

		// 数据
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Post model : list) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
		ReportUtil report = new ReportUtil();
		report.setTitle("岗位列表");
		report.reportGrid(grid);
	}

	/**
	 * 公司部门岗位树
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/department/buildMyPostTree.do")
	@ResponseBody
	public List<TreeNode> buildMyDeptPostTree(Integer valid) throws Exception {
		return postService.buildMyDeptPostTree(valid);
	}

	/**
	 * 加载树
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/department/buildPostTree.do")
	@ResponseBody
	public List<TreeNode> buildPostTree(String deptid) throws Exception {
		return postService.buildPostTree(deptid);
	}

	
	/**
	 * 加载部门以及子部门的树
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/department/buildMyPostTreeByDept.do")
	@ResponseBody
	public List<TreeNode> buildMyPostTreeByDept(String deptid) throws Exception {
		return postService.buildMyPostTreeByDept(deptid);
	}
	
	/**
	 * 通过部门id得到岗位信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/department/searchPost.do")
	@ResponseBody
	public void searchPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		postService.searchPost(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("岗位信息");
			report.reportGrid(grid);
		} else {
			grid.printLoadResponseText();
		}
	}

	/**
	 * 保存or修改岗位信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/department/saveOrUpdatePost.do")
	@ResponseBody
	public Post saveOrUpdatePost(Post model) {
		postService.saveOrUpdatePost(model);
		return model;
	}

	/**
	 * 通过id得到岗位信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/department/getPostByPostId.do")
	@ResponseBody
	public Post getPostByPostId(String id) {
		return postService.getPostByPostId(id);
	}

	/**
	 * 通过岗位id删除岗位信息
	 * 
	 * @param ids
	 */
	@RequestMapping("/department/delPostById.do")
	@ResponseBody
	public void delPostById(String ids) {
		postService.delPostById(ids);
	}

	/**
	 * 失效or还原岗位信息
	 * 
	 * @param ids
	 * @param state
	 */
	@RequestMapping("/department/invalidOrReductionPostById.do")
	@ResponseBody
	public void invalidOrReductionPostById(String ids, int state) {
		postService.invalidOrReductionPostById(ids, state);
	}

	/**
	 * 验证岗位名称是否重复
	 * 
	 * @param postid
	 * @param postname
	 * @return
	 */
	@RequestMapping("/department/verificationPostname.do")
	@ResponseBody
	public String verificationPostname(String postid, String deptid, String postname) {
		return postService.verificationPostname(postid, deptid, postname);
	}

	/**
	 * 排序
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/department/orderPost.do")
	@ResponseBody
	public void orderPost(String id, String targetid, String moveType) throws Exception {
		postService.orderPost(id, targetid, moveType);
	}
}
