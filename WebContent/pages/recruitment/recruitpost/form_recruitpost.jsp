<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>招聘职位管理</title>
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
<script type="text/javascript" src="plugin/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="pages/tree.js"></script>

<script type="text/javascript">
var tid='${param.id}';
var pageState=false;

$(document).ready(function() {
	//日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
       	beforeShow: function(input, inst) {
   			if($(input).attr("readonly"))
   				inst.inline=true;
   		}
    });
	
	
    //加载表单验证
    $("#myForm").validate({submitHandler:function(form) {
	    	$(form).ajaxSubmit(function(data) {
	    		
	    		alert("操作成功！");
	    		
	    		//重新加载
	    		loadData(data.recruitpostguid,false);
	    		
	    		pageState=true;
	    		
	    		
	        	//页面保存过
	        	window.parent.callbackPageState(true);
	        });
		}
	});
  	
    
  	//加載數據
    loadData(tid,true);
    
    
});




//取数据
function loadData(tid,state) {
	if(tid!=null&&tid!=''&&tid!='null'){
		$.getJSON("recruitment/getRecruitPostById.do", {id:tid}, function(data) {
			if(data!=null){
				$("#isurgent"+data.isurgent).attr("checked",true);
				for(var key in data){
					//正文
					if(key=='postcontent'){
						var content=data[key];
						if(content!=null){
							$("#postcontent").val(content.postcontent);
						}
					}else if($('#myForm #'+key)){
						$('#myForm #'+key).val(data[key]);
					}
					
				}
				
				//显示招聘计划标签页
				if(window.parent.callbackRecruitpost)
					window.parent.callbackRecruitpost(tid,data.recruitprogramguid);
				
				//判定下状态
				initPageState();
				//渲染编辑器
				if(state)
					renderHtmlBox();
				
			}
		});
	}else{
		//控制状态
		initPageState();
		
		//渲染编辑器
		if(state)
			renderHtmlBox();
	}
}







//页面状态
function initPageState(){
	if(!${edit==true}){
		formDisabled(true);
		
		//编辑器只读
		CKEDITOR.config.readOnly = true; 
		$("#save").hide();
		$("#audit").hide();
		$("#cancel").hide();
		return;
	}
	
	
	//审核权限
	if(${audit==true}){
		//判断是否审核
		var isaudited=$("#isaudited").val();
		//已经审核
		if(isaudited==0){
			$("#cancel").show();
			$("#save").hide();
			$("#audit").hide();
			formDisabled(true);
			
			
			//编辑器只读
			CKEDITOR.config.readOnly = true; 
	   	}else{
	   		$("#cancel").hide();
			$("#save").show();
			$("#audit").show();
			formDisabled(false);
			
			//编辑器只读
			CKEDITOR.config.readOnly = false; 
	   	}
	}
}




//保存
function save(isaudit){
	var content=CKEDITOR.instances.postcontent.getData();
	if(content!=null&&content!=''&&content.length>67){
		//審核人去掉
		if(isaudit==1){
			$("#audituser").val(null);
		}else{
			$("#audituser").val('${userid}');
		}
		$("#isaudited").val(isaudit);
		$("#postcontent").val(content);
		$("#myForm").submit();
	}else
		alert("招聘正文不能为空！");
}




//返回
function back(){
	window.parent.convertView(pageState?null:'');
}





//公司回调
function callBackCompany(){
	$("#deptid").val(null);
	$("#deptname").val(null);
}



//回调招聘计划
function recruitprogramTreeCallback(id){
	if(id!=null&&id!=''&&id!='null'){
		$.getJSON("recruitprogram/getRecruitprogramById.do", {id:id}, function(data) {
			if(data!=null){
				$("#companyid").val(data.companyid);
				$("#companyname").val(data.companyname);
				$("#deptid").val(data.deptid);
				$("#deptname").val(data.deptname);
				$("#deptname").val(data.deptname);
				$("#postnum").val(data.postnum);
			}
		});
	}
}
</script>

<script type="text/javascript">
//渲染编辑器
function renderHtmlBox(){
	CKEDITOR.replace('postcontent',{
		height:500,
		fullPage : true,
		filebrowserFlashUploadUrl : '${baseUrl}uploadCkeditFile.do?directory=ckedit',
		filebrowserImageUploadUrl : '${baseUrl}uploadCkeditFile.do?directory=ckedit'
	});
}

</script>




</head>

<body>
<div class="table">
	<div class="table-bar">
		<div class="table-title">
			招聘职位信息
		</div>
		<div class="table-ctrl">
			<a class="btn" id="save" href="javascript:save(1);" style="display:none"><i class="icon icon-check"></i><span>保存</span></a>
			<a class="btn" id="audit" href="javascript:save(0);" style="display:none"><i class="icon icon-ok-circle"></i><span>审核发布</span></a>
			<a class="btn" id="cancel" href="javascript:save(1);" style="display:none"><i class="icon icon-ban-circle"></i><span>取消发布</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<form action="recruitment/saveOrUpdateRecruitPost.do" id="myForm"  class="form" method="post">
			<input type="hidden" id="recruitpostguid" name="recruitpostguid" value=""/>
			<input id="isaudited" name="isaudited" type="hidden" value="1"/>
			<input id="modtime" name="modtime" type="hidden" value=""/>
			<input id="traffic" name="traffic" type="hidden" value="0" />
			<input id="collection" name="collection" type="hidden" value="0" />
			<input id="releasedate" name="releasedate" type="hidden" value=""/>
			<input id="pubuser" name="pubuser" type="hidden" value="${username}"/>
			<input id="audituser" name="audituser" type="hidden"/>
			<fieldset>
				<legend>基本信息</legend>
				<ul>
					<li>
						<span><em class="red">* </em>排序号：</span>
						<input id="dorder" name="dorder" class="{required:true,maxlength:38,number:true} inputstyle"  />
					</li>
					<li>
					    <span style="color:green;">招聘计划：</span>
					    <input id="recruitprogramguid" name="recruitprogramguid" type="hidden" value=""/>
					    <input id="recruitprogramname" name="recruitprogramname" class="inputselectstyle" onclick="chooseRecruitprogramTree('#recruitprogramguid','#recruitprogramname','=-1');"/>
					    <div class="search-trigger" onclick="chooseRecruitprogramTree('#recruitprogramguid','#recruitprogramname','=-1');" title="输入部门!"/>
					</li>
				</ul>
				
				<ul>
					<li>
		                <span><em class="red">* </em>公司名称：</span>
		                <input id="companyid" name="companyid" type="hidden" value=""/>
					    <input id="companyname" name="companyname" class="{required:true} inputselectstyle" onclick="chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
					    <div class="search-trigger" onclick="chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
		            </li>
					<li>
					    <span>部门名称：</span>
					    <input id="deptid" name="deptid" type="hidden" value=""/>
					    <input id="deptname" name="deptname" class="inputselectstyle" onclick="chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val());"/>
					    <div class="search-trigger" onclick="chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val());"/>
					</li>
				</ul>
				
				<ul>
					<li>
		                <span><em class="red">* </em>招聘类别：</span>
		                <input id="posttype" name="posttype" type="hidden" value=""/>
					    <input id="posttypename" name="posttypelanguagename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#posttype','#posttypename','POSTTYPE');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#posttype','#posttypename','POSTTYPE');"/>
		            </li>
					<li>
					    <span><em class="red">* </em>职位类别：</span>
					    <input id="categoryguid" name="categoryguid" type="hidden" value=""/>
					    <input id="categoryname" name="categoryname" class="{required:true} inputselectstyle" onclick="chooseCategoryTree('#categoryguid','#categoryname');"/>
					    <div class="select-trigger" onclick="chooseCategoryTree('#categoryguid','#categoryname');"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span><em class="red">* </em>职位名称：</span>
					    <input id="postname" name="postname" class="{required:true,maxlength:50} inputstyle" style="width:570px;"/>
					</li>
				</ul>
				
			</fieldset>
			<fieldset>
				<legend>其他信息</legend>
				<ul>
					<li>
					    <span><em class="red">* </em>工作年限：</span>
						<input id="workage" name="workage" type="hidden" value=""/>
					    <input id="workagename" name="workagename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#workage','#workagename','WORKAGE');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#workage','#workagename','WORKAGE');"/>
					
					</li>
					<li>
					    <span><em class="red">* </em>语言要求：</span>
					    <input id="language" name="language" type="hidden" value=""/>
					    <input id="languagename" name="languagename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#language','#languagename','LANGUAGE');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#language','#languagename','LANGUAGE');"/>
					</li>
				</ul>
				<ul>	
					<li>
					    <span>学历要求：</span>
					    <input id="educational" name="educational" type="hidden" value=""/>
					    <input id="educationalname" name="educationalname" class="inputselectstyle" onclick="chooseOptionTree('#educational','#educationalname','CULTURE');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#educational','#educationalname','CULTURE');"/>
					</li>
					<li>
					    <span><em class="red">* </em>工作地点：</span>
					    <input id="workplaceguid" name="workplaceguid" type="hidden" value=""/>
					    <input id="workplacename" name="workplacename" class="{required:true} inputselectstyle" onclick="chooseWorkplaceTree('#workplaceguid','#workplacename');"/>
					    <div class="select-trigger" onclick="chooseWorkplaceTree('#workplaceguid','#workplacename');"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>招聘人数：</span>
					    <input id="postnum" name="postnum" class="{maxlength:10,number:true,digits:true} inputstyle"/>
					</li>
					<li>
					    <span>岗位职能：</span>
					    <input id="functions" name="functions" class="{maxlength:50} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>关键字：</span>
					    <input id="keyword" name="keyword" class="{maxlength:100} inputstyle"/>
					</li>
					<li>
		                <span><em class="red">* </em>失效时间：</span>
		                <input id="validdate" name="validdate" class="{required:true} inputselectstyle datepicker"/>
		               	<div class="date-trigger" onclick="$('#validdate').focus();"/>
		            </li>
				</ul>
				
				<ul>
		            <li>
					    <span>&nbsp;</span>
					    <label for="isurgent0">
							<input type="radio" id="isurgent0" name="isurgent" value="0" checked="true"  class="checkboxstyle"/>不紧急
						</label>
						<label for="isurgent1">
					    	<input type="radio" id="isurgent1" name="isurgent" value="1" class="checkboxstyle"/>紧急
					    </label>
					</li>
				</ul>
				<ul>
					<li>
						<span>备注：</span>
						<textarea id="rmk" name="rmk" rows="5" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
					</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend><em class="red">* </em>招聘正文</legend>
				<div style="height:650px">
					<textarea id="postcontent" name="postcontent.postcontent" style="display:none;"></textarea>
				</div>
			</fieldset>
		</form>
	</div>


</body>
</html>
