package com.mk.framework.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;

public class ButtonPurviewInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String reqUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		if (StringUtils.hasLength(contextPath)) {
			reqUri = reqUri.substring(contextPath.length() + 1);
		} else {
			reqUri = reqUri.substring(1);
		}
		UserContext context = ContextFacade.getUserContext();
		if (context == null)
			return true;
		request.setAttribute("userid", context.getUserId());
		request.setAttribute("username", context.getUsername());

		// 组织
		request.setAttribute("companyid", context.getCompanyid());
		request.setAttribute("companyname", context.getCompanyname());
		request.setAttribute("deptname", context.getDeptname());
		request.setAttribute("postname", context.getPostname());
		request.setAttribute("admin", context.isAdmin());
		request.setAttribute("mobile", context.getMobile());

		List<String> hasPurviewButton = context.accessableOperation(reqUri);
		if (hasPurviewButton != null)
			for (String button : hasPurviewButton) {
				if (StringUtils.hasLength(button))
					request.setAttribute(button, true);
			}
		return true;
	}
}
