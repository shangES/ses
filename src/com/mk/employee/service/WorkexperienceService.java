package com.mk.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Train;
import com.mk.employee.entity.Workexperience;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;

@Service
public class WorkexperienceService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchWorkexperience(GridServerHandler grid) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countWorkexperience(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<Workexperience> list = mapper.searchWorkexperience(grid);
		for (Workexperience model : list) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}

	/**
	 * 保存
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateWorkexperience(Workexperience model) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		if (StringUtils.isEmpty(model.getWorkexperienceid())) {
			model.setWorkexperienceid(UUIDGenerator.randomUUID());
			mapper.insertWorkexperience(model);
		} else {
			mapper.updateWorkexperience(model);
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Workexperience getWorkexperienceById(String id) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		Workexperience model = mapper.getWorkexperienceById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delWorkexperienceById(String ids) {
		String[] obj = ids.split(",");
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		for (String id : obj) {
			mapper.delWorkexperienceById(id);
		}
	}
}
