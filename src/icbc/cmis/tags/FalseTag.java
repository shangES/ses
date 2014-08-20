/*
 * $Id: FalseTag.java,v 1.1 2001/10/12 00:53:55 ro89390 Exp $
 * Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 */

package icbc.cmis.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Tag showing its body if its parent (ConditionalTag) is false.
 */
public class FalseTag extends TagSupport {

    public int doStartTag() throws JspTagException {
        boolean value = ((ConditionalTag)
                         findAncestorWithClass(this, ConditionalTag.class))
            .getValue();
        return value ? SKIP_BODY : EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspTagException {
        return EVAL_PAGE;
    }
}
