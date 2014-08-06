package com.mk.fuzhu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.OptionType;

@Component
public class NameConvertCodeService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private CodeConvertNameService codeConvertNameService;

	/**
	 * 有权限的公司
	 * 
	 * @return
	 */
	public Map<String, Company> getHasCompany() {
		List<Company> list = companyService.getHasCompanys(Constance.VALID_YES);

		Map<String, Company> map = new HashMap<String, Company>();
		for (Company model : list) {
			map.put(model.getCompanyname(), model);
		}
		return map;
	}

	/**
	 * 有权限的部门
	 * 
	 * @return
	 */
	public Map<String, Department> getHasDepartment() {
		List<Department> list = departmentService.getHasDepartment(Constance.VALID_YES);

		Map<String, Department> map = new HashMap<String, Department>();
		if (!list.isEmpty()) {
			// 缓存
			Map<String, Department> cachMap = new HashMap<String, Department>();
			for (Department model : list) {
				cachMap.put(model.getDeptid(), model);
			}

			// 转出全路径
			StringBuffer name = null;
			for (Department model : list) {
				name = new StringBuffer();
				topDeptPath(name, model.getPdeptid(), cachMap);
				name.append(model.getDeptname());
				map.put(model.getCompanyid() + "/" + name.toString(), model);
			}
		}
		return map;
	}

	private static void topDeptPath(StringBuffer name, String deptguid, Map<String, Department> map) {
		Department model = map.get(deptguid);
		if (model != null) {
			if (model.getPdeptid() != null) {
				topDeptPath(name, model.getPdeptid(), map);
			}
			name.append(model.getDeptname());
			name.append("/");
		}
	}

	/**
	 * 选项名称转code
	 * 
	 * @param typecode
	 * @param optionname
	 * @return
	 */
	public Integer getOptionCodeByTypeAndName(String typecode, String optionname) {
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		OptionList model = optionDao.getOptionListByTypeAndName(typecode, optionname);
		if (model != null)
			return model.getCode();
		return null;
	}

	/**
	 * 职务名称转code
	 * 
	 * @param companyid
	 * @param jobname
	 * @return
	 */
	public Job getJobByName(String companyid, String jobname) {
		if (StringUtils.isEmpty(companyid) || StringUtils.isEmpty(jobname))
			return null;

		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		List<Job> list = companyDao.getJobByName(companyid, jobname);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 职级名称转code
	 * 
	 * @param companyid
	 * @param jobname
	 * @return
	 */
	public Rank getRankByName(String companyid, String rankname) {
		if (StringUtils.isEmpty(companyid) || StringUtils.isEmpty(rankname))
			return null;

		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		List<Rank> list = companyDao.getRankByName(companyid, rankname);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 岗位名称转code
	 * 
	 * @param companyid
	 * @param deptid
	 * @param postname
	 * @return
	 */
	public Post getPostByName(String companyid, String deptid, String postname) {
		if (StringUtils.isEmpty(companyid) || StringUtils.isEmpty(deptid) || StringUtils.isEmpty(postname))
			return null;

		PostDao postDao = sqlSession.getMapper(PostDao.class);
		List<Post> list = postDao.getPostByPostName(companyid, deptid, postname);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 编制类别
	 * 
	 * @param companyid
	 * @param deptid
	 * @param postid
	 * @param quotaname
	 * @return
	 */
	public Quota getQuotaByName(String postid, String quotaname) {
		if (StringUtils.isEmpty(postid) || StringUtils.isEmpty(quotaname))
			return null;

		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		List<Quota> list = quotaDao.getQuotaByPostIdAndBudgettypename(postid,Constance.VALID_YES,quotaname);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 员工信息检验工号唯一
	 * 
	 * @param jobnumber
	 * @return
	 */
	public boolean checkEmployeeByJobnumber(String jobnumber) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);

		List<Employee> list = mapper.getEmployeeByJobnumber(null, jobnumber);
		if (!list.isEmpty())
			return true;
		return false;
	}

	/**
	 * 取员工信息
	 * 
	 * @param jobnumber
	 * @return
	 */
	public Employee getEmployeeByJobnumber(String jobnumber, String name) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		List<Employee> list = mapper.getEmployeeByJobnumber(null, jobnumber);
		for (Employee model : list) {
			//if (model.getName().equals(name)) {
				model.convertMakOneCodeToName(mapper, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
				return model;
			//}
		}
		return null;
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
			map.put(model.getCompanyid() + "/" + model.getJobname(), model.getJobid());
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
			map.put(model.getCompanyid() + "/" + model.getLevelname(), model.getRankid());
		}
		return map;
	}

	/**
	 * 崗位
	 * 
	 * @return
	 */
	public Map<String, String> getAllPostMap() {
		PostDao postDao = sqlSession.getMapper(PostDao.class);

		List<Post> list = postDao.getAllPost(Constance.VALID_NO);
		Map<String, String> map = new HashMap<String, String>();
		for (Post model : list) {
			map.put(model.getDeptid() + "/" + model.getPostname(), model.getPostid());
		}
		return map;
	}

	/**
	 * 編制
	 * 
	 * @return
	 */
	public Map<String, String> getAllQuotaMap() {
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		CompanyDao comDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao deptDao = sqlSession.getMapper(DepartmentDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);

		// 取數據
		List<Quota> list = quotaDao.getAllQuota(Constance.VALID_NO);

		if (list.size() > Constance.ConvertCodeNum) {
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 编制类型转型
			Map<String, String> budgettypemap = codeConvertNameService.getAllQuotaBudgettypeMap();

			for (Quota model : list) {
				model.convertManyCodeToName(quotaDao,companyMap, deptMap, postMap, budgettypemap);
			}
		} else {
			for (Quota model : list) {
				model.convertOneCodeToName(quotaDao, comDao, deptDao, postDao);
			}
		}

		Map<String, String> map = new HashMap<String, String>();
		for (Quota model : list) {
			map.put(model.getPostid() + "/" + model.getBudgettypename(), model.getQuotaid());
		}
		return map;
	}

	/**
	 * 选项
	 * 
	 * @param workstate
	 * @return
	 */
	public Map<String, Integer> getOptionMapByTypeCode(String typecode) {
		OptionDao mapper = sqlSession.getMapper(OptionDao.class);

		// 类型
		OptionType type = mapper.getOptionTypeByCode(typecode);
		if (type == null)
			return null;

		// 选项
		List<OptionList> list = mapper.getOptionListByOptionTypeId(type.getOptiontypeguid());
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (OptionList model : list) {
			map.put(model.getName(), model.getCode());
		}
		return map;
	}

	/**
	 * 编制
	 * 
	 * @return
	 */
	public Map<String, String> getAllQuotaBudgettypeMap() {
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		List<Budgetype> list = companyDao.getAllQuotaBudgettype();
		Map<String, String> map = new HashMap<String, String>();
		for (Budgetype model : list) {
			map.put(model.getCompanyid() + "/" + model.getBudgettypename(), model.getBudgettypeid());
		}
		return map;
	}

	/**
	 * 编制类别
	 * 
	 * @return
	 */
	public Budgetype getBudgettypeByName(String companyid, String budgettypename) {
		if (StringUtils.isEmpty(companyid) || StringUtils.isEmpty(budgettypename))
			return null;

		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		List<Budgetype> list = mapper.getBudgettypeByName(companyid, budgettypename);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}
