package com.mk.framework.mail;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.mk.audition.entity.AuditionRecord;
import com.mk.framework.grid.util.DateUtil;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.note.SmsHttpBindingStub;
import com.mk.framework.note.Sms_ServiceLocator;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.mycandidates.entity.Recommend;
import com.mk.person.entity.Person;
import com.mk.resume.entity.Resume;
import com.mk.thirdpartner.entity.ThirdPartner;

public class SendMessageService {
	@Value("${send.message.cilent.corpid}")
	private String corpid;

	@Value("${send.message.cilent.username}")
	private String username;

	@Value("${send.message.cilent.password}")
	private String password;

	@Value("${send.message.cilent.sendtime}")
	private String sendtime;

	@Value("${send.message.cilent.type}")
	private String type;

	@Value("${send.message.cilent.reserved1}")
	private String reserved1;

	@Value("${send.message.cilent.reserved2}")
	private String reserved2;

	@Value("${send.message.cilent.reserved3}")
	private String reserved3;

	@Value("${app.message.open}")
	private boolean open;

	private FreeMarkerConfigurer freeMarkerConfigurer;// FreeMarker的技术类

	public FreeMarkerConfigurer getFreeMarkerConfigurer() {
		return freeMarkerConfigurer;
	}

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	/**
	 * 发送短信方法
	 * 
	 * @param messageContent
	 * @param phoneNum
	 * @return
	 */
	public String sendMessage(String messageContent, String phoneNum) {
		if (!open)
			return "短息网关关闭";

		String result = "";
		if (StringUtils.isNotEmpty(phoneNum) && StringUtils.isNotEmpty(messageContent) && phoneNum.length() == 11) {

			// 生成20位流水号
			String time = System.nanoTime() + "" + System.currentTimeMillis();
			String serialnumber = time.substring(0, 20);

			Sms_ServiceLocator service = new Sms_ServiceLocator();
			SmsHttpBindingStub sms;
			try {
				sms = (SmsHttpBindingStub) service.getSmsHttpPort();
				result = sms.sms(corpid, username, password, messageContent, phoneNum, serialnumber, sendtime, type, reserved1, reserved2, reserved3);
				// System.out.println("返回内容:"+result);

			} catch (Exception e) {
				e.printStackTrace();
			}

			return result;
		}

		return "发送失败";

	}

	/**
	 * 批量发送短信
	 * 
	 * @param messageContent
	 * @param phoneNum
	 * @return
	 */
	public String sendMessage_Sum(String messageContent, String phoneNum) {
		if (!open)
			return "短息网关关闭";

		String result = "";
		if (StringUtils.isNotEmpty(phoneNum) && StringUtils.isNotEmpty(messageContent)) {

			String[] phonearray = phoneNum.split(",");
			for (String phone : phonearray) {
				if (phone.length() == 11) {
					// 生成20位流水号
					String time = System.nanoTime() + "" + System.currentTimeMillis();
					String serialnumber = time.substring(0, 20);

					Sms_ServiceLocator service = new Sms_ServiceLocator();
					SmsHttpBindingStub sms;
					try {
						sms = (SmsHttpBindingStub) service.getSmsHttpPort();
						result = sms.sms(corpid, username, password, messageContent, phone, serialnumber, sendtime, type, reserved1, reserved2, reserved3);
						// System.out.println("返回内容:"+result);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return result;
		}

		return "发送失败";

	}

	/**
	 * 发送推荐短信(部门领导)
	 * 
	 * @param mycandidates
	 * @param resume
	 * @param recommend
	 * @return
	 * @throws Exception
	 */
	public void sendTuiJianMsgTo(String resumename, String deptUserName, String deptUserMobile) throws Exception {
		if (!open){
			return ;
		}
			
		 // 通过指定模板名获取FreeMarker模板实例
		 freemarker.template.Template tpl =freeMarkerConfigurer.getConfiguration().getTemplate("msg_tuijian.ftl");
		 Map map = new HashMap();
		 map.put("name", resumename);
		 map.put("username", deptUserName);
		 map.put("moditimestamp", DateUtil.formatDateYMDHHmmChina(new Timestamp(System.currentTimeMillis())));
		
		 // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		 String msg = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,map);
		 sendMessage(msg, deptUserMobile);
	}

	/**
	 * 发送面试短信(应聘者)
	 * 
	 * @param mycandidates
	 * @param resume
	 * @param recommend
	 * @param auditionrecord
	 * @throws Exception
	 */
	public void sendMianShiMsgTo(MyCandidates mycandidates, Resume resume, Recommend recommend, AuditionRecord auditionrecord, String operationMobile,String name) throws Exception {
		if (!open)
			return ;
		
		 // 通过指定模板名获取FreeMarker模板实例
		 freemarker.template.Template tpl =freeMarkerConfigurer.getConfiguration().getTemplate("msg_mianshi.ftl");
		 Map map = new HashMap();
		 map.put("name", resume.getName());
		 map.put("auditiondate", auditionrecord.getAuditiondate());
		 map.put("auditionaddress", auditionrecord.getAuditionaddress());
		 map.put("deptname", recommend.getRecommenddeptname());
		 map.put("postname", recommend.getRecommendpostname());
		 map.put("moditimestamp",
		 DateUtil.formatDateYMDHHmmChina(auditionrecord.getModitimestamp()));
		 map.put("mobile", operationMobile);
		 map.put("people", name);
		
		 // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		 String msg = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,map);
		 sendMessage(msg, resume.getMobile());
	}

	
	/**
	 * 评价后发给人才信息
	 * 
	 * @param mycandidates
	 * @param resume
	 * @param recommend
	 * @param auditionrecord
	 * @throws Exception
	 */
	public void sendResumeAssessMsgTo(String name,Timestamp date,String mobile,String email) throws Exception {
		if (!open)
			return ;
		
		 // 通过指定模板名获取FreeMarker模板实例
		 freemarker.template.Template tpl =freeMarkerConfigurer.getConfiguration().getTemplate("msg_rencai.ftl");
		 Map map = new HashMap();
		 map.put("name", name);
		 map.put("moditimestamp",DateUtil.formatDateYMDHHmmChina(date));
		 map.put("email", email);
		
		 // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		 String msg = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,map);
		 sendMessage(msg, mobile);
	}
	
	
	/**
	 * 发送面试短信(面试官)
	 * 
	 * @param mycandidates
	 * @param resume
	 * @param recommend
	 * @param auditionrecord
	 * @throws Exception
	 */
	public void sendMianShiMsgToInterviewer(String resumename, String deptUserName, String deptUserMobile) throws Exception {
		if (!open)
			return ;
		
		 //通过指定模板名获取FreeMarker模板实例
		 freemarker.template.Template tpl =freeMarkerConfigurer.getConfiguration().getTemplate("msg_msByInterviewer.ftl");
		 Map map = new HashMap();
		 map.put("username", deptUserName);
		 map.put("name", resumename);
		 map.put("moditimestamp", DateUtil.formatDateYMDHHmmChina(new
		 Timestamp(System.currentTimeMillis())));
		
		 // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		 String msg = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,map);
		 sendMessage(msg, deptUserMobile);
	}

	/**
	 * 面试通过(应聘者)
	 * 
	 * @param resume
	 * @param person
	 * @throws Exception
	 */
	public void sendTongGuoMsgTo(Resume resume, String operationMobile,String name) throws Exception {
		if (!open)
			return ;
		
		// 通过指定模板名获取FreeMarker模板实例
		 freemarker.template.Template tpl =freeMarkerConfigurer.getConfiguration().getTemplate("msg_tongguo.ftl");
		 Map map = new HashMap();
		 map.put("name", resume.getName());
		 map.put("moditimestamp", DateUtil.formatDateYMDHHmmChina(new Date(System.currentTimeMillis())));
		 map.put("mobile", operationMobile);
		 map.put("people", name);
		
		 // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		 String msg = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,map);
		 sendMessage(msg, resume.getMobile());
	}

	/**
	 * 待体检短信
	 * 
	 * @param thirdpartner
	 * @param info
	 * @throws Exception
	 */
	public void sendTiJianMsgTo(ThirdPartner thirdpartner, String info, String operationMobile,String name) throws Exception {
		if (!open)
			return ;
		 // 通过指定模板名获取FreeMarker模板实例
		 freemarker.template.Template tpl =freeMarkerConfigurer.getConfiguration().getTemplate("msg_tijian.ftl");
		 Map map = new HashMap();
		 map.put("thirdpartnername", thirdpartner.getThirdpartnername());
		 map.put("name", thirdpartner.getName());
		 map.put("info", info);
		 map.put("moditimestamp", DateUtil.formatDateYMDHHmmChina(new Date(System.currentTimeMillis())));
		 map.put("mobile", operationMobile);
		 map.put("people", name);
		
		 // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		 String msg = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,map);
		 sendMessage(msg, thirdpartner.getMobile());
	}

	/**
	 * 入职短信提醒(应聘者)
	 * 
	 * @param resume
	 * @param person
	 * @throws Exception
	 */
	public void sendRuZhiMsgTo(Resume resume, Person person, String operationMobile) throws Exception {
		if (!open){
			System.out.println("短信控制关闭!");
			return ;
		}
		
		 // 通过指定模板名获取FreeMarker模板实例
		 freemarker.template.Template tpl =freeMarkerConfigurer.getConfiguration().getTemplate("msg_ruzhi.ftl");
		 Map map = new HashMap();
		 map.put("name", resume.getName());
		 map.put("bdr", person.getRegisusername());
		 map.put("bdrq", person.getJoindate());
		 map.put("bddd", person.getRegisaddress());
		 map.put("deptname", person.getDeptname());
		 map.put("postname", person.getPostname());
		 map.put("moditimestamp", DateUtil.formatDateYMDHHmmChina(new Date(System.currentTimeMillis())));
		 map.put("mobile", operationMobile);
		
		 // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		 String msg = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,map);
		 sendMessage(msg, resume.getMobile());
	}

	/**
	 * 入职短信提示(报到人)
	 * 
	 * @param resume
	 * @param recruitpost
	 */
	public void sendRuZhiMsgToByPeople(Resume resume, Person person, String operationMobile) throws Exception {
		
		if (!open)
			return ;
		// 通过指定模板名获取FreeMarker模板实例
		 freemarker.template.Template tpl =
		 freeMarkerConfigurer.getConfiguration().getTemplate("msg_people.ftl");
		 Map map = new HashMap();
		 map.put("name", resume.getName());
		 map.put("bdr", person.getRegisusername());
		 map.put("bdrq", person.getJoindate());
		 map.put("bddd", person.getRegisaddress());
		 map.put("deptname", person.getDeptname());
		 map.put("postname", person.getPostname());
		 map.put("moditimestamp", DateUtil.formatDateYMDHHmmChina(new Date(System.currentTimeMillis())));
		 map.put("mobile", operationMobile);
		
		 // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		 String msg = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
		 sendMessage(msg, person.getRegisusermobile());
	}

}
