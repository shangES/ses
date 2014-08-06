package com.mk.thirdpartner.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.framework.grid.GridServerHandler;
import com.mk.thirdpartner.entity.ThirdPartner;
import com.mk.thirdpartner.entity.ThirdPartyAssess;

public interface ThirdPartnerDao {

	// ========================第三方机构基本信息管理========================
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertThirdPartner(ThirdPartner model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateThirdPartner(ThirdPartner model);

	/**
	 * 刪除
	 * 
	 * @param guid
	 */
	void delThirdPartnerById(@Param(value = "guid") String guid);

	/**
	 * 得到
	 * 
	 * @param guid
	 * @return
	 */
	ThirdPartner getThirdPartnerById(@Param(value = "guid") String guid);

	/**
	 * 得到
	 * 
	 * @return
	 */
	List<ThirdPartner> getAllThirdPartner();

	// =====================第三方机构测评信息管理===========================

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertThirdPartyAssess(ThirdPartyAssess model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateThirdPartyAssess(ThirdPartyAssess model);

	/**
	 * 删除
	 * 
	 * @param model
	 */
	void delThirdPartyAssessById(@Param(value = "thirdpartyassessguid") String thirdpartyassessguid);
	
	/**
	 * 删除
	 * @param thirdpartnerguid
	 */
	void delThirdPartyAssessByThirdPartnerGuid(@Param(value = "thirdpartnerguid") String thirdpartnerguid);

	/**
	 * 得到
	 * 
	 * @param thirdpartyassessguid
	 */
	ThirdPartyAssess getThirdPartyAssessById(@Param(value = "thirdpartyassessguid") String thirdpartyassessguid);

	/**
	 * 得到
	 * 
	 * @param grid
	 */
	List<ThirdPartyAssess> getAllThirdPartyAssess(GridServerHandler grid);

	/**
	 * 总条数
	 * 
	 * @param thirdpartnerguid
	 * @return
	 */
	Integer countThirdPartyAssess(GridServerHandler grid);

	/**
	 * 等级通过
	 * 
	 * @param guid
	 */
	void setThirdPartyAssessLevel(@Param(value = "thirdpartyassessguid") String thirdpartyassessguid);

	/**
	 * 体检机构树
	 * 
	 * @return
	 */
	List<ThirdPartner> getAllPartner();
	
	/**
	 * 检验年度
	 * 
	 * @param num
	 * @return
	 */
	ThirdPartyAssess checkYear(@Param(value = "year") Integer year,@Param(value = "thirdpartnerguid") String thirdpartnerguid,@Param(value = "thirdpartyassessguid") String thirdpartyassessguid);
}
