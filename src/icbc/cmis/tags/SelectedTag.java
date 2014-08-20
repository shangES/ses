/*
 * $Id: SelectedTag.java,v 1.1 2001/10/19 20:50:21 ro89390 Exp $
 * Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 */

package icbc.cmis.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;

import java.io.IOException;
import java.util.*;

/**
 * Defines what should be selected for an 'option' tag.
 */
public class SelectedTag extends BodyTagSupport {
    public int doAfterBody() throws JspTagException {
        SelectTag tag
            = (SelectTag) findAncestorWithClass(this, SelectTag.class);
        BodyContent bc = getBodyContent();
        String selectedValue = bc.getString();
        if (selectedValue != null && !selectedValue.trim().equals("")) {
            tag.setSelectedValue(selectedValue);
        }
        bc.clearBody();
        return SKIP_BODY;
    }
}
