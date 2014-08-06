package com.mk.recruitment.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.tree.TreeNode;
import com.mk.recruitment.entity.Category;
import com.mk.recruitment.service.CategoryService;

@Controller
public class CategoryAction {
	@Autowired
	private CategoryService categoryService;

	
	/**
	 * 得到职位类别树
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/buildAllCategory.do")
	@ResponseBody
	public List<TreeNode> buildAllCategory() throws Exception {
		return categoryService.buildAllCategory();
	}
	
	
	
	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/searchCategory.do")
	public void searchCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		categoryService.searchCategory(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("职位类别");
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
	@RequestMapping("/recruitment/saveOrUpdateCategory.do")
	@ResponseBody
	public Category saveOrUpdateCategory(Category model) {
		categoryService.saveOrUpdateCategory(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/recruitment/getCategoryById.do")
	@ResponseBody
	public Category getCategoryById(String id) {
		Category model = categoryService.getCategoryById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/delCategoryById.do")
	@ResponseBody
	public void delCategoryById(String ids) throws Exception {
		categoryService.delCategoryById(ids);
	}

	/**
	 * 失效/还原
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/validCategoryById.do")
	@ResponseBody
	public void validCategoryById(String ids, Integer state) throws Exception {
		categoryService.validCategoryById(ids, state);
	}
}
