package com.mk.mycandidates.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.grid.GridServerHandler;
import com.mk.mycandidates.entity.Recommend;
import com.mk.mycandidates.service.RecommendService;

@Controller
public class RecommendAction {
	@Autowired
	private RecommendService recommendService;

	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/mycandidates/searchRecommend.do")
	@ResponseBody
	public void searchRecommend(HttpServletRequest request, HttpServletResponse response) {
		GridServerHandler grid = new GridServerHandler(request, response);
		recommendService.searchRecommend(grid);
		grid.printLoadResponseText();
	}

	/**
	 * 保存
	 * 
	 * @param model
	 */
	@RequestMapping("/mycandidates/saveOrUpdateRecommend.do")
	@ResponseBody
	public Recommend saveOrUpdateRecommend(Recommend model) throws Exception{
		recommendService.saveOrUpdateRecommend(model);
		return model;
	}
	
	
	/**
	 * 保存(直接认定 部门筛选)
	 * 
	 * @param model
	 */
	@RequestMapping("/mycandidates/saveOrUpdateRecommendByAffirm.do")
	@ResponseBody
	public Recommend saveOrUpdateRecommendByAffirm(Recommend model) throws Exception{
		recommendService.saveOrUpdateRecommendByAffirm(model);
		return model;
	}
	
	
	/**
	 * 得到数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/mycandidates/getRecommendById.do")
	@ResponseBody
	public Recommend getRecommendById(String id) {
		return recommendService.getRecommendById(id);
	}
	
	/**
	 * 得到数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/mycandidates/getRecommendByCandidatesGuidAndState.do")
	@ResponseBody
	public Recommend getRecommendByCandidatesGuidAndState(String id) {
		return recommendService.getRecommendByCandidatesGuidAndState(id);
	}
	
	
	
}
