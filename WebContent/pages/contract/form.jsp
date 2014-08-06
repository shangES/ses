<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>合同管理</title>
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
		$.getJSON("contract/getContractById.do", {id:tid}, function(data) {
			if(data!=null){
				for(var key in data){
					if($('#myForm #'+key)){
						$('#myForm #'+key).val(data[key]);
					}
				}
				
				
				if(data.state=="0"||data.state=="2"){
					formDisabled(true);
					$("#revert").show();
					$("#save").hide();
					$("#end").hide();
				}
			}
		});
	if(!edit){
		formDisabled(true);
		$("#save").hide();
		$("#end").hide();
	}
}





//恢复
function revertContract(valid){
	var contractid=$("#contractid").val();
	$.post("contract/validContractById.do",{ids:contractid,state:valid}, function() {
		window.parent.convertView(null);
    });
}






//保存
function save(){
	var startdate=$("#startdate").val();
	var enddate=$("#enddate").val();
	
	if(startdate!=null&&startdate!=''&&enddate!=null&&enddate!=''){
		 var sd = new  Date(Date.parse(startdate.replace(/-/g,"/")));
		 var ed = new  Date(Date.parse(enddate.replace(/-/g,"/")));
		 
		 if (sd -ed>0) {
			 alert("到期时间要大于生效时间");
			 $("#enddate").focus();
		 }else{
			 $('#myForm').submit();
		 }
	}else{
		 $('#myForm').submit();
	}
	
}






//返回
function back(){
	window.parent.convertView('');
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
			
			
			//有效的合同
			$("#state").val(1);
		}
		});
}






//部门选择回调
function callbackDept(){
	$("#jobid").val(null);
	$("#jobname").val(null);
	$("#postid").val(null);
	$("#postname").val(null);
}









//终止合同
function endContract(valid){
	$("#state").val(2);
	save();
}

</script>
</head>

<body>
<div class="sort">
	<div class="sort-title">
		<h3>合同管理</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>
<div class="table">
	<div class="table-bar">
		<div class="table-title">
			合同信息
		</div>
		<div class="table-ctrl">
			<a class="btn" id="end" href="javascript:endContract();"><i class="icon icon-stop"></i><span>终止合同</span></a>
			<a class="btn" id="save" href="javascript:save();"><i class="icon icon-check"></i><span>保存</span></a>
   			<a class="btn" id="revert" href="javascript:revertContract(1);" style="display:none" ><i class="icon icon-retweet"></i><span>恢复</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<form action="contract/saveOrUpdateContract.do" id="myForm"  class="form" method="post">
			<input type="hidden" id="contractid" name="contractid" value=""/>
			<input type="hidden" id="employeeid" name="employeeid" value=""/>
			<input type="hidden" id="state" name="state" value="1"/>
			<input id="modiuser" name="modiuser" type="hidden" value="${username}" />
			<input id="modimemo" name="modimemo" type="hidden" value="" />
			<fieldset>
			<legend>基本信息</legend>
				<ul>
					<li>
		                <span><em class="red">* </em>公司：</span>
		                <input id="companyid" name="companyid" type="hidden" value=""/>
					    <input id="companyname" name="companyname" class="{required:true} inputselectstyle" onclick="chooseMyHasCompanyTree('#companyid','#companyname');"/>
					    <div class="search-trigger" onclick="chooseMyHasCompanyTree('#companyid','#companyname');"/>
		            </li>
					<li>
					    <span><em class="red">* </em>部门名称：</span>
					    <input id="deptid" name="deptid" type="hidden" value=""/>
					    <input id="deptname" name="deptname" class="{required:true} inputselectstyle disabled" onclick="chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val(),callbackDept);" readonly="true"/>
					    <div class="search-trigger" onclick="chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val(),callbackDept);"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>岗位：</span>
					    <input id="postid" name="postid" type="hidden" value=""/>
					    <input id="postname" name="postname" class="inputselectstyle disabled" onclick="choosePostTree('#postid','#postname',$('#deptid').val(),callbackpost);"  readonly="true"/>
					    <div class="select-trigger" onclick="choosePostTree('#postid','#postname',$('#deptid').val());" />
					</li>
					<li>
						<span><em class="red">* </em>员工工号：</span>
						<input id="jobnumber" name="jobnumber" class="inputstyle disabled"/>
					</li>
				</ul>
				<ul>
					<li>
						<span><em class="red">* </em>姓名：</span>
						<input id="name" name="name" class="{required:true} inputselectstyle"  onclick="chooseEmployeeTree('#name','#name','!=-1');"/>
						 <div class="search-trigger" onclick="chooseEmployeeTree('#name','#name','!=-1');" title="员工请假选择名字，工号!"/>
					</li>
					<li>
						<span>合同编号：</span>
						<input id="contractcode" name="contractcode" class="{maxlength:10} inputstyle" />
					</li>
				</ul>
			    <ul>
					<li>
						<span><em class="red">* </em>合同工时：</span>
						<input id="hourssystem" name="hourssystem" type="hidden" value=""/>
					    <input id="hourssystemname" name="hourssystemname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#hourssystem','#hourssystemname','HOURSSYSTEM');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#hourssystem','#hourssystemname','HOURSSYSTEM');"/>
					</li>
					<li>
						<span><em class="red">* </em>合同类型：</span>
						<input id="contracttype" name="contracttype" type="hidden" value=""/>
					    <input id="contracttypename" name="contracttypename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#contracttype','#contracttypename','CONTRACTTYPE');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#contracttype','#contracttypename','CONTRACTTYPE');"/>
					</li>
				</ul>
		    
				<ul>
					<li>
						<span><em class="red">* </em>签订日期：</span>
						<input id="signdate" name="signdate" class="{required:true,dateISO:true}  inputselectstyle datepicker"/>
					    <div class="date-trigger" onclick="$('#signdate').focus();"/>
					</li>
					<li>
						<span><em class="red">* </em>生效日期：</span>
						<input id="startdate" name="startdate" class="{required:true,dateISO:true}  inputselectstyle datepicker"/>
					    <div class="date-trigger" onclick="$('#startdate').focus();"/>
					</li>
				</ul>
				<ul>
					<li>
						<span>到期时间：</span>
						<input id="enddate" name="enddate" class="{dateISO:true}  inputselectstyle datepicker"  />
					    <div class="date-trigger" onclick="$('#enddate').focus();"/>
					</li>
				</ul>
				<ul>
					<li>
						<span>合同内容：</span>
						<textarea id="content" name="content" rows="5" cols="40" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
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



</body>
</html>
