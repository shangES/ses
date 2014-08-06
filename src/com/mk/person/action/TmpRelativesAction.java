package com.mk.person.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.grid.util.StringUtils;
import com.mk.person.entity.TmpRelatives;
import com.mk.person.service.TmpRelativesService;

@Controller
public class TmpRelativesAction {
	@Autowired
	private TmpRelativesService tmpRelativesService;

	/**
	 * 家属状况渲染
	 * 
	 * @param map
	 * @param mycandidatesguid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myperson/getTmpRelativesListHtml.do")
	public ModelAndView getTmpRelativesListHtml(ModelMap map, String mycandidatesguid) throws Exception {
		if (StringUtils.isEmpty(mycandidatesguid))
			return null;
		List<TmpRelatives> list = tmpRelativesService.getTmpRelativesListByMycandidateGuid(mycandidatesguid);
		map.put("tmpRelativeslist", list);
		return new ModelAndView("/pages/person/themes/list_tmprelatives.jsp");
	}

	/**
	 * 家属状况添加
	 * 
	 * @param map
	 * @param mycandidatesguid
	 * @param ordernum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myperson/getTmpRelativesHtml.do")
	public ModelAndView getTmpRelativesHtml(ModelMap map, String mycandidatesguid, Integer ordernum) throws Exception {
		if (StringUtils.isEmpty(mycandidatesguid))
			return null;

		map.put("mycandidatesguid", mycandidatesguid);
		map.put("ordernum", ordernum);

		return new ModelAndView("/pages/person/form_tmprelatives.jsp");
	}

	/**
	 * 保存或修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/myperson/saveOrUpdateTmpRelatives.do")
	@ResponseBody
	public TmpRelatives saveOrUpdateTmpRelatives(TmpRelatives model) {
		tmpRelativesService.saveOrUpdateTmpRelatives(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/myperson/delTmpRelativesById.do")
	@ResponseBody
	public void delTmpRelativesById(String ids) throws Exception {
		tmpRelativesService.delTmpRelativesById(ids);
	}
}
