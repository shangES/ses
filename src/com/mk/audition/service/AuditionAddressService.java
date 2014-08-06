package com.mk.audition.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.audition.dao.AuditionDao;
import com.mk.audition.entity.AuditionAddress;
import com.mk.framework.constance.Constance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.recruitment.tree.WorkPlaceTree;

@Service
public class AuditionAddressService {

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 新增 修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateAuditionAddress(AuditionAddress model) {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		if (StringUtils.isEmpty(model.getAuditionaddressguid())) {
			UserContext uc = ContextFacade.getUserContext();
			model.setAuditionaddressguid(UUIDGenerator.randomUUID());
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			model.setModiuser(uc.getLoginname());
			mapper.insertAuditionAddress(model);
		} else {
			mapper.updateAuditionAddress(model);
		}
	}

	/**
	 * 得到
	 * 
	 * @param guid
	 * @return
	 */
	public AuditionAddress getAuditionAddressById(String guid) {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		if (StringUtils.isNotEmpty(guid)) {
			return mapper.getAuditionAddressById(guid);
		}
		return null;
	}

	/**
	 * 得到
	 * 
	 * @param grid
	 * @return
	 */
	public void searchAuditionAddress(GridServerHandler grid) {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		Integer count = mapper.countAuditionAddress(grid);
		PageUtils.setTotalRows(grid, count);
		List<AuditionAddress> list = mapper.searchAuditionAddress(grid);
		for (AuditionAddress model : list) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}

	/**
	 * 删除
	 * 
	 * @param guid
	 */
	@Transactional
	public void delAuditionAddressById(String ids) {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		String[] id = ids.split(",");
		for (String guid : id) {
			mapper.delAuditionAddressById(guid);
		}
	}

	/**
	 * 失效与预定
	 * 
	 * @param ids
	 * @param state
	 */
	@Transactional
	public void validContractById(String ids, Integer state) {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		String[] id = ids.split(",");
		for (String guid : id) {
			AuditionAddress model = mapper.getAuditionAddressById(guid);
			if (model != null) {
				model.setState(state);
				mapper.updateAuditionAddress(model);
			}
		}
	}

	
	/**
	 * 
	 * 面试地点树
	 * 
	 * @return
	 */
	public List<TreeNode> buildAllAuditionAddress() {
		AuditionDao mapper = sqlSession.getMapper(AuditionDao.class);
		List<AuditionAddress> list=mapper.getAllAuditionAddress(Constance.VALID_YES);
		
		if(list.isEmpty()){
			return null;
		}
		// 成树
		WorkPlaceTree tree = new WorkPlaceTree();
		return tree.dobuildAllAuditionAddress(list);
	}
}
