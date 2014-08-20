/*
 * $Id: SumTag.java,v 1.1 2001/10/18 20:55:54 ro89390 Exp $
 * Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 */

package icbc.cmis.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;

import java.io.IOException;
import java.util.*;

/**
 * A sum of terms.
 *
 * Also see TermTag.
 */
public class SumTag extends BodyTagSupport {

    List terms = new ArrayList();

    public void add(String term) {
        terms.add(term);
    }

    public int doStartTag() throws JspTagException {
        return 100;
    }

    public int doEndTag() throws JspTagException {
        try {
            Iterator it = terms.iterator();
            int sum = 0;
            while (it.hasNext()) {
                sum += Integer.parseInt((String) it.next());
            }
            pageContext.getOut().print(sum);
            return EVAL_PAGE;
        }
        catch (IOException e) {
            throw new JspTagException("SumTag: " + e.getMessage());
        }
    }
}
