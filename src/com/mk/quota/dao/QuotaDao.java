package com.mk.quota.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.framework.grid.GridServerHandler;
import com.mk.quota.entity.Quota;
import com.mk.quota.entity.QuotaOperate;
import com.mk.webservice.entity.WSPostNum;

public interface QuotaDao {
	// ==================编制管理==================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countQuota(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Quota> searchQuota(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 */
	void insertQuota(Quota model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateQuota(Quota model);

	/**
	 * 得到
	 * 
	 * @return
	 */
	Quota getQuotaById(String quotaid);
	
	/**
	 * 得到流程中的编制信息
	 * 
	 * @param processinstanceid
	 * @return
	 */
	Quota getQuotaByProcessinstanceId(String processinstanceid);

	/**
	 * 得到最大
	 * 
	 * @return
	 */
	String getMaxQuotaCode();

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delQuotaByQuotaid(String quotaid);
	
	
	/**
	 * 删除
	 * 
	 * @param postguid
	 */
	void delQuotaByPostId(String postid);

	/**
	 * 得到
	 * 
	 * @param postid
	 * @return
	 */
	List<Quota> getQuotaByPostid(@Param(value = "postid") String postid);

	/**
	 * 得到
	 * 
	 * @param quotaid
	 * @param postid
	 * @param budgettype
	 * @param state
	 * @return
	 */
	List<Quota> getQuotaByPostIdAndBudgettype(@Param(value = "quotaid") String quotaid, @Param(value = "postid") String postid, @Param(value = "budgettype") String budgettype,
			@Param(value = "state") Integer state);
	
	/**
	 * 
	 * 
	 * @param postid
	 * @param state
	 * @param budgettype
	 * @return
	 */
	List<Quota> getQuotaByPostIdAndBudgettypename(@Param(value = "postid") String postid, @Param(value = "state") Integer state,@Param(value = "budgettype") String budgettype);
	
	/**
	 * 得到
	 * @param validNo
	 * @return
	 */
	List<Quota> getAllQuota(@Param(value = "valid")Integer valid);

	/**
	 * 统计招聘计划人数
	 * 
	 * @return
	 */
	int sumRecruitprogramByQoutaId(@Param(value = "quotaid") String quotaid);
	
	/**
	 * 统计编制人数(一级部门)
	 * 
	 * @return
	 */
	int sumQuotaNumByDeptCode(@Param(value = "deptcode") String deptcode);
	
	
	/**
	 * 面试通过人数
	 * 
	 * @param recruitprogramguid
	 * @return
	 */
	int sumInterviewNumberByRecruitprogramId(@Param(value = "recruitprogramguid") String recruitprogramguid);
	
	/**
	 * 统计锁定人数
	 * 
	 * @return
	 */
	int sumQuotaOperateByQoutaId(@Param(value = "quotaid") String quotaid);
	
	/***
	 * 统计在岗人数
	 * 
	 * @return
	 */
	int countEmployeeByQuotaId(@Param(value = "quotaid") String quotaid);
	
	/**
	 * 统计此部门下的占编人数
	 * 
	 * @param deptcode
	 * @return
	 */
	int countEmployeeByDeptCode(@Param(value = "deptcode") String deptcode);
	
	//---------------------11月21日20：54加的-----------------------
	/**
	 * 统计在这个岗位下的人数
	 * 
	 * @param code
	 * @return
	 */
	int countEmployeeByPostCode(@Param(value = "code") String code);
	
	
	/**
	 * 统计这个岗位下的编制数
	 * 
	 * @param code
	 * @return
	 */
	List<WSPostNum> countQuotaByPostCode(@Param(value = "code") String code);
	//--------------------------------------------

	// ==================编制操作==================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countQuotaOperate(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<QuotaOperate> searchQuotaOperate(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertQuotaOperate(QuotaOperate model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delQuotaOperateById(String quotaoperateguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delQuotaOperateByQuotaid(String quotaid);
	
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delQuotaOperateByPostId(String postid);
}
