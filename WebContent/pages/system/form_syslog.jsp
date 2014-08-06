<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>系统日志管理</title>
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
<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>

<script type="text/javascript">
var tid='${param.id}';

$(document).ready(function() {
	
	//加载数据
	loadGrid();
	
	//加载表单验证
    $("#myForm").validate({submitHandler:function(form) {
	    	$(form).ajaxSubmit(function(data) {
	    		window.parent.convertView(null);
	        });
		}
	});
    
});	







//加载数据
function loadGrid(){
	if(tid!=null&&tid!=''&&tid!='null'){
		$.post("system/getSyslogById.do",{id:tid}, function(data) {
			if(data!=null){
				for(var key in data){
					if($('#myForm #'+key)){
						$('#myForm #'+key).val(data[key]);
					}
				}
			}
	  	});
	}
}






//保存
function save(){
	$("#myForm").submit();
}




//返回
function back(){
	window.parent.convertView(null);
}


</script>
</head>
<body>
<div class="sort">
	<div class="sort-title">
		<h3>系统日志管理</h3>
	</div>
	<div class="table">
	<div class="table-bar">
		<div class="table-title">
			系统日志
		</div>
		<div class="table-ctrl">
			<a class="btn" href="javascript:save();"><i class="icon icon-check"></i><span>保存</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<form action="system/saveOrUpdateSyslog.do" method="post" id="myForm" class="form">
		<input id="logguid" name="logguid" type="hidden" value=""/>
		<input id="modiuser" name="modiuser" type="hidden" value=""/>
		<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
		<fieldset>
			<legend>基本信息</legend>
			<ul>
				<li>
					<span><em class="red">* </em>主体：</span>
					<input id="subject" name="subject" class="{required:true,maxlength:16} inputstyle"/>
				</li>
				<li>
					<span>来源：</span>
					<input id="source" name="source" class="{maxlength:16} inputstyle">
				</li>
			</ul>
			<ul>
				<li>
					<span>目标：</span>
					<input id="target" name="target" class="{maxlength:16} inputstyle">
				</li>
			</ul>
			<ul>
				<li>
					<span>描述：</span>
					<textarea id="description" name="description" rows="5" style="width:560px;" cols="40" class="{maxlength:500} areastyle" ></textarea>
				</li>
			</ul>
			<ul>
				<li>
					<span>操作备注：</span>
					<textarea id="modimemo" name="modimemo" rows="5" style="width:560px;" cols="40" class="{maxlength:500} areastyle" ></textarea>
				</li>
			</ul>
			</fieldset>
       	</form>
       	
       	<br/>
       	<br/>
       	<br/>
       	<br/>
	</div>
	</div>
</div>
</body>
</html>