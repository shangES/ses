package com.mk.thirdpartner.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.recruitment.dao.WebUserDao;
import com.mk.system.dao.OptionDao;
import com.mk.thirdpartner.dao.ThirdPartnerDao;
import com.mk.thirdpartner.entity.ThirdPartner;
import com.mk.thirdpartner.tree.ThirdPartnerTree;

@Service
public class ThirdPartnerService {

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 修改 保存
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateThirdPartner(ThirdPartner model) {
		ThirdPartnerDao mapper = sqlSession.getMapper(ThirdPartnerDao.class);
		model.setModtime(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isEmpty(model.getThirdpartnerguid())) {
			model.setThirdpartnerguid(UUIDGenerator.randomUUID());
			mapper.insertThirdPartner(model);
		} else {
			mapper.updateThirdPartner(model);
		}
	}

	/**
	 * 得到
	 * 
	 * @param guid
	 * @return
	 */
	public ThirdPartner getThirdPartnerById(String guid) {
		ThirdPartnerDao mapper = sqlSession.getMapper(ThirdPartnerDao.class);
		OptionDao option = sqlSession.getMapper(OptionDao.class);
		ThirdPartner model = mapper.getThirdPartnerById(guid);
		if (model != null) {
			model.convertCodeToName(option);
		}
		return model;
	}

	/**
	 * 机构树
	 * 
	 * @return
	 */
	public List<TreeNode> buildThirdPartnerCheckTree() {
		ThirdPartnerDao mapper = sqlSession.getMapper(ThirdPartnerDao.class);
		List<ThirdPartner> list = mapper.getAllThirdPartner();
		ThirdPartnerTree tree = new ThirdPartnerTree();
		return tree.doBuild(list);
	}

	/**
	 * 删除
	 * 
	 * @param guid
	 */
	@Transactional
	public void delThirdPartnerById(String guid) {
		ThirdPartnerDao mapper = sqlSession.getMapper(ThirdPartnerDao.class);
		WebUserDao webMapper = sqlSession.getMapper(WebUserDao.class);
		webMapper.delWebUserByThirdpartnerId(guid);
		mapper.delThirdPartyAssessByThirdPartnerGuid(guid);
		mapper.delThirdPartnerById(guid);
	}

	/**
	 * 体检机构树
	 * 
	 * @return
	 */

	public List<TreeNode> buildPartnerCheckTree() {
		ThirdPartnerDao mapper = sqlSession.getMapper(ThirdPartnerDao.class);
		List<ThirdPartner> list = mapper.getAllPartner();
		ThirdPartnerTree tree = new ThirdPartnerTree();
		return tree.doBuildPartner(list);
	}

}
