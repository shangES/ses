package com.mk.recruitment.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Eduexperience;
import com.mk.employee.entity.Employee;
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
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.mycandidates.service.MyCandidatesService;
import com.mk.quota.dao.QuotaDao;
import com.mk.recruitment.dao.RecruitmentDao;
import com.mk.recruitment.dao.WebUserDao;
import com.mk.recruitment.entity.Category;
import com.mk.recruitment.entity.PostContent;
import com.mk.recruitment.entity.RecruitPost;
import com.mk.recruitment.entity.WebUser;
import com.mk.recruitprogram.dao.RecruitprogramDao;
import com.mk.recruitprogram.entity.RecruitProgram;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.EducationExperience;
import com.mk.resume.entity.Resume;
import com.mk.resume.entity.TrainingExperience;
import com.mk.resume.entity.WorkExperience;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.User;

@Service
public class RecruitPostService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CodeConvertNameService codeConvertNameService;
	@Autowired
	private MyCandidatesService myCandidatesService;
	
	
	/**
	 * 得到所有职位类别
	 * 
	 * @param id
	 * @return
	 */
	public List<Category> getAllCategory() {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		List<Category> list = mapper.getAllCategory(Constance.VALID_YES);
		return list;
	}

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchRecruitPost(GridServerHandler grid) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countRecruitPost(grid);
		PageUtils.setTotalRows(grid, count);
		// 招聘计划
		Map<String, RecruitProgram> recruitProgramMap = codeConvertNameService.getAllRecruitProgramMap();
		// 搜索
		List<RecruitPost> list = mapper.searchRecruitPost(grid);
		// 公司
		Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
		// 崗位
		Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
		// 部門
		Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
		// 工作地点
		Map<String, String> workplaceMap = codeConvertNameService.getAllWorkPlaceMap();
		// 职位类别
		Map<String, String> categoryMap = codeConvertNameService.getAllCategoryMap();
		// 学历
		Map<Integer, String> educationalMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
		// 工作年限
		Map<Integer, String> jobyearMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.WORKAGE);
		// 招聘类别
		Map<Integer, String> posttypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.POSTTYPE);
		// 语言要求
		Map<Integer, String> languageMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.LANGUAGE);
		// 状态
		Map<Integer, String> stateMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RecruitProgram_state);
		if (list.size() > Constance.ConvertCodeNum) {
			for (RecruitPost model : list) {
				model.convertManyCodeToName(deptMap, workplaceMap, categoryMap, educationalMap, jobyearMap, posttypeMap, languageMap, companyMap, recruitprogramDao, postMap, recruitProgramMap,
						stateMap, mapper);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (RecruitPost model : list) {
				model.convertOneCodeToName(mapper, departmentDao, optionDao, companyDao, recruitprogramDao, postDao, quotaDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}

	/**
	 * 保存或修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateRecruitPost(RecruitPost model) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		// 修改时间
		model.setModtime(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isEmpty(model.getRecruitpostguid())) {
			model.setRecruitpostguid(UUIDGenerator.randomUUID());
			model.setReleasedate(new Date(System.currentTimeMillis()));
			mapper.insertRecruitPost(model);
		} else {
			mapper.updateRecruitPost(model);
		}

		// 保存正文
		PostContent postcontent = model.getPostcontent();
		mapper.delPostContentByRecruitPostId(model.getRecruitpostguid());
		if (postcontent != null && StringUtils.isNotEmpty(postcontent.getPostcontent())) {
			postcontent.setRecruitpostguid(model.getRecruitpostguid());
			mapper.insertPostContent(postcontent);
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public RecruitPost getRecruitPostById(String id) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);

		RecruitPost model = mapper.getRecruitPostByRecruitPostId(id);
		if (model != null) {
			// 正文
			model.convertOneCodeToName(mapper, departmentDao, optionDao, companyDao, recruitprogramDao, postDao, quotaDao);
			PostContent postcontent = mapper.getPostContentByRecruitPostId(id);
			model.setPostcontent(postcontent);
		}
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	public void delRecruitPostById(String ids) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			
			//删除我的收藏
			mapper.delMyFavoritesByRecruitPostGuid(id);
			
			//删除有此职位的应聘信息
			List<MyCandidates> list = myCandidatesDao.getMyCandidatesByRecruitPostGuid(id);
			if(!list.isEmpty()){
				for(MyCandidates myCandidates:list){
					myCandidatesService.delMyCandidatesById(myCandidates.getMycandidatesguid());
				}
			}
			
			mapper.delPostContentByRecruitPostId(id);
			mapper.delRecruitPostByRecruitPostId(id);
		}
	}

	/**
	 * 审核/取消发布
	 * 
	 * @param ids
	 */
	@Transactional
	public void auditRecruitPostById(String ids, Integer state) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		UserContext uc = ContextFacade.getUserContext();
		String[] obj = ids.split(",");
		for (String id : obj) {
			RecruitPost model = mapper.getRecruitPostByRecruitPostId(id);
			if (model != null) {
				model.setIsaudited(state);
				model.setAudituser(null);
				if (state == 0)
					model.setAudituser(uc.getUsername());
				mapper.updateRecruitPost(model);
			}
		}

	}

	/***
	 * 
	 * 内部员工竞聘
	 * 
	 * 
	 */
	@Transactional
	public String applyRecruitPostByUserId(String id) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		UserContext uc = ContextFacade.getUserContext();
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		//RecruitPost recruitpost = mapper.getRecruitPostByRecruitPostId(id);
		User user = systemDao.getUserByUserId(uc.getUserId());
		if (user != null) {
			Employee employee = employeeDao.getEmployeeById(user.getEmployeeid());
			if (employee != null) {
				WebUser webuser = webUserDao.getWebUserById(employee.getEmployeeid());
				if (webuser == null) {
					// 保存外网用户
					webuser = new WebUser(employee.getEmail(), employee.getName());
					webuser.setWebuserguid(employee.getEmployeeid());
					webUserDao.insertWebUser(webuser);

					// 保存简历
					Resume resume = new Resume();
					resume.setWebuserguid(webuser.getWebuserguid());
					resume.setName(employee.getName());
					resume.setBirthday(employee.getBirthday());
					resume.setSex(employee.getSex());
					resume.setMobile(employee.getMobile());
					resume.setEmail(webuser.getEmail());
					resume.setCreatetime(new Timestamp(System.currentTimeMillis()));
					resume.setModtime(new Timestamp(System.currentTimeMillis()));
					resume.setHomeplace(employee.getHomephone());
					resume.setCulture(employee.getCulture());
					resume.setPhoto(employee.getPhoto());
					resumeDao.insertResume(resume);
				}

				// 判断是否有工作经历

				resumeDao.delWorkExperienceByWebuserId(webuser.getWebuserguid());
				List<Workexperience> worklist = employeeDao.getWorkexperienceByEmployeeId(employee.getEmployeeid());
				if (!worklist.isEmpty()) {
					for (Workexperience works : worklist) {
						WorkExperience work = new WorkExperience();
						work.setWorkexperienceguid(UUIDGenerator.randomUUID());
						work.setWebuserguid(webuser.getWebuserguid());
						work.setWorkunit(works.getWorkunit());
						work.setPosation(works.getJob());
						work.setStartdate(works.getStartdate());
						work.setEnddate(works.getEnddate());
						work.setJobdescription(works.getDescription());
						work.setModtime(new Timestamp(System.currentTimeMillis()));
						resumeDao.insertWorkExperience(work);
					}
				}

				// 判断是否有教育经历
				resumeDao.delEducationExperienceByWebuserId(webuser.getWebuserguid());
				List<Eduexperience> edulist = employeeDao.getEduexperienceByEmployeeId(employee.getEmployeeid());
				if (!edulist.isEmpty()) {
					for (Eduexperience edus : edulist) {
						EducationExperience edu = new EducationExperience();
						edu.setEducationexperienceguid(UUIDGenerator.randomUUID());
						edu.setWebuserguid(webuser.getWebuserguid());
						edu.setStartdate(edus.getStartdate());
						edu.setEnddate(edus.getEnddate());
						edu.setCulture(edus.getCulture());
						edu.setSchool(edus.getEduorg());
						edu.setSpecialty(edus.getProfessional());
						edu.setMajordescription(edus.getDescription());
						edu.setModtime(new Timestamp(System.currentTimeMillis()));
						resumeDao.insertEducationExperience(edu);
					}
				}

				// 判断是否有培训经历
				resumeDao.delTrainingExperienceByWebuserId(webuser.getWebuserguid());
				List<Train> trainlist = employeeDao.getTrainByEmployeeId(employee.getEmployeeid());
				if (!trainlist.isEmpty()) {
					for (Train trains : trainlist) {
						TrainingExperience train = new TrainingExperience();
						train.setTrainingexperienceguid(UUIDGenerator.randomUUID());
						train.setWebuserguid(webuser.getWebuserguid());
						train.setStartdate(trains.getTraindate());
						train.setTraininginstitutions(trains.getTrainorg());
						train.setCertificate(trains.getResult());
						train.setTrainingcontent(trains.getContent());
						train.setModtime(new Timestamp(System.currentTimeMillis()));
						resumeDao.insertTrainingExperience(train);
					}
				}
			}
			
			return employee.getEmployeeid();
			
		}

		return null;
		
	}

	/**
	 * 内部职位列表
	 * 
	 * @param grid
	 */
	public void searchInternalPost(GridServerHandler grid) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		
		
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countRecruitPost(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<RecruitPost> list = mapper.searchInternalPost(grid);
		if (list.size() > 0) {
			for (RecruitPost model : list) {
				model.convertOneCodeToName(mapper, departmentDao, optionDao, companyDao, recruitprogramDao, postDao, quotaDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}
	
	
	/**
	 * 我的竞聘
	 * 
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchMyRecruitpost(GridServerHandler grid) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();
		
		// 權限組織
		Constance.initAdminPam(grid.getParameters());
		
		// 统计
		Integer count = mapper.countMyRecruitpost(grid);
		PageUtils.setTotalRows(grid, count);
		
		// 搜索
		List<RecruitPost> list = mapper.searchMyRecruitpost(grid);
		for (RecruitPost model : list) {
			model.convertOneCodeToName(mapper, departmentDao, optionDao, companyDao, recruitprogramDao, postDao, quotaDao);
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}

	
	/**
	 * 内部竞聘生成应聘信息
	 * 
	 * 
	 * @param id
	 * @param recruitpostguid
	 */
	public void competitionRecruitPostById(String id, String recruitpostguid) {
		MyCandidatesDao myCandidatesDao=sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		MyCandidates myCandidates=new MyCandidates();
		RecruitPost recruitPost = mapper.getRecruitPostByRecruitPostId(id);
		if(recruitPost!=null){
			myCandidates.setRecruitpostname(recruitPost.getPostname());
		}
		myCandidates.setMycandidatesguid(UUIDGenerator.randomUUID());
		myCandidates.setRecruitpostguid(recruitpostguid);
		myCandidates.setWebuserguid(id);
		myCandidates.setCandidatesstate(Constance.VALID_YES);
		myCandidates.setReadtype(Constance.VALID_NO);
		myCandidates.setCandidatestype(Constance.User6);
		myCandidates.setProgress(Constance.VALID_YES);
		myCandidates.setCandidatestime(new Date(System.currentTimeMillis()));
		myCandidates.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		myCandidatesDao.insertMyCandidates(myCandidates);
	}
	
	
}
