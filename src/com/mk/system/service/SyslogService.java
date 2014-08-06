package com.mk.system.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.system.dao.SyslogDao;
import com.mk.system.entity.Syslog;

@Service
public class SyslogService {

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 搜索系统日志
	 * 
	 * @param grid
	 */
	public void searchSyslog(GridServerHandler grid) {
		SyslogDao mapper = sqlSession.getMapper(SyslogDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计系统日志的数量
		Integer count = mapper.countSyslog(grid);
		PageUtils.setTotalRows(grid, count);

		// 得到所有的系统日志
		List<Syslog> list = mapper.searchSyslog(grid);

		for (Syslog model : list) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}

	/**
	 * 保存or修改系统日志
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateSyslog(Syslog model) {
		SyslogDao mapper = sqlSession.getMapper(SyslogDao.class);
		UserContext uc = ContextFacade.getUserContext();
		if (StringUtils.isEmpty(model.getLogguid())) {
			model.setLogguid(UUIDGenerator.randomUUID());
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			model.setModiuser(uc.getUsername());
			mapper.insertSyslog(model);
		} else {
			mapper.updateSyslog(model);
		}
	}

	/**
	 * 删除系统日志
	 * 
	 * @param ids
	 */
	@Transactional
	public void delSyslog(String ids) {
		SyslogDao mapper = sqlSession.getMapper(SyslogDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delSyslog(id);
		}
	}
	
	
	/**
	 * 得到
	 * @param id
	 * @return
	 */
	public  Syslog getSyslogById(String id){
		SyslogDao mapper = sqlSession.getMapper(SyslogDao.class);
		return mapper.getSyslogById(id);
	}

		
}