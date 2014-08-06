package com.mk.recruitprogram.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.constance.Constance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.tree.TreePageGrid;
import com.mk.recruitprogram.entity.RecruitProgram;
import com.mk.recruitprogram.service.RecruitprogramService;
import com.mk.recruitprogram.tree.RecruitProgramTree;

@Controller
public class RecruitprogramAction {

	@Autowired
	private RecruitprogramService recruitprogramService;

	/**
	 * 招聘计划树
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitprogram/searchRecruitprogramTree.do")
	@ResponseBody
	public TreePageGrid searchRecruitprogramTree(@RequestBody TreePageGrid grid) {
		List<RecruitProgram> data = recruitprogramService.searchRecruitprogramTree(grid);
		RecruitProgramTree tree = new RecruitProgramTree();
		List<TreeNode> nodes = tree.dobuild(data);
		grid.setDataList(nodes);
		return grid;
	}

	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/recruitprogram/searchRecruitprogram.do")
	@ResponseBody
	public void searchRecruitprogram(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		recruitprogramService.searchRecruitprogram(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("招聘计划管理");
			report.reportGrid(grid);
		} else {
			grid.printLoadResponseText();
		}
	}

	/**
	 * 批量保存
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitprogram/saveRecruitProgramGrid.do")
	public void saveRecruitProgramGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		String msg = recruitprogramService.saveRecruitProgramGrid(grid);
		if (StringUtils.isNotEmpty(msg))
			grid.printResponseText(msg);
	}

	/**
	 * 保存or修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/recruitprogram/saveOrUpdateRecruitProgram.do")
	@ResponseBody
	public RecruitProgram saveOrUpdateRecruitProgram(RecruitProgram model) {
		recruitprogramService.saveOrUpdateRecruitProgram(model);
		return model;
	}
	
	
	/**
	 * 细分操作
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/recruitprogram/UpdateRecruitProgram.do")
	@ResponseBody
	public RecruitProgram UpdateRecruitProgram(RecruitProgram model) {
		recruitprogramService.UpdateRecruitProgram(model);
		return model;
	}
	

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/recruitprogram/getRecruitprogramById.do")
	@ResponseBody
	public RecruitProgram getRecruitprogramById(String id,String taskid) {
		return recruitprogramService.getRecruitprogramById(id,taskid);
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@RequestMapping("/recruitprogram/delRecruitprogramById.do")
	@ResponseBody
	public void delRecruitprogramById(String ids) {
		recruitprogramService.delRecruitprogramById(ids);
	}

	/**
	 * 通过id修改state状态
	 * 
	 * @param ids
	 * @param state
	 */
	@RequestMapping("/recruitprogram/updateRecruitprogramStateById.do")
	@ResponseBody
	public void updateRecruitprogramStateById(String ids, Integer state) {
		recruitprogramService.updateRecruitprogramStateById(ids, state);
	}

	
	/**
	 * 取消细分
	 * 
	 * @param ids
	 * @param state
	 */
	@RequestMapping("/recruitprogram/cancelRecruitprogramByIdAndInterfacecode.do")
	@ResponseBody
	public void cancelRecruitprogramByIdAndInterfacecode(String id, String interfacecode) {
		recruitprogramService.cancelRecruitprogramByIdAndInterfacecode(id, interfacecode);
	}
	
	
	
	/**
	 * 流程审核
	 * 
	 * @param ids
	 * @param state
	 */
	@RequestMapping("/recruitprogram/completeTask.do")
	@ResponseBody
	public void completeTask(String taskid, Integer commit, String result) {
		recruitprogramService.completeTask(taskid, commit, result,Constance.State_Process);
	}
	
	
	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/recruitprogram/searchRecruitProgramOperate.do")
	@ResponseBody
	public void searchRecruitProgramOperate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		recruitprogramService.searchRecruitProgramOperate(grid);
		grid.printLoadResponseText();
	}
	
	
	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/recruitprogram/getRecruitprogramByPostId.do")
	@ResponseBody
	public List<RecruitProgram> getRecruitprogramByPostId(String id,String quotaid) {
		return recruitprogramService.getRecruitprogramByPostId(id,quotaid);
	}
}
