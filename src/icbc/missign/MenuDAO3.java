package icbc.missign;

import icbc.cmis.base.CmisConstance;
import icbc.cmis.base.TableBean;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.genMsg;
import icbc.cmis.service.CmisDao;
import icbc.cmis.util.Func_XMLfiltrate;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import oracle.jdbc.OracleTypes;

/**
 * Title:
 * Description: 业务功能裁减模块的数据库操作类，包括调用存储过程；
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author  金智伟 完成于2002-04-27
 * 			 夏春香 修改于2006-02-28
 * @version 1.0
 *
 * Modifiy History:
 *    20020620  为了调整模块间的相互顺序，增加了查询APP_ORDER
 *    20020711  防止数据库表mag_function中父节点和子节点的代码相同，在查询语句中加上了"app_pmodule_code <> app_module_code"的条件
 *    20020730  cmis，bmis，vmis都会调用本模块，在请求中增加application参数区分不同的应用
 *    20021118  取数据库用户missign用户和口令从配置文件中读取
 *    20030916  为了动态显示岗位，把岗位权限统一用spriv[]表示，并增加岗位数employeeClassCount参数
 * 	　20060228　为了正确修改数据库记录，增加地区号area_code参数，并增加标志是否有效性sEnable参数
 * 
 */

public class MenuDAO3 extends CmisDao {
  private boolean bDebug = false;

  private Vector forbidFuncId = null;

  public MenuDAO3(icbc.cmis.operation.CmisOperation op) {
    super(op);
  }

  public void menuItem(String[] row, int level, StringBuffer menu, Vector classes) {
    String id = row[0];
    String name = row[1];
    String pid = row[2];
    String baseWebPath = (String)CmisConstance.getParameterSettings().get("webBasePath"); //根路径
    int child = Integer.valueOf(row[3]).intValue(); //是否有子模块，0=无，1=有
    String sPrivilege = row[4].trim();
    String sBankClass = row[5].trim();
    String sAppOrder = row[6].trim(); //模块的顺序号
    String sEnable = row[8].trim(); //菜单是否有效
    name = Func_XMLfiltrate.validXml(name);
    //分析权限字
    String sPrivSymbol = "√";
    String[] sPriv = new String[20];
    for (int i = 0; i < classes.size(); i++) {
      if (i < sPrivilege.length() && sPrivilege.charAt(Integer.valueOf(((String) (classes.get(i)))).intValue() - 1) == '1')
        sPriv[i] = sPrivSymbol;
      else
        sPriv[i] = "&nbsp;";
    }

    //菜单是否有效
    String sEnableflag = "&nbsp;";
    if (sEnable.charAt(0) == '1')
      sEnableflag = sPrivSymbol;

    //分析行级别
    String sHq = "&nbsp;", sFirst = "&nbsp;", sSemiFirst = "&nbsp;", sSecond = "&nbsp;", sBranch = "&nbsp;";
    if (sBankClass.charAt(0) == '1')
      sHq = sPrivSymbol;
    if (sBankClass.charAt(1) == '1')
      sFirst = sPrivSymbol;
    if (sBankClass.charAt(2) == '1')
      sSemiFirst = sPrivSymbol;
    if (sBankClass.charAt(3) == '1')
      sSecond = sPrivSymbol;
    if (sBankClass.charAt(4) == '1')
      sBranch = sPrivSymbol;
    
    menu.append("<TR align=\"center\" bgcolor=\"#f1f1f1\" TITLE=\"");
    menu.append(name);
    menu.append("\" Class=\"Navigator");
    if (level > 0)
      menu.append("-Hidden"); //是子模块
    menu.append("\" ID=\"");
    menu.append(id);
    menu.append("\" AncestorID=\"");
    menu.append(pid);
    menu.append("\" Depth=\"");
    menu.append(level);
    if (child > 0) { //有子模块
      menu.append("\" Expanded=\"no\"");
      menu.append(" onClick=\"javascript:toggle('");
      menu.append(id);
      menu.append("');");
    }
    menu.append("\">\n");
    menu.append("\t<TD width=\"18\"><input type=\"radio\" name=\"ModualRadio\" value=\"1\" onclick=\"javascript:SelectModule('");
    menu.append(name);
    menu.append("', '");
    menu.append(id);
    menu.append("', '");
    menu.append(pid);
    menu.append("', '");
    menu.append(sPrivilege);
    menu.append("', '");
    menu.append(sBankClass);
    menu.append("', '");
    menu.append(sAppOrder);
    menu.append("', '");
    menu.append(sEnable);
    menu.append("', '");
    menu.append(child);
    menu.append("')\"></TD>\n");
    menu.append("\t<TD");
    if (child > 0) {
      menu.append(" STYLE=\"cursor:hand\"");
    }
    menu.append(" align=\"left\">\n");
    menu.append("\t\t<DIV noWrap=\"true\" STYLE=\"padding-left:");
    menu.append(level);
    menu.append("em;\">");

    //menu.append("<img src=\"/icbc/cmis/images/open.gif\">");
    menu.append("<img src=\"" + baseWebPath + "/images/");
    if (child > 0) {
      menu.append("bs.gif");
    }
    else {
      menu.append("dot.gif");
    }
    menu.append("\">");
    menu.append(name);
    menu.append("</DIV>\n\t</TD>\n");
    menu.append("\t<TD align=center>");
    menu.append(id);
    menu.append("</TD>\n");
    menu.append("\t<TD align=center>");
    menu.append(sAppOrder);
    menu.append("</TD>\n");
    for (int i = 0; i < classes.size(); i++) {
      menu.append("\t<TD>");
      menu.append(sPriv[i]);
      menu.append("</TD>\n");
    }

    menu.append("\t<TD>");
    menu.append(sHq);
    menu.append("</TD>\n");
    menu.append("\t<TD>");
    menu.append(sFirst);
    menu.append("</TD>\n");
    menu.append("\t<TD>");
    menu.append(sSemiFirst);
    menu.append("</TD>\n");
    menu.append("\t<TD>");
    menu.append(sSecond);
    menu.append("</TD>\n");
    menu.append("\t<TD>");
    menu.append(sBranch);
    menu.append("</TD>\n");
    menu.append("\t<TD>");
    menu.append(sEnableflag);
    menu.append("</TD>\n");
    menu.append("</TR>\n");
  }

  /**
   * 产生一层菜单
   * @param pstmt
   * @param level
   * @param pcode
   * @param menu
   * @throws TranFailException
   */
  public void getOneLevel(PreparedStatement pstmt, int level, String pcode, StringBuffer menu, Vector classes) throws TranFailException {
    ResultSet rs = null;
    Vector rows = new Vector();

    try {
      pstmt.setString(2, pcode);
      rs = pstmt.executeQuery();
      while (rs.next()) {

        String[] row =
          {
            rs.getString(1),
            rs.getString(2),
            rs.getString(3),
            rs.getString(4),
            rs.getString(5),
            rs.getString(6),
            rs.getString(7),
            rs.getString(8),
            rs.getString(9),
            rs.getString(10),
            rs.getString(11)};
        rows.add(row);
      }
      try {
        if (rs != null) {
          rs.close();
          rs = null;
        }
      }
      catch (Exception ex) {}

      if (!rows.isEmpty()) {
        for (int i = 0; i < rows.size(); i++) {
          String[] row = (String[])rows.get(i);
          menuItem(row, level, menu, classes);
          getOneLevel(pstmt, level + 1, row[0], menu, classes);
        }
      }
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("menu001", "icbc.missign.MenuDAO3.getOneLevel", ex.getMessage(), ex.getMessage());  //not standard
    }
  }

  /**
   *
   * @param major
   * @param area_code  所属行
   * @return
   * @throws TranFailException
   */
  public String getMenuXML(String major_code, String area_code) throws TranFailException {
    Statement stmt = null;
    ResultSet rs = null;
    String sql = null;
    String sql2 = null;
    PreparedStatement pstmt = null;
    StringBuffer menu = new StringBuffer(5000);
    Vector employeeClass = new Vector();
    //String employeeClassXml = null;
    forbidFuncId = new Vector();
    Vector ret = new Vector();
    int employeeClassCount = 0;
	String langCode = (String)(getOperation().getSessionData("LangCode"));
    Hashtable hTable = (Hashtable)CmisConstance.getDictParam(langCode);
    TableBean dict = (TableBean)hTable.get("mag_employee_class");
    Vector emp_classes = dict.getAllNameByCode("class_code", "major_code", major_code);
    String application = (String)CmisConstance.getParameterSettings().get("application");
    StringTokenizer token = new StringTokenizer(application, "|");
    
    //除了在资料库取得的数据外，还有4个：权限1～3 + 扫描权限
    //employeeClassCount = classes.size() + 4;
    Vector classes = new Vector();
    classes.add("1");
    classes.add("2");
    classes.add("3");
    classes.add("4");
    for (int i = 0; i < emp_classes.size(); i++)
      classes.add(emp_classes.get(i));

    try {
      //建立连接
      this.getConnection("missign");
      sql =
        "select APP_MODULE_CODE, FUNC_NAME, APP_PMODULE_CODE, FUNC_SUB_NODE,APP_PRIVILEGE,APP_CLASS, APP_ORDER, BANK_FLAG,ENABLE_FLAG,APP_MAJOR_CODE,APPLICATION from MAG_APPLICATION_NEW, MAG_FUNCTION, MAG_AREA where APP_MAJOR_CODE = ? and APP_PMODULE_CODE = ? and APP_MODULE_CODE = FUNC_CODE and APP_MODULE_CODE <> APP_PMODULE_CODE";
      sql += " and MAG_AREA.AREA_CODE = '" + area_code + "' and MAG_APPLICATION_NEW.AREA_CODE = MAG_AREA.AREA_CODE  and MAG_FUNCTION.Lang_Code='"+langCode+"'";
      sql += " and application in (";
      String subSql = "";
      while (token.hasMoreTokens()) {
        subSql += ",'" + token.nextToken() + "'";
      }
      sql += subSql.substring(1) + ")";
      sql += " order by APP_ORDER";
      if (bDebug) {
        System.out.println(sql);
      }

      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, major_code);
      getOneLevel(pstmt, 0, "00000", menu, classes);
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("menu002", "icbc.missign.missign.MenuDAO3.getMenuXML", ex.getMessage(), ex.getMessage());//not standard
    }
    finally {
      try {
        if (rs != null)
          rs.close();
      }
      catch (Exception ex) {}
      //try { if(stmt != null) rs.close(); } catch (Exception ex) {}
      try {
        if (pstmt != null)
          pstmt.close();
      }
      catch (Exception ex) {}
      this.closeConnection();
    }
    //ret.add(1,menu.toString());
    return menu.toString();
  }

  private boolean isForbid(String id) {
    Enumeration ids = (Enumeration)forbidFuncId.elements();
    while (ids.hasMoreElements()) {

      String forbidId = (String)ids.nextElement();
      if (id.equals(forbidId))
        return true;

    }
    return false;
  }

  /**
   * 调用存储过程，在mag_application_new表中添加新记录，并修改它的父模块的权限
   * @param sMajorCode
   * @param sPModuleId
   * @param sModuleId
   * @param sNewModuleId
   * @param sPrivilege
   * @param sBankClass
   * @param sModuleHasChild 如果新增的模块有子模块，且这个模块已经出现在mag_application_new表中，在mag_function中复制这个节点，（当然func_code取当前最大值加1），这样保证添加的有子模块的节点的唯一性
   * @param sSiblingChild   新增的模块是作为选中模块的兄弟还是儿子(sibling, child)
   * @param sAppName 应用的名称（cmis，bmis，vmis）
   * @param sEnableFlag 标志模板是否有效
   * @param area_code 所属行
   * @throws Exception
   */
  public void addNewFunc(
    String sMajorCode,
    String sPModuleId,
    String sModuleId,
    String sNewModuleId,
    String sPrivilege,
    String sBankClass,
    String sModuleHasChild,
    String sSiblingChild,
    String sAppName,
    String sEnableFlag,
    String area_code)
    throws Exception {
    try {
      //String sUser = (String)icbc.cmis.base.CmisConstance.getParameterSettings().get("dbUserName");
      //String sPass = (String)icbc.cmis.base.CmisConstance.getParameterSettings().get("dbUserPass");
      this.getConnection("missign");
      //getConnection("missign", "missign");

      CallableStatement cStmt = conn.prepareCall("{ ?=call pack_magapp_tool.addNewApplication(?,?,?,?,?,?,?,?,?,?,?,?)}");

      cStmt.registerOutParameter(1, OracleTypes.INTEGER);
      cStmt.registerOutParameter(13, OracleTypes.VARCHAR);
      cStmt.setString(2, sPModuleId);
      cStmt.setString(3, sModuleId);
      cStmt.setString(4, sNewModuleId);
      cStmt.setString(5, sPrivilege);
      cStmt.setString(6, sBankClass);
      cStmt.setString(7, sMajorCode);
      cStmt.setString(8, sModuleHasChild);
      cStmt.setString(9, sSiblingChild);
      cStmt.setString(10, sAppName);
      cStmt.setString(11, sEnableFlag);
      cStmt.setString(12, area_code);

      cStmt.executeQuery();

      int retValue = cStmt.getInt(1);
      String err_txt = cStmt.getString(13);

      cStmt.close();
      closeConnection();

      if (retValue != 1) {
        throw new Exception(err_txt);
      }
    }
    catch (Exception ee) {
      if (conn != null)
        closeConnection();
      throw ee;
    }

  }

  /**
   * 删除模块及其子模块
   * @param sMajorCode
   * @param sPModuleId
   * @param sModuleId
   * @throws Exception
   */
  public void deleteFunc(String sMajorCode, String sPModuleId, String sModuleId) throws Exception {
    try {

      this.getConnection("missign");

      CallableStatement cStmt = conn.prepareCall("{ ?=call pack_magapp_tool.deleteApplication(?,?,?,?)}");

      cStmt.registerOutParameter(1, OracleTypes.INTEGER);
      cStmt.registerOutParameter(5, OracleTypes.VARCHAR);
      cStmt.setString(2, sMajorCode);
      cStmt.setString(3, sPModuleId);
      cStmt.setString(4, sModuleId);

      cStmt.executeQuery();

      int retValue = cStmt.getInt(1);
      String err_txt = cStmt.getString(5);

      cStmt.close();
      closeConnection();

      if (retValue != 1) {
        throw new Exception(err_txt);
      }
    }
    catch (Exception ee) {
      if (conn != null)
        closeConnection();
      throw ee;
    }

  }

  /**
   * 修改模块的权限和行级别，同时修改父子模块
   * @param sMajorCode
   * @param sPModuleId
   * @param sModuleId
   * @param sPrivilege
   * @param sBankClass
   * @param sAppOrder
   * @param sEnableFlag 标志模板是否有效
   * @param area_code 所属行
   * @throws Exception
   */
  public void updateFunc(
    String sMajorCode,
    String sPModuleId,
    String sModuleId,
    String sPrivilege,
    String sBankClass,
    String sAppOrder,
    String sEnableFlag,
    String area_code)
    throws Exception {
    try {
      getConnection("missign");

      CallableStatement cStmt = conn.prepareCall("{ ?=call pack_magapp_tool.updateApplication(?,?,?,?,?,?,?,?,?)}");

      cStmt.registerOutParameter(1, OracleTypes.INTEGER);
      cStmt.registerOutParameter(10, OracleTypes.VARCHAR);
      cStmt.setString(2, sMajorCode);
      cStmt.setString(3, sPModuleId);
      cStmt.setString(4, sModuleId);
      cStmt.setString(5, sPrivilege);
      cStmt.setString(6, sBankClass);
      cStmt.setString(7, sAppOrder);
      cStmt.setString(8, sEnableFlag);
      cStmt.setString(9, area_code);
      cStmt.executeQuery();

      int retValue = cStmt.getInt(1);
      String err_txt = cStmt.getString(10);

      cStmt.close();
      closeConnection();

      if (retValue != 1) {
        throw new Exception(err_txt);
      }
    }
    catch (Exception ee) {
      if (conn != null)
        closeConnection();
      throw ee;
    }

  }

  /**
   * 查询业务名
   * @param major_code 业务号
   * @param area_code	地区号
   * @return
   * @throws TranFailException
   */
  public boolean isExistMoudle(String major_code, String moudle_code) throws TranFailException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Vector rows = new Vector();
    try {
      this.getConnection("missign");
      String sql = "";
      sql = "select APP_MODULE_CODE  from MAG_APPLICATION_NEW where APP_MODULE_CODE = ? and APP_MAJOR_CODE = ? AND AREA_CODE = '00000000' ";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, moudle_code); 
      pstmt.setString(2, major_code);
      rs = pstmt.executeQuery();
      boolean isExist = rs.next();
      if (pstmt != null) {
        pstmt.close();
      }
      closeConnection();
      return isExist;

    }
    catch (Exception e) {
      try {
        if (pstmt != null)
          pstmt.close();
      }
      catch (Exception e1) {}
      closeConnection();
      throw new TranFailException(
        "099995",
        "icbc.missign.MenuDAO3.isExistMoudle",
        e.getMessage(),
        genMsg.getErrMsgByLang((String)getOperation().getSessionData("LangCode"), "099995"));
    }
  }

}