/*
 * 创建日期 2006-3-2
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

package icbc.cmis.flow.util;

import java.util.*;
import icbc.cmis.operation.*;
import icbc.cmis.second.pkg.*;
import icbc.cmis.base.*;

/**
 * @author 郑期彬 功能-显示各字段信息 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class util_content_msg2Op extends icbc.cmis.operation.CmisOperation {
	public util_content_msg2Op() {
		super();
	}

	private String webBasePath = (String) CmisConstance.getParameterSettings()
			.get("webBasePath");

	util_content_msg2Dao dao = null;

	public void execute() throws Exception, icbc.cmis.base.TranFailException {
		String action = getStringAt("opAction");
		try {

			//查询本人意见说明
			if (action.equals("20002")) {
				queryshowadvice();
			}
			//保存调查人意见
			else if (action.equals("10001")) {
				saveadviceforsearcher();
			}

			setOperationDataToSession();
		} catch (Exception ex) {
			setOperationDataToSession();
			throw new TranFailException("util_content_msg2",
					"EBAlreadyApprove.execute()", ex.getMessage(), "调查分析意见");
		}

	}

	/**
	 * <b>功能描述: </b> <br>
	 * <p>
	 * 查询本人意见说明
	 * </p>
	 * 
	 * @throws TranFailException
	 *  
	 */
	private void queryshowadvice() throws TranFailException {
		String entcode = getStringAt("msg2_entcode"); //客户代码
		String tradecode = getStringAt("msg2_tradecode"); //业务申请号
		String ordernum = getStringAt("msg2_ordernum"); //序号
		String ordercode = getStringAt("msg2_ordercode"); //环节(主要用来区分是否是调查人)
		String firstflag = getStringAt("firstflag");
		dao = new util_content_msg2Dao(this);
		Vector contentlist = new Vector();

		try {
			if (firstflag.equals("1")) {
				contentlist = dao.getadvicetxt(entcode, tradecode, ordernum,
						ordercode);
			}
			if (firstflag.equals("0")) {
				contentlist = dao.getselfadvicetxt(entcode, tradecode,
						ordernum, ordercode);
			}

			this.setFieldValue("contentlist", contentlist);
			this.setFieldValue("entcode", entcode);
			this.setFieldValue("tradecode", tradecode);
			this.setFieldValue("ordernum", ordernum);
			this.setFieldValue("ordercode", ordercode);
			this.setFieldValue("firstflag", firstflag);
			if (firstflag.equals("1")) {
				setReplyPage("/icbc/cmis/flow/util/util_selfshowmsg2_flow.jsp");
			}
			if (firstflag.equals("0")) {
				setReplyPage("/icbc/cmis/flow/util/util_selfshowmsg_flow.jsp");
			}

			this.setOperationDataToSession();
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("util_content_msg2",
					"EBAlreadyApprove.queryshowadvice", e.getMessage());
		}
	}

	/**
	 * <b>功能描述: </b> <br>
	 * <p>
	 * 保存调查人意见
	 * </p>
	 * 
	 * @throws TranFailException
	 *  
	 */
	private void saveadviceforsearcher() throws TranFailException {
		String numflag = getStringAt("numflag"); //客户代码
		String entcode = getStringAt("msg2_entcode"); //客户代码
		String tradecode = getStringAt("msg2_tradecode"); //业务申请号
		String tradetype = getStringAt("msg2_tradetype"); //申请种类
		String flowtype = getStringAt("msg2_flowtype"); //流程种类
		String ordernum = getStringAt("msg2_ordernum"); //序号
		String ordercode = getStringAt("msg2_ordercode"); //环节(主要用来区分是否是调查人)
		String firstflag = getStringAt("firstflag");
		String areacode = (String) this.getSessionData("AreaCode");
		String employeecode = (String) this.getSessionData("EmployeeCode");
		this.setFieldValue("firstflag", firstflag);
		String process012 = ""; /* 调查人为附加条件或限制性条款 /本人意见说明 */
		String process013 = ""; /* 客户现金流量分析及预测 */
		String process014 = ""; /* 担保情况分析 */
		String process015 = ""; /* 风险分析 */
		String process016 = ""; /* 收益分析 */
		String process027 = ""; /* 风险分析 */
		String process028 = ""; /* 收益分析 */
		if (firstflag.equals("1")) {
			process012 = getStringAt("msg2_process012"); /* 调查人为附加条件或限制性条款 */
			process013 = getStringAt("msg2_process013"); /* 客户现金流量分析及预测 */
			process014 = getStringAt("msg2_process014"); /* 担保情况分析 */
			process015 = getStringAt("msg2_process015"); /* 风险分析 */
			process016 = getStringAt("msg2_process016"); /* 收益分析 */
			process027 = getStringAt("msg2_process027"); /* 风险分析 */
			process028 = getStringAt("msg2_process028"); /* 风险分析 */

		}
		if (firstflag.equals("0")) {
			process012 = getStringAt("msgprocess012"); //本人意见说明
		}

		String rtag = "";

		try {
			DBProcedureParamsDef defcs = null;
			this.setFieldValue("in_numflag", numflag);
			this.setFieldValue("in_entcode", entcode);
			this.setFieldValue("in_tradecode", tradecode);
			this.setFieldValue("in_tradetype", tradetype);
			this.setFieldValue("in_flowtype", flowtype);
			this.setFieldValue("in_ordernum", ordernum);
			this.setFieldValue("in_ordercode", ordercode);
			this.setFieldValue("in_areacode", areacode);
			this.setFieldValue("in_employeecode", employeecode);
			if (firstflag.equals("1")) {
				this.setFieldValue("in_process013", process013);
				this.setFieldValue("in_process014", process014);
				this.setFieldValue("in_process015", process015);
				this.setFieldValue("in_process016", process016);
				this.setFieldValue("in_process012", process012);
				this.setFieldValue("in_process027", process027);
				this.setFieldValue("in_process028", process028);
			}
			if (firstflag.equals("0")) {

				this.setFieldValue("in_process012", process012);
			}
			if (firstflag.equals("1")) {
				defcs = new DBProcedureParamsDef(
						"pack_flow_unit.proc_saveselfadviceforsearcher");
				defcs.addInParam("in_numflag");
				defcs.addInParam("in_entcode");
				defcs.addInParam("in_tradecode");
				defcs.addInParam("in_flowtype");
				defcs.addInParam("in_tradetype");
				defcs.addInParam("in_ordernum");
				defcs.addInParam("in_ordercode");
				defcs.addInParam("in_areacode");
				defcs.addInParam("in_employeecode");
				defcs.addInParam("in_process013");
				defcs.addInParam("in_process014");
				defcs.addInParam("in_process015");
				defcs.addInParam("in_process016");
				defcs.addInParam("in_process012");
				defcs.addInParam("in_process027");
				defcs.addInParam("in_process028");
				defcs.addOutParam("ret_flag");
				defcs.addOutParam("ret_mess");
			}
			if (firstflag.equals("0")) {

				defcs = new DBProcedureParamsDef(
						"pack_flow_unit.proc_saveselfadvice");
				this.setFieldValue("in_formflag", "1");
				defcs.addInParam("in_numflag");
				defcs.addInParam("in_formflag");
				defcs.addInParam("in_entcode");
				defcs.addInParam("in_tradecode");
				defcs.addInParam("in_flowtype");
				defcs.addInParam("in_tradetype");
				defcs.addInParam("in_ordernum");
				defcs.addInParam("in_ordercode");
				defcs.addInParam("in_areacode");
				defcs.addInParam("in_employeecode");
				defcs.addInParam("in_process012");
				defcs.addOutParam("ret_flag");
				defcs.addOutParam("ret_mess");
			}

			DBProcedureAccessService dbProcService = new DBProcedureAccessService(
					this);
			dbProcService.executeProcedure(getOperationData(), defcs);
			dbProcService = null;
			String retCode = getStringAt("ret_flag");
			String retText = getStringAt("ret_mess");

			if (!retCode.equals("0")) {
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>"
						+ retText + "</error>");
			} else {
				rtag += "<rec/>";
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>"
						+ rtag + "</info>");
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>"
					+ e.getMessage() + "</error>");
			//throw new TranFailException("util_content_msg2Op",
			// "EBAlreadyApprove.saveadviceforsearcher", e.getMessage());
		}

	}
}