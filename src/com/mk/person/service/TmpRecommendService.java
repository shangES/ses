package com.mk.person.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.person.dao.PersonDao;
import com.mk.person.entity.TmpRecommend;
import com.mk.system.dao.OptionDao;

@Service
public class TmpRecommendService {
	@Autowired
	private SqlSession sqlSession;
	
	
	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	public List<TmpRecommend> getTmpRecommendListByMycandidateGuid(String mycandidatesguid) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		List<TmpRecommend> list = mapper.getTmpRecommendListByMycandidateGuid(mycandidatesguid);
		if(!list.isEmpty()){
			for(TmpRecommend recommend:list){
				recommend.convertOneCodeToName(optionDao);
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
	public void saveOrUpdateTmpRecommend(TmpRecommend model) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		if (StringUtils.isEmpty(model.getRecommendguid())) {
			model.setRecommendguid(UUIDGenerator.randomUUID());
			mapper.insertTmpRecommend(model);
		} else {
			mapper.updateTmpRecommend(model);
		}

	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delTmpRecommendById(String ids) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delTmpRecommendById(id);
		}

	}
}
