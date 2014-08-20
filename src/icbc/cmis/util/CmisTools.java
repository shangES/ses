package icbc.cmis.util;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class CmisTools {

  public CmisTools() {
    super();
  }
   public static String getSpace(String str){
     if (str.equals("")) return "&nbsp;";
     else return str;
  }
   //将数字值转换为加逗号显示
   public static String extractNumber(String str){
     if (str==null || str.equals("")) return "";
     int point_pos = str.indexOf(".");
     String int_part="";
     if (point_pos==-1) { //整数
        return getIntPart(str)+".00";
     }else{
        int_part = str.substring(0,point_pos);
        return getIntPart(int_part)+str.substring(point_pos);
     }
   }
   private static String getIntPart(String str){
     int len = str.length();
     String ret = "";
     if (len<=3) return str;
     int mod_len = len % 3;
     ret = str.substring(0,mod_len);
     for (int i=mod_len;i<len;i=i+3)
        ret += ","+str.substring(i,i+3);
     if (ret.substring(0,1).equals(","))
         ret = ret.substring(1);
     return ret;
    }
   public static String extractDate(String str,int len){
      if (str==null || str.equals("")) return "";
      if (str.length()!=6 && str.length()!=8) return str;
      if (len==6) return str.substring(0,4)+"/"+str.substring(4);
      if (len==8) return str.substring(0,4)+"/"+str.substring(4,6)+"/"+str.substring(6,8);
      return str;
  }
}