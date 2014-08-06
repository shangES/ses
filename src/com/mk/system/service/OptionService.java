package com.mk.system.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.OptionType;

@Service
public class OptionService {
	@Autowired
	private SqlSession sqlSession;

	// ===============选项类型==================
	/**
	 * 得到全部的选项类别
	 * 
	 * @return
	 */
	public List<OptionType> getAllOptiontype() {
		OptionDao mapper = sqlSession.getMapper(OptionDao.class);
		List<OptionType> list = mapper.getAllOptionType();
		return list;
	}

	/**
	 * 通过id得到选项类型的数据
	 * 
	 * @param id
	 * @return
	 */
	public OptionType getOptionTypeById(String id) {
		OptionDao mapper = sqlSession.getMapper(OptionDao.class);
		OptionType model = mapper.getOptionTypeById(id);
		return model;
	}

	/**
	 * 通过code得到选项类型的数据
	 * 
	 * @param code
	 * @return
	 */
	public OptionType getOptionTypeByCode(String code) {
		OptionDao mapper = sqlSession.getMapper(OptionDao.class);
		OptionType model = mapper.getOptionTypeByCode(code);
		return model;
	}

	/**
	 * 保存or修改选项类型
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateOptionType(OptionType model) {
		OptionDao mapper = sqlSession.getMapper(OptionDao.class);
		if (StringUtils.isEmpty(model.getOptiontypeguid())) {
			model.setOptiontypeguid(UUIDGenerator.randomUUID());
			mapper.insertOptionType(model);
		} else {
			mapper.updateOptionType(model);
		}
	}

	// ===============选项列表=================
	/**
	 * 通过选项类型的id得到选项列表的数据
	 * 
	 * @param id
	 * @return
	 */
	public List<OptionList> getOptionListByOptionTypeId(String id) {
		OptionDao mapper = sqlSession.getMapper(OptionDao.class);
		List<OptionList> list = mapper.getOptionListByOptionTypeId(id);
		return list;
	}

	/**
	 * 保存or修改选项列表的数据
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateOptionList(OptionList model) {
		OptionDao mapper = sqlSession.getMapper(OptionDao.class);
		if (StringUtils.isEmpty(model.getOptionid())) {
			model.setOptionid(UUIDGenerator.randomUUID());
			mapper.insertOptionList(model);
		} else {
			mapper.updateOptionList(model);
		}
	}

	/**
	 * 删除选项列表的数据
	 * 
	 * @param ids
	 */
	@Transactional
	public void delOptionListById(String ids) {
		OptionDao mapper = sqlSession.getMapper(OptionDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delOptionListById(id);
		}
	}

	/**
	 * 通过选项列表的id得到选项列表的数据
	 * 
	 * @param id
	 * @return
	 */
	public OptionList getOptionListByListId(String id) {
		OptionDao mapper = sqlSession.getMapper(OptionDao.class);
		OptionList model = mapper.getOptionListByListId(id);
		return model;
	}
	
	
	/**
	 * 验证代码
	 * @param optionid
	 * @param optiontypeguid
	 * @param code
	 * @return
	 */
	@Transactional
	public boolean checkOptionList(String optionid,String optiontypeguid,String  code){
		OptionDao mapper = sqlSession.getMapper(OptionDao.class);
		List<OptionList> list = mapper.checkOptionList(optionid,optiontypeguid,code);
		if(list.size()!=0){
			return true;
		}
		return false;
	}
}
