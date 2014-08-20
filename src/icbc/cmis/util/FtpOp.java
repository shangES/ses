package icbc.cmis.util;

import icbc.cmis.operation.*;
import icbc.cmis.base.*;
import java.util.*;
import java.io.*;
import sun.net.ftp.*;
import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FtpOp extends CmisOperation {
	FtpClient ftp;
    TelnetInputStream tis;
    int ch;
    String FtpServerIp;
    int FtpServerPort;
    String FtpUserName;
    String FtpUserPwd;
    String localDownloadPath;
	public FtpOp() {
	}
	public void execute() throws java.lang.Exception {
		/**@todo: implement this icbc.cmis.operation.CmisOperation abstract method*/
		try{

			String action = getStringAt("act");
			if(action.equals("localGet")){
				getLocalFile();
			}
			else {
			  throw new Exception();
			}
			/* else if(action.equals("reupdateFunc")){
				reupdateFunc();
			}*/
		}
		catch(Exception e){
			throw e;
		}


	}
	

  private void getLocalFile() throws Exception {
  	RandomAccessFile getFile=null;
  	try{
  	  boolean isFileExisted = false;
  	  String fileName = getStringAt("fileName");
  	  localDownloadPath =
        (String) CmisConstance.getParameterSettings().get("LocalDownloadPath");
      File file = new File(localDownloadPath);
      String[] localFileNames = file.list();
      for(int i =0;i<localFileNames.length;i++)
        if(localFileNames[i].equals(fileName)){
        	isFileExisted = true;
        	break;
        }
      if(isFileExisted){
        try{
          this.removeDataField("localTargerFileName");
        }
        catch(Exception e){}
        this.addDataField("localTargerFileName",fileName);
        this.setOperationDataToSession();
        this.setReplyPage("/util/util_FtpLocalDownloadConfirm.jsp");
      }
      else{
       //get the file on the remote server;
        FtpServerIp =
          (String) CmisConstance.getParameterSettings().get("FTPServerIP");
        FtpServerPort =
          Integer.parseInt((String) CmisConstance.getParameterSettings().get("FTPServerPort"));
        FtpUserName = (String) CmisConstance.getParameterSettings().get("FTPUserName");
        FtpUserPwd =
          (String) CmisConstance.getParameterSettings().get("FTPUserPWD");
        ftp = new FtpClient(FtpServerIp,FtpServerPort);
        ftp.login(FtpUserName,FtpUserPwd);
        ftp.binary();
        try{
          tis = ftp.get(fileName);          
        }
        catch(FileNotFoundException ioe){
          this.setReplyPage("/util/util_FtpRemoteGet.jsp");
          ftp.closeServer();
          return;
        }
        getFile = new RandomAccessFile(localDownloadPath + fileName, "rw");
        getFile.seek(0L);
        tis = ftp.get(fileName);
        DataInputStream inputs = new DataInputStream(tis);
    
        while(inputs.available()> 0)
          getFile.writeByte(inputs.readByte());
        tis.close();
        getFile.close();
        ftp.closeServer();
        /*try{
          this.removeDataField("localTargerFilePath");
        }
        catch(Exception e){}
        this.addDataField("localTargerFilePath",localDownloadPath);*/
        try{
          this.removeDataField("localTargerFileName");
        }
        catch(Exception e){}
        this.addDataField("localTargerFileName",fileName);
        this.setOperationDataToSession();
        this.setReplyPage("/util/util_FtpLocalDownloadConfirm.jsp");
      }
  	}
  	catch(Exception e){
  	  try{
		tis.close();
	  }
	  catch(Exception e1){}
	  try{
		getFile.close();
	  }
	  catch(Exception e1){}
	  try{
        ftp.closeServer();
	  }
	  catch(Exception e1){}
      throw e;
    }
  }
  
}
