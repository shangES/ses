package com.mk;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.grid.util.DateUtil;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.NameConvertCodeService;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.recruitment.dao.RecruitmentDao;
import com.mk.recruitment.dao.WebUserDao;
import com.mk.recruitment.entity.RecruitPost;
import com.mk.recruitment.entity.WebUser;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.EducationExperience;
import com.mk.resume.entity.ProjectExperience;
import com.mk.resume.entity.Resume;
import com.mk.resume.entity.TrainingExperience;
import com.mk.resume.entity.WorkExperience;

public class ReadEmailZlZPFileUtils {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		File file = new File("C:/Users/lgl/Desktop/mail.html");
		System.out.println(getDocument(file));
	}

	private static String getDocument(File html) throws Exception {
		String text = "";
		// 设置编码集
		org.jsoup.nodes.Document doc = Jsoup.parse(html, "GBK");

		// 简历信息resume
		Elements links = doc.select("body>table");
		// System.out.println(links.size());

		if (links.size() < 2) {
			System.out.println("模板不对请检查!");
			return null;
		}

		Resume resume = new Resume();
		// 开始解析
		if (!links.isEmpty()) {
			Elements users = links.select("table[bgcolor=#ffffff][border=0][cellpadding=0][cellspacing=0][width=600]");

			// System.out.println("==========用户基本信息=============");
			// System.out.println("==================================");

			// 外網用戶
			WebUser user = null;

			// 用户基本信息
			Element user1 = users.get(0);

			// 姓名
			Elements strong = user1.select("td[nowrap=nowrap][valign=top][width=1%]");
			String name = strong.get(0).text();
			// System.out.println("name======" + name);

			// 性别,出生日期,婚姻状况
			Elements sexday = user1.select("td[align=right][valign=top]");
			String[] array = sexday.text().split("\\|");

			if (array.length > 0) {
				// 性别
				String sexname = array[0];
				// System.out.println("sexname==" + sexname);

				// 婚姻状况或出生日期
				String marriedname = array[1];
				marriedname = marriedname.replaceAll(" ", "");
				if (marriedname.equals("未婚") || marriedname.equals("已婚") || marriedname.equals("离异") || marriedname.equals("保密")) {
					// System.out.println("婚姻状况========" + marriedname);

					// 出生日期
					String birthday = array[2];
					birthday = birthday.replaceAll(" ", "");
					birthday = birthday.substring(0, birthday.length() - 1);
					birthday += "01日";
					// System.out.println("birthday:" +
					// DateUtil.parseChina(birthday));

					// 现居住地址,手机 email
					String homeplacephone = array[4];
					String[] homeplacephonelist = homeplacephone.split(" ");
					// System.out.println(homeplacephonelist.length);

					// 现居住地
					String homeplace = homeplacephonelist[1];
					homeplace = homeplace.substring(4, homeplace.length());
					// System.out.println("居住地：" + homeplace);
					if (homeplacephonelist.length == 5) {
						if (array.length == 6) {
							String phone = homeplacephonelist[4];
							phone = phone.substring(0, phone.length() - 4);
							// System.out.println("手机号码：" + phone);

							String emails = array[5];
							String[] emaillist = emails.split(" ");
							// System.out.println("邮箱:" + emaillist[3]);
						} else {
							String phone = homeplacephonelist[2];
							phone = phone.substring(0, phone.length() - 4);
							// System.out.println("手机号码：" + phone);

							String emails = homeplacephonelist[4];
							// System.out.println("邮箱：" + emails);
						}
					} else if (homeplacephonelist.length == 6) {
						String phone = homeplacephonelist[3];
						phone = phone.substring(0, phone.length() - 4);
						// System.out.println("手机号码：" + phone);

						String emails = homeplacephonelist[5];
						// System.out.println("邮箱：" + emails);
					}

				} else {
					// 出生日期
					String birthday = marriedname.substring(0, marriedname.length() - 1);
					birthday += "01日";
					// System.out.println("birthday====" +
					// DateUtil.parseChina(birthday));

					// 现居住地址,手机 email
					String homeplacephone = array[3];
					String[] homeplacephonelist = homeplacephone.split(" ");
					// System.out.println(homeplacephonelist.length);

					// 现居住地
					String homeplace = homeplacephonelist[1];
					if (!homeplace.isEmpty() && homeplace.length() > 4) {
						homeplace = homeplace.substring(4, homeplace.length());
						// System.out.println("居住地：" + homeplace);
					}

					if (homeplacephonelist.length == 5) {
						if (array.length == 6) {
							String phone = homeplacephonelist[4];
							phone = phone.substring(0, phone.length() - 4);
							// System.out.println("手机号码：" + phone);

							String emails = array[5];
							String[] emaillist = emails.split(" ");
							// System.out.println("邮箱:" + emaillist[3]);
						} else {
							String phone = homeplacephonelist[2];
							phone = phone.substring(0, phone.length() - 4);
							// System.out.println("手机号码：" + phone);

							String emails = homeplacephonelist[4];
							// System.out.println("邮箱：" + emails);
						}
					} else if (homeplacephonelist.length == 6) {
						String phone = homeplacephonelist[3];
						phone = phone.substring(0, phone.length() - 4);
						// System.out.println("手机号码：" + phone);

						String emails = homeplacephonelist[5];
						// System.out.println("邮箱：" + emails);
					}

				}

			}
			Elements workdates = links.select("td[nowrap=nowrap][valign=top][width=1%]");
			Elements works = links.select("td[align=left][class=line150]");
			List<WorkExperience> worklist = new ArrayList<WorkExperience>();
			if (!workdates.isEmpty() && !works.isEmpty()) {
				for (int i = 1; i < workdates.size(); i++) {
					String workdate = workdates.get(i).text();
					if (!workdate.isEmpty() && workdate.length() > 8) {
						String worknames = works.get(i - 1).text();
						String[] arraydate = workdate.split("\\--");
						String[] workArray = worknames.split("\\|");
						// System.out.println("工作次数：" + workArray.length);
						WorkExperience workExperience = new WorkExperience();
						if (arraydate.length > 0) {
							String startdate = arraydate[0];
							startdate += "/01";
							startdate = startdate.replaceAll(" ", "");
							String enddate = arraydate[1];
							enddate = enddate.substring(0, enddate.length() - 1);
							enddate += "/01";
							enddate = enddate.replaceAll(" ", "");
							// 工作单位
							String workunit = workArray[0];
							// System.out.println("最近工作单位：" + workunit);
							workExperience.setWorkunit(workunit);
							if (workArray.length > 0 && workArray.length == 6) {
								String posationhtml = workArray[2];
								String posation = posationhtml.replace(" ", "");
								// System.out.println("职位：" + posation);
								workExperience.setPosation(posation);

								String jobdescription = workArray[5];
								if (StringUtils.isEmpty(jobdescription)) {
									jobdescription = "无";
								} else {
									jobdescription = jobdescription.replace(" ", "");
								}

								workExperience.setJobdescription(jobdescription);

								// System.out.println("职责描述：" + jobdescription);
							} else if (workArray.length == 5) {
								String posations = workArray[2];
								String posation = posations.replace(" ", "");
								// System.out.println("职位：" + posation);
								workExperience.setPosation(posation);

								String jobdescription = workArray[4];
								if (StringUtils.isEmpty(jobdescription)) {
									jobdescription = "无";
								} else {
									jobdescription = jobdescription.replace(" ", "");
								}

								workExperience.setJobdescription(jobdescription);

								// System.out.println("职责描述：" + jobdescription);
							} else if (workArray.length == 4) {
								String posations = workArray[1];
								String posation = posations.replace(" ", "");
								// System.out.println("职位：" + posation);
								workExperience.setPosation(posation);

								String jobdescription = workArray[3];
								if (StringUtils.isEmpty(jobdescription)) {
									jobdescription = "无";
								} else {
									jobdescription = jobdescription.replace(" ", "");
								}

								workExperience.setJobdescription(jobdescription);

								// System.out.println("职责描述：" + jobdescription);
							}

							workExperience.setStartdate(DateUtil.parseEnglish(startdate));
							workExperience.setEnddate(DateUtil.parseEnglish(enddate));
							workExperience.setWorkexperienceguid(UUIDGenerator.randomUUID());
							workExperience.setModtime(new Timestamp(System.currentTimeMillis()));
							workExperience.setWebuserguid(resume.getWebuserguid());
							// System.out.println("起始时间" + startdate);
							// System.out.println("结束时间" + enddate);

							worklist.add(workExperience);
						}

					}
				}
			}

			for (WorkExperience workExperience : worklist) {
				// System.out.println(workExperience.toString());
			}

			// System.out.println("==================================");
			// System.out.println("==========教育经历 =============");
			// System.out.println("==================================");
			Elements educations = links.select("table[border=0][cellspacing=0][width=580]");

			// 数据
			List<EducationExperience> list = new ArrayList<EducationExperience>();
			for (int i = 0; i < educations.size(); i++) {
				String manyname = educations.get(i).text();
				manyname = manyname.replaceAll("  ", "");
				// System.out.println("manyname" + manyname);
				// System.out.println("次数" + manyname.length());
				if (manyname.equals("  教育经历")) {
					Element educationhtml = educations.get(i + 1);
					// 時間
					Elements dates = educationhtml.select("td");
					Elements len = educationhtml.select("br");
						String tmpworkunit = dates.text();
						System.out.println("tmpworkunit" + tmpworkunit);
						String[] ls = tmpworkunit.split("\\|");
						if (ls.length > 0) {
							String[] ls1 = ls[0].split("：");
							String[] ls2 = ls1[0].split("--");
							String startdate = ls2[0] + "/01";
							String enddate = ls2[1] + "/01";
							// System.out.println("=====" +
							// startdate.replace(" ", "") + "=====" +
							// enddate.replace(" ", ""));

							// 实例化
							EducationExperience model = new EducationExperience();
							model.setEducationexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());
							model.setStartdate(DateUtil.parseEnglish(startdate.replaceAll(" ", "")));
							model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							model.setMajordescription("无");

							// 学院
							model.setSchool(ls1[1].replace(" ", ""));
							// System.out.println("学校：" + ls1[1].replace(" ",
							// ""));

							// 专业
							model.setSpecialty(ls[1].replace(" ", ""));
							// System.out.println("专业：" + ls[1].replace(" ",
							// ""));

							// 学历
							String culturename = ls[2].replace(" ", "");
							String[] al=culturename.split("：");
							if(al.length>1){
								culturename=al[0].substring(0,al[0].indexOf("2"));
							}

							System.out.println("学历：" + culturename);
							model.setCulture(1);
							
							list.add(model);
						}

					for (EducationExperience edu : list) {
						// System.out.println(edu.toString());
					}

				} else if (manyname.equals("  项目经验")) {
					List<ProjectExperience> projectlist = new ArrayList<ProjectExperience>();
					// System.out.println("项目经验：" + educations.get(i + 1));
					Element pojecthtml = educations.get(i + 1);
					String dochtm = pojecthtml.toString();
					dochtm = dochtm.replaceAll(" ", "");
					org.jsoup.nodes.Document jy = Jsoup.parse(dochtm, "GBK");
					String pojecthtext = jy.text();

					// 得到第一个项目经验的时间 and 名称
					String[] idxname = pojecthtext.split(" ");
					// System.out.println("idxname===" + idxname[0]);

					String[] dateandname = idxname[0].split("：");
					// 开始时间 结束时间
					String[] date = dateandname[0].split("--");
					String startdate = date[0] + "/01";
					String enddate = date[1] + "/01";

					String pojectname = dateandname[1];
					// System.out.println("pojectname==" + pojectname);
					Elements divpojecthtml = pojecthtml.select("div[class=resume_p]");
					// 保存项目经验
					ProjectExperience model = new ProjectExperience();
					String oneJobDescription = divpojecthtml.get(0).text();
					model.setProjectexperienceguid(UUIDGenerator.randomUUID());
					model.setWebuserguid(resume.getWebuserguid());
					model.setStartdate(DateUtil.parseEnglish(startdate.replaceAll(" ", "")));
					model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
					model.setItemname(pojectname);
					model.setJobdescription(oneJobDescription);
					model.setModtime(new Timestamp(System.currentTimeMillis()));
					projectlist.add(model);

					if (divpojecthtml.size() > 2) {

						Elements ppojecthtml = pojecthtml.select("p");

						for (int j = 2; j < divpojecthtml.size(); j += 2) {
							ProjectExperience modelfor = new ProjectExperience();
							modelfor.setProjectexperienceguid(UUIDGenerator.randomUUID());
							modelfor.setWebuserguid(resume.getWebuserguid());
							modelfor.setModtime(new Timestamp(System.currentTimeMillis()));

							String[] dateandnamefor = ppojecthtml.get(j - 1).text().split("：");
							// 开始时间 结束时间
							String[] datefor = dateandnamefor[0].split("--");
							String startdatefor = datefor[0] + "/01";
							String enddatefor = datefor[1] + "/01";
							String pojectnamefor = dateandnamefor[1];
							modelfor.setStartdate(DateUtil.parseEnglish(startdatefor.replaceAll(" ", "")));
							modelfor.setEnddate(DateUtil.parseEnglish(enddatefor.replaceAll(" ", "")));
							modelfor.setItemname(pojectnamefor);
							modelfor.setJobdescription(divpojecthtml.get(j).text());

							projectlist.add(modelfor);
						}
					}

					for (ProjectExperience pro : projectlist) {
						// System.out.println(pro.toString());
					}

					// String pojecthtext=pojecthtml.text();
					// System.out.println("pojecthtml.text==="+pojecthtext);
					//
					// System.out.println("pojecthtml.text==="+pojecthtext.substring(0,
					// pojecthtext.indexOf("：")));

					// System.out.println("项目经历"+educations.get(i+1));
					// String pojecthtml = educations.get(i + 1).text();
					// System.out.println("pojecthtml===" + pojecthtml);
					// // 判断有几条项目经验（-1）
					// String[] nums = pojecthtml.split("\\ -- ");
					// String[] ls = pojecthtml.split("\\：");
					// for(String a:ls){
					// System.out.println("aaaa====="+a);
					// }
					// ProjectExperience model = new ProjectExperience();
					// String[] ls = pojecthtml.split("\\：");
					// for(String a:ls){
					// System.out.println("aaaa====="+a);
					// }
					// if (ls.length > 0) {
					// String[] ls1 = ls[0].split("：");
					// // 时间取出来
					// String datese = ls1[0];
					// String[] datels = datese.split(" -- ");
					// String startdate = datels[0] + "/01";
					// System.out.println("项目开始时间：" + startdate);
					// String enddate = datels[1] + "/01";
					// System.out.println("项目结束时间：" + enddate);
					// model.setStartdate(DateUtil.parseEnglish(startdate));
					// model.setEnddate(DateUtil.parseEnglish(enddate));
					//
					// String itemname = ls[1].substring(0, ls[1].length() - 4);
					// itemname.replaceAll(" ", "");
					// String jobdescription = ls[3].substring(0, ls[3].length()
					// - 4);
					// jobdescription.replaceAll(" ", "");
					// System.out.println("项目名称：" + itemname);
					// System.out.println("职责描述：" + jobdescription);
					//
					// }

				} else if (manyname.equals("  培训经历")) {
					// 数据
					List<TrainingExperience> traininglist = new ArrayList<TrainingExperience>();
					Element trainingshtml = educations.get(i + 1);
					Elements traininghtml = trainingshtml.select("td[class=line150]");
					for (org.jsoup.nodes.Element date : traininghtml) {
						String tmpworkunit = date.text();
						String[] ls = tmpworkunit.split("：");
						if (ls.length > 0) {
							// System.out.println("ls[1]" + ls[1]);
							// 实例化
							TrainingExperience model = new TrainingExperience();
							model.setTrainingexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());

							// 培训机构
							String[] jg = ls[1].split(" ");
							// System.out.println("培训机构=========="+jg[0]);

							// 开始时间
							String datese = ls[0];
							String[] datels = datese.split("--");
							String startdate = datels[0] + "/01";
							// System.out.println("结束时间" + datels[1]);
							String enddate = datels[1] + "/01";
							if ("至今".equals(datels[1])) {
								enddate = null;
							}
							// System.out.println("培训开始时间：" + startdate);
							// System.out.println("培训结束时间：" + enddate);
							model.setStartdate(DateUtil.parseEnglish(startdate.replaceAll(" ", "")));
							model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));

							String[] ls2 = ls[2].split(" ");
							if (ls2[1].equals("所获证书")) {
								String[] ls3 = ls[3].split(" ");
								String certificate = ls3[0].replace(" ", "");
								model.setCertificate(certificate);
								// System.out.println("所获证书:" + certificate);

								model.setTrainingcontent(ls[5].replace(" ", ""));
								// System.out.println("培训内容：" + ls[5]);
							} else {
								model.setCertificate("无");

								model.setTrainingcontent(ls[4].replace(" ", ""));
								// System.out.println("培训内容：" + ls[4]);
							}

							traininglist.add(model);
						}
					}
					// 保存培训经历
					for (TrainingExperience model : traininglist) {
						// System.out.println(model.toString());
					}

				}
			}
		}
		return text;
	}

	/**
	 * 解析邮件保存
	 * 
	 * @param nameConvertCodeService
	 * @param sqlSession
	 * @param htm
	 * @return
	 */
	@Transactional
	public WebUser save(NameConvertCodeService nameConvertCodeService, SqlSession sqlSession, String htm, String subject, String eamilguid) {
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		// 邮件正文
		if (StringUtils.isEmpty(htm))
			return null;
		htm = htm.replaceAll("&nbsp;", "");

		// 设置编码集
		org.jsoup.nodes.Document doc = Jsoup.parse(htm, "UTF-8");
		//System.out.println("===doc==="+doc);

		// 简历信息
		Elements links = doc.select("body>table");
		//System.out.println("***************************************"+links.size());
		if (links.size() < 2) {
			return null;
		}
		
		// 开始解析
		if (!links.isEmpty()) {

			Elements users = links.select("table[bgcolor=#ffffff][border=0][cellpadding=0][cellspacing=0][width=600]");
			//System.out.println("===users==="+users);
			// 外網用戶
			WebUser user = null;

			// 用户基本信息
			Element user1 = users.get(0);
			//System.out.println("===用户基本信息==="+user1);
			// 姓名
			Elements strong = user1.select("td[nowrap=nowrap][valign=top][width=1%]");
			String name = strong.get(0).text();

			String email = null;
			String moiblie = null;
			String birthday = null;
			String sexname = null;
			String homeplace = null;
			String homeplacephone=null;
			String marriedname = null;
			

			// 性别,出生日期,婚姻状况
			Elements sexday = user1.select("td[align=right][valign=top]");
			String[] array = sexday.text().split("\\|");
			
			if(array.length > 0){
				for(String strtemp : array){
					String tempname = strtemp.replaceAll(" ", "");
					if(tempname.equals("男") || tempname.equals("女")){
						//性别
						sexname = tempname;
						continue;
					}else if(tempname.equals("未婚") || tempname.equals("已婚") || tempname.equals("离异") || tempname.equals("保密")){
						marriedname = tempname;
						continue;
					}else if(tempname.endsWith("生")){
						//出生日期
						birthday = tempname.substring(0, tempname.length() - 1);
						birthday += "01日";
						continue;
					}else if(tempname.startsWith("现居住于")){
						//现居地
						homeplace = tempname;
						continue;
					}
				}
				
				String phoneandemail = array[array.length-1];
				int num = phoneandemail.lastIndexOf(":");
				//邮箱
				email = phoneandemail.substring(num+1, phoneandemail.length()).replaceAll(" ", "");
				//手机
				String[] arryphone = phoneandemail.split(" ");
				for(String str : arryphone){
					if(str.endsWith(")")){
						moiblie = str;
					}
				}
			}
			
			if(homeplace.length() > 25){
				String place = array[array.length-1];
				String[] tem = place.split(" ");
				for(String str : tem){
					String strplace = str.replaceAll(" ","");
					if(strplace.startsWith("现居住于")){
						int index = strplace.lastIndexOf("于");
						homeplace = strplace.substring(index+1, strplace.length());
						break;
					}
				}
			}

			/*System.out.println("==email===="+email);
			System.out.println("==moiblie===="+moiblie);
			System.out.println("==birthday===="+birthday);
			System.out.println("==sexname===="+sexname);
			System.out.println("==homeplace===="+homeplace);*/
			
			if(StringUtils.isEmpty(email) || StringUtils.isEmpty(moiblie) || StringUtils.isEmpty(birthday) || StringUtils.isEmpty(sexname) || StringUtils.isEmpty(homeplace))
				System.out.println(doc);
			

			//System.out.println("====智联招聘==="+moiblie);
			//只取数字
			if(moiblie != null){
				String regEx="[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(moiblie);
				moiblie = m.replaceAll("").trim();
			}
			if (StringUtils.isEmpty(email)) {
				return null;
			}
			// 保存到本地
			user = webUserDao.checkWebUserByEmail(null, email);
			if (user == null) {
				// 保存外网用户
				user = new WebUser(email, name);
				user.setWebuserguid(UUIDGenerator.randomUUID());
				
				webUserDao.insertWebUser(user);
			}
			user.setMobile(moiblie);
			// 外網用戶必須存在
			if (user == null)
				return null;
			// 简历信息
			Resume resume = resumeDao.getResumeById(user.getWebuserguid());
			// 如果簡歷存在以最新的為準
			boolean isnew = false;
			if (resume == null) {
				isnew = true;
				resume = new Resume();
				resume.setWebuserguid(user.getWebuserguid());
				resume.setCreatetime(new Timestamp(System.currentTimeMillis()));
			}
			resume.setModtime(new Timestamp(System.currentTimeMillis()));

			// 姓名
			resume.setName(user.getUsername());
			// 电话
			resume.setMobile(user.getMobile());
			// 邮件
			resume.setEmail(user.getEmail());

			// 居住地
			resume.setHomeplace(homeplace);

			// 出生日期
			resume.setBirthday(DateUtil.parseChina(birthday));

			// 性别
			if (!sexname.isEmpty()) {
				Integer sex = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.SEX, sexname);
				if (sex != null) {
					resume.setSexname(sexname);
					resume.setSex(sex);
				} else {
					resume.setSex(Constance.VALID_NO);
				}

			}
			
			//人才库
			//if(eamilguid == null)
				resume.setMark(Constance.VALID_NO);

			// 工作年限
			resume.setWorkage(Constance.VALID_NO);

			// 学历
			resume.setCulture(Constance.VALID_NO);
			Elements tableelement = user1.select("table[width=580][align=center]");
			//System.out.println("====第二个table[width=580][align=center]"+tableelement);
			if(tableelement.size() < 2){
				return null;
			}
			//自我评价和求职意向
			Elements workselement = tableelement.get(1).select("table[width=580][cellpadding=2][bgcolor=#f6f7f8]");
			//System.out.println("====自我评价和求职意向===="+workselement);
			if(workselement.size()<=0){
				return null;
			}
			
			//自我评价
			//System.out.println(workselement.select("tr").get(0).select("span"));
			String tmpvaluation = workselement.select("tr").get(0).select("span").text().replaceAll(" ", "");
			//System.out.println("====自我评价===="+tmpvaluation);
			if(tmpvaluation.equals("自我评价") || tmpvaluation.equals("技能") || tmpvaluation.equals("职业目标") || tmpvaluation.equals("职业心声") || tmpvaluation.equals("技术优势") || tmpvaluation.equals("工作技能")){
				Elements valuationelement = workselement.get(0).parent().select("table[width=580][bgcolor!=#f6f7f8]");
				String valuation = valuationelement.select("div[class=resume_p]").text();
				if(valuation.length() > 500)
					valuation = valuation.substring(0, 500);
				//System.out.println("====自我评价===="+valuation);
				resume.setValuation(valuation);
				//求职意向
				if(valuationelement.select("table[width=100%][cellpadding=2]") != null){
					Elements jobintensionelements = valuationelement.select("table[width=100%][cellpadding=2]").select("td[width=1%][nowrap=nowrap][align=right]");
					if(jobintensionelements.size() > 0){
						for(Element jobintensionelement : jobintensionelements){
							String job = jobintensionelement.text().replaceAll(" ", "");
							if(job.equals("期望从事职业：")){
								String occupation = jobintensionelement.nextElementSibling().text();
								if(occupation.length() > 100)
									occupation = occupation.substring(0, 100);
								//System.out.println("====期望从事职业===="+occupation);
								resume.setOccupation(occupation);
							}
							if(job.equals("期望月薪：")){
								String salary = jobintensionelement.nextElementSibling().text();
								if(salary.length() > 50)
									salary = salary.substring(0, 50);
								//System.out.println("====期望月薪===="+salary);
								resume.setSalary(salary);
							}
							if(job.equals("目前状况：")){
								String situation = jobintensionelement.nextElementSibling().text();
								if(situation.length() > 50)
									situation = situation.substring(0, 50);
								//System.out.println("====目前状况===="+situation);
								resume.setSituation(situation);
							}
						}
					}
				}
			}else{
				if(tableelement.select("table[width=580][cellpadding=0][cellspacing=0][border=0][align!=center][bgcolor!=#f6f7f8]") != null){
					//自定义自我评价标题
					Elements  valuationelement = tableelement.select("table[width=580][cellpadding=0][cellspacing=0][border=0][align!=center][bgcolor!=#f6f7f8]");
					//System.out.println("=========="+valuationelement.size());
					if(valuationelement.size() > 0){
						for(Element element : valuationelement){
							if(element.select("div[class=resume_p]") != null){
								String valuation = element.select("div[class=resume_p]").text();
								if(valuation.length() > 500)
									valuation = valuation.substring(0, 500);
								//System.out.println("====自我评价===="+valuation);
								resume.setValuation(valuation);
								//求职意向
								if(element.select("table[width=100%][cellpadding=2]") != null){
									Elements jobintensionelements = element.select("table[width=100%][cellpadding=2]").select("td[width=1%][nowrap=nowrap][align=right]");
									if(jobintensionelements.size() > 0){
										for(Element jobintensionelement : jobintensionelements){
											String job = jobintensionelement.text().replaceAll(" ", "");
											if(job.equals("期望从事职业：")){
												String occupation = jobintensionelement.nextElementSibling().text();
												if(occupation.length() > 100)
													occupation = occupation.substring(0, 100);
												//System.out.println("====期望从事职业===="+occupation);
												resume.setOccupation(occupation);
											}
											if(job.equals("期望月薪：")){
												String salary = jobintensionelement.nextElementSibling().text();
												if(salary.length() > 50)
													salary = salary.substring(0, 50);
												//System.out.println("====期望月薪===="+salary);
												resume.setSalary(salary);
											}
											if(job.equals("目前状况：")){
												String situation = jobintensionelement.nextElementSibling().text();
												if(situation.length() > 50)
													situation = situation.substring(0, 50);
												//System.out.println("====目前状况===="+situation);
												resume.setSituation(situation);
											}
										}
									}
								}
								//System.out.println("========="+element);
								break;
							}
						}
					}
			}
		}
			//行业
			String industry = "";
			for(Element element : workselement){
				//System.out.println("====值为===="+element.select("span"));
				String typeinfo = element.select("span").text().replaceAll(" ", "");
				if(typeinfo.equals("工作经历")){
					// 工作经历先删除后插入
					//resumeDao.delWorkExperienceByWebuserId(resume.getWebuserguid());
					List<WorkExperience> list = new ArrayList<WorkExperience>();
					Elements dateselement = element.parent().select("td[width=1%][nowrap=nowrap][valign=top]");
					if(dateselement != null && dateselement.size() > 0){
						for(Element ele : dateselement){
							if(ele.nextElementSibling() != null){
								//System.out.println("====内容===="+ele.nextElementSibling().text());
								String[] temp = ele.nextElementSibling().text().split(" \\| ");
								if(temp.length > 1){
									for(int i = 1; i < temp.length; i++){
										String[] workunits = temp[i].split(" ");
										if(workunits.length > 1){
											/* 判别工作职位和工作描述
											 *如： 2008/07 -- 2008/09： 杭州巨星精密有限公司 | 实习生 加工制造（原料加工/模具） | 民营 | 1000-2000元/月 该公司为出口型制造企业，实习期间主要在各基层岗位学习实践，锻炼了团队合作能力 
											 * */
											//工作描述
											if(workunits[0].split("-").length <= 1 || !workunits[0].split(":")[0].equals("规模") || workunits[1].length() <= 25){
												industry = workunits[1];
												if(industry.length() > 50)
													industry = industry.substring(0, 50);
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			//学历
			Integer cultureresume = null;
			for(Element element : workselement){
				//System.out.println("====值为===="+element.select("span"));
				String typeinfo = element.select("span").text().replaceAll(" ", "");
				if(typeinfo.equals("教育经历")){
					Elements educationctelement = element.parent().select("table[width=580][bgcolor!=#f6f7f8]");
					//System.out.println("====教育经历===="+educationctelement);
					List<EducationExperience> list = new ArrayList<EducationExperience>();
					if(educationctelement.select("td") != null && educationctelement.select("td") .size() > 0){
						Elements tdelements = educationctelement.select("td");
						if(tdelements.size() > 0){
							for(Element projectele : tdelements){

								//System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+projectele.text());
								/*
								 * 教育经历例子，在一个td里循环教育经历
								 * <td>2008/08 -- 2010/12：Ball State University(美国鲍尔州立大学) | 计算机科学与技术 | 本科<br /><br />2007/09 -- 2011/06：成都信息工程学院 | 计算机科学与技术 | 本科</td>
								 * 
								 * */
								String[] allinfo = projectele.text().split("：");
								if(allinfo.length>0){
									for(int k = 0; k < allinfo.length-1;k++){
										//学历
										String culturename = "";
										//学历
										if(allinfo[k+1].length() > 0){
											String[] others = allinfo[k+1].split("\\|");
											if(others.length > 2){
												culturename = others[2];
												if(culturename.split("--").length > 1){
													String[] tempculturename =culturename.split("--");
													if(tempculturename[0].replaceAll(" ", "").length() > 7)
													culturename = tempculturename[0].replaceAll(" ", "").substring(0, tempculturename[0].replaceAll(" ", "").length()-7);
												}
												if(culturename.length() > 50)
													culturename = culturename.substring(0, 50);
												cultureresume = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturename.replaceAll(" ", ""));
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			if (cultureresume == null)
				cultureresume = Constance.VALID_NO;
			resume.setCulture(cultureresume);
			
			if(StringUtils.isEmpty(industry))
				industry = "无";
			resume.setIndustry(industry);
			//System.out.println("=====resume====="+resume.toString());
			if (isnew)
				resumeDao.insertResume(resume);
			else
				resumeDao.updateResume(resume);
			
			for(Element element : workselement){
				//System.out.println("====值为===="+element.select("span"));
				String typeinfo = element.select("span").text().replaceAll(" ", "");
				if(typeinfo.equals("工作经历")){
					// 工作经历先删除后插入
					resumeDao.delWorkExperienceByWebuserId(resume.getWebuserguid());
					List<WorkExperience> list = new ArrayList<WorkExperience>();
					Elements dateselement = element.parent().select("td[width=1%][nowrap=nowrap][valign=top]");
					if(dateselement != null && dateselement.size() > 0){
						for(Element ele : dateselement){
							WorkExperience model = new WorkExperience();
							String[] dates = ele.text().split("--");
							//开始时间
							String startdate = "";
							//结束时间
							String enddate = "";
							//工作单位
							String workunit = "";
							//职位
							String position = "";
							//职责描述
							String jobdescription = "";
							
							if(dates.length > 0){
								startdate = dates[0]+"/01";
								enddate = dates[1]+"/01";
							}
							if(ele.nextElementSibling() != null){
								//System.out.println("====内容===="+ele.nextElementSibling().text());
								String[] temp = ele.nextElementSibling().text().split(" \\| ");
								if(temp.length > 0){
									//工作单位
									workunit = temp[0];
									if(workunit.length() > 50)
										workunit = workunit.substring(0, 50);
								}
								if(temp.length > 1){
									for(int i = 1; i < temp.length; i++){
										String[] workunits = temp[i].split(" ");
										if(workunits.length > 1){
											/* 判别工作职位和工作描述
											 *如： 2008/07 -- 2008/09： 杭州巨星精密有限公司 | 实习生 加工制造（原料加工/模具） | 民营 | 1000-2000元/月 该公司为出口型制造企业，实习期间主要在各基层岗位学习实践，锻炼了团队合作能力 
											 * */
											//工作描述
											if(workunits[0].split("-").length > 1 || workunits[0].split(":")[0].equals("规模") || workunits[1].length() > 25){
												for(int k = 1 ; k < workunits.length; k++){
													jobdescription = jobdescription+workunits[k];
												}
												if(jobdescription.length() > 500)
													jobdescription = jobdescription.substring(0, 500);
											}else{
												position = workunits[0];
												if(position.length() > 50)
													position = position.substring(0, 50);
												
											}
										}
									}
								}
							}
							model.setWorkexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());
							model.setStartdate(DateUtil.parseEnglish(startdate.replaceAll(" ", "").replaceAll("：", "")));
							if (model.getStartdate() == null)
								model.setStartdate(new Date(System.currentTimeMillis()));
							model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "").replaceAll("：", "")));
							if(StringUtils.isEmpty(workunit))
								workunit = "无";
							if(StringUtils.isEmpty(position))
								position = "无";
							if(StringUtils.isEmpty(jobdescription))
								jobdescription = "无";
							model.setPosation(position);
							model.setWorkunit(workunit);
							model.setJobdescription(jobdescription);
							list.add(model);
						}
						for(WorkExperience model : list){
							//System.out.println("===工作经历==="+model.toString());
							resumeDao.insertWorkExperience(model);
						}
					}
				}else if(typeinfo.equals("项目经验")){
					// 项目经验先删除后插入
					resumeDao.delProjectExperienceByWebuserId(resume.getWebuserguid());
					Elements projecelement = element.parent().select("table[width=580][bgcolor!=#f6f7f8]");
					List<ProjectExperience> list = new ArrayList<ProjectExperience>();
					//System.out.println("====项目经验===="+projecelement.select("td").text());
					String[] arrproject = projecelement.select("td").text().split(" -- ");
					//System.out.println("====个数为===="+arrproject.length);
					if(arrproject.length > 0){
						for(int i = 1; i < arrproject.length;i++){
							ProjectExperience model = new ProjectExperience();
							//开始时间
							String startdate = "";
							//结束时间
							String enddate = "";
							//项目名称
							String workunit = "";
							//工作描述
							String jobdescription = "";
							String[] tempinfos = arrproject[i].split("：");
							//开始时间
							if(i==1){
								startdate = arrproject[0] + "/01";
								//System.out.println("===开始时间1111==="+startdate);
							}else{
								//如果是最后一个项目
								if(i == arrproject.length-1){
									String[] tempdate = arrproject[arrproject.length-2].split("：");
									startdate = tempdate[tempdate.length-1].replaceAll(" ", "").substring(tempdate[tempdate.length-1].replaceAll(" ", "").length()-7, tempdate[tempdate.length-1].replaceAll(" ", "").length()) + "/01";
									//System.out.println("===最后一个项目开始时间2222==="+startdate);
								}else{
									//不是最后一个项目，取arrproject[i-1].split("：")的最后一个数组的最后7个字符为时间
									if(tempinfos.length > 0){
											String[] tempdate = arrproject[i-1].split("：");
											//System.out.println("====第"+i+"个的最后一个值==="+tempdate[tempdate.length-1].replaceAll(" ", ""));
											startdate = tempdate[tempdate.length-1].replaceAll(" ", "").substring(tempdate[tempdate.length-1].replaceAll(" ", "").length()-7, tempdate[tempdate.length-1].replaceAll(" ", "").length()) + "/01";
											//System.out.println("===不是最后一个项目开始时间2222==="+startdate);
									}
								}
							}
							if(tempinfos.length > 0){
							//结束时间，工作单位，工作描述
							for(int num = 1; num < tempinfos.length; num++){
								//结束时间
								enddate = tempinfos[0] + "/01";
								//System.out.println("====结束时间===="+enddate + "/01");
								//项目名称
								if(tempinfos.length > 1){
								String[] workunits = tempinfos[1].split(" ");
								if(workunits.length > 0){
									if(tempinfos[1].replaceAll(" ", "").length() > 4)
										workunit = tempinfos[1].substring(0, tempinfos[1].replaceAll(" ", "").length()-4);
									else
										workunit = workunits[0];
								}
								if(workunit.length() > 50)
									workunit = workunit.substring(0, 50);
								}
								//System.out.println("====工作单位===="+workunit);
								//工作描述
								if(tempinfos[num].split(" ").length > 1){
									String[] temp = tempinfos[num].split(" ");
									if(temp[temp.length-1].replaceAll(" ","").length()==4 && temp[temp.length-1].replaceAll(" ","").equals("责任描述")){
										if(num+1 < tempinfos.length){
											String[] job = tempinfos[num+1].split(" ");
											if(job.length > 0)
												jobdescription = job[0];
											if(jobdescription.length() > 500)
												jobdescription = jobdescription.substring(0, 500);
										}
									}
								}
								//System.out.println("===工作描述===="+jobdescription);
							}
							}
							model.setStartdate(DateUtil.parseEnglish(startdate.replaceAll(" ", "")));
							if (model.getStartdate() == null)
								model.setStartdate(new Date(System.currentTimeMillis()));
							model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							model.setProjectexperienceguid(UUIDGenerator.randomUUID());
							model.setWebuserguid(resume.getWebuserguid());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							if(StringUtils.isEmpty(jobdescription))
								jobdescription = "无";
							model.setJobdescription(jobdescription);
							model.setItemname(workunit);
							list.add(model);
						}
						for (ProjectExperience model : list) {
							//System.out.println("===项目经验==="+model.toString());
							resumeDao.insertProjectExperience(model);
						}
					}
					
				}else if(typeinfo.equals("教育经历")){
					// 教育经验先删除后插入
					resumeDao.delEducationExperienceByWebuserId(resume.getWebuserguid());
					Elements educationctelement = element.parent().select("table[width=580][bgcolor!=#f6f7f8]");
					//System.out.println("====教育经历===="+educationctelement);
					List<EducationExperience> list = new ArrayList<EducationExperience>();
					if(educationctelement.select("td") != null && educationctelement.select("td") .size() > 0){
						Elements tdelements = educationctelement.select("td");
						if(tdelements.size() > 0){
							for(Element projectele : tdelements){
								//System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"+projectele.text());
								/*
								 * 教育经历例子，在一个td里循环教育经历
								 * <td>2008/08 -- 2010/12：Ball State University(美国鲍尔州立大学) | 计算机科学与技术 | 本科<br /><br />2007/09 -- 2011/06：成都信息工程学院 | 计算机科学与技术 | 本科</td>
								 * 
								 * */
								String[] allinfo = projectele.text().split("：");
								if(allinfo.length>0){
									for(int k = 0; k < allinfo.length-1;k++){
										EducationExperience model = new EducationExperience();
										//开始时间
										String startdate = "";
										//结束时间
										String enddate = "";
										//学校
										String school = "";
										// 专业
										String specialty = "无";
										//学历
										String culturename = "";
										// 专业描述
										String description = "";
										
										Integer culture = null;
										String[] dates = allinfo[k].split("--");
										//时间
										if(dates.length > 0){
											//截取字符串末尾值
											int length = dates[0].replaceAll(" ", "").length();
											if(length > 7)
											startdate = dates[0].replaceAll(" ", "").substring(dates[0].replaceAll(" ", "").length()-7, dates[0].replaceAll(" ", "").length()) + "/01";
											else
												startdate = dates[0].replaceAll(" ", "") + "/01";
											//System.out.println("====开始时间===="+startdate);
										}if(dates.length > 1){
											enddate = dates[1].replaceAll(" ", "") + "/01";
											//System.out.println("====结束时间===="+enddate);
										}
										//学校
										if(allinfo[k+1].length() > 0){
											String[] others = allinfo[k+1].split("\\|");
											if(others.length > 0){
												school = others[0];
												if(school.length() > 50)
													school = school.substring(0, 50);
												//System.out.println("====学校===="+school);
											}
											// 专业
											if(others.length > 1){
												specialty = others[1];
												if(specialty.split("--").length > 1){
													String[] tempspecialty =specialty.split("--");
													if( tempspecialty[0].replaceAll(" ", "").length() > 7)
														specialty = tempspecialty[0].replaceAll(" ", "").substring(0, tempspecialty[0].replaceAll(" ", "").length()-7);
												}
												if(specialty.length() > 50)
													specialty = specialty.substring(0, 50);
												//System.out.println("====专业===="+specialty);
											}
											//学历
											if(others.length > 2){
												culturename = others[2];
												if(culturename.split("--").length > 1){
													String[] tempculturename =culturename.split("--");
													if(tempculturename[0].replaceAll(" ", "").length() > 7)
													culturename = tempculturename[0].replaceAll(" ", "").substring(0, tempculturename[0].replaceAll(" ", "").length()-7);
												}
												if(culturename.length() > 50)
													culturename = culturename.substring(0, 50);
												culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturename.replaceAll(" ", ""));
												if (culture == null)
													culture = Constance.VALID_NO;
											}
											// 专业描述
											if(others.length > 4){
												description = others[3];
												if(description.split("--").length > 1){
													String[] tempdescription =description.split("--");
													if( tempdescription[0].replaceAll(" ", "").length() > 7)
														description = tempdescription[0].replaceAll(" ", "").substring(0, tempdescription[0].replaceAll(" ", "").length()-7);
												}
												if(description.length() > 500)
													description = description.substring(0, 500);
												//System.out.println("====专业描述===="+specialty);
											}
										}
										model.setStartdate(DateUtil.parseEnglish(startdate));
										if (model.getStartdate() == null)
											model.setStartdate(new Date(System.currentTimeMillis()));
										model.setEnddate(DateUtil.parseEnglish(enddate));
										model.setEducationexperienceguid(UUIDGenerator.randomUUID());
										model.setWebuserguid(resume.getWebuserguid());
										model.setModtime(new Timestamp(System.currentTimeMillis()));
										model.setSpecialty(specialty);
										if (culture == null)
											culture = Constance.VALID_NO;
										if (culture == null)
											culture = Constance.VALID_NO;
										model.setCulture(culture);
										if(StringUtils.isEmpty(description))
											description = "无";
										model.setMajordescription(description);
										model.setSchool(school);
										list.add(model);
									}
									for (EducationExperience model : list) {
										//System.out.println("===教育经历==="+model.toString());
										resumeDao.insertEducationExperience(model);
									}
								}
							}
						}
					}
				}else if(typeinfo.equals("培训经历")){
					// 教育经验先删除后插入
					resumeDao.delTrainingExperienceByWebuserId(resume.getWebuserguid());
					List<TrainingExperience> list = new ArrayList<TrainingExperience>();
					Elements trainelement = element.parent().select("table[width=580]");
					//System.out.println("====培训经历===="+trainelement);
					if(trainelement.select("td[class=line150]").size() > 0){
						Elements tdelements = trainelement.select("td[class=line150]");
						for(Element ele : tdelements){
							TrainingExperience model = new TrainingExperience();
							String[] datas = ele.text().split("：");
							if(datas.length > 0){
								//开始时间
								String startdate = "";
								//结束时间
								String enddate = "";
								//培训机构
								String trainunit = "";
								//培训课程
								//String curriculum = "";
								//培训描述
								String traindescription = "";
								//证书
								String certificate = "";
								for(int i = 1; i < datas.length; i++){
									String[] dates = datas[0].split("--");
									if(dates.length > 0){
										startdate = dates[0]+"/01";
										enddate = dates[1]+"/01";
										//System.out.println("====开始时间===="+startdate);
										//System.out.println("====结束时间===="+enddate);
									}
									//培训机构
									if(dates.length > 1){
										String[] temptrainunit = datas[1].split(" ");
										if(temptrainunit.length > 0){
											trainunit = temptrainunit[0];
											if(temptrainunit.length > 50)
												trainunit = trainunit.substring(0, 50);
											//System.out.println("====培训机构===="+trainunit);
										}
									}
									//System.out.println("===第"+i+"个值为===="+datas[i]);
									if(datas[i].split(" ").length > 1){
										String[] tempcertificate = datas[i].split(" ");
										if(tempcertificate[1].length() >= 4){
											String temp = tempcertificate[1].replaceAll(" ", "");
											//所获取的信息为下一个数组的第一个值
											if(temp.equals("所获证书")){
												if(i < datas.length-1){
													String[] arrcertificate = datas[i+1].split(" ");
													if(arrcertificate.length > 0){
														certificate = arrcertificate[0];
														if(certificate.length() > 50)
															certificate = certificate.substring(0, 50);
														//System.out.println("====证书===="+certificate);
													}
												}
											}else if(temp.equals("培训描述")){
												if(i < datas.length-1){
													String[] arrtraindescription = datas[i+1].split(" ");
													if(arrtraindescription.length > 0){
														traindescription = arrtraindescription[0];
														if(traindescription.length() > 500)
															traindescription = traindescription.substring(0, 500);
														//System.out.println("====培训描述===="+traindescription);
													}
												}
											}
										}
									}
									model.setStartdate(DateUtil.parseEnglish(startdate.replaceAll(" ", "")));
									if (model.getStartdate() == null)
										model.setStartdate(new Date(System.currentTimeMillis()));
									model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
									model.setTrainingexperienceguid(UUIDGenerator.randomUUID());
									model.setWebuserguid(resume.getWebuserguid());
									model.setModtime(new Timestamp(System.currentTimeMillis()));
									if(StringUtils.isEmpty(certificate))
										certificate = "无";
									model.setCertificate(certificate);
									if(StringUtils.isEmpty(traindescription))
										traindescription = "无";
									model.setTrainingcontent(traindescription);
									if(StringUtils.isEmpty(trainunit))
										trainunit = "无";
									model.setTraininginstitutions(trainunit);
									
								}
							}
							list.add(model);
						}
						for (TrainingExperience model : list) {
							//System.out.println("===培训经历==="+model.toString());
							resumeDao.insertTrainingExperience(model);
						}
					}
				}
			}
			//if(eamilguid != null){
				// 应聘信息
				// 招聘职位截取
				String postname=null;
				if(StringUtils.isNotEmpty(subject)){
					if(subject.indexOf("聘")!=-1&&subject.lastIndexOf("-")!=-1){
						postname= subject.substring(subject.indexOf("聘") + 1, subject.lastIndexOf("-"));
						postname = postname.replace(" ", "");
					}
				}else
					subject = "";

							// 实例化
				MyCandidates model = new MyCandidates();
				model.setMycandidatesguid(UUIDGenerator.randomUUID());
				model.setWebuserguid(user.getWebuserguid());
				model.setCandidatesstate(Constance.CandidatesState_One);
				model.setCandidatestype(Constance.User4);
				model.setProgress(Constance.VALID_NO);
				model.setResumeeamilguid(eamilguid);
				model.setReadtype(Constance.VALID_NO);
				model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				// 職位
				if(StringUtils.isNotEmpty(postname)){
					List<RecruitPost> postlist = recruitmentDao.getRecruitPostByRecruitPostName(postname);
					model.setRecruitpostname(postname);
					model.setPostname(postname);
					if (!postlist.isEmpty()) {
						RecruitPost recruitPost = postlist.get(0);
						model.setRecruitpostguid(recruitPost.getRecruitpostguid());
						model.setRecruitpostname(postname);
					}else{
						model.setRecruitpostname(postname);
					}
				}
				
				//过滤掉一周以内投递过的相同简历
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				model.setTempdate(format.format(System.currentTimeMillis()));
				List<MyCandidates> candidateslist = myCandidatesDao.checkResume(model);
				if(candidateslist != null&&!candidateslist.isEmpty()){
					//System.out.println("===一周内以投递===");
					return null;
				}

				// 投递时间
				model.setCandidatestime(new Date(System.currentTimeMillis()));
				

				// 保存应聘信息
				myCandidatesDao.insertMyCandidates(model);
				
				
				// 回傳應聘
				// user.setMycandidatesguid(model.getMycandidatesguid());

				// 回傳應聘
				// user.setMycandidatesguid(model.getMycandidatesguid());
			//}

			return user;
		}
		return null;
	}

	/**
	 * 上传简历解析
	 * 
	 * @param nameConvertCodeService
	 * @param sqlSession
	 * @param htm
	 * @param subject
	 * @param eamilguid
	 * @return
	 */
	public WebUser savediv(NameConvertCodeService nameConvertCodeService, SqlSession sqlSession, String htm, String subject, String eamilguid){

		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		// 邮件正文
		if (StringUtils.isEmpty(htm))
			return null;
		htm = htm.replaceAll("&nbsp;", "");

		// 设置编码集
		org.jsoup.nodes.Document doc = Jsoup.parse(htm);
		//System.out.println("===doc==="+doc);

		// 简历信息
		Elements links = doc.select("body>div");
		//System.out.println("***************************************"+links.size());
		if (links.size() < 0) {
			return null;
		}

		// 开始解析
		if (!links.isEmpty()) {

			Elements users = links.select("div[class=summary]");
			//System.out.println("===users==="+users);
			// 外網用戶
			WebUser user = null;

			// 用户基本信息
			if(users.size() <= 0)
				return null;
			Element user1 = users.get(0);
			//System.out.println("===用户基本信息==="+user1);
			// 姓名
			Elements strong = user1.select("h1");
			String name = strong.get(0).text();

			String email = null;
			String moiblie = null;
			String birthday = null;
			String sexname = null;
			String homeplace = null;
			String register=null;//户籍
			String marriedname = null;
			

			// 性别,出生日期,婚姻状况
			//Elements sexday = user1.select("td[align=right][valign=top]");
			String[] array = users.text().split("\\|");
			if(array.length > 0){
				for(int i = 0; i < array.length; i++){
					//System.out.println("==str=="+array[i]);
					if(i==0){
						String[] temp =  array[i].split(" ");
						if(temp.length>=2)
							sexname = temp[1];
						//System.out.println("==性别=="+sexname);
					}else if(i==1){
						marriedname = array[i];
						//System.out.println("==婚否=="+marriedname);
					}else if(i==2){
						birthday = array[i];
						birthday = birthday.replaceAll(" ", "");
						birthday = birthday.substring(0, birthday.length() - 1);
						birthday += "01日";
						marriedname = array[i];
						//System.out.println("==出生日期=="+marriedname);
					}else if(i==3){
						String[] temp =  array[i].split("：");
						if(temp.length >= 2)
							register = temp[1];
						//System.out.println("==户籍=="+register);
					}else if(i==4){
						String[] temp =  array[i].split(" ");
						if(temp.length > 0){
							if(StringUtils.isNotEmpty(temp[0]))
								homeplace = temp[0];
							else if(temp.length > 1 && StringUtils.isNotEmpty(temp[1]))
								homeplace = temp[1];
							else
								homeplace = "无";
						}
					//	System.out.println("==居住地=="+homeplace);
					}
				}
			}
			
			Elements emailelements = user1.select("a");
			Element emailelement = null;
			if(emailelements.size()>0)
				emailelement = emailelements.get(0);
			email = emailelement.text();
			//System.out.println("==邮箱=="+email);
			
			String phonestring = user1.text();
			String[] phonearray = phonestring.split("：");
			for(String str : phonearray){
				String[] temp = str.split(" ");
				for(int i = 0; i < temp.length; i++){
					if(temp[i].equals("E-mail")){
						if(i>0){
							moiblie = temp[i-1];
						}
					}
				}
			}
			if(StringUtils.isEmpty(moiblie))
				moiblie = "无";
			else{
				String regEx="[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(moiblie);
				moiblie = m.replaceAll("").trim();
			}
			//System.out.println("==手机=="+moiblie);
			if (StringUtils.isEmpty(email)) {
				return null;
			}
			// 保存到本地
			user = webUserDao.checkWebUserByEmail(null, email);
			if (user == null) {
				// 保存外网用户
				user = new WebUser(email, name);
				user.setWebuserguid(UUIDGenerator.randomUUID());
				
				webUserDao.insertWebUser(user);
			}
			user.setMobile(moiblie);
			// 外網用戶必須存在
			if (user == null)
				return null;
			// 简历信息
			Resume resume = resumeDao.getResumeById(user.getWebuserguid());
			// 如果簡歷存在以最新的為準
			boolean isnew = false;
			if (resume == null) {
				isnew = true;
				resume = new Resume();
				resume.setWebuserguid(user.getWebuserguid());
				resume.setCreatetime(new Timestamp(System.currentTimeMillis()));
			}
			resume.setModtime(new Timestamp(System.currentTimeMillis()));

			// 姓名
			resume.setName(user.getUsername());
			// 电话
			resume.setMobile(user.getMobile());
			// 邮件
			resume.setEmail(user.getEmail());

			// 居住地
			resume.setHomeplace(homeplace);

			// 出生日期
			resume.setBirthday(DateUtil.parseChina(birthday));

			// 性别
			if (!sexname.isEmpty()) {
				Integer sex = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.SEX, sexname);
				if (sex != null) {
					resume.setSexname(sexname);
					resume.setSex(sex);
				} else {
					resume.setSex(Constance.VALID_NO);
				}

			}
			
			//人才库
			//if(eamilguid == null)
				resume.setMark(Constance.VALID_NO);

			// 工作年限
			resume.setWorkage(Constance.VALID_NO);

			// 学历
			resume.setCulture(Constance.VALID_NO);
			
			Elements dlelements = links.select("dl[class=details]");
			if(dlelements.size() <= 0)
				return null;
			Elements dtelements = dlelements.get(0).select("dt").select("h5");
			String salary = "";
			String situation = "";
			String industry = "";
			String occupation = "";
			String valuation ="";
			boolean istrue = true;
			for(Element dtelement : dtelements){
				String text = dtelement.text().replaceAll(" ", "");
				if(text.equals("求职意向")){
					Elements lielements = dtelement.parent().nextElementSibling().select("li");
					Elements strongelements = lielements.select("strong");
					for(Element strongelement : strongelements){
						String temp = strongelement.text().replaceAll(" ", "");
						if(temp.equals("期望职业：")){
							String[] occu = strongelement.parent().text().split("：");
							if(occu.length > 1){
								occupation = occu[1];
								if(occupation.length() > 100)
									occupation = occupation.substring(0, 100);
								//System.out.println("====期望从事职业===="+occupation);
								resume.setOccupation(occupation);
							}
						}else if(temp.equals("期望月薪：")){
							String[] sal = strongelement.parent().text().split("：");
							if(sal.length > 1){
								salary = sal[1];
								if(salary.length() > 50)
									salary = salary.substring(0, 50);
								//System.out.println("====期望月薪===="+salary);
								resume.setSalary(salary);
							}
						}else if(temp.equals("目前状况：")){
							String[] sit = strongelement.parent().text().split("：");
							if(sit.length > 1){
								situation = sit[1];
								if(situation.length() > 50)
									situation = situation.substring(0, 50);
								//System.out.println("====目前状况===="+situation);
								resume.setSituation(situation);
							}
						}else if(temp.equals("期望行业：")){
							String[] ind = strongelement.parent().text().split("：");
							if(ind.length > 1){
								industry = ind[1];
								if(industry.length() > 50)
									industry = industry.substring(0, 50);
								//System.out.println("期望行业："+industry);
								resume.setIndustry(industry);
							}
						}
					}
				}else if(text.equals("自我评价")){
					valuation= dtelement.parent().nextElementSibling().text();
					if(valuation.length() > 500)
						valuation = valuation.substring(0, 500);
					if(StringUtils.isEmpty(salary))
						salary = "无";
					if(StringUtils.isEmpty(valuation))
						valuation = "无";
					if(StringUtils.isEmpty(occupation))
						occupation = "无";
					if(StringUtils.isEmpty(industry))
						industry = "无";
					if(StringUtils.isEmpty(situation))
						situation = "无";
					if(StringUtils.isEmpty(valuation))
						valuation = "无";
					resume.setSalary(salary);
					resume.setValuation(valuation);
					resume.setOccupation(occupation);
					resume.setIndustry(industry);
					resume.setSituation(situation);
					//System.out.println("====自我评价===="+valuation);
					resume.setValuation(valuation);
					if (isnew) {
						//System.out.println("====新增111===="+resume.toString());
						resumeDao.insertResume(resume);
					} else {
						//System.out.println("====更新111===="+resume.toString());
						resumeDao.updateResume(resume);
					}
					istrue = false;
				}else if(text.equals("工作经历")){
					// 工作经历先删除后插入
					resumeDao.delWorkExperienceByWebuserId(resume.getWebuserguid());
					List<WorkExperience> list = new ArrayList<WorkExperience>();
					Element workelement = dtelement.parent().nextElementSibling();
					Elements workelements = workelement.select("div[class=work-experience]");
					for(Element tempelement : workelements){
						WorkExperience model = new WorkExperience();
						Elements dateelements = tempelement.select("p");
						String date = "";
						if(dateelements.size() > 1){
							date = dateelements.get(0).text();
						}
						String[] dates = date.split("--");
						String startdate = "";
						String enddate = "";
						if(dates.length >1){
							startdate = dates[0].replaceAll(" ", "")+"/01";
							enddate = dates[1].replaceAll(" ", "")+"/01";
						}
						String companyinfo = tempelement.select("h6").text().replaceAll(" ", "");
						String[] com = companyinfo.split("\\|");
						//工作单位
						String workunit  = "";
						//职位
						String position = "";
						//职责描述
						String jobdescription = "";
						if(com.length > 0)
							workunit = com[0];
						if(com.length > 2)
							position = com[2];
						Elements jobelements = workelement.select("strong");
						for(Element jobdeselement : jobelements){
							if(jobdeselement.text().replaceAll(" ", "").equals("工作描述：")){
								String jobdesc = jobdeselement.parent().text();
								String[] job = jobdesc.split("：");
								if(job.length >= 2)
									jobdescription = job[1];
							}
							break;
						}
						model.setWorkexperienceguid(UUIDGenerator.randomUUID());
						model.setModtime(new Timestamp(System.currentTimeMillis()));
						model.setWebuserguid(resume.getWebuserguid());
						model.setStartdate(DateUtil.parseEnglish(startdate));
						if (model.getStartdate() == null)
							model.setStartdate(new Date(System.currentTimeMillis()));
						model.setEnddate(DateUtil.parseEnglish(enddate));
						if(StringUtils.isEmpty(workunit))
							workunit = "无";
						if(StringUtils.isEmpty(position))
							position = "无";
						if(StringUtils.isEmpty(jobdescription))
							jobdescription = "无";
						model.setPosation(position);
						model.setWorkunit(workunit);
						model.setJobdescription(jobdescription);
						list.add(model);
					}
					for(WorkExperience model : list){
						//System.out.println("===工作经历==="+model.toString());
						resumeDao.insertWorkExperience(model);
					}
				}else if(text.equals("项目经验")){
					// 项目经验先删除后插入
					resumeDao.delProjectExperienceByWebuserId(resume.getWebuserguid());
					List<ProjectExperience> list = new ArrayList<ProjectExperience>();
					Element projectelement = dtelement.parent().nextElementSibling();
					Elements projectelements = projectelement.select("div[class=project-experience");
					for(Element element : projectelements){
						ProjectExperience model = new ProjectExperience();
						String startdate = "";
						String enddate = "";
						//项目名称
						String workunit = "";
						//工作描述
						String jobdescription = "";
						Elements dateelements = element.select("p");
						String date = "";
						if(dateelements.size() > 1){
							date = dateelements.get(0).text();
						}
						String[] dates = date.split("--");
						if(dates.length >1){
							startdate = dates[0].replaceAll(" ", "")+"/01";
							enddate = dates[1].replaceAll(" ", "")+"/01";
						}
						Elements projectnameelement = element.select("h6");
						if(projectnameelement.size() > 0)
							workunit = projectnameelement.get(0).text();
						Elements jobelements = element.select("strong");
						for(Element prelement : jobelements){
							if(prelement.text().replaceAll(" ", "").equals("责任描述：")){
								String projectel = prelement.parent().text();
								String[] pro = projectel.split("：");
								if(pro.length >= 2)
									jobdescription = pro[1];
								//System.out.println("jobdescription==="+jobdescription);
								break;
							}
						}
						
						model.setStartdate(DateUtil.parseEnglish(startdate));
						if (model.getStartdate() == null)
							model.setStartdate(new Date(System.currentTimeMillis()));
						model.setEnddate(DateUtil.parseEnglish(enddate));
						model.setProjectexperienceguid(UUIDGenerator.randomUUID());
						model.setWebuserguid(resume.getWebuserguid());
						model.setModtime(new Timestamp(System.currentTimeMillis()));
						if(StringUtils.isEmpty(jobdescription))
							jobdescription = "无";
						model.setJobdescription(jobdescription);
						model.setItemname(workunit);
						list.add(model);
					}
					for (ProjectExperience model : list) {
						//System.out.println("===项目经验==="+model.toString());
						resumeDao.insertProjectExperience(model);
					}
				}else if(text.equals("教育经历")){
					// 教育经验先删除后插入
					resumeDao.delEducationExperienceByWebuserId(resume.getWebuserguid());
					//System.out.println("====教育经历===="+educationctelement);
					List<EducationExperience> list = new ArrayList<EducationExperience>();
					
					Elements educationelements = dlelements.select("div[class=education-background");
					for(Element element : educationelements){
						EducationExperience model = new EducationExperience();
						//开始时间
						String startdate = "";
						//结束时间
						String enddate = "";
						//学校
						String school = "";
						// 专业
						String specialty = "";
						//学历
						String culturename = "";
						// 专业描述
						String description = "";
						Integer culture = null;
						Elements dateelements = element.select("p");
						String date = "";
						if(dateelements.size() > 1){
							date = dateelements.get(0).text();
						}
						String[] dates = date.split("--");
						if(dates.length >1){
							startdate = dates[0].replaceAll(" ", "")+"/01";
							enddate = dates[1].replaceAll(" ", "")+"/01";
						}
						String education = element.select("h6").text().replaceAll(" ", "");
						String[] educations = education.split("\\|");
						if(educations.length > 0)
							school = educations[0];
						if(educations.length > 1)
							specialty = educations[1];
						if(educations.length > 2)
							culturename = educations[2];
						culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturename.replaceAll(" ", ""));
						if (culture == null)
							culture = Constance.VALID_NO;
						model.setStartdate(DateUtil.parseEnglish(startdate));
						if (model.getStartdate() == null)
							model.setStartdate(new Date(System.currentTimeMillis()));
						model.setEnddate(DateUtil.parseEnglish(enddate));
						model.setEducationexperienceguid(UUIDGenerator.randomUUID());
						model.setWebuserguid(resume.getWebuserguid());
						model.setModtime(new Timestamp(System.currentTimeMillis()));
						model.setSpecialty(specialty);
						if (culture == null)
							culture = Constance.VALID_NO;
						if (culture == null)
							culture = Constance.VALID_NO;
						model.setCulture(culture);
						if(StringUtils.isEmpty(description))
							description = "无";
						model.setMajordescription(description);
						model.setSchool(school);
						list.add(model);
					}
					for (EducationExperience model : list) {
						//System.out.println("===教育经历==="+model.toString());
						resumeDao.insertEducationExperience(model);
					}
				}else if(text.equals("培训经历")){
					// 教育经验先删除后插入
					resumeDao.delTrainingExperienceByWebuserId(resume.getWebuserguid());
					List<TrainingExperience> list = new ArrayList<TrainingExperience>();
					Element trainingelement = dtelement.parent().nextElementSibling();
					Elements trainingelements = trainingelement.select("div[class=training]");
					for(Element element : trainingelements){
						TrainingExperience model = new TrainingExperience();
						//开始时间
						String startdate = "";
						//结束时间
						String enddate = "";
						//培训机构
						String trainunit = "";
						//培训课程
						//String curriculum = "";
						//培训描述
						String traindescription = "";
						//证书
						String certificate = "";
						Elements dateelements = element.select("p");
						String date = "";
						if(dateelements.size() > 1){
							date = dateelements.get(0).text();
						}
						String[] dates = date.split("--");
						if(dates.length >1){
							startdate = dates[0].replaceAll(" ", "")+"/01";
							enddate = dates[1].replaceAll(" ", "")+"/01";
						}
						Elements trainunitelements = element.select("h6");
						if(trainunitelements.size() > 0)
							trainunit = trainunitelements.get(0).text();
						Elements trainingstrongelements = element.select("strong");
						for(Element traelement : trainingstrongelements){
							if(traelement.text().replaceAll(" ", "").equals("培训描述：")){
								String trainel = traelement.parent().text();
								String[] train = trainel.split("：");
								if(train.length >= 2)
									traindescription = train[1];
							}else if(traelement.text().replaceAll(" ", "").equals("所获证书：")){
								String certifi = traelement.parent().text();
								String[] tificate = certifi.split("：");
								if(tificate.length >= 2)
									certificate = tificate[1];
							}
							model.setStartdate(DateUtil.parseEnglish(startdate.replaceAll(" ", "")));
							if (model.getStartdate() == null)
								model.setStartdate(new Date(System.currentTimeMillis()));
							model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							model.setTrainingexperienceguid(UUIDGenerator.randomUUID());
							model.setWebuserguid(resume.getWebuserguid());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							if(StringUtils.isEmpty(certificate))
								certificate = "无";
							model.setCertificate(certificate);
							if(StringUtils.isEmpty(traindescription))
								traindescription = "无";
							model.setTrainingcontent(traindescription);
							if(StringUtils.isEmpty(trainunit))
								trainunit = "无";
							model.setTraininginstitutions(trainunit);
						}
						list.add(model);
					}
					for (TrainingExperience model : list) {
						//System.out.println("===培训经历==="+model.toString());
						resumeDao.insertTrainingExperience(model);
					}
				}
			}
			if(istrue){
				if(StringUtils.isEmpty(salary))
					salary = "无";
				if(StringUtils.isEmpty(valuation))
					valuation = "无";
				if(StringUtils.isEmpty(occupation))
					occupation = "无";
				if(StringUtils.isEmpty(industry))
					industry = "无";
				if(StringUtils.isEmpty(situation))
					situation = "无";
				if(StringUtils.isEmpty(valuation))
					valuation = "无";
				resume.setSalary(salary);
				resume.setValuation(valuation);
				resume.setOccupation(occupation);
				resume.setIndustry(industry);
				resume.setSituation(situation);
				//System.out.println("====自我评价===="+valuation);
				resume.setValuation(valuation);
				if (isnew) {
					//System.out.println("====新增111===="+resume.toString());
					resumeDao.insertResume(resume);
				} else {
					//System.out.println("====更新111===="+resume.toString());
					resumeDao.updateResume(resume);
				}
			}
			String postname = "";
			MyCandidates model = new MyCandidates();
			model.setMycandidatesguid(UUIDGenerator.randomUUID());
			model.setWebuserguid(user.getWebuserguid());
			model.setCandidatesstate(Constance.CandidatesState_One);
			model.setCandidatestype(Constance.User4);
			model.setProgress(Constance.VALID_NO);
			model.setResumeeamilguid(eamilguid);
			model.setReadtype(Constance.VALID_NO);
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			// 職位
			if(StringUtils.isNotEmpty(postname)){
				List<RecruitPost> postlist = recruitmentDao.getRecruitPostByRecruitPostName(postname);
				model.setRecruitpostname(postname);
				model.setPostname(postname);
				if (!postlist.isEmpty()) {
					RecruitPost recruitPost = postlist.get(0);
					model.setRecruitpostguid(recruitPost.getRecruitpostguid());
					model.setRecruitpostname(postname);
				}else{
					model.setRecruitpostname(postname);
				}
			}
			
			//过滤掉一周以内投递过的相同简历
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			model.setTempdate(format.format(System.currentTimeMillis()));
			List<MyCandidates> candidateslist = myCandidatesDao.checkResume(model);
			if(candidateslist != null&&!candidateslist.isEmpty()){
				//System.out.println("===一周内以投递===");
				return null;
			}

			// 投递时间
			model.setCandidatestime(new Date(System.currentTimeMillis()));

			// 保存应聘信息
			myCandidatesDao.insertMyCandidates(model);
		return user;
		}
		return null;
	
	}
}
