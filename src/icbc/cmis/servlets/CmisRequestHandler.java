package icbc.cmis.servlets;

import icbc.cmis.base.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import icbc.cmis.operation.*;
//import icbc.cmis.service.OpAuthService;
import javax.servlet.*;
import icbc.missign.*;
/**
 * Insert the type's description here.
 * Creation date: (2001-12-24 13:25:36)
 * @author: Administrator
 */
public class CmisRequestHandler {
  /**
   * CmisRequestHandler constructor comment.
   */
  public CmisRequestHandler() {
    super();
  }
  /**
   * Insert the method's description here.
   * Creation date: (2001-12-25 10:22:21)
   * @param response javax.servlet.http.HttpServletRequest
   */
  public void handleException(HttpServlet httpServlet, HttpServletRequest req, HttpServletResponse response, Exception ex) {
    try {
      if (!CmisConstance.isServerStarted) {
        req.setAttribute("tranErrorCode", "xdtz22126");
        req.setAttribute("tranErrorLocation", "CmisRequestHandler.handleException()");
        req.setAttribute("tranErrorDispMsg", "请联系系统管理员确认系统是否已经准备就绪！");
        req.setAttribute("tranErrorMsg", "Server not start up!");
        ServletContext servletcontext = httpServlet.getServletContext();
        RequestDispatcher requestdispatcher = servletcontext.getRequestDispatcher("/error.jsp");
        requestdispatcher.forward(req, response);
        icbc.cmis.base.Trace.trace("CmisRequestHandler", 0, 0, "", "system not start!");
        return;
      }
      String page = null;
      TranFailException eee = null;
      if (ex instanceof SignOnException) {
        page = (String)CmisConstance.getParameterSettings().get("signOnErrPage");
        eee = (TranFailException)ex;

      }
      else if (ex instanceof TranFailException) {
        page = (String)CmisConstance.getParameterSettings().get("tranErrorPage");
        eee = (TranFailException)ex;

      }
      else {
        page = (String)CmisConstance.getParameterSettings().get("tranErrorPage");
        eee = new TranFailException("未知错误码", "", ex.toString(), "未知错误");

      }
      if (eee.getErrorCode().trim().equals("xdtz22118")) {
        page = (String)CmisConstance.getParameterSettings().get("signOnErrPage");
      }
      String xmlOutputMark = null;
      try {
        xmlOutputMark = (String)req.getParameter("xmlOutput");
      }
      catch (Exception eee1) {}
      if (xmlOutputMark != null && xmlOutputMark.trim().equals("true")) {
        String encoding = (String)CmisConstance.getParameterSettings().get("encoding");
        String xmlStr = "<?xml version=\"1.0\" encoding=\"" + encoding + "\" ?>\n";
        xmlStr = xmlStr + "<error>\n";
        xmlStr = xmlStr + "<errorCode> 错误代码：" + eee.getErrorCode() + " </errorCode>\n";
        xmlStr = xmlStr + "<errorDispMsg> 错误信息：" + eee.getDisplayMessage() + " </errorDispMsg>\n";
        xmlStr = xmlStr + "</error>";
        response.setContentType("text/html; charset=" + encoding);
        PrintWriter out = response.getWriter();
        out.println(xmlStr);

        out.flush();
        out.close();
        return;
      }
      else {
        if (page == null) {
          req.setAttribute("tranErrorCode", "xdtz22126");
          req.setAttribute("tranErrorLocation", "CmisRequestHandler.handleException()");
          req.setAttribute("tranErrorDispMsg", "请联系系统管理员确认系统是否已经准备就绪！");
          req.setAttribute("tranErrorMsg", "Server not start up!");
          ServletContext servletcontext = httpServlet.getServletContext();
          RequestDispatcher requestdispatcher = servletcontext.getRequestDispatcher("/error.jsp");
          requestdispatcher.forward(req, response);
          icbc.cmis.base.Trace.trace("CmisRequestHandler", 0, 0, "", "system not start!");
          return;
        }
        req.setAttribute("tranErrorCode", eee.getErrorCode());
        req.setAttribute("tranErrorLocation", eee.getErrorLocation());
        req.setAttribute("tranErrorDispMsg", eee.getDisplayMessage());
        req.setAttribute("tranErrorMsg", eee.getErrorMsg());
        ServletContext servletcontext = httpServlet.getServletContext();
        RequestDispatcher requestdispatcher = servletcontext.getRequestDispatcher(page);
        requestdispatcher.forward(req, response);
        icbc.cmis.base.Trace.trace("CmisRequestHandler", 0, 0, "", "handleException:" + eee.toMsg());
        return;
      }
    }
    catch (Throwable e) {
      sendRepMsg(e, response, "");
    }

  }
  /**
  * Insert the method's description here.
  * Creation date: (2001-12-25 11:27:38)
  * @param op com.icbc.cmis.operation.CmisOperation
  */
  public void processReply(HttpServlet httpServlet, String page, HttpServletRequest req, HttpServletResponse rep) throws Exception {
    try {
      if (page.trim().startsWith("DirectOutput")) {
        icbc.cmis.base.Trace.trace("CmisRequestHandler", 0, 0, "", "processReply page:DirectOutput");
        String encoding = (String)CmisConstance.getParameterSettings().get("encoding");
        rep.setContentType("text/html; charset=" + encoding);
        PrintWriter o = rep.getWriter();
        o.println(page.substring(12, page.length()));
        o.flush();
        o.close();
        return;
      }
      else {
        icbc.cmis.base.Trace.trace("CmisRequestHandler", 0, 0, "", "processReply page:" + page);
        ServletContext servletcontext = httpServlet.getServletContext();
        RequestDispatcher requestdispatcher = servletcontext.getRequestDispatcher(page);
        requestdispatcher.forward(req, rep);
        return;
      }

    }
    catch (Throwable e) {
      sendRepMsg(e, rep, page);
      return;

    }

  }
  private void sendRepMsg(Throwable e, HttpServletResponse rep, String page) {
    try {

      String webBasePath = (String)CmisConstance.getParameterSettings().get("webBasePath");
      String retStr = getThrowableStackMsg(e);
      rep.setContentType("text/html; charset=GBK");
      PrintWriter o = rep.getWriter();
      o.println("<html><head><title>工商银行信贷台账错误信息</title>");
      o.println("<link rel=\"stylesheet\" href=\"" + webBasePath + "/css/main.css\" type=\"text/css\"></head>");
      o.println(
        "<script language=\"javaScript\">	function showMsg(){	 if(document.all.errorDetialMsg.style.display==\"\"){ 	document.all.errorDetialMsg.style.display=\"none\";	 document.all.btn.innerHTML=\">>>>>>>...\";	 }else{	document.all.errorDetialMsg.style.display=\"\"; document.all.btn.innerHTML=\"<<<<<<<...\";}	}</script>");
      o.println("<body><div align=\"center\"><cmis:tabpage width = \"500\" height=\"300\"><cmis:tabpagebar title = \"失败\" selected=\"true\" />");
      o.println("<cmis:tabpagecontent><table width='100%' border='0' cellspacing='0' cellpadding='0'>");
      o.println(
        "<tr><td width='20%'><img src='" + webBasePath + "/images/stop.gif' width='80' height='80'></td><td width='80%'><h2><br>交易处理失败!</h2>");
      o.println("错误编码：CmisRequestHandler001<br>");
      o.println("错误描述：此错误未在系统中登记，交易处理失败。请点击返回按钮返回重试,如果重试不成功,请联系系统管理员帮助解决。<br><br>");
      o.println(
        "</td></tr><tr><td align='right'>&nbsp;&nbsp;&nbsp;</td><br><td align=\"center\"><a href=\"javascript:showMsg()\"<label id=\"btn\">>>>>>>...</label></a></td></tr><tr><td></td>");
      o.println("<td><label id=\"errorDetialMsg\" style=\"display:none\">");
      o.println("出错位置：CmisRequestHandler.sendRepMsg(.....)<BR>");
      o.println("出错内容：");
      o.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系统解析返回页面失败,页面如下:");
      o.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{" + page + "}<br>");
      o.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;异常信息：");
      o.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + retStr);
      o.println("</label></td></tr><tr><td colspan=\"2\"><div align=\"center\"><p><br><br><br><br><br></p><p>");
      o.println(
        "<a><img  border=\"0\" src=\""
          + webBasePath
          + "/images/return.gif\" width=\"48\" height=\"24\"  onclick=\"javascript:history.back();\"></a>&nbsp;&nbsp;&nbsp;<br><br></p>");
      o.println("</div></td></tr></table></cmis:tabpagecontent></cmis:tabpage></div>");
      o.println("<br><hr><div align=\"center\">&copy; Copyright 中国工商银行 2001.12 </div><br><br><br>");
      o.println("</body></html>");
      o.flush();
      o.close();
    }
    catch (Exception ee) {
      e.printStackTrace();
    }
  }

  private String getExceptionStackMsg(Exception e) {
    try {
      if (e == null)
        return "";
      java.io.StringWriter stringWriter = new java.io.StringWriter();
      java.io.PrintWriter print = new java.io.PrintWriter(stringWriter);
      e.printStackTrace(print);
      return new String(stringWriter.getBuffer());
    }
    catch (Exception ee) {
      return e.toString();
    }
  }

  private String getThrowableStackMsg(Throwable e) {
    try {
      if (e == null)
        return "";
      java.io.StringWriter stringWriter = new java.io.StringWriter();
      java.io.PrintWriter print = new java.io.PrintWriter(stringWriter);
      e.printStackTrace(print);
      return new String(stringWriter.getBuffer());
    }
    catch (Exception ee) {
      return e.toString();
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (2001-12-25 10:36:05)
   */
  private Class createOperation(String opName) throws TranFailException, Exception {

    Class c = null;
    String opPackage = "";
    String tmpOpName = "";
    opName = opName.trim();
    c = (Class)CmisConstance.getOperationClass(opName);

    if (c == null && opName.indexOf(".") == -1) {
      opPackage = (String)CmisConstance.getParameterSettings().get("operationPackages");
      StringTokenizer toke = new StringTokenizer(opPackage, "|");
      while (toke.hasMoreElements()) {
        String tmpStr = ((String)toke.nextElement()).trim() + "." + opName;
        c = (Class)CmisConstance.getOperationClass(tmpStr);
        if (c == null) {
          continue;
        }
        else {
          tmpOpName = tmpStr;
          break;
        }
      }
    }
    else {
      tmpOpName = opName;
    }

    if (c != null) {
      return c;
    }
    else {
      Exception ecl = null;
      try {
        c = (Class)Class.forName(opName);
        if (c != null)
          tmpOpName = opName;
      }
      catch (ClassNotFoundException e) {
        ecl = e;
      }
      if ((ecl != null || c == null) && opName.indexOf(".") == -1) {
        c = null;
        opPackage = (String)CmisConstance.getParameterSettings().get("operationPackages");
        StringTokenizer toke = new StringTokenizer(opPackage, "|");
        while (c == null && toke.hasMoreTokens()) {
          try {
            String tmpStr = ((String)toke.nextElement()).trim() + "." + opName;
            c = (Class)Class.forName(tmpStr);
            if (c != null)
              tmpOpName = tmpStr;
          }
          catch (ClassNotFoundException ee) {}
        }
      }
      if (c == null) {
        icbc.cmis.base.Trace.trace("CmisRequestHandler", 0, 0, "", "OperatinName:" + opName);
        throw new TranFailException(
          "xdtz22119",
          "CmisRequestHandler.createOperation(String opName)",
          "未找到交易处理模块[" + opName + "] in packages [" + opPackage + "]");
      }
      try {
        icbc.cmis.base.Trace.trace("CmisRequestHandler", 0, 0, "", "OperatinName:" + tmpOpName);
        CmisConstance.addCmisOperation(tmpOpName, c);
        return c;
      }
      catch (Exception ex) {

        throw new TranFailException("xdtz22120", "CmisRequestHandler.createOperation(String opName)", ex.getMessage());
      }
    }

  }

  /**
   * Insert the method's description here.
   * Creation date: (2001-12-25 10:17:16)
   * @return com.icbc.cmis.base.KeyedDataCollection
   * @param reqest javax.servlet.http.HttpServletRequest
   */
  private KeyedDataCollection parseRequestData(HttpServletRequest req, CMisSessionMgr mgr) throws TranFailException {
    try {
      boolean mark = false;
      String opDataUnclear = null;
      try {
        opDataUnclear = (String)req.getParameter("opDataUnclear");
      }
      catch (Exception eo) {}
      if (opDataUnclear == null)
        opDataUnclear = "ICBC_ECC";
      KeyedDataCollection kColl = (KeyedDataCollection)mgr.getSessionData("operationSessionData");
      if (kColl == null)
        kColl = new KeyedDataCollection();
      else {
        mark = true;
        String opName = (String)req.getParameter("operationName");
        String opName1 = (String)kColl.getValueAt("operationName");
        if (!opName.trim().equals(opName1.trim()) && !opDataUnclear.trim().equals("true")) {
          mark = false;
          mgr.removeSessionData("operationSessionData");
          kColl = null;
          kColl = new KeyedDataCollection();
        }
        else {
          mgr.removeSessionData("operationSessionData");

          if (opDataUnclear.trim().equals("true")) {
            try {
              kColl.addElement("oldOperationName", opName1);
            }
            catch (Exception eo) {
              kColl.setValueAt("oldOperationName", opName1);
            }
          }
          else if (opDataUnclear.trim().equals("false")) {
            mark = false;
            kColl = null;
            kColl = new KeyedDataCollection();
          }
        }

      }

      for (Enumeration enumeration = req.getParameterNames(); enumeration.hasMoreElements();) {
        String attrName = (String)enumeration.nextElement();
        String attrValue[] = (String[])req.getParameterValues(attrName);

        if (attrValue == null) {
          attrValue = new String[1];
          attrValue[0] = "";
        }
        for (int i = 0; i < attrValue.length; i++) {
          attrValue[i] = icbc.cmis.util.Decode.decode(attrValue[i]);
        }

        if (!mark) {
          if (attrValue.length == 1)
            kColl.addElement(attrName, attrValue[0]);
          else
            kColl.addElement(attrName, attrValue);

        }
        else {
          try {
            if (attrValue.length == 1)
              kColl.setValueAt(attrName, attrValue[0]);
            else
              kColl.setValueAt(attrName, attrValue);
          }
          catch (CTEObjectNotFoundException ect) {
            if (attrValue.length == 1)
              kColl.addElement(attrName, attrValue[0]);
            else
              kColl.addElement(attrName, attrValue);

          }
        }
      }
      return kColl;

    }
    catch (Exception e) {
      throw new TranFailException("xdtz22111", "CmisRequestHandler.parseRequestData(HttpServletRequest req)", e.getMessage());
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (2001-12-25 10:36:05)
   */
  public void processRequest(HttpServlet httpServlet, HttpServletRequest req, HttpServletResponse rep, CMisSessionMgr mgr) {
    String opName = null;

    try {
      opName = (String)req.getParameter("operationName");

      Class c = createOperation(opName);
      opName = c.getName().toString();

      CmisConstance.isValidRuningStatus(opName, req);
      String replayPage = "";
      try {
        CmisOperation operation = (CmisOperation)c.newInstance();
        operation.setOperationName(opName);

        KeyedDataCollection opData = (KeyedDataCollection)parseRequestData(req, mgr);
        operation.setOperationData(opData);

        try {
          operation.setServerAddr(req.getServerName() + ":" + req.getServerPort(), req.getRemoteAddr());
        }
        catch (Exception eee) {}

        operation.setSessionMgr(mgr);
        operation.run();
        req.setAttribute("operationData", operation.getOperationData());
        replayPage = (String)operation.getReplyPage();
      }
      catch (Exception ex) {
        throw ex;
      }
      finally {
        CmisConstance.resetRuningStatus(opName, req);
      }
      processReply(httpServlet, replayPage, req, rep);
    }
    catch (Exception e) {
      handleException(httpServlet, req, rep, e);
    }
  }
}
