package com.mk.person.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.person.entity.ExaminationRecord;
import com.mk.person.service.ExaminationRecordService;

@Controller
public class ExaminationRecordAction {

	@Autowired
	private ExaminationRecordService examinationRecordService;

	/**
	 * 搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/examinationrecord/searchExaminationRecord.do")
	@ResponseBody
	public void searchExaminationRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {

		GridServerHandler grid = new GridServerHandler(request, response);
		examinationRecordService.searchExaminationRecord(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("体检记录");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * 保存或修改
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/examinationrecord/saveOrUpdateExaminationRecord.do")
	@ResponseBody
	public ExaminationRecord saveOrUpdateExaminationRecord(ExaminationRecord model) throws Exception {
		examinationRecordService.saveOrUpdateExaminationRecord(model);
		return model;

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/examinationRecord/getExaminationRecordById.do")
	@ResponseBody
	public ExaminationRecord getExaminationRecordById(String id) {
		ExaminationRecord model = examinationRecordService.getExaminationRecordById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/examinationrecord/delExaminatioRecordById.do")
	@ResponseBody
	public void delExaminatioRecordById(String ids) throws Exception {
		examinationRecordService.delExaminatioRecordById(ids);
	}
}
