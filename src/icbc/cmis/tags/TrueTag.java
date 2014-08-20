/*
 * $Id: TrueTag.java,v 1.1 2001/10/12 00:53:55 ro89390 Exp $
 * Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 */

package icbc.cmis.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Tag showing its body if its parent (ConditionalTag) is true.
 */
public class TrueTag extends TagSupport {

    public int doStartTag() throws JspTagException {
        boolean value = ((ConditionalTag)
                         findAncestorWithClass(this, ConditionalTag.class))
            .getValue();
        return value ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

    public int doEndTag() throws JspTagException {
        return EVAL_PAGE;
    }
}
