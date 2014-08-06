package com.mk.person.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.person.dao.PersonDao;
import com.mk.person.entity.TmpProjectExperience;

@Service
public class TmpProjectExperienceService {
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 保存或修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateTmpProjectExperience(TmpProjectExperience model) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		if (StringUtils.isEmpty(model.getProjectexperienceguid())) {
			model.setProjectexperienceguid(UUIDGenerator.randomUUID());
			model.setModtime(new Timestamp(System.currentTimeMillis()));
			mapper.insertTmpProjectExperience(model);
		} else {
			mapper.updateTmpProjectExperience(model);
		}

	}

	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	public List<TmpProjectExperience> getTmpProjectExperienceListByMycandidateGuid(String mycandidatesguid) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		List<TmpProjectExperience> list = mapper.getTmpProjectExperienceListByMycandidateGuid(mycandidatesguid);
		return list;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delTmpProjectExperienceById(String ids) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delTmpProjectExperienceById(id);
		}
	}
}
