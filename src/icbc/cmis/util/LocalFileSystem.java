package icbc.cmis.util;
import java.io.*;
import icbc.cmis.base.TranFailException;
/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LocalFileSystem {
  String directory = null;
  //File[] files = null;
  public LocalFileSystem(){
  }
  
  public void setPath(String path) throws Exception{
    directory = path;
    if(directory.equals(""))
      throw new TranFailException("LocalFileSystem","icbc.cmis.util.LocalFileSystem","本地文件系统路径设置错误","本地文件系统路径设置错误");
  }
  
  public int isFileCompleted(String fileName) throws Exception{
    if(directory == null)
      throw new TranFailException("LocalFileSystem","icbc.cmis.util.LocalFileSystem","本地文件系统路径设置未设置","本地文件系统路径设置未设置");
    String okFileName = fileName.substring(0,fileName.indexOf(".")+1)+"ok";
    File file = new File(directory + fileName);
    File okFile = new File(directory + okFileName);
    if(file.exists()){
      if(okFile.exists())
        return 1;
      else
        return 0;
    }
    else
      return -1;
  }
  
  
}
