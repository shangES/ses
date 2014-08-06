package com.mk.system.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.company.dao.CompanyDao;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.employee.dao.EmployeeDao;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.quota.dao.QuotaDao;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.User;

@Service
public class UserFilterService {
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	public void searchUserFilter(GridServerHandler grid) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		
		List<JSONObject> data = new ArrayList<JSONObject>();
		
		
		// 统计
		Integer count = mapper.countUserFilter(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<User> list = mapper.searchUserFilter(grid);
		for (User model : list) {
			model.convertOneCodeToName(employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}
	
	
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	public void delUserFilterByUserId( String ids) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delUserFilterByUserId(id);
		}
	}
}
