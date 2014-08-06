package com.mk.resume.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.mail.MailSendService;
import com.mk.framework.mail.SendMessageService;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.Resume;
import com.mk.resume.entity.ResumeAssess;
import com.mk.system.dao.OptionDao;

@Service
public class ResumeAssessService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CodeConvertNameService codeConvertNameService;
	@Autowired
	private MailSendService mailSendService;
	@Autowired
	private SendMessageService sendMessageService;
	

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	public void searchResumeAssess(GridServerHandler grid) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();

		// 统计
		Integer count = mapper.countResumeAssess(grid);
		PageUtils.setTotalRows(grid, count);
		
		//搜索
		List<ResumeAssess> list = mapper.searchResumeAssess(grid);

		if (list.size() > Constance.ConvertCodeNum) {
			// 评价等级
			Map<Integer, String> assesslevelMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.ASSESSLEVEL);
			// 评价体系
			Map<Integer, String> assesshierarchyMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.ASSESSLEVEL);
			for (ResumeAssess model : list) {
				model.convertManyCodeToName(assesslevelMap,assesshierarchyMap);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (ResumeAssess model : list) {
				model.convertOneCodeToName(optionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}

	/**
	 * 保存 or 修改
	 * 
	 * @param model
	 * @throws Exception 
	 */
	@Transactional
	public void saveOrUpdateResumeAssess(ResumeAssess model) throws Exception {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		UserContext uc = ContextFacade.getUserContext();
		if (StringUtils.isEmpty(model.getAssessguid())) {
			//保存评价
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			model.setAssessguid(UUIDGenerator.randomUUID());
			model.setModiuser(uc.getUsername());
			mapper.insertResumeAssess(model);
			
			//回写简历(人才库)
			Resume resume = mapper.getResumeById(model.getWebuserguid());
			if(resume!=null){
				resume.setMark(Constance.VALID_YES);
				mapper.updateResume(resume);
				
				
				//发送的短信(收录到人才库)
				sendMessageService.sendResumeAssessMsgTo(resume.getName(), model.getModitimestamp(),resume.getMobile(),resume.getEmail());
				
				//发送邮件
				String email=resume.getEmail();
			    String regex = "[a-zA-Z0-9_]{6,12}+@[a-zA-Z]+(\\.[a-zA-Z]+){1,3}";
			    if(email.matches(regex)){
			    	mailSendService.sendResumeMailTo(resume.getName(), resume.getModtime(), email);
			    }
			}
		} else {
			mapper.updateResumeAssess(model);
		}

	}

	/**
	 * 刪除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delResumeAssessById(String ids) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delResumeAssessById(id);
		}
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public ResumeAssess getResumeAssessById(String id) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		ResumeAssess model = mapper.getResumeAssessById(id);
		if(model!=null){
			model.convertOneCodeToName(optionDao);
		}
		return model;
	}

	
	/**
	 * 得到
	 * 
	 * 
	 * @param id
	 * @return
	 */
	public List<ResumeAssess> getResumeAssessByWebUserId(String id) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		List<ResumeAssess> list = mapper.getResumeAssessByWebUserId(id);
		if(!list.isEmpty()){
			for(ResumeAssess assess:list){
				assess.convertOneCodeToName(optionDao);
			}
		}
		return list;
	}
	
	
}
