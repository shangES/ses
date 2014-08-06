package com.mk.person.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.grid.util.StringUtils;
import com.mk.person.entity.TmpEduExperience;
import com.mk.person.service.TmpEduExperienceService;

@Controller
public class TmpEduExperienceAction {

	@Autowired
	private TmpEduExperienceService tmpEduExperienceService;

	/**
	 * 教育经历渲染
	 * 
	 * @param map
	 * @param mycandidatesguid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/person/getTmpEduExperienceListHtml.do")
	public ModelAndView getTmpEduExperienceListHtml(ModelMap map, String mycandidatesguid) throws Exception {
		if (StringUtils.isEmpty(mycandidatesguid))
			return null;
		List<TmpEduExperience> list = tmpEduExperienceService.getTmpEduExperienceListByMycandidateGuid(mycandidatesguid);
		map.put("tmpeduexperiencelist", list);
		return new ModelAndView("/pages/person/themes/list_tmpeduexperience.jsp");
	}

	/**
	 * 教育经历添加
	 * 
	 * @param map
	 * @param mycandidatesguid
	 * @param ordernum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/person/getTmpEduExperienceHtml.do")
	public ModelAndView getTmpEduExperienceHtml(ModelMap map, String mycandidatesguid, Integer ordernum) throws Exception {
		if (StringUtils.isEmpty(mycandidatesguid))
			return null;

		map.put("mycandidatesguid", mycandidatesguid);
		map.put("ordernum", ordernum);
		return new ModelAndView("/pages/person/form_tmpeduexperience.jsp");
	}

	/**
	 * 修改或保存
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/person/saveOrUpdateTmpEduExperience.do")
	@ResponseBody
	public TmpEduExperience saveOrUpdateTmpEduExperience(TmpEduExperience model) {
		tmpEduExperienceService.saveOrUpdateTmpEduExperience(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/person/delTmpEduExperienceById.do")
	@ResponseBody
	public void delTmpEduExperienceById(String ids) throws Exception {
		tmpEduExperienceService.delTmpEduExperienceById(ids);
	}
}
