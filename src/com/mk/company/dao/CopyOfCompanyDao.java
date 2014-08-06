package com.mk.company.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.company.entity.Budgetype;
import com.mk.company.entity.Company;
import com.mk.company.entity.Job;
import com.mk.company.entity.Rank;
import com.mk.framework.grid.GridServerHandler;

public interface CopyOfCompanyDao {
	// ============公司================
	/**
	 * 全部公司
	 * 
	 * @return
	 */
	List<Company> getAllCompanys(@Param(value = "state") Integer state);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertCompany(Company model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateCompany(Company model);

	/**
	 * 得到
	 * 
	 * @param code
	 * @return
	 */
	List<Company> getCompanyByCode(@Param(value = "code") String code);

	/**
	 * 删除
	 * 
	 * @param companyid
	 */
	void delCompanyByCompanyCode(String code);

	/**
	 * 得到
	 * 
	 * @param companyid
	 * @return
	 */
	Company getCompanyByCompanyId(String companyid);
	
	
	/**
	 * 得到
	 * 
	 * @param companyname
	 * @return
	 */
	Company getCompanyByCompanyName(@Param(value = "companyname") String companyname);

	/**
	 * 得到最大的id
	 * 
	 * @param pid
	 * @return
	 */
	String getMaxCompanyCodeByPCompanyId(@Param(value = "pid") String pid);

	// ============职务================

	/**
	 * 全部
	 * 
	 * @param state
	 * @return
	 */
	List<Job> getAllJob(@Param(value = "state") Integer state);

	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countJob(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Job> searchJob(GridServerHandler grid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Job getJobById(String jobid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertJob(Job model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateJob(Job model);

	/**
	 * 删除
	 * 
	 * @param jobid
	 */
	void delJobByJobId(String jobid);

	/**
	 * 删除
	 * 
	 * @param companyid
	 */
	void delJobByCompanyCode(String companycode);

	/**
	 * 得到
	 * 
	 * @param companyid
	 * @return
	 */
	List<Job> getJobByCompanyId(String companyid);

	/**
	 * 得到
	 * 
	 * @param companyid
	 * @param jobname
	 * @return
	 */
	List<Job> getJobByName(@Param(value = "companyid") String companyid, @Param(value = "jobname") String jobname);

	// ============职级================
	/**
	 * 全部
	 * 
	 * @param state
	 * @return
	 */
	List<Rank> getAllRank(@Param(value = "state") Integer state);

	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countRank(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Rank> searchRank(GridServerHandler grid);

	/**
	 * 得到
	 * 
	 * @param companyid
	 * @return
	 */
	List<Rank> getRankByCompanyId(String companyid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Rank getRankById(String rankid);
	
	
	
	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Rank getRankByInterfacecode(@Param(value = "interfacecode") String interfacecode,@Param(value = "companyid") String companyid);
	
	
	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertRank(Rank model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateRank(Rank model);

	/**
	 * 删除
	 * 
	 * @param rankid
	 */
	void delRankByRankId(String rankid);

	/**
	 * 删除
	 * 
	 * @param companyid
	 */
	void delRankByCompanyCode(String companycode);
	
	/**
	 * 得到
	 * 
	 * @param companyid
	 * @param rankname
	 * @return
	 */
	List<Rank> getRankByName(@Param(value = "companyid") String companyid, @Param(value = "rankname") String rankname);

	// ============编制类型================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countBudgetype(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Budgetype> searchBudgetype(GridServerHandler grid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Budgetype getBudgetypeById(String budgettypeid);
	
	/**
	 * 通过公司id得到该公司下得编制类型
	 * @param companyid
	 * @return
	 */
	List<Budgetype> getQuotaBudgettypeByCompanyId(String companyid);
	
	/**
	 * 全部的编制
	 * @return
	 */
	List<Budgetype> getAllQuotaBudgettype();

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertBudgetype(Budgetype model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateBudgetype(Budgetype model);

	/**
	 * 删除
	 * 
	 * @param budgetypeid
	 */
	void delBudgetypeByBudgetypeId(String budgettypeid);

	/**
	 * 删除
	 * 
	 * @param companyid
	 */
	void delBudgetypeByCompanyCode(String companycode);

	/**
	 * 得到
	 * 
	 * @param companyid
	 * @param budgettypename
	 * @return
	 */
	List<Budgetype> getBudgettypeByName(@Param(value = "companyid") String companyid, @Param(value = "budgettypename") String budgettypename);


}
