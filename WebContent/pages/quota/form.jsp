<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>编制管理</title>
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
var taskid='${param.taskid}';

var pageState=false;
var myvalidform=null;
$(document).ready(function() {
    //加载表单验证
    myvalidform=$("#myForm").validate({submitHandler:function(form) {
	    	$(form).ajaxSubmit(function(data) {
	    		alert("保存成功");
	    		tid=data.quotaid;
	    		
	    		loadData();
	    		
	    		pageState=true;
	    		
	        	//页面保存过
	        	window.parent.callbackPageState(true);
	        });
		}
	});
    
    
  	//加載數據
    //loadData();
    
    
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
function loadData(){
	if(tid!=null&&tid!=''&&tid!='null'){
		$.getJSON("quota/getQuotaById.do", {id:tid,taskid:taskid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#myForm #' + key);
			        if(el) 
			            el.val(data[key]);
			    }
				
				//按钮状态
				butState(data);
				
				//回调
				if(window.parent.callbackQuota)
					window.parent.callbackQuota(tid);
			}
		});
	}else{
		butState(null);
	}
}





//按钮
function butState(obj){
	//判断是否可以保存
	if(${param.edit!=true}){
		formDisabled(true,'#myForm');
		return;
	}
	
	if(obj==null){
		$("#save").show();
		return;
	}
	
	//是否审核中
	if(obj.state==1){
		formDisabled(false,'#myForm');
		
		$("#save").show();
		$("#audit").hide();
	}else if(obj.state==2&&obj.assignee=='${userid}'){//当前用户是审核用户
		$("#taskid").val(taskid);
		formDisabled(true,'#myForm');
	
	
		$("#save").hide();
  		$("#audit").show();
	}else if(obj.state==2&&obj.assignee!='${userid}'){
		formDisabled(true,'#myForm');
		
		$("#save").hide();
		$("#audit").hide();
	}else{
		formDisabled(false,'#myForm');
		$("#save").show();
		$("#audit").hide();
	}
	
	//审核信息
	if(obj.audithistorys!=null){
		renderProcessInfo(obj.audithistorys);
	}
}





//保存
function save(){
	var postid=$("#postid").val();
	var quotaid=$("#quotaid").val();
	var state=$("#state").val();
	var budgettype=$("#budgettype").val();
	$.post("quota/checkQuotaByPostIdAndBudgettype.do",{quotaid:quotaid,postid:postid,budgettype:budgettype,state:state}, function(data) {
		if(data!=null&&data!=""){
			alert(data);
			$("#budgettype").val(null);
			$("#budgettypename").val(null);
			$("#postid").val(null);
			$("#postname").val(null);
		}else
			$("#runprocess").val(true);
			$('#myForm').submit();
    });
}



//返回
function back(){
	window.parent.convertView(pageState?null:'');
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



//岗位回调
function callbackpost(){
	var postid=$("#postid").val();
	var quotaid=$("#quotaid").val();
	var state=$("#state").val();
	var budgettype=$("#budgettype").val();
	$.post("quota/checkQuotaByPostIdAndBudgettype.do",{quotaid:quotaid,postid:postid,budgettype:budgettype,state:state}, function(data) {
		if(data!=null&&data!=""){
			alert(data);
			$("#postid").val(null);
			$("#postname").val(null);
		}
    });
	
}






//验证此岗位下编制是否被占
function callbackBudgettype(){
	var postid=$("#postid").val();
	var quotaid=$("#quotaid").val();
	var state=$("#state").val();
	var budgettype=$("#budgettype").val();
	$.post("quota/checkQuotaByPostIdAndBudgettype.do",{quotaid:quotaid,postid:postid,budgettype:budgettype,state:state}, function(data) {
		if(data!=null&&data!=""){
			alert(data);
			$("#budgettype").val(null);
			$("#budgettypename").val(null);
		}
    });
}



//流程处理
function audit(){
	if(myvalidform.form()){
		$("#auditDialog").dialog({
	        autoOpen: true,
	        modal: true,
	        model:true,
	        resizable:false,
	        width:500,
	        buttons: {
	            "确定": function() {
	            	$("#auditForm").submit();
	            },
	            "取消": function() {
	                $(this).dialog("close");
	            }
	        },
	        open:function(){
	        	if(auditForm){
	        		$("#auditForm").validate({submitHandler:function(form) {
		        	    	$(form).ajaxSubmit(function(data) {
		        	    		 alert("审核成功");
		        	    		 pageState=true;
		        	    		 loadData();
		        	    		 
		        	    		 //页面保存过
	        		        	 window.parent.callbackPageState(pageState);
		        	    		 $("#auditDialog").dialog("close");
		        	        });
		        		}
		        	});
	        	}
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
			编制基本信息
		</div>
		<div class="table-ctrl">
			<a class="btn" id="audit" href="javascript:audit();" style="display:none;><i class="icon icon-random"></i><span>会签审核</span></a>	
			<a class="btn save" href="javascript:save();" id="save" ><i class="icon icon-check"></i><span>保存</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<form action="quota/saveOrUpdateQuota.do" method="post" id="myForm" class="form">
			<input id="quotaid" name="quotaid" type="hidden" value="" />
			<input id="quotacode" name="quotacode" type="hidden" value="" />
			<input id="state" name="state" type="hidden" value="1" />
			<input id="modiuser" name="modiuser" type="hidden" value="" />
			<input id="modimemo" name="modimemo" type="hidden" value="" />
			<input id="moditimestamp" name="moditimestamp" type="hidden" value="" />
			<input type="hidden" id="runprocess" name="runprocess" value="true" />
			
			<!-- 流程信息 -->
			<input id="variables_commit" name="variables['rate']" type="hidden" value="2" />
			<input id="variables_commitNum" name="variables['commitNum']" type="hidden" value="1" />
			
			<fieldset>
				<legend>基本信息</legend>
				<ul>
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
					    <span>岗位名称：</span>
					    <input id="postid" name="postid" type="hidden" value=""/>
					    <input id="postname" name="postname" class="inputselectstyle" onclick="choosePostTree('#postid','#postname',$('#deptid').val(),callbackpost);"/>
					    <div class="select-trigger" onclick="choosePostTree('#postid','#postname',$('#deptid').val());" />
					</li>
					<li>
						<span><em class="red">* </em>编制类别：</span>
						<input id="budgettype" name="budgettype" type="hidden" value=""/>
						<input id="budgettypename" name="budgettypename" class="{required:true} inputselectstyle" onclick="chooseBudgettypeTree('#budgettype','#budgettypename',$('#companyid').val(),callbackBudgettype);"/>
						<div class="search-trigger" onclick="chooseBudgettypeTree('#budgettype','#budgettypename',$('#companyid').val(),callbackBudgettype);"/>
					</li>
				</ul>
				<ul>
					
					<li>
						<span><em class="red">* </em>编制人数：</span>
						<input id="budgetnumber" name="budgetnumber" class="{required:true,number:true,maxlength:40} inputstyle" value=""/>
					</li>
					<li>
						<span><em class="red">* </em>生效日期：</span>
						<input id="budgetdate" name="budgetdate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
						<div class="date-trigger" onclick="$('#budgetdate').focus();"/>
					</li>
				</ul>
	            <ul>
					<li>
					    <span><em class="red">* </em>变更原因：</span>
					    <textarea id="memo" name="memo" rows="5" style="width:575px;" class="{required:true,maxlength:500} areastyle" ></textarea>
					</li>
	            </ul>
				</fieldset>
      		</form>
      		<div class="form" id="myProcess">
    	
    		</div>
      		<br>
      		<br>
      		<br>
      		<br>
		</div>	
</div>

<!-- 审核信息 -->
<div id="auditDialog" title="审核信息" style="display:none;">
    <form action="quota/completeTask.do" id="auditForm" class="form" method="post">
    	<input type="hidden" id="taskid" name="taskid" value=""/>
        <ul>
        	<li>
        		<span>&nbsp;</span>
        		<label>
        			<input type="radio" name="commit" class="checkboxstyle" value="1" checked="true"/>同意
        		</label>
        		<label>
        			<input type="radio" name="commit" class="checkboxstyle" value="0"/>不同意
        		</label>
        	</li>
        </ul>
        <ul>
			<li>
				<span><em class="red">* </em>审核意见：</span>
				<textarea name="result" rows="8" style="width:280px;" class="{required:true,maxlength:500} areastyle"></textarea>
			</li>
		</ul>
    </form>
</div>


</body>
</html>
