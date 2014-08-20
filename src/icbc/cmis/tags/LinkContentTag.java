/*
 * $Id: LinkContentTag.java,v 1.1 2001/10/18 20:55:54 ro89390 Exp $
 * Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 */

package icbc.cmis.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;

import java.io.IOException;
import java.util.*;

/**
 * The content for a link (i.e. the stuff that goes in between
 * &lt;a href=...&gt; and &lt;/a&gt;).
 */
public class LinkContentTag extends BodyTagSupport {
    public int doAfterBody() throws JspTagException {
        LinkTag linkTag
            = (LinkTag) findAncestorWithClass(this, LinkTag.class);
        BodyContent bc = getBodyContent();
        linkTag.setLinkContent(bc.getString());
        bc.clearBody();
        return SKIP_BODY;
    }
}
