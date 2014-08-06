package com.mk.bpmn.service;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.constance.Constance;
import com.mk.framework.grid.util.StringUtils;
import com.mk.quota.dao.QuotaDao;
import com.mk.quota.entity.Quota;
import com.mk.recruitprogram.dao.RecruitprogramDao;
import com.mk.recruitprogram.entity.RecruitProgram;

/**
 * 发文会签流程Service
 * 
 */
@Service
public class DispatchWorkflowService {

	@Autowired
	private SqlSession sqlSession;
	@Autowired
	protected RuntimeService runtimeService;

	@Autowired
	protected TaskService taskService;

	@Autowired
	protected HistoryService historyService;

	@Autowired
	private IdentityService identityService;

	/**
	 * 会签用户
	 * 
	 * @param execution
	 * @return
	 */
	public List<String> getSignUser(Execution execution) {
		List<String> list = new ArrayList<String>();
		list.add("402880873d3a46a5013d3a4baa950f3c");
		list.add("402880873d3a46a5013d3a4babfb0f48");
		return list;
	}

	/**
	 * 是否允许结束会签（多实例） 参数的含义请参考用户手册
	 */
	public Boolean canComplete(DelegateExecution execution, Integer rate, Integer nrOfInstances, Integer nrOfActiveInstances, Integer nrOfCompletedInstances, Integer loopCounter) {
		return loopCounter >= rate;
	}

	/**
	 * 流程招聘计划结束
	 * 
	 * @param variables
	 */
	@Transactional
	public void endTask(DelegateExecution execution) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);

		// 同意
		String processInstanceId = execution.getProcessInstanceId();
		System.out.println("processInstanceId=========" + processInstanceId);
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
		System.out.println("processInstance=========" + processInstance.toString());

		if (processInstance != null) {
			RecruitProgram model = mapper.getRecruitprogramByProcessinstanceId(processInstance.getBusinessKey());

			if (model != null) {
				// 同意次数
				Integer commitNumDb = 0;
				String num = String.valueOf(execution.getVariable("commitNum"));
				System.out.println("=========================="+num);
				if (StringUtils.isNotEmpty(num)) {
					commitNumDb = Integer.valueOf(num);
				}
				if (commitNumDb >= Constance.State_Process) {
					model.setState(Constance.State_Release);
				} else{
					//有1不同意则评为失效
					model.setState(Constance.State_Fail);
					
					//回写细分前的招聘计划(如果是细分出来的计划)
					String code=model.getRecruitprogramcode();
					code=code.substring(0, 8);
					RecruitProgram recruit = mapper.getRecruitprogramByCode(code);
					if(recruit!=null){
						recruit.setPostnum(recruit.getPostnum()+model.getPostnum());
						recruit.setState(Constance.State_Tobe);
						mapper.updateRecruitprogram(recruit);
					}
				}
					
				System.out.println("**********************************************************8");
				System.out.println(model.toString());
				mapper.updateRecruitprogram(model);
			}
		}
	}
	
	
	/**
	 * 流程编制结束
	 * 
	 * @param variables
	 */
	@Transactional
	public void endTaskQuota(DelegateExecution execution) {
		QuotaDao mapper = sqlSession.getMapper(QuotaDao.class);

		// 同意
		String processInstanceId = execution.getProcessInstanceId();
		System.out.println("processInstanceId=========" + processInstanceId);
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
		System.out.println("processInstance=========" + processInstance.toString());

		if (processInstance != null) {
			
			Quota model = mapper.getQuotaByProcessinstanceId(processInstance.getBusinessKey());

			if (model != null) {
				// 同意次数
				Integer commitNumDb = 0;
				String num = String.valueOf(execution.getVariable("commitNum"));
				System.out.println("=========================="+num);
				if (StringUtils.isNotEmpty(num)) {
					commitNumDb = Integer.valueOf(num);
				}
				if (commitNumDb >= Constance.State_Process) {
					model.setState(Constance.State_Normal);
				} else{
					//有1不同意则评为失效
					model.setState(Constance.State_Fail);
				}
				mapper.updateQuota(model);
			}
		}
	}
}
