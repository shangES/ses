<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>电子简历</title>
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
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>

<script type="text/javascript" src="plugin/office/office.js"></script>

<script type="text/javascript">

var TANGER_OCX=null;
$(document).ready(function(){
	//初始化
	initOffice("#myForm");
	
	//检查
	if(!$.browser.msie){
		$.messager.show('操作提示', 'Office控件不支持火狐浏览,请使用IE内核的浏览器!',6000);
		return;
	}
	
	
	//加载文档
	TANGER_OCX=document.all('TANGER_OCX');
	if(TANGER_OCX==null){
		alert('Office控件加载失败!请检查设置一下您的IE安全选项!');
		return;
	}
	TANGER_OCX.TitleBar=false;
	TANGER_OCX.Menubar=false;
	TANGER_OCX.BeginOpenFromURL('${param.filepath}');//新增时候加载的模板
});





//保存
function save(){
	TANGER_OCX.SaveToURL("resume/uploadResumeFile.do","DocFile","","${param.id}","myForm");
}





//返回
function back(){
	window.parent.convertView('');
}


</script>



</head>
<body>
<div class="table">
	<div class="table-bar">
		<div class="table-title">
			电子简历信息
		</div>
		<div class="table-ctrl">
			<a class="btn" id="save" href="javascript:save();"><i class="icon icon-check"></i><span>保存</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<form id="myForm" action="resume/uploadResumeEamil.do" method="post" enctype="multipart/form-data" style="height:800px;background:#fff;">
			
		</form>
	</div>
</div>
</body>
</html>
