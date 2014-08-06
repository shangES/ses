package com.mk.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.HrRecommend;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.system.dao.OptionDao;

@Service
public class HrRecommendService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchHrRecommend(GridServerHandler grid) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countHrRecommend(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<HrRecommend> list = mapper.searchHrRecommend(grid);
		for (HrRecommend model : list) {
			model.convertOneCodeToName(optionDao);
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}



	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delHrRecommendById(String ids) {
		String[] obj = ids.split(",");
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		for (String id : obj) {
			mapper.delHrRecommendById(id);
		}
	}
}
