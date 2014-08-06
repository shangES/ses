package com.mk.resume.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.grid.util.StringUtils;
import com.mk.resume.entity.ProjectExperience;
import com.mk.resume.service.ProjectExperienceService;

@Controller
public class ProjectExperienceAction {
	@Autowired
	private ProjectExperienceService projectExperienceService;

	/**
	 * 添加
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/getProjectExperienceHtml.do")
	public ModelAndView getProjectExperienceHtml(ModelMap map, String webuserguid, Integer ordernum) throws Exception {
		if (StringUtils.isEmpty(webuserguid))
			return null;

		map.put("webuserguid", webuserguid);
		map.put("ordernum", ordernum);
		return new ModelAndView("/pages/resume/form_projectexperience.jsp");
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/getProjectExperienceListHtml.do")
	public ModelAndView getProjectExperienceListHtml(ModelMap map, String webuserguid) throws Exception {
		if (webuserguid.isEmpty())
			return null;
		List<ProjectExperience> list = projectExperienceService.getProjectExperienceListByResumeGuid(webuserguid);
		map.put("projectexperiences", list);
		return new ModelAndView("/pages/resume/themes/list_projectexperience.jsp");
	}

	/**
	 * 保存or修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/resume/saveOrUpdateProjectExperience.do")
	@ResponseBody
	public ProjectExperience saveOrUpdateProjectExperience(ProjectExperience model) {
		projectExperienceService.saveOrUpdateProjectExperience(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/resume/delProjectExperienceById.do")
	@ResponseBody
	public void delProjectExperienceById(String ids) throws Exception {
		projectExperienceService.delProjectExperienceById(ids);
	}

}
