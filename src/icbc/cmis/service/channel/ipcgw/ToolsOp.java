/*
 * 创建日期 2006-11-13
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.service.channel.ipcgw;

import java.util.Hashtable;
import java.util.Vector;

import icbc.cmis.base.CmisConstance;
import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.CmisOperation;
import icbc.cmis.second.pkg.DBProcedureAccessService;
import icbc.cmis.second.pkg.DBProcedureParamsDef;

/**
 * @author ZJFH-wanglx
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class ToolsOp extends CmisOperation {
  /**
   * 
   */
  public ToolsOp() {
    super();
  }
  public void execute() throws TranFailException {
    try {
      String opAction = this.getStringAt("opAction");
      if (opAction.equals("20001")) {
        query_trace();
      }
      else if (opAction.equals("40001")) {
        del_trace();
      }
    }
    catch (TranFailException ex) {

      throw ex;
    }
    catch (Exception e) {
      throw new TranFailException("ToolsOp",
      //错误编码，使用者看
      getClass().getName() + ".execute()", //出错位置,开发者看
      e.getMessage() //错误内容，开发者看
      );
    }
  }
  /**
   * @throws TranFailException
   */
  private void query_trace() throws TranFailException {
    try {
      ToolsDAO dao = new ToolsDAO(this);
      Vector v_param = new Vector();
      v_param.add(this.getStringAt("v_queryTime"));
      v_param.add(this.getStringAt("v_hostIP"));
      Hashtable h_table = dao.query_trace(v_param);
      this.setFieldValue("flag", "1");
      this.setFieldValue("h_table", h_table);
      this.setReplyPage("/util/util_ICPGW_Mag.jsp");
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception e) {
      throw new TranFailException("099993", //错误代码。
      "ToolsOp.query_it()", //错误提示的程序位置。
      e.getMessage(), //错误类的相关信息。
      e.getMessage() //自行描述的错误信息。
      );
    }
  }
  /**
   * @throws TranFailException
   */
  private void del_trace() throws TranFailException {
    try {
      ToolsDAO dao = new ToolsDAO(this);
      dao.del_trace(this.getStringAt("v_hostIP"), this.getStringAt("v_queryTime"));
      this.setFieldValue("okTitle", "成功");
      this.setFieldValue("okMsg", "删除成功");
      String baseWebPath = (String)CmisConstance.getParameterSettings().get("webBasePath");
      this.setFieldValue("okReturn", baseWebPath + "/util/util_ICPGW_Mag.jsp");
      this.setReplyPage("/ok.jsp");
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception e) {
      throw new TranFailException("099993", //错误代码。
      "ToolsOp.del_trace()", //错误提示的程序位置。
      e.getMessage(), //错误类的相关信息。
      e.getMessage() //自行描述的错误信息。
      );
    }
  }
}
