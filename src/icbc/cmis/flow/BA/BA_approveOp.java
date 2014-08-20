package icbc.cmis.flow.BA;

import java.util.*;
import java.sql.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.*;
import icbc.cmis.second.pkg.*;

/**
 * 审批处理
 * @author zjfh-zhangyz
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class BA_approveOp extends CmisOperation {
	private String webBasePath = (String)CmisConstance.getParameterSettings().get("webBasePath");
	private SqlTool sqltool = null;

	public BA_approveOp() {
		super();
	}

	private void init() throws Exception {
		sqltool = new SqlTool(this);
	}

	public void execute() throws java.lang.Exception, TranFailException {
		try {
			String opAction = this.getStringAt("opAction");

			if (opAction.equals("getmylist")) { //查询待本人处理列表
				getmylist();
			} else if (opAction.equals("getourlist")) { //查询待本行处理列表
				getourlist();
			} else if (opAction.equals("showinfo")) { //调查资料信息页面
				showinfo();
			} else if (opAction.equals("showcreditlike")) { //台账信息页面
				showcreditlike();
			} else if (opAction.equals("showdeal")) { //审批页面
				showdeal();
			} else if (opAction.equals("showextra")) { //扩展页面

			}

		} catch (TranFailException e) {
			setErrorCode(e.getErrorCode());
			setErrorCode(((TranFailException)e).getErrorCode());
			setErrorDispMsg(e.getDisplayMessage(e.getErrorCode()));
			setErrorLocation(e.getErrorLocation());
			setErrorMessage(e.getErrorMsg());
			this.setOperationDataToSession();
			setReplyPage(webBasePath + "/error.jsp");
		} catch (Exception ee) {
			throw ee;
		}
	}

	public void getmylist() throws Exception {
		try {
			String employeecode = (String)this.getSessionData("EmployeeCode"); //柜员号
			String empareacode = (String)this.getSessionData("AreaCode"); //柜员所属地区

			this.setReplyPage(webBasePath + "/flow/B/BA/BA_approvelist_me.jsp");
		} catch (TranFailException ee) {
			this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.CCGE.EA", "EA_basicinfoOp.saveinfo()", e.getMessage(), e.getMessage());
		}
	}

	public void getourlist() throws Exception {
		try {
			String employeecode = (String)this.getSessionData("EmployeeCode"); //柜员号
			String empareacode = (String)this.getSessionData("AreaCode"); //柜员所属地区

			this.setReplyPage(webBasePath + "/flow/B/BA/BA_approvelist_us.jsp");
		} catch (TranFailException ee) {
			this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.CCGE.EA", "EA_basicinfoOp.saveinfo()", e.getMessage(), e.getMessage());
		}
	}

	public void showinfo() throws Exception {
		try {
			String employeecode = (String)this.getSessionData("EmployeeCode"); //柜员号
			String empareacode = (String)this.getSessionData("AreaCode"); //柜员所属地区

			this.setReplyPage(webBasePath + "/flow/B/BA/BA_approve_infoframe.jsp");
		} catch (TranFailException ee) {
			this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.CCGE.EA", "EA_basicinfoOp.saveinfo()", e.getMessage(), e.getMessage());
		}
	}

	public void showcreditlike() throws Exception {
		try {
			String employeecode = (String)this.getSessionData("EmployeeCode"); //柜员号
			String empareacode = (String)this.getSessionData("AreaCode"); //柜员所属地区

			this.setReplyPage(webBasePath + "/flow/B/BA/BA_approve_creditlink.jsp");
		} catch (TranFailException ee) {
			this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.CCGE.EA", "EA_basicinfoOp.saveinfo()", e.getMessage(), e.getMessage());
		}
	}

	public void showdeal() throws Exception {
		try {
			String employeecode = (String)this.getSessionData("EmployeeCode"); //柜员号
			String empareacode = (String)this.getSessionData("AreaCode"); //柜员所属地区

			this.setReplyPage(webBasePath + "/flow/B/BA/BA_approve_dealmain.jsp");
		} catch (TranFailException ee) {
			this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.CCGE.EA", "EA_basicinfoOp.saveinfo()", e.getMessage(), e.getMessage());
		}
	}

}