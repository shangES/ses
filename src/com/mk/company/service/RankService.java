package com.mk.company.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.company.entity.Rank;
import com.mk.company.tree.RankTree;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.utils.UUIDGenerator;

@Service
public class RankService {
	@Autowired
	private SqlSession sqlSession;

	public List<TreeNode> buildRankTree(String companyid) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);

		// 公司信息
		Company company = mapper.getCompanyByCompanyId(companyid);
		if (company == null)
			return null;

		// 职级信息
		List<Rank> list = mapper.getRankByCompanyId(companyid);
		RankTree tree= new RankTree();
		return tree.doBulid(company, list);
	}
	
	
	
	
	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	public void searchRank(GridServerHandler grid) {
		CompanyDao mapper=sqlSession.getMapper(CompanyDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countRank(grid);
		PageUtils.setTotalRows(grid, count);
		
		List<Rank> list = mapper.searchRank(grid);
		for(Rank rank:list){
			data.add(JSONUtils.Bean2JSONObject(rank));
		}
		grid.setData(data);
	}

	/**
	 * 保存及修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateRank(Rank model) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isEmpty(model.getRankid())) {
			model.setRankid(UUIDGenerator.randomUUID());
			mapper.insertRank(model);
		}else{
			mapper.updateRank(model);
		}
		
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Rank getRankById(String id) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		return mapper.getRankById(id);
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delRankById(String ids) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delRankByRankId(id);
		}
	}

	/**
	 * 失效及恢复
	 * 
	 * @param ids
	 * @param valid
	 */
	@Transactional
	public void validRankById(String ids, Integer valid) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			Rank model = mapper.getRankById(id);
			model.setState(valid);
			mapper.updateRank(model);
		}
	}

}
