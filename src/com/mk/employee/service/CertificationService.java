package com.mk.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Certification;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.system.dao.OptionDao;

@Service
public class CertificationService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 得到所有的职称认证
	 * 
	 * @return
	 */
	public List<Certification> getAllCertification() {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		List<Certification> list = mapper.getAllCertification();
		return list;
	}

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchCertification(GridServerHandler grid) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countCertification(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<Certification> list = mapper.searchCertification(grid);
		for (Certification model : list) {
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
	public void saveOrUpdateCertification(Certification model) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		if (StringUtils.isEmpty(model.getCertificationid())) {
			model.setCertificationid(UUIDGenerator.randomUUID());
			mapper.insertCertification(model);
		} else {
			mapper.updateCertification(model);
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Certification getCertificationById(String id) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		Certification model = mapper.getCertificationById(id);
		if (model != null)
			model.convertOneCodeToName(optionDao);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delCertificationById(String ids) {
		String[] obj = ids.split(",");
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		for (String id : obj) {
			mapper.delCertificationById(id);
		}
	}

}
