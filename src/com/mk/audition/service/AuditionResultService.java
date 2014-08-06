package com.mk.audition.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.audition.dao.AuditionDao;
import com.mk.audition.entity.AuditionRecord;
import com.mk.audition.entity.AuditionResult;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.entity.Department;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Position;
import com.mk.employee.entity.Workexperience;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.mycandidates.entity.MyCandidatesHistory;
import com.mk.mycandidates.entity.Recommend;
import com.mk.recruitprogram.dao.RecruitprogramDao;
import com.mk.recruitprogram.entity.RecruitProgram;
import com.mk.recruitprogram.entity.RecruitProgramOperate;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.WorkExperience;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.OptionType;

@Service
public class AuditionResultService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 保存修改
	 * 
	 * @param model
	 * @throws Exception
	 */
	@Transactional
	public void saveOrUpateAuditionResult(AuditionResult model) throws Exception {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		// MyCandidatesDao myCandidatesDao =
		// sqlSession.getMapper(MyCandidatesDao.class);
		if (StringUtils.isNotEmpty(model.getAuditionrecordguid())) {
			AuditionResult result = mapper.getAuditionResultByAuditionRecordId(model.getAuditionrecordguid());
			if (result != null) {
				mapper.updateAuditionResult(model);
			} else {
				model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				mapper.insertAuditionResult(model);
			}
		}
	}

	/**
	 * 回写我的应聘
	 * 
	 * @throws Exception
	 * 
	 */
	@Transactional
	public void updateMyCandidatesByAuditionResult(RecruitprogramDao recruitprogramDao,DepartmentDao departmentDao,AuditionResult model, AuditionDao mapper, MyCandidates myCandidates, MyCandidatesDao myCandidatesDao, OptionDao optionDao, String username,
			EmployeeDao employeeDao,ResumeDao resumeDao) throws Exception {
		AuditionRecord record = mapper.getAuditionRecordByAuditionRecordId(model.getAuditionrecordguid());
		if (record != null) {
			Integer state = Constance.CandidatesState_Eight;

			// 结果（1:复试、2:建议录用、3:拟淘汰,4:未签到）
			// 状态(0:不匹配,1:正常,2:匹配推荐,3:未认定,4:认定面试,5:按排面试,6:复试,7:面试通过,8:面试未通过,9:待定,10:待体检,11:安排体检,12:体检通过,13:体检未通过,14:待入职,15:已经入职)
			if (model.getResulttype().equals(Constance.ResultType_One)) {
				state = Constance.CandidatesState_Six;
			} else if (model.getResulttype().equals(Constance.ResultType_Two)) {
				state = Constance.CandidatesState_Seven;
			} else if (model.getResulttype().equals(Constance.ResultType_Four)) {
				state = Constance.CandidatesState_Nine;
			}

			// 保存操作历史
			MyCandidatesHistory myCandidatesHistory = new MyCandidatesHistory();
			myCandidatesHistory.setMycandidateshistoryguid(UUIDGenerator.randomUUID());
			myCandidatesHistory.setModiuser(username);
			myCandidatesHistory.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			myCandidatesHistory.setMycandidatesguid(myCandidates.getMycandidatesguid());
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CANDIDATESSTATE, state);
			if (opt != null) {
				myCandidatesHistory.setModimemo(opt.getName());
			}
			myCandidatesDao.insertMyCandidatesHistory(myCandidatesHistory);

			// 回写面试记录表
			if (state.equals(Constance.CandidatesState_Six) || state.equals(Constance.CandidatesState_Nine)||state.equals(Constance.CandidatesState_Eight)) {
				record.setState(Constance.VALID_NO);
				mapper.updateAuditionRecord(record);
			}

//			if(state.equals(Constance.CandidatesState_Seven)){
//				//面试通过后面试通过人数+1(锁定招聘计划)
//				List<Recommend> recommends = myCandidatesDao.getRecommendByCandidatesGuidAndState(myCandidates.getMycandidatesguid());
//				if(!recommends.isEmpty()&&recommends!=null){
//					Recommend recommend = recommends.get(0);
//					String deptid=recommend.getRecommenddeptid();
//					Department dept = departmentDao.getDepartmentByDepartmentId(deptid);
//					if(dept!=null){
//						if(StringUtils.isNotEmpty(dept.getPdeptid())){
//							deptid=dept.getPdeptid();
//						}
//					}
//					//通过部门去取招聘计划
//					List<RecruitProgram> recruitprograms = recruitprogramDao.getAllRecruitprogramByDeptid(deptid);
//					if(!recruitprograms.isEmpty()&&recruitprograms!=null){
//						RecruitProgram recruitprogram=recruitprograms.get(0);
//						
//						//操作信息(员工入职后都是锁定状态)
//						RecruitProgramOperate ope = new RecruitProgramOperate();
//						ope.setRecruitprogramoperateguid(UUIDGenerator.randomUUID());
//						ope.setRecruitprogramguid(recruitprogram.getRecruitprogramguid());
//						ope.setOperatestate(Constance.OperateState_Lock);
//						ope.setModiuser(username);
//						ope.setModitimestamp(new Timestamp(System.currentTimeMillis()));
//						ope.setOperatenum(Constance.VALID_YES);
//						recruitprogramDao.insertRecruitProgramOperate(ope);
//						
//						myCandidates.setRecruitprogramoperateguid(ope.getRecruitprogramoperateguid());
//					}
//				}
				
				// 判断是否是内部职位的面试通过
				if (myCandidates.getCandidatestype().equals(Constance.User6) && state.equals(Constance.CandidatesState_Seven)) {
					List<Position> oldpositions = employeeDao.getPositionByEmployeeIdAndIsstaff(myCandidates.getWebuserguid());
					if (!oldpositions.isEmpty()) {
						Position oldpo = oldpositions.get(0);

						// 如果本公司的时候职级、职务未输把原来的职级、职务赋上去
						List<Recommend> recommendlist = myCandidatesDao.getRecommendByCandidatesGuidAndState(myCandidates.getMycandidatesguid());
						if (!recommendlist.isEmpty()) {
							Recommend recomend = recommendlist.get(0);
							oldpo.setCompanyid(recomend.getRecommendcompanyid());
							oldpo.setDeptid(recomend.getRecommenddeptid());
							oldpo.setPostid(recomend.getRecommendpostguid());
							if (oldpo.getCompanyid().equals(recomend.getRecommendcompanyid())) {
								oldpo.setJobid(oldpo.getJobid());
								oldpo.setRankid(oldpo.getRankid());
							}
							oldpo.setState(Constance.VALID_YES);
							oldpo.setIsstaff(Constance.STATE_TODO);
							oldpo.setStartdate(new Date(System.currentTimeMillis()));
							oldpo.setModiuser(username);
							oldpo.setEmployeeid(myCandidates.getWebuserguid());
							oldpo.setPostionguid(UUIDGenerator.randomUUID());
							oldpo.setModitimestamp(new Timestamp(System.currentTimeMillis()));
							employeeDao.insertPosition(oldpo);
						}
						
						//回写员工的工作经历
						employeeDao.delWorkexperienceByEmployeeId(myCandidates.getWebuserguid());
						List<WorkExperience> worklist = resumeDao.getAllWorkExperienceByWebuserId(myCandidates.getWebuserguid());
						if(!worklist.isEmpty()){
							for(WorkExperience work:worklist){
								Workexperience tmpworkexperience = new Workexperience();
								tmpworkexperience.setWorkexperienceid(UUIDGenerator.randomUUID());
								tmpworkexperience.setEmployeeid(myCandidates.getWebuserguid());
								tmpworkexperience.setWorkunit(work.getWorkunit());
								tmpworkexperience.setStartdate(work.getStartdate());
								tmpworkexperience.setEnddate(work.getEnddate());
								tmpworkexperience.setJob(work.getJobdescription());
								tmpworkexperience.setDescription(work.getJobdescription());
								employeeDao.insertWorkexperience(tmpworkexperience);
							}
						}
						
						//回写应聘状态为已入职
						state=Constance.CandidatesState_Fifteen;

					}
				}
//			}
			

			
			// 回写我的应聘
			myCandidates.setCandidatesstate(state);
			myCandidates.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			myCandidatesDao.updateMyCandidates(myCandidates);
		}
	}

	/**
	 * 保存修改
	 * 
	 * @param model
	 * @throws Exception
	 */
	@Transactional
	public void saveResultByMyCandidatesGuid(AuditionResult model) throws Exception {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		DepartmentDao departmentDao=sqlSession.getMapper(DepartmentDao.class);
		RecruitprogramDao recruitprogramDao=sqlSession.getMapper(RecruitprogramDao.class);
		UserContext uc = ContextFacade.getUserContext();
		if (StringUtils.isNotEmpty(model.getMycandidatesguid())) {
			MyCandidates myCandidates = myCandidatesDao.getMyCandidatesById(model.getMycandidatesguid());
			AuditionRecord record = mapper.getAuditionRecordByMycandidatesguidAndState(model.getMycandidatesguid());

			// 先删后插
			mapper.delAuditionResultByAuditionRecordId(record.getAuditionrecordguid());

			if(StringUtils.isNotEmpty(model.getResultcontentname())){
				OptionList option=optionDao.getOptionListByTypeAndName(OptionConstance.RESULTCONTENT,model.getResultcontentname());
				if(option!=null){
					model.setResultcontent(option.getCode());
				}else{
					OptionType opttype=optionDao.getOptionTypeByCode(OptionConstance.RESULTCONTENT);
					if(opttype!=null){
						OptionList opt=new OptionList();
						opt.setOptionid(UUIDGenerator.randomUUID());
						opt.setOptiontypeguid(opttype.getOptiontypeguid());
						opt.setIsdefault(Constance.VALID_NO);
						opt.setReserved(Constance.VALID_NO);
						opt.setName(model.getResultcontentname());
						int num=optionDao.getMaxOptionListCodeByTypeId(opttype.getOptiontypeguid());
						opt.setCode(num+1);
						opt.setDorder(opt.getCode());
						optionDao.insertOptionList(opt);
						model.setResultcontent(opt.getCode());
					}
				}
			}
			
			
			// 保存
			model.setAuditionrecordguid(record.getAuditionrecordguid());
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			model.setModiuser(uc.getUsername());
			mapper.insertAuditionResult(model);

			// 回写我的应聘
			updateMyCandidatesByAuditionResult(recruitprogramDao,departmentDao,model, mapper, myCandidates, myCandidatesDao, optionDao, uc.getUsername(), employeeDao,resumeDao);

		}
	}

	/**
	 * 得到
	 * 
	 * @param auditionrecordguid
	 * @return
	 */
	public AuditionResult getAuditionResultByAuditionResultId(String auditionrecordguid) {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		AuditionResult model = mapper.getAuditionResultByAuditionRecordId(auditionrecordguid);
		if (model != null) {
			model.convertOneCodeToName(optionDao);
		}
		return model;
	}

	/**
	 * 批量保存
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional
	public void saveAuditionResult(GridServerHandler grid) throws Exception {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		ResumeDao resumeDao=sqlSession.getMapper(ResumeDao.class);
		DepartmentDao departmentDao=sqlSession.getMapper(DepartmentDao.class);
		RecruitprogramDao recruitprogramDao=sqlSession.getMapper(RecruitprogramDao.class);
		UserContext uc = ContextFacade.getUserContext();
		List<Object> addList = grid.getUpdatedRecords(MyCandidates.class);
		for (Object object : addList) {
			MyCandidates model = (MyCandidates) object;

			if (model.getResulttype() != null && model.getResultcontent() != null) {
				// 保存面试结果
				AuditionResult auditionResult = new AuditionResult();
				auditionResult.setAuditionrecordguid(model.getAuditionrecordguid());
				auditionResult.setIsrelease(Constance.ResultType_Two);
				auditionResult.setResulttype(model.getResulttype());
				auditionResult.setResultcontent(model.getResultcontent());
				auditionResult.setModiuser(uc.getUsername());
				auditionResult.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				mapper.insertAuditionResult(auditionResult);

				// 回写我的应聘
				updateMyCandidatesByAuditionResult(recruitprogramDao,departmentDao,auditionResult, mapper, model, myCandidatesDao, optionDao, uc.getUsername(), employeeDao,resumeDao);
			}

		}
	}

}
