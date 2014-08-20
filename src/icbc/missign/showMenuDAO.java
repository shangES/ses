package icbc.missign;

import icbc.cmis.service.CmisDao;
import java.lang.StringBuffer;
import java.sql.*;
import java.util.StringTokenizer;
import java.util.Vector;
import icbc.cmis.base.TranFailException;

/**
 * 产生菜单的xml文件
 * @author unascribed
 * @version 1.0
 */

public class showMenuDAO extends CmisDao {
  static String space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
  public showMenuDAO(icbc.cmis.operation.CmisOperation op) {
  super(op);
  }

  /**
   * 产生一项菜单项
   * @param row
   * @param level
   * @param menu
   */
  public void menuItem(String[] row, int level, StringBuffer menu) {
        String id = row[0];
        String name = row[1];
        String pid = row[2];
        String child = row[3];
        String status = row[4];
        String developer = row[5];
        /*try {
          developer = new String(developer.getBytes("GBK"),"GBK");
        }
        catch (Exception ex) {
          System.out.println(developer);
        }*/


        menu.append("<tr>");
        if(child.equals("0")) {
//          menu.append("<td STYLE='padding-left:'"+ level +"' em;'>"+space.substring(0,level * 6) + id + " " + name + "</td>");
          menu.append("<td STYLE='padding-left:"+ level +"em;'>"+ id + " " + name + "</td>");
          menu.append("<td>"+((row[8].trim().equals(""))?"&nbsp;":row[8]) + "</td>");
          menu.append("<td>"+((row[9].trim().equals(""))?"&nbsp;":row[9]) + "</td>");
          menu.append("<td>"+((status.trim().equals(""))?"&nbsp;":status) + "</td>");
          menu.append("<td>"+((developer.trim().equals(""))?"&nbsp;":developer) + "</td>");
          menu.append("<td>"+((row[6].trim().equals(""))?"&nbsp;":row[6]) + "</td>");
          menu.append("<td>"+((row[7].trim().equals(""))?"&nbsp;":row[7]) + "</td>");
        }else{
          menu.append("<td colspan='7' STYLE='padding-left:"+ level +"em;'>"+ id + " " + name + "</td>");
//          menu.append("<td colspan='7'>"+space.substring(0,level * 2) + id + " " + name + "</td>");
        }
        menu.append("</tr>");
  }

  /**
   * 产生一层菜单
   * @param pstmt
   * @param level
   * @param pcode
   * @param menu
   * @throws TranFailException
   */
  public void getOneLevel(PreparedStatement pstmt,int level, String pcode,StringBuffer menu)  throws TranFailException {
    ResultSet rs = null;
    Vector rows = new Vector();
    try {
      pstmt.setString(2,pcode);
      rs = pstmt.executeQuery();
      while(rs.next()) {
        String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10)};
        rows.add(row);
      }
      try { if(rs != null) rs.close(); } catch (Exception ex) {}
      if(!rows.isEmpty()) {
        for (int i = 0; i < rows.size(); i++) {
          String[] row = (String[])rows.get(i);
          menuItem(row,level,menu);
          getOneLevel(pstmt,level + 1,row[0],menu);
        }
      }
    } catch(TranFailException ex) {
      throw ex;
    } catch(Exception ex) {
      throw new TranFailException("menu003","icbc.missign.MenuDAO.getOneLevel",ex.getMessage(),ex.getMessage());
    }

  }


  public String allMenu() throws TranFailException {
    Statement stmt = null;
    ResultSet rs = null;
    String sql = null;
    PreparedStatement pstmt = null;
    StringBuffer menu = new StringBuffer(50000);

    try {
      //建立连接
      this.getConnection("missign","missign");
      stmt = conn.createStatement();

      String tsql = "select * from mag_major order by major_code";
      rs = stmt.executeQuery(tsql);
      //组织菜单查询语句
      //                 1             2            3              4
      sql = "select APP_MODULE_CODE, FUNC_NAME, APP_PMODULE_CODE, FUNC_SUB_NODE, decode(func_status,'0','提交','1','正在修改','2','正在开发','未开发'),nvl(func_developer,' '),nvl(LAST_MODIFY_DATE,' '), nvl(EXPECT_FINISH_DATE,' '),app_privilege,app_class from MAG_APPLICATION_NEW, MAG_FUNCTION where APP_MAJOR_CODE = ? and APP_PMODULE_CODE = ? and APP_MODULE_CODE = FUNC_CODE order by APP_ORDER";

      pstmt = conn.prepareStatement(sql);
      menu.append("<table border='1'>");
      while (rs.next()) {
        pstmt.setString(1,rs.getString(1));
        menu.append("<tr bgcolor='#CCCCCC'><td colspan='7'>" + rs.getString(1) + " " + rs.getString(2) + "</td></tr>");
        getOneLevel(pstmt,0,"00000",menu);
      }
      menu.append("</table>");

    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("menu002","icbc.missign.MenuDAO.getMenuXML",ex.getMessage(),ex.getMessage());
    }
    finally {
      try { if(rs != null) rs.close(); } catch (Exception ex) {}
      try { if(stmt != null) rs.close(); } catch (Exception ex) {}
      try { if(pstmt != null) rs.close(); } catch (Exception ex) {}
      this.closeConnection();
    }
    return menu.toString();
  }
}