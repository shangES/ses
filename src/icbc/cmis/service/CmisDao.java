package icbc.cmis.service;

import java.sql.*;
import icbc.cmis.base.*;
import oracle.jdbc.*;
import icbc.cmis.operation.CmisOperation;
import java.util.Vector;
/**
 * Insert the type's description here.
 * Creation date: (2001-12-26 13:48:40)
 * @author: Administrator
 * 20031031
 * getConnection(String userName)新增票据取数据库链接
 * 
 * 20031031
 * closeConnection()修改释放连接操作
 * 
 */
public abstract class CmisDao {
	protected Connection conn = null;
	private CallableStatement cstmt = null; 
	private int retflag = 1; //default is fail 
	private String dbResourceName = null;
	private static String dbUser = "cmis3";
  	private static String verifyStr = "icbc_cmis3";
  	private CmisOperation operation = null;
  	private Vector vConnection = new Vector();
  	private String tmpdbuser = null;
  	private String tmpdbpass = null;
  	private String lang_code = null;     //柜员语言update by 林洋 2007-06-21 
  	private String zone_code = null;     //时区码update by 林洋 2007-06-21
	
/**
 * CmisDao constructor comment.  
 */
public CmisDao(CmisOperation op) {
	super();
	dbUser = (String)CmisConstance.getParameterSettings().get("cmis3UserName");
//  	verifyStr = (String)CmisConstance.getParameterSettings().get("cmis3Verify");
	String jdbcName = (String)CmisConstance.getParameterSettings().get("jdbcResourceName");
	dbResourceName = jdbcName;
	if(operation == null){
		operation = op;
		try{                        //update by linyang 2007-06-21
				lang_code = (String)operation.getSessionData("LangCode");
			}catch (Exception e){
			lang_code = "zh_CN";
			}
		try{  
				zone_code = (String)operation.getSessionData("ZoneCode");
			}catch (Exception e){
			zone_code = "0";
			}
		operation.addCmisDao(this);
	}
	
}
/**
 * CmisDao constructor comment.
 */
public CmisDao(String dbResourceName1,CmisOperation op) {
	super();
	dbUser = (String)CmisConstance.getParameterSettings().get("cmis3UserName");
  	verifyStr = (String)CmisConstance.getParameterSettings().get("cmis3Verify");
	dbResourceName = dbResourceName1;
	if(operation == null){
		operation = op;
		try{                    //update by linyang 2007-06-21
				lang_code = (String)operation.getSessionData("LangCode");
			}catch (TranFailException e){}
		try{
				zone_code = (String)operation.getSessionData("ZoneCode");
			}catch (TranFailException e){}
		operation.addCmisDao(this);
	}
	
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-26 13:55:14)
 */
private void initDBPoolName(){
	if(operation != null){
		String tmpCode = null;
		try{
			tmpCode = (String)operation.getSessionData("FUNCTIONCODE");
		}catch(Exception eee){
			tmpCode = null;
		}
		if(tmpCode != null && tmpCode.trim().length() != 0){
			String tmp = CmisConstance.getDBPoolName(tmpCode);
			if(tmp != null && tmp.trim().length() != 0)
				dbResourceName = tmp.trim();
		}
		return ;
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-26 13:55:14)
 */
protected void closeConnection(){
	if(conn != null){
		try{
			vConnection.remove(conn);
		}catch(Exception e){}
		String tmpPool = null;
//	    String userId = (String)CmisConstance.getParameterSettings().get("dbUserName");
//	    String poolSwitch = (String)CmisConstance.getParameterSettings().get("poolSwitch");
		tmpPool = CmisConstance.getDBPool(tmpdbuser.toLowerCase());

//		if(poolSwitch != null && poolSwitch.trim().equalsIgnoreCase("true")&&tmpdbuser.equalsIgnoreCase("bms")){
//	  	   tmpPool = (String)CmisConstance.getParameterSettings().get("bmsPoolName");
//		}
//	    else if(poolSwitch != null && poolSwitch.trim().equalsIgnoreCase("true")&&userId != null&&userId.trim().equalsIgnoreCase(tmpdbuser)){
//		   tmpPool = dbResourceName+"M";
//	    }
		icbc.cmis.base.CmisConstance.JDBCPool.releaseConnection(tmpPool,conn);
		operation.connectCount--;
		conn = null;
		String tmp = this.getClass().toString();
		icbc.cmis.base.Trace.trace(tmp,0,0,"",tmp+".closeConnection() success!");
	}
}
//protected void closeConnection(){
//	if(conn != null){
//		try{
//			vConnection.remove(conn);
//		}catch(Exception e){}
//		String tmpPool = dbResourceName;
//	    String userId = (String)CmisConstance.getParameterSettings().get("dbUserName");
//	    String poolSwitch = (String)CmisConstance.getParameterSettings().get("poolSwitch");
//		if(poolSwitch != null && poolSwitch.trim().equalsIgnoreCase("true")&&tmpdbuser.equalsIgnoreCase("bms")){
//	  	   tmpPool = (String)CmisConstance.getParameterSettings().get("bmsPoolName");
//		}
//	    else if(poolSwitch != null && poolSwitch.trim().equalsIgnoreCase("true")&&userId != null&&userId.trim().equalsIgnoreCase(tmpdbuser)){
//		   tmpPool = dbResourceName+"M";
//	    }
//		icbc.cmis.base.CmisConstance.JDBCPool.releaseConnection(tmpPool,conn);
//		operation.connectCount--;
//		conn = null;
//		String tmp = this.getClass().toString();
//		icbc.cmis.base.Trace.trace(tmp,0,0,"",tmp+".closeConnection() success!");
//	}
//}
/**
 * Insert the method's description here.
 * Creation date: (2002-5-23 13:23:54)
 */
public void cmisOperationRun()
{
    for (int i = 0; i < vConnection.size(); i++)
        {
        try
            {
            Connection c = (Connection) vConnection.elementAt(i);
            if(c == null){
            	 String name = (operation == null ? "" : operation.getClass().toString());
                    String tmpStr =
                        "=**=-Waring-=**=:\nDataBase connection is null!\n in ["
                            + name
                            + "--(call dao)-->"
                            + this.getClass().toString()
                            + "]";
                     System.out.println(tmpStr);
                    icbc.cmis.base.Trace.trace("=**=-Waring-=**=", 0, 0, "", tmpStr);
                    continue;
            }
            if(c instanceof SQLConnection){
           		c = ((SQLConnection)c).getConnection();
            }		
            if (c != null && !c.isClosed())
                {
                try
                    {
                    try
                        {
                        if (c instanceof oracle.jdbc.OracleConnection)
                            {
                            //((oracle.jdbc.OracleConnection) c).close_statements();
                        }
                    }
                    catch (Exception e)
                        {
                    }

                    try
                        {
                        c.close();
                    }
                    catch (Exception eee)
                        {
                    }
                    String name = (operation == null ? "" : operation.getClass().toString());
                    String tmpStr =
                        "=**=-Waring-=**=:\nDataBase connection is not closed!\n in ["
                            + name
                            + "--(call dao)-->"
                            + this.getClass().toString()
                            + "]";
                    System.out.println(tmpStr);
                    icbc.cmis.base.Trace.trace("=**=-Waring-=**=", 0, 0, "", tmpStr);
                }
                catch (Exception ee)
                    {
                }
            }
        }
        catch (Exception e)
            {
        }
    }
}
/**
 * Insert the method's description here.
 * Creation date: (2002-5-23 13:09:18)
 */
public void daoCommit()throws Exception {
	conn.commit();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-5-23 13:09:18)
 */
public void daoRollback()throws Exception {
	conn.rollback();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-26 13:54:45)
 * @return java.sql.Connection
 */
protected void getConnByVerify(String userName,String veryStr) throws Exception{
	
	String userPass = getDBUserPass(userName,veryStr);
	
	getConnection(userName,userPass);
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-26 13:54:45)
 * @return java.sql.Connection
 */
protected void getConnection() throws Exception{
	getConnByVerify(dbUser,CmisConstance.getPassVerify(dbUser));
	
}

/**
 * Insert the method's description here.
 * Creation date: (2001-12-26 13:54:45)
 * @return java.sql.Connection
 */
protected void getConnection(String userName,String userPass) throws Exception{
	closeConnection();
	if(operation == null){
		throw new Exception("在CmisDao中业务逻辑模块未指定，请求数据库连接失败！");
	}
	tmpdbuser = userName.trim();

	String tmpPool = null;
//	String userId = (String)CmisConstance.getParameterSettings().get("dbUserName");
//	String poolSwitch = (String)CmisConstance.getParameterSettings().get("poolSwitch");

//	if(poolSwitch != null && poolSwitch.trim().equalsIgnoreCase("true")&&userId != null&&userId.trim().equalsIgnoreCase(tmpdbuser)){
//		tmpPool = dbResourceName+"M";
//	}
//	if(poolSwitch != null && poolSwitch.trim().equalsIgnoreCase("true")){
		tmpPool = CmisConstance.getDBPool(userName.toLowerCase());
//	}
//	else{
//		initDBPoolName();
//		tmpPool = dbResourceName;
//	}
	if(tmpPool==null){
		throw new Exception("在CmisDao中取"+userName+"的连接池名称失败！");
	}	
	conn = icbc.cmis.base.CmisConstance.JDBCPool.getConnection(tmpPool,userName,userPass);
	operation.connectCount++;
	if(conn != null){
		vConnection.add(conn);
	}
	String tmp = this.getClass().toString();
	icbc.cmis.base.Trace.trace(tmp,0,0,"",tmp+".getConnection(String userName,String userPass) success!");
	// added by 林洋  2007-06-21
	this.setZoneLang();
	
}

protected void getConnection(String userName) throws Exception{
	closeConnection();
	if(operation == null){
		throw new Exception("在CmisDao中业务逻辑模块未指定，请求数据库连接失败！");
	}
	tmpdbuser = userName.trim();
//	initDBPoolName();
	String userPass= null;
	String tmpPool = null;


//		tmpPool = (String)CmisConstance.getParameterSettings().get("bmsPoolName");
//		userPass = (String)CmisConstance.getParameterSettings().get("bmsPoolPass");
//	String poolSwitch = (String)CmisConstance.getParameterSettings().get("poolSwitch");

//	if(poolSwitch != null && poolSwitch.trim().equalsIgnoreCase("true")){
		tmpPool = CmisConstance.getDBPool(userName.toLowerCase());
//	}
//	else{
//		initDBPoolName();
//		tmpPool = dbResourceName;
//	}
	String veryStr = CmisConstance.getPassVerify(userName.toLowerCase());
	userPass = getDBUserPass(userName,veryStr);
	conn = icbc.cmis.base.CmisConstance.JDBCPool.getConnection(tmpPool,userName,userPass);
	operation.connectCount++;
	if(conn != null){
		vConnection.add(conn);
	}
	String tmp = this.getClass().toString();
	icbc.cmis.base.Trace.trace(tmp,0,0,"",tmp+".getConnection(String userName) success!");
	
//	added by 林洋  2007-06-21
	 //this.setZoneLang();
	
}




/**
 * Insert the method's description here.
 * Creation date: (2001-12-31 15:35:54)
 * @param userName java.lang.String
 * @param userPass java.lang.String
 */

//private String getDBUserPass(String userName,String verifyStr) throws Exception {
//	if(operation == null){
//		throw new Exception("在CmisDao中业务逻辑模块未指定，请求数据库连接失败！");
//	}
//	String pass = (String)CmisConstance.getDBUserPass(userName,verifyStr);
//	String tmp = this.getClass().toString();
//	if(pass == null){
//		icbc.cmis.base.Trace.trace(tmp,0,0,"",tmp+".getDBUserPass(String userName,String verifyStr) begin!");
//		java.sql.CallableStatement call = null;
//		java.sql.Connection con = null;
//		String tmpPool = dbResourceName;
//		try {
//	        String dbSource = (String)CmisConstance.getParameterSettings().get("jdbcResourceName");
//	        String userId = (String)CmisConstance.getParameterSettings().get("dbUserName");
//	        String userPass = (String)CmisConstance.getParameterSettings().get("dbUserPass");
//	        String userPassProc = (String)CmisConstance.getParameterSettings().get("getUserPassProc");
//			String poolSwitch = (String)CmisConstance.getParameterSettings().get("poolSwitch");
//			if(poolSwitch != null && poolSwitch.trim().equalsIgnoreCase("true")){
//		 		tmpPool = dbResourceName+"M";
//			}
//			con = CmisConstance.JDBCPool.getConnection(tmpPool,userId, userPass);
//		  	call = con.prepareCall ("{ ? = call "+userName.toUpperCase()+"."+userPassProc.trim()+"('"+userName+"','"+verifyStr+"')}");
//		  	icbc.cmis.base.Trace.trace(tmp,0,0,"",tmp+".getDBUserPass(String,String),Call String:\n"+"{ ? = call "+userName.toUpperCase()+"."+userPassProc.trim()+"('"+userName+"','"+verifyStr+"')}");
//			call.registerOutParameter (1, oracle.jdbc.driver.OracleTypes.VARCHAR);
//			call.execute ();
//			pass = call.getString (1);
//			if(pass == null){
//				icbc.cmis.base.Trace.trace(tmp,0,0,"",tmp+".getDBUserPass(String,String),Warning:the pass got from DB is null!");
//			}
//			icbc.cmis.base.Trace.trace(tmp,0,0,"",tmp+".getDBUserPass(String userName,String verifyStr) end!");
//		} catch(Exception ex) {
//	        icbc.cmis.base.Trace.trace(tmp,0,0,"",tmp+".getDBUserPass(String userName,String verifyStr) exception end!Exception:\n"+ex.toString());
//			throw ex;
//		}finally{
//			try{
//				if(call != null){call.close();}
//			}catch(Exception ee){}
//			try{
//				if(con != null){CmisConstance.JDBCPool.releaseConnection(tmpPool,con);con = null;}
//			}catch(Exception ee){}
//		}
//		
//		if(pass != null){
//			CmisConstance.addDBUserInfo(userName.trim()+"."+verifyStr.trim(),pass);
//		}else{
//		 	throw new TranFailException("xdtz22123","CmisDao. getDBUserPass(String userName,String verifyStr)","取数据库用户密码失败");
//		}
//	}
//	return pass;
//	
//}


private String getDBUserPass(String userName,String verifyStr) throws Exception {
	if(operation == null){
		throw new Exception("在CmisDao中业务逻辑模块未指定，请求数据库连接失败！");
	}
	String pass = (String)CmisConstance.getDBUserPass(userName,verifyStr);
	
	if (pass == null) {
		if (userName.equals("cmis3")) {
			pass = "cmis3";
		} else {
			pass = "missign";
		} 
	}
	String tmp = this.getClass().toString();
	if(pass == null){
		icbc.cmis.base.Trace.trace(tmp,0,0,"",tmp+".getDBUserPass(String userName,String verifyStr) begin!");
		java.sql.CallableStatement call = null;
		java.sql.Connection con = null;
		String tmpPool = dbResourceName;
		try {
	        String dbSource = (String)CmisConstance.getParameterSettings().get("jdbcResourceName");
	        String userId = (String)CmisConstance.getParameterSettings().get("dbUserInName");
	        String userPass = (String)CmisConstance.getParameterSettings().get("dbUserInPass");
	        String userPassProc = (String)CmisConstance.getParameterSettings().get("getUserPassProc");
	        //can removed
//			String poolSwitch = (String)CmisConstance.getParameterSettings().get("poolSwitch");
//			if(poolSwitch != null && poolSwitch.trim().equalsIgnoreCase("true")){
////		 		tmpPool = dbResourceName+"M";
//			}
	        //can removed end
			
			con = CmisConstance.JDBCPool.getConnection(tmpPool,userId, userPass);
//		  	call = con.prepareCall ("{ ? = call "+userName.toUpperCase()+"."+userPassProc.trim()+"('"+userName+"','"+verifyStr+"')}");
		  	call = con.prepareCall ("{ ? = call "+userPassProc.trim()+"('"+userName+"','"+verifyStr+"')}");
		  	icbc.cmis.base.Trace.trace(tmp,0,0,"",tmp+".getDBUserPass(String,String),Call String:\n"+"{ ? = call "+userName.toUpperCase()+"."+userPassProc.trim()+"('"+userName+"','"+verifyStr+"')}");
			call.registerOutParameter (1, oracle.jdbc.OracleTypes.VARCHAR);
			call.execute ();
			pass = call.getString (1);
			if(pass == null){
				icbc.cmis.base.Trace.trace(tmp,0,0,"",tmp+".getDBUserPass(String,String),Warning:the pass got from DB is null!");
			}
			icbc.cmis.base.Trace.trace(tmp,0,0,"",tmp+".getDBUserPass(String userName,String verifyStr) end!");
		} catch(Exception ex) {
	        icbc.cmis.base.Trace.trace(tmp,0,0,"",tmp+".getDBUserPass(String userName,String verifyStr) exception end!Exception:\n"+ex.toString());
			throw ex;
		}finally{
			try{
				if(call != null){call.close();}
			}catch(Exception ee){}
			try{
				if(con != null){CmisConstance.JDBCPool.releaseConnection(tmpPool,con);con = null;}
			}catch(Exception ee){}
		}
		
		if(pass != null){
			CmisConstance.addDBUserInfo(userName.trim()+"."+verifyStr.trim(),pass);
		}else{
		 	throw new TranFailException("xdtz22123","CmisDao. getDBUserPass(String userName,String verifyStr)","取数据库用户密码失败");
		}
	}
	return pass;
	
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 10:42:52)
 * @return icbc.cmis.operation.CmisOperation
 */
protected CmisOperation getOperation() {
	if(operation == null){
		return new icbc.cmis.operation.DummyOperation();
	}
	return operation;
}
public String substringb(String ts,int len) {
  byte[] tb = ts.getBytes();
  if(tb.length <= len) return ts;

  float k = 0;
  for(int i=0;i<len;i++) {
    if(tb[i] < 0) {
      k += 0.5;
    }else{
      k ++;
    }
  }
  return ts.substring(0,(int)k);
}
private void setZoneLang(){
	try{
	
	cstmt = conn.prepareCall("{?=call setclient(?,?)}");
		 cstmt.registerOutParameter(1, OracleTypes.VARCHAR); //ret_flag  always be '0'
		 cstmt.setString(2, this.lang_code);
		 cstmt.setString(3, this.zone_code);
	
		 cstmt.executeUpdate();
	
		 retflag = cstmt.getInt(1);
		 //if(retflag != 0){
		//	   throw new Exception("在CmisDao中设置柜员语言和时区失败!");
		 
	
		 //if (cstmt != null)
			// cstmt.close();
         }catch (Exception e){e.printStackTrace();}
         finally{
	            if (cstmt != null)
				try{
				cstmt.close();
				}catch (Exception ee){}
				}
         
}
}
