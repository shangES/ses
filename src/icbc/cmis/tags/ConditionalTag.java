/*
 * $Id: ConditionalTag.java,v 1.2 2001/10/18 00:46:06 ro89390 Exp $
 * Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 */

package icbc.cmis.tags;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.*;
import javax.servlet.http.HttpServletRequest;

import java.util.Collection;
import icbc.cmis.base.*;

/**
 * A conditional tag.
 *
 * &lt;waf:conditional id=&quot;id&quot; scope=&quot;scope&quot;&gt;
 *   &lt;waf:true&gt;blah&lt;/waf:true&gt;
 *   &lt;waf:false&gt;blah&lt;/waf:false&gt;
 * &lt;/waf:conditional&gt;
 */
public class ConditionalTag extends TagSupport {

    boolean value;

    private KeyedDataCollection context;
    String id="operationData";
    String indexname="";
    String property;
    String scope="request";
    String trueString =null;
    String falseString =null;
    public String recordID= null ;
    private String defaultRecordid = "0";
    private icbc.cmis.base.CMisSessionMgr sm;

    public void setId(String i) { id = i; }

    public void setIndexed(String idx) { indexname = idx; }

    public void setScope(String s) { scope = s; }

    public void setproperty(String t) { property = t; }

    public void settruestring(String t) { trueString = t; }

    public void setRecordid(String recordid ) {
        this.recordID = recordid;
    }

    public boolean getValue() { return value; }

    public String getTrueValue() { return trueString; }

    public String getIndexed() { return indexname; }

    public String getRecid()  throws Exception {
    String recID;
      try{
             if( recordID == null ){
                recID = defaultRecordid;
             }else if(elementExist(context, recordID)){
                recID =  (String)context.getValueAt(recordID);
                if( recID == null ) recID = defaultRecordid;
             }else{
                recID = defaultRecordid;
             }
      }catch(Exception ee){
            throw ee;
      }
             return recID;
    }

    public int doStartTag() throws JspTagException {
        Object o=null;

try{
    if (scope.equals("session")) {
         sm = new icbc.cmis.base.CMisSessionMgr((HttpServletRequest)pageContext.getRequest());
         o = sm.getSessionData(property);
    }else{
        if (scope.equals("request")) {
            context = (KeyedDataCollection)pageContext.getRequest().getAttribute(id);
        }
        else if (scope.equals("page")) {
            context = (KeyedDataCollection)pageContext.getAttribute(id);
        }
        else{
            context = (KeyedDataCollection)pageContext.getRequest().getAttribute(id);
        }
        if( indexname.length() != 0 ){
            if( context.isElementExist( indexname ))
               o = context.getElement(indexname);
        }else{
            if( context.isElementExist( property ))
               o = context.getValueAt(property);
        }
    }
  }catch(Exception ee){
      throw new JspTagException("ConditionalTag中取元素出错"+this.indexname );
  }

        value = doTest(o);

        return EVAL_BODY_INCLUDE;
    }

    protected boolean doTest(Object o) throws JspTagException {
        return false;
    }

    public int doEndTag() throws JspTagException { return EVAL_PAGE; }

  private boolean elementExist( KeyedDataCollection keyed, String elementName ){
         //从keyed中判断是否存在数据
          if(keyed.isElementExist(elementName))
             return true;
          else
             return false;
  }


}
