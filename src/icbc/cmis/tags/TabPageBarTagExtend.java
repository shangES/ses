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

public class TabPageBarTagExtend extends TagSupport {
  private String webBasePath = (String)CmisConstance.getParameterSettings().get("webBasePath");      		
  private String title;
  private String url = "#";
  private String onclick;
  private boolean selected = false;
  private int ind = 1;  //use to get the images name
//  private static String[][] images =  {{"/icbc/cmis/images/p1.gif","/icbc/cmis/images/p4.gif"},
//                                       {"/icbc/cmis/images/p2.gif","/icbc/cmis/images/p5.gif"},
//                                       {"/icbc/cmis/images/p3.gif","/icbc/cmis/images/p6.gif"}
//                                       };
private static String[][] images =  {{"/images/p1.gif","/images/p4.gif"},
                                       {"/images/p2.gif","/images/p5.gif"},
                                       {"/images/p3.gif","/images/p6.gif"}
                                       };                                       
  private String selCondition = null;
  private String tabMark = null;
  private String blurFlag = null;
  public TabPageBarTagExtend() {
  }
public int doStartTag() throws javax.servlet.jsp.JspException {
    try {
        String tmpValue = null;
        try {
            tmpValue =
                (String)
                    (
                        (icbc.cmis.base.KeyedDataCollection) pageContext.getRequest().getAttribute(
                            "operationData")).getValueAt(
                    tabMark);
        } catch (Exception ee) {
            tmpValue = pageContext.getRequest().getParameter(tabMark);
        }
        if (tmpValue == null)
            tmpValue = "";
        if (selCondition == null)
            selCondition = "";
        if (blurFlag == null)
            blurFlag = "";
        if (selCondition.trim().equals(tmpValue.trim())
            || (blurFlag.trim().equals("0")
                && selCondition.trim().substring(0, 1).equals(tmpValue.substring(0, 1)))) {
            selected = true;
            ind = 0;
        } else {
            selected = false;
        }
        JspWriter out = this.pageContext.getOut();
        out.println(
            "      <td background=\""+webBasePath+  images[0][ind] + "\" width=\"12\"></td>");
        out.print(
            "        <td background=\""+webBasePath + images[1][ind] + "\" align=\"center\">");
        if (!selected) {
            out.print("<a href=\"" + url + "\" ");
            if (onclick != null) {
                out.print("onclick=\"" + onclick + "\" ");
            }
            out.print(">" + title + "</a>");
        } else {
            out.print(title);
        }
        out.println("</td>");
        out.println(
            "        <td width=\"12\" background=\""+webBasePath + images[2][ind] + "\"></td>");

    } catch (Exception ex) {
        throw new JspException(
            "TabPageBarTagExt doStartTag() error." + ex.getMessage());
    }
    return SKIP_BODY;
}
  public void setBlurFlag(String blurfalg) {
    this.blurFlag = blurfalg;
  }
  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }
  public void setSelCondition(String condition){
	  selCondition = condition;
  }
  public void setSelected(boolean b) {
    this.selected = b;
    if(this.selected) {
      ind = 0;
    }
  }
  public void setTabMarkName(String mark){
	  tabMark = mark;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public void setUrl(String url) {
    if(url != null) {
      this.url = url;
    }
  }
}
