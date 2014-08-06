package com.mk.todo.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.employee.entity.Employee;
import com.mk.framework.chart.ChartData;
import com.mk.framework.chart.ChartModel;
import com.mk.framework.chart.ChartSearch;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.person.entity.Person;
import com.mk.recruitprogram.entity.RecruitProgram;
import com.mk.report.entity.PCD0300;
import com.mk.report.service.ReportService;
import com.mk.todo.entity.DeliverData;
import com.mk.todo.entity.MsgData;
import com.mk.todo.entity.TodoPam;
import com.mk.todo.service.TodoService;

@Controller
public class TodoAction {
	@Autowired
	private TodoService todoService;
	@Autowired
	private ReportService reportService;

	
	/**
	 * 将要过生日的员工
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/todo/getEmployeeBirthdayTodo.do")
	@ResponseBody
	public List<Employee> getEmployeeBirthdayTodo(@RequestBody TodoPam pam) throws Exception {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null)
			return null;

		return todoService.getEmployeeBirthdayTodo(pam);
	}

	/**
	 * 异动待生效的员工
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/todo/getEmployeePosationTodo.do")
	@ResponseBody
	public List<Employee> getEmployeePosationTodo(@RequestBody TodoPam pam) throws Exception {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null)
			return null;

		return todoService.getEmployeePosationTodo(pam);
	}

	/**
	 * 合同将要到期的员工
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/todo/getEmployeeContractTodo.do")
	@ResponseBody
	public List<Employee> getEmployeeContractTodo(@RequestBody TodoPam pam) throws Exception {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null)
			return null;

		return todoService.getEmployeeContractTodo(pam);
	}

	/**
	 * 待转正的员工
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/todo/getEmployeeZhuZhengTodo.do")
	@ResponseBody
	public List<Employee> getEmployeeZhuZhengTodo(@RequestBody TodoPam pam) throws Exception {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null)
			return null;

		return todoService.getEmployeeZhuZhengTodo(pam);
	}

	/**
	 * 招聘计划审批
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/todo/getRecruitprogramTodo.do")
	@ResponseBody
	public List<RecruitProgram> getRecruitprogramTodo(@RequestBody TodoPam pam) throws Exception {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null)
			return null;

		return todoService.getRecruitprogramTodo(uc.getUserId());
	}

	/**
	 * 安排面试
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/todo/getInterviewTodo.do")
	@ResponseBody
	public List<MyCandidates> getInterviewTodo(@RequestBody TodoPam pam) throws Exception {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null)
			return null;

		return todoService.getInterviewTodo(pam);
	}

	/**
	 * 待安排体检
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/todo/getExaminationTodo.do")
	@ResponseBody
	public List<MyCandidates> getExaminationTodo(@RequestBody TodoPam pam) throws Exception {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null)
			return null;

		return todoService.getExaminationTodo(pam);
	}

	/**
	 * 待确定入职的员工
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/todo/getEntryOkTodo.do")
	@ResponseBody
	public List<MyCandidates> getEntryOkTodo(@RequestBody TodoPam pam) throws Exception {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null)
			return null;

		return todoService.getEntryOkTodo(pam);
	}

	/**
	 * 待入职的招聘员工
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/todo/getEntryTodo.do")
	@ResponseBody
	public List<Person> getEntryTodo(@RequestBody TodoPam pam) throws Exception {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null)
			return null;

		return todoService.getEntryTodo(pam);
	}

	/**
	 * 面试人员列表
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/todo/getAuditionTodo.do")
	@ResponseBody
	public List<MyCandidates> getAuditionTodo(@RequestBody TodoPam pam) throws Exception {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null)
			return null;

		return todoService.getAuditionTodo(pam);
	}

	/**
	 * 待认定的简历
	 * 
	 * @param pam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/todo/getAffirmMyCandidatesTodo.do")
	@ResponseBody
	public List<MyCandidates> getAffirmMyCandidatesTodo(@RequestBody TodoPam pam) throws Exception {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null) {
			return null;
		}
		return todoService.getAffirmMyCandidatesTodo(pam);
	}

	/**
	 * 面試結果
	 * 
	 * @param pam
	 * @return
	 */
	@RequestMapping("/todo/getAffirmAuditionResultsTodo.do")
	@ResponseBody
	public List<MyCandidates> getAffirmAuditionResultsTodo(@RequestBody TodoPam pam) {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null) {
			return null;
		}
		return todoService.getAffirmAuditionResultsTodo(pam);
	}

	/**
	 * 消息框
	 * 
	 * @param pam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/todo/getMsgData.do")
	@ResponseBody
	public MsgData getMsgData(@RequestBody TodoPam pam) throws Exception {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null) {
			return null;
		}
		return todoService.getMsgData(pam);
	}
	
	
	/**
	 * 投递情况
	 * 
	 * @param pam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/todo/getDeliverTodo.do")
	@ResponseBody
	public DeliverData getDeliverTodo(@RequestBody TodoPam pam) throws Exception {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null) {
			return null;
		}
		return todoService.getDeliverTodo(pam);
	}
	

	/**
	 * 应聘状态统计图
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/todo/loadCountMyCandidatesChart.do")
	public ModelAndView loadCountMyCandidatesChart(ModelMap map, ChartSearch search) throws Exception {
		// 设置
		map.put("s3d", search.isS3d());
		map.put("legendEnabled", search.isLegendEnabled());
		map.put("legendPosition", search.getLegendPosition());
		map.put("chart", search.getType());
		map.put("cylinder", search.getCylinder());

		// 数据
		List<ChartData> list = todoService.loadCountMyCandidatesChart(search);

		map.put("data", list);
		return new ModelAndView("/pages/chart/data.jsp");
	}

	/**
	 * 来源渠道统计图
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/todo/loadCountMyCandidatesTypeChart.do")
	public ModelAndView loadCountMyCandidatesTypeChart(ModelMap map, ChartSearch search) throws Exception {
		// 设置
		map.put("s3d", search.isS3d());
		map.put("legendEnabled", search.isLegendEnabled());
		map.put("legendPosition", search.getLegendPosition());
		map.put("chart", search.getType());
		map.put("cylinder", search.getCylinder());

		// 数据
		List<ChartData> list = todoService.loadCountMyCandidatesTypeChart(search);

		map.put("data", list);
		return new ModelAndView("/pages/chart/data.jsp");
	}

	/**
	 * 编制情况统计图
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/todo/myBZQKChartBody.do")
	public ModelAndView myBZQKChartBody(ModelMap map, ChartSearch search) throws Exception {
		// 设置
		map.put("s3d", search.isS3d());
		map.put("legendEnabled", search.isLegendEnabled());
		map.put("legendPosition", search.getLegendPosition());
		map.put("chart", search.getType());
		map.put("cylinder", search.getCylinder());

		// 编制
		List<ChartModel> list1 = todoService.loadmyBZQKChartBody(search);
		map.put("data1", list1);
		List<ChartModel> list2 = todoService.loadmyZBQKChartBody(search);
		map.put("data2", list2);
		return new ModelAndView("/pages/chart/bzqk.jsp");
	}
	
	/**
	 * 当日投递情况
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/todo/myDeliverChartBody.do")
	public ModelAndView myDeliverChartBody(ModelMap map) throws Exception {
		TodoPam pam = new TodoPam();
		DeliverData deliverdata = todoService.getDeliverTodo(pam);
		map.put("data", deliverdata);
		return new ModelAndView("/pages/chart/deliver.jsp");
	}
	
	/**
	 * 招聘计划统计图
	 * 
	 * @param map
	 * @param search
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("todo/myRecuritprogramChartBody.do")
	@ResponseBody
	public List<PCD0300> myRecuritprogramChartBody(@RequestBody TodoPam pam) throws Exception {
		UserContext uc = ContextFacade.getUserContext();
		if (uc == null || uc.getUserId() == null) {
			return null;
		}
		return reportService.searchPCD_0300(pam);
		// 设置
		/*map.put("s3d", search.isS3d());
		map.put("legendEnabled", search.isLegendEnabled());
		map.put("legendPosition", search.getLegendPosition());
		map.put("chart", search.getType());
		map.put("cylinder", search.getCylinder());
	
		// 数据
		List<ChartModel> list = todoService.loadmyRecuritprogramChartBody(search);
	
		map.put("data", list);
		return new ModelAndView("/pages/chart/recruitprogram.jsp");*/
	}
}
