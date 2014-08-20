package icbc.cmis.flow.util;

import java.util.*;
import java.sql.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.*;
import icbc.cmis.second.pkg.*;

public class util_content_doassistOp extends CmisOperation {
	private String webBasePath = (String) CmisConstance.getParameterSettings()
			.get("webBasePath");

	private SqlTool sqltool = null;

	public util_content_doassistOp() {
		super();
	}

	private void init() throws Exception {
		sqltool = new SqlTool(this);
	}

	public void execute() throws java.lang.Exception, TranFailException {
		try {
			String opAction = this.getStringAt("opAction");
			if (opAction.equals("asssave")) {
				asssave();
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
	 * 保存辅助审批意见说明
	 * 
	 * @throws Exception
	 */
	public void asssave() throws Exception {
		String employeecode = (String) this.getSessionData("EmployeeCode"); //柜员号
		String empareacode = (String) this.getSessionData("AreaCode"); //柜员所属地区
		String sysdate = CmisConstance.getWorkDate("yyyyMMdd"); //时间
		String entcode = this.getStringAt("approve_entcode"); //客户号
		String tradecode = this.getStringAt("approve_tradecode"); //申请号
		String ordernum = this.getStringAt("approve_ordernum"); //环节号
		String approve_returnurl = this.getStringAt("approve_returnurl");
		String advice006 = "";
		try {
			advice006 = this.getStringAt("ass_advice006");
		} catch (Exception eee) {
			advice006 = "";
		}
		String advice008 = this.getStringAt("ass_advice008");
		util_content_doassistDao dao = new util_content_doassistDao(this);
		HashMap hmap = dao.saveass(entcode, tradecode, ordernum, employeecode,
				empareacode, advice006, advice008, sysdate);
		String result = (String) hmap.get("result");
		String info = (String) hmap.get("info");
		if (result.equals("0")) {
			this.setFieldValue("okTitle", genMsg.getErrMsg("099997"));
			this.setFieldValue("okMsg", info);
			this.setFieldValue("okReturn", approve_returnurl);
			this.setReplyPage(webBasePath + "/ok.jsp");
		} else {
			this.setFieldValue("infoTitle", genMsg.getErrMsg("099993"));
			this.setFieldValue("infoMsg", info);
			this.setFieldValue("infoReturn", approve_returnurl);
			this.setReplyPage(webBasePath + "/util/util_info.jsp");
		}
		this.setOperationDataToSession();
	}

}