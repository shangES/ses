/*
 * $Id: SmartPropertyTag.java,v 1.4 2001/10/26 22:55:55 ro89390 Exp $
 * Copyright 2000 Sun Microsystems, Inc. All rights reserved.
 * Copyright 2000 Sun Microsystems, Inc. Tous droits r?erv?.
 */

package icbc.cmis.tags;

import java.lang.reflect.Method;
import java.io.IOException;

import java.util.Locale;
import java.beans.Beans;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * This class allows you to retrieve a parameter from the request and output it to the page
 * This should allow you to avoid expressions for extracting parameter info.
 *
 */

public class SmartPropertyTag extends TagSupport {

    private Object bean = null;
    private String id = null;
    private String property = null;
    private String format = null;
    private String localeString = null;
    private String numberFormatPattern = null;
    private String precisionString = null;
    private String scope = null;

    public SmartPropertyTag() {
        super();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setScope(String scope) {
        this.scope = scope;
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

    public int doStartTag() throws JspTagException {
        // check if beans tag is in list tag
        if (bean == null) {
            // try to find the bean in the scope
            if (scope.toLowerCase().equals("request")) {
                bean = pageContext.getRequest().getAttribute(id);
            } else if (scope.toLowerCase().equals("session")) {
                 bean = pageContext.getSession().getAttribute(id);
            } else if (scope.toLowerCase().equals("page")) {
                  bean = pageContext.getAttribute(id);
            }
            // otherwise create a new one and put it in the scope
            if (bean == null) {
                throw new JspTagException("SmartPropertyTag: Object not found: " + id);
            }
        }
       return SKIP_BODY;
    }

  public int doEndTag() throws JspTagException {
    // print out attribute
    try {
      JspWriter out = pageContext.getOut();
      String targetText = getText();
      if (format != null) targetText = formatText(targetText);
      out.print(targetText);
    } catch(IOException ioe) {
      System.err.println("SmartPropertyTag: Error printing attribute: " + ioe);
      throw new JspTagException("SmartPropertyTag: IO Error printing attribute.");
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
  private String getText() throws JspTagException {

      String targetMethod = null;
      if (property == null) targetMethod = "toString";
      else targetMethod = "get" + property.substring(0,1).toUpperCase() + property.substring(1,property.length());

      Object returnValue = null;
      try {
          // no arguments are needed
          Class[] args = {};
          Object[] params = {};
          Method m = bean.getClass().getMethod(targetMethod, args);
          if (m == null) {
              throw new JspTagException("SmartPropertyTag: There is no method by the name of " + targetMethod);
          }
          returnValue = m.invoke(bean,params);
        } catch ( java.lang.NoSuchMethodException ex) {
              throw new JspTagException("SmartPropertyTag: Method for property " + property + " not found.");
        } catch (java.lang.reflect.InvocationTargetException ex) {
              throw new JspTagException("SmartPropertyTag: Error calling method " + targetMethod + ":" + ex);
        } catch (java.lang.IllegalAccessException ex) {
              throw new JspTagException("SmartPropertyTag: Error calling method " + targetMethod + ":" + ex);
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
}

