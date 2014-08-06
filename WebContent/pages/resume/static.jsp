<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title>简历管理</title>
<base href="${baseUrl }"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<link rel="stylesheet" type="text/css" href="${baseUrl }skins/css/style.css" id="framecss"/>
<link rel="stylesheet" type="text/css" href="plugin/grid/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/default/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/green/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/mac/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/china/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/vista/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/calendar/calendar-blue.css"  />


<link rel="stylesheet" type="text/css" href="skins/css/form.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeIcons.css"/>
<link rel="stylesheet" type="text/css" href="plugin/grid/calendar/calendar-blue.css"  />
<link rel="stylesheet" type="text/css" href="plugin/lodop/PrintSample10.css"/>

<style>
<!--
.resumeBody {color: #555555;font-size: 12px;border-top:1px solid #ddd;}
.resumeBody strong, .resumeBody h1, .resumeBody h5, .resumeBody h6 {color: #555555;font-size:14px;padding:0px;margin:0px;}
.resumeBody h1 {font-size: 32px;height: 42px;line-height: 1em;}
.resumeBody .headerImg {border: 1px solid #DDDDDD;padding: 5px;}
.resumeBody .ver-line {color: #555555;font-size: 14px;padding: 0 5px;}
.resumeBody h5, .resumeBody h6, .resumeBody strong {font-weight: bold;}
.resumeBody .details dt h5 {color: #315AAA;font-size: 14px;height: 26px;line-height: 26px;text-indent: 20px;}
.resumeBody .summary, .resumeBody .details dd {line-height: 25px;;margin: 10px;padding-left: 10px;}
.resumeBody .certificates, .printhiden .training, .resumeBody .graduates, .resumeBody .social, .resumeBody .education-background, .resumeBody .project-experience, .resumeBody .work-experience {border-bottom: 1px dotted #CCCCCC;margin-bottom: 10px;padding-bottom: 5px;}
.resumeBody .certificates:last-child, .printhiden .training:last-child, .resumeBody .graduates:last-child, .resumeBody .education-background:last-child, .resumeBody .project-experience:last-child, .resumeBody .work-experience:last-child {border-bottom: 0 none;}
.printhiden .training h6 {margin-bottom: 10px;}
.resumeBody .em {}

table.static-table{border:0px;}
table.static-table td{border:0px;}
table.static-table tbody tr:HOVER {
background:none;
}

.printhiden{padding:5px 10px 10px 0px;}
.printhiden strong, .printhiden h1, .printhiden h5, .printhiden h6 {color: #555555;font-size:14px;padding:0px;margin:0px;}
.printhiden h5, .printhiden h6, .printhiden strong {font-weight: bold;}
.printhiden dt h5 {color: #315AAA;font-size: 14px;height: 26px;line-height: 26px;text-indent: 20px;}
.printhiden dd {line-height: 25px;;margin: 10px;padding-left: 10px;}
.printhiden .ver-line {color: #555555;font-size: 14px;padding: 0 5px;}
-->
</style>

<!-- 打印style,切勿修改 -->
<style type="text/css" id="remove">
 .details  dt {background: url("skins/images/lookResumebg.jpg") no-repeat scroll 5px 0 transparent;} 
</style>


<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="skins/js/jquery.layout.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>

<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="plugin/timepicker/jquery-ui-timepicker-addon.js"></script>

<script type="text/javascript" src="plugin/grid/calendar/calendar.js"></script>
<script type="text/javascript" src="plugin/grid/calendar/calendar-en.js"></script>
<script type="text/javascript" src="plugin/grid/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="plugin/grid/gt_grid_all.js"></script>
<script type="text/javascript" src="plugin/grid/gt_msg_cn.js"></script>
<script type="text/javascript" src="pages/tree.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="plugin/lodop/LodopFuncs.js"></script>





<script type="text/javascript">
var mycandidatesguid='${param.mycandidatesguid}';
var auditionrecordguid='${param.auditionrecordguid}';
var candidatesstate='${param.candidatesstate}';
var webuserguid='${param.id}';
var auditiontime='${param.auditiontime}';
var userid='${userid}';
var recommend='${param.recommend}';
var arrangement='${param.arrangement}';
var advise='${param.advise}';
var entry='${param.entry}';
var recommendguid='${param.recommendguid}';
var isclose='${param.isclose}';

var filter='${param.filter}';
var formopen='${param.formopen}';
var rownumber='${param.rownumber}';

var pageState=false;

$(document).ready(function () {
	 //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd'
    });
  
    //日期
    $(".timepicker").datetimepicker({
		dateFormat:'yy-mm-dd',
		timeFormat: 'hh:mm:ss',
		beforeShow: function(input, inst) {
   			if($(input).attr("readonly"))
   				inst.inline=true;
   		}
	});
    
  	//按钮状态
	butState();
    
  	if(filter){
  		tuijian();
  	}
  	
  	if(formopen){
  		$("#formopen1").show();
  		$("#formopen2").show();
  	}else{
  		$("#fanhui").show();
  		$("#fanhui1").show();
  	}
});

//是否显示推荐按钮
function tuijian(){
	$("#tuijian1").show();
	$("#tuijian2").show();
}



//判断按钮状态
function butState(){
	$.getJSON("mycandidates/getMyCandidatesByIdAndRecommendGuid.do", {id:mycandidatesguid,recommendguid:recommendguid}, function(data) {
		  if(data!=''&&data!=null){	
			  
			  //判断状态
			  	if((data.candidatesstate==4||data.candidatesstate==6||data.candidatesstate==9)&&(arrangement||${admin})){
			  		auditiontime=data.auditiontime;
			  		recommendguid=data.recommendguid;
			  		if(data.candidatesstate==6||data.candidatesstate==9){
			  			auditiontime=null;
			  		}
			  		$("#arrangement").show();
				}else if((data.candidatesstate==7||data.candidatesstate==13||data.candidatesstate==16||data.candidatesstate==17)&&(advise||${admin})&&data.candidatestype!=6){
					$("#examination ").show();
				}else if(data.candidatesstate==12&&(entry||${admin})){
					$("#entry").show();
				}else if(data.candidatesstate==2&&(data.userguid==userid||${admin})){
					//从简历管理过来的都无认定和面试结果的权限
				  	if(recommendguid!=null&&recommendguid!=''){
				  		$("#rending").show();
						$("#rending1").show();
				  	}
				}else if(data.candidatesstate==5&&(data.maininterviewerguid==userid||${admin})){
					if(recommendguid!=null&&recommendguid!=''){
						$("#jieguo").show();
						$("#jieguo1").show();
					}
				}
		  }
	});
	
	
	if(isclose){
		$("#isclose").show();
		$("#openSystem").show();
		$("#fanhui").hide();
		$("#isclose1").show();
		$("#fanhui1").hide();
		$("#xiugai").hide();
		$("#xiugai1").hide();
		$("#gotop").hide();
		$("#tuijian1").hide();
		$("#tuijian2").hide();
	}
}



//返回
function back(){
	window.parent.convertView(pageState?null:'');
}


function back2(){
	if(formopen){
		recommendclose();
		$("#tujian1").hide();
		$("#tuijian2").hide();
	}else
		window.parent.convertView(null);
}


//关闭推荐信息
function recommendclose(){
	$("#recommendWindow").dialog("close");
}



//修改简历
function edit(){
	window.location.href='${baseUrl }resume.do?page=form&id='+webuserguid;
}

//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	
}




function close(){
	window.parent.close();
}
</script>



<script type="text/javascript">
//安排面试窗口
var arrangementForm=null;
function openArrangementWindow(state){
	//打开安排面试
	$("#arrangementWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		position:['center','top'],
		buttons: {
			"确定":function(){
				if(arrangementForm.form()){
					var array=new Array();
					$("#employeeids input[type='checkbox']").each(function(){
						array.push($(this).val());
					});
					$('#arrangementForm input[id=userguid]').val(array.toString());
					$("#arrangementForm").submit();
				}
			},
			"重置":function(){
				$("#arrangementForm").clearForm();
				$("#employeeids").html('<span>&nbsp;</span>');
			},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			$("#arrangementForm").clearForm();
			$("#employeeids").html('<span>&nbsp;</span>');
			$("#array").val(mycandidatesguid);
			$('#arrangementForm input[id=state]').val(1);
			$('#arrangementForm input[id=recommendguid]').val(recommendguid);
			
			//默认是发送短信和邮件
			$("input[name='isemail']").attr("checked", true);
			$("input[name='ismsg']").attr("checked", true);
			$("#isemail").val(1);
			$("#ismsg").val(1);
			
			
			if(auditiontime!=null&&auditiontime!=''){
				$('#arrangementForm input[id=auditiondate]').val(auditiontime);
			}
			
			//校验
			if(arrangementForm==null){
				arrangementForm=$("#arrangementForm").validate();
			    $('#arrangementForm').ajaxForm(function(data) {
			    	alert("安排面试成功！");
			    	$("#arrangementWindow").dialog("close");
			    	pageState=true;
		    		window.parent.callbackPageState(pageState);
		    		back();
			    });
			}
		}
	});
}


//安排体检
var examinationForm=null;
function openExaminationWindow(state){
	$("#examinationWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		position:['center','top'],
		buttons: {
			"确定":function(){
				if(examinationForm.form()){
					var isemail=$("#isemails").attr("checked")?1:0;
					var ismsg=$("#ismsgs").attr("checked")?1:0;
					updateBatchCandidatesstate(state,mycandidatesguid,isemail,ismsg);
					$("#examinationWindow").dialog("close");
				}
			},
			"重置":function(){
				$("#examinationWindow").clearForm();
			},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			//默认是发送短信和邮件
			$("input[name='isemails']").attr("checked", true);
			$("input[name='ismsgs']").attr("checked", true);
			$("#isemails").val(1);
			$("#ismsgs").val(1);
			
			
			//校验
			if(examinationForm==null)
				examinationForm=$("#examinationForm").validate();
		}
	});
}


//待体检 
function updateBatchCandidatesstate(state,arrayList,isemail,ismsg){
	var thirdpartnerguid=$("#thirdpartnerguid").val();
	$.getJSON("mycandidates/updateBatchCandidatesstate.do", {ids:arrayList,state:state,thirdpartnerguid:thirdpartnerguid,isemail:isemail,ismsg:ismsg}, function(data) {
		alert("操作成功！");
		pageState=true;
		window.parent.callbackPageState(pageState);
		back();
	});
}


//待入职
var personForm=null;
function openQuotaWindow(state){
	$.getJSON("resume/getResumeById.do", {id:webuserguid}, function(data) {
		if(data!=null){
	       if(data.sex!=null&&data.sex!=''){
	    	   $("#personWindow").dialog({
	    			autoOpen: true,
	    			modal: true,
	    			resizable:false,
	    			position:['center','top'],
	    			width:480,
	    			buttons: {
	    				"确定": function() {
	    					if(personForm.form){
	    						personsave();
	    					}
	    				},
	    				"取消": function() {
	    					$(this).dialog("close");
	    				}
	    			},open:function(){
	    				$("#personForm").clearForm();
	    				//加载公司部门岗位信息
	    				loadQuota(mycandidatesguid);
	    				$('#personForm input[id=mycandidatesguid]').val(mycandidatesguid);
	    				personForm=$("#personForm").validate({submitHandler: function(form) {
	    				    	$(form).ajaxSubmit(function(data) {
	    				    		alert("操作成功！");
	    				    		$("#personWindow").dialog("close");
	    				    		pageState=true;
	    				    		window.parent.callbackPageState(pageState);
	    				    		back();
	    				        });
	    					}
	    				});
	    			}
	    		});
	       }else{
	    	   alert("当前应聘者的简历信息不全,请补全简历信息再填写入职信息!");
	       }
		}
	});
}


//待入职保存
function personsave(){
	//当前岗位是否存在于招聘计划中
	var postid=$("#t_postid").val();
	var quotaid=$("#quotaid").val();
	$.getJSON("recruitprogram/getRecruitprogramByPostId.do", {id:postid,quotaid:quotaid}, function(data) {
		if(data!=null&&data!=''){
			//判编是否存在
			$.getJSON("quota/getQuotaById.do", {id:quotaid}, function(msg) {
				if(msg!=null&&msg!=''){
			       if(msg.vacancynumber<=0){
			    	   alert("该编制下已经超编,请重新选择!");
			    	   return;
			       }else{
			    	   $("#personForm").submit();
			       }
				}else{
					$("#personForm").submit();
				}
			});
		}else{
			alert("你所选择的岗位不在招聘计划内，请重新选择!");
			return;
		}
	});
}



//置值(公司、部门、岗位)
function loadQuota(mycandidatesid){
	$.getJSON("mycandidates/getRecommendByCandidatesGuidAndState.do", {id:mycandidatesid}, function(data) {
		if(data!=null){
	        $("#t_companyid").val(data.recommendcompanyid);
	        $("#t_companyname").val(data.recommendcompanyname);
	        $("#t_deptid").val(data.recommenddeptid);
	        $("#t_deptname").val(data.recommenddeptname);
	        $("#t_postid").val(data.recommendpostguid);
	        $("#t_postname").val(data.recommendpostname);
		}
	});
}


//公司回调
function callBackTCompany(){
	$("#t_deptid").val(null);
	$("#t_deptname").val(null);
	$("#rankid").val(null);
	$("#rankname").val(null);
	
	//部门选择回调
	callbackTDept();
}


//部门选择回调
function callbackTDept(){
	$("#t_postid").val(null);
	$("#t_postname").val(null);
	$("#quotaid").val(null);
	$("#quotaname").val(null);
}


//校验编制下是否满员
function callBackQuota(){
	var quotaid=$("#quotaid").val();
	$.getJSON("quota/getQuotaById.do", {id:quotaid}, function(data) {
		if(data!=null){
	       if(data.vacancynumber<=0){
	    	   alert("该编制下已经满人,请填写备注信息!");
	       }
		}
	});
}






//打勾发送邮件、短信
function checkisEmail(el){
	var state=$(el).attr("checked");
	$(el).val(state?1:0);
}


</script>


<script type="text/javascript">

//推荐多人
function onRecommend(mycandidatesguid,webuserguid,state){
	//检验是否已在推荐后的流程中了
	$.getJSON("mycandidates/checkMyCandidatesByUserGuid.do",{userguid:webuserguid,state:state}, function(data) {
		if(data!=null&&data!=""){
			alert(data);
			return;
		}
		
		$.getJSON("mycandidates/checkMyCandidatesByCandidatesGuid.do",{mycandidatesguid:mycandidatesguid},function(msg){
			if(msg!=null&&msg!=''){
				alert(msg);
				return;
			}
		
			$("#recommendWindow").dialog({
				autoOpen: true,
				modal: true,
				resizable:false,
				width:750,
				height:530,
				open:function(){
					var url='mycandidates.do?page=list_recommend&mycandidatesguid='+mycandidatesguid;
					var htm='<iframe id="myRecommend" name="myRecommend" width="100%" height="100%" frameborder="0" src="'+url+'" scrolling="no" ></iframe>';
					$("#recommendWindow").html(htm);
				},
				close:function(){
					$("#recommendWindow").html(null);
				}
			});
			
		});
	});
	
}
</script>


<script type="text/javascript">

//确认面试
var matchForm=null;
function openMacthWindow(mycandidatesid,state,single,webuserguid){
	var array=new Array();
	var userarray=new Array();
	if(single){
		array.push(mycandidatesid);
		userarray.push(webuserguid);
	}
	$.getJSON("mycandidates/checkMyCandidatesByUserGuid.do",{userguid:userarray.toString(),state:state}, function(data) {
		if(data!=null&&data!=""){
			alert(data);
			return;
		}else{
			openMacth(mycandidatesid,state,array);
		}
	});
	
}


function openMacth(mycandidatesid,state,array){
	$("#matchWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		position:['center','top'],
		buttons: {
			"确定":function(){
				if(matchForm.form()){
					$("#matchForm").submit();
				}
			},
			"重置":function(){
				$("#matchForm").clearForm();
			},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			$("#matchForm").clearForm();
			$("#matchstate").val(state);
			$("#matchForm #arrayList").val(array.toString());
			
			//认定把推荐公司，推荐部门，推荐岗位，推荐人置值
			if(recommendguid!=null&&recommendguid!=''&&recommendguid!='null')
				$.getJSON("mycandidates/getRecommendById.do", {id:recommendguid}, function(data) {
					if(data!=null){
						for (var key in data) {
					       // var el = $('#matchForm #' + key);
					        var el = $('#matchForm input[id=' + key+'],#matchForm textarea[id=' + key+']');
					        if(el) el.val(data[key]);
					    }
						//设置默认值
						$("#matchstate").val(state);
						$("#matchForm #arrayList").val(array.toString());
					}
				});
			
			//校验
			if(matchForm==null)
				matchForm=$("#matchForm").validate({submitHandler:function(form) {
				    	$(form).ajaxSubmit(function(data) {
				    		alert("操作成功!");
				    		$("#matchWindow").dialog("close");
				    		$("#rending").hide();
				    		$("#rending1").hide();
				    		pageState=true;
				    		window.parent.callbackPageState(pageState);
				    		back();
				        });
					}
				});
		}
	});
}



//公司回调
function callBackCompany(){
	$("#recommenddeptid").val(null);
	$("#recommenddeptname").val(null);
	//部门选择回调
	callbackDept();
}



//部门选择回调
function callbackDept(){
	$("#recommendpostguid").val(null);
	$("#recommendpostname").val(null);
	$("#userguid").val(null);
	$("#username").val(null);
}
</script>


<script type="text/javascript">

//面试结果窗口
var addAuditionResultForm=null;
function openAuditionResultWindow(id){
	$("#addAuditionResultWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		position:['center','top'],
		width:480,
		buttons: {
			"确定": function() {
				if(addAuditionResultForm.form){
					$("#addAuditionResultForm").submit();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#addAuditionResultForm").clearForm();
			$('#addAuditionResultForm input[id=mycandidatesguid]').val(mycandidatesguid);
			$("#isrelease").val(2);
			addAuditionResultForm=$("#addAuditionResultForm").validate({submitHandler: function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		alert("操作成功");
			    		$("#addAuditionResultWindow").dialog("close");
			    		$("#jieguo").hide();
			    		$("#jieguo1").hide();
			    		pageState=true;
			    		window.parent.callbackPageState(pageState);
			    		back();
			        });
				}
			});
		}
	});
}




//评价
function openResumeAssess(id){
	$("#addResumeAssessWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		position:['center','top'],
		buttons: {
			"确定": function() {
				if(addResumeAssessForm.form){
					$("#addResumeAssessForm").submit();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#addResumeAssessForm").clearForm();
			$("#webuserguid").val(id);
			addResumeAssessForm=$("#addResumeAssessForm").validate({submitHandler: function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		alert("评价成功！");
			    		$("#addResumeAssessWindow").dialog("close");
			    		pageState=true;
			    		window.parent.callbackPageState(pageState);
			    		back();
			        });
				}
			});
		}
	});
}



//CSSRuleList，样式表的规则集合列表
function getStyleSheet(element) {
    return element.sheet || element.styleSheet;
}

//打印简历
/* function myPreview(){	
	alert(111);
	$.post("resume/resumePrint.do",{id:webuserguid}, function(data) {
		var link = document.getElementById("framecss");
		var sheet = getStyleSheet(link);
 		alert(sheet.href);
 		if(sheet.cssRules){
 			alert(sheet.cssRules.length);
 			//sheet.addRule(".resumeBody .details dt ", '{background: url("skins/images/lookResumebg.jpg") no-repeat scroll 5px 0 transparent;}', 4);
 			//sheet.deleteRule(0);
 			sheet.insertRule('.resumeBody .details dt {background: url("skins/images/lookResumebg.jpg") no-repeat scroll 5px 0 transparent;}', 32);
 			alert(sheet.cssRules.length);
 			//alert(sheet.style.cssText);
 		}
 		else{
 			//alert("2222"+sheet.rules[4].style.cssText);
 			alert(sheet.style.cssText);
 		}
		LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  	
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_打印关联内容一");
		LODOP.ADD_PRINT_HTM("20px","5%","90%","100%",data);
		LODOP.SET_PRINT_MODE("PRINT_PAGE_PERCENT","full-width");
		LODOP.PREVIEW();
    },'html');
};	 */
 
 
//打印简历
function myPreview(flag){	
	LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  	
	var strBodyStyle="<style>"+document.getElementById("style1").innerHTML+".resumeBody li{list-style:none;width:100%;} .resumeHistorys{display:display;} "+"</style>";
	strBodyStyle = strBodyStyle+"<style>.details .printhiden{display:none;}</style>"
	var link = document.getElementById("remove");
	var sheet = getStyleSheet(link);
	if(sheet&&sheet!=null){
		var cssRules=sheet.cssRules;
		//删除、浏览器判断
		if(cssRules!=null&&cssRules.length > 0){
			if(cssRules)
				sheet.deleteRule(0);
			else
				sheet.removeRule(0);//IE版本
		}
		else{
			$(".details dt").css({"background":"none"});
		}
	}
	
	$(".details dt").prepend('<img alt="" style="float:left;position:absolute;z-index:-1;margin-left:5px;" src="${baseUrl }skins/images/lookResumebg.jpg"/>');
	var strFormHtml ="";
	if(!flag){
		$(".falseh5").css({"margin-left":"10px"});
		strFormHtml=strBodyStyle+"<body><div align='center' style='border-bottom:1px solid #ddd;'><h1>个人简历</h1></div>"+document.getElementById("form1").innerHTML+document.getElementById("resumeHistory").innerHTML+"</body>";
	}
	else{
		$(".dlimg dt").css({"margin-left":"10px"});
		$(".fushu dt").prepend('<img alt="" style="float:left;position:absolute;z-index:-1;margin-left:5px;" src="${baseUrl }skins/images/lookResumebg.jpg"/>');
		strFormHtml=strBodyStyle+"<body><div align='center' style='border-bottom:1px solid #ddd;'><h1>个人简历</h1></div>"+document.getElementById("printAll").innerHTML+document.getElementById("resumeHistory").innerHTML+"</body>";
	}
		
	LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_样式风格");
	//纸张
	LODOP. SET_PRINT_PAGESIZE (1,0, 0,"A4");
	//如果前面剩余空间不足，关联对象顺序打印时就"从新页开始"
	LODOP.SET_PRINT_STYLEA("All","LinkNewPage",true);
	LODOP.ADD_PRINT_HTM("2%","5%","90%","90%",strFormHtml);
	LODOP.PREVIEW();
	if(flag)
		$(".dlimg dt").css({"margin-left":"0px"});
};



//投递历史
var searchForm=null;
function searchHistory(mycandidatesguid){
	$("#history").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		height:600,
		maxHeight:800,
		width:550,
		position:['center','top'],
		buttons: {
			
		},
		open:function(){
			var url='resume/getResumeHistoryById.do?mycandidatesguid='+mycandidatesguid;
			var htm='<iframe id="history" name="history" width="100%" height="100%" frameborder="0" src="'+url+'" scrolling="no" ></iframe>';
			$("#history").html(htm);
		}
	});
}



</script>


<script type="text/javascript">

//双击图片变大
function showPic(dom){
	var cityObj = $(dom);
	var cityOffset = $(dom).offset();
	var img = $("#mypic").attr("src");
	var htm='';
	htm+='<img style="width:100%;height:100%;border:1px solid #CCCCCC;" src="';
	htm+=img;
	htm+='">';
	var mypicwindow=$("#mypicwindow");
	mypicwindow.html(htm);
    mypicwindow.css({"left":cityOffset.left/2+"px"});
    mypicwindow.css({"top":cityOffset.top/2+"px"});
	mypicwindow.show();
}

function hidePic(){
	var mypicwindow=$("#mypicwindow");
	mypicwindow.hide();
}



// 通讯录
function goAddressList(addresslistguid){
	window.open('${baseUrl}resume.do?page=form_addresslist&id='+addresslistguid);
}


//进入系统
function openSystem(){
	window.open("http://125.210.208.60:9080/hrmweb");
}





</script>






<!--打印-->
<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
	<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="install_lodop32.exe"></embed>
</object> 
</head>
<body ondblclick="hidePic();" style="overflow:auto">


<div class="table">
	<div class="table-bar">
		<div class="table-title">
			简历预览
		</div>
		<div class="table-ctrl">
			<!--  <a class="btn" id="tuijian" href="javascript:openMacthWindow(mycandidatesguid,2,false);" style="display:none"><i class="icon icon-thumbs-up"></i><span>推荐</span></a>-->
			<a class="btn" id="pingjia2" href="javascript:openResumeAssess(webuserguid)"><i class="icon icon-thumbs-up"></i><span>评价</span></a>
			<a class="btn" style="display:none" id="tuijian2" href="javascript:onRecommend(mycandidatesguid,webuserguid,2);" style="display:none"><i class="icon icon-thumbs-up"></i><span>推荐</span></a>
			<a class="btn" id="jieguo" href="javascript:openAuditionResultWindow();" style="display:none"><i class="icon icon-thumbs-up"></i><span>结果反馈</span></a>
		    <a class="btn" id="rending" href="javascript:openMacthWindow(mycandidatesguid,4,true,null)" style="display:none"><i class="icon icon-thumbs-up"></i><span>确认面试</span></a>
   		    <a class="btn" id="arrangement" href="javascript:openArrangementWindow(5)" style="display:none"><i class="icon icon-indent-left"></i><span>安排面试</span></a>
		    <a class="btn" id="examination" href="javascript:openExaminationWindow(10)" style="display:none"><i class="icon icon-fire"></i><span>安排体检</span></a>
		    <a class="btn" id="entry" href="javascript:openQuotaWindow(14)" style="display:none"><i class="icon icon-fire"></i><span>安排入职</span></a>
		    <a class="btn" href="javascript:edit();" id="xiugai"><i class="icon icon-pencil"></i><span>修改</span></a>
			<a class="btn" href="javascript:myPreview(false);"><i class="icon icon-print"></i><span>打印</span></a>
			<a class="btn" href="javascript:myPreview(true);"><i class="icon icon-print"></i><span>打印结果集</span></a>
			<a class="btn" id="fanhui" href="javascript:back();" style="display:none"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
			<a class="btn" id="formopen2" style="display:none" href="javascript:window.close();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
			<a class="btn" style="display:none" id="isclose"  href="javascript:window.close();"><i class="icon icon-remove"></i><span>关闭</span></a>
			<a class="btn" style="display:none" id="openSystem"  href="javascript:openSystem();"><i class="icon icon-in"></i><span>进入系统</span></a>
		</div>
	</div>
	<!-- 打印投递历史 -->
		<div id="resumeHistory" class="resumeHistorys" style="height: auto;width: auto; display: none;">
	    <c:if test="${mycandidateslist != null}">
			     <dl class="details dlimg">
				<dt><h5 style="color: #315AAA;font-size: 14px;height: 26px;line-height: 26px;text-indent: 20px;" >投递历史</h5></dt>
				</dl>
			   <div class="resumeBody">
			         <!--本次  -->
			         <ul style="float: left; width:auto;">
						<br>
						<li>
							投递时间：${mycandidates.candidatestime }
						</li>
						<li>
						&nbsp;&nbsp;&nbsp;&nbsp;<h6><span style="color: #369BD7">(1)</span>应聘公司：${mycandidates.companyname} <span class="ver-line">|</span> 应聘职位：${mycandidates.postname}
						   </h6>
						</li>
						<br>
					 </ul>
					 <!-- 历史 -->
					<c:forEach items="${mycandidateslist }" var="item" varStatus="statu" begin="0">
					 <ul style="float: left; width: auto;">
						<br>
						<li style="font-weight: normal;font-size: 13px;">
							投递时间：${item.candidatestime }
						</li>
						<li>
						&nbsp;&nbsp;&nbsp;&nbsp;<h6><h6><span style="color: #369BD7">(${statu.index+2})</span>应聘公司：
						   ${item.companyname} <span class="ver-line">|</span> 应聘职位：${item.postname}
						   </h6>
						</li>
						<br>
					 </ul>
					</c:forEach>
					 <ul style="clear:both;">
					 </ul>
			 </div>
	     </c:if> 
	</div>
	
		<!-- 投递历史 -->
		
	    <c:if test="${mycandidateslist != null}">
	       <hr color="#DDDDDD">
			     <br><p style="font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;投递历史</p>
			   <div class="resumeBody">
			         <!--本次  -->
			         <ul style="float: left; width: 335px;">
						<br>
						<li width="100px";>
						&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #369BD7">(1)</span>应聘公司：
						   ${fn:length(mycandidates.companyname ) > 13 ? fn:substring(mycandidates.companyname ,0,13) : mycandidates.companyname }${fn:length(mycandidates.companyname ) > 13 ? '...' : ''}
						</li>
						<br>
						
						<li width="100px";>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;应聘职位：
						   <a href="javascript:searchHistory('${mycandidates.mycandidatesguid }');" title="${mycandidates.postname}">
						   ${fn:length(mycandidates.postname ) > 13 ? fn:substring(mycandidates.postname ,0,13) : mycandidates.postname }${fn:length(mycandidates.postname ) > 13 ? '...' : ''}
						   </a>
						</li>
						<br>
						
						<li width="100px";>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;投递时间：${mycandidates.candidatestime }
						</li>
					 </ul>
					 <!-- 历史 -->
					<c:forEach items="${mycandidateslist }" var="item" varStatus="statu" begin="0">
					 <ul style="float: left; width: 335px;">
						<br>
						<li width="100px";>
						&nbsp;&nbsp;&nbsp;&nbsp;(${statu.index+2})应聘公司：
						   ${fn:length(item.companyname ) > 13 ? fn:substring(item.companyname ,0,13) : item.companyname }${fn:length(item.companyname ) > 13 ? '...' : ''}
						</li>
						<br>
						
						<li width="100px";>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;应聘职位：
						   <a href="javascript:searchHistory('${item.mycandidatesguid }');" title="${item.postname}">
						    ${fn:length(item.postname ) > 13 ? fn:substring(item.postname ,0,13) : item.postname }${fn:length(item.postname ) > 13 ? '...' : ''}
						   </a>
						</li>
						<br>
						
						<li width="100px";>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;投递时间：${item.candidatestime }
						</li>
					 </ul>
					</c:forEach>
					 <ul style="clear:both;">
					 </ul>
			 </div>
	     </c:if> 
	<div id="printAll">
	<div class="table-wrapper" id="form1">
	
		<div class="resumeBody">
								
			<!-- 基本信息 -->
			<div class="summary">
			<c:if test="${resume.photo==null}">
				<img id="mypic" onclick="showPic(this);" style="max-width:200px;height:125px;float:right;" class="headerImg" alt="" src="${baseUrl }skins/images/nopic.jpg">
			</c:if>
			<c:if test="${resume.photo!=null}">
				<img id="mypic" onclick="showPic(this);" style="max-width:200px;height:125px;float:right;" class="headerImg" alt="" src="${resume.photo }">
			</c:if>
		           <h1>${resume.name }</h1>
		           ${resume.sexname } <span class="ver-line">|</span>
		           ${resume.birthday }生 <span class="ver-line">|</span>
		           现居住于${resume.homeplace } <br> 
		           ${resume.workagename }(工作经验) <span class="ver-line">|</span>
		           最高学历${resume.culturename }</span><span class="ver-line">|</span>籍贯${resume.nativeplace}<br> 
		           ${resume.mobile }(手机) <br>
		           E-mail：<a href="mailto:${resume.email }">${resume.email }</a> <br>
		           <c:choose>
		           	<c:when test="${fn:length(resumefiles) > 0}">
		           		附件信息：
		           		<c:forEach items="${resumefiles}"  var="item" varStatus="index">
		           			<c:choose>
		           				<c:when test="${index.index>0}">
		           					<a href="downloadDocument.do?id=${item.resumefileguid}" style="margin-left: 65px;">${item.resumefilename}</a> <br>
			           			</c:when>
			           			<c:otherwise>
			           				<a href="downloadDocument.do?id=${item.resumefileguid}">${item.resumefilename}</a> <br>
			           			</c:otherwise>
		           			</c:choose>
		           		</c:forEach>
		           	</c:when>
		           	<c:otherwise>
		           		附件信息：
		           	</c:otherwise>
		           </c:choose>
			</div>
			
			<dl class="details">
				<dt><h5>自我评价</h5></dt>
				<dd>
	               <div class="training">
	                    ${resume.valuation}
	               </div>
		         </dd>
		     </dl>    
		    <dl class="details">
				<dt><h5>求职意向</h5></dt>
				<dd>
	               <div class="training">
	               	   <strong>期望从事职业：</strong>${resume.occupation}<br>
	                   <strong>行业：</strong>${resume.industry}<br>
	                   <strong>期望月薪：</strong>${resume.salary}元/月<br>
	                   <strong>目前状况：</strong>${resume.situation}<br>
	               </div>
		         </dd>     
		   </dl>
		   
			<dl class="details">
				<dt><h5>工作经历</h5></dt>
				<dd>
					<c:forEach items="${workexperiences }" var="item">
		               <div class="work-experience">
		                   <p>${item.startdate } -- ${item.enddate==null?'至今':item.enddate }</p>
		                   <h6>${item.workunit } <span class="ver-line">|</span> ${item.posation } </h6>
		                   <p>
		                       <strong>职责描述：</strong><br>
		                       ${item.jobdescription }
		                   </p>
		               </div>
		               </c:forEach>
		           </dd>
		           
		           
				<dt><h5>项目经验</h5></dt>
				<dd>
					<c:forEach items="${projectexperiences }" var="item">
		               <div class="project-experience">
		                   <p>${item.startdate } -- ${item.enddate==null?'至今':item.enddate }</p>
		                   <h6>${item.itemname }</h6>
		                   <p>
		                   <strong>职责描述：</strong><br>
		                    ${item.jobdescription }
		                   </p>
		               </div>
		               </c:forEach>
		         </dd>
		           
		           
				<dt><h5>教育经历</h5></dt>
				<dd>
					<c:forEach items="${educationexperiences }" var="item">
		               <div class="education-background">
		                  <p>${item.startdate } -- ${item.enddate==null?'至今':item.enddate }</p>
		                   <h6>${item.school } <span class="ver-line">|</span> ${item.specialty } <span class="ver-line">|</span> ${item.culturename } </h6>
		                   <p>
		                   <strong>专业描述：</strong><br>
		                    ${item.majordescription }
		                   </p>
		               </div>
		               </c:forEach>
		           </dd>
		            
		            
				<dt><h5>培训经历</h5></dt>
				<dd>
					<c:forEach items="${trainingexperiences }" var="item">
		               <div class="training">
		                   <p>${item.startdate } -- ${item.enddate==null?'至今':item.enddate }</p>
		                   <h6>${item.traininginstitutions } <span class="ver-line">|</span> ${item.certificate } 
		                   <p>
		                   	<strong>培训描述：</strong><br>
		                     		${item.trainingcontent }
		                      </p>
		               </div>
		               </c:forEach>
		          </dd>
		        
		  
			</dl>	
		</div>
	</div>
	
	<div class="printhiden" style="height: auto;width: auto;">
		<dl class="details">
            <c:if test="${interiorRecommend!=null}">
				<dt><h5>内部推荐信息</h5></dt>
				<dd>
	               <div class="training">
	               		<h6>内部推荐人：
                		 <a href="javascript:goAddressList('${interiorRecommend.addresslistguid }');">${interiorRecommend.username}</a>
                		</h6>
	               </div>
		         </dd>
	       </c:if> 
	       
               <c:if test="${recommendsize!=0}">
					<c:forEach items="${recommends}" var="item">
						<div class="fushu" style="border-top:1px dashed #ff0000;padding:10px 0px;">
						 <dt ><h5>简历推荐</h5></dt>
						 	<dd>
				               <div class="training">
				                   <p>建议面试时间：${item.auditiontime }</p>
				                   <h6>${item.recommendcompanyname } <span class="ver-line">|</span> ${item.recommenddeptname } <span class="ver-line">|</span> ${item.recommendpostname } </h6>
				                   <h6>推荐到：
					                   <a href="javascript:goAddressList('${item.addresslistguid }');">
					                   		${item.username}
					                   </a>
				                   </h6>
				                   <h6>推荐人：<span>${item.modiuser}</span></h6>
				                   <p><strong>备注：</strong><br>${item.modimemo }</p>
				               </div>
	              			</dd>  
		               
		               
	             	<dt><h5>面试记录</h5></dt>
						 <dd>
							<c:forEach items="${item.recordResult}" var="record">
				               <div class="training">
				               	   <p>面试时间：${record.auditiondate}</p>
				               	   <h6>${record.maininterviewerguidname} <span class="ver-line">|</span> ${record.auditionaddress} <span class="ver-line">|</span> ${record.resulttypename} </h6>
				                   <p>
				                   <strong>部门评语：</strong><br>
				                    ${record.resultcontentname }
				                   </p>
				                   <p>
				                   <strong>HR评语：</strong><br>
				                    ${record.hrresultcontent}
				                   </p>
				               </div>
				               </c:forEach>
				          </dd>
		          
		          <dt><h5>体检记录</h5></dt>
					 <dd>
						<c:forEach items="${item.examinations}" var="examination">
							<c:if test="${examination.examinationdate!=null}">
				               <div class="training">
				                   <p>体检时间：${examination.examinationdate}</p>
				                   <c:choose>
				                   	   <c:when test="${examination.thirdpartnerguid!=null}">
					                   		<h6>${examination.thirdpartnerguidname}<span class="ver-line">|</span> ${examination.examinationaddress} <span class="ver-line">|</span> ${examination.examinationstatename}</h6>
					               	   		<p>
						                  		 <strong>体检评语：</strong><br>
						                    	 ${examination.examinationresult}
						                    </p>
					                   </c:when>
					                   <c:otherwise>
					                   		<a href="downloadExaminationRecord.do?id=${examination.examinationrecordguid}">体检报告</a><br>
					                   </c:otherwise>
				                   </c:choose>
				                   <p>
				                  		 <c:choose>
				                  		 	<c:when test="${examination.examinationstate!=null}">
				                  		 		   <p>体检结果：${examination.examinationstatename}</p>
				                  		 	</c:when>
				                  		 	<c:otherwise>
				                  		 		<p>体检结果：无</p>
				                  		 	</c:otherwise>
				                  		 </c:choose>
				                    	 
				                   </p>
				               </div>
			               </c:if>
			               </c:forEach>
			          </dd>
		          
		          </div>
		         	 </c:forEach>
		         
		     	</c:if> 
		          
	          <c:if test="${assesslistsize!=0}">
	           	 <dt><h5>简历评价</h5></dt>
				 <dd>
					<c:forEach items="${assesslist}" var="item">
		               <div class="training">
		               	   <p>评价时间：${item.moditimestamp }</p>
		               	    <h6>${item.assesshierarchyname } <span class="ver-line">|</span> ${item.assesslevelname } <span class="ver-line">|</span> ${item.modiuser } </h6>
		                    <p>
		                   <strong>评价结果：</strong><br>
		                    ${item.assessresult }
		                   </p>
		               </div>
		               </c:forEach>
		          </dd>
	          </c:if>
             </dl>
             </div>
             
         </div>
</div>



<div class="table-bar">
		<div class="table-ctrl">
			<a class="btn" id="pingjia1" href="javascript:openResumeAssess(webuserguid)"><i class="icon icon-thumbs-up"></i><span>评价</span></a>
			<a class="btn" style="display:none" id="tuijian1" href="javascript:onRecommend(mycandidatesguid,webuserguid,2);"><i class="icon icon-thumbs-up"></i><span>推荐</span></a>
			<a class="btn" id="jieguo1" href="javascript:openAuditionResultWindow();" style="display:none"><i class="icon icon-thumbs-up"></i><span>结果反馈</span></a>
		    <a class="btn" id="rending1" href="javascript:openMacthWindow(mycandidatesguid,4,true,null)" style="display:none"><i class="icon icon-thumbs-up"></i><span>确认面试</span></a>
	  		<a class="btn" id="arrangement" href="javascript:openArrangementWindow(5)" style="display:none"><i class="icon icon-indent-left"></i><span>安排面试</span></a>
		    <a class="btn" id="examination" href="javascript:openExaminationWindow(10)" style="display:none"><i class="icon icon-fire"></i><span>安排体检</span></a>
		    <a class="btn" id="entry" href="javascript:openQuotaWindow(14)" style="display:none"><i class="icon icon-fire"></i><span>安排入职</span></a>
		    <a class="btn" href="javascript:edit();" id="xiugai1"><i class="icon icon-pencil"></i><span>修改</span></a>
			<a class="btn" href="javascript:myPreview(false);"><i class="icon icon-list-alt"></i><span>打印</span></a>
			<a class="btn" href="javascript:myPreview(true);"><i class="icon icon-print"></i><span>打印结果集</span></a>
			<a class="btn" href="javascript: _goTop();" id="gotop"><i class="icon icon-arrow-up"></i><span>回到顶部</span></a>
			<a class="btn" id="fanhui1" href="javascript:back();" style="display:none"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
			<a class="btn" id="formopen1" style="display:none" href="javascript:window.close();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
			<a class="btn" style="display:none" id="isclose1"  href="javascript:window.close();"><i class="icon icon-remove"></i><span>关闭</span></a>
		</div>
</div>
<hr color="#DDDDDD">




<!--确认面试-->
<div id="matchWindow" title="确认面试信息" style="display:none;">
	<form action="mycandidates/saveOrUpdateRecommend.do" method="post" id="matchForm" class="form">
		<input id="recommendguid" name="recommendguid" type="hidden" value=""/>
		<input id="mycandidatesguid" name="mycandidatesguid" type="hidden" value=""/>
	    <input id="modiuser" name="modiuser" type="hidden" value="${username}"/>
		<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
		<input id="arrayList" name="arrayList" type="hidden" value=""/>
		<input id="userguid" name="userguid" type="hidden" value=""/>
		<input id="matchstate" name="matchstate" type="hidden" value=""/>
		<ul>
			<li>
				<span><em class="red">*</em>建议公司：</span>
				<input id="recommendcompanyid" name="recommendcompanyid" type="hidden" value=""/>
				<input id="recommendcompanyname" name="recommendcompanyname" class="{required:true} inputselectstyle" onclick="chooseMyHasCompanyTree('#recommendcompanyid','#recommendcompanyname',callBackCompany);"/>
				<div class="select-trigger" onclick="chooseMyHasCompanyTree('#recommendcompanyid','#recommendcompanyname',callBackCompany);"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">*</em>建议部门：</span>
				<input id="recommenddeptid" name="recommenddeptid" type="hidden" value=""/>
				<input id="recommenddeptname" name="recommenddeptname" class="{required:true} inputselectstyle" onclick="chooseOneCompanyDeptTree('#recommenddeptid','#recommenddeptname',$('#recommendcompanyid').val(),callbackDept);"/>
				<div class="select-trigger" onclick="chooseOneCompanyDeptTree('#recommenddeptid','#recommenddeptname',$('#recommendcompanyid').val(),callbackDept);"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">*</em>建议岗位：</span>
				<input id="recommendpostguid" name="recommendpostguid" type="hidden" value=""/>
				<input id="recommendpostname" name="recommendpostname" class="{required:true} inputselectstyle" onclick="chooseMyPostTree('#recommendpostguid','#recommendpostname',$('#recommenddeptid').val());"/>
				<div class="select-trigger" onclick="chooseMyPostTree('#recommendpostguid','#recommendpostname',$('#recommenddeptid').val());"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">*</em>建议安排时间：</span>
				<input id="auditiontime" name="auditiontime" class="{required:true} inputselectstyle timepicker"/>
				<div class="date-trigger" onclick="$('#auditiontime').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>备注：</span>
				<textarea id="modimemo" name="modimemo" rows="3" cols="40" style="width:250px;" class="{maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>

<!-- 面试结果窗口 -->
<div id="addAuditionResultWindow" title="面试结果信息" style="display:none;">
	<form action="audition/saveResultByMyCandidatesGuid.do" id="addAuditionResultForm" class="form" method="post">
		<input type="hidden" id="auditionrecordguid" name="auditionrecordguid" value=""/>
		<input type="hidden" id="mycandidatesguid" name="mycandidatesguid" value=""/>
		<input type="hidden" id="moditimestamp" name="moditimestamp"/>
		<input id="modiuser" name="modiuser" type="hidden" value="" />
		<input id="isrelease" name="isrelease" type="hidden" value=2 />
		<ul>
			<li>
			    <span><em class="red">* </em>面试结果：</span>
				<input id="resulttype" name="resulttype" type="hidden" value=""/>
			    <input id="resulttypename" name="resulttypename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#resulttype','#resulttypename','RESULTTYPE');"/>
 			    <div class="search-trigger" onclick="chooseOptionTree('#resulttype','#resulttypename','RESULTTYPE');" />
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>评语：</span>
			    <input id="resultcontent" name="resultcontent" type="hidden" value=""/>
			    <input id="resultcontentname" name="resultcontentname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#resultcontent','#resultcontentname','RESULTCONTENT');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#resultcontent','#resultcontentnamename','RESULTCONTENT');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>备注：</span>
				<textarea id="modimemo" name="modimemo" rows="3" cols="40" style="width:250px;" class="{maxlength:100} areastyle"></textarea>
			</li>
		</ul>
		
	</form>
</div>


<!-- 安排面试 -->
<div id="arrangementWindow" title="安排面试" style="display:none;">
	<form action="audition/saveOrUpateAuditionRecord.do" method="post" id="arrangementForm" class="form">
		<input id="auditionrecordguid" name="auditionrecordguid" type="hidden" value=""/>
		<input id="mycandidatesguid" name="mycandidatesguid" type="hidden" value=""/>
		<input id="userguid" name="userguid" type="hidden" value=""/>
		<input id="modiuser" name="modiuser" type="hidden" value=""/>
		<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
		<input id="state" name="state" type="hidden" value="1"/>
		<input id="recommendguid" name="recommendguid" type="hidden" value=""/>
		
		
		<input id="array" name="array" type="hidden" value=""/>
		<ul>
			<li id="employeeids">
				<span>&nbsp;</span>
			</li>
		</ul>
		<ul>
			<li>
				<span>面试官(抄送)：</span>
				<input id="interviewerguid" name="interviewerguid" type="hidden" value=""/>
				<input id="interviewerguidname" name="interviewerguidname"  class="inputselectstyle" onclick="choosemaininterviewerTree('#interviewerguid','#interviewerguidname');"/>
				<div class="select-trigger" onclick="choosemaininterviewerTree('#interviewerguid','#interviewerguidname');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">*</em>面试官：</span>
				<input id="maininterviewerguid" name="maininterviewerguid" type="hidden" value=""/>
				<input id="maininterviewerguidname" name="maininterviewerguidname" class="{required:true} inputselectstyle" onclick="chooseinterviewerTree('#maininterviewerguid','#maininterviewerguidname','');"/>
				<div class="select-trigger" onclick="chooseinterviewerTree('#maininterviewerguid','#maininterviewerguidname','');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">*</em>面试时间：</span>
				<input id="auditiondate" name="auditiondate" class="{required:true}  inputselectstyle timepicker"/>
				<div class="date-trigger" onclick="$('#auditiondate').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">*</em>面试地点：</span>
				<input id="auditionaddressguid" name="auditionaddressguid" type="hidden" value=""/>
				<input id="auditionaddress" name="auditionaddress" class="{required:true} inputselectstyle" onclick="chooseAddressTree('#auditionaddressguid','#auditionaddress');"/>
				<div class="select-trigger" onclick="chooseAddressTree('#auditionaddressguid','#auditionaddress');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>&nbsp;</span>
                <label for="isemail">
               		<input id="isemail" name="isemail" type="checkbox" class="checkboxstyle"  checked="true" onclick="checkisEmail(this);" />是否发送邮件
                </label>
			</li>
			<li>
                <label for="ismsg">
               		<input id="ismsg" name="ismsg" type="checkbox" class="checkboxstyle"  checked="true" onclick="checkisEmail(this);" />是否发送短信
                </label>
			</li>
		</ul>
		<ul>
			<li>
				<span>备注：</span>
				<textarea id="modimemo" name="modimemo" rows="3" cols="40" style="width:250px;" class="{maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>



<!-- 体检机构 -->
<div id="examinationWindow" title="体检机构" style="display:none;">
	<form action="" id="examinationForm" class="form">
		<ul>
			<li>
			    <span><em class="red">* </em>体检机构：</span>
			    <input id="thirdpartnerguid" name="thirdpartnerguid" type="hidden" value=""/>
                <input id="thirdpartnername" name="thirdpartnername" class="{required:true} inputselectstyle" onclick="chooseAllPartnerTree('#thirdpartnerguid','#thirdpartnername');"/>
                <div class="select-trigger" onclick="chooseAllPartnerTree('#thirdpartnerguid','#thirdpartnername');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>&nbsp;</span>
                <label for="isemails">
               		<input id="isemails" name="isemails" type="checkbox" class="checkboxstyle"  checked="true" onclick="checkisEmail(this);" />是否发送邮件
                </label>
			</li>
			<li>
                <label for="ismsgs">
               		<input id="ismsgs" name="ismsgs" type="checkbox" class="checkboxstyle"  checked="true" onclick="checkisEmail(this);" />是否发送短信
                </label>
			</li>
		</ul>
	</form>
</div>






<!-- 入职信息 -->
<div id="personWindow" title="入职信息" style="display:none;">
	<form action="person/savePerson.do" id="personForm" class="form" method="post">
		<input type="hidden" id="mycandidatesguid" name="mycandidatesguid" value=""/>
		<input type="hidden" id="modiuser" name="modiuser" value=""/>
		<input type="hidden" id="moditimestamp" name="moditimestamp" value=""/>
		
		<ul>
			<li>
                <span><em class="red">* </em>公司名称：</span>
                <input id="t_companyid" name="companyid" type="hidden" value=""/>
			    <input id="t_companyname" name="companyname" class="{required:true} inputselectstyle" onclick="chooseMyHasCompanyTree('#t_companyid','#t_companyname',callBackTCompany);"/>
			    <div class="search-trigger" onclick="chooseMyHasCompanyTree('#t_companyid','#t_companyname',callBackTCompany);"/>
            </li>
        </ul>
        <ul>
			<li>
			    <span><em class="red">* </em>部门名称：</span>
			    <input id="t_deptid" name="deptid" type="hidden" value=""/>
			    <input id="t_deptname" name="deptname" class="{required:true} inputselectstyle" onclick="chooseOneCompanyDeptTree('#t_deptid','#t_deptname',$('#t_companyid').val(),callbackTDept);"/>
			    <div class="search-trigger" onclick="chooseOneCompanyDeptTree('#t_deptid','#t_deptname',$('#t_companyid').val(),callbackTDept);"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>岗位名称：</span>
			    <input id="t_postid" name="postid" type="hidden" value=""/>
			    <input id="t_postname" name="postname" class="{required:true} inputselectstyle" onclick="choosePostTree('#t_postid','#t_postname',$('#t_deptid').val());"/>
			    <div class="search-trigger" onclick="choosePostTree('#t_postid','#t_postname',$('#t_deptid').val());"/>
			</li>
        </ul>
        <ul>
	        <li>
			    <span><em class="red">* </em>编制类别：</span>
			    <input id="quotaid" name="quotaid" type="hidden" value=""/>
			    <input id="quotaname" name="quotaname" class="{required:true} inputselectstyle" onclick="chooseQuotaTree('#quotaid','#quotaname',$('#t_postid').val(),callBackQuota);"/>
			    <div class="select-trigger" onclick="chooseQuotaTree('#quotaid','#quotaname',$('#t_postid').val(),callBackQuota);"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>职级：</span>
			     <input id="rankid" name="rankid" type="hidden" value=""/>
			    <input id="rankname" name="rankname" class="{required:true} inputselectstyle" onclick="chooseRankTree('#rankid','#rankname',$('#t_companyid').val());"/>
			    <div class="select-trigger" onclick="chooseRankTree('#rankid','#rankname',$('#t_companyid').val());" />
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
		<ul>
			<li>
			    <span><em class="red">* </em>报到人：</span>
			    <input id="regisuserguid" name="regisuserguid" type="hidden" value=""/>
				<input id="regisusername" name="regisusername" class="{required:true} inputselectstyle" onclick="chooseinterviewerTree('#regisuserguid','#regisusername','');"/>
				<div class="select-trigger" onclick="chooseinterviewerTree('#regisuserguid','#regisusername','');"/>
			</li>
		</ul>
		<ul>
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
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>办公地点：</span>
			    <input id="officeaddress" name="officeaddress" class="{required:true,maxlength:100} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>社保归属地：</span>
			    <input id="social" name="social" class="{required:true,maxlength:50} inputstyle"/>
			</li>
		</ul>
		<ul id="beizhu">
			<li>
				<span>备注：</span>
				<textarea id="memo" name="memo" rows="2" cols="40" style="width:250px;" class="{maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>


<!-- 投递历史 -->
<div id="history" title="" style="display:none;max-height:800px;">
	
</div>


<!-- 选择推荐的数据-->
<div id="recommendWindow" title="推荐信息(加*列必填)" style="display:none;overflow:hidden;">
	
</div>


<div id="mypicwindow" class="chooseTree" style="background:#f8f8f8;border:1px solid #CCCCCC;max-width:200px; max-height:400px;padding:10px;margin: 0 auto;">

</div>



<!-- 评价信息 -->
<div id="addResumeAssessWindow" title="评价信息" style="display:none;">
	<form action="resume/saveOrUpdateResumeAssess.do" method="post" id="addResumeAssessForm" class="form">
		<input id="assessguid" name="assessguid" type="hidden" value=""/>
		<input id="webuserguid" name="webuserguid" type="hidden" value=""/>
		<input id="modiuser" name="modiuser" type="hidden" value=""/>
		<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
		
		<ul>
			<li>
                <span><em class="red">* </em>评价等级：</span>
                <input id="assesslevel" name="assesslevel" type="hidden" value=""/>
			    <input id="assesslevelname" name="assesslevelname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#assesslevel','#assesslevelname','ASSESSLEVEL');"/>
           		 <div class="search-trigger" onclick="chooseOptionTree('#assesslevel','#assesslevelname','ASSESSLEVEL');"/>
            </li>
        </ul>
        <ul>
			<li>
                <span><em class="red">* </em>评价体系：</span>
                <input id="assesshierarchy" name="assesshierarchy" type="hidden" value=""/>
			    <input id="assesshierarchyname" name="assesshierarchyname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#assesshierarchy','#assesshierarchyname','ASSESSHIERARCHY');"/>
           		 <div class="search-trigger" onclick="chooseOptionTree('#assesshierarchy','#assesshierarchyname','ASSESSHIERARCHY');"/>
            </li>
        </ul>
		<ul>
			<li>
				<span><em class="red">* </em>评价结果：</span>
				<textarea id="assessresult" name="assessresult" rows="3" cols="40" style="width:250px;" class="{required:true,maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>


</body>



<!-- 打印样式 -->
<style id="style1">

.table-wrapper{
	padding:5px 10px 10px 10px;
}

table.static-table {
    border:0px;
	width: 100%;
	table-layout: fixed;
	border-top:0px solid #ccc;
	border-right:0px solid #ccc;
}

table.static-table th {
	border-left:0px solid #ccc;
	border-bottom:0px solid #ccc;
	border-right:0px solid #ccc;
	border-top:0px solid #ccc;
	height: 35px;
	font-weight:normal;
	color:#000;
	overflow: hidden;
	white-space: nowrap;
	word-break: keep-all;
	word-wrap: normal;
	text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
}

table.static-table td {
	border-left:0px solid #ccc;
	border-bottom:0px solid #ccc;
	border-right:0px solid #ccc;
	border-top:0px solid #ccc;
	qdisplay: block;
	height: 35px;
	line-height: 35px;
	padding:0px 5px;
	overflow: hidden;
	white-space: nowrap;
	word-break: keep-all;
	word-wrap: normal;
	text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
}

table.static-table tbody tr:HOVER {
	background:#f8f8f8;
}
.printhiden,.resumeHistorys{margin-left: 10px;}
.resumeBody, .printhiden ,.resumeHistorys{color: #555555;font-size: 12px;}
.resumeBody strong, .resumeBody h1, .resumeBody h5, .resumeBody h6,.printhiden h5,.printhiden strong, .printhiden h1,.printhiden h6{color: #555555;font-size:14px;padding:0px;margin:0px;}
.resumeBody h1 {font-size: 32px;height: 42px;line-height: 1em;}
.resumeBody .headerImg {border: 1px solid #DDDDDD;padding: 5px;}
.resumeBody .ver-line {color: #555555;font-size: 14px;padding: 0 5px;}
.resumeBody h5, .resumeBody h6, .resumeBody strong,.printhiden h5, .printhiden h6, .printhiden strong  {font-weight: bold;}
.resumeBody .details dt h5, .printhiden .details dt h5,.resumeHistorys dt h5{color: #315AAA;font-size: 14px;height: 26px;line-height: 26px;text-indent: 20px;}
.resumeBody .summary, .resumeBody .details dd, .printhiden .details dd {line-height: 25px;;margin: 10px;padding-left: 10px;}
.resumeBody .certificates, .resumeBody .training, .resumeBody .graduates, .resumeBody .social, .resumeBody .education-background, .resumeBody .project-experience, .resumeBody .work-experience,.printhiden .training {border-bottom: 1px dotted #CCCCCC;margin-bottom: 10px;padding-bottom: 5px;}
.resumeBody .certificates:last-child, .resumeBody .training:last-child, .resumeBody .graduates:last-child, .resumeBody .education-background:last-child, .resumeBody .project-experience:last-child, .resumeBody .work-experience:last-child {border-bottom: 0 none;}
.resumeBody .training h6 {margin-bottom: 10px;}
.resumeBody .em {}

.training .work-experience .project-experience .education-background{font-size: 28px;font-family:'KaiTi_GB2312';}
</style> 


</html>
