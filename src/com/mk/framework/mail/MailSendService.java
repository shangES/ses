package com.mk.framework.mail;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.testng.annotations.Test;

import com.mk.audition.entity.AuditionRecord;
import com.mk.framework.grid.util.DateUtil;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.mycandidates.entity.Recommend;
import com.mk.person.entity.Person;
import com.mk.resume.entity.Resume;
import com.mk.thirdpartner.entity.ThirdPartner;

public class MailSendService {
	private JavaMailSender sender;
	private FreeMarkerConfigurer freeMarkerConfigurer = null;// FreeMarker的技术类

	@Value("${send.mail.server.from}")
	private String fromMailAddr;
	
	@Value("${app.message.open}")
	private boolean open;

	// ==================================================
	/**
	 * 发送推荐邮件
	 * 
	 * @param mycandidates
	 * @param resume
	 * @param recommend
	 * @throws Exception
	 */
	public void sendTuiJianMailTo(MyCandidates mycandidates, Resume resume,Recommend recommend) throws Exception {
		if (!open)
			return ;

		MimeMessage msg = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, false, "gb2312");
		helper.setFrom(fromMailAddr);
		helper.setTo(recommend.getUseremail());
		helper.setSubject("华数推荐信");

		// 通过指定模板名获取FreeMarker模板实例
		freemarker.template.Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("tuijian.ftl");
		Map map = new HashMap();
		map.put("name", resume.getName());
		map.put("username", recommend.getUsername());
		map.put("deptname", recommend.getRecommenddeptname());
		map.put("postname", recommend.getRecommendpostname());
		map.put("moditimestamp", recommend.getModitimestamp());
		map.put("recommendname", recommend.getModiuser());
		
		// 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
		helper.setText(htmlText, true);
		sender.send(msg);
	}
	
	
	/**
	 * 发送面試邮件(应聘者)
	 * 
	 * @param mycandidates
	 * @param resume
	 * @param recruitpost
	 * @param auditionrecord
	 * @throws Exception
	 */
	public void sendMianShiMailTo(MyCandidates mycandidates, Resume resume, Recommend recommend, AuditionRecord auditionrecord,String operationMobile,String name) throws Exception {
		if (!open)
			return ;

		MimeMessage msg = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, false, "gb2312");
		helper.setFrom(fromMailAddr);
		helper.setTo(resume.getEmail());
		helper.setSubject("华数面试函");

		// 通过指定模板名获取FreeMarker模板实例
		freemarker.template.Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("mianshi.ftl");
		Map map = new HashMap();
		map.put("name", resume.getName());
		map.put("auditiondate", auditionrecord.getAuditiondate());
		map.put("auditionaddress", auditionrecord.getAuditionaddress());
		map.put("deptname", recommend.getRecommenddeptname());
		map.put("postname", recommend.getRecommendpostname());
		map.put("moditimestamp", auditionrecord.getModitimestamp());
		map.put("mobile", operationMobile);
		map.put("people", name);

		// 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
		helper.setText(htmlText, true);
		sender.send(msg);

	}
	
	
	
	/**
	 * 发送录入人才库(人才)
	 * 
	 * @param mycandidates
	 * @param resume
	 * @param recruitpost
	 * @param auditionrecord
	 * @throws Exception
	 */
	public void sendResumeMailTo(String name,Timestamp date,String email) throws Exception {
		if (!open)
			return ;

		MimeMessage msg = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, false, "gb2312");
		helper.setFrom(fromMailAddr);
		helper.setTo(email);
		helper.setSubject("华数人才库通知");

		// 通过指定模板名获取FreeMarker模板实例
		freemarker.template.Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("rencai.ftl");
		Map map = new HashMap();
		map.put("name", name);
		map.put("moditimestamp", date);
		map.put("email", email);

		// 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
		helper.setText(htmlText, true);
		sender.send(msg);

	}
	
	
	/**
	 * 发送面試邮件(面试官)
	 * 
	 * @param mycandidates
	 * @param resume
	 * @param recruitpost
	 * @param auditionrecord
	 * @throws Exception
	 */
	public void sendMianShiMailToInterviewer(MyCandidates mycandidates, Resume resume, Recommend recommend, AuditionRecord auditionrecord,String operationMobile,String name) throws Exception {
		if (!open)
			return ;

		MimeMessage msg = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, false, "gb2312");
		helper.setFrom(fromMailAddr);
		helper.setTo(auditionrecord.getEmployeeemail());
		helper.setSubject("华数面试函");

		// 通过指定模板名获取FreeMarker模板实例
		freemarker.template.Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("msByInterviewer.ftl");
		Map map = new HashMap();
		map.put("username", auditionrecord.getMaininterviewerguidname());
		map.put("name", resume.getName());
		map.put("auditiondate", auditionrecord.getAuditiondate());
		map.put("auditionaddress", auditionrecord.getAuditionaddress());
		map.put("deptname", recommend.getRecommenddeptname());
		map.put("postname", recommend.getRecommendpostname());
		map.put("moditimestamp", auditionrecord.getModitimestamp());
		map.put("mobile", operationMobile);
		map.put("people", name);

		// 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
		helper.setText(htmlText, true);
		sender.send(msg);

	}
	
	
	/**
	 * 面试通过(应聘者)
	 * 
	 * @param resume
	 * @param recruitpost
	 */
	public void sendTongGuoMailTo(Resume resume,String operationMobile,String name) throws Exception {
		if (!open)
			return ;

		
		MimeMessage msg = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, false, "gb2312");
		helper.setFrom(fromMailAddr);
		helper.setTo(resume.getEmail());
		helper.setSubject("华数招聘面试通过通知");

		// 通过指定模板名获取FreeMarker模板实例
		freemarker.template.Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("tongguo.ftl");
		Map map = new HashMap();
		map.put("name", resume.getName());
		map.put("moditimestamp", DateUtil.formatDateYMDHHmmSS(new Date(System.currentTimeMillis())));
		map.put("mobile", operationMobile);
		map.put("people", name);
		
		// 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
		helper.setText(htmlText, true);
		sender.send(msg);
	}
	
	

	/**
	 * 待体检
	 * 
	 * @param thirdpartner
	 * @param msg
	 * @throws Exception
	 */
	public void sendTiJianMailTo(ThirdPartner thirdpartner, String info,String operationMobile,String name) throws Exception {
		if (!open)
			return ;

		MimeMessage msg = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, false, "gb2312");
		helper.setFrom(fromMailAddr);
		helper.setTo(thirdpartner.getEmail());
		helper.setSubject("华数员工待按排体检通知");

		// 通过指定模板名获取FreeMarker模板实例
		freemarker.template.Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("tijian.ftl");
		Map map = new HashMap();
		map.put("thirdpartnername", thirdpartner.getThirdpartnername());
		map.put("name", thirdpartner.getName());
		map.put("info", info);
		map.put("moditimestamp", DateUtil.formatDateYMDHHmmSS(new Date(System.currentTimeMillis())));
		map.put("mobile", operationMobile);
		map.put("people", name);

		// 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
		helper.setText(htmlText, true);
		sender.send(msg);
	}

	// ====================================================

	/**
	 * 入职邮件提示(应聘者)
	 * 
	 * @param resume
	 * @param recruitpost
	 */
	public void sendRuZhiMailTo(Resume resume, Person person,String operationMobile) throws Exception {
		if (!open){
			System.out.println("邮件控制关闭!");
			return ;
		}
			
		MimeMessage msg = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, false, "gb2312");
		helper.setFrom(fromMailAddr);
		helper.setTo(resume.getEmail());
		helper.setSubject("华数员工待入职资料填写通知");

		// 通过指定模板名获取FreeMarker模板实例
		freemarker.template.Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("ruzhi.ftl");
		Map map = new HashMap();
		map.put("name", resume.getName());
		map.put("bdr", person.getRegisusername());
		map.put("bdrq", person.getJoindate());
		map.put("bddd", person.getRegisaddress());
		map.put("deptname", person.getDeptname());
		map.put("postname", person.getPostname());
		map.put("mobile", operationMobile);
		map.put("moditimestamp", DateUtil.formatDateYMDHHmmSS(new Date(System.currentTimeMillis())));

		// 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
		helper.setText(htmlText, true);
		sender.send(msg);

	}
	
	
	
	
	/**
	 * 入职邮件提示(报到人)
	 * 
	 * @param resume
	 * @param recruitpost
	 */
	public void sendRuZhiMailToByPeople(Resume resume, Person person,String operationMobile) throws Exception {
		if (!open)
			return ;

		MimeMessage msg = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, false, "gb2312");
		helper.setFrom(fromMailAddr);
		helper.setTo(person.getRegisuseremail());
		helper.setSubject("华数员工报到通知");

		// 通过指定模板名获取FreeMarker模板实例
		freemarker.template.Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("people.ftl");
		Map map = new HashMap();
		map.put("name", resume.getName());
		map.put("bdr", person.getRegisusername());
		map.put("bdrq", person.getJoindate());
		map.put("bddd", person.getRegisaddress());
		map.put("deptname", person.getDeptname());
		map.put("postname", person.getPostname());
		map.put("moditimestamp", DateUtil.formatDateYMDHHmmSS(new Date(System.currentTimeMillis())));
 		map.put("mobile", operationMobile);

		// 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
		helper.setText(htmlText, true);
		sender.send(msg);
	}
	
	

	// ====================================================

	/**
	 * 短信发送
	 * 
	 * @param phoneNumber
	 * @param message
	 */
	@Test
	public void sendShortMessage(String phoneNumber, String message) {
		if (!open)
			return ;

		System.out.println("=========");
////		 Sms sms = new Sms(smsServer); 
////		 String[] destinationAddresses = new String[]{phoneNumber};
////		 String extendCode = "0101"; //自定义扩展代码（模块）
////		 String ApplicationID= "openmas"; 
////		 String Password = "openmas"; //发送短信
////		 String gateWayid = sms.SendMessage(destinationAddresses, message,extendCode, ApplicationID, Password);
////		 System.out.println(gateWayid);
//		
//
//        //Account.
//        String account = "yangkai";
//        //Password.
//        String password = "13858020284";
//        //Area code.
//        String areaCode = "0571";
//        //Service code.
//        String serviceCode = "9999";
//        //Enterprise code.
//        String enterpriseCode = "001062";
//
//        //Get current directory.
//        String userDirectory = System.getProperty("user.dir");
//        //Create work directory.
//        File workDirectory = new File(userDirectory);
//        //Create directory.
//        File logDirectory = new File(userDirectory,"logs");
//        //Check result.
//        if(!logDirectory.exists() && !logDirectory.mkdir())
//        {
//            System.out.println("SGIPAPI.main : fail to create log directory !");
//            return;
//        }
//        //Log mode.
//        int logMode = LogMode.LOG_ALL;
//        //Initialize log.
//        if(!SimpleLog.initialize(logDirectory,logMode))
//        {
//            System.out.println("SGIPAPI.main : fail to initialize log !");
//            return;
//        }
//
//        try
//        {
//            //Create API.
//            SGIPAPI sgipApi = new SGIPAPI(areaCode,enterpriseCode,serviceCode,account,password);
//            //Connect.
//            if(sgipApi.connect("127.0.0.1",7890))
//            {
//                //Print.
//                System.out.println("SGIPAPI.main : connection was built !");
//
//                //Count.
//                int totalCount = 1,sendCount = 0,reportCount = 0;
//                //Do while.
//                while(reportCount < totalCount)
//                {
//                    //Check send count.
//                    if(sendCount < totalCount)
//                    {
//                        //Create submit.
//                        SGIPSubmit submit = new SGIPSubmit(sgipApi.nextSequence());
//
//                        //SP Number
//                        submit.sp_number = serviceCode;
//                        //Charge Number
//                        submit.charge_number = null;
//                        //User Count
//                        submit.user_count = 1;
//                        //User Number
//                        submit.user_number = new String[]{"18868415976"};
//                        //Corporation ID
//                        submit.corporation_id = enterpriseCode;
//                        //Service Type
//                        submit.service_type = "HELP";
//                        //Fee Type
//                        submit.fee_type = 0;
//                        //Fee Value
//                        submit.fee_value = null;
//                        //Given Value
//                        submit.given_value = null;
//                        //Agent Flag
//                        submit.agent_flag = 0;
//                        //Mo Relate to MT Flag
//                        submit.mo_relate_to_mt_flag = 2;
//                        //Expire Time
//                        submit.expire_time = null;
//                        //Schedule Time
//                        submit.schedule_time = null;
//                        //Report Flag
//                        submit.report_flag = 2;
//                        //TP_PID
//                        submit.tp_pid = 0;
//                        //TP_UDHI
//                        submit.tp_udhi = 0;
//                        //Message Coding
//                        submit.message_coding = 0;
//                        //Message Type
//                        submit.message_type = 0;
//                        //Message Content
//                        submit.message_content = "Hello World !".getBytes("US-ASCII");
//                        //Message Length
//                        submit.message_length = submit.message_content.length;
//                        //Reserved Field
//                        submit.reserve = null;
//
//                        //Write packet.
//                        sgipApi.writePacket(submit);
//                        //Add send count and clear flag.
//                        sendCount ++;
//                    }
//
//                    //Check available.
//                    if(sgipApi.available())
//                    {
//                        //Read packet.
//                        SGIPPacket packet = sgipApi.readPacket();
//                        //Check instance.
//                        if(packet instanceof SGIPDeliver)
//                        {
//                            //Get deliver.
//                            SGIPDeliver deliver = (SGIPDeliver)packet;
//                            //Create deliver response.
//                            SGIPDeliverResponse response = new SGIPDeliverResponse(packet.sequence);
//                            //Set result.
//                            response.result = 0;
//                            //Write response.
//                            sgipApi.writePacket(response);//Do response as soon as possible.
//
//                            ///////////////////////////////////////////////////////////////////////////
//                            //
//                            // Do process of deliver.
//                            //
//                            // <p>You can add your own processing here !</p>
//                            //
//                            //Print.
//                            System.out.println("SGIPAPI.main : normal/source(" + deliver.user_number + ")");
//                            //
//                            ///////////////////////////////////////////////////////////////////////////
//                        }
//                        else if(packet instanceof SGIPReport)
//                        {
//                            //Get report.
//                            SGIPReport report = (SGIPReport)packet;
//                            //Create report response.
//                            SGIPReportResponse response = new SGIPReportResponse(packet.sequence);
//                            //Set result.
//                            response.result = 0;
//                            //Write response.
//                            sgipApi.writePacket(response);//Do response as soon as possible.
//
//                            //Add report count.
//                            reportCount ++;
//                            ///////////////////////////////////////////////////////////////////////////
//                            //
//                            // Do process of deliver.
//                            //
//                            // <p>You can add your own processing here !</p>
//                            //
//                            //Print.
//                            System.out.println("SGIPAPI.main : report/status(" + report.error_code + ")");
//                            //
//                            ///////////////////////////////////////////////////////////////////////////
//                        }
//                        //Check command.
//                        else if(packet instanceof SGIPSubmitResponse)
//                        {
//                            //Create submit response.
//                            SGIPSubmitResponse response = (SGIPSubmitResponse)packet;
//                            //Print.
//                            System.out.println("SGIPAPI.main : submit/result(" + response.result + ")");
//
//                            ///////////////////////////////////////////////////////////////////////////
//                            //
//                            // Do process of submit response.
//                            //
//                            // <p>You can add your own processing here !</p>
//                            //
//                            //
//                            ///////////////////////////////////////////////////////////////////////////
//                        }
//                    }
//                }
//            }
//            else
//            {
//                //Print.
//                System.out.println("SGIPAPI.main : fail to connect !");
//            }
//            //Close.
//            sgipApi.close();
//            //Print.
//            System.out.println("SGIPAPI.main : connection was closed !");
//        }
//        catch(Exception e)
//        {
//            //Print.
//            System.out.println("SGIPAPI.main : " + e.getMessage());
//            System.out.println("SGIPAPI.main : unexpected exit !");
//        }
//
//        //Uninitialize log.
//        SimpleLog.uninitialize();
//
//        //Exit
//        System.exit(0);
//	
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		 
	}

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	public void setSender(JavaMailSender sender) {
		this.sender = sender;
	}

}
