package com.mk.resume.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.EducationExperience;
import com.mk.system.dao.OptionDao;

@Service
public class EducationExperienceService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 保存以及修改
	 * 
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateEducationExperience(EducationExperience model) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		if (StringUtils.isEmpty(model.getEducationexperienceguid())) {
			model.setEducationexperienceguid(UUIDGenerator.randomUUID());
			model.setModtime(new Timestamp(System.currentTimeMillis()));
			mapper.insertEducationExperience(model);
		} else {
			mapper.updateEducationExperience(model);
		}
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delEducationExperienceById(String ids) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delEducationExperienceById(id);
		}
	}

	/**
	 * 得到
	 * 
	 * @return
	 */
	public List<EducationExperience> getEducationExperienceListByResumeGuid(String resumeguid) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		List<EducationExperience> list = mapper.getAllEducationExperienceByWebuserId(resumeguid);
		for (EducationExperience model : list) {
			model.convertOneCodeToName(optionDao);
		}

		return list;
	}

}
