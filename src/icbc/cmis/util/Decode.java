package icbc.cmis.util;

public class Decode {

  public Decode() {
  }

  /**
   * 将 @@NNNN 编码转换为汉字
   * @param src
   * @return
   */
  public static String decode(String src) {
    //目标串
    String des = "";
    //源串长度
    int len = src.length();
    //@@起始位置指针
    int i = 0;
    //下一次开始查找位置指针
    int k = 0;
    while(true) {
      //查找@@的位置，即可能是汉字的起始位置
      i = src.indexOf("@@",k);
      //若找不到@@则表示没有汉字
      if(i == -1) {
        //若指针未到字符串的尾部，则将剩下的字符串复制到目标串
        if(k < len) des += src.substring(k,len);
        //完成
        break;
      }
      //复制非汉字到目标串
      des += src.substring(k,i);
      //若@@后没有足够的位数，则表示当前@@不是汉字的起始符号
      if(i + 6 > len) {
        //将剩下的字符串复制到目标串
        des += src.substring(k,len);
        break;
      }
      //取@@后的四位unicode编码
      String ts = src.substring(i + 2,i + 6);
      try {
        //目标串加一个汉字
        char tc = (char)Integer.parseInt(ts,16);
        des += tc;
        k = i + 6;
      }
      catch (Exception ex) {
        //转换出错，则表示@@后不是汉字编码
        des += "@";
        k = i + 1;
      }
    }
    return des;
  }

}