<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>通讯录管理</title>
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
var pageState=false;


$(document).ready(function() {
    //加载表单验证
    $("#myForm").validate();
    $('#myForm').ajaxForm(function(data) {
    	tid=data.addresslistguid;
    	loadData(tid);
    });
    
    formDisabled(true);
    
    
    
    //加载数据
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


//加载数据
function loadData(tid){
	if(tid!=null&&tid!=''&&tid!='null'){
		$.getJSON("addresslist/getAddressListByEmployeeId.do", {id:tid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#myForm #' + key);
			        if(el) 
			            el.val(data[key]);
			    }
			}else{
				$("#myForm").clearForm();
			}
		});
	}
}






//返回
function back(){
	window.parent.convertView(pageState?null:'');
}




//部门选择回调
function callbackDept(){
	$("#postid").val(null);
}




//同步
function refresh(){
	$.post("addresslist/refreshAddressList.do",function(){
		alert("更新成功");
		loadData(tid);
	});
}


</script>

</head>

<body>
	<div class="table">
		<div class="table-bar">
			<div class="table-title">
				通讯录信息
			</div>
			<div class="table-ctrl">
				<a class="btn" href="javascript:refresh();" id='save'><i class="icon icon-refresh"></i><span>同步通讯录</span></a>
				<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
			</div>
		</div>
		<div class="table-wrapper">
			<form action="" method="post" id="myForm" class="form">
				<input id="addresslistguid" name="addresslistguid" type="hidden" value="" />
				<input id="employeeid" name="employeeid" type="hidden" value="" />
				<input id="employeecode" name="employeecode" type="hidden" value="" />
				<input id="postcode" name="postcode" type="hidden" value="" />
				<input id="deptcode" name="deptcode" type="hidden" value="" />
				<input id="modiuser" name="modiuser" type="hidden" value="${username}" />
				<input id="modimemo" name="modimemo" type="hidden" value="" />
			    <input id="companycode" name="companycode" type="hidden" value="" />
				<input id="moditimestamp" name="moditimestamp" type="hidden" value="" />
				<input id="refreshtimestamp" name="refreshtimestamp" type="hidden" value="" />
				
				<fieldset>
					<legend>基本信息</legend>
					<ul id="myCompany">
						<li>
						    <span><em class="red">* </em>公司名称：</span>
						    <input id="companyid" name="companyid" type="hidden" value=""/>
						    <input id="companyname" name="companyname" class="{required:true} inputstyle disabled" style="width:570px;" readonly="true"/>
						</li>
					</ul>
					<ul>
						<li>
						    <span><em class="red">* </em>部门名称：</span>
						    <input id="deptid" name="deptid" type="hidden" value=""/>
						    <input id="deptname" name="deptname" class="{required:true} inputselectstyle" onclick="chooseMyDeptTree('#deptid','#deptname',callbackDept);"/>
						    <div class="select-trigger" onclick="chooseMyDeptTree('#deptid','#deptname',callbackDept);"/>
						</li>
						<li>
						    <span>岗位名称：</span>
						    <input id="postid" name="postid" type="hidden" value=""/>
						    <input id="postname" name="postname" class="inputselectstyle" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
						    <div class="select-trigger" onclick="choosePostTree('#postid','#postname',$('#deptid').val());" />
						</li>
					</ul>
					<ul>
			           	<li>
			           		<span><em class="red">* </em>工号：</span>
						    <input id="jobnumber" name="jobnumber" class="{required:true,number:true,maxlength:40} inputstyle"/>
			            </li>
		             	<li>
			           		<span><em class="red">* </em>姓名：</span>
						    <input id="name" name="name" class="{required:true,maxlength:20} inputstyle"/>
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
						    <span>内网电话：</span>
						    <input id="innerphone" name="innerphone" class="{maxlength:20} inputstyle"/>
						</li>
		            </ul>
		            <ul>
						<li>
						    <span>分机：</span>
						    <input id="extphone" name="extphone" class="{maxlength:20} inputstyle"/>
						</li>
						<li>
						    <span>手机：</span>
						    <input id="mobilephone" name="mobilephone" class="{maxlength:20} inputstyle"/>
						</li>
		            </ul>
		            <ul>
		            	<li>
						    <span>邮箱：</span>
						    <input id="email" name="email" class="{maxlength:40,email:true} inputstyle"/>
						</li>
		            	<li>
						    <span>备用手机：</span>
						    <input id="mobilephone2" name="mobilephone2" class="{maxlength:20} inputstyle"/>
						</li>
		            </ul>
		            <ul>
		            	<li>
						    <span>办公地址：</span>
						    <input id="officeaddress" name="officeaddress" class="{maxlength:100} inputstyle"/>
						</li>
		            </ul>
		            <ul>
						<li>
						    <span>备注：</span>
						     <textarea id="memo" name="memo" rows="5" style="width:575px;" class="{maxlength:500} areastyle" ></textarea>
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
