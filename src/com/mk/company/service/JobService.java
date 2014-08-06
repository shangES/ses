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
import com.mk.company.entity.Job;
import com.mk.company.tree.JobTree;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.utils.UUIDGenerator;

@Service
public class JobService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 加載樹
	 * 
	 * @param companyid
	 * @return
	 */
	public List<TreeNode> buildJobTree(String companyid) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);

		// 公司信息
		Company company = mapper.getCompanyByCompanyId(companyid);
		if (company == null)
			return null;

		// 職務信息
		List<Job> list = mapper.getJobByCompanyId(companyid);
		JobTree tree = new JobTree();
		return tree.doBulid(company, list);
	}

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	public void searchJob(GridServerHandler grid) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countJob(grid);
		PageUtils.setTotalRows(grid, count);

		List<Job> list = mapper.searchJob(grid);
		for (Job job : list) {
			data.add(JSONUtils.Bean2JSONObject(job));
		}
		grid.setData(data);
	}

	/**
	 * 保存及修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateJob(Job model) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isEmpty(model.getJobid())) {
			model.setJobid(UUIDGenerator.randomUUID());
			mapper.insertJob(model);
		} else {
			mapper.updateJob(model);
		}
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Job getJobById(String id) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		return mapper.getJobById(id);
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delJobById(String ids) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delJobByJobId(id);
		}
	}

	/**
	 * 失效及恢复
	 * 
	 * @param ids
	 * @param valid
	 */
	@Transactional
	public void validJobById(String ids, Integer valid) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			Job model = mapper.getJobById(id);
			model.setState(valid);
			mapper.updateJob(model);
		}
	}

}
