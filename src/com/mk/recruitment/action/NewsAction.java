package com.mk.recruitment.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.recruitment.entity.News;
import com.mk.recruitment.entity.NewsModule;
import com.mk.recruitment.service.NewsService;

@Controller
public class NewsAction {
	@Autowired
	private NewsService newsService;
	
	/**
	 * 得到咨询模块
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/getNewsModuleById.do")
	@ResponseBody
	public NewsModule getNewsModuleById(String id) throws Exception {
		return newsService.getNewsModuleById(id);
	}
	
	

	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/searchNews.do")
	public void searchNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		newsService.searchNews(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("咨询管理");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();

	}

	/**
	 * 保存or修改选项类型
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/recruitment/saveOrUpdateNews.do")
	@ResponseBody
	public News saveOrUpdateNews(News model) {
		newsService.saveOrUpdateNews(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/recruitment/getNewsById.do")
	@ResponseBody
	public News getNewsById(String id) {
		News model = newsService.getNewsById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/delNewsById.do")
	@ResponseBody
	public void delNewsById(String ids) throws Exception {
		newsService.delNewsById(ids);
	}

	/**
	 * 失效/还原
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/validNewsById.do")
	@ResponseBody
	public void validNewsById(String ids, Integer state) throws Exception {
		newsService.validNewsById(ids, state);
	}
	
	
	/**
	 * 审核发布
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/auditNewsById.do")
	@ResponseBody
	public void auditNewsById(String ids,Integer state) throws Exception {
		newsService.auditNewsById(ids,state);
	}
	
}
