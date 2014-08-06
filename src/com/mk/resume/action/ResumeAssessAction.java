package com.mk.resume.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.resume.entity.ResumeAssess;
import com.mk.resume.service.ResumeAssessService;

@Controller
public class ResumeAssessAction {
	@Autowired
	private ResumeAssessService resumeAssessService;

	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/resume/searchResumeAssess.do")
	@ResponseBody
	public void searchResumeAssess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		resumeAssessService.searchResumeAssess(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("评价信息");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * 保存or修改
	 * 
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/resume/saveOrUpdateResumeAssess.do")
	@ResponseBody
	public ResumeAssess saveOrUpdateResumeAssess(ResumeAssess model) throws Exception {
		resumeAssessService.saveOrUpdateResumeAssess(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@RequestMapping("/resume/delResumeAssessById.do")
	@ResponseBody
	public void delResumeAssessById(String ids) {
		resumeAssessService.delResumeAssessById(ids);
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/resume/getResumeAssessById.do")
	@ResponseBody
	public ResumeAssess getResumeAssessById(String id) {
		return resumeAssessService.getResumeAssessById(id);
	}
}
