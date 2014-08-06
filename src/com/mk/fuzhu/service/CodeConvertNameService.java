package com.mk.fuzhu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mk.OrgPathUtil;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Budgetype;
import com.mk.company.entity.Company;
import com.mk.company.entity.Job;
import com.mk.company.entity.Rank;
import com.mk.company.service.CompanyService;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.department.service.DepartmentService;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.framework.constance.Constance;
import com.mk.framework.grid.util.StringUtils;
import com.mk.quota.dao.QuotaDao;
import com.mk.quota.entity.Quota;
import com.mk.recruitment.dao.RecruitmentDao;
import com.mk.recruitment.dao.WebUserDao;
import com.mk.recruitment.entity.Category;
import com.mk.recruitment.entity.RecruitPost;
import com.mk.recruitment.entity.WebUser;
import com.mk.recruitment.entity.WorkPlace;
import com.mk.recruitprogram.dao.RecruitprogramDao;
import com.mk.recruitprogram.entity.RecruitProgram;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.OptionType;
import com.mk.system.entity.User;
import com.mk.system.entity.UserRole;
import com.mk.thirdpartner.dao.ThirdPartnerDao;
import com.mk.thirdpartner.entity.ThirdPartner;

@Component
public class CodeConvertNameService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private DepartmentService departmentService;

	/**
	 * 公司
	 * 
	 * @return
	 */
	public Map<String, Company> getAllCompanyMap() {
		List<Company> list = companyService.getHasCompanys(Constance.VALID_NO);
		if (list == null || list.isEmpty()) {
			return new HashMap<String, Company>();
		}
		return OrgPathUtil.getAllCompanyFullPath(list);
	}

	/**
	 * 部門
	 * 
	 * @return
	 */
	public Map<String, Department> getAllDepartmentMap() {
		List<Department> list = departmentService.getHasDepartment(Constance.VALID_NO);
		return OrgPathUtil.getAllDepartmentFullPath(list);
	}

	/**
	 * 職務
	 * 
	 * @return
	 */
	public Map<String, String> getAllJobMap() {
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);

		List<Job> list = companyDao.getAllJob(Constance.VALID_NO);
		Map<String, String> map = new HashMap<String, String>();
		for (Job model : list) {
			map.put(model.getJobid(), model.getJobname());
		}
		return map;
	}

	// 招聘职位
	public Map<String, RecruitPost> getAllRecruitPostMap() {
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);

		List<RecruitPost> list = recruitmentDao.getAllRecruitPost();
		Map<String, RecruitPost> map = new HashMap<String, RecruitPost>();
		for (RecruitPost model : list) {
			map.put(model.getRecruitpostguid(), model);
		}
		return map;
	}

	/**
	 * 职级
	 * 
	 * @return
	 */
	public Map<String, String> getAllRankMap() {
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		List<Rank> list = companyDao.getAllRank(Constance.VALID_NO);
		Map<String, String> map = new HashMap<String, String>();
		for (Rank model : list) {
			map.put(model.getRankid(), model.getLevelname());
		}
		return map;
	}

	/**
	 * 职级
	 * 
	 * @return
	 */
	public Map<String, String> getAllQuotaBudgettypeMap() {
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		List<Budgetype> list = companyDao.getAllQuotaBudgettype();
		Map<String, String> map = new HashMap<String, String>();
		for (Budgetype model : list) {
			map.put(model.getBudgettypeid(), model.getBudgettypename());
		}
		return map;
	}

	/**
	 * 崗位
	 * 
	 * @return
	 */
	public Map<String, Post> getAllPostMap() {
		PostDao postDao = sqlSession.getMapper(PostDao.class);

		List<Post> list = postDao.getAllPost(Constance.VALID_NO);
		Map<String, Post> map = new HashMap<String, Post>();
		for (Post model : list) {
			map.put(model.getPostid(), model);
		}
		return map;
	}

	/**
	 * 招聘计划
	 * 
	 * @return
	 */
	public Map<String, RecruitProgram> getAllRecruitProgramMap() {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);

		List<RecruitProgram> list = mapper.getAllRecruitprogram(Constance.State_Release);
		Map<String, RecruitProgram> map = new HashMap<String, RecruitProgram>();
		for (RecruitProgram model : list) {
			map.put(model.getRecruitprogramguid(), model);
		}
		return map;
	}
	
	
	/**
	 * 編制
	 * 
	 * @return
	 */
	public Map<String, String> getAllQuotaMap() {
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		// 取數據
		List<Quota> list = quotaDao.getAllQuota(Constance.VALID_NO);

		if (list.size() > Constance.ConvertCodeNum) {
			// 编制类型转型
			Map<String, String> budgettypemap = getAllQuotaBudgettypeMap();
			for (Quota model : list) {
				model.convertManyBudgettype(budgettypemap);
			}
		} else {
			for (Quota model : list) {
				model.convertBudgettype(companyDao);
			}
		}

		Map<String, String> map = new HashMap<String, String>();
		for (Quota model : list) {
			map.put(model.getQuotaid(), model.getBudgettypename() + "(" + model.getBudgetnumber() + ")");
		}
		return map;
	}

	/**
	 * 选项
	 * 
	 * @param code
	 * @return
	 */
	public Map<Integer, String> getOptionMapByTypeCode(String code) {
		OptionDao mapper = sqlSession.getMapper(OptionDao.class);

		// 类型
		OptionType type = mapper.getOptionTypeByCode(code);
		if (type == null)
			return null;

		// 选项
		List<OptionList> list = mapper.getOptionListByOptionTypeId(type.getOptiontypeguid());
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (OptionList model : list) {
			map.put(model.getCode(), model.getName());
		}
		return map;
	}

	/**
	 * 角色
	 * 
	 * @return
	 */
	public Map<String, String> getAllRoleMap() {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);

		List<UserRole> list = mapper.getAllUserRole();

		Map<String, String> map = new HashMap<String, String>();
		for (UserRole model : list) {
			String tmp = map.get(model.getUserguid());
			if (tmp == null) {
				tmp = model.getRolename();
				map.put(model.getUserguid(), tmp);
			} else {
				tmp += ("," + model.getRolename());
				map.put(model.getUserguid(), tmp);
			}
		}
		return map;
	}

	/**
	 * 工作地点
	 * 
	 * @return
	 */
	public Map<String, String> getAllWorkPlaceMap() {
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		List<WorkPlace> list = recruitmentDao.getAllWorkPlace(Constance.VALID_NO);
		Map<String, String> map = new HashMap<String, String>();
		for (WorkPlace model : list) {
			map.put(model.getWorkplaceguid(), model.getWorkplacename());
		}
		return map;
	}

	/**
	 * 职位类别
	 * 
	 * @return
	 */
	public Map<String, String> getAllCategoryMap() {
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		List<Category> list = recruitmentDao.getAllCategory(Constance.VALID_NO);
		Map<String, String> map = new HashMap<String, String>();
		for (Category model : list) {
			map.put(model.getCategoryguid(), model.getCategoryname());
		}
		return map;
	}

	/**
	 * 得到全部外网
	 * 
	 * @return
	 */
	public Map<String, String> getAllWebUserMap() {
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		List<WebUser> list = webUserDao.getAllWebUser();
		Map<String, String> map = new HashMap<String, String>();
		for (WebUser model : list) {
			map.put(model.getWebuserguid(), model.getUsername());
		}
		return map;
	}

	/**
	 * 得到所有员工
	 * 
	 * @return
	 */
	public Map<String, String> getAllEmployeeMap() {
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		List<Employee> list=employeeDao.getAllEmployee();
		Map<String, String> map = new HashMap<String, String>();
		for (Employee model : list) {
			map.put(model.getEmployeeid(), model.getName());
		}
		return map;
	}
	
	/**
	 * 得到所有用户
	 * 
	 * @return
	 */
	public Map<String, String> getAllUserMap() {
		SystemDao userDao = sqlSession.getMapper(SystemDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		List<User> list = userDao.getAllUser();
		Map<String, String> map = new HashMap<String, String>();
		for (User model : list) {
			model.convertOneCodeToName(employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
			map.put(model.getUserguid(), model.getUsername());
		}
		return map;
	}


	/**
	 * 体检机构
	 * 
	 * @return
	 */
	public Map<String, String> getAllThirdpartnerMap() {
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);

		List<ThirdPartner> list = thirdPartnerDao.getAllThirdPartner();
		Map<String, String> map = new HashMap<String, String>();
		for (ThirdPartner model : list) {
			map.put(model.getThirdpartnerguid(), model.getThirdpartnername());
		}
		return map;
	}
}
