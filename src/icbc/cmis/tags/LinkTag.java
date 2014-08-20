/*
 * $Id: LinkTag.java,v 1.3 2001/10/26 00:02:44 ro89390 Exp $
 * Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 */

package icbc.cmis.tags;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;

import java.io.IOException;
import java.util.*;

/**
 * A link (i.e. &lt;a href=...&gt; and &lt;/a&gt;).
 *
 * Also see LinkContentTag and QueryParameterTag.
 */
public class LinkTag extends BodyTagSupport {

    Map parameters = new TreeMap();
    String linkContent = "";
    String href = "";

    public void setHref(String h) { href = h; }

    public void setLinkContent(String lc) { linkContent = lc; }

    public void putParameter(String name, String value) {
        parameters.put(name, value);
    }

    public int doStartTag() throws JspTagException {
        return 100;
    }

    public int doEndTag() throws JspTagException {
        try {
            StringBuffer html = new StringBuffer();
            html.append("<a href=\"");

            StringBuffer url = new StringBuffer();
            url.append(href);
            Iterator it = parameters.keySet().iterator();
            if (it.hasNext()) {
                url.append("?");
                String name = (String) it.next();
                url.append(name);
                url.append("=");
                url.append(parameters.get(name));
                while (it.hasNext()) {
                    url.append("&");
                    name = (String) it.next();
                    url.append(name);
                    url.append("=");
                    url.append(parameters.get(name));
                }
            }

            html.append(((HttpServletResponse)
                         pageContext.getResponse())
                        .encodeURL(url.toString()));
            html.append("\">");
            html.append(linkContent);
            html.append("</a>");
            pageContext.getOut().print(html.toString());
            return EVAL_PAGE;
        }
        catch (IOException e) {
            throw new JspTagException("LinkTag: " + e.getMessage());
        }
    }
}
