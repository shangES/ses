/*
 * 创建日期 2006-3-7
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

package icbc.cmis.flow.util;

import java.util.*;
import icbc.cmis.base.*;

/**
 * @author 郑期彬 功能-审批历史列表查询 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class util_content_flowOp extends icbc.cmis.operation.CmisOperation {
	public util_content_flowOp() {
		super();
	}

	private String webBasePath = (String) CmisConstance.getParameterSettings()
			.get("webBasePath");

	util_content_flowDao dao = null;

	public void execute() throws Exception, icbc.cmis.base.TranFailException {
		String action = getStringAt("opAction");
		try {

			//查看历史审批列表
			if (action.equals("20001")) {
				queryhistory();
			} else if (action.equals("20002")) {
				queryshowcontent();
			}
			setOperationDataToSession();
		} catch (Exception ex) {
			setOperationDataToSession();
			throw new TranFailException("util_content_flow",
					"EBAlreadyApprove.execute()", ex.getMessage(), "审批历史列表");
		}

	}

	/**
	 * <b>功能描述: </b> <br>
	 * <p>
	 * 查询审批历史列表
	 * </p>
	 * 
	 * @throws TranFailException
	 *  
	 */
	private void queryhistory() throws TranFailException {

		String entcode = getStringAt("entcode"); //客户代码
		String tradecode = getStringAt("tradecode"); //业务申请号
		String rtag = "";

		String process005 = ""; //序号
		String process007 = ""; //处理人地区
		String process008 = ""; //处理人代码
		String employee_name = ""; //处理人名称
		String process006 = ""; //处理人环节
		String process011 = ""; //意见
		String process012 = ""; //非调查人为意见说明
		String process020 = ""; //辅助审核内容
		String process019 = ""; // 软性提示意见
		String process021 = ""; //附加条件或限制性条款
		String stepprocess008 = ""; //为调查环节处理人代码

		dao = new util_content_flowDao(this);
		Vector contentlist = new Vector();
		Vector steplist = new Vector();
		try {
			contentlist = dao.getqueryhistory(entcode, tradecode);
			steplist = dao.getstep(entcode, tradecode);
			this.setFieldValue("contentlist", contentlist);
			this.setFieldValue("entcode", entcode);

			for (int i = 0; i < contentlist.size(); i++) {
				Hashtable temp = (Hashtable) contentlist.get(i);
				String step = ""; //是否调查环节标志
				//recordnum=(String)temp.get(String.valueOf(i+1));
				for (int j = 0; j < steplist.size(); j++) {
					Hashtable ht = (Hashtable) steplist.get(j);
					stepprocess008 = (String) ht.get("process008");
				}
				process005 = (String) temp.get("process005");
				process007 = (String) temp.get("process007");
				process008 = (String) temp.get("process008");
				employee_name = (String) temp.get("employee_name");
				process006 = (String) temp.get("process006");
				process011 = (String) temp.get("process011");
				process012 = (String) temp.get("process012");
				process020 = (String) temp.get("process020");
				process019 = (String) temp.get("process019");
				if ((process008).equals(stepprocess008)) {
					step = "dc";
				}
				rtag += "<rec process005='" + process005 + "' process007='"
						+ process007 + "' process008='" + process008
						+ "' employee_name='" + employee_name
						+ "' process006='" + process006 + "' process011='"
						+ process011 + "' step='" + step + "'   />";

			}

			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>"
					+ rtag + "</info>");

			//this.setOperationDataToSession();
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("util_content_flow",
					"EBAlreadyApprove.queryhistory", e.getMessage());
		}
	}

	/**
	 * <b>功能描述: </b> <br>
	 * <p>
	 * 查询具体内容
	 * </p>
	 * 
	 * @throws TranFailException
	 *  
	 */
	private void queryshowcontent() throws TranFailException {

		String formflag = getStringAt("formflag"); //查询项标志 1,意见说明
		// 2,附加条件或限制性条款内容 3.辅助内容
		String entcode = getStringAt("entcode"); //客户代码
		String tradecode = getStringAt("tradecode"); //业务申请号
		String xh = getStringAt("xh"); //序号
		String step = getStringAt("step"); //环节(主要用来区分是否是调查人)

		dao = new util_content_flowDao(this);
		String returnURL = "";
		Vector contentlist = new Vector();
		Vector advicelist = null;
		try {
			contentlist = dao.getcontenttxt(formflag, entcode, tradecode, xh,
					step);
			if (formflag.equals("3")) {
				advicelist = dao.getadvicelist(entcode, tradecode, xh);
			}
			this.setFieldValue("contentlist", contentlist);
			this.setFieldValue("advicelist", advicelist);
			if ((formflag.equals("2")) && (step.equals("1"))) {
				setReplyPage("/icbc/cmis/flow/util/util_showcontentforsearcher_flow.jsp");
			} else {
				this.setFieldValue("formflag", formflag);
				this.setFieldValue("step", step);
				setReplyPage("/icbc/cmis/flow/util/util_showcontent_flow.jsp");
			}
			this.setOperationDataToSession();
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("util_content_flow",
					"EBAlreadyApprove.queryshowcontent", e.getMessage());
		}
	}

}