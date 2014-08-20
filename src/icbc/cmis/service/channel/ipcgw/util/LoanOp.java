/*
 * 创建日期 2006-11-2
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.service.channel.ipcgw.util;

import java.util.Vector;

import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.CmisOperation;

/**
 * @author ZJFH-wanglx
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class LoanOp extends CmisOperation {
  /**
   * 对公贷款
   */
  public LoanOp() {
    super();
  }
  /* （非 Javadoc）
   * @see icbc.cmis.operation.CmisOperation#execute()
   */
  public void execute() throws Exception {
    try {
      publicDAO dao = new publicDAO(this);
      Vector v_param = new Vector();
	  v_param.add(this.getStringAt("ONLY_ID"));
      v_param.add(this.getStringAt("NEWSTYPE"));
      v_param.add(this.getStringAt("TIMESTMP"));
      v_param.add(this.getStringAt("TRXTYPE"));
      v_param.add(this.getStringAt("ZONENO"));
      v_param.add(this.getStringAt("PHYBRNO"));
      v_param.add(this.getStringAt("ACCNO"));
      v_param.add(this.getStringAt("LOANNO"));
      v_param.add(this.getStringAt("LOANSQNO"));
      v_param.add(this.getStringAt("CURRTYPE"));
      v_param.add(this.getStringAt("TRXCODE"));
      v_param.add(this.getStringAt("BUSIDATE"));
      v_param.add(this.getStringAt("BUSITIME"));
      v_param.add(this.getStringAt("TELLERNO"));
      v_param.add(this.getStringAt("TRXSQNB"));
      v_param.add(this.getStringAt("TRXSQNS"));
      v_param.add(this.getStringAt("WORKDATE"));
      v_param.add(this.getStringAt("SEQNO"));
      v_param.add(this.getStringAt("TRANTYPE"));
      v_param.add(this.getStringAt("STATUS"));
      v_param.add(this.getStringAt("UPDTRANF"));
      v_param.add(this.getStringAt("REVTRANF"));
      v_param.add(this.getStringAt("DRCRF"));
      v_param.add(this.getStringAt("SUBCODE"));
      v_param.add(this.getStringAt("FLAG"));
      v_param.add(this.getStringAt("AMOUNT"));
      v_param.add(this.getStringAt("BALANCE"));
      v_param.add(this.getStringAt("RATECODE"));
      v_param.add(this.getStringAt("FLOARATE"));
      v_param.add(this.getStringAt("EXHRCODE"));
      v_param.add(this.getStringAt("EXHFRATE"));
      v_param.add(this.getStringAt("FINFRATE"));
      v_param.add(this.getStringAt("CALINTCL"));
      v_param.add(this.getStringAt("MEMFDATE"));
	  v_param.add(this.getStringAt("RATEINCM"));
      v_param.add(this.getStringAt("BRTNFLAG"));
		v_param.add(this.getStringAt("FLOARATE_FLAG"));
		v_param.add(this.getStringAt("EXHFRATE_FLAG"));
		v_param.add(this.getStringAt("FINFRATE_FLAG"));      
      for (int i = 39; i < 41; i++) {
        v_param.add("");
      }
      dao.insert_it(v_param);
	  this.setFieldValue("RETFLAG","0000");//标识 成功
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception e) {
      throw new TranFailException("对公贷款LoanOp",
      //错误编码，使用者看
      "LoanOp.execute()", //出错位置,开发者看
      e.getMessage() //错误内容，开发者看
      );
    }
  }
}
