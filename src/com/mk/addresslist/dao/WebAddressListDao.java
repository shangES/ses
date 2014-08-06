package com.mk.addresslist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.addresslist.entity.WebAddressList;
import com.mk.framework.grid.GridServerHandler;

public interface WebAddressListDao {
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	public Integer countWebAddressList(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	public List<WebAddressList> searchWebAddressList(GridServerHandler grid);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	WebAddressList getWebAddressListById(String addresslistguid);

	/**
	 * 得到
	 * 
	 * @return
	 */
	List<WebAddressList> getWebAddressListByUserId(String userid);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	public void updateWebAddressList(WebAddressList model);

	/**
	 * 得到同一个部门的员工
	 * 
	 * @param deptid
	 * @return
	 */
	List<WebAddressList> getWebAddressListByDpetId(@Param(value = "id") String id, @Param(value = "deptid") String deptid);
}
