package com.mk.company.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.company.entity.Budgetype;
import com.mk.company.service.BudgetypeService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;

@Controller
public class BudgetypeAction {
	@Autowired
	private BudgetypeService budgetypeService;
	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/budgetype/searchBudgetype.do")
	public void searchBudgetype(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		budgetypeService.searchBudgetype(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("编制类型信息表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * 保存及修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/budgetype/saveOrUpdateBudgetype.do")
	@ResponseBody
	public Budgetype saveOrUpdateBudgetype(Budgetype model) {
		budgetypeService.saveOrUpdateBudgetype(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@RequestMapping("/budgetype/delBudgetypeById.do")
	@ResponseBody
	public void delBudgetypeById(String ids) {
		budgetypeService.delBudgetypeById(ids);
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/budgetype/getBudgetypeById.do")
	@ResponseBody
	public Budgetype getBudgetypeById(String id) {
		Budgetype model = budgetypeService.getBudgetypeById(id);
		return model;
	}

	/**
	 * 失效及恢复
	 * 
	 * @param ids
	 * @param valid
	 * @throws Exception
	 */
	@RequestMapping("/budgetype/validBudgetypeById.do")
	@ResponseBody
	public void validBudgetypeById(String ids, Integer valid) throws Exception {
		budgetypeService.validBudgetypeById(ids, valid);
	}
}	
