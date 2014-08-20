package icbc.cmis.util;

/**
 * Title:        cmis
 * Description:  cmis3
 * Copyright:    Copyright (c) 2001
 * Company:      icbc
 * @author
 * @version 1.0
 */

public class StringBufferR {
  private StringBuffer strb = null;

  public StringBufferR(String str) {
    strb = new StringBuffer(str);
  }

  public int findStr(String str,int begin) {
    int strlen = str.length();
    int ret = -1;
    for(int i=begin;i<= strb.length() - strlen ; i++) {
      if(strb.substring(i,i + strlen).equals(str)) {
        ret = i;
        break;
      }
    }
    return ret;
  }

  public void replace(String src,String des) {
    int i,j;
    int srclen = src.length();
    j = 0;
    while((i = this.findStr(src,j)) != -1) {
      strb.delete(i,i + srclen);
      strb.insert(i,des);
      j = i  + des.length();
    }
  }

  public String toString() {
    return strb.toString();
  }

  public static void main (String[] args) {
    StringBufferR sbr = new StringBufferR("abc.def.kkk");
    //System.out.println(sbr);
    sbr.replace(".","_");
    //System.out.println(sbr);
  }
}