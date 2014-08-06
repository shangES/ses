package com.mk.bpmn.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.bpmn.ProcessDefinitionCache;
import com.mk.bpmn.entity.MkProcessDefinition;
import com.mk.framework.constance.ProcessConstance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;

@Service
public class WorkflowProcessDefinitionService {
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected HistoryService historyService;

	/**
	 * 流程发布
	 * 
	 * @param inputStream
	 */
	public void deployment(String name, ZipInputStream inputStream) {
		repositoryService.createDeployment().name(name).addZipInputStream(inputStream).deploy();
	}

	/**
	 * 搜索流程定义列表
	 * 
	 * @param grid
	 */
	public void searchProcessList(GridServerHandler grid) {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc();
		// 统计
		long count = processDefinitionQuery.count();
		PageUtils.setTotalRows(grid, (int) count);

		// 分页
		List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(grid.getStartRowNum(), grid.getEndRowNum());
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (ProcessDefinition processDefinition : processDefinitionList) {
			String deploymentId = processDefinition.getDeploymentId();
			Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();

			MkProcessDefinition model = new MkProcessDefinition(processDefinition, deployment);
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);

	}

	/**
	 * 删除流程定义列表
	 * 
	 * @param ids
	 */
	@Transactional
	public void deleteDeployment(String ids) {
		if (StringUtils.isNotEmpty(ids)) {
			String[] obj = ids.split(",");
			for (String id : obj) {
				repositoryService.deleteDeployment(id, true);
			}
		}
	}

	/**
	 * 挂起、激活流程实例
	 */
	@Transactional
	public void activateProcessDefinitionById(String processdefinitionid, Integer state) {
		if (state.equals(ProcessConstance.ProcessDefinition_active)) {
			repositoryService.activateProcessDefinitionById(processdefinitionid, true, null);
		} else if (state.equals(ProcessConstance.ProcessDefinition_suspend)) {
			repositoryService.suspendProcessDefinitionById(processdefinitionid, true, null);
		}
	}

	// ======================================================================================================

	/**
	 * 搜索运行中的列表
	 * 
	 * @param grid
	 */
	public void searchRunningPrcessInstance(GridServerHandler grid) {
		ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
		ProcessDefinitionCache.setRepositoryService(repositoryService);

		// 统计
		long count = processInstanceQuery.count();
		PageUtils.setTotalRows(grid, (int) count);

		// 分页
		List<ProcessInstance> list = processInstanceQuery.listPage(grid.getStartRowNum(), grid.getEndRowNum());
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (ProcessInstance model : list) {
			JSONObject obj = JSONUtils.Bean2JSONObject(model);

			List<String> actives = runtimeService.getActiveActivityIds(model.getId());

			// 当前环节
			if (!actives.isEmpty()) {
				//System.out.println(actives.toString());
				String activityname = ProcessDefinitionCache.getActivityName(model.getProcessDefinitionId(), actives.get(0));
				obj.accumulate("activityname", activityname);
			}
			data.add(obj);
		}
		grid.setData(data);
	}

	/**
	 * 挂起、激活流程实例
	 */
	@Transactional
	public void activateProcessInstanceById(String processinstanceid, Integer state) {
		if (state.equals(ProcessConstance.ProcessDefinition_active)) {
			runtimeService.activateProcessInstanceById(processinstanceid);
		} else if (state.equals(ProcessConstance.ProcessDefinition_suspend)) {
			runtimeService.suspendProcessInstanceById(processinstanceid);
		}
	}

	/**
	 * 删除流程实例
	 * 
	 * @param ids
	 */
	public void deleteProcessInstance(String ids) {
		if (StringUtils.isNotEmpty(ids)) {
			String[] obj = ids.split(",");
			for (String id : obj) {
				runtimeService.deleteProcessInstance(id, null);
			}
		}

	}

	/**
	 * 读取资源，通过部署ID
	 * 
	 * @param deploymentId
	 * @param resourceName
	 * @return
	 */
	public InputStream loadDeploymentResource(String deploymentid, String resourcename) {
		return repositoryService.getResourceAsStream(deploymentid, resourcename);
	}
}
