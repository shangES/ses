// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   CheckboxTag.java

package icbc.cmis.tags;

import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

// Referenced classes of package icbc.cmis.tags:
//            FormTag

public class CheckboxTag extends BodyTagSupport
{

    private String value;
    private String name;
    private boolean checked;
    static Class class$icbc$cmis$tags$FormTag;

    public CheckboxTag()
    {
        checked = true;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    public int doStartTag()
        throws JspTagException
    {
        return 100;
    }

    public int doEndTag()
        throws JspTagException
    {
        try
        {
            FormTag tag = (FormTag)TagSupport.findAncestorWithClass(this, class$icbc$cmis$tags$FormTag != null ? class$icbc$cmis$tags$FormTag : (class$icbc$cmis$tags$FormTag = class$("icbc.cmis.tags.FormTag")));
            StringBuffer html = new StringBuffer();
            html.append("<input type=\"checkbox\"");
            html.append(name == null ? "" : String.valueOf(String.valueOf((new StringBuffer(" name=\"")).append(name).append("\""))));
            html.append(value == null ? "" : String.valueOf(String.valueOf((new StringBuffer(" value=\"")).append(value).append("\""))));
            if(!checked)
                html.append(" checked");
            html.append(">");
            pageContext.getOut().print(html.toString());
            byte byte0 = 6;
            return byte0;
        }
        catch(IOException e)
        {
            throw new JspTagException("CheckboxTag: ".concat(String.valueOf(String.valueOf(e.getMessage()))));
        }
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
