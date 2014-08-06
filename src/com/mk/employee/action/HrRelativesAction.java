package com.mk.employee.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.employee.entity.HrRelatives;
import com.mk.employee.service.HrRelativesService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;

@Controller
public class HrRelativesAction {
	@Autowired
	private HrRelativesService hrRelativesService;
	
	
	
	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/searchHrRelatives.do")
	public void searchHrRelatives(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		hrRelativesService.searchHrRelatives(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("员工亲属表");
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
	@RequestMapping("/employee/saveOrUpdateRelatives.do")
	@ResponseBody
	public HrRelatives saveOrUpdateRelatives(HrRelatives model) throws Exception {
		hrRelativesService.saveOrUpdateRelatives(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/getHrRelativesById.do")
	@ResponseBody
	public HrRelatives getHrRelativesById(String id) throws Exception {
		HrRelatives model = hrRelativesService.getHrRelativesById(id);
		return model;
	}
	
	
	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/delHrRelativesById.do")
	@ResponseBody
	public void delHrRelativesById(String ids) throws Exception {
		hrRelativesService.delHrRelativesById(ids);
	}
}
