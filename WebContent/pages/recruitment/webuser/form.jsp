<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>外网人员管理</title>
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
var edit='${param.edit}';

$(document).ready(function() {
	
    //加载表单验证
    $("#myForm").validate({submitHandler:function(form) {
	    	$(form).ajaxSubmit(function(data) {
	    		window.parent.convertView(null);
	        });
		}
	});
    
    
  	//加載數據
    loadData(tid);
    
    
});







//取数据
function loadData(tid) {
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("recruitment/getWebUserById.do", {id:tid}, function(data) {
			if(data!=null){
				for(var key in data){
					if($('#myForm #'+key)){
						$('#myForm #'+key).val(data[key]);
					}
				}
				//判定下状态
				butstate();
				
				//登录名、密码不能被修改
				formDisabled(true,"#loginnamePanel");
				formDisabled(true,"#myPassword");
			}
		});
	else{
		$("#save").show();
	}
	
}




//页面状态
function butstate(){
	if(!edit){
		formDisabled(true);
		$("#save").hide();
	}else{
		var valid=$("#valid").val();
	
		//失效的数据显示还原按钮
		if(valid==0){
			$("#revert").show();
			formDisabled(true);
		}else{
			$("#lose").show();
			$("#save").show();
		}
		
	}
}







//失效及恢复
function validOneWorkPlace(state){
	$("#valid").val(state);
	save();
}







//切换视图
function convertView(url){
	if ($(".list").css("display")!="none") {
		$(".list").hide();
		$("#detail").show();
		
		showLoading();
		$("#detail").attr("src",url);
	}else{
		$("#detail").removeAttr("src");
		$(".list").show();
		if(url==null)
			mygrid.reload();
  }
}






//保存
function save(){
	var email=$("#email").val();
	var webuserguid=$("#webuserguid").val();
	$.getJSON("recruitment/checkWebUserByEmail.do",{webuserguid:webuserguid,name:email}, function(data) {
		if(data!=null&&data!=""){
			alert(data);
			$("#email").val(null);
		}else
			$('#myForm').submit();
	});
	
}






//返回
function back(){
	window.parent.convertView('');
}





//判断登录名是否存在
function checkLoginName(){
	var email=$("#email").val();
	var webuserguid=$("#webuserguid").val();
	if (email!=null&&email!=""){
		$.getJSON("recruitment/checkWebUserByEmail.do",{webuserguid:webuserguid,name:email}, function(data) {
			if(data!=null&&data!=""){
				alert(data);
				$("#email").val(null);
			}
		});
	}
}









</script>
</head>

<body>
<div class="sort">
	<div class="sort-title">
		<h3>外网人员管理</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>
<div class="table">
	<div class="table-bar">
		<div class="table-title">
			外网人员信息
		</div>
		<div class="table-ctrl">
			<a class="btn" id="lose"  href="javascript:validOneWorkPlace(0);" style="display:none"><i class="icon icon-trash"></i><span>失效</span></a>
   			<a class="btn" id="revert"  href="javascript:validOneWorkPlace(1);" style="display:none"><i class="icon icon-retweet"></i><span>恢复</span></a>
			<a class="btn" id="save" href="javascript:save();" style="display:none"><i class="icon icon-check" ></i><span>保存</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<form action="recruitment/saveOrUpdateWebUser.do" id="myForm"  class="form" method="post">
			<input type="hidden" id="webuserguid" name="webuserguid" value=""/>
			<input type="hidden" id="valid" name="valid" value="1"/>
			<input type="hidden" id="isadmin" name="isadmin" value="0"/>
			<input type="hidden" id="code" name="code" value=""/>
			<fieldset>
			<legend>基本信息</legend>
				<ul>
					<li>
						<span><em class="red">* </em>用户名：</span>
						<input id="username" name="username" class="{required:true,maxlength:20} inputstyle"  />
					</li>
					<li id="loginnamePanel">
						<span><em class="red">* </em>登录名(邮箱注册)：</span>
						<input id="email" name="email" class="{required:true,maxlength:50,email:true} inputstyle"  onblur="checkLoginName();"/>
					</li>
				</ul>
				<ul>
					<li id="myPassword">
						<span><em class="red">* </em>密码：</span>
						<input type="password" id="password" name="password" class="{required:true,maxlength:10} inputstyle"  />
					</li>
					<li>
						<span><em class="red">* </em>联系电话：</span>
						<input id="mobile" name="mobile" class="{required:true,maxlength:20} inputstyle"  />
					</li>
				</ul>
				<ul>
					<li>
						<span>备注：</span>
						<textarea id="rmk" name="rmk" rows="5" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
					</li>
				</ul>
			</fieldset>
		</form>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
	</div>
</div>



</body>
</html>
