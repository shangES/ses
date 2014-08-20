/*
 * $Id: ListIsEmptyTag.java,v 1.1 2001/10/18 00:46:05 ro89390 Exp $
 * Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 */

package icbc.cmis.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import java.util.Collection;
import icbc.cmis.base.*;

/**
 * A conditional tag returning whether a list is empty.
 */
public class ListIsEmptyTag extends ConditionalTag {

    private String returnPage=null;

    protected boolean doTest(Object o) throws JspTagException {
        int rows;
        if (o == null) {
            return true;
        }
        else if (o instanceof IndexedDataCollection) {
            rows = ((IndexedDataCollection) o).getSize();
            if( rows == 0 )
               return true;
            else
               return false;
        }
        else {
            throw new JspTagException("ListIsEmptyTag: Not a Collection.");
        }
    }

}

