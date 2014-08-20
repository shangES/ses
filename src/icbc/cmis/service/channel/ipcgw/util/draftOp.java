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
public class draftOp extends CmisOperation {
  /**
   * @param op
   */
  public draftOp() {
    super();
  }
  /**
   * @throws Exception
   */
  public void execute() throws Exception {
    try {
      publicDAO dao = new publicDAO(this);
      Vector v_param = new Vector();
	  v_param.add(this.getStringAt("ONLY_ID"));
      v_param.add(this.getStringAt("NEWSTYPE"));
      v_param.add(this.getStringAt("TIMESTMP"));
      v_param.add(this.getStringAt("ZONENO"));
      v_param.add(this.getStringAt("BRNO"));
      v_param.add(this.getStringAt("VOUHTYPE"));
      v_param.add(this.getStringAt("VOUHNO"));
      v_param.add(this.getStringAt("HSTNO"));
      v_param.add(this.getStringAt("SEQNO"));
      v_param.add(this.getStringAt("TRANTYPE"));
      v_param.add(this.getStringAt("CURRTYPE"));
      v_param.add(this.getStringAt("AMOUNT"));
      v_param.add(this.getStringAt("BALANCE"));
      v_param.add(this.getStringAt("STATUS"));
      v_param.add(this.getStringAt("STATUS1"));
      v_param.add(this.getStringAt("ACCNO"));
      v_param.add(this.getStringAt("GRTTYPE"));
      v_param.add(this.getStringAt("OPENDATE"));
      v_param.add(this.getStringAt("MATDATE"));
      v_param.add(this.getStringAt("CLOSDATE"));
      v_param.add(this.getStringAt("TELLERNO"));
      v_param.add(this.getStringAt("AUTHTLNO"));
      v_param.add(this.getStringAt("NOTES1"));
      v_param.add(this.getStringAt("NOTES2"));
      v_param.add(this.getStringAt("NOTES3"));
      v_param.add(this.getStringAt("NOTES4"));
      v_param.add(this.getStringAt("BUSIDATE"));
      v_param.add(this.getStringAt("BUSITIME"));
      v_param.add(this.getStringAt("TRXZONENO"));
      v_param.add(this.getStringAt("TRXBRNO"));
      v_param.add(this.getStringAt("TRXTELLER"));
      v_param.add(this.getStringAt("TRXSQNB"));
      v_param.add(this.getStringAt("TRXSQNS"));
      v_param.add(this.getStringAt("REVTRANF"));
      for (int i = 34; i < 41; i++) {
        v_param.add("");
      }
      dao.insert_it(v_param);
      this.setFieldValue("RETFLAG","0000");//标识 成功
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception e) {
      throw new TranFailException("承兑保函draftOp",
      //错误编码，使用者看
      "draftOp.execute()", //出错位置,开发者看
      e.getMessage() //错误内容，开发者看
      );
    }
  }
}
