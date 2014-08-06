package com.mk.framework.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 这个类后期需要替换掉，用annotation替代，具体去查文档 2011-01-11 悲剧啊，居然没有弄好，就先用这个吧
 * 
 * @date 2010-11-25
 */

public class MkHandlerExceptionResolver extends SimpleMappingExceptionResolver {

	private Logger logger = LoggerFactory.getLogger(MkHandlerExceptionResolver.class);

	/**
	 * 异常的默认处理类
	 */
	public ModelAndView resolveException(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception ex) {
		ex.printStackTrace();
		logger.warn("Handle exception: " + ex.getClass().getName());
		Map<String, String> model = new HashMap<String, String>();
		model.put("ex", ex.getClass().getSimpleName());
		model.put("error", ex.getMessage());
		return new ModelAndView("/error.jsp", model);
	}

}
