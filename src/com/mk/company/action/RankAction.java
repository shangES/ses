package com.mk.company.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.company.entity.Rank;
import com.mk.company.service.RankService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.tree.TreeNode;

@Controller
public class RankAction {
	@Autowired
	private RankService rankService;
	
	/**
	 * 加载树
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/company/buildRankTree.do")
	@ResponseBody
	public List<TreeNode> buildRankTree(String companyid) throws Exception {
		return rankService.buildRankTree(companyid);
	}

	

	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/rank/searchRank.do")
	public void searchRank(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		rankService.searchRank(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("职级信息表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * 保存及修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/rank/saveOrUpdateRank.do")
	@ResponseBody
	public Rank saveOrUpdateRank(Rank model) {
		rankService.saveOrUpdateRank(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@RequestMapping("/rank/delRankById.do")
	@ResponseBody
	public void delRankById(String ids) {
		rankService.delRankById(ids);
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/rank/getRankById.do")
	@ResponseBody
	public Rank getRankById(String id) {
		Rank model = rankService.getRankById(id);
		return model;
	}

	/**
	 * 失效及恢复
	 * 
	 * @param ids
	 * @param valid
	 * @throws Exception
	 */
	@RequestMapping("/rank/validRankById.do")
	@ResponseBody
	public void validRankById(String ids, Integer valid) throws Exception {
		rankService.validRankById(ids, valid);
	}

}
