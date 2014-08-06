package com.mk.person.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.audition.dao.AuditionDao;
import com.mk.audition.entity.AuditionRecord;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Eduexperience;
import com.mk.employee.entity.Employee;
import com.mk.employee.entity.Family;
import com.mk.employee.entity.HrRecommend;
import com.mk.employee.entity.HrRelatives;
import com.mk.employee.entity.Position;
import com.mk.employee.entity.Train;
import com.mk.employee.entity.Workexperience;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.mail.MailSendService;
import com.mk.framework.mail.SendMessageService;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.mycandidates.entity.Bussiness;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.mycandidates.entity.MyCandidatesHistory;
import com.mk.person.dao.PersonDao;
import com.mk.person.entity.Person;
import com.mk.person.entity.TmpEduExperience;
import com.mk.person.entity.TmpFamily;
import com.mk.person.entity.TmpProjectExperience;
import com.mk.person.entity.TmpRecommend;
import com.mk.person.entity.TmpRelatives;
import com.mk.person.entity.TmpTrain;
import com.mk.person.entity.TmpWorkExperience;
import com.mk.quota.dao.QuotaDao;
import com.mk.recruitment.dao.WebUserDao;
import com.mk.recruitment.entity.WebUser;
import com.mk.recruitprogram.dao.RecruitprogramDao;
import com.mk.recruitprogram.entity.RecruitProgram;
import com.mk.recruitprogram.entity.RecruitProgramOperate;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.EducationExperience;
import com.mk.resume.entity.ProjectExperience;
import com.mk.resume.entity.Relatives;
import com.mk.resume.entity.Resume;
import com.mk.resume.entity.TrainingExperience;
import com.mk.resume.entity.WorkExperience;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.User;

@Service
public class PersonService {

	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CodeConvertNameService codeConvertNameService;
	@Autowired
	private MailSendService mailSendService;
	@Autowired
	private SendMessageService sendMessageService;

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchPerson(GridServerHandler grid) {

		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		CompanyDao comDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao deptDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();

		// 统计
		Integer count = mapper.countPerson(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<Person> list = mapper.searchPerson(grid);
		// 批量
		if (list.size() > Constance.ConvertCodeNum) {
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部门类型
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 职级
			Map<String, String> rankMap = codeConvertNameService.getAllRankMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 編制
			Map<String, String> quotaMap = codeConvertNameService.getAllQuotaMap();
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);
			// 文化程度
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			// 民族
			Map<Integer, String> nationMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.NATION);
			// 血型
			Map<Integer, String> bloodtypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.BLOODTYPE);
			// 政治面貌
			Map<Integer, String> politicsMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.POLITICS);
			// 婚姻状况
			Map<Integer, String> marriedMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.MARRIED);
			// 与本人关系
			Map<Integer, String> contactrelationshipMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RELATIONSHIP);
			// 用工性质
			Map<Integer, String> employtypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.EMPLOYTYPE);
			// 户籍类型
			Map<Integer, String> domicilplaceMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.DOMICILPLACE);
			// 用户列表
			Map<String, String> userMap = codeConvertNameService.getAllEmployeeMap();
			for (Person model : list) {
				model.convertManyCodeToName(companyMap, deptMap, rankMap, postMap, quotaMap, sexMap, cultureMap, nationMap, bloodtypeMap, politicsMap, marriedMap, contactrelationshipMap,
						employtypeMap, userMap, domicilplaceMap);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (Person model : list) {
				model.convertOneCodeToName(optionDao, comDao, deptDao, postDao, quotaDao, systemDao, employeeDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}

		grid.setData(data);

	}

	/**
	 * 保存或修改
	 * 
	 * @param model
	 * @throws Exception 
	 */
	@Transactional
	public void saveOrUpdatePerson(HttpServletRequest request,Person model) throws Exception {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		WebUserDao webuserdao = sqlSession.getMapper(WebUserDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		MyCandidatesDao myCandidatesDao=sqlSession.getMapper(MyCandidatesDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		UserContext uc = ContextFacade.getUserContext();

		if (StringUtils.isEmpty(model.getMycandidatesguid())) {
			model.setModitimestamp(new Date(System.currentTimeMillis()));
			mapper.insertPerson(model);
		} else {
			if(StringUtils.isNotEmpty(model.getPhoto())){
				String photo=model.getPhoto();
				photo=photo.substring(photo.indexOf("u"),photo.length());
				model.setPhoto(photo.replaceAll(" ", ""));
			}
			mapper.updatePerson(model);

			// 把人才信息加入到员工中
			Employee employee = new Employee();
			employee.setEmployeeid(UUIDGenerator.randomUUID());
			employee.setName(model.getName());
			employee.setSex(model.getSex());
			employee.setBirthday(model.getBirthday());
			employee.setCardnumber(model.getCardnumber());
			employee.setCulture(model.getCulture());
			employee.setNation(model.getNation());
			employee.setMobile(model.getMobile());
			employee.setAddressmobile(model.getAddressmobile());
			employee.setResidenceplace(model.getResidenceplace());
			employee.setHomeplace(model.getHomeplace());
			employee.setHomephone(model.getHomephone());
			employee.setBloodtype(model.getBloodtype());
			employee.setDomicilplace(model.getDomicilplace());
			employee.setNativeplace(model.getNativeplace());
			employee.setPolitics(model.getPolitics());
			employee.setMarried(model.getMarried());
			employee.setPrivateemail(model.getPrivateemail());
			employee.setPhoto(model.getPhoto());
			employee.setContactphone(model.getContactphone());
			employee.setContactname(model.getContactname());
			employee.setContactrelationship(model.getContactrelationship());
			employee.setWorkstate(Constance.WORKSTATE_Try);
			employee.setJobnumber(model.getJobnumber());
			employee.setSecrecy(Constance.VALID_NO);
			employee.setChecknumber(model.getJobnumber());
			employee.setEmploytype(model.getEmploytype());
			employee.setJoinworkdate(model.getJoinworkdate());
			employee.setJoingroupdate(new Date(model.getJoindate().getTime()));
			employee.setJoindate(new Date(model.getJoindate().getTime()));
			employee.setSocial(model.getSocial());
			employee.setEmail(model.getEmail());
			employee.setOfficeaddress(model.getOfficeaddress());
			employee.setDorder(Constance.VALID_NO);
			employee.setMemo(model.getMemo());
			employee.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			employee.setHeight(model.getHeight());
			employee.setSocietydate(model.getSocietydate());
			employee.setLegaladdress(model.getLegaladdress());
			employee.setPayment(model.getPayment());
			employee.setStarttime(model.getStarttime());
			employee.setEndtime(model.getEndtime());
			employee.setSkills(model.getSkills());
			employee.setHobbies(model.getHobbies());
			employee.setModiuser(uc.getUsername());
			employeeDao.insertEmployee(employee);

			// 用户表
			// 新加系统用户
			User user = new User(employee);
			systemDao.insertUser(user);
			// 保存用户通讯录权限
			systemDao.saveUserAddressCompany(user);

			// 保存职工表
			Position position = new Position();
			position.setPostionguid(UUIDGenerator.randomUUID());
			position.setEmployeeid(employee.getEmployeeid());
			position.setCompanyid(model.getCompanyid());
			position.setDeptid(model.getDeptid());
			position.setPostid(model.getPostid());
			position.setQuotaid(model.getQuotaid());
			position.setRankid(model.getRankid());
			position.setIsstaff(Constance.VALID_YES);
			position.setStartdate(new Date(model.getJoindate().getTime()));
			position.setState(Constance.VALID_YES);
			position.setModiuser(employee.getModiuser());
			position.setModitimestamp(employee.getModitimestamp());
			employeeDao.insertPosition(position);

			// 保存教育经历
			List<TmpEduExperience> edulist = mapper.getAllTmpEduExperienceListById(model.getMycandidatesguid());
			if (!edulist.isEmpty()) {
				for (TmpEduExperience edu : edulist) {
					Eduexperience eduexperience = new Eduexperience();
					eduexperience.setEduexperienceid(UUIDGenerator.randomUUID());
					eduexperience.setEmployeeid(employee.getEmployeeid());
					eduexperience.setEduorg(edu.getEduorg());
					eduexperience.setCulture(edu.getCulture());
					eduexperience.setLearningtype(edu.getLearningtype());
					eduexperience.setStartdate(edu.getStartdate());
					eduexperience.setEnddate(edu.getEnddate());
					eduexperience.setProfessional(edu.getProfessional());
					eduexperience.setDescription(edu.getDescription());
					employeeDao.insertEduexperience(eduexperience);
				}
			}

			// 保存工作经历
			List<TmpWorkExperience> worklist = mapper.getAllTmpWorkExperienceById(model.getMycandidatesguid());
			if (!edulist.isEmpty()) {
				for (TmpWorkExperience work : worklist) {
					Workexperience workExperience = new Workexperience();
					workExperience.setWorkexperienceid(UUIDGenerator.randomUUID());
					workExperience.setEmployeeid(employee.getEmployeeid());
					workExperience.setWorkunit(work.getWorkunit());
					workExperience.setStartdate(work.getStartdate());
					workExperience.setEnddate(work.getEnddate());
					workExperience.setEmail(work.getEmail());
					workExperience.setReporter(work.getReporter());
					workExperience.setReporterphone(work.getReporterphone());
					String job=work.getJob();
					if(job.length()>20){
						job=job.substring(0,20);
					}
					workExperience.setJob(job);
					workExperience.setDescription(work.getDescription());
					employeeDao.insertWorkexperience(workExperience);
					
					
					WebUser wuser=null;
					//boolean flagwork=false;
					
					//保存证明人
					if(StringUtils.isNotEmpty(work.getEmail())){
						wuser = webuserdao.checkWebUserByEmail(null, work.getEmail());
						if (wuser == null) {
							System.out.println("新增邮箱用户==================");
							
							// 注册用户
							wuser = new WebUser(work.getEmail(), work.getReporter());
							wuser.setWebuserguid(UUIDGenerator.randomUUID());
							wuser.setMobile(work.getReporterphone());
							webuserdao.insertWebUser(wuser);
							
							// 保存简历
							Resume resume=new Resume();
							resume.setWebuserguid(wuser.getWebuserguid());
							resume.setName(work.getReporter());
							resume.setMark(Constance.VALID_NO);
							resume.setMobile(work.getReporterphone());
							resume.setEmail(work.getEmail());
							resume.setModtime(new Timestamp(System.currentTimeMillis()));
							resume.setCreatetime(new Timestamp(System.currentTimeMillis()));
							resumeDao.insertResume(resume);
							
							//flagwork=true;
						}
					}else{
						if(StringUtils.isNotEmpty(work.getReporterphone())){
							wuser = webuserdao.checkWebUserByEmail(null, work.getReporterphone());
							if (wuser == null) {
								System.out.println("新增手机用户==================");
								
								// 注册用户
								wuser = new WebUser(work.getReporterphone(), work.getReporter());
								wuser.setWebuserguid(UUIDGenerator.randomUUID());
								wuser.setMobile(work.getReporterphone());
								webuserdao.insertWebUser(wuser);
								
								// 保存简历
								Resume resume=new Resume();
								resume.setWebuserguid(wuser.getWebuserguid());
								resume.setName(work.getReporter());
								resume.setMark(Constance.VALID_NO);
								resume.setMobile(work.getReporterphone());
								resume.setEmail(work.getReporterphone());
								resume.setModtime(new Timestamp(System.currentTimeMillis()));
								resume.setCreatetime(new Timestamp(System.currentTimeMillis()));
								resumeDao.insertResume(resume);
								
								//flagwork=false;
							}
						}
					}
					
					
					if(wuser!=null){
						// 保存我的应聘
						MyCandidates myCandidate = new MyCandidates();
						myCandidate.setMycandidatesguid(UUIDGenerator.randomUUID());
						myCandidate.setCandidatesstate(Constance.VALID_YES);
						myCandidate.setWebuserguid(wuser.getWebuserguid());
						myCandidate.setProgress(Constance.VALID_YES);
						myCandidate.setCandidatestime(new Date(System.currentTimeMillis()));
						myCandidate.setReadtype(Constance.VALID_NO);
						myCandidate.setCandidatestype(Constance.User7);
						myCandidate.setEmployeeid(employee.getEmployeeid());
						myCandidate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
						myCandidatesDao.insertMyCandidates(myCandidate);
						
						
//						Resume webresume = resumeDao.getResumeById(wuser.getWebuserguid());
//						//发送短信和邮件
//						if(webresume!=null){
//							if(flagwork){
//								mailSendService.sendResumeMailTo(webresume,webresume.getCreatetime());
//							}
//							sendMessageService.sendResumeAssessMsgTo(webresume,webresume.getCreatetime());
//						}
					}
				}
			}

			// 保存培训经历
			List<TmpTrain> trainlist = mapper.getTmpTrainListByMycandidateGuid(model.getMycandidatesguid());
			if (!edulist.isEmpty()) {
				for (TmpTrain trains : trainlist) {
					Train train = new Train();
					train.setTrainid(UUIDGenerator.randomUUID());
					train.setEmployeeid(employee.getEmployeeid());
					train.setTraindate(trains.getTraindate());
					train.setTrainlength(trains.getTrainlength());
					train.setTrainorg(trains.getTrainorg());
					train.setContent(trains.getContent());
					train.setResult(trains.getResult());
					employeeDao.insertTrain(train);
				}
			}
			
			
			//保存推荐信息
			List<TmpRecommend> recommendlist = mapper.getTmpRecommendListByMycandidateGuid(model.getMycandidatesguid());
			if(!recommendlist.isEmpty()){
				for (TmpRecommend tmpRecommend : recommendlist) {
					HrRecommend hrRecommend=new HrRecommend();
					hrRecommend.setRecommendguid(UUIDGenerator.randomUUID());
					hrRecommend.setEmployeeid(employee.getEmployeeid());
					hrRecommend.setMobile(tmpRecommend.getMobile());
					hrRecommend.setName(tmpRecommend.getName());
					hrRecommend.setSex(tmpRecommend.getSex());
					hrRecommend.setSituation(tmpRecommend.getSituation());
					hrRecommend.setEmail(tmpRecommend.getEmail());
					hrRecommend.setRecommendpostname(tmpRecommend.getRecommendpostname());
					hrRecommend.setWorkage(tmpRecommend.getWorkage());
					hrRecommend.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					employeeDao.insertHrRecommend(hrRecommend);
					
					WebUser web=null;
					//boolean flagRecommend=false;
					
					//保存推荐人
					if(StringUtils.isNotEmpty(tmpRecommend.getEmail())){
						web= webuserdao.checkWebUserByEmail(null, tmpRecommend.getEmail());
						if (web == null) {
							System.out.println("新增邮箱用户==================");
							
							// 注册用户
							web = new WebUser(tmpRecommend.getEmail(), tmpRecommend.getName());
							web.setWebuserguid(UUIDGenerator.randomUUID());
							web.setMobile(tmpRecommend.getMobile());
							webuserdao.insertWebUser(web);
							
							// 保存简历
							Resume resume=new Resume();
							resume.setWebuserguid(web.getWebuserguid());
							resume.setName(tmpRecommend.getName());
							resume.setMark(Constance.VALID_NO);
							resume.setMobile(tmpRecommend.getMobile());
							resume.setEmail(tmpRecommend.getEmail());
							resume.setModtime(new Timestamp(System.currentTimeMillis()));
							resume.setCreatetime(new Timestamp(System.currentTimeMillis()));
							resumeDao.insertResume(resume);
							
							//flagRecommend=true;
						}
					}else{
						if(StringUtils.isNotEmpty(tmpRecommend.getMobile())){
							web= webuserdao.checkWebUserByEmail(null, tmpRecommend.getMobile());
							if (web == null) {
								System.out.println("新增手机用户==================");
								
								// 注册用户
								web = new WebUser(tmpRecommend.getMobile(), tmpRecommend.getName());
								web.setWebuserguid(UUIDGenerator.randomUUID());
								web.setMobile(tmpRecommend.getMobile());
								webuserdao.insertWebUser(web);
								
								// 保存简历
								Resume resume=new Resume();
								resume.setWebuserguid(web.getWebuserguid());
								resume.setName(tmpRecommend.getName());
								resume.setMark(Constance.VALID_NO);
								resume.setSex(tmpRecommend.getSex());
								resume.setMobile(tmpRecommend.getMobile());
								resume.setEmail(tmpRecommend.getMobile());
								resume.setModtime(new Timestamp(System.currentTimeMillis()));
								resume.setCreatetime(new Timestamp(System.currentTimeMillis()));
								resumeDao.insertResume(resume);
								
								//flagRecommend=false;
							}
						}
					}
					
					if(web!=null){
						// 保存我的应聘
						MyCandidates myCandidate = new MyCandidates();
						myCandidate.setMycandidatesguid(UUIDGenerator.randomUUID());
						myCandidate.setCandidatesstate(Constance.VALID_YES);
						myCandidate.setWebuserguid(web.getWebuserguid());
						myCandidate.setProgress(Constance.VALID_YES);
						myCandidate.setCandidatestime(new Date(System.currentTimeMillis()));
						myCandidate.setReadtype(Constance.VALID_NO);
						myCandidate.setCandidatestype(Constance.User7);
						myCandidate.setEmployeeid(employee.getEmployeeid());
						if(StringUtils.isNotEmpty(tmpRecommend.getRecommendpostname())){
							myCandidate.setRecruitpostname(tmpRecommend.getRecommendpostname());
						}
						myCandidate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
						myCandidatesDao.insertMyCandidates(myCandidate);
						
//						Resume webresume = resumeDao.getResumeById(web.getWebuserguid());
//						//发送短信和邮件
//						if(webresume!=null){
//							if(flagRecommend){
//								mailSendService.sendResumeMailTo(webresume,webresume.getCreatetime());
//							}
//							sendMessageService.sendResumeAssessMsgTo(webresume,webresume.getCreatetime());
//						}
					}
				}
			}
			
			
			
			//保存员工公司亲属关系
			List<TmpRelatives> relativeslist = mapper.getTmpRelativesListByMycandidateGuid(model.getMycandidatesguid());
			if(!relativeslist.isEmpty()){
				for (TmpRelatives tmpRelatives : relativeslist) {
					HrRelatives hrRelatives=new HrRelatives();
					hrRelatives.setRelativesguid(UUIDGenerator.randomUUID());
					hrRelatives.setEmployeeid(employee.getEmployeeid());
					hrRelatives.setCompanyname(tmpRelatives.getCompanyname());
					hrRelatives.setDeptname(tmpRelatives.getDeptname());
					hrRelatives.setPostname(tmpRelatives.getPostname());
					String employeename=tmpRelatives.getEmployeename();
					hrRelatives.setEmployeename(employeename);
					employeeDao.insertHrRelatives(hrRelatives);
				}
			}
			
			//保存家庭状况
			List<TmpFamily> familylist = mapper.getTmpFamilyListByMycandidateGuid(model.getMycandidatesguid());
			if (!familylist.isEmpty()) {
				for (TmpFamily familys : familylist) {
					Family family=new Family();
					family.setContactrelationship(familys.getContactrelationship());
					family.setFamilyid(UUIDGenerator.randomUUID());
					family.setJob(familys.getJob());
					family.setMobile(familys.getMobile());
					family.setName(familys.getName());
					family.setWorkunit(familys.getWorkunit());
					family.setEmployeeid(employee.getEmployeeid());
					employeeDao.insertFamily(family);
				}
			}
			
			
			
			
			
			// 回写我的应聘
			MyCandidates myCandidates = myCandidatesDao.getMyCandidatesById(model.getMycandidatesguid());
			if (myCandidates != null) {
				myCandidates.setCandidatesstate(Constance.CandidatesState_Fifteen);
				myCandidates.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				myCandidatesDao.updateMyCandidates(myCandidates);

//				// 回写招聘计划
//				List<RecruitProgram> recruitprograms = recruitprogramDao.getRecruitprogramByQuotaidAndPostid(model.getQuotaid(), model.getPostid());
//				if (!recruitprograms.isEmpty()) {
//					RecruitProgram recruitprogram = recruitprograms.get(0);
//					Integer num = recruitprogram.getPostnum();
//					recruitprogram.setPostnum(num - 1);
//					recruitprogramDao.updateRecruitprogram(recruitprogram);
//					
//					//操作信息(员工入职后都是锁定状态)
//					RecruitProgramOperate ope = new RecruitProgramOperate();
//					ope.setRecruitprogramoperateguid(UUIDGenerator.randomUUID());
//					ope.setRecruitprogramguid(recruitprogram.getRecruitprogramguid());
//					ope.setOperatestate(Constance.OperateState_Lock);
//					ope.setModiuser(uc.getUsername());
//					ope.setModitimestamp(new Timestamp(System.currentTimeMillis()));
//					ope.setOperatenum(Constance.VALID_YES);
//					recruitprogramDao.insertRecruitProgramOperate(ope);
//				}
			}

			// 删除待入职人才信息
			mapper.delTmpEduExperienceByMycandidatesguId(model.getMycandidatesguid());
			mapper.delTmpTrainByMycandidatesguId(model.getMycandidatesguid());
			mapper.delTmpWorkExperienceByMycandidatesguId(model.getMycandidatesguid());
			mapper.delTmpFamilyByMycandidatesguId(model.getMycandidatesguid());
			mapper.delTmpRelativesByMycandidatesguId(model.getMycandidatesguid());
			mapper.delTmpProjectExperienceByMycandidatesguId(model.getMycandidatesguid());
			mapper.delTmpRecommendByMycandidatesguId(model.getMycandidatesguid());
			mapper.delPersonById(model.getMycandidatesguid());

		}

	}

	/**
	 * 保存
	 * 
	 * @param model
	 */
	@Transactional
	public void savePerson(Person model) throws Exception {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao deptDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		RecruitprogramDao recruitprogramDao=sqlSession.getMapper(RecruitprogramDao.class);
		
		UserContext uc = ContextFacade.getUserContext();

		if (StringUtils.isNotEmpty(model.getMycandidatesguid())) {
			MyCandidates myCandidates = myCandidatesDao.getMyCandidatesById(model.getMycandidatesguid());
			if (myCandidates != null) {
				Resume resume = resumeDao.getResumeById(myCandidates.getWebuserguid());
				if (resume != null) {
					// 添加待入职人才信息
					model.setName(resume.getName());
					model.setSex(resume.getSex());
					model.setBirthday(resume.getBirthday());
					model.setCardnumber("无");
					model.setMobile(resume.getMobile());
					model.setAddressmobile(resume.getMobile());
					model.setHomeplace(resume.getHomeplace());
					model.setPrivateemail(resume.getEmail());
					model.setPhoto(resume.getPhoto());
					model.setModiuser(uc.getUsername());
					model.setModitimestamp(new Date(System.currentTimeMillis()));
					model.setCulture(resume.getCulture());
					model.setPhoto(resume.getPhoto());
					mapper.insertPerson(model);

					// 教育经历
					List<EducationExperience> listEducation = resumeDao.getAllEducationExperienceByWebuserId(resume.getWebuserguid());
					if(!listEducation.isEmpty()){
						for (EducationExperience education : listEducation) {
							TmpEduExperience tmpeduexperience = new TmpEduExperience();
							tmpeduexperience.setEduexperienceid(UUIDGenerator.randomUUID());
							tmpeduexperience.setMycandidatesguid(model.getMycandidatesguid());
							tmpeduexperience.setEduorg(education.getSchool());
							tmpeduexperience.setCulture(education.getCulture());
							tmpeduexperience.setStartdate(education.getStartdate());
							tmpeduexperience.setEnddate(education.getEnddate());
							tmpeduexperience.setProfessional(education.getSpecialty());
							tmpeduexperience.setDescription(education.getMajordescription());
							mapper.insertTmpEduExperience(tmpeduexperience);
						}
					}

					// 培训经历
					List<TrainingExperience> listTraining = resumeDao.getAllTrainingExperienceByWebuserId(resume.getWebuserguid());
					if(!listTraining.isEmpty()){
						for (TrainingExperience training : listTraining) {
							TmpTrain tmptrain = new TmpTrain();
							tmptrain.setTrainid(UUIDGenerator.randomUUID());
							tmptrain.setMycandidatesguid(model.getMycandidatesguid());
							tmptrain.setTraindate(training.getStartdate());
							tmptrain.setTrainorg(training.getTraininginstitutions());
							tmptrain.setContent(training.getTrainingcontent());
	
							mapper.insertTmpTrain(tmptrain);
						}
					}

					// 工作经历
					List<WorkExperience> listwork = resumeDao.getAllWorkExperienceByWebuserId(resume.getWebuserguid());
					if(!listwork.isEmpty()){
						for (WorkExperience work : listwork) {
							TmpWorkExperience tmpworkexperience = new TmpWorkExperience();
							tmpworkexperience.setWorkexperienceid(UUIDGenerator.randomUUID());
							tmpworkexperience.setMycandidatesguid(model.getMycandidatesguid());
							tmpworkexperience.setWorkunit(work.getWorkunit());
							tmpworkexperience.setStartdate(work.getStartdate());
							tmpworkexperience.setEnddate(work.getEnddate());
							tmpworkexperience.setJob(work.getJobdescription());
							tmpworkexperience.setDescription(work.getJobdescription());
							mapper.insertTmpWorkExperience(tmpworkexperience);
						}
					}
					
					// 项目经历
					List<ProjectExperience> prolist = resumeDao.getAllProjectExperienceByWebuserId(resume.getWebuserguid());
					if(!prolist.isEmpty()){
						for(ProjectExperience pro:prolist){
							TmpProjectExperience tmpProjectExperience=new TmpProjectExperience();
							tmpProjectExperience.setProjectexperienceguid(UUIDGenerator.randomUUID());
							tmpProjectExperience.setEnddate(pro.getEnddate());
							tmpProjectExperience.setStartdate(pro.getStartdate());
							tmpProjectExperience.setItemname(pro.getItemname());
							tmpProjectExperience.setJobdescription(pro.getJobdescription());
							tmpProjectExperience.setMycandidatesguid(model.getMycandidatesguid());
							tmpProjectExperience.setRmk(pro.getRmk());
							tmpProjectExperience.setModtime(pro.getModtime());
							mapper.insertTmpProjectExperience(tmpProjectExperience);
						}
					}
					
					//亲属关系表
					List<Relatives> relativeslist = resumeDao.getRelativesByWebUserId(resume.getWebuserguid());
					if(!relativeslist.isEmpty()){
						for(Relatives relatives:relativeslist){
							TmpRelatives tmpRelatives=new TmpRelatives();
							tmpRelatives.setEmployeename(relatives.getEmployeename());
							tmpRelatives.setCompanyname(relatives.getCompanyname());
							tmpRelatives.setDeptname(relatives.getDeptname());
							tmpRelatives.setPostname(relatives.getPostname());
							tmpRelatives.setMycandidatesguid(model.getMycandidatesguid());
							tmpRelatives.setRelativesguid(UUIDGenerator.randomUUID());
							mapper.insertTmpRelatives(tmpRelatives);
						}
					}
					
				}

				// 保存操作历史
				MyCandidatesHistory myCandidatesHistory = new MyCandidatesHistory();
				myCandidatesHistory.setMycandidateshistoryguid(UUIDGenerator.randomUUID());
				myCandidatesHistory.setModiuser(uc.getUsername());
				myCandidatesHistory.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				myCandidatesHistory.setMycandidatesguid(model.getMycandidatesguid());
				OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CANDIDATESSTATE, Constance.CandidatesState_Fourteen);
				if (opt != null) {
					myCandidatesHistory.setModimemo(opt.getName());
				}
				myCandidatesDao.insertMyCandidatesHistory(myCandidatesHistory);

				
				
				//回写招聘计划
				List<RecruitProgram> recruitprograms=recruitprogramDao.getRecruitprogramByQuotaidAndPostid(model.getQuotaid(), model.getPostid());
				if(!recruitprograms.isEmpty()&&recruitprograms!=null){
					RecruitProgram recruitprogram=recruitprograms.get(0);
					
					//操作信息(员工入职后都是锁定状态)
					RecruitProgramOperate ope = new RecruitProgramOperate();
					ope.setRecruitprogramoperateguid(UUIDGenerator.randomUUID());
					ope.setRecruitprogramguid(recruitprogram.getRecruitprogramguid());
					ope.setOperatestate(Constance.OperateState_Lock);
					ope.setModiuser(uc.getUsername());
					ope.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					ope.setOperatenum(Constance.VALID_YES);
					recruitprogramDao.insertRecruitProgramOperate(ope);
					
					//应聘信息上附招聘操作信息id
					myCandidates.setRecruitprogramoperateguid(ope.getRecruitprogramoperateguid());
				}
				
				// 回写应聘状态
				myCandidates.setCandidatesstate(Constance.CandidatesState_Fourteen);
				myCandidates.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				myCandidatesDao.updateMyCandidates(myCandidates);

				// 带入职的时候发送邮件(给应聘者、报到人)
				model.convertOneCodeToName(optionDao, companyDao, deptDao, postDao, quotaDao, systemDao, employeeDao);
				if(resume!=null){
					try{
						//发送邮件
						mailSendService.sendRuZhiMailTo(resume, model,uc.getMobile());
						
						//发送短信
						sendMessageService.sendRuZhiMsgTo(resume, model,uc.getMobile());
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				//mailSendService.sendRuZhiMailToByPeople(resume, model,uc.getMobile());
				// 发送短信
				//sendMessageService.sendRuZhiMsgToByPeople(resume, model,uc.getMobile());
				
			}

		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Person getPersonById(String id) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		CompanyDao comDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao deptDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);

		Person model = mapper.getPersonById(id);
		if (model != null) {
			model.convertOneCodeToName(optionDao, comDao, deptDao, postDao, quotaDao, systemDao, employeeDao);
		}
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param ids
	 */
	@Transactional
	public void delPersonById(HttpServletRequest request, String ids) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delPersonById(id);
		}

	}
	
	
	
	/**
	 * 发短信和邮件给推荐人以及证明
	 * 
	 * 
	 */
	@Transactional
	public void sendMessageToReterence(String nowdate) throws Exception{
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		
		// 取一个月前入职推荐的人和证明人
		List<HrRecommend> recommends = employeeDao.getHrRecommendByInduction(nowdate);
		if (!recommends.isEmpty()&&recommends!=null) {
			for(HrRecommend hrrecommend:recommends){
				String email=hrrecommend.getEmail();
				// String regex = "[a-zA-Z0-9_]{6,12}+@[a-zA-Z]+(\\.[a-zA-Z]+){1,3}";
				//发送邮件(有邮箱地址)
				 if(StringUtils.isNotEmpty(email)){
					 mailSendService.sendResumeMailTo(hrrecommend.getName(),hrrecommend.getModitimestamp(), email);
				 }else{
					 email=hrrecommend.getMobile();
				 }
				 
				//发送短信
				sendMessageService.sendResumeAssessMsgTo(hrrecommend.getName(), hrrecommend.getModitimestamp(), hrrecommend.getMobile(), email);

//				 if(email.matches(regex)){
//					 mailSendService.sendResumeMailTo(hrrecommend.getName(),hrrecommend.getModitimestamp(), email);
//				 }
				
				//回写短信发送
				Bussiness bussiness=new Bussiness();
				bussiness.setBussinessguid(UUIDGenerator.randomUUID());
				bussiness.setOperateguid(hrrecommend.getRecommendguid());
				bussiness.setTablename(Constance.HrRecommendTable);
				mapper.insertBussiness(bussiness);
			}
		}
	}
}
