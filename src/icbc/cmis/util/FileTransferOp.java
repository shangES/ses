package icbc.cmis.util;

import icbc.cmis.operation.*;
import icbc.cmis.base.*;
import java.util.*;
import java.io.*;
//import sun.net.ftp.*;
//import sun.net.TelnetInputStream;
//import sun.net.TelnetOutputStream;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FileTransferOp extends CmisOperation {

	public FileTransferOp() {
	}
	public void execute() throws java.lang.Exception {
		/**@todo: implement this icbc.cmis.operation.CmisOperation abstract method*/
		try{

			String action = getStringAt("act");
			if(action.equals("req")){
				performReq();
			}
			else if(action.equals("recent")){
				performQueryRecent();
			}
			else if(action.equals("download")){
				download();
			}
			else {
			  throw new Exception();
			}
		}
		catch(Exception e){
			throw e;
		}


	}

  private void performQueryRecent() throws Exception {
  	String jobType = null;
  	String empCode = null;
  	String areaCode = null;
  	String jobId =null;
  	String date = null;
  	Vector vec = null;
  	String fileName = null;
  	boolean isFileExisted = false;

  	try{
  	  jobType = getStringAt("job_type");
  	  empCode  = (String)getSessionData("EmployeeCode");
      areaCode = (String)getSessionData("AreaCode");
      
      FileTransferOpDAO dao = new FileTransferOpDAO(this);
      date = CmisConstance.getWorkDate("yyyyMMdd");
      vec = dao.performGetRecent(empCode,areaCode,jobType,date);

      try{
        addDataField("fileList",vec);
      }catch(Exception e){
        removeDataField("fileList");
        addDataField("fileList",vec);
      }
      this.setOperationDataToSession();
      setReplyPage("/icbc/cmis/util/util_FileTransferList.jsp");
  	}
  	catch(Exception e){
  	  throw e;
  	}
  }

  private void performReq() throws Exception {	
  	String jobType = null;
  	String paraValue = null;
  	String empCode = null;
  	String areaCode = null;
  	String jobId =null;
  	Vector vec = null;
  	Vector fileList = null;
  	
  	String fileName = null;
  	boolean isFileExisted = false;
  	try{
  	  jobType = getStringAt("job_type");
  	  paraValue = getStringAt("para_values");
  	  empCode  = (String)getSessionData("EmployeeCode");
      areaCode = (String)getSessionData("AreaCode");
      
      FileTransferOpDAO dao = new FileTransferOpDAO(this);
      vec = dao.performQuery(empCode,areaCode,jobType,paraValue);
      String isExisted = (String)vec.get(0);
      if(isExisted.equals("true")){
        

        String[] str = new String[7];
        for(int i=1;i<vec.size();i++)
          str[i-1] = (String)vec.get(i);
        fileList = new Vector();
        fileList.add(str);
        try{
          addDataField("fileList",fileList);
        }catch(Exception e){
          removeDataField("fileList");
          addDataField("fileList",fileList);
        }
        this.setOperationDataToSession();
        setReplyPage("/icbc/cmis/util/util_FileTransferList.jsp");
      }
      else{
      	String[] str = new String[7];
        str[0] = (String)vec.get(1);
        str[1] = (String)vec.get(2);
        str[2] = null;
        str[3] = null;
        str[4] = "O";
        str[5] = null;
        str[6] = null;
        
        fileList = new Vector();
        fileList.add(str);
        try{
          addDataField("fileList",fileList);
        }catch(Exception e){
          removeDataField("fileList");
          addDataField("fileList",fileList);
        }
        this.setOperationDataToSession();
        setReplyPage("/icbc/cmis/util/util_FileTransferList.jsp");
      }
  	}
  	catch(Exception e){
  	  throw e;
  	}
  }


  private void download() throws Exception {
    
    String where="where  file_no='";
    String fileName="";
    String tableName="mag_job_file";
    String blobFieldName="file_content";
    try{

      fileName = getStringAt("fileName");
      where = where + fileName + "'";
      setReplyPage(
         (String)CmisConstance.getParameterSettings().get("webBasePath") 
          + "/servlet/icbc.cmis.servlets.CmisZippedDownloadReqServlet?" 
          + "Where=" + where + "&"
          + "BlobFieldName=" + blobFieldName + "&"
          + "TableName=" + tableName + "&"
          + "FileName=" + fileName );
    }
    catch(Exception e){
      throw e;
    }
  }
  
  
}
