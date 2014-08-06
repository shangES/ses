package com.mk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.entity.Department;

public class OrgPathUtil {

	/**
	 * 公司全路径
	 * 
	 * @param companyid
	 * @param mapper
	 * @return
	 */
	public static final Company getOneCompanyFullPath(String companyid, CompanyDao mapper) {
		Company model = mapper.getCompanyByCompanyId(companyid);
		if (model == null)
			return null;
		StringBuffer name = new StringBuffer();

		// 递归向上
		if (model.getPcompanyid() != null)
			topPath(name, model.getPcompanyid(), mapper);
		name.append(model.getCompanyname());

		model.setCompanyfullname(name.toString());
		return model;
	}

	// 递归向上
	private static final void topPath(StringBuffer name, String companyid, CompanyDao mapper) {
		Company model = mapper.getCompanyByCompanyId(companyid);
		if (model != null) {
			if (model.getPcompanyid() != null) {
				topPath(name, model.getPcompanyid(), mapper);
			}
			name.append(model.getCompanyname());
			name.append("/");
		}
	}

	/**
	 * 公司全路径
	 * 
	 * @param list
	 * @return
	 */
	public static final Map<String, Company> getAllCompanyFullPath(List<Company> list) {
		// 缓存
		Map<String, Company> cachMap = new HashMap<String, Company>();
		for (Company model : list) {
			cachMap.put(model.getCompanyid(), model);
		}

		// 转出全路径
		StringBuffer name = null;
		Map<String, Company> map = new HashMap<String, Company>();
		for (Company model : list) {
			name = new StringBuffer();
			topPath(name, model.getPcompanyid(), cachMap);
			name.append(model.getCompanyname());
			model.setCompanyfullname(name.toString());
			map.put(model.getCompanyid(), model);
		}

		return map;
	}

	// 递归向上
	private static final void topPath(StringBuffer name, String orgguid, Map<String, Company> map) {
		Company model = map.get(orgguid);
		if (model != null) {
			if (model.getPcompanyid() != null) {
				topPath(name, model.getPcompanyid(), map);
			}
			name.append(model.getCompanyname());
			name.append("/");
		}
	}

	// ========================================================

	/**
	 * 部门全路径
	 * 
	 * @param companyid
	 * @param mapper
	 * @return
	 */
	public static final Department getOneDepartmentFullPath(String deptid, DepartmentDao mapper) {
		Department model = mapper.getDepartmentByDepartmentId(deptid);
		if (model == null)
			return null;
		StringBuffer name = new StringBuffer();

		// 递归向上
		if (model.getPdeptid() != null)
			topPath(name, model.getPdeptid(), mapper);
		name.append(model.getDeptname());

		model.setDeptfullname(name.toString());
		return model;
	}

	// 递归向上
	private static final void topPath(StringBuffer name, String deptid, DepartmentDao mapper) {
		Department model = mapper.getDepartmentByDepartmentId(deptid);
		if (model != null) {
			if (model.getPdeptid() != null) {
				topPath(name, model.getPdeptid(), mapper);
			}
			name.append(model.getDeptname());
			name.append("/");
		}
	}

	/**
	 * 全部部六的全路径
	 * 
	 * @param list
	 * @return
	 */
	public static Map<String, Department> getAllDepartmentFullPath(List<Department> list) {
		// 缓存
		Map<String, Department> cachMap = new HashMap<String, Department>();
		for (Department model : list) {
			cachMap.put(model.getDeptid(), model);
		}

		// 转出全路径
		StringBuffer name = null;
		Map<String, Department> map = new HashMap<String, Department>();
		for (Department model : list) {
			name = new StringBuffer();
			topDeptPath(name, model.getPdeptid(), cachMap);
			name.append(model.getDeptname());
			model.setDeptfullname(name.toString());
			map.put(model.getDeptid(), model);
		}

		return map;
	}

	private static void topDeptPath(StringBuffer name, String deptguid, Map<String, Department> map) {
		Department model = map.get(deptguid);
		if (model != null) {
			if (model.getPdeptid() != null) {
				topDeptPath(name, model.getPdeptid(), map);
			}
			name.append(model.getDeptname());
			name.append("/");
		}
	}

}
