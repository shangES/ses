package com.mk.framework.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.mk.ReadEmail51JobFileUtils;
import com.mk.ReadEmailPersonFileUtils;
import com.mk.ReadEmailZlZPFileUtils;
import com.mk.framework.constance.Constance;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.spring.SpringContextHolder;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.NameConvertCodeService;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.recruitment.dao.WebUserDao;
import com.mk.recruitment.entity.WebUser;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.Resume;
import com.mk.resume.entity.ResumeEamil;
import com.mk.resume.entity.ResumeEamilFile;
import com.mk.resume.entity.ResumeFile;

/**
 * 有一封邮件就需要建立一个ReciveMail对象
 */
public class MailReceiveService {

	// 请求来源邮件序号
	public static String CONFIGEMAIL = "email";
	public static String CONFIGEMAIL_PWD = "email_pwd";
	public static String CONFIGEMAIL_POP = "email_pop";

	// ==
	private MimeMessage mimeMessage = null;
	private String saveAttachPath; // 附件下载后的存放目录
	private String saveAttachFile; // 附件下载后的存放目录
	private StringBuffer bodytext = new StringBuffer();// 存放邮件内容
	//
	private String host;
	private String username;
	private String password;

	public MailReceiveService() {

	}

	public MailReceiveService(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	/**
	 * 获得发件人的地址和姓名
	 */
	public String getFrom() {
		String from = null;
		try {
			InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
			from = address[0].getAddress();
			if (from == null)
				from = "";
		} catch (MessagingException e) {
			System.out.println("非法域名");
		}
		return from;
	}

	/**
	 * 获得发件人的地址和姓名
	 */
	public String getPersonal() {
		String personal = null;
		try {
			InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
			personal = address[0].getPersonal();
			if (personal == null){
				String from = address[0].getAddress();
				if (from == null){
					personal = "";
				}else{
					personal=from;
				}
			}
		} catch (MessagingException e) {
			System.out.println("非法域名");
		}
		return personal;
	}

	/**
	 * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同 "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址
	 */
	public String getMailAddress(String type) throws Exception {
		String mailaddr = "";
		String addtype = type.toUpperCase();
		InternetAddress[] address = null;
		if (addtype.equals("TO") || addtype.equals("CC") || addtype.equals("BCC")) {
			if (addtype.equals("TO")) {
				address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
			} else if (addtype.equals("CC")) {
				address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
			} else {
				address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
			}
			if (address != null) {
				for (int i = 0; i < address.length; i++) {
					String email = address[i].getAddress();
					if (email == null)
						email = "";
					else {
						email = MimeUtility.decodeText(email);
					}
					String personal = address[i].getPersonal();
					if (personal == null)
						personal = "";
					else {
						personal = MimeUtility.decodeText(personal);
					}
					String compositeto = personal + "<" + email + ">";
					mailaddr += "," + compositeto;
				}
				if (StringUtils.isNotEmpty(mailaddr))
					mailaddr = mailaddr.substring(1);
			}
		} else {
			throw new Exception("Error emailaddr type!");
		}
		return mailaddr;
	}

	/**
	 * 获得邮件主题
	 */
	public String getSubject() throws MessagingException {
		String subject = "";
		try {
			subject = MimeUtility.decodeText(mimeMessage.getSubject());
			if (subject == null)
				subject = "";

		} catch (Exception exce) {
		}
		return subject;
	}

	/**
	 * 获得邮件发送日期
	 */
	public Date getSentDate() throws Exception {
		return mimeMessage.getSentDate();
	}

	/**
	 * 获得邮件正文内容
	 */
	public String getBodyText() {
		if (bodytext == null || bodytext.length() <= 0)
			return " ";
		return bodytext.toString();
	}

	/**
	 * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
	 */
	public void getMailContent(Part part, boolean state) {
		try {
			String contenttype = part.getContentType();
			int nameindex = contenttype.indexOf("name");
			boolean conname = false;
			if (nameindex != -1)
				conname = true;
			// System.out.println("CONTENTTYPE: " + contenttype);

			if (part.isMimeType("text/plain") && !conname && state) {
				bodytext.append((String) part.getContent());
			} else if (part.isMimeType("text/html") && !conname) {
				bodytext.append((String) part.getContent());
			} else if (part.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) part.getContent();
				getMailContent(multipart.getBodyPart(0), false);
			} else if (part.isMimeType("message/rfc822")) {
				getMailContent((Part) part.getContent(), true);
			} else {
			}
		} catch (Exception e) {
			System.out.println("邮件内容异常！");
		}
	}

	/**
	 * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
	 */
	public boolean getReplySign() throws MessagingException {
		boolean replysign = false;
		String needreply[] = mimeMessage.getHeader("Disposition-Notification-To");
		if (needreply != null) {
			replysign = true;
		}
		return replysign;
	}

	/**
	 * 获得此邮件的Message-ID
	 */
	public String getMessageId() throws MessagingException {
		return mimeMessage.getMessageID();
	}

	/**
	 * 【判断此邮件是否已读，如果未读返回返回false,反之返回true】
	 */
	public boolean isNew() throws MessagingException {
		boolean isnew = false;
		Flags flags = ((Message) mimeMessage).getFlags();
		Flags.Flag[] flag = flags.getSystemFlags();
		// System.out.println("flags's length: " + flag.length);
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == Flags.Flag.SEEN) {
				isnew = true;
				break;
			}
		}
		return isnew;
	}

	/**
	 * 判断此邮件是否包含附件
	 */
	public boolean isContainAttach(Part part) throws Exception {
		boolean attachflag = false;
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = mpart.getDisposition();
				if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))))
					attachflag = true;
				else if (mpart.isMimeType("multipart/*")) {
					attachflag = isContainAttach((Part) mpart);
				} else {
					String contype = mpart.getContentType();
					if (contype.toLowerCase().indexOf("application") != -1)
						attachflag = true;
					if (contype.toLowerCase().indexOf("name") != -1)
						attachflag = true;
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			attachflag = isContainAttach((Part) part.getContent());
		}
		return attachflag;
	}

	/**
	 * 【保存附件】
	 */
	public void saveAttachMent(Part part, ResumeDao resumeDao, String webuserguid, String resumeeamilguid) {
		// sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();

		String fileName = "";
		try {
			if (part.isMimeType("multipart/*")) {
				Multipart mp = (Multipart) part.getContent();
				for (int i = 0; i < mp.getCount(); i++) {
					BodyPart mpart = mp.getBodyPart(i);
					String disposition = mpart.getDisposition();
					if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
						fileName = mpart.getFileName();

						if (StringUtils.isEmpty(fileName))
							return;
						fileName = MimeUtility.decodeText(fileName);
						// System.out.println("0000mpart.getFileName()=====" +
						// fileName);
						// // 截取文件名转码
						// fileName = fileName.substring(fileName.indexOf("B?")
						// + 2,
						// fileName.indexOf("?="));
						// fileName = new
						// String(decoder.decodeBuffer(fileName));
						// System.out.println("0000mpart.getFileName()=====" +
						// fileName);
						saveFile(fileName, mpart.getInputStream(), resumeDao, webuserguid, resumeeamilguid);
					} else if (mpart.isMimeType("multipart/*")) {
						saveAttachMent(mpart, resumeDao, webuserguid, resumeeamilguid);
					} else {
						fileName = mpart.getFileName();
						if ((fileName != null) && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
							fileName = MimeUtility.decodeText(fileName);

							// 下載保存
							saveFile(fileName, mpart.getInputStream(), resumeDao, webuserguid, resumeeamilguid);
						}
					}
				}
			} else if (part.isMimeType("message/rfc822")) {
				saveAttachMent((Part) part.getContent(), resumeDao, webuserguid, resumeeamilguid);
			}
		} catch (Exception e) {
			System.out.println("保存附件有異常！");
		}
	}

	/**
	 * 【真正的保存附件到指定目录里】
	 */
	private void saveFile(String fileName, InputStream in, ResumeDao resumeDao, String webuserguid, String resumeeamilguid) throws Exception {
		String savePath = "upload/emailfile";

		String path = SpringContextHolder.getApplicationContext().getResource("/").getFile().getAbsolutePath() + "/" + savePath;
		if (fileName.length() <= 0 || fileName.lastIndexOf(".") <= 0) {
			return;
		}

		// 后辍名
		String hz = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
		String name = System.currentTimeMillis() + hz;
		// System.out.println("fileName============" + fileName);
		// System.out.println("path============" + path);
		// System.out.println("fileName============" + name);
		// =============================
		// 目录
		File storedir = new File(path);
		if (!storedir.isDirectory())
			storedir.mkdir();

		// 再一级目录
		String tdir = "/" + this.getFrom() + "/";
		savePath += tdir;
		path += tdir;

		// 目录
		storedir = new File(path);
		if (!storedir.isDirectory())
			storedir.mkdir();

		// =============================
		// System.out.println("path============" + path);
		// System.out.println("fileName============" + name);
		// System.out.println("pathfileName============" + path + name);

		// 写文件
		FileOutputStream writer = new FileOutputStream(new File(path + name));
		byte[] content = new byte[1024];
		while ((in.read(content)) != -1) {
			writer.write(content);
		}
		writer.close();
		in.close();

		// 设置路径
		this.setSaveAttachFile(fileName);
		this.setSaveAttachPath(savePath + name);

		// 保存到数据库(简历邮箱附件)
		ResumeEamilFile model = new ResumeEamilFile();
		model.setFilename(this.getSaveAttachFile());
		model.setFilepath(this.getSaveAttachPath());
		model.setResumeeamilguid(resumeeamilguid);
		model.setResumeeamilfileguid(UUIDGenerator.randomUUID());
		model.setModtime(new Timestamp(System.currentTimeMillis()));
		resumeDao.insertResumeEamilFile(model);

		// 保存简历附件
		ResumeFile resumeFile = new ResumeFile();
		resumeFile.setResumefileguid(UUIDGenerator.randomUUID());
		resumeFile.setWebuserguid(webuserguid);
		resumeFile.setResumefilename(this.getSaveAttachFile());
		resumeFile.setResumefilepath(this.getSaveAttachPath());
		resumeFile.setModtime(new Timestamp(System.currentTimeMillis()));
		resumeDao.insertResumeFile(resumeFile);

		// 如果附近有图片把图片放回简历中去
		if (hz.equals(".jpg") || hz.equals(".gif") || hz.equals(".png") || hz.equals(".bmp")) {
			Resume resume = resumeDao.getResumeById(webuserguid);
			if (resume != null) {
				resume.setPhoto(this.getSaveAttachPath());
				resumeDao.updateResume(resume);
			}
		}

	}

	public String getSaveAttachPath() {
		return saveAttachPath;
	}

	public void setSaveAttachPath(String saveAttachPath) {
		this.saveAttachPath = saveAttachPath;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSaveAttachFile() {
		return saveAttachFile;
	}

	public void setSaveAttachFile(String saveAttachFile) {
		this.saveAttachFile = saveAttachFile;
	}

	/**
	 * 取数据
	 * 
	 * @param webUserDao
	 * @param resumeDao
	 * @param userguid
	 * 
	 * @return
	 * @throws Exception
	 */
	public void refreshResumeEamil(NameConvertCodeService nameConvertCodeService, SqlSession sqlSession) throws Exception {
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);

		// 获取默认会话
		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props, null);
		// 使用POP3会话机制，连接服务器
		Store store = session.getStore("pop3");
		store.connect(host, username, password);

		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		Message message[] = folder.getMessages();
		// System.out.println("Messages's length: " + message.length);

		MailReceiveService pmm = null;
		org.jsoup.nodes.Document doc = null;
		String bodytext = null;
		for (int i = 0; i < message.length; i++) {
			MimeMessage msg = (MimeMessage) message[i];
			if (!msg.getFolder().isOpen())
				msg.getFolder().open(Folder.READ_WRITE);
			// System.out.println("msg.getMessageNumber()========" +
			// msg.getMessageNumber());
			// 检查邮件是否已经存在
			ResumeEamil model = resumeDao.getResumeEamilByInterFaceCode(msg.getMessageID());
			if (model != null) {
				continue;
			}

			// 构造邮件对象
			pmm = new MailReceiveService(msg);
			if (StringUtils.isEmpty(pmm.getFrom()) || StringUtils.isEmpty(pmm.getPersonal()) || StringUtils.isEmpty(pmm.getSubject())) {
				continue;
			}

			// 获得邮件内容===============
			pmm.getMailContent((Part) message[i], true);
			bodytext = pmm.getBodyText();
			if (bodytext.length() > 50000) {
				continue;
			}
			bodytext = bodytext.replaceAll("&nbsp;", "");
			
			String resumeemaliguid=UUIDGenerator.randomUUID();

			// 自动投递简历
			WebUser user = null;
			if (pmm.getPersonal().startsWith("前程无忧")) {
				ReadEmail51JobFileUtils readEmail51JobFileUtil = new ReadEmail51JobFileUtils();

				doc = Jsoup.parse(bodytext, "UTf-8");
				Elements table = doc.select("body>table");
				if (table.select("table[class=table_set]").size() > 0) {
					user = readEmail51JobFileUtil.save(nameConvertCodeService, sqlSession, doc, resumeemaliguid);
				} else if (table.select("table[class=v_table02]").size() > 0) {
					user = readEmail51JobFileUtil.saveAnalysisResumeModel_3(nameConvertCodeService, sqlSession, doc, resumeemaliguid);
				} else if (table.select("table[class=table_set]").size() <= 0 && table.select("table[class=v_table02]").size() <= 0) {
					user = readEmail51JobFileUtil.saveAnalysisResumeModel_2(nameConvertCodeService, sqlSession, doc, resumeemaliguid);
				}

			} else if (pmm.getPersonal().startsWith("智联求职者")) {
				ReadEmailZlZPFileUtils readEmailZlZPFileUtils = new ReadEmailZlZPFileUtils();
				user = readEmailZlZPFileUtils.save(nameConvertCodeService, sqlSession, bodytext, pmm.getSubject(), resumeemaliguid);
			} else {
				// 投递个人邮箱
				ReadEmailPersonFileUtils readEmailPersonFileUtils = new ReadEmailPersonFileUtils();
				user = readEmailPersonFileUtils.save(sqlSession, pmm.getSubject(), pmm.getFrom(), resumeemaliguid, pmm.getPersonal());
			}

			// 外網用戶必須存在
			if (user == null)
				continue;

			// 郵件信息
			ResumeEamil resumeEamil = new ResumeEamil();
			resumeEamil.setInterfacecode(msg.getMessageID());
			resumeEamil.setResumeeamilguid(resumeemaliguid);
			resumeEamil.setReadtype(Constance.readtype_N);
			resumeEamil.setWebuserguid(user.getWebuserguid());
			String personal=pmm.getPersonal();
			if(personal.length()>50&&StringUtils.isNotEmpty(personal)){
				personal=personal.substring(0,50);
			}	
			resumeEamil.setPersonal(personal);
			resumeEamil.setEmail(pmm.getFrom());
			resumeEamil.setSubject(pmm.getSubject());
			if(StringUtils.isNotEmpty(bodytext)){
				resumeEamil.setContent(bodytext);
			}else{
				resumeEamil.setContent("无");
			}
			resumeEamil.setModtime(new Timestamp(System.currentTimeMillis()));
			resumeDao.insertResumeEamil(resumeEamil);

			// 获取简历邮箱guid
			String resumeeamilguid = resumeEamil.getResumeeamilguid();
			String webuserguid = user.getWebuserguid();
			// 保存附件
			pmm.saveAttachMent((Part) message[i], resumeDao, webuserguid, resumeeamilguid);

		}
	}

}
