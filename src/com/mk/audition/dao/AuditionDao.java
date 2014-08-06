package com.mk.audition.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.audition.entity.AuditionAddress;
import com.mk.audition.entity.AuditionRecord;
import com.mk.audition.entity.AuditionResult;
import com.mk.audition.entity.Interviewer;
import com.mk.framework.grid.GridServerHandler;
import com.mk.recruitment.entity.WorkPlace;

public interface AuditionDao {

	// ============面试记录===========================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countAuditionRecord(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * 
	 * @param grid
	 * @return
	 */
	List<AuditionRecord> searchAuditionRecord(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertAuditionRecord(AuditionRecord model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateAuditionRecord(AuditionRecord model);

	/**
	 * 得到
	 * 
	 * @param auditionrecordguid
	 * @return
	 */
	AuditionRecord getAuditionRecordByAuditionRecordId(String auditionrecordguid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	List<AuditionRecord> getAuditionRecordNoResultByMyCandidatesId(String mycandidatesguid);
	
	/**
	 * 
	 * 得到
	 * 
	 * @return
	 */
	List<AuditionRecord> getAuditionRecordByToInterviewer();

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	List<AuditionRecord> getAuditionRecordByMycandidatesguid(String mycandidatesguid);

	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	AuditionRecord getAuditionRecordByMycandidatesguidAndState(@Param(value = "mycandidatesguid") String mycandidatesguid);

	/**
	 * 删除
	 * 
	 * @param auditionrecordguid
	 */
	void delAuditionRecordByAuditionRecordId(String auditionrecordguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delAuditionRecordByMycandidatesguid(String mycandidatesguid);

	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	List<AuditionRecord> getAuditionRecordAndResultByMycandidatesguid(String mycandidatesguid);

	
	/**
	 * 得到
	 * 
	 * @param recommendguid
	 * @return
	 */
	List<AuditionRecord> getAuditionRecordAndResultByRecommendGuid(String recommendguid);
	
	
	/**
	 * 删除
	 * 
	 */
	void delAuditionRecordByCandidatesguidAndState(@Param(value = "mycandidatesguid") String mycandidatesguid,@Param(value = "state") Integer state);
	
	/**
	 * 得到
	 * 
	 * @param recommendguid
	 * @return
	 */
	List<AuditionRecord> getAuditionRecordAndResultByRecommendguid(String recommendguid);
	
	// ============面试结果===========================

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertAuditionResult(AuditionResult model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateAuditionResult(AuditionResult model);

	/**
	 * 得到
	 * 
	 * @param auditionrecordguid
	 * @return
	 */
	AuditionResult getAuditionResultByAuditionRecordId(String auditionrecordguid);

	/**
	 * 删除
	 * 
	 * @param auditionrecordguid
	 */
	void delAuditionResultByAuditionRecordId(String auditionrecordguid);

	/**
	 * 删除
	 * 
	 * @param mycandidatesguid
	 */
	void delAuditionResultByMycandidatesguid(String mycandidatesguid);

	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	List<AuditionResult> getAuditionResultByMycandidatesguid(String mycandidatesguid);

	// ============面试官(抄送)===========================
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertInterviewer(Interviewer model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateInterviewer(Interviewer model);

	/**
	 * 得到
	 * 
	 * @param auditionrecordguid
	 * @return
	 */
	Interviewer getInterviewerById(String interviewerguid);

	/**
	 * 得到
	 * 
	 * @param auditionrecordguid
	 * @return
	 */
	List<Interviewer> getInterviewerByAuditionRecordId(String auditionrecordguid);

	/**
	 * 删除
	 * 
	 * @param auditionrecordguid
	 */
	void delInterviewerByAuditionRecordId(String auditionrecordguid);

	/**
	 * 删除
	 * 
	 * @param mycandidatesguid
	 */
	void delInterviewerById(String interviewerguid);

	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	List<AuditionRecord> getNewAuditionRecordByMycandidatesguid(String mycandidatesguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delInterviewerByMycandidatesguid(String mycandidatesguid);

	// ============面试地点===========================
	/**
	 * 得到所有的面试地点(有效)
	 * 
	 * @param state
	 * @return
	 */
	List<AuditionAddress> getAllAuditionAddress(@Param(value = "state") Integer state);

	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countAuditionAddress(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<AuditionAddress> searchAuditionAddress(GridServerHandler grid);

	/**
	 * 新增
	 * 
	 * @param model
	 */
	void insertAuditionAddress(AuditionAddress model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateAuditionAddress(AuditionAddress model);

	/**
	 * 删除
	 * 
	 * @param guid
	 */
	void delAuditionAddressById(String auditionaddressguid);

	/**
	 * 得到
	 * 
	 * @param guid
	 * @return
	 */
	AuditionAddress getAuditionAddressById(String auditionaddressguid);
	
	/**
	 * 得到
	 * 
	 * @param auditionaddress
	 * @return
	 */
	AuditionAddress getAuditionAddressByName(String auditionaddress);

}
