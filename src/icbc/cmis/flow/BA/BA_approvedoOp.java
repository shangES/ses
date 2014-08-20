package icbc.cmis.flow.BA;

import java.util.*;

import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;

import icbc.cmis.base.*;
import icbc.cmis.operation.*;

/**
 * 审批处理
 * 
 * @author zjfh-zhangyz
 * 
 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class BA_approvedoOp extends CmisOperation {
	private String webBasePath = (String) CmisConstance.getParameterSettings()
			.get("webBasePath");

	private SqlTool sqltool = null;

	public BA_approvedoOp() {
		super();
	}

	private void init() throws Exception {
		sqltool = new SqlTool(this);
	}

	public void execute() throws java.lang.Exception, TranFailException {
		try {
			String opAction = this.getStringAt("opAction");

			if (opAction.equals("showinfo")) { //调查资料信息页面
				showinfo();
			} else if (opAction.equals("showcreditlike")) { //台账信息页面
				showcreditlike();
			} else if (opAction.equals("showdeal")) { //审批页面
				showdeal();
			} else if (opAction.equals("showextra")) { //扩展页面
				showextra();
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
	 * 调查资料信息页面
	 * 
	 * @throws Exception
	 */
	public void showinfo() throws Exception {
		try {
			UserContext ucontext = ContextFacade.getUserContext();
			String employeecode =ucontext.getUserId(); //柜员号
			String empareacode = ucontext.getCompanyid(); //柜员所属地区
			
			String approve_entcode = this.getStringAt("approve_entcode"); //客户号
			String approve_entname = this.getStringAt("approve_entname"); //客户名称
			String approve_tradecode = this.getStringAt("approve_tradecode"); //申请号
			String approve_tradetype = this.getStringAt("approve_tradetype"); //申请种类
			String approve_tradetypename = this
					.getStringAt("approve_tradetypename"); //申请种类名称
			String approve_flowtype = this.getStringAt("approve_flowtype"); //流程种类
			String approve_flowtypename = this
					.getStringAt("approve_flowtypename"); //流程种类名称
			String approve_ordernum = this.getStringAt("approve_ordernum"); //环节号
			String approve_ordercode = this.getStringAt("approve_ordercode"); //环节代码
			String approve_busitype = this.getStringAt("approve_busitype"); ////业务性质，0，自营，1，委托
			String approve_spflag = this.getStringAt("approve_spflag"); //细分环节标志
			String approve_returnurl = this.getStringAt("approve_returnurl"); //返回地址

			//取环节名称
			BA_approvedoDao dao = new BA_approvedoDao(this);
			String approve_ordername = dao.getordername(approve_flowtype,
					approve_ordercode);
			this.setFieldValue("approve_ordername", approve_ordername); //环节名称

			//是否发起行
			String approve_isfirst = dao.checkfirst(approve_entcode,
					approve_tradecode, approve_ordercode, empareacode,
					employeecode);
			this.setFieldValue("approve_isfirst", approve_isfirst); //是否发起环节

			//取调查资料tab
			getinfotabmane();

			//取URL
			String infourl = dao.getinfourl(approve_tradetype, approve_entcode,
					approve_entname, approve_tradecode);

			if (!infourl.equals("")) {
				this.setFieldValue("infourl", webBasePath + infourl);
			} else {
				this.setFieldValue("infourl", "");
			}

			//取扩展
			makeextratab();

			//取台帐
			isshowtab2();

			this.setReplyPage(webBasePath
					+ "/flow/B/BA/BA_approve_infoframe.jsp");
			//this.setOperationDataToSession();
		} catch (TranFailException ee) {
			//this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA",
					"BA_approveOp.showinfo()", e.getMessage(), e.getMessage());
		}
	}

	/**
	 * 生成台账页面
	 * 
	 * @throws Exception
	 */
	public void showcreditlike() throws Exception {
		try {
			UserContext ucontext = ContextFacade.getUserContext();
			String employeecode =ucontext.getUserId(); //柜员号
			String empareacode = ucontext.getCompanyid(); //柜员所属地区

			String approve_entcode = this.getStringAt("approve_entcode"); //客户号
			String approve_entname = this.getStringAt("approve_entname"); //客户名称
			String approve_tradecode = this.getStringAt("approve_tradecode"); //申请号
			String approve_tradetype = this.getStringAt("approve_tradetype"); //申请种类
			String approve_tradetypename = this
					.getStringAt("approve_tradetypename"); //申请种类名称
			String approve_flowtype = this.getStringAt("approve_flowtype"); //流程种类
			String approve_flowtypename = this
					.getStringAt("approve_flowtypename"); //流程种类名称
			String approve_ordernum = this.getStringAt("approve_ordernum"); //环节号
			String approve_ordercode = this.getStringAt("approve_ordercode"); //环节代码
			String approve_busitype = this.getStringAt("approve_busitype"); ////业务性质，0，自营，1，委托
			String approve_spflag = this.getStringAt("approve_spflag"); //细分环节标志
			String approve_returnurl = this.getStringAt("approve_returnurl"); //返回地址

			String approve_ordername = this.getStringAt("approve_ordername"); //环节名称
			String approve_isfirst = this.getStringAt("approve_isfirst"); //是否发起环节

			String returnurl = webBasePath
					+ "/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BA.BA_approvedoOp&opDataUnclear=true&opAction=showcreditlike"
					+ "&approve_entcode=" + approve_entcode
					+ "&approve_entname="
					+ java.net.URLEncoder.encode(approve_entname)
					+ "&approve_tradecode="
					+ java.net.URLEncoder.encode(approve_tradecode)
					+ "&approve_tradetype=" + approve_tradetype
					+ "&approve_tradetypename="
					+ java.net.URLEncoder.encode(approve_tradetypename)
					+ "&approve_flowtype=" + approve_flowtype
					+ "&approve_flowtypename="
					+ java.net.URLEncoder.encode(approve_flowtypename)
					+ "&approve_ordernum=" + approve_ordernum
					+ "&approve_ordercode=" + approve_ordercode
					+ "&approve_busitype=" + approve_busitype
					+ "&approve_spflag=" + approve_spflag
					+ "&approve_returnurl="
					+ java.net.URLEncoder.encode(approve_returnurl)
					+ "&approve_ordername=" + approve_ordername
					+ "&approve_isfirst=" + approve_isfirst;
			BA_approvedoDao dao = new BA_approvedoDao(this);
			ArrayList alist = dao.getcrediturl(approve_tradetype, returnurl,
					webBasePath, approve_entcode, approve_entname);

			this.setFieldValue("linklist", alist);

			//取调查资料tab
			getinfotabmane();
			//取扩展
			makeextratab();
			//取台帐
			isshowtab2();

			this.setReplyPage(webBasePath
					+ "/flow/B/BA/BA_approve_creditlink.jsp");
			//this.setOperationDataToSession();
		} catch (TranFailException ee) {
			//this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA",
					"BA_approveOp.showcreditlike()", e.getMessage(), e
							.getMessage());
		}
	}

	/**
	 * 进入审批处理页面
	 * 
	 * @throws Exception
	 */
	public void showdeal() throws Exception {
		try {
			UserContext ucontext = ContextFacade.getUserContext();
			String employeecode =ucontext.getUserId(); //柜员号
			String empareacode = ucontext.getCompanyid(); //柜员所属地区

			String approve_tradetype = this.getStringAt("approve_tradetype"); //申请种类
			String approve_ordercode = this.getStringAt("approve_ordercode"); //环节代码
			String approve_spflag = this.getStringAt("approve_spflag"); //细分环节标志

			BA_approvedoDao dao = new BA_approvedoDao(this);
			ArrayList ALfrag = dao.queryfragment(approve_ordercode,
					approve_spflag, approve_tradetype);
			ArrayList ALbtn = dao.querybutton(approve_ordercode,
					approve_spflag, approve_tradetype);

			this.setFieldValue("ALfrag", ALfrag);
			this.setFieldValue("ALbtn", ALbtn);

			//取调查资料tab
			getinfotabmane();
			//取扩展
			makeextratab();
			//取台帐
			isshowtab2();

			this.setReplyPage(webBasePath
					+ "/flow/B/BA/BA_approve_dealmain.jsp");
			//this.setOperationDataToSession();
		} catch (TranFailException ee) {
			//this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA",
					"BA_approveOp.showdeal()", e.getMessage(), e.getMessage());
		}
	}

	public void showextra() throws Exception {
		try {
			String extrataborder = this.getStringAt("extrataborder");
			String extrataburl = this.getStringAt("extrataburl");

			this.setFieldValue("extrataborder", extrataborder);
			this.setFieldValue("extrataburl", extrataburl);

			//取调查资料tab
			getinfotabmane();
			//取扩展
			makeextratab();
			//取台帐
			isshowtab2();

			this.setReplyPage(webBasePath
					+ "/flow/B/BA/BA_approve_extratab.jsp");
			//this.setOperationDataToSession();
		} catch (TranFailException ee) {
			//this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA",
					"BA_approveOp.makeextratab()", e.getMessage(), e
							.getMessage());
		}
	}

	/**
	 * 取扩展
	 * 
	 * @throws Exception
	 */
	public void makeextratab() throws Exception {
		try {
			String approve_entcode = this.getStringAt("approve_entcode"); //客户号
			String approve_entname = this.getStringAt("approve_entname"); //客户名称
			String approve_tradecode = this.getStringAt("approve_tradecode"); //申请号
			String approve_tradetype = this.getStringAt("approve_tradetype"); //申请种类
			BA_approvedoDao dao = new BA_approvedoDao(this);
			ArrayList exttablist = dao.makeextratab(approve_entcode,
					approve_entname, approve_tradecode, approve_tradetype,
					webBasePath);
			this.setFieldValue("exttablist", exttablist);
			//this.setOperationDataToSession();
		} catch (TranFailException ee) {
			//this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA",
					"BA_approveOp.makeextratab()", e.getMessage(), e
							.getMessage());
		}
	}

	/**
	 * 取调查资料tab名
	 * 
	 * @throws Exception
	 */
	public void getinfotabmane() throws Exception {
		try {
			String approve_tradetype = this.getStringAt("approve_tradetype"); //申请种类
			BA_approvedoDao dao = new BA_approvedoDao(this);
			String infotabname = dao.getinfoname(approve_tradetype);
			this.setFieldValue("infotabname", infotabname);
			//this.setOperationDataToSession();
		} catch (TranFailException ee) {
			//this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA",
					"BA_approveOp.makeextratab()", e.getMessage(), e
							.getMessage());
		}
	}

	/**
	 * 是否显示台帐
	 * 
	 * @throws Exception
	 */
	public void isshowtab2() throws Exception {
		try {
			String approve_tradetype = this.getStringAt("approve_tradetype"); //申请种类
			BA_approvedoDao dao = new BA_approvedoDao(this);
			String isshowtab2 = dao.isshowtab2(approve_tradetype);
			this.setFieldValue("isshowtab2", isshowtab2);
			//this.setOperationDataToSession();
		} catch (TranFailException ee) {
			//this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA",
					"BA_approveOp.makeextratab()", e.getMessage(), e
							.getMessage());
		}
	}

}