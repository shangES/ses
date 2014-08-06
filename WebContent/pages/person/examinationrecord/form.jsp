<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>体检记录</title>
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
<script type="text/javascript" src="skins/js/jquery.ajaxupload.3.6.js"></script>
<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="pages/person/person.js"></script>

<script type="text/javascript">
var tid='${param.id}';
var pageState=true;
$(document).ready(function() {
	
	
	//加载表单验证
    $("#myForm").validate({debug:true,submitHandler: function(form) {
			$(form).ajaxSubmit(function(data) {
				tid=data.examinationrecordguid;
				//重新加载
			//	loadData(tid);
				//不可修改
				formDisabled(true,'#myForm');
			});
		}
	});
	
	//加载
	loadData(tid);
	
    
	//日期选择,主题js
	initDatePicker();
	
});



//加载数据
function loadData(tid) {
	if(tid!=null&&tid!=''&&tid!='null'){
		$.getJSON("examinationRecord/getExaminationRecordById.do", {id:tid}, function(data) {
			if(data!=null){
				for(var key in data){
					if($('#myForm #'+key)){
						$('#myForm #'+key).val(data[key]);
					}
				}
				
				//简历不可修改
				formDisabled(true,'#myForm');
				
			}
		});
	}
}



//修改
function editPerson(){
	formDisabled(false,'#myForm');
}


//保存
function savePerson(){
	$('#myForm').submit();
}


//返回
function back(){
	window.parent.convertView(pageState?null:'');
}

</script>


</head>

<body>
<div class="sort0">
<div class="table">
	<div class="table-bar">
		<div class="table-title">
			体检记录信息
		</div>
		<div class="table-ctrl">
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<form action="examinationrecord/saveOrUpdateExaminationRecord.do" id="myForm"  class="form" method="post">
			<fieldset>
				<legend>人员信息</legend>
				<input type="hidden" id="examinationrecordguid" name="examinationrecordguid" value=""/>
				<input type="hidden" id="mycandidatesguid" name="mycandidatesguid" value=""/>
				<input id="userguid" name="userguid" type="hidden" value=""/>
				<ul>
					<li>
						<span><em class="red">* </em>体检日期：</span>
						<input id="examinationdate" name="examinationdate" class="{required:true,dateISO:true} inputstyle inputselectstyle datepicker"/>
						<div class="date-trigger" onclick="$('#examinationdate').focus();"/>
					</li>
					<li>
					    <span><em class="red">* </em>体检地址：</span>
					    <input id="examinationaddress" name="examinationaddress" class="{required:true,maxlength:25} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span><em class="red">* </em>体检结果：</span>
					    <input id="examinationresult" name="examinationresult" class="{required:true,maxlength:25} inputstyle"/>
					</li>
					<li>
					    <span><em class="red">* </em>操作人：</span>
					    <input id="modiuser" name="modiuser" class="{required:true,maxlength:25} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
						<span><em class="red">* </em>操作时间：</span>
						<input id="moditimestamp" name="moditimestamp" class="{required:true,dateISO:true} inputstyle inputselectstyle datepicker"/>
						<div class="date-trigger" onclick="$('#moditimestamp').focus();"/>
					</li>
					<li>
					    <span><em class="red">* </em>状态：</span>
					    <input id="examinationstate" name="examinationstate" type="hidden" value=""/>
					    <input id="examinationstatename" name="examinationstatename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#examinationstate','#examinationstatename','EXAMINATIONSTATE');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#examinationstate','#examinationstatename','EXAMINATIONSTATE');"/>
					</li>
				</ul>
				<ul>
		         	<li>
		                <span>操作备注：</span>
		                <textarea id="modimemo" name="modimemo" rows="5" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
		            </li>
		        </ul>
		        
		        <div class="formpanel">
					<div class="formpanel_line">
						<div class="right">
						    <a class="btn" href="javascript:editPerson();"><i class="icon icon-pencil"></i><span>修改</span></a>
							<a class="btn" href="javascript:savePerson();"><i class="icon icon-check"></i><span>保存</span></a>
						</div>
					</div>
				</div>
		        
		         
			</fieldset>
		</form>
							
	</div>
</div>
</body>
</html>
