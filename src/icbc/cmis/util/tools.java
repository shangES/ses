package icbc.cmis.util;

import java.util.*;
import java.sql.*;
import icbc.cmis.base.*;
import java.text.DecimalFormat;
/**
 * A Bean class.
 * <P>
 * @author Longbow
 */
public class tools  {
  public static String display(String inStr){
    /*
    String rStr = new String();
    if (inStr != null)
      try{
        rStr = new String(inStr.getBytes("8859_1"),"GBK");
      } catch (java.io.UnsupportedEncodingException e ){
        rStr = null;
      } finally{
        return rStr;
      }
    else
      return null;
    */
    return inStr;
  }
  public static String fmat(String inStr,int decimalLength){
    String format = "#,##0";
    if(decimalLength > 0) {
      format += ".";
      for (int i = 0; i < decimalLength; i++) {
        format += "0";
      }
    }

    try{
      Double value = Double.valueOf(inStr);
      DecimalFormat df = new DecimalFormat(format);
      String ts = df.format(value);
      return ts;
    }catch (NullPointerException ex){
      return "0";
    }catch (Exception ex){
      return inStr;
    }
  }
  public static String formatIntToString(int nValue , int nLength) {
    String strValue = String.valueOf(nValue);
    int nValueLen = strValue.length() ;
    String strReturn = new String();
    for (int i=0 ; i<(nLength-nValueLen) ; i++) {
      strReturn += "0";
    }
    return strReturn+strValue;
  }
  public static String FullLength(String inStr,int StrLength){
    try{
      String outString = inStr;
      int applen = StrLength-inStr.getBytes().length;
      if (applen < 0 ) return inStr;
      for (int i = 0;i< applen;i++){
        outString += ".";
      }
      return outString;
    }catch (Exception ex){
      return inStr;
    }
  }
/**
 * Insert the method's description here.
 * Creation date: (2003-3-19 13:53:18)
 */
public static String getRsString(ResultSet rs, String item) {
  try {
    return (rs.getString(item)==null)?"":(rs.getString(item).trim()) ;
  }
  catch (Exception ex) {
    return "";
  }
  }
/**
 * Insert the method's description here.
 * Creation date: (2003-3-17 14:59:34)
 */ 


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
/**
 * Insert the method's description here.
 * Creation date: (2003-3-19 13:53:18)
 */
public void newMethod() {}
  public static String nvl(String inStr,String replaceStr){
    if (inStr == null) return replaceStr;
    else return inStr;
  }
/**
 * Insert the method's description here.
 * Creation date: (2003-3-19 13:53:18)
 */
  public static String replaceNullString(String tmpStr){
  try {
    String a = (tmpStr == null)?"":tmpStr;
    return a;
  }
  catch (Exception ex) {
    return "";
  }
  }
/**
 * Insert the method's description here.
 * Creation date: (2003-3-19 13:53:18)
 */
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
}
