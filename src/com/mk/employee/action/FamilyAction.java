package com.mk.employee.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.employee.entity.Family;
import com.mk.employee.service.FamilyService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;

@Controller
public class FamilyAction {
	@Autowired
	private FamilyService familyService;
	
	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/searchFamily.do")
	public void searchFamily(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		familyService.searchFamily(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("家属信息表");
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
	@RequestMapping("/employee/saveOrUpdateFamily.do")
	@ResponseBody
	public Family saveOrUpdateFamily(Family model) throws Exception {
		familyService.saveOrUpdateFamily(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/getFamilyById.do")
	@ResponseBody
	public Family getFamilyById(String id) throws Exception {
		Family model = familyService.getFamilyById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/delFamilyById.do")
	@ResponseBody
	public void delFamilyById(String ids) throws Exception {
		familyService.delFamilyById(ids);
	}

	
	
}	
