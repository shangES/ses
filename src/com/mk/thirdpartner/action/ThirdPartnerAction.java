package com.mk.thirdpartner.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.tree.TreeNode;
import com.mk.thirdpartner.entity.ThirdPartner;
import com.mk.thirdpartner.service.ThirdPartnerService;

@Controller
public class ThirdPartnerAction {

	@Autowired
	private ThirdPartnerService thirdPartnerService;

	/**
	 * 修改 保存
	 * 
	 * @param model
	 */
	@RequestMapping("/thirdpartner/saveOrUpdateThirPartner.do")
	@ResponseBody
	public ThirdPartner saveOrupdateThirdPartner(ThirdPartner model) {
		thirdPartnerService.saveOrUpdateThirdPartner(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param guid
	 * @return
	 */
	@RequestMapping("/thirdpartner/getThirdPartnerById.do")
	@ResponseBody
	public ThirdPartner getThirdPartnerById(String id) {
		return thirdPartnerService.getThirdPartnerById(id);
	}

	/**
	 * 删除
	 * 
	 * @param guid
	 */
	@RequestMapping("/thirdpartner/delThirdPartnerById.do")
	@ResponseBody
	public void delThirdPartnerById(String id) {
		thirdPartnerService.delThirdPartnerById(id);
	}

	/**
	 * 机构树
	 * 
	 * @return
	 */
	@RequestMapping("/thirdpartner/buildThirdPartnerCheckTree.do")
	@ResponseBody
	public List<TreeNode> buildThirdPartnerCheckTree() {
		return thirdPartnerService.buildThirdPartnerCheckTree();
	}

	/**
	 * 体检机构树
	 * 
	 * @return
	 */
	@RequestMapping("/thirdpartner/buildPartnerCheckTree.do")
	@ResponseBody
	public List<TreeNode> buildPartnerCheckTree() {
		return thirdPartnerService.buildPartnerCheckTree();
	}
}
