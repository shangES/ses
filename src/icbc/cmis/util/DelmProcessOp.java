package icbc.cmis.util;

import java.sql.*;
import icbc.cmis.base.*;
import java.util.*;
import icbc.cmis.second.pkg.*;
import icbc.cmis.operation.*;
import icbc.cmis.util.*;
import icbc.cmis.base.CmisConstance;


public class DelmProcessOp extends CmisOperation {
	private String webBasePath = (String) CmisConstance.getParameterSettings().get("webBasePath");
	
	public DelmProcessOp() {
		super();
	}
	
	public void execute() throws Exception {
		try {
			String action = getStringAt("opAction");
			if (action.equals("20001")) {
				//empquery();
			} else if(action.equals("20002")) {
				processdelete();
			}
			setOperationDataToSession();
		}catch (Exception e) {
			setErrorCode("error util.DelEmpRole");
			setErrorDispMsg("交易平台错误：交易处理失败");
			setErrorLocation("util.DelEmpRole.execute()");
			String msg = e.getMessage();
			if (msg == null)
				msg = e.toString();
			setErrorMessage(msg);
			setReplyPage(webBasePath + "/error.jsp");
			setOperationDataToSession();
		}
	}
	
	public void processdelete() throws Exception	{
		try {
			String str_customer_code = getStringAt("del_customer_code");
			this.setFieldValue("in_customer_code",str_customer_code);
			char firststr = str_customer_code.charAt(0);
			
			DBProcedureParamsDef def;
			if ((firststr>='a' && firststr<='z') ||(firststr>='A' && firststr<='Z'))
				def = new DBProcedureParamsDef("pack_delmprocess.proc_delete_group");
			else
				def = new DBProcedureParamsDef("pack_delmprocess.proc_delete_single");
			def.addInParam("del_customer_code");
			def.addOutParam("out_flag");
			def.addOutParam("out_msg");
			DBProcedureAccessService proceSrv = new DBProcedureAccessService(this);
			int returnCode = proceSrv.executeProcedure(this.getOperationData(), def);
			String reinfo = (String)getStringAt("out_msg");
			proceSrv = null;
			if (returnCode == 0) {
				this.setFieldValue("okTitle", "OK!");
				this.setFieldValue("okMsg", "删除" + str_customer_code + "企业授信流程成功");
				this.setFieldValue("okReturn",webBasePath + "/util/util_DelmProcessFirst.jsp");
				this.setReplyPage("/icbc/cmis/ok.jsp");
			} else {
				this.setFieldValue("infoTitle", "FAIL!");
				this.setFieldValue("infoMsg", "删除" + str_customer_code + "企业授信流程失败" + reinfo);
				this.setFieldValue("infoReturn",webBasePath + "/util/util_DelmProcessFirst.jsp");
				this.setReplyPage("/icbc/cmis/util/util_info.jsp");				
			}
		}catch (Exception e) {
			throw new TranFailException(
				"processdelete()",
				"DelmProcessOp.processdelete()",
				e.getMessage(),
				"");
		}
	}
	
	
	
	
	
}