package com.mk.audition.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.audition.entity.AuditionAddress;
import com.mk.audition.service.AuditionAddressService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;

@Controller
public class AuditionAddressAction {

	@Autowired
	private AuditionAddressService auditionAddressService;
	
	
	/**
	 * 得到面试地点树
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/auditionaddress/buildAllAuditionAddress.do")
	@ResponseBody
	public List<TreeNode> buildAllAuditionAddress() throws Exception {
		return auditionAddressService.buildAllAuditionAddress();
	}
	

	/**
	 * 保存 修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/audition/saveOrUpdateAuditionAddress.do")
	@ResponseBody
	public AuditionAddress saveOrUpdateAuditionAddress(AuditionAddress model) {
		auditionAddressService.saveOrUpdateAuditionAddress(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/audition/searchAuditionAddress.do")
	public void searchAuditionAddress(HttpServletRequest request, HttpServletResponse response) {
		GridServerHandler grid = new GridServerHandler(request, response);
		auditionAddressService.searchAuditionAddress(grid);
		grid.printLoadResponseText();
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/audition/getAuditionAddressById.do")
	@ResponseBody
	public AuditionAddress getAuditionAddressById(String id) {
		return auditionAddressService.getAuditionAddressById(id);
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@RequestMapping("/audition/delAuditionAddressById.do")
	@ResponseBody
	public void delAuditionAddressById(String ids) {
		auditionAddressService.delAuditionAddressById(ids);
	}

	/**
	 * 失效与预定
	 * 
	 * @param ids
	 * @param state
	 */
	@RequestMapping("/audition/validContractById.do")
	@ResponseBody
	public void validContractById(String ids, Integer state) {
		auditionAddressService.validContractById(ids, state);
	}

}
