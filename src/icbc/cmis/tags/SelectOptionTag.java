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
import icbc.cmis.base.*;

/**
 * HTML 'option' tag. Child of SelectTag.
 */
public class SelectOptionTag extends BodyTagSupport {

    java.util.Hashtable h_table;
    DictBean bean = null;
    String value = "";
    String property = "";
    String datatype = "";
    private String id = null;

    public void setId(String id){
        this.id = id;
    }
    public void setProperty(String property){
        this.property = property;
    }

    public void setDataType(String datatype){
        this.datatype = datatype;
    }

    public int doStartTag() throws JspTagException {
       java.util.Hashtable h_table=CmisConstance.getDictParam();
       return SKIP_BODY;
    }

    public int doAfterBody() throws JspTagException {
        Vector retVect;
        String retValue="";
        SelectTag selectTag
            = (SelectTag) findAncestorWithClass(this, SelectTag.class);
        BodyContent bc = getBodyContent();
        String text = bc.getString();

        bean = (DictBean)h_table.get(this.property);
        retVect = bean.getKeys( );
    int j = retVect.size();
    for (int i = 0;i < j ;i++ ) {

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
}
        bc.clearBody();
        return SKIP_BODY;
    }
}
