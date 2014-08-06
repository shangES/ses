package com.mk.contract.action;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.contract.entity.Contract;
import com.mk.contract.service.ContractService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.grid.util.StringUtils;

@Controller
public class ContractAction {
	@Autowired
	private ContractService contractService;

	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/contract/searchContract.do")
	public void searchContract(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		contractService.searchContract(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("合同信息表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();

	}

	/**
	 * 批量保存
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/contract/saveContractGrid.do")
	public void saveContractGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		String msg = contractService.saveContractGrid(grid);
		if (StringUtils.isNotEmpty(msg))
			grid.printResponseText(msg);
	}

	/**
	 * 保存
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/contract/saveOrUpdateContract.do")
	@ResponseBody
	public Contract saveOrUpdateContract(Contract model) throws Exception {
		contractService.saveOrUpdateContract(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/contract/getContractById.do")
	@ResponseBody
	public Contract getContractById(String id) throws Exception {
		Contract model = contractService.getContractById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/contract/delContractById.do")
	@ResponseBody
	public void delContractById(String ids) throws Exception {
		contractService.delContractById(ids);
	}

	/**
	 * 失效/还原
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/contract/validContractById.do")
	@ResponseBody
	public void validContractById(String ids, Integer state) throws Exception {
		contractService.validContractById(ids, state);
	}
	
	/**
	 * 终止合同
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/contract/endContractById.do")
	@ResponseBody
	public void endContractById(String ids, Integer state,Date enddate,String memo) throws Exception {
		contractService.endContractById(ids, state,enddate, memo);
	}
}
