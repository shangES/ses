package com.mk.company.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.company.entity.Job;
import com.mk.company.service.JobService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.tree.TreeNode;

@Controller
public class JobAction {
	@Autowired
	private JobService jobService;

	/**
	 * 加载树
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/company/buildJobTree.do")
	@ResponseBody
	public List<TreeNode> buildJobTree(String companyid) throws Exception {
		return jobService.buildJobTree(companyid);
	}

	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/job/searchJob.do")
	public void searchJob(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		jobService.searchJob(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("职务信息表");
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
	@RequestMapping("/job/saveOrUpdateJob.do")
	@ResponseBody
	public Job saveOrUpdateJob(Job model) {
		jobService.saveOrUpdateJob(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@RequestMapping("/job/delJobById.do")
	@ResponseBody
	public void delJobById(String ids) {
		jobService.delJobById(ids);
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/job/getJobById.do")
	@ResponseBody
	public Job getJobById(String id) {
		Job model = jobService.getJobById(id);
		return model;
	}

	/**
	 * 失效及恢复
	 * 
	 * @param ids
	 * @param valid
	 * @throws Exception
	 */
	@RequestMapping("/job/validJobById.do")
	@ResponseBody
	public void validJobById(String ids, Integer valid) throws Exception {
		jobService.validJobById(ids, valid);
	}

}
