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
<link rel="stylesheet" type="text/css" href="pages/addresslist/css/web.css"/>

<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="pages/addresslist/css/web.js"></script>
<script type="text/javascript" src="skins/js/jquery.layout.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	$('body').layout({
		
    });
    
    
	loadData('${param.id}');
});


//加载数据
function loadData(tid){
	$.getJSON("webaddresslist/getWebAddressListById.do", {id:tid}, function(data) {
		if(data!=null){
			for (var key in data) {
		        var el = $('#myForm #' + key);
		        if(el) 
		            el.html(data[key]);
		    }
			
			//加载同部门员工
			loadDeptPersonData(tid,data.deptid);
			
			
			//定位部门
			window.parent.makerTreePosation(data.deptid);
		}
	});
}



//加载同部门员工
function loadDeptPersonData(tid,tdeptid){
	$("#sameDeptEmployee").html(null);
	$.getJSON("webaddresslist/getWebAddressListByDpetId.do", {id:tid,deptid:tdeptid}, function(data) {
		if(data!=null){
			var htm="";
			for(var i=0;i<data.length;i++){
				var obj=data[i];
				htm+='<a href="javascript:loadData(\''+obj.addresslistguid+'\');">';
				htm+=obj.name;
				//htm+="("+obj.jobnumber+")";
				htm+='</a> &nbsp;&nbsp;';
			}
			$("#sameDeptEmployee").html(htm);
		}
	});
}




//返回
function back(){
	window.parent.convertView('');
}

</script>

</head>

<body>

<div style="padding:5px;" class="ui-layout-center">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="background:#F4F6F8;border:1px solid #D7DCE2;border-bottom:0px;">
		<tr>
			<td>
				&nbsp;&nbsp;员工通讯录信息
			</td>
			<td width="65px" style="padding:5px 0px;">
				<a class="webbtn" href="javascript:back();"><img src="pages/addresslist/css/images/bullet_go.png"/>返回</a>
			</td>
		</tr>
	</table>
	
	<div id="myForm" >
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="statictable" align="center">
			<tr>
				<td class="label" width="120px">公司</td>
				<td id="companyname"></td>
			</tr>
			<tr>
				<td class="label">部门</td>
				<td id="deptname"></td>
			</tr>
			<tr>
				<td class="label" >员工工号</td>
				<td id="jobnumber" name="jobnumber"></td>
			</tr>
			<tr>
				<td class="label">姓名</td>
				<td id="name" name="name"></td>
			</tr>
			<tr>
				<td class="label">性别</td>
				<td id="sexname"></td>
			</tr>
			
			<tr>
				<td class="label">邮箱</td>
				<td id="email"></td>
			</tr>
			<tr>
				<td class="label">手机</td>
				<td id="mobilephone"></td>
			</tr>
			<tr>
				<td class="label">办公电话</td>
				<td id="extphone"></td>
			</tr>
			<tr>
				<td class="label">手机内网号码</td>
				<td id="innerphone"></td>
			</tr>
		    <tr>
		        <td class="label">同部门其他员工</td>
		        <td id="sameDeptEmployee">
		       		<img src="skins/images/ajax-loader-small.gif"/>
		        </td>
		    </tr>
		</table>
	</div>
</div>

</body>
</html>
