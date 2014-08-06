package com.mk.company.service;

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
import com.mk.framework.constance.Constance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.UserAddressCompany;
import com.mk.system.entity.UserManageRange;

@Service
public class CompanyService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private AddressListService addressListService;

	/**
	 * 通讯录公司
	 * 
	 * @param validYes
	 * @return
	 */
	public List<Company> getHasWebCompanys(Integer state) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		UserContext uc = ContextFacade.getUserContext();

		// 全部公司
		List<Company> list = mapper.getAllCompanys(state);

		// 系統管理員
		if (uc.isAdmin()) {
			return list;
		}
		if (uc == null || StringUtils.isEmpty(uc.getUserId())) {
			return null;
		}

		// 緩存公司
		Map<String, Company> map = new HashMap<String, Company>();
		for (Company model : list) {
			map.put(model.getCompanyid(), model);
		}

		// 緩存有權限的公司
		List<UserAddressCompany> umrs = systemDao.getUserAddressCompanyByUserId(uc.getUserId());
		if (!umrs.isEmpty() && umrs.size() == 1) {
			UserAddressCompany tmp = umrs.get(0);
			if (StringUtils.isEmpty(tmp.getCompanyid())) {
				umrs = new ArrayList<UserAddressCompany>();
				for (Company company : list) {
					UserAddressCompany model = new UserAddressCompany(company, uc.getUserId());
					umrs.add(model);
				}
			}
		}

		// 已经有授权
		List<Company> hasCompanys = new ArrayList<Company>();
		if (!umrs.isEmpty()) {
			// 缓存
			Map<String, UserAddressCompany> hasMap = new HashMap<String, UserAddressCompany>();
			for (UserAddressCompany model : umrs) {
				hasMap.put(model.getCompanyid(), model);
			}
			// 向上有權限的公司
			for (UserAddressCompany model : umrs) {
				topPathWebCompany(model.getCompanyid(), map, hasMap, hasCompanys, true);
			}

		}
		return hasCompanys;
	}

	// 递归向上
	private void topPathWebCompany(String companyid, Map<String, Company> allMap, Map<String, UserAddressCompany> hasMap, List<Company> hasCompanys, boolean isaudit) {
		Company model = allMap.get(companyid);
		if (model != null) {
			// 设置有权限
			// 过滤重复
			if (!hasCompanys.contains(model)) {
				model.setIsaudit(isaudit);
				hasCompanys.add(model);
			}

			// 向上
			String pcompanyid = model.getPcompanyid();
			if (StringUtils.isNotEmpty(pcompanyid)) {
				UserAddressCompany tmpUMR = hasMap.get(pcompanyid);
				if (tmpUMR != null) {
					topPathWebCompany(pcompanyid, allMap, hasMap, hasCompanys, true);
				} else {
					topPathWebCompany(pcompanyid, allMap, hasMap, hasCompanys, false);
				}
			}
		}
	}

	// ===================================================================

	/**
	 * 得權限的公司
	 * 
	 * @return
	 */
	public List<Company> getHasCompanys(Integer state) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		UserContext uc = ContextFacade.getUserContext();
		// 全部公司
		List<Company> list = mapper.getAllCompanys(state);

		// 系統管理員
		if (uc.isAdmin()) {
			return list;
		}
		if (uc == null || StringUtils.isEmpty(uc.getUserId())) {
			return null;
		}

		// 緩存公司
		Map<String, Company> map = new HashMap<String, Company>();
		for (Company model : list) {
			map.put(model.getCompanyid(), model);
		}

		// 緩存有權限的公司
		List<UserManageRange> umrs = systemDao.getUserManageRangeByUserId(uc.getUserId());

		// 已经有授权
		List<Company> hasCompanys = new ArrayList<Company>();
		if (!umrs.isEmpty()) {
			// 缓存
			Map<String, UserManageRange> hasMap = new HashMap<String, UserManageRange>();
			for (UserManageRange model : umrs) {
				hasMap.put(model.getCompanyid(), model);
			}
			// 向上有權限的公司
			for (UserManageRange model : umrs) {
				topPathCompany(model.getCompanyid(), map, hasMap, hasCompanys, true);
			}

		}
		return hasCompanys;
	}

	// 递归向上
	private void topPathCompany(String companyid, Map<String, Company> allMap, Map<String, UserManageRange> hasMap, List<Company> hasCompanys, boolean isaudit) {
		Company model = allMap.get(companyid);
		if (model != null) {
			// 设置有权限
			// 过滤重复
			if (!hasCompanys.contains(model)) {
				model.setIsaudit(isaudit);
				hasCompanys.add(model);
			}

			// 向上
			String pcompanyid = model.getPcompanyid();
			if (StringUtils.isNotEmpty(pcompanyid)) {
				UserManageRange tmpUMR = hasMap.get(pcompanyid);
				if (tmpUMR != null) {
					topPathCompany(pcompanyid, allMap, hasMap, hasCompanys, true);
				} else {
					topPathCompany(pcompanyid, allMap, hasMap, hasCompanys, false);
				}
			}
		}
	}

	/**
	 * 得到全部公司
	 * 
	 * @return
	 */
	public List<Company> getAllCompanys(Integer state) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		return mapper.getAllCompanys(state);
	}


	/**
	 * 保存及修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateCompany(Company model) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		UserContext uc = ContextFacade.getUserContext();

		model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isEmpty(model.getCompanyid())) {
			// 统计maxcode
			String pid = model.getPcompanyid();
			String maxcode = mapper.getMaxCompanyCodeByPCompanyId(pid);
			Company parent = null;
			if (StringUtils.isNotEmpty(pid))
				parent = mapper.getCompanyByCompanyId(pid);

			model.setCompanycode(UUIDGenerator.format0000_ID(parent == null ? null : parent.getCompanycode(), maxcode, 1));
			model.setCompanyid(UUIDGenerator.randomUUID());
			mapper.insertCompany(model);

			// 非系统管理员
			// 保存用户公司授权
			if (!uc.isAdmin()) {
				UserManageRange umr = new UserManageRange(model, uc.getUserId());
				systemDao.insertUserManageRange(umr);
			}

		} else {
			mapper.updateCompany(model);

		}
	}

	/**
	 * 得到
	 * 
	 * @param companyid
	 * @return
	 */
	public Company getCompanyById(String companyid) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		Company model = mapper.getCompanyByCompanyId(companyid);
		if (model != null)
			model.convertCodeToName(optionDao);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	public void delCompanyByCompanyCode(String id) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		Company com = mapper.getCompanyByCompanyId(id);
		if (com != null) {
			String companycode = com.getCompanycode();
			mapper.delBudgetypeByCompanyCode(companycode);
			mapper.delJobByCompanyCode(companycode);
			mapper.delRankByCompanyCode(companycode);
			mapper.delCompanyByCompanyCode(companycode);
		}

	}

	/**
	 * 有效无效
	 * 
	 * @param companyid
	 * @param valid
	 */
	@Transactional
	public void validCompanyById(String companyid, Integer valid) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		if (valid.equals(Constance.VALID_NO)) {
			Company model = mapper.getCompanyByCompanyId(companyid);
			List<Company> list = mapper.getCompanyByCode(model.getCompanycode());
			for (Company com : list) {
				com.setState(valid);
				mapper.updateCompany(com);
			}
		} else {
			Company model = mapper.getCompanyByCompanyId(companyid);
			model.setState(Constance.VALID_YES);
			mapper.updateCompany(model);
		}
	}

	/**
	 * 排序
	 * 
	 * @param id
	 * @param targetid
	 * @param moveType
	 */
	@Transactional
	public void orderCompany(String id, String targetid, String moveType) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);

		// ========================
		// 移动的节点
		Company selectNode = mapper.getCompanyByCompanyId(id);
		if (selectNode == null)
			return;
		// 移动的目标节点
		Company targetNode = mapper.getCompanyByCompanyId(targetid);
		if (targetNode == null)
			return;
		// 他们的上级节点
		Company parentNode = null;
		if (StringUtils.isNotEmpty(selectNode.getPcompanyid()))
			parentNode = mapper.getCompanyByCompanyId(selectNode.getPcompanyid());
		String parentCode = (parentNode != null ? parentNode.getCompanycode() : null);

		// ========================

		// 确定向上移还是向下移动
		String selectNewCode = null;
		int step = 0;
		if (moveType.equals("next")) {
			step = 1;
			selectNewCode = createNetCode(targetNode, step);
		} else {
			selectNewCode = targetNode.getCompanycode();
		}

		// 2:找到所有影响的node
		List<Company> list = mapper.getCompanyByCode(parentCode);
		Map<String, List<Company>> companyMap = new HashMap<String, List<Company>>();
		for (Company model : list) {
			List<Company> tmplist = companyMap.get(model.getPcompanyid());
			if (tmplist == null) {
				tmplist = new ArrayList<Company>();
				tmplist.add(model);
				companyMap.put(model.getPcompanyid(), tmplist);
			} else {
				tmplist.add(model);
			}
		}

		// 3:找相同的修改
		affectNodeUpate(mapper, list, companyMap, selectNewCode);

		// 4:更新移动的节点
		// 修改子级节点
		updateChildNode(mapper, selectNode, selectNewCode, companyMap);
		selectNode.setCompanycode(selectNewCode);
		mapper.updateCompany(selectNode);

		// 同步通讯录
		addressListService.refreshAddressList();
	}

	// 影响节点
	@Transactional
	private void affectNodeUpate(CompanyDao mapper, List<Company> list, Map<String, List<Company>> companyMap, String selectCode) {
		for (Company model : list) {
			// 移动后的code数据库里如果已经存在
			if (model.getCompanycode().equals(selectCode)) {
				String newcode = createNetCode(model, 1);

				// 修改子级节点
				updateChildNode(mapper, model, newcode, companyMap);

				// 递归向下寻找
				affectNodeUpate(mapper, list, companyMap, newcode);

				model.setCompanycode(newcode);
				mapper.updateCompany(model);
			}
		}
	}

	// 儿子节点更新code
	@Transactional
	private void updateChildNode(CompanyDao mapper, Company parentNode, String newParentCode, Map<String, List<Company>> companyMap) {
		List<Company> list = companyMap.get(parentNode.getCompanyid());
		if (list != null && !list.isEmpty())
			for (Company model : list) {
				String companycode = model.getCompanycode();
				// 组装新的code
				String newcode = newParentCode + companycode.substring(newParentCode.length(), companycode.length());
				// 递归向上找子节点
				updateChildNode(mapper, model, newcode, companyMap);

				model.setCompanycode(newcode);
				mapper.updateCompany(model);
			}

	}

	// 生成新的companycode
	private String createNetCode(Company targetNode, int step) {
		String targetCode = targetNode.getCompanycode();
		int targetCodeLg = targetCode.length();
		String maxCode = targetCode.substring(targetCodeLg - 5, targetCodeLg);

		// 上级code
		String parentCode = null;
		if (StringUtils.isNotEmpty(targetNode.getPcompanyid()))
			parentCode = targetCode.substring(0, targetCodeLg - 5);

		// 生成移动后的code
		String selectCode = UUIDGenerator.format0000_ID(parentCode, maxCode, step);
		return selectCode;
	}

}
