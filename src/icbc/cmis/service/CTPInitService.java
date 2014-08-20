////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2004 ICBC
//
//ALL RIGHTS RESERVED BY ICBC CORPORATION, THIS PROGRAM
//MUST BE USED SOLELY FOR THE PURPOSE FOR WHICH IT WAS  
//FURNISHED BY ICBC CORPORATION, NO PART OF THIS PROGRAM
//MAY BE REPRODUCED OR DISCLOSED TO OTHERS, IN ANY FORM
//WITHOUT THE PRIOR WRITTEN PERMISSION OF ICBC CORPORATION.
//USE OF COPYRIGHT NOTICE DOES NOT EVIDENCE PUBLICATION
//OF THE PROGRAM
//
//			ICBC CONFIDENTIAL AND PROPRIETARY
//
////////////////////////////////////////////////////////////////////////////
//

package icbc.cmis.service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.icbc.cte.base.CTEConstance;
import com.icbc.cte.base.CTEEventListener;
import com.icbc.cte.base.Context;
import com.icbc.cte.base.Settings;
import com.icbc.cte.base.Trace;
import com.icbc.cte.cs.servlet.CSServer;
import com.icbc.cte.cs.servlet.CSSessionTimeoutListener;
import com.icbc.cte.cs.servlet.StatemachineTimeoutListener;
import com.icbc.ctp.auth.init.AuthInfoModelInit;

/*************************************************************
 * 
 * <b>创建日期: </b> 2004-6-29<br>
 * <b>标题: </b>CTP初始化服务<br>
 * <b>类描述: </b><br>
 * 该类用于CM2002框架初始化时，进行CTP运行环境的初始化。<br>
 * 用于构建CTP的运行环境。<br>
 * <p>Copyright: Copyright (c)2004</p>
 * <p>Company: ICBC</p>
 * 
 * @author YangGuangRun
 * 
 * @version 
 * 
 * @since 
 * 
 * @see 
 * 
 *************************************************************/

public class CTPInitService {
	
	Context context = null;
	
	private static String dsePath = "c:\\cte\\server\\cte.ini";

	String setPassword = "";

	String setUserName = "";

	String startUpContextName = "serverCtx";
	
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param iniPath
	 * @throws Exception
	 *  
	 */
	private void initialize(String iniPath)throws Exception {
		CTEConstance.reset();
		Context.reset();
		Settings.reset(iniPath);
	
		// Creates the initial context in the server.
		context = Context.readObject(startUpContextName);
		// Starts the client server session.
		CSServer srv = (CSServer)context.getService("CSServer");
		srv.initiateServer();
		CTEEventListener lister = (CTEEventListener)context.getService("CTEListener");
		srv.addCSSessionTimeoutListener((CSSessionTimeoutListener)lister);
		srv.addStatemachineTimeoutListener((StatemachineTimeoutListener)lister);

	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * 
	 *  
	 */
	private void initService() {
		//系统管理添加
		AuthInfoModelInit ai = new AuthInfoModelInit();
		ai.init();
		
		//////////////////////////////////////////////////////////////////////
		/*
		 * 修改原因：由于InitServiece 由参数控制是否调用，而初始化交易限制列表
		 * 			 属于系统必须的动作，所以在此将该操作移出至init(),
		 * 			 doGetRefresh(),doPostRefresh();直接调用。
		 * 修改时间：2004-07-02
		 * 修改人：杨光润
		 * 			
		 */
		/*try {
			CTEConstance.loadSystemInfo();
		}catch (Exception ex) {
		}*/
		///////////////////////////////////////////////////////////////////////
		
	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param req
	 * @param res
	 * @return
	 *  
	 */
	protected String processRequest(HttpServletRequest req,HttpServletResponse res){
		String message = "Initialization OK";
		try {
			String path = CTPInitService.dsePath;
			String password = (String)req.getParameter("password");
			String userName = (String)req.getParameter("userName");

			/*if (userName == null || !userName.trim().equals(setUserName))
				 {
				  return "ERROR in Server: 非法用户!";
				 }
				 if (password == null || !password.trim().equals(setPassword))
				 {
				  return "ERROR in Server: 非法口令!";
				 }
			 */

			String contextName = (String)req.getParameter("context");
			if (contextName != null) {
				this.startUpContextName = contextName;
			}

			String iniPath = (String)req.getParameter("path");
			if (iniPath == null) {
				iniPath = path;
			}
			if (iniPath != null) {
				initialize(iniPath);
			}
			else {
				return "ERROR in Server:  参数[dseIniPath]未配置！";
			}
		}
		catch (Throwable t) {
			message = "ERROR in Server : " + t.toString();
		}

		return message;
	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param sc
	 * @return
	 * @throws Exception
	 *  
	 */
	public boolean init(ServletConfig sc,boolean isNeedAuthInfo) throws Exception {
		try {
			String initStart = sc.getInitParameter("initStart");
	
			String path = sc.getInitParameter("dseIniPath");
			CTPInitService.dsePath = path;
	
			setUserName = sc.getInitParameter("userName");
			setPassword = sc.getInitParameter("password");
	
			if (initStart != null && initStart.equals("true")) {
				initialize(path);
			}
			String localHost = "";
			try {
				localHost =java.net.InetAddress.getLocalHost().getHostAddress();
			}
			catch (Exception exx) {
				localHost = "unknown host ip";
			}
			String cloneId = sc.getInitParameter("JVMID");
			if (cloneId == null) {
				cloneId = "";
			}
			CTEConstance.appServerID = localHost.trim() + cloneId.trim();
			if(isNeedAuthInfo)
				initService(); //系统管理初始化、EntityService 初始化。
				
			////////////////////////////////////////////////////////////////////
			/*
			 * 修改原因：由于InitServiece 由参数控制是否调用，而初始化交易限制列表
		 	 * 			 属于系统必须的动作，所以在此将该操作移至此
		 	 * 修改时间：2004-07-02
		 	 * 修改人：YangGuangRun
			 */
			try {
				CTEConstance.loadSystemInfo();
			}catch (Exception ex) {
			}
			////////////////////////////////////////////////////////////////////
		}
		catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param req
	 * @param res
	 * @return
	 * @throws java.io.IOException
	 *  
	 */
	public boolean doGetRefresh(HttpServletRequest req,
								HttpServletResponse res,
								boolean isNeedAuthInfo)
				   throws java.io.IOException{
		String message = "";
		message = processRequest(req, res);
		if(isNeedAuthInfo)
			initService(); //系统管理初始化、EntityService 初始化。
			
		//////////////////////////////////////////////////////////////////////////////
		/*
		 * 修改原因:由于CTP初始化内嵌于CM2002中所以将输出屏蔽
		 * 修改时间:2004-06-29
		 * 修改人:YangGuangRun
		 */
		// Get an OutputStream to send back a response
		/*ServletOutputStream o = res.getOutputStream();
		// Sends the response
		o.println(
			"<HEAD><H1><B>CTPDEMOStartupServlet</B></H1><HR size=0.5></HEAD>");
		o.println("<BODY>");
		o.write(message.getBytes());
		o.println("<BR><HR size=0.5>");
		o.println("<p><a href='"+CTEConstance.getAppFullWebPathBeforeLogon("0")+"logon.jsp'><b>Sign in Page...</b></a></p>");
		o.println("</BODY></HTML>");
		o.close();*/
		///////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////
		/*
		 * 修改原因：由于InitServiece 由参数控制是否调用，而初始化交易限制列表
		 * 			 属于系统必须的动作，所以在此将该操作移至此
		 * 修改时间：2004-07-02
		 * 修改人：YangGuangRun
		 */
		try {
			CTEConstance.loadSystemInfo();
		}catch (Exception ex) {
		}
		////////////////////////////////////////////////////////////////////
		
		Trace.trace("End of doGet");
		return true;
	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param req
	 * @param res
	 * @return
	 * @throws java.io.IOException
	 *  
	 */
	public boolean doPostRefresh(HttpServletRequest req,
								 HttpServletResponse res,
								 boolean isNeedAuthInfo)
					   throws java.io.IOException{
		String message = processRequest(req, res);
		if(isNeedAuthInfo)
			initService(); //系统管理初始化、EntityService 初始化。
			
		///////////////////////////////////////////////////////////////////////////
		/*
		 * 修改原因:由于CTP初始化内嵌于CM2002中所以将输出屏蔽
		 * 修改时间:2004-06-29
		 * 修改人:YangGuangRun
		 */
		//Get an output stream to send the response back
		/*java.io.DataOutputStream out =
			new java.io.DataOutputStream(res.getOutputStream());
		// The response is the "message" String converted to UTF8
		byte[] messageBytes = message.getBytes("UTF8");
		out.writeInt(messageBytes.length);
		out.write(messageBytes, 0, messageBytes.length);
		out.flush();
		out.close();*/
		///////////////////////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////////////////////
		/*
		 * 修改原因：由于InitServiece 由参数控制是否调用，而初始化交易限制列表
		 * 			 属于系统必须的动作，所以在此将该操作移至此
		 * 修改时间：2004-07-02
		 * 修改人：YangGuangRun
		 */
		try {
			CTEConstance.loadSystemInfo();
		}catch (Exception ex) {
		}
		////////////////////////////////////////////////////////////////////
		
		return true;
	}

}
