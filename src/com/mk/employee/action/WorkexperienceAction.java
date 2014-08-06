package com.mk.employee.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.employee.entity.Workexperience;
import com.mk.employee.service.WorkexperienceService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;

@Controller
public class WorkexperienceAction {
	@Autowired
	private WorkexperienceService workexperienceService;
	
	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/searchWorkexperience.do")
	public void searchWorkexperience(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		workexperienceService.searchWorkexperience(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("工作经历信息表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();

	}

	/**
	 * 保存
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/saveOrUpdateWorkexperience.do")
	@ResponseBody
	public Workexperience saveOrUpdateWorkexperience(Workexperience model) throws Exception {
		workexperienceService.saveOrUpdateWorkexperience(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/getWorkexperienceById.do")
	@ResponseBody
	public Workexperience getWorkexperienceById(String id) throws Exception {
		Workexperience model = workexperienceService.getWorkexperienceById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/delWorkexperienceById.do")
	@ResponseBody
	public void delWorkexperienceById(String ids) throws Exception {
		workexperienceService.delWorkexperienceById(ids);
	}

}
