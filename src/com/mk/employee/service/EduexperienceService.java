package com.mk.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Eduexperience;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.system.dao.OptionDao;

@Service
public class EduexperienceService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CodeConvertNameService codeConvertNameService;
	
	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchEduexperience(GridServerHandler grid) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countEduexperience(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<Eduexperience> list = mapper.searchEduexperience(grid);
		// 批量
		if (list.size() > Constance.ConvertCodeNum) {
			// 学习形式
			Map<Integer, String> learningtypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.LEARNINGTYPE);
			// 學歷情況
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			for (Eduexperience model : list) {
				model.convertManyCodeToName(learningtypeMap,cultureMap);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}else{
			for (Eduexperience model : list) {
				model.convertOneCodeToName(optionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		
		
		grid.setData(data);
	}

	/**
	 * 保存
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateEduexperience(Eduexperience model) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		if (StringUtils.isEmpty(model.getEduexperienceid())) {
			model.setEduexperienceid(UUIDGenerator.randomUUID());
			mapper.insertEduexperience(model);
		} else {
			mapper.updateEduexperience(model);
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Eduexperience getEduexperienceById(String id) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		Eduexperience model = mapper.getEduexperienceById(id);
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
	public void delEduexperienceById(String ids) {
		String[] obj = ids.split(",");
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		for (String id : obj) {
			mapper.delEduexperienceById(id);
		}
	}

}
