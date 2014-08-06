package com.mk.person.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.framework.grid.util.StringUtils;
import com.mk.person.entity.TmpTrain;
import com.mk.person.service.TmpTrainService;

@Controller
public class TmpTrainAction {

	@Autowired
	private TmpTrainService tmpTrainService;

	/**
	 * 培训经历渲染
	 * 
	 * @param map
	 * @param mycandidatesguid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/person/getTmpTrainListHtml.do")
	public ModelAndView getTmpTrainListHtml(ModelMap map, String mycandidatesguid) throws Exception {
		if (StringUtils.isEmpty(mycandidatesguid))
			return null;
		List<TmpTrain> list = tmpTrainService.getTmpTrainListByMycandidateGuid(mycandidatesguid);
		map.put("tmptrainlist", list);
		return new ModelAndView("/pages/person/themes/list_tmptrain.jsp");
	}

	/**
	 * 培训经历添加
	 * 
	 * @param map
	 * @param mycandidatesguid
	 * @param ordernum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/person/getTmpTrainHtml.do")
	public ModelAndView getWorkExperienceHtml(ModelMap map, String mycandidatesguid, Integer ordernum) throws Exception {
		if (StringUtils.isEmpty(mycandidatesguid))
			return null;

		map.put("mycandidatesguid", mycandidatesguid);
		map.put("ordernum", ordernum);
		return new ModelAndView("/pages/person/form_tmptrain.jsp");
	}

	/**
	 * 保存或修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/person/saveOrUpdateTmpTrain.do")
	@ResponseBody
	public TmpTrain saveOrUpdateTmpTrain(TmpTrain model) {
		tmpTrainService.saveOrUpdateTmpTrain(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@RequestMapping("/person/delTmpTrainByTrainId.do")
	@ResponseBody
	public void delTmpTrainByTrainId(String ids) throws Exception {
		tmpTrainService.delTmpTrainByTrainId(ids);
	}
}
