package com.mk.employee.service;

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
import com.mk.employee.entity.Vacation;
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
import com.mk.quota.dao.QuotaDao;
import com.mk.system.dao.OptionDao;

@Service
public class VacationService {

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private CodeConvertNameService codeConvertNameService;

	/**
	 * 得到
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchVacation(GridServerHandler grid) {
		OptionDao option = sqlSession.getMapper(OptionDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		EmployeeDao emDao = sqlSession.getMapper(EmployeeDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = emDao.countVacation(grid);
		PageUtils.setTotalRows(grid, count);
		ArrayList<JSONObject> data = new ArrayList<JSONObject>();

		// 得打list
		List<Vacation> list = emDao.searchVacation(grid);
		if (list.size() > Constance.ConvertCodeNum) {
			for (Vacation model : list) {
				// 请假类型
				Map<Integer, String> vacationtypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.VACATIONTYPE);
				// 公司
				Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
				// 部門
				Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
				// 職務
				Map<String, String> jobMap = codeConvertNameService.getAllJobMap();
				// 職級
				Map<String, String> rankMap = codeConvertNameService.getAllRankMap();
				// 崗位
				Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
				// 編制
				Map<String, String> quotaMap = codeConvertNameService.getAllQuotaMap();
				model.convertManyCodeToName(vacationtypeMap, companyMap, deptMap, jobMap,rankMap, postMap, quotaMap, emDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (Vacation model : list) {
				model.convertOneCodeToName(option, emDao, companyDao, departmentDao, postDao,quotaDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}

	/**
	 * 保存or修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateVacation(Vacation model) {
		EmployeeDao emDao = sqlSession.getMapper(EmployeeDao.class);
		UserContext uc = ContextFacade.getUserContext();
		if (StringUtils.isEmpty(model.getVacationid())) {
			model.setVacationid(UUIDGenerator.randomUUID());
			model.setModiuser(uc.getUsername());
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			emDao.insertVacation(model);
		} else {
			emDao.updateVacation(model);
		}
	}

	/**
	 * 批量保存
	 * 
	 * @param grid
	 * @return
	 */
	@Transactional
	public String saveVacationGrid(GridServerHandler grid) {
		EmployeeDao emDao = sqlSession.getMapper(EmployeeDao.class);
		UserContext uc = ContextFacade.getUserContext();
		// 添加的行
		List<Object> addList = grid.getInsertedRecords(Vacation.class);
		if (!addList.isEmpty()) {
			for (Object obj : addList) {
				Vacation model = (Vacation) obj;
				model.setVacationid(UUIDGenerator.randomUUID());
				model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				model.setModiuser(uc.getUsername());
				emDao.insertVacation(model);
			}
		}

		if (addList.size() > 0)
			return "共 " + addList.size() + " 条数据，成功导入！";
		return null;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delVacation(String ids) {
		EmployeeDao emDao = sqlSession.getMapper(EmployeeDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			emDao.delVacation(id);
		}
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Vacation getVacationById(String id) {
		EmployeeDao emDao = sqlSession.getMapper(EmployeeDao.class);
		OptionDao option = sqlSession.getMapper(OptionDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		
		
		Vacation model = emDao.getVacationById(id);
		model.convertOneCodeToName(option, emDao, companyDao, departmentDao, postDao,quotaDao);
		return model;
	}
}
