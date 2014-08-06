package com.mk.resume.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.addresslist.dao.AddressListDao;
import com.mk.company.dao.CompanyDao;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.employee.dao.EmployeeDao;
import com.mk.framework.constance.Constance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.quota.dao.QuotaDao;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.InteriorRecommend;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;

@Service
public class InteriorRecommendService {
	@Autowired
	private SqlSession sqlSession;
	
	
	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchRecommend(GridServerHandler grid) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		AddressListDao addressListDao = sqlSession.getMapper(AddressListDao.class);
		MyCandidatesDao myCandidatesDao=sqlSession.getMapper(MyCandidatesDao.class);
		
		
		List<JSONObject> data = new ArrayList<JSONObject>();

		// 统计
		Integer count = mapper.countRecommend(grid);
		PageUtils.setTotalRows(grid, count);

		// 權限組織
		Constance.initAdminPam(grid.getParameters());
		
		// 搜索
		List<InteriorRecommend> list = mapper.searchRecommend(grid);
		for (InteriorRecommend model : list) {
			model.convertOneCodeToName(departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, optionDao, addressListDao,myCandidatesDao,mapper);
			data.add(JSONUtils.Bean2JSONObject(model));
		}

		grid.setData(data);

	}
}
