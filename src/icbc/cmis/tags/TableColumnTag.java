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

public class TableColumnTag extends TagSupport {


    private String name=null;
    private String value=null;

    public TableColumnTag() {
        super();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setvalue(String value){
        this.value = value;
    }

    public String getValue( ){
        return this.value;
    }

    public String getName( ){
        return this.name;
    }

    public int doStartTag() throws JspTagException {
    return EVAL_BODY_INCLUDE;
    }
  public int doEndTag() throws javax.servlet.jsp.JspException {
    return EVAL_PAGE;
  }


}
