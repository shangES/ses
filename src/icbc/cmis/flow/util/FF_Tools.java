package icbc.cmis.flow.util;

import java.util.*;
import java.sql.*;
import icbc.cmis.base.*;

public class FF_Tools{

  public static String getRsString(ResultSet rs, String item) {
  try {
    return (rs.getString(item)==null)?"":(rs.getString(item).trim()) ;
  }
  catch (Exception ex) {
    return "";
  }
  }

  public static double getRsDouble(ResultSet rs, String item) {
  try {
    String a = (rs.getString(item)==null)?"0":(rs.getString(item).trim()) ;
    if (a.length()==0){
      a = "0";
    }
    return (Double.valueOf(a).doubleValue());
  }
  catch (Exception ex) {
    return 0;
  }
  }

  public static String replaceNullString(String tmpStr){
  try {
    String a = (tmpStr == null)?"":tmpStr;
    return a;
  }
  catch (Exception ex) {
    return "";
  }
  }

  public static String replaceNullStringWithNbsp(String tmpStr){
  try {
    String a = replaceNullString(tmpStr);
    if (a.length()== 0 ){
      a = "&nbsp;";
    }
    return a;
  }
  catch (Exception ex) {
    return "&nbsp;";
  }
  }

  /**
   * 将数值型字符保留小数点后dec位
   * @param str
   * @param dec
   * @return
   */
  public static String reserveDecimal(String str,int dec ){
  int i = 0;
  String tmp = "";
  String tmp1 ="";
  try{
    i = str.lastIndexOf(".");
    tmp = str.substring(0,i) ;
    tmp1 = str.substring(i + 1 );
    if (tmp1.length()<=dec){
      return str;
    }else{
      return (tmp + "." + tmp1.substring(0,4));
    }
  }catch (Exception ex){
    return str;
  }
  }

  /**
   * 将金额字符转化格式 #，###，###.##
   * @param tmpStr
   * @return
   */
  public static String toAmtStr(String tmpStr){
  String srcStr ="";
  String intStr ="";
  String objStr ="";
  String dolStr ="";
  String nvl = "";
  int dolinx = 0;
  int i=0;
  int j=0;
  try {
    if (tmpStr == null ){
      srcStr = "";
      return srcStr;
    }
    if (tmpStr.length()==0){
      return "";
    }
    srcStr = tmpStr.trim();
    dolinx = srcStr.indexOf(".");
    if (dolinx == -1){
      intStr = srcStr;   //整数部分
      dolStr = "";       //小数部分
    }else{
      intStr = srcStr.substring(0,dolinx);
      dolStr = srcStr.substring(dolinx,srcStr.length());
    }
    if (intStr.charAt(0)=='-'){
      nvl = "-";
      intStr = intStr.substring(1,intStr.length());
    }else
    if (intStr.charAt(0)=='+'){
      nvl = "+";
      intStr = intStr.substring(1,intStr.length());
    }
    j = 1;
    for ( i = intStr.length() -1;i >= 0;i --){
      if (j  == 3){
        objStr = "," + intStr.charAt(i) + objStr;
        j=1;
      }else{
        objStr = intStr.charAt(i) + objStr;
        j++;
      }
    }
    if (objStr.charAt(0)==','){
      objStr = objStr.substring(1,objStr.length());
    }
    objStr = nvl + objStr + dolStr;
    return objStr;
  } catch (Exception ex){
    return tmpStr.trim();
  }
  }

  public static KeyedDataCollection  HtableToKdColl(Hashtable h_tmp){
  KeyedDataCollection h_kd = new KeyedDataCollection();
  try {
    if ( h_tmp == null ){
      return null;
    }
//    h_kd.setName(KdCollname);
    for (Enumeration enum_tmp = h_tmp.keys();enum_tmp.hasMoreElements();){
      String keycode = (String) enum_tmp.nextElement();
      Object keydes  =  h_tmp.get(keycode);
      DataElement field1 = new DataElement();
      field1.setName(keycode);
      h_kd.addElement(field1);
      h_kd.setValueAt(keycode,keydes);
    }
    return h_kd;
  }
  catch (Exception ex){
    return null;
  }
  }
}