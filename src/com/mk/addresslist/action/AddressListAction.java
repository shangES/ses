package com.mk.addresslist.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.addresslist.entity.AddressList;
import com.mk.addresslist.service.AddressListService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.grid.util.StringUtils;

@Controller
public class AddressListAction {
	@Autowired
	private AddressListService addressListService;

	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/addresslist/searchAddressList.do")
	public void searchAddressList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		addressListService.searchAddressList(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("通讯录信息表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();

	}

	/**
	 * 批量保存
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/addresslist/saveAddressListGrid.do")
	public void saveAddressListGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		String msg = addressListService.saveAddressListGrid(grid);
		if (StringUtils.isNotEmpty(msg))
			grid.printResponseText(msg);
	}

	/**
	 * 保存
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/addresslist/saveOrUpdateAddressList.do")
	@ResponseBody
	public AddressList saveOrUpdateAddressList(AddressList model) throws Exception {
		addressListService.saveOrUpdateAddressList(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/addresslist/getAddressListById.do")
	@ResponseBody
	public AddressList getAddressListById(String id) throws Exception {
		AddressList model = addressListService.getAddressListById(id);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/addresslist/getAddressListByEmployeeId.do")
	@ResponseBody
	public AddressList getAddressListByEmployeeId(String id) throws Exception {
		AddressList model = addressListService.getAddressListByEmployeeId(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/addresslist/delAddressListById.do")
	@ResponseBody
	public void delAddressListById(String ids) throws Exception {
		addressListService.delAddressListById(ids);
	}

	/**
	 * 同步员工
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/addresslist/refreshAddressList.do")
	@ResponseBody
	public void refreshAddressList() throws Exception {
		addressListService.refreshAddressList();
	}
}
