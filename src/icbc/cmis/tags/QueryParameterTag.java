/*
 * $Id: QueryParameterTag.java,v 1.1 2001/10/18 20:55:54 ro89390 Exp $
 * Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 */

package icbc.cmis.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;

import java.io.IOException;
import java.util.*;

/**
 * A parameter for a link (i.e. a key-value pair in string such
 * as "?foo=yes&bar=5").
 */
public class QueryParameterTag extends BodyTagSupport {

    String name = "";

    public void setName(String n) { name = n; }

    public int doAfterBody() throws JspTagException {
        LinkTag linkTag
            = (LinkTag) findAncestorWithClass(this, LinkTag.class);
        BodyContent bc = getBodyContent();
        String value = bc.getString();
        if (name != null && !name.trim().equals("")
            && value != null && !value.trim().equals("")) {
            linkTag.putParameter(name, bc.getString());
        }
        bc.clearBody();
        return SKIP_BODY;
    }
}
