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
 * @author 郑期彬
 * 功能-显示各字段信息
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class util_content_msg extends icbc.cmis.operation.CmisOperation {
	public util_content_msg() {
			super();
	}
	private String webBasePath =
				(String) CmisConstance.getParameterSettings().get("webBasePath");
	util_content_msgDao dao = null;
	public void execute() throws Exception, icbc.cmis.base.TranFailException {
		String action = getStringAt("opAction");
		try {

			//查询本人意见说明
			 if (action.equals("20001")) {
				queryshowadvice();
					}
					//保存本人意见说明
			else if (action.equals("10001")) {
				saveadvice();
			};
			
		}

		catch (Exception ex) {
			setOperationDataToSession();
			throw new TranFailException("util_content_msg", "EBAlreadyApprove.execute()", ex.getMessage(), "意见说明");
		}
		
	}
	

/**
	* <b>功能描述: </b><br>
	* <p>查询本人意见说明</p>
	* @throws TranFailException
	*  
	*/
	private void queryshowadvice() throws TranFailException {
	
		String entcode =getStringAt("entcode");   //客户代码
		String tradecode =getStringAt("tradecode");//业务申请号
		String ordernum =getStringAt("ordernum");  //序号
		String ordercode =getStringAt("ordercode"); //环节(主要用来区分是否是调查人)

		dao = new util_content_msgDao(this);
		Vector contentlist = new Vector();

		try {
			contentlist = dao.getadvicetxt(entcode,tradecode,ordernum,ordercode);
			this.setFieldValue("contentlist", contentlist);
			this.setFieldValue("entcode", entcode);
			this.setFieldValue("tradecode", tradecode);
			this.setFieldValue("ordernum", ordernum);
			this.setFieldValue("ordercode", ordercode);
			
			 
			 setReplyPage("/icbc/cmis/flow/util/util_selfshowcontent_msg.jsp");
			 
			this.setOperationDataToSession();
		}
		catch (TranFailException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TranFailException("util_content_msg", "EBAlreadyApprove.queryshowadvice", e.getMessage());
		}
	}
	
/**
	* <b>功能描述: </b><br>
	* <p>保存非调查人意见</p>
	* @throws TranFailException
	*  
	*/
	private void saveadvice() throws TranFailException {
		String entcode =getStringAt("entcode");   //客户代码
		String tradecode =getStringAt("tradecode");//业务申请号
		String ordernum =getStringAt("ordernum");  //序号
		String ordercode =getStringAt("ordercode"); //环节(主要用来区分是否是调查人)
		String process012 =getStringAt("process012"); //本人意见说明
		try {
		DBProcedureParamsDef defcs = null;
		this.setFieldValue("in_entcode", entcode);
		this.setFieldValue("in_tradecode", tradecode);
		this.setFieldValue("in_ordernum", ordernum);
		this.setFieldValue("in_ordercode", ordercode);
		this.setFieldValue("in_process012", process012);

		defcs = new DBProcedureParamsDef("pack_flow_unit.proc_saveselfadvice");
		defcs.addInParam("in_entcode");
		defcs.addInParam("in_tradecode");
		defcs.addInParam("in_ordernum");
		defcs.addInParam("in_ordercode");	 
		defcs.addInParam("in_process012");
		defcs.addOutParam("ret_flag");	 
		defcs.addOutParam("ret_mess");
		DBProcedureAccessService dbProcService = new DBProcedureAccessService(this);
		int returncode = dbProcService.executeProcedure(getOperationData(), defcs);
		dbProcService = null;
		String retCode = getStringAt("ret_flag");
		String retText = getStringAt("ret_mess");


		if (!retCode.equals("0")) {
			throw new TranFailException(
				"pack_flow_unit.proc_saveselfadvice",
				"SE_pes_EvalueQueryOp.dk_pj()",
				retText,
				"本人意见说明保存错误");
		} ;
		if (returncode != 0)
		{
		 String e = this.getStringAt("ret_mess");

		 throw new icbc.cmis.base.TranFailException("pack_flow_unit.proc_saveselfadvice", "util_showcontent_flowOp.send_sqfs()", "!", e);
		}

	}
	catch (TranFailException e) {
		throw e;
	}
	catch (Exception e) {
		throw new TranFailException("util_content_msg", "EBAlreadyApprove.saveadvice", e.getMessage());
	}
		dao = new util_content_msgDao(this);
		String returnURL = "";
		Vector contentlist = new Vector();

		try {
			contentlist = dao.getadvicetxt( entcode, tradecode,ordernum,ordercode);
			this.setFieldValue("contentlist", contentlist);

			
			 setReplyPage("/icbc/cmis/flow/util/util_content_msg.jsp");
			
			this.setOperationDataToSession();
		}
		catch (TranFailException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TranFailException("util_content_msg", "EBAlreadyApprove.saveadvice", e.getMessage());
		}
	}
	

}
