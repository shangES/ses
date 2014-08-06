<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>我的应聘管理</title>
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

var pageState=false;
$(document).ready(function() {
	
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
		$.getJSON("mycandidates/getMyCandidatesById.do", {id:tid}, function(data) {
			if(data!=null){
				for(var key in data){
					if($('#myForm #'+key)){
						$('#myForm #'+key).val(data[key]);
					}
				}
				//按钮状态
				initButtonState();
				
				
				//回调
				if(window.parent.callbackShowTab)
					window.parent.callbackShowTab(data);
				
			}
		});
	else{
		//按钮状态
		initButtonState();
	}
		
}



//按钮状态
function initButtonState(){
	formDisabled(true,"#myForm");
}








//返回
function back(){
	window.parent.convertView('');
}




</script>
</head>

<body>
<div class="table">
	<div class="table-bar">
		<div class="table-title">
			应聘管理信息
		</div>
		<div class="table-ctrl">
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper" >
		<form action="" id="myForm"  class="form" method="post">
			<input type="hidden" id="mycandidatesguid" name="mycandidatesguid" value=""/>
			<input type="hidden" id="recruitpostguid" name="recruitpostguid" value=""/>
			<input type="hidden" id="resumeguid" name="resumeguid" value=""/>
			<input type="hidden" id="state" name="state" value="1"/>
			<input type="hidden" id="holdmemo" name="holdmemo" value=""/>
			
			<input id="progress" name="progress" type="hidden" value=""/>
			
			<fieldset>
			<legend>基本信息</legend>
				<ul>
					<li>
		                <span>申请人：</span>
		                <input id="webuserguid" name="webuserguid" type="hidden"  value=""/>
		                <input id="webusername" name="webusername"  class="inputstyle" value=""/>
		            </li>
					<li>
						<span>申请时间：</span>
						<input id="candidatestime" name="candidatestime" class="{required:true,dateISO:true}  inputselectstyle datepicker"/>
				   	 	<div class="date-trigger" onclick="$('#candidatestime').focus();"/>
					</li>
				</ul>
				<ul>
					<li>
						<span>匹配人：</span>
						<input id="matchuser" name="matchuser" class="inputstyle" value=""/>
					</li>
					<li>
						<span>匹配时间：</span>
						<input id="matchtime" name="matchtime" class="{required:true,dateISO:true}  inputselectstyle datepicker"/>
				   	 	<div class="date-trigger" onclick="$('#matchtime').focus();"/>
					</li>
				</ul>
				<ul>
					<li>
						<span>认定人：</span>
						<input id="holduser" name="holduser" class="inputstyle" value=""/>
					</li>
					<li>
						<span>认定时间：</span>
						<input id="holdtime" name="holdtime" class="{required:true,dateISO:true}  inputselectstyle datepicker"/>
				   	 	<div class="date-trigger" onclick="$('#holdtime').focus();"/>
					</li>
				</ul>
		    	<ul>
					<li>
						<span>推荐公司：</span>
						<input id="recommendcompanyid" name="recommendcompanyid" type="hidden"  value=""/>
						<input id="recommendcompanyname" name="recommendcompanyname" class="{required:true,maxlength:38}  inputstyle"/>
					</li>
					<li>
						<span>推荐部门：</span>
						<input id="recommenddeptid" name="recommenddeptid" type="hidden"  value=""/>
						<input id="recommenddeptname" name="recommenddeptname" class="{required:true,maxlength:38}  inputstyle"/>
					</li>
				</ul>
		    	<ul>
					<li>
						<span>推荐岗位：</span>
						<input id="recommendpostguid" name="recommendpostguid" type="hidden"  value=""/>
						<input id="recommendpostname" name="recommendpostname" class="{required:true,maxlength:38}  inputstyle"/>
					</li>
					<li>
						<span>建议面试时间：</span>
						<input id="auditiontime" name="auditiontime" class="{required:true,dateISO:true}  inputselectstyle datepicker"/>
				   	 	<div class="date-trigger" onclick="$('#auditiontime').focus();"/>
					</li>
				</ul>
				
				<ul>
					<li>
						<span>备注：</span>
						<textarea id="matchmemo" name="matchmemo" rows="5" cols="40" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
					</li>
				</ul>
			</fieldset>
		</form>
	</div>


</body>
</html>
