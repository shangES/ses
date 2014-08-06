package com.mk.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Train;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;

@Service
public class TrainService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchTrain(GridServerHandler grid) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countTrain(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<Train> list = mapper.searchTrain(grid);
		for (Train model : list) {
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
	public void saveOrUpdateTrain(Train model) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		if (StringUtils.isEmpty(model.getTrainid())) {
			model.setTrainid(UUIDGenerator.randomUUID());
			mapper.insertTrain(model);
		} else {
			mapper.updateTrain(model);
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Train getTrainById(String id) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		Train model = mapper.getTrainById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delTrainById(String ids) {
		String[] obj = ids.split(",");
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		for (String id : obj) {
			mapper.delTrainById(id);
		}
	}

}
