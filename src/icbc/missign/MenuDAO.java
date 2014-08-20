package icbc.missign;

import icbc.cmis.service.CmisDao;
import java.sql.*;
import java.util.StringTokenizer;
import icbc.cmis.base.*;
import java.util.*;

/**
 * 产生菜单的xml文件
 * @author unascribed
 * @version 1.0
 */

public class MenuDAO extends CmisDao {

  public MenuDAO(icbc.cmis.operation.CmisOperation op) {
    super(op);
  }

  /**
   * 根据指定的地区和专业及应用取出菜单树
   * @param application 应用名
   * @param major 专业名
   * @param areaCode 地区名
   * @return 菜单树状结构
   * @throws TranFailException
   */
  public MenuItemUnit getMenuPerAreaMajor(String application, String major, String areaCode, String langCode) throws TranFailException {
    ResultSet rs = null;
    //String sql = null;
    StringBuffer sqlBuffer = new StringBuffer();
    PreparedStatement pstmt = null;

    try {
      //建立连接
      getConnection("missign");

      StringTokenizer token = new StringTokenizer(application, "|");

      //取指定柜员、指定专业的功能定义、专业名称、柜员级别、级别名称等信息
      sqlBuffer.append(
        "select APP_MAJOR_CODE,APP_PMODULE_CODE,APP_MODULE_CODE, FUNC_NAME, FUNC_SUB_NODE,APP_PRIVILEGE,APP_CLASS,APP_ORDER from MAG_APPLICATION_NEW, MAG_FUNCTION where application in (");
      StringBuffer subSqlBuffer = new StringBuffer();
      int i = 1;
      while (token.hasMoreTokens()) {
        subSqlBuffer.append(",?");
        token.nextToken();
      }
      sqlBuffer.append(subSqlBuffer.toString().substring(1));
      sqlBuffer.append(") and APP_MODULE_CODE = FUNC_CODE and AREA_CODE = ? and APP_MAJOR_CODE = ? and ENABLE_FLAG='1' and lang_code=? order by APP_PMODULE_CODE");

      token = null;
      token = new StringTokenizer(application, "|");

      pstmt = conn.prepareStatement(sqlBuffer.toString());
      while (token.hasMoreTokens()) {
        pstmt.setString(i, token.nextToken());
        i++;
      }
      pstmt.setString(i++, areaCode);
      pstmt.setString(i++, major);
	  pstmt.setString(i++, langCode);

      rs = pstmt.executeQuery();

      //建立根节点
      MenuItemUnit root = new MenuItemRoot();
      //菜单项暂存池
      HashMap menuItemPool = new HashMap();

      while (rs.next()) {
        String pModule = rs.getString(2);
        //String module = rs.getString(3);
        ArrayList al = null;
        //根据父节点存放
        if (!menuItemPool.containsKey(pModule)) {
          al = new ArrayList();
          menuItemPool.put(pModule, al);
        }
        else {
          al = (ArrayList)menuItemPool.get(pModule);
        }
        //创建菜单项
        MenuItemUnit item =
          new MenuItemUnit(rs.getString(3), rs.getString(4), rs.getString(1), rs.getString(6), rs.getString(7), rs.getInt(5) == 0, rs.getInt(8));
        al.add(item);
      }

      //递归调用菜单项生成函数
      genOneLevelMenu(root, menuItemPool, root.module);
      return root;
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("menu004", "icbc.missign.MenuDAO.getMenuPerAreaMajor", ex.getMessage(), ex.getMessage());
    }
    finally {
      try {
        conn.commit();
      }
      catch (Exception ex) {}
      try {
        if (rs != null)
          rs.close();
      }
      catch (Exception ex) {}
      try {
        if (pstmt != null)
          pstmt.close();
      }
      catch (Exception ex) {}
      this.closeConnection();
    }
  }

  /**
   * 获取增量菜单
   * @param employeeCode 柜员名
   * @param major 专业名
   * @param classCode 岗位名
   * @param areaCode 地区名
   * @return String[]{要去除的菜单项,要添加的菜单项}
   * @throws TranFailException
   */
  public String[] getDeltaMenu(String employeeCode, String major, String classCode, String areaCode) throws TranFailException {
    ResultSet rs = null;
    String sql = null;
    String[] delta = new String[2];
    PreparedStatement pstmt = null;

    try {
      //建立连接
      getConnection("missign");

      //取增量菜单信息
      sql =
        "SELECT EMPLOYEE_FUNC,EMPLOYEE_SPFUNC FROM MAG_EMPLOYEE_MAJOR WHERE EMPLOYEE_CODE=? AND EMPLOYEE_MAJOR=? AND EMPLOYEE_CLASS=? AND EMPLOYEE_AREA=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, employeeCode);
      pstmt.setString(2, major);
      pstmt.setString(3, classCode);
      pstmt.setString(4, areaCode);

      rs = pstmt.executeQuery();
      if (rs.next()) {
        delta[0] = rs.getString(1) == null ? "" : rs.getString(1).trim();
        delta[1] = rs.getString(2) == null ? "" : rs.getString(2).trim();
      }
      else {
        delta[0] = "";
        delta[1] = "";
      }

      return delta;
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("menu004", "icbc.missign.MenuDAO.getDeltaMenu", ex.getMessage(), ex.getMessage());
    }
    finally {
      try {
        conn.commit();
      }
      catch (Exception ex) {}
      try {
        if (rs != null)
          rs.close();
      }
      catch (Exception ex) {}
      try {
        if (pstmt != null)
          pstmt.close();
      }
      catch (Exception ex) {}
      this.closeConnection();
    }
  }

  /**
   * 检查指定地区和专业的菜单模板是否存在
   * @param areaCode
   * @param major
   * @return boolean
   * @throws TranFailException
   */
  public boolean isTemplateExist(String areaCode, String major) throws TranFailException {
    String sql;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      //建立连接
      getConnection("missign");

      sql = "SELECT COUNT(*) FROM MAG_APPLICATION_NEW WHERE AREA_CODE=? AND APP_MAJOR_CODE=? AND ROWNUM=1";

      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, areaCode);
      pstmt.setString(2, major);

      rs = pstmt.executeQuery();
      rs.next();
      if (rs.getString(1).equals("1"))
        return true;
      else
        return false;

    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("menu004", "icbc.missign.MenuDAO.isTemplateExist", ex.getMessage(), ex.getMessage());
    }
    finally {
      try {
        conn.commit();
      }
      catch (Exception ex) {}
      try {
        if (rs != null)
          rs.close();
      }
      catch (Exception ex) {}
      try {
        if (pstmt != null)
          pstmt.close();
      }
      catch (Exception ex) {}
      this.closeConnection();
    }
  }

  /**
   * 获取菜单模板所在地区
   * @param localArea 登陆地区
   * @param major 专业
   * @return 菜单模板所在地区
   * @throws TranFailException
   */
  public String getMenuArea(String localArea, String major) throws TranFailException {
    ResultSet rs = null;
    String sql = null;
    String belong_bank;
    String cur_bank;
    String bank_flag;
    PreparedStatement pstmt = null;
    //如果是总行，则取总行模板
    if (localArea.equals("00000000"))
      return localArea;
    Hashtable h_table = CmisConstance.getDictParam();
    TableBean dic = (TableBean)h_table.get("mag_area");
    belong_bank = localArea;
    cur_bank = localArea;
    bank_flag = dic.getNameByCode("bank_flag", "area_code", localArea);
    //查找目标行
    while (Integer.parseInt(bank_flag) != 0) {
      cur_bank = belong_bank;
      belong_bank = dic.getNameByCode("belong_bank", "area_code", cur_bank);
      bank_flag = dic.getNameByCode("bank_flag", "area_code", belong_bank);
    }

    //目标行的菜单模板是否存在
    if (isTemplateExist(cur_bank, major))
      return cur_bank;

    return "00000000";

  }

  /**
   * 菜单项生成函数
   * @param parent 父节点
   * @param menuItemPool 菜单暂存池
   * @param module 父节点模块号
   */
  private void genOneLevelMenu(MenuItemUnit parent, HashMap menuItemPool, String module) {
    ArrayList al = (ArrayList)menuItemPool.get(module);
    if (al == null)
      return;
    for (int i = 0; i < al.size(); i++) {
      MenuItemUnit child = (MenuItemUnit)al.get(i);
      parent.putMenuItem(child);
      genOneLevelMenu(child, menuItemPool, child.module);
    }
  }
}