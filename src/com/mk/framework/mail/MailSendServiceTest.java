package com.mk.framework.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "/spring-context.xml" })
public class MailSendServiceTest {
	@Autowired
	private MailSendService service;

	@Test
	public void testFindUserByLoginName() throws Exception {
//		for (int i = 0; i < 30; i++) {
//			service.sendMailTo("循环邮件测试，这是第0封", "715400104@qq.com", "这是一封来自华数的测试邮件！");
//		}
		service.sendShortMessage("11", "11");
	}
}
