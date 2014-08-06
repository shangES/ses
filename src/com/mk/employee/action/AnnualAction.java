package com.mk.employee.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.employee.entity.Annual;
import com.mk.employee.service.AnnualService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.grid.util.StringUtils;

@Controller
public class AnnualAction {
	@Autowired
	private AnnualService annualService;

	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/annual/searchAnnual.do")
	@ResponseBody
	public void searchAnnual(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		annualService.searchAnnual(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("年休假信息");
			report.reportGrid(grid);
		} else {
			grid.printLoadResponseText();
		}

	}
	
	/**
	 * 批量保存
	 * @param request
	 * @param response
	 */
	@RequestMapping("/annual/saveAnnualGrid.do")
	@ResponseBody
	public void saveAnnualGrid(HttpServletRequest request, HttpServletResponse response){
		GridServerHandler grid = new GridServerHandler(request, response);
		String msg =annualService.saveAnnualGrid(grid);
		if (StringUtils.isNotEmpty(msg))
			grid.printResponseText(msg);
	}
	

	/**
	 * 保存or修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/annual/saveOrUpdateAnnual.do")
	@ResponseBody
	public Annual saveOrUpdateAnnual(Annual model) {
		annualService.saveOrUpdateAnnual(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@RequestMapping("/annual/delAnnualById.do")
	@ResponseBody
	public void delAnnualById(String ids) {
		annualService.delAnnual(ids);
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/annual/getAnnualById.do")
	@ResponseBody
	public Annual getAnnualById(String id) {
		return annualService.getAnnualById(id);
	}
	
	/**
	 * 得到
	 * 
	 * @param id
	 * @param year
	 * @return
	 */
	@RequestMapping("/annual/getAnnualByEmployeeIdAndYear.do")
	@ResponseBody
	public Annual getAnnualByEmployeeIdAndYear(String id,Integer year,String annualguid) {
		return annualService.getAnnualByEmployeeIdAndYear(id,year,annualguid);
	}
	
}
