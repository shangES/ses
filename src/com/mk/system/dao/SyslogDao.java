package com.mk.system.dao;

import java.util.List;

import com.mk.framework.grid.GridServerHandler;
import com.mk.system.entity.Syslog;

public interface SyslogDao {

	/**
	 * 搜索全部系统日志
	 * 
	 * @param grid
	 * @return
	 */
	List<Syslog> searchSyslog(GridServerHandler grid);

	/**
	 * 统计系统日志
	 * 
	 * @param grid
	 * @return
	 */
	Integer countSyslog(GridServerHandler grid);

	/**
	 * 添加系统日志
	 * 
	 * @param model
	 */
	void insertSyslog(Syslog model);

	/**
	 * 修改系统日志
	 * 
	 * @param model
	 */
	void updateSyslog(Syslog model);

	/**
	 * 删除系统日志
	 * 
	 * @param id
	 */
	void delSyslog(String id);

	/**
	 * 通过id得到系统日志
	 * 
	 * @param id
	 * @return
	 */
	Syslog getSyslogById(String id);
}
