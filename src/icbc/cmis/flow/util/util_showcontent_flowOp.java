/*
 * 创建日期 2006-3-1
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
public class util_showcontent_flowOp extends icbc.cmis.operation.CmisOperation {
	public util_showcontent_flowOp() {
			super();
	}
	private String webBasePath =
				(String) CmisConstance.getParameterSettings().get("webBasePath");
	util_content_flowDao dao = null;
	public void execute() throws Exception, icbc.cmis.base.TranFailException {
		String action = getStringAt("opAction");
		try {
			//查询具体内容
			if (action.equals("20001")) {
				queryshowcontent();
				};

		}

		catch (Exception ex) {
			setOperationDataToSession();
			throw new TranFailException("dzypjBB0001", "EBAlreadyApprove.execute()", ex.getMessage(), "评估测算信息管理");
		}
		
	}
	
/**
	* <b>功能描述: </b><br>
	* <p>查询具体内容</p>
	* @throws TranFailException
	*  
	*/
	private void queryshowcontent() throws TranFailException {
		
		
		String formflag =getStringAt("formflag");  //查询项标志  1,意见说明 2,附加条件或限制性条款内容 3.辅助内容
		String entcode =getStringAt("entcode");   //客户代码
		String tradecode =getStringAt("tradecode");//业务申请号
		String flowtype =getStringAt("flowtype");  //流程种类
		String xh =getStringAt("xh");  //序号
		String step =getStringAt("step"); //环节(主要用来区分是否是调查人)

		 

		dao = new util_content_flowDao(this);
		String returnURL = "";
		Vector contentlist = new Vector();

		try {
			contentlist = dao.getcontenttxt(formflag, entcode, tradecode,flowtype,xh,step);
			this.setFieldValue("contentlist", contentlist);

			if (formflag.equals("2")  || (step.equals("调查人")))
			 { 
			 setReplyPage("/icbc/cmis/flow/util/util_showcontentforsearcher_flow.jsp");
			 }
			 else
			 {
			 this.setFieldValue("formflag", formflag);
			 setReplyPage("/icbc/cmis/flow/util/util_showcontent_flow.jsp");
			 }
			this.setOperationDataToSession();
		}
		catch (TranFailException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TranFailException("util_showcontent_flowOp", "EBAlreadyApprove.queryshowcontent", e.getMessage());
		}
	}
	

}
