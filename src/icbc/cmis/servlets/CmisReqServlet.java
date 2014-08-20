package icbc.cmis.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import icbc.cmis.base.*;
/**
 * Insert the type's description here.
 * Creation date: (2001-12-24 13:21:39)
 * @author: Administrator
 * //20040423去掉同步的交易 chenj		
 */
public class CmisReqServlet extends HttpServlet {
/**
 * CmisReqServlet constructor comment.
 */
public CmisReqServlet() {
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
public void doPost(HttpServletRequest req, HttpServletResponse rep) throws ServletException, java.io.IOException{
	
	CmisRequestHandler requestHandle = null;
	icbc.cmis.base.Trace.trace("",0,0,"","CmisReqServlet.doPost() begin");
	try
	{
		requestHandle = new CmisRequestHandler();

		CMisSessionMgr sMgr = new CMisSessionMgr(req);		
				
		requestHandle.processRequest(this,req,rep,sMgr);
		icbc.cmis.base.Trace.trace("",0,0,"","CmisReqServlet.doPost() end");
	}catch(Exception e)
	{
		if(e instanceof TranFailException)
			requestHandle.handleException(this,req,rep,e);
			else{
				TranFailException ee = new TranFailException("xdtz22126","CmisReqServlet.doPost(HttpServletRequest req, HttpServletResponse rep)",e.getMessage());
				requestHandle.handleException(this,req,rep,ee);
			}
		
		icbc.cmis.base.Trace.trace("",0,0,"","CmisReqServlet.doPost() exception,"+e.toString());
	}
}
}
