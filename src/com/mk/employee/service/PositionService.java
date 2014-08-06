package com.mk.employee.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.company.dao.CompanyDao;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.employee.entity.Position;
import com.mk.framework.constance.Constance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreePageGrid;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.quota.dao.QuotaDao;
import com.mk.system.dao.OptionDao;

@Service
public class PositionService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchPosition(GridServerHandler grid) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countPosition(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<Position> list = mapper.searchPosition(grid);
		for (Position model : list) {
			model.convertOneCodeToName(companyDao, departmentDao, postDao, quotaDao, optionDao);
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}

	/**
	 * 保存
	 * 
	 * @param model
	 */
	@Transactional
	public String saveOrUpdatePosition(Position model) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);

		// 检验
		List<Position> list = mapper.checkIndexCompanyDeptPost(model);
		if (!list.isEmpty())
			return "任职信息已经存在,请检查！";

		// 修改时间
		if (StringUtils.isEmpty(model.getPostionguid())) {
			model.setPostionguid(UUIDGenerator.randomUUID());
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			mapper.insertPosition(model);
		} else {
			mapper.updatePosition(model);
		}

		return null;
	}

	/**
	 * 异动保存
	 * 
	 * @param model
	 * @return
	 */
	@Transactional
	public String saveOrUpdateManyPosition(Position model) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);

		String ids = model.getEmployeeid();
		if (StringUtils.isNotEmpty(ids)) {
			String[] objs = ids.split(",");

			// 检查
			StringBuffer msg = new StringBuffer();
			Employee emp = null;
			for (String employeeid : objs) {
				// 检验
				model.setEmployeeid(employeeid);
				List<Position> list = mapper.checkIndexCompanyDeptPost(model);
				if (!list.isEmpty()) {
					emp = mapper.getEmployeeById(employeeid);
					if (emp != null) {
						msg.append(emp.getName());
						msg.append("(");
						msg.append(emp.getJobnumber());
						msg.append(")");
						msg.append(",");
					}
				}
			}
			if (msg.length() > 0) {
				msg.setLength(msg.length() - 1);
				msg.append("任职信息已经存在,请检查！");
				return msg.toString();
			}

			// ==========================
			// 保存
			for (String employeeid : objs) {

				// 找到原来的编制
				// 关掉
				// List<Position> list =
				// mapper.getPositionByEmployeeIdAndIsstaff(employeeid);
				// for (Position postion : list) {
				// postion.setEnddate(model.getStartdate());
				// mapper.updatePosition(postion);
				// }

				List<Position> oldpositions = mapper.getPositionByEmployeeIdAndIsstaff(employeeid);
				if (!oldpositions.isEmpty()) {
					Position oldpo = oldpositions.get(0);

					// 如果本公司异动的时候职级、职务未输把原来的职级、职务赋上去
					if (oldpo.getCompanyid().equals(model.getCompanyid())) {
						if (StringUtils.isEmpty(model.getRankid())) {
							model.setRankid(oldpo.getRankid());
						}
						if (StringUtils.isEmpty(model.getJobid())) {
							model.setJobid(oldpo.getJobid());
						}
						if (StringUtils.isEmpty(model.getQuotaid())) {
							model.setQuotaid(oldpo.getQuotaid());
						}
					}
				}

				// 插出新的编制
				model.setEmployeeid(employeeid);
				model.setPostionguid(UUIDGenerator.randomUUID());
				model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				mapper.insertPosition(model);

			}
		}
		return null;
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Position getPositionById(String id) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		Position model = mapper.getPositionById(id);
		if (model != null) {
			model.convertOneCodeToName(companyDao, departmentDao, postDao, quotaDao, optionDao);
		}
		return model;
	}

	
	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Position getPositionByUserId(String id) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		Position model = mapper.getPositionByUserId(id);
		if (model != null) {
			model.convertOneCodeToName(companyDao, departmentDao, postDao, quotaDao, optionDao);
		}
		return model;
	}
	
	
	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delPositionById(String ids) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);

		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delPositionById(id);
		}
	}

	/**
	 * 失效/还原
	 * 
	 * @param ids
	 */
	@Transactional
	public void validPositionById(String ids, Integer state) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			Position model = mapper.getPositionById(id);
			if (model != null) {
				model.setState(state);
				mapper.updatePosition(model);
			}
		}
	}

	/**
	 * 部门有任职
	 * 
	 * @param deptcode
	 * @return
	 */
	public boolean getPositionByDeptCode(String deptcode) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);

		List<Position> list = mapper.getPositionByDeptCode(deptcode);
		if (!list.isEmpty())
			return true;
		return false;
	}

	/**
	 * 结束兼任
	 * 
	 * @param postionguid
	 * @param enddate
	 * @return
	 */
	@Transactional
	public void endPositionById(String postionguid, Date enddate) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);

		Position model = mapper.getPositionById(postionguid);
		if (model != null) {
			model.setState(Constance.STATE_Del);
			model.setEnddate(enddate);
			mapper.updatePosition(model);
		}
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Position getPositionByEmployeeId(String id) {
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		List<Position> list = mapper.getPositionByEmployeeIdAndIsstaff(id);

		if (!list.isEmpty()) {
			Position model = list.get(0);
			model.convertOneCodeToName(companyDao, departmentDao, postDao, quotaDao, optionDao);
			return model;
		}
		return null;
	}

	/**
	 * 岗位分页
	 * 
	 * @param grid
	 * @return
	 */
	public List<Post> searchPostTree(TreePageGrid grid){
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		UserContext uc = ContextFacade.getUserContext();
		// 统计
		Integer count = mapper.countPostTree(grid);
		grid.getPage().setTotalrows(count);
		grid.getPage().init();
		// 分页
		List<String> str = mapper.searchPostTree(grid);
		List<Post> list = new ArrayList<Post>();
		System.out.println("str==="+str);
		System.out.println("uc.getCompanyid()==="+uc.getCompanyid());
		if(!str.isEmpty()){
			for(String postname : str){
				List<Post> listpost = mapper.getPostByName(postname);
				if(listpost.size() > 0)
					list.add(listpost.get(0));
			}
		}
		return list;
	}
}
