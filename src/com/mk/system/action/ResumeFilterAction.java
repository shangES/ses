package com.mk.system.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.grid.GridServerHandler;
import com.mk.system.entity.ResumeFilter;
import com.mk.system.entity.UserFilterPam;
import com.mk.system.service.ResumeFilterService;

@Controller
public class ResumeFilterAction {
	@Autowired
	private ResumeFilterService resumeFilterService;


	/**
	 * 通过选项列表的id得到选项列表的数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/system/getResumeFilterById.do")
	@ResponseBody
	public ResumeFilter getResumeFilterById(String id) {
		ResumeFilter model = resumeFilterService.getResumeFilterById(id);
		return model;
	}

	/**
	 * 搜索(所有的)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/system/searchResumeFilter.do")
	@ResponseBody
	public void searchResumeFilter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		resumeFilterService.searchResumeFilter(grid);
		grid.printLoadResponseText();
	}
	
	
	/**
	 * 保存or修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/system/saveOrUpdateFilter.do")
	@ResponseBody
	public ResumeFilter saveOrUpdateFilter(ResumeFilter model) {
		resumeFilterService.saveOrUpdateFilter(model);
		return model;
	}
	
	/**
	 * 保存授权
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/system/saveUserFilter.do")
	@ResponseBody
	public void saveUserFilter(@RequestBody UserFilterPam data) {
		resumeFilterService.saveUserFilter(data);
	}

}
