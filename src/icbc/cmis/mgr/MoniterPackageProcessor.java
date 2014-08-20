package icbc.cmis.mgr;

import java.io.*;
import java.util.*;
import icbc.cmis.base.*;


/**
 * 
 *   @(#) MoniterPackageProcessor.java	1.0 03/02/2000
 *   Copyright (c) 1999 ECC R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 03/02/2000
 *   @author  ZhongMingChang
 *   
 */

 
 
public class MoniterPackageProcessor implements Runnable {
	String message = null;
	MonitorClient monitor = null;
/**
 * MoniterPackageProcessor constructor comment.
 */
public MoniterPackageProcessor() {
	super();
}
/**
 * MoniterPackageProcessor constructor comment.
 */
public MoniterPackageProcessor(MonitorClient monitor, String msg) {
	super();
	this.message = msg;
	this.monitor = monitor;
}
/**
 * MoniterPackageProcessor constructor comment.
 */
public MoniterPackageProcessor(String msg) {
	super();
	this.message = msg;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-28 16:15:03)
 * @return java.lang.String
 */
private String getReloadSettings() throws Exception {

    icbc.cmis.operation.SqlTool sqlTool = null;
    try {
//        String userId = (String) CmisConstance.getParameterSettings().get("dbUserName");
//        String userPass =
//            (String) CmisConstance.getParameterSettings().get("dbUserPass");
        sqlTool = new icbc.cmis.operation.SqlTool(new icbc.cmis.operation.DummyOperation(-1));
//        sqlTool.getConn(userId, userPass);
        sqlTool.getConn("missign");
        java.util.Vector v = new java.util.Vector(3);
        v.add(0, "tab_code");
        v.add(1, "tab_type");
        v.add(2, "tab_owner");
        IndexedDataCollection result = new IndexedDataCollection();
        java.util.Enumeration e = CmisConstance.getDBUsers();
        for (; e.hasMoreElements();) {
            String sysType = (String) e.nextElement();
            sqlTool.executeQueryFix(
                v,
                "mag_cache_tables",
                "where TAB_OWNER='" + sysType.trim().toUpperCase() + "'",
                result);
        }
        sqlTool.closeconn();
        String s = "";
        for (int i = 0; i < result.getSize(); i++) {
            KeyedDataCollection kColl = (KeyedDataCollection) result.getElement(i);
            String tableName = (String) kColl.getValueAt("tab_code");
            String tableType = (String) kColl.getValueAt("tab_type");
            String tableOwer = (String) kColl.getValueAt("tab_owner");
            String optionValue = tableName + "*" + tableType + "*" + tableOwer;
            s = s + optionValue + "=" + tableName + "|";
        }
        return String.valueOf(result.getSize()) + "|" + s;
    } catch (Exception e) {
        if (sqlTool != null) {
            try {
                sqlTool.closeconn();
            } catch (Exception ee) {
            }
        }
        throw e;
    }
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-20 13:06:38)
 * @return int
 * @param userName java.lang.String
 * @param userPass java.lang.String
 */
private int mgrLogon(String userName, String userPass) {
	try{
		if(!CmisConstance.isServerStarted) return 1;
		MonitorClient monitorClient = (MonitorClient)CmisConstance.monitor;
		if(monitorClient == null) return 1;
		String mgrName = monitorClient.getMgrName();
		String mgrPass = monitorClient.getMgrPass();
		if(mgrName == null || mgrPass == null) return 2;
		if(!userName.trim().equals(mgrName.trim())||!(userPass.trim().equals(mgrPass.trim()))) return 3;
	}catch(Exception e){
		return 4;
	}
	return 0;
}
/**
	 * When an object implementing interface <code>Runnable</code> is used 
	 * to create a thread, starting the thread causes the object's 
	 * <code>run</code> method to be called in that separately executing 
	 * thread. 
	 * <p>
	 * The general contract of the method <code>run</code> is that it may 
	 * take any action whatsoever.
	 *
	 * @see     java.lang.Thread#run()
	 */
public void run() {
	
	if (message.startsWith("1001")) //reflash the server information
		{
		if (CmisConstance.logonNums < 0)
			CmisConstance.logonNums = 0;
			if (CmisConstance.onLineNums < 0)
			CmisConstance.onLineNums = 0;

		String msg = "1001" + CmisConstance.logonNums + "|" + CmisConstance.onLineNums;
		monitor.sendMessageToServer(msg);
	}
	else if(message.startsWith("2001")){
			   try{
			    	monitor.sendMessageToServer("20010"+message.substring(5,message.length())+"}"+getReloadSettings());
			   }catch(Exception ex){
				   monitor.sendMessageToServer("20011"+message.substring(5,message.length()));
			   }
		}
	else if(message.startsWith("2002")){
		 StringTokenizer toke = new StringTokenizer(message,"|");
		 toke.nextElement();
		 String serverName = (String)toke.nextElement();
		 String id=(String)toke.nextElement();
		 String desc =(String)toke.nextElement();
		 try{
			 icbc.cmis.service.GeneralSQLService sqlTool = new icbc.cmis.service.GeneralSQLService();
			 int tidx = id.indexOf("*");
			 String tableName = id.substring(0,tidx);
			 id = id.substring(tidx+1,id.length());
			 String tableType = id.substring(0,id.indexOf("*"));
			 String tableOwn = id.substring(id.indexOf("*")+1,id.length());
			 
			 sqlTool.updateDictTable(tableName,tableOwn,Integer.valueOf(tableType).intValue());
			
			 monitor.sendMessageToServer("20020|"+serverName+"|"+desc);
		 }catch(Exception ex){
			 monitor.sendMessageToServer("20021|"+serverName+"|"+desc);
		}
	}
	
	else if(message.startsWith("2004")){//管理员登录
		try{
			message = message.substring(message.indexOf("|")+1,message.length());
			int index = message.indexOf("|");
			String userName = message.substring(0,index);
			String userPass = message.substring(index+1,message.length());
			monitor.sendMessageToServer("2004"+mgrLogon(userName,userPass));
		}catch(Exception e){
			monitor.sendMessageToServer("20044");
		}
	}
	else if(message.startsWith("2009")){//资源回收
		try{
			Runtime rt = Runtime.getRuntime();
			rt.gc();
			long tm = rt.totalMemory();
			long fm = rt.freeMemory();
			String total = "total memory is      "+String.valueOf(tm);
			String free =  "free memory is       "+String.valueOf(fm);
			String av =    "used memory is       "+String.valueOf(tm -fm);
			monitor.sendMessageToServer("20090"+"\n"+total+"\n"+free+"\n"+av);
		}catch(Exception e){
			monitor.sendMessageToServer("20091");
		}
	}
	else if(message.startsWith("2011")){//资源回收
		try{
			Runtime rt = Runtime.getRuntime();
			rt.gc();
			long tm = rt.totalMemory();
			long fm = rt.freeMemory();
			String total = String.valueOf(tm);
			String free =  String.valueOf(fm);
			String av =    String.valueOf(tm -fm);
			av = av+" [ "+String.valueOf(100L - (fm * 100L) / tm) + "% ]";
			monitor.sendMessageToServer("20110"+"|"+total+"|"+free+"|"+av);
		}catch(Exception e){
			monitor.sendMessageToServer("20111");
		}
	}
	else if(message.startsWith("2012")){//取系统属性参数配置
		try{
			String srvName = message.substring(5,message.length());
			String pkg = "20120|"+srvName+"|";
			java.util.Properties properties = System.getProperties();
			for(Enumeration enumeration = properties.keys(); enumeration.hasMoreElements();){
				String s1 = (String)enumeration.nextElement();
				String s2 = (String)properties.get(s1);
				pkg = pkg+s1+" = "+s2+"\n";
			}
			monitor.sendMessageToServer(pkg);
		
		}catch(Exception e){
			monitor.sendMessageToServer("20121");
		}
	}
	else if(message.startsWith("2008")){
		message = message.substring(5,message.length());
		int index = message.indexOf("|");
		String srvName = message.substring(0,index);
		String file = message.substring(index+1,message.length());
		try{
			FileInputStream in = new FileInputStream(file);
			int len = in.available();
			byte[] bContent = new byte[len];
			in.read(bContent,0,len);
			monitor.sendMessageToServer("20080|"+srvName+"|"+new String(bContent));
			
		}catch(Exception e){
			monitor.sendMessageToServer("20081|"+srvName);
		}
	}
	else if(message.startsWith("2016")){
		message = message.substring(5,message.length());
		int index = message.indexOf("|");
		String srvName = message.substring(0,index);
		String file = message.substring(index+1,message.length());
		try{
			FileInputStream in = new FileInputStream(file);
			int len = in.available();
			byte[] bContent = new byte[len];
			in.read(bContent,0,len);
			monitor.sendMessageToServer("20160|"+srvName+"|"+new String(bContent));
			
		}catch(Exception e){
			monitor.sendMessageToServer("20161|"+srvName);
		}
	}
	else if(message.startsWith("3001")){
		message = message.substring(5,message.length());
		try{
			CmisConstance.initializeErrorMessageTable();
			monitor.sendMessageToServer("30010|"+message);
		}catch(Exception e){
			monitor.sendMessageToServer("30011|"+message);
		}
	}
	else if(message.startsWith("3002")){
		message = message.substring(5,message.length());
		try{
			CmisConstance.initializePassVerifyTable();
			monitor.sendMessageToServer("30020|"+message);
		}catch(Exception e){
			monitor.sendMessageToServer("30021|"+message);
		}
	}
	else if(message.startsWith("4444")){
		message = message.substring(5,message.length());
		try{
			CmisConstance.initializeLimitedOpList();
			monitor.sendMessageToServer("44440|"+message);
		}catch(Exception e){
			monitor.sendMessageToServer("44441|"+message);
		}
	}
	return; 
}
}
