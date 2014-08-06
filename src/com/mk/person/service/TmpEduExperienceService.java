package com.mk.person.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.person.dao.PersonDao;
import com.mk.person.entity.TmpEduExperience;
import com.mk.system.dao.OptionDao;

@Service
public class TmpEduExperienceService {

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 保存或修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateTmpEduExperience(TmpEduExperience model) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		if (StringUtils.isEmpty(model.getEduexperienceid())) {
			model.setEduexperienceid(UUIDGenerator.randomUUID());
			mapper.insertTmpEduExperience(model);
		} else {
			mapper.updateTmpEduExperience(model);
		}

	}

	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	public List<TmpEduExperience> getTmpEduExperienceListByMycandidateGuid(String mycandidatesguid) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		List<TmpEduExperience> list = mapper.getAllTmpEduExperienceListById(mycandidatesguid);
		if(!list.isEmpty()){
			for(TmpEduExperience edu:list){
				edu.convertOneCodeToName(optionDao);
			}
			return list;
		}
		return null;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delTmpEduExperienceById(String ids) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delTmpEduExperienceById(id);
		}
	}

}
