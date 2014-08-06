package com.mk.employee.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.employee.BirthdayUtil;
import com.mk.employee.entity.DelEmployeePam;
import com.mk.employee.entity.Employee;
import com.mk.employee.service.EmployeeService;
import com.mk.employee.tree.EmployeeCardnoTree;
import com.mk.framework.constance.Constance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.spring.SpringContextHolder;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.tree.TreePageGrid;

@Controller
public class EmployeeAction {
	@Autowired
	private EmployeeService employeeService;

	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/searchEmployee.do")
	public void searchEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		// 年龄转生日=========
		BirthdayUtil.fromBirthdayToAge(grid);

		employeeService.searchEmployee(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("员工信息表");
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
	@RequestMapping("/employee/saveEmployeeGrid.do")
	public void saveEmployeeGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		String msg = employeeService.saveEmployeeGrid(grid);
		if (StringUtils.isNotEmpty(msg))
			grid.printResponseText(msg);
	}

	/**
	 * 保存
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/saveOrUpdateEmployee.do")
	@ResponseBody
	public Employee saveOrUpdateEmployee(Employee model) throws Exception {
		employeeService.saveOrUpdateEmployee(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/getEmployeeById.do")
	@ResponseBody
	public Employee getEmployeeById(HttpServletRequest request,String id, String postionguid) throws Exception {
		Employee model = employeeService.getEmployeeById(id, postionguid);
		String str = this.changePath(request, model.getPhoto());
		model.setPhoto(str);
		return model;
	}
	
	
	
	
	
	
	/**
	 * 选择图片下载路径，不在内网，到外网查找
	 * 
	 * @param request
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String changePath(HttpServletRequest request,String path) throws IOException{
		String tempPath = "";
		if(path==null)
			return null;
		//例子：http://localhost:port/project/filepath
		//  http://localhost:port
		String physicalpath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		String realphysicalpath = SpringContextHolder.getApplicationContext().getResource("/").getFile().getAbsolutePath() + "/";
		//file.toString===http://localhost:port/project/filepath
		File file = new File(realphysicalpath+path);
		if(file.exists()){
			//内网路径查找
			//项目名：/project/
			String filepath = physicalpath+"/"+request.getSession().getServletContext().getContextPath()+"/";
			tempPath = filepath+path;
		}else{
			String outerfilepath = realphysicalpath.replaceAll(request.getSession().getServletContext().getContextPath().substring(1, request.getSession().getServletContext().getContextPath().length()), Constance.PROJECTNAMEOUTER);
			File outerfile = new File(outerfilepath+path);
			if(outerfile.exists()){
				tempPath = physicalpath+"/"+Constance.PROJECTNAMEOUTER+"/"+path;
			}else
				tempPath=null;
		}
		
		return tempPath;
	}
	
	
	
	
	
	
	

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/delEmployeeById.do")
	@ResponseBody
	public void delEmployeeById(@RequestBody DelEmployeePam pam) throws Exception {
		employeeService.delEmployeeById(pam.getList());
	}

	/**
	 * 员工树
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/searchEmployeeTree.do")
	@ResponseBody
	public TreePageGrid searchEmployeeTree(@RequestBody TreePageGrid grid) {
		List<Employee> data = employeeService.searchEmployeeTree(grid);
		EmployeeCardnoTree tree = new EmployeeCardnoTree();
		List<TreeNode> nodes = tree.doBuild(data);
		grid.setDataList(nodes);
		return grid;
	}

	/**
	 * 用户树
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/searchUserTree.do")
	@ResponseBody
	public TreePageGrid searchUserTree(@RequestBody TreePageGrid grid) {
		List<Employee> data = employeeService.searchUserTree(grid);
		EmployeeCardnoTree tree = new EmployeeCardnoTree();
		List<TreeNode> nodes = tree.doBuildUserTree(data);
		grid.setDataList(nodes);
		return grid;
	}

	/**
	 * 检验工号重复
	 * 
	 * @param employeeid
	 * @param jobnumber
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/employee/checkEmployeeByJobnumber.do")
	@ResponseBody
	public String checkEmployeeByJobnumber(String employeeid, String jobnumber) throws Exception {
		return employeeService.checkEmployeeByJobnumber(employeeid, jobnumber);
	}

	/**
	 * 检验身份证重复
	 * 
	 * @param employeeid
	 * @param jobnumber
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/employee/checkEmployeeByCardno.do")
	@ResponseBody
	public String checkEmployeeByCardno(String employeeid, String cardnumber) throws Exception {
		return employeeService.checkEmployeeByCardno(employeeid, cardnumber);
	}

}
