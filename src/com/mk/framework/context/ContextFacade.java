package com.mk.framework.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ContextFacade {
	public static UserContext getUserContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null)
			return null;
		Object principal = authentication.getPrincipal();
		if (principal instanceof String)
			return null;
		return (UserContext) principal;
	}
}
