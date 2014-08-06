package com.mk.person.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.grid.util.StringUtils;
import com.mk.person.entity.TmpFamily;
import com.mk.person.service.TmpFamilyService;


@Controller
public class TmpFamilyAction {
	@Autowired
	private TmpFamilyService tmpFamilyService;
	
	
	
	/**
	 * 家属状况渲染
	 * 
	 * @param map
	 * @param mycandidatesguid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myperson/getTmpFamilyListHtml.do")
	public ModelAndView getTmpFamilyListHtml(ModelMap map, String mycandidatesguid) throws Exception {
		if (StringUtils.isEmpty(mycandidatesguid))
			return null;
		List<TmpFamily> list = tmpFamilyService.getTmpFamilyListByMycandidateGuid(mycandidatesguid);
		map.put("tmpFamilylist", list);
		return new ModelAndView("/pages/person/themes/list_tmpfamily.jsp");
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
	@RequestMapping("/myperson/getTmpFamilyHtml.do")
	public ModelAndView getTmpFamilyHtml(ModelMap map, String mycandidatesguid, Integer ordernum) throws Exception {
		if (StringUtils.isEmpty(mycandidatesguid))
			return null;

		map.put("mycandidatesguid", mycandidatesguid);
		map.put("ordernum", ordernum);
		
		return new ModelAndView("/pages/person/form_tmpfamily.jsp");
	}

	/**
	 * 保存或修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/myperson/saveOrUpdateTmpFamily.do")
	@ResponseBody
	public TmpFamily saveOrUpdateTmpFamily(TmpFamily model) {
		tmpFamilyService.saveOrUpdateTmpFamily(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/myperson/delTmpFamilyById.do")
	@ResponseBody
	public void delTmpFamilyById(String ids) throws Exception {
		tmpFamilyService.delTmpFamilyById(ids);
	}
}
