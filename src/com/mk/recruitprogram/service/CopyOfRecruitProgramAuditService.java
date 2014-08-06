package com.mk.recruitprogram.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.company.entity.Rank;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.entity.Department;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Position;
import com.mk.framework.constance.Constance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.recruitprogram.dao.RecruitProgramAuditRemoteDao;
import com.mk.recruitprogram.dao.RecruitprogramDao;
import com.mk.recruitprogram.entity.RecruitProgram;
import com.mk.recruitprogram.entity.RecruitProgramAudit;
import com.mk.recruitprogram.entity.RecruitProgramOperate;

@Service
public class CopyOfRecruitProgramAuditService {
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	RecruitProgramAuditRemoteDao mapper;

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchRecruitprogramaudit(GridServerHandler grid) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();

		// 统计
		Integer count = mapper.countRecruitprogramaudit(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<RecruitProgramAudit> list = mapper.searchRecruitprogramaudit(grid);
		
		for (RecruitProgramAudit model : list) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}

	/**
	 * 保存or修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateRecruitProgramAudit(RecruitProgramAudit model) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		UserContext uc = ContextFacade.getUserContext();
		if (StringUtils.isEmpty(model.getRecruitprogramauditguid())) {
			model.setRecruitprogramauditguid(UUIDGenerator.randomUUID());
			model.setInterfacecode(UUIDGenerator.randomUUID());
			mapper.insertRecruitprogramaudit(model);
		} else {
			//生成一张招聘计划
			RecruitProgram program=new RecruitProgram();
			program.setRecruitprogramguid(UUIDGenerator.randomUUID());
			program.setRecruitprogramauditguid(model.getRecruitprogramauditguid());
			program.setRecruitprogramcode(getMaxRecruitProgramCode());
			program.setQuotaid(model.getQuotaid());
			program.setApplydate(model.getStartdate());
			program.setCompanyid(model.getCompanyid());
			program.setDeptid(model.getDeptid());
			program.setPostid(model.getPostid());
			program.setRankid(model.getRankid());
			program.setPostnum(model.getRemainnum());
			program.setState(Constance.OperateState_Lock);
			program.setModiuser(uc.getUsername());
			program.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			mapper.insertRecruitprogram(program);
			
			//操作信息
			RecruitProgramOperate ope = new RecruitProgramOperate();
			ope.setRecruitprogramoperateguid(UUIDGenerator.randomUUID());
			ope.setRecruitprogramguid(program.getRecruitprogramguid());
			ope.setOperatestate(Constance.OperateState_Add);
			ope.setModiuser(uc.getUsername());
			ope.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			ope.setOperatenum(program.getPostnum());
			mapper.insertRecruitProgramOperate(ope);
			
			//回写OA招聘计划
			RecruitProgramAudit audit = mapper.getRecruitprogramauditById(model.getRecruitprogramauditguid());
			//置还有多少招聘人数可以分配
			if(audit!=null){
				Integer num=audit.getRemainnum()-model.getRemainnum();
				model.setRemainnum(num);
			}
			model.setState(Constance.VALID_YES);
			mapper.updateRecruitprogramaudit(model);
			
			
			
		}
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delRecruitprogramauditById(String ids) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delRecruitprogramauditByAuditId(id);
		}
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public RecruitProgramAudit getRecruitprogramauditById(String id) {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		RecruitProgramAudit model = mapper.getRecruitprogramauditById(id);
		return model;
	}

	/**
	 * 同步招聘计划
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@Transactional
	public void refreshRecruitProgramAudit() throws Exception {
		RecruitprogramDao recruitprogramdao = sqlSession.getMapper(RecruitprogramDao.class);
		DepartmentDao departmentdao=sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao=sqlSession.getMapper(CompanyDao.class);
		List<RecruitProgramAudit> list = mapper.getRemoteRecruitProgramAuditRemote();
		UserContext uc = ContextFacade.getUserContext();
		if(list!=null&&!list.isEmpty()){
			for(RecruitProgramAudit model : list){
				RecruitProgram recruit = recruitprogramdao.getRecruitprogramById(model.getRecruitprogramauditguid());
				if(recruit == null){
					Company com = companyDao.getCompanyByCompanyName(model.getCompanyname());
					if(com!=null){
						if(model != null && StringUtils.isNotEmpty(model.getDeptname())){
							Department department = departmentdao.getDepartmentbyDepartmentName(model.getDeptname(),com.getCompanyid());
							RecruitProgram recruitprogram = new RecruitProgram();
							if(department != null){
								recruitprogram.setDeptid(department.getDeptid());
							}else if(StringUtils.isNotEmpty(model.getWordcode())){
								EmployeeDao employeedao = sqlSession.getMapper(EmployeeDao.class);
								Position position = employeedao.getEmployeeByJobnum(model.getWordcode());
								if(position != null){
									Department dept = departmentdao.getDepartmentByDepartmentId(position.getDeptid());
									if(dept!=null){
										if(StringUtils.isEmpty(dept.getPdeptid())){
											recruitprogram.setDeptid(position.getDeptid());
										}else{
											List<Department> pdepts = departmentdao.getDepartmentByPDeptid(com.getCompanyid(), dept.getPdeptid());
											if(!pdepts.isEmpty()&&pdepts!=null){
												Department pdept = pdepts.get(0);
												recruitprogram.setDeptid(pdept.getDeptid());
											}
										}
									}
								}
							}else{
								recruitprogram.setDeptid(Constance.DEPTID);
							}
							
							//设置岗位
							if(StringUtils.isNotEmpty(model.getPostname())){
								recruitprogram.setPostname(model.getPostname());
							}
							
//							//设置职级
//							if(StringUtils.isNotEmpty(model.getRankname())){
//								Rank rank = companyDao.getRankByInterfacecode(model.getRankname(),com.getCompanyid());
//								if(rank!=null){
//									recruitprogram.setRankname(rank.getLevelname());
//									recruitprogram.setRankid(rank.getRankid());
//								}
//							}
							
							
							//********************招聘计划管理************************//
							recruitprogram.setRecruitprogramguid(model.getRecruitprogramauditguid());
							recruitprogram.setInterfacecode(model.getInterfacecode());
							recruitprogram.setModitimestamp(new Timestamp(System.currentTimeMillis()));
							recruitprogram.setState(Constance.STATE_TODO);
							recruitprogram.setPostnum(model.getPostnum());
							//申请时间
							if(model.getStartdate()!=null){
								recruitprogram.setApplydate(model.getStartdate());
							}else{
								recruitprogram.setApplydate(new Date(System.currentTimeMillis()));
							}
							
							//计划到岗时间
							if(model.getHillockdate()!=null){
								recruitprogram.setHillockdate(model.getHillockdate());
							}
							
//							recruitprogram.setCompanyid(Constance.COMPANYID);
//							recruitprogram.setCompanyname(Constance.COMPANYNAME);
							recruitprogram.setCompanyid(com.getCompanyid());
							recruitprogram.setCompanyname(com.getCompanyname());
							if(uc != null){
								recruitprogram.setModiuser(uc.getUsername());
							}
							// 设置code
							String maxcode = getMaxRecruitProgramCode();
							recruitprogram.setRecruitprogramcode(maxcode);
							
							
							//如果未取到部门
							if(StringUtils.isEmpty(recruitprogram.getDeptid())){
								recruitprogram.setDeptid(Constance.DEPTID);
							}
							
							recruitprogramdao.insertRecruitprogram(recruitprogram);
							
							//***************招聘计划操作*****************//
							RecruitProgramOperate recruitprogramoperate = new RecruitProgramOperate();
							recruitprogramoperate.setRecruitprogramoperateguid(UUIDGenerator.randomUUID());
							recruitprogramoperate.setRecruitprogramguid(recruitprogram.getRecruitprogramguid());
							recruitprogramoperate.setOperatenum(recruitprogram.getPostnum());
							recruitprogramoperate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
							recruitprogramoperate.setOperatestate(Constance.OperateState_Add);
							if(uc != null){
								recruitprogramoperate.setModiuser(uc.getUsername());
							}
							recruitprogramdao.insertRecruitProgramOperate(recruitprogramoperate);
						}
					}
				}
			}
		}
	}
	
	
	
	/**
	 * 得到最大code
	 * 
	 * @param id
	 * @return
	 */
	public String getMaxRecruitProgramCode() {
		RecruitprogramDao mapper = sqlSession.getMapper(RecruitprogramDao.class);
		// 當天最大code
		String maxcode = mapper.getMaxRecruitProgramCode();

		if (StringUtils.isNotEmpty(maxcode)) {
			return UUIDGenerator.format0000_ID(Constance.recruitprogram_prefix, maxcode, 1);
		}
		return UUIDGenerator.format0000_ID(Constance.recruitprogram_prefix, maxcode, 1);
	}
}
