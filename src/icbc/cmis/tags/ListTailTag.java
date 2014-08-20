/*
 * $Id: ListTag.java,v 1.4 2001/10/26 22:55:55 ro89390 Exp $
 * Copyright 2000 Sun Microsystems, Inc. All rights reserved.
 * Copyright 2000 Sun Microsystems, Inc. Tous droits r?erv?.
 */

package icbc.cmis.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Collection;

import icbc.cmis.base.*;
import icbc.cmis.operation.*;

/**
 *
 * This class allows you to retrieve a parameter from the request and output it to the page
 * This should allow you to avoid expressions for extracting parameter info.
 *
 */

public class ListTailTag extends BodyTagSupport {
    private ListTag listtag;

    public void setListTag( ListTag list ) {
        this.listtag = list;
    }

    public ListTailTag() {
        super();
    }

    public int doStartTag() throws JspTagException {
    return EVAL_BODY_TAG;
    }

    public int doEndTag() throws JspTagException {
      JspWriter pageout = pageContext.getOut();
        try {
            BodyContent body = getBodyContent();
            if (body != null) {
                JspWriter out = body.getEnclosingWriter();
                out.print(body.getString());
            }
            listtag.writeListFooter( pageout, this );
        } catch(IOException ioe) {
            System.err.println("Error handling items tag: " + ioe);
        }
        return EVAL_PAGE;
    }


}
