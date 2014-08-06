<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>邮件推荐</title>
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

<script type="text/javascript" src="pages/tree.js"></script>
<script type="text/javascript" src="pages/resume/recommend/tree.js"></script>

<script type="text/javascript">
var tid='${param.id}';

$(document).ready(function() {
    //加载表单验证
    $("#myForm").validate();
    $('#myForm').ajaxForm(function(data) {
    	if(data!=null){
    		alert("推荐成功");
    		$("#myForm").clearForm();
    	}
    });
    
    //初始上传
    initFile();
    
    
    //加载数据
    loadData();
    
    //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
       	beforeShow: function(input, inst) {
   			if($(input).attr("readonly"))
   				inst.inline=true;
   		}
    });
    
    //关闭等到层
  	if(window.parent.hidenLoading){
  		window.parent.hidenLoading();
  	}
});



//取数据
function loadData() {
	if(tid!=null&&tid!=''&&tid!='null'){
		$.getJSON("resume/getResumeEamilById.do", {id:tid}, function(data) {
			if(data!=null){
				$("#filepath").val(data.filename);
	    		$("#resumefilePath").val(data.filepath);
	    		$("#resumefilename").val(data.filename);
			}
		});
	}
}

//保存
function save(){
	$('#myForm').submit();
}






//上传附件
function initFile(){
    new AjaxUpload('#filepath', {
    	id:'upFile',
    	action: 'uploadFile.do',
    	name:'file1',
    	autoSubmit:true,
    	responseType: 'json',
    	data: {table:'t_resume_file'},
    	onComplete: function(filename,obj) {
			$("#filepath").val(filename);
    		$("#resumefilePath").val(obj.filepath);
    		$("#resumefilename").val(filename);
    	}
   	});
}






//返回
function back(){
	window.parent.convertView('');
}




</script>









<script type="text/javascript">
//选择招聘专员
function chooseRecruiter(){
	$("#chooseRecruiterWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:"650",
		height:500,
		buttons: {
			
		},
		open:function(){
			if($("#chooseRecruiter").attr("src")=="")
				$("#chooseRecruiter").attr("src","resume.do?page=recommend/chooseRecruiter");
		}
	});
}

//回调
function callbackChooseRecruiter(id){
	$("#recruitpostguid").val(id);
}


//回调
function callbackRecruiter(){
	$("#postname").val(null);
}
</script>
</head>

<body>

	<div class="table">
		<div class="table-bar">
			<div class="table-title">
				邮件推荐
			</div>
			<div class="table-ctrl">
				<a class="btn" href="javascript:chooseRecruiter();" style="display:none"><i class="icon icon-indent-left"></i><span>选择招聘专员</span></a>
				<a class="btn save" href="javascript:save();" id='save'><i class="icon icon-check"></i><span>保存</span></a>
				<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
			</div>
		</div>
		<div class="table-wrapper">
			<form action="resume/saveOrUpdateRecommendResume.do" method="post" id="myForm" class="form">
				<input type="hidden" id="resumeguid" name="resumeguid" value=""/>
				<input type="hidden" id="recommenduserguid" name="recommenduserguid" value="${userid }"/>
				<input type="hidden" id="userguid" name="userguid" value=""/>
				<input type="hidden" id="mark" name="mark" value=""/>
				<input id="keyword" name="keyword" type="hidden" value=""/>
				<input id="photo" name="photo" type="hidden" value=""/>
				<input id="modtime" name="modtime" type="hidden" value=""/>
				<input id="resumetype" name="resumetype" type="hidden" value="3" />
				<input id="createtime" name="createtime" type="hidden" value=""/>
				<input type="hidden" id="recruitpostguid" name="recruitpostguid" value=""/>
				<input id="resumefilename" name="resumefilename" type="hidden" />
				<input id="resumefilePath" name="resumefilePath" type="hidden" />
				<fieldset>
					<legend>基本信息</legend>
					<ul>
		            	<li>
			                <span><em class="red">* </em>招聘专员：</span>
			                <input id="userguid" name="userguid" type="hidden" value=""/>
						    <input id="username" name="username" class="inputselectstyle" onclick="chooseRecruiterTree('#userguid','#username',callbackRecruiter);"/>
						    <div class="search-trigger" onclick="chooseRecruiterTree('#userguid','#username',callbackRecruiter);" />
			            </li>
			            
		            	<li>
						    <span><em class="red">* </em>职务名称：</span>
							<input id="postname" name="postname" class="inputselectstyle" onclick="choosePostNameTree('#postname',$('#userguid').val(),callbackChooseRecruiter);"/>
						    <div class="search-trigger" onclick="choosePostNameTree('#postname',$('#userguid').val(),callbackChooseRecruiter);" />
						</li>
		            </ul>
					<ul>
		             	<li>
			           		<span><em class="red">* </em>姓名：</span>
						    <input id="name" name="name" class="{required:true,maxlength:25} inputstyle"/>
	                  	</li>
						<li>
						    <span><em class="red">* </em>出生日期：</span>
						    <input id="birthday" name="birthday" class="{required:true,dateISO:true} inputstyle inputselectstyle datepicker"/>
						    <div class="date-trigger" onclick="$('#birthday').focus();"/>
						</li>
			       </ul>
		           <ul>
						<li>
						    <span><em class="red">* </em>性别：</span>
						    <input id="sex" name="sex" type="hidden" value=""/>
						    <input id="sexname" name="sexname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#sex','#sexname','SEX');"/>
						    <div class="select-trigger" onclick="chooseOptionTree('#sex','#sexname','SEX');"/>
						</li>
						<li>
						    <span><em class="red">* </em>学历情况：</span>
						    <input id="culture" name="culture" type="hidden" value=""/>
						    <input id="culturename" name="culturename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#culture','#culturename','CULTURE');"/>
						    <div class="select-trigger" onclick="chooseOptionTree('#culture','#culturename','CULTURE');"/>
						</li>
		            </ul>
		            <ul>
						<li>
						    <span><em class="red">* </em>手机：</span>
						    <input id="mobile" name="mobile" class="{required:true,maxlength:25} inputstyle"/>
						</li>
						<li>
						    <span><em class="red">* </em>邮箱：</span>
						    <input id="email" name="email" class="{required:true,maxlength:25,email:true} inputstyle" />
						</li>
		            </ul>
		            <ul>
		            	<li>
						    <span><em class="red">* </em>工作年限：</span>
						    <input id="workage" name="workage" type="hidden" value=""/>
						    <input id="workagename" name="workagename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#workage','#workagename','WORKAGE');"/>
						    <div class="select-trigger" onclick="chooseOptionTree('#workage','#workagename','WORKAGE');"/>
						</li>
		            </ul>
		            
		            <ul>
		            	<li>
						    <span><em class="red">* </em>家庭地址：</span>
						    <input id="homeplace" name="homeplace" class="{maxlength:50,required:true} inputstyle" style="width:570px;"/>
						</li>
		            </ul>
					<ul>
			            <li>
			                <span><em class="red">* </em>附件：</span>
			                <input id="filepath" name="filepath" style="width:570px;" size="65" class="inputstyle {required:true}" />
		                </li>
					<ul>
					<ul>
			         	<li>
			                <span>备注：</span>
			                <textarea id="rmk" name="rmk" rows="5" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
			            </li>
			        </ul>
					</fieldset>
	      		</form>
			</div>	
	</div>

<!-- 选择招聘专员-->
<div id="chooseRecruiterWindow" title="选择招聘专员" style="display:none;overflow:hidden;">
	<iframe id="chooseRecruiter" width="100%" height="100%" frameborder="0" src="" scrolling="no">
</div>


</body>
</html>
