package com.mk;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.grid.util.DateUtil;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.recruitment.entity.RecruitPost;
import com.mk.recruitment.entity.WebUser;
import com.mk.resume.entity.EducationExperience;
import com.mk.resume.entity.Resume;
import com.mk.resume.entity.TrainingExperience;
import com.mk.resume.entity.WorkExperience;

public class ReadEmaiFileTest {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// File file = new File("C:/Users/lgl/Desktop/mail1.html");
		File file = new File("E:/myjob.html");
		System.out.println("file===" + file.toString());
		System.out.println(getDocument(file));
		System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyy="+new Date(System.currentTimeMillis()));
	}

	private static String getDocument(File html) throws Exception {
		String text = "";
		// 设置编码集
		org.jsoup.nodes.Document doc = Jsoup.parse(html, "utf-8");
		
		Elements  div = doc.select("body>table");
		System.out.println("ooooooooo"+div.select("table[class=table_set]").size());
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
			System.out.println("姓名：===" + name);

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
						System.out.println("电话：===" + mobile);
					}

					// 邮件
					email = te.get(1).text();
					System.out.println("邮件：===" + email);
					if (StringUtils.isEmpty(email))
						return null;
				} else {
					// 电话
					mobile = te.get(1).text();
					if (mobile != null && mobile.length() > 4) {
						mobile = mobile.substring(0, mobile.length() - 4);
						System.out.println("mobile：===" + mobile);
					}

					// 邮件
					email = te.get(2).text();
					System.out.println("邮件：===" + email);
					if (StringUtils.isEmpty(email))
						return null;
				}
				if (user == null) {
					// 保存外网用户
					user = new WebUser(email, name);
					user.setWebuserguid(UUIDGenerator.randomUUID());
					user.setMobile(mobile);
				}
			}

			// 外網用戶必須存在
			if (user == null)
				return null;

			// 性别,出生日期
			Elements sexday = users.select("b");
			String[] array = sexday.text().split("\\|");

			// 工作年限(选项)
			if (array.length > 0) {
				String workagename = array[0];
				workagename = workagename.replaceAll(" ", "");
				if (StringUtils.isNotEmpty(workagename)) {
					workagename = workagename.substring(0, workagename.length() - 4);
					System.out.println("工作年限：===" + workagename);
				}
			}

			// 性别
			if (array.length >= 1) {
				String sexel = array[1];
				String sexname = sexel.replaceAll(" ", "");
				// 数据翻译
				System.out.println("性别：===" + sexname);
			}

			// 出生日期
			if (array.length >= 2) {
				String birthday = array[2];
				birthday = birthday.substring(birthday.indexOf("(") + 1, birthday.indexOf(")"));
				String[] birthdays = birthday.split("/");
				if (birthdays.length > 1) {
					birthday = birthday.replace(" ", "");
					System.out.println("出生日期：===" + birthday);
					// resume.setBirthday(DateUtil.parseEnglish(birthday));
				} else {
					birthday = birthday.replace(" ", "");
					// resume.setBirthday(DateUtil.parseChina(birthday));
					System.out.println("出生日期：===" + birthday);
				}
			}

			// 居住地
			Elements address = users.select("td[height=20][width=10%]+td");
			if (address.text().isEmpty()) {
				// resume.setHomeplace("无");
				System.out.println("居住地：===" + address.text());
			} else {
				// resume.setHomeplace(address.text());
				System.out.println("居住地：===" + address.text());
			}

			// 最高学历
			Elements culturehtm = root.select("td[colspan=2][width=49%] td[width=230]");
			if (!culturehtm.isEmpty()) {
				String culturename = culturehtm.text();
				System.out.println("最高学历：===" + culturename);
				Elements study = root.select("td[width=49%][colspan=2]");
				Elements studylist = study.select("td[colspan!=2][width!=64][width!=230]");// 专业和学校
				for (int i = 0; i < studylist.size(); i = i + 2) {
					System.out.println(studylist.get(i).text() + studylist.get(i + 1).text());
				}

				Elements salary = root.select("td[width=188]");
				System.out.println("年薪：" + salary.text());

				// 应界生和社会招聘
				// [ 应届生简历/无工作经验 ]
				Elements typeel = root.select("span[style=color:#676767;]");
				String type = typeel.text();
				System.out.println("应界生和社会招聘：===" + type);
				boolean resumeState = type.equals("[ 应届生简历/无工作经验 ]");

				// 解析
				Elements info = root.select("div[class=v3_t]");

				// 社会实践
				if (!resumeState) {
					// 最近工作
					Elements lastwork = root.select("td[width=45%][colspan=2]");
					System.out.println("最近工作");
					String company = lastwork.select("td[width=64]").text();
					String companyname = lastwork.select("td[width=219]").text();
					System.out.println(company + companyname);// 公司
					Elements tdlist = lastwork.select("td[colspan!=2][width!=64][width!=219]");// 行业和职位
					for (int i = 0; i < tdlist.size(); i = i + 2) {
						System.out.println(tdlist.get(i).text() + tdlist.get(i + 1).text());
					}
				}
				if (table100.isEmpty())
					return null;
				if (info.isEmpty() && info.size() <= 0)
					return null;
				for (int i = 0; i < info.size(); i++) {
					String[] them = info.get(i).text().split(" ");// 数组去掉前后空格
					if(them.length < 1)
						return null;
					System.out.println("==="+them[1]);
					if (them[1].equals("自我评价")) {
						// 自我评价
						Element evaluatetable = root.select("table[class=v_table03]").get(i);
						String[] str = evaluatetable.text().split(" ");
						for(String evaluat : str){
						System.out.println("自我评价：" +evaluat);
						}
					}
					if (them[1].equals("求职意向")) {
						// 求职意向
						Element jobintension = root.select("table[class=v_table03]").get(i);
						Elements tdelements = jobintension.select("td");
						for (int j = 0; j < tdelements.size(); j = j + 2) {
							System.out.println(tdelements.get(j).text() + tdelements.get(j + 1).text());
						}
					}
					if (them[1].equals("工作经验")) {
						// 工作经验
						System.out.println("=========工作经验=========");
						Element workexperiencetable = root.select("table[class=v_table03]").get(i);
						Elements cont = workexperiencetable.select("td[id=Cur_Val][colspan=2][valign=top]");
						for (Element element : cont) {
							String firm = element.parent().previousElementSibling().previousElementSibling().previousElementSibling().text();
							System.out.println("公司：" + firm);

							String industrys = element.parent().previousElementSibling().previousElementSibling().select("td[ class!=weight110]").text();
							System.out.println("行业：" + industrys);

							Elements posationelements = element.parent().previousElementSibling().select("td");
							String posationname = posationelements.get(1).text();
							System.out.println("职位：" + posationname);

							String content = element.text();
							System.out.println("YYYYYYY"+content.split(" "));
							String[] str = content.split(" ");
							for (String conten : str) {
								System.out.println("内容：" + conten);
							}
						}
					}
					if (them[1].equals("教育经历")) {
						// 教育经历
						System.out.println("=========教育经历=========");
						Element studyexperiencetable = root.select("table[class=v_table03]").get(i);
						Elements educationelements = studyexperiencetable.select("td[ class=weight180][vAlign=top]");
						for (Element element : educationelements) {
							String date = element.text();
							System.out.println("时间：" + date);
							String school = element.nextElementSibling().text();
							System.out.println("学校：" + school);
							String major = element.nextElementSibling().nextElementSibling().text();
							System.out.println("专业：" + major);
							String level = element.nextElementSibling().nextElementSibling().nextElementSibling().text();
							System.out.println("学历：" + level);
						}
					}
					if (them[1].equals("培训经历")) {
						// 培训经历
						System.out.println("=========培训经历=========");
						Element traintable = root.select("table[class=v_table03]").get(i);
						Elements trainelements = traintable.select("td[class=weight110][vAlign=top]");
						for (Element element : trainelements) {
							String date = element.text();
							System.out.println("时间：" + date);
							String organization = element.nextElementSibling().text();
							System.out.println("培训机构：" + organization);
							String content = element.nextElementSibling().nextElementSibling().text();
							System.out.println("培训内容：" + content);
							String credentials = element.nextElementSibling().nextElementSibling().nextElementSibling().text();
							System.out.println("培训证书：" + credentials);
						}
					}
					if (them[1].equals("语言能力")) {
						// 语言能力
						System.out.println("=========语言能力=========");
						Element languagetable = root.select("table[class=v_table03]").get(i);
						Elements languageelements = languagetable.select("td[class=weight120][vAlign=top]");
						for (Element element : languageelements) {
							String language = element.text();
							// System.out.println("英语：" + language);
							String other = element.nextElementSibling().text();
							System.out.println(language + other);
						}
					}
					if (them[1].equals("IT技能")) {
						System.out.println("=========IT技能=========");
						Element skillstable = root.select("table[class=v_table03]").get(i);
						Elements skillselements = skillstable.select("td[rowspan=1]");
						for (Element element : skillselements) {
							String skillname = element.previousElementSibling().previousElementSibling().previousElementSibling().text();
							System.out.println("技能名称：" + skillname);
							String skilled = element.previousElementSibling().previousElementSibling().text();
							System.out.println("熟练程度：" + skilled);
							String date = element.previousElementSibling().text();
							System.out.println("使用时间：" + date);
						}
					}
					if (them[1].equals("其他信息")) {
						// 其他信息
						System.out.println("=========其他信息=========");
						Element otherstable = root.select("table[class=v_table03]").get(i);
						Elements otherselements = otherstable.select("td[class=weight110][valign=top]");
						for (Element element : otherselements) {
							String title = element.text();
							String content = element.nextElementSibling().text();
							System.out.println(title + content);
						}
					}
				}

			}
		}
		return text;
	}

}