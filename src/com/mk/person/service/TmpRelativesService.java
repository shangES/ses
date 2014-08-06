package com.mk.person.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.person.dao.PersonDao;
import com.mk.person.entity.TmpRelatives;

@Service
public class TmpRelativesService {
	@Autowired
	private SqlSession sqlSession;
	
	
	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	public List<TmpRelatives> getTmpRelativesListByMycandidateGuid(String mycandidatesguid) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		List<TmpRelatives> list = mapper.getTmpRelativesListByMycandidateGuid(mycandidatesguid);
		return list;
	}

	/**
	 * 保存或修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateTmpRelatives(TmpRelatives model) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		if (StringUtils.isEmpty(model.getRelativesguid())) {
			model.setRelativesguid(UUIDGenerator.randomUUID());
			mapper.insertTmpRelatives(model);
		} else {
			mapper.updateTmpRelatives(model);
		}

	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delTmpRelativesById(String ids) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delTmpRelativesById(id);
		}

	}
}
