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
import com.mk.resume.entity.TrainingExperience;

@Service
public class TrainingExperienceService {
	
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 保存以及修改
	 * 
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateTrainingExperience(TrainingExperience model) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		if (StringUtils.isEmpty(model.getTrainingexperienceguid())) {
			model.setTrainingexperienceguid(UUIDGenerator.randomUUID());
			model.setModtime(new Timestamp(System.currentTimeMillis()));
			mapper.insertTrainingExperience(model);
		} else {
			mapper.updateTrainingExperience(model);
		}
	}

	
	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delTrainingExperienceById(String ids) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delTrainingExperienceById(id);
		}
	}


	/**
	 * 得到
	 * 
	 * @return
	 */
	public List<TrainingExperience> getTrainingExperienceListByResumeGuid(String resumeguid) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		return mapper.getAllTrainingExperienceByWebuserId(resumeguid);
	}

}
