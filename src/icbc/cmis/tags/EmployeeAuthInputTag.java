/*
 * 创建日期 2005-11-8
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
public class EmployeeAuthInputTag extends TagSupport {
  private String id = null;
  private String name = null;
  private String maxlength = null;
  private String onblur = null;
  private String onchange = null;
  private String onclick = null;
  private String onfocus = null;
  private String onkeypress = null;
  private String size = null;
  private String type = null;
  private String value = null;

  private String table = null;
  private String field = null;

  public int doStartTag() throws JspTagException {
    boolean needTitle = false;

    CheckEmployeeAuthTag parent = (CheckEmployeeAuthTag)findAncestorWithClass(this, CheckEmployeeAuthTag.class);
    if (parent == null)
      throw new JspTagException("Expect CheckEmployeeAuth tag");

    StringBuffer buffer = new StringBuffer();
    buffer.append("<input type=\"");
    buffer.append(type);
    buffer.append("\" name=\"");
    buffer.append(name);
    buffer.append("\" ");
    //    if (id != null && !id.equals("")) {
    //      buffer.append("id=\"");
    //      buffer.append(id);
    //      buffer.append("\" ");
    //    }

    if (maxlength != null && !maxlength.equals("")) {
      buffer.append("maxlength=\"");
      buffer.append(maxlength);
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

    if (onclick != null && !onclick.equals("")) {
      buffer.append("onclick=\"");
      buffer.append(onclick);
      buffer.append("\" ");
    }

    if (onfocus != null && !onfocus.equals("")) {
      buffer.append("onfocus=\"");
      buffer.append(onfocus);
      buffer.append("\" ");
    }

    if (onkeypress != null && !onkeypress.equals("")) {
      buffer.append("onkeypress=\"");
      buffer.append(onkeypress);
      buffer.append("\" ");
    }

    buffer.append("value=\"");
    buffer.append(value);
    buffer.append("\" ");

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
    }
    if (needTitle) {
      buffer.append(" id='noedit' ");
    }
    else {
      buffer.append(" id='can_edit_tp1' ");
    }
    buffer.append(" readonly ");
    buffer.append(">");
    try {
      JspWriter out = pageContext.getOut();
      if (needTitle) {
        out.println("<a title=\"你无权修改该输入域\">" + buffer.toString() + "</a>");
      }
      else
        out.println(buffer.toString());
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

  public void setMaxlength(String maxlength) {
    this.maxlength = maxlength;
  }

  public void setOnblur(String onblur) {
    this.onblur = onblur;
  }

  public void setOnchange(String onchange) {
    this.onchange = onchange;
  }

  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }

  public void setOnfocus(String onfocus) {
    this.onfocus = onfocus;
  }

  public void setOnkeypress(String onkeypress) {
    this.onkeypress = onkeypress;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
