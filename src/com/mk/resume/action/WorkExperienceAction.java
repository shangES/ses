package com.mk.resume.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.grid.util.StringUtils;
import com.mk.resume.entity.WorkExperience;
import com.mk.resume.service.WorkExperienceService;

@Controller
public class WorkExperienceAction {
	@Autowired
	private WorkExperienceService workExperienceService;

	/**
	 * 工作经历添加
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/getWorkExperienceHtml.do")
	public ModelAndView getWorkExperienceHtml(ModelMap map, String webuserguid, Integer ordernum) throws Exception {
		if (StringUtils.isEmpty(webuserguid))
			return null;

		map.put("webuserguid", webuserguid);
		map.put("ordernum", ordernum);
		return new ModelAndView("/pages/resume/form_workexperience.jsp");
	}

	/**
	 * 工作经历渲染
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/getWorkExperienceListHtml.do")
	public ModelAndView getWorkExperienceListHtml(ModelMap map, String webuserguid) throws Exception {
		if (StringUtils.isEmpty(webuserguid))
			return null;
		List<WorkExperience> list = workExperienceService.getWorkExperienceListByResumeGuid(webuserguid);
		map.put("workexperiencelist", list);
		return new ModelAndView("/pages/resume/themes/list_workexperience.jsp");
	}

	/**
	 * 保存or修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/resume/saveOrUpdateWorkExperience.do")
	@ResponseBody
	public WorkExperience saveOrUpdateWorkExperience(WorkExperience model) {
		workExperienceService.saveOrUpdateWorkExperience(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/resume/delWorkExperienceById.do")
	@ResponseBody
	public void delWorkExperienceById(String ids) throws Exception {
		workExperienceService.delWorkExperienceById(ids);
	}

}
