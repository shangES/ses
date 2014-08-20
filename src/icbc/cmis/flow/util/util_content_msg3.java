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
public class util_content_msg3 extends icbc.cmis.operation.CmisOperation {
	public util_content_msg3() {
		super();
	}

	private String webBasePath = (String) CmisConstance.getParameterSettings()
			.get("webBasePath");

	util_content_msg3Dao dao = null;

	public void execute() throws Exception, icbc.cmis.base.TranFailException {
		String action = getStringAt("opAction");
		try {

			//查询本人意见说明
			if (action.equals("20002")) {
				queryshowadvice();
			}
			//保存非调查人意见
			else if (action.equals("10001")) {
				saveadvice();
			}
			;
			setOperationDataToSession();
		} catch (Exception ex) {
			setOperationDataToSession();
			throw new TranFailException("util_content_msg3",
					"EBAlreadyApprove.execute()", ex.getMessage(),
					"非调查人为附加条件或限制性条款");
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
		String entcode = getStringAt("msg3_entcode"); //客户代码
		String tradecode = getStringAt("msg3_tradecode"); //业务申请号
		String ordernum = getStringAt("msg3_ordernum"); //序号
		String ordercode = getStringAt("msg3_ordercode"); //环节(主要用来区分是否是调查人)

		dao = new util_content_msg3Dao(this);
		Vector contentlist = new Vector();

		try {
			contentlist = dao.getadvicetxt(entcode, tradecode, ordernum,
					ordercode);
			this.setFieldValue("contentlist", contentlist);
			this.setFieldValue("entcode", entcode);
			this.setFieldValue("tradecode", tradecode);
			this.setFieldValue("ordernum", ordernum);
			this.setFieldValue("ordercode", ordercode);

			setReplyPage("/icbc/cmis/flow/util/util_selfshowmsg3_flow.jsp");

			this.setOperationDataToSession();
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("util_content_msg3",
					"EBAlreadyApprove.queryshowadvice", e.getMessage());
		}
	}

	/**
	 * <b>功能描述: </b> <br>
	 * <p>
	 * 保存非调查人意见
	 * </p>
	 * 
	 * @throws TranFailException
	 *  
	 */
	private void saveadvice() throws TranFailException {
		String formflag = "3";
		String numflag = getStringAt("numflag"); //客户代码
		String entcode = getStringAt("msg3_entcode"); //客户代码
		String tradecode = getStringAt("msg3_tradecode"); //业务申请号
		String tradetype = getStringAt("msg3_tradetype"); //申请种类
		String flowtype = getStringAt("msg3_flowtype"); //流程种类
		String ordernum = getStringAt("msg3_ordernum"); //序号
		String ordercode = getStringAt("msg3_ordercode"); //环节(主要用来区分是否是调查人)
		String process021 = getStringAt("msg3process021"); //本人意见说明
		String areacode = (String) this.getSessionData("AreaCode");
		String employeecode = (String) this.getSessionData("EmployeeCode");
		String rtag = "";
		try {
			DBProcedureParamsDef defcs = null;
			this.setFieldValue("in_numflag", numflag);
			this.setFieldValue("in_formflag", formflag);
			this.setFieldValue("in_entcode", entcode);
			this.setFieldValue("in_tradecode", tradecode);
			this.setFieldValue("in_tradetype", tradetype);
			this.setFieldValue("in_flowtype", flowtype);
			this.setFieldValue("in_ordernum", ordernum);
			this.setFieldValue("in_ordercode", ordercode);
			this.setFieldValue("in_areacode", areacode);
			this.setFieldValue("in_employeecode", employeecode);
			this.setFieldValue("in_process021", process021);
			defcs = new DBProcedureParamsDef(
					"pack_flow_unit.proc_saveselfadvice");
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
			defcs.addInParam("in_process021");
			defcs.addOutParam("ret_flag");
			defcs.addOutParam("ret_mess");
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
				rtag += "<rec    />";
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>"
						+ rtag + "</info>");
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>"
					+ e.getMessage() + "</error>");
			//throw new TranFailException("util_content_msg3",
			// "EBAlreadyApprove.saveadvice", e.getMessage());
		}

	}

}