package icbc.cmis.util;

public class Func_XMLfiltrate {

  /**
   *  
   * Description  : 过滤XML输出的特殊字符
   * CreationDate : 2007-6-20 13:35:24
   * @author     : 魏洪波
   *   
   * @return
   */

  public static String validXml(String xml) {
    String ret = xml;

    final String[] FROM = { "&", "<", ">", "'", "\"" };
    final String[] TO = { "&amp;", "&lt;", "&gt;", "&apos;", "&quot;" };

    for (int i = 0; i < FROM.length; i++) {
      ret = replaceAll(ret, FROM[i], TO[i]);
    }

    return ret;
  }

  /**
   *  
   * Description  : 替换 (注意:区分大小写)
   * CreationDate : 2007-6-20 15:05:15
   * @author     : 魏洪波
   *   
   * @param src
   * @param replaceEx
   * @param relacement
   * @return
   */
  public static String replaceAll(String src, String replaceEx, String relacement) {
    String ret = "";

    int srcLen = src.length();
    int repLen = replaceEx.length();

    int start = 0;

    int i;
    for (i = 0; i < srcLen - repLen + 1;) {
      if (src.substring(i, i + repLen).equals(replaceEx)) {
        ret += src.substring(start, i) + relacement;

        i += repLen;
        start = i;
      }
      else {
        i++;
      }

    }

    if (i+repLen-1 > start) {
      ret += src.substring(start, i+repLen-1);
    }

    return ret;

  }

}