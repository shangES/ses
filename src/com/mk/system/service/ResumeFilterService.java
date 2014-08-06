package com.mk.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.ResumeFilter;
import com.mk.system.entity.UserFilter;
import com.mk.system.entity.UserFilterPam;

@Service
public class ResumeFilterService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchResumeFilter(GridServerHandler grid) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();

		//取选择的用户
		String userguid = grid.getPageParameter("userid");

		//用户的筛选权限
		List<UserFilter> checkeds = mapper.getUserFilterByUserId(userguid);
		Map<String, List<UserFilter>> map = new HashMap<String, List<UserFilter>>();
		for (UserFilter filter : checkeds) {
			List<UserFilter> tmpList = map.get(filter.getFilterguid());
			if (tmpList == null) {
				tmpList = new ArrayList<UserFilter>();
				tmpList.add(filter);
				map.put(filter.getFilterguid(), tmpList);
			} else {
				tmpList.add(filter);
			}
		}

		// 搜索
		List<ResumeFilter> list = mapper.searchResumeFilter(grid);
		for (ResumeFilter model : list) {
			//有权限的打钩
			if (map.get(model.getFilterguid()) != null)
				model.setChecked(true);
			data.add(JSONUtils.Bean2JSONObject(model));
		}

		grid.setData(data);
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public ResumeFilter getResumeFilterById(String id) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		ResumeFilter model = mapper.getResumeFilterById(id);
		return model;
	}

	/**
	 * 保存、修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateFilter(ResumeFilter model) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		if (StringUtils.isEmpty(model.getFilterguid())) {
			model.setFilterguid(UUIDGenerator.randomUUID());
			mapper.insertResumeFilter(model);
		} else {
			mapper.updateResumeFilter(model);
		}
	}

	/**
	 * 保存赋权
	 * 
	 * @param data
	 */
	@Transactional
	public void saveUserFilter(UserFilterPam data) {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		mapper.delUserFilterByUserId(data.getUserguid());
		List<String> list = data.getList();

		if (list != null && !list.isEmpty()) {
			for (String filterguid : list) {
				UserFilter userFilter = new UserFilter();
				userFilter.setFilterguid(filterguid);
				userFilter.setUserguid(data.getUserguid());
				mapper.insertUserFilter(userFilter);
			}
		}
	}

}
