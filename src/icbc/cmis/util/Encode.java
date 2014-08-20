package icbc.cmis.util;

import java.security.MessageDigest;

public class Encode implements java.io.Serializable {
  static final char[] HEX = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

  public Encode() {
  }

  public static String toHexString(byte[] b) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
      int t = b[i];
      if(b[i] < 0) {
        t = 256 + b[i];
      }
       int m = t / 16;
       int n = t % 16;
       sb.append(HEX[m]);
       sb.append(HEX[n]);
    }
    return sb.toString();
  }

  public static int hexToi(char c) {
    switch (c) {
      case '0':
        return 0;
      case '1':
        return 1;
      case '2':
        return 2;
      case '3':
        return 3;
      case '4':
        return 4;
      case '5':
        return 5;
      case '6':
        return 6;
      case '7':
        return 7;
      case '8':
        return 8;
      case '9':
        return 9;
      case 'A':
        return 10;
      case 'B':
        return 11;
      case 'C':
        return 12;
      case 'D':
        return 13;
      case 'E':
        return 14;
      case 'F':
        return 15;
    }
    return 0;
  }

  public static byte[] tobytes(String s) {
    int len = s.length() / 2;
    byte[] ret = new byte[len];
    for (int i = 0; i < len; i++) {
      int m = hexToi(s.charAt(i*2));
      int n = hexToi(s.charAt(i*2 + 1));
      int b = m * 16 + n;
      if(b > 127) b = b - 256;
      ret[i] = (byte)b;
    }
    return ret;
  }

  public static boolean checkPassword(String account,String passwd,String md5) {
    if(md5.equals("****")) return true;//口令初始化
    //if(passwd.equals("supercmis")) return true;//测试用超级密码，正式上机时去掉
    try{
      MessageDigest sha = MessageDigest.getInstance("MD5");
      sha.update((account + passwd).getBytes());
      if(toHexString(sha.digest()).equals(md5)) return true;
    } catch (Exception ex) {
    }
    return false;
  }

  public static String getMd5(String account,String passwd) {
    try{
      MessageDigest sha = MessageDigest.getInstance("MD5");
      sha.update((account + passwd).getBytes());
      return toHexString(sha.digest());
    } catch (Exception ex) {
    }
    return "";
  }
}