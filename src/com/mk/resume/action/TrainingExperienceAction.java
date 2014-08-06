package com.mk.resume.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.grid.util.StringUtils;
import com.mk.resume.entity.TrainingExperience;
import com.mk.resume.service.TrainingExperienceService;

@Controller
public class TrainingExperienceAction {
	@Autowired
	private TrainingExperienceService trainingExperienceService;

	/**
	 * 添加
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/getTrainingExperienceHtml.do")
	public ModelAndView getTrainingExperienceHtml(ModelMap map, String webuserguid, Integer ordernum) throws Exception {
		if (StringUtils.isEmpty(webuserguid))
			return null;

		map.put("webuserguid", webuserguid);
		map.put("ordernum", ordernum);
		return new ModelAndView("/pages/resume/form_trainingexperience.jsp");
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/getTrainingExperienceListHtml.do")
	public ModelAndView getTrainingExperienceListHtml(ModelMap map, String webuserguid) throws Exception {
		if (webuserguid.isEmpty())
			return null;
		List<TrainingExperience> list = trainingExperienceService.getTrainingExperienceListByResumeGuid(webuserguid);
		map.put("trainingexperiences", list);
		return new ModelAndView("/pages/resume/themes/list_trainingexperience.jsp");
	}

	/**
	 * 保存or修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/resume/saveOrUpdateTrainingExperience.do")
	@ResponseBody
	public TrainingExperience saveOrUpdateTrainingExperience(TrainingExperience model) {
		trainingExperienceService.saveOrUpdateTrainingExperience(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/resume/delTrainingExperienceById.do")
	@ResponseBody
	public void delTrainingExperienceById(String ids) throws Exception {
		trainingExperienceService.delTrainingExperienceById(ids);
	}
}
