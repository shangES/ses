package icbc.cmis.util;

import icbc.cmis.base.CMisSessionMgr;
import icbc.cmis.base.TranFailException;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 	1.0
 * @author
 */
public class ChooseArea2 extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=GBK";
	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
            doPost(request,response);
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
            response.setContentType(CONTENT_TYPE);
                PrintWriter out = response.getWriter();
                CMisSessionMgr session = new CMisSessionMgr(request);

                try {
                  if(!CMisSessionMgr.checkSession(request)){
                    outTime(out,"  当前会话已失效，请重新登录");
                    return;
                  }

                  String area = request.getParameter("area");
                  String range = request.getParameter("range");
                  String customercode = request.getParameter("customercode");
                  ChooseAreaDAO dao = new ChooseAreaDAO(new icbc.cmis.operation.DummyOperation());
                  String xml = dao.getArea2(area,range,customercode);

                  out.println("<?xml version=\"1.0\" encoding=\"GBK\"?>");
                  //out.println("<?xml version=\"1.0\"?>");
                  out.print(xml);
                }
                catch (TranFailException ex) {
                    outTime(out,"数据提取失败！" + ex.getErrorCode()+"" + ex.getErrorLocation()+"" + ex.getDisplayMessage()+"" + ex.getErrorMsg()+"");
                }
                catch (Exception ex) {
                    outTime(out,"数据提取失败！" + ex.getMessage());
                }
	}
    public void outTime(PrintWriter out,String info) {
        out.println("<?xml version=\"1.0\"?>");
        out.println("<error>");
        out.println(info);
        out.println("</error>");
      }
}
