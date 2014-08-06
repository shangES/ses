package com.mk.employee.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.employee.service.HrRecommendService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;

@Controller
public class HrRecommendAction {
	@Autowired
	private HrRecommendService hrRecommendService;
	
	
	
	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/searchHrRecommend.do")
	public void searchHrRecommend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		hrRecommendService.searchHrRecommend(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("员工入职推荐信息表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();

	}
	
	
	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/delHrRecommendById.do")
	@ResponseBody
	public void delHrRecommendById(String ids) throws Exception {
		hrRecommendService.delHrRecommendById(ids);
	}
	
	
}
