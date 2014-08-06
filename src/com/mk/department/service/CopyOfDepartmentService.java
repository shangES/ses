package com.mk.department.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.addresslist.service.AddressListService;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.company.service.CompanyService;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.tree.DepartmentTree;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.Syslog;
import com.mk.system.service.SyslogService;

@Service
public class CopyOfDepartmentService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CodeConvertNameService codeConvertNameService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private SyslogService syslogService;
	@Autowired
	private AddressListService addressListService;

	/**
	 * 部门树
	 * 
	 * @return
	 */
	public List<TreeNode> buildMyDepartmentTree(Integer state) {
		// 数据
		List<Company> compays = companyService.getHasCompanys(Constance.VALID_YES);
		List<Department> depts = getHasDepartment(state);

		// 成树
		DepartmentTree tree = new DepartmentTree();
		return tree.doBuild(compays, depts);
	}

	/**
	 * 公司下的部门树(多选)
	 * 
	 * @param companyid
	 * @param valid
	 * @return
	 */
	public List<TreeNode> buildMyMultipleDepartmentTree(String companyid) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);

		// 公司信息
		Company company = mapper.getCompanyByCompanyId(companyid);
		if (company == null)
			return null;

		// 部门信息
		List<Department> depts = departmentDao.getDepartmentByCompanyId(companyid);
		// 成树
		DepartmentTree tree = new DepartmentTree();
		return tree.doMultipleDepartmentTreeBuild(company, depts);
	}

	/**
	 * 公司下的部门树(单选)
	 * 
	 * @param companyid
	 * @param valid
	 * @return
	 */
	public List<TreeNode> buildOneCompanyDeptTree(String companyid) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);

		// 公司信息
		Company company = mapper.getCompanyByCompanyId(companyid);
		if (company == null)
			return null;

		// 部门信息
		List<Department> depts = departmentDao.getDepartmentByCompanyId(companyid);
		// 成树
		DepartmentTree tree = new DepartmentTree();
		return tree.doOneCompanyDeptBuild(company, depts);
	}

	/**
	 * 二级部门树
	 * 
	 * @param companyid
	 * @return
	 */
	public List<TreeNode> buildPDeptTree(String deptid) {
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);

		// 部门信息
		Department dept = departmentDao.getDepartmentByDepartmentId(deptid);
		if (dept == null)
			return null;

		// 部门信息
		List<Department> depts = departmentDao.getDepartmentByPDeptid(dept.getCompanyid(), deptid);
		if (depts.isEmpty()) {
			return null;
		}

		// 成树
		DepartmentTree tree = new DepartmentTree();
		return tree.buildPDeptTree(dept, depts);
	}

	/**
	 * 保存
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateDepartment(Department model) {
		DepartmentDao mapper = sqlSession.getMapper(DepartmentDao.class);
		model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isEmpty(model.getDeptid())) {
			// 统计maxcode
			String pid = model.getPdeptid();
			String maxcode = mapper.getMaxDepartmentCodeByPDepartmentId(pid);
			Department parent = null;
			if (StringUtils.isNotEmpty(pid))
				parent = mapper.getDepartmentByDepartmentId(pid);
			model.setDeptcode(UUIDGenerator.format0000_ID(parent == null ? null : parent.getDeptcode(), maxcode, 1));
			model.setDeptid(UUIDGenerator.randomUUID());
			mapper.insertDepartment(model);

		} else {
			mapper.updateDepartment(model);
		}
	}

	/**
	 * 得到
	 * 
	 * @param Departmentid
	 * @return
	 */
	public Department getDepartmentById(String Departmentid) {
		DepartmentDao mapper = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		Department model = mapper.getDepartmentByDepartmentId(Departmentid);
		if (model != null)
			model.convertOneCodeToName(companyDao, optionDao);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	public void delDepartmentByDepartmentCode(String id) {
		DepartmentDao mapper = sqlSession.getMapper(DepartmentDao.class);
		PostDao post = sqlSession.getMapper(PostDao.class);
		Department model = mapper.getDepartmentByDepartmentId(id);
		if (model != null) {
			post.delPostByDeptcode(model.getDeptcode());
			mapper.delDepartmentByDepartmentCode(model.getDeptcode());
		}

		// 系统日志
		Syslog log = new Syslog("部门信息" + Constance.del);
		syslogService.saveOrUpdateSyslog(log);
	}

	/**
	 * 有效无效
	 * 
	 * @param Departmentid
	 * @param state
	 */
	@Transactional
	public void validDepartmentById(String Departmentid, Integer valid) {
		DepartmentDao mapper = sqlSession.getMapper(DepartmentDao.class);
		Department model = mapper.getDepartmentByDepartmentId(Departmentid);
		if (model != null) {
			model.setState(valid);
			mapper.updateDepartment(model);
		}
	}

	// =============================================
	/**
	 * 通讯录有权限的公司
	 * 
	 * @param validYes
	 * @return
	 */
	public List<Department> getHasWebDepartment(Integer state) {
		DepartmentDao mapper = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		UserContext uc = ContextFacade.getUserContext();

		List<Department> depts = null;
		if (uc.isAdmin() || uc == null || StringUtils.isEmpty(uc.getUserId())) {
			depts = mapper.getAllDepartment(state);
		} else
			depts = mapper.getHasWebDepartment(state, uc.getUserId());

		if (depts.size() > Constance.ConvertCodeNum) {
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部门类型
			Map<Integer, String> depttypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.H_O_DEPARTMENT_DEPTTYPE);
			// 体系
			Map<Integer, String> assesshierarchyMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.ASSESSHIERARCHY);

			for (Department model : depts) {
				model.convertManyCodeToName(companyMap, depttypeMap, assesshierarchyMap);
			}
		} else {
			for (Department model : depts) {
				model.convertOneCodeToName(companyDao, optionDao);
			}
		}

		return depts;
	}

	// =============================================

	/**
	 * 有权限的部门
	 * 
	 * @param Departmentid
	 * @return
	 */
	public List<Department> getHasDepartment(Integer state) {
		DepartmentDao mapper = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		UserContext uc = ContextFacade.getUserContext();

		List<Department> depts = null;
		if (uc.isAdmin()) {
			depts = mapper.getAllDepartment(state);
		} else
			depts = mapper.getHasDepartment(state, uc.getUserId());

		if (depts.size() > Constance.ConvertCodeNum) {
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部门类型
			Map<Integer, String> depttypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.H_O_DEPARTMENT_DEPTTYPE);
			// 体系
			Map<Integer, String> assesshierarchyMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.ASSESSHIERARCHY);
			for (Department model : depts) {
				model.convertManyCodeToName(companyMap, depttypeMap, assesshierarchyMap);
			}
		} else {
			for (Department model : depts) {
				model.convertOneCodeToName(companyDao, optionDao);
			}
		}

		return depts;
	}

	/**
	 * 公司有部门
	 * 
	 * @param companycode
	 * @return
	 */
	public boolean getDepartmentByCompanyCode(String companycode) {
		DepartmentDao mapper = sqlSession.getMapper(DepartmentDao.class);

		List<Department> list = mapper.getDepartmentByCompanyCode(companycode);
		if (!list.isEmpty())
			return true;
		return false;
	}

	// =======================================================
	/**
	 * 排序
	 * 
	 * @param id
	 * @param targetid
	 * @param moveType
	 */
	@Transactional
	public void orderDepartment(String id, String targetid, String moveType) {
		DepartmentDao mapper = sqlSession.getMapper(DepartmentDao.class);

		// ========================
		// 移动的节点
		Department selectNode = mapper.getDepartmentByDepartmentId(id);
		if (selectNode == null)
			return;
		// 移动的目标节点
		Department targetNode = mapper.getDepartmentByDepartmentId(targetid);
		if (targetNode == null)
			return;
		// 他们的上级节点
		Department parentNode = null;
		if (StringUtils.isNotEmpty(selectNode.getPdeptid()))
			parentNode = mapper.getDepartmentByDepartmentId(selectNode.getPdeptid());
		String parentCode = (parentNode != null ? parentNode.getDeptcode() : null);

		// ========================

		// 1：生成移动节点的code
		// 确定向上移还是向下移动
		int step = 0;
		String selectNewCode = null;
		if (moveType.equals("next")) {
			step = 1;
			selectNewCode = createNetCode(targetNode, step);
		} else {
			selectNewCode = targetNode.getDeptcode();
		}

		// 2:找到所有影响的node
		List<Department> list = mapper.getDepartmentByDepartmentCode(parentCode);

		Map<String, List<Department>> deptMap = new HashMap<String, List<Department>>();
		for (Department model : list) {
			List<Department> tmplist = deptMap.get(model.getPdeptid());
			if (tmplist == null) {
				tmplist = new ArrayList<Department>();
				tmplist.add(model);
				deptMap.put(model.getPdeptid(), tmplist);
			} else {
				tmplist.add(model);
			}
		}

		// 3:找相同的修改
		affectNodeUpate(mapper, list, deptMap, selectNewCode);

		// 4:更新移动的节点
		updateChildNode(mapper, selectNode, selectNewCode, deptMap);

		// 修改子级节点
		selectNode.setDeptcode(selectNewCode);
		mapper.updateDepartment(selectNode);

		// 同步通讯录
		addressListService.refreshAddressList();
	}

	// 影响节点
	@Transactional
	private void affectNodeUpate(DepartmentDao mapper, List<Department> list, Map<String, List<Department>> deptMap, String selectNewCode) {
		for (Department model : list) {
			// 移动后的code数据库里如果已经存在
			if (model.getDeptcode().equals(selectNewCode)) {
				String newcode = createNetCode(model, 1);

				// 修改子级节点
				updateChildNode(mapper, model, newcode, deptMap);
				// 递归向下寻找
				affectNodeUpate(mapper, list, deptMap, newcode);

				model.setDeptcode(newcode);
				mapper.updateDepartment(model);
			}
		}
	}

	// 儿子节点更新code
	@Transactional
	private void updateChildNode(DepartmentDao mapper, Department parentNode, String newParentCode, Map<String, List<Department>> deptMap) {
		List<Department> list = deptMap.get(parentNode.getDeptid());

		if (list != null && !list.isEmpty())
			for (Department model : list) {
				String deptcode = model.getDeptcode();
				// 组装新的code
				String newcode = newParentCode + deptcode.substring(newParentCode.length(), deptcode.length());

				// 递归向上找子节点
				updateChildNode(mapper, model, newcode, deptMap);

				model.setDeptcode(newcode);
				mapper.updateDepartment(model);
			}

	}

	// 生成新的companycode
	private String createNetCode(Department targetNode, int step) {
		String targetCode = targetNode.getDeptcode();
		int targetCodeLg = targetCode.length();
		String maxCode = targetCode.substring(targetCodeLg - 5, targetCodeLg);

		// 上级code
		String parentCode = null;
		if (StringUtils.isNotEmpty(targetNode.getPdeptid()))
			parentCode = targetCode.substring(0, targetCodeLg - 5);

		// 生成移动后的code
		String selectCode = UUIDGenerator.format0000_ID(parentCode, maxCode, step);
		return selectCode;
	}

	/**
	 * 解析部门
	 * 
	 * @param deptname
	 * @return
	 */
	public Department getDepartmentByName(String companyid, String deptname) {
		System.out.println("==="+companyid);
		DepartmentDao mapper = sqlSession.getMapper(DepartmentDao.class);
		UserContext uc = ContextFacade.getUserContext();
		deptname = deptname.replaceAll(" ", "");
		Department dept = new Department();
		String[] depts = deptname.split("/");
		dept.setCompanyid(companyid);
		dept.setDeptid(UUIDGenerator.randomUUID());
		dept.setState(Constance.VALID_YES);
		dept.setModiuser(uc.getUsername());
		dept.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		
		if (depts.length > 1) {
			//得到一级部门的名称
			String zdeptname=depts[0];
			
			//查找下一级部门中是否有这个部门
			Department ddept = mapper.getDepartmentbyDepartmentName(zdeptname, companyid);
			if(ddept!=null){
				//得到二级部门的名称
				String pdeptname=depts[1];
				//判断是否在这个一级部门下有这个部门
				Department sjdept = mapper.getPDepartmentbyDepartmentName(pdeptname, companyid, ddept.getDeptid());
				if(sjdept!=null){
					return sjdept;
				}else{
					//如无此部门保存此部门
					Department sdept = new Department();
					sdept.setDeptid(UUIDGenerator.randomUUID());
					sdept.setState(Constance.VALID_YES);
					sdept.setDeptname(pdeptname);
					sdept.setDeptduty(pdeptname);
					sdept.setDeptfunction(pdeptname);
					sdept.setCompanyid(companyid);
					sdept.setModiuser(uc.getUsername());
					sdept.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					String maxcode = mapper.getMaxDepartmentCodeByPDepartmentId(ddept.getDeptid());
					sdept.setDeptcode(UUIDGenerator.format0000_ID(ddept.getDeptcode(), maxcode, 1));
					mapper.insertDepartment(sdept);
					return sdept;
				}
			}else{
				//如无此部门在一级部门下就保存此部门和对应的二级部门
				Department sdept = new Department();
				sdept.setDeptid(UUIDGenerator.randomUUID());
				sdept.setState(Constance.VALID_YES);
				sdept.setDeptname(depts[0]);
				sdept.setDeptduty(depts[0]);
				sdept.setDeptfunction(depts[0]);
				sdept.setCompanyid(companyid);
				sdept.setModiuser(uc.getUsername());
				sdept.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				String maxcode = mapper.getMaxDepartmentCodeByPDepartmentId(null);
				sdept.setDeptcode(UUIDGenerator.format0000_ID(null, maxcode, 1));
				mapper.insertDepartment(sdept);
				
				//再保存二级部门
				dept.setDeptname(depts[1]);
				dept.setDeptduty(depts[1]);
				dept.setDeptfunction(depts[1]);
				dept.setPdeptid(sdept.getDeptid());
				String pmaxcode = mapper.getMaxDepartmentCodeByPDepartmentId(sdept.getDeptid());
				dept.setDeptcode(UUIDGenerator.format0000_ID(sdept.getDeptcode(), pmaxcode, 1));
				mapper.insertDepartment(dept);
			}
		} else {
			//如果只有一个部门组成(判断此部门是否存在)
			Department wdept = mapper.getDepartmentbyDepartmentName(deptname, companyid);
			
			if(wdept==null){
				dept.setDeptname(deptname);
				dept.setDeptduty(deptname);
				dept.setDeptfunction(deptname);
				String maxcode = mapper.getMaxDepartmentCodeByPDepartmentId(null);
				dept.setDeptcode(UUIDGenerator.format0000_ID(null, maxcode, 1));
				mapper.insertDepartment(dept);
			}else{
				return wdept;
			}
		}

		return dept;
	}

}
