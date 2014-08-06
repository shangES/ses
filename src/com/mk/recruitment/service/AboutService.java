package com.mk.recruitment.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.recruitment.dao.RecruitmentDao;
import com.mk.recruitment.entity.About;
import com.mk.recruitment.entity.AboutContent;

@Service
public class AboutService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 所有华数模块
	 * 
	 * @return
	 */
	public List<About> getAllAbout() {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		List<About> list = mapper.getAllAbout();
		return list;
	}

	/**
	 * 得到关于华数的模块
	 * 
	 * @param id
	 * @return
	 */
	public About getAboutById(String id) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		About model = mapper.getAboutById(id);
		if (model != null) {
			AboutContent content = mapper.getAboutContentById(id);
			if (content != null)
				model.setAboutcontent(content.getAboutcontent());
		}
		return model;
	}

	/**
	 * 保存
	 * 
	 * @param model
	 */
	public void saveOrUpdateAboutContent(AboutContent model) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		//先删后插
		mapper.delAboutContentById(model.getAboutguid());
		mapper.insertAboutContent(model);
	}

	/**
	 * 得到关于华数的正文
	 * 
	 * @param id
	 * @return
	 */
	public AboutContent getAboutContentById(String id) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		AboutContent model = mapper.getAboutContentById(id);
		if (model != null) {
			return model;
		}
		return null;
	}

}
