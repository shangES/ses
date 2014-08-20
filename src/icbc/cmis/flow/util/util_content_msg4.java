package icbc.cmis.flow.util;

import java.util.*;
import icbc.cmis.operation.*;
import icbc.cmis.second.pkg.*;
import icbc.cmis.base.*;

/**
 * 授信意见输入片段
 * 
 * @author zjfh-zhangyz
 * 
 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class util_content_msg4 extends icbc.cmis.operation.CmisOperation {
	public util_content_msg4() {
		super();
	}

	private String webBasePath = (String) CmisConstance.getParameterSettings()
			.get("webBasePath");

	public void execute() throws Exception, icbc.cmis.base.TranFailException {
		String action = getStringAt("opAction");
		try {
			if (action.equals("10001")) {
				querymsg();
			} else if (action.equals("20001")) {
				savemsg();
			}
			setOperationDataToSession();
		} catch (Exception ex) {
			setOperationDataToSession();
			throw new TranFailException("util_content_msg4", "execute()", ex
					.getMessage(), "授信意见输入");
		}
	}

	public void querymsg() throws TranFailException {
		try {
			//调用查询
			DBProcedureParamsDef def = new DBProcedureParamsDef(
					"pack_approvesx.query_sxmsg");
			def.addInParam("msg4_entcode"); //客户号
			def.addInParam("msg4_tradecode"); //申请号
			def.addInParam("msg4_ordernum"); //序号
			def.addInParam("msg4_employeecode"); //柜员
			def.addOutParam("out_flag");
			def.addOutParam("out_msg");
			DBProcedureAccessService proceSrv = new DBProcedureAccessService(
					this);
			proceSrv.executeProcedure(this.getOperationData(), def);
			proceSrv = null;
			String out_flag = this.getStringAt("out_flag");
			String out_msg = this.getStringAt("out_msg");
			this.setReplyPage(webBasePath
					+ "/flow/util/util_content_msg4input.jsp");
			this.setOperationDataToSession();
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util",
					"util_content_msg4.querymsg", e.getMessage());
		}
	}

	public void savemsg() throws TranFailException {
		try {
			//调用查询
			DBProcedureParamsDef def = new DBProcedureParamsDef(
					"pack_approvesx.update_sxmsg");
			def.addInParam("msg4_entcode"); //客户号
			def.addInParam("msg4_tradecode"); //申请号
			def.addInParam("msg4_flowtype"); //流程种类
			def.addInParam("msg4_tradetype"); //申请种类
			def.addInParam("msg4_ordernum"); //序号
			def.addInParam("msg4_ordercode"); //环节
			def.addInParam("msg4_empareacode"); //地区
			def.addInParam("msg4_employeecode"); //柜员
			def.addInParam("ta340041013"); //意见
			def.addOutParam("out_flag");
			def.addOutParam("out_msg");
			DBProcedureAccessService proceSrv = new DBProcedureAccessService(
					this);
			proceSrv.executeProcedure(this.getOperationData(), def);
			proceSrv = null;
			String out_flag = this.getStringAt("out_flag");
			String out_msg = this.getStringAt("out_msg");
			if (out_flag.equals("0")) {
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>ok</info>");
			} else {
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>"
						+ out_msg + "</error>");
			}
			this.setOperationDataToSession();
		} catch (TranFailException e) {
			this.setOperationDataToSession();
			throw e;
		} catch (Exception e) {
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>"
					+ e.getMessage() + "</error>");
		}
	}

}