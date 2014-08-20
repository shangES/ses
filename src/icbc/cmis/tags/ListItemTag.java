/*
 * $Id: ListItemTag.java,v 1.5 2001/10/26 22:55:54 ro89390 Exp $
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
/**
 *
 * This class allows you to retrieve a parameter from the request and output it to the page
 * This should allow you to avoid expressions for extracting parameter info.
 *
 */

public class ListItemTag extends TagSupport {

    private ListTag listTag;
    private KeyedDataCollection item = null;
    private String property = null;
    private String format = null;
    private String localeString = null;
    private String numberFormatPattern = null;
    private String precisionString = null;
    boolean isRowid = false;
    private  int currentRowid;
    private  int currentRowid4show;
    boolean isRowid4show = false;

    public ListItemTag() {
        super();
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

    public void setisrowid(boolean r) { isRowid = r; }

    public void setisrowid4show(boolean r) { isRowid4show = r; }

    public int doStartTag() throws JspTagException {
        // check if items tag is in list tag
        listTag = (ListTag) findAncestorWithClass(this, ListTag.class);

        if (listTag == null) {
          throw new JspTagException("ListItemTag: ListItem tag not inside items tag");
        }

        if( isRowid || isRowid4show )
          return SKIP_BODY;
        else{
          item = (KeyedDataCollection)listTag.getCurrentItem();
        }

        if (item == null) {
          throw new JspTagException("ListItemTag: There is no item to list.");
        }
        return SKIP_BODY;
    }

  public int doEndTag() throws JspTagException {
    // print out attribute
    try {
      JspWriter out = pageContext.getOut();
      String targetText = getText();
      if (format != null) targetText = formatText(targetText);
      if( targetText.equals("") || targetText.equals(null))
         targetText = "&nbsp";

      out.print(targetText);
    } catch(IOException ioe) {
      System.err.println("ListItemTag: Error printing attribute: " + ioe);
      throw new JspTagException("ListItemTag: IO Error printing attribute.");
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

      if (property == null && !isRowid && !isRowid4show )
              throw new JspTagException("ListItemTag: property null deny");

      Object returnValue = null;
      currentRowid4show = listTag.getCurrentID();
      currentRowid = currentRowid4show - 1;
      try {
          // no arguments are needed
          if( isRowid4show )
             returnValue = String.valueOf(currentRowid4show).trim();
          else if( isRowid )
             returnValue = String.valueOf(currentRowid).trim();
          else//
             returnValue = ((String)item.getValueAt(property)).trim();
          if( returnValue == null )
             returnValue="";
        } catch (Exception ex) {
              throw new JspTagException("ListItemTag: Error calling method " + property + ":" + ex);
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
              throw new JspTagException("ListItemTag: Error extracting property: Can not handle the return type for property " + property);
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
             if( text.equals( null ) || text.equals("") )
               return "";
             else
               throw new JspTagException("ListItemTag: Error converting : " + text + " to a double. Ensure it is a number.");
        }
        if (format.toLowerCase().equals("number")) {
            if (numberFormatPattern != null) formattedString = I18nUtil.formatNumber(dub,precision,numberFormatPattern, locale);
            else formattedString = I18nUtil.formatNumber(dub,precision,locale);
        } else if (format.toLowerCase().equals("currency")) {
            if (numberFormatPattern != null) formattedString = I18nUtil.formatCurrency(dub,precision,numberFormatPattern,locale);
            else formattedString = I18nUtil.formatCurrency(dub,precision,locale);
        } else if (format.toLowerCase().equals("date")) {
/*
            if (numberFormatPattern != null) formattedString = I18nUtil.formatCurrency(dub,precision,numberFormatPattern,locale);
            else formattedString = I18nUtil.formatDate("20020303","yyyy-mm-dd");
*/
            formattedString = I18nUtil.formatDate("20020303","yyyy-mm-dd");
        } else {
            throw new JspTagException("ListItemTag: Error extracting formatting text: Do not know format:>" + format + "<");
        }
        return formattedString;
    }

}

