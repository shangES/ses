package icbc.cmis.flow.BTN;

import java.util.*;
import java.sql.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.*;
import icbc.cmis.second.pkg.*;

/**
 * 为了实现通用的审批按钮事件而建立此方法
 * 通用否决处理，包括否决前的校验处理
 * @author zjfh-zhangyz
 * 2006-11-22 / 18:01:42
 *
 */
public class gene_foujueOp extends CmisOperation {
	private String webBasePath = (String)CmisConstance.getParameterSettings().get("webBasePath");

	public gene_foujueOp() {
		super();
	}

	private void init() throws Exception {
	}

	public void execute() throws java.lang.Exception, TranFailException {
		try {
			String opAction = getStringAt("opAction");
			if (opAction.equals("10000")) { //校验
				tocheck();
			} else if (opAction.equals("10001")) { //否决处理
				tofoujue();
			}
		} catch (TranFailException e) {
			setErrorCode(e.getErrorCode());
			setErrorCode(((TranFailException)e).getErrorCode());
			setErrorDispMsg(e.getDisplayMessage(e.getErrorCode()));
			setErrorLocation(e.getErrorLocation());
			setErrorMessage(e.getErrorMsg());
			//this.setOperationDataToSession();
			setReplyPage(webBasePath + "/error.jsp");
		} catch (Exception ee) {
			throw ee;
		}
	}

	public void tofoujue() throws Exception {
		try {
			String approve_returnurl = this.getStringAt("approve_returnurl"); //返回地址

			HashMap hmapinfo = new HashMap();
			hmapinfo.put("employeecode", this.getStringAt("employeecode"));
			hmapinfo.put("empareacode", this.getStringAt("empareacode"));
			hmapinfo.put("empareaname", this.getStringAt("empareaname"));

			hmapinfo.put("approve_entcode", this.getStringAt("approve_entcode"));
			hmapinfo.put("approve_entname", this.getStringAt("approve_entname"));
			hmapinfo.put("approve_tradecode", this.getStringAt("approve_tradecode"));
			hmapinfo.put("approve_tradetype", this.getStringAt("approve_tradetype"));
			hmapinfo.put("approve_tradetypename", this.getStringAt("approve_tradetypename"));
			hmapinfo.put("approve_flowtype", this.getStringAt("approve_flowtype"));
			hmapinfo.put("approve_flowtypename", this.getStringAt("approve_flowtypename"));
			hmapinfo.put("approve_ordernum", this.getStringAt("approve_ordernum"));
			hmapinfo.put("approve_ordercode", this.getStringAt("approve_ordercode"));
			hmapinfo.put("approve_ordername", this.getStringAt("approve_ordername"));
			hmapinfo.put("approve_busitype", this.getStringAt("approve_busitype"));

			hmapinfo.put("approve_isfirst", this.getStringAt("approve_isfirst"));

			hmapinfo.put("nextflow", this.getStringAt("nextflow"));
			hmapinfo.put("nextflowname", this.getStringAt("nextflowname"));
			hmapinfo.put("nextareacode", this.getStringAt("nextareacode"));
			hmapinfo.put("nextareaname", this.getStringAt("nextareaname"));
			hmapinfo.put("nextemployeecode", this.getStringAt("nextemployeecode"));
			hmapinfo.put("nextemployeename", this.getStringAt("nextemployeename"));

			gene_foujueDao dao = new gene_foujueDao(this);
			HashMap hmaprst = dao.dofoujue(hmapinfo);

			String out_flag = (String)hmaprst.get("flag");
			String out_msg = (String)hmaprst.get("msg");

			if (out_flag.equals("0")) {
				this.setFieldValue("okTitle", genMsg.getErrMsg("099997"));
				this.setFieldValue("okMsg", out_msg);
				this.setFieldValue("okReturn", approve_returnurl);
				this.setReplyPage("/icbc/cmis/ok.jsp");
			} else {
				this.setFieldValue("infoTitle", genMsg.getErrMsg("099993"));
				this.setFieldValue("infoMsg", out_msg);
				this.setFieldValue("infoReturn", approve_returnurl);
				this.setReplyPage(webBasePath + "/util/util_info.jsp");
			}

			//this.setOperationDataToSession();
		} catch (TranFailException ee) {
			//this.setOperationDataToSession();
			throw ee;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BTN", "gene_foujueOp.tofoujue()", e.getMessage(), e.getMessage());
		}
	}

	public void tocheck() throws TranFailException {
		try {
			HashMap hmapinfo = new HashMap();
			hmapinfo.put("employeecode", this.getStringAt("employeecode"));
			hmapinfo.put("empareacode", this.getStringAt("empareacode"));
			hmapinfo.put("approve_entcode", this.getStringAt("approve_entcode"));
			hmapinfo.put("approve_tradecode", this.getStringAt("approve_tradecode"));
			hmapinfo.put("approve_tradetype", this.getStringAt("approve_tradetype"));
			hmapinfo.put("approve_flowtype", this.getStringAt("approve_flowtype"));
			hmapinfo.put("approve_ordernum", this.getStringAt("approve_ordernum"));
			hmapinfo.put("approve_ordercode", this.getStringAt("approve_ordercode"));
			hmapinfo.put("approve_busitype", this.getStringAt("approve_busitype"));

			gene_foujueDao dao = new gene_foujueDao(this);
			HashMap hmap = dao.directcheck(hmapinfo);

			String sFlag = (String)hmap.get("flag");
			String sMsg = (String)hmap.get("msg");
			if (sFlag.equals("0")) {
				this.setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>ok</info>");
			} else {
				this.setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>" + sMsg + "</error>");
			}
			//this.setOperationDataToSession();
		} catch (TranFailException e) {
			//this.setOperationDataToSession();
			throw e;
		} catch (Exception e) {
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>" + e.getMessage() + "</error>");
		}
	}

}