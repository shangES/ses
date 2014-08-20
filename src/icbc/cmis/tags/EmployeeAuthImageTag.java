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
public class EmployeeAuthImageTag extends TagSupport {
  private String table = null;
  private String field = null;
  private String src = null;
  private String border = null;
  private String onclick = null;
  private String style = null;
  private String title = null;
  private String width = null;
  private String height = null;
  private String value = null;
  private String id = null;
  boolean needTitle = false;

  public int doStartTag() throws JspTagException {

    CheckEmployeeAuthTag parent = (CheckEmployeeAuthTag)findAncestorWithClass(this, CheckEmployeeAuthTag.class);
    if (parent == null)
      throw new JspTagException("Expect CheckEmployeeAuth tag");

    StringBuffer buffer = new StringBuffer();
    buffer.append("<img src=\"");

    buffer.append(src);
    buffer.append("\" ");

    if (border != null && !border.equals("")) {
      buffer.append("border=\"");
      buffer.append(border);
      buffer.append("\" ");
    }

    //if (id != null && !id.equals("")) {
      buffer.append("id=\"can_edit_tp3\"");
    //  buffer.append(id);
    //  buffer.append("\" ");
    //}

    if (style != null && !style.equals("")) {
      buffer.append("style=\"");
      buffer.append(style);
      buffer.append("\" ");
    }

    if (title != null && !title.equals("")) {
      buffer.append("title=\"");
      buffer.append(title);
      buffer.append("\" ");
    }

    if (width != null && !width.equals("")) {
      buffer.append("width=\"");
      buffer.append(width);
      buffer.append("\" ");
    }

    if (height != null && !height.equals("")) {
      buffer.append("height=\"");
      buffer.append(height);
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
    }

    if (needTitle) {
      buffer.append(" onclick=\"alert('你无权修改该输入域')\" ");
    }
    else {
      buffer.append(" onclick=\"");
      buffer.append(onclick);
      buffer.append("\" ");

    }
    buffer.append(">");
    try {
      JspWriter out = pageContext.getOut();

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

  public void setSrc(String src) {
    this.src = src;
  }

  public void setBorder(String border) {
    this.border = border;
  }

  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
