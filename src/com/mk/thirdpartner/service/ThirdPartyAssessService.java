package com.mk.thirdpartner.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.recruitment.entity.WebUser;
import com.mk.recruitment.service.WebUserService;
import com.mk.system.dao.OptionDao;
import com.mk.thirdpartner.dao.ThirdPartnerDao;
import com.mk.thirdpartner.entity.ThirdPartner;
import com.mk.thirdpartner.entity.ThirdPartyAssess;
import com.mk.thirdpartner.tree.ThirdPartnerTree;

@Service
public class ThirdPartyAssessService {

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private WebUserService webUserService;

	/**
	 * 保存
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpThirdPartyAssess(ThirdPartyAssess model) {
		ThirdPartnerDao mapper = sqlSession.getMapper(ThirdPartnerDao.class);
		model.setThirdpartyassessdate(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isEmpty(model.getThirdpartyassessguid())) {
			model.setThirdpartyassessguid(UUIDGenerator.randomUUID());
			mapper.insertThirdPartyAssess(model);
		} else {
			mapper.updateThirdPartyAssess(model);
		}
	}

	/**
	 * 删除
	 * 
	 * @param model
	 */
	@Transactional
	public void delThirdPartyAssessById(String guid) {
		ThirdPartnerDao mapper = sqlSession.getMapper(ThirdPartnerDao.class);
		String[] obj = guid.split(",");
		for (String thirdpartyassessguid : obj) {
			mapper.delThirdPartyAssessById(thirdpartyassessguid);
		}
	}

	/**
	 * 得到
	 * 
	 * @param guid
	 */
	public ThirdPartyAssess getThirdPartyAssessById(String thirdpartyassessguid) {
		ThirdPartnerDao mapper = sqlSession.getMapper(ThirdPartnerDao.class);
		OptionDao option = sqlSession.getMapper(OptionDao.class);
		ThirdPartyAssess model = mapper.getThirdPartyAssessById(thirdpartyassessguid);
		if(model!=null){
			model.convertCodeToName(option);
		}
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param grid
	 */
	public void getAllThirdPartyAssess(GridServerHandler grid) {
		ThirdPartnerDao mapper = sqlSession.getMapper(ThirdPartnerDao.class);
		OptionDao option = sqlSession.getMapper(OptionDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		Integer count = mapper.countThirdPartyAssess(grid);
		
		PageUtils.setTotalRows(grid, count);

		List<ThirdPartyAssess> list = mapper.getAllThirdPartyAssess(grid);
		for (ThirdPartyAssess model : list) {
			model.convertCodeToName(option);
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}

	/**
	 * 等级通过
	 * 
	 * @param guid
	 */
	@Transactional
	public void setThirdPartyAssessLevel(String thirdpartyassessguid) {
		ThirdPartnerDao mapper = sqlSession.getMapper(ThirdPartnerDao.class);
		String[] obj = thirdpartyassessguid.split(",");
		for (String guid : obj) {
			if (StringUtils.isNotEmpty(guid)) {
				mapper.setThirdPartyAssessLevel(guid);
			}
		}
	}

	/********************** 机构用户 *********************************/

	public List<TreeNode> buildThirdPartnerWebUserCheckTree() {
		ThirdPartnerDao mapper = sqlSession.getMapper(ThirdPartnerDao.class);
		List<ThirdPartner> list = mapper.getAllThirdPartner();
		List<WebUser> webList = webUserService.getAllWebUser();
		ThirdPartnerTree tree = new ThirdPartnerTree();
		return tree.doThirdPartnerWebUserBuild(list, webList);
	}

	/**
	 * 检验年度
	 * 
	 * @param num
	 * @return
	 */
	public String checkYear(Integer num,String thirdpartyassessguid,String thirdpartnerguid){
		ThirdPartnerDao mapper = sqlSession.getMapper(ThirdPartnerDao.class);
		StringBuffer buffer = new StringBuffer();
		ThirdPartyAssess model = mapper.checkYear(num,thirdpartnerguid,thirdpartyassessguid);
		if(model != null){
			buffer.append("年度[");
			buffer.append(model.getYear());
			buffer.append("]已存在！");
			return buffer.toString();
		}
		return buffer.toString();
	}
}
