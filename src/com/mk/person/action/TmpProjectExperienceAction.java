package com.mk.person.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.grid.util.StringUtils;
import com.mk.person.entity.TmpProjectExperience;
import com.mk.person.service.TmpProjectExperienceService;

@Controller
public class TmpProjectExperienceAction {
	@Autowired
	private TmpProjectExperienceService tmpProjectExperienceService;
	
	
	/**
	 * 项目经历渲染
	 * 
	 * @param map
	 * @param mycandidatesguid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myperson/getTmpProjectExperienceListHtml.do")
	public ModelAndView getTmpProjectExperienceListHtml(ModelMap map, String mycandidatesguid) throws Exception {
		if (StringUtils.isEmpty(mycandidatesguid))
			return null;
		List<TmpProjectExperience> list = tmpProjectExperienceService.getTmpProjectExperienceListByMycandidateGuid(mycandidatesguid);
		map.put("tmpproexperiencelist", list);
		return new ModelAndView("/pages/person/themes/list_tmpproexperience.jsp");
	}

	/**
	 * 项目经历添加
	 * 
	 * @param map
	 * @param mycandidatesguid
	 * @param ordernum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myperson/getTmpProjectExperienceHtml.do")
	public ModelAndView getTmpProjectExperienceHtml(ModelMap map, String mycandidatesguid, Integer ordernum) throws Exception {
		if (StringUtils.isEmpty(mycandidatesguid))
			return null;

		map.put("mycandidatesguid", mycandidatesguid);
		map.put("ordernum", ordernum);
		return new ModelAndView("/pages/person/form_tmpproexperience.jsp");
	}

	/**
	 * 修改或保存
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/myperson/saveOrUpdateTmpProjectExperience.do")
	@ResponseBody
	public TmpProjectExperience saveOrUpdateTmpProjectExperience(TmpProjectExperience model) {
		tmpProjectExperienceService.saveOrUpdateTmpProjectExperience(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/myperson/delTmpProjectExperienceById.do")
	@ResponseBody
	public void delTmpEduExperienceById(String ids) throws Exception {
		tmpProjectExperienceService.delTmpProjectExperienceById(ids);
	}
}
