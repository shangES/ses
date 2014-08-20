//进行五级分类支行处理控制

package icbc.cmis.util;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.*;
import java.sql.*;
public class util_VBranchCheck extends HttpServlet {

  private static final String CONTENT_TYPE = "text/html; charset=GBK";


  private SqlTool tool = null;
  /**Initialize global variables*/
  public void init() throws ServletException {
  }
  /**Process the HTTP Get request*/
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request,response);
  }
  /**Process the HTTP Post request*/
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    String area_code="",deal_date="";
    area_code = request.getParameter("area_code");
    deal_date = request.getParameter("deal_date");

    try{
       branchCheck( area_code, deal_date, out );
    }
    catch (TranFailException ex) {
        outTime(out,"数据提取失败！" + ex.getErrorCode()+"" + ex.getErrorLocation()+"" + ex.getDisplayMessage()+"" + ex.getErrorMsg()+"");
    }
    catch (Exception ex) {
        outTime(out,"数据提取失败！" + ex.getMessage());
    }

  }
  /**Clean up resources*/
  public void destroy() {
  }
      //处理异常
   public void outTime(PrintWriter out,String info) {
    out.println("<?xml version=\"1.0\"?>");
    out.println("<error>");
    out.println("<errorcode>"+info+"</errorcode>");
    out.println("<errormsg>"+info+"</errormsg>");
    out.println("</error>");
  }

  public void branchCheck( String area_code,String deal_date,PrintWriter out)throws TranFailException{
    String out_flag="",out_mesg="";
    try{
      KeyedDataCollection kInput = new KeyedDataCollection();
      kInput.addElement("v_area_code",area_code );
      kInput.addElement("v_deal_date",deal_date );
      Vector inParam = new Vector();
      inParam.add("v_area_code");
      inParam.add("v_deal_date");
      Vector outParam = new Vector();
      outParam.add("v_out_flag");
      outParam.add("v_out_mesg");
      KeyedDataCollection kResult = new KeyedDataCollection();
      kResult.setName("procResult");

     icbc.cmis.service.JDBCProcedureService srv = new icbc.cmis.service.JDBCProcedureService(new icbc.cmis.operation.DummyOperation());
     srv.getConn();
     int succMark = srv.executeProcedure("pack_commontools.proc_branchcheck",kInput,inParam,outParam,kResult);
     out_flag = (String)kResult.getValueAt("v_out_flag");
     out_mesg = (String)kResult.getValueAt("v_out_mesg");
     srv.closeConn();
     out.println("<?xml version=\"1.0\" encoding=\"GBK\"?>");
     out.print("<datas out_flag='"+out_flag+"' out_mesg='"+out_mesg+"' >");
     out.print("</datas>");
     return;

    }
     catch(TranFailException ee){
      if(tool!=null)
         tool.closeconn();
      throw ee;
    }
    catch(Exception ex){
      if(tool!=null)
         tool.closeconn();
      throw new TranFailException("xdtz0FFE666","util_VBranchCheck.proc_sx()",ex.getMessage(),"");
    }
  }



}