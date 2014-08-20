package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.base.MuiTranFailException;
import icbc.cmis.base.TranFailException;
import java.sql.*;
import java.util.Vector;

public class ChooseAreaDAO extends CmisDao {

  public ChooseAreaDAO(icbc.cmis.operation.CmisOperation op) {
  super(op);
  }

  public String getArea(String areaCode,String range) throws TranFailException {
    int disable = 5;
    if(range.charAt(4) == '0') {
      disable = 4;
      if(range.charAt(3) == '0') {
        disable = 3;
        if(range.charAt(2) == '0') {
          disable = 2;
          if(range.charAt(1) == '0') {
            disable = 1;
          }
        }
      }
    }
    StringBuffer xml = new StringBuffer(1000);
    String sql = null;
//    Statement stmt = null;
    java.sql.PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      this.getConnection();
      //stmt = conn.createStatement();
      //xml.append("<?xml version='1.0' encoding='GBK' ?><banks ");
      xml.append("<banks ");
      //取得行级别
//      sql = "select AREA_CODE, AREA_NAME, BANK_FLAG from mag_area where AREA_CODE = '" + areaCode + "'";
      sql = "select AREA_CODE, AREA_NAME, BANK_FLAG from mag_area where AREA_CODE = ? ";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, areaCode);
      rs = pstmt.executeQuery();
//      rs = stmt.executeQuery(sql);
      rs.next();
      xml.append(" currentCode='"+rs.getString(1)+"'");
      xml.append(" currentName='"+rs.getString(2)+"'");
      xml.append(" currentLevel='"+rs.getString(3)+"'>");

      //查询上级行及下一级行
//    sql = "select AREA_CODE, AREA_NAME, BANK_FLAG, BELONG_BANK from mag_area  start with area_code = '" + areaCode+"' connect by area_code = prior belong_bank union select AREA_CODE, AREA_NAME, BANK_FLAG, BELONG_BANK from mag_area,minit  where belong_bank = '"+ areaCode +"' and BANK_FLAG < " + disable + " and area_code = local_code(+) and local_code is not null ";
      pstmt.close();
      sql = "select AREA_CODE, AREA_NAME, BANK_FLAG, BELONG_BANK from mag_area  start with area_code = ? connect by area_code = prior belong_bank union select AREA_CODE, AREA_NAME, BANK_FLAG, BELONG_BANK from mag_area,minit  where belong_bank = ? and BANK_FLAG < ? and area_code = local_code(+) and local_code is not null ";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, areaCode);
      pstmt.setString(2, areaCode);
      pstmt.setInt(3,disable);
//      pstmt.setString(3, disable);
      rs = pstmt.executeQuery();
//      rs = stmt.executeQuery(sql);
      int level = 9;
      xml.append("<level9>");
      while (rs.next()) {
        int tlevel = rs.getInt(3);
        if(tlevel == 0) {
          if (!areaCode.equals("00000000")&&range.charAt(0) != '1') {
            continue;
          } else {
            tlevel = 1;
          }
        }
        if(tlevel != level) {
          xml.append("</level" + level + ">");
          level = tlevel;
          xml.append("<level" + level + ">");
        }
        xml.append("<bank c='" + rs.getString(1) + "' n='" + rs.getString(2) + "' l='" + tlevel + "' />");
      }
      xml.append("</level" + level + ">");
      xml.append("</banks>");
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
//      throw new TranFailException("cmisutil212","icbc.cmis.util.ChooseAreaDAO",ex.getMessage() + sql,"产生地区列表错误！");
	  throw new MuiTranFailException("099993","ChooseAreaDAO.getArea()"," ",(String)this.getOperation().getSessionData("LangCode"));
    }
    finally {
      if(rs != null) try {rs.close();} catch (Exception ex) {};
      if(pstmt != null) try {pstmt.close();} catch (Exception ex) {};
      this.closeConnection();
    }
    return xml.toString();
  }
  
  public String getArea2(String areaCode,String range,String customercode) throws TranFailException {
      int disable = 5;
      if(range.charAt(4) == '0') {
        disable = 4;
        if(range.charAt(3) == '0') {
          disable = 3;
          if(range.charAt(2) == '0') {
            disable = 2;
            if(range.charAt(1) == '0') {
              disable = 1;
            }
          }
        }
      }
      StringBuffer xml = new StringBuffer(1000);
      String sql = null;
//      Statement stmt = null;
      java.sql.PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        this.getConnection();
        //stmt = conn.createStatement();
        //xml.append("<?xml version='1.0' encoding='GBK' ?><banks ");
        xml.append("<banks ");
        //取得行级别
//        sql = "select AREA_CODE, AREA_NAME, BANK_FLAG from mag_area where AREA_CODE = '" + areaCode + "'";
        sql = "select AREA_CODE, AREA_NAME, BANK_FLAG from mag_area where AREA_CODE = ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, areaCode);
        rs = pstmt.executeQuery();
//        rs = stmt.executeQuery(sql);
        rs.next();
        xml.append(" currentCode='"+rs.getString(1)+"'");
        xml.append(" currentName='"+rs.getString(2)+"'");
        xml.append(" currentLevel='"+rs.getString(3)+"'>");

        //查询上级行及下一级行
//      sql = "select AREA_CODE, AREA_NAME, BANK_FLAG, BELONG_BANK from mag_area  start with area_code = '" + areaCode+"' connect by area_code = prior belong_bank union select AREA_CODE, AREA_NAME, BANK_FLAG, BELONG_BANK from mag_area,minit  where belong_bank = '"+ areaCode +"' and BANK_FLAG < " + disable + " and area_code = local_code(+) and local_code is not null ";
        pstmt.close();
        sql = "select    AREA_CODE, AREA_NAME, BANK_FLAG, BELONG_BANK from (select AREA_CODE, AREA_NAME, BANK_FLAG, BELONG_BANK from mag_area start with area_code = ? connect by belong_bank = prior area_code      ) a,ta20001L where ta20001L001=? and a.area_code=ta20001L002 ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, areaCode);
        pstmt.setString(2, customercode);        
//        pstmt.setString(3, disable);
        rs = pstmt.executeQuery();
//        rs = stmt.executeQuery(sql);
        int level = 9;
        xml.append("<level9>");
        while (rs.next()) {
          int tlevel = rs.getInt(3);
          if(tlevel == 0) {
            if (!areaCode.equals("00000000")&&range.charAt(0) != '1') {
              continue;
            } else {
              tlevel = 1;
            }
          }
          if(tlevel != level) {
            xml.append("</level" + level + ">");
            level = tlevel;
            xml.append("<level" + level + ">");
          }
          xml.append("<bank c='" + rs.getString(1) + "' n='" + rs.getString(2) + "' l='" + tlevel + "' />");
        }
        xml.append("</level" + level + ">");
        xml.append("</banks>");
      }
      catch (TranFailException ex) {
        throw ex;
      }
      catch (Exception ex) {
//        throw new TranFailException("cmisutil212","icbc.cmis.util.ChooseAreaDAO",ex.getMessage() + sql,"产生地区列表错误！");
		throw new MuiTranFailException("099993","ChooseAreaDAO.getArea2()"," ",(String)this.getOperation().getSessionData("LangCode"));
      }
      finally {
        if(rs != null) try {rs.close();} catch (Exception ex) {};
        if(pstmt != null) try {pstmt.close();} catch (Exception ex) {};
        this.closeConnection();
      }
      return xml.toString();
    }
}