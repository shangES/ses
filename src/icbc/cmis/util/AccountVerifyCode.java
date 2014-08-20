package icbc.cmis.util;

public class AccountVerifyCode {
  final static int[] p = { 11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73};

  public AccountVerifyCode() {
  }

  public static String verifyCode(String acc) {
    int ret = 0,m;
    for (int i = 0; i < 17; i++) {
      m = Integer.valueOf(acc.substring(i,i + 1)).intValue();
      ret += p[i] * m;
    }
    m = ret % 97;
    ret = 97 - m;
    if(ret > 9) {
      return String.valueOf(ret);
    } else {
      return "0" + String.valueOf(ret);
    }
  }

  public static String to19(String acc) {
    if(acc == null) return "";
    if(acc.length() < 17) return acc;
    return acc.substring(0,17) + verifyCode(acc.substring(0,17));
  }

  public static boolean isValid(String acc) {
    if(acc == null) return true;
    if(acc.length() < 17) return true;
    String b = acc.substring(0,17);
    String t = acc.substring(17,19);
    String p = verifyCode(b);
    return t.equals(p);
  }
}