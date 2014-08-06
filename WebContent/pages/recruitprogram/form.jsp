<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>招聘计划管理</title>
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
var edit='${param.edit==true}';

var pageState=false;
var myvalidform=null;
$(document).ready(function() {
    //加载表单验证
    myvalidform=$("#myForm").validate({submitHandler:function(form) {
	    	$(form).ajaxSubmit(function(data) {
	    		alert("保存成功");
	    		tid=data.recruitprogramguid;
	    		
	    		loadData(tid);
	    		
	    		pageState=true;
	    		
	        	//页面保存过
	        	window.parent.callbackPageState(true);
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
		$.getJSON("recruitprogram/getRecruitprogramById.do", {id:tid,taskid:taskid}, function(data) {
			if(data!=null){
				for(var key in data){
					if($('#myForm #'+key)){
						$('#myForm #'+key).val(data[key]);
					}
				}
				//按钮状态
				initButtonState(data);
				
				//oa过来的且细分过来的不能修改
				if(data.interfacecode!=null&&data.interfacecode!=''&&data.quotaid==null){
					formDisabled(true,'#myForm');
					$("#save").hide();
					
					 //回调
					window.parent.callbackRecruitprogram(tid,data.quotaid,data.interfacecode);
					return;
				}
			}
		});
	else{
		//按钮状态
		initButtonState(null);
	}
		
}



//按钮状态
function initButtonState(obj){
	//判断是否可以保存
	  if(!edit){
		formDisabled(true,'#myForm');
		$("#save").hide();
		
		 //回调
		window.parent.callbackRecruitprogram(tid,obj.quotaid,obj,interfacecode);
		return;
	}	

	if(obj==null){
		$("#save").show();
		return;
	}
	//发布
	if(obj.state==3){
		formDisabled(false,'#myForm');
		formDisabled(true,'.deptidLi');
		
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
		//$("#save").hide();
		$("#save").show();
		$("#audit").hide();
	}
	
	
	//审核信息
	if(obj.audithistorys!=null){
		renderProcessInfo(obj.audithistorys);
	}
	
	
	//显示oa 基本信息 标签页
	if(window.parent.callbackRecruitprogram)
		window.parent.callbackRecruitprogram(tid,obj.quotaid,obj.interfacecode);
	
}






//保存
function save(){
	$("#runprocess").val(true);
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
		        	    		 loadData(tid);
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
			招聘计划信息
		</div>
		<div class="table-ctrl">
			<a class="btn" id="audit" href="javascript:audit();" style="display:none;><i class="icon icon-random"></i><span>会签审核</span></a>	
			<a class="btn" id="save" href="javascript:save();" ><i class="icon icon-check"></i><span>保存</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper" >
		<form action="recruitprogram/saveOrUpdateRecruitProgram.do" id="myForm"  class="form" method="post">
			<input type="hidden" id="recruitprogramguid" name="recruitprogramguid" value=""/>
			<input type="hidden" id="interfacecode" name="interfacecode" value=""/>
			<input type="hidden" id="recruitprogramcode" name="recruitprogramcode" value=""/>
			<input type="hidden" id="processinstanceid" name="processinstanceid" value=""/>
			<input type="hidden" id="state" name="state" value="-1"/>
			<input type="hidden" id="modiuser" name="modiuser" value="${username}" />
			<input type="hidden" id="moditimestamp" name="moditimestamp" value="" />
			<input type="hidden" id="modimemo" name="modimemo" value="" />
			<input type="hidden" id="runprocess" name="runprocess" value="true" />
			
			<!-- 流程信息 -->
			<input id="variables_commit" name="variables['rate']" type="hidden" value="2" />
			<input id="variables_commitNum" name="variables['commitNum']" type="hidden" value="1" />
			
			
			
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
						<span><em class="red">* </em>计划到岗时间：</span>
						<input id="hillockdate" name="hillockdate" class="{required:true,dateISO:true}  inputselectstyle datepicker"/>
				    	<div class="date-trigger" onclick="$('#hillockdate').focus();"/>
					</li>
				</ul>
		    	<ul>
					<li class="deptidLi">
						<span><em class="red">* </em>申请时间：</span>
						<input id="applydate" name="applydate" class="{required:true,dateISO:true}  inputselectstyle datepicker"/>
				   	 	<div class="date-trigger" onclick="$('#applydate').focus();"/>
					</li>
					<li>
						<span><em class="red">* </em>招聘人数：</span>
						<input id="postnum" name="postnum" class="{required:true,maxlength:38}  inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
						<span><em class="red">* </em>变更原因：</span>
						<textarea id="memo" name="memo" rows="5" cols="40" style="width:570px;" class="{required:true,maxlength:500} areastyle"></textarea>
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






<!-- 审核信息 -->
<div id="auditDialog" title="审核信息" style="display:none;">
    <form action="recruitprogram/completeTask.do" id="auditForm" class="form" method="post">
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
