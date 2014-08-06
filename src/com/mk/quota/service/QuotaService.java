package com.mk.quota.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.bpmn.dao.BpmnDao;
import com.mk.bpmn.entity.AuditHistory;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Budgetype;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.ProcessConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.quota.dao.QuotaDao;
import com.mk.quota.entity.Quota;
import com.mk.quota.entity.QuotaOperate;
import com.mk.quota.tree.TQuotaTree;
import com.mk.recruitprogram.dao.RecruitprogramDao;
import com.mk.recruitprogram.entity.RecruitProgram;

@Service
public class QuotaService {
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;

	/**
	 * 公司编制树
	 * 
	 * @return
	 */
	public List<TreeNode> buildQuotaTreeByCompanyid(String companyid) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		List<Budgetype> list = mapper.getQuotaBudgettypeByCompanyId(companyid);
		if (list.size() == 0) {
			return null;
		}
		// 成树
		TQuotaTree tree = new TQuotaTree();
		return tree.buildQuotaTreeByCompanyid(list);
	}

	/**
	 * 部门编制树
	 * 
	 * @param postid
	 * @return
	 */
	public List<TreeNode> buildMyQuotaTree(String postid) {
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		CompanyDao comDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postdao = sqlSession.getMapper(PostDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		// 编制
		List<Quota> list = quotaDao.getQuotaByPostid(postid);
		for (Quota model : list) {
			model.convertOneCodeToName(quotaDao, comDao, departmentDao, postdao);
		}

		// 成树
		TQuotaTree tree = new TQuotaTree();
		return tree.dobuild(list);
	}

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchQuota(GridServerHandler grid) {
		QuotaDao mapper = sqlSession.getMapper(QuotaDao.class);
		CompanyDao comDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postdao = sqlSession.getMapper(PostDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		UserContext uc = ContextFacade.getUserContext();

		// 權限組織
		Constance.initAdminPam(grid.getParameters());


		// 统计
		Integer count = mapper.countQuota(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<Quota> list = mapper.searchQuota(grid);
		

		JSONObject json = null;
		int size = list.size();
		
		
//		Integer employednumber = 0;
//		Integer vacancynumber = 0;
//		Integer operatenum = 0;
//		Integer postnumber = 0;
//		Integer budgetnumber = 0;

		for (int i = 0; i < size; i++) {
			Quota model = list.get(i);
			
			// 设置当前任务信息
			if (StringUtils.isNotEmpty(model.getProcessinstanceid())) {
				ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(model.getProcessinstanceid()).singleResult();
				if (pi != null) {
					List<Task> tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).taskAssignee(uc.getUserId()).active().orderByTaskCreateTime().desc().list();
					if (tasks != null && !tasks.isEmpty()) {
						Task task = tasks.get(0);
						model.setTaskid(task.getId());
						model.setAssignee(task.getAssignee());
					}
				}
			}
			
			model.convertOneCodeToName(mapper, comDao, departmentDao, postdao);
			json = JSONUtils.Bean2JSONObject(model);

			data.add(json);

//			employednumber += model.getEmployednumber();
//			vacancynumber += model.getVacancynumber();
//			operatenum += model.getOperatenum();
//			postnumber += model.getPostnumber();
//			budgetnumber += model.getBudgetnumber();
//			// 页面上看到的最后一条显示合计
//			if (i == size - 1) {
//				Quota tmpModel = new Quota();
//				tmpModel.setEmployednumber(employednumber);
//				tmpModel.setVacancynumber(vacancynumber);
//				tmpModel.setOperatenum(operatenum);
//				tmpModel.setPostnumber(postnumber);
//				tmpModel.setBudgetnumber(budgetnumber);
//				data.add(JSONUtils.Bean2JSONObject(tmpModel));
//			}
		}

		grid.setData(data);
	}

	/**
	 * 批量保存
	 * 
	 * @param grid
	 * @return
	 */
	@Transactional
	public String saveQuotaGrid(GridServerHandler grid) {
		QuotaDao mapper = sqlSession.getMapper(QuotaDao.class);
		UserContext uc = ContextFacade.getUserContext();
		
		// 添加的行
		List<Object> quotaList = grid.getInsertedRecords(Quota.class);
		if (!quotaList.isEmpty()) {
			for (Object obj : quotaList) {
				Quota model = (Quota) obj;

				// 设置code
				String maxcode = getMaxQuotaCode();
				model.setQuotacode(maxcode);

				model.setQuotaid(UUIDGenerator.randomUUID());
				model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				mapper.insertQuota(model);
				
				
				// 保存新增的操作
				QuotaOperate operate = new QuotaOperate();
				operate.setQuotaoperateguid(UUIDGenerator.randomUUID());
				operate.setQuotaid(model.getQuotaid());
				operate.setOperatestate(Constance.OperateState_Add);
				operate.setModiuser(uc.getUsername());
				operate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				operate.setOperatenum(model.getBudgetnumber());
				mapper.insertQuotaOperate(operate);
				
				
			}

		}

		if (quotaList.size() > 0)
			return "共 " + quotaList.size() + " 条数据，成功导入！";
		return null;
	}

	/**
	 * 保存及修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateQuota(Quota model) {
		QuotaDao mapper = sqlSession.getMapper(QuotaDao.class);
		UserContext uc = ContextFacade.getUserContext();
		if (StringUtils.isEmpty(model.getQuotaid())) {
			model.setModiuser(uc.getUsername());
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			// 设置code
			String maxcode = getMaxQuotaCode();
			model.setQuotacode(maxcode);
			model.setQuotaid(UUIDGenerator.randomUUID());
			mapper.insertQuota(model);

			// 保存新增的操作
			QuotaOperate operate = new QuotaOperate();
			operate.setQuotaoperateguid(UUIDGenerator.randomUUID());
			operate.setQuotaid(model.getQuotaid());
			operate.setOperatestate(Constance.OperateState_Add);
			operate.setModiuser(uc.getUsername());
			operate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			operate.setOperatenum(model.getBudgetnumber());
			mapper.insertQuotaOperate(operate);

		} else {
			// 取原来的
			Quota quota = mapper.getQuotaById(model.getQuotaid());
			if (!quota.getBudgetnumber().equals(model.getBudgetnumber())) {
				if (quota.getBudgetnumber() > model.getBudgetnumber()) {
					// 编制操作_追减
					int operatenum = quota.getBudgetnumber() - model.getBudgetnumber();
					
					QuotaOperate operate = new QuotaOperate();
					operate.setQuotaoperateguid(UUIDGenerator.randomUUID());
					operate.setQuotaid(model.getQuotaid());
					operate.setOperatestate(Constance.OperateState_Del);
					operate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					operate.setModiuser(uc.getUsername());
					operate.setModimemo(model.getMemo());
					operate.setOperatenum(operatenum);
					mapper.insertQuotaOperate(operate);
				} else {
					// 编制操作_追加
					int operatenum = model.getBudgetnumber() - quota.getBudgetnumber();

					QuotaOperate operate = new QuotaOperate();
					operate.setQuotaoperateguid(UUIDGenerator.randomUUID());
					operate.setQuotaid(model.getQuotaid());
					operate.setOperatestate(Constance.OperateState_Add);
					operate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					operate.setModiuser(uc.getUsername());
					operate.setModimemo(model.getMemo());
					operate.setOperatenum(operatenum);
					mapper.insertQuotaOperate(operate);
				}
			}else{
				//做修改操作
				QuotaOperate ope = new QuotaOperate();
				ope.setQuotaoperateguid(UUIDGenerator.randomUUID());
				ope.setQuotaid(model.getQuotaid());
				ope.setOperatestate(Constance.OperateState_edit);
				ope.setModiuser(uc.getUsername());
				ope.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				ope.setOperatenum(Constance.VALID_NO);
				ope.setModimemo(model.getMemo());
				mapper.insertQuotaOperate(ope);
			}
			
			// 拿原来的编制
			Quota tmpModel = mapper.getQuotaById(model.getQuotaid());
			if(tmpModel!=null){
				// 走流程
				if (model.isRunprocess()){
					if(model.getBudgetnumber()!=null&&tmpModel.getBudgetnumber()!=null&&!tmpModel.getBudgetnumber().equals(model.getBudgetnumber())){
						// 流程定义已经挂起
						ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(ProcessConstance.PD_Quota).active().latestVersion()
								.singleResult();
						if (!processDefinition.isSuspended()) {

							// 启动流程
							// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
							String businessKey = UUIDGenerator.randomUUID();
							identityService.setAuthenticatedUserId(uc.getUserId());
							ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessConstance.PD_Quota, businessKey, model.getVariables());
							model.setProcessinstanceid(processInstance.getBusinessKey());

							// 如果当前用户是审核用户则提交
							List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee(uc.getUserId()).active().orderByTaskCreateTime().desc().list();
							if (tasks != null && !tasks.isEmpty()) {
								Task task = tasks.get(0);
								completeTask(task.getId(), ProcessConstance.commit, "同意", Constance.State_Process);
							}

							// 流程中
							model.setState(Constance.State_Process);
						}
					}
				}
			}

			mapper.updateQuota(model);
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Quota getQuotaById(String id,String taskid) {
		QuotaDao mapper = sqlSession.getMapper(QuotaDao.class);
		CompanyDao comDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postdao = sqlSession.getMapper(PostDao.class);
		DepartmentDao deptDao = sqlSession.getMapper(DepartmentDao.class);
		BpmnDao bpmnDao = sqlSession.getMapper(BpmnDao.class);
		Quota model = mapper.getQuotaById(id);
		if (model != null){
			// 取到该流程的任务
			if (taskid != null && taskid != "") {
				Task task = taskService.createTaskQuery().taskId(taskid).active().singleResult();
				if (task != null) {
					model.setTaskid(taskid);
					model.setAssignee(task.getAssignee());
				}
			}

			// 审核历史
			if (StringUtils.isNotEmpty(model.getProcessinstanceid())) {
				List<AuditHistory> audithistorys = bpmnDao.getAuditHistoryByProcessInstanceId(model.getProcessinstanceid());
				model.setAudithistorys(audithistorys);
			}
						
			model.convertOneCodeToName(mapper, comDao, deptDao, postdao);
		}
			
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delQuotaByQuotaid(String ids) {
		QuotaDao mapper = sqlSession.getMapper(QuotaDao.class);
		RecruitprogramDao programDao = sqlSession.getMapper(RecruitprogramDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			programDao.delRecruitprogramByQuotaId(id);
			mapper.delQuotaOperateByQuotaid(id);
			mapper.delQuotaByQuotaid(id);
		}
	}

	/**
	 * 得到最大code
	 * 
	 * @param id
	 * @return
	 */
	public String getMaxQuotaCode() {
		QuotaDao mapper = sqlSession.getMapper(QuotaDao.class);
		// 當天最大code
		String maxcode = mapper.getMaxQuotaCode();

		if (StringUtils.isNotEmpty(maxcode)) {
			return UUIDGenerator.format0000_ID(Constance.quota_prefix, maxcode, 1);
		}
		return UUIDGenerator.format0000_ID(Constance.quota_prefix, maxcode, 1);
	}

	/**
	 * 验证岗位下编制存在
	 * 
	 * @param quotaid
	 * @param postid
	 * @param budgettype
	 * @param state
	 * @return
	 */
	public String checkQuotaByPostIdAndBudgettype(String quotaid, String postid, String budgettype, Integer state) {
		QuotaDao mapper = sqlSession.getMapper(QuotaDao.class);
		CompanyDao comDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postdao = sqlSession.getMapper(PostDao.class);
		DepartmentDao deptDao = sqlSession.getMapper(DepartmentDao.class);

		StringBuffer buffer = new StringBuffer();
		if (StringUtils.isNotEmpty(budgettype)) {
			List<Quota> list = mapper.getQuotaByPostIdAndBudgettype(quotaid, postid, budgettype, state);
			if (!list.isEmpty()) {
				buffer.append("此岗位下编制类型[");
				for (Quota model : list) {
					model.convertOneCodeToName(mapper, comDao, deptDao, postdao);
					buffer.append(model.getBudgettypename());
				}
				buffer.append("]已经存在！");
				return buffer.toString();
			}
		}
		return buffer.toString();
	}
	
	
	/**
	 * 流程审核
	 * 
	 * @param variables
	 */
	@Transactional
	public void completeTask(String taskid, Integer commit, String result, Integer rate) {
		BpmnDao bpmnDao = sqlSession.getMapper(BpmnDao.class);

		Task task = taskService.createTaskQuery().taskId(taskid).active().singleResult();
		if (task != null) {
			Map map = taskService.getVariables(task.getId());
			// 同意
			int commitNum = 1;
			String commitNumDb = String.valueOf(map.get("commitNum"));
			
			
			if (StringUtils.isNotEmpty(commitNumDb)) {
				commitNum = Integer.valueOf(commitNumDb);
				if (commit.equals(ProcessConstance.commit))
					commitNum=commitNum+1;
			}
			System.out.println("=============commitNum============="+commitNum);
			// 变量
			map.put("commitNum", commitNum);
			map.put("commit", commit);
			map.put("rate", rate);

			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
			if (processInstance != null) {
				// 审核意见
				String businessKey = processInstance.getBusinessKey();
				AuditHistory audit = new AuditHistory(businessKey, task, result);
				bpmnDao.insertAuditHistory(audit);

				// 流程提交
				taskService.complete(task.getId(), map);
			}
		}
	}
}
