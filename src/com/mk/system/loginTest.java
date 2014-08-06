package com.mk.system;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mk.system.service.UserService;

/**
 * Created by IntelliJ IDEA. User: zhangyu Date: 12-7-3 Time: 下午3:56 To change
 * this template use File | Settings | File Templates.
 */

@ContextConfiguration(locations = { "/spring-context.xml" })
public class loginTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private UserService userService;

	@Test
	public void login() throws IOException {
		userService.loadUserByUsername("T0001");

	}

}
