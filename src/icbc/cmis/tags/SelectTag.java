/*
 * $Id: SelectTag.java,v 1.2 2001/10/25 23:37:37 ro89390 Exp $
 * Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 */
 
package icbc.cmis.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;
import icbc.cmis.base.*;

import java.io.IOException;
import java.util.*;

/**
 * HTML 'select' tag.
 */
public class SelectTag extends BodyTagSupport {

  Map options = new TreeMap();
  String selectedValue = "";
  java.util.Hashtable h_table = null;
  DictBean bean = null;
  int size = 1;
  String name = null;
  String id = "operationData";
  String property = null;
  String addtext = null;
  String addvalue = null;
  String source = "dictbean";
  String cname = "dict_name";
  String cvalue = "dict_code";
  String onChange = null;
  boolean isEditable = true;
  boolean isSpecial = false;
  KeyedDataCollection context;
  Vector record = null;
  Vector namevector = null;

  public void setSelectedValue(String sv) {
    selectedValue = sv.trim();
  }

  public void setSize(int s) {
    size = s;
  }

  public void setProperty(String p) {
    property = p.toLowerCase();
  }

  public void setName(String n) {
    name = n;
  }

  public void setAddtext(String at) {
    addtext = at;
  }

  public void setAddvalue(String av) {
    addvalue = av;
  }

  public void setEditable(boolean e) {
    isEditable = e;
  }

  public void setSource(String s) {
    source = s;
  }

  public void setcname(String n) {
    cname = n;
  }

  public void setcvalue(String v) {
    cvalue = v;
  }

  public void setspecial(boolean s) {
    isSpecial = s;
  }

  public void setonchange(String o) {
    onChange = o;
  }

  public void putOption(String value, String text) {
    options.put(value, text); 
  }

  public int doStartTag() throws JspTagException {
    CMisSessionMgr sm = new icbc.cmis.base.CMisSessionMgr((HttpServletRequest)pageContext.getRequest());
    //try { 
     // String langCode = (String)sm.getSessionData("LangCode");
      if (source.toLowerCase().equals("tablebean") || source.toLowerCase().equals("userbean")) {
        context = (KeyedDataCollection)pageContext.getRequest().getAttribute(id);
        h_table = CmisConstance.getDictParam();
        bean = (DictBean)getTableBean();  
      }
      else if (source.toLowerCase().equals("dictbean")) { 
        h_table = CmisConstance.getDictParam();
        bean = (DictBean)h_table.get(this.property);
      }
      else {
        h_table = CmisConstance.getDictParam();
        bean = (DictBean)h_table.get(this.property);
      }
   
    

    return 100;
  }

  public int doEndTag() throws JspTagException {
    Vector retVect = null;
    String defaultValue = null;
    try {
      StringBuffer html = new StringBuffer();

      retVect = bean.getKeys();

      if (isEditable) {
        html.append("<select");
        html.append(size > 0 ? (" size=\"" + size + "\"") : "");
        if (onChange != null)
          html.append(" onchange=\"" + onChange + "\" ");
        html.append(" name=\"" + name + "\">");
        html.append("<option value=\"\"></option>");
        if (addvalue != null) {
          html.append("<option value=\"" + addvalue + "\"");
          html.append(addvalue.equals(selectedValue) ? " selected>" : ">");
          html.append(addtext);
          html.append("</option>");
        }

        for (int i = 0; i < retVect.size(); i++) {
          String value = (String)retVect.get(i);
          String text = (String)bean.getValue(value);
          html.append("<option value=\"" + value + "\"");
          html.append(value.equals(selectedValue) ? " selected>" : ">");
          html.append(text);
          html.append("</option>");
        }
        html.append("</select>");
      }
      else { //isEditable=false不可编辑 若无缺省值，则置addtext或&nbsp
        defaultValue = bean.getValue(selectedValue.trim());
        if (defaultValue == null || defaultValue.trim().equals("")) {
          /*
          modified by yhua on 2002/10/15
                             if( addtext != null )
                                html.append(addtext);
                             else
          */
          if (selectedValue.trim().equals(addvalue))
            html.append(addtext);
          else
            html.append("&nbsp");
        }
        else {
          html.append(bean.getValue(selectedValue));
        }
      }
      pageContext.getOut().print(html.toString());
      return EVAL_PAGE;
    }
    catch (IOException e) {
      throw new JspTagException("LinkTag: " + e.getMessage());
    }
  }

  public DictBean getTableBean() throws JspTagException {
    int iname;
    int ivalue;
    icbc.cmis.base.DictBean dictbean = new DictBean();
    Vector m = null;
    TableBean dict = (TableBean)h_table.get(this.property.toLowerCase());
    m = dict.getTableValus();
    namevector = dict.getColumns();
    iname = namevector.indexOf(cname);
    ivalue = namevector.indexOf(cvalue);

    for (int rid = 0; rid < m.size(); rid++) {
      record = (Vector)m.elementAt(rid);
      String str1 = (String)record.elementAt(ivalue);
      String str2 = (String)record.elementAt(iname);

      //若需要进行特殊处理,则调用以下模块validItem
      if (isSpecial) {
        if (!validItem(str1, str2))
          continue;
        else
          dictbean.addData(str1, str2);
      }
      else {
        dictbean.addData(str1, str2);
      }
    }
    return dictbean;  
  }

  /**
   * 若为合法的内容，则写入bean中，否则不写
   */
  public boolean validItem(String value, String name) {
    TableColumnTag tag = null;

    //ma300009信用等级对应表
    if (property.trim().toLowerCase().equals("ma300009")) {
      if (value.trim().equals("90") || value.trim().equals("00"))
        return false;
      else
        return true;
    }

    //一般受约束的字段
    //  1.定性评价指标表 ma300003
    tag = (TableColumnTag)findAncestorWithClass(this, TableColumnTag.class);
    while (true) {
      if (tag == null)
        return true;
      int c = namevector.indexOf(tag.getName());
      String rowvalue = (String)record.elementAt(c);
      if (!rowvalue.equals(tag.getValue()))
        return false;
      tag = (TableColumnTag)findAncestorWithClass(tag, TableColumnTag.class);
    }
  }

}
