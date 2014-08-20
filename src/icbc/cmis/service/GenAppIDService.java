package icbc.cmis.service;

import icbc.cmis.base.*;
import java.sql.*;
import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (2002-1-7 9:44:19)
 * @author: Administrator
 */
public class GenAppIDService extends CmisDao {
//	private Vector v_tables = new Vector();
//	private Vector tmpTable = new Vector();
//	private Vector vTable2 = new Vector();
//	private Vector vTable3 = new Vector();	
/**
 * GeneralSQLService constructor comment.
 */
public GenAppIDService() {
	super(new icbc.cmis.operation.DummyOperation());
}
/**
 * GeneralSQLService constructor comment.
 * @param dbResourceName1 java.lang.String
 */
public GenAppIDService(String dbResourceName1) {
	super(dbResourceName1,new icbc.cmis.operation.DummyOperation());
}
/**
 * Insert the method's description here.
 * Creation date: (2002-4-15 14:26:49)
 * @param systemUserName java.lang.String
 */
public String getAppId()throws Exception {
	Statement stmt = null;
	try{
		String hostaddress = java.net.InetAddress.getLocalHost().getHostAddress();
		String appCode = CmisConstance.appCode;	
//		String userId = (String)CmisConstance.getParameterSettings().get("dbUserName");
//	    String userPass = (String)CmisConstance.getParameterSettings().get("dbUserPass");
//		getConnection(userId,userPass);
		getConnection("missign");
		stmt=conn.createStatement();
		String sql="select clone_id from mag_app_id where isuse = '0' and app_address = '"+ hostaddress +"' and app_id='"+appCode+"' and rownum = 1";
		System.out.println(hostaddress);
		ResultSet rs=stmt.executeQuery(sql);
		String clone_id ="0";		
		if(rs.next()){
			clone_id = rs.getString(1);
			System.out.println(clone_id);			
			stmt.executeQuery("update mag_app_id set isuse = '1' where app_address = '"+ hostaddress +"' and clone_id = '"+clone_id+"' and app_id='"+appCode+"'");
			conn.commit();
			ResultSet rs2 = stmt.executeQuery("select count(*) from mag_app_id where isuse = '0' and app_address = '"+ hostaddress +"' and app_id='"+appCode+"'");
			if (rs2.next()){
				if(rs2.getInt(1)==0){
					stmt.executeQuery("update mag_app_id set isuse = '0' where app_address = '"+ hostaddress +"' and app_id='"+appCode+"'");
					conn.commit();

				}
				rs2.close();
			}
		}
		
		rs.close();
		stmt.close();
		closeConnection();
		return clone_id;
  	}catch (Exception e){
	  	try{
	 	 	stmt.close();
	  	}catch(Exception ee){}
	  	 throw e;
  	}finally {
	  	try{
	 	 	closeConnection();
	  	}catch(Exception ee){}
	}
}

}
