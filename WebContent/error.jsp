<%@ page contentType="text/html;charset=GB2312" isErrorPage="true" %>  
<%@ page import="java.io.PrintWriter" %>
<html>
<head>
<title>系统出理错误---请联系管理员!!!!!!!</title>
</head>
<body>
<h2>errorPage</h2>
<p>错误产生：<I><%= exception %></I></p><br>
<pre>
问题如下：<% exception.printStackTrace(new PrintWriter(out)); %> //输出错误的原因
</pre>
</body>
</html>
