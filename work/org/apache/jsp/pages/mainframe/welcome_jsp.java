package org.apache.jsp.pages.mainframe;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;

public final class welcome_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/META-INF/c.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_005ftest;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fif_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fif_005ftest.release();
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
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>华数人力资源管理系统</title>\r\n");
      out.write("<base href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${baseUrl }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"/>\r\n");
      out.write("<meta http-equiv=\"pragma\" content=\"no-cache\"/>\r\n");
      out.write("<meta http-equiv=\"cache-control\" content=\"no-cache\"/>\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"0\"/>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"skins/css/frame.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"skins/css/style.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"skins/css/form.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"plugin/tree/zTreeStyle.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"plugin/tree/zTreeIcons.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"skins/css/jquery-ui-1.8.15.custom.css\"/>\r\n");
      out.write("\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write(".column { float: left; padding-bottom: 10px;display:inline;width:50%;}\r\n");
      out.write(".portlet { margin: 0 5px 10px 5px;border:1px solid #ddd; }\r\n");
      out.write(".portlet-header {padding-left:10px; border:0px;height:30px;line-height:30px;}\r\n");
      out.write(".portlet-header .ui-icon { float: right;cursor: pointer; }\r\n");
      out.write(".portlet-content { padding: 0.2em; }\r\n");
      out.write(".ui-sortable-placeholder { border: 1px dotted black; visibility: visible !important; height: 300px !important; }\r\n");
      out.write(".ui-sortable-placeholder * { visibility: hidden; }\r\n");
      out.write("\r\n");
      out.write("table.static-table {width: 100%;table-layout: fixed;border-top:1px solid #E8E8E8;border-right:1px solid #E8E8E8;}\r\n");
      out.write("table.static-table tr:HOVER {background:#f8f8f8;}\r\n");
      out.write("table.static-table th {background:#f8f8f8;border-left:1px solid #E8E8E8;border-bottom:1px solid #E8E8E8;height: 30px;font-weight:normal;overflow: hidden;white-space: nowrap;word-break: keep-all;word-wrap: normal;text-overflow: ellipsis;-o-text-overflow: ellipsis;}\r\n");
      out.write("table.static-table td {border-left:1px solid #E8E8E8;border-bottom:1px solid #E8E8E8;qdisplay: block;height: 25px;line-height: 25px;padding:0px 5px;overflow: hidden;white-space: nowrap;word-break: keep-all;word-wrap: normal;text-overflow: ellipsis;-o-text-overflow: ellipsis;}\r\n");
      out.write("table.static-table .index {background:#f8f8f8;}\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"skins/js/jquery-1.4.4.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"skins/js/public.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"skins/js/jquery.json-2.2.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"skins/js/jquery-ui-1.8.5.custom.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"plugin/tree/jquery.ztree.all.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"plugin/form/jquery.form.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"plugin/form/jquery.validate.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"plugin/form/lib/jquery.metadata.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"pages/mainframe/welcome.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"pages/tree.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"skins/js/AnyChart.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var userid=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${userid}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\";\r\n");
      out.write("var companyid=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${companyid}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\";\r\n");
      out.write("$(document).ready(function () {\r\n");
      out.write("\t\r\n");
      out.write("    pageReload();\r\n");
      out.write("\t\r\n");
      out.write("\t$(\".portlet\").addClass(\"ui-corner-all\")\r\n");
      out.write("\t\t.find(\".portlet-header\")\r\n");
      out.write("\t\t.addClass( \"ui-widget-header ui-corner-all\" )\r\n");
      out.write("\t\t.prepend( \"<span class='ui-icon ui-icon-carat-1-s'></span>\")\r\n");
      out.write("\t\t.end();\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\t$(\".portlet-header .ui-icon\").click(function() {\r\n");
      out.write("\t\t$(this).toggleClass(\"ui-icon-carat-1-s\").toggleClass(\"ui-icon-carat-1-n\");\r\n");
      out.write("\t\t$(this).parents(\".portlet:first\").find(\".portlet-content\").toggle();\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t  //日期\r\n");
      out.write("    $(\".datepicker\").datepicker({\r\n");
      out.write("        dateFormat: 'yy-mm-dd'\r\n");
      out.write("    });\r\n");
      out.write("  \r\n");
      out.write("\t\r\n");
      out.write("\t //关闭等待层\r\n");
      out.write("    if(window.parent.hidenLoading)\r\n");
      out.write("    \twindow.parent.hidenLoading();\r\n");
      out.write("});\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function pageReload(){\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${recruitprogram==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\t//招聘计划审批\r\n");
      out.write("\t\t$(\"#recruitprogram\").html('<img src=\"skins/images/ajax-loader-small.gif\"/>');\r\n");
      out.write("\t\tgetRecruitprogramTodo();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${affirm==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\t//待认定的简历\r\n");
      out.write("\t\t$(\"#affirm\").html('<img src=\"skins/images/ajax-loader-small.gif\"/>');\r\n");
      out.write("\t\tgetAffirmMyCandidatesTodo();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${interview==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\t//待安排的面试\r\n");
      out.write("\t\t$(\"#interview\").html('<img src=\"skins/images/ajax-loader-small.gif\"/>');\r\n");
      out.write("\t\tgetInterviewTodo();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${audition==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\t//面试人员列表\r\n");
      out.write("\t\t$(\"#audition\").html('<img src=\"skins/images/ajax-loader-small.gif\"/>');\r\n");
      out.write("\t\tgetAuditionTodo();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${result==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\t//面试结果的反馈\r\n");
      out.write("\t\t$(\"#result\").html('<img src=\"skins/images/ajax-loader-small.gif\"/>');\r\n");
      out.write("\t\tgetAffirmAuditionResultsTodo();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${examination==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\t//待安排的体检\r\n");
      out.write("\t\t$(\"#examination\").html('<img src=\"skins/images/ajax-loader-small.gif\"/>');\r\n");
      out.write("\t\tgetExaminationTodo();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${entryOk==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\t//待入职的应聘者\r\n");
      out.write("\t\t$(\"#entryOk\").html('<img src=\"skins/images/ajax-loader-small.gif\"/>');\r\n");
      out.write("\t\tgetEntryOkTodo();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${entry==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\t//待确定入职的员工\r\n");
      out.write("\t\t$(\"#entry\").html('<img src=\"skins/images/ajax-loader-small.gif\"/>');\r\n");
      out.write("\t\tgetEntryTodo();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t//异动待生效的员工\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${employeeposation==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\t$(\"#employeePosation\").html('<img src=\"skins/images/ajax-loader-small.gif\"/>');\r\n");
      out.write("\t\tgetEmployeePosationTodo();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t//待转正的员工\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${employeezhuzheng==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\t$(\"#employeezhuzheng\").html('<img src=\"skins/images/ajax-loader-small.gif\"/>');\r\n");
      out.write("\t\tgetEmployeeZhuZhengTodo();\r\n");
      out.write("\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\r\n");
      out.write("\t//合同将要到期的员工\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${employeecontract==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\t$(\"#employeeContract\").html('<img src=\"skins/images/ajax-loader-small.gif\"/>');\r\n");
      out.write("\t\tgetEmployeeContractTodo();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t// 我的应聘状态统计图\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${mycandidateschart==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\t$(\"#mycandidatesstate\").html('<img src=\"skins/images/ajax-loader-small.gif\"/>');\r\n");
      out.write("\t\tloadMyCandidatesChart();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t//来源渠道统计表\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${mycandidatesTypechart==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\t$(\"#mycandidatestype\").html('<img src=\"skins/images/ajax-loader-small.gif\"/>');\r\n");
      out.write("\t\tloadMyCandidatesTypeChart();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\t//体系编制情况统计图表\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bzqkchart==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\tloadBZQKChart(null);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t//当日投递情况\r\n");
      out.write("\tif(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${deliver==true}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("){\r\n");
      out.write("\t\tgetDeliverTodo();\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//切换视图\r\n");
      out.write("function convertView(url){\r\n");
      out.write("\tif ($(\".sort\").css(\"display\")!=\"none\") {\r\n");
      out.write("\t\t$(\".sort\").hide();\r\n");
      out.write("\t\t$(\"#detail\").show();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"#detail\").attr(\"src\",url);\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\t$(\"#detail\").height(0);\r\n");
      out.write("\t\t$(\"#detail\").removeAttr(\"src\");\r\n");
      out.write("\t\t$(\".sort\").show();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t//计算高度\r\n");
      out.write("\t\t_autoHeight();\r\n");
      out.write("\t\tif(url==null)\r\n");
      out.write("\t\t\tpageReload();\r\n");
      out.write("  }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<div class=\"sort\">\r\n");
      out.write("\t<div class=\"sort-title\">\r\n");
      out.write("\t\t<h3>首页</h3>\r\n");
      out.write("\t\t<div class=\"title-ctrl\">\r\n");
      out.write("\t\t\t<a class=\"btn-ctrl\" href=\"javascript:chevronUpDown('.sort-cont',false);\"><i class=\"icon icon-chevron-up\"></i></a>\r\n");
      out.write("\t\t\t<a class=\"btn-ctrl\" href=\"javascript:chevronUpDown('.sort-cont',true);\"><i class=\"icon icon-chevron-down\"></i></a>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t<div class=\"sort-cont sort-table\">\r\n");
      out.write("\t\t<div class=\"table\" style=\"padding-top:10px;\">\r\n");
      out.write("\t\t\t<div class=\"table-wrapper\">\r\n");
      out.write("\t\t\t    <div id=\"mycandidatesstate\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div id=\"myBZQKChart\">\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div class=\"column\" id=\"column1\">\r\n");
      out.write("\t\t\t\t\t<div id=\"deliver\">\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div id=\"recruitprogram\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div id=\"affirm\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div id=\"interview\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div id=\"audition\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div id=\"employeePosation\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div id=\"employeezhuzheng\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"column\" id=\"column2\">\r\n");
      out.write("\t\t\t\t\t <div id=\"mycandidatestype\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div id=\"result\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div id=\"examination\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div id=\"entryOk\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div id=\"entry\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div id=\"employeeContract\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t    \r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("<iframe id=\"detail\" name=\"detail\" width=\"100%\" height=\"100%\" frameborder=\"0\" src=\"\" scrolling=\"no\" style=\"display:none;\"></iframe>\r\n");
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

  private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent(null);
    // /pages/mainframe/welcome.jsp(224,4) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${bzqkchart==true}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t<div class=\"portlet\">\r\n");
        out.write("\t\t\t\t\t\t<div class=\"portlet-header ui-widget-header\"><span class=\"icon icon-bookmark\"></span>\r\n");
        out.write("\t\t\t\t\t\t\t<select onchange = \"changeChart(this)\" style=\"height:80%;text-algin:center;font-weight:bold;\">\r\n");
        out.write("\t\t\t\t\t\t\t\t<option value=\"1\" id=\"bztjt\">体系编制情况统计图</option>\r\n");
        out.write("\t\t\t\t\t\t\t\t<option value=\"2\" id=\"requirt\">招聘计划统计图</option>\r\n");
        out.write("\t\t\t\t\t\t\t\t</select>\r\n");
        out.write("\t\t\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t\t<div id=\"BZQKContent\"></div>\r\n");
        out.write("\t\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t\t");
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
}
