package icbc.cmis.util;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import icbc.cmis.base.*;

public class ChooseArea extends HttpServlet {
  private static final String CONTENT_TYPE = "text/xml; charset=GBK";
//  private static final String CONTENT_TYPE = "text/xml";
  /**Initialize global variables*/
  public void init() throws ServletException {
  }
  /**Process the HTTP Get request*/
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request,response);
 }

  /**Clean up resources*/
  public void destroy() {
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    CMisSessionMgr session = new CMisSessionMgr(request);

    try {
      if(!CMisSessionMgr.checkSession(request)){
//        outTime(out,"  当前会话已失效，请重新登录");
		outTime(out,genMsg.getErrMsgByLang((String)session.getSessionData("LangCode"),"099991"));
        return;
      }

      String area = request.getParameter("area");
      String range = request.getParameter("range");
      ChooseAreaDAO dao = new ChooseAreaDAO(new icbc.cmis.operation.DummyOperation());
      String xml = dao.getArea(area,range);

      out.println("<?xml version=\"1.0\" encoding=\"GBK\"?>");
      //out.println("<?xml version=\"1.0\"?>");
      out.print(xml);
//	  out.print(icbc.cmis.util.Func_XMLfiltrate.validXml(xml));
    }
    catch (TranFailException ex) {
//        outTime(out,"数据提取失败！" + ex.getErrorCode()+"" + ex.getErrorLocation()+"" + ex.getDisplayMessage()+"" + ex.getErrorMsg()+"");
		try{
			outTime(out,genMsg.getErrMsgByLang((String)session.getSessionData("LangCode"),"099993") + ex.getErrorCode()+"" + ex.getErrorLocation()+"" + ex.getDisplayMessage()+"" + ex.getErrorMsg()+"");
		}
		catch(TranFailException ee){
			ee.printStackTrace();
		}
		
    }
    catch (Exception ex) {
//        outTime(out,"数据提取失败！" + ex.getMessage());
			try{
						outTime(out,genMsg.getErrMsgByLang((String)session.getSessionData("LangCode"),"099993") + ex.getMessage());
					}
					catch(TranFailException ee){
						ee.printStackTrace();
					}
    }

  }

  public void outTime(PrintWriter out,String info) {
    out.println("<?xml version=\"1.0\"?>");
    out.println("<error>");
//    out.println(info);
	out.println(icbc.cmis.util.Func_XMLfiltrate.validXml(info));
    out.println("</error>");
  }
}