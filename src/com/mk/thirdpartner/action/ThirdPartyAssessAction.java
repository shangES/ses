package com.mk.thirdpartner.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.thirdpartner.entity.ThirdPartyAssess;
import com.mk.thirdpartner.service.ThirdPartyAssessService;

@Controller
public class ThirdPartyAssessAction {

	@Autowired
	private ThirdPartyAssessService thirdPartyAssessService;

	/**
	 * 得到
	 * 
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/thirdpartyassess/getAllThirdPartyAssess.do")
	public void getAllThirdPartyAssess(HttpServletRequest request, HttpServletResponse response) {
		GridServerHandler grid = new GridServerHandler(request, response);

		if (StringUtils.isEmpty(grid.getPageParameter("thirdpartnerguid")))
			return;

		thirdPartyAssessService.getAllThirdPartyAssess(grid);
		grid.printLoadResponseText();
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/thirdpartyassess/getThirdPartyAssessById.do")
	@ResponseBody
	public ThirdPartyAssess getThirdPartyAssessById(String id) {
		return thirdPartyAssessService.getThirdPartyAssessById(id);
	}

	/**
	 * 修改 保存
	 * 
	 * @param model
	 * @param thirdpartnerguid
	 * @return
	 */
	@RequestMapping("/thirdpartyassess/saveOrUpThirdPartyAssess.do")
	@ResponseBody
	public ThirdPartyAssess saveOrUpThirdPartyAssess(ThirdPartyAssess model) {
		thirdPartyAssessService.saveOrUpThirdPartyAssess(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@RequestMapping("/thirdpartyassess/delThirdPartyAssessById.do")
	@ResponseBody
	public void delThirdPartyAssessById(String ids) {
		thirdPartyAssessService.delThirdPartyAssessById(ids);
	}

	/**
	 * 等级通过
	 * 
	 * @param id
	 */
	@RequestMapping("/thirdpartyassess/setThirdPartyAssessLevel.do")
	@ResponseBody
	public void setThirdPartyAssessLevel(String ids) {
		thirdPartyAssessService.setThirdPartyAssessLevel(ids);
	}

	/**
	 * 机构用户树
	 * 
	 * @return
	 */
	@RequestMapping("/thirdpartyassess/buildThirdPartnerWebUserCheckTree.do")
	@ResponseBody
	public List<TreeNode> buildThirdPartnerWebUserCheckTree() {
		return thirdPartyAssessService.buildThirdPartnerWebUserCheckTree();
	}

	/**
	 * 检验年度
	 * 
	 * @param num
	 * @return
	 */
	@RequestMapping("thirdpartyassess/checkYear.do")
	@ResponseBody
	public String checkYear(Integer num, String thirdpartyassessguid,String thirdpartnerguid) {
		return thirdPartyAssessService.checkYear(num, thirdpartyassessguid,thirdpartnerguid);
	}
}
