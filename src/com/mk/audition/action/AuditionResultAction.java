package com.mk.audition.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.audition.entity.AuditionResult;
import com.mk.audition.service.AuditionResultService;
import com.mk.framework.grid.GridServerHandler;

@Controller
public class AuditionResultAction {
	@Autowired
	private AuditionResultService auditionResultService;

	/**
	 * 保存修改
	 * 
	 * @param model
	 * @throws Exception 
	 */
	@RequestMapping("/audition/saveOrUpateAuditionResult.do")
	@ResponseBody
	public void saveOrUpateAuditionResult(AuditionResult model) throws Exception {
		auditionResultService.saveOrUpateAuditionResult(model);
	}
	
	/**
	 * 保存面试结果
	 * 
	 * @param model
	 * @throws Exception 
	 */
	@RequestMapping("/audition/saveResultByMyCandidatesGuid.do")
	@ResponseBody
	public void saveResultByMyCandidatesGuid(AuditionResult model) throws Exception {
		auditionResultService.saveResultByMyCandidatesGuid(model);
	}

	/**
	 * 得的
	 * 
	 * @param auditionrecordguid
	 * @return
	 */
	@RequestMapping("/audition/getAuditionResultByAuditionResultId.do")
	@ResponseBody
	public AuditionResult getAuditionResultByAuditionResultId(String auditionrecordguid) {
		return auditionResultService.getAuditionResultByAuditionResultId(auditionrecordguid);
	}
	
	/**
	 * 批量保存
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/audition/saveAuditionResult.do")
	@ResponseBody
	public void saveAuditionResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		auditionResultService.saveAuditionResult(grid);
	}
}
