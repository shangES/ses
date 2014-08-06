package com.mk.department.action;

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

import com.mk.department.entity.Department;
import com.mk.department.service.DepartmentService;
import com.mk.framework.constance.Constance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.model.ColumnInfo;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.tree.TreeNode;

@Controller
public class DepartmentAction {

	@Autowired
	private DepartmentService departmentService;

	/**
	 * 导出部门
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("department/exportDepartment.do")
	public void exportDepartment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		List<Department> list = departmentService.getHasDepartment(Constance.VALID_YES);

		// 参数设置
		Map<Object, Object> parameterMap = new HashMap<Object, Object>();
		parameterMap.put("exportFileName", new String[] { "部门列表.xls" });
		grid.setParameterMap(parameterMap);

		// 列
		List<ColumnInfo> columns = Department.initExcelColumn();
		grid.setColumnInfo(columns);

		// 数据
		List<JSONObject> data = new ArrayList<JSONObject>();
		for (Department model : list) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
		ReportUtil report = new ReportUtil();
		report.setTitle("部门列表");
		report.reportGrid(grid);
	}

	/**
	 * 全部有权限的
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/department/buildMyDepartmentTree.do")
	@ResponseBody
	public List<TreeNode> buildMyDepartmentTree(Integer valid) throws Exception {
		return departmentService.buildMyDepartmentTree(valid);
	}
	
	/**
	 * 单个公司下的部门树(多选)
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/department/buildMyMultipleDepartmentTree.do")
	@ResponseBody
	public List<TreeNode> buildMyMultipleDepartmentTree(String companyid) throws Exception {
		return departmentService.buildMyMultipleDepartmentTree(companyid);
	}
	
	
	/**
	 * 单个公司下的部门树(单选)
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/department/buildOneCompanyDeptTree.do")
	@ResponseBody
	public List<TreeNode> buildOneCompanyDeptTree(String companyid) throws Exception {
		return departmentService.buildOneCompanyDeptTree(companyid);
	}
	
	
	/**
	 * 二级部门树(单选)
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/department/buildPDeptTree.do")
	@ResponseBody
	public List<TreeNode> buildPDeptTree(String deptid) throws Exception {
		return departmentService.buildPDeptTree(deptid);
	}


	/**
	 * 保存
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/department/saveOrUpdateDepartment.do")
	@ResponseBody
	public Department saveOrUpdateDepartment(Department model) throws Exception {
		departmentService.saveOrUpdateDepartment(model);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping("/department/delDepartmentByDepartmentId.do")
	@ResponseBody
	public void delDepartmentByDepartmentId(String id) throws Exception {
		departmentService.delDepartmentByDepartmentCode(id);
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/department/getDepartmentById.do")
	@ResponseBody
	public Department getDepartmentById(String id) throws Exception {
		Department data = departmentService.getDepartmentById(id);
		return data;
	}

	/**
	 * 有效无效
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/department/validDepartmentById.do")
	@ResponseBody
	public void validDepartmentById(String Departmentid, Integer valid) throws Exception {
		departmentService.validDepartmentById(Departmentid, valid);
	}

	/**
	 * 公司有部门
	 * 
	 * @param pid
	 * @throws Exception
	 */
	@RequestMapping("/department/getDepartmentByCompanyCode.do")
	@ResponseBody
	public boolean getDepartmentByCompanyCode(String companycode) throws Exception {
		return departmentService.getDepartmentByCompanyCode(companycode);
	}
	
	
	
	
	
	/**
	 * 排序
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/department/orderDepartment.do")
	@ResponseBody
	public void orderDepartment(String id, String targetid, String moveType) throws Exception {
		departmentService.orderDepartment(id, targetid, moveType);
	}

}
