/*
 * 创建日期 2004-11-9
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
public class Iterate2 {
  
  
  //密钥长度(至少)
  private static final int keyLength = 1;
  
  //密钥数组
  byte[] key = null;
  
  /**
   * <b>功能描述: </b><br>
   * <p>构造函数</p>
   * @param key 密钥
   */
  public Iterate2(byte[] key){
  	this.key = key;
  }
  
  /**
   * <b>功能描述: </b><br>
   * <p>生成key</p>
   * @return byte[] 密钥
   */
  public static byte[] genKey(){ 
  	byte[] key = new byte[Iterate2.keyLength];
  	for(int i=0;i<key.length;i++)
  	  key[i] = (byte)(Math.round((float)Math.random() * 256));
  	return key;
  }
  
  /**
   * <b>功能描述: </b><br>
   * <p>加密</p>
   * @param text 明文
   * @return byte[] 密文
   * @throws Exception
   */
  public byte[] encode(byte[] text) throws Exception{
	testKey();
	byte[] crypto = new byte[text.length];
	for(int i=0;i<text.length;i++){
	  crypto[i] = (byte)((int)text[i] + (int)key[(i%key.length)]);
	  if(i<key.length)
	    crypto[i] = (byte)(crypto[i] ^ key[i]);
	}
	crypto = reverse(crypto);
	return crypto;
  }
  
  /**
   * <b>功能描述: </b><br>
   * <p>解密</p>
   * @param crypto 密文
   * @return byte[] 明文
   * @throws Exception(int)crypto[i]
   */
  public byte[] decode(byte[] crypto) throws Exception{
	testKey();
	byte[] text = new byte[crypto.length];
	crypto = reverse(crypto);
	for(int i=0;i<crypto.length;i++){
	  if(i<key.length)
	    crypto[i] = (byte)(crypto[i] ^ key[i]);
	  text[i] = (byte)((int)crypto[i] - (int)key[(i%key.length)]);
	}
	return text;
  }
  
  /**
   * <b>功能描述: </b><br>
   * <p>测试密钥是否为空和长度是否合适</p>
   */
  private void testKey() throws Exception{
  	if(key == null || key.length<Iterate2.keyLength)
  	  throw new Exception("密钥为空或者长度太短");
  }
  
  private byte[] reverse(byte[] src){
  	byte[] res = new byte[src.length];
  	for(int i=0;i<res.length;i++)
  	  res[i] = src[src.length-1-i];
  	return res;
  }
  
}
