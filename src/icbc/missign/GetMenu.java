package icbc.missign;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.DummyOperation;
import icbc.cmis.operation.*;
import java.sql.*;
import icbc.cmis.operation.SqlTool;
import icbc.cmis.service.*;
public class GetMenu extends HttpServlet {
	icbc.cmis.tags.PropertyResourceReader propertyResourceReader;
  private static final String CONTENT_TYPE = "text/html; charset=GBK";
  public static HashMap menus = new HashMap();
  //public static HashMap cached;
  private String baseWebPath;
  String langCode="zh_CN";
  /**
   * Initialize global variables
   * @throws ServletException
   */
  public void init() throws ServletException {
    //cache();
    baseWebPath = (String)CmisConstance.getParameterSettings().get("webBasePath");
  }

  private void cache(String majorCode, String areaCode, String langCode) throws ServletException {
    MenuDAO dao = new MenuDAO(new icbc.cmis.operation.DummyOperation());
    String application = (String)CmisConstance.getParameterSettings().get("application");
    try {
      MenuItemUnit menuTree = dao.getMenuPerAreaMajor(application, majorCode, areaCode, langCode);
      //cached.put(majorCode + "$" + areaCode, "true");
      menus.put(majorCode + "$" + areaCode + "$" + langCode, menuTree);
    }
    catch (Exception ex) {
      throw new ServletException(ex);
    }
  }

  /**
   *Process the HTTP Get request
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  /**Clean up resources*/
  public void destroy() {}

  /**
   * 根据传入的参数，显示相应的菜单
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    propertyResourceReader=new icbc.cmis.tags.PropertyResourceReader(langCode,"icbc.cmis.util.ytdk_frames");
		//add hlz 20070612
    //检查会话是否有效
    try {
      if (!CMisSessionMgr.checkSession(request)) {
        //outTime(out, "  当前会话已失效，<br>请重新登录</font></b><br>");
		outTime(out,propertyResourceReader.getPrivateStr("C000005"));
        return;
      }
      CMisSessionMgr session = new CMisSessionMgr(request);

      //获取参数：柜员信息employee、专业major、专业级别majorClass
      String major = request.getParameter("major");
      String majorClass = request.getParameter("majorClass");
      String sysCode = request.getParameter("sysCode");
      Employee employee = (Employee)session.getSessionData("Employee");
      //add by yanbo 20040429 for switch apps
      boolean isSwitch = false;
      String selectedSysCode = null;
      try {
        if (((String)session.getSessionData("isSwitch")).equals("true")){
           isSwitch = true;
		   session.removeSessionData("isSwitch");
        }
      }
      catch (Exception e) {}
      if (isSwitch) {
        try {
          selectedSysCode = ((String)session.getSessionData("selSysCode"));
        }
        catch (Exception e) {}
      }
      //end by yanbo 20040429
      //设置柜员当前信息
      int roleCode = 0;
      //判断当前应用是否改变
      if (!isSwitch) {
        if (sysCode == null || sysCode.equals(employee.getEmployeeSys())) { //说明相同没有改变
          if (majorClass != null && !majorClass.equals("")) {
            roleCode = Integer.valueOf(majorClass).intValue();
          }
          employee.setCurrentRole(major, roleCode);
        }
        else {
          employee.setCurrentRoleSys(sysCode);
        }
      }
      else {

        TableBean mag_sys_major = new TableBean();
        Hashtable hTable = (Hashtable)CmisConstance.getDictParam();
        mag_sys_major = (TableBean)hTable.get("mag_sys_major");

        String major_code = null;
        int role_code = 0;
        Vector majors = employee.getMajors();
        for (int i = 0; i < majors.size(); i++) {
          String[] vmajor = (String[])majors.get(i);
          if (selectedSysCode.equals(mag_sys_major.getNameByCode("sys_code", "major_code", vmajor[0]))) {
            major_code = vmajor[0];
            break;
          }
        }
        Collection roles = employee.getRoles();
        for (Iterator i = roles.iterator(); i.hasNext();) {
          Role item = (Role)i.next();
          if (item.getMajorCode().equals(major_code)) {
            role_code = item.getRoleCode();
            break;
          }
        }

        employee.setCurrentRoleSys(selectedSysCode);
        employee.setCurrentRole(major_code, role_code);

      }

      session.updateSessionData("Employee", employee);
      session.updateSessionData("EmployeeClass", employee.getEmployeeClass());
      session.updateSessionData("EmployeeClassName", employee.getEmployeeClassName());
      session.updateSessionData("Major", employee.getEmployeeMajor());
      session.updateSessionData("MajorName", employee.getEmployeeMajorName());
	  session.updateSessionData("LangCode", employee.getLangCode());
	  session.updateSessionData("ZoneCode", employee.getZoneCode());

      session.sessionCommit();
      //输出菜单
      try {
        if ((request.getParameter("isSwitch")).equals("true"))
          isSwitch = true;
      }
      catch (Exception ex) {}
      MenuItemUnit curMenuTree = null;
      MenuItemUnit menuTree = null;
      if (!CmisConstance.isPopedom(employee.getMdbSID(), employee.getEmployeeMajor(), "1", "**********")) {

        MenuDAO dao = new MenuDAO(new icbc.cmis.operation.DummyOperation());
        //取得菜单模板所在地区
        String templateArea = dao.getMenuArea((String)session.getSessionData("AreaCode"), (String)session.getSessionData("Major"));
        //取得增量菜单
        String[] deltaMenu =
          dao.getDeltaMenu(
            employee.getEmployeeCode(),
            employee.getEmployeeMajor(),
            employee.getEmployeeClass(),
            (String)session.getSessionData("AreaCode"));
        //如果菜单没有被缓存，则缓存菜单
        if (!menus.containsKey((String)session.getSessionData("Major") + "$" + templateArea + "$" + employee.getLangCode()))
          cache((String)session.getSessionData("Major"), templateArea, employee.getLangCode());
        menuTree = (MenuItemUnit)menus.get((String)session.getSessionData("Major") + "$" + templateArea + "$" + employee.getLangCode());

        //如果增量菜单为空则取缓存菜单，否则取复制后的一份菜单
        if (deltaMenu[1].equals("") && deltaMenu[0].equals("")) {
          curMenuTree = menuTree;
        }
        else {
          curMenuTree = ((MenuItemRoot)menuTree).cloneTree();
          //合并增量菜单
          mergeDelta(curMenuTree, deltaMenu, employee);
        }
      }
      else {
        curMenuTree = new MenuItemRoot();
      }
      //输出菜单
      Menu menu = new Menu();
      out.println(menu.getHtml(curMenuTree, employee, isSwitch));

      //清理内存
      if (curMenuTree != menuTree)
        curMenuTree.destory();
      curMenuTree = null;
    }
    catch (TranFailException ex) {
      outTime(out, ex.getMessage());
    }
    catch (Exception ex) {
     //outTime(out, "  菜单生成失败<br>" + ex.getMessage());
		outTime(out, propertyResourceReader.getPrivateStr("C000006") + ex.getMessage());
    }

  }

  /**
   * 合并增量菜单
   * @param curMenuTree 菜单树
   * @param delta 增量菜单
   * @param employee 柜员
   */
  public void mergeDelta(MenuItemUnit curMenuTree, String[] delta, Employee employee) {
    StringTokenizer romove = new StringTokenizer(delta[0], ",");
    String func = null;
    String funcList = null;
    while (romove.hasMoreTokens()) {
      func = romove.nextToken();
      //curMenuTree.removeMenuItem(func);
      curMenuTree.updateRolePrivilegeAppClass(func, employee, false);
    }
    StringTokenizer addList = new StringTokenizer(delta[1], ",");
    while (addList.hasMoreTokens()) {
      funcList = addList.nextToken();
      addFuncList(curMenuTree, funcList, employee);
    }
  }

  /**
   * 添加功能
   * @param curMenuTree 菜单树
   * @param funcList 功能
   * @param employee 柜员
   */
  public void addFuncList(MenuItemUnit curMenuTree, String funcList, Employee employee) {
    StringTokenizer add = new StringTokenizer(funcList, ">");
    String func;
    //String parentModule = "00000";
    while (add.hasMoreTokens()) {
      func = add.nextToken();
      if (curMenuTree.itemPicker.containsKey(func)) {
        //parentModule = func;
        curMenuTree.updateRolePrivilegeAppClass(func, employee, true);
        //				MenuItemUnit item = curMenuTree.getMenuItemUnit(func);
        //				if (item.appClass.charAt(Integer.parseInt(employee.getBankFlag())) == '0') {
        //					char[] c1 = item.appClass.toCharArray();
        //					c1[Integer.parseInt(employee.getBankFlag())] = '1';
        //					item.appClass = new String(c1);
        //				}
        //				if (item.rolePrivilege.charAt(Integer.parseInt(employee.getEmployeeClass()) - 1) == '0') {
        //					char[] c2 = item.rolePrivilege.toCharArray();
        //					c2[Integer.parseInt(employee.getEmployeeClass()) - 1] = '1';
        //					item.rolePrivilege = new String(c2);
        //				}
      }
      else
        continue;

    }
  }

  public void outTime(PrintWriter out, String info) {
    out.println("<html>");
    out.println("<head>");
    //out.println("<title>超时</title>");
	  out.println(propertyResourceReader.getPrivateStr("C000007"));
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">");
    out.println("<link rel=\"stylesheet\" href=\"" + baseWebPath + "/css/navigator.css\" type=\"text/css\">");
    out.println("</head>");
    out.println("");
    out.println("<body bgcolor=\"#FFFFFF\" text=\"#000000\">");
    out.println("<p align=\"center\"><br>");
    out.println("  <br>");
    out.println("  <br>");
    out.println("  <table><tr><td><b><font color=\"#FFFFFF\">");
    out.println(info);
    out.println("  </td></tr></table>");
    out.println("</p>");
    out.println(
      "<p align=\"center\"><a href=\""
        + baseWebPath
        + "/login.jsp\" target=\"_top\"><img src=\""
        + baseWebPath
        + "/images/g_login.gif\" width=\"48\" height=\"24\" border=\"0\"></a> ");
    out.println("</p>");
    out.println("</body>");
    out.println("</html>");
  }

}