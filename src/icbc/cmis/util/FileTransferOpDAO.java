package icbc.cmis.util;

import icbc.cmis.base.*;
import icbc.cmis.service.CmisDao;
import java.sql.*;
import java.util.*;
import java.io.*;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FileTransferOpDAO extends CmisDao{

  static Integer insertLock = new Integer(0);
  
  public FileTransferOpDAO(icbc.cmis.operation.CmisOperation op) {
    super(op);
  }
  
  public String genJobId(String areaCode, Connection connection) throws TranFailException {
    String sql = "select * from (select job001 from mag_job_list where job001 like '"+areaCode+CmisConstance.getWorkDate("yyyy")+"%' order by job001 desc) where rownum = 1";
    Statement st = null;
    ResultSet rs = null;
    String maxJobId = null;
    String jobId = areaCode+CmisConstance.getWorkDate("yyyy")+"00000";
    try{
      st = connection.createStatement();
      rs = st.executeQuery(sql);
      if(rs.next()){
        maxJobId = rs.getString(1);
        String seq = "" + (Integer.parseInt(maxJobId.substring(12))+1);
        if(seq.length()>5)
         throw new TranFailException("cmisGenJobId","icbc.cmis.util.FileTransferOpDAO","jobId数值溢出","jobId数值溢出");
        jobId = jobId.substring(0,17-seq.length()) + seq;
      }
    }
    catch(Exception e){
  	  throw new TranFailException("cmisFileTransfer","icbc.cmis.util.FileTransferOpDAO",e.getMessage(),e.getMessage());
  	}
  	finally {
      if(rs != null) try {rs.close();} catch (Exception ex) {}
      if(st!= null) try {st.close();} catch (Exception ex) {}
    }
    return jobId;
  }
  
  private String formatDateAndTime(String time){
    String ret = time;
    if(time!= null)
  	      if(time.length()==14)
  	        ret=time.substring(0,4)+"/"+time.substring(4,6)+"/"+time.substring(6,8)+" "+time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12);
  	return ret;    	
  }
  
  public Vector performGetRecent(String empCode,String areaCode,String jobType,String date) throws TranFailException {
    Vector ret = new Vector();
    String sql ="select job001,job004,job005,job006,job007,job012,job011 from mag_job_list where job013=? and job002=? and to_date(SUBSTR(job004,1,8),'YYYYMMDD') + 2 >= to_date(?,'YYYYMMDD')"; 
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try{
  	  this.getConnection();
  	  pstmt = conn.prepareStatement(sql);
  	  pstmt.setString(1,empCode);
  	  pstmt.setString(2,areaCode);
  	  pstmt.setString(3,date);
  	  rs = pstmt.executeQuery();
  	  
  	  while(rs.next()){
  	    String[] str = new String[7];
  	    for(int i= 0;i<7;i++)
  	      str[i] = rs.getString(i+1);
  	    str[1] = formatDateAndTime(str[1]);
  	    str[2] = formatDateAndTime(str[2]);
  	    str[3] = formatDateAndTime(str[3]);
        
  	    ret.add(str);  
  	  }
  	 }
  	catch(Exception e){
  	  try{ conn.rollback();}  catch (Exception ex) {}	
  	  throw new TranFailException("cmisFileTransfer","icbc.cmis.util.FileTransferOpDAO",e.getMessage(),e.getMessage());
  	}
  	finally {
      if(rs != null) try {rs.close();} catch (Exception ex) {}
      if(pstmt != null) try {pstmt.close();} catch (Exception ex) {}
      this.closeConnection();
    }
    
    return ret;
  }
  
  public Vector performQuery(String empCode,String areaCode,String jobType,String paras) throws TranFailException {
    PreparedStatement pstmt = null;
  	ResultSet rs = null;
    String sql = "select job004,job005,job006,job007,job012,job011 from mag_job_list where job008=? and job003=?";
    String sql2 = "select job001 from mag_job_list where job013 =? and job012 =? and job002 =?";
    String sql_ins = "insert into mag_job_list(job001,job002,job003,job004,job007,job008,job012) values(?,?,?,?,?,?,?)";
    String sql_ins2 = "insert into mag_job_list(job001,job002,job003,job004,job005,job006,job007,job008,job011,job012,job013) values(?,?,?,?,?,?,?,?,?,?,?)";
    String isReqExisted = null;
    String jobId = null;
    String fileName = null;
    String procState = null;
    String putTime =null;
    String beginTime = null;
    String endTime = null;
    String errMsg = null;
    Vector ret = new Vector();
    int n = 0;
    

    try{
  	  this.getConnection();
  	  pstmt = conn.prepareStatement(sql);
  	  pstmt.setString(1,jobType);
  	  pstmt.setString(2,paras);
  	  rs = pstmt.executeQuery();
  	  if(rs.next()){
  	    putTime = formatDateAndTime(rs.getString(1));
  	    beginTime = formatDateAndTime(rs.getString(2));
  	    endTime = formatDateAndTime(rs.getString(3));
  	    fileName = rs.getString(5);
  	    procState = rs.getString(4);
  	    errMsg = rs.getString(6);
  	    isReqExisted = "true";
  	    
  	    rs.close();
  	    pstmt.close();
  	    pstmt = conn.prepareStatement(sql2);
  	    pstmt.setString(1,fileName);
  	    pstmt.setString(2,empCode);
  	    pstmt.setString(3,areaCode);
  	    rs = pstmt.executeQuery();

  	    if(!rs.next()){
  	      n = 0;
  	      pstmt.close();
  	      pstmt = conn.prepareStatement(sql_ins2);
   
  	      pstmt.setString(2,areaCode);
  	      pstmt.setString(3,paras);
  	      pstmt.setString(4,putTime);
  	      pstmt.setString(5,beginTime);
          pstmt.setString(6,endTime);
          pstmt.setString(7,procState);
          pstmt.setString(8,jobType);
          pstmt.setString(9,errMsg);
          pstmt.setString(10,empCode);
          pstmt.setString(11,fileName);
          
  	      synchronized(insertLock){
            jobId = genJobId(areaCode,conn);
            pstmt.setString(1,jobId);
            n = pstmt.executeUpdate();
          
            if(n==0)
  	          throw new TranFailException("cmisFileTransfer","icbc.cmis.util.FileTransferOpDAO","无法在mag_job_list中插入新值","无法在mag_job_list中插入新值");
  	        else
  	          conn.commit();
          }
  	    }
  	    
  	    ret.add(isReqExisted);
  	    ret.add(jobId);
  	    ret.add(putTime);
  	    ret.add(beginTime);
  	    ret.add(endTime);
  	    ret.add(procState);
  	    ret.add(fileName);
  	    ret.add(errMsg);
  	  }
  	  else{
  	  	
  	  	isReqExisted = "false";
  	  	String curTime = null;
  	    pstmt.close();
     	      
        pstmt = conn.prepareStatement(sql_ins);
        curTime = CmisConstance.getWorkDate("yyyyMMddHHmmss");
        pstmt.setString(2,areaCode);
        pstmt.setString(3,paras);
        pstmt.setString(4,putTime);
        pstmt.setString(5,"O");
  	    pstmt.setString(6,jobType);
  	    pstmt.setString(7,empCode);
         
        synchronized(insertLock){
  	      jobId = genJobId(areaCode,conn);
          pstmt.setString(1,jobId);  	        	      
  	      n = pstmt.executeUpdate();
  	      if(n==0)
  	        throw new TranFailException("cmisFileTransfer","icbc.cmis.util.FileTransferOpDAO","无法在mag_job_list中插入新值","无法在mag_job_list中插入新值");
  	      else
            conn.commit();
        }
          
  	    ret.add(isReqExisted);
  	    ret.add(jobId);
  	    ret.add(formatDateAndTime(curTime));
  	  }
  	}
  	catch(Exception e){
  	  try{ conn.rollback();}  catch (Exception ex) {}	
  	  throw new TranFailException("cmisFileTransfer","icbc.cmis.util.FileTransferOpDAO",e.getMessage(),e.getMessage());
  	}
  	finally {
      if(rs != null) try {rs.close();} catch (Exception ex) {}
      if(pstmt != null) try {pstmt.close();} catch (Exception ex) {}
      this.closeConnection();
    }
    
    return ret;
  }
  

}
