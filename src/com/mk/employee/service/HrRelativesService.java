package com.mk.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.HrRelatives;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.system.dao.OptionDao;

@Service
public class HrRelativesService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchHrRelatives(GridServerHandler grid) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		OptionDao optionDao=sqlSession.getMapper(OptionDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countHrRelatives(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<HrRelatives> list = mapper.searchHrRelatives(grid);
		for (HrRelatives model : list) {
			model.convertOneCodeToName(optionDao);
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
	public void saveOrUpdateRelatives(HrRelatives model) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		 String employeename =model.getEmployeename();
		if(StringUtils.isNotEmpty(employeename)&&employeename.indexOf("(")>0){
			model.setEmployeename(employeename.substring(0,employeename.indexOf("(")));
		}
		if (StringUtils.isEmpty(model.getRelativesguid())) {
			model.setRelativesguid(UUIDGenerator.randomUUID());
			mapper.insertHrRelatives(model);
		} else {
			mapper.updateHrRelatives(model);
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public HrRelatives getHrRelativesById(String id) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		OptionDao optionDao=sqlSession.getMapper(OptionDao.class);
		HrRelatives model = mapper.getHrRelativesById(id);
		if(model!=null){
			model.convertOneCodeToName(optionDao);
		}
		return model;
	}
	


	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delHrRelativesById(String ids) {
		String[] obj = ids.split(",");
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		for (String id : obj) {
			mapper.delHrRelativesById(id);
		}
	}
}
