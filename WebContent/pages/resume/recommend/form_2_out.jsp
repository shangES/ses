<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>内部推荐</title>
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

<script type="text/javascript" src="pages/tree.js"></script>
<script type="text/javascript" src="pages/resume/recommend/tree.js"></script>

<script type="text/javascript">
var tid='${userid}';
var recruitpostguid='${param.recruitpostguid}';

$(document).ready(function() {
    //加载表单验证
    $("#myForm").validate();
    $('#myForm').ajaxForm(function(data) {
    	if(data!=null){
    		alert("操作成功!");
    		$("#myForm").clearForm();
    		$("input[name='mychecked1']").attr("checked",true);
    		$("input[name='mychecked2']").attr("checked",true);
    		$("#relativesData").html(null);
    		$("#relatives").hide();
    		loadrecruitpost(data.recruitpostguid);
    	}
    });
    
    initFile();
    
    
    //从职位列表过来的岗位信息
    loadrecruitpost(recruitpostguid);
    
    //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
       	beforeShow: function(input, inst) {
   			if($(input).attr("readonly"))
   				inst.inline=true;
   		}
    });
    
    //关闭等到层
  	if(window.parent.hidenLoading){
  		window.parent.hidenLoading();
  	}
});



//得到岗位信息
function loadrecruitpost(id){
	  $("#recruitpostguid").val(id);
	  if(id!=null&&id!=''&&id!='null'){
		  $.getJSON("recruitment/getRecruitPostById.do", {id:id}, function(data) {
				if(data!=null){
					$("#r_companyname").val(data.companyname);
					$("#r_deptname").val(data.deptname);
					$("#r_postname").val(data.postname);
				}
		  });
	  }
	  
}





//保存
function save(){
	$('#myForm').submit();
}


//回调
function callbackRecruiter(){
	$("#postname").val(null);
}



//上传附件
function initFile(){
    new AjaxUpload('#filepath', {
    	id:'upFile',
    	action: 'uploadFile.do',
    	name:'file1',
    	autoSubmit:true,
    	responseType: 'json',
    	data: {table:'t_resume_file'},
    	onComplete: function(filename,obj) {
			$("#filepath").val(filename);
    		$("#resumefilePath").val(obj.filepath);
    		$("#resumefilename").val(filename);
    	}
   	});
}




//返回
function back(){
	window.parent.convertView('');
}




</script>









<script type="text/javascript">
//选择招聘专员
function chooseRecruiter(){
	$("#chooseRecruiterWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:"650",
		height:500,
		buttons: {
			
		},
		open:function(){
			if($("#chooseRecruiter").attr("src")=="")
				$("#chooseRecruiter").attr("src","resume.do?page=recommend/chooseRecruiter");
		}
	});
}

//回调
function callbackChooseRecruiter(id){
	$("#recruitpostguid").val(id);
}

function emailTreeCallback(userid){
	if(userid!=null&&userid!=''&&userid!='null'){
		$.getJSON("resume/getResumeByUserId.do", {id:userid}, function(data) {
			if(data!=null&&data!=''){
				$("#name").val(data.name);
				$("#sex").val(data.sex);
				$("#sexname").val(data.sexname);
				$("#birthday").val(data.birthday);
				$("#mobile").val(data.mobile);
				$("#homeplace").val(data.homeplace);
				$("#culture").val(data.culture);
				$("#culturename").val(data.culturename);
				$("#workage").val(data.workage);
				$("#workagename").val(data.workagename);
				
				var relatives=data.relatives;
				if(relatives!=null&&relatives!=''){
					$("input[name='mychecked2']").attr("checked",false);
					loadRelatives(relatives);
				}else{
					$("input[name='mychecked2']").attr("checked",true);
					loadRelatives(null);
					$("#relatives").hide();
				}
					
				
			}else{
				$("input[name='mychecked2']").attr("checked",true);
				loadRelatives(null);
				$("#relatives").hide();
				
				
				$("#name").val(null);
				$("#sex").val(null);
				$("#sexname").val(null);
				$("#birthday").val(null);
				$("#mobile").val(null);
				$("#homeplace").val(null);
				$("#culture").val(null);
				$("#culturename").val(null);
				$("#workage").val(null);
				$("#workagename").val(null);
			}
		});
	}
}

</script>




<script type="text/javascript">

//根据姓名加载人员信息
function inputName(){
	var name=$("#name").val();
    if(name!=null&&name!=''&&name!='null'){
	$.getJSON("resume/getResumeByName.do", {name:name}, function(data) {
		if(data!=null){
			var relatives=data.relatives;
			if(relatives!=null&&relatives!=''){
				$("input[name='mychecked2']").attr("checked",false);
				loadRelatives(relatives);
			}else{
				$("input[name='mychecked2']").attr("checked",true);
				loadRelatives(null);
				$("#relatives").hide();
			}
			
			for(var key in data){
				if($('#myForm #'+key)){
					$('#myForm #'+key).val(data[key]);
				  }
			     }									
				}else{
					$("#email").val(null);
					$("#sex").val(null);
					$("#sexname").val(null);
					$("#birthday").val(null);
					$("#mobile").val(null);
					$("#homeplace").val(null);
					$("#culture").val(null);
					$("#culturename").val(null);
					$("#workage").val(null);
					$("#workagename").val(null);
				}			
         	});
         }else{
        	 
        	 	$("input[name='mychecked2']").attr("checked",true);
				loadRelatives(null);
				$("#relatives").hide();
        	 
			   $("#email").val(null);
			   $("#sex").val(null);
			   $("#sexname").val(null);
			   $("#birthday").val(null);
		       $("#mobile").val(null);
			   $("#homeplace").val(null);
			   $("#culture").val(null);
			   $("#culturename").val(null);
			   $("#workage").val(null);
			   $("#workagename").val(null);
			}			
}

















//渲染专家信息
var relativesNum=0;
function renderRelatives(obj){
	var htm="";
	htm+='<div id="relatives_'+relativesNum+'">';
	htm+='<ul>';
	htm+='<li>';
	htm+='<span><em class="red">* </em>亲属姓名：</span>';
	htm+='<input id="employeeid_'+relativesNum+'" name="relatives['+relativesNum+'].employeeid" type="hidden" index="'+relativesNum+'"/>';
	htm+='<input id="employeename_'+relativesNum+'" name="relatives['+relativesNum+'].employeename" class="{required:true} inputselectstyle" value="'+(obj.employeename==null?'':obj.employeename)+'" onclick="chooseinterviewerTree(\'#employeeid_'+relativesNum+'\',\'#employeename_'+relativesNum+'\','+"'"+"'"+',callbackUser);" />';
	htm+='<div class="select-trigger" onclick="chooseinterviewerTree(\'#employeeid_'+relativesNum+'\',\'#employeename_'+relativesNum+'\','+"'"+"'"+',callbackUser);"></div>';
	htm+='</li>';
	htm+='<li>';
	htm+='<span><em class="red">* </em>公司： </span>';
	htm+='<input id="companyname_'+relativesNum+'" name="relatives['+relativesNum+'].companyname" class="{required:true,maxlength:50} inputstyle disabled" readonly="true" value="'+(obj.companyname==null?'':obj.companyname)+'"  />';
	htm+='</li>'; 
	htm+='</ul>';
	htm+='<ul>';
	htm+='<li>';
	htm+='<span><em class="red">* </em>部门： </span>';
	htm+='<input id="deptname_'+relativesNum+'" name="relatives['+relativesNum+'].deptname" class="{required:true,maxlength:50}  inputstyle disabled" readonly="true" value="'+(obj.deptname==null?'':obj.deptname)+'"/>';
	htm+='</li>';
	htm+='<li>';
	htm+='<span><em class="red">* </em>岗位： </span>';
	htm+='<input id="postname_'+relativesNum+'" name="relatives['+relativesNum+'].postname" class="{required:true,maxlength:50}  inputstyle disabled" readonly="true" value="'+(obj.postname==null?'':obj.postname)+'"/>';
	htm+='</li>';
	htm+='</ul>';
	htm+='<div class="formpanel">';
	htm+='<div class="formpanel_line">';
	htm+='<div class="right">';
	htm+='<a class="btn" href="javascript:remove('+relativesNum+')">';
	htm+='<i class="icon icon-check"></i>';
	htm+='<span>删除</span>';
	htm+='</a>'
	htm+='</div>';
	htm+='</div>';
	htm+='</div>';
	htm+='</div>';
	relativesNum++;
	return htm;
}


//是否声明
function checkIsTrue(el){
	var state=$(el).attr("checked");
	if(!state){
		$("#relatives").show();
	}else{
		$("#relatives").hide();
		$("#relativesData").html(null);
	}
}


//加载专家信息
function loadRelatives(data){
	if(data==null)
		return;
	$("#relatives").show();
	var htm="";
	for(var i=0;i<data.length;i++){
		var obj=data[i];
		htm+=renderRelatives(obj);
	}
	$("#relativesData").html(htm);
	
}


//添加
function addRelatives(){
	var htm=renderRelatives({});
	if(relativesNum==1){
		$("#relativesData").html(htm);
	}else
		$("#relativesData").append(htm);
	
}



//删除
function remove(idx){
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	$("#relatives_"+idx).remove();
}





//用户回调
function callbackUser(node){
	var idx=$('input[value='+node.id+']').attr("index");
	$.getJSON("employee/getPositionByUserId.do", {id:node.id}, function(data) {
		if(data!=null){
			$('#companyname_'+idx).val(data.companyname);
			$('#deptname_'+idx).val(data.deptname);
			$('#postname_'+idx).val(data.postname);
		}
	});
}



</script>


</head>

<body>

<div class="table">
	<div class="table-bar">
		<div class="table-title">
			内部推荐
		</div>
		<div class="table-ctrl">
			<!--  <a class="btn" href="javascript:chooseRecruiter();" style="display:none"><i class="icon icon-indent-left"></i><span>选择招聘专员</span></a>-->
			<a class="btn save" href="javascript:save();" id='save'><i class="icon icon-check"></i><span>保存</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
	
	    <form action="" method="post" id="myForm_user" class="form">
			<fieldset>
				<legend>推荐人信息</legend>
				<ul>
					<li>
						<span>公司：</span>
						<input id="tcompanyname" name="tcompanyname" class="{maxlength:25} inputstyle disabled" value="${companyname }"/>
					</li>
					<li>
						<span>部门：</span>
						<input id="tdeptname" name="tdeptname" class="{maxlength:25} inputstyle disabled" value="${deptname }"/>
					</li>
				</ul>
				<ul>
					<li>
					  	<span>岗位：</span>
						<input id="tpostname" name="tpostname" class="{maxlength:25} inputstyle disabled" value="${postname }"/>
					</li>
					<li>
					   <span>推荐人姓名：</span>
					   <input id="tname" name="tname" class="{maxlength:25} inputstyle disabled" value="${username }"/>
					</li>
				</ul>
			</fieldset>
	    </form>
	    
		<form action="resume/saveOrUpdateRecommendResume.do" method="post" id="myForm" class="form">
			<input type="hidden" id="recommenduserguid" name="recommenduserguid" value="${userid}"/>
			<input type="hidden" id="webuserguid" name="webuserguid" value=""/>
			<input type="hidden" id="mark" name="mark" value=""/>
			<input id="keyword" name="keyword" type="hidden" value=""/>
			<input id="photo" name="photo" type="hidden" value=""/>
			<input id="modtime" name="modtime" type="hidden" value=""/>
			<input id="createtime" name="createtime" type="hidden" value=""/>
			<input type="hidden" id="recruitpostguid" name="recruitpostguid" value=""/>
			<input id="resumefilename" name="resumefilename" type="hidden" />
			<input id="resumefilePath" name="resumefilePath" type="hidden" />
			
			
			
			 <fieldset>
				<legend>岗位信息</legend>
				<!-- <ul>
	            	<li>
		                <span>招聘专员：</span>
		                <input id="userid" name="userid" type="hidden" value=""/>
					    <input id="username" name="username" class=" inputselectstyle" onclick="chooseRecruiterTree('#userid','#username',callbackRecruiter);"/>
					    <div class="search-trigger" onclick="chooseRecruiterTree('#userid','#username',callbackRecruiter);" />
		            </li>
		            
	            	<li>
					    <span>推荐职务：</span>
						<input id="postname" name="postname" class=" inputselectstyle" onclick="choosePostNameTree('#postname',$('#userid').val(),callbackChooseRecruiter);"/>
					    <div class="search-trigger" onclick="choosePostNameTree('#postname',$('#userid').val(),callbackChooseRecruiter);" />
					</li>
	            </ul>-->
	            <ul>
					<li>
		                <span><em class="red">* </em>公司名称：</span>
					    <input id="r_companyname" name="companyname" class="{required:true} inputstyle disabled" readonly="true" />
		            </li>
					<li>
					    <span><em class="red">* </em>部门名称：</span>
					    <input id="r_deptname" name="deptname" class="{required:true} inputstyle disabled" readonly="true"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span><em class="red">* </em>岗位名称：</span>
					    <input id="r_postname" name="postname" class="{required:true} inputstyle disabled" readonly="true"/>
					</li>
				</ul>
			</fieldset>
			
			
			<fieldset>
				<legend>人才简况</legend>		
				<ul>
				    <li>
		           		<span><em class="red">* </em>人才姓名：</span>
					    <input id="name" name="name" class="{required:true,maxlength:25} inputstyle" onblur="inputName();"/>
                  	</li>
					<li>
					    <span><em class="red">* </em>人才邮箱：</span>
					    <input id="email" name="email" class="{required:true,maxlength:25,email:true} inputselectstyle" onclick="chooseemailTree('#email');" />
					    <div class="search-trigger" onclick="chooseemailTree('#email');" />
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
					    <span><em class="red">* </em>出生日期：</span>
					    <input id="birthday" name="birthday" class="{required:true,dateISO:true} inputstyle inputselectstyle datepicker"/>
					    <div class="date-trigger" onclick="$('#birthday').focus();"/>
					</li>
		       </ul>
	           <ul>
					<li>
					    <span><em class="red">* </em>学历情况：</span>
					    <input id="culture" name="culture" type="hidden" value=""/>
					    <input id="culturename" name="culturename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#culture','#culturename','CULTURE');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#culture','#culturename','CULTURE');"/>
					</li>
					<li>
					    <span><em class="red">* </em>手机：</span>
					    <input id="mobile" name="mobile" class="{required:true,maxlength:25} inputstyle"/>
					</li>
	            </ul>
	            <ul>
	            	<li>
					    <span><em class="red">* </em>工作年限：</span>
					    <input id="workage" name="workage" type="hidden" value=""/>
					    <input id="workagename" name="workagename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#workage','#workagename','WORKAGE');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#workage','#workagename','WORKAGE');"/>
					</li>
	            </ul>
	            
	            <ul>
	            	<li>
					    <span><em class="red">* </em>家庭地址：</span>
					    <input id="homeplace" name="homeplace" class="{maxlength:50,required:true} inputstyle" style="width:570px;"/>
					</li>
	            </ul>
				<ul>
		            <li>
		                <span>附件：</span>
		                <input id="filepath" name="filepath" style="width:570px;" size="65" class="inputstyle" />
	                </li>
				<ul>
				<ul>
		         	<li>
		                <span>备注：</span>
		                <textarea id="rmk" name="rmk" rows="5" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
		            </li>
		        </ul>
		        
				<ul>
					<li>
						<span style="color:red;">推荐人声明：</span>
    					<label>
    						<input name="mychecked1" type="checkbox"  class="checkboxstyle" checked />本人申明，推荐的人才与本人无任何亲属关系，如有隐瞒公司可保留解除劳动合同之权利。
    					</label>
					</li>
				</ul>
				<ul>
					<li>
						<span style="color:red;">人才声明：</span>
    					<label>
    						<input name="mychecked2" type="checkbox"  class="checkboxstyle" checked  onclick='checkIsTrue(this)'/>本人申明，本人在华数公司无任何亲属关系，如有隐瞒公司可保留解除劳动合同之权利。
    					</label>
					</li>
				</ul>
				</fieldset>
				
				<div style="height:700px;">
					<div class="form" id="relatives" style="display:none;" >
						<fieldset>
							<legend>人才公司亲属</legend>
							<div id="relativesData">
							
							</div>
							<div class="formpanel">
								<div class="formpanel_line">
									<div class="right">
										<a class="btn" href="javascript:addRelatives();"><i class="icon icon-plus"></i><span>添加</span></a>
									</div>
								</div>
							</div>
							
						</fieldset>
					</div>
				</div>
				
				
      		</form>
      		
			
		
		
		</div>	
		
</div>



</body>
</html>
