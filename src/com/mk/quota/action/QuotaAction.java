package com.mk.quota.action;

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
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.quota.entity.Quota;
import com.mk.quota.service.QuotaService;

@Controller
public class QuotaAction {
	@Autowired
	private QuotaService quotaService;

	/**
	 * 公司编制树
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/quota/buildQuotaTreeByCompanyid.do")
	@ResponseBody
	public List<TreeNode> buildQuotaTreeByCompanyid(String companyid) throws Exception {
		return quotaService.buildQuotaTreeByCompanyid(companyid);
	}

	/**
	 * 部门编制树
	 * 
	 * @param postid
	 * @throws Exception
	 */
	@RequestMapping("/quota/buildMyQuotaTree.do")
	@ResponseBody
	public List<TreeNode> buildMyQuotaTree(String postid) throws Exception {
		if (StringUtils.isEmpty(postid))
			return null;
		return quotaService.buildMyQuotaTree(postid);
	}

	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/quota/searchQuota.do")
	@ResponseBody
	public void searchQuota(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		quotaService.searchQuota(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("部门编制信息");
			report.reportGrid(grid);
		} else {
			grid.printLoadResponseText();
		}
	}

	/**
	 * 批量保存
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/quota/saveQuotaGrid.do")
	public void saveQuotaGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		String msg = quotaService.saveQuotaGrid(grid);
		if (StringUtils.isNotEmpty(msg))
			grid.printResponseText(msg);
	}

	/**
	 * 保存 or 修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/quota/saveOrUpdateQuota.do")
	@ResponseBody
	public Quota saveOrUpdateQuota(Quota model) {
		quotaService.saveOrUpdateQuota(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param quotaid
	 * @return
	 */
	@RequestMapping("/quota/getQuotaById.do")
	@ResponseBody
	public Quota getQuotaById(String id,String taskid) {
		Quota model = quotaService.getQuotaById(id,taskid);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@RequestMapping("/quota/delQuotaByQuotaid.do")
	@ResponseBody
	public void delQuotaByQuotaid(String ids) {
		quotaService.delQuotaByQuotaid(ids);
	}

	/**
	 * 校验岗位 编制类型
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/quota/checkQuotaByPostIdAndBudgettype.do")
	@ResponseBody
	public String checkQuotaByPostIdAndBudgettype(String quotaid, String postid, String budgettype, Integer state) {
		return quotaService.checkQuotaByPostIdAndBudgettype(quotaid, postid, budgettype, state);
	}
	
	/**
	 * 流程审核
	 * 
	 * @param ids
	 * @param state
	 */
	@RequestMapping("/quota/completeTask.do")
	@ResponseBody
	public void completeTask(String taskid, Integer commit, String result) {
		quotaService.completeTask(taskid, commit, result,Constance.State_Process);
	}
}
