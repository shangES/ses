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
import com.mk.resume.entity.ProjectExperience;

@Service
public class ProjectExperienceService {
	
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 保存以及修改
	 * 
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateProjectExperience(ProjectExperience model) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		if (StringUtils.isEmpty(model.getProjectexperienceguid())) {
			model.setProjectexperienceguid(UUIDGenerator.randomUUID());
			model.setModtime(new Timestamp(System.currentTimeMillis()));
			mapper.insertProjectExperience(model);
		} else {
			mapper.updateProjectExperience(model);
		}
	}

	
	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delProjectExperienceById(String ids) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delProjectExperienceById(id);
		}
	}


	/**
	 * 得到
	 * 
	 * @return
	 */
	public List<ProjectExperience> getProjectExperienceListByResumeGuid(String resumeguid) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		return mapper.getAllProjectExperienceByWebuserId(resumeguid);
	}

}
