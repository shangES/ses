/*
 * 创建日期 2005-11-17
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.tags;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;
import javax.servlet.*;
import java.util.*;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class EmployeeAuthSelectTag extends BodyTagSupport {
  private String id = null;
  private String name = null;
  private String multiple = null;
  private String onblur = null;
  private String onchange = null;
  private String onfocus = null;
  private String size = null;
  private String value = null;

  private String table = null;
  private String field = null;

  boolean needTitle = false;

  public int doStartTag() throws JspTagException {

    CheckEmployeeAuthTag parent = (CheckEmployeeAuthTag)findAncestorWithClass(this, CheckEmployeeAuthTag.class);
    if (parent == null)
      throw new JspTagException("Expect CheckEmployeeAuth tag");

    StringBuffer buffer = new StringBuffer();
    buffer.append("<select ");

    if (id != null && !id.equals("")) {
      buffer.append("id=\"");
      buffer.append(id);
      buffer.append("\" ");
    }

    if (multiple != null && !multiple.equals("")) {
      buffer.append("multiple=\"");
      buffer.append(multiple);
      buffer.append("\" ");
    }

    if (size != null && !size.equals("")) {
      buffer.append("size=\"");
      buffer.append(size);
      buffer.append("\" ");
    }

    if (onblur != null && !onblur.equals("")) {
      buffer.append("onblur=\"");
      buffer.append(onblur);
      buffer.append("\" ");
    }

    if (onchange != null && !onchange.equals("")) {
      buffer.append("onchange=\"");
      buffer.append(onchange);
      buffer.append("\" ");
    }

    if (onfocus != null && !onfocus.equals("")) {
      buffer.append("onfocus=\"");
      buffer.append(onfocus);
      buffer.append("\" ");
    }

    if (!value.equals("")) {
      Hashtable auth = parent.getAuthHashtable();
      if (table == null || table.equals("")) {
        if (((field.startsWith("TA") || field.startsWith("ta"))) && field.length() == 11) {
          table = field.substring(0, 8);
        }
      }

      if (table == null || table.equals(""))
        throw new JspTagException("Valid attribute table value need in tableCheckEmployeeAuth tag");

      Vector nodes = (Vector)auth.get(table.toUpperCase());
      if (nodes != null)
        for (int i = 0; i < nodes.size(); i++) {
          String node = (String)nodes.get(i);
          if (node.equalsIgnoreCase(field)) {
            needTitle = true;
            break;
          }
        }

      if (needTitle) {
        buffer.append(" name=\"");
        buffer.append(name);
        buffer.append("_disp\" ");
		buffer.append(" id='noedit' ");
		buffer.append(" disabled ");
        buffer.append(">");
      }
      else {
        buffer.append(" name=\"");
        buffer.append(name);
        buffer.append("\" ");
		buffer.append(" id='can_edit_tp2' ");
		buffer.append(" disabled ");
        buffer.append(">");
      }
    }
    else {
      buffer.append(" name=\"");
      buffer.append(name);
      buffer.append("\" ");
	  buffer.append(" id='can_edit_tp2' ");
	  buffer.append(" disabled ");
      buffer.append(">");
    }

    try {
      JspWriter out = pageContext.getOut();
      if (needTitle) {
        out.println("<a title=\"你无权修改该输入域\">" + buffer.toString());
      }
      else
        out.println(buffer.toString());
    }
    catch (IOException ioe) {
      throw new JspTagException("页面输出失败");
    }

    return EVAL_BODY_INCLUDE;
  }

  public int doAfterBody() throws JspTagException {
    try {
      JspWriter out = pageContext.getOut();
      out.println("</select>");
      if (needTitle) {
        out.println("</a>");
        out.println("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\">");
      }
    }
    catch (IOException ioe) {
      throw new JspTagException("页面输出失败");
    }
    return SKIP_BODY;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public void setField(String field) {
    this.field = field;
  }

//  public void setId(String id) {
//    this.id = id;
//  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMultiple(String multiple) {
    this.multiple = multiple;
  }

  public void setOnblur(String onblur) {
    this.onblur = onblur;
  }

  public void setOnchange(String onchange) {
    this.onchange = onchange;
  }

  public void setOnfocus(String onfocus) {
    this.onfocus = onfocus;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
