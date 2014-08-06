package com.mk.department.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.department.entity.Post;
import com.mk.framework.grid.GridServerHandler;
import com.mk.quota.entity.Quota;

public interface PostDao {

	// ================岗位信息==================
	/**
	 * 全部
	 * 
	 * @param state
	 * @return
	 */
	List<Post> getAllPost(@Param(value = "state") Integer state);

	/**
	 * 通过部门id得到岗位信息
	 * 
	 * @param deptid
	 * @return
	 */
	List<Post> getPostByDeptid(String deptid);

	/**
	 * 加载部门以及子部门的岗位信息
	 * 
	 * @param deptid
	 * @return
	 */
	List<Post> getPostByDeptCode(@Param(value = "deptcode") String deptcode);

	/**
	 * 通过id统计岗位信息的数量
	 * 
	 * @param deptid
	 * @return
	 */
	Integer countPost(GridServerHandler grid);

	/**
	 * 得到岗位信息
	 * 
	 * @param grid
	 * @return
	 */
	List<Post> searchPost(GridServerHandler grid);

	/**
	 * 保存岗位信息
	 * 
	 * @param model
	 */
	void insertPost(Post model);

	/**
	 * 修改岗位信息
	 * 
	 * @param model
	 */
	void updatePost(Post model);

	/**
	 * 通过id删除岗位信息
	 * 
	 * @param id
	 */
	void delPostById(String id);

	/**
	 * 通过code删除
	 * 
	 * @param deptcode
	 */
	void delPostByDeptcode(String deptcode);

	/**
	 * 通过id得到岗位信息
	 * 
	 * @param id
	 * @return
	 */
	Post getPostByPostId(String id);

	/**
	 * 得到
	 * 
	 * @param companyid
	 * @param deptid
	 * @param postname
	 * @return
	 */
	List<Post> getPostByPostName(@Param(value = "companyid") String companyid, @Param(value = "deptid") String deptid, @Param(value = "postname") String postname);

	/**
	 * 通过岗位id失效岗位信息
	 * 
	 * @param id
	 * @param state
	 * @param modiuser
	 * @param moditimestamp
	 */
	void invalidOrReductionPostById(@Param(value = "id") String id, @Param(value = "state") int state);

	/**
	 * 通过 postid postname 查找岗位名称是否重复
	 * 
	 * @param postid
	 * @param postname
	 * @return
	 */
	List<Post> verificationPostname(@Param(value = "postid") String postid, @Param(value = "deptid") String deptid, @Param(value = "postname") String postname);

	/**
	 * 得到
	 * 
	 * @param state
	 * @param userguid
	 * @return
	 */
	List<Post> getHasPost(@Param(value = "state") Integer state, @Param(value = "userguid") String userguid);

	/**
	 * 取出postcode最大值
	 * 
	 * @param deptid
	 * @return
	 */
	String getMaxPostCodeByDepartmentId(String deptid);

}
