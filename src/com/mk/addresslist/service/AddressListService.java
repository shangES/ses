package com.mk.addresslist.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.addresslist.dao.AddressListDao;
import com.mk.addresslist.entity.AddressList;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.Syslog;
import com.mk.system.service.SyslogService;

@Service
public class AddressListService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CodeConvertNameService codeConvertNameService;
	@Autowired
	private SyslogService syslogService;

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	public void searchAddressList(GridServerHandler grid) {
		AddressListDao mapper = sqlSession.getMapper(AddressListDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = mapper.countAddressList(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<AddressList> list = mapper.searchAddressList(grid);
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

			for (AddressList model : list) {
				model.convertManyCodeToName(mapper, companyMap, deptMap, postMap, sexMap);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (AddressList model : list) {
				model.convertOneCodeToName(mapper, companyDao, departmentDao, postDao, optionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}

	/**
	 * 批量保存
	 * 
	 * @param grid
	 * @return
	 */
	@Transactional
	public String saveAddressListGrid(GridServerHandler grid) {
		AddressListDao mapper = sqlSession.getMapper(AddressListDao.class);

		// 添加的行
		List<Object> addList = grid.getInsertedRecords(AddressList.class);
		if (!addList.isEmpty()) {
			for (Object obj : addList) {
				AddressList model = (AddressList) obj;
				model.setAddresslistguid(UUIDGenerator.randomUUID());
				model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				model.setRefreshtimestamp(new Timestamp(System.currentTimeMillis()));
				mapper.insertAddressList(model);
			}

			// 系统日志
			Syslog log = new Syslog("通讯录信息批量" + Constance.imp + "(" + addList.size() + " 条数据)");
			syslogService.saveOrUpdateSyslog(log);
		}

		if (addList.size() > 0)
			return "共 " + addList.size() + " 条数据，成功导入！";
		return null;
	}

	/**
	 * 保存
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateAddressList(AddressList model) {
		AddressListDao mapper = sqlSession.getMapper(AddressListDao.class);
		EmployeeDao eDao = sqlSession.getMapper(EmployeeDao.class);
		if (StringUtils.isEmpty(model.getAddresslistguid())) {
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			model.setRefreshtimestamp(new Timestamp(System.currentTimeMillis()));
			model.setAddresslistguid(UUIDGenerator.randomUUID());
			mapper.insertAddressList(model);

			// 系统日志
			Syslog log = new Syslog("通讯录信息" + Constance.add);
			syslogService.saveOrUpdateSyslog(log);
		} else {
			//改变员工信息
			Employee employee = eDao.getEmployeeById(model.getEmployeeid());
			employee.setAddressmobile(model.getMobilephone());
			employee.setMobile2(model.getMobilephone2());
			employee.setOfficephone(model.getExtphone());
			employee.setEmail(model.getEmail());
			employee.setShortphone(model.getInnerphone());
			eDao.updateEmployee(employee);
			//修改通讯录
			mapper.updateAddressList(model);

			// 系统日志
			Syslog log = new Syslog("通讯录信息" + Constance.edit);
			syslogService.saveOrUpdateSyslog(log);
		}
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public AddressList getAddressListById(String id) {
		AddressListDao mapper = sqlSession.getMapper(AddressListDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		AddressList model = mapper.getAddressListById(id);
		if (model != null) {
			model.convertOneCodeToName(mapper, companyDao, departmentDao, postDao, optionDao);
		}
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public AddressList getAddressListByEmployeeId(String id) {
		AddressListDao mapper = sqlSession.getMapper(AddressListDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);

		List<AddressList> list = mapper.getAddressListByEmployeeId(id);
		if (!list.isEmpty()) {
			AddressList model = list.get(0);
			model.convertOneCodeToName(mapper, companyDao, departmentDao, postDao, optionDao);
			return model;
		}
		return null;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delAddressListById(String ids) {
		AddressListDao mapper = sqlSession.getMapper(AddressListDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delAddressListById(id);
		}

		// 系统日志
		Syslog log = new Syslog("通讯录信息" + Constance.del);
		syslogService.saveOrUpdateSyslog(log);
	}

	/**
	 * 同步员工
	 * 
	 */
	@Transactional
	public void refreshAddressList() {
		AddressListDao mapper = sqlSession.getMapper(AddressListDao.class);
		mapper.delAddressListByEmployeeId();
		mapper.insertAddressListByEmployee();

		// 系统日志
		Syslog log = new Syslog("通讯录信息" + Constance.refresh);
		syslogService.saveOrUpdateSyslog(log);
	}

}
