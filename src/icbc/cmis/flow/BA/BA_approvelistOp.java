package icbc.cmis.flow.BA;

import java.util.*;

import com.mk.framework.context.*;

import icbc.cmis.base.*;
import icbc.cmis.operation.*;
import icbc.cmis.tags.muiStr;

/**
 * 审批的待处理列表查询
 * 
 * @author zjfh-zhangyz
 * @version 20060307
 * 
 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class BA_approvelistOp extends CmisOperation {
	private String webBasePath = (String) CmisConstance.getParameterSettings()
			.get("webBasePath");

	private SqlTool sqltool = null;

	public BA_approvelistOp() {
		super();
	}

	private void init() throws Exception {
		sqltool = new SqlTool(this);
	}

	private String getLangCode() throws Exception {
		String LangCode = "zh_CN";
		return LangCode;
	}

	public void execute() throws java.lang.Exception, TranFailException {
		try {
			String opAction = this.getStringAt("opAction");

			if (opAction.equals("getmylist")) { //查询待本人处理列表
				getmylist();
			} else if (opAction.equals("qotherpageme")) { //本人处理列表翻页
				qotherpageme();
			} else if (opAction.equals("getourlist")) { //查询待本行处理列表
				getourlist();
			} else if (opAction.equals("qotherpageus")) { //本行处理列表翻页
				qotherpageus();
			} else if (opAction.equals("checkapply")) { //进行申请书输入校验
				checkapply();
			}
		} catch (TranFailException e) {
			setErrorCode(e.getErrorCode());
			setErrorCode(((TranFailException) e).getErrorCode());
			setErrorDispMsg(e.getDisplayMessage(e.getErrorCode()));
			setErrorLocation(e.getErrorLocation());
			setErrorMessage(e.getErrorMsg());
			this.setOperationDataToSession();
			setReplyPage(webBasePath + "/error.jsp");
		} catch (Exception ee) {
			throw ee;
		}
	}

	/**
	 * 得到待本人处理的审批列表
	 * 
	 * @throws Exception
	 */
	public void getmylist() throws Exception {
		try {
			UserContext ucontext = ContextFacade.getUserContext();
			String employeecode =ucontext.getUserId(); //柜员号
			String empareacode = ucontext.getCompanyid(); //柜员所属地区
			
			String ordercode = this.getStringAt("ordercode"); //当前环节代码
			String busitype = this.getStringAt("busitype"); //业务性质，0自营，1委托，2授信
			String runproc = ""; //是否调用存储过程查询未进流程业务，0否；1是
			try {
				runproc = this.getStringAt("runproc");
			} catch (Exception eee) {
				runproc = "0"; //默认不调用存储过程
			}
			this.setFieldValue("runproc", runproc);

			String spflag = ""; //特别标记，细分环节
			try {
				spflag = this.getStringAt("spflag");
			} catch (Exception eee) {
				spflag = "1";
			}
			this.setFieldValue("spflag", spflag);

			String now_page_me = ""; //页码
			try {
				now_page_me = this.getStringAt("now_page_me");
			} catch (Exception eee) {
				now_page_me = "1";
			}
			this.setFieldValue("now_page_me", now_page_me);

			BA_approvelistDao dao = new BA_approvelistDao(this);
			ArrayList alist = dao.querylist_me(employeecode, empareacode,
					ordercode, runproc, busitype);
			this.setFieldValue("alist", alist);

			String max_num = alist.size() + "";
			this.setFieldValue("max_num", max_num); //总数

			if (busitype.equals("0")) {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvelist_me.jsp");
			} else if (busitype.equals("1")) {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvewt_me.jsp");
			} else if (busitype.equals("2")) {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvesx_me.jsp");
			} else if (busitype.equals("4")) { //拨备
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvebb_me.jsp");
			} else {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvelist_me.jsp");
			}

			//this.setOperationDataToSession();
		} catch (TranFailException ee) {
			//this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA",
					"BA_approvelistOp.saveinfo()", e.getMessage(), e
							.getMessage());
		}
	}

	/**
	 * 本人处理列表翻页
	 * 
	 * @throws Exception
	 */
	public void qotherpageme() throws Exception {
		try {
			String now_page_me = this.getStringAt("now_page_me"); //页码
			String busitype = this.getStringAt("busitype"); //业务性质，0自营，1委托，2授信
			this.setFieldValue("now_page_me", now_page_me);
			if (busitype.equals("0")) {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvelist_me.jsp");
			} else if (busitype.equals("1")) {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvewt_me.jsp");
			} else if (busitype.equals("2")) {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvesx_me.jsp");
			} else if (busitype.equals("4")) { //拨备
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvebb_me.jsp");
			} else {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvelist_me.jsp");
			}
			this.setOperationDataToSession();
		} catch (TranFailException ee) {
			this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA",
					"BA_approvelistOp.saveinfo()", e.getMessage(), e
							.getMessage());
		}
	}

	/**
	 * 得到待本行处理的审批列表
	 * 
	 * @throws Exception
	 */
	public void getourlist() throws Exception {
		try {
			String employeecode = (String) this.getSessionData("EmployeeCode"); //柜员号
			String empareacode = (String) this.getSessionData("AreaCode"); //柜员所属地区
			String ordercode = this.getStringAt("ordercode"); //当前环节代码
			String busitype = this.getStringAt("busitype"); //业务性质，0自营，1委托，2授信
			String runproc = ""; //是否调用存储过程查询未进流程业务，0否；1是
			try {
				runproc = this.getStringAt("runproc");
			} catch (Exception eee) {
				runproc = "0"; //默认不调用存储过程
			}
			this.setFieldValue("runproc", runproc);

			String spflag = ""; //特别标记，细分环节
			try {
				spflag = this.getStringAt("spflag");
			} catch (Exception eee) {
				spflag = "1";
			}
			this.setFieldValue("spflag", spflag);

			String now_page_us = ""; //页码
			try {
				now_page_us = this.getStringAt("now_page_us");
			} catch (Exception eee) {
				now_page_us = "1";
			}
			this.setFieldValue("now_page_us", now_page_us);

			BA_approvelistDao dao = new BA_approvelistDao(this);
			ArrayList alist = dao.querylist_us(empareacode, employeecode,
					ordercode, runproc, busitype);

			this.setFieldValue("alist", alist);
			String max_num = alist.size() + "";
			this.setFieldValue("max_num", max_num); //总数

			if (busitype.equals("0")) {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvelist_us.jsp");
			} else if (busitype.equals("1")) {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvewt_us.jsp");
			} else if (busitype.equals("2")) {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvesx_us.jsp");
			} else if (busitype.equals("4")) { //拨备
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvebb_us.jsp");
			} else {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvelist_us.jsp");
			}

			this.setOperationDataToSession();
		} catch (TranFailException ee) {
			this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA",
					"BA_approvelistOp.saveinfo()", e.getMessage(), e
							.getMessage());
		}
	}

	/**
	 * 本行处理列表翻页
	 * 
	 * @throws Exception
	 */
	public void qotherpageus() throws Exception {
		try {
			String now_page_us = this.getStringAt("now_page_us"); //页码
			String busitype = this.getStringAt("busitype"); //业务性质，0自营，1委托，2授信
			this.setFieldValue("now_page_us", now_page_us);
			if (busitype.equals("0")) {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvelist_us.jsp");
			} else if (busitype.equals("1")) {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvewt_us.jsp");
			} else if (busitype.equals("2")) {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvesx_us.jsp");
			} else if (busitype.equals("4")) { //拨备
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvebb_us.jsp");
			} else {
				this.setReplyPage(webBasePath
						+ "/flow/B/BA/BA_approvelist_us.jsp");
			}
			this.setOperationDataToSession();
		} catch (TranFailException ee) {
			this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA",
					"BA_approvelistOp.saveinfo()", e.getMessage(), e
							.getMessage());
		}
	}

	/**
	 * 申请书校验
	 * 
	 * @throws Exception
	 */
	public void checkapply() throws Exception {
		try {
			String employeecode = (String) this.getSessionData("EmployeeCode"); //柜员号
			String empareacode = (String) this.getSessionData("AreaCode"); //柜员所属地区
			String chk_entcode = this.getStringAt("chk_entcode"); //客户号
			String chk_tradecode = this.getStringAt("chk_tradecode"); //申请号
			String chk_tradetype = this.getStringAt("chk_tradetype"); //申请种类
			String chk_ordercode = this.getStringAt("chk_ordercode"); //环节种类

			BA_approvelistDao dao = new BA_approvelistDao(this);

			HashMap hmap = dao.checkapply(chk_entcode, chk_tradecode,
					empareacode, employeecode, chk_ordercode, chk_tradetype);

			String o_opinion = (String) hmap.get("o_opinion");
			String o_message = (String) hmap.get("o_message");

			muiStr muistr = new muiStr("icbc.cmis.flow.BA.flow_BA", this
					.getLangCode());

			if (o_opinion.equals("-1")) {
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>"
						+ muistr.getStr("C000019")
						+ "："
						+ o_message
						+ "</error>");
			}
			if (o_opinion.equals("1")) {
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>"
						+ muistr.getStr("C000019")
						+ "："
						+ o_message
						+ "</error>");
			}
			if (o_opinion.equals("2")) {
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><hint>"
						+ muistr.getStr("C000018")
						+ "："
						+ o_message
						+ "</hint>");
			}
			if (o_opinion.equals("0")) {
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>"
						+ o_message + "</info>");
			}
			this.setOperationDataToSession();
		} catch (TranFailException ee) {
			this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA",
					"BA_approvelistOp.saveinfo()", e.getMessage(), e
							.getMessage());
		}
	}

}