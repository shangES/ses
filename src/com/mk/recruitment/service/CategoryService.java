package com.mk.recruitment.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.constance.Constance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.recruitment.dao.RecruitmentDao;
import com.mk.recruitment.entity.Category;
import com.mk.recruitment.tree.CategoryTree;

@Service
public class CategoryService {
	@Autowired
	private SqlSession sqlSession;
	
	
	/**
	 * 得到职位类别树
	 * 
	 * @return
	 */
	public List<TreeNode> buildAllCategory() {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		List<Category> list = mapper.getAllCategory(Constance.VALID_YES);
		if(list.size()==0){
			return null;
		}
		// 成树
		CategoryTree tree = new CategoryTree();
		return tree.dobuildAllCategory(list);
	}
	
	
	
	
	
	

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchCategory(GridServerHandler grid) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countCategory(grid);
		PageUtils.setTotalRows(grid, count);
		List<Category> list = mapper.searchCategory(grid);
		// 批量
		if (list.size() > Constance.ConvertCodeNum) {
			for (Category model : list) {
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (Category model : list) {
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}

	/**
	 * 保存或修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateCategory(Category model) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		// 修改时间
		model.setModtime(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isEmpty(model.getCategoryguid())) {
			model.setCategoryguid(UUIDGenerator.randomUUID());
			mapper.insertCategory(model);
		} else {
			mapper.updateCategory(model);
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Category getCategoryById(String id) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		Category model = mapper.getCategoryById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	public void delCategoryById(String ids) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delCategoryById(id);
		}
	}

	/**
	 * 失效还原
	 * 
	 * @param ids
	 * @param state
	 */
	@Transactional
	public void validCategoryById(String ids, Integer state) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			Category model = mapper.getCategoryById(id);
			if (model != null) {
				model.setValid(state);
				mapper.updateCategory(model);
			}
		}

	}

}
