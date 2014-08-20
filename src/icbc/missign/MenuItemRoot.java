package icbc.missign;
import java.util.*;
import icbc.cmis.base.CmisConstance;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.SSICTool;

/**
 * @author 严波
 * 创建日期 2006-2-15
 * 类用途：公共菜单容器类(根节点)
 */
public class MenuItemRoot extends MenuItemUnit {
  /**
   * 构造函数
   *
   */
  public MenuItemRoot() {
    super();
    super.module = "00000";
    super.itemPicker = new HashMap();
    super.level = -1;
    super.subNode = false;
    super.appOrder = 0;
    super.appClass = "11111";
    super.rolePrivilege = "111111111111111111111111111111111111111"; //和数据库中的字段长度保持一致
    super.itemPicker.put(super.module, this);
  }

  /**
   * 转换成菜单字符串输出
   * @param role 角色
   * @return 菜单字符串
   */
  public String toMenu(Role role) throws TranFailException {
    try {
      StringBuffer menu = new StringBuffer();
      menu.append("<TABLE width=\"180\" cellspacing=\"1\" cellpadding=\"0\">");
      //输出子节点的菜单
      for (Iterator iterator = children.iterator(); iterator.hasNext();) {
        MenuItemUnit child = (MenuItemUnit)iterator.next();
        menu.append(child.toMenu(role));
      }
      //输出固定的菜单 修改密码
      menu.append("<TR TITLE=\""+icbc.cmis.tags.MuiTagBase.getStr(propertyFile, role.getLangCode(), "S009")+"\" Class=\"Navigator\" ID=\"01008\" AncestorID=\"00000\" Depth=\"0\">");
      menu.append("<TD STYLE=\"cursor:hand\"  class=\"title\">");
      menu.append("<DIV noWrap=\"true\" STYLE=\"padding-left:0em;\">");
      menu.append("<img src=\"" + baseWebPath + "/images/dot.gif\">");
      menu.append("<a href =\"javascript:goto('01008')\">"+icbc.cmis.tags.MuiTagBase.getStr(propertyFile, role.getLangCode(), "S009")+"</A>");
      menu.append("</DIV>");
      menu.append("</TD>");
      //menu.append("</TR>");
      menu.append("</TR>");
      //默认业务设置
      menu.append("<TR TITLE=\""+icbc.cmis.tags.MuiTagBase.getStr(propertyFile, role.getLangCode(), "S005")+"\" Class=\"Navigator\" ID=\"90102\" AncestorID=\"00000\" Depth=\"0\">");
      menu.append("<TD STYLE=\"cursor:hand\"  class=\"title\">");
      menu.append("<DIV noWrap=\"true\" STYLE=\"padding-left:0em;\">");
      menu.append("<img src=\"" + baseWebPath + "/images/dot.gif\">");
      menu.append("<a href =\"javascript:goto('90102')\">"+icbc.cmis.tags.MuiTagBase.getStr(propertyFile, role.getLangCode(), "S005")+"</A>");
      menu.append("</DIV>");
      menu.append("</TD>");
      menu.append("</TR>");
      
      //默认语言设置
	  menu.append("<TR TITLE=\""+icbc.cmis.tags.MuiTagBase.getStr(propertyFile, role.getLangCode(), "S011")+"\" Class=\"Navigator\" ID=\"90103\" AncestorID=\"00000\" Depth=\"0\">");
	  menu.append("<TD STYLE=\"cursor:hand\"  class=\"title\">");
	  menu.append("<DIV noWrap=\"true\" STYLE=\"padding-left:0em;\">");
	  menu.append("<img src=\"" + baseWebPath + "/images/dot.gif\">");
	  menu.append("<a href =\"javascript:goto('90103')\">"+icbc.cmis.tags.MuiTagBase.getStr(propertyFile, role.getLangCode(), "S011")+"</A>");
	  menu.append("</DIV>");
	  menu.append("</TD>");
	  menu.append("</TR>");
      
	  //切换语言
	  menu.append("<TR TITLE=\""+icbc.cmis.tags.MuiTagBase.getStr(propertyFile, role.getLangCode(), "S010")+"\" Class=\"Navigator\" ID=\"x\" AncestorID=\"00000\" Depth=\"0\">");
	  menu.append("<TD STYLE=\"cursor:hand\"  class=\"title\">");
	  menu.append("<DIV noWrap=\"true\" STYLE=\"padding-left:0em;\">");
	  menu.append("<img src=\"" + baseWebPath + "/images/dot.gif\">");
	  menu.append("<a href =\"javascript:changeLang(\'"+role.getLangCode()+"\')\">"+icbc.cmis.tags.MuiTagBase.getStr(propertyFile, role.getLangCode(), "S010")+"</A>");
	  menu.append("</DIV>");
	  menu.append("</TD>");
	  menu.append("</TR>");
      
      //统一认证的签退
      //退出本系统
      if (SSICTool.isSSICEnabled()) {
        menu.append("<TR TITLE=\""+icbc.cmis.tags.MuiTagBase.getStr(propertyFile, role.getLangCode(), "S006")+"\" Class=\"Navigator\" ID=\"241\" AncestorID=\"0\" Depth=\"0\">");
        menu.append("<TD STYLE=\"cursor:hand\"  class=\"title\">");
        menu.append("<DIV noWrap=\"true\" STYLE=\"padding-left:0em;\">");
        menu.append("<img src=\"" + baseWebPath + "/images/dot.gif\">");
        menu.append("<a href =\"icbc.cmis.servlets.CmisReqServlet?operationName=LogoutOp\" target=\"_top\")\">"+icbc.cmis.tags.MuiTagBase.getStr(propertyFile, role.getLangCode(), "S006")+"</a>");
        menu.append("</DIV>");
        menu.append("</TD>");
        menu.append("</TR>");
        //柜员签退
        menu.append("<TR TITLE=\""+icbc.cmis.tags.MuiTagBase.getStr(propertyFile, role.getLangCode(), "S007")+"\" Class=\"Navigator\" ID=\"242\" AncestorID=\"0\" Depth=\"0\">");
        menu.append("<TD STYLE=\"cursor:hand\"  class=\"title\">");
        menu.append("<DIV noWrap=\"true\" STYLE=\"padding-left:0em;\">");
        menu.append("<img src=\"" + baseWebPath + "/images/dot.gif\">");
        menu.append("<a href =\"icbc.cmis.servlets.CmisReqServlet?operationName=LogoutOp&scene=quitSSIC\" target=\"_top\")\">"+icbc.cmis.tags.MuiTagBase.getStr(propertyFile, role.getLangCode(), "S007")+"</A>");
        menu.append("</DIV>");
        menu.append("</TD>");
        menu.append("</TR>");
      }
      //非统一认证的签退
      else {
        menu.append("<TR TITLE=\""+icbc.cmis.tags.MuiTagBase.getStr(propertyFile, role.getLangCode(), "S006")+"\" Class=\"Navigator\" ID=\"241\" AncestorID=\"0\" Depth=\"0\">");
        menu.append("<TD STYLE=\"cursor:hand\"  class=\"title\">");
        menu.append("<DIV noWrap=\"true\" STYLE=\"padding-left:0em;\">");
        menu.append("<img src=\"" + baseWebPath + "/images/dot.gif\">");
        menu.append("<a href =\"icbc.cmis.servlets.CmisReqServlet?operationName=LogoutOp\" target=\"_top\")\">"+icbc.cmis.tags.MuiTagBase.getStr(propertyFile, role.getLangCode(), "S006")+"</A>");
        menu.append("</DIV>");
        menu.append("</TD>");
        menu.append("</TR>");
      }
      menu.append("</TABLE>");

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
   * 覆写clone函数
   */
  public MenuItemUnit cloneTree() throws CloneNotSupportedException {
    MenuItemRoot root = new MenuItemRoot();
    root.children = new TreeSet();
	root.itemPicker.put(root.module, root);
    for (Iterator iterator = children.iterator(); iterator.hasNext();) {
      MenuItemUnit thisChild = (MenuItemUnit)iterator.next();
	  MenuItemUnit cloneChild = thisChild.cloneTree(root.itemPicker);
//	  cloneChild.itemPicker = root.itemPicker;
//	  cloneChild.itemPicker.put(cloneChild.module,cloneChild);
	  root.children.add(cloneChild);
    }
    //(TreeSet)children.clone();
    //MenuItemRoot root = (MenuItemRoot)super.clone();
    //root.itemPicker = (HashMap)itemPicker.clone();
    return root;
  }

  /**
   * 对象销毁
   * 将变量的引用置成null，方便垃圾回收
   */
  public void destory() {
    for (Iterator iterator = children.iterator(); iterator.hasNext();) {
      MenuItemUnit child = (MenuItemUnit)iterator.next();
      child.destory();
    }
    itemPicker.clear();
    itemPicker = null;
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