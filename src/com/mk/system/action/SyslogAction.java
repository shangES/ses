package com.mk.system.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.system.entity.Syslog;
import com.mk.system.service.SyslogService;

@Controller
public class SyslogAction {
	@Autowired
	private SyslogService syslogService;

	/**
	 * 搜索得到全部系统日志信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/system/searchSyslog.do")
	@ResponseBody
	public void searchSyslog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		syslogService.searchSyslog(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("系统日志信息");
			report.reportGrid(grid);
		} else {
			grid.printLoadResponseText();
		}
	}

	/**
	 * 保存or修改系统日志
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/system/saveOrUpdateSyslog.do")
	@ResponseBody
	public Syslog saveOrUpdateSyslog(Syslog model) {
		syslogService.saveOrUpdateSyslog(model);
		return model;
	}

	/**
	 * 删除系统日志
	 * 
	 * @param ids
	 */
	@RequestMapping("/system/delSyslog.do")
	@ResponseBody
	public void delSyslog(String ids) {
		syslogService.delSyslog(ids);
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/system/getSyslogById.do")
	@ResponseBody
	public Syslog getSyslogById(String id) {
		return syslogService.getSyslogById(id);
	}
}
