<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<!DOCTYPE html>
<html>
<head>
<title>关于华数</title>
<base href="${baseUrl }"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="skins/css/style.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/form.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css"/>

<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>


<script type="text/javascript">

$(document).ready(function() {
	//tab页
	loadTab();
	
	
	//加载数据
	var pid=$("li[index='0']").attr("id");
	$("#detail").attr("src","about.do?page=form&id="+pid);
	
	 //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
	
});



//tab页
var tabIndex=0;
function loadTab(){
	$("#mytab").tabs({
    	select: function(event, ui) {
    		tabIndex=ui.index;
    		//取点击对应标签的id
    		var pid=$("li[index='"+tabIndex+"']").attr("id");
    		$("#detail").attr("src","about.do?page=form&id="+pid); 
    	}
    });
}






</script>


</head>
<body>

<div class="sort">
	<div class="sort-title">
		<h3>关于华数</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>
	
	<div class="sort-cont sort-table">
		<div id="mytab">
			<ul>
				<c:forEach items="${tabs}" var="item" varStatus="idx">
					<li id='${item.aboutguid}' index="${idx.index }"><a href="#tab0">${item.aboutname }</a></li>
				</c:forEach>
			</ul>
			<div id="tab0">
				<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" scrolling="no" src="" ></iframe>
				<br/>
				<br/>
			</div>
		</div>
	</div>
</div>




</body>
</html>