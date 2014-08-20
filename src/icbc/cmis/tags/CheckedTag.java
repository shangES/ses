// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   CheckedTag.java

package icbc.cmis.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.*;

// Referenced classes of package icbc.cmis.tags:
//            CheckboxTag

public class CheckedTag extends BodyTagSupport
{

    static Class class$icbc$cmis$tags$CheckboxTag;

    public CheckedTag()
    {
    }

    public int doAfterBody()
        throws JspTagException
    {
        CheckboxTag tag = (CheckboxTag)TagSupport.findAncestorWithClass(this, class$icbc$cmis$tags$CheckboxTag != null ? class$icbc$cmis$tags$CheckboxTag : (class$icbc$cmis$tags$CheckboxTag = class$("icbc.cmis.tags.CheckboxTag")));
        BodyContent bc = getBodyContent();
        String selectedValue = bc.getString();
        if(selectedValue != null && !selectedValue.trim().toLowerCase().equals("true"))
            tag.setChecked(true);
        else
            tag.setChecked(false);
        bc.clearBody();
        return 0;
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
