<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>直接面试通过</title>
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
var tid='${userid}';

$(document).ready(function() {
    //加载表单验证
    $("#myForm").validate();
    $('#myForm').ajaxForm(function(data) {
    	if(data!=null){
    		alert("操作成功!");
    		$("#myForm").clearForm();
    		
    		//加載數據
			window.parent.mygrid.reload();
    	}
    });
    
    initFile();
    
    //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
       	beforeShow: function(input, inst) {
   			if($(input).attr("readonly"))
   				inst.inline=true;
   		}
    });
    
    
});





//保存
function save(){
	$('#myForm').submit();
}


//回调
function callbackRecruiter(){
	$("#postname").val(null);
}




//公司回调
function callBackCompany(){
	$("#recommenddeptid").val(null);
	$("#recommenddeptname").val(null);
	
	//部门选择回调
	callbackDept();
}


//部门选择回调
function callbackDept(){
	$("#recommendpostguid").val(null);
	$("#recommendpostname").val(null);
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









</script>









<script type="text/javascript">

//回调
function callbackChooseRecruiter(id){
	$("#recruitpostguid").val(id);
}

function emailTreeCallback(userid){
	if(userid!=null&&userid!=''&&userid!='null'){
		$.getJSON("resume/getResumeByUserId.do", {id:userid}, function(data) {
			if(data!=null&&data!=''){
				$("#name").val(data.name);
				$("#sex").val(data.sex);
				$("#sexname").val(data.sexname);
				$("#birthday").val(data.birthday);
				$("#mobile").val(data.mobile);
				$("#homeplace").val(data.homeplace);
				$("#culture").val(data.culture);
				$("#culturename").val(data.culturename);
				$("#workage").val(data.workage);
				$("#workagename").val(data.workagename);
			}else{
				$("#name").val(null);
				$("#sex").val(null);
				$("#sexname").val(null);
				$("#birthday").val(null);
				$("#mobile").val(null);
				$("#homeplace").val(null);
				$("#culture").val(null);
				$("#culturename").val(null);
				$("#workage").val(null);
				$("#workagename").val(null);
			}
		});
	}
}

</script>




<script type="text/javascript">

//根据姓名加载人员信息
function inputName(){
	var name=$("#name").val();
    if(name!=null&&name!=''&&name!='null'){
	$.getJSON("resume/getResumeByName.do", {name:name}, function(data) {
		if(data!=null){
			for(var key in data){
				if($('#myForm #'+key)){
					$('#myForm #'+key).val(data[key]);
				  }
			     }									
				}else{
					$("#email").val(null);
					$("#sex").val(null);
					$("#sexname").val(null);
					$("#birthday").val(null);
					$("#mobile").val(null);
					$("#homeplace").val(null);
					$("#culture").val(null);
					$("#culturename").val(null);
					$("#workage").val(null);
					$("#workagename").val(null);
				}			
         	});
         }else{
				   $("#email").val(null);
				   $("#sex").val(null);
				   $("#sexname").val(null);
				   $("#birthday").val(null);
			       $("#mobile").val(null);
				   $("#homeplace").val(null);
				   $("#culture").val(null);
				   $("#culturename").val(null);
				   $("#workage").val(null);
				   $("#workagename").val(null);
			}			
}
</script>


</head>

<body>

<div class="sort">
	<!--  
	<div class="sort-title">
		<h3>直接录用</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>-->

	<div class="table">
		<div class="table-bar">
			<div class="table-title">
				直接面试通过
			</div>
			<div class="table-ctrl">
				<a class="btn save" href="javascript:save();" id='save'><i class="icon icon-check"></i><span>保存</span></a>
			</div>
		</div>
		<div class="table-wrapper">
			<form action="resume/saveOrUpdateMycandidatesByIntervie.do" method="post" id="myForm" class="form">
				<input type="hidden" id="recommenduserguid" name="recommenduserguid" value="${userid}"/>
				<input type="hidden" id="webuserguid" name="webuserguid" value=""/>
				<input type="hidden" id="mark" name="mark" value=""/>
				<input id="keyword" name="keyword" type="hidden" value=""/>
				<input id="photo" name="photo" type="hidden" value=""/>
				<input id="modtime" name="modtime" type="hidden" value=""/>
				<input id="createtime" name="createtime" type="hidden" value=""/>
				<input type="hidden" id="recruitpostguid" name="recruitpostguid" value=""/>
				<input id="resumefilename" name="resumefilename" type="hidden" />
				<input id="resumefilePath" name="resumefilePath" type="hidden" />
				
				<fieldset>
					<legend>岗位信息</legend>
					<!--  
					<ul>
		            	<li>
			                <span><em class="red">* </em>招聘专员：</span>
			                <input id="userid" name="userid" type="hidden" value=""/>
						    <input id="username" name="username" class="{required:true} inputselectstyle" onclick="chooseRecruiterTree('#userid','#username',callbackRecruiter);"/>
						    <div class="search-trigger" onclick="chooseRecruiterTree('#userid','#username',callbackRecruiter);" />
			            </li>
		            	<li>
						    <span><em class="red">* </em>推荐职务：</span>
							<input id="postname" name="postname" class="{required:true} inputselectstyle" onclick="choosePostNameTree('#postname',$('#userid').val(),callbackChooseRecruiter);"/>
						    <div class="search-trigger" onclick="choosePostNameTree('#postname',$('#userid').val(),callbackChooseRecruiter);" />
						</li>
		            </ul>
		            -->
		            <ul>
						<li>
			                <span><em class="red">* </em>公司名称：</span>
			                <input id="recommendcompanyid" name="recommendcompanyid" type="hidden" value=""/>
						    <input id="recommendcompanyname" name="recommendcompanyname" class="{required:true} inputselectstyle" onclick="chooseMyHasCompanyTree('#recommendcompanyid','#recommendcompanyname',callBackCompany);"/>
						    <div class="search-trigger" onclick="chooseMyHasCompanyTree('#recommendcompanyid','#recommendcompanyname',callBackCompany);"/>
			            </li>
						<li>
						    <span><em class="red">* </em>部门名称：</span>
						    <input id="recommenddeptid" name="recommenddeptid" type="hidden" value=""/>
						    <input id="recommenddeptname" name="recommenddeptname" class="{required:true} inputselectstyle" onclick="chooseOneCompanyDeptTree('#recommenddeptid','#recommenddeptname',$('#recommendcompanyid').val(),callbackDept);"/>
						    <div class="search-trigger" onclick="chooseOneCompanyDeptTree('#recommenddeptid','#recommenddeptname',$('#recommendcompanyid').val(),callbackDept);"/>
						</li>
					</ul>
					<ul>
						<li>
						    <span><em class="red">* </em>岗位名称：</span>
						    <input id="recommendpostguid" name="recommendpostguid" type="hidden" value=""/>
						    <input id="recommendpostname" name="recommendpostname" class="{required:true} inputselectstyle" onclick="choosePostTree('#recommendpostguid','#recommendpostname',$('#recommenddeptid').val());"/>
						    <div class="select-trigger" onclick="choosePostTree('#recommendpostguid','#recommendpostname',$('#recommenddeptid').val());"/>
						</li>
					</ul>
				</fieldset>
				
				<fieldset>
					<legend>人才信息</legend>		
					<ul>
					    <li>
			           		<span><em class="red">* </em>人才姓名：</span>
						    <input id="name" name="name" class="{required:true,maxlength:25} inputstyle" onblur="inputName();"/>
	                  	</li>
						<li>
						    <span><em class="red">* </em>人才邮箱：</span>
						    <input id="email" name="email" class="{required:true,maxlength:25,email:true} inputselectstyle" onclick="chooseemailTree('#email');" />
						    <div class="search-trigger" onclick="chooseemailTree('#email');" />
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
						    <span><em class="red">* </em>出生日期：</span>
						    <input id="birthday" name="birthday" class="{required:true,dateISO:true} inputstyle inputselectstyle datepicker"/>
						    <div class="date-trigger" onclick="$('#birthday').focus();"/>
						</li>
			       </ul>
		           <ul>
						<li>
						    <span><em class="red">* </em>学历情况：</span>
						    <input id="culture" name="culture" type="hidden" value=""/>
						    <input id="culturename" name="culturename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#culture','#culturename','CULTURE');"/>
						    <div class="select-trigger" onclick="chooseOptionTree('#culture','#culturename','CULTURE');"/>
						</li>
						<li>
						    <span><em class="red">* </em>手机：</span>
						    <input id="mobile" name="mobile" class="{required:true,maxlength:25} inputstyle"/>
						</li>
		            </ul>
		            <ul>
		            	<li>
						    <span><em class="red">* </em>工作年限：</span>
						    <input id="workage" name="workage" type="hidden" value=""/>
						    <input id="workagename" name="workagename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#workage','#workagename','WORKAGE');"/>
						    <div class="select-trigger" onclick="chooseOptionTree('#workage','#workagename','WORKAGE');"/>
						</li>
						<li>
						    <span><em class="red">* </em>籍贯：</span>
						    <input id="nativeplace" name="nativeplace" class="{required:true,maxlength:20} inputstyle"/>
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
			                <span>附件：</span>
			                <input id="filepath" name="filepath" style="width:570px;" size="65" class="inputstyle" />
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
	      		<br>
	      		<br>
	      		<br>
	      		<br>
			</div>	
	</div>
</div>



</body>
</html>
