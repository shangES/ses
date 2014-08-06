package com.mk.system.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.company.entity.Budgetype;
import com.mk.company.service.BudgetypeService;
import com.mk.framework.chart.ChartModel;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.util.StringUtils;
import com.mk.mycandidates.service.MyCandidatesService;
import com.mk.recruitment.entity.About;
import com.mk.recruitment.service.AboutService;
import com.mk.resume.service.ResumeEamilService;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.OptionType;
import com.mk.system.service.OptionService;
import com.mk.todo.entity.MsgData;
import com.mk.todo.entity.TodoPam;
import com.mk.todo.service.TodoService;

@Controller
public class HomePageAction {
	@Autowired
	private AboutService aboutService;
	@Autowired
	private OptionService optionService;
	@Autowired
	private ResumeEamilService resumeEamilService;
	@Autowired
	private MyCandidatesService myCandidatesService;
	@Autowired
	private TodoService todoService;
	@Autowired
	private BudgetypeService budgetypeService;

	// 登陆
	@RequestMapping("/login.do")
	public ModelAndView handleLogin(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 判断session里是否有用户信息
		if (request.getSession().getAttribute("user") == null) {
			// 如果是ajax请求响应头会有，x-requested-with；
			if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
				// 在响应头设置session状态
				response.setHeader("sessionstatus", "timeout");
			}
		}
		return new ModelAndView("/pages/login/login.jsp");
	}

	// 主框架
	@RequestMapping("/index.do")
	public ModelAndView handleHomepage(ModelMap map, String page) throws Exception {
		if (page != null) {
			return new ModelAndView("/pages/mainframe/" + page + ".jsp");
		}

		// 消息
		TodoPam pam = new TodoPam();
		MsgData data = todoService.getMsgData(pam);
		map.put("msg", data);
		return new ModelAndView("/pages/mainframe/main.jsp");
	}

	// 系统管理
	@RequestMapping("/system.do")
	public ModelAndView system(String page) throws Exception {
		return new ModelAndView("/pages/system/" + page + ".jsp");
	}

	// 工作流
	@RequestMapping("/bpmn.do")
	public ModelAndView bpmn(String page) throws Exception {
		return new ModelAndView("/pages/bpmn/" + page + ".jsp");
	}

	// 公司管理
	@RequestMapping("/company.do")
	public ModelAndView company(String page) throws Exception {
		return new ModelAndView("/pages/company/" + page + ".jsp");
	}

	// 部门管理
	@RequestMapping("/department.do")
	public ModelAndView department(String page) throws Exception {
		return new ModelAndView("/pages/department/" + page + ".jsp");
	}

	// 员工管理
	@RequestMapping("/employee.do")
	public ModelAndView employee(String page) throws Exception {
		return new ModelAndView("/pages/employee/" + page + ".jsp");
	}

	// 请假管理
	@RequestMapping("/vacation.do")
	public ModelAndView vacation(String page) throws Exception {
		return new ModelAndView("/pages/vacation/" + page + ".jsp");
	}
	
	// 年休假管理
	@RequestMapping("/annual.do")
	public ModelAndView annual(String page) throws Exception {
		return new ModelAndView("/pages/annual/" + page + ".jsp");
	}

	// 通讯录管理
	@RequestMapping("/addresslist.do")
	public ModelAndView addresslist(String page) throws Exception {
		return new ModelAndView("/pages/addresslist/" + page + ".jsp");
	}

	// 合同管理
	@RequestMapping("/contract.do")
	public ModelAndView contract(String page) throws Exception {
		return new ModelAndView("/pages/contract/" + page + ".jsp");
	}

	// 通讯录外网
	@RequestMapping("/address.do")
	public ModelAndView address(String page) throws Exception {
		if (page == null)
			return new ModelAndView("/pages/addresslist/web/index.jsp");
		return new ModelAndView("/pages/addresslist/web/" + page + ".jsp");
	}

	// 数据报表
	@RequestMapping("/report.do")
	public ModelAndView report(ModelMap map, String page) {
		UserContext uc = ContextFacade.getUserContext();
		// 员工类别统计表
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotEmpty(page) && page.equals("PCD_0201")) {
			OptionType opt = optionService.getOptionTypeByCode(OptionConstance.SEX);
			List<OptionList> list = optionService.getOptionListByOptionTypeId(opt.getOptiontypeguid());
			map.put("options", list);
			map.put("optionNum", list.size());

			// list页面展现性别
			for (OptionList model : list) {
				sb.append("{id: '");
				sb.append("SEX_" + model.getCode());
				sb.append("', header: '");
				sb.append(model.getName());
				sb.append("', width :80 ,headAlign:'center',align:'center'},");
			}
		} else if (StringUtils.isNotEmpty(page) && page.equals("PCD_0202")) {
			OptionType optiontype = optionService.getOptionTypeByCode(OptionConstance.CULTURE);
			List<OptionList> list = optionService.getOptionListByOptionTypeId(optiontype.getOptiontypeguid());
			map.put("options", list);
			map.put("optionNum", list.size());

			// list页面展现学历情况
			for (OptionList model : list) {
				sb.append("{id: '");
				sb.append("CULTURE_" + model.getCode());
				sb.append("', header: '");
				sb.append(model.getName());
				sb.append("', width :80 ,headAlign:'center',align:'center'},");
			}
		} else if (StringUtils.isNotEmpty(page) && page.equals("PCD_0205")) {
			OptionType optiontype = optionService.getOptionTypeByCode(OptionConstance.POLITICS);
			List<OptionList> list = optionService.getOptionListByOptionTypeId(optiontype.getOptiontypeguid());

			map.put("options", list);
			map.put("optionNum", list.size());

			// list页面展现政治面貌情况
			for (OptionList model : list) {
				sb.append("{id: '");
				sb.append("POLITICS_" + model.getCode());
				sb.append("', header: '");
				sb.append(model.getName());
				sb.append("', width :120 ,headAlign:'center',align:'center'},");
			}
		} else if (StringUtils.isNotEmpty(page) && page.equals("PCD_0206")) {
			OptionType optiontype = optionService.getOptionTypeByCode(OptionConstance.AUTHNAME);
			List<OptionList> list = optionService.getOptionListByOptionTypeId(optiontype.getOptiontypeguid());
			map.put("options", list);
			map.put("optionNum", list.size());

			// list页面展现职称认证情况
			for (int i = 0; i < list.size(); i++) {
				OptionList model = list.get(i);
				sb.append("{id: '");
				sb.append("AUTHNAME_" + (i + 1));
				sb.append("', header: '");
				sb.append(model.getName());
				sb.append("', width :100 ,headAlign:'center',align:'center'},");
			}
		} else if (StringUtils.isNotEmpty(page) && page.equals("PCD_0208")) {
			OptionType optiontype = optionService.getOptionTypeByCode(OptionConstance.MARRIED);
			List<OptionList> list = optionService.getOptionListByOptionTypeId(optiontype.getOptiontypeguid());

			OptionType opt = optionService.getOptionTypeByCode(OptionConstance.SEX);
			List<OptionList> sexlist = optionService.getOptionListByOptionTypeId(opt.getOptiontypeguid());
			map.put("sexs", sexlist);
			map.put("sexNum", sexlist.size());

			map.put("marrieds", list);
			map.put("marriedNum", list.size());

			// list页面展现性别婚姻情况
			int index = 1;
			for (OptionList model : list) {
				for (OptionList sex : sexlist) {
					sb.append("{id: '");
					sb.append("COLUMN_" + index);
					sb.append("', header: '");
					sb.append(sex.getName());
					sb.append("', width :100 ,headAlign:'center',align:'center'},");
					index++;
				}
			}
		}else if (StringUtils.isNotEmpty(page) && page.equals("PCD_0308")) {
			if(uc!=null){
				List<Budgetype> list = budgetypeService.getBudgetypeByCompanyId(uc.getCompanyid());
				map.put("budgetypes", list);
				map.put("budgetypeNum", list.size());
				// list页面展现编制情况
				int index=1;
				for(Budgetype model:list){
					sb.append("{id: '");
					sb.append("BZ_" + index);
					sb.append("', header: '");
					sb.append("编制数");
					sb.append("', width :100 ,headAlign:'center',align:'center'},");
					sb.append("{id: '");
					sb.append("SY_" + index);
					sb.append("', header: '");
					sb.append("在岗数");
					sb.append("', width :100 ,headAlign:'center',align:'center'},");
					sb.append("{id: '");
					sb.append("QB_" + index);
					sb.append("', header: '");
					sb.append("缺编数");
					sb.append("', width :100 ,headAlign:'center',align:'center'},");
					index++;
				}
			}
		}
		map.put("colsOption", sb.toString());
		return new ModelAndView("/pages/report/" + page + ".jsp");
	}

	// 职位类别管理
	@RequestMapping("/category.do")
	public ModelAndView category(String page) throws Exception {
		return new ModelAndView("/pages/recruitment/category/" + page + ".jsp");
	}

	// 外网人员管理
	@RequestMapping("/webUser.do")
	public ModelAndView webUser(String page) throws Exception {
		return new ModelAndView("/pages/recruitment/webuser/" + page + ".jsp");
	}

	// 工作地点管理
	@RequestMapping("/workplace.do")
	public ModelAndView workplace(String page) throws Exception {
		return new ModelAndView("/pages/recruitment/workplace/" + page + ".jsp");
	}

	// 轮播图片管理
	@RequestMapping("/carousel.do")
	public ModelAndView carousel(String page) throws Exception {
		return new ModelAndView("/pages/recruitment/carousel/" + page + ".jsp");
	}

	// 网站首页
	@RequestMapping("/web/index.do")
	public ModelAndView web(String page) throws Exception {
		return new ModelAndView("/web/index.jsp");
	}

	// 资讯管理
	@RequestMapping("/news.do")
	public ModelAndView news(String page) throws Exception {
		return new ModelAndView("/pages/recruitment/news/" + page + ".jsp");
	}

	// 招聘职位管理
	@RequestMapping("/recruitpost.do")
	public ModelAndView recruitpost(String page) throws Exception {
		return new ModelAndView("/pages/recruitment/recruitpost/" + page + ".jsp");
	}

	// 招聘计划管理
	@RequestMapping("/recruitprogram.do")
	public ModelAndView recruitprogram(String page) throws Exception {
		return new ModelAndView("/pages/recruitprogram/" + page + ".jsp");
	}

	// 关于华数管理
	@RequestMapping("/about.do")
	public ModelAndView about(ModelMap map, String page) throws Exception {
		if (StringUtils.isNotEmpty(page) && page.equals("tab")) {
			List<About> list = aboutService.getAllAbout();
			if (list == null || list.isEmpty())
				return null;
			map.put("tabs", list);
		}
		return new ModelAndView("/pages/recruitment/about/" + page + ".jsp");
	}

	// 简历管理
	@RequestMapping("/mycandidates.do")
	public ModelAndView mycandidates(String page) throws Exception {
		return new ModelAndView("/pages/mycandidates/" + page + ".jsp");
	}

	// 人才库管理
	@RequestMapping("/resume.do")
	public ModelAndView resume(String page) throws Exception {
		return new ModelAndView("/pages/resume/" + page + ".jsp");
	}

	// 加载word简历管理
	@RequestMapping("/resumeemail.do")
	public ModelAndView resumeemail(ModelMap map, String page, String id) throws Exception {
		/*
		 * // word标签 if (StringUtils.isNotEmpty(id) && page.equals("form_word"))
		 * { ResumeEamil model = resumeEamilService.getResumeEamilById(id); if
		 * (model != null) { map.put("filepath", model.getFilepath()); } }
		 */return new ModelAndView("/pages/resume/resumeeamil/" + page + ".jsp");
	}

	// 编制管理
	@RequestMapping("/quota.do")
	public ModelAndView quota(String page) throws Exception {
		return new ModelAndView("/pages/quota/" + page + ".jsp");
	}

	// 待入职人才管理
	@RequestMapping("/person.do")
	public ModelAndView person(String page) throws Exception {
		return new ModelAndView("/pages/person/" + page + ".jsp");
	}

	// 第三方机构管理
	@RequestMapping("/thirdpartner.do")
	public ModelAndView thridPartner(String page) throws Exception {
		return new ModelAndView("/pages/thirdpartner/" + page + ".jsp");
	}

	// 第三方机构人员管理
	@RequestMapping("/ThirdPartyUser.do")
	public ModelAndView ThirdPartyUser(String page) throws Exception {
		return new ModelAndView("/pages/thirdpartner/" + page + ".jsp");
	}

	// 面试管理
	@RequestMapping("/auditionrecord.do")
	public ModelAndView auditionrecord(String page) throws Exception {
		return new ModelAndView("/pages/auditionrecord/" + page + ".jsp");
	}

	// 面试地点管理
	@RequestMapping("/auditionaddress.do")
	public ModelAndView auditionaddress(String page) throws Exception {
		return new ModelAndView("/pages/auditionaddress/" + page + ".jsp");
	}

	// 体检记录
	@RequestMapping("examinationrecord.do")
	public ModelAndView examinationrecord(String page) throws Exception {
		return new ModelAndView("/pages/person/examinationrecord/" + page + ".jsp");
	}

	// 消息框
	@RequestMapping("/notices.do")
	@ResponseBody
	public List<ChartModel> getNotices(ModelMap map) throws Exception {
		List<ChartModel> chartmodels = myCandidatesService.countMsg();
		if (chartmodels != null && !chartmodels.isEmpty()) {
			return chartmodels;
		}
		return null;
	}
}