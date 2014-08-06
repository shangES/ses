package com.mk.recruitment.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.constance.Constance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.tree.TreeNode;
import com.mk.recruitment.entity.WorkPlace;
import com.mk.recruitment.service.WorkPlaceService;

@Controller
public class WorkPlaceAction {
	@Autowired
	private WorkPlaceService workPlaceService;
	
	
	/**
	 * 得到工作地点树
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/buildAllWorkPlace.do")
	@ResponseBody
	public List<TreeNode> buildAllWorkPlace() throws Exception {
		return workPlaceService.buildAllWorkPlace();
	}
	
	
	

	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/searchWorkPlace.do")
	public void searchWorkPlace(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		workPlaceService.searchWorkPlace(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("工作地点");
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
	@RequestMapping("/recruitment/saveOrUpdateWorkPlace.do")
	@ResponseBody
	public WorkPlace saveOrUpdateWorkPlace(WorkPlace model) {
		workPlaceService.saveOrUpdateWorkPlace(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/recruitment/getWorkPlaceById.do")
	@ResponseBody
	public WorkPlace getWorkPlaceById(String id) {
		WorkPlace model = workPlaceService.getWorkPlaceById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/delWorkPlaceById.do")
	@ResponseBody
	public void delWorkPlaceById(String ids) throws Exception {
		workPlaceService.delWorkPlaceById(ids);
	}

	/**
	 * 失效/还原
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/validWorkPlaceById.do")
	@ResponseBody
	public void validWorkPlaceById(String ids, Integer state) throws Exception {
		workPlaceService.validWorkPlaceById(ids, state);
	}
}
