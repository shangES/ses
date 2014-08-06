package com.mk.bpmn.action;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.bpmn.service.WorkflowProcessDefinitionService;
import com.mk.bpmn.service.WorkflowTraceService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;

@Controller
public class WorkFlowAction {

	@Autowired
	private WorkflowProcessDefinitionService workflowProcessDefinitionService;
	@Autowired
	private WorkflowTraceService workflowTraceService;

	/**
	 * 搜索流程定义列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/bpmn/searchProcessList.do")
	public void searchProcessList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		workflowProcessDefinitionService.searchProcessList(grid);

		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("流程定义列表");
			report.reportGrid(grid);
		} else {
			grid.printLoadResponseText();
		}
	}

	/**
	 * 删除流程定义列表
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/bpmn/deleteDeployment.do")
	@ResponseBody
	public void deleteDeployment(String ids) throws Exception {
		workflowProcessDefinitionService.deleteDeployment(ids);
	}

	/**
	 * 挂起、激活流程定义
	 */
	@RequestMapping("/bpmn/activateProcessDefinitionById.do")
	@ResponseBody
	public void activateProcessDefinitionById(String processdefinitionid, Integer state) {
		workflowProcessDefinitionService.activateProcessDefinitionById(processdefinitionid, state);
	}

	/**
	 * 搜索运行中的流程列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/bpmn/searchRunningPrcessInstance.do")
	public void searchRunningPrcessInstance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		workflowProcessDefinitionService.searchRunningPrcessInstance(grid);

		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("运行中的流程列表");
			report.reportGrid(grid);
		} else {
			grid.printLoadResponseText();
		}
	}

	/**
	 * 挂起、激活流程实例
	 */
	@RequestMapping("/bpmn/activateProcessInstanceById.do")
	@ResponseBody
	public void activateProcessInstanceById(String processinstanceid, Integer state) {
		workflowProcessDefinitionService.activateProcessInstanceById(processinstanceid, state);
	}

	/**
	 * 删除流程实例
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/bpmn/deleteProcessInstance.do")
	@ResponseBody
	public void deleteProcessInstance(String ids) throws Exception {
		workflowProcessDefinitionService.deleteProcessInstance(ids);
	}

	/**
	 * 读取资源，通过部署ID
	 * 
	 * @param deploymentid
	 * @param resourcename
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/bpmn/loadDeploymentResource.do")
	public void loadDeploymentResource(String deploymentid, String resourcename, HttpServletResponse response) throws Exception {
		InputStream resourceAsStream = workflowProcessDefinitionService.loadDeploymentResource(deploymentid, resourcename);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 读取资源，通过流程ID
	 * 
	 * @param resourceType
	 * @param processInstanceId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/bpmn/loadByProcessInstance.do")
	public void loadByProcessInstance(String resourceType, String processInstanceId, HttpServletResponse response) throws Exception {
		InputStream resourceAsStream = workflowTraceService.loadByProcessInstance(resourceType, processInstanceId);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 输出跟踪流程信息
	 * 
	 * @param processInstanceId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bpmn/traceProcess.do")
	@ResponseBody
	public List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception {
		List<Map<String, Object>> activityInfos = workflowTraceService.traceProcess(processInstanceId);
		return activityInfos;
	}

	/**
	 * 读取带跟踪的图片
	 * 
	 * @param executionId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/bpmn/readResource/{executionId}.do")
	public void readResource(@PathVariable("executionId") String executionId, HttpServletResponse response) throws Exception {
		InputStream imageStream = workflowTraceService.readResource(executionId);
		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

}
