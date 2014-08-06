package com.mk.resume.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.grid.util.StringUtils;
import com.mk.resume.entity.EducationExperience;
import com.mk.resume.service.EducationExperienceService;

@Controller
public class EducationExperienceAction {
	@Autowired
	private EducationExperienceService educationExperienceService;
	
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/getEducationExperienceHtml.do")
	public ModelAndView getEducationExperienceHtml(ModelMap map, String webuserguid, Integer ordernum) throws Exception {
		if (StringUtils.isEmpty(webuserguid))
			return null;

		map.put("webuserguid", webuserguid);
		map.put("ordernum", ordernum);
		return new ModelAndView("/pages/resume/form_educationexperience.jsp");
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/getEducationExperienceListHtml.do")
	public ModelAndView getEducationExperienceListHtml(ModelMap map, String webuserguid) throws Exception {
		if (webuserguid.isEmpty())
			return null;
		List<EducationExperience> list = educationExperienceService.getEducationExperienceListByResumeGuid(webuserguid);
		map.put("educationexperiences", list);
		return new ModelAndView("/pages/resume/themes/list_educationexperience.jsp");
	}

	/**
	 * 保存or修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/resume/saveOrUpdateEducationExperience.do")
	@ResponseBody
	public EducationExperience saveOrUpdateEducationExperience(EducationExperience model) {
		educationExperienceService.saveOrUpdateEducationExperience(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/resume/delEducationExperienceById.do")
	@ResponseBody
	public void delEducationExperienceById(String ids) throws Exception {
		educationExperienceService.delEducationExperienceById(ids);
	}

}
