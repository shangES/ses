package icbc.cmis.flow.util;
import java.util.*;
import icbc.cmis.operation.*;
import icbc.cmis.second.pkg.*;
import icbc.cmis.base.*;

/**
 * 通用，单一意见查询保存
 * @author zjfh-zhangyz
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class util_content_msg_geneOp extends icbc.cmis.operation.CmisOperation {
	public util_content_msg_geneOp() {
		super();
	}

	private String webBasePath = (String)CmisConstance.getParameterSettings().get("webBasePath");

	public void execute() throws Exception, icbc.cmis.base.TranFailException {
		String action = getStringAt("opAction");
		try {
			if (action.equals("10001")) { //查询意见可修改
				querymsgformodify();
			} else if (action.equals("10002")) { //仅仅查询意见
				querymsgonly();
			} else if (action.equals("20001")) { //保存意见
				savemsg();
			} else if (action.equals("30001")) { //校验意见
				checkmsg();
			}
			//setOperationDataToSession();
		} catch (Exception ex) {
			//setOperationDataToSession();
			throw new TranFailException("util_content_msg_geneOp", "execute()", ex.getMessage(), ex.getMessage());
		}
	}

	/**
	 * 查询意见可修改
	 * @throws TranFailException
	 */
	public void querymsgformodify() throws TranFailException {
		try {
			//调用查询
			DBProcedureParamsDef def = new DBProcedureParamsDef("pack_approve_msg.query_msgformodify");
			def.addInParam("msg_g_entcode"); //客户号
			def.addInParam("msg_g_tradecode"); //申请号
			def.addInParam("msg_g_ordernum"); //序号
			def.addInParam("msg_g_employeecode"); //柜员
			def.addOutParam("out_flag");
			def.addOutParam("out_msg");
			DBProcedureAccessService proceSrv = new DBProcedureAccessService(this);
			proceSrv.executeProcedure(this.getOperationData(), def);
			proceSrv = null;
			String out_flag = this.getStringAt("out_flag");
			String out_msg = this.getStringAt("out_msg");
			this.setReplyPage(webBasePath + "/flow/util/util_content_msg_gene_input.jsp"); //修改页面

			//this.setOperationDataToSession();
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util", "util_content_msg4.querymsg", e.getMessage());
		}
	}

	/**
	 * 仅仅查询意见
	 * @throws TranFailException
	 */
	public void querymsgonly() throws TranFailException {
		try {
			//调用查询
			DBProcedureParamsDef def = new DBProcedureParamsDef("pack_approve_msg.query_msgonly");
			def.addInParam("msg_g_entcode"); //客户号
			def.addInParam("msg_g_tradecode"); //申请号
			def.addInParam("msg_g_ordernum"); //序号
			def.addOutParam("out_flag");
			def.addOutParam("out_msg");
			DBProcedureAccessService proceSrv = new DBProcedureAccessService(this);
			proceSrv.executeProcedure(this.getOperationData(), def);
			proceSrv = null;
			String out_flag = this.getStringAt("out_flag");
			String out_msg = this.getStringAt("out_msg");
			this.setReplyPage(webBasePath + "/flow/util/util_content_msg_gene_query.jsp"); //查询页面

			//this.setOperationDataToSession();
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util", "util_content_msg4.querymsg", e.getMessage());
		}
	}

	/**
	 * 意见保存
	 * @throws TranFailException
	 */
	public void savemsg() throws TranFailException {
		try {
			//调用保存
			util_content_msg_geneDao dao = new util_content_msg_geneDao(this);
			String msg_g_entcode = this.getStringAt("msg_g_entcode");
			String msg_g_tradecode = this.getStringAt("msg_g_tradecode");
			String msg_g_flowtype = this.getStringAt("msg_g_flowtype");
			String msg_g_tradetype = this.getStringAt("msg_g_tradetype");
			String msg_g_ordernum = this.getStringAt("msg_g_ordernum");
			String msg_g_ordercode = this.getStringAt("msg_g_ordercode");
			String msg_g_empareacode = this.getStringAt("msg_g_empareacode");
			String msg_g_employeecode = this.getStringAt("msg_g_employeecode");
			String msg_g_msginput = this.getStringAt("msg_g_msginput");
			HashMap hmap =
				dao.savemsg(
					msg_g_entcode,
					msg_g_tradecode,
					msg_g_flowtype,
					msg_g_tradetype,
					msg_g_ordernum,
					msg_g_ordercode,
					msg_g_empareacode,
					msg_g_employeecode,
					msg_g_msginput);

			String out_flag = (String)hmap.get("result");
			String out_msg = (String)hmap.get("reinfo");
			if (out_flag.equals("0")) {
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>ok</info>");
			} else {
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>" + out_msg + "</error>");
			}
			//this.setOperationDataToSession();
		} catch (TranFailException e) {
			//this.setOperationDataToSession();
			throw e;
		} catch (Exception e) {
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>" + e.getMessage() + "</error>");
		}
	}

	/**
	 * 意见输入校验
	 * @throws Exception
	 */
	public void checkmsg() throws Exception {
		try {
			DBProcedureParamsDef def = new DBProcedureParamsDef("pack_approve_msg.check_msg");
			def.addInParam("check_ent");
			def.addInParam("check_app");
			def.addInParam("check_num");
			def.addOutParam("out_flag");
			def.addOutParam("out_msg");
			DBProcedureAccessService proceSrv = new DBProcedureAccessService(this);
			proceSrv.executeProcedure(this.getOperationData(), def);
			proceSrv = null;
			String out_flag = this.getStringAt("out_flag");
			String out_msg = this.getStringAt("out_msg");
			if (out_flag.equals("0")) {
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>ok</info>");
			} else {
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>" + out_msg + "</error>");
			}
			this.setOperationDataToSession();
		} catch (TranFailException ee) {
			this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			this.setOperationDataToSession();
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>" + e.getMessage() + "</error>");
		}
	}

}