package com.mk.person.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.grid.util.StringUtils;
import com.mk.person.entity.TmpWorkExperience;
import com.mk.person.service.TmpWorkExperienceService;

@Controller
public class TmpWorkExperieceAction {

	@Autowired
	private TmpWorkExperienceService tmpWorkExperienceService;

	/**
	 * 工作经历渲染
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/person/getTmpWorkExperienceListHtml.do")
	public ModelAndView getWorkExperienceListHtml(ModelMap map, String mycandidatesguid) throws Exception {
		if (StringUtils.isEmpty(mycandidatesguid))
			return null;
		List<TmpWorkExperience> list = tmpWorkExperienceService.getTmpWorkExperienceListByMycandidateGuid(mycandidatesguid);
		map.put("tmpworkexperiencelist", list);
		return new ModelAndView("/pages/person/themes/list_tmpworkexperience.jsp");
	}

	/**
	 * 工作经历添加
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/person/getTmpWorkExperienceHtml.do")
	public ModelAndView getWorkExperienceHtml(ModelMap map, String mycandidatesguid, Integer ordernum) throws Exception {
		if (StringUtils.isEmpty(mycandidatesguid))
			return null;

		map.put("mycandidatesguid", mycandidatesguid);
		map.put("ordernum", ordernum);
		return new ModelAndView("/pages/person/form_tmpworkexperience.jsp");
	}

	/**
	 * 添加或修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/person/saveOrUpdateTmpWorkExperience.do")
	@ResponseBody
	public TmpWorkExperience saveOrUpdateTmpWorkExperience(TmpWorkExperience model) {
		tmpWorkExperienceService.saveOrUpdateTmpWorkExperience(model);
		return model;

	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/person/delTmpWorkExperienceById.do")
	@ResponseBody
	public void delTmpWorkExperienceById(String ids) throws Exception {
		tmpWorkExperienceService.delTmpWorkExperienceById(ids);
	}
}
