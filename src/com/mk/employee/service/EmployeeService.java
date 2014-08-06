package com.mk.employee.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.contract.dao.ContractDao;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.employee.entity.Position;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreePageGrid;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.quota.dao.QuotaDao;
import com.mk.resume.dao.ResumeDao;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.Syslog;
import com.mk.system.entity.User;
import com.mk.system.service.SyslogService;

@Service
public class EmployeeService {
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
	@Transactional(readOnly = true)
	public void searchEmployee(GridServerHandler grid) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countEmployee(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<Employee> list = mapper.searchEmployee(grid);

		// 批量
		if (list.size() > Constance.ConvertCodeNum) {
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 職務
			Map<String, String> jobMap = codeConvertNameService.getAllJobMap();
			// 职级
			Map<String, String> rankMap = codeConvertNameService.getAllRankMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 編制
			Map<String, String> quotaMap = codeConvertNameService.getAllQuotaMap();
			// 状态
			Map<Integer, String> stateMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.WORKSTATE);
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);
			// 文化程度
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			// 民族
			Map<Integer, String> nationMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.NATION);
			// 血型
			Map<Integer, String> bloodtypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.BLOODTYPE);
			// 政治面貌
			Map<Integer, String> politicsMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.POLITICS);
			// 婚姻状况
			Map<Integer, String> marriedMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.MARRIED);
			// 员工类别
			Map<Integer, String> classificationMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CLASSIFICATION);
			// 用工性质
			Map<Integer, String> employtypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.EMPLOYTYPE);
			// 离职原因
			Map<Integer, String> resignationreasonMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RESIGNATIONREASON);
			// 紧急联系人关系
			Map<Integer, String> contactrelationshipMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RELATIONSHIP);
			// 户籍类型
			Map<Integer, String> domicilplaceMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.DOMICILPLACE);

			for (Employee model : list) {
				model.convertManyCodeToName(mapper, companyMap, deptMap, jobMap, rankMap, postMap, quotaMap, stateMap, sexMap, cultureMap, nationMap, bloodtypeMap, politicsMap, marriedMap,
						classificationMap, employtypeMap, resignationreasonMap, contactrelationshipMap, domicilplaceMap);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (Employee model : list) {
				model.convertOneCodeToName(mapper, companyDao, departmentDao, postDao, optionDao, quotaDao);
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
	public String saveEmployeeGrid(GridServerHandler grid) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);

		// 添加的行
		List<Object> addList = grid.getInsertedRecords(Employee.class);
		if (!addList.isEmpty()) {
			for (Object obj : addList) {
				// 员工信息
				Employee model = (Employee) obj;
				model.setEmployeeid(UUIDGenerator.randomUUID());
				model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				mapper.insertEmployee(model);

				// 员工任职
				Position position = new Position(model);
				mapper.insertPosition(position);

				// 新加系统用户
				User user = new User(model);
				systemDao.insertUser(user);
				// 保存用户通讯录权限
				systemDao.saveUserAddressCompany(user);
			}

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
	public void saveOrUpdateEmployee(Employee model) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);

		// 修改时间
		if (StringUtils.isEmpty(model.getEmployeeid())) {
			model.setEmployeeid(UUIDGenerator.randomUUID());
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			mapper.insertEmployee(model);

			// 新加系统用户
			User user = new User(model);
			systemDao.insertUser(user);
			// 保存用户通讯录权限
			systemDao.saveUserAddressCompany(user);

		} else {
			if(StringUtils.isNotEmpty(model.getPhoto())){
				String photo=model.getPhoto();
				photo=photo.substring(photo.indexOf("u"),photo.length());
				model.setPhoto(photo.replaceAll(" ", ""));
			}
			mapper.updateEmployee(model);

			// 系统用户登陆名
			// 员工离职
			if (model.getWorkstate().equals(Constance.WORKSTATE_Departure)) {
				systemDao.delUserAddressCompanyByEmployeeId(model.getEmployeeid());
				systemDao.delUserManageRangeByEmployeeId(model.getEmployeeid());
				systemDao.delUserRoleByEmployeeId(model.getEmployeeid());
				systemDao.delUserDepartmentByEmployeeId(model.getEmployeeid());
				systemDao.delUserFilterByEmployeeId(model.getEmployeeid());
				systemDao.delUserByEmployeeId(model.getEmployeeid());
			} else {
				User user = systemDao.getUserByUserEmployeeId(model.getEmployeeid());
				// 新加系统用户
				if (user == null) {
					user = new User(model);
					systemDao.insertUser(user);
					// 保存用户通讯录权限
					systemDao.saveUserAddressCompany(user);
				} else {
					// 检查系统用户的登陆名（工号）是否发生改变
					if (!user.getLoginname().equals(model.getJobnumber())) {
						user.setLoginname(model.getJobnumber());
						systemDao.updateUser(user);
					}
				}
			}

		}

		// 员工任职
		Position position = model.getPosition();
		if (position != null) {
			// 员工离职
			// if (model.getWorkstate().equals(Constance.WORKSTATE_Departure)) {
			// position.setEnddate(model.getResignationdate());
			// }

			position.setEmployeeid(model.getEmployeeid());
			position.setModiuser(model.getModiuser());
			position.setModitimestamp(model.getModitimestamp());

			// 保存
			if (StringUtils.isEmpty(position.getPostionguid())) {
				position.setPostionguid(UUIDGenerator.randomUUID());
				mapper.insertPosition(position);
			} else {
				Position dbposition = mapper.getPositionById(position.getPostionguid());
				// 涉及异动员工生效
				// 找到原来的编制
				// 关掉
				if (dbposition != null) {
					if (dbposition.getIsstaff() == Constance.IsStaff_Todo && dbposition.getIsstaff() != position.getIsstaff()) {
						List<Position> list = mapper.getPositionByEmployeeIdAndIsstaff(model.getEmployeeid());
						for (Position oldPs : list) {
							oldPs.setState(Constance.STATE_Del);
							oldPs.setEnddate(position.getStartdate());
							mapper.updatePosition(oldPs);
						}
					}
					mapper.updatePosition(position);
				}
			}
		}
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Employee getEmployeeById(String id, String postionguid) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		Employee model = mapper.getEmployeeById(id);
		if (model != null) {
			model.convertMakOneCodeToName(mapper, companyDao, departmentDao, postDao, quotaDao, optionDao, postionguid);
		}
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delEmployeeById(List<Employee> list) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		ContractDao contractDao = sqlSession.getMapper(ContractDao.class);
		StringBuffer buffer=new StringBuffer();
		for (Employee model : list) {
			String id = model.getEmployeeid();
			buffer.append(model.getName());
			buffer.append(",");
			if (model.getIsstaff() == Constance.IsStaff_Yes) {

				// 系统管理
				systemDao.delUserAddressCompanyByEmployeeId(id);
				systemDao.delUserManageRangeByEmployeeId(id);
				systemDao.delUserRoleByEmployeeId(id);
				systemDao.delUserDepartmentByEmployeeId(id);
				systemDao.delUserFilterByEmployeeId(id);
				systemDao.delUserByEmployeeId(id);

				// 业务数据
				mapper.delCertificationByEmployeeId(id);
				contractDao.delContractByEmployeeId(id);
				mapper.delEduexperienceByEmployeeId(id);
				mapper.delPositionByEmployeeId(id);
				mapper.delTrainByEmployeeId(id);
				mapper.delWorkexperienceByEmployeeId(id);
				mapper.delFamilyByEmployeeId(id);
				mapper.delHrRecommendByEmployeeId(id);
				mapper.delHrRelativesByEmployeeId(id);

				// 删除请假的数据
				mapper.delVacation(id);

				// 自己删除
				mapper.delEmployeeById(id);

			} else {
				if (StringUtils.isNotEmpty(model.getPostionguid()))
					mapper.delPositionById(model.getPostionguid());
			}

			// 兼职删除逻辑
			/*
			 * else if (model.getIsstaff() == Constance.IsStaff_No) { if
			 * (StringUtils.isNotEmpty(model.getPostionguid())) { Position
			 * dbposition = mapper.getPositionById(model.getPostionguid());
			 * dbposition.setState(Constance.STATE_Del);
			 * dbposition.setEnddate(new Date(System.currentTimeMillis()));
			 * mapper.updatePosition(dbposition); } }
			 */
		}
		
		// 系统日志
		Syslog log = new Syslog("员工信息" + buffer.toString()+Constance.del);
		syslogService.saveOrUpdateSyslog(log);
	}

	/**
	 * 身份证号匹配
	 * 
	 * @param grid
	 * @return
	 */
	public List<Employee> searchEmployeeTree(TreePageGrid grid) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = mapper.countEmployeeTree(grid);
		grid.getPage().setTotalrows(count);
		grid.getPage().init();
		// 分页
		List<Employee> list = mapper.searchEmployeeTree(grid);
		for (Employee employee : list) {
			employee.convertOneCodeToName(mapper, companyDao, departmentDao, postDao, optionDao, quotaDao);
		}
		return list;
	}

	/**
	 * 所有人员
	 * 
	 * @param grid
	 * @return
	 */
	public List<Employee> searchUserTree(TreePageGrid grid) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		// 權限組織
		Constance.initAdminPam(grid.getParameters());

		// 统计
		Integer count = mapper.countUserTree(grid);
		grid.getPage().setTotalrows(count);
		grid.getPage().init();
		// 分页
		List<Employee> list = mapper.searchUserTree(grid);
		for (Employee employee : list) {
			employee.convertOneCodeToName(mapper, companyDao, departmentDao, postDao, optionDao, quotaDao);
		}
		return list;
	}

	/**
	 * 检验工号重复
	 * 
	 * @param employeeid
	 * @param jobnumber
	 * @return
	 */
	public String checkEmployeeByJobnumber(String employeeid, String jobnumber) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		// 检查索引

		StringBuffer msg = new StringBuffer();
		if (StringUtils.isNotEmpty(jobnumber)) {
			List<Employee> list = mapper.getEmployeeByJobnumber(employeeid, jobnumber);
			if (!list.isEmpty()) {
				msg.append("工号【");
				for (Employee model : list) {
					msg.append(model.getJobnumber() + "(" + model.getName() + ")");
					msg.append(",");
				}
				if (msg.length() > 1)
					msg.setLength(msg.length() - 1);
				msg.append("】已经存在！");
				return msg.toString();
			}
		}
		return msg.toString();
	}

	/**
	 * 检验身份证重复
	 * 
	 * @param employeeid
	 * @param jobnumber
	 * @return
	 */
	public String checkEmployeeByCardno(String employeeid, String cardnumber) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		StringBuffer msg = new StringBuffer();
		if (StringUtils.isNotEmpty(cardnumber)) {
			List<Employee> list = mapper.getEmployeeByCardnumber(employeeid, cardnumber);
			if (!list.isEmpty()) {
				msg.append("身份证号【");
				for (Employee model : list) {
					msg.append(model.getCardnumber() + "(" + model.getName() + ")");
					msg.append(",");
				}
				if (msg.length() > 1)
					msg.setLength(msg.length() - 1);
				msg.append("】已经存在！");
				return msg.toString();
			}
		}
		return msg.toString();
	}

}
