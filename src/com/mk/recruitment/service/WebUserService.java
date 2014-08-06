package com.mk.recruitment.service;

import java.sql.Date;
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
import com.mk.framework.tree.TreePageGrid;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.recruitment.dao.WebUserDao;
import com.mk.recruitment.entity.WebUser;

@Service
public class WebUserService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 保存
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateWebUser(WebUser model) {
		WebUserDao mapper = sqlSession.getMapper(WebUserDao.class);
		model.setModtime(new Date(System.currentTimeMillis()));
		if (StringUtils.isEmpty(model.getWebuserguid())) {
			model.setWebuserguid(UUIDGenerator.randomUUID());
			model.setCode(UUIDGenerator.randomUUID());
			mapper.insertWebUser(model);
		} else {
			mapper.updateWebUser(model);
		}
	}

	/**
	 * 所有
	 * 
	 * @return
	 */
	public List<WebUser> getAllWebUser() {
		WebUserDao mapper = sqlSession.getMapper(WebUserDao.class);
		return mapper.getAllWebUser();
	}

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchWebUser(GridServerHandler grid) {
		WebUserDao mapper = sqlSession.getMapper(WebUserDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();

		Integer count = mapper.countWebUser(grid);
		PageUtils.setTotalRows(grid, count);

		List<WebUser> list = mapper.searchWebUser(grid);
		for (WebUser moder : list) {
			data.add(JSONUtils.Bean2JSONObject(moder));
		}
		grid.setData(data);
	}

	/**
	 * 得到
	 * 
	 * @param Id
	 * @return
	 */
	public WebUser getWebUserById(String Id) {
		WebUserDao mapper = sqlSession.getMapper(WebUserDao.class);
		WebUser model = mapper.getWebUserById(Id);
		return model;
	}

	/**
	 * 失效
	 * 
	 * @param ids
	 * @param state
	 */
	@Transactional
	public void validWebUserById(String ids, Integer state) {
		WebUserDao mapper = sqlSession.getMapper(WebUserDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			WebUser model = mapper.getWebUserById(id);
			if (model != null) {
				model.setValid(state);
				mapper.updateWebUser(model);
			}
		}
	}

	/**
	 * 删除
	 * 
	 * @param Id
	 */
	@Transactional
	public void delWebUserById(String Ids) {
		WebUserDao mapper = sqlSession.getMapper(WebUserDao.class);
		String[] obj = Ids.split(",");
		for (String id : obj) {
			mapper.delWebUserById(id);
		}
	}

	/**
	 * 校验登录名
	 * 
	 * @param name
	 * @return
	 */
	public String checkWebUserByEmail(String webuserguid, String name) {
		WebUserDao mapper = sqlSession.getMapper(WebUserDao.class);
		StringBuffer buffer = new StringBuffer();
		if (StringUtils.isNotEmpty(name)) {
			WebUser user = mapper.checkWebUserByEmail(webuserguid, name);
			if (user != null) {
				buffer.append("邮件地址[");
				buffer.append(user.getEmail());
				buffer.append("]已被注册！");
				return buffer.toString();
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 删除
	 * 
	 * @param guid
	 */
	@Transactional
	public void delWebUserByThirdpartnerId(String thirdpartnerguid){
		WebUserDao mapper = sqlSession.getMapper(WebUserDao.class);
		mapper.delWebUserByThirdpartnerId(thirdpartnerguid);
	}
	
	
	/**
	 * 邮箱匹配
	 * 
	 * @param grid
	 * @return
	 */
	public List<WebUser> searchEmailTree(TreePageGrid grid) {
		WebUserDao mapper = sqlSession.getMapper(WebUserDao.class);

		// 统计
		Integer count = mapper.countEmailTree(grid);
		grid.getPage().setTotalrows(count);
		grid.getPage().init();
		// 分页
		List<WebUser> list = mapper.searchEmailTree(grid);
		return list;
	}
}
