package com.mk.framework.logger;

import com.mk.framework.context.UserContext;
import com.mk.framework.spring.SpringContextHolder;

/**
 * Created by IntelliJ IDEA. User: zhangyu Date: 11-1-24 Time: 上午11:52
 */
public class LogTools {
	public static void log(String description, UserContext context) {
		RequestScopeLogger logger = SpringContextHolder.getBean("requestLogger");
	}
}
