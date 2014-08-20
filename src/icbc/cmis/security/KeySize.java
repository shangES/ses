/*
 * 创建日期 2004-11-22
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.security;

/**
 * @author ZJFH-yanb
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class KeySize {
  private final int size;
  
  private KeySize(int size){
  	this.size = size;
  }
  
  public static final KeySize Bits128 = new KeySize(128);
  public static final KeySize Bits192 = new KeySize(192);
  public static final KeySize Bits256 = new KeySize(256);
}
