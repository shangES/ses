package com.mk.recruitment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.tree.TreePageGrid;
import com.mk.recruitment.entity.WebUser;

public interface WebUserDao {

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertWebUser(WebUser model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateWebUser(WebUser model);

	/**
	 * 所有
	 * 
	 * @return
	 */
	List<WebUser> getAllWebUser();

	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countWebUser(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<WebUser> searchWebUser(GridServerHandler grid);

	/**
	 * 查询
	 * 
	 * @param Id
	 * @return
	 */
	WebUser getWebUserById(String webuserguid);

	/**
	 * 删除
	 * 
	 * @param Id
	 */
	void delWebUserById(String webuserguid);

	/**
	 * 删除
	 * 
	 * @param guid
	 */
	void delWebUserByThirdpartnerId(String thirdpartnerguid);

	/**
	 * 得到
	 * 
	 * @param name
	 * @return
	 */
	WebUser checkWebUserByEmail(@Param(value = "webuserguid") String webuserguid, @Param(value = "email") String email);

	/**
	 * 邮箱统计
	 * 
	 * @param grid
	 * @return
	 */
	int countEmailTree(TreePageGrid grid);

	/**
	 * 邮箱搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<WebUser> searchEmailTree(TreePageGrid grid);
}
