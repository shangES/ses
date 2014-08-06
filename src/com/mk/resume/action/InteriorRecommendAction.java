package com.mk.resume.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.employee.BirthdayUtil;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.resume.service.InteriorRecommendService;

@Controller
public class InteriorRecommendAction {
	@Autowired
	private InteriorRecommendService interiorRecommendService;
	
	
	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/searchRecommend.do")
	@ResponseBody
	public void searchRecommend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		
		interiorRecommendService.searchRecommend(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("我的推荐");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();

	}
}
