package com.mk.system.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.mail.SendMessageService;

@Controller
public class SendMsgAction {

	@Autowired
	private SendMessageService sendMessageService;

	/**
	 * 单个发送短信
	 * 
	 * @param phonenum
	 * @param msgcontent
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/message/sendMessage.do")
	@ResponseBody
	public String sendMessage(String phonenum, String msgcontent) throws Exception {
		String result="";
		if (!phonenum.isEmpty()&&!msgcontent.isEmpty()) {
			result = sendMessageService.sendMessage(msgcontent, phonenum);
		}
		return result;
	}

	/**
	 * 批量发送短信
	 * 
	 * @param phonenum
	 * @param msgcontent
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/message/sendMessage_Sum.do")
	@ResponseBody
	public void sendMessage_Sum(String phonenum_sum, String msgcontent_sum) throws Exception {
		if (!phonenum_sum.isEmpty()&&!msgcontent_sum.isEmpty()) {
		  sendMessageService.sendMessage_Sum(msgcontent_sum, phonenum_sum);
	  }
	}
}
