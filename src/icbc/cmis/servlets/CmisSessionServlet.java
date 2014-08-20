package icbc.cmis.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.CmisOperation;
import java.net.*;
/**
 * Insert the type's description here.
 * Creation date: (2001-12-24 13:23:30)
 * @author: Administrator
 * 
 * 20040423去掉同步的交易 chenj
 * 
 */
public class CmisSessionServlet extends HttpServlet {
/**
 * CmisSessionServlet constructor comment.
 */
public CmisSessionServlet() {
	super();
}
/**
 * service method comment.
 */
public void doGet(HttpServletRequest req, HttpServletResponse rep) throws ServletException, java.io.IOException {
	doPost(req,rep);
}
/**
 * service method comment.
 */
public void doPost(HttpServletRequest req, HttpServletResponse rep) throws ServletException, java.io.IOException {
	
	CmisRequestHandler requestHandle  = null;
	icbc.cmis.base.Trace.trace("",0,0,"","CmisSessionServlet.doPost() begin");
	try
	{
		//20040423去掉同步的交易 chenj
//		CmisConstance.onLineNumsPlus();
		requestHandle = new CmisRequestHandler();
		if(!CmisConstance.isServerStarted){
		  throw new Exception();
		}
		CMisSessionMgr sMgr = new CMisSessionMgr(req,true);
        //new added
        try{
          String opName = req.getParameter("operationName");
          if(opName.endsWith("SwitchLogin")){
            URL referringURL = new URL(req.getHeader("Referer"));
            //URL localHostURL = new URL(req.getHeader("Host"));
            sMgr.addSessionData("ReferringHost",referringURL.getHost());
            //sMgr.addSessionData("localHost",localHostURL.getHost());
          }
        }
        catch (Exception e){
        }
        
        
		requestHandle.processRequest(this,req,rep,sMgr);
/*
		String sessionManual = null;
		try{
			sessionManual = (String)CmisConstance.getParameterSettings().get("sessionManual");
		}catch(Exception eee){
			sessionManual = "false";
		}
		
		if(sessionManual !=null && sessionManual.equals("true")){
			((com.ibm.websphere.servlet.session.IBMSession)req.getSession(false)).sync();
		}
*/
		icbc.cmis.base.Trace.trace("",0,0,"","CmisSessionServlet.doPost() end");
//20040423去掉同步的交易 chenj
//		CmisConstance.logonNumsPlus();
//		CmisConstance.onLineNumsReduce();

	}catch(Exception e)
	{
//20040423去掉同步的交易 chenj		
//		CmisConstance.onLineNumsReduce();
		TranFailException te = null;
		if(e instanceof TranFailException)
			te = (TranFailException)e;
			else 
				te = new TranFailException("xdtz22122","CmisSessionServlet.doPost(HttpServletRequest req, HttpServletResponse rep)",e.getMessage());
		
		requestHandle.handleException(this,req,rep,te);
		icbc.cmis.base.Trace.trace("",0,0,"","CmisSessionServlet.doPost() exception,"+e.toString());
	}
	
}
}
