/*
 * 创建日期 2004-11-19
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.service;

import icbc.cmis.util.DBTools;
import icbc.cmis.operation.DummyOperation;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.KeyedDataCollection;
import com.icbc.cte.base.KeyedCollection;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author ZJFH-yanb
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class OpAuthService {
  private String opName = null;
  private String opId = null;
  private KeyedDataCollection kdc = null;
  private KeyedCollection kc = null;
  private boolean isCtpOp = false;
  private String errMsg = null;

  public OpAuthService(String opName, KeyedDataCollection kdc) {
    this.opName = opName;
    this.kdc = kdc;
    this.isCtpOp = false;
  }

  public OpAuthService(String opName, KeyedCollection kc) {
    this.opName = opName;
    this.kc = kc;
    this.isCtpOp = true;
  }

  public boolean isOpAuthed() throws Exception {
    if (!isOpNameAuthed())
      return false;
    if (!isOpActionAuthed())
      return false;
    return true;
  }

  private boolean isOpNameAuthed() throws Exception {
    opId = getOpId();
    if (opId != null)
      return true;
    else {
      errMsg = "OP名称[" + this.opName + "]没有登记！";
      return false;
    }
  }

  private String getOpId() throws TranFailException {
    String queryStr = "SELECT OPID FROM MAG_OP_NAME WHERE OPNAME=?";
    try {
      DBTools srv = new DBTools(new DummyOperation(), "missign");
      Vector inParam = new Vector(1);
      inParam.add(this.opName);

      String opId = srv.executeSQL(queryStr, inParam);
      if (opId == null || opId.equals(""))
        return null;
      else
        return opId;
    }
    catch (Exception e) {
      throw new TranFailException("icbc.cmis.service.OpAuthService", "OpAuthService.getOpId", "取operation id失败", "取operation id失败");
    }
  }

  private boolean isOpActionAuthed() throws TranFailException {
    Vector actionAndValue = getOpActionAndValue();
    String curActionValue = "";
    String actionName = "";
    if (actionAndValue.size() == 0){
	  errMsg = "OP名为["+this.opName +"]的OP Action没有登记";
      return false;
    }
    else {
      if (!this.isCtpOp) {
        //actionName = (String) ((Hashtable)actionAndValue.get(0)).get("OPACTNAME");
//        if (actionName.equalsIgnoreCase("NULL"))
//          return true;
		
		//KeyedDataCollection context = (KeyedDataCollection)kdc.getAttribute("operationData");
        for (int i = 0; i < actionAndValue.size(); i++) {
          actionName = (String) ((Hashtable)actionAndValue.get(i)).get("OPACTNAME");
		  String valueReg = (String) ((Hashtable)actionAndValue.get(i)).get("OPACTVAL");
		  if(valueReg.equals("NULL"))
		    return true;
          String actionValue = null;
          try{
			actionValue = (String)kdc.getValueAt(actionName);
          }catch(Exception e){}
          
          if (actionValue != null) {
			curActionValue = actionValue;
			//String valueReg = (String) ((Hashtable)actionAndValue.get(i)).get("OPACTVAL");
            if (valueReg.equals(actionValue))
              return true;
          }
        }
        errMsg = "OP名为["+this.opName +"]的[" + actionName + "]在context中的值为[" + curActionValue+ "]，和登记值无法匹配！";
        return false;
      }
      else{
		errMsg = "目前暂不支持CTP框架下的OP认证！";
        return false;
      }
    }
  }

  private Vector getOpActionAndValue() throws TranFailException {
    String queryStr = "SELECT OPACTNAME,OPACTVAL FROM MAG_OP_ACTION WHERE OPID=? AND OPACTNAME IS NOT NULL";
    try {
      if (this.opId == null)
        throw new Exception();
      DBTools srv = new DBTools(new DummyOperation(), "missign");
      Vector inParam = new Vector(1);
      inParam.add(this.opId);

      Vector actionAndValue = srv.executeSQLResult(queryStr, inParam);
      return actionAndValue;
    }
    catch (Exception e) {
      throw new TranFailException("icbc.cmis.service.OpAuthService", "OpAuthService.getOpActionAndValue", "取operation action值失败", "取operation action值失败");
    }
  }

  public String getErrorMsg() {
    return this.errMsg;
  }

}
