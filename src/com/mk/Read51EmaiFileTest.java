package com.mk;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.mk.framework.constance.Constance;
import com.mk.framework.grid.util.DateUtil;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.recruitment.entity.RecruitPost;
import com.mk.resume.entity.EducationExperience;
import com.mk.resume.entity.Resume;
import com.mk.resume.entity.TrainingExperience;
import com.mk.resume.entity.WorkExperience;

public class Read51EmaiFileTest {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		File file = new File("E:/Noname1.html");
		getDocument(file);
	}

	private static String getDocument(File html) throws Exception {
		String text = "";
		// 设置编码集
		org.jsoup.nodes.Document doc = Jsoup.parse(html, "GBK");

		// 简历信息
		Elements root = doc.select("body>table");
		
		System.out.println("PPPPPPPPP="+root.select("table[class=v_table02]").size());
		System.out.println("ffffff"+root.select("table[class=v_table02]").get(3).previousElementSibling());
		if (root.size() < 2) {
			System.out.println("模板不对请检查!");
			return null;
		}

		// 开始解析
		if (!root.isEmpty()) {
			System.out.println("==================================");
			System.out.println("==========用户基本信息=============");
			System.out.println("==================================");
			// <table border="0" cellpadding="0" cellspacing="0"
			// width="100%">
			// <tbody>
			// <tr>
			// <td colspan="4" height="26">
			// <span
			// class="blue1"><b>一年以上工作经验&nbsp;&nbsp;|&nbsp;&nbsp;女&nbsp;&nbsp;|&nbsp;&nbsp;22岁(1990年5月17日)
			// &nbsp;|&nbsp;&nbsp;未婚&nbsp;&nbsp;|&nbsp;&nbsp;166cm</b>
			// </span></td>
			// <td align="center" rowspan="6" valign="middle" width="17%">
			// <img height="110"
			// src="http://img01.51jobcdn.com/im/2009/resumetemplate/resume_match_womanpic.gif"
			// width="90" /> <span> (ID:75078323) </span></td>
			// </tr>
			// <tr>
			// <td height="20" width="10%">
			// 居住地：</td>
			// <td height="20" width="42%">
			// 杭州-西湖区</td>
			// <td height="20" width="11%">
			// &nbsp;</td>
			// <td height="20" width="20%">
			// &nbsp;</td>
			// </tr>
			// <tr>
			// <td height="20">
			// 电　话：</td>
			// <td colspan="3" height="20">
			// 13666617720（手机）</td>
			// </tr>
			// <tr>
			// <td height="20">
			// E-mail：</td>
			// <td colspan="3" height="20">
			// <a class="blue1"
			// href="mailto:644926428@qq.com&#10;&#9;&#9;&#9;&#9;&#9;&#9;&#9;&#9;&#9;&#9;&#9;&#9;&#9;&#9;&#9;&#9;&#9;   ">
			// 644926428@qq.com </a> <span id="qqstid"><img
			// align="absmiddle"
			// src="http://wpa.qq.com/pa?p=2:&#10;644926428&#10;:45" />
			// </span></td>
			// </tr>
			// </tbody>
			// </table>

			// 用户基本信息
			Elements users = root.select("table[width=97%]");

			// 姓名
			Elements strong = users.select("strong");
			String name = strong.text();
			System.out.println("姓名====" + name);

			// 性别,出生日期
			Elements sexday = users.select("b");
			String[] array = sexday.text().split("\\|");
			if (array.length >= 5) {
				// 工作年限(选项)
				String workagename = array[0];
				System.out.println("工作年限(选项)====" + workagename);

				// 性别
				String sexname = array[1];
				System.out.println("性别====" + sexname);

				// 出生日期
				String birthday = array[2];
				birthday = birthday.substring(birthday.indexOf("(") + 1, birthday.indexOf(")"));
				System.out.println("出生日期====" + DateUtil.parseChina(birthday));
			}

			// 居住地
			Elements address = users.select("td[height=20][width=10%]+td");
			System.out.println("居住地：" + address.text());

			// 电话,E-mail
			Elements te = users.select("td[colspan=3][height=20]");
			System.out.println("个数：" + te.size());
			if (!te.isEmpty() && te.size() >= 2) {
				// System.out.println("电话邮件：" + te);
				int num = te.size();

				// 电话
				String mobile = te.get(num - 3).text();
				if (mobile != null && mobile.length() > 4) {
					mobile = mobile.substring(0, mobile.length() - 4);

					System.out.println("电话====" + mobile);
				}

				// 邮件
				String email = te.get(num - 2).text();
				System.out.println("邮件====" + email);

			}

			System.out.println("==================================");
			System.out.println("==========最高学历=============");
			System.out.println("==================================");
			// <td colspan="2" width="49%">
			// <table border="0" cellpadding="0" cellspacing="0"
			// style="margin: 8px auto; padding: 0px 0px 0px 10px; line-height: 22px;"
			// width="100%">
			// <tbody>
			// <tr>
			// <td colspan="2">
			// <span class="blue" style="font-size: 14px;"><b>最高学历</b>
			// </span></td>
			// </tr>
			// <tr>
			// <td width="59">
			// 学　历：</td>
			// <td width="230">
			// 大专</td>
			// </tr>
			// <tr>
			// <td>
			// 专　业：</td>
			// <td>
			// 旅游管理</td>
			// </tr>
			// <tr>
			// <td height="22">
			// 学　校：</td>
			// <td>
			// 杭州职业技术学院</td>
			// </tr>
			// </tbody>
			// </table>
			// </td>
			// 学历
			Elements culturehtm = root.select("td[colspan=2][width=49%]");
			Elements culturehtmname = culturehtm.select("table>tbody>tr>td[width=230]");
			String culturename = culturehtmname.text();
			System.out.println("最高学历====" + culturename);

			// System.out.println("==================================");
			// System.out.println("==========主题信息 =============");
			// System.out.println("==================================");

			Elements themes = root.select("table[class=v_table02]");
			// System.out.println("主题信息=======" + themes.size());
			// System.out.println("主题信息=======" + themes);

			System.out.println("==================================");
			System.out.println("==========工作经验 =============");
			System.out.println("==================================");
			// <table border="0" cellspacing="0" cellpadding="0"
			// style="width:700px;line-height:22px;" class="v_table02">
			// <tbody>
			// <tr>
			// <td colspan="2" valign="top">1998 /7--至今：上海达克网络系统工程有限公司</td>
			// </tr>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">所属行业：</td>
			// <td style="width:590px" valign="top" width="610">计算机软件</td>
			// </tr>
			// <tr>
			// <td colspan="2" valign="top"> <strong>产品及技术支持部</strong>
			// &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			// <strong>部门经理</strong> </td>
			// </tr>
			// <tr>
			// <td id="Cur_Val" colspan="2" valign="top">1.负责大客户的售前支持 <br
			// />2.制定新产品的技术支持方案 <br />3.负责部门日常管理，协调部门内部工作 <br
			// />4.负责销售人员和技术支持工程师的技术培训。</td>
			// </tr>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">汇报对象：</td>
			// <td style="width:590px" valign="top" width="610">CTO</td>
			// </tr>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">下属人数：</td>
			// <td style="width:590px" valign="top" width="610">30人</td>
			// </tr>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">证 明
			// 人：</td>
			// <td style="width:590px" valign="top" width="610">David.yan</td>
			// </tr>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">
			// 工作业绩：</td>
			// <td style="width:590px" id="Cur_Val" valign="top"
			// width="610">主持开发了如下网络系统工程的技术支持方案： <br />1、华东大学国家图象重点试验室网络工程 <br
			// />2、国际展览中心网络系统工程 <br />3、商业银行网络改造工程</td>
			// </tr>
			// <tr>
			// <td colspan="2" valign="top">
			// <hr size="1" width="100%" align="right" style="color:#e0e0e0" />
			// </td>
			// </tr>
			// <tr>
			// <td colspan="2" valign="top">1996 /1--1998
			// /6：美国Multi-Media计算机有限公司上海办事处</td>
			// </tr>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">所属行业：</td>
			// <td style="width:590px" valign="top" width="610">计算机软件</td>
			// </tr>
			// <tr>
			// <td colspan="2" valign="top"> <strong>信息技术部</strong>
			// &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			// <strong>技术支持工程师</strong> </td>
			// </tr>
			// <tr>
			// <td id="Cur_Val" colspan="2" valign="top">1.负责产品售前、售后技术支持 <br
			// />2.负责销售渠道和用户的技术培训 <br />3.负责产品技术文档的翻译工作</td>
			// </tr>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">汇报对象：</td>
			// <td style="width:590px" valign="top" width="610">部门经理</td>
			// </tr>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">下属人数：</td>
			// <td style="width:590px" valign="top" width="610">5人</td>
			// </tr>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">离职原因：</td>
			// <td style="width:590px" valign="top" width="610">合同到期</td>
			// </tr>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">
			// 工作业绩：</td>
			// <td style="width:590px" id="Cur_Val" valign="top"
			// width="610">作为Project Leader, 领导了“企业管理自动化”项目的开发。</td>
			// </tr>
			// <tr>
			// <td colspan="2" valign="top">
			// <hr size="1" width="100%" align="right" style="color:#e0e0e0" />
			// </td>
			// </tr>
			// <tr>
			// <td colspan="2" valign="top">1993 /7--1996
			// /10：上海华佳信息系统有限公司（少于50人）</td>
			// </tr>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">所属行业：</td>
			// <td style="width:590px" valign="top" width="610">计算机软件</td>
			// </tr>
			// <tr>
			// <td colspan="2" valign="top"> <strong>信息技术部</strong>
			// &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			// <strong>系统工程师</strong> </td>
			// </tr>
			// <tr>
			// <td id="Cur_Val" colspan="2"
			// valign="top">1.负责为用户进行SUN工作站及UNIX系统集成项目支持、服务及培训； <br />2.参与开发SUN
			// SPARC兼容工作站； <br />3.用户售前咨询。</td>
			// </tr>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">汇报对象：</td>
			// <td style="width:590px" valign="top" width="610">部门经理</td>
			// </tr>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">下属人数：</td>
			// <td style="width:590px" valign="top" width="610">3人</td>
			// </tr>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">
			// 工作业绩：</td>
			// <td style="width:590px" id="Cur_Val" valign="top"
			// width="610">1、参与建立了环美连锁超市收银系统 <br />2、编制公司内部人事财务管理系统</td>
			// </tr>
			// </tbody>
			// </table>
			if (themes.size() >= 3) {
				org.jsoup.nodes.Element workexperiences = themes.get(3);
				//System.out.println("工作经验======" + workexperiences);

				// 工作经验
				Elements workunits = workexperiences.select("td[id=Cur_Val][colspan=2][valign=top]");
				for (org.jsoup.nodes.Element node : workunits) {

					// 工作单位
					org.jsoup.nodes.Element workunit = node.parent().previousElementSibling().previousElementSibling().previousElementSibling();
					String tmpworkunit = workunit.text();
					String[] ls = tmpworkunit.split("：");
					
					// 开始时间
					String datese = ls[0];
					String[] datels = datese.split("--");
					String startdate = datels[0] + "/01";
					String enddate = datels[1] + "/01";

					System.out.println("开始时间=====" + startdate);
					System.out.println("结束时间=====" + enddate);

					// 职位
					org.jsoup.nodes.Element posationdom = node.parent().previousElementSibling();
					Elements posationname = posationdom.select("strong:eq(1)");
					System.out.println("职位======" + posationname.text());

					// 工作单位
					String dwname = ls[1];
					int idx = dwname.indexOf(" [");
					if (idx > 1) {
						dwname = dwname.substring(0, idx);
						if (dwname.length() > 50) {
							dwname = dwname.substring(0, 50);
						}
					}

					System.out.println("工作单位=====" + dwname);

					// 职责描述
					System.out.println("职责描述=====" + node.text());
					System.out.println("***************************");
				}

			}

			System.out.println("==================================");
			System.out.println("==========教育经历 =============");
			System.out.println("==================================");
			// <table border="0" cellspacing="0" cellpadding="0"
			// style="width:700px;line-height:22px;" class="v_table02">
			// <tbody>
			// <tr>
			// <td style="width:180px" class="weight180" valign="top"
			// height="30">1989 /9--1993 /7</td>
			// <td style="width:220px;padding-right:10px;"
			// class="weight220 padding10" valign="top">上海交通大学</td>
			// <td style="width:240px;padding-right:10px;" class="padding10"
			// valign="top">电气工程及其自动化</td>
			// <td style="width:60px" class="weight60" valign="top">本科</td>
			// </tr>
			// <tr align="left">
			// <td id="Cur_Val" colspan="4" valign="top"
			// height="30">多次获奖学金，并担任系学生会部长职务</td>
			// </tr>
			// <tr align="left">
			// <td colspan="4" valign="top">
			// <hr size="1" width="100%" style="color:#e0e0e0" /> </td>
			// </tr>
			// <tr>
			// <td style="width:180px" class="weight180" valign="top"
			// height="30">1986 /9--1989 /7</td>
			// <td style="width:220px;padding-right:10px;"
			// class="weight220 padding10" valign="top">上海市华东师大二附中</td>
			// <td style="width:240px;padding-right:10px;" class="padding10"
			// valign="top">若无合适选项请在此处填 写</td>
			// <td style="width:60px" class="weight60" valign="top">高中</td>
			// </tr>
			// </tbody>
			// </table>

			if (themes.size() >= 4) {
				org.jsoup.nodes.Element workexperiences = themes.get(4);
				//System.out.println("教育经历======" + workexperiences);

				Elements nodes = workexperiences.select("td[class=weight180]");
				for (org.jsoup.nodes.Element node : nodes) {
					String tmpworkexperiences = node.text();
					// 开始时间
					String[] datels = tmpworkexperiences.split("--");
					String startdate = datels[0] + "/01";
					String enddate = datels[1] + "/01";

					System.out.println("开始时间======" + startdate);
					System.out.println("结束时间======" + enddate);

					// 學校
					org.jsoup.nodes.Element schooldom = node.nextElementSibling();
					System.out.println("學校======" + schooldom.text());

					// 专业
					org.jsoup.nodes.Element specialtysdom = node.nextElementSibling().nextElementSibling();
					System.out.println("专业======" + specialtysdom.text());

					// 学历
					org.jsoup.nodes.Element culturesdom = node.nextElementSibling().nextElementSibling().nextElementSibling();
					System.out.println("学历======" + culturesdom.text());
					System.out.println("***************************");
				}

			}

			System.out.println("==================================");
			System.out.println("==========培训经历 =============");
			System.out.println("==================================");
			// <table border="0" cellspacing="0" cellpadding="0"
			// style="width:700px;line-height:22px;" class="v_table02">
			// <tbody>
			// <tr>
			// <td style="width:110px" class="weight110" valign="top">1996
			// /3--1996 /4</td>
			// <td style="width:220px;padding-right:10px;"
			// class="weight220 padding10" valign="top">上海微软高级技术培训中心</td>
			// <td style="width:220px;padding-right:10px;"
			// class="weight220 padding10" valign="top">系统工程师</td>
			// <td style="width:150px" valign="top"></td>
			// </tr>
			// <tr align="left">
			// <td id="Cur_Val" colspan="4" valign="top">微软公司系统工程师证书</td>
			// </tr>
			// </tbody>
			// </table>
			if (themes.size() >= 5) {
				org.jsoup.nodes.Element trainingexperiences = themes.get(5);
				//System.out.println("培训经历======" + trainingexperiences);

				Elements nodes = trainingexperiences.select("td[class=weight110]");
				for (org.jsoup.nodes.Element node : nodes) {
					String tmpworkexperiences = node.text();
					// 开始时间
					String[] datels = tmpworkexperiences.split("--");
					String startdate = datels[0] + "/01";
					String enddate = datels[1] + "/01";

					System.out.println("开始时间======" + startdate);
					System.out.println("结束时间======" + enddate);

					// 培训机构
					org.jsoup.nodes.Element traininginstitutionsdom = node.nextElementSibling();
					System.out.println("培训机构======" + traininginstitutionsdom.text());

					// 培训内容
					org.jsoup.nodes.Element trainingcontentsdom = node.nextElementSibling().nextElementSibling();
					System.out.println("培训内容======" + trainingcontentsdom.text());

					// 证书
					org.jsoup.nodes.Element certificatesdom = node.nextElementSibling().nextElementSibling().nextElementSibling();
					System.out.println("证书======" + certificatesdom.text());

					System.out.println("***************************");
				}
			}
		}

		System.out.println("==================================");
		System.out.println("==========应聘信息 =============");
		System.out.println("==================================");
		// 应聘信息
		Elements mycandidates = doc.select("body>table>tbody>tr>td>table>tbody>tr>td[align=left][class=blue1][valign=top]");

		if (!mycandidates.isEmpty()) {
			// 应聘职位
			org.jsoup.nodes.Element postnameel = mycandidates.get(0);
			String postname = postnameel.text();
			System.out.println("应聘职位======" + postname);


			// 投递时间
			org.jsoup.nodes.Element candidatestime = mycandidates.get(2);
			System.out.println("投递时间======" + candidatestime.text());

		}
		return text;
	}
}