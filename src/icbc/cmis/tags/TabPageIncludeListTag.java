package icbc.cmis.tags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import icbc.cmis.base.CmisConstance;

/**
 * Title:        cmis
 * Description:  cmis3
 * Copyright:    Copyright (c) 2001
 * Company:      icbc
 * @author icbc
 * @version 1.0
 */

public class TabPageIncludeListTag extends TagSupport {
  private String webBasePath = (String)CmisConstance.getParameterSettings().get("webBasePath");	
  private String width = "100%";
  private String height = "100%";
  private String align = "left";
  private String valign="top";
  private String info = "";

  public TabPageIncludeListTag() {
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
       ListInfoTag listInfoTag
            = (ListInfoTag) findAncestorWithClass(this, ListInfoTag.class);
      if (listInfoTag != null)
            this.info = this.info + listInfoTag.getListInfo( );

      JspWriter out = this.pageContext.getOut();
      out.println("        <td background=\""+webBasePath+"/images/p7.gif\" width=\"12\"></td>");
      out.println("        <td>" + info + "</td>");
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
      out.println("    <td align=\""+align+"\" valign=\""+valign+"\">");
      out.println("<!---------------------begin of tabpage content-------------------------------->");
    } catch (Exception ex) {
      throw new JspException("FrameWorkTag doInitBody() error." + ex.getMessage());
    }
    return EVAL_BODY_INCLUDE;
  }

  public int doEndTag() throws javax.servlet.jsp.JspException {
    try {
      JspWriter out = this.pageContext.getOut();
      out.println("<!---------------------end of tabpage content-------------------------------->");
      out.println("    </td>");
      out.println("    <td width=\"7\" class=\"td1\"></td>");
      out.println("    <td width=\"7\" background=\""+webBasePath+"/images/p11.gif\"></td>");
      out.println("  </tr>");
      out.println("  <tr height=\"7\"> ");
      //out.println("    <td width=\"7\"  class=\"td1\"></td>");
      out.println("    <td width=\"7\"   class=\"td1\"><img src=\""+webBasePath+"/images/p10.gif\" width=\"7\" height=\"7\"></td>");
      out.println("    <td class=\"td1\"></td>");
      out.println("    <td width=\"7\"   class=\"td1\"><img src=\""+webBasePath+"/images/p10.gif\" width=\"7\" height=\"7\"></td>");
      out.println("    <td width=\"7\"><img src=\""+webBasePath+"/images/p12.gif\"></td>");
      out.println("  </tr>");
      out.println("</table>");
    }
    catch (Exception ex) {
      throw new JspException("FrameWorkTag doAfterBody() error." + ex.getMessage());
    }
    return EVAL_PAGE;
  }
}