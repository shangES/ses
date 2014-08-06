package com.mk.employee.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.employee.entity.Train;
import com.mk.employee.service.TrainService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;

@Controller
public class TrainAction {
	@Autowired
	private TrainService trainService;

	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/searchTrain.do")
	public void searchTrain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		trainService.searchTrain(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("培训经历信息表");
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
	@RequestMapping("/employee/saveOrUpdateTrain.do")
	@ResponseBody
	public Train saveOrUpdateTrain(Train model) throws Exception {
		trainService.saveOrUpdateTrain(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/getTrainById.do")
	@ResponseBody
	public Train getTrainById(String id) throws Exception {
		Train model = trainService.getTrainById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/delTrainById.do")
	@ResponseBody
	public void delTrainById(String ids) throws Exception {
		trainService.delTrainById(ids);
	}

}
