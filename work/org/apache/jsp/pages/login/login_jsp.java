package org.apache.jsp.pages.login;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<title>华数帐务管理系统V2.0--用户登陆</title>\r\n");
      out.write("<base href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${baseUrl }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"  href=\"pages/login/login.css\" />\r\n");
      out.write("<script type=\"text/javascript\" src=\"skins/js/jquery-1.4.4.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"skins/js/jquery.cookie.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var cookieName='mycookie';\r\n");
      out.write("var cookiePWD='mycookiePwd';\r\n");
      out.write("$(document).ready(function(){\r\n");
      out.write("   \tvar val = $.cookie(cookieName);\r\n");
      out.write("\tif (val)$(\"#userName\").attr('value',val);\r\n");
      out.write("\r\n");
      out.write("\tvar pwd=$.cookie(cookiePWD);\r\n");
      out.write("\tif(pwd!=null){\r\n");
      out.write("\t\t$(\"#password\").attr('value',pwd);\r\n");
      out.write("\t\t$('#lock').attr('checked',true);\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tif('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${param.error}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("'=='true')\r\n");
      out.write("\t\t$('#ErrorMsg').css({'display':''});\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t//检查ＩＥ版本\r\n");
      out.write("\tif($.browser.msie&&$.browser.version==6){\r\n");
      out.write("\t\t$('#ieVersion').css('display','');\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t//换背景图\r\n");
      out.write("\tvar bg='url(./pages/login/img/4.gif)';\r\n");
      out.write("\t$('#lg_bg').css('background-image',bg);\r\n");
      out.write("\r\n");
      out.write("});\r\n");
      out.write("function checkUserName(){\r\n");
      out.write("\tvar userName=$('#userName').attr('value');\r\n");
      out.write("\tvar pwd=$('#password').attr('value');\r\n");
      out.write("\tif(userName==null||userName==''||pwd==null||pwd=='') {\r\n");
      out.write("\t\t$('#ErrorMsg').css('display','');\r\n");
      out.write("\t\t$('#ErrorMsg').html(\"用户名或密码不能为空!\");\r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$.cookie(cookieName, userName, {expires: 100});\r\n");
      out.write("\t\tif($('#lock').attr('checked'))\r\n");
      out.write("\t\t\t$.cookie(cookiePWD, pwd, {expires: 100});\r\n");
      out.write("\t\telse\r\n");
      out.write("\t\t\t$.cookie(cookiePWD,null);\r\n");
      out.write("\t\treturn true;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("function formSubmit(e){\r\n");
      out.write("\tif(e.keyCode==13&&checkUserName()){\r\n");
      out.write("\t\t$(\"form:first\").submit();\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("function Submit(e){\r\n");
      out.write("\tif(checkUserName())\r\n");
      out.write("\t\t$(\"form:first\").submit();\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<div id=\"ieVersion\" style=\"width:100%;height:30px;line-height:30px;text-align:center;color:red;background:#eee;display:none;\">\r\n");
      out.write("\t您的浏览器版本过低，为了您能更好的体验本系统，请您把IE版本更新至IE8！！如需帮助请联系管理员．\r\n");
      out.write("</div>\r\n");
      out.write("<div class=\"wp header clearfix\">\r\n");
      out.write("\t<img src=\"./pages/login/img/logo.gif\"></img>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<form name=\"loginForm\" name=\"loginForm\" action=\"j_spring_security_check\" method=\"post\" onkeypress=\"formSubmit(event);\">\r\n");
      out.write("<div id=\"lg_bg\" class=\"login-frame clearfix\">\r\n");
      out.write("<div class=\"login\">\r\n");
      out.write("\t<ul id=\"login-nav\" class=\"login-nav login-nav-user\">\r\n");
      out.write("\t\t<li class=\"user\"><a href=\"#\">用户登陆</a></li>\r\n");
      out.write("\t</ul>\r\n");
      out.write("\t<div class=\"form-list\">\r\n");
      out.write("\t<ul>\r\n");
      out.write("\t\t<li class=\"tips-li\">\r\n");
      out.write("\t\t\t<div class=\"tips-error\" id=\"ErrorMsg\" style=\"display:none;\">用户名或密码错误，请重新登录</div>\r\n");
      out.write("\t\t</li>\r\n");
      out.write("\t\t<li>\r\n");
      out.write("\t\t\t<label class=\"tit\" for=\"txtName\">用户名</label>\r\n");
      out.write("\t\t\t<div class=\"form-right\">\r\n");
      out.write("\t\t\t\t<input name=\"j_username\" id=\"userName\" tabindex=\"1\" class=\"input-1\" maxlength=\"20\" type=\"text\"/>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</li>\r\n");
      out.write("\t\t<li>\r\n");
      out.write("\t\t\t<label class=\"tit\" for=\"txtPass\">密码</label>\r\n");
      out.write("\t\t\t<div class=\"form-right\">\r\n");
      out.write("\t\t\t\t<span id=\"securityContainer\"></span>\r\n");
      out.write("\t\t\t\t<input name=\"j_password\" id=\"password\" tabindex=\"2\" class=\"input-1\"\tmaxlength=\"20\" type=\"password\" />\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</li>\r\n");
      out.write("\t\t<li>\r\n");
      out.write("\t\t\t<label for=\"lock\" class=\"tit\"><input id=\"lock\" type=\"checkbox\" style=\"vertical-align: middle\" title=\"为了您的信息安全，请不要在网吧或公用电脑上使用。 \"/>记住密码</label>\r\n");
      out.write("\t\t</li>\r\n");
      out.write("\t\t<li class=\"btn-li clearfix\">\r\n");
      out.write("\t\t\t<input name=\"BtnLogin\" id=\"BtnLogin\" class=\"login-submit\" value=\"登&nbsp;&nbsp;录\" type=\"button\" onclick=\"Submit(event);return false;\"/>\r\n");
      out.write("\t\t</li>\r\n");
      out.write("\t</ul>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("</div>\r\n");
      out.write("</form>\r\n");
      out.write("<div class=\"footv4\">\r\n");
      out.write("<div class=\"ftul\">\r\n");
      out.write("<a href=\"#\">分辨率:1024*768以上</a>|\r\n");
      out.write("<a href=\"#\">浏览器IE8及以上版本</a>|\r\n");
      out.write("<a href=\"#\">flash10.0及以上版本</a>|\r\n");
      out.write("<a href=\"swf/数据模板.xls\" style=\"color:#fd6c01\">业务数据模板下载</a>|\r\n");
      out.write("<a href=\"swf/券卡数据模板.xls\" style=\"color:#fd6c01\">券卡数据模板下载</a>|\r\n");
      out.write("<a href=\"#\">系统帮助</a>\r\n");
      out.write("</div>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
