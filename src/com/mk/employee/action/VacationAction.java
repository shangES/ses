package com.mk.employee.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.employee.entity.Vacation;
import com.mk.employee.service.VacationService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.grid.util.StringUtils;

@Controller
public class VacationAction {
	@Autowired
	private VacationService vacationService;

	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/vacation/searchVacation.do")
	@ResponseBody
	public void searchVacation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		vacationService.searchVacation(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("请假信息");
			report.reportGrid(grid);
		} else {
			grid.printLoadResponseText();
		}

	}
	
	
	
	/**
	 * 保存or修改
	 * @param model
	 * @return
	 */
	@RequestMapping("/vacation/saveOrUpdateVacation.do")
	@ResponseBody
	public Vacation saveOrUpdateVacation(Vacation model){
		vacationService.saveOrUpdateVacation(model);
		return model;
	}
	
	
	/**
	 * 批量保存
	 * @param request
	 * @param response
	 */
	@RequestMapping("/vacation/saveVacationGrid.do")
	@ResponseBody
	public void saveVacationGrid(HttpServletRequest request, HttpServletResponse response){
		GridServerHandler grid = new GridServerHandler(request, response);
		String msg =vacationService.saveVacationGrid(grid);
		if (StringUtils.isNotEmpty(msg))
			grid.printResponseText(msg);
	}
	
	
	
	
	/**
	 *删除
	 * @param ids
	 */
	@RequestMapping("/vacation/delVacation.do")
	@ResponseBody
	public void  delVacation(String ids){
		vacationService.delVacation(ids);
	}
	
	
	/**
	 * 得到
	 * @param id
	 * @return
	 */
	@RequestMapping("/vacation/getVacationById.do")
	@ResponseBody
	public Vacation getVacationById(String id){
		return vacationService.getVacationById(id);
	}
}
