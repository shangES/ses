/*
 * $Id: BooleanTag.java,v 1.2 2001/10/21 04:46:04 gmurray Exp $
 * Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 */

package icbc.cmis.tags;

import javax.servlet.jsp.JspTagException;

/**
 * A conditional tag returning whether a Boolean or String
 * representing a boolean is true.
 */
public class BooleanTag extends ConditionalTag {

    protected boolean doTest(Object o) throws JspTagException {
        if (o == null) {
            return false;
        }
        else if (o instanceof String) {
            return Boolean.valueOf((String) o).booleanValue();
        }
        else if (o instanceof Boolean) {
            return ((Boolean) o).booleanValue();
        }
        else {
            throw new JspTagException("ConditionalTag: Not a String or Boolean.");
        }
    }

    public void setProperty() {

    }

    private void testProperty() {

    }
}
