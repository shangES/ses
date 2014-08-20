/*
 * $Id: FormTag.java,v 1.3 2001/10/26 00:02:44 ro89390 Exp $
 * Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 */

package icbc.cmis.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;

import java.io.IOException;
import java.util.*;

/**
 * A smart form.
 */
public class FormTag extends BodyTagSupport {

    Map validatedFields = new TreeMap();
    String name;
    String action;
    String method;
    String formHTML;

    public void putValidatedField(String fieldName, String fieldType) {
        validatedFields.put(fieldName, fieldType);
    }

    public void setName(String n) { name = n; }

    public void setAction(String a) { action = a; }

    public void setMethod(String m) { method = m; }

    public int doStartTag() throws JspTagException {
        return 100;
    }

    public int doAfterBody() throws JspTagException {
        BodyContent bc = getBodyContent();
        formHTML = bc.getString();
        bc.clearBody();
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException {
        try {
            StringBuffer html = new StringBuffer();
            StringBuffer javaScript = new StringBuffer();

            javaScript.append("<script language=\"JavaScript\">\n");
            javaScript.append("function validate() {\n");
            javaScript.append("    var ret = true;\n");
            for (Iterator it = validatedFields.keySet().iterator();
                 it.hasNext(); ) {
                String fieldName = (String) it.next();
                String fieldType = (String) validatedFields.get(fieldName);
                javaScript.append("    if (window.document.");
                javaScript.append(name + ".");
                javaScript.append(fieldName + ".");
                javaScript.append("value");
                javaScript.append(" ==");
                javaScript.append(" \"\"");
                javaScript.append(") {\n");
                javaScript.append("        alert(\"" + fieldName
                                  + " is empty.\");\n");
                javaScript.append("        ret = false;\n");
                javaScript.append("    }\n");
            }
            javaScript.append("    return ret;\n");
            javaScript.append("}\n");
            javaScript.append("</script>\n");

            html.append("<form");
            html.append(" name=\"" + name +"\"");
            html.append(" action=\"" + action + "\"");
            html.append(" method=\"" + method + "\"");
            html.append(" onSubmit=\"return validate();\"");
            html.append(">\n");
            html.append(formHTML);
            html.append("</form>");
            pageContext.getOut().print(javaScript.toString());
            pageContext.getOut().print(html.toString());
            return EVAL_PAGE;
        }
        catch (IOException e) {
            throw new JspTagException("FormTag: " + e.getMessage());
        }
    }
}
