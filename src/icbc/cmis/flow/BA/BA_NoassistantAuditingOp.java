package icbc.cmis.flow.BA;

import java.util.*;
import java.sql.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.*;
import icbc.cmis.second.pkg.*;

public class BA_NoassistantAuditingOp extends CmisOperation {
	private String webBasePath = (String)CmisConstance.getParameterSettings().get("webBasePath");
	private SqlTool sqltool = null;

	public BA_NoassistantAuditingOp() {
		super();
	}

	public void execute() throws java.lang.Exception, TranFailException {
		try {
			String opAction = this.getStringAt("opAction");

			if (opAction.equals("getlist")) { //查询待本人辅助列表
				getlist();
			} else if (opAction.equals("qotherpage")) { //本人辅助列表翻页
				qotherpageme();
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

	/**
	 * 查询列表
	 * @throws Exception
	 */
	public void getlist() throws Exception {
		try {
			String employeecode = (String)this.getSessionData("EmployeeCode"); //柜员号
			String empareacode = (String)this.getSessionData("AreaCode"); //柜员所属地区
			this.setFieldValue("now_page_me", "1");

			BA_NoassistantAuditingDao dao = new BA_NoassistantAuditingDao(this);
			ArrayList alist = dao.querylist(empareacode, employeecode);
			this.setFieldValue("alist", alist);

			String max_num = alist.size() + "";
			this.setFieldValue("max_num", max_num); //总数

			this.setReplyPage(webBasePath + "/flow/B/BA/BA_NoassistantAuditing.jsp");
			this.setOperationDataToSession();
		} catch (TranFailException ee) {
			this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA", "BA_NoassistantAuditingOp.getlist()", e.getMessage(), e.getMessage());
		}
	}

	/**
	 * 翻页
	 * @throws Exception
	 */
	public void qotherpageme() throws Exception {
		try {
			String now_page_me = this.getStringAt("now_page_me"); //页码
			this.setFieldValue("now_page_me", now_page_me);
			this.setReplyPage(webBasePath + "/flow/B/BA/BA_NoassistantAuditing.jsp");
			this.setOperationDataToSession();
		} catch (TranFailException ee) {
			this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException(
				"icbc.cmis.flow.BA",
				"BA_NoassistantAuditingOp.qotherpageme()",
				e.getMessage(),
				e.getMessage());
		}
	}

}