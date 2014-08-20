package icbc.cmis.tags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class FrameWorkTag extends TagSupport {
  private String align = "left";
  private String valign="top";


  public FrameWorkTag() {
  }

  public void setAlign(String align) {
    this.align = align;
  }

  public void setValign(String valign) {
    this.valign = valign;
  }

  public int doStartTag() throws javax.servlet.jsp.JspException {
    try {
      JspWriter out = this.pageContext.getOut();
      out.println("    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
      out.println("      <tr height=\"10\">");
      out.println("        <td width=\"10\"></td>");
      out.println("        <td></td>");
      out.println("        <td width=\"10\"></td>");
      out.println("      </tr>");
      out.println("      <tr>");
      out.println("        <td width=\"10\"></td>");
      out.println("        <td align=\""+align+"\" valign=\""+valign+"\">");
      out.println("<!--------------Frame's Content-------------------->");
    }
    catch (Exception ex) {
      throw new JspException("FrameWorkTag doInitBody() error." + ex.getMessage());
    }
    return EVAL_BODY_INCLUDE;
  }

  public int doEndTag() throws javax.servlet.jsp.JspException {
    try {
      JspWriter out = this.pageContext.getOut();
      out.println("<!-----------end of Frame's Content---------------->");
      out.println("        </td>");
      out.println("        <td width=\"10\"></td>");
      out.println("      </tr>");
      out.println("      <tr height=\"10\">");
      out.println("        <td width=\"10\"></td>");
      out.println("        <td></td>");
      out.println("        <td width=\"10\"></td>");
      out.println("      </tr>");
      out.println("    </table>");
    }
    catch (Exception ex) {
      throw new JspException("FrameWorkTag doAfterBody() error." + ex.getMessage());
    }
    return EVAL_PAGE;
  }
}