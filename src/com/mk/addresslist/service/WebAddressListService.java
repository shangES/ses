package com.mk.addresslist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.addresslist.dao.WebAddressListDao;
import com.mk.addresslist.entity.WebAddressList;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.company.service.CompanyService;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.department.service.DepartmentService;
import com.mk.department.tree.DepartmentTree;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.system.dao.OptionDao;

@Service
public class WebAddressListService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private CodeConvertNameService codeConvertNameService;

	/**
	 * 具有权限的公司部门树
	 * 
	 * @return
	 */
	public List<TreeNode> buildMyDepartmentTree() {
		// 数据
		List<Company> compays = companyService.getHasWebCompanys(Constance.VALID_YES);
		List<Department> depts = departmentService.getHasWebDepartment(Constance.VALID_YES);

		// 成树
		DepartmentTree tree = new DepartmentTree();
		return tree.doBuild(compays, depts);
	}

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	public void searchWebAddressList(GridServerHandler grid) {
		WebAddressListDao mapper = sqlSession.getMapper(WebAddressListDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = mapper.countWebAddressList(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<WebAddressList> list = mapper.searchWebAddressList(grid);
		// 批量
		if (list.size() > Constance.ConvertCodeNum) {
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);

			for (WebAddressList model : list) {
				model.convertManyCodeToName(companyMap, deptMap, postMap, sexMap);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (WebAddressList model : list) {
				model.convertOneCodeToName(companyDao, departmentDao, postDao, optionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public WebAddressList getWebAddressListById(String id) {
		WebAddressListDao mapper = sqlSession.getMapper(WebAddressListDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		WebAddressList model = mapper.getWebAddressListById(id);
		if (model != null) {
			model.convertOneCodeToName(companyDao, departmentDao, postDao, optionDao);
		}

		return model;
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public WebAddressList getWebAddressListByUserId(String id) {
		WebAddressListDao mapper = sqlSession.getMapper(WebAddressListDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);

		// 根据登陆用户能取到多条通讯信息(兼任和占编)
		List<WebAddressList> list = mapper.getWebAddressListByUserId(id);
		if (!list.isEmpty()) {
			WebAddressList model = list.get(0);
			model.convertOneCodeToName(companyDao, departmentDao, postDao, optionDao);
			return model;

		}
		return null;
	}

	/**
	 * 得到
	 * 
	 * @param deptid
	 * 
	 * @param id
	 * @return
	 */
	public List<WebAddressList> getWebAddressListByDpetId(String id, String deptid) {
		WebAddressListDao mapper = sqlSession.getMapper(WebAddressListDao.class);
		return mapper.getWebAddressListByDpetId(id, deptid);
	}

	/**
	 * 修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateWebAddressList(WebAddressList model) {
		WebAddressListDao mapper = sqlSession.getMapper(WebAddressListDao.class);
		EmployeeDao eDao = sqlSession.getMapper(EmployeeDao.class);
		if (StringUtils.isNotEmpty(model.getEmployeeid())) {
			Employee employee = eDao.getEmployeeById(model.getEmployeeid());
			employee.setAddressmobile(model.getMobilephone());
			employee.setMobile2(model.getMobilephone2());
			employee.setOfficephone(model.getExtphone());
			employee.setEmail(model.getEmail());
			employee.setShortphone(model.getInnerphone());
			eDao.updateEmployee(employee);
		}
		mapper.updateWebAddressList(model);
	}

}
