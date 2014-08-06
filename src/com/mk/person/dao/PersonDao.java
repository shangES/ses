package com.mk.person.dao;

import java.util.List;

import com.mk.framework.grid.GridServerHandler;
import com.mk.person.entity.ExaminationRecord;
import com.mk.person.entity.Person;
import com.mk.person.entity.TmpEduExperience;
import com.mk.person.entity.TmpFamily;
import com.mk.person.entity.TmpProjectExperience;
import com.mk.person.entity.TmpRecommend;
import com.mk.person.entity.TmpRelatives;
import com.mk.person.entity.TmpTrain;
import com.mk.person.entity.TmpWorkExperience;

public interface PersonDao {

	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countPerson(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Person> searchPerson(GridServerHandler grid);

	/**
	 * 插入
	 * 
	 * @param model
	 */
	void insertPerson(Person model);

	/**
	 * 更新
	 * 
	 * @param model
	 */
	void updatePerson(Person model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Person getPersonById(String id);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delPersonById(String id);

	// ========================工作经历======================
	/**
	 * 添加
	 * 
	 * @param model
	 */
	void insertTmpWorkExperience(TmpWorkExperience model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateTmpWorkExperience(TmpWorkExperience model);

	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	List<TmpWorkExperience> getAllTmpWorkExperienceById(String mycandidatesguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delTmpWorkExperienceById(String id);

	/**
	 * 删除
	 * 
	 * @param mycandidatesguid
	 */
	void delTmpWorkExperienceByMycandidatesguId(String mycandidatesguid);

	// =================教育经历=======================
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertTmpEduExperience(TmpEduExperience model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateTmpEduExperience(TmpEduExperience model);

	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	List<TmpEduExperience> getAllTmpEduExperienceListById(String mycandidatesguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delTmpEduExperienceById(String id);

	/**
	 * 删除
	 * 
	 * @param mycandidatesguid
	 */
	void delTmpEduExperienceByMycandidatesguId(String mycandidatesguid);

	// ====================培训经历==============================
	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	List<TmpTrain> getTmpTrainListByMycandidateGuid(String mycandidatesguid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertTmpTrain(TmpTrain model);

	/**
	 * 更新
	 * 
	 * @param model
	 */
	void updateTmpTrain(TmpTrain model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delTmpTrainByTrainId(String id);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delTmpTrainByMycandidatesguId(String mycandidatesguid);

	// =======================体检记录===========================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countExaminationRecord(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<ExaminationRecord> searchExaminationRecord(GridServerHandler grid);

	/**
	 * 
	 * 保存
	 * 
	 * @param model
	 */
	void insertExaminationRecord(ExaminationRecord model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateExaminationRecord(ExaminationRecord model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	ExaminationRecord getExaminationRecordById(String id);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delExaminatioRecordById(String id);

	/**
	 * 根据外键删除
	 * 
	 * @param mycandidatesguid
	 */
	void delExaminationRecordByMyCandidatesGuid(String mycandidatesguid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	List<ExaminationRecord> getExaminationRecordByMyCandidatesGuid(String mycandidatesguid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	List<ExaminationRecord> getExaminationsByRecommendGuid(String recommendguid);

	
	/**
	 * 得到
	 * 
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	ExaminationRecord getExaminationRecordByMyCandidatesGuidAndState(String mycandidatesguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delExaminationRecordByCandidatesguidAndState(String mycandidatesguid);
	
	/**
	 * 得到
	 * 
	 * @param recommendguid
	 * @return
	 */
	List<ExaminationRecord> getExaminationsByMyRecommendguid(String recommendguid);

	// =======================家庭状况===========================
	/**
	 * 添加
	 * 
	 * @param model
	 */
	void insertTmpFamily(TmpFamily model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateTmpFamily(TmpFamily model);
	
	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	List<TmpFamily> getTmpFamilyListByMycandidateGuid(String mycandidatesguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delTmpFamilyById(String id);
	
	/**
	 * 删除
	 * 
	 * @param mycandidatesguid
	 */
	void delTmpFamilyByMycandidatesguId(String mycandidatesguid);

	// =======================公司亲属表===========================
	/**
	 * 保存
	 * 
	 * @param tmpRelatives
	 */
	void insertTmpRelatives(TmpRelatives tmpRelatives);
	
	
	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateTmpRelatives(TmpRelatives model);

	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	List<TmpRelatives> getTmpRelativesListByMycandidateGuid(String mycandidatesguid);

	
	/**
	 * 删除
	 * 
	 * 
	 * @param relativesguid
	 */
	void delTmpRelativesById(String relativesguid);
	
	
	/**
	 * 删除
	 * 
	 * @param mycandidatesguid
	 */
	void delTmpRelativesByMycandidatesguId(String mycandidatesguid);

	// =======================推荐信息表===========================
	/**
	 * 保存
	 * 
	 * @param tmpRelatives
	 */
	void insertTmpRecommend(TmpRecommend model);
	
	/**
	 * 修改
	 * 
	 * 
	 * @param model
	 */
	void updateTmpRecommend(TmpRecommend model);

	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	List<TmpRecommend> getTmpRecommendListByMycandidateGuid(String mycandidatesguid);

	
	/**
	 * 删除
	 * 
	 * @param recommendguid
	 */
	void delTmpRecommendById(String recommendguid);
	
	
	/**
	 * 删除
	 * 
	 * @param mycandidatesguid
	 */
	void delTmpRecommendByMycandidatesguId(String mycandidatesguid);

	// ======================项目经历===========================
	/**
	 * 保存
	 * 
	 * @param tmpProjectExperience
	 */
	void insertTmpProjectExperience(TmpProjectExperience tmpProjectExperience);

	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	List<TmpProjectExperience> getTmpProjectExperienceListByMycandidateGuid(String mycandidatesguid);

	/**
	 * 更新
	 * 
	 * @param model
	 */
	void updateTmpProjectExperience(TmpProjectExperience model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delTmpProjectExperienceById(String projectexperienceguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delTmpProjectExperienceByMycandidatesguId(String mycandidatesguid);
}
