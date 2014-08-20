package icbc.missign;
import java.util.*;
import icbc.cmis.base.CmisConstance;
import icbc.cmis.base.TranFailException;
/**
 * @author ZJFH-yanb
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
/**
 * @author ZJFH-yanb
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
/**
 * @author 严波
 * 创建日期 2006-2-15
 * 类用途：公共菜单容器类
 */
public class MenuItemUnit implements Comparable, Cloneable {
  protected HashMap itemPicker = null; //菜单项存取器
  protected TreeSet children = new TreeSet(); //子节点集合
  protected String module = null; //本节点功能号
  protected int level; //本节点层次
  protected String pmodule = null; //父节点功能号
  protected String funcName = null; //功能名称
  protected boolean subNode; //是否为叶节点
  protected String rolePrivilege = null; //角色岗位权限
  protected String majorCode = null; //专业号
  protected String appClass = null; //行级别权限
  protected int appOrder; //功能序号
  protected static String baseWebPath = (String)CmisConstance.getParameterSettings().get("webBasePath");
  protected String propertyFile = "icbc.missign.icbc_missign";

  /**
   * 构造函数
   * @param module  模块名
   * @param funcName 模块中文名
   * @param majorCode  专业号
   * @param rolePrivilege  角色岗位权限
   * @param appClass  行级别权限
   * @param subNode  是否子节点
   * @param appOrder  菜单顺序
   */
  public MenuItemUnit(String module, String funcName, String majorCode, String rolePrivilege, String appClass, boolean subNode, int appOrder) {
    this.module = module;
    this.funcName = funcName;
    this.subNode = subNode;
    this.rolePrivilege = rolePrivilege;
    this.majorCode = majorCode;
    this.appClass = appClass;
    this.appOrder = appOrder;
  }

  /**
   * 构造函数
   * 需要结合setXXX使用
   */
  public MenuItemUnit() {}

  /**
   * 设置模块名
   * @param module 模块名
   */
  public void setModule(String module) {
    this.module = module;
  }

  /**
   * 设置模块中文名
   * @param funcName 模块中文名
   */
  public void setFuncName(String funcName) {
    this.funcName = funcName;
  }

  /**
   * 设置菜单项存取器
   * @param itemPicker 菜单项存取器
   */
  public void setItemPicker(HashMap itemPicker) {
    this.itemPicker = itemPicker;
  }

  /**
   * 获得菜单项存取器
   */
  public HashMap getItemPicker() {
    return itemPicker;
  }

  /**
   * 设置父节点
   * @param parent 父节点
   */
  public void setParent(MenuItemUnit parent) {
    this.itemPicker = parent.itemPicker;
    this.level = parent.level + 1;
    this.pmodule = parent.module;
  }

  /**
   * 设置是否子节点
   * @param subNode 是否子节点
   */
  public void setSubNode(boolean subNode) {
    this.subNode = subNode;
  }

  /**
   * 设置角色岗位权限
   * @param rolePrivilege 角色岗位权限
   */
  public void setRolePrivilege(String rolePrivilege) {
    this.rolePrivilege = rolePrivilege;
  }

  /**
   * 设置专业号
   * @param majorCode 专业号
   */
  public void setMajorCode(String majorCode) {
    this.majorCode = majorCode;
  }

  /**
   * 设置行级别权限
   * @param appClass 行级别权限
   */
  public void setAppClass(String appClass) {
    this.appClass = appClass;
  }

  /**
   * 设置功能序号
   * @param appOrder 功能序号
   */
  public void setAppOrder(int appOrder) {
    this.appOrder = appOrder;
  }

  /**
   * 设置子节点集合
   * @param children 子节点集合
   */
  public void setChildren(TreeSet children) {
    this.children = children;
  }

  /**
   * 获取子节点集合
   * @return 子节点集合
   */
  public TreeSet getChildren() {
    return this.children;
  }

  /**
   * 加入一个子节点
   * @param item 子节点
   */
  public void putMenuItem(MenuItemUnit item) {
    item.setParent(this);
    children.add(item);
    itemPicker.put(item.module, item);
  }

  /**
   * 除去指定的节点
   * @param func 节点名
   */
  public void removeMenuItem(String func) {
    MenuItemUnit item = (MenuItemUnit)itemPicker.get(func);
    if (item == null)
      return;
    item.itemPicker.remove(func);
    MenuItemUnit parent = (MenuItemUnit)itemPicker.get(item.pmodule);
    if (parent == null)
      parent.children.remove(item);

  }

  public void updateRolePrivilegeAppClass(String func, Employee employee, boolean state) {
    char ori, tar;
    if (state) {
      ori = '0';
      tar = '1';
    }
    else {
      ori = '1';
      tar = '0';
    }
    MenuItemUnit item = this.getMenuItemUnit(func);
    if (item == null)
      return;
    if (item.appClass.charAt(Integer.parseInt(employee.getBankFlag())) == ori) {
      char[] c1 = item.appClass.toCharArray();
      c1[Integer.parseInt(employee.getBankFlag())] = tar;
      item.appClass = new String(c1);
    }
    if (item.rolePrivilege.charAt(Integer.parseInt(employee.getEmployeeClass()) - 1) == ori) {
      char[] c2 = item.rolePrivilege.toCharArray();
      c2[Integer.parseInt(employee.getEmployeeClass()) - 1] = tar;
      item.rolePrivilege = new String(c2);
    }

  }

  /**
   * 获得指定的节点对象
   * @param module 节点模块名
   * @return 节点对象
   */
  public MenuItemUnit getMenuItemUnit(String module) {
    return (MenuItemUnit)itemPicker.get(module);
  }

  /**
   * 获得空白条
   * @return 空白条
   */
  public String getSpacer() {
    return "<TABLE width=\"180\" cellspacing=\"1\" cellpadding=\"0\"><TR><td class=\"title1\" height=\"16\"></td></TR></TABLE>";
  }

  /**
   * 转换成菜单字符串输出
   * @param role 角色
   * @return 菜单字符串
   */
  public String toMenu(Role role) throws TranFailException {
    try {
      //如果角色没有该菜单的权限，就不展示该菜单
      if (rolePrivilege.charAt(role.getRoleCode() - 1) == '0')
        return "";
      //如果行级别没有该菜单的权限，就不展示该菜单
      if (appClass.charAt(role.getBankFlag()) == '0')
        return "";
      StringBuffer menu = new StringBuffer();
      menu.append("<TR TITLE=\"");
      menu.append(funcName);
      menu.append("\" Class=\"Navigator");
      if (level > 0)
        menu.append("-Hidden");
      menu.append("\" ID=\"");
      menu.append(module);
      menu.append("\" AncestorID=\"");
      menu.append(pmodule);
      menu.append("\" Depth=\"");
      menu.append(level);
      if (!subNode) {
        menu.append("\" Expanded=\"no\" onclick=\"toggle('");
        menu.append(module);
        menu.append("')");
      }
      menu.append("\"><TD STYLE=\"cursor:hand\"  class=\"title\">");
      menu.append("<DIV noWrap=\"true\" STYLE=\"padding-left:");
      menu.append(level);
      menu.append("em;\"><img src=\"" + baseWebPath + "/images/");
      if (!subNode) {
        menu.append("bs.gif");
      }
      else {
        menu.append("dot.gif");
      }
      menu.append("\">");
      if (subNode) {
        menu.append("<A STYLE=\"cursor:hand\"");
        menu.append("href =\"javascript:goto('");
        menu.append(module + "')\"");
        menu.append(">");
        menu.append(funcName);
        menu.append("</A>");
      }
      else {
        menu.append(funcName);
      }
      menu.append("</DIV></TD></TR>");
      //输出子节点的菜单
      for (Iterator iterator = children.iterator(); iterator.hasNext();) {
        MenuItemUnit child = (MenuItemUnit)iterator.next();
        menu.append(child.toMenu(role));
      }
      return menu.toString();
    }
    catch (TranFailException e) {
      throw e;
    }
    catch (Exception e) {
      throw new TranFailException("MenuItemUnit", "toMenu", this.module + "节点菜单生成失败" + e.getMessage(), this.module + "节点菜单生成失败" + e.getMessage());
    }
  }

  /**
   * 覆写equals函数
   */
  public boolean equals(Object o) {
    return o instanceof MenuItemUnit && this.module.equals(((MenuItemUnit)o).module);
  }

  /**
   * 覆写hashCode函数
   */
  public int hashCode() {
    return this.module.hashCode();
  }

  /**
   * 覆写compareTo函数
   */
  public int compareTo(Object o) {
    MenuItemUnit item = (MenuItemUnit)o;
    int delta = this.appOrder - item.appOrder;
    //如果appOrder一样的话，就比较module的大小
    if (delta != 0)
      return delta;
    else
      return this.module.compareTo(item.module);
  }

  /**
   * 覆写clone函数
   */
  public MenuItemUnit cloneTree(HashMap itp) throws CloneNotSupportedException {
    MenuItemUnit item = (MenuItemUnit)super.clone();
    item.children = new TreeSet();
    item.itemPicker = itp;
    item.itemPicker.put(item.module, item);

    for (Iterator iterator = children.iterator(); iterator.hasNext();) {
      MenuItemUnit thisChild = (MenuItemUnit)iterator.next();
      MenuItemUnit cloneChild = thisChild.cloneTree(item.itemPicker);
      item.children.add(cloneChild);
    }
    //item.children = (TreeSet)children.clone();
    //	item.appClass = new String(this.appClass);
    //	item.rolePrivilege = new String(this.rolePrivilege);
    return item;
  }

  /**
   * 对象销毁
   * 将变量的引用置成null，方便垃圾回收
   */
  public void destory() {
    //销毁子节点
    for (Iterator iterator = children.iterator(); iterator.hasNext();) {
      MenuItemUnit child = (MenuItemUnit)iterator.next();
      child.destory();
    }
    itemPicker = null; //菜单项存取器
    children.clear(); //子节点集合
    children = null;
    module = null; //本节点功能号
    pmodule = null; //父节点功能号
    funcName = null; //功能名称
    rolePrivilege = null; //角色权限
    majorCode = null; //专业号
    appClass = null; //行级别权限
  }

}
