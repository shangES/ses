package com.mk.person.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.person.dao.PersonDao;
import com.mk.person.entity.TmpWorkExperience;
import com.mk.system.dao.OptionDao;

@Service
public class TmpWorkExperienceService {

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 添加或修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateTmpWorkExperience(TmpWorkExperience model) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		if (StringUtils.isEmpty(model.getWorkexperienceid())) {
			model.setWorkexperienceid(UUIDGenerator.randomUUID());
			mapper.insertTmpWorkExperience(model);
		} else {
			mapper.updateTmpWorkExperience(model);
		}

	}

	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	public List<TmpWorkExperience> getTmpWorkExperienceListByMycandidateGuid(String mycandidatesguid) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		List<TmpWorkExperience> list = new ArrayList<TmpWorkExperience>();
		List<TmpWorkExperience> templist =  mapper.getAllTmpWorkExperienceById(mycandidatesguid);
		for(TmpWorkExperience model : templist){
			if(model != null)
				model.convertOneCodeToName(optionDao);
			list.add(model);
		}
		return list;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delTmpWorkExperienceById(String ids) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delTmpWorkExperienceById(id);
		}

	}

}
