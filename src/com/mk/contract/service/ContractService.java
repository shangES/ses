package com.mk.contract.service;

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
import com.mk.contract.dao.ContractDao;
import com.mk.contract.entity.Contract;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.quota.dao.QuotaDao;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.Syslog;
import com.mk.system.service.SyslogService;

@Service
public class ContractService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CodeConvertNameService codeConvertNameService;
	@Autowired
	private SyslogService syslogService;

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchContract(GridServerHandler grid) {
		ContractDao mapper = sqlSession.getMapper(ContractDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		EmployeeDao emDao = sqlSession.getMapper(EmployeeDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = mapper.countContract(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<Contract> list = mapper.searchContract(grid);
		if (list.size() > Constance.ConvertCodeNum) {
			Map<Integer, String> contracttypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CONTRACTTYPE);
			Map<Integer, String> hourssystemMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.HOURSSYSTEM);
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 職務
			Map<String, String> jobMap = codeConvertNameService.getAllJobMap();
			// 職級
			Map<String, String> rankMap = codeConvertNameService.getAllRankMap();
			// 編制
			Map<String, String> quotaMap = codeConvertNameService.getAllQuotaMap();
			for (Contract model : list) {
				model.convertManyCodeToName(companyMap, deptMap, postMap, jobMap, rankMap, quotaMap, contracttypeMap, hourssystemMap, emDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (Contract model : list) {
				model.convertOneCodeToName(optionDao, emDao, companyDao, departmentDao, postDao,quotaDao);
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
	public String saveContractGrid(GridServerHandler grid) {
		ContractDao mapper = sqlSession.getMapper(ContractDao.class);

		// 添加的行
		List<Object> addList = grid.getInsertedRecords(Contract.class);
		if (!addList.isEmpty()) {
			for (Object obj : addList) {
				Contract model = (Contract) obj;

				model.setContractid(UUIDGenerator.randomUUID());
				model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				mapper.insertContract(model);
			}

			// 系统日志
			Syslog log = new Syslog("合同信息批量" + Constance.imp + "(" + addList.size() + " 条数据)");
			syslogService.saveOrUpdateSyslog(log);
		}

		if (addList.size() > 0)
			return "共 " + addList.size() + " 条数据，成功导入！";
		return null;
	}

	/**
	 * 保存
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateContract(Contract model) {
		ContractDao mapper = sqlSession.getMapper(ContractDao.class);
		model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isEmpty(model.getContractid())) {
			model.setContractid(UUIDGenerator.randomUUID());
			mapper.insertContract(model);
		} else {
			mapper.updateContract(model);
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Contract getContractById(String id) {
		ContractDao mapper = sqlSession.getMapper(ContractDao.class);
		EmployeeDao emDao = sqlSession.getMapper(EmployeeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		
		
		Contract model = mapper.getContractById(id);
		if (model != null) {
			model.convertOneCodeToName(optionDao, emDao, companyDao, departmentDao, postDao,quotaDao);
		}
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delContractById(String ids) {
		String[] obj = ids.split(",");
		ContractDao mapper = sqlSession.getMapper(ContractDao.class);
		for (String id : obj) {
			mapper.delContractById(id);
		}
	}

	/**
	 * 失效/还原
	 * 
	 * @param ids
	 * @param state
	 */
	@Transactional
	public void validContractById(String ids, Integer state) {
		ContractDao mapper = sqlSession.getMapper(ContractDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			Contract model = mapper.getContractById(id);
			if (model != null) {
				model.setState(state);
				model.setEnddate(null);
				mapper.updateContract(model);
			}
		}
	}

	/**
	 * 终止合同
	 * 
	 * @param ids
	 * @param state
	 * @param enddate
	 * @param memo
	 */
	@Transactional
	public void endContractById(String ids, Integer state, Date enddate, String memo) {
		ContractDao mapper = sqlSession.getMapper(ContractDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			Contract model = mapper.getContractById(id);
			if (model != null) {
				model.setState(state);
				model.setEnddate(enddate);
				model.setMemo(memo);
				mapper.updateContract(model);
			}
		}
	}

}
