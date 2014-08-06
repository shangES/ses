<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>OA信息管理</title>
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

<script type="text/javascript" src="pages/tree.js"></script>
<script type="text/javascript" src="pages/bpmn/workflow.js"></script>

<script type="text/javascript">
var tid='${param.id}';

var pageState=true;

$(document).ready(function() {
		 //加载表单验证
    $("#myForm").validate({submitHandler:function(form) {
	    	$(form).ajaxSubmit(function(data) {
	    		alert("保存成功");
	        });
		}
	});
		 
		 
    loadData(tid);
  	
    
    formDisabled(true,'#myForm');
    
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
 function loadData(tid) {
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("recruitprogram/getRecruitprogramauditById.do", {id:tid}, function(data) {
			if(data!=null){
				for(var key in data){
					if($('#myForm #'+key)){
						$('#myForm #'+key).val(data[key]);
					}
				}
			}
		});
} 






//保存
function save(){
	$('#myForm').submit();
}





//返回
function back(){
	window.parent.convertView(pageState?null:'');
}




//部门选择回调
function callbackDept(){
	$("#postid").val(null);
	$("#postname").val(null);
}








//公司回调
function callBackCompany(){
	$("#deptid").val(null);
	$("#deptname").val(null);
	
	//部门选择回调
	callbackDept();
}



//部门选择回调
function callbackDept(){
	$("#postid").val(null);
	$("#postname").val(null);
}


//回调公司 部门
function tquotaTreeCallback(id){
	if(id!=null&&id!=''&&id!='null'){
		$.getJSON("department/getPostByPostId.do", {id:id}, function(data) {
			if(data!=null){
				$("#companyid").val(data.companyid);
				$("#companyname").val(data.companyname);
				$("#deptid").val(data.deptid);
				$("#deptname").val(data.deptname);
			}
		});
	}
}
</script>
</head>

<body>
<div class="table">
	<div class="table-bar">
		<div class="table-title">
			表格名称
		</div>
		<div class="table-ctrl">
			<!--  <a class="btn" id="save" href="javascript:save();" ><i class="icon icon-check"></i><span>保存</span></a>-->
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper" >
		<form action="recruitprogram/saveOrUpdateRecruitProgramAudit.do" id="myForm"  class="form" method="post">
			<input type="hidden" id="recruitprogramguid" name="recruitprogramguid" value=""/>
			<input type="hidden" id="recruitprogramauditguid" name="recruitprogramauditguid" value=""/>
			<input type="hidden" id="state" name="state" value=""/>
			<input type="hidden" id="interfacecode" name="interfacecode" value="">
			<fieldset>
			<legend>基本信息</legend>
				<ul class="deptidLi">
					<li>
		                <span><em class="red">* </em>公司名称：</span>
		                <input id="companyid" name="companyid" type="hidden" value=""/>
					    <input id="companyname" name="companyname" class="{required:true} inputselectstyle" onclick="chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
					    <div class="search-trigger" onclick="chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
		            </li>
					<li>
					    <span><em class="red">* </em>部门名称：</span>
					    <input id="deptid" name="deptid" type="hidden" value=""/>
					    <input id="deptname" name="deptname" class="{required:true} inputselectstyle" onclick="chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val(),callbackDept);"/>
					    <div class="search-trigger" onclick="chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val(),callbackDept);"/>
					</li>
				</ul>
				<ul>
					<li>
						<span><em class="red">* </em>岗位：</span>
						<input id="postid" name="postid" type="hidden" value=""/>
					    <input id="postname" name="postname" class="{required:true} inputselectstyle" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
					    <div class="select-trigger" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
					</li>
					<li>
						<span>职级：</span>
						<input id="rankid" name="rankid" type="hidden" value=""/>
						<input id="rankname" name="rankname" class="inputselectstyle"  onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());"/>
						<div class="search-trigger" onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());"/>
					</li>
				</ul>
				<ul>
					<li>
						<span><em class="red">* </em>编制类型：</span>
						<input id="quotaid" name="quotaid" type="hidden"/>
						<input id="quotaname" name="quotaname" class="{required:true} inputselectstyle"  <div class="search-trigger" onclick="chooseQuotaTree('#quotaid','#quotaname',$('#postid').val());"/>
				    	<div class="search-trigger" onclick="chooseQuotaTree('#quotaid','#quotaname',$('#postid').val());"/>
					</li>
					<li>
						<span><em class="red">* </em>招聘人数：</span>
						<input id="postnum" name="postnum" class="{required:true,maxlength:38}  inputstyle"/>
					</li>
				</ul>
		    	<ul>
		    		<li>
						<span><em class="red">* </em>开始时间：</span>
						<input id="startdate" name="startdate" class="{required:true,dateISO:true}  inputselectstyle datepicker"/>
				    	<div class="date-trigger" onclick="$('#hillockdate').focus();"/>
					</li>
					<li class="deptidLi">
						<span>结束时间：</span>
						<input id="enddate" name="enddate" class="{dateISO:true}  inputselectstyle datepicker"/>
				   	 	<div class="date-trigger" onclick="$('#applydate').focus();"/>
					</li>
				</ul>
				<ul>
					<li>
						<span><em class="red">* </em>对应人数：</span>
						<input id="remainnum" name="remainnum" class="{required:true,maxlength:38}  inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
						<span>备注：</span>
						<textarea id="auditresult" name="auditresult" rows="5" cols="40" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
					</li>
				</ul>
			</fieldset>
		</form>
		<div class="form" id="myProcess">
    	
    	</div>
    	<br/>
    	<br/>
    	<br/>
    	<br/>
	</div>



</body>
</html>
