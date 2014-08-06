package com.mk.system.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.system.service.UserFilterService;

@Controller
public class UserFilterAction {
	@Autowired
	private UserFilterService userFilterService;
	
	
	
	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/system/searchUserFilter.do")
	public void searchUserFilter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		userFilterService.searchUserFilter(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("授权用户列表");
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
	@RequestMapping("/system/delUserFilterByUserId.do")
	@ResponseBody
	public void delUserFilterByUserId(String ids) throws Exception {
		userFilterService.delUserFilterByUserId(ids);
	}

	
	
}
