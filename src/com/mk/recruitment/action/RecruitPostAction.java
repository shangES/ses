package com.mk.recruitment.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.tree.TreeNode;
import com.mk.recruitment.entity.Category;
import com.mk.recruitment.entity.RecruitPost;
import com.mk.recruitment.service.RecruitPostService;
import com.mk.recruitment.tree.CategoryTree;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.OptionType;

@Controller
public class RecruitPostAction {

	@Autowired
	private RecruitPostService recruitPostService;

	/**
	 * 工作类别树(多选)
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/buildCategoryTree.do")
	@ResponseBody
	public List<TreeNode> buildCategoryTree() throws Exception {
		List<Category> data = recruitPostService.getAllCategory();
		CategoryTree tree = new CategoryTree();
		return tree.dobuildAllCategory(data);
	}

	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/searchRecruitPost.do")
	public void searchRecruitPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		recruitPostService.searchRecruitPost(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("职位管理");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();

	}

	/**
	 * 保存or修改选项类型
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/recruitment/saveOrUpdateRecruitPost.do")
	@ResponseBody
	public RecruitPost saveOrUpdateRecruitPost(RecruitPost model) {
		recruitPostService.saveOrUpdateRecruitPost(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/recruitment/getRecruitPostById.do")
	@ResponseBody
	public RecruitPost getRecruitPostById(String id) {
		RecruitPost model = recruitPostService.getRecruitPostById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/delRecruitPostById.do")
	@ResponseBody
	public void delRecruitPostById(String ids) throws Exception {
		recruitPostService.delRecruitPostById(ids);
	}

	/**
	 * 审核发布
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/auditRecruitPostById.do")
	@ResponseBody
	public void auditRecruitPostById(String ids, Integer state) throws Exception {
		recruitPostService.auditRecruitPostById(ids, state);
	}
	
	
	
	
	/**
	 * 内部竞聘
	 * 
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/applyRecruitPostByUserId.do")
	@ResponseBody
	public String applyRecruitPostByUserId(String id) throws Exception {
		return recruitPostService.applyRecruitPostByUserId(id);
	}
	
	
	
	/**
	 * 内部竞聘
	 * 
	 * 
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/competitionRecruitPostById.do")
	@ResponseBody
	public void competitionRecruitPostById(String id,String recruitpostguid) throws Exception {
		recruitPostService.competitionRecruitPostById(id,recruitpostguid);
	}
	
	
	
	
	
	
	

	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/searchInternalPost.do")
	public void searchInternalPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		recruitPostService.searchInternalPost(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("内部职位管理");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();

	}
	
	
	
	
	
	
	
	
	/**
	 * 我的竞聘
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitpost/searchMyRecruitpost.do")
	public void searchMyRecruitpost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		recruitPostService.searchMyRecruitpost(grid);
		grid.printLoadResponseText();

	}
}
