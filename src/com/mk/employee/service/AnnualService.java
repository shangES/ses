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
import com.mk.employee.entity.Annual;
import com.mk.employee.entity.Vacation;
import com.mk.framework.constance.Constance;
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
public class AnnualService {

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
	public void searchAnnual(GridServerHandler grid) {
		OptionDao option = sqlSession.getMapper(OptionDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		EmployeeDao emDao = sqlSession.getMapper(EmployeeDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = emDao.countAnnual(grid);
		PageUtils.setTotalRows(grid, count);
		ArrayList<JSONObject> data = new ArrayList<JSONObject>();

		// 得到
		List<Annual> list = emDao.searchAnnual(grid);
		if (list.size() > Constance.ConvertCodeNum) {
			for (Annual model : list) {
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
				model.convertManyCodeToName(companyMap, deptMap, jobMap,rankMap, postMap, quotaMap, emDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (Annual model : list) {
				model.convertOneCodeToName(option,emDao, companyDao, departmentDao, postDao,quotaDao);
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
	public String saveAnnualGrid(GridServerHandler grid) {
		EmployeeDao emDao = sqlSession.getMapper(EmployeeDao.class);
		UserContext uc = ContextFacade.getUserContext();
		// 添加的行
		List<Object> addList = grid.getInsertedRecords(Annual.class);
		if (!addList.isEmpty()) {
			for (Object obj : addList) {
				Annual model = (Annual) obj;
				model.setAnnualguid(UUIDGenerator.randomUUID());
				model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				model.setModiuser(uc.getUsername());
				emDao.insertAnnual(model);
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
	public void saveOrUpdateAnnual(Annual model) {
		EmployeeDao emDao = sqlSession.getMapper(EmployeeDao.class);
		UserContext uc = ContextFacade.getUserContext();
		if (StringUtils.isEmpty(model.getAnnualguid())) {
			model.setAnnualguid(UUIDGenerator.randomUUID());
			model.setModiuser(uc.getUsername());
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			emDao.insertAnnual(model);
		} else {
			emDao.updateAnnual(model);
		}
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delAnnual(String ids) {
		EmployeeDao emDao = sqlSession.getMapper(EmployeeDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			emDao.delAnnualById(id);
		}
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Annual getAnnualById(String id) {
		EmployeeDao emDao = sqlSession.getMapper(EmployeeDao.class);
		OptionDao option = sqlSession.getMapper(OptionDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		
		
		Annual model = emDao.getAnnualById(id);
		if(model!=null){
			model.convertOneCodeToName(option,emDao, companyDao, departmentDao, postDao,quotaDao);
		}
		
		return model;
	}
	
	/**
	 * 得到
	 * 
	 * @param id
	 * @param year
	 * @return
	 */
	public Annual getAnnualByEmployeeIdAndYear(String id,Integer year,String annualguid) {
		EmployeeDao emDao = sqlSession.getMapper(EmployeeDao.class);
		return emDao.getAnnualByEmployeeIdAndYear(id,year,annualguid);
	}
	
}
