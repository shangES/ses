// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ListFooterTag.java

package icbc.cmis.tags;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.TagSupport;

// Referenced classes of package icbc.cmis.tags:
//            ListTag, Debug

public class ListFooterTag extends TagSupport
{

    private String action;
    private String restatus;
    private ListTag listTag;
    static Class class$icbc$cmis$tags$ListTag;

    public ListFooterTag()
    {
        action = null;
        restatus = null;
        listTag = null;
    }

    public int doStartTag()
        throws JspTagException
    {
        listTag = (ListTag)TagSupport.findAncestorWithClass(this, class$icbc$cmis$tags$ListTag != null ? class$icbc$cmis$tags$ListTag : (class$icbc$cmis$tags$ListTag = class$("icbc.cmis.tags.ListTag")));
        if(listTag == null)
            throw new JspTagException("NextFormTag: nextForm tag not inside listtag");
        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        if(listTag.hasNextForm)
            try
            {
                JspWriter out = pageContext.getOut();
                out.print("<form name=\"formtag1\" method=\"GET\" action=\"/servlet/icbc.cmis.servlets.CmisReqServlet\" >");
                out.print(String.valueOf(String.valueOf((new StringBuffer("<input type=hidden name=\"ListTagBeginPosition\" value=\"")).append(listTag.getStartIndex()).append("\">"))));
                out.print("<input type=hidden name=\"opDataUnclear\" value=\"true\">");
                out.print("<input type=hidden name=\"operationName\" value=\"EB_PrimaryDealOP\">");
                out.print(String.valueOf(String.valueOf((new StringBuffer("<input type=hidden name=\"operationStatus\" value=\"")).append(restatus).append("\">"))));
                out.print("<input type=\"image\" border=\"0\" src=\"/icbc/cmis/images/next_page.gif\" value=\"Next\">");
                out.print("</form>");
            }
            catch(IOException ioe)
            {
                Debug.println("NextFormTag: error printing <form> tag");
            }
        return 1;
    }

    public int doEndTag()
    {
        return 6;
    }

    public void setaction(String action)
    {
        this.action = action;
    }

    public void setrestatus(String status)
    {
        restatus = status;
    }

    static Class class$(String x$0)
    {
        try
        {
            return Class.forName(x$0);
        }
        catch(ClassNotFoundException x$1)
        {
            throw new NoClassDefFoundError(x$1.getMessage());
        }
    }
}
