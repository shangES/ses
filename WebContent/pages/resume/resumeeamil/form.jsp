<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>邮箱管理</title>
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
<script type="text/javascript" src="skins/js/jquery.ajaxupload.3.6.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="plugin/ckeditor/ckeditor.js"></script>


<script type="text/javascript">
var tid='${param.id}';
var pageState=false;

$(document).ready(function() {
	
	
    //加载表单验证
    $("#myForm").validate({submitHandler:function(form) {
	    	$(form).ajaxSubmit(function(data) {
	    		alert("保存成功!");
	    		tid=data.resumeeamilguid;
	    		
	    		//重新加载
	    	    loadData();

	    		pageState=true;
	    		
	        	//页面保存过
	        	window.parent.callbackPageState(true);
	        });
		}
	});
    
    
  	//加載數據
    loadData();
    
    
    //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
       	beforeShow: function(input, inst) {
   			if($(input).attr("readonly"))
   				inst.inline=true;
   		}
    });
});










//取数据
function loadData() {
	if(tid!=null&&tid!=''&&tid!='null'){
		$.getJSON("resume/getResumeEamilById.do", {id:tid}, function(data) {
			if(data!=null){
				for(var key in data){
					if($('#myForm #'+key)){
						$('#myForm #'+key).val(data[key]);
					}
				}
				
				
				//刷新已經讀
				if(data.readtype==0){
					var array=new Array();
					array.push(tid);
					$.post("resume/updateByReadtype.do",{ids:array.toString(),readtype:1}, function() {
						pageState=true;
				    });
				}
					
				//渲染html
				renderHtmlBox();
				
				///判定下状态
				initButtonState();
			}
		});
	}else{
		renderHtmlBox();
		
		///判定下状态
		initButtonState();
	}
		
	
}




///判定下状态
function initButtonState(){
	if(${edit==true}){
		$("#save").show();
	}else{
		$("#save").hide();
		formDisabled(true);
	}
}



//渲染编辑器
function renderHtmlBox(){
	CKEDITOR.replace('content',{
		height:500,
		fullPage : true,
		//toolbarStartupExpanded:false,
		filebrowserFlashUploadUrl : '${baseUrl}uploadCkeditFile.do?directory=ckedit',
		filebrowserImageUploadUrl : '${baseUrl}uploadCkeditFile.do?directory=ckedit'
	});
}














//保存
function save(){
	var content=CKEDITOR.instances.content.getData();
	if(content!=null&&content!=''&&content.length>67){
		$("#content").val(content);
		var email=$("#email").val();
		var resumeeamilguid=$("#resumeeamilguid").val();
		var readtype=$("#readtype").val(0);
		$.post("resume/getResumeEamilByEmail.do",{email:email,id:resumeeamilguid}, function(msg) {
			if(msg!=null&&msg!=''){
				alert("该邮箱已经存在!");
				$("#email").val(null);
			}else{
				$("#myForm").submit();
			}
		});
	}else{
		alert("内容不能为空！");
	}
}






//返回
function back(){
	window.parent.convertView(pageState?null:'');
}










</script>
</head>

<body>
<div class="table">
	<div class="table-bar">
		<div class="table-title">
			邮箱信息
		</div>
		<div class="table-ctrl">
			<a class="btn" id="save" href="javascript:save();"><i class="icon icon-check"></i><span>保存</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<form action="resume/saveOrUpdateResumeEamil.do" method="post" id="myForm" class="form">
			<input type="hidden" id="resumeeamilguid" name="resumeeamilguid" value=""/>
			<input type="hidden" id="userguid" name="userguid" value=""/>
			<input type="hidden" id="modtime" name="modtime" value=""/>
			<input type="hidden" id="filepath" name="filepath" value=""/>
			<input type="hidden" id="readtype" name="readtype" value=""/>
			<fieldset>
				<legend>基本信息</legend>
				
				<ul>
					<li>
					    <span><em class="red">* </em>发件人：</span>
					    <input id="personal" name="personal" class="{required:true,maxlength:50} inputstyle"/>
					</li>
					<li>
					    <span><em class="red">* </em>电子邮箱：</span>
					    <input id="email" name="email" class="{required:true,maxlength:50,email:true} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span><em class="red">* </em>主题：</span>
					    <input id="subject" name="subject" class="{required:true,maxlength:100} inputstyle" style="width:570px;"/>
					</li>
				</ul>
				<ul>
					<li>
						<span>备注：</span>
						<textarea id="rmk" name="rmk"  rows="5" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
					</li>
				</ul>
			</fieldset>
			
			<fieldset>
				<legend><em class="red">* </em>正文</legend>
				<div style="height:650px">
					<textarea id="content" name="content" style="display:none;"></textarea>
				</div>
			</fieldset>
		</form>
		</div>
</div>
</body>
</html>
