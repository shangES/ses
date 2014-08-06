<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>简历信息</title>
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


<script type="text/javascript" src="pages/resume/theme.js"></script>

<script type="text/javascript">
var tid='${param.id}';
var recruitpostguid='${param.recruitpostguid}';


var pageState=false;

$(document).ready(function() {
	
	//加载表单验证
    $("#myResume").validate({debug:true,submitHandler: function(form) {
			$(form).ajaxSubmit(function(data) {
				tid=data.webuserguid;
				pageState=true;
				
				//重新加载
				loadData(data.webuserguid,true);
				
				alert("操作成功!");
				
			});
		}
	});
	
	//加载
	loadData(tid,true);
	
    
	//日期选择,主题js
	initDatePicker();
	
	//照片控件
	initPhoto();
});








//加载数据
function loadData(tid,state) {
	if(tid!=null&&tid!=''&&tid!='null'){
		$.getJSON("resume/getResumeById.do", {id:tid}, function(data) {
			if(data!=null){
				for(var key in data){
					if($('#myResume #'+key)){
						$('#myResume #'+key).val(data[key]);
					}
				}
				
				//加载主题信息
				if(state){
					//工作经历
					loadWorkExperience();
					//项目经历
					loadProjectExperience();
					//教育经历
					loadEducationExperience();
					//培训经历
					loadTrainingExperience();
					//附件上传
					loadResumeFile();
				}
				
				//照片
				if(data.photo!=null){
					$("#photo_button").attr("src", data.photo);
					$("#clearPhoto").show();
				}
			}
		});
	}
}



//修改
function editResume(){
	formDisabled(false,'#myResume');
}


//保存
function saveResume(){
	$('#myResume').submit();
}







//竞聘
function competition(){
	var webuserguid=$("#webuserguid").val();
	$.post("recruitment/competitionRecruitPostById.do",{id:webuserguid,recruitpostguid:recruitpostguid}, function() {
		pageState=true;
		alert("操作成功!");
		back();
    });
}








//返回
function back(){
	window.parent.convertView(pageState?null:'');
}

</script>

<script type="text/javascript">
//上传照片
function initPhoto(){
    new AjaxUpload('#photo_button', {
    	id:'upFile',
    	action: 'uploadFile.do',
    	name:'file1',
    	autoSubmit:true,
    	responseType: 'json',
    	data: {table:'j_resume_photo'},
    	onSubmit: function(file, ext){
    		$("#photo_button").attr("src","skins/images/ajax-loader-small.gif");
    		if (ext && /^(jpg|png|jpeg|gif)$/i.test(ext)) {  
    			
               } else {
            	   $("#photo_button").attr("src","skins/images/nopic.jpg");
                   alert('只能上传jpg|png|jpeg|gif图片!');
                   return false;
               }
    	},  
    	onComplete: function(filename,obj) {
    		$("#photo_button").attr("src",obj.filepath);
    		$("#photo").val(obj.filepath);
    	}
   	});
}

//清空所处的照片
function clearPhoto(){
	$("#photo_button").attr("src","skins/images/nopic.jpg");
	$("#photo").val("");
}

</script>



</head>

<body>


<div class="sort">
	<div class="sort-title">
		<h3>简历信息管理</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>
	<div class="table">
		<div class="table-bar">
			<div class="table-title">
				简历基本信息
			</div>
			<div class="table-ctrl">
				<a class="btn" href="javascript:competition();"><i class="icon icon-bookmark"></i><span>竞聘</span></a>
				<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
			</div>
		</div>
		<div class="table-wrapper">
			<form action="resume/saveOrUpdateResume.do" id="myResume"  class="form" method="post">
				<fieldset>
					<legend>简历信息</legend>
					<input type="hidden" id="recommenduserguid" name="recommenduserguid" value=""/>
					<input id="createtime" name="createtime" type="hidden" value=""/>
					<input id="modtime" name="modtime" type="hidden" value=""/>
					<input id="webuserguid" name="webuserguid" type="hidden" value=""/>
					<input id="keyword" name="keyword" type="hidden" value=""/>
					<input id="photo" name="photo" type="hidden" value=""/>
					<input id="resumetype" name="resumetype" type="hidden" value="1"/>
					<input id="mark" name="mark" type="hidden" value="1"/>
				
					<table border="0">
						<tr>
							<td>
								<ul>
									<li>
									    <span>照片：</span>
									    <div style="float:left;border:1px solid #b5b8c8;text-align:center;">
									    	<div style="width:228px;padding-top:15px;">
												<img src="skins/images/nopic.jpg" id="photo_button" style="max-width:208px;height:155px;"/>
											</div>
											<a href="javascript:clearPhoto();" style="float:right;">X&nbsp;</a>
										</div>
									</li>
								</ul>
							</td>
							<td style="width:358px;">
								<ul>
									<li>
									    <span><em class="red">* </em>姓名：</span>
									    <input id="name" name="name" class="{required:true,maxlength:25} inputstyle"/>
									</li>
								</ul>
								<ul>
									<li>
									    <span><em class="red">* </em>性别：</span>
									    <input id="sex" name="sex" type="hidden" value=""/>
									    <input id="sexname" name="sexname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#sex','#sexname','SEX');"/>
									    <div class="select-trigger" onclick="chooseOptionTree('#sex','#sexname','SEX');"/>
									</li>
								</ul>
								<ul>
									<li>
									    <span><em class="red">* </em>出生日期：</span>
									    <input id="birthday" name="birthday" class="{required:true,dateISO:true} inputstyle inputselectstyle datepicker"/>
									    <div class="date-trigger" onclick="$('#birthday').focus();"/>
									</li>
								</ul>
								<ul>
									 <li>
									    <span><em class="red">* </em>手机：</span>
									    <input id="mobile" name="mobile" class="{required:true,maxlength:25} inputstyle"/>
									</li>
								</ul>
							</td>
						</tr>
					</table>
					<ul>
						<li>
						    <span><em class="red">* </em>工作年限：</span>
						    <input id="workage" name="workage" type="hidden" value=""/>
						    <input id="workagename" name="workagename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#workage','#workagename','WORKAGE');"/>
						    <div class="select-trigger" onclick="chooseOptionTree('#workage','#workagename','WORKAGE');"/>
						</li>
						<li>
							<span><em class="red">* </em>最高学历：</span>
							<input id="culture" name="culture" type="hidden" value=""/>
						    <input id="culturename" name="culturename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#culture','#culturename','CULTURE');"/>
						    <div class="select-trigger" onclick="chooseOptionTree('#culture','#culturename','CULTURE');"/>
						</li>
					</ul>
					<ul>
						<li>
						    <span><em class="red">* </em>电子邮箱：</span>
						    <input id="email" name="email" class="{required:true,maxlength:25,email:true} inputstyle"/>
						</li>
						<li>
						    <span><em class="red">* </em>现居住地：</span>
						    <input id="homeplace" name="homeplace" class="{maxlength:50,required:true} inputstyle"/>
						</li>
					</ul>
					<ul>
						<li>
						    <span>期望从事职业：</span>
						    <input id="occupation" name="occupation" class="{maxlength:100} inputstyle"/>
						</li>
						<li>
						    <span>期望月薪：</span>
						    <input id="salary" name="salary" class="{maxlength:30} inputstyle"/>
						</li>
					</ul>
					<ul>
			         	<li>
			                <span>自我评价：</span>
			                <textarea id="valuation" name="valuation" rows="2" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
			            </li>
			        </ul>
					<ul>
			         	<li>
			                <span>目前状况：</span>
			                <textarea id="situation" name="situation" rows="2" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
			            </li>
			        </ul>
			        <ul>
			         	<li>
			                <span>行业：</span>
			                <textarea id="industry" name="industry" rows="2" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
			            </li>
			        </ul>
					<ul>
			         	<li>
			                <span>备注：</span>
			                <textarea id="rmk" name="rmk" rows="5" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
			            </li>
			        </ul>
			        
			        <div class="formpanel">
						<div class="formpanel_line">
							<div class="right">
								<!--  <a class="btn" href="javascript:editResume();"><i class="icon icon-pencil"></i><span>修改</span></a>-->
								<a class="btn" href="javascript:saveResume();"><i class="icon icon-check"></i><span>保存</span></a>
							</div>
						</div>
					</div>
			        
			         
				</fieldset>
			</form>
				
				
			<div class="form">
				<fieldset>
					<legend>工作经历</legend>
					<div id="myWorkExperiences">
					</div>
					<div class="formpanel">
						<div class="formpanel_line">
							<div class="right">
								<a class="btn" href="javascript:addWorkExperience();"><i class="icon icon-plus"></i><span>添加</span></a>
							</div>
						</div>
					</div>
					
				</fieldset>
			</div>
			
			
			
			
			
			<div class="form">
				<fieldset>
					<legend>项目经历</legend>
					<div id="myProjectExperiences">
					</div>
					<div class="formpanel">
						<div class="formpanel_line">
							<div class="right">
								<a class="btn" href="javascript:addProjectExperience();"><i class="icon icon-plus"></i><span>添加</span></a>
							</div>
						</div>
					</div>
					
				</fieldset>
			</div>
			
			<div class="form">
				<fieldset>
					<legend>教育经历</legend>
					<div id="myEducationExperiences">
					</div>
					
					<div class="formpanel">
						<div class="formpanel_line">
							<div class="right">
								<a class="btn" href="javascript:addEducationExperience();"><i class="icon icon-plus"></i><span>添加</span></a>
							</div>
						</div>
					</div>
				</fieldset>
			</div>
			
			<div class="form">
				<fieldset>
					<legend>培训经历</legend>
					<div id="myTrainingExperiences">
					</div>
					<div class="formpanel">
						<div class="formpanel_line">
							<div class="right">
								<a class="btn" href="javascript:addTrainingExperience();"><i class="icon icon-plus"></i><span>添加</span></a>
							</div>
						</div>
					</div>
				</fieldset>
			</div>
			
			<div class="form">
				<fieldset>
					<legend>附件上传</legend>
					<div id="myResumeFiles">
					</div>
					<div class="formpanel">
						<div class="formpanel_line">
							<div class="right">
								<a class="btn" href="javascript:addResumeFile();"><i class="icon icon-plus"></i><span>添加</span></a>
							</div>
						</div>
					</div>
				</fieldset>
			</div>
			
		</div>
</div>


</body>
</html>
