package com.mk.quota.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.quota.entity.QuotaOperate;
import com.mk.quota.service.QuotaOperateService;

@Controller
public class QuotaOperateAction {
	@Autowired
	private QuotaOperateService quotaOperateService;
	
	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/quota/searchQuotaOperate.do")
	@ResponseBody
	public void searchQuotaOperate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		quotaOperateService.searchQuotaOperate(grid);
		grid.printLoadResponseText();
	}
	
	
	/**
	 * 保存 
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/quota/saveOrUpdateQuotaOperate.do")
	@ResponseBody
	public QuotaOperate saveOrUpdateQuotaOperate(QuotaOperate model) {
		quotaOperateService.saveOrUpdateQuotaOperate(model);
		return model;
	}
	
	
	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@RequestMapping("/quota/delQuotaOperateById.do")
	@ResponseBody
	public void delQuotaOperateById(String ids) {
		quotaOperateService.delQuotaOperateById(ids);
	}
}
