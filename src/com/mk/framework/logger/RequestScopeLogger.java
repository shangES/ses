package com.mk.framework.logger;

import org.springframework.util.StringUtils;

/**
 * Created by IntelliJ IDEA. User: zhangyu Date: 11-1-24 Time: 上午11:40
 */
public class RequestScopeLogger {
	private StringBuffer datapack = new StringBuffer();

	public void setExecutInfo(String sql, String params) {
		if (StringUtils.hasLength(sql) && sql.toLowerCase().trim().startsWith("select"))
			return;
		datapack.append(sql);
		datapack.append("\n");
		datapack.append(params);
		datapack.append("\n");
	}

}
