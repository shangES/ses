package icbc.cmis.tags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import icbc.cmis.base.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Title:        cmis
 * Description:  cmis3
 * Copyright:    Copyright (c) 2001
 * Company:      icbc
 * @author icbc
 * @version 1.0
 */

public class SessionCheckTag extends TagSupport {
  private String width = "100%";
  private String height = "100%";
  private String align = "left";
  private String valign="top";
  private String info = "";

  public SessionCheckTag() {
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public void setAlign(String align) {
    this.align = align;
  }

  public void setValign(String valign) {
    this.valign = valign;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public int doStartTag() throws javax.servlet.jsp.JspException {
    try {
      
	String webBasePath = (String)CmisConstance.getParameterSettings().get("webBasePath");      
      
      ServletRequest request = this.pageContext.getRequest();
      if(!CMisSessionMgr.checkSession((HttpServletRequest)request)){
        JspWriter out = this.pageContext.getOut();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>");
        out.println("工商银行信贷台账错误信息");
        out.println("</title>");
        out.println("<link rel=\"stylesheet\" href=\""+webBasePath+"/css/main.css\" type=\"text/css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<div align=\"center\">");
        out.println("<table width=\"500\" height=\"250\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        out.println("  <tr height=\"32\"> ");
        out.println("    <td background=\""+webBasePath+"/images/p8.gif\" width=\"7\"/>");
        out.println("    <td background=\""+webBasePath+"/images/p8.gif\" >");
        out.println("<!---------------------begin of tabpage bar-------------------------------->");
        out.println("    <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        out.println("      <tr height=\"32\">");
        out.println("");
        out.println("      <td background=\""+webBasePath+"/images/p1.gif\" width=\"12\"></td>");
        out.println("        <td background=\""+webBasePath+"/images/p2.gif\" align=\"center\">失败</td>");
        out.println("        <td width=\"12\" background=\""+webBasePath+"/images/p3.gif\"></td>");
        out.println("");
        out.println("        <td background=\""+webBasePath+"/images/p7.gif\" width=\"12\"></td>");
        out.println("        <td></td>");
        out.println("      </tr>");
        out.println("    </table>");
        out.println("<!---------------------end of tabpage bar-------------------------------->");
        out.println("    </td>");
        out.println("    <td background=\""+webBasePath+"/images/p8.gif\" width=\"7\"></td>");
        out.println("    <td width=\"7\"><img src=\""+webBasePath+"/images/p9.gif\" width=\"7\" height=\"32\"></td>");
        out.println("  </tr>");
        out.println("  <tr height=\"7\"> ");
        out.println("    <td width=\"7\" class=\"td1\"></td>");
        out.println("    <td class=\"td1\"></td>");
        out.println("    <td width=\"7\" class=\"td1\"></td>");
        out.println("    <td width=\"7\" background=\""+webBasePath+"/images/p11.gif\"></td>");
        out.println("  </tr>");
        out.println("  <tr> ");
        out.println("    <td width=\"7\" class=\"td1\"></td>");
        out.println("    <td align=\"left\" valign=\"top\">");
        out.println("<!---------------------begin of tabpage content-------------------------------->");
        out.println("");
        out.println("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
        out.println("  <tr>");
        out.println("      <td width='20%'><img src='"+webBasePath+"/images/stop.gif' width='80' height='80'></td>");
        out.println("      <td width='80%'>");
        out.println("        <h2><br>");
        out.println("          请重新登录!</h2>");
        out.println("        Session已超时，请重新登录。<BR>");
        out.println("    </td>");
        out.println("  </tr>");
        out.println("    <tr valign=\"bottom\"> ");
        out.println("      <td colspan=\"2\"> ");
        out.println("        <div align=\"right\"> ");
        out.println("          <br>");
        out.println("            <br>");
        out.println("            <br>");
        out.println("            <br>");
        out.println("            <br>");
        out.println("            <br>");
        out.println("            <br>");
        out.println("          <a href=\""+webBasePath+"/qslogin.jsp\" target=\"_top\"><img src=\""+webBasePath+"/images/login.gif\" width=\"48\" height=\"24\" border=\"0\"></a>");
        out.println("          &nbsp;&nbsp;&nbsp;<br><br>");
        out.println("          ");
        out.println("      </div>");
        out.println("    </td>");
        out.println("  </tr>");
        out.println("</table>");
        out.println("<!---------------------end of tabpage content-------------------------------->");
        out.println("    </td>");
        out.println("    <td width=\"7\" class=\"td1\"></td>");
        out.println("    <td width=\"7\" background=\""+webBasePath+"/images/p11.gif\"></td>");
        out.println("  </tr>");
        out.println("  <tr height=\"7\"> ");
        out.println("    <td width=\"7\"   class=\"td1\"><img src=\""+webBasePath+"/images/p10.gif\" width=\"7\" height=\"7\"></td>");
        out.println("    <td class=\"td1\"></td>");
        out.println("    <td width=\"7\"   class=\"td1\"><img src=\""+webBasePath+"/images/p10.gif\" width=\"7\" height=\"7\"></td>");
        out.println("    <td width=\"7\"><img src=\""+webBasePath+"/images/p12.gif\"></td>");
        out.println("  </tr>");
        out.println("</table>");
        out.println("</div>");
        out.println("<br>");
        out.println("<hr>");
        out.println("<div align=\"center\">&copy; Copyright 中国工商银行 2001.12 </div>");
        out.println("<br>");
        out.println("<br>");
        out.println("<br>");
        out.println("</body>");
        out.println("</html>");
        return SKIP_BODY;
      }
    } catch (Exception ex) {
      throw new JspException("FrameWorkTag doInitBody() error." + ex.getMessage());
    }
    return EVAL_BODY_INCLUDE;
  }

  public int doEndTag() throws javax.servlet.jsp.JspException {
    return EVAL_PAGE;
  }
}