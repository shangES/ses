package com.mk.framework.mail;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA. User: zhangyu Date: 12-7-3 Time: 下午3:56 To change
 * this template use File | Settings | File Templates.
 */

@ContextConfiguration(locations = { "/spring-context.xml" })
public class SendMessageServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private SendMessageService sendMessageService;

	@Test
	public void login() throws IOException {
		//sendMessageService.sendMessage("美科短信测试", "18868415976,13071861380");

	}

}
