package com.mk.person.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.person.dao.PersonDao;
import com.mk.person.entity.TmpFamily;
import com.mk.system.dao.OptionDao;

@Service
public class TmpFamilyService {
	@Autowired
	private SqlSession sqlSession;
	
	
	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	public List<TmpFamily> getTmpFamilyListByMycandidateGuid(String mycandidatesguid) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		List<TmpFamily> list = mapper.getTmpFamilyListByMycandidateGuid(mycandidatesguid);
		if(!list.isEmpty()){
			for(TmpFamily family:list){
				family.convertOneCodeToName(optionDao);
			}
			return list;
		}
		return null;
	}

	/**
	 * 保存或修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateTmpFamily(TmpFamily model) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		if (StringUtils.isEmpty(model.getFamilyid())) {
			model.setFamilyid(UUIDGenerator.randomUUID());
			mapper.insertTmpFamily(model);
		} else {
			mapper.updateTmpFamily(model);
		}

	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delTmpFamilyById(String ids) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delTmpFamilyById(id);
		}

	}
}

