<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>资讯管理</title>
<base href="${baseUrl }"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="skins/css/style.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/form.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeIcons.css"/>



<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="skins/js/jquery.ajaxupload.3.6.js"></script>
<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="plugin/ckeditor/ckeditor.js"></script>


<script type="text/javascript">
var tid='${param.id}';


$(document).ready(function() {
    //加载表单验证
    $("#myForm").validate({submitHandler:function(form) {
	    	$(form).ajaxSubmit(function(data) {
	    		alert("保存成功！");
	        });
		}
	});
    
  	
  	//加载数据
	loadData();
    
});




//取数据
function loadData() {
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("recruitment/getAboutById.do", {id:tid}, function(data) {
			if(data!=null){
				for(var key in data){
					if($('#myForm #'+key)){
						$('#myForm #'+key).val(data[key]);
					}
				}
				
				//渲染编辑器
				renderHtmlBox();
			}
		});
	else{
		$("#aboutguid").val(tid);
		renderHtmlBox();
	}
}







//保存
function save(){
	var content=CKEDITOR.instances.aboutcontent.getData();
	if(content!=null&&content!=''&&content.length>67){
		$("#aboutcontent").val(content);
		$("#myForm").submit();
	}else
		alert("正文不能为空！");
}






















</script>

<script type="text/javascript">
//渲染编辑器
function renderHtmlBox(){
	CKEDITOR.replace('aboutcontent',{
		height:500,
		fullPage : true,
		filebrowserFlashUploadUrl : '${baseUrl}uploadCkeditFile.do?directory=ckedit',
		filebrowserImageUploadUrl : '${baseUrl}uploadCkeditFile.do?directory=ckedit'
	});
}

</script>




</head>

<body>
<div class="table">
	<div class="table-bar">
		<div class="table-title">
			基本信息
		</div>
		<div class="table-ctrl">
			<a class="btn" id="save" href="javascript:save();"><i class="icon icon-check"></i><span>保存</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<form action="recruitment/saveOrUpdateAboutContent.do" id="myForm"  class="form" method="post">
			<input type="hidden" id="aboutguid" name="aboutguid" value=""/>
			<div style="height:650px">
				<textarea id="aboutcontent" name="aboutcontent" style="display:none;"></textarea>
			</div>
		</form>
	</div>
</div>



</body>
</html>
