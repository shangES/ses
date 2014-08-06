package com.mk.company.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Budgetype;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;

@Service
public class BudgetypeService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	public void searchBudgetype(GridServerHandler grid) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countBudgetype(grid);
		PageUtils.setTotalRows(grid, count);

		List<Budgetype> list = mapper.searchBudgetype(grid);
		for (Budgetype budgetype : list) {
			data.add(JSONUtils.Bean2JSONObject(budgetype));
		}
		grid.setData(data);

	}

	/**
	 * 保存及修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateBudgetype(Budgetype model) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isEmpty(model.getBudgettypeid())) {
			model.setBudgettypeid(UUIDGenerator.randomUUID());
			mapper.insertBudgetype(model);
		} else {
			mapper.updateBudgetype(model);
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Budgetype getBudgetypeById(String id) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		return mapper.getBudgetypeById(id);
	}
	
	/**
	 * 得到公司下的编制
	 * 
	 * @param id
	 * @return
	 */
	public List<Budgetype> getBudgetypeByCompanyId(String companyid) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		List<Budgetype> list=mapper.getQuotaBudgettypeByCompanyId(companyid);
		return list;
	}
	
	

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delBudgetypeById(String ids) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delBudgetypeByBudgetypeId(id);
		}

	}

	/**
	 * 失效及恢复
	 * 
	 * @param ids
	 * @param valid
	 */
	@Transactional
	public void validBudgetypeById(String ids, Integer valid) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			Budgetype model = mapper.getBudgetypeById(id);
			model.setState(valid);
			mapper.updateBudgetype(model);
		}
	}
}
