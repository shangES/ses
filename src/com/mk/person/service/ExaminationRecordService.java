package com.mk.person.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.person.dao.PersonDao;
import com.mk.person.entity.ExaminationRecord;
import com.mk.system.dao.OptionDao;
import com.mk.thirdpartner.dao.ThirdPartnerDao;

@Service
public class ExaminationRecordService {

	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CodeConvertNameService codeConvertNameService;

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	public void searchExaminationRecord(GridServerHandler grid) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();

		// 统计
		Integer count = mapper.countExaminationRecord(grid);
		PageUtils.setTotalRows(grid, count);

		List<ExaminationRecord> list = mapper.searchExaminationRecord(grid);
		if (list.size() > Constance.ConvertCodeNum) {
			// 体检机构
			Map<String, String> thirdpartnerMap = codeConvertNameService.getAllThirdpartnerMap();
			
			// 体检状态
			Map<Integer, String> exminationMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.EXAMINATIONSTATE);
			for (ExaminationRecord model : list) {
				model.convertManyCodeToName(thirdpartnerMap,exminationMap);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (ExaminationRecord model : list) {
				model.convertOneCodeToName(thirdPartnerDao,optionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}

		grid.setData(data);
	}

	/**
	 * 保存或修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateExaminationRecord(ExaminationRecord model) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		if (StringUtils.isEmpty(model.getExaminationrecordguid())) {
			model.setExaminationrecordguid(UUIDGenerator.randomUUID());
			mapper.insertExaminationRecord(model);
		} else {
			mapper.updateExaminationRecord(model);
		}
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public ExaminationRecord getExaminationRecordById(String id) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		ExaminationRecord model = mapper.getExaminationRecordById(id);
		return model;
	}
	
	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public ExaminationRecord getExaminationRecordByMyCandidatesGuid(String id) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		 List<ExaminationRecord> list = mapper.getExaminationRecordByMyCandidatesGuid(id);
		if(!list.isEmpty()){
			ExaminationRecord model=list.get(0);
			model.convertOneCodeToName(thirdPartnerDao,optionDao);
			return model;
		 }
		return null;
	}
	
	
	
	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public List<ExaminationRecord> getExaminationsByMyCandidatesid(String id) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		 List<ExaminationRecord> list = mapper.getExaminationRecordByMyCandidatesGuid(id);
		if(!list.isEmpty()){
			for(ExaminationRecord examination:list){
				examination.convertOneCodeToName(thirdPartnerDao, optionDao);
			}
		 }
		return list;
	}
	
	
	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public List<ExaminationRecord> getExaminationsByRecommendGuid(String id) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		 List<ExaminationRecord> list = mapper.getExaminationsByRecommendGuid(id);
		if(!list.isEmpty()){
			for(ExaminationRecord examination:list){
				examination.convertOneCodeToName(thirdPartnerDao, optionDao);
			}
		 }
		return list;
	}
	

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delExaminatioRecordById(String ids) {
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delExaminatioRecordById(id);
		}

	}
	
	/**
	 * 得到
	 * 
	 * @param recommendguid
	 * @return
	 */
	public List<ExaminationRecord> getExaminationsByMyRecommendguid(String recommendguid){
		PersonDao mapper = sqlSession.getMapper(PersonDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		ThirdPartnerDao thirdPartnerDao = sqlSession.getMapper(ThirdPartnerDao.class);
		 List<ExaminationRecord> list = mapper.getExaminationsByMyRecommendguid(recommendguid);
		if(!list.isEmpty()){
			for(ExaminationRecord examination:list){
				examination.convertOneCodeToName(thirdPartnerDao, optionDao);
			}
		 }
		return list;
	}

}
