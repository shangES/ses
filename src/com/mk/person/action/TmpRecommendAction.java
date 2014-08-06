package com.mk.person.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.grid.util.StringUtils;
import com.mk.person.entity.TmpRecommend;
import com.mk.person.service.TmpRecommendService;

@Controller
public class TmpRecommendAction {
	@Autowired
	private TmpRecommendService tmpRecommendService;
	
	
	/**
	 * 推荐信息渲染
	 * 
	 * @param map
	 * @param mycandidatesguid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myperson/getTmpRecommendListHtml.do")
	public ModelAndView getTmpRecommendListHtml(ModelMap map, String mycandidatesguid) throws Exception {
		if (StringUtils.isEmpty(mycandidatesguid))
			return null;
		List<TmpRecommend> list = tmpRecommendService.getTmpRecommendListByMycandidateGuid(mycandidatesguid);
		map.put("tmpRecommendlist", list);
		return new ModelAndView("/pages/person/themes/list_tmprecommend.jsp");
	}

	/**
	 * 推荐信息添加
	 * 
	 * @param map
	 * @param mycandidatesguid
	 * @param ordernum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myperson/getTmpRecommendHtml.do")
	public ModelAndView getTmpRecommendHtml(ModelMap map, String mycandidatesguid, Integer ordernum) throws Exception {
		if (StringUtils.isEmpty(mycandidatesguid))
			return null;

		map.put("mycandidatesguid", mycandidatesguid);
		map.put("ordernum", ordernum);
		
		return new ModelAndView("/pages/person/form_tmprecommend.jsp");
	}

	/**
	 * 保存或修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/myperson/saveOrUpdateTmpRecommend.do")
	@ResponseBody
	public TmpRecommend saveOrUpdateTmpRecommend(TmpRecommend model) {
		tmpRecommendService.saveOrUpdateTmpRecommend(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/myperson/delTmpRecommendById.do")
	@ResponseBody
	public void delTmpRecommendById(String ids) throws Exception {
		tmpRecommendService.delTmpRecommendById(ids);
	}
}
