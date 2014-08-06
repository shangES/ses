package com.mk.addresslist.dao;

import java.util.List;

import com.mk.addresslist.entity.AddressList;
import com.mk.framework.grid.GridServerHandler;

public interface AddressListDao {

	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	public Integer countAddressList(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	public List<AddressList> searchAddressList(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertAddressList(AddressList model);

	/**
	 * 更新
	 * 
	 * @param model
	 */
	void updateAddressList(AddressList model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delAddressListById(String addresslistguid);

	/**
	 * 根据员工id删除
	 * 
	 */
	void delAddressListByEmployeeId();

	/**
	 * 员工刷新到通讯录里
	 * 
	 */
	void insertAddressListByEmployee();

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	AddressList getAddressListById(String addresslistguid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	List<AddressList> getAddressListByEmployeeId(String id);

}
