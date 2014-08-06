package org.apache.jsp.pages.mainframe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class main_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/META-INF/c.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fif_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fchoose = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fwhen_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fotherwise = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fif_005ftest.release();
    _005fjspx_005ftagPool_005fc_005fchoose.release();
    _005fjspx_005ftagPool_005fc_005fwhen_005ftest.release();
    _005fjspx_005ftagPool_005fc_005fotherwise.release();
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
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>华数人力资源管理系统V2.0</title>\r\n");
      out.write("<base href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${baseUrl }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"/>\r\n");
      out.write("<meta http-equiv=\"pragma\" content=\"no-cache\"/>\r\n");
      out.write("<meta http-equiv=\"cache-control\" content=\"no-cache\"/>\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"0\"/>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"skins/css/frame.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"skins/css/style.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"skins/css/form.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"skins/css/jquery-ui-1.8.15.custom.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"plugin/tree/zTreeStyle.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"plugin/tree/zTreeIcons.css\"/>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"skins/js/jquery-1.4.4.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"skins/js/jquery.json-2.2.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"skins/js/public.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"skins/js/jquery-ui-1.8.5.custom.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"plugin/tree/jquery.ztree.all.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"plugin/form/jquery.form.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"plugin/form/jquery.validate.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"plugin/form/lib/jquery.metadata.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"pages/mainframe/sdmenu.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"pages/mainframe/menu.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"pages/tree.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("$(document).ready(function () {\r\n");
      out.write("\tminWidth();\r\n");
      out.write("\t//加载菜单\r\n");
      out.write("   loadMyFunction();\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("   Array.prototype.strip = function () {            \r\n");
      out.write("\t   if (this.length < 2) return [this[0]] || [];            \r\n");
      out.write("\t   var arr = [];            \r\n");
      out.write("\t   for (var i = 0; i < this.length; i++) {                \r\n");
      out.write("\t\t   arr.push(this.splice(i--, 1));                \r\n");
      out.write("\t\t   for (var j = 0; j < this.length; j++) {                    \r\n");
      out.write("\t\t\t   if (this[j] == arr[arr.length - 1]) {                        \r\n");
      out.write("\t\t\t\t   this.splice(j--, 1);                    \r\n");
      out.write("\t\t\t   }                \r\n");
      out.write("\t\t   }            \r\n");
      out.write("\t   }            \r\n");
      out.write("\t   return arr;        \r\n");
      out.write("   }\r\n");
      out.write("\t\r\n");
      out.write("   \r\n");
      out.write("\t//欢迎页面\r\n");
      out.write("   var chg=$(\"body\").outerHeight()-220;\r\n");
      out.write("\tif(chg<=500){\r\n");
      out.write("\t\t$(\"#ContentFrame\").height(\"500\");\r\n");
      out.write("\t}else\r\n");
      out.write("  \t\t$(\"#ContentFrame\").height(chg);\r\n");
      out.write("   $(\"#ContentFrame\").attr(\"src\",\"index.do?page=welcome\");\r\n");
      out.write("    \r\n");
      out.write("});\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//设置页面的最小宽度\r\n");
      out.write("function minWidth(){\r\n");
      out.write("\tvar pageWidth = document.documentElement.clientWidth || document.body.clientWidth;\r\n");
      out.write("\tvar minWidth = 1000;\r\n");
      out.write("\t(pageWidth < 1000) ? $(\"body\").width(\"1000px\") : $(\"body\").width(\"100%\");\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("$(window).resize(function(){\r\n");
      out.write("\tsetTimeout(minWidth,10);\r\n");
      out.write("})\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//滚动条回到顶部\r\n");
      out.write("function goBodyTop(){\r\n");
      out.write("\t$(\"body,html\").scrollTop(0);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("//滚动条回到中间\r\n");
      out.write("function goCenter(){\r\n");
      out.write("\tvar height=$(\"body\").outerHeight();\r\n");
      out.write("\t$(\"body,html\").scrollTop(height/2-240);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function hiddenInfo(event,obj){\r\n");
      out.write("\tvar div = document.getElementById(obj.id);\r\n");
      out.write("\tvar x=event.clientX;\r\n");
      out.write("\tvar y=event.clientY;\r\n");
      out.write("\tvar divx1 = div.offsetLeft;\r\n");
      out.write("\tvar divy1 = div.offsetTop;\r\n");
      out.write("\tvar divx2 = div.offsetLeft + div.offsetWidth;\r\n");
      out.write("\tvar divy2 = div.offsetTop + div.offsetHeight;\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\tif( (x < divx1 || x > divx2 || y < divy1 || y > divy2)&&x!=-1&&y!=-1){\r\n");
      out.write("\t\t$(\"#msgPanel\").hide();\r\n");
      out.write("\t\t$(\"#notePanel\").hide();\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function showInfo(){\r\n");
      out.write("\t$(\"#msgtable\").css({display:\"block\"});\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function hiddenInfo(){\r\n");
      out.write("\t$(\"#msgtable\").css({display:\"none\"});\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body style=\"overflow:auto;\" id=\"HOHO\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!--Header-->\r\n");
      out.write("<div id=\"Header\">\r\n");
      out.write("\t<div class=\"header-main\">\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t<div class=\"header-bar\">\r\n");
      out.write("\t\t<ul style=\"line-height:30px\">\r\n");
      out.write("\t\t\t<li>\r\n");
      out.write("\t\t\t\t<div class=\"chout\">\r\n");
      out.write("\t\t\t\t\t<a href=\"index.do\" style=\"color:#fff;\"><img src=\"skins/images/shield.png\"/>系统首页</a>\r\n");
      out.write("\t\t\t\t\t&nbsp;\r\n");
      out.write("\t\t\t\t\t&nbsp;\r\n");
      out.write("\t\t         \t<!-- <a href=\"swf/业务数据模板.xls\" style=\"color:#fff;\"><img src=\"skins/images/new.png\"/>数据模板</a>\r\n");
      out.write("\t\t\t\t\t&nbsp;\r\n");
      out.write("\t\t\t\t\t&nbsp;\r\n");
      out.write("\t\t\t\t\t<a href=\"swf/人事手册.rar\" style=\"color:#fff;\"><img src=\"skins/images/book_open.png\"/>人事手册</a>\r\n");
      out.write("\t\t\t\t\t&nbsp;\r\n");
      out.write("\t\t\t\t\t&nbsp;\r\n");
      out.write("\t\t\t\t\t<a href=\"swf/招聘手册.rar\" style=\"color:#fff;\"><img src=\"skins/images/book_open.png\"/>招聘手册</a>\r\n");
      out.write("\t\t\t\t\t&nbsp;\r\n");
      out.write("\t\t\t\t\t&nbsp;\r\n");
      out.write("\t\t\t\t\t<a href=\"http://125.210.208.60:9080/bookweb/book/home.do\" style=\"color:#fff;\"><img src=\"skins/images/weather_sun.png\"/>阳光书屋</a>\r\n");
      out.write("\t\t\t\t\t&nbsp;\r\n");
      out.write("\t\t\t\t\t&nbsp; -->\r\n");
      out.write("\t\t\t\t\t<a href=\"address.do\" style=\"color:#fff;\"><img src=\"skins/images/group.png\"/>通讯录</a>\r\n");
      out.write("\t\t\t\t\t&nbsp;\r\n");
      out.write("\t\t\t\t\t&nbsp;\r\n");
      out.write("\t\t\t\t\t<a href=\"j_spring_security_logout\" style=\"color:#fff;\"><img src=\"skins/images/cross.png\"/>注销</a> \r\n");
      out.write("\t\t         </div>\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t\t<li style=\"padding-top:10px;color:#fff;\">\r\n");
      out.write("\t\t\t\t");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${companyname }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write('/');
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${deptname }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write('：');
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${username }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write(" \r\n");
      out.write("\t\t\t\t&nbsp;\r\n");
      out.write("\t\t\t\t&nbsp;\r\n");
      out.write("\t\t\t</li>\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!--Wrapper-->\r\n");
      out.write("<div id=\"Wrapper\">\r\n");
      out.write("\t<!--Left-->\r\n");
      out.write("\t<div id=\"Sider\">\r\n");
      out.write("\t\t<div id=\"Menu\">\r\n");
      out.write("\t\t\t<div class=\"fixt\"></div>\r\n");
      out.write("\t\t\t<div class=\"sdmenu\">\r\n");
      out.write("\t\t\t\t<span id=\"menus\" >\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t</span>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"fixb\"></div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t<!--Content-->\r\n");
      out.write("\t<div id=\"Content\">\r\n");
      out.write("\t\t<iframe id=\"ContentFrame\" name=\"ContentFrame\" src=\"\" scrolling=\"no\" frameborder=\"0\" width=\"100%\" frameborder=\"0\" height=\"100px\"></iframe>\r\n");
      out.write("\t\t<br>\r\n");
      out.write("\t\t<br>\r\n");
      out.write("\t\t<br>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!--Footer\r\n");
      out.write("<div id=\"Footer\" class=\"bottom\">\r\n");
      out.write("\t<div>\r\n");
      out.write("\t  <h1>华数人力资源管理系统&nbsp;</h1>\r\n");
      out.write("\t  <h3>@2013-2014华数版权所有&nbsp;&nbsp;</h3>\r\n");
      out.write("\t  <h3 style=\"float:right;\">技术支持：浙江美科科技公司</h3>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("-->\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- 消息框提示 -->\r\n");
      out.write("<style>\r\n");
      out.write("#msg{\r\n");
      out.write("height: 31px;\r\n");
      out.write("position:fixed; \r\n");
      out.write("right:10px; \r\n");
      out.write("bottom:0px; \r\n");
      out.write("cursor:pointer;\r\n");
      out.write("_position:absolute;\r\n");
      out.write("_bottom:auto;\r\n");
      out.write("_top:expression(eval(document.documentElement.scrollTop+document.documentElement.clientHeight-this.offsetHeight-(parseInt(this.currentStyle.marginTop,10)||0)-(parseInt(this.currentStyle.marginBottom,10)||0)));\r\n");
      out.write("_margin-bottom:0px;\r\n");
      out.write("WIDTH:350PX;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#msg .msgcontent{\r\n");
      out.write("\r\n");
      out.write("background:#D6D6D6 url(\"skins/images/alert.png\") no-repeat;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("#J_BrandBar {\r\n");
      out.write("    background-color: #EEEEEE;\r\n");
      out.write("    border: 1px solid #D0D0D0;\r\n");
      out.write("    border-radius: 3px 3px 0 0;\r\n");
      out.write("    cursor: pointer;\r\n");
      out.write("    font-size: 12px;\r\n");
      out.write("    height: 28px;\r\n");
      out.write("    margin: 2px 5px 0;\r\n");
      out.write("    width: 95px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".BrandFlyer {\r\n");
      out.write("    background: url(\"skins/images/SmsSend.png\") no-repeat scroll 0 0 transparent;\r\n");
      out.write("    float: left;\r\n");
      out.write("    height: 20px;\r\n");
      out.write("    margin: 4px 0 4px 12px;\r\n");
      out.write("    width: 20px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write(".tm_cmbar a{\r\n");
      out.write("    color: #181818;\r\n");
      out.write("    display: block;\r\n");
      out.write("    float: left;\r\n");
      out.write("    line-height: 20px;\r\n");
      out.write("    margin: 4px 4px 4px 0;\r\n");
      out.write("    text-decoration: none;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".tm_cmbar_clearfix:after, .tm_cmbar_clearfix:before {\r\n");
      out.write("    content: \"\";\r\n");
      out.write("    display: table;\r\n");
      out.write("    overflow: hidden;\r\n");
      out.write("}\r\n");
      out.write(".tm_cmbar_clearfix:after, .tm_cmbar_clearfix:before {\r\n");
      out.write("    content: \"\";\r\n");
      out.write("    display: table;\r\n");
      out.write("    overflow: hidden;\r\n");
      out.write("}\r\n");
      out.write(".tm_cmbar_clearfix:after {\r\n");
      out.write("    clear: both;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//短信信息提示框\r\n");
      out.write("function showNote(names,mobiles){\r\n");
      out.write("\t\r\n");
      out.write("\tposationNote(names,mobiles);\r\n");
      out.write("\t\r\n");
      out.write("\tvar panel=$(\"#notePanel\");\r\n");
      out.write("\tvar flag=panel.css(\"display\");\r\n");
      out.write("\t\r\n");
      out.write("\tif(flag=='none'||mobiles==null)\r\n");
      out.write("\t\tpanel.toggle();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//短信框计算位置\r\n");
      out.write("function posationNote(names,mobiles){\r\n");
      out.write("\tvar cityObj = $(\".msgcontent\");\r\n");
      out.write("\tvar cityOffset = cityObj.offset();\r\n");
      out.write("    if(mobiles!=null&&mobiles!=\"\"){\r\n");
      out.write("    \tvar phonesum=$(\"#phonenum_sum\").val();\r\n");
      out.write("    \tvar phonenumname=$(\"#phonenum_name\").val();\r\n");
      out.write("    \tif(phonenumname==''||phonenumname==null){\r\n");
      out.write("    \t\t$(\"#phonenum_name\").val(names);\r\n");
      out.write("    \t\t$(\"#phonenum_sum\").val(mobiles);\r\n");
      out.write("    \t}else{\r\n");
      out.write("    \t\tphonesum=phonesum+\",\"+mobiles;\r\n");
      out.write("    \t\tvar t1 = phonesum.split(',');                \r\n");
      out.write("    \t\t$(\"#phonenum_sum\").val(t1.strip());\r\n");
      out.write("    \t\t\r\n");
      out.write("    \t\tphonenumname=phonenumname+\",\"+names;\r\n");
      out.write("    \t\tvar t2 = phonenumname.split(',');                \r\n");
      out.write("    \t\t$(\"#phonenum_name\").val(t2.strip());\r\n");
      out.write("    \t}\r\n");
      out.write("    \t$(\"#msgcontent_sum\").val(\"\");\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$(\"#phonenum_sum\").val(\"\");\r\n");
      out.write("\t\t$(\"#phonenum_name\").val(\"\");\r\n");
      out.write("\t\t$(\"#msgcontent_sum\").val(\"\");\r\n");
      out.write("\t}\r\n");
      out.write("\tvar panel=$(\"#notePanel\");\r\n");
      out.write("\tpanel.css({\"left\":cityOffset.left+3 + \"px\"});\r\n");
      out.write("\tpanel.css({\"top\":cityOffset.top-panel.outerHeight()-$(document).scrollTop() +\"px\"});\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//发送\r\n");
      out.write("function sendMsg(){\r\n");
      out.write("\tvar phonenum_sum = $(\"#phonenum_sum\").val();\r\n");
      out.write("\tvar msgcontent_sum = $(\"#msgcontent_sum\").val();\r\n");
      out.write("\tif(phonenum_sum==null||phonenum_sum==\"\"||msgcontent_sum==null||msgcontent_sum==\"\"){\r\n");
      out.write("\t\talert(\"信息不能为空!\");\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\t$.post(\"message/sendMessage_Sum.do\",{phonenum_sum:phonenum_sum,msgcontent_sum:msgcontent_sum}, function() {\r\n");
      out.write("\t\talert(\"发送成功！\");\r\n");
      out.write("\t\tvar panel=$(\"#notePanel\");\r\n");
      out.write("\t\tpanel.toggle();\r\n");
      out.write("    });\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("\t\r\n");
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

  private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent(null);
    // /pages/mainframe/main.jsp(291,0) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg!=null}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("<!-- <div id=\"msg\" onMouseOver=\"showInfo();\" onMouseOut=\"hiddenInfo()\"> -->\r\n");
        out.write("<!-- <table width=\"100%\" style=\"display: none;\" id=\"msgtable\"> -->\r\n");
        out.write("<div id=\"msg\" >\r\n");
        out.write("\t<table width=\"100%\" id=\"msgtable\">\r\n");
        out.write("\t\t<tr>\r\n");
        out.write("\t\t\t<td width=\"35\" valign=\"top\">\r\n");
        out.write("\t\t\t\t<!--<a href=\"javascript:showNote(null);\">\r\n");
        out.write("\t\t\t\t        <img src=\"skins/images/sms.png\" height=\"31px\"/>\r\n");
        out.write("\t\t\t\t\t</a>-->\r\n");
        out.write("\t\t\t\t\t\r\n");
        out.write("\t\t\t\t   <div id=\"J_BrandBar\" class=\"tm_cmbar_clearfix tm_cmbar\" order=\"20\">\r\n");
        out.write("                         <div class=\"BrandFlyer\"></div>\r\n");
        out.write("                          <a href=\"javascript:showNote(null);\">短信平台</a>\r\n");
        out.write("                   </div>\r\n");
        out.write("\t\t\t\t\t\r\n");
        out.write("\t\t\t\t</td>\r\n");
        out.write("\t\t\t<td class=\"msgcontent\">\r\n");
        out.write("\t\t\t\t<table width=\"100%\" border=\"0\" style=\"color:#fff;\" class=\"static-table-noline\">\r\n");
        out.write("\t\t\t\t\t<tr>\r\n");
        out.write("\t\t\t\t\t\t<td width=\"20\" align=\"right\">\r\n");
        out.write("\t\t\t\t\t\t\t<b id=\"news\">");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.news}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("</b>\r\n");
        out.write("\t\t\t\t\t\t</td>\r\n");
        out.write("\t\t\t\t\t\t<td width=\"5\">\r\n");
        out.write("\t\t\t\t\t\t<span>|</span>\r\n");
        out.write("\t\t\t\t\t\t</td>\r\n");
        out.write("\t\t\t\t\t\t<td>\r\n");
        out.write("\t\t\t\t\t\t\t<a href=\"javascript:showMsg();\" style=\"color:#fff;display:block;width:90%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;\"><img src=\"skins/images/d_new.gif\"/><span id=\"newsmsg\" title=\"");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.newsmsg}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write('"');
        out.write('>');
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.newsmsg}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("</span></a>\r\n");
        out.write("\t\t\t\t\t\t</td>\r\n");
        out.write("\t\t\t\t\t</tr>\r\n");
        out.write("\t\t\t\t</table>\r\n");
        out.write("\t\t\t</td>\r\n");
        out.write("\t\t</tr>\r\n");
        out.write("\t</table>\r\n");
        out.write("</div>\r\n");
        out.write("<!-- <div id=\"msgPanel\" style=\"background:#D6D6D6;position:fixed ;z-index: 888;width:240px;display:none;\" onmouseout=\"hiddenInfo(event,this)\"> -->\r\n");
        out.write("\t<div id=\"msgPanel\" style=\"background:#D6D6D6;position:fixed ;z-index: 888;width:240px;display:none;\">\r\n");
        out.write("\t<div style=\"padding:5px 20px;\">\r\n");
        out.write("\t\t<table width=\"100%\" border=\"0\" class=\"static-table-noline\">\r\n");
        out.write("\t\t\t<tr>\r\n");
        out.write("\t\t\t");
        if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
          return true;
        out.write("\t\r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("\t\t\t<tr style=\"display:none\">\r\n");
        out.write("\t\t\t\t<td>\r\n");
        out.write("\t\t\t\t\t<a href=\"mycandidates.do?page=list_dept\" target=\"ContentFrame\">待认定的简历</a>\r\n");
        out.write("\t\t\t\t</td>\r\n");
        out.write("\t\t\t\t<td align=\"right\" id=\"affirms\">\r\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.affirms}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t</td>\r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("\t\t\t<tr>\r\n");
        out.write("\t\t\t");
        if (_jspx_meth_c_005fchoose_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("\t\t\t<tr style=\"display:none\">\r\n");
        out.write("\t\t\t\t<td>\r\n");
        out.write("\t\t\t\t\t<a href=\"mycandidates.do?page=list_dept\" target=\"ContentFrame\">面试结果待反馈</a>\r\n");
        out.write("\t\t\t\t</td>\r\n");
        out.write("\t\t\t\t<td align=\"right\" id=\"affirmauditionresults\">\r\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.affirmauditionresults }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t</td>\r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("\t\t\t<tr>\r\n");
        out.write("\t\t\t");
        if (_jspx_meth_c_005fchoose_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("\t\t\t<tr>\r\n");
        out.write("\t\t\t");
        if (_jspx_meth_c_005fchoose_005f3(_jspx_th_c_005fif_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("\t\t\t\r\n");
        out.write("\t\t\t<tr>\r\n");
        out.write("\t\t\t");
        if (_jspx_meth_c_005fchoose_005f4(_jspx_th_c_005fif_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("\t\t\t\r\n");
        out.write("\t\t\t<tr>\r\n");
        out.write("\t\t\t");
        if (_jspx_meth_c_005fchoose_005f5(_jspx_th_c_005fif_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("\t\t</table>\r\n");
        out.write("\t</div>\r\n");
        out.write("</div>\r\n");
        out.write("\r\n");
        out.write("\r\n");
        out.write("<!-- 短信  -->\r\n");
        out.write("<div id=\"notePanel\" style=\"background:#D6D6D6;position:fixed ;z-index: 888;width:240px;display:none;\" >\r\n");
        out.write("\r\n");
        out.write("\t<!-- onmouseout=\"hiddenInfo(event,this)\" -->\r\n");
        out.write("\r\n");
        out.write("\t<div style=\"padding:5px 20px; height: 230px;\">\r\n");
        out.write("\t\t<table width=\"100%\" border=\"0\" class=\"static-table-noline\">\r\n");
        out.write("\t\t  <tr>\r\n");
        out.write("\t\t    <td width=\"47\" align=\"left\" style=\"padding: 0px; margin: 0px;\"> <span>姓名：</td>\r\n");
        out.write("\t\t    <td>\r\n");
        out.write("\t    \t\t <input id=\"phonenum_name\" name=\"phonenum_name\" class=\"inputstyle\" style=\"width: 150px;\" disabled/>\r\n");
        out.write("\t\t     <!--  <input id=\"phonenum_sum\" name=\"phonenum_sum\" class=\"inputstyle\" style=\"width: 150px;\" />-->\r\n");
        out.write("\t\t    </td>\r\n");
        out.write("\t\t  </tr>\r\n");
        out.write("\t\t  <tr>\r\n");
        out.write("\t\t    <td width=\"47\" align=\"left\" style=\"padding: 0px; margin: 0px;\"> <span><em class=\"red\">* </em>手机：</span></td>\r\n");
        out.write("\t\t    <td>\r\n");
        out.write("\t    \t\t<input id=\"phonenum_sum\" name=\"phonenum_sum\" class=\"inputstyle\" style=\"width: 150px;\" />\r\n");
        out.write("\t\t    </td>\r\n");
        out.write("\t\t  </tr>\r\n");
        out.write("\t\t  <tr>\r\n");
        out.write("\t\t    <td width=\"47\" align=\"left\" style=\"padding: 0px; margin: 0px;\">\r\n");
        out.write("\t\t     <span><em class=\"red\">* </em>内容：</span>\r\n");
        out.write("\t\t    </td>\r\n");
        out.write("\t\t    <td>\r\n");
        out.write("\t\t     <textarea id=\"msgcontent_sum\" name=\"msgcontent_sum\"  rows=\"3\" cols=\"20\" style=\"width:150px; height: 130px;word-wrap:break-word; word-break:break-all; \" class=\"areastyle\"></textarea>\r\n");
        out.write("\t\t    </td>\r\n");
        out.write("\t\t  </tr>\r\n");
        out.write("\t\t  <tr>\r\n");
        out.write("\t\t    <td>\r\n");
        out.write("\t\t    </td>\r\n");
        out.write("\t\t    <td align=\"right\">\r\n");
        out.write("\t\t     <a class=\"btn\" href=\"javascript:sendMsg();\"><i class=\"icon icon-envelope\"></i><span>发送</span></a>\r\n");
        out.write("\t\t    </td>\r\n");
        out.write("\t\t  </tr>\r\n");
        out.write("\t\t</table>\r\n");
        out.write("\t</div>\r\n");
        out.write("</div>\r\n");
        out.write("\t\r\n");
        out.write("<script type=\"text/javascript\">\r\n");
        out.write("\r\n");
        out.write("\r\n");
        out.write("\r\n");
        out.write("\r\n");
        out.write("//系统提示消息\r\n");
        out.write("function showMsg(){\r\n");
        out.write("\tposationMsg();\r\n");
        out.write("\t\r\n");
        out.write("\tvar panel1=$(\"#notePanel\");\r\n");
        out.write("\tvar flag=panel1.css(\"display\");\r\n");
        out.write("\t\r\n");
        out.write("\tif(flag=='block')\r\n");
        out.write("\t\tpanel1.toggle();\r\n");
        out.write("\t\r\n");
        out.write("\t\r\n");
        out.write("\tvar panel=$(\"#msgPanel\");\r\n");
        out.write("\tpanel.toggle();\r\n");
        out.write("}\r\n");
        out.write("\r\n");
        out.write("\r\n");
        out.write("\r\n");
        out.write("//计算位置\r\n");
        out.write("function posationMsg(){\r\n");
        out.write("\tvar cityObj = $(\".msgcontent\");\r\n");
        out.write("\tvar cityOffset = cityObj.offset();\r\n");
        out.write("\t\r\n");
        out.write("\tvar panel=$(\"#msgPanel\");\r\n");
        out.write("\tpanel.css({\"left\":cityOffset.left+3 + \"px\"});\r\n");
        out.write("\tpanel.css({\"top\":cityOffset.top-panel.outerHeight()-$(document).scrollTop() +\"px\"});\r\n");
        out.write("}\r\n");
        out.write("\r\n");
        out.write("\r\n");
        out.write("\r\n");
        out.write("\r\n");
        out.write("//获取信息\r\n");
        out.write("function getMsg(){\r\n");
        out.write("\tvar pam={parameters:{}};\r\n");
        out.write("\t$.ajax({  \r\n");
        out.write("\t\turl:\"todo/getMsgData.do\",\r\n");
        out.write("\t\tcontentType: \"application/json; charset=utf-8\",  \r\n");
        out.write("\t\ttype: \"POST\",  \r\n");
        out.write("\t\tdataType: \"json\",  \r\n");
        out.write("\t\tdata: JSON.stringify(pam),\r\n");
        out.write("\t\tbeforeSend : null,\r\n");
        out.write("\t\tsuccess: function(data) { \r\n");
        out.write("\t\t\tfor(var key in data){\r\n");
        out.write("\t\t\t\t$(\"#\"+key).text(data[key]);\r\n");
        out.write("\t\t\t}\r\n");
        out.write("\t\t}\r\n");
        out.write("\t});\r\n");
        out.write("}\r\n");
        out.write("\r\n");
        out.write("//定时刷新\r\n");
        out.write("var interval = setInterval(getMsg, 30000);\r\n");
        out.write("\r\n");
        out.write("</script>\r\n");
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fchoose_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:choose
    org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f0 = (org.apache.taglibs.standard.tag.common.core.ChooseTag) _005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
    _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fchoose_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f0);
    int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
    if (_jspx_eval_c_005fchoose_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t   ");
        if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t   ");
        if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fchoose_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fwhen_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:when
    org.apache.taglibs.standard.tag.rt.core.WhenTag _jspx_th_c_005fwhen_005f0 = (org.apache.taglibs.standard.tag.rt.core.WhenTag) _005fjspx_005ftagPool_005fc_005fwhen_005ftest.get(org.apache.taglibs.standard.tag.rt.core.WhenTag.class);
    _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fwhen_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f0);
    // /pages/mainframe/main.jsp(332,6) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fwhen_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.hotnews == 1 }", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
    if (_jspx_eval_c_005fwhen_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t <td>\r\n");
        out.write("\t\t\t\t\t<a href=\"recruitprogram.do?page=list\" target=\"ContentFrame\" style=\"color:#CC0000;\">招聘计划审批</a>\r\n");
        out.write("\t\t\t\t </td>\r\n");
        out.write("\t\t\t\t <td align=\"right\" id=\"recruitprograms\" style=\"color:#CC0000;\">\r\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.recruitprograms }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t </td>\r\n");
        out.write("\t\t\t   ");
        int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fwhen_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fwhen_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fwhen_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fotherwise_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:otherwise
    org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (org.apache.taglibs.standard.tag.common.core.OtherwiseTag) _005fjspx_005ftagPool_005fc_005fotherwise.get(org.apache.taglibs.standard.tag.common.core.OtherwiseTag.class);
    _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fotherwise_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f0);
    int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
    if (_jspx_eval_c_005fotherwise_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t <td>\r\n");
        out.write("\t\t\t\t\t<a href=\"recruitprogram.do?page=list\" target=\"ContentFrame\">招聘计划审批</a>\r\n");
        out.write("\t\t\t\t </td>\r\n");
        out.write("\t\t\t\t <td align=\"right\" id=\"recruitprograms\">\r\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.recruitprograms }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t </td>\r\n");
        out.write("\t\t\t   ");
        int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fotherwise_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fchoose_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:choose
    org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f1 = (org.apache.taglibs.standard.tag.common.core.ChooseTag) _005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
    _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fchoose_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f0);
    int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
    if (_jspx_eval_c_005fchoose_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t   ");
        if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t   ");
        if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fchoose_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fwhen_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:when
    org.apache.taglibs.standard.tag.rt.core.WhenTag _jspx_th_c_005fwhen_005f1 = (org.apache.taglibs.standard.tag.rt.core.WhenTag) _005fjspx_005ftagPool_005fc_005fwhen_005ftest.get(org.apache.taglibs.standard.tag.rt.core.WhenTag.class);
    _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fwhen_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f1);
    // /pages/mainframe/main.jsp(360,6) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fwhen_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.hotnews == 2 }", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
    if (_jspx_eval_c_005fwhen_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t  <td>\r\n");
        out.write("\t\t\t\t\t<a href=\"mycandidates.do?page=list\" target=\"ContentFrame\" style=\"color:#CC0000;\">待安排的面试</a>\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t\t  <td align=\"right\" id=\"interviews\" style=\"color:#CC0000;\">\r\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.interviews}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t   ");
        int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fwhen_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fwhen_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fwhen_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fotherwise_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:otherwise
    org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (org.apache.taglibs.standard.tag.common.core.OtherwiseTag) _005fjspx_005ftagPool_005fc_005fotherwise.get(org.apache.taglibs.standard.tag.common.core.OtherwiseTag.class);
    _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fotherwise_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f1);
    int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
    if (_jspx_eval_c_005fotherwise_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t  <td>\r\n");
        out.write("\t\t\t\t\t<a href=\"mycandidates.do?page=list\" target=\"ContentFrame\">待安排的面试</a>\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t\t  <td align=\"right\" id=\"interviews\">\r\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.interviews}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t   ");
        int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fotherwise_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fchoose_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:choose
    org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f2 = (org.apache.taglibs.standard.tag.common.core.ChooseTag) _005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
    _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fchoose_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f0);
    int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
    if (_jspx_eval_c_005fchoose_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t   ");
        if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t   ");
        if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fchoose_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
    return false;
  }

  private boolean _jspx_meth_c_005fwhen_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:when
    org.apache.taglibs.standard.tag.rt.core.WhenTag _jspx_th_c_005fwhen_005f2 = (org.apache.taglibs.standard.tag.rt.core.WhenTag) _005fjspx_005ftagPool_005fc_005fwhen_005ftest.get(org.apache.taglibs.standard.tag.rt.core.WhenTag.class);
    _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fwhen_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f2);
    // /pages/mainframe/main.jsp(388,6) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fwhen_005f2.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.hotnews == 3 }", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
    if (_jspx_eval_c_005fwhen_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t      <td>\r\n");
        out.write("\t\t\t\t\t<a href=\"mycandidates.do?page=list\" target=\"ContentFrame\" style=\"color:#CC0000;\">面试结果待反馈</a>\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t\t  <td align=\"right\" id=\"releases\" style=\"color:#CC0000;\">\r\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.releases}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t   ");
        int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fwhen_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fwhen_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fwhen_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
    return false;
  }

  private boolean _jspx_meth_c_005fotherwise_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:otherwise
    org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (org.apache.taglibs.standard.tag.common.core.OtherwiseTag) _005fjspx_005ftagPool_005fc_005fotherwise.get(org.apache.taglibs.standard.tag.common.core.OtherwiseTag.class);
    _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fotherwise_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f2);
    int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
    if (_jspx_eval_c_005fotherwise_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t      <td>\r\n");
        out.write("\t\t\t\t\t<a href=\"mycandidates.do?page=list\" target=\"ContentFrame\">面试结果待反馈</a>\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t\t  <td align=\"right\" id=\"releases\">\r\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.releases}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t   ");
        int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fotherwise_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
    return false;
  }

  private boolean _jspx_meth_c_005fchoose_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:choose
    org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f3 = (org.apache.taglibs.standard.tag.common.core.ChooseTag) _005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
    _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005fchoose_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f0);
    int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
    if (_jspx_eval_c_005fchoose_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t   ");
        if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t   ");
        if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fchoose_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
    return false;
  }

  private boolean _jspx_meth_c_005fwhen_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:when
    org.apache.taglibs.standard.tag.rt.core.WhenTag _jspx_th_c_005fwhen_005f3 = (org.apache.taglibs.standard.tag.rt.core.WhenTag) _005fjspx_005ftagPool_005fc_005fwhen_005ftest.get(org.apache.taglibs.standard.tag.rt.core.WhenTag.class);
    _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005fwhen_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f3);
    // /pages/mainframe/main.jsp(408,6) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fwhen_005f3.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.hotnews == 4 }", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
    if (_jspx_eval_c_005fwhen_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t      <td>\r\n");
        out.write("\t\t\t\t\t<a href=\"mycandidates.do?page=list\" target=\"ContentFrame\" style=\"color:#CC0000;\">待安排的体检</a>\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t\t  <td align=\"right\" id=\"examinations\" style=\"color:#CC0000;\">\r\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.examinations }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t   ");
        int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fwhen_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fwhen_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fwhen_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
    return false;
  }

  private boolean _jspx_meth_c_005fotherwise_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:otherwise
    org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (org.apache.taglibs.standard.tag.common.core.OtherwiseTag) _005fjspx_005ftagPool_005fc_005fotherwise.get(org.apache.taglibs.standard.tag.common.core.OtherwiseTag.class);
    _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005fotherwise_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f3);
    int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
    if (_jspx_eval_c_005fotherwise_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t      <td>\r\n");
        out.write("\t\t\t\t\t<a href=\"mycandidates.do?page=list\" target=\"ContentFrame\">待安排的体检</a>\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t\t  <td align=\"right\" id=\"examinations\">\r\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.examinations }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t   ");
        int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fotherwise_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
    return false;
  }

  private boolean _jspx_meth_c_005fchoose_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:choose
    org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f4 = (org.apache.taglibs.standard.tag.common.core.ChooseTag) _005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
    _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
    _jspx_th_c_005fchoose_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f0);
    int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
    if (_jspx_eval_c_005fchoose_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t   ");
        if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t   ");
        if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fchoose_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
    return false;
  }

  private boolean _jspx_meth_c_005fwhen_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:when
    org.apache.taglibs.standard.tag.rt.core.WhenTag _jspx_th_c_005fwhen_005f4 = (org.apache.taglibs.standard.tag.rt.core.WhenTag) _005fjspx_005ftagPool_005fc_005fwhen_005ftest.get(org.apache.taglibs.standard.tag.rt.core.WhenTag.class);
    _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
    _jspx_th_c_005fwhen_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f4);
    // /pages/mainframe/main.jsp(429,6) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fwhen_005f4.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.hotnews == 5 }", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
    if (_jspx_eval_c_005fwhen_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t      <td>\r\n");
        out.write("\t\t\t\t\t<a href=\"mycandidates.do?page=list\" target=\"ContentFrame\" style=\"color:#CC0000;\">待入职的应聘者</a>\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t\t  <td align=\"right\" id=\"entryoktodos\" style=\"color:#CC0000;\">\r\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.entryoktodos }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t   ");
        int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fwhen_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fwhen_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fwhen_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
    return false;
  }

  private boolean _jspx_meth_c_005fotherwise_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:otherwise
    org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (org.apache.taglibs.standard.tag.common.core.OtherwiseTag) _005fjspx_005ftagPool_005fc_005fotherwise.get(org.apache.taglibs.standard.tag.common.core.OtherwiseTag.class);
    _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
    _jspx_th_c_005fotherwise_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f4);
    int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
    if (_jspx_eval_c_005fotherwise_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t      <td>\r\n");
        out.write("\t\t\t\t\t<a href=\"mycandidates.do?page=list\" target=\"ContentFrame\">待入职的应聘者</a>\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t\t  <td align=\"right\" id=\"entryoktodos\">\r\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.entryoktodos }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t   ");
        int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fotherwise_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
    return false;
  }

  private boolean _jspx_meth_c_005fchoose_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:choose
    org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f5 = (org.apache.taglibs.standard.tag.common.core.ChooseTag) _005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
    _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
    _jspx_th_c_005fchoose_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f0);
    int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
    if (_jspx_eval_c_005fchoose_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t   ");
        if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t   ");
        if (_jspx_meth_c_005fotherwise_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fchoose_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
    return false;
  }

  private boolean _jspx_meth_c_005fwhen_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:when
    org.apache.taglibs.standard.tag.rt.core.WhenTag _jspx_th_c_005fwhen_005f5 = (org.apache.taglibs.standard.tag.rt.core.WhenTag) _005fjspx_005ftagPool_005fc_005fwhen_005ftest.get(org.apache.taglibs.standard.tag.rt.core.WhenTag.class);
    _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
    _jspx_th_c_005fwhen_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f5);
    // /pages/mainframe/main.jsp(450,6) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fwhen_005f5.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.hotnews == 6 }", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
    if (_jspx_eval_c_005fwhen_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t      <td>\r\n");
        out.write("\t\t\t\t\t<a href=\"recruitprogram.do?page=list\" target=\"ContentFrame\" style=\"color:#CC0000;\">OA信息</a>\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t\t  <td align=\"right\" id=\"releases\" style=\"color:#CC0000;\">\r\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.audit}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t   ");
        int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fwhen_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fwhen_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fwhen_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
    return false;
  }

  private boolean _jspx_meth_c_005fotherwise_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:otherwise
    org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (org.apache.taglibs.standard.tag.common.core.OtherwiseTag) _005fjspx_005ftagPool_005fc_005fotherwise.get(org.apache.taglibs.standard.tag.common.core.OtherwiseTag.class);
    _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
    _jspx_th_c_005fotherwise_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fchoose_005f5);
    int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
    if (_jspx_eval_c_005fotherwise_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t      <td>\r\n");
        out.write("\t\t\t\t\t<a href=\"recruitprogram.do?page=list\" target=\"ContentFrame\">OA信息</a>\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t\t  <td align=\"right\" id=\"releases\">\r\n");
        out.write("\t\t\t\t\t");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${msg.audit}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\r\n");
        out.write("\t\t\t\t  </td>\r\n");
        out.write("\t\t\t   ");
        int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fotherwise_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
    return false;
  }
}
