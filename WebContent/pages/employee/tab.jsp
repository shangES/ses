<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>表单管理</title>
<base href="${baseUrl }"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="skins/css/style.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/form.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css"/>

<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>


<script type="text/javascript">
var tid='${param.id}';
var edit='${edit}';
var pageState=false;


$(document).ready(function() {
	//tab页
	loadTab();
	
	
	//加载数据
	$("#detail").attr("src","employee.do?page=form_employee&id="+tid+"&postionguid=${param.postionguid}&edit="+edit);
	
});



//tab页
var tabIndex=0;
function loadTab(){
	$("#mytab").tabs({
    	select: function(event, ui) {
    		tabIndex=ui.index;
    		
    		//刷新
    		if(tabIndex==0){
    			$("#detail").attr("src","employee.do?page=form_employee&id="+tid+"&postionguid=${param.postionguid}&edit="+edit);
    		}else if(tabIndex==1){
    			$("#detail").attr("src","employee.do?page=form_position&id="+tid+"&edit="+edit);
    		}else if(tabIndex==2){
    			$("#detail").attr("src","employee.do?page=form_contract&id="+tid+"&edit="+edit);
    		}else if(tabIndex==3){
    			$("#detail").attr("src","employee.do?page=form_vacation&id="+tid+"&edit="+edit);
    		}else if(tabIndex==4){
    			$("#detail").attr("src","employee.do?page=form_eduexperience&id="+tid+"&edit="+edit);
    		}else if(tabIndex==5){
    			$("#detail").attr("src","employee.do?page=form_train&id="+tid+"&edit="+edit);
    		}else if(tabIndex==6){
    			$("#detail").attr("src","employee.do?page=form_certification&id="+tid+"&edit="+edit);
    		}else if(tabIndex==7){
    			$("#detail").attr("src","employee.do?page=form_workexperience&id="+tid+"&edit="+edit);
    		}else if(tabIndex==8){
    			$("#detail").attr("src","employee.do?page=form_family&id="+tid+"&edit="+edit);
    		}else if(tabIndex==9){
    			$("#detail").attr("src","employee.do?page=form_addresslist&id="+tid+"&edit="+edit);
    		}else if(tabIndex==10){
    			$("#detail").attr("src","employee.do?page=form_relatives&id="+tid+"&edit="+edit);
    		}else if(tabIndex==11){
    			$("#detail").attr("src","employee.do?page=form_recommend&id="+tid+"&edit="+edit);
    		}else if(tabIndex==12){
    			$("#detail").attr("src","employee.do?page=form_Annual&id="+tid+"&edit="+edit);
    		}
    	}
    });
}





//切换视图
function convertView(url){
	window.parent.convertView(pageState?null:'');
}



//回调
function callbackEmpolyee(id,state){
	tid=id;
	
	$("#position").show();
	
	if(${contract==true})
		$("#contract").show();
	
	if(${vacation==true})
		$("#vacation").show();
	
	if(${eduexperience==true})
		$("#eduexperience").show();
	
	if(${train==true})
		$("#train").show();
	
	if(${certification==true})
		$("#certification").show();
	
	if(${workexperience==true})
		$("#workexperience").show();
	
	if(${addresslist==true})
		$("#addresslist").show();
	
	if(${family==true})
		$("#family").show();
		
	if(${relatives==true})
		$("#relatives").show();
	
	if(${recommend==true})
		$("#recommend").show();
	
	if(${annual==true})
		$("#annual").show();
	
	//离职
	if(state==-1)
		edit=false;
}



//页面保存状态
function callbackPageState(state){
	pageState=state;
	
}
</script>


</head>
<body>

<div class="sort">
	<div class="sort-title">
		<h3>员工管理</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>
	
	<div class="sort-cont sort-table">
		<div id="mytab">
			<ul>
				<li><a href="#tab0">基本信息</a></li>
				<li id="position" style="display:none;"><a href="#tab0">任职经历</a></li>
				<li id="contract" style="display:none;"><a href="#tab0">合同信息</a></li>
				<li id="vacation" style="display:none;"><a href="#tab0">请假信息</a></li>
				<li id="eduexperience" style="display:none;"><a href="#tab0">教育经历</a></li>
				<li id="train" style="display:none;"><a href="#tab0">培训经历</a></li>
				<li id="certification" style="display:none;"><a href="#tab0">职称认证</a></li>
				<li id="workexperience" style="display:none;"><a href="#tab0">工作经历</a></li>
				<li id="family" style="display:none;"><a href="#tab0">家庭状况</a></li>
				<li id="addresslist" style="display:none;"><a href="#tab0">通讯录</a></li>
				<li id="relatives" style="display:none;"><a href="#tab0">公司亲属表</a></li>
				<li id="recommend" style="display:none;"><a href="#tab0">入职推荐表</a></li>
				<li id="annual" style="display:none;"><a href="#tab0">年休假信息</a></li>
			</ul>
			<div id="tab0">
				<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" scrolling="no" src="" ></iframe>
			</div>
		</div>
	</div>
</div>




</body>
</html>