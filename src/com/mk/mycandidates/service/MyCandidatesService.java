package com.mk.mycandidates.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.framework.chart.ChartModel;
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
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.mycandidates.entity.Bussiness;
import com.mk.mycandidates.entity.CandidatesOperate;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.mycandidates.entity.MyCandidatesHistory;
import com.mk.mycandidates.entity.Recommend;
import com.mk.person.dao.PersonDao;
import com.mk.person.entity.ExaminationRecord;
import com.mk.quota.dao.QuotaDao;
import com.mk.recruitment.dao.RecruitmentDao;
import com.mk.recruitment.dao.WebUserDao;
import com.mk.recruitment.entity.RecruitPost;
import com.mk.recruitprogram.dao.RecruitprogramDao;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.Resume;
import com.mk.resume.entity.ResumeEamil;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.ResumeFilter;
import com.mk.system.entity.Role;
import com.mk.system.entity.UserManageRange;
import com.mk.system.entity.UserRole;
import com.mk.thirdpartner.dao.ThirdPartnerDao;
import com.mk.thirdpartner.entity.ThirdPartner;
import com.mk.todo.entity.TodoPam;

@Service
public class MyCandidatesService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CodeConvertNameService codeConvertNameService;
	@Autowired
	private MailSendService mailSendService;
	@Autowired
	private SendMessageService sendMessageService;

	/**
	 * 搜索(简历管理)
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchMyCandidatesAndResume(GridServerHandler grid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = mapper.countMyCandidatesAndResume(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<MyCandidates> list = mapper.searchMyCandidatesAndResume(grid);
		
		if (list.size() > Constance.ConvertCodeNum) {
			// 来源
			Map<Integer, String> resumetypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RESUMETYPE);
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 工作地点
			Map<String, String> workplaceMap = codeConvertNameService.getAllWorkPlaceMap();
			// 职位
			Map<String, RecruitPost> recruitpostMap = codeConvertNameService.getAllRecruitPostMap();
			// 工作年限
			Map<Integer, String> workageMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.WORKAGE);
			// 学历
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			// 应聘状态
			Map<Integer, String> candidatesMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CANDIDATESSTATE);
			// 外网用户
			Map<String, String> webUserMap = codeConvertNameService.getAllWebUserMap();
			// 体检机构
			Map<String, String> thirdpartnerMap = codeConvertNameService.getAllThirdpartnerMap();
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);

			for (MyCandidates model : list) {
				model.convertManyCodeToName(recruitpostMap, workageMap, cultureMap, companyMap, deptMap, workplaceMap, candidatesMap, resumetypeMap, recruitmentDao, postMap, webUserMap,
						thirdpartnerMap, mapper, personDao, sexMap, resumeDao, auditionDao, optionDao, departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, thirdPartnerDao);
				data.add(JSONUtils.Bean2JSONObject(model));
				
				
			}
		} else {
			for (MyCandidates model : list) {
				model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, mapper, systemDao, personDao,
						recruitprogramDao, quotaDao, auditionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		
		grid.setData(data);
	}

	/**
	 * 搜索(部门筛选)
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchMyCandidatesAndResumeByDept(GridServerHandler grid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();

		String userid = grid.getPageParameter("userid");
		List<ResumeFilter> resumefilters = systemDao.getResumefilterByUserid(userid);

		StringBuffer buffer = new StringBuffer();
		if (!resumefilters.isEmpty()) {
			for (ResumeFilter fiter : resumefilters) {
				buffer.append(" AND ");
				buffer.append(fiter.getTablecolumnname());
				buffer.append(" ");
				buffer.append(fiter.getCode());
				buffer.append(" ");
				buffer.append(fiter.getFilterconditions());
			}
		} else {
			buffer.append(" AND sex = 5");
		}
		Map map = grid.getParameters();
		map.put("sqlWhere", buffer.toString());

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = mapper.countMyCandidatesAndResumeByDept(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<MyCandidates> list = mapper.searchMyCandidatesAndResumeByDept(grid);

		if (list.size() > Constance.ConvertCodeNum) {
			// 来源
			Map<Integer, String> resumetypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RESUMETYPE);
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 工作地点
			Map<String, String> workplaceMap = codeConvertNameService.getAllWorkPlaceMap();
			// 职位
			Map<String, RecruitPost> recruitpostMap = codeConvertNameService.getAllRecruitPostMap();
			// 工作年限
			Map<Integer, String> workageMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.WORKAGE);
			// 学历
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			// 应聘状态
			Map<Integer, String> candidatesMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CANDIDATESSTATE);
			// 外网用户
			Map<String, String> webUserMap = codeConvertNameService.getAllWebUserMap();
			// 体检机构
			Map<String, String> thirdpartnerMap = codeConvertNameService.getAllThirdpartnerMap();
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);

			for (MyCandidates model : list) {
				model.convertManyCodeToName(recruitpostMap, workageMap, cultureMap, companyMap, deptMap, workplaceMap, candidatesMap, resumetypeMap, recruitmentDao, postMap, webUserMap,
						thirdpartnerMap, mapper, personDao, sexMap, resumeDao, auditionDao, optionDao, departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, thirdPartnerDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (MyCandidates model : list) {
				model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, mapper, systemDao, personDao,
						recruitprogramDao, quotaDao, auditionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}

	/**
	 * 搜索(简历筛选)
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchMycandidatesByFilter(GridServerHandler grid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();

		// 统计
		Integer count = mapper.countMycandidatesByFilter(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<MyCandidates> list = mapper.searchMycandidatesByFilter(grid);

		if (list.size() > Constance.ConvertCodeNum) {
			// 来源
			Map<Integer, String> resumetypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RESUMETYPE);
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 工作地点
			Map<String, String> workplaceMap = codeConvertNameService.getAllWorkPlaceMap();
			// 职位
			Map<String, RecruitPost> recruitpostMap = codeConvertNameService.getAllRecruitPostMap();
			// 工作年限
			Map<Integer, String> workageMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.WORKAGE);
			// 学历
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			// 应聘状态
			Map<Integer, String> candidatesMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CANDIDATESSTATE);
			// 外网用户
			Map<String, String> webUserMap = codeConvertNameService.getAllWebUserMap();
			// 体检机构
			Map<String, String> thirdpartnerMap = codeConvertNameService.getAllThirdpartnerMap();
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);

			for (MyCandidates model : list) {
				model.convertManyCodeToName(recruitpostMap, workageMap, cultureMap, companyMap, deptMap, workplaceMap, candidatesMap, resumetypeMap, recruitmentDao, postMap, webUserMap,
						thirdpartnerMap, mapper, personDao, sexMap, resumeDao, auditionDao, optionDao, departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, thirdPartnerDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (MyCandidates model : list) {
				model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, mapper, systemDao, personDao,
						recruitprogramDao, quotaDao, auditionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}

	/**
	 * 搜索(部门简历)
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchMyCandidatesByDept(GridServerHandler grid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = mapper.countMyCandidatesByDept(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<MyCandidates> list = mapper.searchMyCandidatesByDept(grid);

		if (list.size() > Constance.ConvertCodeNum) {
			// 来源
			Map<Integer, String> resumetypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RESUMETYPE);
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 工作地点
			Map<String, String> workplaceMap = codeConvertNameService.getAllWorkPlaceMap();
			// 职位
			Map<String, RecruitPost> recruitpostMap = codeConvertNameService.getAllRecruitPostMap();
			// 工作年限
			Map<Integer, String> workageMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.WORKAGE);
			// 学历
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			// 应聘状态
			Map<Integer, String> candidatesMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CANDIDATESSTATE);
			// 外网用户
			Map<String, String> webUserMap = codeConvertNameService.getAllWebUserMap();
			// 体检机构
			Map<String, String> thirdpartnerMap = codeConvertNameService.getAllThirdpartnerMap();
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);

			for (MyCandidates model : list) {
				model.convertManyCodeToName(recruitpostMap, workageMap, cultureMap, companyMap, deptMap, workplaceMap, candidatesMap, resumetypeMap, recruitmentDao, postMap, webUserMap,
						thirdpartnerMap, mapper, personDao, sexMap, resumeDao, auditionDao, optionDao, departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, thirdPartnerDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (MyCandidates model : list) {
				model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, mapper, systemDao, personDao,
						recruitprogramDao, quotaDao, auditionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}

	/**
	 * 搜索(安排面试的数据(招聘专员负责的部门)
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchMycandidatesByAudition(GridServerHandler grid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = mapper.countMycandidatesByAudition(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<MyCandidates> list = mapper.searchMycandidatesByAudition(grid);

		if (list.size() > Constance.ConvertCodeNum) {
			// 来源
			Map<Integer, String> resumetypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RESUMETYPE);
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 工作地点
			Map<String, String> workplaceMap = codeConvertNameService.getAllWorkPlaceMap();
			// 职位
			Map<String, RecruitPost> recruitpostMap = codeConvertNameService.getAllRecruitPostMap();
			// 工作年限
			Map<Integer, String> workageMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.WORKAGE);
			// 学历
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			// 应聘状态
			Map<Integer, String> candidatesMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CANDIDATESSTATE);
			// 外网用户
			Map<String, String> webUserMap = codeConvertNameService.getAllWebUserMap();
			// 体检机构
			Map<String, String> thirdpartnerMap = codeConvertNameService.getAllThirdpartnerMap();
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);

			for (MyCandidates model : list) {
				model.convertManyCodeToName(recruitpostMap, workageMap, cultureMap, companyMap, deptMap, workplaceMap, candidatesMap, resumetypeMap, recruitmentDao, postMap, webUserMap,
						thirdpartnerMap, mapper, personDao, sexMap, resumeDao, auditionDao, optionDao, departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, thirdPartnerDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (MyCandidates model : list) {
				model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, mapper, systemDao, personDao,
						recruitprogramDao, quotaDao, auditionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}

	/**
	 * 搜索(待发布的面试数据)
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchMycandidatesByResult(GridServerHandler grid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = mapper.countMycandidatesByResult(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<MyCandidates> list = mapper.searchMycandidatesByResult(grid);

		if (list.size() > Constance.ConvertCodeNum) {
			// 来源
			Map<Integer, String> resumetypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RESUMETYPE);
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 工作地点
			Map<String, String> workplaceMap = codeConvertNameService.getAllWorkPlaceMap();
			// 职位
			Map<String, RecruitPost> recruitpostMap = codeConvertNameService.getAllRecruitPostMap();
			// 工作年限
			Map<Integer, String> workageMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.WORKAGE);
			// 学历
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			// 应聘状态
			Map<Integer, String> candidatesMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CANDIDATESSTATE);
			// 外网用户
			Map<String, String> webUserMap = codeConvertNameService.getAllWebUserMap();
			// 体检机构
			Map<String, String> thirdpartnerMap = codeConvertNameService.getAllThirdpartnerMap();
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);

			for (MyCandidates model : list) {
				model.convertManyCodeToName(recruitpostMap, workageMap, cultureMap, companyMap, deptMap, workplaceMap, candidatesMap, resumetypeMap, recruitmentDao, postMap, webUserMap,
						thirdpartnerMap, mapper, personDao, sexMap, resumeDao, auditionDao, optionDao, departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, thirdPartnerDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (MyCandidates model : list) {
				model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, mapper, systemDao, personDao,
						recruitprogramDao, quotaDao, auditionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}

	/**
	 * 搜索(邀请他人来反馈面试结果)(部门简历)
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchMycandidatesByMainInterviewer(GridServerHandler grid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = mapper.countMycandidatesByMainInterviewer(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<MyCandidates> list = mapper.searchMycandidatesByMainInterviewer(grid);

		if (list.size() > Constance.ConvertCodeNum) {
			// 来源
			Map<Integer, String> resumetypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RESUMETYPE);
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 工作地点
			Map<String, String> workplaceMap = codeConvertNameService.getAllWorkPlaceMap();
			// 职位
			Map<String, RecruitPost> recruitpostMap = codeConvertNameService.getAllRecruitPostMap();
			// 工作年限
			Map<Integer, String> workageMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.WORKAGE);
			// 学历
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			// 应聘状态
			Map<Integer, String> candidatesMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CANDIDATESSTATE);
			// 外网用户
			Map<String, String> webUserMap = codeConvertNameService.getAllWebUserMap();
			// 体检机构
			Map<String, String> thirdpartnerMap = codeConvertNameService.getAllThirdpartnerMap();
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);

			for (MyCandidates model : list) {
				model.convertManyCodeToName(recruitpostMap, workageMap, cultureMap, companyMap, deptMap, workplaceMap, candidatesMap, resumetypeMap, recruitmentDao, postMap, webUserMap,
						thirdpartnerMap, mapper, personDao, sexMap, resumeDao, auditionDao, optionDao, departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, thirdPartnerDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (MyCandidates model : list) {
				model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, mapper, systemDao, personDao,
						recruitprogramDao, quotaDao, auditionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}

	/**
	 * 面试人员列表
	 * 
	 * @param grid
	 */
	public void searchCandidatesPerson(GridServerHandler grid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
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
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();

		// 统计
		Integer count = mapper.countCandidatesPerson(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<MyCandidates> list = mapper.searchCandidatesPerson(grid);

		if (list.size() > Constance.ConvertCodeNum) {
			// 来源
			Map<Integer, String> resumetypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RESUMETYPE);
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 工作地点
			Map<String, String> workplaceMap = codeConvertNameService.getAllWorkPlaceMap();
			// 职位
			Map<String, RecruitPost> recruitpostMap = codeConvertNameService.getAllRecruitPostMap();
			// 工作年限
			Map<Integer, String> workageMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.WORKAGE);
			// 学历
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			// 应聘状态
			Map<Integer, String> candidatesMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CANDIDATESSTATE);
			// 外网用户
			Map<String, String> webUserMap = codeConvertNameService.getAllWebUserMap();
			// 体检机构
			Map<String, String> thirdpartnerMap = codeConvertNameService.getAllThirdpartnerMap();
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);

			for (MyCandidates model : list) {
				model.convertManyCodeToName(recruitpostMap, workageMap, cultureMap, companyMap, deptMap, workplaceMap, candidatesMap, resumetypeMap, recruitmentDao, postMap, webUserMap,
						thirdpartnerMap, mapper, personDao, sexMap, resumeDao, auditionDao, optionDao, departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, thirdPartnerDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (MyCandidates model : list) {
				model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, mapper, systemDao, personDao,
						recruitprogramDao, quotaDao, auditionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}

	/**
	 * 忽略取消操作
	 * 
	 * @param mycandidatesguid
	 * @param state
	 * @param recommendpostguid
	 * @param matchmemo
	 * @throws Exception
	 */
	@Transactional
	public MyCandidates updateMyCandidatesByState(String mycandidatesguid, Integer state, String modimemo, String memo, String recommendguid) throws Exception {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		UserContext uc = ContextFacade.getUserContext();

		MyCandidates model = mapper.getMyCandidatesById(mycandidatesguid);
		
		//后面用来判断简历筛选的忽略还是简历管理的取消
		boolean isfilter=true;
		
		if (model != null) {
			// 操作历史保存
			MyCandidatesHistory myCandidatesHistory = new MyCandidatesHistory();
			myCandidatesHistory.setMycandidateshistoryguid(UUIDGenerator.randomUUID());
			if (model.getCandidatesstate() >= Constance.CandidatesState_Seven||state==Constance.CandidatesState_Blacklist) {
				myCandidatesHistory.setModimemo(memo);
			} else {
				OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CANDIDATESSTATE, model.getCandidatesstate());
				if (opt != null) {
					if(state==1){
						myCandidatesHistory.setModimemo(opt.getName() + Constance.ZhengChang);
					}else{
						myCandidatesHistory.setModimemo(opt.getName() + Constance.hulv);
					}
					
				}
			}
			
			// 取消的时候
			if (state.equals(Constance.CandidatesState_Four) || state.equals(Constance.CandidatesState_Seven) || state.equals(Constance.CandidatesState_Twelve)) {
				isfilter=false;
				
				// 取消面试的时候要把面试记录设无效
				if (state.equals(Constance.CandidatesState_Four)) {
					AuditionRecord auditon = auditionDao.getAuditionRecordByMycandidatesguidAndState(mycandidatesguid);
					if (auditon != null) {
						auditon.setState(Constance.VALID_NO);
						auditionDao.updateAuditionRecord(auditon);
					}
				} else if (state.equals(Constance.CandidatesState_Seven)) {
					ExaminationRecord examin = personDao.getExaminationRecordByMyCandidatesGuidAndState(mycandidatesguid);
					if (examin != null) {
						examin.setState(Constance.VALID_NO);
						personDao.updateExaminationRecord(examin);
					}
				} else if (state.equals(Constance.CandidatesState_Twelve)) {
					// 取消入职需删除待人才信息
					personDao.delTmpEduExperienceByMycandidatesguId(mycandidatesguid);
					personDao.delTmpTrainByMycandidatesguId(mycandidatesguid);
					personDao.delTmpWorkExperienceByMycandidatesguId(mycandidatesguid);
					personDao.delTmpProjectExperienceByMycandidatesguId(mycandidatesguid);
					personDao.delTmpRecommendByMycandidatesguId(mycandidatesguid);
					personDao.delTmpFamilyByMycandidatesguId(mycandidatesguid);
					personDao.delTmpRelativesByMycandidatesguId(mycandidatesguid);
					personDao.delPersonById(mycandidatesguid);
				}

				// 执行取消操作的历史保存
				OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CANDIDATESSTATE, model.getCandidatesstate());
				if (opt != null) {
					myCandidatesHistory.setModimemo(opt.getName() + Constance.quxiao);
				}
			} else {
				// 把选中的那条推荐信息给设为无效(忽略)
				if (StringUtils.isNotEmpty(recommendguid)) {
					Recommend recommend = mapper.getRecommendById(recommendguid);
					if (recommend != null) {
						recommend.setState(Constance.VALID_NO);
						mapper.updateRecommend(recommend);
					}
				}

				// 忽略面试记录
				AuditionRecord audition = auditionDao.getAuditionRecordByMycandidatesguidAndState(mycandidatesguid);
				if (audition != null) {
					audition.setState(Constance.VALID_NO);
					auditionDao.updateAuditionRecord(audition);
				}

				// 忽略体检记录
				ExaminationRecord examination = personDao.getExaminationRecordByMyCandidatesGuidAndState(mycandidatesguid);
				if (examination != null) {
					examination.setState(Constance.VALID_NO);
					personDao.updateExaminationRecord(examination);
				}
				
				// 忽略入职需删除待人才信息
				personDao.delTmpEduExperienceByMycandidatesguId(mycandidatesguid);
				personDao.delTmpTrainByMycandidatesguId(mycandidatesguid);
				personDao.delTmpWorkExperienceByMycandidatesguId(mycandidatesguid);
				personDao.delTmpProjectExperienceByMycandidatesguId(mycandidatesguid);
				personDao.delTmpRecommendByMycandidatesguId(mycandidatesguid);
				personDao.delTmpFamilyByMycandidatesguId(mycandidatesguid);
				personDao.delTmpRelativesByMycandidatesguId(mycandidatesguid);
				personDao.delPersonById(mycandidatesguid);
			}

			// 保存历史(应聘)
			myCandidatesHistory.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			myCandidatesHistory.setModiuser(uc.getUsername());
			myCandidatesHistory.setMycandidatesguid(mycandidatesguid);
			mapper.insertMyCandidatesHistory(myCandidatesHistory);
			
			//判断是简历筛选的忽略还是简历管理的忽略
			if(StringUtils.isEmpty(recommendguid)&&isfilter){
				List<Recommend> recommends = mapper.getRecommendByCandidatesGuidAndState(model.getMycandidatesguid());
				if(recommends!=null&&!recommends.isEmpty()){
					for(Recommend recommend:recommends){
						recommend.setState(Constance.VALID_NO);
						mapper.updateRecommend(recommend);
					}
				}
				model.setCandidatesstate(state);
			}else{
				// 忽略推荐信息后是否修改应聘状态
				List<Recommend> recommendlist = mapper.getRecommendByCandidatesGuidAndState(model.getMycandidatesguid());
				if (model.getCandidatesstate().equals(Constance.CandidatesState_Two)&&(recommendlist!=null&&!recommendlist.isEmpty())) {
					model.setCandidatesstate(model.getCandidatesstate());
				} else {
					model.setCandidatesstate(state);
				}
			}
			
			//把锁定的那条招聘计划给删除(忽略取消)
			if(StringUtils.isNotEmpty(model.getRecruitprogramoperateguid())){
				recruitprogramDao.delRecruitProgramOperateById(model.getRecruitprogramoperateguid());
			}
			
			//回写应聘信息
			model.setRecruitprogramoperateguid(null);
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			mapper.updateMyCandidates(model);
		}

		return model;
	}

	/**
	 * 体检操作
	 * 
	 * @param ids
	 * @param state
	 * @param recommendpostguid
	 * @param matchmemo
	 * @param recommendcompanyid
	 * @param recommenddeptid
	 * @throws Exception
	 */
	@Transactional
	public void updateBatchCandidatesstate(String ids, Integer state, String thirdpartnerguid, Integer isemail, Integer ismsg) throws Exception {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		UserContext uc = ContextFacade.getUserContext();

		if (StringUtils.isEmpty(ids))
			return;

		String[] obj = ids.split(",");
		if (state.equals(Constance.CandidatesState_Ten)) {
			// 待体检
			StringBuffer msg = new StringBuffer();
			for (String id : obj) {
				MyCandidates model = mapper.getMyCandidatesById(id);
				if (model != null) {
					// 删除有效的一条(并发)
					personDao.delExaminationRecordByCandidatesguidAndState(id);

					// 插入体检记录
					ExaminationRecord examinationRecord = new ExaminationRecord();
					examinationRecord.setExaminationrecordguid(UUIDGenerator.randomUUID());
					examinationRecord.setState(Constance.VALID_YES);
					examinationRecord.setThirdpartnerguid(thirdpartnerguid);
					examinationRecord.setMycandidatesguid(id);
					
					// 取有效的推荐信息拿推荐id
					List<Recommend> recommends = mapper.getRecommendByCandidatesGuidAndState(model.getMycandidatesguid());
					if (recommends!=null&&!recommends.isEmpty()) {
						Recommend recommend = recommends.get(0);
						examinationRecord.setRecommendguid(recommend.getRecommendguid());
					}
					personDao.insertExaminationRecord(examinationRecord);

					// 保存操作历史
					MyCandidatesHistory myCandidatesHistory = new MyCandidatesHistory();
					myCandidatesHistory.setMycandidateshistoryguid(UUIDGenerator.randomUUID());
					myCandidatesHistory.setModiuser(uc.getUsername());
					myCandidatesHistory.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					myCandidatesHistory.setMycandidatesguid(id);
					OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CANDIDATESSTATE, state);
					if (opt != null) {
						myCandidatesHistory.setModimemo(opt.getName());
					}
					mapper.insertMyCandidatesHistory(myCandidatesHistory);

					// 回写应聘状态
					model.setCandidatesstate(state);
					model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					mapper.updateMyCandidates(model);

					// 邮件信息
					 Resume resume=resumeDao.getResumeById(model.getWebuserguid());
					 if (resume != null) {
					 	msg.append(resume.getName());
					 	msg.append("，");
					 }
				}
			}

			// 设置长度
			 if (msg.length() > 0) {
				 msg.setLength(msg.length() - 1);
				 msg.append("(");
				 msg.append(obj.length);
				 msg.append("人)");
			 }

			 //发送邮件 发送短信给体检单位
			 ThirdPartner thirdpartner=thirdPartnerDao.getThirdPartnerById(thirdpartnerguid);
			 if (thirdpartner != null) {
				 if(isemail.equals(Constance.VALID_YES)){
					 mailSendService.sendTiJianMailTo(thirdpartner, msg.toString(),uc.getMobile(),uc.getUsername());
				 }
			
				 if(ismsg.equals(Constance.VALID_YES)){
					 sendMessageService.sendTiJianMsgTo(thirdpartner, msg.toString(),uc.getMobile(),uc.getUsername());
				 }
			
			 }

		}

	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delMyCandidatesById(String ids) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);

		if (StringUtils.isNotEmpty(ids)) {
			String[] obj = ids.split(",");
			for (String id : obj) {
				// 面试记录
				auditionDao.delInterviewerByMycandidatesguid(id);
				auditionDao.delAuditionResultByMycandidatesguid(id);
				auditionDao.delAuditionRecordByMycandidatesguid(id);

				// 内部推荐信息
				resumeDao.delInteriorRecommendById(id);

				// 推荐信息
				mapper.delRecommendByCandidatesGuid(id);
				mapper.delCandidatesOperateByCandidatesGuid(id);
				mapper.delyCandidatesHistoryByMyCandidatesGuid(id);

				// 人才库
				personDao.delTmpWorkExperienceByMycandidatesguId(id);
				personDao.delTmpEduExperienceByMycandidatesguId(id);
				personDao.delTmpTrainByMycandidatesguId(id);
				personDao.delTmpProjectExperienceByMycandidatesguId(id);
				personDao.delTmpFamilyByMycandidatesguId(id);
				personDao.delTmpRelativesByMycandidatesguId(id);
				personDao.delTmpRecommendByMycandidatesguId(id);
				personDao.delPersonById(id);
				personDao.delExaminationRecordByMyCandidatesGuid(id);

				mapper.delMyCandidatesById(id);
			}
		}

	}

	/**
	 * 修改状态
	 * 
	 * @param ids
	 * @param state
	 */
	@Transactional
	public void updateResumeMarkById(String ids, Integer state) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			Resume model = mapper.getResumeById(id);
			if (model != null) {
				model.setMark(state);
				mapper.updateResume(model);
			}
		}
	}

	/**
	 * 得到数据
	 * 
	 * @param id
	 * @return
	 */
	public MyCandidates getMyCandidatesById(String id) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		MyCandidates model = mapper.getMyCandidatesById(id);
		if (model != null) {
			model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, mapper, systemDao, personDao,
					recruitprogramDao, quotaDao, auditionDao);
		}
		return model;
	}

	/**
	 * 得到数据(根据应聘id和推荐id拿1条数据)
	 * 
	 * @param id
	 * @return
	 */
	public MyCandidates getMyCandidatesByIdAndRecommendGuid(String id, String recommendguid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		List<MyCandidates> list = mapper.getMyCandidatesByIdAndRecommendGuid(id, recommendguid);
		
		if(!list.isEmpty()){
			MyCandidates model = list.get(0);

			if (model != null) {
				model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, mapper, systemDao, personDao,
						recruitprogramDao, quotaDao, auditionDao);
			}
			return model;
		}
		
		return null;
		
	}

	/**
	 * 验证用户是否有在面试之后的数据
	 * 
	 * @param userguid
	 * @param name
	 * @return
	 */
	public String checkMyCandidatesByUserGuid(String userguid, Integer state) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);

		if (StringUtils.isNotEmpty(userguid)) {
			StringBuffer buffer = new StringBuffer();

			String[] obj = userguid.split(",");
			for (String id : obj) {
				MyCandidates model = mapper.getMyCandidatesByUserGuid(id, state);
				if (model != null) {
					buffer.append(model.getName());
					buffer.append(",");
				}
			}

			if (StringUtils.isNotEmpty(buffer.toString())) {
				buffer.setLength(buffer.length() - 1);
				buffer.append("已在面试流程中！");
				return buffer.toString();
			}
		}
		return null;
	}

	/**
	 * 得到
	 * 
	 * @param grid
	 */
	public void searchMyCandidatesByState(GridServerHandler grid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = mapper.countMyCandidatesByState(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<MyCandidates> list = mapper.searchMyCandidatesByState(grid);

		if (list.size() > Constance.ConvertCodeNum) {
			// 来源
			Map<Integer, String> resumetypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RESUMETYPE);
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 工作地点
			Map<String, String> workplaceMap = codeConvertNameService.getAllWorkPlaceMap();
			// 职位
			Map<String, RecruitPost> recruitpostMap = codeConvertNameService.getAllRecruitPostMap();
			// 工作年限
			Map<Integer, String> workageMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.WORKAGE);
			// 学历
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			// 应聘状态
			Map<Integer, String> candidatesMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CANDIDATESSTATE);
			// 外网用户
			Map<String, String> webUserMap = codeConvertNameService.getAllWebUserMap();
			// 体检机构
			Map<String, String> thirdpartnerMap = codeConvertNameService.getAllThirdpartnerMap();
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);

			for (MyCandidates model : list) {
				model.convertManyCodeToName(recruitpostMap, workageMap, cultureMap, companyMap, deptMap, workplaceMap, candidatesMap, resumetypeMap, recruitmentDao, postMap, webUserMap,
						thirdpartnerMap, mapper, personDao, sexMap, resumeDao, auditionDao, optionDao, departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, thirdPartnerDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (MyCandidates model : list) {
				model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, mapper, systemDao, personDao,
						recruitprogramDao, quotaDao, auditionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);

	}

	/**
	 * 批量保存(安排面试)
	 * 
	 * @param grid
	 * @return
	 */
	@Transactional
	public void saveAuditionGrid(GridServerHandler grid) throws Exception {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);

		UserContext uc = ContextFacade.getUserContext();

		Role role = systemDao.getRoleByRoleName(Constance.DEPARTMENTBRANCH);
		
		String isemail = grid.getPageParameter("isemail");
		String ismsg = grid.getPageParameter("ismsg");

		// 修改的行
		List<Object> list = grid.getInsertedRecords(MyCandidates.class);
		if (!list.isEmpty()) {
			for (Object obj : list) {
				MyCandidates model = (MyCandidates) obj;

				if (StringUtils.isNotEmpty(model.getAuditionaddress()) && StringUtils.isNotEmpty(model.getMaininterviewerguid()) && model.getAuditiontime() != null) {

					// 保存面试地址
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

					// 删除安排的面试中有效(全删全插防止并发)
					AuditionRecord audition = mapper.getAuditionRecordByMycandidatesguidAndState(model.getMycandidatesguid());
					if (audition != null) {
						mapper.delInterviewerByAuditionRecordId(audition.getAuditionrecordguid());
					}
					mapper.delAuditionRecordByCandidatesguidAndState(model.getMycandidatesguid(), Constance.VALID_YES);

					
					// 保存面试记录
					AuditionRecord auditionRecord = new AuditionRecord();
					auditionRecord.setAuditionrecordguid(UUIDGenerator.randomUUID());
					auditionRecord.setRecommendguid(model.getRecommendguid());
					auditionRecord.setState(Constance.VALID_YES);
					auditionRecord.setAuditionaddress(model.getAuditionaddress());
					auditionRecord.setAuditiondate(model.getAuditiontime());
					auditionRecord.setMaininterviewerguid(model.getMaininterviewerguid());
					auditionRecord.setModimemo(model.getModimemo());
					auditionRecord.setMycandidatesguid(model.getMycandidatesguid());
					auditionRecord.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					auditionRecord.setModiuser(uc.getUsername());
					mapper.insertAuditionRecord(auditionRecord);
					
					//部门面试官赋权
					if(role!=null){
						UserRole userRole = systemDao.getUserRoleByRoleAndUsergGuid(role.getRoleid(), auditionRecord.getMaininterviewerguid());
						if(userRole == null){
							userRole = new UserRole();
							userRole.setUserguid(auditionRecord.getMaininterviewerguid());
							userRole.setRoleid(role.getRoleid());
							systemDao.insertUserRole(userRole);
						}
					}
					
					//判断推荐者是否有华数公司的权限 无增加公司权限
					UserManageRange userManagerange = systemDao.getUserManageRangeByUserIdAndCompanyId(Constance.COMPANYID,auditionRecord.getMaininterviewerguid());
					if(userManagerange==null){
						UserManageRange manageRange=new UserManageRange();
						manageRange.setCompanyid(Constance.COMPANYID);
						manageRange.setUserguid(auditionRecord.getMaininterviewerguid());
						manageRange.setIsdefault(Constance.VALID_YES);
						systemDao.insertUserManageRange(manageRange);
					}
					
					// 保存操作历史
					MyCandidatesHistory myCandidatesHistory = new MyCandidatesHistory();
					myCandidatesHistory.setMycandidateshistoryguid(UUIDGenerator.randomUUID());
					myCandidatesHistory.setModiuser(uc.getUsername());
					myCandidatesHistory.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					myCandidatesHistory.setMycandidatesguid(model.getMycandidatesguid());
					OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CANDIDATESSTATE, Constance.CandidatesState_Five);
					if (opt != null) {
						myCandidatesHistory.setModimemo(opt.getName());
					}
					myCandidatesDao.insertMyCandidatesHistory(myCandidatesHistory);

					// 回写我的应聘
					model.setCandidatesstate(Constance.CandidatesState_Five);
					model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					myCandidatesDao.updateMyCandidates(model);

					// 发邮件(应聘者、面试官)
					 List<Recommend> recommendlist =myCandidatesDao.getRecommendByCandidatesGuidAndState(model.getMycandidatesguid());
					 if (!recommendlist.isEmpty()) {
						 Resume resume =resumeDao.getResumeById(model.getWebuserguid());
						 Recommend recommend = recommendlist.get(0);
						 recommend.convertOneCodeToName(departmentDao,companyDao,postDao, systemDao, employeeDao, quotaDao,optionDao);
						 auditionRecord.convertOneCodeToName(systemDao,employeeDao, companyDao, departmentDao, postDao,quotaDao, optionDao, null);
						 //判断是否发邮件
						 if(isemail.equals(Constance.VALID_YES.toString())){
							 if(resume!=null&&recommend!=null){
								// 发送应聘者
								 mailSendService.sendMianShiMailTo(model,resume,recommend, auditionRecord,uc.getMobile(),uc.getUsername());
								 // 发送面试官
								 mailSendService.sendMianShiMailToInterviewer(model, resume, recommend, auditionRecord,uc.getMobile(),uc.getUsername());
							 }
						 }
						 //判断是否发短信
						 if(ismsg.equals(Constance.VALID_NO.toString())){
							 Bussiness bussiness=new Bussiness();
							 bussiness.setBussinessguid(UUIDGenerator.randomUUID());
							 bussiness.setOperateguid(auditionRecord.getAuditionrecordguid());
							 bussiness.setTablename(Constance.auditionRecordTable);
							 myCandidatesDao.insertBussiness(bussiness);
						 }else{
							 // 发送应聘者
							 if(resume!=null&&recommend!=null){
								 sendMessageService.sendMianShiMsgTo(model, resume,recommend, auditionRecord,uc.getMobile(),uc.getUsername());
							 }
							
						 }
					 }

				}
			}
		}
		grid.setSuccess(true);
	}

	/**
	 * 保存多人推荐
	 * 
	 * @param grid
	 * @return
	 */
	@Transactional
	public String saveRcommendGrid(GridServerHandler grid) throws Exception {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);

		UserContext uc = ContextFacade.getUserContext();

		String isemail = grid.getPageParameter("isemail");
		String ismsg = grid.getPageParameter("ismsg");

		// 添加的行
		List<Object> list = grid.getInsertedRecords(Recommend.class);

		Role role = systemDao.getRoleByRoleName(Constance.DEPARTMENTBRANCH);
		
		
		//20140312日   14:15分钟改的
		int successnum=0;

		if (!list.isEmpty()) {
			for (Object obj : list) {
				Recommend model = (Recommend) obj;

				String ids = model.getMycandidatesguid();
				String[] objs = ids.split(",");
				for (String id : objs) {
					MyCandidates mycandidate = mapper.getMyCandidatesById(id);
					if (StringUtils.isNotEmpty(model.getRecommendcompanyid()) && StringUtils.isNotEmpty(model.getRecommenddeptid()) && StringUtils.isNotEmpty(model.getRecommendpostname())
							&& StringUtils.isNotEmpty(model.getUserguid())) {
						
						successnum++;
						
						// 保存推荐信息
						model.setRecommendguid(UUIDGenerator.randomUUID());
						model.setMycandidatesguid(id);
						model.setState(Constance.VALID_YES);
						model.setModiuser(uc.getUsername());
						model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
						model.setModimemo(model.getModimemo());
						mapper.insertRecommend(model);
						
						//角色赋权
						if(role!=null){
							//部门认定赋权
							UserRole userRole = systemDao.getUserRoleByRoleAndUsergGuid(role.getRoleid(), model.getUserguid());
							if(userRole == null){
								userRole = new UserRole();
								userRole.setUserguid(model.getUserguid());
								userRole.setRoleid(role.getRoleid());
								systemDao.insertUserRole(userRole);
							}
						}
						
						
						//判断推荐者是否有华数公司的权限
						UserManageRange userManagerange = systemDao.getUserManageRangeByUserIdAndCompanyId(Constance.COMPANYID,model.getUserguid());
						if(userManagerange==null){
							UserManageRange manageRange=new UserManageRange();
							manageRange.setCompanyid(Constance.COMPANYID);
							manageRange.setUserguid(model.getUserguid());
							manageRange.setIsdefault(Constance.VALID_YES);
							systemDao.insertUserManageRange(manageRange);
						}
						
						// 保存操作历史
						MyCandidatesHistory myCandidatesHistory = new MyCandidatesHistory();
						myCandidatesHistory.setMycandidateshistoryguid(UUIDGenerator.randomUUID());
						myCandidatesHistory.setModiuser(uc.getUsername());
						myCandidatesHistory.setModitimestamp(new Timestamp(System.currentTimeMillis()));
						myCandidatesHistory.setMycandidatesguid(model.getMycandidatesguid());
						OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CANDIDATESSTATE, Constance.CandidatesState_Two);
						if (opt != null) {
							myCandidatesHistory.setModimemo(opt.getName());
						}
						mapper.insertMyCandidatesHistory(myCandidatesHistory);
						
						// 回写我的应聘
						mycandidate.setCandidatesstate(Constance.CandidatesState_Two);
						mycandidate.setMatchtime(new Timestamp(System.currentTimeMillis()));
						mycandidate.setMatchuser(uc.getUsername());
						mycandidate.setMatchmemo(model.getModimemo());
						mycandidate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
						mapper.updateMyCandidates(mycandidate);
						
						//锁定同一简历的应聘数据(推荐的时候锁定)
						List<MyCandidates> mycandidateslist=mapper.getCandidatesByUserGuid(mycandidate.getWebuserguid(),mycandidate.getMycandidatesguid());
						if(mycandidateslist!=null&&!mycandidateslist.isEmpty()){
							for(MyCandidates mycandidates:mycandidateslist){
								mycandidates.setCandidatesstate(Constance.CandidatesState_BlackLock);
								mapper.updateMyCandidates(mycandidates);
							}
						}

						 // 发邮件给推荐的领导
						 model.convertOneCodeToName(departmentDao,companyDao,postDao, systemDao, employeeDao, quotaDao,optionDao);
						 Resume resume=resumeDao.getResumeById(mycandidate.getWebuserguid());
						 if(resume!=null){
							 if(isemail.equals(Constance.VALID_YES.toString())){
						 		mailSendService.sendTuiJianMailTo(mycandidate,resume,model);
							 }
							
							 //是否发送短信 不发增加记录
							 if(ismsg.equals(Constance.VALID_NO.toString())){
								 Bussiness bussiness=new Bussiness();
								 bussiness.setBussinessguid(UUIDGenerator.randomUUID());
								 bussiness.setOperateguid(model.getRecommendguid());
								 bussiness.setTablename(Constance.recommendTable);
								 mapper.insertBussiness(bussiness);
							 }
						 }
					}
				}
			}
		}
		grid.setSuccess(true);
		
		
		if(successnum>0){
			return "保存成功!";
		}
		
		return "保存不成功！";
	}

	/**
	 * 批量发布
	 * 
	 * @param grid
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void saveAuditionResultGrid(GridServerHandler grid) throws Exception {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		UserContext uc = ContextFacade.getUserContext();
		String isemail = grid.getPageParameter("isemail");
		String ismsg = grid.getPageParameter("ismsg");

		// 修改的行
		List<Object> list = grid.getInsertedRecords(MyCandidates.class);

		if (!list.isEmpty()) {
			for (Object obj : list) {
				MyCandidates model = (MyCandidates) obj;

				if (model.getResultcontent() != null && StringUtils.isNotEmpty(model.getHrresultcontent())) {
					AuditionResult result = mapper.getAuditionResultByAuditionRecordId(model.getAuditionrecordguid());
					// 修改面试评语和hr评语
					if (result != null) {
						result.setResultcontent(model.getResultcontent());
						result.setHrresultcontent(model.getHrresultcontent());
						result.setIsrelease(Constance.VALID_YES);
						mapper.updateAuditionResult(result);
					}
				}

				// 回写我的应聘
				model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				myCandidatesDao.updateMyCandidates(model);

				Resume resume = resumeDao.getResumeById(model.getWebuserguid());
				if (resume != null) {
					if (isemail.equals(Constance.VALID_YES.toString())) {
						// 发送邮件给应聘者
						mailSendService.sendTongGuoMailTo(resume,uc.getMobile(),uc.getUsername());
					}

					if (ismsg.equals(Constance.VALID_YES.toString())) {
						// 发送短信给应聘者
						sendMessageService.sendTongGuoMsgTo(resume,uc.getMobile(),uc.getUsername());
					}
				}
			}
		}
		grid.setSuccess(true);
	}

	/**
	 * 批量修改面试官
	 * 
	 * @param grid
	 * @return
	 */
	@Transactional
	public void saveAuditionMainInterviewer(GridServerHandler grid) {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);

		// 修改的行
		List<Object> list = grid.getInsertedRecords(MyCandidates.class);

		if (!list.isEmpty()) {
			for (Object obj : list) {
				MyCandidates model = (MyCandidates) obj;

				if (StringUtils.isNotEmpty(model.getAuditionrecordguid())) {
					AuditionRecord record = mapper.getAuditionRecordByAuditionRecordId(model.getAuditionrecordguid());

					// 修改面试官
					if (record != null) {
						record.setMaininterviewerguid(model.getMaininterviewerguid());
						mapper.updateAuditionRecord(record);
					}
				}

				// 回写我的应聘
				model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				myCandidatesDao.updateMyCandidates(model);
			}
		}
		grid.setSuccess(true);
	}

	/**
	 * 更新
	 */
	@Transactional
	public void updateInvalidMyCandidatesByState(String _datas) throws Exception {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);

		List<Recommend> list = mapper.getInvalidMyCandidatesByState(_datas);

		if (!list.isEmpty()) {
			for (Recommend recommend : list) {
				// 失效
				recommend.setState(Constance.VALID_NO);
				mapper.updateRecommend(recommend);

				// 判断是否存在有效推荐信息 无则回写应聘信息
				List<Recommend> recommendlist = mapper.getRecommendByCandidatesGuidAndState(recommend.getMycandidatesguid());
				if (recommendlist.isEmpty()) {
					MyCandidates model = mapper.getMyCandidatesById(recommend.getMycandidatesguid());
					model.setCandidatesstate(Constance.CandidatesState_One);
					mapper.updateMyCandidates(model);
				}
			}
		}

	}

	/**
	 * 标记为已读
	 * 
	 * @param ids
	 */
	@Transactional
	public void updateByReadtype(String ids, Integer readtype) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		UserContext uc = ContextFacade.getUserContext();
		if (StringUtils.isNotEmpty(ids)) {
			String[] obj = ids.split(",");
			for (String mycandidatesguid : obj) {
				MyCandidates model = mapper.getMyCandidatesById(mycandidatesguid);
				if (model != null) {
					model.setReadtype(readtype);
					mapper.updateMyCandidates(model);
				}
				
				
				//应聘信息操作表
				CandidatesOperate operate = mapper.getCandidatesOperateByCandidatesGuidAndUserGuid(mycandidatesguid,uc.getUserId());
				if(operate==null){
					CandidatesOperate candidatesOperate=new CandidatesOperate();
					candidatesOperate.setCandidatesoperateguid(UUIDGenerator.randomUUID());
					candidatesOperate.setMycandidatesguid(mycandidatesguid);
					candidatesOperate.setUserguid(uc.getUserId());
					mapper.insertCandidatesOperate(candidatesOperate);
				}
			}
		}
	}

	/**
	 * 首页提示简历信息
	 * 
	 * @return
	 */
	public List<ChartModel> countMsg() {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		List<ChartModel> list = mapper.countMsgByState();
		return list;
	}

	/**
	 * 投递历史
	 * 
	 * @param id
	 * @return
	 */
	public List<MyCandidates> getMyCandidatesByWebUserGuid(String id, String mycandidatesguid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);
		List<MyCandidates> list = mapper.getHistoryMyCandidatesByWebUserGuid(id, mycandidatesguid);
		for (MyCandidates myCandidates : list) {
			myCandidates.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, mapper, systemDao, personDao,
					recruitprogramDao, quotaDao, auditionDao);
		}
		return list;
	}

	/**
	 * 验证是否取消 判断是否还在操作的下个流程中
	 * 
	 * 
	 * @param id
	 * @param state
	 * @return
	 */
	public MyCandidates checkMyCandidatesByState(String id, Integer state) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		MyCandidates model = mapper.getMyCandidatesById(id);
		if (model != null) {
			if (model.getCandidatesstate().equals(state)) {
				return model;
			}
		}
		return null;
	}

	/**
	 * 我的应聘操作历史搜索
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	public void searchMyCandidatesHistoryByMyCandidatesGuid(GridServerHandler grid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countMyCandidatesHistoryByMyCandidatesGuid(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<MyCandidatesHistory> list = mapper.searchMyCandidatesHistoryByMyCandidatesGuid(grid);

		for (MyCandidatesHistory model : list) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}

	/**
	 * 人才库推荐
	 * 
	 * @param model
	 */
	@Transactional
	public void saveMyCandidateAndRecommend(MyCandidates model) throws Exception {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);

		UserContext uc = ContextFacade.getUserContext();
		Role role = systemDao.getRoleByRoleName(Constance.DEPARTMENTBRANCH);
		
		
		// 保存我的应聘
		MyCandidates myCandidates = new MyCandidates();

		// 验证是否有原始邮箱
		List<ResumeEamil> list = resumeDao.getResumeEamilByWebUserId(model.getWebuserguid());
		if (!list.isEmpty()) {
			ResumeEamil resmueemail = list.get(0);
			myCandidates.setResumeeamilguid(resmueemail.getResumeeamilguid());
		}

		myCandidates.setMycandidatesguid(UUIDGenerator.randomUUID());
		myCandidates.setRecruitpostguid(model.getRecruitpostguid());
		myCandidates.setRecruitpostname(model.getRecommendpostname());
		myCandidates.setWebuserguid(model.getWebuserguid());
		myCandidates.setCandidatesstate(model.getCandidatesstate());
		myCandidates.setReadtype(model.getReadtype());
		myCandidates.setCandidatestype(model.getCandidatestype());
		myCandidates.setProgress(Constance.VALID_NO);
		myCandidates.setCandidatestime(new Date(System.currentTimeMillis()));
		myCandidates.setMatchtime(new Timestamp(System.currentTimeMillis()));
		myCandidates.setMatchuser(uc.getUsername());
		myCandidates.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		mapper.insertMyCandidates(myCandidates);

		// 保存推荐信息
		Recommend recommend = new Recommend();
		recommend.setRecommendguid(UUIDGenerator.randomUUID());
		recommend.setMycandidatesguid(myCandidates.getMycandidatesguid());
		recommend.setState(Constance.CandidatesState_One);
		recommend.setRecommendcompanyid(model.getRecommendcompanyid());
		recommend.setRecommenddeptid(model.getRecommenddeptid());
		recommend.setRecommendpostguid(model.getRecommendpostguid());
		recommend.setRecommendpostname(model.getRecommendpostname());
		recommend.setUserguid(model.getUserguid());
		recommend.setModiuser(uc.getUsername());
		recommend.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		recommend.setMatchstate(Constance.CandidatesState_Two);
		recommend.setModimemo(model.getModimemo());
		mapper.insertRecommend(recommend);
		
		//角色赋权
		if(role!=null){
			//部门认定赋权
			UserRole userRole = systemDao.getUserRoleByRoleAndUsergGuid(role.getRoleid(), recommend.getUserguid());
			if(userRole == null){
				userRole = new UserRole();
				userRole.setUserguid(recommend.getUserguid());
				userRole.setRoleid(role.getRoleid());
				systemDao.insertUserRole(userRole);
			}
		}
		
		//判断推荐者是否有华数公司的权限
		UserManageRange userManagerange = systemDao.getUserManageRangeByUserIdAndCompanyId(Constance.COMPANYID,recommend.getUserguid());
		if(userManagerange==null){
			UserManageRange manageRange=new UserManageRange();
			manageRange.setCompanyid(Constance.COMPANYID);
			manageRange.setUserguid(recommend.getUserguid());
			manageRange.setIsdefault(Constance.VALID_YES);
			systemDao.insertUserManageRange(manageRange);
		}
		
		// 保存操作历史
		MyCandidatesHistory myCandidatesHistory = new MyCandidatesHistory();
		myCandidatesHistory.setMycandidateshistoryguid(UUIDGenerator.randomUUID());
		myCandidatesHistory.setModiuser(uc.getUsername());
		myCandidatesHistory.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		myCandidatesHistory.setMycandidatesguid(myCandidates.getMycandidatesguid());
		OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CANDIDATESSTATE, recommend.getMatchstate());
		if (opt != null) {
			myCandidatesHistory.setModimemo(opt.getName());
		}
		mapper.insertMyCandidatesHistory(myCandidatesHistory);

		 // 发邮件和短信给推荐的领导
		 recommend.convertOneCodeToName(departmentDao, companyDao,postDao,systemDao, employeeDao, quotaDao, optionDao);
		 Resume resume =resumeDao.getResumeById(myCandidates.getWebuserguid());
		 if(resume!=null){
			 mailSendService.sendTuiJianMailTo(myCandidates, resume, recommend);
		 }
		 
		 
		//锁定此应聘人的其他应聘信息
		List<MyCandidates> mycandidateslist=mapper.getCandidatesByUserGuid(myCandidates.getWebuserguid(),myCandidates.getMycandidatesguid());
		if(mycandidateslist!=null&&!mycandidateslist.isEmpty()){
			for(MyCandidates mycandidates:mycandidateslist){
				mycandidates.setCandidatesstate(Constance.CandidatesState_BlackLock);
				mapper.updateMyCandidates(mycandidates);
			}
		}
	}

	/**
	 * 邀请他人反馈面试结果（简历管理下）
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchMycandidatesByMainInterviewerList(GridServerHandler grid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = mapper.countMycandidatesByMainInterviewerList(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<MyCandidates> list = mapper.searchMycandidatesByMainInterviewerList(grid);

		if (list.size() > Constance.ConvertCodeNum) {
			// 来源
			Map<Integer, String> resumetypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RESUMETYPE);
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 工作地点
			Map<String, String> workplaceMap = codeConvertNameService.getAllWorkPlaceMap();
			// 职位
			Map<String, RecruitPost> recruitpostMap = codeConvertNameService.getAllRecruitPostMap();
			// 工作年限
			Map<Integer, String> workageMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.WORKAGE);
			// 学历
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			// 应聘状态
			Map<Integer, String> candidatesMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CANDIDATESSTATE);
			// 外网用户
			Map<String, String> webUserMap = codeConvertNameService.getAllWebUserMap();
			// 体检机构
			Map<String, String> thirdpartnerMap = codeConvertNameService.getAllThirdpartnerMap();
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);

			for (MyCandidates model : list) {
				model.convertManyCodeToName(recruitpostMap, workageMap, cultureMap, companyMap, deptMap, workplaceMap, candidatesMap, resumetypeMap, recruitmentDao, postMap, webUserMap,
						thirdpartnerMap, mapper, personDao, sexMap, resumeDao, auditionDao, optionDao, departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, thirdPartnerDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (MyCandidates model : list) {
				model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, mapper, systemDao, personDao,
						recruitprogramDao, quotaDao, auditionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);

	}

	
	/**
	 * 得到部门简历信息
	 * 
	 * 
	 * @return
	 */
	public List<MyCandidates> getMyDptCandidatesByUserGuid(TodoPam pam) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		PersonDao personDao = sqlSession.getMapper(PersonDao.class);
		RecruitprogramDao recruitprogramDao = sqlSession.getMapper(RecruitprogramDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		AuditionDao auditionDao = sqlSession.getMapper(AuditionDao.class);
		
		// 權限組織
		Constance.initAdminPam(pam.getParameters());
		List<MyCandidates> list=mapper.getMyDptCandidatesByUserGuid(pam);
		if(!list.isEmpty()){
			for (MyCandidates model : list) {
				model.convertOneCodeToName(recruitmentDao, optionDao, departmentDao, companyDao, webUserDao, postDao, thirdPartnerDao, employeeDao, resumeDao, mapper, systemDao, personDao,
						recruitprogramDao, quotaDao, auditionDao);
			}
		}
		return list;
	}

	
	/**
	 * 得到有申请职位得到的应聘信息
	 * 
	 * 
	 * @param ids
	 * @return
	 */
	public String checkMyCandidatesByRecruitpostGuid(String ids) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		if (StringUtils.isNotEmpty(ids)) {
			StringBuffer buffer = new StringBuffer();

			String[] obj = ids.split(",");
			for (String id : obj) {
				List<MyCandidates> list=mapper.getMyCandidatesByRecruitPostGuid(id);
				if (!list.isEmpty()) {
					RecruitPost model=recruitmentDao.getRecruitPostByRecruitPostId(id);
					if(model!=null){
						buffer.append(model.getPostname());
						buffer.append(",");
					}
				}
			}

			if (StringUtils.isNotEmpty(buffer.toString())) {
				buffer.setLength(buffer.length() - 1);
				buffer.append("已被人申请,确认要删除和此职位有关的数据吗？");
				return buffer.toString();
			}
		}
		return null;
		
		
		
	}
	
	/**
	 * 退回
	 * 
	 * @param id
	 */
	@Transactional
	public void sendback(String id,String recommendguid){
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		
		//推荐信息修改
		Recommend model = mapper.getRecommendById(recommendguid);
		if(model!=null){
			model.setState(Constance.CandidatesState_Zero);
			mapper.updateRecommend(model);
		}
		
		
		//回写应聘状态
		List<Recommend> list = mapper.getRecommendByCandidatesGuidAndState(id);
		if(list.isEmpty()){
			MyCandidates mycandidates = mapper.getMyCandidatesById(id);
			if(mycandidates!=null){
				mycandidates.setCandidatesstate(Constance.CandidatesState_One);
				//20140313  11:15分钟改的
				mycandidates.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				mapper.updateMyCandidates(mycandidates);
			}
		}
	}

	
	/**
	 * 锁定在面试过程中面试人的其他应聘信息
	 * 
	 * 
	 */
	@Transactional
	public void lockMyCandidates() {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		//查在面试中的应聘信息
		List<MyCandidates> list=mapper.getMyCandidatesByAudition();
		if(list!=null&&!list.isEmpty()){
			for(MyCandidates mycandidates:list){
				//此应聘人的其他应聘信息
				List<MyCandidates> mycandidateslist=mapper.getCandidatesByUserGuid(mycandidates.getWebuserguid(),mycandidates.getMycandidatesguid());
				if(!mycandidateslist.isEmpty()&&mycandidateslist!=null){
					for(MyCandidates model:mycandidateslist){
						model.setCandidatesstate(Constance.CandidatesState_BlackLock);
						mapper.updateMyCandidates(model);
					}
				}
			}
		}
	}
	
	
	/**
	 * 解锁其他应聘信息
	 * 
	 * 
	 */
	@Transactional
	public void unlockMyCandidates() {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		//查锁定的信息
		List<MyCandidates> candidateslist=mapper.getMyCandidatesByLock();
		if(candidateslist!=null&&!candidateslist.isEmpty()){
			for(MyCandidates mycandidates:candidateslist){
				List<MyCandidates> candidates=mapper.getCandidatesByAudition(mycandidates.getWebuserguid());
				if(candidates.isEmpty()){
					mycandidates.setCandidatesstate(Constance.VALID_YES);
					mapper.updateMyCandidates(mycandidates);
				}
			}
		}
		
	}

	
	/**
	 * 验证推荐是否过期(两天锁定)
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	public String checkMyCandidatesByCandidatesGuid(String mycandidatesguid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		
		Date date = new Date(System.currentTimeMillis());
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DATE, Constance.FAILURETIME);
		StringBuffer _datas = new StringBuffer();
		_datas.append(DateUtil.formatDateYMD(ca.getTime()));
		_datas.append(" 23:59:59");
		
		List<Recommend> recommends = mapper.getRecommendByCandidatesGuidAndTime(mycandidatesguid,_datas.toString());
		if(recommends!=null&&!recommends.isEmpty()){
			return "此份简历已被人推荐!";
		}
		
		return null;
	}

	
	/**
	 * 直接体检通过
	 * 
	 * @param ids
	 */
	public void examinationMyCandidatesById(String ids) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		if (StringUtils.isNotEmpty(ids)) {
			String[] obj = ids.split(",");
			for (String id : obj) {
				MyCandidates mycandidates = mapper.getMyCandidatesById(id);
				if(mycandidates!=null){
					mycandidates.setCandidatesstate(Constance.CandidatesState_Twelve);
					mycandidates.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					mapper.updateMyCandidates(mycandidates);
				}
			}
		}
	}
	
	
	/**
	 * 自检通过
	 * 
	 * @param ids
	 */
	public void examinationMyCandidates(String id,String filepath,String recommenid,Integer examinationtype) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		PersonDao personDao=sqlSession.getMapper(PersonDao.class);
		
		MyCandidates model = mapper.getMyCandidatesById(id);
		if(model!=null){
			//删除有效地(并发)
			personDao.delExaminationRecordByCandidatesguidAndState(id);
			
			//并回写应聘表
			model.setCandidatesstate(Constance.CandidatesState_Twelve);
			
			//保存体检记录
			ExaminationRecord record=new ExaminationRecord();
			record.setExaminationrecordguid(UUIDGenerator.randomUUID());
			record.setMycandidatesguid(id);
			record.setRecommendguid(recommenid);
			record.setExaminationstate(Constance.VALID_YES);
			record.setState(Constance.VALID_YES);
			record.setFilepath(filepath);
			record.setExaminationtype(examinationtype);
			record.setExaminationdate(new Date(System.currentTimeMillis()));
			
			//不合格体检不通过
			if(examinationtype.equals(Constance.ExaminationType_One)){
				model.setCandidatesstate(Constance.CandidatesState_Thirteen);
				record.setExaminationstate(Constance.ExaminationState_Two);
			}
			personDao.insertExaminationRecord(record);
			
			//回写招聘
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			mapper.updateMyCandidates(model);
		}
		
	}
}
