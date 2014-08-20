package icbc.cmis.util;
import java.io.*;
import sun.net.ftp.*;
import sun.net.TelnetInputStream;
import icbc.cmis.base.TranFailException;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FtpDownloadClient {
  static Integer clientsCount = new Integer(0);
  
  FtpClient ftp = null;                    //FTP 实例
  TelnetInputStream tis = null;        
  //TelnetInputStream tisList = null;
  PrintWriter file = null;
  BufferedReader inputs =null;
  BufferedReader inputsList = null;
  DataInputStream inputs2 = null;
  RandomAccessFile file2=null;
  RandomAccessFile okFile = null;
  int ch;
  String FtpServerIp=null;
  int FtpServerPort=21;
  String FtpUserName=null;
  String FtpUserPwd=null;
  
  //初始化参数
  public FtpDownloadClient(String ip,int port,String userName,String userPwd){
    FtpServerIp = ip;
    FtpServerPort = port;
    FtpUserName = userName;
    FtpUserPwd = userPwd;	
  }
  
  public void login(int maxClients) throws Exception{
    try{
      ftp = new FtpClient(FtpServerIp,FtpServerPort);
      synchronized(FtpDownloadClient.clientsCount){
        if(FtpDownloadClient.clientsCount.intValue()<maxClients)
          FtpDownloadClient.clientsCount = new Integer(FtpDownloadClient.clientsCount.intValue() +1);
        else
          throw new TranFailException("ftpDownloadClient","icbc.cmis.util.FtpDownloadClient","已经达到服务器FTP连接数的上限","已经达到服务器FTP连接数的上限");
      }
      try{
        ftp.login(FtpUserName,FtpUserPwd);
      }
      catch(Exception e){
        synchronized(FtpDownloadClient.clientsCount){
          FtpDownloadClient.clientsCount = new Integer(FtpDownloadClient.clientsCount.intValue() - 1);
        }
        throw e;
      }
    }
    catch(Exception e){
      throw e;
    }
  }
  
  public void getFile(String remoteDownloadPath,String localDownloadPath,String fileName,long MaxLengthLimit) throws Exception{
    String buffer = null;
    int pos;
    if(ftp==null)
      throw new TranFailException("ftpDownloadClient","icbc.cmis.util.FtpDownloadClient","未登陆服务器","未登陆服务器");
     
    try{
      ftp.cd(remoteDownloadPath);
      //tisList = ftp.list();
      inputsList = new BufferedReader(new InputStreamReader(new DataInputStream(ftp.list())));
      while((buffer=inputsList.readLine())!=null){
        if(buffer.indexOf(fileName)>=0){
          pos = buffer.indexOf(" ");
          buffer = buffer.substring(pos).trim();
          pos = buffer.indexOf(" ");
          buffer = buffer.substring(pos).trim();
          pos = buffer.indexOf(" ");
          buffer = buffer.substring(pos).trim();
          pos = buffer.indexOf(" ");
          buffer = buffer.substring(pos).trim();
          pos = buffer.indexOf(" ");
          buffer = buffer.substring(0,pos).trim();
          if(Long.parseLong(buffer)>MaxLengthLimit)
            throw new TranFailException("ftpDownloadClient","icbc.cmis.util.FtpDownloadClient.getFile","提取的文件过大，放弃提取","提取的文件过大，放弃提取");
        }
      }
      if(fileName.substring(fileName.indexOf(".")+1).equals("txt")){  
        ftp.ascii();
        try{
          tis = ftp.get(remoteDownloadPath + fileName);          
        }
        catch(FileNotFoundException fe){
          ftp.closeServer();
          throw fe;
        }
      
        inputs = new BufferedReader(new InputStreamReader(new DataInputStream(tis)));
        file = new PrintWriter(new BufferedWriter(new FileWriter(localDownloadPath + fileName)));
     
        while((buffer=inputs.readLine())!=null)
          file.println(buffer);
        inputs.close();
        tis.close();
       
        file.close();
      }
      else{
        ftp.binary();
        
        try{
          tis = ftp.get(remoteDownloadPath + fileName);          
        }
        catch(FileNotFoundException fe){
          ftp.closeServer();
          throw fe;
        }

        file2 = new RandomAccessFile(localDownloadPath + fileName, "rw");
        file2.seek(0L);
        inputs2 =new DataInputStream(tis);
        
        while(inputs2.available()> 0)
          file2.writeByte(inputs2.readByte());
        inputs2.close();
        tis.close();
        
        file2.close();
        okFile = new RandomAccessFile(localDownloadPath + fileName.substring(0,fileName.indexOf(".")+1)+"ok", "rw");
        okFile.close();
      }
    }
    catch(Exception e){
  	  try{
		inputs.close();
	  }
	  catch(Exception e1){}
      try{
        file2.close();
      }
      catch(Exception e1){}
      try{
        inputs2.close();
      }
      catch(Exception e1){}
      try{
        inputsList.close();
      }
      catch(Exception e1){}
  	  try{
		tis.close();
	  }
	  catch(Exception e1){}
	  try{
		file.close();
	  }
	  catch(Exception e1){}
	  try{
        ftp.closeServer();
	  }
	  catch(Exception e1){}
      throw e;
    }
  }
  
  public void close() throws Exception{
    synchronized(FtpDownloadClient.clientsCount){
      FtpDownloadClient.clientsCount = new Integer(FtpDownloadClient.clientsCount.intValue() - 1);
    }
    ftp.closeServer();
  } 	
}
