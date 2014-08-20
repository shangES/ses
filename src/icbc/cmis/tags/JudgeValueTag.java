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
public class JudgeValueTag extends ConditionalTag {

    protected boolean doTest(Object o) throws JspTagException {
        String trueValue=null;
        String realValue=null;
        KeyedDataCollection singleRec=null;
        trueValue = getTrueValue( );
        int rid=0;
        String tt=null;
        if (o == null) {
            return false;
        }
        else if (o instanceof String ) {
            if( trueValue.equals( o ))
               return true;
            else
               return false;
        }
        else if (o instanceof IndexedDataCollection ) {
        try {
             rid = Integer.parseInt(getRecid( ));
             singleRec =(KeyedDataCollection) ((IndexedDataCollection)o).getElement( rid );

             realValue = (String)singleRec.getValueAt(property);
        } catch (Exception ee) {
            throw new JspTagException("JudgeValueTag: Error getting, indexname:"+indexname+"recordID:" + rid + " property:"+ property+" from IndexedDataCollection");
        }
            if( trueValue.equals( realValue ))
               return true;
            else
               return false;
        }
        else {
            throw new JspTagException("JudgeValueTag: Not a Collection.");
        }
    }
}
