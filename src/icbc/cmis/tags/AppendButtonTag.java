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

public class AppendButtonTag extends TagSupport {


    private String href=null;
    private String imagesrc=null;
    private String onclick=null;
    private String title=null;
    private boolean confirmAdd = false;

    public AppendButtonTag() {
        super();
    }

    public void setHref(String href){
        this.href = href;
    }

    public void setImagesrc(String imagesrc){
        this.imagesrc = imagesrc;
    }

    public void setOnclick( String onclick ){
        this.onclick = onclick;
    }

    public void setconfirmadd( boolean confirm ) {
        this.confirmAdd = confirm;
    }

    public void settitle( String title ){
        this.title = title;
    }

    public String getHref( ){
        return this.href;
    }
    public String getImagesrc( ){
        return this.imagesrc;
    }
    public String getOnclick( ){
        return this.onclick;
    }
    public boolean getConfirm( ){
        return this.confirmAdd;
    }
    public String getTitle( ){
        return this.title;
    }

    public int doStartTag() throws JspTagException {
    return EVAL_BODY_INCLUDE;
    }
  public int doEndTag() throws javax.servlet.jsp.JspException {
    return EVAL_PAGE;
  }


}
