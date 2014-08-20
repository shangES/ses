/*
 * $Id: SmartPropertyTag.java,v 1.4 2001/10/26 22:55:55 ro89390 Exp $
 * Copyright 2000 Sun Microsystems, Inc. All rights reserved.
 * Copyright 2000 Sun Microsystems, Inc. Tous droits r?erv?.
 */

package icbc.cmis.tags;

import java.lang.reflect.Method;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import icbc.cmis.base.*;
import icbc.cmis.operation.*;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
/**
 *
 * This class allows you to retrieve a parameter from the request and output it to the page
 * This should allow you to avoid expressions for extracting parameter info.
 *
 */

public class SimplePropertyTag extends TagSupport {

    KeyedDataCollection context;
    private String id = "operationData";
    private String type = "keyed";
    private String recordID= null ;
    private String property = "";
    private String indexedName = "";
    private String format = null;
    private String localeString = null;
    private String numberFormatPattern = null;
    private String precisionString = null;
    private String scope = "request";
    private String defaultRecordid = "0";
    private icbc.cmis.base.CMisSessionMgr sm;

    public SimplePropertyTag() {
        super();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setRecordid(String recordid ) {
        this.recordID = recordid;
    }
    public void setIndexed(String indexed ) {
        this.indexedName = indexed;
    }
    /**
     * To be used when formatting currency
     *
     */
    public void setLocale(String localeString) {
        this.localeString = localeString;
    }

    /**
     * Support number format and currency format here.
     * When this is set ensure that the proper decile places are
     * supportted.
     *
     */
    public void setFormatText(String format) {
        this.format = format;
    }

    /**
     * Only used when formattting numbers
     *
     */
    public void setNumberFormatPattern(String numberFormatPattern) {
        this.numberFormatPattern = numberFormatPattern;
    }

    public void setPrecision(String precisionString) {
        this.precisionString = precisionString;
    }

    public void setProperty(String property){
        this.property = property;
    }

    public int doStartTag() throws JspTagException{

     if (scope.toLowerCase().equals("session")) {
         sm = new icbc.cmis.base.CMisSessionMgr((HttpServletRequest)pageContext.getRequest());
     }else{
        if (context == null) {
            // try to find the context in the scope
            if (scope.toLowerCase().equals("request")) {
                context = (KeyedDataCollection)pageContext.getRequest().getAttribute(id);
            } else if (scope.toLowerCase().equals("page")) {
                  context = (KeyedDataCollection)pageContext.getAttribute(id);
            }
            else
                context = (KeyedDataCollection)pageContext.getRequest().getAttribute(id);
            // otherwise create a new one and put it in the scope
            if (context == null) {
                throw new JspTagException("SmartPropertyTag: Object not found: " + id);
            }
        }
     }
       return 100;
    }

  public int doEndTag() throws JspTagException {
    // print out attribute
    try {
      JspWriter out = pageContext.getOut();
      String targetText = getText();
      if (format != null) targetText = formatText(targetText);
      out.print(targetText);
    } catch(IOException ioe) {
      System.err.println("SimplePropertyTag1: Error printing attribute: " + ioe);
      throw new JspTagException("SimplePropertyTag1: IO Error printing attribute.");
    }
    catch(Exception ioe) {
      System.err.println("SimplePropertyTag2: Error printing attribute: " + ioe);
      throw new JspTagException("SimplePropertyTag该元素不存在"+this.property);
    }
    return(EVAL_PAGE);
  }

  /**
   * Using the current Object use reflection to obtain the
   * String data from the element method the same as a JavaBean
   * would use:
   * <br><br>e.g. a getXXXX method which has no parameters
   * <br>    The target method is the property attribute
   *
   * The default method that is called is the toString method on the object.
   */
  private String getText() throws Exception {

      Object returnValue = null;


try{
      if (property == null) returnValue="";
      else
         returnValue=getValue( );

      if( returnValue == null ) returnValue="";
  }catch(Exception ee){
	  throw ee;
  }

        // do casting of Integers and Dobules here
        if (returnValue instanceof java.lang.String) {
            return (String)returnValue;
        } else if (returnValue instanceof java.lang.Integer) {
            return ((Integer)returnValue).toString();
        } else if (returnValue instanceof java.lang.Double){
            return ((Double)returnValue).toString();
        } else if (returnValue instanceof java.lang.Float) {
            return ((Float)returnValue).toString();
        } else {
            return returnValue + "";
        }
    }


    /**
     * Apply number formatting for the default locale the application is running in unless
     * specfied with the locale attribute.
     *
     * Apply the pattern if specified.
     *
     */
    private String formatText(String text) throws JspTagException {
        String formattedString = null;
        Locale locale = null;
        int precision = 2;

        //若为空值则返回
        if( text.equals( null ) || text.equals( ""))           return text;

        if (precisionString != null) {
            try {
                precision = Integer.parseInt(precisionString);
            } catch (java.lang.NumberFormatException ex) {
                // if this fails stick with the default;
                precision = 2;
            }

        }

        locale = I18nUtil.getLocaleFromString(localeString);
        if (locale == null) locale = Locale.getDefault();
        // use doubles for the number formatting
        double dub = 0;
        try {
            dub = Double.parseDouble(text);
        } catch (java.lang.NumberFormatException nex) {
            throw new JspTagException("SmartPropertyTag: Error converting : " + text + " to a double. Ensure it is a number.");
        }
        if (format.toLowerCase().equals("number")) {
            if (numberFormatPattern != null) formattedString = I18nUtil.formatNumber(dub,precision,numberFormatPattern, locale);
            else formattedString = I18nUtil.formatNumber(dub,precision,locale);
        } else if (format.toLowerCase().equals("currency")) {
            if (numberFormatPattern != null) formattedString = I18nUtil.formatCurrency(dub,precision,numberFormatPattern,locale);
            else formattedString = I18nUtil.formatCurrency(dub,precision,locale);
        } else {
            throw new JspTagException("SmartPropertyTag: Error extracting formatting text: Do not know format:>" + format + "<");
        }
        return formattedString;
    }

  private String getValue() throws Exception {
   String returnValue=null;
   String recID=null;
   IndexedDataCollection jspicoll = null;
   KeyedDataCollection jspkcoll = null;
   try{
         //从indexed集中取数据
         if (type.toLowerCase().equals("indexed")) {
             if(!elementExist(context, indexedName))
                return "";
             else
                jspicoll =  (IndexedDataCollection)context.getElement(indexedName);

             if( recordID == null ){
                recID =  defaultRecordid;
             }else if(elementExist(context, recordID)){
                recID =  (String)context.getValueAt(recordID);
                if( recID == null ) recID = defaultRecordid;
             }else{
                recID = defaultRecordid;
             }

             jspkcoll = (KeyedDataCollection)jspicoll.getElement( Integer.parseInt( recID ) );

             if(elementExist(jspkcoll, property))
                returnValue = (String)jspkcoll.getValueAt(property);
         } else if( type.toLowerCase().equals("keyed")){
             if (scope.toLowerCase().equals("session")) {
               return returnValue = (String)sm.getSessionData(property);
             }
             if(elementExist(context, property))
                returnValue =  (String)context.getValueAt(property);
             } else{
                if(elementExist(context, property))
                  returnValue =  (String)context.getValueAt(property);
         }
  }catch(Exception ee){
	  throw ee;
  }
  return returnValue;
}
  private boolean elementExist( KeyedDataCollection keyed, String elementName ){
         //从keyed中判断是否存在数据
          if(keyed.isElementExist(elementName))
             return true;
          else
             return false;
  }

}

