package com.mk.employee.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.employee.entity.Eduexperience;
import com.mk.employee.service.EduexperienceService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;

@Controller
public class EduexperienceAction {
	@Autowired
	private EduexperienceService eduexperienceService;

	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/searchEduexperience.do")
	public void searchPosition(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		eduexperienceService.searchEduexperience(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("教育经历信息表");
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
	@RequestMapping("/employee/saveOrUpdateEduexperience.do")
	@ResponseBody
	public Eduexperience saveOrUpdateEduexperience(Eduexperience model) throws Exception {
		eduexperienceService.saveOrUpdateEduexperience(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/getEduexperienceById.do")
	@ResponseBody
	public Eduexperience getEduexperienceById(String id) throws Exception {
		Eduexperience model = eduexperienceService.getEduexperienceById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/delEduexperienceById.do")
	@ResponseBody
	public void delEduexperienceById(String ids) throws Exception {
		eduexperienceService.delEduexperienceById(ids);
	}
}
