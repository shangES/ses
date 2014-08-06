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
var companyid='${param.companyid}';
var deptid='${param.deptid}';
var companycode='${param.companycode}';
var deptcode='${param.deptcode}';
var companyname='${param.companyname}';
var deptname='${param.deptname}';


var pageState=false;
$(document).ready(function() {
    //加载表单验证
    $("#myForm").validate();
    $('#myForm').ajaxForm(function(data) {
    	if(data!=null){
    		alert("操作成功");
    		id=data.addresslistguid;
    		loadData(id);
    		pageState=true;
    	}
    });
    
    
    
  	////加載數據
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
		$.getJSON("addresslist/getAddressListById.do", {id:tid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#myForm #' + key);
			        if(el) 
			            el.val(data[key]);
			    }
				
			}
		});
	}else{
		
		$("#companyid").val(companyid);
		$("#deptid").val(deptid);
		$("#companyname").val(companyname);
		$("#deptname").val(deptname);
		
		$("#companycode").val(companycode);
		$("#deptcode").val(deptcode);
		
	}
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
}



</script>

</head>

<body>

<div class="sort">
	<div class="sort-title">
		<h3>通讯录管理</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>

	<div class="table">
		<div class="table-bar">
			<div class="table-title">
				通讯录信息
			</div>
			<div class="table-ctrl">
				<a class="btn save" href="javascript:save();" id='save'><i class="icon icon-check"></i><span>保存</span></a>
				<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
			</div>
		</div>
		<div class="table-wrapper">
			<form action="addresslist/saveOrUpdateAddressList.do" method="post" id="myForm" class="form">
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
						    <span><em class="red">* </em>排序号：</span>
						    <input id="dorder" name="dorder" class="{required:true,number:true,maxlength:19} inputstyle"/>
						</li>
						<li>
			           		<span>工号：</span>
						    <input id="jobnumber" name="jobnumber" class="{maxlength:20} inputstyle"/>
			            </li>
					</ul>
					<ul>
		             	<li>
			           		<span><em class="red">* </em>姓名：</span>
						    <input id="name" name="name" class="{required:true,maxlength:20} inputstyle"/>
	                  </li>
	                  <li>
						    <span><em class="red">* </em>性别：</span>
						    <input id="sex" name="sex" type="hidden" value=""/>
						    <input id="sexname" name="sexname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#sex','#sexname','SEX');"/>
						    <div class="select-trigger" onclick="chooseOptionTree('#sex','#sexname','SEX');"/>
						</li>
			       </ul>
		           <ul>
						<li>
						    <span>内网电话：</span>
						    <input id="innerphone" name="innerphone" class="{maxlength:20} inputstyle"/>
						</li>
						<li>
						    <span>分机：</span>
						    <input id="extphone" name="extphone" class="{maxlength:20} inputstyle"/>
						</li>
		            </ul>
		            <ul>
						<li>
						    <span>手机：</span>
						    <input id="mobilephone" name="mobilephone" class="{maxlength:20} inputstyle"/>
						</li>
						<li>
						    <span>备用手机：</span>
						    <input id="mobilephone2" name="mobilephone2" class="{maxlength:20} inputstyle"/>
						</li>
		            </ul>
		            <ul>
						<li>
						    <span>邮箱：</span>
						    <input id="email" name="email" class="{maxlength:40,email:true} inputstyle"/>
						</li>
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
</div>




</body>
</html>
