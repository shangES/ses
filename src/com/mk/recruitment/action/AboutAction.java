package com.mk.recruitment.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.recruitment.entity.About;
import com.mk.recruitment.entity.AboutContent;
import com.mk.recruitment.service.AboutService;

@Controller
public class AboutAction {
	@Autowired
	private AboutService aboutService;

	/**
	 * 得到关于华数的模块
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/getAboutById.do")
	@ResponseBody
	public About getAboutById(String id) throws Exception {
		return aboutService.getAboutById(id);
	}

	/**
	 * 保存or修改选项类型
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/recruitment/saveOrUpdateAboutContent.do")
	@ResponseBody
	public AboutContent saveOrUpdateAboutContent(AboutContent model) {
		aboutService.saveOrUpdateAboutContent(model);
		return model;
	}

	/**
	 * 得到关于华数的模块
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/getAboutContentById.do")
	@ResponseBody
	public AboutContent getAboutContentById(String id) throws Exception {
		return aboutService.getAboutContentById(id);
	}

}
