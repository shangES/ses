package com.mk.recruitment.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.recruitment.dao.RecruitmentDao;
import com.mk.recruitment.entity.News;
import com.mk.recruitment.entity.NewsContent;
import com.mk.recruitment.entity.NewsModule;

@Service
public class NewsService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 咨询模块
	 * 
	 * @return
	 */
	public List<NewsModule> getAllNewsModule() {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		List<NewsModule> list = mapper.getAllNewsModule();
		return list;
	}

	/**
	 * 咨询模块
	 * 
	 * @param id
	 * @return
	 */
	public NewsModule getNewsModuleById(String id) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		return mapper.getNewsModuleById(id);
	}

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchNews(GridServerHandler grid) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countNews(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<News> list = mapper.searchNews(grid);
		for (News model : list) {
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
	public void saveOrUpdateNews(News model) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		if (StringUtils.isEmpty(model.getNewsguid())) {
			// 发布时间
			model.setModtime(new Date(System.currentTimeMillis()));
			model.setNewsguid(UUIDGenerator.randomUUID());
			mapper.insertNews(model);
		} else {
			mapper.updateNews(model);
		}

		// 保存正文
		NewsContent newsContent=model.getNewscontent();
		mapper.delNewsContentById(model.getNewsguid());
		if (newsContent != null && StringUtils.isNotEmpty(newsContent.getNewscontent())) {
			newsContent.setNewsguid(model.getNewsguid());
			mapper.insertNewsContent(newsContent);
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public News getNewsById(String id) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		News model = mapper.getNewsById(id);
		if (model != null) {
			// 正文
			NewsContent newsContent = mapper.getNewsContentById(id);
			model.setNewscontent(newsContent);
		}
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	public void delNewsById(String ids) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delNewsContentById(id);
			mapper.delNewsById(id);
		}
	}

	/**
	 * 失效还原
	 * 
	 * @param ids
	 * @param state
	 */
	@Transactional
	public void validNewsById(String ids, Integer state) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			News model = mapper.getNewsById(id);
			if (model != null) {
				model.setValid(state);
				mapper.updateNews(model);
			}
		}

	}

	/**
	 * 审核发布
	 * 
	 * @param ids
	 */
	@Transactional
	public void auditNewsById(String ids,Integer state) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		UserContext uc = ContextFacade.getUserContext();
		String[] obj = ids.split(",");
		for (String id : obj) {
			News model = mapper.getNewsById(id);
			if (model != null) {
				model.setIsaudited(state);
				model.setAudituser(null);
				if(state==0)
					model.setAudituser(uc.getUserId());
				mapper.updateNews(model);
			}
		}

	}
}
