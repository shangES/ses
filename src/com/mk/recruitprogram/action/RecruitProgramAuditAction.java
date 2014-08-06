package com.mk.recruitprogram.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.recruitprogram.dao.RecruitProgramAuditRemoteDao;
import com.mk.recruitprogram.entity.RecruitProgramAudit;
import com.mk.recruitprogram.service.RecruitProgramAuditService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;

@Controller
public class RecruitProgramAuditAction {
	@Autowired
	private RecruitProgramAuditService recruitProgramAuditService;

	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/recruitprogram/searchRecruitProgramAudit.do")
	@ResponseBody
	public void searchRecruitProgramAudit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		recruitProgramAuditService.searchRecruitprogramaudit(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("招聘计划OA审批管理");
			report.reportGrid(grid);
		} else {
			grid.printLoadResponseText();
		}
	}

	/**
	 * 保存or修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/recruitprogram/saveOrUpdateRecruitProgramAudit.do")
	@ResponseBody
	public RecruitProgramAudit saveOrUpdateRecruitProgramAudit(RecruitProgramAudit model) {
		recruitProgramAuditService.saveOrUpdateRecruitProgramAudit(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@RequestMapping("/recruitprogram/delRecruitprogramauditById.do")
	@ResponseBody
	public void delRecruitprogramauditById(String ids) {
		recruitProgramAuditService.delRecruitprogramauditById(ids);
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/recruitprogram/getRecruitprogramauditById.do")
	@ResponseBody
	public RecruitProgramAudit getRecruitprogramauditById(String id) {
		return recruitProgramAuditService.getRecruitprogramauditById(id);
	}
	
	/**
	 * 同步邮件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/recruitprogram/refreshRecruitProgramAudit.do")
	@ResponseBody
	public void refreshRecruitProgramAudit() throws Exception {
		recruitProgramAuditService.refreshRecruitProgramAudit();
	}
}
