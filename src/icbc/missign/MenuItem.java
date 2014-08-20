package icbc.missign;

import java.io.Serializable;
import icbc.cmis.base.CmisConstance;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class MenuItem implements Serializable, Comparable {
  private String majorCode;
  private String pModule;
  private String module;
  private String funcName;
  private boolean subNode = true;
  private String privilege;
  private String appClass;
  private int order = 0;
  private String baseWebPath;

  public MenuItem(String majorCode, String pModule, String module, String funcName,
                  boolean subNode, String privilege, String appClass, int order) {
    this.majorCode = majorCode;
    this.pModule = pModule;
    this.module = module;
    this.funcName = funcName;
    this.subNode = subNode;
    this.privilege = privilege;
    this.appClass = appClass;
    this.order = order;
    this.baseWebPath =(String) CmisConstance.getParameterSettings().get("webBasePath");
  }

  public String getAppClass() {
    return appClass;
  }
  public String getFuncName() {
    return funcName;
  }
  public String getMajorCode() {
    return majorCode;
  }
  public String getModule() {
    return module;
  }
  public String getPModule() {
    return pModule;
  }
  public String getPrivilege() {
    return privilege;
  }
  public boolean isSubNode() {
    return subNode;
  }

  /**
   * 产生一项菜单项
   * @param row menuitem
   * @param level level
   * @param menu allmenu
   */
  public String getHtml(int level) {
    StringBuffer menu = new StringBuffer();
    menu.append("<TR TITLE=\"");
    menu.append(funcName);
    menu.append("\" Class=\"Navigator");
    if(level > 0) menu.append("-Hidden");
    menu.append("\" ID=\"");
    menu.append(module);
    menu.append("\" AncestorID=\"");
    menu.append(pModule);
    menu.append("\" Depth=\"");
    menu.append(level);
    if(!subNode) {
      menu.append("\" Expanded=\"no\" onclick=\"toggle('");
      menu.append(module);
      menu.append("')");
    }
    menu.append("\"><TD STYLE=\"cursor:hand\"  class=\"title\">");
    menu.append("<DIV noWrap=\"true\" STYLE=\"padding-left:");
    menu.append(level);
    menu.append("em;\"><img src=\""+baseWebPath+"/images/");
    if(!subNode) {
      menu.append("bs.gif");
    } else {
      menu.append("dot.gif");
    }
    menu.append("\">");
    if(subNode) {
      menu.append("<A STYLE=\"cursor:hand\"");
      menu.append("href =\"javascript:goto('");
      menu.append(module + "')\"");
      menu.append(">");
      menu.append(funcName);
      menu.append("</A>");
    }else{
      menu.append(funcName);
    }
    menu.append("</DIV></TD></TR>");
    return menu.toString();
  }

  public int compareTo(Object o) {
    if(o instanceof MenuItem) {
      MenuItem that = (MenuItem)o;
      return this.order - that.order;
    }
    return 1;
  }

}