package com.mk.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.system.entity.OptionList;
import com.mk.system.entity.OptionType;

public interface OptionDao {

	// ============选项管理================
	/**
	 * 得到全部的选项类别
	 * 
	 * @return
	 */
	List<OptionType> getAllOptionType();

	/**
	 * 通过id得到选项类型的数据
	 * 
	 * @param id
	 * @return
	 */
	OptionType getOptionTypeById(String id);

	/**
	 * 保存选项类型
	 * 
	 * @param model
	 */
	void insertOptionType(OptionType model);

	/**
	 * 修改选项类型
	 * 
	 * @param model
	 */
	void updateOptionType(OptionType model);

	/**
	 * 通过code得到选项类型的数据
	 * 
	 * @param code
	 * @return
	 */
	OptionType getOptionTypeByCode(String code);
	
	

	// ==============选项列表=====================
	/**
	 * 通过选项类型的id得到列表的数据
	 * 
	 * @param id
	 * @return
	 */
	List<OptionList> getOptionListByOptionTypeId(String id);

	/**
	 * 保存选项列表
	 * 
	 * @param model
	 */
	void insertOptionList(OptionList model);

	/**
	 * 修改选项列表
	 * 
	 * @param model
	 */
	void updateOptionList(OptionList model);

	/**
	 * 删除选项列表的数据
	 * 
	 * @param id
	 */
	void delOptionListById(String id);

	/**
	 * 通过选项列表的id得到选项列表的数据
	 * 
	 * @param id
	 * @return
	 */
	OptionList getOptionListByListId(String id);

	/**
	 * 得到
	 * 
	 * @param sex
	 * @param sex2
	 * @return
	 */
	OptionList getOptionListByTypeAndCode(@Param(value = "typecode") String typecode, @Param(value = "code") Integer code);

	/**
	 * 得到
	 * @param typecode
	 * @param optionname
	 * @return
	 */
	OptionList getOptionListByTypeAndName(@Param(value = "typecode") String typecode, @Param(value = "name") String name);
	
	
	/**
	 * 验证代码
	 * @param optionid
	 * @param optiontypeguid
	 * @param code
	 * @return
	 */
	List<OptionList> checkOptionList(@Param(value = "optionid") String optionid,@Param(value = "optiontypeguid") String optiontypeguid,@Param(value = "code") String  code);


	/**
	 * 得到最大code
	 * 
	 * @return
	 */
	int getMaxOptionListCodeByTypeId(@Param(value = "optiontypeguid") String optiontypeguid);
}
