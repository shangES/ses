package com.mk.mycandidates.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.framework.chart.ChartModel;
import com.mk.framework.grid.GridServerHandler;
import com.mk.mycandidates.entity.Bussiness;
import com.mk.mycandidates.entity.CandidatesOperate;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.mycandidates.entity.MyCandidatesHistory;
import com.mk.mycandidates.entity.Recommend;
import com.mk.todo.entity.TodoPam;

public interface MyCandidatesDao {
	// ========================简历信息 对应 我的应聘==================================
	/**
	 * 搜索(安排面试)
	 * 
	 * @param grid
	 * @return
	 */
	List<MyCandidates> searchMycandidatesByAudition(GridServerHandler grid);

	/**
	 * 统计(安排面试)
	 * 
	 * @param grid
	 * @return
	 */
	Integer countMycandidatesByAudition(GridServerHandler grid);

	/**
	 * 搜索(待发布的面试)
	 * 
	 * @param grid
	 * @return
	 */
	List<MyCandidates> searchMycandidatesByResult(GridServerHandler grid);

	/**
	 * 统计(待发布的面试)
	 * 
	 * @param grid
	 * @return
	 */
	Integer countMycandidatesByResult(GridServerHandler grid);

	/**
	 * 搜索(部门简历)
	 * 
	 * @param grid
	 * @return
	 */
	List<MyCandidates> searchMyCandidatesByDept(GridServerHandler grid);

	/**
	 * 统计(部门简历)
	 * 
	 * @param grid
	 * @return
	 */
	Integer countMyCandidatesByDept(GridServerHandler grid);

	/**
	 * 搜索(简历筛选)
	 * 
	 * @param grid
	 * @return
	 */
	List<MyCandidates> searchMycandidatesByFilter(GridServerHandler grid);

	/**
	 * 统计(简历筛选)
	 * 
	 * @param grid
	 * @return
	 */
	Integer countMycandidatesByFilter(GridServerHandler grid);

	/**
	 * 搜索(简历管理)
	 * 
	 * @param grid
	 * @return
	 */
	List<MyCandidates> searchMyCandidatesAndResume(GridServerHandler grid);

	/**
	 * 统计(简历筛选、简历管理)
	 * 
	 * @param grid
	 * @return
	 */
	Integer countMyCandidatesAndResume(GridServerHandler grid);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateMyCandidates(MyCandidates model);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertMyCandidates(MyCandidates model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delMyCandidatesById(String id);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delMyCandidatesByUserId(String webuserguid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	MyCandidates getMyCandidatesById(String id);

	/**
	 * 得到
	 * 
	 * 
	 * @param id
	 * @param recommendguid
	 * @return
	 */
	List<MyCandidates> getMyCandidatesByIdAndRecommendGuid(@Param(value = "mycandidatesguid") String id, @Param(value = "recommendguid") String recommendguid);

	/**
	 * 验证
	 * 
	 * @param userguid
	 * @return
	 */
	MyCandidates getMyCandidatesByUserGuid(@Param(value = "webuserguid") String webuserguid, @Param(value = "state") Integer state);

	/**
	 * 应聘者是否在应聘流程中
	 * 
	 * 
	 * @param webuserguid
	 * @return
	 */
	MyCandidates getMyCandidatesByWebUserGuid(@Param(value = "webuserguid") String webuserguid);

	
	/**
	 * 得到
	 * 
	 * 
	 * @param recruitpostguid
	 * @return
	 */
	List<MyCandidates> getMyCandidatesByRecruitPostGuid(@Param(value = "recruitpostguid") String recruitpostguid);
	
	
	/**
	 * 外键和状态查询
	 * 
	 * @param webuserguid
	 * @param candidatesstate
	 * @return
	 */
	List<MyCandidates> getMyCandidatesByWebUserGuidAndState(@Param(value = "webuserguid") String webuserguid, @Param(value = "candidatesstate") Integer candidatesstate);

	/**
	 * 统计面试人员
	 * 
	 * @param grid
	 * @return
	 */
	Integer countCandidatesPerson(GridServerHandler grid);

	/**
	 * 搜索人员面试列表
	 * 
	 * @param grid
	 * @return
	 */
	List<MyCandidates> searchCandidatesPerson(GridServerHandler grid);

	/**
	 * 统计(可以修改面试官的数据)
	 * 
	 * @param grid
	 * @return
	 */
	Integer countMycandidatesByMainInterviewer(GridServerHandler grid);

	/**
	 * 搜索(可以修改面试官的数据)
	 * 
	 * @param grid
	 * @return
	 */
	List<MyCandidates> searchMycandidatesByMainInterviewer(GridServerHandler grid);

	/**
	 * 部门筛选统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countMyCandidatesAndResumeByDept(GridServerHandler grid);

	/**
	 * 部门筛选搜索
	 * 
	 * 
	 * @param grid
	 * @return
	 */
	List<MyCandidates> searchMyCandidatesAndResumeByDept(GridServerHandler grid);

	/**
	 * 统计面试结果
	 * 
	 * @param grid
	 * @return
	 */
	Integer countMyCandidatesByState(GridServerHandler grid);

	/**
	 * 搜索面试结果
	 * 
	 * @param grid
	 * @return
	 */
	List<MyCandidates> searchMyCandidatesByState(GridServerHandler grid);

	/**
	 * 投递历史
	 * 
	 * @param id
	 * @return
	 */
	List<MyCandidates> getHistoryMyCandidatesByWebUserGuid(@Param(value = "webuserguid") String webuserguid, @Param(value = "mycandidatesguid") String mycandidatesguid);

	/**
	 * 邀请他人反馈面试结果（简历管理下）搜索
	 * 
	 * @param grid
	 * @return
	 */
	Integer countMycandidatesByMainInterviewerList(GridServerHandler grid);

	/**
	 * 邀请他人反馈面试结果（简历管理下）搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<MyCandidates> searchMycandidatesByMainInterviewerList(GridServerHandler grid);

	// ==================推荐信息==================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countRecommend(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Recommend> searchRecommend(GridServerHandler grid);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateRecommend(Recommend model);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertRecommend(Recommend model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delRecommendByCandidatesGuid(String mycandidatesguid);

	/**
	 * 删除
	 * 
	 * 
	 */
	void delRecommendByCandidatesGuidAndState(@Param(value = "mycandidatesguid") String mycandidatesguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delRecommendById(String recommendguid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Recommend getRecommendById(String recommendguid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	List<Recommend> getRecommendByCandidatesGuid(String mycandidatesguid);

	/**
	 * 得到
	 * 
	 * @return
	 */
	List<Recommend> getRecommendByCandidatesGuidAndState(String mycandidatesguid);

	/**
	 * 得到
	 * 
	 * 
	 * @return
	 */
	List<Recommend> getRecommendByToRecommender();

	/**
	 * 得到
	 * 
	 * @return
	 */
	List<Recommend> getInvalidMyCandidatesByState(String _datas);

	/**
	 * 根据应聘ID统计推荐次数
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	Integer countRecommendByMyCandidatesGuid(String mycandidatesguid);

	/**
	 * 转推荐次数
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	Integer countZRecommendByMyCandidatesGuid(String mycandidatesguid);

	/**
	 * 统计认定次数
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	Integer countHoldByMyCandidatesGuid(String mycandidatesguid);

	/**
	 * 统计安排面试次数
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	Integer countAuditionrecordByMyCandidatesGuid(String mycandidatesguid);

	/**
	 * 首页信息提示统计
	 * 
	 * @param state
	 * @return
	 */
	List<ChartModel> countMsgByState();

	/**
	 * 投递次数
	 * 
	 * @param webuserguid
	 * @return
	 */
	Integer countSend(String webuserguid);

	// ==================应聘过程中流程的操作历史==================
	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateMyCandidatesHistory(MyCandidatesHistory model);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertMyCandidatesHistory(MyCandidatesHistory model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delMyCandidatesHistoryById(String mycandidateshistoryguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delyCandidatesHistoryByMyCandidatesGuid(String mycandidatesguid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	MyCandidatesHistory getMyCandidatesHistoryById(String mycandidateshistoryguid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	List<MyCandidatesHistory> getMyCandidatesHistoryByMyCandidatesGuid(String mycandidatesguid);

	/**
	 * 统计我的应聘历史
	 * 
	 * @param grid
	 * @return
	 */
	Integer countMyCandidatesHistoryByMyCandidatesGuid(GridServerHandler grid);

	/**
	 * 我的应聘历史记录搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<MyCandidatesHistory> searchMyCandidatesHistoryByMyCandidatesGuid(GridServerHandler grid);
	
	/**
	 * 检查一星期内是否有投过相同职位简历
	 * 
	 * @param model
	 * @return
	 */
	List<MyCandidates> checkResume(MyCandidates model);

	// ==================短信发送==================
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertBussiness(Bussiness model);

	
	/**
	 * 外网链接oa
	 * 
	 * @param pam
	 * @return
	 */
	List<MyCandidates> getMyDptCandidatesByUserGuid(TodoPam pam);
	
	
	// ========================应聘信息操作表==================================
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertCandidatesOperate(CandidatesOperate model);
	
	
	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	List<CandidatesOperate> getCandidatesOperateByCandidatesGuid(@Param(value = "mycandidatesguid") String mycandidatesguid);
	
	
	/**
	 * 得到
	 * 
	 * @param userguid
	 * @return
	 */
	List<CandidatesOperate> getCandidatesOperateByUserGuid(@Param(value = "userguid") String userguid);
	
	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @param userguid
	 * @return
	 */
	CandidatesOperate getCandidatesOperateByCandidatesGuidAndUserGuid(@Param(value = "mycandidatesguid") String mycandidatesguid,@Param(value = "userguid") String userguid);

	
	//------------------------------------------
	/**
	 * 查在面试中的应聘信息
	 * 
	 * 
	 * @return
	 */
	List<MyCandidates> getMyCandidatesByAudition();

	/**
	 * 除此应聘信息以外的另外应聘信息
	 * 
	 * @param webuserguid
	 * @param mycandidatesguid
	 * @return
	 */
	List<MyCandidates> getCandidatesByUserGuid(@Param(value = "webuserguid") String webuserguid,@Param(value = "mycandidatesguid") String mycandidatesguid);

	/**
	 * 查锁定的应聘信息
	 * 
	 * @return
	 */
	List<MyCandidates> getMyCandidatesByLock();

	
	/**
	 * 根据锁定的应聘信息的id查有没有应聘信息
	 * 
	 * @param webuserguid
	 * @return
	 */
	List<MyCandidates> getCandidatesByAudition(@Param(value = "webuserguid") String webuserguid);
	
	//------------------------------------------
	/**
	 * 删除
	 * 
	 * @param userguid
	 * @return
	 */
	void delCandidatesOperateByCandidatesGuid(@Param(value = "candidatesguid") String candidatesguid);
	
	
	/**
	 * 得到
	 * 
	 * @return
	 */
	List<Recommend> getRecommendByCandidatesGuidAndTime(@Param(value = "mycandidatesguid") String mycandidatesguid,@Param(value = "times") String times);
}
