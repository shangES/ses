package icbc.cmis.service;

import java.util.Vector;
import java.net.InetAddress;
import icbc.cmis.base.KeyedDataCollection;
import icbc.cmis.base.IndexedDataCollection;
import icbc.cmis.base.CmisConstance;
import icbc.missign.GetMenu;
/**
 * Insert the type's description here.
 * Creation date: (2002-2-5 19:23:15)
 * @author: Administrator
 */
public class CmisSystemInfoThread implements Runnable {
	private long runIntevalTime = 600000;
	private long lastRunTime = 0;
	private long lastGCTime = 0;
	private long gcIntevalTime = 3600000;
/**
 * CmisOperationPoolMgr constructor comment.
 */
public CmisSystemInfoThread() {
	super();
	try{
		runIntevalTime = Long.valueOf((String)icbc.cmis.base.CmisConstance.getParameterSettings().get("threadRunInteval")).longValue();
	}catch(Exception e){
		runIntevalTime = 300000;
	}
	try{
		gcIntevalTime = Long.valueOf((String)icbc.cmis.base.CmisConstance.getParameterSettings().get("GCRunInteval")).longValue();
	}catch(Exception e){
		gcIntevalTime = 3600000;
	}
	lastRunTime = System.currentTimeMillis();
	lastGCTime = lastRunTime;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-4-13 17:11:49)
 */
private synchronized void clearDict() throws Exception{
	JDBCProcedureService srv = null;
	try{
//    	System.out.println("clearDict1");
		int dt = Integer.valueOf((String)icbc.cmis.base.CmisConstance.getParameterSettings().get("needClearDictDay")).intValue();
		String yy = CmisConstance.getWorkDate("yyyy");
		String mm = CmisConstance.getWorkDate("MM");
		String dd = CmisConstance.getWorkDate("dd");
		int intD = Integer.valueOf(dd).intValue();
		int intM = Integer.valueOf(mm).intValue();
		int intY = Integer.valueOf(yy).intValue();
//    	System.out.println("clearDict2");		
		if(intD <= dt){
			if(intM == 1){
				intY = intY -1;
				intM = 12;
			}
			intD = 28+intD;//只求一个概数，每月以28天计算
		}
		intD = intD-dt;
//    	System.out.println("clearDict3");		
		yy = String.valueOf(intY);
		mm = String.valueOf(intM);
		dd = String.valueOf(intD);
		if(mm.length() == 1)mm = "0"+mm;
		if(dd.length() == 1)dd= "0"+dd;
		Vector v = new Vector(3);
		v.add(0,yy+mm+dd);
		v.add(1,java.net.InetAddress.getLocalHost().getHostAddress()+CmisConstance.appServerID);
		v.add(2,"");
    	//System.out.println("clearDict4");		
		srv = new JDBCProcedureService(new icbc.cmis.operation.DummyOperation());
//		String userId = (String)CmisConstance.getParameterSettings().get("dbUserName");
//	    String userPass = (String)CmisConstance.getParameterSettings().get("dbUserPass");
//		srv.getConn(userId,userPass);
		srv.getConn("missign");
		java.util.Enumeration e = icbc.cmis.base.CmisConstance.getDBUsers();
	   	//System.out.println("clearDict5");
		for(;e.hasMoreElements();){
			String sysType = (String)e.nextElement();
			v.setElementAt(sysType.trim(),2);
			srv.executeJournalProcedure("clearDICT_TEMPORARY_TABLEProc",v);
		}
		srv.closeConn();
		srv = null;
		
	}catch(Exception e){
		throw e;
	}finally {
		if(srv != null){
			try{
				srv.closeConn();
			}catch(Exception ee){}
			srv = null;
		}
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2002-4-13 17:12:43)
 */
private synchronized void getSystemInfo()
{
    lastRunTime = System.currentTimeMillis();
    String localHost = "";
    try
        {
        localHost = java.net.InetAddress.getLocalHost().getHostAddress();
    }
    catch (Exception e)
        {
        localHost = "unknown host ip";
    }
    localHost = localHost + " " + CmisConstance.appServerID + " ";
    System.out.println(
        "AppServer[" + localHost + "]Runing CmisSystemInfoThread thread.....");
    if (CmisConstance.monitor != null){
    	//System.out.println("getinfo1");
        CmisConstance.monitor.sendMessageToServer(
            "AppServer[" + localHost + "]Runing CmisSystemInfoThread thread.....");
    }
    	//System.out.println("getinfo2");    
    //java.util.Date date = new java.util.Date();
    //java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy年MM月dd日HH:mm ss");
    String curTime = CmisConstance.getWorkDate("yyyy年MM月dd日HH:mm ss");
    System.out.println("Current AppServer time[" + curTime + "]");
    if (CmisConstance.monitor != null)
        CmisConstance.monitor.sendMessageToServer(
            "Current AppServer time[" + curTime + "]");
    icbc.cmis.base.Trace.trace(
        "",
        0,
        0,
        "",
        "Current AppServer time[" + curTime + "]");

    Runtime runtime = Runtime.getRuntime();
    String totalMem =
        "Total memery[" + runtime.totalMemory() + "] in [" + localHost + "]";
    System.out.println(totalMem);
    if (CmisConstance.monitor != null)
        CmisConstance.monitor.sendMessageToServer(totalMem);
    icbc.cmis.base.Trace.trace("", 0, 0, "", totalMem);

    if (gcIntevalTime != -1
        && System.currentTimeMillis() - lastGCTime >= gcIntevalTime)
        {
        lastGCTime = System.currentTimeMillis();
        String curFreeMem1 =
            "Current free memery["
                + runtime.freeMemory()
                + "] in ["
                + localHost
                + "] Garbage collection in working...";
        System.out.println(curFreeMem1);
        if (CmisConstance.monitor != null)
            CmisConstance.monitor.sendMessageToServer(curFreeMem1);
        icbc.cmis.base.Trace.trace("", 0, 0, "", curFreeMem1);

        runtime.gc();

        String curFreeMem2 =
            "Current free memery["
                + runtime.freeMemory()
                + "] in ["
                + localHost
                + "] Garbage collection end.";
        System.out.println(curFreeMem2);
        if (CmisConstance.monitor != null)
            CmisConstance.monitor.sendMessageToServer(curFreeMem2);
        icbc.cmis.base.Trace.trace("", 0, 0, "", curFreeMem2);
    }
    else
        {
        String curFreeMem1 =
            "Current free memery[" + runtime.freeMemory() + "] in [" + localHost + "]";
        System.out.println(curFreeMem1);
        if (CmisConstance.monitor != null)
            CmisConstance.monitor.sendMessageToServer(curFreeMem1);
        icbc.cmis.base.Trace.trace("", 0, 0, "", curFreeMem1);
    }
 //comment by yanbo on 20060405
/*
    String cmisDBC = "";
    if (icbc.cmis.base.CmisConstance.JDBCPool
        instanceof WasJDBCConnectionPoolService)
        cmisDBC =
            "In using DBConnections ["
                + icbc.cmis.service.WasJDBCConnectionPoolService.cons
                + "] in ["
                + localHost
                + "]";
    else
        if (icbc.cmis.base.CmisConstance.JDBCPool instanceof JDBCConnectionPoolService)
            cmisDBC =
                "In using DBConnections ["
                    + icbc.cmis.base.ConnectionManager.inUseConnections
                    + "] in ["
                    + localHost
                    + "]";
    System.out.println(cmisDBC);
    if (CmisConstance.monitor != null)
        CmisConstance.monitor.sendMessageToServer(cmisDBC);
    icbc.cmis.base.Trace.trace("", 0, 0, "", cmisDBC);
    String mqStr =
        "Current mqMgr connections is: ["
            + MQConnectionPoolService.mqConnCount
            + "] in ["
            + localHost
            + "]";
    String mqStr1 =
        "Current mqMgr in used  connections is: ["
            + MQConnectionPoolService.inUsedMqConnCount
            + "] in ["
            + localHost
            + "]";
    System.out.println(mqStr);
    if (CmisConstance.monitor != null)
        CmisConstance.monitor.sendMessageToServer(mqStr);
    icbc.cmis.base.Trace.trace("", 0, 0, "", mqStr);
    System.out.println(mqStr1);
    if (CmisConstance.monitor != null)
        CmisConstance.monitor.sendMessageToServer(mqStr1);
    icbc.cmis.base.Trace.trace("", 0, 0, "", mqStr1);
//    lastRunTime = System.currentTimeMillis();*/
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
	try{
		icbc.cmis.base.Trace.trace("*****CmisSystemInfoThread thread is runing....",0,0,"","");
		System.out.println(" CmisSystemInfoThread thread is runing....");
					updateDict();		
		while(true){
			if(System.currentTimeMillis() - lastRunTime >= runIntevalTime){
				try{
					if(runIntevalTime != -1){
						getSystemInfo();
					}
				}catch(Exception ee){
					System.out.println("in CmisSystemInfoThread, Get System info failure!\nExeption:"+ee.toString());
				}
				try{
					updateDict();
				}catch(Exception ee){
					System.out.println("in CmisSystemInfoThread, Update dict table failure!\nExeption:"+ee.toString());
				}
				try{
					clearDict();
				}catch(Exception ee){
					System.out.println("in CmisSystemInfoThread, clear dict table failure!\nExeption:"+ee.toString());
				}
			}else{
				Thread.currentThread().sleep(runIntevalTime);
			}
		}
	}catch(Exception e){
		CmisConstance.pringMsg("CmisSystemInfoThread thread run fail!!\nMaybe Administrator must restart the Thread CmisSystemInfoThread!\n");
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2002-4-13 17:11:49)
 */
private synchronized void updateDict() throws Exception{
	JDBCProcedureService srv = null;
	GeneralSQLService tool = null;
	try{
		String host = InetAddress.getLocalHost().getHostAddress()+CmisConstance.appServerID;
    	//System.out.println("updateDict1");
		KeyedDataCollection context = new KeyedDataCollection();
		context.setName("inColl");
		context.addElement("host",host.trim());
		context.addElement("tableOwner","");
		Vector inParam = new Vector(2);
		inParam.add(0,"host");
		inParam.add(1,"tableOwner");
		IndexedDataCollection iColl = new IndexedDataCollection();
		iColl.setName("iResult");
		Vector outParam = new Vector(3);
		outParam.add(0,"tableName");
		outParam.add(1,"tableType");
		outParam.add(2,"tableOwner");
		srv = new JDBCProcedureService(new icbc.cmis.operation.DummyOperation());
//		String userId = (String)CmisConstance.getParameterSettings().get("dbUserName");
//	    String userPass = (String)CmisConstance.getParameterSettings().get("dbUserPass");
//		srv.getConn(userId,userPass);
		srv.getConn("missign");
		String tmpStr = "PACK_DICT_TEMPORARY_TABLE.getDICT_TEMPORARY_TABLE";
		java.util.Enumeration e = CmisConstance.getDBUsers();
    	//System.out.println("updateDict2");		
		for(;e.hasMoreElements();){
			String sysType11 = (String)e.nextElement();
			context.setValueAt("tableOwner",sysType11.trim());
			if(srv.executeProcedure(tmpStr,context,inParam,outParam,iColl) != 0){
				srv.closeConn();
				System.out.println("AppServer["+host+"] call proc ["+tmpStr+"] fail!");
				CmisConstance.monitor.sendMessageToServer("后台线程更新字典表：AppServer["+host+"] call proc ["+tmpStr+"] fail!");
				throw new Exception("AppServer["+host+"] call proc ["+tmpStr+"] fail!");
			}
		}
    	//System.out.println("updateDict3");		
		for(int i = 0;i<iColl.getSize();i++){
			if(tool == null){
				tool = new GeneralSQLService();
			}
			String tableName = (String)((KeyedDataCollection)iColl.getElement(i)).getValueAt("tableName");
			String tableType = (String)((KeyedDataCollection)iColl.getElement(i)).getValueAt("tableType");
			String tableOwner = (String)((KeyedDataCollection)iColl.getElement(i)).getValueAt("tableOwner");
			
		    try{  
//					if(tableName.trim().equalsIgnoreCase("UTILMAJORMAGICBCECC")){
//						GetMenu.cached = false;
//						CmisConstance.pringMsg("in CmisSystemInfoThread,appServer["+host+"] 修改业务功能菜单 success!");
//						CmisConstance.monitor.sendMessageToServer("后台线程更新字典表：in CmisSystemInfoThread,appServer["+host+"] 修改业务功能菜单 success!");
//					}
					if(tableName.trim().startsWith("UTILMAJORMAGICBCECC") || tableName.trim().startsWith("utilmajormagicbcecc")){
						String target = tableName.trim();
						target = target.substring(target.indexOf("[")+1,target.indexOf("]"));
						GetMenu.menus.remove(target);
						CmisConstance.pringMsg("in CmisSystemInfoThread,appServer["+host+"] 修改业务功能菜单 success!");
						CmisConstance.monitor.sendMessageToServer("后台线程更新字典表：in CmisSystemInfoThread,appServer["+host+"] 修改业务功能菜单 success!");
					}
					if(tableName.trim().equalsIgnoreCase("UTILMAJORMAGALL")){
						GetMenu.menus.clear();
						CmisConstance.pringMsg("in CmisSystemInfoThread,appServer["+host+"] 重载业务功能菜单 success!");
						CmisConstance.monitor.sendMessageToServer("后台线程更新字典表：in CmisSystemInfoThread,appServer["+host+"] 重载业务功能菜单 success!");
					}
					else if(tableName.trim().equalsIgnoreCase("INITWORKDATE")){
					    CmisConstance.setWorkDate();
					    CmisConstance.pringMsg("in CmisSystemInfoThread,appServer["+host+"] 初始化系统日期 success!");
						CmisConstance.monitor.sendMessageToServer("后台线程初始化系统日期：in CmisSystemInfoThread,appServer["+host+"] 初始化系统日期 success!");
					    //System.out.println("in CmisSystemInfoThread,appServer["+host+"] 初始化系统日期 success!");
					}
					else if(tableName.trim().equalsIgnoreCase("INITLIMITEDOP")){
					    CmisConstance.initializeLimitedOpList();
					    CmisConstance.pringMsg("in CmisSystemInfoThread,appServer["+host+"] 初始化最大操作数 success!");
						CmisConstance.monitor.sendMessageToServer("后台线程初始化最大操作数：in CmisSystemInfoThread,appServer["+host+"] 初始化最大操作数 success!");
						//System.out.println("in CmisSystemInfoThread,appServer["+host+"] 初始化最大操作数 success!");
					}
					else{
						tool.updateDictTable(tableName,tableOwner,Integer.valueOf(tableType).intValue());
						CmisConstance.pringMsg("in CmisSystemInfoThread,appServer["+host+"] update dict ["+tableName+"] success!");
						CmisConstance.monitor.sendMessageToServer("后台线程更新字典表：in CmisSystemInfoThread,appServer["+host+"] update dict ["+tableName+"] success!");
					    //System.out.println("后台线程更新字典表：in CmisSystemInfoThread,appServer["+host+"] update dict ["+tableName+"] success!");
					}
					try{
						Vector vv = new Vector(3);
						vv.add(0,host);
						vv.add(1,tableName.trim());
						vv.add(2,tableOwner);
						srv.executeJournalProcedure("deleteDICT_TEMPORARY_TABLEProc",vv);
					}catch(Exception ed){
						System.out.println("in CmisSystemInfoThread,appServer["+host+"] call procedure[deleteDICT_TEMPORARY_TABLEProc] to delete dict ["+tableName+"] falure!Exception:\n"+ed.getMessage());
						CmisConstance.monitor.sendMessageToServer("后台线程更新字典表：in CmisSystemInfoThread,appServer["+host+"] call procedure[deleteDICT_TEMPORARY_TABLEProc] to delete dict ["+tableName+"] falure!Exception:\n"+ed.getMessage());
				
					}
				
			}catch(Exception ee){
				System.out.println("in CmisSystemInfoThread,appServer["+host+"] update dict ["+tableName+"] failure!Exception:\n"+ee.getMessage());
				CmisConstance.monitor.sendMessageToServer("后台线程更新字典表：in CmisSystemInfoThread,appServer["+host+"] update dict ["+tableName+"] failure!Exception:\n"+ee.getMessage());
			}
		}
		srv.closeConn();
		srv = null;
		if(tool != null){
			tool.closeConnection();
			tool = null;
		}
		
	}catch(Exception e){
		throw e;
	}finally {
		if(srv != null){
			try{
				srv.closeConn();
			}catch(Exception ee){}
			srv = null;
		}
		if(tool != null){
			try{
				tool.closeConnection();
			}catch(Exception ee){}
			tool = null;
		}
	}
}
}
