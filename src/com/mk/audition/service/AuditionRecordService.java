package com.mk.audition.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.audition.dao.AuditionDao;
import com.mk.audition.entity.AuditionAddress;
import com.mk.audition.entity.AuditionRecord;
import com.mk.audition.entity.AuditionResult;
import com.mk.audition.entity.Interviewer;
import com.mk.company.dao.CompanyDao;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.employee.dao.EmployeeDao;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.DateUtil;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.mail.MailSendService;
import com.mk.framework.mail.SendMessageService;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.mycandidates.entity.Bussiness;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.mycandidates.entity.MyCandidatesHistory;
import com.mk.mycandidates.entity.Recommend;
import com.mk.mycandidates.service.MyCandidatesService;
import com.mk.quota.dao.QuotaDao;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.Resume;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.Role;
import com.mk.system.entity.UserManageRange;
import com.mk.system.entity.UserRole;

@Service
public class AuditionRecordService {
	@Autowired
	private MyCandidatesService myCandidatesService;
	@Autowired
	private MailSendService mailSendService;
	@Autowired
	private SendMessageService sendMessageService;

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 搜索
	 * 
	 * @return
	 */
	public void searchAuditionRecord(GridServerHandler grid) {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countAuditionRecord(grid);
		PageUtils.setTotalRows(grid, count);

		List<AuditionRecord> list = mapper.searchAuditionRecord(grid);

		for (AuditionRecord model : list) {
			model.convertOneCodeToName(systemDao, employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}

	/**
	 * 保存修改
	 * 
	 * @param model
	 * @throws Exception
	 */
	@Transactional
	public void saveOrUpateAuditionRecord(AuditionRecord model) throws Exception {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		UserContext uc = ContextFacade.getUserContext();

		// 插入
		if (StringUtils.isEmpty(model.getAuditionrecordguid())) {
			String ids = model.getArray();
			if (StringUtils.isEmpty(ids)) {
				return;
			}
		
			Role role = systemDao.getRoleByRoleName(Constance.DEPARTMENTBRANCH);
		
			String[] obj = ids.split(",");
			for (String id : obj) {
				MyCandidates mycandidates = myCandidatesDao.getMyCandidatesById(id);
				if (mycandidates != null) {
					// 删除安排的面试中有效(阻止并发的情况)
					AuditionRecord audition = mapper.getAuditionRecordByMycandidatesguidAndState(id);
					if (audition != null) {
						mapper.delInterviewerByAuditionRecordId(audition.getAuditionrecordguid());
					}
					mapper.delAuditionRecordByCandidatesguidAndState(id, Constance.VALID_YES);

					// 面试记录
					model.setAuditionrecordguid(UUIDGenerator.randomUUID());
					model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					model.setMycandidatesguid(id);
					model.setModiuser(ContextFacade.getUserContext().getUsername());
					mapper.insertAuditionRecord(model);
					
					
					//部门面试官赋权
					if(role!=null){
						UserRole userRole = systemDao.getUserRoleByRoleAndUsergGuid(role.getRoleid(), model.getMaininterviewerguid());
						if(userRole == null){
							userRole = new UserRole();
							userRole.setUserguid(model.getMaininterviewerguid());
							userRole.setRoleid(role.getRoleid());
							systemDao.insertUserRole(userRole);
						}
					}
					
					//判断推荐者是否有华数公司的权限 无增加公司权限
					UserManageRange userManagerange = systemDao.getUserManageRangeByUserIdAndCompanyId(Constance.COMPANYID, model.getMaininterviewerguid());
					if(userManagerange==null){
						UserManageRange manageRange=new UserManageRange();
						manageRange.setCompanyid(Constance.COMPANYID);
						manageRange.setUserguid(model.getMaininterviewerguid());
						manageRange.setIsdefault(Constance.VALID_YES);
						systemDao.insertUserManageRange(manageRange);
					}
					

					// 保存面试官(抄送)
					if (StringUtils.isNotEmpty(model.getUserguid())) {
						String userguid = model.getUserguid();
						String[] userguids = userguid.split(",");
						for (String userid : userguids) {
							Interviewer interviewer = new Interviewer();
							interviewer.setAuditionrecordguid(model.getAuditionrecordguid());
							interviewer.setUserguid(userid);
							interviewer.setInterviewerguid(UUIDGenerator.randomUUID());
							mapper.insertInterviewer(interviewer);
							
							//部门认定赋权
//							if(StringUtils.isNotEmpty(role.getRoleid())){
//								UserRole userRole = systemDao.getUserRoleByRoleAndUsergGuid(role.getRoleid(), userid);
//								if(userRole == null){
//									userRole = new UserRole();
//									userRole.setUserguid(userid);
//									userRole.setRoleid(role.getRoleid());
//									systemDao.insertUserRole(userRole);
//								}
//							}
						}
					}

					// 面试地点的保存
					AuditionAddress address = mapper.getAuditionAddressByName(model.getAuditionaddress());
					if (address == null) {
						AuditionAddress auditionAddress = new AuditionAddress();
						auditionAddress.setAuditionaddressguid(UUIDGenerator.randomUUID());
						auditionAddress.setAuditionaddress(model.getAuditionaddress());
						auditionAddress.setState(Constance.VALID_YES);
						auditionAddress.setModiuser(uc.getUsername());
						auditionAddress.setModitimestamp(new Timestamp(System.currentTimeMillis()));
						mapper.insertAuditionAddress(auditionAddress);
					}

					// 保存操作历史
					MyCandidatesHistory myCandidatesHistory = new MyCandidatesHistory();
					myCandidatesHistory.setMycandidateshistoryguid(UUIDGenerator.randomUUID());
					myCandidatesHistory.setModiuser(uc.getUsername());
					myCandidatesHistory.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					myCandidatesHistory.setMycandidatesguid(id);
					OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CANDIDATESSTATE, Constance.CandidatesState_Five);
					if (opt != null) {
						myCandidatesHistory.setModimemo(opt.getName());
					}
					myCandidatesDao.insertMyCandidatesHistory(myCandidatesHistory);

					// 回寫我的應聘
					mycandidates.setCandidatesstate(Constance.CandidatesState_Five);
					mycandidates.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					myCandidatesDao.updateMyCandidates(mycandidates);

					// 发邮件(应聘者、面试官)
					List<Recommend> recommendlist = myCandidatesDao.getRecommendByCandidatesGuidAndState(id);
					if (!recommendlist.isEmpty()) {
						Resume resume = resumeDao.getResumeById(mycandidates.getWebuserguid());
						Recommend recommend = recommendlist.get(0);
						recommend.convertOneCodeToName(departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, optionDao);
						model.convertOneCodeToName(systemDao, employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
						
						// 发送应聘者
						if(model.getIsemail()!=null){
							if(resume!=null&&recommend!=null){
								mailSendService.sendMianShiMailTo(mycandidates, resume, recommend, model,uc.getMobile(),uc.getUsername());
							 	mailSendService.sendMianShiMailToInterviewer(mycandidates,resume, recommend, model,uc.getMobile(),uc.getUsername());
							}
						}
						
						if(model.getIsmsg()!=null){
							//发送应聘者
							if(resume!=null&&recommend!=null){
								sendMessageService.sendMianShiMsgTo(mycandidates, resume, recommend, model,uc.getMobile(),uc.getUsername());
							}
						}else{
							// 发送面试官
							Bussiness bussiness=new Bussiness();
							bussiness.setBussinessguid(UUIDGenerator.randomUUID());
							bussiness.setOperateguid(model.getAuditionrecordguid());
							bussiness.setTablename(Constance.auditionRecordTable);
							myCandidatesDao.insertBussiness(bussiness);
						}
					}
				}
			}
		} else {
			mapper.updateAuditionRecord(model);
		}
	}

	/**
	 * 得到
	 * 
	 * @param auditionrecordguid
	 * @return
	 */
	public AuditionRecord getAuditionRecordByAuditionRecordId(String auditionrecordguid) {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionRecord model = mapper.getAuditionRecordByAuditionRecordId(auditionrecordguid);
		if (model != null) {
			model.convertOneCodeToName(systemDao, employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
		}
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param auditionrecordguid
	 */
	@Transactional
	public void delAuditionRecordByAuditionRecordId(String ids) {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delInterviewerByAuditionRecordId(id);
			mapper.delAuditionResultByAuditionRecordId(id);
			mapper.delAuditionRecordByAuditionRecordId(id);
		}
	}

	/**
	 * 审核发布、取消发布
	 * 
	 * @param ids
	 */
	@Transactional
	public void auditAuditionRecordById(String ids, Integer state) {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			AuditionResult model = mapper.getAuditionResultByAuditionRecordId(id);
			if (model != null) {
				model.setIsrelease(state);
				mapper.updateAuditionResult(model);
			}
		}

	}

	/**
	 * 校验是否可以取消此次面试
	 * 
	 * 
	 * @param id
	 * @return
	 */
	public AuditionRecord checkAuditionRecordIsResult(String ids) {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		if (StringUtils.isNotEmpty(ids)) {
			List<AuditionRecord> list = mapper.getAuditionRecordNoResultByMyCandidatesId(ids);
			if (!list.isEmpty()) {
				AuditionRecord model = list.get(0);
				return model;
			}
		}
		return null;
	}

	/**
	 * 得到
	 * 
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	public List<AuditionRecord> getAuditionRecordAndResultByMycandidatesguid(String mycandidatesguid) {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		List<AuditionRecord> list = mapper.getAuditionRecordAndResultByMycandidatesguid(mycandidatesguid);
		if (!list.isEmpty()) {
			for (AuditionRecord record : list) {
				record.convertOneCodeToName(systemDao, employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
			}
		}
		return list;
	}

	
	
	/**
	 * 得到
	 * 
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	public List<AuditionRecord> getAuditionRecordAndResultByRecommendGuid(String recommendguid) {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		List<AuditionRecord> list = mapper.getAuditionRecordAndResultByRecommendGuid(recommendguid);
		if (!list.isEmpty()) {
			for (AuditionRecord record : list) {
				record.convertOneCodeToName(systemDao, employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
			}
		}
		return list;
	}
	
	
	
	/**
	 * 发短信给面试官
	 * 
	 * 
	 */
	@Transactional
	public void sendMessageToInterviewer() throws Exception{
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		
		// 取当前1小时 前的没发短信的面试记录信息
		List<AuditionRecord> list = auditionDao.getAuditionRecordByToInterviewer();
		
		Map<String, List<AuditionRecord>> userMap = new HashMap<String, List<AuditionRecord>>();
		if (!list.isEmpty()) {
			for(AuditionRecord model:list){
				List<AuditionRecord> tmplist = userMap.get(model.getMaininterviewerguid());
				if (tmplist == null) {
					tmplist = new ArrayList<AuditionRecord>();
					tmplist.add(model);
					userMap.put(model.getMaininterviewerguid(), tmplist);
				} else {
					tmplist.add(model);
				}
			}
			
			//发送短信
			for(String key:userMap.keySet()){
				List<AuditionRecord> tmplist=userMap.get(key);
				String deptUserMobile=null;
				String deptUsername=null;
				StringBuffer msg = new StringBuffer();
				//msg.append("请于");
				for(AuditionRecord auditionRecord:tmplist){
					auditionRecord.convertOneCodeToName(systemDao, employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
					MyCandidates model = mapper.getMyCandidatesById(auditionRecord.getMycandidatesguid());
					Resume resume = resumeDao.getResumeById(model.getWebuserguid());
					if (resume != null) {
//						 msg.append(DateUtil.formatDateYMDHHmmChina(auditionRecord.getAuditiondate()));
//						 msg.append("在");
//						 msg.append(auditionRecord.getAuditionaddress());
//						 msg.append("对");
						 msg.append(resume.getName());
//						 msg.append("进行面试");
						 msg.append("，");
					 }
					
					//回写短信发送
					Bussiness bussiness=new Bussiness();
					bussiness.setBussinessguid(UUIDGenerator.randomUUID());
					bussiness.setOperateguid(auditionRecord.getAuditionrecordguid());
					bussiness.setTablename(Constance.auditionRecordTable);
					mapper.insertBussiness(bussiness);
					
					deptUserMobile=auditionRecord.getEmployeemobile();
					deptUsername=auditionRecord.getMaininterviewerguidname();
				}
				
				if (msg.length() > 0) {
					 msg.setLength(msg.length() - 1);
					 msg.append("(");
					 msg.append(tmplist.size());
					 msg.append("人)");
				}
				
				//发送短信
				sendMessageService.sendMianShiMsgToInterviewer(msg.toString(),deptUsername,deptUserMobile);
			}
		}
	}
	
	/**
	 * 得到
	 * 
	 * @param recommendguid
	 * @return
	 */
	public List<AuditionRecord> getAuditionRecordAndResultByRecommendguid(String recommendguid){
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		List<AuditionRecord> list = auditionDao.getAuditionRecordAndResultByRecommendguid(recommendguid.replaceFirst(",", ""));
		if (!list.isEmpty()) {
			for (AuditionRecord record : list) {
				record.convertOneCodeToName(systemDao, employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
			}
		}
		return list;
	}
}
