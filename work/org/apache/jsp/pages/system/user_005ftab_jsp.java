package org.apache.jsp.pages.system;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class user_005ftab_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>表单管理</title>\r\n");
      out.write("<base href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${baseUrl }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"/>\r\n");
      out.write("<meta http-equiv=\"pragma\" content=\"no-cache\"/>\r\n");
      out.write("<meta http-equiv=\"cache-control\" content=\"no-cache\"/>\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"0\"/>    \r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"skins/css/style.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"skins/css/form.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"skins/css/jquery-ui-1.8.15.custom.css\"/>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"skins/js/jquery-1.4.4.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"skins/js/public.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"skins/js/jquery-ui-1.8.5.custom.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var tid='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${param.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("var edit='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${edit}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("var pageState=false;\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("$(document).ready(function() {\r\n");
      out.write("\t//tab页\r\n");
      out.write("\tloadTab();\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t//加载数据\r\n");
      out.write("\t$(\"#detail\").attr(\"src\",\"system.do?page=user_tree\");\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("    //关闭等待层\r\n");
      out.write("    if(window.parent.hidenLoading)\r\n");
      out.write("    \twindow.parent.hidenLoading();\r\n");
      out.write("\t\r\n");
      out.write("});\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//tab页\r\n");
      out.write("var tabIndex=0;\r\n");
      out.write("function loadTab(){\r\n");
      out.write("\t$(\"#mytab\").tabs({\r\n");
      out.write("    \tselect: function(event, ui) {\r\n");
      out.write("    \t\ttabIndex=ui.index;\r\n");
      out.write("    \t\t\r\n");
      out.write("    \t\t//刷新\r\n");
      out.write("    \t\tif(tabIndex==0){\r\n");
      out.write("    \t\t\t$(\"#detail\").attr(\"src\",\"system.do?page=user_tree\");\r\n");
      out.write("    \t\t}else if(tabIndex==1){\r\n");
      out.write("    \t\t\t$(\"#detail\").attr(\"src\",\"system.do?page=user_grid\");\r\n");
      out.write("    \t\t}\r\n");
      out.write("    \t}\r\n");
      out.write("    });\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//切换视图\r\n");
      out.write("function convertView(url){\r\n");
      out.write("\twindow.parent.convertView(pageState?null:'');\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"sort\">\r\n");
      out.write("\t<div class=\"sort-title\">\r\n");
      out.write("\t\t<h3>用户管理</h3>\r\n");
      out.write("\t\t<div class=\"title-ctrl\">\r\n");
      out.write("\t\t\t<a class=\"btn-ctrl\" href=\"javascript:chevronUpDown('.sort-cont',false);\"><i class=\"icon icon-chevron-up\"></i></a>\r\n");
      out.write("\t\t\t<a class=\"btn-ctrl\" href=\"javascript:chevronUpDown('.sort-cont',true);\"><i class=\"icon icon-chevron-down\"></i></a>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t<div class=\"sort-cont sort-table\">\r\n");
      out.write("\t\t<div id=\"mytab\">\r\n");
      out.write("\t\t\t<ul>\r\n");
      out.write("\t\t\t\t<li><a href=\"#tab0\">用户树</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"#tab0\">用户列表</a></li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t\t<div id=\"tab0\">\r\n");
      out.write("\t\t\t\t<iframe id=\"detail\" name=\"detail\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" src=\"\" ></iframe>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
