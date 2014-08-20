/*
 * $Id: OptionTag.java,v 1.1 2001/10/19 20:50:21 ro89390 Exp $
 * Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 */

package icbc.cmis.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;

import java.io.IOException;
import java.util.*;

/**
 * HTML 'option' tag. Child of SelectTag.
 */
public class OptionTag extends BodyTagSupport {

    String value = "";

    public void setValue(String v) { value = v; }

    public int doAfterBody() throws JspTagException {
        SelectTag selectTag
            = (SelectTag) findAncestorWithClass(this, SelectTag.class);
        BodyContent bc = getBodyContent();
        String text = bc.getString();
        if (value != null && !value.trim().equals("")) {
            if (text != null && !text.trim().equals("")) {
                selectTag.putOption(value,
                                    (text != null && !text.trim().equals(""))
                                    ? text
                                    : value);
            }
            else {
                selectTag.putOption(value, value);
            }
        }
        bc.clearBody();
        return SKIP_BODY;
    }
}
