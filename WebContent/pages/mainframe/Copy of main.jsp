<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<head>
<title>华数人力资源管理系统V2.0</title>
<base href="${baseUrl }"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="skins/css/frame.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/style.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/form.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeIcons.css"/>

<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>

<script type="text/javascript" src="pages/mainframe/sdmenu.js"></script>
<script type="text/javascript" src="pages/mainframe/menu.js"></script>


<script type="text/javascript">

$(document).ready(function () {
	minWidth();
	//加载菜单
   loadMyFunction();
	
   
	//欢迎页面
   var chg=$("body").outerHeight()-220;
	if(chg<=500){
		$("#ContentFrame").height("500");
	}else
  		$("#ContentFrame").height(chg);
   $("#ContentFrame").attr("src","index.do?page=welcome");
    
});




//设置页面的最小宽度
function minWidth(){
	var pageWidth = document.documentElement.clientWidth || document.body.clientWidth;
	var minWidth = 1000;
	(pageWidth < 1000) ? $("body").width("1000px") : $("body").width("100%");
}

$(window).resize(function(){
	setTimeout(minWidth,10);
})





</script>
</head>

<body style="overflow:auto;">


<!--Header-->
<div id="Header">
	<div class="header-main">
		<img src="skins/images/WASU_logo.png" >
	</div>
	
	<div class="header-bar">
		<ul style="line-height:30px">
			<li>
				<div class="chout">
		         	<a href="swf/业务数据模板.xls" style="color:#fff;"><img src="skins/images/new.png"/>数据模板</a>
					&nbsp;
					&nbsp;
					<a href="swf/操作手册.doc" style="color:#fff;"><img src="skins/images/book_open.png"/>操作手册</a>
					&nbsp;
					&nbsp;
					<a href="http://125.210.208.60:9080/bookweb/book/home.do" style="color:#fff;"><img src="skins/images/weather_sun.png"/>阳光书屋</a>
					&nbsp;
					&nbsp;
					<a href="address.do" style="color:#fff;"><img src="skins/images/group.png"/>通讯录</a>
					&nbsp;
					&nbsp;
					
					<!-- &nbsp;
					&nbsp;
					<a href="j_spring_security_logout" style="color:#fff;"><img src="skins/images/cross.png"/>注销</a> -->
		         </div>
			</li>
			<li style="padding-top:10px;color:#fff;">
				${companyname }/${deptname }：${username } 
				&nbsp;
				&nbsp;
			</li>
		</ul>
	</div>
</div>




<!--Wrapper-->
<div id="Wrapper">
	<!--Left-->
	<div id="Sider">
		<div id="Menu">
			<div class="fixt"></div>
			<div id="menus" class="sdmenu">
			
			</div>
			<div class="fixb"></div>
		</div>
	</div>
	
	<!--Content-->
	<div id="Content">
		<iframe id="ContentFrame" name="ContentFrame" src="" scrolling="no" frameborder="0" width="100%" frameborder="0" height="100px"></iframe>
	</div>
</div>

<!--Footer-->
<div id="Footer" class="bottom">
	<div>
	  <h1>华数人力资源管理系统&nbsp;</h1>
	  <h3>@2013-2014华数版权所有&nbsp;&nbsp;</h3>
	  <h3 style="float:right;">技术支持：浙江美科科技公司</h3>
	</div>
</div>
</body>
</html>