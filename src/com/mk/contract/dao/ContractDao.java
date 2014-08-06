package com.mk.contract.dao;

import java.util.List;

import com.mk.contract.entity.Contract;
import com.mk.framework.grid.GridServerHandler;

public interface ContractDao {
	// =================合同信息====================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countContract(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Contract> searchContract(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertContract(Contract model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateContract(Contract model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Contract getContractById(String contractid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delContractById(String contractid);

	/**
	 * 删除
	 * 
	 * @param employeeid
	 */
	void delContractByEmployeeId(String employeeid);
}
