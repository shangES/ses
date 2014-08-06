package com.mk.company.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.company.entity.Company;
import com.mk.company.service.CompanyService;
import com.mk.company.tree.CompanyTree;
import com.mk.framework.constance.Constance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.model.ColumnInfo;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.tree.TreeNode;

@Controller
public class CompanyAction {
	@Autowired
	private CompanyService companyService;

	/**
	 * 导出公司
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/company/exportCompany.do")
	public void exportCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		List<Company> list = companyService.getHasCompanys(Constance.VALID_YES);

		// 参数设置
		Map<Object, Object> parameterMap = new HashMap<Object, Object>();
		parameterMap.put("exportFileName", new String[] { "公司列表.xls" });
		grid.setParameterMap(parameterMap);

		// 列
		List<ColumnInfo> columns = Company.initExcelColumn();
		grid.setColumnInfo(columns);

		// 数据
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Company model : list) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);

		ReportUtil report = new ReportUtil();
		report.setTitle("公司列表");
		report.reportGrid(grid);
	}

	/**
	 * 加载全部公司
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/company/buildAllCompanyTree.do")
	@ResponseBody
	public List<TreeNode> buildAllCompanyTree(Integer valid) throws Exception {
		List<Company> data = companyService.getAllCompanys(valid);
		CompanyTree tree = new CompanyTree();
		return tree.doBuild(data);
	}

	/**
	 * 加载有权公司
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/company/buildMyHasCompanyTree.do")
	@ResponseBody
	public List<TreeNode> buildMyHasCompanyTree(Integer valid) throws Exception {
		List<Company> data = companyService.getHasCompanys(valid);
		CompanyTree tree = new CompanyTree();
		return tree.doBuild(data);
	}

	/**
	 * 加载有权限的
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/company/buildCompanyTree.do")
	@ResponseBody
	public List<TreeNode> buildCompanyTree(Integer valid) throws Exception {
		List<Company> data = companyService.getHasCompanys(valid);
		CompanyTree tree = new CompanyTree();
		return tree.doBuild(data);
	}

	/**
	 * 保存
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/company/saveOrUpdateCompany.do")
	@ResponseBody
	public Company saveOrUpdateCompany(Company model) throws Exception {
		companyService.saveOrUpdateCompany(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping("/company/delCompanyByCompanyCode.do")
	@ResponseBody
	public void delCompanyByCompanyCode(String id) throws Exception {
		companyService.delCompanyByCompanyCode(id);
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/company/getCompanyById.do")
	@ResponseBody
	public Company getCompanyById(String id) throws Exception {
		Company data = companyService.getCompanyById(id);
		return data;
	}

	/**
	 * 有效无效
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/company/validCompanyById.do")
	@ResponseBody
	public void validCompanyById(String companyid, Integer valid) throws Exception {
		companyService.validCompanyById(companyid, valid);
	}

	/**
	 * 排序
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/company/orderCompany.do")
	@ResponseBody
	public void orderCompany(String id, String targetid, String moveType) throws Exception {
		companyService.orderCompany(id, targetid, moveType);
	}

}
