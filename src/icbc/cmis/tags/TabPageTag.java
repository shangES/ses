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

public class TabPageTag extends TagSupport {
  private String width = "100%";
  private String height = "100%";

  public TabPageTag() {
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public int doStartTag() throws javax.servlet.jsp.JspException {

    try {
    String webBasePath = (String)CmisConstance.getParameterSettings().get("webBasePath");      	  	  	
      JspWriter out = this.pageContext.getOut();
      out.println("<table width=\""+width+"\" height=\""+height+"\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
      out.println("  <tr height=\"32\"> ");
      out.println("    <td background=\""+webBasePath+"/images/p8.gif\" width=\"7\"/>");
      out.println("    <td background=\""+webBasePath+"/images/p8.gif\" >");
      out.println("<!---------------------begin of tabpage bar-------------------------------->");
      out.println("    <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
      out.println("      <tr height=\"32\">");
    } catch (Exception ex) {
      throw new JspException("FrameWorkTag doInitBody() error." + ex.getMessage());
    }
    return EVAL_BODY_INCLUDE;
  }

  public int doEndTag() throws javax.servlet.jsp.JspException {
    return EVAL_PAGE;
  }
}