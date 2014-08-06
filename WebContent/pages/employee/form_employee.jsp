<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>員工管理</title>
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
<script type="text/javascript" src="skins/js/jquery.ajaxupload.3.6.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>

<script type="text/javascript" src="pages/tree.js"></script>

<script type="text/javascript">
var tid='${param.id}';
var postionguid='${param.postionguid}';

$(document).ready(function() {
    //加载表单验证
    $("#myForm").validate();
    $('#myForm').ajaxForm(function(data) {
    	alert("保存成功！");
    	tid=data.employeeid;
    	loadData();
    	
    	//页面保存过
    	window.parent.callbackPageState(true);
    });
    
    
    
  	////加載數據
    loadData();
    
    
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
function loadData() {
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("employee/getEmployeeById.do", {id:tid,postionguid:postionguid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#myForm #' + key);
			        
			        //员工任职
			        if(key=='position'){
			        	loadPositionData(data[key]);
			        }else if(key=='payment'){
			        	$('#' + key+data[key]).attr("checked",true);
			        }else{
			        	 if(el)el.val(data[key]);
			        }
			    }
				
				//photo
				if(data.photo!=null&&data.photo!=''){
					$("#photo_button").attr("src", data.photo);
				}
				
				//按钮状态
				butState();
				
				
				
				//计算高度
				_autoHeight();
				
			}
		});
	else{
		//员工入职
		$("#myResignation").hide();
		$("#myOfficial").hide();
    	$(".lz").hide();
    	$(".hf").hide();
    	$(".zz").hide();
    	$(".save").show();
    	
    	
    	//照片上传
		initPhoto();
    	
    	
		//计算高度
		_autoHeight();
	}
}





//员工任职
function loadPositionData(data){
	if(data!=null){
		for (var key in data) {
	        var el = $('#position #' + key);
	       	if(el)el.val(data[key]);
	    }
	}
}


//按钮状态
//状态(1:正常,0:试用,-1:离职)
function butState(){
	//判断是否可以保存
	if(${param.edit!=true}){
		formDisabled(true,'#myForm');
		
		 //回调
		window.parent.callbackEmpolyee(tid,workstate);
		return;
	}
	
	
	var workstate=$("#workstate").val();
	var isstaff=$("#isstaff").val();
	
	//异动员工生效
	if(isstaff==-1){
		$(".active").show();
		formDisabled(true,'#myForm');
    	formDisabled(false,'#position');
    	formDisabled(true,'#myCompany');
		return ;
	}
	
	//兼任员工信息不可修改
	if(isstaff==0){
		//readonly
		$("#myQuota").hide();
    	formDisabled(true,'#myForm');
    	formDisabled(false,'#position');
    	formDisabled(true,'#myCompany');
    	$(".save").show();
		return;
	}
	
	
	//正常员工
	if(workstate==0){
    	$("#myResignation").hide();
    	$("#myOfficial").hide();
    	$(".active").hide();
    	$(".sy").hide();
    	$(".lz").show();
    	$(".hf").hide();
    	$(".zz").show();
    	$(".save").show();
    	
    	//照片上传
		initPhoto();
    }else  if(workstate==-1){
    	$("#myResignation").show();
    	$("#myOfficial").show();
    	$(".active").hide();
    	$(".sy").hide();
    	$(".lz").hide();
    	$(".hf").show();
    	$(".zz").hide();
    	$(".save").hide();
    	
    	
    	//readonly
    	formDisabled(true,'#myForm');
    }else{
    	$("#myResignation").hide();
    	$("#myOfficial").show();
    	$(".active").hide();
    	$(".sy").show();
    	$(".lz").show();
    	$(".hf").hide();
    	$(".zz").hide();
    	$(".save").show();
    	
    	//照片上传
		initPhoto();
    	
    	//readonly
    	formDisabled(false,'#myForm');
    	formDisabled(true,'#myCompany');
    }
	
	 //回调
	window.parent.callbackEmpolyee(tid,workstate);
	
}



//保存
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





//异动员工立即生效
function onActive(){
	$("#state").val(1);
	$("#isstaff").val(1);
	
	//保存
	save();
}





//在职转为试用
function saveSy(){
	if(!confirm('确认要把该员工转为试用期吗？')){
		return;
	}
	
	$("#workstate").val(0);
	//保存
	save();
}


//返回
function back(){
	window.parent.convertView('');
}




//部门选择回调
function callbackDept(){
	$("#jobid").val(null);
	$("#jobname").val(null);
	$("#postid").val(null);
	$("#postname").val(null);
	$("#quotaid").val(null);
	$("#quotaname").val(null);
	$("#rankid").val(null);
	$("#rankname").val(null);
}




//员工树选择回调
function employeeTreeCallback(id){
	$.getJSON("employee/getEmployeeById.do", {id:id}, function(data) {
		if(data!=null){
			for (var key in data) {
		        var el = $('#myForm #' + key);
		        
		        //员工任职
		        if(key=='position'){
		        	loadPositionData(data[key]);
		        }else{
		        	 if(el)el.val(data[key]);
		        }
		    }
			
			//离职再入职
			$("#workstate").val(1);
			$("#resignationdate").val(null);
			$("#resignationreason").val(null);
		}
	});
	
}
</script>



<script type="text/javascript">
//转正
var officialForm=null;
function openOfficial(){
	$("#official").dialog({
		position: ['center',100],
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		buttons: {
			"确定":function(){
				if(officialForm.form()){
					$("#workstate").val(1);
					$("#officialdate").val($("#o_officialdate").val());
					$("#officialmemo").val($("#o_officialmemo").val());
					//保存
					save();
					
					//关闭
					$("#official").dialog("close");
				}
			},
			"重置":function(){
				$("#officialForm").clearForm();
			},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			//校验
			$("#officialForm").clearForm();
			if(officialForm==null){
				officialForm=$("#officialForm").validate();
			}
		}
	});
}






//离职
var resignationForm=null;
function openResignation(){
	$("#resignation").dialog({
		position: ['center',100],
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		buttons: {
			"确定":function(){
				if(resignationForm.form()){
					$("#workstate").val(-1);
					$("#resignationdate").val($("#r_resignationdate").val());
					$("#resignationreason").val($("#r_resignationreason").val());
					//保存
					save();
					//关闭
					$("#resignation").dialog("close");
				}
			},
			"重置":function(){
				$("#resignationForm").clearForm();
			},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			//校验
			$("#resignationForm").clearForm();
			if(resignationForm==null){
				resignationForm=$("#resignationForm").validate();
			}
		}
	});
}



//离职恢复
function revertResignation(){
	$("#workstate").val(1);
	$("#resignationdate").val(null);
	$("#resignationreason").val(null);
	save();
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
    	data: {table:'t_user_photo'},
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




//计算计划转正时间
function setOfficialdateplan(){
	var studymonth=$("#studymonth").val();
	var joindate=$("#joindate").val();
	
	if(joindate!=null&&joindate!=''&&studymonth!=null&&studymonth!=''){
		var tdate = new  Date(Date.parse(joindate.replace(/-/g,"/")));
		tdate.setMonth(tdate.getMonth()+new Number(studymonth));
		$("#officialdateplan").val(tdate.format("yyyy-MM-dd"));
	}
}





//身份证的验证
function checkCardno(){
	var cardno=$("#cardnumber").val();
	if(cardno!=null&&cardno.length==18){
		//生日
		var y=cardno.substring(6,10);
		var m=cardno.substring(10,12);
		var d=cardno.substring(12,14);
		$("#birthday").val(y+"-"+m+"-"+d);
		
		//性别
		var sex=cardno.substring(16,17);
		$("#sexname").val(sex%2==0?"女":"男");
		$("#sex").val(sex%2==0?2:1);
	}
}


//身份证重复
function checkRepeatCardno(){
	var employeeid=$("#employeeid").val();
	var cardnumber=$("#cardnumber").val();
	if (cardnumber!=null&cardnumber!=""){
		$.getJSON("employee/checkEmployeeByCardno.do",{employeeid:employeeid,cardnumber:cardnumber}, function(data) {
			if(data!=null&&data!=''){
				alert(data);
				$("#cardnumber").val(null);
				$("#birthday").val(null);
				$("#sexname").val(null);
				$("#sex").val(null);
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
			员工信息
		</div>
		<div class="table-ctrl">
			<a class="btn active" href="javascript:onActive();" style="display:none;"><i class="icon icon-off"></i><span>立即生效</span></a>
			<a class="btn sy" href="javascript:saveSy();" style="display:none;"><i class="icon icon-retweet"></i><span>转为试用期</span></a>
			<a class="btn lz" href="javascript:openResignation();" style="display:none;"><i class="icon icon-stop"></i><span>离职</span></a>
			<a class="btn hf" href="javascript:revertResignation();" style="display:none;"><i class="icon icon-retweet"></i><span>离职恢复</span></a>
			<a class="btn zz" href="javascript:openOfficial();" style="display:none;"><i class="icon icon-bookmark"></i><span>转正</span></a>
			
			<a class="btn save" href="javascript:save();" style="display:none;"><i class="icon icon-check"></i><span>保存</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<form action="employee/saveOrUpdateEmployee.do" method="post" id="myForm" class="form">
			<input id="employeeid" name="employeeid" type="hidden" value=""/>
			<input id="workstate" name="workstate" type="hidden" value="0"/>
			<input id="photo" name="photo" type="hidden" value=""/>
			<input id="modiuser" name="modiuser" type="hidden" value="${username }"/>
			<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
			
			
			<fieldset id="position">
				<legend>岗位信息</legend>
				<input id="postionguid" name="position.postionguid" type="hidden" value=""/>
				<input id="isstaff" name="position.isstaff" type="hidden" value="1"/>
				<input id="state" name="position.state" type="hidden" value="1"/>
				<input id="enddate" name="position.enddate" type="hidden"/>
				<ul id="myCompany">
					<li>
					    <span><em class="red">* </em>公司名称：</span>
					    <input id="companyid" name="position.companyid" type="hidden" value=""/>
					    <input id="companyname" name="position.companyname" class="{required:true} inputstyle disabled" readonly="true"/>
					</li>
					<li>
		                <span>员工状态：</span>
					    <input id="workstatename" name="workstatename" class="inputstyle disabled" value="试用" readonly="true"  style="width:100px;"/>
					    <input id="isstaffname" name="position.isstaffname" class="inputstyle disabled" readonly="true" value="占编" style="width:107px;"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span><em class="red">* </em>部门名称：</span>
					    <input id="deptid" name="position.deptid" type="hidden" value=""/>
					    <input id="deptname" name="position.deptname" class="{required:true} inputselectstyle" onclick="chooseMyDeptTree('#deptid','#deptname',callbackDept);"/>
					    <div class="select-trigger" onclick="chooseMyDeptTree('#deptid','#deptname',callbackDept);"/>
					</li>
					<li>
					    <span>职务名称：</span>
					    <input id="jobid" name="position.jobid" type="hidden" value=""/>
					    <input id="jobname" name="position.jobname" class="inputselectstyle" onclick="chooseJobTree('#jobid','#jobname',$('#companyid').val());"/>
					    <div class="select-trigger" onclick="chooseJobTree('#jobid','#jobname',$('#companyid').val());"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>岗位名称：</span>
					    <input id="postid" name="position.postid" type="hidden" value=""/>
					    <input id="postname" name="position.postname" class="inputselectstyle" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
					    <div class="select-trigger" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
					</li>
					<li>
					    <span><em class="red">* </em>聘任时间：</span>
					    <input id="startdate" name="position.startdate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
					    <div class="date-trigger" onclick="$('#startdate').focus();"/>
					</li>
				</ul>
				<ul id="myQuota">
					<li>
					    <span>编制类别：</span>
					    <input id="quotaid" name="position.quotaid" type="hidden" value=""/>
					    <input id="quotaname" name="position.quotaname" class="inputselectstyle" onclick="chooseQuotaTree('#quotaid','#quotaname',$('#postid').val());"/>
					    <div class="select-trigger" onclick="chooseQuotaTree('#quotaid','#quotaname',$('#postid').val());"/>
					</li>
					<li>
					    <span>员工职级：</span>
					     <input id="rankid" name="position.rankid" type="hidden" value=""/>
					    <input id="rankname" name="position.rankname" class="inputselectstyle" onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());"/>
					    <div class="select-trigger" onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());" />
					</li>
				</ul>
			</fieldset>
			
			
			<fieldset>
				<legend>基本信息</legend>
				<table border="0">
					<tr>
						<td>
							<ul>
								<li>
								    <span>照片：</span>
								    <div style="float:left;border:1px solid #b5b8c8;text-align:center;vertical-align: middle;">
								    	<div style="width:228px;padding-top:15px;">
											<img src="skins/images/nopic.jpg" id="photo_button" style="height:125px;"/>
										</div>
										<a href="javascript:clearPhoto();" style="float:right;">X&nbsp;</a>
									</div>
								</li>
							</ul>
						</td>
						<td style="width:358px;">
							<ul>
								<li>
								    <span><em class="red">* </em>排序号：</span>
								    <input id="dorder" name="dorder" class="{required:true,number:true,maxlength:19} inputstyle"/>
								</li>
							</ul>
							<ul>
								<li>
								    <span><em class="red">* </em>姓名：</span>
								    <input id="name" name="name" class="{required:true,maxlength:10} inputselectstyle"/>
								    <div class="search-trigger" onclick="chooseEmployeeTree('#name','#name','=-1');" title="离职员工再入职选择姓名,工号!"/>
								</li>
							</ul>
							<ul>
								<li>
								    <span><em class="red">* </em>身份证号码：</span>
								    <input id="cardnumber" name="cardnumber" class="{required:true,maxlength:18} inputstyle" onkeyup="checkCardno();" onblur="checkRepeatCardno();"/>
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
					    <span><em class="red">* </em>性别：</span>
					    <input id="sex" name="sex" type="hidden" value=""/>
					    <input id="sexname" name="sexname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#sex','#sexname','SEX');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#sex','#sexname','SEX');"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>身高：</span>
					    <input id="height" name="height" class="{maxlength:50} inputstyle"/>
					</li>
					<li>
					    <span><em class="red">* </em>户口性质：</span>
					    <input id="domicilplace" name="domicilplace" type="hidden" value=""/>
					    <input id="domicilplacename" name="domicilplacename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#domicilplace','#domicilplacename','DOMICILPLACE');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#domicilplace','#domicilplacename','DOMICILPLACE');"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>政治面貌：</span>
					    <input id="politics" name="politics" type="hidden" value=""/>
					    <input id="politicsname" name="politicsname" class="inputselectstyle" onclick="chooseOptionTree('#politics','#politicsname','POLITICS');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#politics','#politicsname','POLITICS');"/>
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
					    <span>民族：</span>
					    <input id="nation" name="nation" type="hidden" value=""/>
					    <input id="nationname" name="nationname" class="inputselectstyle" onclick="chooseOptionTree('#nation','#nationname','NATION');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#nation','#nationname','NATION');"/>
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
					    <span>家庭电话：</span>
					    <input id="homephone" name="homephone" class="{maxlength:20} inputstyle"/>
					</li>
					<li>
					    <span><em class="red">* </em>家庭住址：</span>
					    <input id="homeplace" name="homeplace" class="{required:true,maxlength:50} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>籍贯：</span>
					    <input id="nativeplace" name="nativeplace" class="{maxlength:20} inputstyle"/>
					</li>
					<li>
					    <span><em class="red">* </em>身份证地址：</span>
					    <input id="residenceplace" name="residenceplace" class="{required:true,maxlength:50} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>参加工作时间：</span>
					    <input id="joinworkdate" name="joinworkdate" class="{dateISO:true} inputselectstyle datepicker"/>
					    <div class="date-trigger" onclick="$('#joinworkdate').focus();"/>
					</li>
					<li>
					    <span><em class="red">* </em>手机：</span>
					    <input id="mobile" name="mobile" class="{required:true,maxlength:20} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>备用手机：</span>
					    <input id="mobile2" name="mobile2" class="{maxlength:20} inputstyle"/>
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
					    <span>私人邮箱：</span>
					    <input id="privateemail" name="privateemail" class="{maxlength:50,email:true} inputstyle"/>
					</li>
					<li>
					    <span>紧急联系人：</span>
					    <input id="contactname" name="contactname" class="{maxlength:20} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>紧急联系人电话：</span>
					    <input id="contactphone" name="contactphone" class="{maxlength:50} inputstyle"/>
					</li>
					<li>
					    <span>与紧急联系人关系：</span>
					    <input id="contactrelationship" name="contactrelationship" type="hidden" value=""/>
					    <input id="contactrelationshipname" name="contactrelationshipname" class="inputselectstyle" onclick="chooseOptionTree('#contactrelationship','#contactrelationshipname','RELATIONSHIP');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#contactrelationship','#contactrelationshipname','RELATIONSHIP');"/>
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
				
			</fieldset>
			
			
			<fieldset>
				<legend>任职信息</legend>
				<ul>
					<li>
					    <span><em class="red">* </em>工号：</span>
					    <input id="jobnumber" name="jobnumber" class="{required:true,maxlength:20} inputstyle" onblur="checkJobnumber();"/>
					</li>
					<li>
					    <span><em class="red">* </em>加入公司时间：</span>
					    <input id="joindate" name="joindate" class="{required:true,dateISO:true} inputselectstyle datepicker" onblur="setOfficialdateplan();" />
					    <div class="date-trigger" onclick="$('#joindate').focus();"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>考勤号：</span>
					    <input id="checknumber" name="checknumber" class="{maxlength:20} inputstyle"/>
					</li>
					<li>
					    <span>加入集团时间：</span>
					    <input id="joingroupdate" name="joingroupdate" class="{dateISO:true} inputselectstyle datepicker"/>
					    <div class="date-trigger" onclick="$('#joingroupdate').focus();"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span><em class="red">* </em>员工类别：</span>
					    <input id="classification" name="classification" type="hidden" value=""/>
					    <input id="classificationname" name="classificationname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#classification','#classificationname','CLASSIFICATION');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#classification','#classificationname','CLASSIFICATION');"/>
					</li>
					<li>
					    <span>用工性质：</span>
					    <input id="employtype" name="employtype" type="hidden" value=""/>
					    <input id="employtypename" name="employtypename" class="inputselectstyle" onclick="chooseOptionTree('#employtype','#employtypename','EMPLOYTYPE');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#employtype','#employtypename','EMPLOYTYPE');"/>
					</li>
				</ul>
				<ul>
				
					<li>
					    <span style="color:green;">手机内网号码：</span>
					    <input id="shortphone" name="shortphone" class="{maxlength:10} inputstyle"/>
					</li>
					<li>
					    <span style="color:green;">公司邮箱：</span>
					    <input id="email" name="email" class="{maxlength:50,email:true} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span style="color:green;">办公电话：</span>
					    <input id="officephone" name="officephone" class="{maxlength:20} inputstyle"/>
					</li>
					<li>
					    <span>办公地址：</span>
					    <input id="officeaddress" name="officeaddress" class="{maxlength:100} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span style="color:green;"><em class="red">* </em>通讯录信息保密：</span>
					    <input id="secrecy" name="secrecy" type="hidden" value=""/>
					    <input id="secrecyname" name="secrecyname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#secrecy','#secrecyname','SECRETLEVEL');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#secrecy','#secrecyname','SECRETLEVEL');"/>
					</li>
					<li>
					    <span style="color:green;">通讯录手机：</span>
					    <input id="addressmobile" name="addressmobile" class="{maxlength:20} inputstyle"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span><em class="red">* </em>试用期(月)：</span>
					    <input id="studymonth" name="studymonth" class="{required:true,number:true,maxlength:4} inputstyle" onblur="setOfficialdateplan();" />
					</li>
					<li>
					    <span>计划转正时间：</span>
					    <input id="officialdateplan" name="officialdateplan" class="{dateISO:true} inputselectstyle datepicker"/>
					    <div class="date-trigger" onclick="$('#officialdateplan').focus();"/>
					</li>
				</ul>
				<div id="myOfficial">
					<ul>
						<li>
						    <span>转正时间：</span>
						    <input id="officialdate" name="officialdate" class="{dateISO:true} inputselectstyle datepicker"/>
						    <div class="date-trigger" onclick="$('#officialdate').focus();"/>
						</li>
					</ul>
					<ul>
						<li>
						    <span>转正备注：</span>
						    <textarea id="officialmemo" name="officialmemo"  rows="5" style="width:570px;" class="{maxlength:250} areastyle"></textarea>
						</li>
					</ul>
				</div>
				<ul id="myResignation">
					<li>
					    <span>离职时间：</span>
					    <input id="resignationdate" name="resignationdate" class="{dateISO:true} inputselectstyle datepicker"/>
					    <div class="date-trigger" onclick="$('#resignationdate').focus();"/>
					</li>
					<li>
					    <span>离职原因：</span>
					    <input id="resignationreason" name="resignationreason" type="hidden" value=""/>
					    <input id="resignationreasonname" name="resignationreasonname" class="inputselectstyle" onclick="chooseOptionTree('#resignationreason','#resignationreasonname','RESIGNATIONREASON');"/>
					    <div class="select-trigger" onclick="chooseOptionTree('#resignationreason','#resignationreasonname','RESIGNATIONREASON');"/>
					</li>
				</ul>
				<ul>
					<li>
					    <span>备注：</span>
					    <textarea id="modimemo" name="modimemo"  rows="5" style="width:570px;" class="{maxlength:250} areastyle"></textarea>
					</li>
				</ul>
			</fieldset>
			
       </form>
	</div>
	<div class="table-bar" style="border-top:1px solid #ddd;">
		<div class="table-ctrl">
			<a class="btn active" href="javascript:onActive();" style="display:none;"><i class="icon icon-off"></i><span>立即生效</span></a>
			<a class="btn lz" href="javascript:openResignation();" style="display:none;"><i class="icon icon-minus"></i><span>离职</span></a>
			<a class="btn hf" href="javascript:revertResignation();" style="display:none;"><i class="icon icon-retweet"></i><span>离职恢复</span></a>
			<a class="btn zz" href="javascript:openOfficial();" style="display:none;"><i class="icon icon-bookmark"></i><span>转正</span></a>
			
			<a class="btn save" href="javascript:save();" style="display:none;"><i class="icon icon-check"></i><span>保存</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
</div>







<!-- 员工转正-->
<div id="official" title="员工转正" style="display:none;">
	<form action="" id="officialForm"  class="form">
		<ul>
			<li>
			    <span><em class="red">* </em>转正时间：</span>
			    <input id="o_officialdate" name="o_officialdate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#o_officialdate').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span>转正备注：</span>
			    <textarea id="o_officialmemo" name="o_officialmemo"  rows="5"  cols="40" class="{maxlength:250} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>








<!-- 员工离职 -->
<div id="resignation" title="员工离职" style="display:none;">
	<form action="" id="resignationForm"  class="form">
		<ul>
			<li>
			    <span><em class="red">* </em>离职时间：</span>
			    <input id="r_resignationdate" name="r_resignationdate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#r_resignationdate').focus();"/>
			</li>
		</ul>
		<ul>
		<li>
		    <span><em class="red">* </em>离职原因：</span>
		    <input id="r_resignationreason" name="r_resignationreason" type="hidden" value=""/>
		    <input id="r_resignationreasonname" name="r_resignationreasonname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#r_resignationreason','#r_resignationreasonname','RESIGNATIONREASON');"/>
		    <div class="select-trigger" onclick="chooseOptionTree('#r_resignationreason','#r_resignationreasonname','RESIGNATIONREASON');"/>
		</li>
		</ul>
	</form>
</div>




</body>
</html>
