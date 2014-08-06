package com.mk.recruitprogram.service;

import java.sql.Date;
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
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.constance.ProcessConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreePageGrid;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.quota.dao.QuotaDao;
import com.mk.recruitprogram.dao.RecruitprogramDao;
import com.mk.recruitprogram.entity.RecruitProgram;
import com.mk.recruitprogram.entity.RecruitProgramOperate;
import com.mk.system.dao.OptionDao;

@Service
public class RecruitprogramService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CodeConvertNameService codeConvertNameService;

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
	 * 招聘计划树
	 * 
	 * @param grid
	 * @return
	 */
	public List<RecruitProgram> searchRecruitprogramTree(TreePageGrid grid) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = mapper.countRecruitprogramTree(grid);
		grid.getPage().setTotalrows(count);
		grid.getPage().init();
		// 分页
		List<RecruitProgram> list = mapper.searchRecruitprogramTree(grid);

		if (list.size() >= Constance.ConvertCodeNum) {
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 职级
			Map<String, String> rankMap = codeConvertNameService.getAllRankMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 編制
			Map<String, String> quotaMap = codeConvertNameService.getAllQuotaMap();
			// 状态
			Map<Integer, String> stateMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RecruitProgram_state);

			for (RecruitProgram model : list) {
				model.convertManyCodeToName(companyMap, deptMap, postMap, quotaMap, rankMap, quotaDao, stateMap);
			}
		} else {
			for (RecruitProgram model : list) {
				model.convertOneCodeToName(companyDao, departmentDao, postDao, quotaDao, optionDao);
			}
		}

		return list;
	}

	/**
	 * 搜索得到列表
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchRecruitprogram(GridServerHandler grid) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();

		// 取多个部门
		String deptids = grid.getPageParameter("deptid");
		if (StringUtils.isNotEmpty(deptids)) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("and c.deptid in (");
			buffer.append(deptids);
			buffer.append(")");
			Map map = grid.getParameters();
			map.put("deptid", buffer.toString());
		}

		// 统计
		Integer count = mapper.countRecruitprogram(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<RecruitProgram> list = mapper.searchRecruitprogram(grid);

		UserContext uc = ContextFacade.getUserContext();
		if (list.size() >= Constance.ConvertCodeNum) {
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 职级
			Map<String, String> rankMap = codeConvertNameService.getAllRankMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 編制
			Map<String, String> quotaMap = codeConvertNameService.getAllQuotaMap();
			// 状态
			Map<Integer, String> stateMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RecruitProgram_state);

			for (RecruitProgram model : list) {
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

				model.convertManyCodeToName(companyMap, deptMap, postMap, quotaMap, rankMap, quotaDao, stateMap);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (RecruitProgram model : list) {
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

				model.convertOneCodeToName(companyDao, departmentDao, postDao, quotaDao, optionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
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
	public String saveRecruitProgramGrid(GridServerHandler grid) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		UserContext uc = ContextFacade.getUserContext();
		// 添加的行
		List<Object> addList = grid.getInsertedRecords(RecruitProgram.class);
		if (!addList.isEmpty()) {
			for (Object obj : addList) {
				// 招聘计划信息
				RecruitProgram model = (RecruitProgram) obj;
				model.setRecruitprogramguid(UUIDGenerator.randomUUID());
				model.setModitimestamp(new Timestamp(System.currentTimeMillis()));

				// 取得最大code
				String maxcode = mapper.getMaxRecruitProgramCode();
				model.setRecruitprogramcode(UUIDGenerator.format0000_ID(null, maxcode, 1));
				mapper.insertRecruitprogram(model);

				// 批量导入操作信息
				RecruitProgramOperate operate = new RecruitProgramOperate();
				operate.setRecruitprogramoperateguid(UUIDGenerator.randomUUID());
				operate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				operate.setOperatenum(model.getPostnum());
				operate.setOperatestate(Constance.OperateState_Add);
				operate.setModiuser(uc.getUsername());
				operate.setRecruitprogramguid(model.getRecruitprogramguid());
				mapper.insertRecruitProgramOperate(operate);
			}

		}

		if (addList.size() > 0)
			return "共 " + addList.size() + " 条数据，成功导入！";
		return null;
	}

	/**
	 * 保存or修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateRecruitProgram(RecruitProgram model) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		UserContext uc = ContextFacade.getUserContext();
		if (StringUtils.isEmpty(model.getRecruitprogramguid())) {
			model.setRecruitprogramguid(UUIDGenerator.randomUUID());
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));

			// 设置code
			String maxcode = getMaxRecruitProgramCode();
			model.setRecruitprogramcode(maxcode);

			mapper.insertRecruitprogram(model);

			// 新增操作
			RecruitProgramOperate operate = new RecruitProgramOperate();
			operate.setRecruitprogramoperateguid(UUIDGenerator.randomUUID());
			operate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			operate.setOperatenum(model.getPostnum());
			operate.setOperatestate(Constance.OperateState_Add);
			operate.setModiuser(uc.getUsername());
			operate.setRecruitprogramguid(model.getRecruitprogramguid());
			mapper.insertRecruitProgramOperate(operate);
		} else {
			// 取原来的招聘计划
			RecruitProgram recruit = mapper.getRecruitprogramById(model.getRecruitprogramguid());
			if (!recruit.getPostnum().equals(model.getPostnum())) {
				if (recruit.getPostnum() > model.getPostnum()) {
					// 招聘计划操作_追减
					int operatenum = recruit.getPostnum() - model.getPostnum();

					// 追减保存
					RecruitProgramOperate operate = new RecruitProgramOperate();
					operate.setRecruitprogramoperateguid(UUIDGenerator.randomUUID());
					operate.setRecruitprogramguid(model.getRecruitprogramguid());
					operate.setOperatenum(operatenum);
					operate.setOperatestate(Constance.OperateState_Del);
					operate.setModiuser(uc.getUsername());
					operate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					operate.setModimemo(model.getMemo());

					mapper.insertRecruitProgramOperate(operate);
				} else {
					// 招聘计划操作_追加
					int operatenum = model.getPostnum() - recruit.getPostnum();

					// 追加保存
					RecruitProgramOperate operate = new RecruitProgramOperate();
					operate.setRecruitprogramoperateguid(UUIDGenerator.randomUUID());
					operate.setRecruitprogramguid(model.getRecruitprogramguid());
					operate.setOperatenum(operatenum);
					operate.setOperatestate(Constance.OperateState_Add);
					operate.setModiuser(uc.getUsername());
					operate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					operate.setModimemo(model.getMemo());

					mapper.insertRecruitProgramOperate(operate);
				}
			} else {
				// 保存修改的操作
				RecruitProgramOperate ope = new RecruitProgramOperate();
				ope.setRecruitprogramoperateguid(UUIDGenerator.randomUUID());
				ope.setRecruitprogramguid(model.getRecruitprogramguid());
				ope.setOperatestate(Constance.OperateState_edit);
				ope.setModiuser(uc.getUsername());
				ope.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				ope.setOperatenum(Constance.VALID_NO);
				ope.setModimemo(model.getMemo());
				mapper.insertRecruitProgramOperate(ope);
			}

			// 检查职级是否改变
			RecruitProgram tmpModel = mapper.getRecruitprogramById(model.getRecruitprogramguid());
			if (tmpModel != null) {

				// 走流程
				if (model.isRunprocess()){
					// 职级、计划到岗时间、招聘人数调整走流程审核
					if ((StringUtils.isNotEmpty(tmpModel.getRankid()) && StringUtils.isNotEmpty(model.getRankid()) && !tmpModel.getRankid().equals(model.getRankid()))||(tmpModel.getHillockdate()!=null && model.getHillockdate()!=null && !tmpModel.getHillockdate().equals(model.getHillockdate()))||(tmpModel.getPostnum()!=null&&model.getPostnum()!=null&&!tmpModel.getPostnum().equals(model.getPostnum()))) {
						// 流程定义已经挂起
						ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(ProcessConstance.PD_RecruitProgram).active().latestVersion()
								.singleResult();
						if (!processDefinition.isSuspended()) {

							// 启动流程
							// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
							String businessKey = UUIDGenerator.randomUUID();
							identityService.setAuthenticatedUserId(uc.getUserId());
							ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessConstance.PD_RecruitProgram, businessKey, model.getVariables());
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

				mapper.updateRecruitprogram(model);
			}
		}

	}

	/**
	 * 细分操作
	 * 
	 * @param model
	 */
	@Transactional
	public void UpdateRecruitProgram(RecruitProgram model) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		UserContext uc = ContextFacade.getUserContext();
		// if
		// (StringUtils.isEmpty(model.getRecruitprogramguid())&&StringUtils.isEmpty(model.getPdeptid()))
		// {
		// model.setRecruitprogramguid(UUIDGenerator.randomUUID());
		// model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		//
		// // 设置code
		// String maxcode = getMaxRecruitProgramCode();
		// model.setRecruitprogramcode(maxcode);
		//
		// mapper.insertRecruitprogram(model);
		//
		// //新增操作
		// RecruitProgramOperate operate = new RecruitProgramOperate();
		// operate.setRecruitprogramoperateguid(UUIDGenerator.randomUUID());
		// operate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		// operate.setOperatenum(model.getPostnum());
		// operate.setOperatestate(Constance.OperateState_Add);
		// operate.setModiuser(uc.getUsername());
		// operate.setRecruitprogramguid(model.getRecruitprogramguid());
		// mapper.insertRecruitProgramOperate(operate);
		// } else {
		// String pdeptid=model.getPdeptid();
		// if(StringUtils.isNotEmpty(pdeptid)){
		// model.setDeptid(pdeptid);
		// }
		// model.setModiuser(uc.getUsername());
		// model.setState(Constance.VALID_YES);
		// mapper.updateRecruitprogram(model);
		// }

		// 未有招聘计划id保存
		if (StringUtils.isNotEmpty(model.getRecruitprogramguid())) {
			// 生成一张招聘计划
			RecruitProgram program = new RecruitProgram();
			program.setRecruitprogramguid(UUIDGenerator.randomUUID());
			program.setInterfacecode(model.getRecruitprogramguid());

			// 新生成的招聘计划code跟上级code关系在一起
			program.setRecruitprogramcode(model.getRecruitprogramcode());
			int codenum = mapper.countRecruitprogramByCode(model.getRecruitprogramcode());
			program.setRecruitprogramcode(model.getRecruitprogramcode() + "_" + codenum);

			program.setCompanyid(model.getCompanyid());
			// 设置部门id
			if (StringUtils.isNotEmpty(model.getPdeptid())) {
				program.setDeptid(model.getPdeptid());
			} else {
				program.setDeptid(model.getDeptid());
			}

			program.setPostid(model.getPostid());
			program.setRankid(model.getRankid());
			program.setQuotaid(model.getQuotaid());
			program.setApplydate(new Date(System.currentTimeMillis()));
			program.setPostnum(model.getRemainnum());
			program.setState(Constance.VALID_YES);
			program.setModiuser(uc.getUsername());
			program.setHillockdate(model.getHillockdate());
			program.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			mapper.insertRecruitprogram(program);

			// 招聘计划操作信息
			RecruitProgramOperate ope = new RecruitProgramOperate();
			ope.setRecruitprogramoperateguid(UUIDGenerator.randomUUID());
			ope.setRecruitprogramguid(program.getRecruitprogramguid());
			ope.setOperatestate(Constance.OperateState_Add);
			ope.setModiuser(uc.getUsername());
			ope.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			ope.setOperatenum(program.getPostnum());
			mapper.insertRecruitProgramOperate(ope);

			// 回写招聘计划
			RecruitProgram recruitprogram = mapper.getRecruitprogramById(model.getRecruitprogramguid());
			if (recruitprogram != null) {
				// 查看本次是否走流程
				// 走流程
				if (model.isRunprocess()) {
					// 职级、计划到岗时间调整走流程审核!recruitprogram.getRankid().equals(model.getRankid())
					if ((StringUtils.isNotEmpty(recruitprogram.getRankid()) && StringUtils.isNotEmpty(model.getRankid()) && (Integer.valueOf(model.getRankid()) > Integer.valueOf(recruitprogram
							.getRankid()))) || ((recruitprogram.getHillockdate() != null) && model.getHillockdate() != null && !recruitprogram.getHillockdate().equals(model.getHillockdate()))) {
						// 流程定义已经挂起
						ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(ProcessConstance.PD_RecruitProgram).active().latestVersion()
								.singleResult();
						if (!processDefinition.isSuspended()) {

							// 启动流程
							// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
							String businessKey = UUIDGenerator.randomUUID();
							identityService.setAuthenticatedUserId(uc.getUserId());
							ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessConstance.PD_RecruitProgram, businessKey, model.getVariables());
							program.setProcessinstanceid(processInstance.getBusinessKey());

							// 如果当前用户是审核用户则提交
							List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee(uc.getUserId()).active().orderByTaskCreateTime().desc().list();
							if (tasks != null && !tasks.isEmpty()) {
								Task task = tasks.get(0);
								completeTask(task.getId(), ProcessConstance.commit, "同意", Constance.State_Process);
							}

							// 流程中
							program.setState(Constance.State_Process);
							program.setMemo("会签审核(职级、到岗时间变动)");
						}
					}
				}

				mapper.updateRecruitprogram(program);

				// 置还有多少招聘人数可以分配
				Integer num = recruitprogram.getPostnum() - program.getPostnum();
				recruitprogram.setPostnum(num);
				if (num <= 0) {
					recruitprogram.setState(Constance.VALID_YES);
				}
				mapper.updateRecruitprogram(recruitprogram);
			}
		}
	}

	/**
	 * 得到最大code
	 * 
	 * @param id
	 * @return
	 */
	public String getMaxRecruitProgramCode() {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		// 當天最大code
		String maxcode = mapper.getMaxRecruitProgramCode();

		if (StringUtils.isNotEmpty(maxcode)) {
			return UUIDGenerator.format0000_ID(Constance.recruitprogram_prefix, maxcode, 1);
		}
		return UUIDGenerator.format0000_ID(Constance.recruitprogram_prefix, maxcode, 1);
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public RecruitProgram getRecruitprogramById(String id, String taskid) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		BpmnDao bpmnDao = sqlSession.getMapper(BpmnDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		RecruitProgram model = mapper.getRecruitprogramById(id);
		if (model != null) {
			model.convertOneCodeToName(companyDao, departmentDao, postDao, quotaDao, optionDao);

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
		}
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delRecruitprogramById(String ids) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		BpmnDao bpmnDao = sqlSession.getMapper(BpmnDao.class);

		String[] obj = ids.split(",");
		for (String id : obj) {
			RecruitProgram model = mapper.getRecruitprogramById(id);
			if (model != null) {
				// 流程信息
				String processinstanceid = model.getProcessinstanceid();
				if (StringUtils.isNotEmpty(processinstanceid)) {
					// 先检查流程是否存在
					ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(processinstanceid).singleResult();
					if (pi != null)
						runtimeService.deleteProcessInstance(pi.getId(), null);
					bpmnDao.delAuditHistoryByProcessInstanceId(processinstanceid);
				}

				// mapper.delRecruitprogramauditByRpId(id);
				mapper.delRecruitProgramOperateByRecruitProgramGuid(id);
				mapper.delRecruitprogramById(id);
			}
		}
	}

	/**
	 * 通过id修改state状态
	 * 
	 * @param ids
	 * @param state
	 */
	@Transactional
	public void updateRecruitprogramStateById(String ids, Integer state) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.updateRecruitprogramStateById(id, state);
		}
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
			System.out.println("=========commitNumDb================="+commitNumDb);
			
			
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

	/**
	 * 流程招聘计划结束
	 * 
	 * @param variables
	 */
	@Transactional
	public void endTask(String executionid, String processinstanceid, int state) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processinstanceid).active().singleResult();
		if (processInstance != null) {
			RecruitProgram model = mapper.getRecruitprogramByProcessinstanceId(processInstance.getBusinessKey());

			if (model != null) {
				model.setState(state);
				mapper.updateRecruitprogram(model);
			}
		}

	}

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	public void searchRecruitProgramOperate(GridServerHandler grid) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();

		// 统计
		Integer count = mapper.countRecruitProgramOperate(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<RecruitProgramOperate> list = mapper.searchRecruitProgramOperate(grid);

		for (RecruitProgramOperate model : list) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}

		grid.setData(data);
	}

	/**
	 * 取消细分操作
	 * 
	 * @param id
	 * @param interfacecode
	 */
	public void cancelRecruitprogramByIdAndInterfacecode(String id, String interfacecode) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		UserContext uc = ContextFacade.getUserContext();
		RecruitProgram model = mapper.getRecruitprogramById(id);
		if (model != null) {
			RecruitProgram recruitprogram = mapper.getRecruitprogramById(interfacecode);
			if (recruitprogram != null) {
				if (recruitprogram.getState().equals(Constance.VALID_YES)) {
					recruitprogram.setState(Constance.STATE_TODO);
					recruitprogram.setPostnum(recruitprogram.getPostnum() + model.getPostnum());
				}
				mapper.updateRecruitprogram(recruitprogram);

				// 计划操作表
				RecruitProgramOperate ope = new RecruitProgramOperate();
				ope.setRecruitprogramoperateguid(UUIDGenerator.randomUUID());
				ope.setRecruitprogramguid(recruitprogram.getRecruitprogramguid());
				ope.setOperatestate(Constance.OperateState_Add);
				ope.setModiuser(uc.getUsername());
				ope.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				ope.setOperatenum(recruitprogram.getPostnum());
				mapper.insertRecruitProgramOperate(ope);
			}

			mapper.delRecruitProgramOperateByRecruitProgramGuid(id);
			mapper.delRecruitprogramById(id);
		}

	}
	
	
	
	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public List<RecruitProgram> getRecruitprogramByPostId(String id,String quotaid) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		
		List<RecruitProgram> list = mapper.getRecruitprogramByQuotaidAndPostid(quotaid, id);
		if(!list.isEmpty()&&list!=null){
			return list;
		}
		return null;
	}
}
