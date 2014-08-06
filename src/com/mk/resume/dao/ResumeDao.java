package com.mk.resume.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.tree.TreePageGrid;
import com.mk.resume.entity.EducationExperience;
import com.mk.resume.entity.InteriorRecommend;
import com.mk.resume.entity.ProjectExperience;
import com.mk.resume.entity.Relatives;
import com.mk.resume.entity.Resume;
import com.mk.resume.entity.ResumeAssess;
import com.mk.resume.entity.ResumeEamil;
import com.mk.resume.entity.ResumeEamilFile;
import com.mk.resume.entity.ResumeFile;
import com.mk.resume.entity.TrainingExperience;
import com.mk.resume.entity.WorkExperience;

public interface ResumeDao {
	// ======================简历信息======================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countResume(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Resume> searchResume(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertResume(Resume model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateResume(Resume model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Resume getResumeById(String webuserguid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	List<Resume> getResumeByWebuserId(String webuserguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delResumeById(String webuserguid);

	/**
	 * 根据名称得到
	 * 
	 * @param name
	 * @return
	 */
	List<Resume> getResumeByName(String name);

	// ======================工作经历======================
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertWorkExperience(WorkExperience model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateWorkExperience(WorkExperience model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	WorkExperience getWorkExperienceById(String workExperienceguid);

	/**
	 * 得到所有的
	 * 
	 * 
	 * @return
	 */
	List<WorkExperience> getAllWorkExperienceByWebuserId(String webuserguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delWorkExperienceById(String workExperienceguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delWorkExperienceByWebuserId(String webuserguid);

	// ======================项目经历======================
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertProjectExperience(ProjectExperience model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateProjectExperience(ProjectExperience model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	ProjectExperience getProjectExperienceById(String projectExperienceguid);

	/**
	 * 得到所有的
	 * 
	 * 
	 * @return
	 */
	List<ProjectExperience> getAllProjectExperienceByWebuserId(String webuserguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delProjectExperienceById(String projectExperienceguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delProjectExperienceByWebuserId(String webuserguid);

	// ======================教育经历======================

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertEducationExperience(EducationExperience model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateEducationExperience(EducationExperience model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	EducationExperience getEducationExperienceById(String educationExperienceguid);

	/**
	 * 得到所有的
	 * 
	 * 
	 * @return
	 */
	List<EducationExperience> getAllEducationExperienceByWebuserId(String webuserguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delEducationExperienceById(String educationExperienceguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delEducationExperienceByWebuserId(String webuserguid);

	// ======================培训经历======================

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertTrainingExperience(TrainingExperience model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateTrainingExperience(TrainingExperience model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	TrainingExperience getTrainingExperienceById(String trainingExperienceguid);

	/**
	 * 得到所有的
	 * 
	 * 
	 * @return
	 */
	List<TrainingExperience> getAllTrainingExperienceByWebuserId(String webuserguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delTrainingExperienceById(String trainingExperienceguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delTrainingExperienceByWebuserId(String webuserguid);

	// ======================附件上传======================
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertResumeFile(ResumeFile model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateResumeFile(ResumeFile model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delResumeFileById(String resumeFileguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delResumeFileByWebuserId(String webuserguid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	ResumeFile getResumeFileById(String resumeFileguid);

	/**
	 * 得到所有的
	 * 
	 * 
	 * @return
	 */
	List<ResumeFile> getAllResumeFileByWebuserId(String webuserguid);

	// ======================电子邮箱======================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countResumeEamil(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<ResumeEamil> searchResumeEamil(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertResumeEamil(ResumeEamil model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateResumeEamil(ResumeEamil model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	ResumeEamil getResumeEamilById(String resumeeamilguid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	ResumeEamil getResumeEamilByInterFaceCode(String interfacecode);

	/**
	 * 得到
	 * 
	 * 
	 * @param webuserguid
	 * @return
	 */
	List<ResumeEamil> getResumeEamilByWebUserId(String webuserguid);

	/**
	 * 得到
	 * 
	 * @param email
	 * @return
	 */
	List<ResumeEamil> getResumeEamilByEmail(@Param(value = "email") String email, @Param(value = "id") String id);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delResumeEamilById(String resumeeamilguid);

	// ====================邮箱简历附件=======================

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertResumeEamilFile(ResumeEamilFile model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateResumeEamilFile(ResumeEamilFile model);

	/**
	 * 删除
	 * 
	 * @param resumeeamilguid
	 */
	void delResumeEamilFileByResumeeamilId(String resumeeamilguid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	ResumeEamilFile getResumeEamilFileById(String id);

	/**
	 * 得到
	 * 
	 * @param resumeeamilguid
	 * @return
	 */
	List<ResumeEamilFile> getResumeEamilFileByResumeeamilId(String resumeeamilguid);

	// ====================<!-- 评价信息 -->=======================
	/**
	 * 搜索数据
	 * 
	 * @param grid
	 * @return
	 */
	List<ResumeAssess> searchResumeAssess(GridServerHandler grid);

	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countResumeAssess(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertResumeAssess(ResumeAssess model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateResumeAssess(ResumeAssess model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delResumeAssessById(String id);

	/**
	 * T通过简历id删除评价信息
	 * 
	 * @param webuserguid
	 */
	void delResumeAssessByWebuserId(String webuserguid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	ResumeAssess getResumeAssessById(String id);

	/**
	 * 得到
	 * 
	 * @return
	 */
	List<ResumeAssess> getResumeAssessByWebUserId(String webuserguid);

	/**
	 * 统计
	 * 
	 * @param webuserguid
	 * @return
	 */
	Integer countResumeAssessByWebuserGuid(String webuserguid);

	/**
	 * 根据邮箱得到
	 * 
	 * @param email
	 * @return
	 */
	Resume getResumeByEmail(String email);

	/**
	 * 根据邮箱删除
	 * 
	 * @param email
	 */
	void delResumeByEmail(String email);

	/**
	 * 简历信息树统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countResumeTree(TreePageGrid grid);

	/**
	 * 简历信息树查询
	 * 
	 * @param grid
	 * @return
	 */
	List<Resume> searchResumeTree(TreePageGrid grid);

	// ====================亲属表=======================
	/**
	 * 保存
	 * 
	 * @param relatives
	 */
	void insertRelatives(Relatives model);

	/**
	 * 删除
	 * 
	 * @param webuserguid
	 */
	void delRelativesByWebUserId(String webuserguid);

	/**
	 * 得到
	 * 
	 * @param webuserguid
	 * @return
	 */
	List<Relatives> getRelativesByWebUserId(String webuserguid);

	// ==================我的推荐==================
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertInteriorRecommend(InteriorRecommend model);

	/**
	 * 删除
	 * 
	 * @param webuserguid
	 */
	void delInteriorRecommendById(String mycandidatesguid);

	/**
	 * 得到
	 * 
	 * @param webuserguid
	 * @return
	 */
	List<InteriorRecommend> getInteriorRecommendByUserGuid(String userguid);

	/**
	 * 得到
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	InteriorRecommend getInteriorRecommendById(String mycandidatesguid);

	
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
	List<InteriorRecommend> searchRecommend(GridServerHandler grid);

	
}
