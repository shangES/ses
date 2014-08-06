package com.mk.addresslist.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.addresslist.entity.WebAddressList;
import com.mk.addresslist.service.WebAddressListService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.tree.TreeNode;

@Controller
public class WebAddressListAction {

	@Autowired
	private WebAddressListService webAddressListService;

	
	/**
	 * 具有权限的公司部门树
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/webaddresslist/buildMyDepartmentTree.do")
	@ResponseBody
	public List<TreeNode> buildMyDepartmentTree() throws Exception {
		return webAddressListService.buildMyDepartmentTree();
	}

	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/webaddresslist/searchWebAddressList.do")
	public void searchWebAddressList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		webAddressListService.searchWebAddressList(grid);
		grid.printLoadResponseText();
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/webaddresslist/getWebAddressListByUserId.do")
	@ResponseBody
	public WebAddressList getWebAddressListByUserId(String id) throws Exception {
		WebAddressList model = webAddressListService.getWebAddressListByUserId(id);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/webaddresslist/getWebAddressListById.do")
	@ResponseBody
	public WebAddressList getWebAddressListById(String id) throws Exception {
		WebAddressList model = webAddressListService.getWebAddressListById(id);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/webaddresslist/getWebAddressListByDpetId.do")
	@ResponseBody
	public List<WebAddressList> getWebAddressListByDpetId(String id, String deptid) throws Exception {
		return webAddressListService.getWebAddressListByDpetId(id, deptid);
	}

	/**
	 * 修改
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/webaddresslist/saveOrUpdateWebAddressList.do")
	@ResponseBody
	public WebAddressList saveOrUpdateWebAddressList(WebAddressList model) throws Exception {
		webAddressListService.saveOrUpdateWebAddressList(model);
		return model;
	}
}
