package com.mk.quota.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.quota.dao.QuotaDao;
import com.mk.quota.entity.QuotaOperate;

@Service
public class QuotaOperateService {
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchQuotaOperate(GridServerHandler grid) {
		QuotaDao mapper = sqlSession.getMapper(QuotaDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();

		// 统计
		Integer count = mapper.countQuotaOperate(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<QuotaOperate> list = mapper.searchQuotaOperate(grid);
		
		for (QuotaOperate model : list) {
			//model.convertOneCodeToName(mapper,comDao, departmentDao, postdao);
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
	public void saveOrUpdateQuotaOperate(QuotaOperate model) {
		QuotaDao mapper = sqlSession.getMapper(QuotaDao.class);
		if (StringUtils.isEmpty(model.getQuotaoperateguid())) {
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			model.setQuotaoperateguid(UUIDGenerator.randomUUID());
			mapper.insertQuotaOperate(model);
		}
		
	}
	
	
	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delQuotaOperateById(String ids) {
		QuotaDao mapper = sqlSession.getMapper(QuotaDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delQuotaOperateById(id);
		}
	}
}
