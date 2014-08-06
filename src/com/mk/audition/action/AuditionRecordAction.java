package com.mk.audition.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.audition.entity.AuditionRecord;
import com.mk.audition.service.AuditionRecordService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;

@Controller
public class AuditionRecordAction {

	@Autowired
	private AuditionRecordService auditionRecordService;

	/**
	 * 搜索
	 * 
	 * @return
	 */
	@RequestMapping("/audition/searchAuditionRecord.do")
	@ResponseBody
	public void searchAuditionRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		auditionRecordService.searchAuditionRecord(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("面试管理");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();

	}

	/**
	 * 得的
	 * 
	 * @param auditionrecordguid
	 * @return
	 */
	@RequestMapping("/audition/getAuditionRecordByAuditionRecordId.do")
	@ResponseBody
	public AuditionRecord getAuditionRecordByAuditionRecordId(String id) {
		return auditionRecordService.getAuditionRecordByAuditionRecordId(id);
	}
	
	
	/**
	 * 校验是否有面试结果
	 * 
	 * @param auditionrecordguid
	 * @return
	 */
	@RequestMapping("/audition/checkAuditionRecordIsResult.do")
	@ResponseBody
	public AuditionRecord checkAuditionRecordIsResult(String id) throws Exception {
		return auditionRecordService.checkAuditionRecordIsResult(id);
	}

	/**
	 * 保存修改
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/audition/saveOrUpateAuditionRecord.do")
	@ResponseBody
	public void saveOrUpateAuditionRecord(AuditionRecord model) throws Exception {
		auditionRecordService.saveOrUpateAuditionRecord(model);
	}

	
	
	
	/**
	 * 删除
	 * 
	 * @param auditionrecordguid
	 */
	@RequestMapping("/audition/delAuditionRecordByAuditionRecordId.do")
	@ResponseBody
	public void delAuditionRecordByAuditionRecordId(String ids) {
		auditionRecordService.delAuditionRecordByAuditionRecordId(ids);
	}

	/**
	 * 审核发布
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/audition/auditAuditionRecordById.do")
	@ResponseBody
	public void auditAuditionRecordById(String ids, Integer state) throws Exception {
		auditionRecordService.auditAuditionRecordById(ids, state);
	}
}
