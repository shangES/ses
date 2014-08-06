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
import com.mk.recruitment.entity.WorkPlace;
import com.mk.recruitment.tree.WorkPlaceTree;

@Service
public class WorkPlaceService {
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 得到工作地点树
	 * 
	 * @return
	 */
	public List<TreeNode> buildAllWorkPlace() {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		List<WorkPlace> list = mapper.getAllWorkPlace(Constance.VALID_YES);
		if(list.size()==0){
			return null;
		}
		// 成树
		WorkPlaceTree tree = new WorkPlaceTree();
		return tree.dobuildAllWorkPlace(list);
	}
	
	
	

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchWorkPlace(GridServerHandler grid) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countWorkPlace(grid);
		PageUtils.setTotalRows(grid, count);
		List<WorkPlace> list = mapper.searchWorkPlace(grid);

		for (WorkPlace model : list) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}

		grid.setData(data);
	}

	/**
	 * 保存或修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateWorkPlace(WorkPlace model) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		// 修改时间
		model.setModtime(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isEmpty(model.getWorkplaceguid())) {
			model.setWorkplaceguid(UUIDGenerator.randomUUID());
			mapper.insertWorkPlace(model);
		} else {
			mapper.updateWorkPlace(model);
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public WorkPlace getWorkPlaceById(String id) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		WorkPlace model = mapper.getWorkPlaceById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	public void delWorkPlaceById(String ids) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delWorkPlaceById(id);
		}
	}

	/**
	 * 失效还原
	 * 
	 * @param ids
	 * @param state
	 */
	@Transactional
	public void validWorkPlaceById(String ids, Integer state) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			WorkPlace model = mapper.getWorkPlaceById(id);
			if (model != null) {
				model.setValid(state);
				mapper.updateWorkPlace(model);
			}
		}

	}

}
