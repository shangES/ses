<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>待入职人才管理</title>
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
<script type="text/javascript" src="plugin/timepicker/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="pages/person/person.js"></script>

<script type="text/javascript" src="pages/tree.js"></script>
<script type="text/javascript">
var tid='${param.id}';
var pageState=false;

$(document).ready(function() {
	
	//加载表单验证
    $("#myForm").validate({debug:true,submitHandler: function(form) {
			$(form).ajaxSubmit(function(data) {
				if(data!=null){
					tid=data.mycandidatesguid;
					pageState=true;
					//重新加载
					//loadData(data.resumeguid,true);
					
					alert("操作成功");
					
					back();
					
					//简历不可修改
					formDisabled(true,'#myForm');
				}
				
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
		$.getJSON("person/getPersonById.do", {id:tid}, function(data) {
			if(data!=null){
				for(var key in data){
					var el = $('#myForm #' + key);
					if(key=='payment'){
			        	$('#' + key+data[key]).attr("checked",true);
			        }else{
			        	 if(el)el.val(data[key]);
			        }
					
				}
				
				//加载主题信息
				if(state){
					//家属状况
					loadTmpFamily();
					//推荐信息
					loadTmpRecommend();
					//公司亲属表
					loadTmpRelatives();
					//项目经历
					loadTmpProExperience();
					//工作经历
					loadTmpWorkExperience();
					//教育经历
					loadTmpEduExperience();
					//培训经历
					loadTmpTrainExperience();
				}
				
				//照片
				if(data.photo!=null){
					$("#photo_button").attr("src", data.photo);
					$("#clearPhoto").show();
				}
				
				//简历不可修改
				//formDisabled(true,'#myForm');
			}
		});
	}
}


//判断工号是否存在
function checkJobnumber(){
	var employeeid=$("#employeeid").val();
	var jobnumber=$("#jobnumber").val();
	if (jobnumber!=null&jobnumber!=""){
		$.getJSON("employee/checkEmployeeByJobnumber.do",{employeeid:employeeid,jobnumber:jobnumber}, function(data) {
			if(data!=null&&data!=''){
				alert(data);
				$("#jobnumber").val(null);
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


//返回
function back(){
	window.parent.convertView(pageState?null:'');
}




//入职
function save(){
	//校验工号
	var employeeid=$("#employeeid").val();
	var jobnumber=$("#jobnumber").val();
	if (jobnumber!=null&jobnumber!=""){
		$.getJSON("employee/checkEmployeeByJobnumber.do",{employeeid:employeeid,jobnumber:jobnumber}, function(data) {
			if(data!=null&&data!=''){
				alert(data);
				$("#jobnumber").val(null);
				return;
			}
			$('#myForm').submit();
		});
	}else{
		$('#myForm').submit();
	}
}


//编制超编与否
function callBackQuota(){
	var quotaid=$("#quotaid").val();
	$.getJSON("quota/getQuotaById.do", {id:quotaid}, function(data) {
		if(data!=null){
	       if(data.vacancynumber<=0){
	    	   alert("该编制下已经超编,请填写备注信息!");
	       }
		}
	});
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
    	data: {table:'j_person_photo'},
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
<div class="table">
	<div class="table-bar">
		<div class="table-title">
			人员基本信息
		</div>
		<div class="table-ctrl">
			<a class="btn" href="javascript:save();" id="ruzhi"><i class="icon icon-plus"></i><span>确定入职</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<form action="person/saveOrUpdatePerson.do" id="myForm"  class="form" method="post">
			<fieldset>
					<legend>岗位信息</legend>
					<!--<div class="formpanel">
						<div class="formpanel_line">
							<div class="right">
							    <a class="btn" href="javascript:editPerson();"><i class="icon icon-pencil"></i><span>修改</span></a>
								<a class="btn" href="javascript:savePerson();"><i class="icon icon-check"></i><span>保存</span></a>
							</div>
						</div>
					</div>-->
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
						    <span><em class="red">* </em>岗位名称：</span>
						    <input id="postid" name="postid" type="hidden" value=""/>
						    <input id="postname" name="postname" class="{required:true} inputselectstyle" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
						    <div class="select-trigger" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
						</li>
						<li>
						    <span><em class="red">* </em>编制类别：</span>
						    <input id="quotaid" name="quotaid" type="hidden" value=""/>
						    <input id="quotaname" name="quotaname" class="{required:true} inputselectstyle" onclick="chooseQuotaTree('#quotaid','#quotaname',$('#postid').val(),callBackQuota);"/>
						    <div class="select-trigger" onclick="chooseQuotaTree('#quotaid','#quotaname',$('#postid').val(),callBackQuota);"/>
						</li>
					</ul>
					<ul>
						<li>
						    <span><em class="red">* </em>员工职级：</span>
						     <input id="rankid" name="rankid" type="hidden" value=""/>
						    <input id="rankname" name="rankname" class="{required:true} inputselectstyle" onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());"/>
						    <div class="select-trigger" onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());" />
						</li>
					</ul>
			</fieldset>
		
			<fieldset>
				<legend>人员报到信息</legend>
				<ul>
					<li>
					    <span><em class="red">* </em>报到人：</span>
					    <input id="regisuserguid" name="regisuserguid" type="hidden" value=""/>
						<input id="regisusername" name="regisusername" class="{required:true} inputselectstyle" onclick="chooseinterviewerTree('#regisuserguid','#regisusername','');"/>
						<div class="select-trigger" onclick="chooseinterviewerTree('#regisuserguid','#regisusername','');"/>
					</li>
					<li>
					    <span><em class="red">* </em>预计报到日期：</span>
					    <input id="joindate" name="joindate" class="{required:true} inputselectstyle timepicker"/>
					    <div class="date-trigger" onclick="$('#joindate').focus();"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span><em class="red">* </em>报到地点：</span>
					    <input id="regisaddress" name="regisaddress" class="{required:true,maxlength:100} inputstyle"/>
					</li>
					<li>
					    <span><em class="red">* </em>办公地点：</span>
					    <input id="officeaddress" name="officeaddress" class="{required:true,maxlength:100} inputstyle"/>
					</li>
				</ul>
			</fieldset>
				
			<fieldset>
				<legend>任职信息</legend>
				<ul>
					<li>
					    <span><em class="red">* </em>工号：</span>
					    <input id="jobnumber" name="jobnumber" class="{required:true,maxlength:20} inputstyle" onblur="checkJobnumber();"/>
					</li>
					<li>
						<span><em class="red">* </em>公司邮箱：</span>
						<input id="email" name="email" class="{required:true,maxlength:50} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span><em class="red">* </em>用工性质：</span>
					    <input id="employtype" name="employtype" type="hidden" value=""/>
					    <input id="employtypename" name="employtypename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#employtype','#employtypename','EMPLOYTYPE');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#employtype','#employtypename','EMPLOYTYPE');"/>
					</li>
				</ul>
			</fieldset>
		
			<fieldset>
				<legend>人员信息</legend>
				<input type="hidden" id="mycandidatesguid" name="mycandidatesguid" value=""/>
				<input id="thirdpartnerguid" name="thirdpartnerguid" type="hidden" value=""/>
				<input id="photo" name="photo" type="hidden" value=""/>
				<input id="modimemo" name="modimemo" type="hidden" value=""/>
				<input id="modiuser" name="modiuser" type="hidden" value=""/>
				<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
				
				
				<table border="0">
					<tr>
						<td>
							<ul>
								<li>
								    <span>照片：</span>
								    <div style="float:left;border:1px solid #b5b8c8;text-align:center;">
								    	<div style="width:228px;padding-top:15px;">
											<img src="skins/images/nopic.jpg" id="photo_button" style="max-width:200px;height:125px;"/>
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
								    <span><em class="red">* </em>身份证号码：</span>
								    <input id="cardnumber" name="cardnumber" class="{required:true,maxlength:25} inputstyle"/>
								</li>
							</ul>
							<ul>
								<li>
									<span><em class="red">* </em>出生日期：</span>
									<input id="birthday" name="birthday" class="{required:true,dateISO:true} inputstyle inputselectstyle datepicker"/>
									<div class="date-trigger" onclick="$('#birthday').focus();"/>
								</li>
							</ul>
						</td>
					</tr>
				</table>
				<ul>
					<li>
					    <span><em class="red">* </em>学历情况：</span>
					    <input id="culture" name="culture" type="hidden" value=""/>
					    <input id="culturename" name="culturename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#culture','#culturename','CULTURE');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#culture','#culturename','CULTURE');"/>
					</li>
					<li>
					    <span>政治面貌：</span>
					    <input id="politics" name="politics" type="hidden" value=""/>
					    <input id="politicsname" name="politicsname" class="inputselectstyle" onclick="chooseOptionTree('#politics','#politicsname','POLITICS');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#politics','#politicsname','POLITICS');"/>
					</li>
				</ul>
				<ul>
					<li>
						<span>民族：</span>
						<input id="nation" name="nation" type="hidden" value=""/>
					    <input id="nationname" name="nationname" class="inputselectstyle" onclick="chooseOptionTree('#nation','#nationname','NATION');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#nation','#natonname','NATION');"/>
					</li>
					<li>
					    <span><em class="red">* </em>手机：</span>
					    <input id="mobile" name="mobile" class="{required:true,maxlength:25} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
						<span><em class="red">* </em>通讯录手机：</span>
						<input id="addressmobile" name="addressmobile" class="{required:true,maxlength:25} inputstyle"/>
					</li>
					<li>
					    <span><em class="red">* </em>身份证地址：</span>
					    <input id="residenceplace" name="residenceplace" class="{required:true,maxlength:25} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
						<span>家庭电话：</span>
						<input id="homephone" name="homephone" class="{maxlength:25} inputstyle"/>
					</li>
					<li>
					    <span><em class="red">* </em>家庭住址：</span>
					    <input id="homeplace" name="homeplace" class="{required:true,maxlength:25} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>身高：</span>
					    <input id="height" name="height" class="{maxlength:50} inputstyle"/>
					</li>
					<li>
						<span>血型：</span>
						<input id="bloodtype" name="bloodtype" type="hidden" value=""/>
					    <input id="bloodtypename" name="bloodtypename" class="inputselectstyle" onclick="chooseOptionTree('#bloodtype','#bloodtypename','BLOODTYPE');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#bloodtype','#bloodtypename','BLOODTYPE');"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span><em class="red">* </em>户口性质：</span>
					    <input id="domicilplace" name="domicilplace" type="hidden" value=""/>
					    <input id="domicilplacename" name="domicilplacename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#domicilplace','#domicilplacename','DOMICILPLACE');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#domicilplace','#domicilplacename','DOMICILPLACE');"/>
					</li>
					<li>
					    <span>婚姻状况：</span>
					    <input id="married" name="married" type="hidden" value=""/>
					    <input id="marriedname" name="marriedname" class="inputselectstyle" onclick="chooseOptionTree('#married','#marriedname','MARRIED');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#married','#marriedname','MARRIED');"/>
					</li>
				
				</ul>
				<ul>
					<li>
						<span>籍贯：</span>
						<input id="nativeplace" name="nativeplace" class="{maxlength:25} inputstyle"/>
					</li>
					<li>
						<span><em class="red">* </em>私人邮箱：</span>
						<input id="privateemail" name="privateemail" class="{required:true,maxlength:25} inputstyle"/>
					</li>
				  
				</ul>
				<ul>
					<li>
						<span><em class="red">* </em>紧急联系人：</span>
						<input id="contactname" name="contactname" class="{required:true,maxlength:25} inputstyle"/>
					</li>
					<li>
						<span>紧急联系人电话：</span>
						<input id="contactphone" name="contactphone" class="{maxlength:25} inputstyle"/>
					</li>
					
				</ul>
				<ul>
					<li>
					    <span>与本人关系：</span>
					    <input id="contactrelationship" name="contactrelationship" type="hidden" value=""/>
					    <input id="contactRelationshipname" name="contactRelationshipname" class="inputselectstyle" onclick="chooseOptionTree('#contactrelationship','#contactRelationshipname','RELATIONSHIP');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#contactrelationship','#contactRelationshipname','RELATIONSHIP');"/>
					</li>
					<li>
						<span>参加工作时间：</span>
						<input id="joinworkdate" name="joinworkdate" class="{dateISO:true} inputstyle inputselectstyle datepicker"/>
						<div class="date-trigger" onclick="$('#joinworkdate').focus();"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span><em class="red">* </em>法律文书送达地址：</span>
					    <input id="legaladdress" name="legaladdress" class="{required:true,maxlength:100} inputstyle" style="width:570px;"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>社保归属地：</span>
					    <input id="social" name="social" class="{maxlength:50} inputstyle" style="width:570px;"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>社保缴纳起始时间：</span>
					    <input id="societydate" name="societydate" class="{dateISO:true} inputselectstyle datepicker"/>
					    <div class="date-trigger" onclick="$('#societydate').focus();"/>
					</li>
	            	<li>
                        <span>缴纳有无中断：</span>
                        <label>
                        	<input type="radio" id="payment0" name="payment" value="0" class="checkboxstyle" />是
                        </label>
                        <label>
                        	<input type="radio" id="payment1" name="payment" value="1" class="checkboxstyle" checked/>否
                        </label>
                    </li>
	            </ul>
	            <ul>
	            	<li>
					    <span>中断开始时间：</span>
					    <input id="starttime" name="starttime" class="{dateISO:true} inputselectstyle datepicker"/>
					    <div class="date-trigger" onclick="$('#starttime').focus();"/>
					</li>
					<li>
					    <span>中断结束时间：</span>
					    <input id="endtime" name="endtime" class="{dateISO:true} inputselectstyle datepicker"/>
					    <div class="date-trigger" onclick="$('#endtime').focus();"/>
					</li>
	            </ul>
				<ul>
					<li>
					    <span>其他技能：</span>
					    <textarea id="skills" name="skills"  rows="5" style="width:570px;" class="{maxlength:100} areastyle"></textarea>
					</li>
				</ul>
				<ul>
					<li>
					    <span>兴趣爱好：</span>
					    <textarea id="hobbies" name="hobbies"  rows="5" style="width:570px;" class="{maxlength:100} areastyle"></textarea>
					</li>
				</ul>
				<ul>
				    <li>
		                <span>备注：</span>
		                <textarea id="memo" name="memo" rows="5" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
		            </li>
				</ul>
		        
		        <!--  
		        <div class="formpanel">
					<div class="formpanel_line">
						<div class="right">
						    <a class="btn" href="javascript:editPerson();"><i class="icon icon-pencil"></i><span>修改</span></a>
							<a class="btn" href="javascript:savePerson();"><i class="icon icon-check"></i><span>保存</span></a>
						</div>
					</div>
				</div>-->
		        
			</fieldset>
			
			
		</form>
			
		<div class="form">
			<fieldset>
				<legend>家庭状况</legend>
				<div id="myFamilys">
					<img src="skins/images/ajax-loader-small.gif">
				</div>
				<div class="formpanel">
					<div class="formpanel_line">
						<div class="right">
							<a class="btn" href="javascript:addTmpFamily();"><i class="icon icon-plus"></i><span>添加</span></a>
						</div>
					</div>
				</div>
			</fieldset>
		</div>
							
		<div class="form">
			<fieldset>
				<legend>入职推荐</legend>
				<div id="myRecommends">
					<img src="skins/images/ajax-loader-small.gif">
				</div>
				<div class="formpanel">
					<div class="formpanel_line">
						<div class="right">
							<a class="btn" href="javascript:addTmpRecommend();"><i class="icon icon-plus"></i><span>添加</span></a>
						</div>
					</div>
				</div>
			</fieldset>
		</div>
							
							
							
		<div class="form">
			<fieldset>
				<legend>公司亲属</legend>
				<div id="myRelatives">
					<img src="skins/images/ajax-loader-small.gif">
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
			
			
		<div class="form">
			<fieldset>
				<legend>工作经历</legend>
				<div id="myWorkExperiences">
					<img src="skins/images/ajax-loader-small.gif">
				</div>
				<div class="formpanel">
					<div class="formpanel_line">
						<div class="right">
							<a class="btn" href="javascript:addTmpWorkExperience();"><i class="icon icon-plus"></i><span>添加</span></a>
						</div>
					</div>
				</div>
				
			</fieldset>
		</div>
					
		<div class="form">
			<fieldset>
				<legend>教育经历</legend>
				<div id="myEducationExperiences">
				<img src="skins/images/ajax-loader-small.gif">
				</div>
				
				<div class="formpanel">
					<div class="formpanel_line">
						<div class="right">
							<a class="btn" href="javascript:addTmpEduExperience();"><i class="icon icon-plus"></i><span>添加</span></a>
						</div>
					</div>
				</div>
			</fieldset>
		</div>
		
		<div class="form">
			<fieldset>
				<legend>项目经历</legend>
				<div id="myPros">
					<img src="skins/images/ajax-loader-small.gif">
				</div>
				<div class="formpanel">
					<div class="formpanel_line">
						<div class="right">
							<a class="btn" href="javascript:addProExperience();"><i class="icon icon-plus"></i><span>添加</span></a>
						</div>
					</div>
				</div>
			</fieldset>
		</div>
		
		<div class="form">
			<fieldset>
				<legend>培训经历</legend>
				<div id="myTrains">
				<img src="skins/images/ajax-loader-small.gif">
				</div>
				<div class="formpanel">
					<div class="formpanel_line">
						<div class="right">
							<a class="btn" href="javascript:addTrainExperience();"><i class="icon icon-plus"></i><span>添加</span></a>
						</div>
					</div>
				</div>
			</fieldset>
		</div>
				
	</div>
</div>



</body>
</html>
