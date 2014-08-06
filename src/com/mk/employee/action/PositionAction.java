package com.mk.employee.action;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.department.entity.Post;
import com.mk.employee.entity.Position;
import com.mk.employee.service.PositionService;
import com.mk.employee.tree.PositionTree;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.tree.TreePageGrid;

@Controller
public class PositionAction {
	@Autowired
	private PositionService positionService;

	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/searchPosition.do")
	public void searchPosition(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		positionService.searchPosition(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("员工任职信息表");
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
	@RequestMapping("/employee/saveOrUpdatePosition.do")
	@ResponseBody
	public String saveOrUpdatePosition(Position model) throws Exception {
		return positionService.saveOrUpdatePosition(model);
	}

	/**
	 * 异动保存
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/saveOrUpdateManyPosition.do")
	@ResponseBody
	public String saveOrUpdateManyPosition(Position model) throws Exception {
		return positionService.saveOrUpdateManyPosition(model);
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/getPositionById.do")
	@ResponseBody
	public Position getPositionById(String id) throws Exception {
		Position model = positionService.getPositionById(id);
		return model;
	}
	
	
	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/getPositionByUserId.do")
	@ResponseBody
	public Position getPositionByUserId(String id) throws Exception {
		Position model = positionService.getPositionByUserId(id);
		return model;
	}
	

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/delPositionById.do")
	@ResponseBody
	public void delPositionById(String ids) throws Exception {
		positionService.delPositionById(ids);
	}

	/**
	 * 失效/还原
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/validPositionById.do")
	@ResponseBody
	public void validPositionById(String ids, Integer state) throws Exception {
		positionService.validPositionById(ids, state);
	}

	/**
	 * 部门有任职
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/employee/getPositionByDeptCode.do")
	@ResponseBody
	public boolean getPositionByDeptCode(String deptcode) throws Exception {
		return positionService.getPositionByDeptCode(deptcode);
	}

	/**
	 * 结束兼任
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/endPositionById.do")
	@ResponseBody
	public void endPositionById(String postionguid, Date enddate) throws Exception {
		positionService.endPositionById(postionguid, enddate);
	}
	
	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/getPositionByEmployeeId.do")
	@ResponseBody
	public Position getPositionByEmployeeId(String id) throws Exception {
		Position model = positionService.getPositionByEmployeeId(id);
		return model;
	}
	
	/**
	 * 岗位
	 * 
	 * @param grid
	 * @return
	 */
	@RequestMapping("/employee/searchPostTree.do")
	@ResponseBody
	public TreePageGrid searchPostTree(@RequestBody TreePageGrid grid) {
		List<Post> data = positionService.searchPostTree(grid);
		PositionTree tree = new PositionTree();
		List<TreeNode> nodes = tree.doBuildUserTree(data);
		grid.setDataList(nodes);
		return grid;
	}

}
