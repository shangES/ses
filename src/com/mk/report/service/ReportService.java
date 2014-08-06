package com.mk.report.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.company.dao.CompanyDao;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.employee.dao.EmployeeDao;
import com.mk.framework.constance.Constance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.quota.dao.QuotaDao;
import com.mk.report.dao.ReportDao;
import com.mk.report.entity.PCD0300;
import com.mk.report.entity.PCD0301;
import com.mk.report.entity.PCD0306;
import com.mk.report.entity.PCD0307;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.todo.entity.TodoPam;

@Service
public class ReportService {
	@Autowired
	private SqlSessionFactoryBean sqlSessionFactory;
	@Autowired
	private CodeConvertNameService codeConvertNameService;

	/**
	 * PCD_0101 传媒花名册
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0101(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0101(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}

	/**
	 * PCD_0102 集团花名册
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0102(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0102(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}

	/**
	 * PCD_0103 新入职人员报表
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0103(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0103(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);
	}

	/**
	 * PCD_0104 离职人员报表
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0104(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0104(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}

	/**
	 * PCD_0105 异动人员报表
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0105(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0105(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}

	/**
	 * PCD_0107 员工生日报表
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0107(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0107(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}

	/**
	 * PCD_0111 工龄报表
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0111(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0111(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}

	/**
	 * PCD_0113 待转正员工报表
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0113(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0113(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}

	/**
	 * PCD_0201 员工类别统计表(分性别员工数量统计表)
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0201(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);
		mapper.searchPCD0201(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}

	/**
	 * PCD_0202 员工类别统计表(分学历员工数量统计表)
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0202(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);
		mapper.searchPCD0202(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}

	/**
	 * PCD_0203 员工类别统计表(分年龄段员工数量统计表)
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0203(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0203(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}

	/**
	 * PCD_0204 员工类别统计表(分工龄段员工数量统计表)
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0204(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0204(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}

	/**
	 * PCD_0205 员工类别统计表(分政治面貌员工数量统计表)
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0205(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0205(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}

	/**
	 * PCD_0206 员工类别统计表(分职称认证员工数量统计表)
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0206(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0206(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}

	/**
	 * PCD_0207 员工类别统计表(分职务员工数量统计表)
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0207(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0207(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}

	/**
	 * PCD_0208 员工类别统计表(分性别及婚姻状况员工数量统计表)
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD0208(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0208(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);
	}

	/**
	 * 招聘计划统计图
	 * 
	 * @param pam
	 * @return
	 * @throws Exception
	 */
	public List<PCD0300> searchPCD_0300(TodoPam pam) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);
		UserContext uc = ContextFacade.getUserContext();
		Map map = pam.getParameters();
		Constance.initAdminPam(map);
		map.put("companyid", uc.getCompanyid());
		mapper.searchPCD0300(pam);
		List<PCD0300> list = pam.getData();
		for (PCD0300 pcd0300 : list) {
			pcd0300.convertOneCodeToName();
		}
		return list;
	}

	/**
	 * PCD_0301 总体编制情况表
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD_0301(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);
		// 取编制类型
		Map<String, String> budgettypemap = codeConvertNameService.getAllQuotaBudgettypeMap();
		mapper.searchPCD0301(grid);

		int BUDGETNUMBER = 0;
		int EMPLOYEENUMBER = 0;
		int QBNUMBER = 0;
		int POSTNUM = 0;

		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			PCD0301 pcd0301 = (PCD0301) model;
			pcd0301.convertManyCodeToName(budgettypemap);
			data.add(JSONUtils.Bean2JSONObject(model));

			BUDGETNUMBER += pcd0301.getBUDGETNUMBER();
			QBNUMBER += pcd0301.getQBNUMBER();
			EMPLOYEENUMBER += pcd0301.getEMPLOYEENUMBER();
			POSTNUM += pcd0301.getPOSTNUM();
		}

		JSONObject obj = new JSONObject();
		obj.accumulate("ASSESSHIERARCHYNAME", "总计");
		obj.accumulate("BUDGETNUMBER", BUDGETNUMBER);
		obj.accumulate("EMPLOYEENUMBER", EMPLOYEENUMBER);
		obj.accumulate("QBNUMBER", QBNUMBER);
		obj.accumulate("POSTNUM", POSTNUM);
		data.add(obj);

		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

		session.close();
	}

	/**
	 * PCD_0302 增编统计表
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD_0302(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0302(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);
	}

	/**
	 * PCD_0303 招聘计划表
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD_0303(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0303(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);
	}

	/**
	 * PCD_0304 渠道统计表
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD_0304(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0304(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);
	}

	/**
	 * PCD_0305 招聘专员推进情况汇总表
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD_0305(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);

		mapper.searchPCD0305(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);
	}

	/**
	 * PCD_0306 岗位推进情况表
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD_0306(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);
		DepartmentDao departmentDao = session.getMapper(DepartmentDao.class);
		CompanyDao companyDao = session.getMapper(CompanyDao.class);
		SystemDao systemDao = session.getMapper(SystemDao.class);
		EmployeeDao employeeDao = session.getMapper(EmployeeDao.class);
		PostDao postDao = session.getMapper(PostDao.class);
		QuotaDao quotaDao = session.getMapper(QuotaDao.class);
		OptionDao optionDao = session.getMapper(OptionDao.class);

		mapper.searchPCD0306(grid);

		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			PCD0306 pcd0306 = (PCD0306) model;
			pcd0306.convertOneCodeToName(departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, optionDao);
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

		session.close();
	}

	/**
	 * PCD_0307 面试官面试情况表
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD_0307(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);
		DepartmentDao departmentDao = session.getMapper(DepartmentDao.class);
		CompanyDao companyDao = session.getMapper(CompanyDao.class);
		SystemDao systemDao = session.getMapper(SystemDao.class);
		EmployeeDao employeeDao = session.getMapper(EmployeeDao.class);
		PostDao postDao = session.getMapper(PostDao.class);
		QuotaDao quotaDao = session.getMapper(QuotaDao.class);
		OptionDao optionDao = session.getMapper(OptionDao.class);

		// // 公司
		// Map<String, Company> companyMap =
		// codeConvertNameService.getAllCompanyMap();
		// // 部門
		// Map<String, Department> deptMap =
		// codeConvertNameService.getAllDepartmentMap();
		// // 職務
		// Map<String, String> jobMap = codeConvertNameService.getAllJobMap();
		// // 職級
		// Map<String, String> rankMap = codeConvertNameService.getAllRankMap();
		// // 崗位
		// Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
		// // 編制
		// Map<String, String> quotaMap =
		// codeConvertNameService.getAllQuotaMap();

		mapper.searchPCD0307(grid);
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			PCD0307 pcd0307 = (PCD0307) model;
			System.out.println("==========" + pcd0307.getD1());
			// pcd0307.convertManyCodeToName(employeeDao, companyMap, deptMap,
			// jobMap, rankMap, postMap, quotaMap, systemDao);
			pcd0307.convertOneCodeToName(departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, optionDao);
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

		session.close();
	}
	
	
	/**
	 * PCD_0308  总体编制统计表
	 * 
	 * @param grid
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void searchPCD_0308(GridServerHandler grid) throws Exception {
		SqlSession session = sqlSessionFactory.getObject().openSession(false);
		session.getConnection().setAutoCommit(false);
		ReportDao mapper = session.getMapper(ReportDao.class);
		mapper.searchPCD0308(grid);
		session.close();
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Object model : grid.getData()) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		PageUtils.setTotalRows(grid, data.size());
		grid.setData(data);

	}
}
