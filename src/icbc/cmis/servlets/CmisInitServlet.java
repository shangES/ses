package icbc.cmis.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import icbc.cmis.base.*;
import icbc.cmis.service.GenAppIDService;

/**
 * Insert the type's description here.
 * Creation date: (2001-12-24 13:26:23)
 * @author: Administrator
 */
public class CmisInitServlet extends HttpServlet {
	String userName = null;
	String userPass = null;
	String cteFilePath = null;
	private boolean monitorType = false;
/**
 * CmisStartupServlet constructor comment.
 */
public CmisInitServlet() {
	super();
}
/**
 * service method comment.
 */
public void doGet(HttpServletRequest req, HttpServletResponse rep) throws ServletException, java.io.IOException {
	try{
		String password1 = (String) req.getParameter("password");
		String userName1 = (String) req.getParameter("userName");

//	if (userName1 == null || !userName.trim().equals(userName))
//		{
//			tipMsg(rep,"ERROR in Server: 非法用户!");
//			return;
//		}
//
//		if (password1 == null || !password1.trim().equals(userPass))
//		{
//			tipMsg(rep,"ERROR in Server: 非法用户!");
//			return ;
//		}
//		cteFilePath = "d:\\ncmis3\\cmis3\\defaultroot\\icbccmis.xml";
//		cteFilePath = "d:\\wsad\\icbccmis_2\\Web Content\\icbccmis.xml";
		//cteFilePath = "E:\\Workbench\\icbccmis\\config\\icbccmis.xml";
//		CmisConstance.systemReset();


//			System.out.println("begin initCTEFile:"+cteFilePath);
//			CmisConstance.initCTEFile(cteFilePath);
//			System.out.println("end initCTEFile:"+cteFilePath);
					
//			icbc.cmis.base.Trace.reset();
	
//			System.out.println("begin initializePassVerifyTable");		
//			CmisConstance.initializePassVerifyTable();
//			System.out.println("end initializePassVerifyTable");	
						
			System.out.println("begin setWorkDate");				
			CmisConstance.setWorkDate();
			System.out.println("end setWorkDate");	
			
//			System.out.println("begin begin gen appid");							
//			try{
//				GenAppIDService gappid= new GenAppIDService();	
//				CmisConstance.appServerID = gappid.getAppId();
//			}
//			catch(Exception e){
//				
//			}
//			if(CmisConstance.appServerID == null) CmisConstance.appServerID = "";
//			System.out.println("end gen appid:"+CmisConstance.appServerID);	
			
//			System.out.println("SSIC initialize BEGIN.");						
//  	                SSICTool.initializeSSIC();
//	                System.out.println("SSIC initialize complete.");			
								
//			System.out.println("begin initializeErrorMessageTable");								
//			CmisConstance.initializeErrorMessageTable();
//			System.out.println("end initializeErrorMessageTable");										
			
//			System.out.println("begin initializationFmt");
//			CmisConstance.initializationFmt();
//			System.out.println("end initializationFmt");		
	
			System.out.println("begin initializeLimitedOpList()");
			CmisConstance.initializeLimitedOpList();
			System.out.println("end initializeLimitedOpList()");
			
			System.out.println("begin initialDictTables()");		
			new icbc.cmis.service.GeneralSQLService().initialDictTables();
			System.out.println("end initialDictTables()");		
			
//			System.out.println("begin startCmisSystemInfoThread()");						
//			CmisConstance.startCmisSystemInfoThread();
//			System.out.println("end startCmisSystemInfoThread()");						




//		CmisConstance.isServerStarted = true;
//		System.out.println(CmisConstance.getDictParam().get("vma350002_350"));
		tipMsg(rep,"应用服务器["+java.net.InetAddress.getLocalHost().getHostAddress()+" "+CmisConstance.appServerID+"]初始化完成! 当前工作日期为:"+CmisConstance.getWorkDate("yyyy年MM月dd日HH时mm分ss秒")+",系统日期为："+CmisConstance.getSystemData("yyyy年MM月dd日HH时mm分ss秒"));
		icbc.cmis.base.Trace.trace("",0,0,"","应用服务器["+java.net.InetAddress.getLocalHost().getHostAddress()+" "+CmisConstance.appServerID+"]初始化完成! 当前工作日期为:"+CmisConstance.getWorkDate("yyyy年MM月dd日HH时mm分ss秒")+",系统日期为："+CmisConstance.getSystemData("yyyy年MM月dd日HH时mm分ss秒"));
	}catch(Exception e){
		tipMsg(rep,"ERROR: exception is:\n"+e.getMessage());
	}
}
/**
 * service method comment.
 */
public void doPost(HttpServletRequest req, HttpServletResponse rep) throws ServletException, java.io.IOException {
	try{
		icbc.cmis.base.Trace.trace("",0,0,"","CmisStartupServlet.doPost() begin");
		String password1 = (String) req.getParameter("password");
		String userName1 = (String) req.getParameter("userName");

	if (userName1 == null || !userName.trim().equals(userName))
		{
			tipMsg1(rep,"ERROR in Server: 非法用户!");
			return;
		}

		if (password1 == null || !password1.trim().equals(userPass))
		{
			tipMsg1(rep,"ERROR in Server: 非法用户!");
			return ;
		}
		boolean isStart = true;
		String statusCheck = (String)req.getParameter("statusCheck");
		if(statusCheck != null && statusCheck.trim().equals("true")){
			if(CmisConstance.isServerStarted){

				tipMsg1(rep,"服务已经启动");
				return ;
			}else{
				isStart = false;
			}
		}
		CmisConstance.systemReset();

			System.out.println("begin initCTEFile:"+cteFilePath);
			CmisConstance.initCTEFile(cteFilePath);
			System.out.println("end initCTEFile:"+cteFilePath);
					
			icbc.cmis.base.Trace.reset();
	
//			System.out.println("begin initializePassVerifyTable");		
//			CmisConstance.initializePassVerifyTable();
//			System.out.println("end initializePassVerifyTable");	
						
			System.out.println("begin setWorkDate");				
			CmisConstance.setWorkDate();
			System.out.println("end setWorkDate");	
			
			System.out.println("begin begin gen appid");							
			try{
				GenAppIDService gappid= new GenAppIDService();	
				CmisConstance.appServerID = gappid.getAppId();
			}
			catch(Exception e){
				
			}
			if(CmisConstance.appServerID == null) CmisConstance.appServerID = "";
			System.out.println("end gen appid:"+CmisConstance.appServerID);				
					
			
			
			System.out.println("SSIC initialize BEGIN.");						
  	                SSICTool.initializeSSIC();
	                System.out.println("SSIC initialize complete.");			
								
			System.out.println("begin initializeErrorMessageTable");								
			CmisConstance.initializeErrorMessageTable();
			System.out.println("end initializeErrorMessageTable");										
			
			System.out.println("begin initializationFmt");
			CmisConstance.initializationFmt();
			System.out.println("end initializationFmt");		
	
			System.out.println("begin initializeLimitedOpList()");
			CmisConstance.initializeLimitedOpList();
			System.out.println("end initializeLimitedOpList()");
			
			System.out.println("begin initialDictTables()");		
			new icbc.cmis.service.GeneralSQLService().initialDictTables();
			System.out.println("end initialDictTables()");		
			
			System.out.println("begin startCmisSystemInfoThread()");						
			CmisConstance.startCmisSystemInfoThread();
			System.out.println("end startCmisSystemInfoThread()");						

		CmisConstance.isServerStarted = true;
		tipMsg1(rep,"应用服务器["+java.net.InetAddress.getLocalHost().getHostAddress()+" "+CmisConstance.appServerID+"]初始化完成! 工作日期为:"+CmisConstance.getWorkDate("yyyy年MM月dd日HH时mm分ss秒")+",系统日期为："+CmisConstance.getSystemData("yyyy年MM月dd日HH时mm分ss秒"));
		icbc.cmis.base.Trace.trace("",0,0,"","应用服务器["+java.net.InetAddress.getLocalHost().getHostAddress()+" "+CmisConstance.appServerID+"]初始化完成! 当前工作日期为:"+CmisConstance.getWorkDate("yyyy年MM月dd日HH时mm分ss秒")+",系统日期为："+CmisConstance.getSystemData("yyyy年MM月dd日HH时mm分ss秒"));
	}catch(Exception e){
		tipMsg1(rep,"ERROR: exception is:\n"+e.getMessage());
	}
}
/**
   @roseuid 3A51E04500FA
   */
public void init(ServletConfig sc)
{
	try
	{
		super.init(sc);
		String initStart = getInitParameter("initStart");
		cteFilePath = sc.getInitParameter("CTEFilePath");
		userName = sc.getInitParameter("userName");
		userPass = sc.getInitParameter("userPass");
		CmisConstance.appServerID = sc.getInitParameter("JVMID");
		if(CmisConstance.appServerID == null) CmisConstance.appServerID = "";
		CmisConstance.appServerID = CmisConstance.appServerID.trim();

		if( initStart!= null && initStart.equals("true"))
		{
			CmisConstance.systemReset();
			System.out.println("begin initCTEFile:"+cteFilePath);
			CmisConstance.initCTEFile(cteFilePath);
			System.out.println("end initCTEFile:"+cteFilePath);
					
			icbc.cmis.base.Trace.reset();
	
//			System.out.println("begin initializePassVerifyTable");		
//			CmisConstance.initializePassVerifyTable();
//			System.out.println("end initializePassVerifyTable");	
						
			System.out.println("begin setWorkDate");				
			CmisConstance.setWorkDate();
			System.out.println("end setWorkDate");	
			
			System.out.println("begin begin gen appid");							
			try{
				GenAppIDService gappid= new GenAppIDService();	
				CmisConstance.appServerID = gappid.getAppId();
			}
			catch(Exception e){
				
			}
			if(CmisConstance.appServerID == null) CmisConstance.appServerID = "";
			System.out.println("end gen appid:"+CmisConstance.appServerID);	
			
			
			System.out.println("SSIC initialize BEGIN.");						
  	                SSICTool.initializeSSIC();
	                System.out.println("SSIC initialize complete.");			
								
			System.out.println("begin initializeErrorMessageTable");								
			CmisConstance.initializeErrorMessageTable();
			System.out.println("end initializeErrorMessageTable");										
			
			System.out.println("begin initializationFmt");
			CmisConstance.initializationFmt();
			System.out.println("end initializationFmt");		
	
			System.out.println("begin initializeLimitedOpList()");
			CmisConstance.initializeLimitedOpList();
			System.out.println("end initializeLimitedOpList()");
			
			System.out.println("begin initialDictTables()");		
			new icbc.cmis.service.GeneralSQLService().initialDictTables();
			System.out.println("end initialDictTables()");		
			
			System.out.println("begin startCmisSystemInfoThread()");						
			CmisConstance.startCmisSystemInfoThread();
			System.out.println("end startCmisSystemInfoThread()");						




			CmisConstance.isServerStarted = true;
			log("AppServer["+java.net.InetAddress.getLocalHost().getHostAddress()+" "+CmisConstance.appServerID+"]initialization ok! current work data:"+CmisConstance.getWorkDate("yyyy年MM月dd日HH时mm分ss秒")+",current system data："+CmisConstance.getSystemData("yyyy年MM月dd日HH时mm分ss秒"));
		}
	}
	catch (Exception e)
	{
		log("icbc.cmis.servlets.CmisStartupServlet Initialization: Exception in init: " + e);
	}
	log("icbc.cmis.servlets.CmisStartupServlet Initialization:End of init");
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-3 19:51:52)
 * @param o javax.servlet.ServletOutputStream
 */
private void tipMsg( HttpServletResponse rep,String message)throws java.io.IOException   {
	java.io.PrintWriter o = (java.io.PrintWriter)rep.getWriter();
	o.println("<HEAD><H1><B>ICBCCmisStartupServlet</B></H1><HR></HEAD>");
	o.println("<BODY>");
	o.write(message);
	o.println("<BR><HR>");
	o.println("</BODY></HTML>");
	o.close();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-3 19:51:52)
 * @param o javax.servlet.ServletOutputStream
 */
private void tipMsg1( HttpServletResponse rep,String message)throws java.io.IOException   {
	java.io.DataOutputStream out = new java.io.DataOutputStream(rep.getOutputStream());
	byte[] messageBytes=message.getBytes("UTF8");
	out.writeInt(messageBytes.length);
	out.write(messageBytes,0,messageBytes.length);

	out.close();
}
}
