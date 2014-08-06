package com.mk.recruitprogram.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.tree.TreePageGrid;
import com.mk.recruitprogram.entity.RecruitProgram;
import com.mk.recruitprogram.entity.RecruitProgramAudit;
import com.mk.recruitprogram.entity.RecruitProgramOperate;

public interface RecruitprogramDao {

	// ====================招聘计划管理===================
	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<RecruitProgram> searchRecruitprogram(GridServerHandler grid);

	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countRecruitprogram(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertRecruitprogram(RecruitProgram model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateRecruitprogram(RecruitProgram model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delRecruitprogramById(String id);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delRecruitprogramByQuotaId(String quotaid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	RecruitProgram getRecruitprogramById(String id);
	
	
	
	/**
	 * 统计这个招聘计划下有多少子计划
	 * 
	 * @param recruitprogramcode
	 * @return
	 */
	int countRecruitprogramByCode(@Param(value = "recruitprogramcode") String recruitprogramcode);

	/**
	 * 得到
	 * 
	 * @param recruitprogramcode
	 * @return
	 */
	RecruitProgram getRecruitprogramByCode(@Param(value = "recruitprogramcode") String recruitprogramcode);
	
	
	/**
	 * 得到
	 * 
	 * @param businessKey
	 * @return
	 */
	RecruitProgram getRecruitprogramByProcessinstanceId(String businessKey);

	/**
	 * 通过id修改state状态
	 * 
	 * @param id
	 * @param state
	 */
	void updateRecruitprogramStateById(@Param(value = "id") String id, @Param(value = "state") Integer state);

	/**
	 * 通过部门id得到
	 * 
	 * @param deptid
	 * @return
	 */
	List<RecruitProgram> getAllRecruitprogramByDeptid(@Param(value = "deptid") String deptid);

	/**
	 * 取得最大code
	 * 
	 * @return
	 */
	String getMaxRecruitProgramCode();

	/**
	 * 得到发布的数据
	 * 
	 * @return
	 */
	List<RecruitProgram> getAllRecruitprogram(@Param(value = "state") Integer state);

	/**
	 * 招聘计划统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countRecruitprogramTree(TreePageGrid grid);

	/**
	 * 招聘计划搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<RecruitProgram> searchRecruitprogramTree(TreePageGrid grid);
	
	
	/**
	 * 得到
	 * 
	 * 
	 * @param quotaid
	 * @param postid
	 * @return
	 */
	List<RecruitProgram> getRecruitprogramByQuotaidAndPostid(@Param(value = "quotaid") String quotaid, @Param(value = "postid") String postid);

	
	// ======================招聘计划OA审批=============================
	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<RecruitProgramAudit> searchRecruitprogramaudit(GridServerHandler grid);

	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countRecruitprogramaudit(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertRecruitprogramaudit(RecruitProgramAudit model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateRecruitprogramaudit(RecruitProgramAudit model);

	/**
	 * id删除
	 * 
	 * @param id
	 */
	void delRecruitprogramauditByAuditId(String id);

	/**
	 * 通过Recruitprogram id删除
	 * 
	 * @param id
	 */
	void delRecruitprogramauditByRpId(String id);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	RecruitProgramAudit getRecruitprogramauditById(String id);
	
	
	
	/**
	 * 得到
	 * 
	 * 
	 * @param interfacecode
	 * @return
	 */
	RecruitProgramAudit getRecruitprogramauditByInterfacecode(@Param(value = "interfacecode") String interfacecode);

	// ==================招聘计划操作==================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countRecruitProgramOperate(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<RecruitProgramOperate> searchRecruitProgramOperate(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertRecruitProgramOperate(RecruitProgramOperate model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delRecruitProgramOperateById(String recruitprogramoperateguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delRecruitProgramOperateByRecruitProgramGuid(String recruitprogramguid);

	/**
	 * 得到
	 * 
	 * @param model
	 * @return
	 */
	RecruitProgramAudit getRecruitProgramAuditByPam(RecruitProgramAudit model);
}
