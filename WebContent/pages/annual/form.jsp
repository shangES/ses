<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>年休假管理</title>
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

<script type="text/javascript" src="pages/tree.js"></script>

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
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("annual/getAnnualById.do", {id:tid}, function(data) {
			if(data!=null){
				for(var key in data){
					if($('#myForm #'+key)){
						$('#myForm #'+key).val(data[key]);
					}
				}
			}
		});
}










//员工树选择回调
function employeeTreeCallback(id){
	$.getJSON("employee/getEmployeeById.do", {id:id}, function(data) {
		if(data!=null){
			for (var key in data) {
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
	window.parent.convertView('');
}









//部门选择回调
function callbackDept(){
	$("#jobid").val(null);
	$("#jobname").val(null);
	$("#postid").val(null);
	$("#postname").val(null);
}

</script>
</head>

<body>
<div class="sort">
	<div class="sort-title">
		<h3>年休假管理</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>
<div class="table">
	<div class="table-bar">
		<div class="table-title">
			年休假信息
		</div>
		<div class="table-ctrl">
			<a class="btn" id="save" href="javascript:save();" ><i class="icon icon-check"></i><span>保存</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<form action="annual/saveOrUpdateAnnual.do" method="post" id="myForm" class="form">
			<input type="hidden" id="annualguid" name="annualguid" value=""/>
			<input type="hidden" id="employeeid" name="employeeid" value=""/>
			<input type="hidden" id="modiuser" name="modiuser" value=""/>
			<input type="hidden" id="moditimestamp" name="moditimestamp" value=""/>
			
			<fieldset>
				<legend>基本信息</legend>
				<ul>
					<li>
						<span><em class="red">* </em>公司：</span>
						<input id="companyid" name="companyid" type="hidden" value=""/>
					    <input id="companyname" name="companyname" class="{required:true} inputstyle disabled" style="width:570px;" readonly="true"/>
					</li>
				</ul>
				<ul>
					<li>
						<span><em class="red">* </em>部门：</span>
						<input id="deptid" name="deptid" type="hidden" value=""/>
					    <input id="deptname" name="deptname" class="{required:true} inputselectstyle disabled" onclick="chooseMyDeptTree('#deptid','#deptname',callbackDept);"  readonly="true"/>
					    <div class="select-trigger" onclick="chooseMyDeptTree('#deptid','#deptname',callbackDept);"/>
					</li>
					<li>
						<span>岗位：</span>
						<input id="postid" name="postid" type="hidden" value=""/>
					    <input id="postname" name="postname" class="inputselectstyle disabled" onclick="chooseJobTree('#postid','#postname',$('#companyid').val());"  readonly="true"/>
					    <div class="select-trigger" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
					</li>
				</ul>
				<ul>
					<li>
						<span><em class="red">* </em>姓名：</span>
						<input id="name" name="name" class="{required:true} inputselectstyle"  onclick="chooseEmployeeTree('#name','#name','!=-1');"/>
						<div class="search-trigger" onclick="chooseEmployeeTree('#name','#name','!=-1');" title="员工请假选择名字，工号!"/>
					</li>
					<li>
						<span><em class="red">* </em>员工工号：</span>
						<input id="jobnumber" name="jobnumber" class="inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
						<span><em class="red">* </em>年度：</span>
						<input id="annualyear" name="annualyear" class="{required:true,maxlength:4,minlength:4,number:true} inputstyle" value=""  onblur="javascript:checkYear();"/>
					</li>
					<li>
						<span><em class="red">* </em>可休年休天数：</span>
						<input id="offnumday" name="offnumday" class="{required:true,maxlength:10,number:true} inputstyle" value=""/>
					</li>
				</ul>
			</fieldset>
		</form>
		</div>
		<br>
		<br>
		<br>
		<br>
</div>
</div>
</body>
</html>
