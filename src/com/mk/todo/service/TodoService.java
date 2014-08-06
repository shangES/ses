package com.mk.todo.service;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.audition.dao.AuditionDao;
import com.mk.company.dao.CompanyDao;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.framework.chart.ChartData;
import com.mk.framework.chart.ChartModel;
import com.mk.framework.chart.ChartSearch;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.ProcessConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.util.DateUtil;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.person.dao.PersonDao;
import com.mk.person.entity.Person;
import com.mk.quota.dao.QuotaDao;
import com.mk.recruitment.dao.RecruitmentDao;
import com.mk.recruitment.dao.WebUserDao;
import com.mk.recruitprogram.dao.RecruitprogramDao;
import com.mk.recruitprogram.entity.RecruitProgram;
import com.mk.recruitprogram.entity.RecruitProgramAudit;
import com.mk.resume.dao.ResumeDao;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.Role;
import com.mk.thirdpartner.dao.ThirdPartnerDao;
import com.mk.todo.dao.TodoDao;
import com.mk.todo.entity.DeliverData;
import com.mk.todo.entity.MsgData;
import com.mk.todo.entity.TodoPam;

@Service
public class TodoService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;

	/**
	 * 将要过生日的员工
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public List<Employee> getEmployeeBirthdayTodo(TodoPam pam) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);

		// 權限組織
		Constance.initAdminPam(pam.getParameters());
		// 搜索
		return mapper.getEmployeeBirthdayTodo(pam);
	}

	/**
	 * 异动待生效的员工
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public List<Employee> getEmployeePosationTodo(TodoPam pam) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);

		// 權限組織
		Constance.initAdminPam(pam.getParameters());
		// 搜索
		return mapper.getEmployeePosationTodo(pam);
	}

	/**
	 * 合同将要到期的员工
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public List<Employee> getEmployeeContractTodo(TodoPam pam) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);

		// 權限組織
		Constance.initAdminPam(pam.getParameters());
		// 搜索
		return mapper.getEmployeeContractTodo(pam);
	}

	/**
	 * 待转正的员工
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public List<Employee> getEmployeeZhuZhengTodo(TodoPam pam) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);

		// 權限組織
		Constance.initAdminPam(pam.getParameters());
		// 搜索
		return mapper.getEmployeeZhuZhengTodo(pam);
	}

	/**
	 * 招聘计划审批
	 * 
	 * @param pam
	 * @return
	 */
	public List<RecruitProgram> getRecruitprogramTodo(String userguid) {

		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		// 根据当前人待办的任务
		try {
			List<Task> list = taskService.createTaskQuery().processDefinitionKey(ProcessConstance.PD_RecruitProgram).taskAssignee(userguid).active().orderByTaskCreateTime().desc().list();

			List<RecruitProgram> data = new ArrayList<RecruitProgram>();
			if (list != null && !list.isEmpty()) {
				for (Task task : list) {
					String processInstanceId = task.getProcessInstanceId();
					ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
					String businessKey = processInstance.getBusinessKey();

					// 取实体
					RecruitProgram model = mapper.getRecruitprogramByProcessinstanceId(businessKey);
					if (model != null) {
						model.convertOneCodeToName(companyDao, departmentDao, postDao, quotaDao, optionDao);
						model.setTaskid(task.getId());
						model.setTaskcreatetime(DateUtil.formatDateMDHHmm(task.getCreateTime()));
						data.add(model);
					}
				}
			}
			return data;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 待安排的面试
	 * 
	 * @param pam
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<MyCandidates> getInterviewTodo(TodoPam pam) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		// 權限組織
		Constance.initAdminPam(pam.getParameters());

		// 搜索
		List<MyCandidates> list = mapper.getInterviewTodo(pam);

		for (MyCandidates model : list) {
			model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, myCandidatesDao, systemDao, personDao,
					recruitprogramDao, quotaDao, auditionDao);
			model.setTimeformat(DateUtil.formatDateMDHHmm(model.getHoldtime()));
		}
		return list;
	}

	/**
	 * 待安排的体检
	 * 
	 * @param pam
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<MyCandidates> getExaminationTodo(TodoPam pam) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		// 權限組織
		Constance.initAdminPam(pam.getParameters());

		// 搜索
		List<MyCandidates> list = mapper.getExaminationTodo(pam);

		for (MyCandidates model : list) {
			model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, myCandidatesDao, systemDao, personDao,
					recruitprogramDao, quotaDao, auditionDao);
			model.setTimeformat(DateUtil.formatDateMDHHmm(model.getModitimestamp()));
		}
		return list;
	}

	/**
	 * 待确定入职的员工
	 * 
	 * @param pam
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<MyCandidates> getEntryOkTodo(TodoPam pam) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		// 權限組織
		Constance.initAdminPam(pam.getParameters());

		// 搜索
		List<MyCandidates> list = mapper.getEntryOkTodo(pam);

		for (MyCandidates model : list) {
			model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, myCandidatesDao, systemDao, personDao,
					recruitprogramDao, quotaDao, auditionDao);
			model.setTimeformat(DateUtil.formatDateMDHHmm(model.getModitimestamp()));
		}
		return list;
	}

	/**
	 * 待入职的招聘员工
	 * 
	 * @param pam
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Person> getEntryTodo(TodoPam pam) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao deptDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao comDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);

		// 權限組織
		Constance.initAdminPam(pam.getParameters());

		// 搜索
		List<Person> list = mapper.getEntryTodo(pam);
		for (Person model : list) {
			model.convertOneCodeToName(optionDao, comDao, deptDao, postDao, quotaDao, systemDao, employeeDao);
			model.setTimeformat(DateUtil.formatDateMDHHmm(model.getJoindate()));
		}

		return list;
	}

	/**
	 * 面试人员列表
	 * 
	 * @param pam
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<MyCandidates> getAuditionTodo(TodoPam pam) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		// 權限組織
		Constance.initAdminPam(pam.getParameters());

		// 搜索
		List<MyCandidates> list = mapper.getAuditionTodo(pam);

		for (MyCandidates model : list) {
			model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, myCandidatesDao, systemDao, personDao,
					recruitprogramDao, quotaDao, auditionDao);
			model.setTimeformat(DateUtil.formatDateMDHHmm(model.getAuditiondate()));
		}
		return list;
	}

	/**
	 * 待认定的简历
	 * 
	 * @param pam
	 * @return
	 * @throws Exception
	 */
	public List<MyCandidates> getAffirmMyCandidatesTodo(TodoPam pam) throws Exception {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		// 權限組織
		Constance.initAdminPam(pam.getParameters());

		List<MyCandidates> list = mapper.getAffirmMyCandidatesTodo(pam);
		for (MyCandidates model : list) {
			model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, myCandidatesDao, systemDao, personDao,
					recruitprogramDao, quotaDao, auditionDao);

		}
		return list;
	}

	/**
	 * 面試結果反饋
	 * 
	 * @param pam
	 * @return
	 */
	public List<MyCandidates> getAffirmAuditionResultsTodo(TodoPam pam) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		// 權限組織
		Constance.initAdminPam(pam.getParameters());

		List<MyCandidates> list = mapper.getAffirmAuditionResultsTodo(pam);
		for (MyCandidates model : list) {
			model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, myCandidatesDao, systemDao, personDao,
					recruitprogramDao, quotaDao, auditionDao);
			if (model.getAuditiondate() != null) {
				model.setTimeformat(DateUtil.formatDateMDHHmm(model.getAuditiondate()));
			}
		}
		return list;
	}

	/**
	 * 待发布的面试结果
	 * 
	 * 
	 * @param pam
	 * @return
	 */
	private List<MyCandidates> getReleasesTodo(TodoPam pam) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		// 權限組織
		Constance.initAdminPam(pam.getParameters());

		List<MyCandidates> list = mapper.getReleasesTodo(pam);
		for (MyCandidates model : list) {
			model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, myCandidatesDao, systemDao, personDao,
					recruitprogramDao, quotaDao, auditionDao);
			if (model.getAuditiondate() != null) {
				model.setTimeformat(DateUtil.formatDateMDHHmm(model.getModitimestamp()));
			}
		}
		return list;
	}

	/**
	 * OA招聘计划结果
	 * 
	 * 
	 * @param pam
	 * @return
	 */
	private List<RecruitProgramAudit> getRecruitProgramAuditTodo(TodoPam pam) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);

		// 權限組織
		Constance.initAdminPam(pam.getParameters());

		List<RecruitProgramAudit> list = mapper.getRecruitProgramAuditTodo(pam);
		for (RecruitProgramAudit model : list) {
			model.convertOneCodeToName(companyDao, departmentDao, postDao, quotaDao, optionDao);
			if (model.getStartdate() != null) {
				model.setTimeformat(DateUtil.formatDateMDHHmm(model.getStartdate()));
			}
		}
		return list;
	}

	/**
	 * 得到招聘计划oa
	 * 
	 * @param pam
	 * @return
	 */
	private List<RecruitProgram> getRecruitProgram(TodoPam pam) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);

		try {
			// 權限組織
			Constance.initAdminPam(pam.getParameters());

			List<RecruitProgram> list = mapper.getRecruitProgram(pam);
			for (RecruitProgram model : list) {
				model.convertOneCodeToName(companyDao, departmentDao, postDao, quotaDao, optionDao);
				if (model.getApplydate() != null) {
					model.setTimeformat(DateUtil.formatDateMDHHmm(model.getApplydate()));
				}
			}
			return list;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 消息框的数据
	 * 
	 * @param pam
	 * @return
	 * @throws Exception
	 */
	public MsgData getMsgData(TodoPam pam) throws Exception {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		UserContext uc = ContextFacade.getUserContext();
		String userguid = uc.getUserId();

		if (!uc.isAdmin()) {
			List<Role> roles = mapper.getRoleByUserId(userguid);
			boolean state = false;
			// String rolename=null;
			//加了段判空的
			if(roles!=null&&!roles.isEmpty()){
				for (Role role : roles) {
					// 检查是否招聘专员
					if (role.getRolename().equals(Constance.RoleName)) {
						state = true;
						// rolename=role.getRolename();
						break;
					}
				}
			}
			
			if (!state)
				return null;
		}

		MsgData data = new MsgData();
		StringBuffer msg = new StringBuffer();
		long time = 0;
		// data.setRole(rolename.equals(Constance.RoleName));

		// if(rolename.equals(Constance.RoleName)){

		// 招聘计划审批
		List<RecruitProgram> recruitprograms = getRecruitprogramTodo(userguid);
		if (recruitprograms != null) {
			data.setRecruitprograms(recruitprograms.size());
			if (!recruitprograms.isEmpty()) {
				RecruitProgram rp = recruitprograms.get(0);
				time = rp.getModitimestamp().getTime();

				msg = new StringBuffer();

				// 最新消息
				msg.append(rp.getDeptname());
				msg.append("，");
				msg.append(rp.getRecruitprogramcode());
				msg.append("，招聘计划待审批！");
				data.setHotnews(Constance.NewsNum1);
			}

		}

		// 待安排的面试
		List<MyCandidates> interviews = getInterviewTodo(pam);
		data.setInterviews(interviews.size());
		if (!interviews.isEmpty()) {
			MyCandidates mc = interviews.get(0);
			long tmpTime = mc.getModitimestamp().getTime();

			// 比较
			if (tmpTime > time) {
				time = tmpTime;

				msg = new StringBuffer();

				// 最新消息
				msg.append(mc.getRecommenddeptname());
				msg.append("，");
				msg.append(mc.getName());
				msg.append("，待安排的面试！");
				data.setHotnews(Constance.NewsNum2);
			}
		}

		// 待发布的面试结果
		List<MyCandidates> releases = getReleasesTodo(pam);
		data.setReleases(releases.size());
		if (!releases.isEmpty()) {
			MyCandidates mc = releases.get(0);
			long tmpTime = mc.getModitimestamp().getTime();

			// 比较
			if (tmpTime > time) {
				time = tmpTime;

				msg = new StringBuffer();

				// 最新消息
				msg.append(mc.getRecommenddeptname());
				msg.append("，");
				msg.append(mc.getName());
				msg.append("，面试结果待反馈！");
				data.setHotnews(Constance.NewsNum3);
			}
		}

		// 待安排的体检
		List<MyCandidates> examinations = getExaminationTodo(pam);
		data.setExaminations(examinations.size());
		if (!examinations.isEmpty()) {
			MyCandidates mc = examinations.get(0);
			long tmpTime = mc.getModitimestamp().getTime();

			// 比较
			if (tmpTime > time) {
				time = tmpTime;

				msg = new StringBuffer();

				// 最新消息
				msg.append(mc.getRecommenddeptname());
				msg.append("，");
				msg.append(mc.getName());
				msg.append("，待安排的体检！");
				data.setHotnews(Constance.NewsNum4);
			}

		}

		// 待入职的应聘者
		List<MyCandidates> entrys = getEntryOkTodo(pam);
		data.setEntryoktodos(entrys.size());
		if (!entrys.isEmpty()) {
			MyCandidates mc = entrys.get(0);
			long tmpTime = mc.getModitimestamp().getTime();

			// 比较
			if (tmpTime > time) {
				time = tmpTime;

				msg = new StringBuffer();

				// 最新消息
				msg.append(mc.getRecommenddeptname());
				msg.append("，");
				msg.append(mc.getName());
				msg.append("，待确定入职！");
				data.setHotnews(Constance.NewsNum5);
			}
		}

		// OA招聘计划信息
		List<RecruitProgram> oarecruitprograms = getRecruitProgram(pam);
		if (oarecruitprograms != null) {
			data.setAudit(oarecruitprograms.size());
			if (!oarecruitprograms.isEmpty()) {
				RecruitProgram mc = oarecruitprograms.get(0);
				long tmpTime = mc.getApplydate().getTime();

				// 比较
				if (tmpTime > time) {
					time = tmpTime;

					msg = new StringBuffer();

					// 最新消息
					msg.append(mc.getDeptname());
					msg.append("，");
					msg.append(mc.getPostname());
					msg.append("，OA招聘信息！");
					data.setHotnews(Constance.NewsNum6);
				}
			}

		}

		// 计算总计
		data.setNews(data.getEntryoktodos() + data.getInterviews() + data.getExaminations() + data.getRecruitprograms() + data.getReleases() + data.getAudit());
		// }

		/*
		 * else if(rolename.equals(Constance.RoleName2)){ //待认定的简历
		 * List<MyCandidates> affirms = getAffirmMyCandidatesTodo(pam);
		 * data.setAffirms(affirms.size()); if (!affirms.isEmpty()) {
		 * MyCandidates mc = affirms.get(0); long tmpTime =
		 * mc.getModitimestamp().getTime();
		 * 
		 * // 比较 if (tmpTime > time) { time = tmpTime;
		 * 
		 * // 最新消息 msg.append(mc.getRecommenddeptname()); msg.append("，");
		 * msg.append(mc.getName()); msg.append("，待认定的简历！"); } }
		 * 
		 * // 面试结果待反馈 List<MyCandidates> affirmauditionresults =
		 * getAffirmAuditionResultsTodo(pam);
		 * data.setAffirmauditionresults(affirmauditionresults.size()); if
		 * (!affirmauditionresults.isEmpty()) { MyCandidates mc =
		 * affirmauditionresults.get(0); long tmpTime =
		 * mc.getModitimestamp().getTime();
		 * 
		 * // 比较 if (tmpTime > time) { time = tmpTime;
		 * 
		 * // 最新消息 msg.append(mc.getDeptname()); msg.append("，");
		 * msg.append(mc.getName()); msg.append("，面试结果待反馈！"); } }
		 * 
		 * //计算总计
		 * data.setNews(data.getAffirmauditionresults()+data.getAffirms()); }
		 */
		data.setNewsmsg(msg.toString());
		return data;
	}

	/**
	 * 我的应聘统计图
	 * 
	 * @param search
	 * @return
	 */
	public List<ChartData> loadCountMyCandidatesChart(ChartSearch search) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		List<ChartModel> data = mapper.countMsgByState();
		// 返回
		List<ChartData> list = new ArrayList<ChartData>();
		ChartData cd = new ChartData();
		cd.setName("A1");
		cd.setColor(search.getColor());
		cd.setList(data);

		list.add(cd);
		return list;
	}

	/**
	 * 来源渠道统计图
	 * 
	 * 
	 * @param search
	 * @return
	 */
	public List<ChartData> loadCountMyCandidatesTypeChart(ChartSearch search) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		List<ChartModel> data = mapper.loadCountMyCandidatesTypeChart();
		// 返回
		List<ChartData> list = new ArrayList<ChartData>();
		ChartData cd = new ChartData();
		cd.setName("A2");
		cd.setColor(search.getColor());
		cd.setList(data);

		list.add(cd);
		return list;
	}

	/**
	 * 编制情况统计图
	 * 
	 * 
	 * @param search
	 * @return
	 */
	public List<ChartModel> loadmyBZQKChartBody(ChartSearch search) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		List<ChartModel> data = mapper.loadmyBZQKChartBody();
		return data;
	}

	/**
	 * 占编情况统计图
	 * 
	 * 
	 * @param search
	 * @return
	 */
	public List<ChartModel> loadmyZBQKChartBody(ChartSearch search) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		List<ChartModel> data = mapper.loadmyZBQKChartBody();
		return data;
	}

	/**
	 * 投递情况
	 * 
	 * 
	 * @param pam
	 * @return
	 * @throws ParseException
	 */
	public DeliverData getDeliverTodo(TodoPam pam) throws ParseException {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		DeliverData data = new DeliverData();
		// 求投递数
		Integer totals = mapper.countAllMyCandidates(pam);
		data.setTotalDeliver(totals);

		// 求当日投递
		Integer todays = mapper.countMyCandidatesByToday(pam);
		data.setTodayDeliver(todays);

		// 求平均投递
		Date mintime = mapper.getMinTime();
		Date date = new Date(System.currentTimeMillis());
		if (mintime != null) {
			int avage = DateUtil.daysBetween(mintime, date);
			data.setAverageDeliver(totals / avage);
		} else {
			data.setAverageDeliver(Constance.VALID_NO);
		}

		// 求当日推荐率
		// 创建一个数值格式化对象
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(2);
		Integer recommends = mapper.countMyCandidatesByRecommend();
		if (todays == 0) {
			data.setRecommendnum(Constance.baifenbi);
		} else {
			String result = numberFormat.format((float) recommends / (float) todays * 100);
			data.setRecommendnum(result);
		}

		// 求平均推荐率
		// 求总推荐数
		Integer sum = mapper.countRecommends();
		if (totals.equals(Constance.VALID_NO)) {
			data.setSumrecommends(Constance.baifenbi);
		} else {
			String result = numberFormat.format((float) sum / (float) totals * 100);
			data.setSumrecommends(result);
		}

		return data;

	}

	/**
	 * 招聘计划统计图
	 * 
	 * 
	 * @param search
	 * @return
	 */
	public List<ChartModel> loadmyRecuritprogramChartBody(ChartSearch search) {
		TodoDao mapper = sqlSession.getMapper(TodoDao.class);
		List<ChartModel> data = mapper.loadmyRecuritprogramChartBody();
		/*
		 * System.out.println("=========="+data.toString()); // 返回
		 * List<ChartModel> list = new ArrayList<ChartModel>(); ChartData cd =
		 * new ChartData(); cd.setName("A2"); cd.setColor(search.getColor());
		 * cd.setList(data);
		 * 
		 * list.add(cd);
		 */
		return data;
	}

}
