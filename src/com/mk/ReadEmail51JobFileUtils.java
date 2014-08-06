package com.mk;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;
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

public class ReadEmail51JobFileUtils {


	@Transactional
	public WebUser save(NameConvertCodeService nameConvertCodeService, SqlSession sqlSession, org.jsoup.nodes.Document doc, String eamilguid) {
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);

		// 简历信息
		Elements links = doc.select("body>table");
		//System.out.println("***************************************"+links);
		if (links.size() < 2) {
			//System.out.println("模板不对请检查!");
			return null;
		}

		// 开始解析
		if (!links.isEmpty()) {
			org.jsoup.nodes.Element root = links.get(1);

			// 用户基本信息
			Elements table100 = root.select("table[border=0][cellpadding=0][cellspacing=0][width=100%]");
			if (table100.isEmpty())
				return null;
			org.jsoup.nodes.Element users = table100.get(0);

			// 外網用戶
			WebUser user = null;

			// 姓名
			Elements strong = root.select("strong");
			if (strong.isEmpty())
				return null;
			org.jsoup.nodes.Element nameel = strong.get(0);
			String name = nameel.text();

			// 电话,E-mail
			Elements te = users.select("td[colspan=3][height=20]");
			// System.out.println("=============电话,E-mail===============");
			// System.out.println(te);
			if (!te.isEmpty() && te.size() >= 2) {
				int num = te.size();
				String mobile = null;
				String email = null;
				if (num == 2) {
					// 电话
					mobile = te.get(0).text();
					if (mobile != null && mobile.length() > 4) {
						mobile = mobile.substring(0, mobile.length() - 4);
					}

					// 邮件
					email = te.get(1).text();
					if (StringUtils.isEmpty(email))
						return null;
				} else {
					// 电话
					mobile = te.get(1).text();
					if (mobile != null && mobile.length() > 4) {
						mobile = mobile.substring(0, mobile.length() - 4);
					}
					
					//只取数字
					if(mobile != null){
					String regEx="[^0-9]";
					Pattern p = Pattern.compile(regEx);
					Matcher m = p.matcher(mobile);
					mobile = m.replaceAll("").trim();
					}
					// 邮件
					email = te.get(2).text();
					if (StringUtils.isEmpty(email))
						return null;
				}

				// 保存到本地
				user = webUserDao.checkWebUserByEmail(null, email);
				if (user == null) {
					// 保存外网用户
					user = new WebUser(email, name);
					user.setWebuserguid(UUIDGenerator.randomUUID());
					
					// System.out.println(user.toString());
					webUserDao.insertWebUser(user);
				}
				user.setMobile(mobile);
			}
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
			if (user.getUsername().length() > 25)
				resume.setName(user.getUsername().substring(0, 25));
			else
				resume.setName(user.getUsername());
			// 电话
			resume.setMobile(user.getMobile());
			// 邮件
			resume.setEmail(user.getEmail());

			// 性别,出生日期
			Elements sexday = users.select("b");
			// System.out.println("sexday=====" + sexday);
			// System.out.println("sexday.text()=====" + sexday.text());
			String[] array = sexday.text().split("\\|");
			// 工作年限(选项)
			// System.out.println("array==="+array[0]);
			if (array.length > 0) {
				String workagename = array[0];
				workagename = workagename.replaceAll(" ", "");
				if (StringUtils.isNotEmpty(workagename)) {
					workagename = workagename.substring(0, workagename.length() - 4);
					// System.out.println("======模板一工作年限======"+workagename);
					Integer workage = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.WORKAGE, workagename);
					if (workage != null) {
						resume.setWorkagename(workagename);
						resume.setWorkage(workage);
					} else {
						if (workagename.equals("应")) {
							// System.out.println("====应届毕业生");
							workagename = "应届毕业生";
							workage = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.WORKAGE, workagename);
							if (workage != null) {
								resume.setWorkagename(workagename);
								resume.setWorkage(workage);
							} else
								resume.setWorkage(Constance.VALID_NO);
						} else {
							// System.out.println("=====在读学生====");
							workagename = "在读学生";
							workage = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.WORKAGE, workagename);
							if (workage != null) {
								resume.setWorkagename(workagename);
								resume.setWorkage(workage);
							} else
								resume.setWorkage(Constance.VALID_NO);
						}
						if (resume.getWorkage() == null)
							resume.setWorkage(Constance.VALID_NO);
					}
				}
			}

			// 性别
			if (array.length >= 1) {
				String sexel = array[1];
				String sexname = sexel.replaceAll(" ", "");
				// 数据翻译
				Integer sex = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.SEX, sexname);
				if (sex != null) {
					resume.setSexname(sexname);
					resume.setSex(sex);
				} else {
					resume.setSex(Constance.VALID_NO);
				}
			}

			// 出生日期
			if (array.length >= 2) {
				String birthday = array[2];
				birthday = birthday.substring(birthday.indexOf("(") + 1, birthday.indexOf(")"));
				String[] birthdays = birthday.split("/");
				if (birthdays.length > 1) {
					birthday = birthday.replace(" ", "");
					resume.setBirthday(DateUtil.parseEnglish(birthday));
				} else {
					birthday = birthday.replace(" ", "");
					resume.setBirthday(DateUtil.parseChina(birthday));
				}
			}
			
			//人才库
			//if(eamilguid == null)
				resume.setMark(Constance.VALID_NO);

			// 居住地
			Elements address = users.select("td[height=20][width=10%]+td");
			if (address.text().isEmpty()) {
				resume.setHomeplace("无");
			} else {
				resume.setHomeplace(address.text());
			}

			// 最高学历
			Elements culturehtm = root.select("td[colspan=2][width=49%] td[width=230]");
			if (!culturehtm.isEmpty()) {
				String culturename = culturehtm.text();
				// 数据库翻译
				Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturename);
				if (culture != null) {
					resume.setCulturename(culturename);
					resume.setCulture(culture);
				} else
					resume.setCulture(Constance.VALID_NO);
			} else
				resume.setCulture(Constance.VALID_NO);

			// System.out.println(resume.toString());
			// 解析
			Elements info = root.select("table[class=table_set]");

			Elements thems = root.select("td[class=cvtitle]");
			//目前状况
			String situation = "";
			//期望月薪
			String salary = "";
			//希望行业
			String occupation = "";
			String valuation = "";
			//行业
			String industry = "";
			//自我评价
			if(thems.size() > 0){
				for(int i = 0; i < thems.size(); i++){
					if(thems.get(i).text().replaceAll(" ", "").equals("自我评价")){
						Elements valuationelement = thems.get(i).parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]");
						valuation = valuationelement.text();
						if(valuation.length() > 500)
							valuation = valuation.substring(0, 500);
						//resume.setValuation(valuation);
						//System.out.println("====自我评价：===="+valuation);
					}else if(thems.get(i).text().replaceAll(" ", "").equals("求职意向")){
						Elements jobintensionelements = thems.get(i).parent().nextElementSibling().nextElementSibling().nextElementSibling().select("table[class=table_set]");
						Elements jobintensionelement = jobintensionelements.select("td[class=text_left]");
						for(Element element : jobintensionelement){
							String[] tempjobintensions = element.text().split("：");
							if(tempjobintensions.length > 0){
								String tempjobintension = tempjobintensions[0].replaceAll(" ", "");
								//System.out.println("====信息长度===="+tempjobintension.length());
								//System.out.println("====模板一求职意向===="+tempjobintension);
								if(tempjobintension.equals("目标职能")){
									occupation = element.select("span").text();
									if(occupation.length() > 100)
										occupation = occupation.substring(0, 100);
									resume.setOccupation(occupation);
									//System.out.println("====希望行业：===="+occupation);
								}else	if(tempjobintension.equals("期望月薪")){
									salary = element.select("span").text();
									if(salary.length() > 50)
										salary = salary.substring(0, 50);
									//System.out.println("====期望月薪：===="+salary);
								}else if(tempjobintension.equals("目前状况")){
									situation = element.select("span").text();
									if(situation.length() > 50)
										situation = situation.substring(0, 50);
									//System.out.println("====目前状况：===="+situation);
								}else if(tempjobintension.equals("希望行业")){
									industry = element.select("span").text();
									if(industry.length() > 50)
										industry = industry.substring(0, 50);
									//System.out.println("====目前状况：===="+situation);
								}
							}
						}
					}
				}
			}
			if(StringUtils.isEmpty(situation))
				situation = "无";
			if(StringUtils.isEmpty(salary))
				salary = "无";
			if(StringUtils.isEmpty(occupation))
				occupation = "无";
			if(StringUtils.isEmpty(valuation))
				valuation = "无";
				resume.setValuation(valuation);
			resume.setSalary(salary);
			resume.setSituation(situation);
			resume.setOccupation(occupation);
			
		/*	//希望行业
		 	for (int j = 1; j < thems.size(); j++) {
					String them = thems.get(j).text();
					if (them.equals("工作经验")) {
						
						 * 解析当前table
						 * 
						 * 
						Elements workexperiencestable = thems.get(j).parent().nextElementSibling().nextElementSibling().nextElementSibling().select("table[class=table_set]");
						
						 * 以td[colspan=2][class=text_left]为起点解析
						 * 
						 * 
						Elements workexperiences = workexperiencestable.select("td[colspan=2][class=text_left]");
						// System.out.println("====工作经验===="+workexperiences);
						for (Element element : workexperiences) {
							if(element.parent().nextElementSibling().select("td[width=22%][class=text_left]") !=null){
								String industryelement = element.parent().nextElementSibling().select("td[width=22%][class=text_left]").text().replaceAll(" ", "");
								if(industryelement.equals("所属行业：")){
									industry = element.parent().nextElementSibling().select("td[width=78%][class=text]").text().replaceAll(" ", "");
									if(industry.length() > 50)
										industry = industry.substring(0, 50);
								}
							}
							break;
					}
				}
			}*/
			if(StringUtils.isEmpty(industry))
				industry = "无";
			resume.setIndustry(industry);
			resume.setSituation("无");
			// 保存简历
			if (isnew)
				resumeDao.insertResume(resume);
			else
				resumeDao.updateResume(resume);
			// System.out.println("保存简历成功!!!!!!!!!!!!!!!!!!");

			// 应界生和社会招聘
			// [ 应届生简历/无工作经验 ]
			Elements typeel = root.select("span[style=color:#676767;]");
			String type = typeel.text();
			boolean resumeState = type.equals("[ 应届生简历/无工作经验 ]");
			// System.out.println("type============" + type);
			// System.out.println("resumeState============" + resumeState);


			// Elements table = root.select("table[class=table_set]");

			// 社会实践
			if (resumeState) {
				if (info.isEmpty())
					return null;
				for (int j = 1; j < thems.size(); j++) {
					String them = thems.get(j).text();
					// 工作经验
					if (them.equals("工作经验")) {
						// 社会实践先删除后插入
						resumeDao.delWorkExperienceByWebuserId(resume.getWebuserguid());
						List<WorkExperience> list = new ArrayList<WorkExperience>();
						/*
						 * 解析当前table
						 * 
						 * */
						Elements workexperiencestable = thems.get(j).parent().nextElementSibling().nextElementSibling().nextElementSibling().select("table[class=table_set]");
						/*
						 * 以td[colspan=2][class=text_left]为起点解析
						 * 
						 * */
						Elements workexperiences = workexperiencestable.select("td[colspan=2][class=text_left]");
						// System.out.println("====工作经验===="+workexperiences);
						for (Element element : workexperiences) {
							WorkExperience model = new WorkExperience();
							// 时间和单位
							String workdate = element.text();

							String[] arrworkdate = workdate.split("：");

							// 工作单位
							String workunit = "";
							workunit = arrworkdate[1];
							if (workunit.length() > 50)
								workunit = workunit.substring(0, 50);

							// 职位
							String department = "";
							if(element.parent().nextElementSibling().nextElementSibling() != null){
							if (element.parent().nextElementSibling().nextElementSibling().select("td[class=text]").text().length() == 0) {
								department = "无";
							} else {
								department = element.parent().nextElementSibling().nextElementSibling().select("td[class=text]").text();
							}
						}
							// 职责描述
							String content = "";
							/*
							 * 以td[colspan=2][class=text_left]为起点向下解析职责描述td[id=Cur_Val][colspan=2][valign=top]里的值
							 * 当td里的属性不等于td[id=Cur_Val][colspan=2][valign=top]，向下解析
							 * 当值不存在时向下解析
							 * 
							 * */
							if (element.parent().nextElementSibling() != null && element.parent().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() == 0) {
								if (element.parent().nextElementSibling().nextElementSibling() != null
										&& element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() == 0) {
									if (element.parent().nextElementSibling().nextElementSibling().nextElementSibling() != null
											&& element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() > 0) {
										content = element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
									}
								} else {
									if(element.parent().nextElementSibling().nextElementSibling() != null)
										content = element.parent().nextElementSibling().nextElementSibling().text();
								}
							} else {
								if(element.parent().nextElementSibling() != null)
									content = element.parent().nextElementSibling().text();
							}
							if (StringUtils.isEmpty(content))
								content = "无";
							if (content.length() > 500)
								content = content.substring(0, 500);
							String dates = arrworkdate[0];
							// 开始时间
							String startdate = "";
							// 结束时间
							String enddate = "";
							if (dates.length() > 0) {
								String[] arrdate = dates.split("--");
								
								startdate = arrdate[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								
								enddate = arrdate[1] + "/01";
								//System.out.println("===开始时间1111===="+startdate);
								//System.out.println("===开始时间22222===="+DateUtil.parseEnglish(startdate));
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setWorkexperienceguid(UUIDGenerator.randomUUID());
							model.setWebuserguid(resume.getWebuserguid());
							model.setModtime(new Timestamp(System.currentTimeMillis()));

							model.setWorkunit(workunit);
							model.setJobdescription(content);
							model.setPosation(department);
							list.add(model);
						}
						for (WorkExperience model : list) {
							resumeDao.insertWorkExperience(model);
						}
					}else	if (them.equals("项目经验")) {
						// 项目经验
						// 项目经验先删除后插入
						resumeDao.delProjectExperienceByWebuserId(resume.getWebuserguid());
						List<ProjectExperience> list = new ArrayList<ProjectExperience>();
						Elements projeceworkexperiencestable = thems.get(j).parent().nextElementSibling().nextElementSibling().nextElementSibling().select("table[class=table_set]");
						Elements projectworkexperiences = projeceworkexperiencestable.select("td[id=Cur_Val][width=84%]");
						for (Element element : projectworkexperiences) {
							// 职责描述
							String projectdescription = element.text();
							if (StringUtils.isEmpty(projectdescription))
								projectdescription = "无";
							if (projectdescription.length() > 500)
								projectdescription = projectdescription.substring(0, 500);

							String workdate = "";
							// 时间和单位
							/*
							 * 以td[id=Cur_Val][width=84%]为起点向上解析时间和单位td里的值，如果有意tr中只含有一个td，既为时间和单位
							 * 
							 * */
							if (element.parent().previousElementSibling() != null && element.parent().previousElementSibling().select("td").size() >= 2) {
								if (element.parent().previousElementSibling().previousElementSibling() != null
										&& element.parent().previousElementSibling().previousElementSibling().select("td").size() >= 2) {
									if (element.parent().previousElementSibling().previousElementSibling().previousElementSibling() != null
											&& element.parent().previousElementSibling().previousElementSibling().previousElementSibling().select("td").size() >= 2) {
										workdate = element.parent().previousElementSibling().previousElementSibling().previousElementSibling().previousElementSibling().text();
									} else {
										if (element.parent().previousElementSibling().previousElementSibling().previousElementSibling() != null)
											workdate = element.parent().previousElementSibling().previousElementSibling().previousElementSibling().text();
									}
								} else {
									if (element.parent().previousElementSibling().previousElementSibling() != null)
										workdate = element.parent().previousElementSibling().previousElementSibling().text();
								}
							} else {
								if (element.parent().previousElementSibling() != null)
									workdate = element.parent().previousElementSibling().text();
							}
							String[] arrworkdate = workdate.split(":");
							if (arrworkdate.length <= 1)
								arrworkdate = workdate.split("：");
							ProjectExperience model = new ProjectExperience();
							// 项目名称
							String projectunit = "";
							if(arrworkdate.length > 1)
								projectunit = arrworkdate[1];
							if (StringUtils.isEmpty(projectunit))
								projectunit = "无";
							if (projectunit.length() > 50)
								projectunit = projectunit.substring(0, 50);
							// 时间
							String startdate = "";
							String enddate = "";
							String dates = arrworkdate[0];
							if (dates.length() > 0) {
								String[] arrdate = dates.split("--");
								//只取数字和/
								startdate = arrdate[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								enddate = arrdate[1] + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setProjectexperienceguid(UUIDGenerator.randomUUID());
							model.setWebuserguid(resume.getWebuserguid());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setJobdescription(projectdescription);
							model.setItemname(projectunit);
							list.add(model);
						}
						for (ProjectExperience model : list) {
							resumeDao.insertProjectExperience(model);
						}
					}else	if (them.equals("教育经历")) {
						// 教育经验
						// 教育经验先删除后插入
						resumeDao.delEducationExperienceByWebuserId(resume.getWebuserguid());
						List<EducationExperience> list = new ArrayList<EducationExperience>();
						Elements educationexperiencetable = thems.get(j).parent().nextElementSibling().nextElementSibling().nextElementSibling().select("table[class=table_set]");
						Elements educationexperience = educationexperiencetable.select("td[class=text_left][width=26%]");
						for (Element element : educationexperience) {

							// 学校
							String school = "";
							if (element.nextElementSibling() != null)
								school = element.nextElementSibling().text();
							if (StringUtils.isEmpty(school))
								school = "无";
							if (school.length() > 50)
								school = school.substring(0, 50);
							// 专业
							String specialty = "";
							if (element.nextElementSibling().nextElementSibling() != null)
								specialty = element.nextElementSibling().nextElementSibling().text();
							if (StringUtils.isEmpty(specialty))
								specialty = "无";
							if (specialty.length() > 50)
								specialty = specialty.substring(0, 50);
							// 学历
							String culturename = "";
							if (element.nextElementSibling().nextElementSibling().nextElementSibling() != null)
								culturename = element.nextElementSibling().nextElementSibling().nextElementSibling().text();
							Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturename);
							if (culture == null)
								culture = Constance.VALID_NO;
							// 专业描述
							String description = "";
							if (element.parent().nextElementSibling() != null)
								description = element.parent().nextElementSibling().select("td[id=Cur_Val]").text();
							if (StringUtils.isEmpty(description))
								description = "无";
							if (description.length() > 500)
								description = description.substring(0, 500);

							EducationExperience model = new EducationExperience();
							// 时间
							String startdate = "";
							String enddate = "";
							String arrdates = element.text();
							if (arrdates.length() > 0) {
								String[] arrdate = arrdates.split("--");
								//只取数字
								startdate = arrdate[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								enddate = arrdate[1] + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setEducationexperienceguid(UUIDGenerator.randomUUID());
							model.setWebuserguid(resume.getWebuserguid());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setSpecialty(specialty);
							model.setCulture(culture);
							model.setMajordescription(description);
							model.setSchool(school);
							list.add(model);
						}
						for (EducationExperience model : list) {
							resumeDao.insertEducationExperience(model);
						}
					}else if (them.equals("培训经历")) {
						// 培训经历
						// 教育经验先删除后插入
						resumeDao.delTrainingExperienceByWebuserId(resume.getWebuserguid());
						List<TrainingExperience> list = new ArrayList<TrainingExperience>();
						Elements trainingexperiencetable = thems.get(j).parent().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().select("table[class=table_set]");
						Elements trainingexperience = trainingexperiencetable.select("td[class=text_left][width=20%]");
						for (Element element : trainingexperience) {
							// 机构
							String trainunit = null;
							if (element.nextElementSibling() != null)
								trainunit = element.nextElementSibling().text();
							if (StringUtils.isEmpty(trainunit))
								trainunit = "无";
							if (trainunit.length() > 50)
								trainunit = trainunit.substring(0, 50);
							// 内容
							String content = null;
							if (element.nextElementSibling().nextElementSibling() != null)
								content = element.nextElementSibling().nextElementSibling().text();
							if (StringUtils.isEmpty(content))
								content = "无";
							if (content.length() > 500)
								content = content.substring(0, 500);
							// 证书
							String certificate = null;
							if (element.nextElementSibling().nextElementSibling().nextElementSibling() != null)
								certificate = element.nextElementSibling().nextElementSibling().nextElementSibling().text();
							if (StringUtils.isEmpty(certificate))
								certificate = "无";
							if (certificate.length() > 50)
								certificate = certificate.substring(0, 50);
							String rmk = null;
							if (element.nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling() != null)
								rmk = element.nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().text();
							if (StringUtils.isEmpty(rmk))
								rmk = "无";
							if (rmk.length() > 500)
								rmk = rmk.substring(0, 500);
							TrainingExperience model = new TrainingExperience();
							// 时间
							String dates = element.text();
							if (dates.length() > 0) {
								String[] arrdate = dates.split("--");
								String startdate = "";
								//只取数字
								startdate = arrdate[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								String enddate = arrdate[1].replaceAll("：", "") + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}

							model.setTrainingexperienceguid(UUIDGenerator.randomUUID());
							model.setWebuserguid(resume.getWebuserguid());
							model.setModtime(new Timestamp(System.currentTimeMillis()));

							model.setCertificate(certificate);
							model.setTrainingcontent(content);
							model.setTraininginstitutions(trainunit);
							model.setRmk(rmk);
							list.add(model);
						}
						for (TrainingExperience model : list) {
							resumeDao.insertTrainingExperience(model);
						}

					}
				}

			} else {
				if (info.isEmpty())
					return null;
				for (int j = 1; j < thems.size(); j++) {
					// System.out.println("=====姓名===="+resume.getName());
					String them = thems.get(j).text();// 数组去掉前后空格
					// 工作经验
					if (them.equals("工作经验")) {
						// 社会实践先删除后插入
						resumeDao.delWorkExperienceByWebuserId(resume.getWebuserguid());
						List<WorkExperience> list = new ArrayList<WorkExperience>();
						/*
						 * 获取当前table
						 * */
						Elements workexperiencestable = thems.get(j).parent().nextElementSibling().nextElementSibling().nextElementSibling().select("table[class=table_set]");
						/*
						 * 以td[colspan=2][class=text_left]为起点开始解析
						 * */
						Elements workexperiences = workexperiencestable.select("td[colspan=2][class=text_left]");
						// System.out.println("====工作经验===="+workexperiences);
						for (Element element : workexperiences) {
							WorkExperience model = new WorkExperience();
							// 时间和单位
							String workdate = element.text();

							String[] arrworkdate = workdate.split("：");

							// 工作单位
							String workunit = "";
							workunit = arrworkdate[1];
							if (workunit.length() > 50)
								workunit = workunit.substring(0, 50);
							// 职位
							String department = "";
							if (element.parent().nextElementSibling() != null && element.parent().nextElementSibling().nextElementSibling() != null) {
								if (element.parent().nextElementSibling().nextElementSibling().select("td[class=text]").text().length() == 0) {
									department = "无";
								} else {
									department = element.parent().nextElementSibling().nextElementSibling().select("td[class=text]").text();
								}
							}
							if (StringUtils.isEmpty(department))
								department = "无";

							// 职责描述
							String content = "";
							/*
							 * 以td[colspan=2][class=text_left]为起点向下解析职责描述td[id=Cur_Val][colspan=2][valign=top]里的值
							 * 当td里的属性不等于td[id=Cur_Val][colspan=2][valign=top]，向下解析
							 * */
							if (element.parent().nextElementSibling() != null && element.parent().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() == 0) {
								if (element.parent().nextElementSibling().nextElementSibling() != null
										&& element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() == 0) {
									if (element.parent().nextElementSibling().nextElementSibling().nextElementSibling() != null
											&& element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]") != null) {
										content = element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
									}
								} else {
									if (element.parent().nextElementSibling().nextElementSibling() != null)
										content = element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
								}
							} else {
								if (element.parent().nextElementSibling() != null)
									content = element.parent().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
							}
							if (StringUtils.isEmpty(content))
								content = "无";
							if (content.length() > 500)
								content = content.substring(0, 500);
							String dates = arrworkdate[0];
							// 开始时间
							String startdate = "";
							// 结束时间
							String enddate = "";
							if (dates.length() > 0) {
								String[] arrdate = dates.split("--");
								//只取数字
								startdate = arrdate[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								enddate = arrdate[1] + "/01";
								//System.out.println("===1开始时间1111===="+startdate);
								//System.out.println("===2开始时间22222===="+DateUtil.parseEnglish(startdate));
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setWorkexperienceguid(UUIDGenerator.randomUUID());
							model.setWebuserguid(resume.getWebuserguid());
							model.setModtime(new Timestamp(System.currentTimeMillis()));

							model.setWorkunit(workunit);
							model.setJobdescription(content);
							model.setPosation(department);
							list.add(model);
						}
						for (WorkExperience model : list) {
							resumeDao.insertWorkExperience(model);
						}
					}else if (them.equals("项目经验")) {
						// System.out.println("====项目经验===="
						// 项目经验);
						// 项目经验先删除后插入
						resumeDao.delProjectExperienceByWebuserId(resume.getWebuserguid());
						List<ProjectExperience> list = new ArrayList<ProjectExperience>();
						Elements projeceworkexperiencestable = thems.get(j).parent().nextElementSibling().nextElementSibling().nextElementSibling().select("table[class=table_set]");
						Elements projectworkexperiences = projeceworkexperiencestable.select("td[id=Cur_Val][width=84%]");
						for (Element element : projectworkexperiences) {
							// 职责描述
							String projectdescription = element.text();
							if (StringUtils.isEmpty(projectdescription))
								projectdescription = "无";
							if (projectdescription.length() > 500)
								projectdescription = projectdescription.substring(0, 500);
							// 时间和单位
							String workdate = "";
							// 时间和单位
							/*
							 *以td[colspan=2][valign=top][id!=Cur_Val]为起点开始解析td
							 *当前标签下的td的个数大于等于2，则向下一个标签寻找
							 **/
							if (element.parent().previousElementSibling() != null && element.parent().previousElementSibling().select("td").size() >= 2) {
								if (element.parent().previousElementSibling().previousElementSibling() != null
										&& element.parent().previousElementSibling().previousElementSibling().select("td").size() >= 2) {
									if (element.parent().previousElementSibling().previousElementSibling().previousElementSibling() != null
											&& element.parent().previousElementSibling().previousElementSibling().previousElementSibling().select("td").size() >= 2) {
										workdate = element.parent().previousElementSibling().previousElementSibling().previousElementSibling().previousElementSibling().text();
									} else {
										if (element.parent().previousElementSibling().previousElementSibling().previousElementSibling() != null)
											workdate = element.parent().previousElementSibling().previousElementSibling().previousElementSibling().text();
									}
								} else {
									if (element.parent().previousElementSibling().previousElementSibling() != null)
										workdate = element.parent().previousElementSibling().previousElementSibling().text();
								}
							} else {
								if (element.parent().previousElementSibling() != null)
									workdate = element.parent().previousElementSibling().text();
							}
							String[] arrworkdate = workdate.split(":");
							if (arrworkdate.length <= 1)
								arrworkdate = workdate.split("：");

							// 项目名称
							String projectunit = "";
							if(arrworkdate.length > 1)
								projectunit = arrworkdate[1];
							if (StringUtils.isEmpty(projectunit))
								projectunit = "无";
							if (projectunit.length() > 50)
								projectunit = projectunit.substring(0, 50);
							
							ProjectExperience model = new ProjectExperience();
							// 时间
							String dates = arrworkdate[0];
							String startdate = "";
							String enddate = "";
							if (dates.length() > 0) {
								String[] arrdate = dates.split("--");
								//只取数字
								startdate = arrdate[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								enddate = arrdate[1] + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setProjectexperienceguid(UUIDGenerator.randomUUID());
							model.setWebuserguid(resume.getWebuserguid());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setJobdescription(projectdescription);
							model.setItemname(projectunit);
							list.add(model);
						}
						for (ProjectExperience model : list) {
							resumeDao.insertProjectExperience(model);
						}
					}else if (them.equals("教育经历")){
						// 教育经验 {
						// 教育经验先删除后插入
						resumeDao.delEducationExperienceByWebuserId(resume.getWebuserguid());
						List<EducationExperience> list = new ArrayList<EducationExperience>();
						Elements educationexperiencetable = thems.get(j).parent().nextElementSibling().nextElementSibling().nextElementSibling().select("table[class=table_set]");
						Elements educationexperience = educationexperiencetable.select("td[class=text_left][width=26%]");
						for (Element element : educationexperience) {

							String school = "";
							// 学校
							if (element.nextElementSibling() != null)
								school = element.nextElementSibling().text();
							if (StringUtils.isEmpty(school))
								school = "无";
							if (school.length() > 50)
								school = school.substring(0, 50);
							// 专业
							String specialty = "";
							if (element.nextElementSibling().nextElementSibling() != null)
								specialty = element.nextElementSibling().nextElementSibling().text();
							if (StringUtils.isEmpty(specialty))
								specialty = "无";
							if (specialty.length() > 50)
								specialty = specialty.substring(0, 50);
							// 学历
							String culturename = "";
							if (element.nextElementSibling().nextElementSibling().nextElementSibling() != null)
								culturename = element.nextElementSibling().nextElementSibling().nextElementSibling().text();
							Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturename);
							if (culture == null)
								culture = Constance.VALID_NO;
							// 专业描述
							String description = "";
							if (element.parent().nextElementSibling() != null)
								description = element.parent().nextElementSibling().select("td[id=Cur_Val]").text();
							if (StringUtils.isEmpty(description))
								description = "无";
							if (description.length() > 500)
								description = description.substring(0, 500);
							String rmk = null;
							if (element.parent().nextElementSibling() != null)
								rmk = element.parent().nextElementSibling().select("td").text();
							if (StringUtils.isNotEmpty(rmk)) {
								if (rmk.length() > 500)
									rmk = rmk.substring(0, 500);
							}

							EducationExperience model = new EducationExperience();
							// 时间
							String arrdates = element.text();
							String startdate = "";
							String enddate = "";
							if (arrdates.length() > 0) {
								String[] arrdate = arrdates.split("--");
								//只取数字和/
								startdate = arrdate[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								enddate = arrdate[1] + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setEducationexperienceguid(UUIDGenerator.randomUUID());
							model.setWebuserguid(resume.getWebuserguid());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setSpecialty(specialty);
							model.setCulture(culture);
							model.setMajordescription(description);
							model.setSchool(school);
							model.setRmk(rmk);
							list.add(model);
						}
						for (EducationExperience model : list) {
							resumeDao.insertEducationExperience(model);
						}
					}else if (them.equals("培训经历")){
						// 培训经历 {
						// 教育经验先删除后插入
						resumeDao.delTrainingExperienceByWebuserId(resume.getWebuserguid());
						List<TrainingExperience> list = new ArrayList<TrainingExperience>();
						Elements trainingexperiencetable = thems.get(j).parent().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().select("table[class=table_set]");
						Elements trainingexperience = trainingexperiencetable.select("td[class=text_left][width=20%]");
						for (Element element : trainingexperience) {

							// 机构
							String trainunit = null;
							if (element.nextElementSibling() != null)
								trainunit = element.nextElementSibling().text();
							if (StringUtils.isEmpty(trainunit))
								trainunit = "无";
							if (trainunit.length() > 50)
								trainunit = trainunit.substring(0, 50);
							// 内容
							String content = null;
							if (element.nextElementSibling().nextElementSibling() != null)
								content = element.nextElementSibling().nextElementSibling().text();
							if (StringUtils.isEmpty(content))
								content = "无";
							if (content.length() > 500)
								content = content.substring(0, 500);
							// 证书
							String certificate = null;
							if (element.nextElementSibling().nextElementSibling().nextElementSibling() != null)
								certificate = element.nextElementSibling().nextElementSibling().nextElementSibling().text();
							if (StringUtils.isEmpty(certificate))
								certificate = "无";
							if (certificate.length() > 50)
								certificate = certificate.substring(0, 50);
							String rmk = null;
							if (element.nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling() != null)
								rmk = element.nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().text();
							if (StringUtils.isEmpty(rmk))
								rmk = "无";
							if (rmk.length() > 500)
								rmk = rmk.substring(0, 500);
							TrainingExperience model = new TrainingExperience();
							// 时间
							String dates = element.text();
							if (dates.length() > 0) {
								String[] arrdate = dates.split("--");
								String startdate = "";
								//只取数字
								startdate = arrdate[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								String enddate = arrdate[1].replaceAll("：", "") + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setTrainingexperienceguid(UUIDGenerator.randomUUID());
							model.setWebuserguid(resume.getWebuserguid());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setCertificate(certificate);
							model.setTrainingcontent(content);
							model.setTraininginstitutions(trainunit);
							model.setRmk(rmk);
							list.add(model);
						}
						for (TrainingExperience model : list) {
							resumeDao.insertTrainingExperience(model);
						}
					}
				}
			}

			//if(eamilguid != null){
				// 应聘信息
				Elements mycandidates = doc.select("body>table>tbody>tr>td>table>tbody>tr>td[align=left][class=blue1][valign=top]");
				if (!mycandidates.isEmpty()) {
					List<MyCandidates> tmpList = myCandidatesDao.getMyCandidatesByWebUserGuidAndState(user.getWebuserguid(), Constance.CandidatesState_Blacklist);
					// 实例化
					MyCandidates model = new MyCandidates();
					model.setMycandidatesguid(UUIDGenerator.randomUUID());
					model.setWebuserguid(user.getWebuserguid());
					model.setCandidatesstate(tmpList.isEmpty() ? Constance.CandidatesState_One : Constance.CandidatesState_Blacklist);
					model.setCandidatestype(Constance.User5);
					model.setProgress(Constance.VALID_NO);
					model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					model.setResumeeamilguid(eamilguid);
					model.setReadtype(Constance.VALID_NO);

					// 应聘职位
					org.jsoup.nodes.Element postnameel = mycandidates.get(0);
					String postname = postnameel.text();

					// 保存职位名称
					model.setRecruitpostname(postname);
					
					// 職位
					List<RecruitPost> list = recruitmentDao.getRecruitPostByRecruitPostName(postname);
					model.setPostname(postname);
					if (!list.isEmpty()) {
						RecruitPost recruitPost = list.get(0);
						model.setRecruitpostguid(recruitPost.getRecruitpostguid());
					} else {
						model.setRecruitpostname(postname);
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
					org.jsoup.nodes.Element candidatestime = mycandidates.get(2);
					model.setCandidatestime(DateUtil.parse(candidatestime.text()));
					myCandidatesDao.insertMyCandidates(model);

					// 回傳應聘
					// user.setMycandidatesguid(model.getMycandidatesguid());

					// 回傳應聘
					// user.setMycandidatesguid(model.getMycandidatesguid());

				}
			//}
			return user;
		}
		return null;
	}

	/**
	 * 简历解析模板二
	 * 
	 * @param nameConvertCodeService
	 * @param sqlSession
	 * @param html
	 * @param eamilguid
	 * @return
	 */
	@Transactional
	public WebUser saveAnalysisResumeModel_2(NameConvertCodeService nameConvertCodeService, SqlSession sqlSession, org.jsoup.nodes.Document doc, String eamilguid) {
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		// 简历信息
		Elements links = doc.select("body>table");
		if (links.size() < 2) {
			System.out.println("模板不对请检查!");
			return null;
		}
		// 开始解析
		if (!links.isEmpty()) {

			org.jsoup.nodes.Element root = links.get(1);

			// 用户基本信息
			Elements table100 = root.select("table[border=0][cellpadding=0][cellspacing=0][width=100%]");
			if (table100.isEmpty())
				return null;
			org.jsoup.nodes.Element users = table100.get(0);

			// 外網用戶
			WebUser user = null;

			// 姓名
			Elements strong = root.select("strong");
			if (strong.isEmpty())
				return null;
			org.jsoup.nodes.Element nameel = strong.get(0);
			String name = nameel.text();

			// 电话,E-mail
			Elements te = users.select("td[colspan=3][height=20]");
			if (!te.isEmpty() && te.size() >= 2) {
				int num = te.size();
				String mobile = null;
				String email = null;
				if (num == 2) {
					// 电话
					mobile = te.get(0).text();
					if (mobile != null && mobile.length() > 4) {
						mobile = mobile.substring(0, mobile.length() - 4);
						// System.out.println("电话：===" + mobile);
					}

					// 邮件
					email = te.get(1).text();
					// System.out.println("邮件：===" + email);
					if (StringUtils.isEmpty(email))
						return null;
				} else {
					// 电话
					mobile = te.get(1).text();
					if (mobile != null && mobile.length() > 4) {
						mobile = mobile.substring(0, mobile.length() - 4);
						// System.out.println("mobile：===" + mobile);
					}
					
					//只取数字
					if(mobile != null){
					String regEx="[^0-9]";
					Pattern p = Pattern.compile(regEx);
					Matcher m = p.matcher(mobile);
					mobile = m.replaceAll("").trim();
					}
					// 邮件
					email = te.get(2).text();
					// System.out.println("邮件：===" + email);
					if (StringUtils.isEmpty(email))
						return null;
				}

				// 保存到本地
				user = webUserDao.checkWebUserByEmail(null, email);
				if (user == null) {
					//System.out.println("模板三：user==null");
					// 保存外网用户
					user = new WebUser(email, name);
					user.setWebuserguid(UUIDGenerator.randomUUID());
					
					// System.out.println(user.toString());
					webUserDao.insertWebUser(user);
				}
				user.setMobile(mobile);
			}

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
			if (user.getUsername().length() > 25)
				resume.setName(user.getUsername().substring(0, 25));
			else
				resume.setName(user.getUsername());
			// 电话
			resume.setMobile(user.getMobile());
			// 邮件
			resume.setEmail(user.getEmail());

			// 性别,出生日期
			Elements sexday = users.select("b");
			String[] array = sexday.text().split("\\|");
			// System.out.println("-----------" + sexday.text());
			// 性别
			if (array.length >= 1) {
				String sexel = array[1];
				String sexname = sexel.replaceAll(" ", "");
				// 数据翻译
				// System.out.println("性别：===" + sexname);
				 if (sexel.length() == 4) {
						sexname = sexel.substring(2, 3).replaceAll(" ", "");
					}
					if (sexel.length() == 2) {
						sexname = sexel.replaceAll(" ", "");
					}
					//System.out.println("====模板二性别1111===="+sexname);
				Integer sex = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.SEX, sexname);
				if (sex != null) {
					resume.setSexname(sexname);
					resume.setSex(sex);
				} else {
					resume.setSex(Constance.VALID_NO);
				}
			}
			//System.out.println("====模板二性别2222===="+resume.getSex());

			// 出生日期
			if (array.length >= 2) {
				String birthday = array[2];
				birthday = birthday.substring(birthday.indexOf("(") + 1, birthday.indexOf(")"));
				String[] birthdays = birthday.split("/");
				if (birthdays.length > 1) {
					birthday = birthday.replace(" ", "");
					resume.setBirthday(DateUtil.parseEnglish(birthday));
				} else {
					birthday = birthday.replace(" ", "");
					resume.setBirthday(DateUtil.parseChina(birthday));
				}
			}
			
			//人才库
			//if(eamilguid == null)
				resume.setMark(Constance.VALID_NO);
			
			// 居住地
			Elements address = users.select("td[height=20][width=10%]+td");
			if (address.text().isEmpty()) {
				resume.setHomeplace("无");
				// System.out.println("居住地：===" + address.text());
			} else {
				resume.setHomeplace(address.text());
				// System.out.println("居住地：===" + address.text());
			}

			// 最高学历
			Elements culturehtm = root.select("td[colspan=2][width=49%] td[width=230]");
			if (!culturehtm.isEmpty()) {
				String culturename = culturehtm.text();
				// 数据库翻译
				Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturename);
				if (culture != null) {
					resume.setCulturename(culturename);
					resume.setCulture(culture);
				} else
					resume.setCulture(Constance.VALID_NO);
			} else
				resume.setCulture(Constance.VALID_NO);

			// 工作年限(选项)
			if (array.length > 0) {
				String workagename = array[0];
				workagename = workagename.replaceAll(" ", "");
				if (StringUtils.isNotEmpty(workagename)) {
					workagename = workagename.substring(0, workagename.length() - 4);
					// System.out.println("======模板二工作年限======"+workagename);
					Integer workage = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.WORKAGE, workagename);
					if (workage != null) {
						resume.setWorkagename(workagename);
						resume.setWorkage(workage);
					} else {
						if (workagename.equals("应")) {
							// System.out.println("====应届毕业生");
							workagename = "应届毕业生";
							workage = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.WORKAGE, workagename);
							if (workage != null) {
								resume.setWorkagename(workagename);
								resume.setWorkage(workage);
							} else
								resume.setWorkage(Constance.VALID_NO);
						} else {
							// System.out.println("=====在读学生====");
							workagename = "在读学生";
							workage = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.WORKAGE, workagename);
							if (workage != null) {
								resume.setWorkagename(workagename);
								resume.setWorkage(workage);
							} else
								resume.setWorkage(Constance.VALID_NO);
						}
						if (resume.getWorkage() == null)
							resume.setWorkage(Constance.VALID_NO);
					}
				}
			}
			// 解析
			Elements info = root.select("div[class=v3_t]");
			//自我评价
			String valuation = "";
			//行业
			String industry = "";
			//工资
			String salary = "";
			//职位
			String occupation = "";
			for (int i = 0; i < info.size(); i++) {
				int j = 1;
				String[] them = info.get(i).text().split(" ");// 数组去掉前后空格
				if (them.length <= 1)
					j = 0;
				Element workexperiencetable = root.select("table[class=v_table03]").get(i);
				if (them[j].equals("自我评价")) {
					valuation = workexperiencetable.select("td[ id=Cur_Val][valign=top]").text();
					if(valuation.length() > 500)
						valuation = valuation.substring(0, 500);
				}else if(them[j].equals("求职意向")){
					if(workexperiencetable.select("td[class=weight110][valign=top]") != null){
						Elements elements = workexperiencetable.select("td[class=weight110][valign=top]");
						for(Element element : elements){
							String childname = element.text().replaceAll(" ", "");
							if(childname.equals("希望行业：")){
								industry = element.nextElementSibling().text();
								if(industry.length() > 50)
									industry = industry.substring(0, 50);
							}else if(childname.equals("期望工资：")){
								salary = element.nextElementSibling().text();
								if(salary.length() > 50)
									salary = salary.substring(0, 50);
							}else if(childname.equals("目标职能：")){
								occupation = element.nextElementSibling().text();
								if(occupation.length() > 100)
									occupation = occupation.substring(0, 100);
							}
						}
					}
				}
			}
			if(StringUtils.isEmpty(valuation))
				valuation = "无";
			if(StringUtils.isEmpty(industry))
				industry = "无";
			if(StringUtils.isEmpty(salary))
				salary = "无";
			if(StringUtils.isEmpty(occupation))
				occupation = "无";
			resume.setValuation(valuation);
			resume.setIndustry(industry);
			resume.setSalary(salary);
			resume.setOccupation(occupation);
			resume.setSituation("无");

			// System.out.println(resume.toString());
			// 保存简历
			if (isnew)
				resumeDao.insertResume(resume);
			else
				resumeDao.updateResume(resume);
			// 年薪
			// Elements salary = root.select("td[width=188]");
			// System.out.println("年薪：" + salary.text());

			// 应界生和社会招聘
			// [ 应届生简历/无工作经验 ]
			Elements typeel = root.select("span[style=color:#676767;]");
			String type = typeel.text();
			// System.out.println("应界生和社会招聘：===" + type);
			boolean resumeState = type.equals("[ 应届生简历/无工作经验 ]");

			if (table100.isEmpty())
				return null;
			if (info.isEmpty() && info.size() <= 0)
				return null;

			// 社会实践
			if (resumeState) {

				for (int i = 0; i < info.size(); i++) {
					int j = 1;
					String[] them = info.get(i).text().split(" ");// 数组去掉前后空格
					if (them.length <= 1)
						j = 0;
					if (them[j].equals("工作经验")) {
						// 社会实践先删除后插入
						resumeDao.delWorkExperienceByWebuserId(resume.getWebuserguid());
						// 工作经验
						// System.out.println("=========工作经验=========");
						/*
						 * 当前table
						 * */
						Element workexperiencetable = root.select("table[class=v_table03]").get(i);
						/*
						 * 以td[colspan=2][valign=top][id!=Cur_Val]为起点开始解析
						 * */
						Elements tmpcont = workexperiencetable.select("td[colspan=2][valign=top][id!=Cur_Val]");
						Elements cont = new Elements();
						/*
						 * 只取td[colspan=2][valign=top][id!=Cur_Val]下无节点的td数
						 * */
						for (Element ele : tmpcont) {
							if (ele.children().size() <= 0) {
								cont.add(ele);
							}
						}
						List<WorkExperience> list = new ArrayList<WorkExperience>();
						for (Element element : cont) {
							// System.out.println("................................................................................"+element);
							String firm = element.text();
							// System.out.println("====时间和单位===="+firm);
							String[] ls = firm.split("：");
							if (ls.length > 0) {
								// 职位
								String posationname = "";
								// 实例化
								WorkExperience model = new WorkExperience();
								// 职位
								if(element.parent().nextElementSibling().nextElementSibling() != null){
								if (element.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top]").text().length() == 0) {
									posationname = "无";
								} else {
									if (element.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top]") != null
											&& element.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top]").text().length() > 0) {
										/*
										 * 获取td[vAlign=top]下第二个值
										 * */
										if (element.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top]").size() > 1)
											posationname = element.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top]").select("strong").get(1).text();
									}
								}
							}
								// 职责描述
								String content = "";
								/*
								 *以td[colspan=2][valign=top][id!=Cur_Val]为起点开始解析td[id=Cur_Val][colspan=2][valign=top]
								 *当前标签不存在td[id=Cur_Val][colspan=2][valign=top]，则向下一个标签寻找
								 **/
								if (element.parent().nextElementSibling() != null && element.parent().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() == 0) {
									if (element.parent().nextElementSibling().nextElementSibling() != null
											&& element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() == 0) {
										if (element.parent().nextElementSibling().nextElementSibling().nextElementSibling() != null
												&& element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() > 0) {
											content = element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
										}
									} else {
										if(element.parent().nextElementSibling().nextElementSibling() != null)
											content = element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
									}
								} else {
									if(element.parent().nextElementSibling() != null)
										content = element.parent().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
								}
								model.setWorkexperienceguid(UUIDGenerator.randomUUID());
								model.setModtime(new Timestamp(System.currentTimeMillis()));
								model.setWebuserguid(resume.getWebuserguid());
								// 职责描述
								if (StringUtils.isEmpty(content))
									content = "无";
								if (content.length() > 500)
									content = content.substring(0, 500);
								model.setJobdescription(content);

								// 工作单位
								if (StringUtils.isNotEmpty(ls[1])){
									if (ls[1].length() > 50)
										model.setWorkunit(ls[1].substring(0, 50));
									else
										model.setWorkunit(ls[1]);
								}else
									model.setWorkunit("无");
								if (StringUtils.isEmpty(posationname))
									posationname = "无";
								if (posationname.length() > 50)
									posationname = posationname.substring(0, 50);
								model.setPosation(posationname);
								// 开始时间
								String datese = ls[0];
								if (datese.length() > 0) {
									String[] datels = datese.split("--");
									String startdate = "";
									startdate = datels[0].replaceAll(" ", "") + "/01";
									String str="[^0-9&&[^/]]";
									Pattern pattern = Pattern.compile(str);
									Matcher matcher = pattern.matcher(startdate);
									startdate = matcher.replaceAll("").trim();
									String enddate = datels[1] + "/01";
									// System.out.println("====工作经历结束时间===="+enddate);
									model.setStartdate(DateUtil.parseEnglish(startdate));
									if (model.getStartdate() == null)
										model.setStartdate(new Date(System.currentTimeMillis()));
									model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
								} else {
									model.setStartdate(new Date(System.currentTimeMillis()));
									model.setEnddate(new Date(System.currentTimeMillis()));
								}
								list.add(model);
							}
						}
						// 保存工作经历
						for (WorkExperience model : list) {
							resumeDao.insertWorkExperience(model);
						}
					}else if (them[j].equals("教育经历")) {
						// 教育经历
						// System.out.println("=========教育经历=========");
						// 先删除后插入
						resumeDao.delEducationExperienceByWebuserId(resume.getWebuserguid());
						// 数据
						List<EducationExperience> list = new ArrayList<EducationExperience>();
						Element studyexperiencetable = root.select("table[class=v_table03]").get(i);
						Elements educationelements = studyexperiencetable.select("td[ class=weight180][vAlign=top]");
						for (Element element : educationelements) {

							// System.out.println("时间：" + date);
							String school = "";
							if(element.nextElementSibling() != null && element.parent().select("td[class=weight220 padding10]") != null)
								school = element.nextElementSibling().text();
							// System.out.println("学校：" + school);
							String major = "";
							if(element.nextElementSibling().nextElementSibling() != null && element.parent().select("td[class=padding10]") != null)
								major = element.nextElementSibling().nextElementSibling().text();
							// System.out.println("专业：" + major);
							String level = "";
							if(element.nextElementSibling().nextElementSibling().nextElementSibling() != null && element.parent().select("td[class=weight60]") != null)
							level = element.nextElementSibling().nextElementSibling().nextElementSibling().text();
							// System.out.println("学历：" + level);
							String majordescription = null;
							if (element.parent().nextElementSibling() == null)
								majordescription = "无";
							else
								majordescription = element.parent().nextElementSibling().select("td[id=Cur_Val][colspan=4][valign=top]").text();
							// System.out.println("专业描述：" + majordescription);

							// 实例化
							EducationExperience model = new EducationExperience();
							// 开始时间
							String date = element.text();
							String startdate = "";
							String enddate = "";
							if (date.length() > 0) {
								String[] datels = date.split("--");
								startdate = datels[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								enddate = datels[1] + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}

							model.setEducationexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());
							// 数据库翻译
							Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, level);
							if (culture == null)
								culture = Constance.VALID_YES;
							model.setCulture(culture);
							if (StringUtils.isEmpty(level))
								level = "无";
							model.setCulturename(level);
							if (StringUtils.isEmpty(school))
								school = "无";
							if (school.length() > 50)
								school = school.substring(0, 50);
							model.setSchool(school);
							if (StringUtils.isEmpty(major))
								major = "无";
							if (major.length() > 50)
								major = major.substring(0, 50);
							model.setSpecialty(major);
							model.setMajordescription(majordescription);
							list.add(model);
						}
						// 保存教育经历
						for (EducationExperience model : list) {
							resumeDao.insertEducationExperience(model);
						}

					}else if (them[j].equals("项目经验")) {
						// System.out.println("==================================");
						// System.out.println("==========项目经验 =============");
						// System.out.println("==================================");
						List<ProjectExperience> list = new ArrayList<ProjectExperience>();
						resumeDao.delProjectExperienceByWebuserId(resume.getWebuserguid());
						Element projectexperiencetable = root.select("table[class=v_table03]").get(i);
						Elements nodes = projectexperiencetable.select("td[class=weight110][ vAlign=top]");
						// System.out.println("========项目经验 ======"+projectexperience);
						for (Element element : nodes) {

							// 项目名称
							String itemname = "";
							if(element.nextElementSibling() != null)
								itemname = element.nextElementSibling().text();
							if (StringUtils.isEmpty(itemname))
								itemname = "无";
							if (itemname.length() > 50)
								itemname = itemname.substring(0, 50);
							String content = "";
							//项目描述
							/*
							 *以td[class=weight110][ vAlign=top]为起点开始解析td[id=Cur_Val][valign=top]
							 *当前标签不存在td[id=Cur_Val][valign=top]，则向下一个标签寻找
							 **/
							if (element.parent().nextElementSibling() != null && element.parent().nextElementSibling().select("td[id=Cur_Val][valign=top]").text().length() == 0) {
								if (element.parent().nextElementSibling().nextElementSibling() != null
										&& element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text().length() == 0) {
									if (element.parent().nextElementSibling().nextElementSibling().nextElementSibling() != null
											&& element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text().length() == 0) {
										content = element.parent().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
									} else {
										if (element.parent().nextElementSibling().nextElementSibling().nextElementSibling() != null)
											content = element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
									}
								} else {
									if (element.parent().nextElementSibling().nextElementSibling() != null)
										content = element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
								}
							} else {
								if (element.parent().nextElementSibling() != null)
									content = element.parent().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
							}

							// 职责描述
							if (StringUtils.isEmpty(content))
								content = "无";
							if (content.length() > 500)
								content = content.substring(0, 500);

							ProjectExperience model = new ProjectExperience();
							// 时间
							String startdate = "";
							String enddate = "";
							if (element.text().length() > 0) {
								String[] date = element.text().split("--");
								//只取数字
								startdate = date[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								enddate = date[1].replaceAll("：", "") + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setProjectexperienceguid(UUIDGenerator.randomUUID());
							model.setWebuserguid(resume.getWebuserguid());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setItemname(itemname);

							model.setJobdescription(content);
							list.add(model);
						}
						for (ProjectExperience model : list) {
							resumeDao.insertProjectExperience(model);
						}
					}else if (them[j].equals("培训经历")) {
						// 培训经历
						// System.out.println("=========培训经历=========");
						resumeDao.delTrainingExperienceByWebuserId(resume.getWebuserguid());
						Element traintable = root.select("table[class=v_table03]").get(i);
						Elements trainelements = traintable.select("td[class=weight110][vAlign=top]");
						List<TrainingExperience> list = new ArrayList<TrainingExperience>();
						for (Element element : trainelements) {

							// System.out.println("时间：" + date);
							//培训机构
							String organization = "";
							//培训内容
							String content = "";
							if(element.nextElementSibling() != null && element.parent().select("td[class=weight220 padding10]") != null){
								Elements tdelements = element.parent().select("td[class=weight220 padding10]");
								if(tdelements.size() > 0){
									if(tdelements.size() > 1){
										organization = tdelements.get(0).text();
										content = tdelements.get(1).text();
									}else
										organization = tdelements.get(0).text();
								}
							}
							String credentials = "";
							if(element.nextElementSibling().nextElementSibling().nextElementSibling() != null && element.parent().select("td[style=width:80px]") != null)
							credentials = element.nextElementSibling().nextElementSibling().nextElementSibling().text();
							// System.out.println("培训证书：" + credentials);

							// 实例化
							TrainingExperience model = new TrainingExperience();
							// 开始时间
							String date = element.text();
							if (date.length() > 0) {
								String[] datels = date.split("--");
								String startdate = "";
								//只取数字
								startdate = datels[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								String enddate = datels[1] + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}

							model.setTrainingexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());
							if (StringUtils.isNotEmpty(organization))
								if (organization.length() > 50)
									model.setTraininginstitutions(organization.substring(0, 50));
								else
									model.setTraininginstitutions(organization);
							else
								model.setTraininginstitutions("无");
							if (StringUtils.isNotEmpty(content))
								if (content.length() > 500)
									model.setTrainingcontent(content.substring(0, 500));
								else
									model.setTrainingcontent(content);
							else
								model.setTrainingcontent("无");
							if (StringUtils.isNotEmpty(credentials))
								if (credentials.length() > 50)
									model.setCertificate(credentials.substring(0, 50));
								else
									model.setCertificate(credentials);
							else
								model.setCertificate("无");
							list.add(model);
						}
						for (TrainingExperience model : list) {
							resumeDao.insertTrainingExperience(model);
						}
					}
				}
			} else {
				for (int i = 0; i < info.size(); i++) {
					String[] them = info.get(i).text().split(" ");// 数组去掉前后空格
					int j = 1;
					if (them.length <= 1)
						j = 0;
					if (them[j].equals("工作经验")) {
						// 社会实践先删除后插入
						resumeDao.delWorkExperienceByWebuserId(resume.getWebuserguid());
						// 工作经验
						// System.out.println("=========工作经验=========");
						/*
						 * 当前table
						 * */
						Element workexperiencetable = root.select("table[class=v_table03]").get(i);
						// Elements cont =
						// workexperiencetable.select("td[id=Cur_Val][colspan=2][valign=top]");
						/*
						 * 以td[colspan=2][valign=top][id!=Cur_Val]为起点解析
						 * */
						Elements tmpcont = workexperiencetable.select("td[colspan=2][valign=top][id!=Cur_Val]");
						Elements cont = new Elements();
						for (Element ele : tmpcont) {
							if (ele.children().size() <= 0) {
								cont.add(ele);
							}
						}
						List<WorkExperience> list = new ArrayList<WorkExperience>();
						for (Element element : cont) {
							// System.out.println("................................................................................"+element);
							String firm = element.text();
							// System.out.println("====时间和单位===="+firm);
							String[] ls = firm.split("：");
							if (ls.length > 0) {
								// 职位
								String posationname = "";
								// 实例化
								WorkExperience model = new WorkExperience();
								// 职位
								if(element.parent().nextElementSibling().nextElementSibling() != null){
								if (element.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top]").text().length() == 0) {
									posationname = "无";
								} else {
									if (element.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top]") != null
											&& element.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top]").text().length() > 0) {
										/*
										 * 如果存在，则解析element.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top]")
										 * 下的第二个strong标签里的值
										 * */
										if (element.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top]").size() > 1)
											posationname = element.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top]").select("strong").get(1).text();
									}else
										posationname = "无";
								}
							}
								// 职责描述
								String content = "";
								/*
								 *以td[colspan=2][valign=top][id!=Cur_Val]为起点开始解析td[id=Cur_Val][colspan=2][valign=top]
								 *当前标签不存在td[id=Cur_Val][colspan=2][valign=top]，则向下一个标签寻找
								 **/
								if (element.parent().nextElementSibling() != null && element.parent().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() == 0) {
									if (element.parent().nextElementSibling().nextElementSibling() != null
											&& element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() == 0) {
										if (element.parent().nextElementSibling().nextElementSibling().nextElementSibling() != null
												&& element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() > 0) {
											content = element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
										}
									} else {
										if(element.parent().nextElementSibling().nextElementSibling() != null)
											content = element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
									}
								} else {
									if(element.parent().nextElementSibling() != null)
										content = element.parent().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
								}
								model.setWorkexperienceguid(UUIDGenerator.randomUUID());
								model.setModtime(new Timestamp(System.currentTimeMillis()));
								model.setWebuserguid(resume.getWebuserguid());
								// 职责描述
								if (StringUtils.isEmpty(content))
									content = "无";
								if (content.length() > 500)
									content = content.substring(0, 500);
								model.setJobdescription(content);

								// 工作单位
								if (StringUtils.isNotEmpty(ls[1])){
									if (ls[1].length() > 50)
										model.setWorkunit(ls[1].substring(0, 50));
									else
										model.setWorkunit(ls[1]);
								}else
									model.setWorkunit("无");
								if (StringUtils.isEmpty(posationname))
									posationname = "无";
								if (posationname.length() > 50)
									posationname = posationname.substring(0, 50);
								model.setPosation(posationname);
								// 开始时间
								String datese = ls[0];
								if (datese.length() > 0) {
									String[] datels = datese.split("--");
									String startdate = "";
									startdate = datels[0].replaceAll(" ", "") + "/01";
									//System.out.println("====工作经验开始时间1111===="+startdate);
									String str="[^0-9&&[^/]]";
									Pattern pattern = Pattern.compile(str);
									Matcher matcher = pattern.matcher(startdate);
									startdate = matcher.replaceAll("").trim();
									//System.out.println("====工作经验开始时间222===="+startdate);
									String enddate = datels[1] + "/01";
									// System.out.println("====工作经历结束时间===="+enddate);
									model.setStartdate(DateUtil.parseEnglish(startdate));
									if (model.getStartdate() == null)
										model.setStartdate(new Date(System.currentTimeMillis()));
									model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
								} else {
									model.setStartdate(new Date(System.currentTimeMillis()));
									model.setEnddate(new Date(System.currentTimeMillis()));
								}
								list.add(model);
							}
						}
						for (WorkExperience model : list) {
							//System.out.println(model.toString());
							resumeDao.insertWorkExperience(model);
						}
					}else if (them[j].equals("教育经历")) {
						// 教育经历
						// System.out.println("=========教育经历=========");
						// 先删除后插入
						resumeDao.delEducationExperienceByWebuserId(resume.getWebuserguid());
						// 数据
						List<EducationExperience> list = new ArrayList<EducationExperience>();
						Element studyexperiencetable = root.select("table[class=v_table03]").get(i);
						Elements educationelements = studyexperiencetable.select("td[ class=weight180][vAlign=top]");
						for (Element element : educationelements) {

							// System.out.println("时间：" + date);
							String school = "";
							if(element.nextElementSibling() != null && element.parent().select("td[class=weight220 padding10]") != null)
								school = element.nextElementSibling().text();
							// System.out.println("学校：" + school);
							String major = "";
							if(element.nextElementSibling().nextElementSibling() != null && element.parent().select("td[class=padding10]") != null)
								major = element.nextElementSibling().nextElementSibling().text();
							// System.out.println("专业：" + major);
							String level = "";
							if(element.nextElementSibling().nextElementSibling().nextElementSibling() != null && element.parent().select("td[class=weight60]") != null)
							level = element.nextElementSibling().nextElementSibling().nextElementSibling().text();
							// System.out.println("学历：" + level);
							String majordescription = null;
							if (element.parent().nextElementSibling() == null)
								majordescription = "无";
							else
								majordescription = element.parent().nextElementSibling().select("td[id=Cur_Val][colspan=4][valign=top]").text();
							// System.out.println("专业描述：" + majordescription);

							// 实例化
							EducationExperience model = new EducationExperience();
							// 开始时间
							String date = element.text();
							if (date.length() > 0) {
								String[] datels = date.split("--");
								String startdate = "";
								//只取数字
								startdate = datels[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								String enddate = datels[1] + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setEducationexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());
							// 数据库翻译
							Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, level);
							if (culture == null)
								culture = Constance.VALID_YES;
							model.setCulture(culture);
							if (StringUtils.isEmpty(level))
								level = "无";
							model.setCulturename(level);
							if (StringUtils.isEmpty(school))
								school = "无";
							if (school.length() > 50)
								school = school.substring(0, 50);
							model.setSchool(school);
							if (StringUtils.isEmpty(major))
								major = "无";
							if (major.length() > 50)
								major = major.substring(0, 50);
							if (majordescription.length() > 500)
								majordescription = majordescription.substring(0, 500);
							model.setSpecialty(major);
							model.setMajordescription(majordescription);
							list.add(model);
						}
						// 保存教育经历
						for (EducationExperience model : list) {
							resumeDao.insertEducationExperience(model);
						}

					}else if (them[j].equals("项目经验")) {
						// System.out.println("==================================");
						// System.out.println("==========项目经验 =============");
						// System.out.println("==================================");
						List<ProjectExperience> list = new ArrayList<ProjectExperience>();
						resumeDao.delProjectExperienceByWebuserId(resume.getWebuserguid());
						Element projectexperiencetable = root.select("table[class=v_table03]").get(i);
						Elements nodes = projectexperiencetable.select("td[class=weight110][ vAlign=top]");
						// System.out.println("========项目经验 ======"+projectexperience);
						for (Element element : nodes) {

							// System.out.println("====项目经验结束时间===="+enddate);
							// 项目名称
							String itemname  = "";
							if(element.nextElementSibling() != null)
								itemname = element.nextElementSibling().text();
							if (StringUtils.isEmpty(itemname))
								itemname = "无";
							if (itemname.length() > 50)
								itemname = itemname.substring(0, 50);
							// 职责描述
							// Elements jobdescription =
							// projectexperiencetable.select("td[id=Cur_Val][valign=top]");
							// System.out.println("====职责描述===="+jobdescription);
							String content = "";
							/*
							 * 以td[id=Cur_Val][colspan=4][valign=top]向下解析td[id=Cur_Val][valign=top]
							 * 当前标签不存在td[id=Cur_Val][valign=top]则向下一个标签解析
							 * */
							if (element.parent().nextElementSibling() != null && element.parent().nextElementSibling().select("td[id=Cur_Val][valign=top]").text().length() == 0) {
								if (element.parent().nextElementSibling().nextElementSibling() != null
										&& element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text().length() == 0) {
									if (element.parent().nextElementSibling().nextElementSibling().nextElementSibling() != null
											&& element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text().length() == 0) {
										content = element.parent().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
									} else {
										if (element.parent().nextElementSibling().nextElementSibling().nextElementSibling() != null)
											content = element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
									}
								} else {
									if (element.parent().nextElementSibling().nextElementSibling() != null)
										content = element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
								}
							} else {
								if (element.parent().nextElementSibling() != null)
									content = element.parent().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
							}
							if (StringUtils.isEmpty(content))
								content = "无";
							if (content.length() > 500)
								content = content.substring(0, 500);

							ProjectExperience model = new ProjectExperience();
							if (element.text().length() > 0) {
								String[] date = element.text().split("--");
								String startdate = "";
								//只取数字和/
								startdate = date[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								String enddate = date[1].replaceAll("：", "") + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setProjectexperienceguid(UUIDGenerator.randomUUID());
							model.setWebuserguid(resume.getWebuserguid());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setItemname(itemname);
							model.setJobdescription(content);
							list.add(model);
						}
						for (ProjectExperience model : list) {
							resumeDao.insertProjectExperience(model);
						}
					}else if (them[j].equals("培训经历")) {
						// 培训经历
						// System.out.println("=========培训经历=========");
						resumeDao.delTrainingExperienceByWebuserId(resume.getWebuserguid());
						Element traintable = root.select("table[class=v_table03]").get(i);
						Elements trainelements = traintable.select("td[class=weight110][vAlign=top]");
						List<TrainingExperience> list = new ArrayList<TrainingExperience>();
						for (Element element : trainelements) {

							// System.out.println("时间：" + date);
							//培训机构
							String organization = "";
							//培训内容
							String content = "";
							if(element.nextElementSibling() != null && element.parent().select("td[class=weight220 padding10]") != null){
								Elements tdelements = element.parent().select("td[class=weight220 padding10]");
								if(tdelements.size() > 0){
									if(tdelements.size() > 1){
										organization = tdelements.get(0).text();
										content = tdelements.get(1).text();
									}else
										organization = tdelements.get(0).text();
								}
							}
							String credentials = "";
							if(element.nextElementSibling().nextElementSibling().nextElementSibling() != null && element.parent().select("td[style=width:80px]") != null)
							credentials = element.nextElementSibling().nextElementSibling().nextElementSibling().text();
							// System.out.println("培训证书：" + credentials);

							// 实例化
							TrainingExperience model = new TrainingExperience();
							// 开始时间
							String date = element.text();
							if (date.length() > 0) {
								String[] datels = date.split("--");
								String startdate = "";
								//只取数字和/
								startdate = datels[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								String enddate = datels[1] + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setTrainingexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());
							if (StringUtils.isNotEmpty(organization))
								if (organization.length() > 50)
									model.setTraininginstitutions(organization.substring(0, 50));
								else
									model.setTraininginstitutions(organization);
							else
								model.setTraininginstitutions("无");
							if (StringUtils.isNotEmpty(content))
								if (content.length() > 500)
									model.setTrainingcontent(content.substring(0, 50));
								else
									model.setTrainingcontent(content);
							else
								model.setTrainingcontent("无");
							if (StringUtils.isNotEmpty(credentials))
								if (credentials.length() > 50)
									model.setCertificate(credentials.substring(0, 50));
								else
									model.setCertificate(credentials);
							else
								model.setCertificate("无");
							list.add(model);
						}
						for (TrainingExperience model : list) {
							resumeDao.insertTrainingExperience(model);
						}
					}
				}
			}
			//if(eamilguid != null){
				// 应聘信息
				Elements mycandidates = doc.select("body>table>tbody>tr>td>table>tbody>tr>td[align=left][class=blue1][valign=top]");
				if (!mycandidates.isEmpty()) {
					//System.out.println("模板二：webuserguid==="+user.toString());
					List<MyCandidates> tmpList = myCandidatesDao.getMyCandidatesByWebUserGuidAndState(user.getWebuserguid(), Constance.CandidatesState_Blacklist);
					// 实例化
					//System.out.println("tmpList.size==="+tmpList.size());
					MyCandidates model = new MyCandidates();
					model.setMycandidatesguid(UUIDGenerator.randomUUID());
					model.setWebuserguid(user.getWebuserguid());
					model.setCandidatesstate(tmpList.isEmpty() ? Constance.CandidatesState_One : Constance.CandidatesState_Blacklist);
					model.setCandidatestype(Constance.User5);
					model.setProgress(Constance.VALID_NO);
					model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					model.setResumeeamilguid(eamilguid);
					model.setReadtype(Constance.VALID_NO);

					// 应聘职位
					Element postnameel = mycandidates.get(0);
					String postname = postnameel.text();

					// 保存职位名称
					model.setRecruitpostname(postname);

					// 職位
					List<RecruitPost> list = recruitmentDao.getRecruitPostByRecruitPostName(postname);
					model.setPostname(postname);
					if (!list.isEmpty()) {
						RecruitPost recruitPost = list.get(0);
						model.setRecruitpostguid(recruitPost.getRecruitpostguid());
					} else {
						model.setRecruitpostname(postname);
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
					org.jsoup.nodes.Element candidatestime = mycandidates.get(2);
					model.setCandidatestime(DateUtil.parse(candidatestime.text()));
					myCandidatesDao.insertMyCandidates(model);
				}
			//}
			return user;
		}
		return null;
	}

	/**
	 * 简历解析模板三
	 * 
	 * @param nameConvertCodeService
	 * @param sqlSession
	 * @param html
	 * @param eamilguid
	 * @return
	 */
	@Transactional
	public WebUser saveAnalysisResumeModel_3(NameConvertCodeService nameConvertCodeService, SqlSession sqlSession, org.jsoup.nodes.Document doc, String eamilguid) {
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);

		// 简历信息
		Elements links = doc.select("body>table");
		if (links.size() < 2) {
			// System.out.println("模板不对请检查!");
			return null;
		}

		// 开始解析
		if (!links.isEmpty()) {
			org.jsoup.nodes.Element root = links.get(1);
			// 用户基本信息
			Elements userelements = root.select("table[width=97%]");
			if (userelements.isEmpty())
				return null;
			Element users = null;
			if (userelements.size() <= 0)
				return null;
			users = userelements.get(0);
			// System.out.println("===user==="+users);

			// 外網用戶
			WebUser user = null;
			// 姓名
			Elements strong = users.select("strong");
			Element strongs = null;
			if (strong.size() > 0)
				strongs = strong.get(0);
			// System.out.println("======用户姓名====="+strongs.text());
			String name = strongs.text();
			// System.out.println("姓名====" + name);

			// 电话,E-mail
			Elements te = users.select("td[colspan=3][height=20]");
			// System.out.println("电话,E-mail个数：" + te.size());
			
			if (!te.isEmpty()) {
				String mobile = null;
				String email = null;
				Element temp = te.get(0);
				// System.out.println("temp Element ==="+temp);
				if (te.size() > 2) {
					mobile = temp.parent().nextElementSibling().select("td[colspan=3][height=20]").text();
					// System.out.println("电话====" + mobile);
					email = temp.parent().nextElementSibling().nextElementSibling().select("td[colspan=3][height=20]").text();
					// System.out.println("邮件====" + email);
				} else {
					mobile = temp.text();
					email = temp.parent().nextElementSibling().select("td[colspan=3][height=20]").text();
				}
				
				//只取数字
				if(mobile != null){
				String regEx="[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(mobile);
				mobile = m.replaceAll("").trim();
				}
				// 保存到本地
				user = webUserDao.checkWebUserByEmail(null, email);
				if (user == null) {
					// 保存外网用户
					user = new WebUser(email, name);
					user.setWebuserguid(UUIDGenerator.randomUUID());
					
					// System.out.println(user.toString());
					webUserDao.insertWebUser(user);
				}
				user.setMobile(mobile);
				// System.out.println("电话邮件：" + te);

			}
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
			if (name.length() > 25) {
				name = name.substring(0, 25);
			}
			resume.setName(name);
			// 电话
			resume.setMobile(user.getMobile());
			// 邮件
			resume.setEmail(user.getEmail());

			// System.out.println("====姓名===="+name);
			// 性别,出生日期
			Elements sexday = users.select("b");
			String[] array = sexday.text().split("\\|");
			if (array.length >= 3) {
				// 工作年限(选项)
				String workagename = array[0];
				workagename = workagename.replaceAll(" ", "");
				if (StringUtils.isNotEmpty(workagename)) {
					workagename = workagename.substring(0, workagename.length() - 4);
					// System.out.println("======模板三工作年限======"+workagename);
					Integer workage = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.WORKAGE, workagename);
					if (workage != null) {
						resume.setWorkagename(workagename);
						resume.setWorkage(workage);
					} else {
						if (workagename.equals("应")) {
							// System.out.println("====应届毕业生");
							workagename = "应届毕业生";
							workage = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.WORKAGE, workagename);
							if (workage != null) {
								resume.setWorkagename(workagename);
								resume.setWorkage(workage);
							} else
								resume.setWorkage(Constance.VALID_NO);
						} else {
							// System.out.println("=====在读学生====");
							workagename = "在读学生";
							workage = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.WORKAGE, workagename);
							if (workage != null) {
								resume.setWorkagename(workagename);
								resume.setWorkage(workage);
							} else
								resume.setWorkage(Constance.VALID_NO);
						}
						if (resume.getWorkage() == null)
							resume.setWorkage(Constance.VALID_NO);
					}
				}
				// System.out.println("工作年限(选项)====" + workagename);

				// 性别
				String sexels = array[1];
				// String sexname = sexels.replaceAll("?", "");
				// System.out.println("====性别===="+sexels);
				// System.out.println("=====姓名===="+resume.getName());
				// System.out.println(sexels.length());
				String sexname = null;
				if (sexels.length() == 4) {
					sexname = sexels.substring(2, 3).replaceAll(" ", "");
				}
				if (sexels.length() == 2) {
					sexname = sexels.replaceAll(" ", "");
				}
				//System.out.println(sexels);
				//System.out.println("=============="+sexname);
				// 数据翻译
				Integer sex = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.SEX, sexname);
				// System.out.println("====sex===="+sex);
				if (sex != null) {
					resume.setSexname(sexname);
					resume.setSex(sex);
				} else {
					resume.setSex(Constance.VALID_NO);
				}
				// System.out.println("性别====" + sexname);

				// 出生日期
				String birthday = array[2];
				birthday = birthday.substring(birthday.indexOf("(") + 1, birthday.indexOf(")"));
				String[] birthdays = birthday.split("/");
				if (birthdays.length > 1) {
					birthday = birthday.replace(" ", "");
					resume.setBirthday(DateUtil.parseEnglish(birthday));
				} else {
					birthday = birthday.replace(" ", "");
					resume.setBirthday(DateUtil.parseChina(birthday));
				}
				// System.out.println("====出生日期===="
				// +DateUtil.parseChina(birthday));
			}
			
			//人才库
			//if(eamilguid == null)
				resume.setMark(Constance.VALID_NO);

			// 居住地
			Elements address = users.select("td[height=20][width=10%]+td");
			if (address.text().isEmpty()) {
				resume.setHomeplace("无");
			} else {
				resume.setHomeplace(address.text());
			}
			/*
			 * System.out.println("居住地：" + address.text());
			 * 
			 * System.out.println("==================================");
			 * System.out.println("==========最高学历=============");
			 * System.out.println("==================================");
			 */
			// 学历
			Elements culturehtm = root.select("td[colspan=2][width=49%]");
			Elements culturenames = culturehtm.select("table>tbody>tr>td[width=230]");
			Element culturehtmname = null;
			String culturename = null;
			if (culturenames.size() > 1) {
				culturehtmname = culturenames.get(0);
				culturename = culturehtmname.text();
			} else
				culturename = culturenames.text();
			if (!StringUtils.isEmpty(culturename)) {
				// 数据库翻译
				Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturename);
				if (culture != null) {
					resume.setCulturename(culturename);
					resume.setCulture(culture);
				} else
					resume.setCulture(Constance.VALID_NO);
			} else
				resume.setCulture(Constance.VALID_NO);
			
			
			
			//行业
			String industry = "";
			//自我评价
			String valuation = "";
			//薪水
			String salary = "";
			//职业
			String occupation = "";
			Elements themes = root.select("table[class=v_table02]");
			for (int i = 0; i < themes.size(); i++) {
				if (i == 0 && themes.get(i).previousElementSibling() == null)
					i = i + 1;
				if (i >= themes.size())
					i = i - 1;
				String tempname = themes.get(i).previousElementSibling().text();
				Element workexperiences = themes.get(i);
				if(tempname.equals("自我评价")){
					if(workexperiences.select("td[style=width:700px;][id=Cur_Val][valign=top]") != null){
						valuation = workexperiences.select("td[style=width:700px;][id=Cur_Val][valign=top]").text();
						if(valuation.length() > 500)
							valuation = valuation.substring(0, 500);
						//System.out.println("====自我评价===="+valuation);
					}
				}else if(tempname.equals("求职意向")){
					if(workexperiences.select("td[style=width:110px][class=weight110][valign=top]") != null){
						Elements elements = workexperiences.select("td[style=width:110px][class=weight110][valign=top]");
						for(Element element : elements){
							String childname = element.text().replaceAll(" ", "");
							if(childname.equals("期望薪水：")){
								salary = element.nextElementSibling().text();
								if(salary.length() > 50)
									salary = salary.substring(0, 50);
							}else if(childname.equals("目标职能：")){
								occupation = element.nextElementSibling().text();
								if(occupation.length() > 100)
									occupation = occupation.substring(0, 100);
							}else if(childname.equals("希望行业：")){
								industry = element.nextElementSibling().text();
								if(industry.length() > 50)
									industry = industry.substring(0, 50);
							}
						}
					}
				}
			/*	//行业
				else if (tempname.equals("工作经验")) {
					if(workexperiences.select("td[style=width:110px][class=weight110][valign=top]") != null){
						Elements elements = workexperiences.select("td[style=width:110px][class=weight110][valign=top]");
						for(Element element : elements){
							if(element.text().replaceAll(" ", "").equals("所属行业：")){
								industry = element.nextElementSibling().text();
								if(industry.length() > 50)
									industry = industry.substring(0, 50);
								System.out.println("====所属行业===="+industry);
								break;
							}
						}
					}
				}*/
			}
			if(StringUtils.isEmpty(salary))
				salary = "无";
			if(StringUtils.isEmpty(valuation))
				valuation = "无";
			if(StringUtils.isEmpty(occupation))
				occupation = "无";
			if(StringUtils.isEmpty(industry))
				industry = "无";
			resume.setSalary(salary);
			resume.setValuation(valuation);
			resume.setOccupation(occupation);
			resume.setIndustry(industry);
			resume.setSituation("无");
			// System.out.println(resume.toString());
			// 保存简历
			// System.out.println("---------------------------"+resume.toString());

			if (isnew) {
				// System.out.println("====新增===="+resume.toString());
				resumeDao.insertResume(resume);
			} else {
				// System.out.println("====更新===="+resume.toString());
				resumeDao.updateResume(resume);
			}
			// System.out.println("最高学历====" + culturename);

			// 应界生和社会招聘
			// [ 应届生简历/无工作经验 ]
			Elements typeel = root.select("span[style=color:#676767;]");
			String type = null;
			if (typeel.size() > 1) {
				type = typeel.get(0).text();
			} else
				type = typeel.text();
			boolean resumeState = type.equals("[ 应届生简历/无工作经验 ]");

			// System.out.println("==================================");
			// System.out.println("==========主题信息 =============");
			// System.out.println("==================================");

			/*
			 * System.out.println("==================================");
			 * System.out.println("==========工作经验 =============");
			 * System.out.println("==================================");
			 */
			if (resumeState) {
				for (int i = 0; i < themes.size(); i++) {
					if (i == 0 && themes.get(i).previousElementSibling() == null)
						i = i + 1;
					if (i >= themes.size())
						i = i - 1;
					String tempname = themes.get(i).previousElementSibling().text();
					if (tempname.equals("工作经验")) {

						// 数据
						List<WorkExperience> list = new ArrayList<WorkExperience>();
						// 社会实践先删除后插入
						resumeDao.delWorkExperienceByWebuserId(resume.getWebuserguid());
						org.jsoup.nodes.Element workexperiences = themes.get(i);
						// System.out.println("工作经验======" + workexperiences);

						// 工作经验
						/*
						 *以 td[colspan=2][valign=top][id!=Cur_Val]为起点开始解析
						 **/
						Elements tmpworkdates = workexperiences.select("td[colspan=2][valign=top][id!=Cur_Val]");
						Elements workdates = new Elements();
						for (Element ele : tmpworkdates) {
							if (ele.children().size() <= 0) {
								workdates.add(ele);
							}
						}
						for (Element node : workdates) {
							// 实例化
							WorkExperience model = new WorkExperience();
							String[] ls = node.text().split("：");
							// 工作单位
							String dwname = ls[1];
							int idx = dwname.indexOf(" [");
							if (idx > 1) {
								dwname = dwname.substring(0, idx);
								if (dwname.length() > 50) {
									dwname = dwname.substring(0, 50);
								}
							}
							if (StringUtils.isNotEmpty(dwname))
								if (dwname.length() > 50)
									model.setWorkunit(dwname.substring(0, 50));
								else
									model.setWorkunit(dwname);
							else
								model.setWorkunit("无");
							// 职位
							String posationname = "";
							if(node.parent().nextElementSibling().nextElementSibling() != null){
							if (node.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top][colspan=2]").select("strong").size() > 1) {
								posationname = node.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top][colspan=2]").select("strong").get(1).text();
							} else {
								posationname = "无";
							}
						}
							// 职责描述
							String content = "";
							/*
							 *以 td[colspan=2][valign=top][id!=Cur_Val]为起点开始解析td[id=Cur_Val][colspan=2][valign=top]
							 *当前标签不存在td[id=Cur_Val][colspan=2][valign=top]，则向下一个标签寻找
							 **/
							if (node.parent().nextElementSibling() != null && node.parent().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() == 0) {
								if (node.parent().nextElementSibling().nextElementSibling() != null
										&& node.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() == 0) {
									if (node.parent().nextElementSibling().nextElementSibling().nextElementSibling() != null
											&& node.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() > 0) {
										content = node.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
									}
								} else {
									if(node.parent().nextElementSibling().nextElementSibling() != null)
										content = node.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
								}
							} else {
								if(node.parent().nextElementSibling() != null)
									content = node.parent().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
							}
							model.setWorkexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());
							// 职责描述
							if (StringUtils.isEmpty(content))
								content = "无";
							if (content.length() > 500)
								content = content.substring(0, 500);
							model.setJobdescription(content);

							// 开始时间
							String datese = ls[0];
							if (datese.length() > 0) {
								String[] datels = datese.split("--");
								String startdate = "";
								//只取数字
								startdate = datels[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								String enddate = datels[1] + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}

							model.setWorkexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());
							if (posationname.length() > 50)
								model.setPosation(posationname.substring(0, 50));
							else
								model.setPosation(posationname);
							list.add(model);
						}
						for (WorkExperience model : list) {
							resumeDao.insertWorkExperience(model);
						}

					}else if (tempname.equals("教育经历")) {
						// 数据
						List<EducationExperience> list = new ArrayList<EducationExperience>();
						// 先删除后插入
						resumeDao.delEducationExperienceByWebuserId(resume.getWebuserguid());
						org.jsoup.nodes.Element workexperiences = themes.get(i);

						Elements nodes = workexperiences.select("td[class=weight180]");
						for (org.jsoup.nodes.Element node : nodes) {

							// 學校
							Element schooldom = null;
							if(node.nextElementSibling() != null && node.nextElementSibling().select("td[class=weight220 padding10]")  != null)
								schooldom = node.nextElementSibling();
							// System.out.println("學校======" +
							// schooldom.text());

							// 专业
							Element specialtysdom = null;
							if(node.nextElementSibling().nextElementSibling() != null && node.nextElementSibling().nextElementSibling().select("td[class=padding10]")  != null)
								specialtysdom = node.nextElementSibling().nextElementSibling();
							//System.out.println("专业======" +specialtysdom.text());

							// 学历
							Element culturesdom = null;
							if(node.nextElementSibling().nextElementSibling().nextElementSibling() != null && node.nextElementSibling().nextElementSibling().nextElementSibling().select("td[class=weight60]")  != null)
								culturesdom = node.nextElementSibling().nextElementSibling().nextElementSibling();
							//System.out.println("学历======" +culturesdom.text());
							// System.out.println("***************************");
							
							// 实例化
							EducationExperience model = new EducationExperience();
							
							if(node.parent().nextElementSibling() != null && node.parent().nextElementSibling().select("td[id=Cur_Val][colspan=4][valign=top][height=30]") != null){
								String majordescription = node.parent().nextElementSibling().select("td[id=Cur_Val][colspan=4][valign=top][height=30]").text();
								if(majordescription.length() > 500)
									majordescription = majordescription.substring(0, 500);
								model.setMajordescription(majordescription);
							}
							// 时间
							String tmpworkexperiences = node.text();
							if (tmpworkexperiences.length() > 0) {
								String[] datels = tmpworkexperiences.split("--");
								String startdate = "";
								//只取数字
								startdate = datels[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								String enddate = datels[1] + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setEducationexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());
							String school = schooldom.text();
							if (StringUtils.isEmpty(school))
								school = "无";
							if (school.length() > 50)
								school = school.substring(0, 50);
							model.setSchool(school);
							String specialtys = specialtysdom.text();
							if (StringUtils.isEmpty(specialtys))
								specialtys = "无";
							if (specialtys.length() > 50)
								specialtys = specialtys.substring(0, 50);
							model.setSpecialty(specialtys);
							// 数据库翻译
							Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturesdom.text());
							if (culture == null)
								culture = Constance.VALID_YES;
							model.setCulture(culture);
							model.setCulturename(culturename);
							list.add(model);
						}
						// 保存教育经历
						for (EducationExperience model : list) {
							resumeDao.insertEducationExperience(model);
						}

					}else if (tempname.equals("项目经验")) {
						// System.out.println("==================================");
						// System.out.println("==========项目经验 =============");
						// System.out.println("==================================");
						List<ProjectExperience> list = new ArrayList<ProjectExperience>();
						resumeDao.delProjectExperienceByWebuserId(resume.getWebuserguid());
						org.jsoup.nodes.Element projectexperience = themes.get(i);
						Elements nodes = projectexperience.select("td[class=weight110][style=width:110px]");
						for (Element element : nodes) {

							// 项目名称
							String itemname = "";
							if(element.nextElementSibling() != null)
							itemname = element.nextElementSibling().text();
							if (StringUtils.isEmpty(itemname))
								itemname = "无";
							if (itemname.length() > 50)
								itemname = itemname.substring(0, 50);
							// 职责描述
							String content = "";
							/*
							 *以 td[class=weight110][style=width:110px]为起点开始解析td[id=Cur_Val][valign=top]
							 *当前标签不存在td[id=Cur_Val][valign=top]，则向下一个标签寻找
							 **/
							if (element.parent().nextElementSibling() != null && element.parent().nextElementSibling().select("td[id=Cur_Val][valign=top]").text().length() == 0) {
								if (element.parent().nextElementSibling().nextElementSibling() != null
										&& element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text().length() == 0) {
									if (element.parent().nextElementSibling().nextElementSibling().nextElementSibling() != null
											&& element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text().length() == 0) {
										content = element.parent().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
									} else {
										if (element.parent().nextElementSibling().nextElementSibling().nextElementSibling() != null)
											content = element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
									}
								} else {
									if (element.parent().nextElementSibling().nextElementSibling() != null)
										content = element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
								}
							} else {
								if (element.parent().nextElementSibling() != null)
									content = element.parent().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
							}

							if (StringUtils.isEmpty(content))
								content = "无";
							if (content.length() > 500)
								content = content.substring(0, 500);

							// System.out.println("====项目描述===="+content);
							ProjectExperience model = new ProjectExperience();
							if (element.text().length() > 0) {
								String[] date = element.text().split("--");
								String startdate = "";
								//只取数字和/
								startdate = date[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								String enddate = date[1].replaceAll("：", "") + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setProjectexperienceguid(UUIDGenerator.randomUUID());
							model.setWebuserguid(resume.getWebuserguid());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setItemname(itemname);

							model.setJobdescription(content);
							list.add(model);
						}
						for (ProjectExperience model : list) {
							resumeDao.insertProjectExperience(model);
						}
					}else if (tempname.equals("培训经历")) {
						// System.out.println("==================================");
						// /System.out.println("==========培训经历 =============");
						// System.out.println("==================================");
						// 数据
						List<TrainingExperience> list = new ArrayList<TrainingExperience>();
						// 先删除后插入
						resumeDao.delTrainingExperienceByWebuserId(resume.getWebuserguid());
						org.jsoup.nodes.Element trainingexperiences = themes.get(i);
						// System.out.println("培训经历======" +
						// trainingexperiences);

						Elements nodes = trainingexperiences.select("td[class=weight110]");
						for (org.jsoup.nodes.Element node : nodes) {

							// System.out.println("开始时间======" + startdate);
							// System.out.println("结束时间======" + enddate);

							// 培训机构
							Element traininginstitutionsdom = null;
							// 培训内容
							Element trainingcontentsdom = null;
							//培训内容和培训机构td相同
							if(node.nextElementSibling() != null && node.parent().select("td[class=weight220 padding10]") != null){
								Elements tdelements = node.parent().select("td[class=weight220 padding10]");
								if(tdelements.size() > 0){
									if(tdelements.size() > 1){
										traininginstitutionsdom = tdelements.get(0);
										trainingcontentsdom = tdelements.get(1);
									}else
										traininginstitutionsdom = tdelements.get(0);
								}
							}
							//traininginstitutionsdom = node.nextElementSibling();
							// System.out.println("培训机构======" +
							// traininginstitutionsdom.text());

							//org.jsoup.nodes.Element trainingcontentsdom = node.nextElementSibling().nextElementSibling();
							// System.out.println("培训内容======" +
							// trainingcontentsdom.text());

							// 证书
							Element certificatesdom = null;
							if(node.nextElementSibling().nextElementSibling().nextElementSibling() != null && node.parent().select("td[style=width:150px]") != null)
							certificatesdom = node.nextElementSibling().nextElementSibling().nextElementSibling();
							// System.out.println("证书======" +
							// certificatesdom.text());

							// System.out.println("***************************");
							// 实例化
							TrainingExperience model = new TrainingExperience();
							// 时间
							String tmpworkexperiences = node.text();
							if (tmpworkexperiences.length() > 0) {
								String[] datels = tmpworkexperiences.split("--");
								String startdate = "";
								//只取数字和/
								startdate = datels[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								String enddate = datels[1] + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setTrainingexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());
							if(trainingcontentsdom != null){
								if (StringUtils.isNotEmpty(trainingcontentsdom.text()))
									if (trainingcontentsdom.text().length() > 500)
										model.setTrainingcontent(trainingcontentsdom.text().substring(0, 500));
									else
										model.setTrainingcontent(trainingcontentsdom.text());
								else
									model.setTrainingcontent("无");
							}
							if(traininginstitutionsdom != null){
								if (StringUtils.isNotEmpty(traininginstitutionsdom.text()))
									if (traininginstitutionsdom.text().length() > 50)
										model.setTraininginstitutions(traininginstitutionsdom.text().substring(0, 50));
									else
										model.setTraininginstitutions(traininginstitutionsdom.text());
								else
									model.setTraininginstitutions("无");
							}
							if(certificatesdom != null){
								if (StringUtils.isNotEmpty(certificatesdom.text()))
									if (certificatesdom.text().length() > 50)
										model.setCertificate(certificatesdom.text().substring(0, 50));
									else
										model.setCertificate(certificatesdom.text());
								else
									model.setCertificate("无");
							}
							list.add(model);
						}
						// 保存培训经历
						for (TrainingExperience model : list) {
							resumeDao.insertTrainingExperience(model);
						}
					}
				}
			} else {
				for (int i = 0; i < themes.size(); i++) {
					if (i == 0 && themes.get(i).previousElementSibling() == null)
						i = i + 1;
					if (i >= themes.size())
						i = i - 1;
					String tempname = themes.get(i).previousElementSibling().text();
					if (tempname.equals("工作经验")) {
						org.jsoup.nodes.Element workexperiences = themes.get(i);
						// System.out.println("======用户姓名====="+strongs.text());
						// System.out.println("======工作经验======" +
						// workexperiences);
						// 数据
						List<WorkExperience> list = new ArrayList<WorkExperience>();
						// 社会实践先删除后插入
						resumeDao.delWorkExperienceByWebuserId(resume.getWebuserguid());
						// 工作经验
						Elements tmpworkdates = workexperiences.select("td[colspan=2][valign=top][id!=Cur_Val]");
						Elements workdates = new Elements();
						for (Element ele : tmpworkdates) {
							if (ele.children().size() <= 0) {
								workdates.add(ele);
							}
						}
						for (Element node : workdates) {
							// 实例化
							WorkExperience model = new WorkExperience();
							String[] ls = node.text().split("：");
							// 工作单位
							String dwname = ls[1];
							int idx = dwname.indexOf(" [");
							if (idx > 1) {
								dwname = dwname.substring(0, idx);
								if (dwname.length() > 50) {
									dwname = dwname.substring(0, 50);
								}
							}
							if (StringUtils.isNotEmpty(dwname))
								if (dwname.length() > 50)
									model.setWorkunit(dwname.substring(0, 50));
								else
									model.setWorkunit(dwname);
							else
								model.setWorkunit("无");
							// 职位
							String posationname = "";
							/*
							 *以 td[colspan=2][valign=top][id!=Cur_Val]为起点开始解析td[id=Cur_Val][colspan=2][valign=top]
							 *当前标签不存在td[id=Cur_Val][colspan=2][valign=top]，则向下一个标签寻找
							 **/
							if(node.parent().nextElementSibling().nextElementSibling() != null){
							if ( node.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top][colspan=2]").select("strong").size() > 1) {
								posationname = node.parent().nextElementSibling().nextElementSibling().select("td[vAlign=top][colspan=2]").select("strong").get(1).text();
							} else {
								posationname = "无";
							}
						}
							// 职责描述
							String content = "";
							if (node.parent().nextElementSibling() != null && node.parent().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() == 0) {
								if (node.parent().nextElementSibling().nextElementSibling() != null
										&& node.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() == 0) {
									if (node.parent().nextElementSibling().nextElementSibling().nextElementSibling() != null
											&& node.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text().length() > 0) {
										content = node.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
									}
								} else {
									if(node.parent().nextElementSibling().nextElementSibling() != null)
										content = node.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
								}
							} else {
								if(node.parent().nextElementSibling() != null)
									content = node.parent().nextElementSibling().select("td[id=Cur_Val][colspan=2][valign=top]").text();
							}
							model.setWorkexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());
							// 职责描述
							if (StringUtils.isEmpty(content))
								content = "无";
							if (content.length() > 500)
								content = content.substring(0, 500);
							model.setJobdescription(content);

							// 开始时间
							String datese = ls[0];
							if (datese.length() > 0) {
								String[] datels = datese.split("--");
								String startdate = "";
								//只取数字和/
								startdate = datels[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								String enddate = datels[1] + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}

							model.setWorkexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());
							if (posationname.length() > 50)
								model.setPosation(posationname.substring(0, 50));
							else
								model.setPosation(posationname);
							list.add(model);
						}
						for (WorkExperience model : list) {
							resumeDao.insertWorkExperience(model);
						}

					}else if (tempname.equals("教育经历")) {
						/*
						 * System.out.println("==================================");
						 * System.out.println("==========教育经历 =============");
						 * System.out.println("==================================");
						 */
						// 数据
						List<EducationExperience> list = new ArrayList<EducationExperience>();
						// 先删除后插入
						resumeDao.delEducationExperienceByWebuserId(resume.getWebuserguid());
						org.jsoup.nodes.Element workexperiences = themes.get(i);
						// System.out.println("======用户姓名====="+strongs.text());
						// System.out.println("=====教育经历======" +
						// workexperiences);

						Elements nodes = workexperiences.select("td[class=weight180]");
						for (org.jsoup.nodes.Element node : nodes) {

							// 學校
							Element schooldom = null;
							if(node.nextElementSibling() != null && node.nextElementSibling().select("td[class=weight220 padding10]")  != null)
								schooldom = node.nextElementSibling();
							//System.out.println("學校======" +schooldom.text());

							// 专业
							Element specialtysdom = null;
							if(node.nextElementSibling().nextElementSibling() != null && node.nextElementSibling().nextElementSibling().select("td[class=padding10]")  != null)
								specialtysdom = node.nextElementSibling().nextElementSibling();
							//System.out.println("专业======" +specialtysdom.text());

							// 学历
							Element culturesdom = null;
							if(node.nextElementSibling().nextElementSibling().nextElementSibling() != null && node.nextElementSibling().nextElementSibling().nextElementSibling().select("td[class=weight60]")  != null)
								culturesdom = node.nextElementSibling().nextElementSibling().nextElementSibling();
							//System.out.println("学历======" +culturesdom.text());
							// System.out.println("***************************");
							
							// 实例化
							EducationExperience model = new EducationExperience();
							
							if(node.parent().nextElementSibling() != null && node.parent().nextElementSibling().select("td[id=Cur_Val][colspan=4][valign=top][height=30]") != null){
								String majordescription = node.parent().nextElementSibling().select("td[id=Cur_Val][colspan=4][valign=top][height=30]").text();
								if(majordescription.length() > 500)
									majordescription = majordescription.substring(0, 500);
								model.setMajordescription(majordescription);
							}
							// 时间
							String tmpworkexperiences = node.text();
							if (tmpworkexperiences.length() > 0) {
								String[] datels = tmpworkexperiences.split("--");
								String startdate = "";
								//只取数字和/
								startdate = datels[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								String enddate = datels[1] + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setEducationexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());
							String school = schooldom.text();
							if (StringUtils.isEmpty(school))
								school = "无";
							if (school.length() > 50)
								school = school.substring(0, 50);
							model.setSchool(school);
							String specialtys = specialtysdom.text();
							if (StringUtils.isEmpty(specialtys))
								specialtys = "无";
							if (specialtys.length() > 50)
								specialtys = specialtys.substring(0, 50);
							model.setSpecialty(specialtys);
							// 数据库翻译
							Integer culture = nameConvertCodeService.getOptionCodeByTypeAndName(OptionConstance.CULTURE, culturesdom.text());
							if (culture == null)
								culture = Constance.VALID_YES;
							model.setCulture(culture);
							model.setCulturename(culturename);
							list.add(model);
						}
						// 保存教育经历
						for (EducationExperience model : list) {
							resumeDao.insertEducationExperience(model);
						}

					}else if (tempname.equals("项目经验")) {
						// System.out.println("==================================");
						// System.out.println("==========项目经验 =============");
						// System.out.println("==================================");
						List<ProjectExperience> list = new ArrayList<ProjectExperience>();
						resumeDao.delProjectExperienceByWebuserId(resume.getWebuserguid());
						org.jsoup.nodes.Element projectexperience = themes.get(i);
						Elements nodes = projectexperience.select("td[class=weight110][style=width:110px]");
						// System.out.println("========项目经验 ======"+projectexperience);
						for (Element element : nodes) {

							// 项目名称
							String itemname = "";
							if(element.nextElementSibling() != null)
							itemname = element.nextElementSibling().text();
							if (StringUtils.isEmpty(itemname))
								itemname = "无";
							if (itemname.length() > 50)
								itemname = itemname.substring(0, 50);
							// 职责描述
							//String cont = "";
							String content = "";
							/*
							 *以 td[class=weight110][style=width:110px]为起点开始解析td[id=Cur_Val][valign=top]
							 *当前标签不存在td[id=Cur_Val][valign=top]，则向下一个标签寻找
							 **/
							if (element.parent().nextElementSibling() != null && element.parent().nextElementSibling().select("td[id=Cur_Val][valign=top]").text().length() == 0) {
								if (element.parent().nextElementSibling().nextElementSibling() != null
										&& element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text().length() == 0) {
									if (element.parent().nextElementSibling().nextElementSibling().nextElementSibling() != null
											&& element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text().length() == 0) {
										content = element.parent().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
									} else {
										if (element.parent().nextElementSibling().nextElementSibling().nextElementSibling() != null)
											content = element.parent().nextElementSibling().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
									}
								} else {
									if (element.parent().nextElementSibling().nextElementSibling() != null)
										content = element.parent().nextElementSibling().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
								}
							} else {
								if (element.parent().nextElementSibling() != null)
									content = element.parent().nextElementSibling().select("td[id=Cur_Val][valign=top]").text();
							}

							if (StringUtils.isEmpty(content))
								content = "无";
							if (content.length() > 500)
								content = content.substring(0, 500);

							ProjectExperience model = new ProjectExperience();
							if (element.text().length() > 0) {
								String[] date = element.text().split("--");
								String startdate = "";
								//只取数字/
								startdate = date[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								String enddate = date[1].replaceAll("：", "") + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setProjectexperienceguid(UUIDGenerator.randomUUID());
							model.setWebuserguid(resume.getWebuserguid());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setItemname(itemname);

							model.setJobdescription(content);
							list.add(model);
						}
						for (ProjectExperience model : list) {
							resumeDao.insertProjectExperience(model);
						}
					}else if (tempname.equals("培训经历")) {
						// System.out.println("==================================");
						// System.out.println("==========培训经历 =============");
						// System.out.println("==================================");
						// 数据
						List<TrainingExperience> list = new ArrayList<TrainingExperience>();
						// 先删除后插入
						resumeDao.delTrainingExperienceByWebuserId(resume.getWebuserguid());
						org.jsoup.nodes.Element trainingexperiences = themes.get(i);
						// System.out.println("======用户姓名====="+strongs.text());
						// System.out.println("=====培训经历======" +
						// trainingexperiences);

						Elements nodes = trainingexperiences.select("td[class=weight110]");
						for (org.jsoup.nodes.Element node : nodes) {
							// System.out.println("开始时间======" + startdate);
							// System.out.println("结束时间======" + enddate);

							// 培训机构
							Element traininginstitutionsdom = null;
							// 培训内容
							Element trainingcontentsdom = null;
							//培训内容和培训机构td相同
							if(node.nextElementSibling() != null && node.parent().select("td[class=weight220 padding10]") != null){
								Elements tdelements = node.parent().select("td[class=weight220 padding10]");
								if(tdelements.size() > 0){
									if(tdelements.size() > 1){
										traininginstitutionsdom = tdelements.get(0);
										trainingcontentsdom = tdelements.get(1);
									}else
										traininginstitutionsdom = tdelements.get(0);
								}
							}

							// 证书
							Element certificatesdom = null;
							if(node.nextElementSibling().nextElementSibling().nextElementSibling() != null && node.parent().select("td[style=width:150px]") != null)
							certificatesdom = node.nextElementSibling().nextElementSibling().nextElementSibling();
							// System.out.println("证书======" +
							// certificatesdom.text());

							// System.out.println("***************************");
							// 实例化
							TrainingExperience model = new TrainingExperience();
							// 时间
							String tmpworkexperiences = node.text();
							if (tmpworkexperiences.length() > 0) {
								String[] datels = tmpworkexperiences.split("--");
								String startdate = "";
								//只取数字/
								startdate = datels[0].replaceAll(" ", "") + "/01";
								String str="[^0-9&&[^/]]";
								Pattern pattern = Pattern.compile(str);
								Matcher matcher = pattern.matcher(startdate);
								startdate = matcher.replaceAll("").trim();
								String enddate = datels[1] + "/01";
								model.setStartdate(DateUtil.parseEnglish(startdate));
								if (model.getStartdate() == null)
									model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(DateUtil.parseEnglish(enddate.replaceAll(" ", "")));
							} else {
								model.setStartdate(new Date(System.currentTimeMillis()));
								model.setEnddate(new Date(System.currentTimeMillis()));
							}
							model.setTrainingexperienceguid(UUIDGenerator.randomUUID());
							model.setModtime(new Timestamp(System.currentTimeMillis()));
							model.setWebuserguid(resume.getWebuserguid());
							if(trainingcontentsdom != null){
								if (StringUtils.isNotEmpty(trainingcontentsdom.text()))
									if (trainingcontentsdom.text().length() > 500)
										model.setTrainingcontent(trainingcontentsdom.text().substring(0, 500));
									else
										model.setTrainingcontent(trainingcontentsdom.text());
								else
									model.setTrainingcontent("无");
							}
							if(traininginstitutionsdom != null){
								if (StringUtils.isNotEmpty(traininginstitutionsdom.text()))
									if (traininginstitutionsdom.text().length() > 50)
										model.setTraininginstitutions(traininginstitutionsdom.text().substring(0, 50));
									else
										model.setTraininginstitutions(traininginstitutionsdom.text());
								else
									model.setTraininginstitutions("无");
							}
							if(certificatesdom != null){
								if (StringUtils.isNotEmpty(certificatesdom.text()))
									if (certificatesdom.text().length() > 50)
										model.setCertificate(certificatesdom.text().substring(0, 50));
									else
										model.setCertificate(certificatesdom.text());
								else
									model.setCertificate("无");
							}
							list.add(model);
						}
						// 保存培训经历
						for (TrainingExperience model : list) {
							resumeDao.insertTrainingExperience(model);
						}
					}
				}
			}

			//if(eamilguid != null){
				/*
				 * System.out.println("==================================");
				 * System.out.println("==========应聘信息 =============");
				 * System.out.println("==================================");
				 */
				// 应聘信息
				Elements mycandidates = doc.select("body>table>tbody>tr>td>table>tbody>tr>td[align=left][class=blue1][valign=top]");
				// System.out.println("==========模板三======"+mycandidates);
				if (!mycandidates.isEmpty()) {
					//System.out.println("模板三：webuserguid==="+user.getWebuserguid());
					//去除黑名单
					List<MyCandidates> tmpList = myCandidatesDao.getMyCandidatesByWebUserGuidAndState(user.getWebuserguid(), Constance.CandidatesState_Blacklist);
					// 实例化
					MyCandidates model = new MyCandidates();
					model.setMycandidatesguid(UUIDGenerator.randomUUID());
					model.setWebuserguid(user.getWebuserguid());
					model.setCandidatesstate(tmpList.isEmpty() ? Constance.CandidatesState_One : Constance.CandidatesState_Blacklist);
					model.setCandidatestype(Constance.User5);
					model.setProgress(Constance.VALID_NO);
					model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					model.setResumeeamilguid(eamilguid);
					model.setReadtype(Constance.VALID_NO);

					// 应聘职位
					org.jsoup.nodes.Element postnameel = mycandidates.get(0);
					String postname = postnameel.text();
					// System.out.println("应聘职位======" + postname);
					
					// 投递时间
					org.jsoup.nodes.Element candidatestime = mycandidates.get(2);
					// System.out.println("投递时间======" + candidatestime.text());
					
					// 職位
					List<RecruitPost> list = recruitmentDao.getRecruitPostByRecruitPostName(postname);
					model.setPostname(postname);
					if (!list.isEmpty()) {
						RecruitPost recruitPost = list.get(0);
						model.setRecruitpostguid(recruitPost.getRecruitpostguid());
					} else {
						model.setRecruitpostname(postname);
					}
					//过滤掉一周以内投递过的相同简历
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					model.setTempdate(format.format(System.currentTimeMillis()));
					List<MyCandidates> candidateslist = myCandidatesDao.checkResume(model);
					if(candidateslist != null&&!candidateslist.isEmpty()){
						//System.out.println("===一周内以投递===");
						return null;
					}
					
					model.setCandidatestime(DateUtil.parse(candidatestime.text()));
					myCandidatesDao.insertMyCandidates(model);
				}
			//}
			return user;
		}
		return null;
	}

}