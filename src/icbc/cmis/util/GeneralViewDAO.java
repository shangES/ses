/*
 * 创建日期 2005-7-27
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.util;
import oracle.jdbc.driver.*;
import icbc.cmis.service.CmisDao;
import icbc.cmis.operation.*;
import icbc.cmis.base.genMsg;
import java.util.*;
import java.io.IOException;
import java.sql.*;
import icbc.cmis.base.TranFailException;
import icbc.cmis.util.DBTools;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class GeneralViewDAO extends CmisDao {

  public GeneralViewDAO(CmisOperation op) {
    super(op);
  }

  /**
   * 取得字段定义
   * @param formCode 页面代码
   * @return Vector
   * @throws TranFailException
   */
  public Vector getFieldDefine(String formCode) throws TranFailException {
    String queryStr =
      "SELECT TABLE_CODE,FIELD_CODE,FIELD_NAME,FIELD_TYPE, "
        + "SELECT_TYPE,SELECTION,SELECTION_FIELD,DISPLAY_FIELD,FILTER_FIELD,FILTER_VALUE, "
        + "IS_NEED,MODIFY_PRIV,IS_PRIMARY,ONSCRIPTTYPE,ONSCRIPTVALUE, "
        + "HINT_CONTENT,MIN_VAL,MAX_VAL,SUB_ORDER,RESTRICTS,DISP_CODE "
        + "FROM MAG_FIELD_DEFINE_COMMON WHERE FORM_CODE=? ORDER BY TABLE_CODE,FIELD_CODE";

    try {
      DBTools srv = new DBTools(this.getOperation());
      Vector inParam = new Vector(1);
      inParam.add(formCode);

      Vector v_result = srv.executeSQLResultNoLimit(queryStr, inParam);
      if (v_result.size() == 0)
        throw new TranFailException(
          "100759",
          "icbc.cmis.util.GeneralViewDAO[getFieldDefine]",
          genMsg.getErrMsg("100759", formCode),
          genMsg.getErrMsg("100759", formCode));
      return v_result;

    }
    catch (TranFailException te) {
      throw te;
    }
    catch (Exception e) {
      throw new TranFailException("100760", "icbc.cmis.util.GeneralViewDAO[getFieldDefine]", e.getMessage(), genMsg.getErrMsg("100760", formCode));
    }
  }

  /**
   * 取得字段布局
   * @param formCode 页面代码
   * @return Vector
   * @throws TranFailException
   */
  public Vector getLayoutDefine(String formCode) throws TranFailException {
    String queryStr =
      "SELECT FORM_CODE,CELL_CODE,PRE_CELL_CODE,CELL_TYPE,TEXT_NAME,INPUT_TABLE,INPUT_FIELD,CELL_COLSPAN,LINK_TARGET "
        + "FROM MAG_CELL_LAYOUT_COMMON "
        + "WHERE FORM_CODE = ? ORDER BY CELL_CODE";

    try {
      DBTools srv = new DBTools(this.getOperation());
      Vector inParam = new Vector(1);
      inParam.add(formCode);

      Vector v_result = srv.executeSQLResultNoLimit(queryStr, inParam);
      if (v_result.size() == 0)
        throw new TranFailException(
          "100761",
          "icbc.cmis.util.GeneralViewDAO[getLayoutDefine]",
          genMsg.getErrMsg("100761", formCode),
          genMsg.getErrMsg("100761", formCode));
      return v_result;

    }
    catch (TranFailException te) {
      throw te;
    }
    catch (Exception e) {
      throw new TranFailException("100762", "icbc.cmis.util.GeneralViewDAO[getLayoutDefine]", e.getMessage(), genMsg.getErrMsg("100762", formCode));
    }
  }
  
  
  
	/**
		* 取得借据列表页面布局
		* @param formCode 页面代码
		* @return Vector
		* @throws TranFailException
		*/
	 public Vector getdueDefine(String formCode) throws TranFailException {
	 	Hashtable hs=new Hashtable();
	 	  
		 String queryStr =
			 "SELECT table_name, field_name, text_name ,amort_flag "
				 + "FROM mag_due_list_common "
				 + "WHERE FORM_CODE = ? ORDER BY field_order";

		 try {
			 DBTools srv = new DBTools(this.getOperation());
			 Vector inParam = new Vector(1);
			 inParam.add(formCode);

			 Vector v_result = srv.executeSQLResultNoLimit(queryStr, inParam);
			 
			 if (v_result.size() == 0)
				 throw new TranFailException(
					 "100761",
					 "icbc.cmis.util.GeneralViewDAO[getdueDefine]",
					 genMsg.getErrMsg("100761", formCode),
					 genMsg.getErrMsg("100761", formCode));
					 
		 
			 return v_result;

		 }
		 catch (TranFailException te) {
			 throw te;
		 }
		 catch (Exception e) {
			 throw new TranFailException("100762", "icbc.cmis.util.GeneralViewDAO[getdueDefine]", e.getMessage(), genMsg.getErrMsg("100762", formCode));
		 }
	 }
  
  /**
   * 取得操作类名
   * @param formCode 页面代码
   * @return String
   * @throws TranFailException
   */
  public String getExtentsDMName(String formCode) throws TranFailException {
    String queryStr = "SELECT DM_CLASS " + "FROM MAG_TABLE_DEFINE_COMMON " + "WHERE FORM_CODE = ?";

    try {
      DBTools srv = new DBTools(this.getOperation());
      Vector inParam = new Vector(1);
      inParam.add(formCode);

      String v_result = srv.executeSQL(queryStr, inParam);
      if (v_result == null || v_result.equals(""))
        throw new TranFailException(
          "100763",
          "icbc.cmis.util.GeneralViewDAO[getExtentsDMName]",
          genMsg.getErrMsg("100763", formCode),
          genMsg.getErrMsg("100763", formCode));
      return v_result;

    }
    catch (TranFailException te) {
      throw te;
    }
    catch (Exception e) {
      throw new TranFailException("100764", "icbc.cmis.util.GeneralViewDAO[getExtentsDMName]", e.getMessage(), genMsg.getErrMsg("100764", formCode));
    }
  }

}
