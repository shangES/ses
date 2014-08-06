<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>简历管理</title>
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
<link rel="stylesheet" type="text/css" href="plugin/grid/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/default/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/green/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/mac/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/china/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/vista/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/calendar/calendar-blue.css"  />


<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="skins/js/jquery.ajaxupload.3.6.js"></script>
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
<script type="text/javascript" src="pages/resume/recommend/tree.js"></script>

<script type="text/javascript">
var arrangement='${arrangement}';
var entry='${entry}';
var advise='${advise}';

$(document).ready(function () {
	
    //列表加载
    loadGrid();
    
    
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
  
  	
    
    
    //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});









//导出
function expGrid(){
	var y=150;
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;
	$("#dialog_exp").dialog({
		autoOpen: true,
		modal: true,
		model:true,
		resizable:false,
		width:400,
		height:130,
		position:[x,y],
		buttons: {
			"确定": function() {
				mygrid.exportGrid('xls');
				$(this).dialog("close");
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},
		close: function() {
		}
	});
}







//搜索
var searchForm=null;
function searchGrid(){
	//计算位置
	var y=150;
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;
	
	
	$("#search").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		position:[x,y],
		buttons: {
			"确定":function(){
				if(searchForm.form()){
					mygrid.reload();
					$(this).dialog("close");
				}
			},
			"重置":function(){
				$("#searchForm").clearForm();
			},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			//校验
			if(searchForm==null)
				searchForm=$("#searchForm").validate();
		}
	});
}




//操作招聘状态 推荐   认定
var matchForm=null;
function openMacthWindow(mycandidatesid,state,single,webuserguid){
	var array=new Array();
	var userarray=new Array();
	if(single){
		array.push(mycandidatesid);
		userarray.push(webuserguid);
	}else{
		var cords=mygrid.getSelectedRecords();
		for(var i=0;i<cords.length;i++){
			var obj=cords[i];
			if((state==2&&obj.candidatesstate==1)||(state==4&&obj.candidatesstate==2)){
				if(jQuery.inArray(obj.webuserguid, userarray)!=-1){
					alert("不能同时推荐同一人,请重新推荐!");
					return;
				}
				array.push(obj.mycandidatesguid);
				userarray.push(obj.webuserguid);
			}
		}
	}
	
	if(array.length<=0){
		alert('请选择要操作的数据！');
		return;
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
		buttons: {
			"确定":function(){
				if(matchForm.form()){
					$("#matchForm").submit();
					$(this).dialog("close");
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
			//判断认定可以选择安排面试时间
			if(state==2){
				$("#auditiontimeUL").hide();
				
			}else if(state==4){
				$("#auditiontimeUL").show();
				
				//认定把推荐公司，推荐部门，推荐岗位，推荐人置值
				if(mycandidatesid!=null&&mycandidatesid!=''&&mycandidatesid!='null')
					$.getJSON("mycandidates/getRecommendByCandidatesGuid.do", {id:mycandidatesid}, function(data) {
						if(data!=null){
							for (var key in data) {
						        var el = $('#matchForm #' + key);
						        if(el) el.val(data[key]);
						    }
							//设置默认值
							$("#matchstate").val(state);
							$("#matchForm #arrayList").val(array.toString());
						}
					});
			}
			
			
			//校验
			if(matchForm==null)
				matchForm=$("#matchForm").validate({submitHandler:function(form) {
				    	$(form).ajaxSubmit(function(data) {
				    		alert("操作成功!");
				    		mygrid.reload();
				        });
					}
				});
		}
	});
}






//选择待安排的面试
function onAnPai(){
	//计算位置
	var y=150;
	var $g = $(document.body);
	var x=$g.width() / 2 - 750/2;
	
	$("#anpai").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:750,
		height:530,
		position:[x,y],
		open:function(){
			var url='mycandidates.do?page=list_audition';
			var htm='<iframe id="myAnPai" name="myAnPai" width="100%" height="100%" frameborder="0" src="'+url+'" scrolling="no" ></iframe>';
			$("#anpai").html(htm);
		},
		close:function(){
			$("#anpai").html(null);
		}
	});
}


//批量发布
function onFaBu(){
	//计算位置
	var y=150;
	var $g = $(document.body);
	var x=$g.width() / 2 - 750/2;
	$("#fabu").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:750,
		height:530,
		position:[x,y],
		open:function(){
			var url='mycandidates.do?page=list_release';
			var htm='<iframe id="myFaBu" name="myFaBu" width="100%" height="100%" frameborder="0" src="'+url+'" scrolling="no" ></iframe>';
			$("#fabu").html(htm);
		},
		close:function(){
			$("#fabu").html(null);
		}
	});
}

//直接面试通过
function onInterview(){
	//计算位置
	var y=150;
	var $g = $(document.body);
	var x=$g.width() / 2 - 750/2;
	$("#interview").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:800,
		height:700,
		position:[x,y],
		open:function(){
			var url='resume.do?page=recommend/form_4';
			var htm='<iframe id="myInterview" name="myInterview" width="100%" height="100%" frameborder="0" src="'+url+'" scrolling="no" ></iframe>';
			$("#interview").html(htm);
		},
		close:function(){
			$("#interview").html(null);
		}
	});
}




//操作招聘状态 建议面试
var proposalForm=null;
function openProposalWindow(mycandidatesid,state){
	$("#proposalWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		buttons: {
			"确定":function(){
				if(proposalForm.form()){
					updateCandidatesstate(mycandidatesid,state,null,null);
					$(this).dialog("close");
				}
			},
			"重置":function(){
				$("#proposalWindow").clearForm();
			},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			//校验
			if(proposalForm==null)
				proposalForm=$("#proposalForm").validate();
		}
	});
}



//打勾发送邮件、短信
function checkisEmail(el){
	var state=$(el).attr("checked");
	$(el).val(state?1:0);
}



//取消--忽略
function updateCandidatesstate(mycandidatesid,valid,memo,recommendguid){
	var state=0;
	if(valid==5){
		state=4;	
	}else if(valid==10){
		state=7;
	}else if(valid==14){
		state=12;
	}
	//取消操作
	if(state==4||state==7||state==12){
		if(!confirm('确认要取消选中数据吗？')){
			return;
		}
		//校验是否已被执行结果操作
		$.getJSON("mycandidates/checkMyCandidatesByState.do", {id:mycandidatesid,state:valid}, function(data) {
			if(data==null||data==''){
				alert("此人已在下个流程中,不能取消!");
				return;
			}else{
				
				$.getJSON("mycandidates/updateMyCandidatesByState.do", {mycandidatesguid:mycandidatesid,state:state}, function(data) {
					alert("操作成功！");
					mygrid.reload();
				});
			}
		});
	}else{
		$.getJSON("mycandidates/updateMyCandidatesByState.do", {mycandidatesguid:mycandidatesid,state:valid,memo:memo,recommendguid:recommendguid}, function(data) {
			alert("操作成功！");
			mygrid.reload();
		});
	}
	

}




//忽略
var loseForm=null;
function loseCandidatesstate(mycandidatesid,state,recommendguid,index){
	//计算位置
	var y=100;
	if(index!=null){
		var cityOffset = $("tr[__gt_ds_index__="+index+"]").offset();
		y=cityOffset.top;
	}
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;
	
	
	$("#loseWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		position:[x,y],
		buttons: {
			"确定": function() {
				if(loseForm.form){
					var state=$("#isstate").val();
					var memo=$('#loseForm textarea[id=modimemo]').val();
					updateCandidatesstate(mycandidatesid,state,memo,recommendguid)
					$("#loseWindow").dialog("close");
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#loseForm").clearForm();
			if(state>=7){
				$("#bz").show();
			}else{
				$("#bz").hide();
			}
			$("#isstate").val(1);
			//校验
			if(loseForm==null)
				loseForm=$("#loseForm").validate();
		}
	});
}


//打勾
function checkisRelease(el){
	var state=$(el).attr("checked");
	$(el).val(state?-1:1);
}


//代体检 
function updateBatchCandidatesstate(state,arrayList,isemail,ismsg){
	var thirdpartnerguid=$("#thirdpartnerguid").val();
	$.getJSON("mycandidates/updateBatchCandidatesstate.do", {ids:arrayList,state:state,thirdpartnerguid:thirdpartnerguid,isemail:isemail,ismsg:ismsg}, function(data) {
		alert("操作成功！");
		mygrid.reload();
	});
}






//安排面试窗口
var arrangementForm=null;
function openArrangementWindow(mycandidatesid,state,single,auditiontime,index,recommendguid){
	var array=new Array();
	if(single){
		array.push(mycandidatesid);
		
	}else{
		var cords=mygrid.getSelectedRecords();
		for(var i=0;i<cords.length;i++){
			var obj=cords[i];
			if(state==5&&(obj.candidatesstate==4||obj.candidatesstate==6||obj.candidatesstate==9)){
				array.push(obj.mycandidatesguid);
			}
		}
	}
	
	if(array.length<=0){
		alert('请选择要操作的数据！');
		return;
	}
	
	openWindow(array,auditiontime,state,index,recommendguid);
}






//打开安排面试
function openWindow(mycandidatesid,auditiontime,state,index,recommendguid){
	//计算位置
	var y=100;
	if(index!=null){
		var cityOffset = $("tr[__gt_ds_index__="+index+"]").offset();
		y=cityOffset.top;
	}
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;
	
	
	$("#arrangementWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		position:[x,y],
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
			$("#array").val(mycandidatesid.toString());
			$('#arrangementForm input[id=auditiondate]').val(auditiontime);
			$("#state").val(1);
			$('#arrangementForm input[id=recommendguid]').val(recommendguid);
			
			//默认是发送短信和邮件
			$("input[name='isemail']").attr("checked", true);
			$("input[name='ismsg']").attr("checked", true);
			$("#isemail").val(1);
			$("#ismsg").val(1);
			
			//校验
			if(arrangementForm==null){
				arrangementForm=$("#arrangementForm").validate();
			    $('#arrangementForm').ajaxForm(function(data) {
			    	alert("安排面试成功！");
			    	  mygrid.reload();
			    	$("#arrangementWindow").dialog("close");
			    });
			}
		}
	});
}









//评价信息窗口
var addResumeAssessForm=null;
function openResumeAssess(id,index){
	//计算位置
	var y=100;
	if(index!=null){
		var cityOffset = $("tr[__gt_ds_index__="+index+"]").offset();
		y=cityOffset.top;
	}
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;
	
	$("#addResumeAssessWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		position:[x,y],
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
			    		mygrid.reload();
			    		$("#addResumeAssessWindow").dialog("close");
			        });
				}
			});
		}
	});
}








//选择体检机构
var examinationForm=null;
function openExaminationWindow(mycandidatesid,state,single,index){
	//提示用户没有选择数据  就进行操作
	var array=new Array();
	if(single){
		array.push(mycandidatesid);
	}else{
		var cords=mygrid.getSelectedRecords();
		for(var i=0;i<cords.length;i++){
			var obj=cords[i];
			if(obj.candidatestype!=6&&state==10&&(obj.candidatesstate==7||obj.candidatesstate==13||obj.candidatesstate==16||obj.candidatesstate==17))
				array.push(obj.mycandidatesguid);
		}
	}
	
	
	if(array.length<=0){
		alert('请选择要操作的数据！');
		return;
	}
	
	
	
	//计算位置
	var y=100;
	if(index!=null){
		var cityOffset = $("tr[__gt_ds_index__="+index+"]").offset();
		y=cityOffset.top;
	}
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;
	
	$("#examinationWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		position:[x,y],
		buttons: {
			"确定":function(){
				if(examinationForm.form()){
					var isemail=$("#isemails").attr("checked")?1:0;
					var ismsg=$("#ismsgs").attr("checked")?1:0;
					updateBatchCandidatesstate(state,array.toString(),isemail,ismsg);
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





//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].mycandidatesguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	
	$.post("mycandidates/delMyCandidatesById.do",{ids:array.toString()}, function() {
		mygrid.reload();
    });
}



//体检通过
function onTiJian(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var id=cords[i].mycandidatesguid;
		if(cords[i].candidatesstate==7){
			array.push(id);
		}
	}
	if(array.length<=0){
		alert('请选择要操作的数据！');
		return;
	}
	if(!confirm('确认操作选中数据吗？')){
		return;
	}
	
	$.post("mycandidates/examinationMyCandidatesById.do",{ids:array.toString()}, function() {
		mygrid.reload();
    });
}


//待入职
var personForm=null;
function openQuotaWindow(mycandidatesid,recommendguid,state,webuserguid,index){
	//计算位置
	var y=100;
	if(index!=null){
		var cityOffset = $("tr[__gt_ds_index__="+index+"]").offset();
		y=cityOffset.top;
	}
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;
	
	$.getJSON("resume/getResumeById.do", {id:webuserguid}, function(data) {
		if(data!=null){
	       if(data.sex!=null&&data.sex!=''){
	    	   $("#personWindow").dialog({
	    			autoOpen: true,
	    			modal: true,
	    			resizable:false,
	    			width:480,
	    			position:[x,y],
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
	    				loadQuota(recommendguid);
	    				$('#personForm input[id=mycandidatesguid]').val(mycandidatesid);
	    				personForm=$("#personForm").validate({submitHandler: function(form) {
	    				    	$(form).ajaxSubmit(function(data) {
	    				    		alert("操作成功！");
	    				    		$("#personWindow").dialog("close");
	    				    		mygrid.reload();
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
function loadQuota(recommendguid){
	$.getJSON("mycandidates/getRecommendById.do", {id:recommendguid}, function(data) {
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


//参数设置
var pam=null;
function initPagePam(){
  	pam={};
	pam.expAll=0;
	pam.name=$("#name").val();
	pam.culture=$("#culture").val();
	pam.workage=$("#workage").val();
	pam.candidatestype=$("#candidatestype").val();
	pam.candidatesstate=$("#candidatesstate").val();
	pam.num=2;
	var myvalid=$("#myrankvalid").attr("checked")?1:0;
  	pam.myvalid=myvalid;
  	pam.sex=$("#sex").val();
  	pam.birthday_s=$("#birthday_s").val();
	pam.birthday_e=$("#birthday_e").val();
	pam.joindate_s=$("#joindate_s").val();
	pam.joindate_e=$("#joindate_e").val();
	pam.keyword=$("#keyword").val();
	pam.homeplace=$("#homeplace").val();
	pam.workplace=$("#workplace").val();
	pam.assesslevel=$("#t_assesslevel").val();
	pam.assesshierarchy=$("#t_assesshierarchy").val();
}




function employeeTreeCallback(id){
	$("#maininterviewerguid").val(id);
}



//公司回调
function callBackCompany(){
	$("#recommenddeptid").val(null);
	$("#recommenddeptname").val(null);

	
	//部门选择回调
	callbackDept();
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
function callbackDept(){
	$("#recommendpostguid").val(null);
	$("#recommendpostname").val(null);
	$("#userguid").val(null);
	$("#username").val(null);
}


//部门选择回调
function callbackTDept(){
	$("#t_postid").val(null);
	$("#t_postname").val(null);
	$("#quotaid").val(null);
	$("#quotaname").val(null);
}


function callbackInter(id){
	$("#interviewerguid").val(id);
}


function callbackMain(id){
	$("#maininterviewerguid").val(id);
}





//校验编制是否存在
function callBackQuota(){
	var quotaid=$("#quotaid").val();
	$.getJSON("quota/getQuotaById.do", {id:quotaid}, function(data) {
		if(data!=null){
	       if(data.vacancynumber<=0){
	    	   alert("该编制下已经超编,请重新选择!");
	       }
		}
	});
}

//回车搜索
function formSubmit(){
	mygrid.reload();
	$("#search").dialog("close");
}




//直接录入
var interviewForm=null;
function interview(){
	$("#interviewWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		buttons: {
			"确定": function() {
				if(interviewForm.form){
					$("#interviewForm").submit();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#interviewForm").clearForm();
			interviewForm=$("#interviewForm").validate({submitHandler: function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		alert("操作成功！");
			    		$("#interviewWindow").dialog("close");
			    		mygrid.reload();
			        });
				}
			});
		}
	});
}




//自检(体检成功)
var myExaminationRecordForm=null;
function OnMyRecord(id,recommenid,index){
	//计算位置
	var y=100;
	if(index!=null){
		var cityOffset = $("tr[__gt_ds_index__="+index+"]").offset();
		y=cityOffset.top;
	}
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;

	$("#myExaminationRecordWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		position:[x,y],
		buttons: {
			"确定": function() {
				if(myExaminationRecordForm.form){
					var filepath=$("#filepath").val();
					if(filepath==null||filepath==''||filepath=='null'){
						alert("请上传体检报告!");
						return;
					}else{
						updateRecord(id,filepath,recommenid);
					}
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#myExaminationRecordForm").clearForm();
			initFile();
			//校验
			if(myExaminationRecordForm==null)
				myExaminationRecordForm=$("#myExaminationRecordForm").validate();
		}
	});
}

//自检
function updateRecord(id,filepath,recommenid){
	$.post("mycandidates/examinationMyCandidates.do",{id:id,filepath:filepath,recommenid:recommenid}, function() {
		alert("操作成功!");
		$("#myExaminationRecordWindow").dialog("close");
		mygrid.reload();
    });
}



//上传附件
function initFile(){
    new AjaxUpload('#recordfilepath', {
    	id:'upFile',
    	action: 'uploadFile.do',
    	name:'file1',
    	autoSubmit:true,
    	responseType: 'json',
    	data: {table:'j_examinationrecord'},
    	onComplete: function(filename,obj) {
			$("#recordfilepath").val(filename);
    		$("#filepath").val(obj.filepath);
    	}
   	});
}



//根据姓名加载人员信息
function inputName(){
	var name=$("#t_name").val();
    if(name!=null&&name!=''&&name!='null'){
		$.getJSON("resume/getResumeByName.do",{name:name}, function(data) {
			if(data!=null){
				for(var key in data){
					
					var el = $('#interviewForm #t_' + key);
			        if(el) 
			            el.val(data[key]);
				        
				}			
	        }
		});
    }
}

//回调
function emailTreeCallback(userid){
	if(userid!=null&&userid!=''&&userid!='null'){
		$.getJSON("resume/getResumeByUserId.do", {id:userid}, function(data) {
			if(data!=null&&data!=''){
				for(var key in data){
					var el = $('#interviewForm #t_' + key);
			        if(el) 
			            el.val(data[key]);
				}
			}
		});
	}
}




//切换视图
function convertView(url){
	if ($(".sort").css("display")!="none") {
		$(".sort").hide();
		$("#detail").show();
		
		$("#detail").attr("src",url);
	}else{
		$("#detail").height(0);
		$("#detail").removeAttr("src");
		$(".sort").show();
		
		//计算高度
		_autoHeight();
		if(url==null)
			mygrid.reload();
  }
}




//批量查看
function showResume(){
	var array=new Array();
	var array_name=new Array();
	var array_mycandidatesguid = new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].webuserguid);
		array_name.push(cords[i].name);
		array_mycandidatesguid.push(cords[i].mycandidatesguid)
	}
	
	if(array.length<=0){
		alert('请选择要查看的数据！');
		return;
	}
	
	var ids = array.toString();
	var names = array_name.toString();
	var mycandidatesguids = array_mycandidatesguid.toString();
	if(ids!=null&&ids!=""&&ids!="null"){
		var url='${baseUrl }mycandidates.do?page=list_sum&ids='+ids+'&names='+encodeURI(names)+'&mycandidatesguids='+mycandidatesguids;
		convertView(url);
	}
	
}



//推荐多人
function onRecommend(index,mycandidatesguid,webuserguid,state){
	//检验是否已在面试的流程中了
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
			
			var y=100;
			if(index!=null){
				var cityOffset = $("tr[__gt_ds_index__="+index+"]").offset();
				y=cityOffset.top;
			}
			var $g = $(document.body);
			var x=$g.width() / 2 - 750/2;
			
			
			$("#recommendWindow").dialog({
				autoOpen: true,
				modal: true,
				resizable:false,
				width:750,
				height:530,
				position:[x,y],
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


//关闭推荐表格并刷新当前数据
function closeReload(){
	mygrid.reload();
	$("#recommendWindow").dialog("close");
}

</script>




<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'mycandidatesguid'},
				{name : 'recruitpostguid'},
				{name : 'webuserguid'},
				{name : 'candidatesstate'},
				{name : 'userguid'},
				{name : 'progress'},
				{name : 'candidatestime'},
				{name : 'matchuser'},
				{name : 'matchtime'},
				{name : 'recommendpostguid'},
				{name : 'matchmemo'},
				{name : 'holduser'},
				{name : 'holdtime'},
				{name : 'holdmemo'},
				{name : 'auditiontime'},
				{name : 'candidatestype'},
				{name : 'mark'},
				{name : 'keyword'},
				{name : 'name'},
				{name : 'sex'},
				{name : 'birthday'},
				{name : 'mobile'},
				{name : 'email'},
				{name : 'homeplace'},
				{name : 'workage'},
				{name : 'culture'},
				{name : 'photo'},
				{name : 'modtime'},
				{name : 'rmk'},
				{name : 'resumetypename'},
				{name : 'postname'},
				{name : 'companyname'},
				{name : 'deptid'},
				{name : 'deptname'},
				{name : 'workplacename'},
				{name : 'birthdayname'},
				{name : 'workagename'},
				{name : 'culturename'},
				{name : 'candidatesstatename'},
				{name : 'resumeeamilguid'},
				{name : 'countauditionrecord'},
				{name : 'countresumeassess'},
				{name : 'countsend'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'candidatesstate' , header: "操作" , width :170,headAlign:'center',align:'left',renderer:function(value,record,colObj,grid,colNo,rowNo){
			//状态(0:不匹配,1:正常,2:匹配推荐,3:未认定,4:认定面试,5:按排面试,6:复试,7:面试通过,8:面试未通过,9:待定,10:待入职,11:已经入职)
				var htm='&nbsp;';
					htm+='<a href="javascript:openResumeAssess(\''+record.webuserguid+'\','+rowNo+')">';
					htm+='评价('+record.countresumeassess+')';
					htm+='</a>';
					htm+='&nbsp;';
					htm+='&nbsp;';
					if(value==1){
						htm+='<a href="javascript:onRecommend('+rowNo+',\''+record.mycandidatesguid+'\',\''+record.webuserguid+'\',2)">';
						htm+='推荐('+record.countrecommend+')';
						htm+='</a>';
					}else if(value!=15){
						htm+='<a href="javascript:loseCandidatesstate(\''+record.mycandidatesguid+'\',\''+value+'\',\''+record.recommendguid+'\','+rowNo+')">';
						htm+='忽略';
						htm+='</a>';
						htm+='&nbsp;';
						htm+='&nbsp;';
						if(value==4||value==6||value==9){
							htm+='<a href="javascript:openArrangementWindow(\''+record.mycandidatesguid+'\',5,true,\''+record.auditiontime+'\','+rowNo+',\''+record.recommendguid+'\')">';
							htm+='安排面试('+record.countauditionrecord+')';
							htm+='</a>';
						}else if(value==5||value==10||value==14){
							htm+='<a href="javascript:updateCandidatesstate(\''+record.mycandidatesguid+'\',\''+value+'\',null,null)">';
							htm+='取消';
							htm+='</a>';
						}else if(value==7&&record.candidatestype!=6){
							htm+='<a href="javascript:openExaminationWindow(\''+record.mycandidatesguid+'\',10,true,'+rowNo+')">';
							htm+='待体检';
							htm+='</a>';
							htm+='&nbsp;';
							htm+='&nbsp;';
							htm+='<a href="javascript:OnMyRecord(\''+record.mycandidatesguid+'\',\''+record.recommendguid+'\','+rowNo+')">';
							htm+='自检';
							htm+='</a>';
						}else if(value==12){
							htm+='<a href="javascript:openQuotaWindow(\''+record.mycandidatesguid+'\',\''+record.recommendguid+'\',14,\''+record.webuserguid+'\','+rowNo+')">';
							htm+='待入职';
							htm+='</a>';
						}else if(value==13||value==16||value==17){
							htm+='<a href="javascript:openExaminationWindow(\''+record.mycandidatesguid+'\',10,true,'+rowNo+')">';
							htm+='复查';
							htm+='</a>';
						}
					}
				return htm;
			}},
			{id: 'candidatesstatename' , header: "状态" , width :80 ,headAlign:'center',align:'left'},
			{id: 'name' , header: "姓名" , width :80 ,headAlign:'center',align:'left',renderer:function(value,record,colObj,grid,colNo,rowNo){
				var htm='&nbsp;';
				htm+='<a href="mycandidates.do?page=form_open&id='+record.webuserguid+'&recruitpostguid='+record.recruitpostguid+'&mycandidatesguid='+record.mycandidatesguid+'&resumeeamilguid='+record.resumeeamilguid+'&recommendguid='+record.recommendguid+'" target="_blank" onmouseover="overPerson(this,\''+record.webuserguid+'\');"  onmouseout="outPerson();">'+value+'('+record.countsend+')'+'</a>';
				htm+='&nbsp;';
				htm+='&nbsp;';
				return htm;
			}},
			{id: 'recommendcompanyname' , header: "推荐公司" , width :180,headAlign:'center',align:'left'},
			{id: 'recommenddeptname' , header: "推荐部门" , width :180,headAlign:'center',align:'left'},
			{id: 'recommendpostname' , header: "推荐岗位" , width :180,headAlign:'center',align:'left'},
			{id: 'postname' , header: "职位名称" , width :180,headAlign:'center',align:'left'},
			//{id: 'birthday' , header: "出生日期" , width :90 ,headAlign:'center',align:'center'},
			{id: 'mobile' , header: "手机号码" , width :90 ,headAlign:'center',align:'center'},
			{id: 'birthdayname' , header: "年龄" , width :50,headAlign:'center',align:'center',number:true},
			{id: 'culturename' , header: "学历" , width :100 ,headAlign:'center',align:'center'},
			{id: 'workagename' , header: "工作年限" , width :100 ,headAlign:'center',align:'center'},
			{id: 'candidatestime' , header: "投递时间" , width :120,headAlign:'center',headAlign:'center',align:'center'},
			{id: 'auditiontime' , header: "建议面试时间" , width :150 ,headAlign:'center',align:'center'},
			{id: 'thirdpartnerguidname' , header: "体检机构" , width :90 ,headAlign:'center',align:'center'},
			{id: 'mark' , header: "人才库" , width :50 ,headAlign:'center',align:'center',renderer:function(value,record,colObj,grid,colNo,rowNo){
				var htm='';
				if(value==1){
					htm+='<img src="skins/images/vcard.png" title="已经纳入人才库"/>';
				}
				return htm;
			}},
			{id: 'candidatestypename' , header: "来源渠道" , width :80 ,headAlign:'center',align:'center'}
		];
	var gridOption={
		id : grid_demo_id,
		loadURL :'mycandidates/searchMyCandidatesAndResume.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'mycandidates/searchMyCandidatesAndResume.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=$('input[name="xls"]:checked').val();
			mygrid.parameters=pam;
		},
		exportFileName : '应聘信息表.xls',
		width: "99.8%",//"100%", // 700,
		height: "100%",  //"100%", // 330,
		container : 'gridbox', 
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		stripeRows: false,
		remoteFilter:true,
		showIndexColumn:false,
		selectRowByCheck:true,
		replaceContainer : true,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'nav | pagesize  state',
		pageSize:size,
		skin:getGridSkin(),
		loadResponseHandler:function(response,requestParameter){
			var obj = jQuery.parseJSON(response.text);
			var page=obj.pageInfo;
			var hg=(page.pageSize+1)*33+50;
			
			mygrid.setSize('99.9%',hg);
			pageResize(hg);
		},
		customRowAttribute : function(record,rn,grid){
			if(record.candidatestype==6){
				return 'style="font-weight:bold;background:#ffffec;"';
			}
		},
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				convertView('mycandidates.do?page=form&id='+record.webuserguid+'&recruitpostguid='+record.recruitpostguid+'&mycandidatesguid='+record.mycandidatesguid+'&resumeeamilguid='+record.resumeeamilguid+"&arrangement="+arrangement+"&advise="+advise+"&entry="+true);
			}
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}



function pageResize(height){
	$("#myTable").height(height);
	//计算高度
	_autoHeight();
}


//鼠标悬停
function overPerson(dom,webuserguid){
	var cityObj = $(dom);
	var cityOffset = $(dom).offset();
	
	  $.ajax({
          type:"get",
          async: true,
          url:"resume/getResumeByWebuserGuid.do",
          dataType:"json",
          data:"webuserguid="+webuserguid,
          success:function(data){	
         		    var htm='';
         			htm+='<table class="">';
         			htm+='<tr>';
         			htm+='<td rowspan="5" valign="top" width="110px">';
         			htm+='<img style="max-width:100px;max-height:100px;border:1px solid #CCCCCC;" src="';
         			if(data.resume.photo==null){
         				htm+='skins/images/nopic.jpg';
         			}else{
         				htm+=data.resume.photo;
         			}
         			htm+='">';

         			//评价
         			if(data.assesslists.length>0){
         				if(data.assesslists.length>=2){
         					for(var i=0;i<2;i++){
            					var obj=data.assesslists[i];
            					htm+='<p>';
                     			htm+='评价('+(i+1)+'):'+obj.assessresult.substring(0,15)+'...';
                     			htm+='</p>';
            				}
         				   }else{
            					for(var i=0;i<data.assesslists.length;i++){
                					var obj=data.assesslists[i];
                					htm+='<p>';
                         			htm+='评价('+(i+1)+'):'+obj.assessresult.substring(0,15)+'...';
                         			htm+='</p>';
                				}
            				}
         			}else{
	         				htm+='<p>';
	             			htm+='评价: '+'无';
	             			htm+='</p>';
         			}
         			htm+='</td>';
         			htm+='<td>工作经历:<br>';
         			
                    //工作经历
         			if(data.workexperiences.length>0){
         				if(data.workexperiences.length>=2){
       					  for(var i=0;i<2;i++){
            			   		var obj=data.workexperiences[i];
           					htm+="("+(i+1)+")";
                    			htm+='<strong>'+obj.startdate;
                    			if(obj.enddate==null){
                    				htm+='-至今';
                    			}else{
                    				htm+=' 至  '+obj.enddate;
                    			}
                    			htm+=' |'+ obj.workunit+ '|'+ obj.posation +'<br></strong>'
                    			htm+='<p>';
                    			htm+='<strong>职责描述: </strong>'+obj.jobdescription.substring(0,60)+'...';
                    			htm+='</p>';	
           				}
       					   
       				   }else{
       					  for(var i=0;i<data.workexperiences.length;i++){
             			   		var obj=data.workexperiences[i];
            					htm+="("+(i+1)+")";
                     			htm+='<strong>'+obj.startdate;
                     			if(obj.enddate==null){
                     				htm+='-至今';
                     			}else{
                     				htm+=' 至  '+obj.enddate;
                     			}
                     			htm+=' |'+ obj.workunit+ '|'+ obj.posation +'<br></strong>'
                     			htm+='<p>';
                     			htm+='<strong>职责描述: </strong>'+obj.jobdescription.substring(0,60)+'...';
                     			htm+='</p>';	
            				}
       				   }
         			}else{
	         				htm+='<p>';
	             			htm+='无';
	             			htm+='</p>';	
         			}
                    htm+='</td></tr>';
         			htm+='</table>';
         			
         			var resumeWindow=$("#resumeWindow");
         			resumeWindow.html(htm);
         			
         			//样式
         			resumeWindow.css({"left":cityOffset.left+cityObj.outerWidth() + "px"});
         			if(cityOffset.top+resumeWindow.outerHeight()<=$(document.body).outerHeight()){
         				resumeWindow.css({"top":cityOffset.top + cityObj.outerHeight()+"px"});
         			}else
         				resumeWindow.css({"top":cityOffset.top - resumeWindow.outerHeight()+"px"});
         			resumeWindow.show();
  						
          }
	  });
	
}

//鼠标移开
function outPerson(){
	$("#resumeWindow").hide();
}



// 修改面试官
function onEdit(){
	//计算位置
	var y=150;
	var $g = $(document.body);
	var x=$g.width() / 2 - 750/2;
	
	$("#editMainInterviewer").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:750,
		height:530,
		position:[x,y],
		buttons: {
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			var url='mycandidates.do?page=list_editMainInterviewer';
			var htm='<iframe id="myEditMainInterviewer" name="myEditMainInterviewer" width="100%" height="100%" frameborder="0" src="'+url+'" scrolling="no" ></iframe>';
			$("#editMainInterviewer").html(htm);
		},
		close:function(){
			$("#editMainInterviewer").html(null);
		}
	});
	
}



//发送短信
function sendMsg(){
	/*var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].mobile);
	}
	if(array.length<=0){
		alert('请选择要发送的数据！');
		return;
	}
	if(!confirm('确认要发送短信吗？')){
		return;
	}
	
	//回调发送短信的页面
	window.parent.parent.showNote(array.toString());*/
	var array=new Array();
	var namearray=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].mobile);
		namearray.push(cords[i].name+'('+cords[i].mobile+')');
	}
	if(array.length<=0){
		alert('请选择要发送的数据！');
		return;
	}
	if(!confirm('确认要发送短信吗？')){
		return;
	}
	
	//回调发送短信的页面
	window.parent.parent.showNote(namearray.toString(),array.toString());
}






</script>

</head>
<body onclick="outPerson();">

<div class="sort">
	<div class="sort-title">
		<h3>简历管理</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>
	
	<div class="sort-cont sort-table">
		<div class="table">
			<div class="table-bar">
				<div class="table-title">
					表格名称
				</div>
				<div class="table-ctrl">
					<a class="btn" href="javascript:showResume();"><i class="icon icon-eye-open"></i><span>查看</span></a>
					<a class="btn" href="javascript:sendMsg();"><i class="icon icon-envelope"></i><span>发送短信</span></a>
					<a class="btn" href="javascript:searchGrid();" style="display:${search?'':'none' }"><i class="icon icon-search"></i><span>搜索</span></a>
					<a class="btn" href="javascript:expGrid();" style="display:${exp?'':'none' }"><i class="icon icon-list-alt"></i><span>导出</span></a>
					<a class="btn" href="javascript:onAnPai();"  style="display:${arrangement?'':'none' }"><i class="icon icon-indent-left"></i><span>安排面试</span></a>
					<a class="btn" href="javascript:openExaminationWindow(null,10,false)" id="adviseButton" style="display:${advise?'':'none' }"><i class="icon icon-fire"></i><span>安排体检</span></a>
					<a class="btn" href="javascript:onFaBu();"><i class="icon icon-ok-circle"></i><span>发布</span></a>
					<a class="btn" href="javascript:onTiJian();" style="display:none"><i class="icon icon-ok-circle"></i><span>体检通过</span></a>
					<!--  <a class="btn" href="javascript:onEdit();"><i class="icon icon-pencil"></i><span>邀请反馈</span></a>-->
					<a class="btn" href="javascript:onInterview();" style="display:${interview?'':'none' }"><i class="icon  icon-eye-open"></i><span>面试通过</span></a>
					<a class="btn" href="javascript:remove();" style="display:${del?'':'none' }"><i class="icon icon-remove"></i><span>删除</span></a>
				</div>
			</div>
			<div class="table-wrapper">
				<div id="myTable" style="height:550px;margin:5px auto;">
					<div id="gridbox" ></div>
                </div>
			</div>
		</div>
	</div>
</div>
<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" src="" scrolling="no" style="display:none;"></iframe>



<!-- 搜索 -->
<div id="search" title="搜索条件设置" style="display:none;">
	<form action="javascript:void(0);" id="searchForm" class="form" onkeydown="javascript:if(event.keyCode==13)formSubmit();">
		<ul>
			<li>
				<span>关键字：</span>
				<input id="keyword" name="keyword" class="inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>应聘状态：</span>
				<input id="candidatesstate" name="candidatesstate" type="hidden" value=""/>
				<input id="candidatesstatename" name="candidatesstatename" class="inputselectstyle" onclick="chooseMyMultipleOptionTree('#candidatesstate','#candidatesstatename','CANDIDATESSTATE');"/>
				<div class="select-trigger" onclick="chooseMyMultipleOptionTree('#candidatesstate','#candidatesstatename','CANDIDATESSTATE');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>来源渠道：</span>
				<input id="candidatestype" name="candidatestype" type="hidden" value=""/>
				<input id="candidatestypename" name="candidatestypename" class="inputselectstyle" onclick="chooseMyMultipleOptionTree('#candidatestype','#candidatestypename','RESUMETYPE');"/>
				<div class="select-trigger" onclick="chooseMyMultipleOptionTree('#candidatestype','#candidatestypename','RESUMETYPE');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>姓名：</span>
				<input id="name" name="name" class="{maxlength:25} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span>性别：</span>
			    <input id="sex" name="sex" type="hidden" value=""/>
			    <input id="sexname" name="sexname" class="inputselectstyle" onclick="chooseOptionTree('#sex','#sexname','SEX');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#sex','#sexname','SEX');"/>
			</li>
		</ul>
		<ul>
         	<li>
			    <span>年龄从：</span>
			    <input id="birthday_e" name="birthday_e" class="{digits:true} inputstyle" style="width:97px;"/>
			</li>
            <li>
			    <span style="width:16px;">至&nbsp;</span>
			    <input id="birthday_s" name="birthday_s" class="{digits:true} inputstyle" style="width:97px;"/>
			</li>
        </ul>
         <ul>
         	<li style="display:inline;">
			    <span>投递时间从：</span>
			    <input id="joindate_s" name="joindate_s" class="{dateISO:true} inputselectstyle datepicker" style="width:80px;"/>
			    <div class="date-trigger" onclick="$('#joindate_s').focus();"/>
			</li>
            <li style="display:inline;">
			    <span style="width:16px;">至&nbsp;</span>
			     <input id="joindate_e" name="joindate_e" class="{dateISO:true} inputselectstyle datepicker" style="width:80px;"/>
			    <div class="date-trigger" onclick="$('#joindate_e').focus();"/>
			</li>
        </ul>
		<ul>
			<li>
				<span>学历：</span>
				<input id="culture" name="culture" type="hidden" value=""/>
				<input id="culturename" name="culturename" class="inputselectstyle" onclick="chooseMyMultipleOptionTree('#culture','#culturename','CULTURE');"/>
				<div class="select-trigger" onclick="chooseMyMultipleOptionTree('#culture','#culturename','CULTURE');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>工作年限：</span>
				<input id="workage" name="workage" type="hidden" value=""/>
				<input id="workagename" name="workagename" class="inputselectstyle" onclick="chooseMyMultipleOptionTree('#workage','#workagename','WORKAGE');"/>
				<div class="select-trigger" onclick="chooseMyMultipleOptionTree('#workage','#workagename','WORKAGE');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>工作地点：</span>
				<input id="workplace" name="workplace" class=" inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>居住地：</span>
				<input id="homeplace" name="homeplace" class="inputstyle"/>
			</li>
		</ul>
	</form>
</div>




<!-- 导出 -->
<div id="dialog_exp" style="display:none;" title="数据导出" >
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				&nbsp;
			  	&nbsp;
				&nbsp;
			  	&nbsp;
				<label for="xls1">
					<input id="xls1" type="radio" name="xls" value="0" checked="true" class="checkboxstyle"/>导出本页
				</label>
			  	&nbsp;
			  	&nbsp;
			  	<label for="xls2">
			  		<input id="xls2" type="radio" name="xls" value="1" class="checkboxstyle"/>全部导出
			  	</label>
			</td>
		</tr>
	</table>
</div>




<!-- 推荐、认定-->
<div id="matchWindow" title="推荐、认定信息" style="display:none;">
	<form action="mycandidates/saveOrUpdateRecommend.do" method="post" id="matchForm" class="form">
		<input id="recommendguid" name="recommendguid" type="hidden" value=""/>
		<input id="mycandidatesguid" name="mycandidatesguid" type="hidden" value=""/>
		<input id="modiuser" name="modiuser" type="hidden" value=""/>
		<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
		<input id="arrayList" name="arrayList" type="hidden" value=""/>
		<input id="matchstate" name="matchstate" type="hidden" value=""/>
		<ul>
			<li>
				<span><em class="red">*</em>推荐公司：</span>
				<input id="recommendcompanyid" name="recommendcompanyid" type="hidden" value=""/>
				<input id="recommendcompanyname" name="recommendcompanyname" class="{required:true} inputselectstyle" onclick="chooseMyHasCompanyTree('#recommendcompanyid','#recommendcompanyname',callBackCompany);"/>
				<div class="select-trigger" onclick="chooseMyHasCompanyTree('#recommendcompanyid','#recommendcompanyname',callBackCompany);"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">*</em>推荐部门：</span>
				<input id="recommenddeptid" name="recommenddeptid" type="hidden" value=""/>
				<input id="recommenddeptname" name="recommenddeptname" class="{required:true} inputselectstyle" onclick="chooseOneCompanyDeptTree('#recommenddeptid','#recommenddeptname',$('#recommendcompanyid').val(),callbackDept);"/>
				<div class="select-trigger" onclick="chooseOneCompanyDeptTree('#recommenddeptid','#recommenddeptname',$('#recommendcompanyid').val(),callbackDept);"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">*</em>推荐岗位：</span>
				<input id="recommendpostguid" name="recommendpostguid" type="hidden" value=""/>
				<input id="recommendpostname" name="recommendpostname" class="{required:true} inputselectstyle" onclick="choosePostTree('#recommendpostguid','#recommendpostname',$('#recommenddeptid').val());"/>
				<div class="select-trigger" onclick="choosePostTree('#recommendpostguid','#recommendpostname',$('#recommenddeptid').val());"/>
			</li>
		</ul>
		<ul>
			<li id="tuijian">
				<span><em class="red">*</em>推荐人：</span>
				<input id="userguid" name="userguid" type="hidden" value=""/>
				<input id="username" name="username" class="{required:true} inputselectstyle" onclick="chooseinterviewerTree('#userguid','#username',$('#recommenddeptid').val());"/>
				<div class="select-trigger" onclick="chooseinterviewerTree('#userguid','#username',$('#recommenddeptid').val());"/>
			</li>
		</ul>
		<ul id="auditiontimeUL" style="display:none">
			<li>
				<span>建议安排时间：</span>
				<input id="auditiontime" name="auditiontime" class="inputselectstyle timepicker"/>
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







<!-- 安排面试 -->
<div id="arrangementWindow" title="安排面试" style="display:none;">
	<form action="audition/saveOrUpateAuditionRecord.do" method="post" id="arrangementForm" class="form">
		<input id="auditionrecordguid" name="auditionrecordguid" type="hidden" value=""/>
		<input id="mycandidatesguid" name="mycandidatesguid" type="hidden" value=""/>
		<input id="userguid" name="userguid" type="hidden" value=""/>
		<input id="state" name="state" type="hidden" value="1"/>
		<input id="modiuser" name="modiuser" type="hidden" value=""/>
		<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
		<input id="array" name="array" type="hidden" value=""/>
		<input id="recommendguid" name="recommendguid" type="hidden" value=""/>
		
		<ul>
			<li id="employeeids">
				<span>&nbsp;</span>
			</li>
		</ul>
		<ul>
			<li>
				<span>面试官(抄送)：</span>
				<input id="interviewerguid" name="interviewerguid" type="hidden" value=""/>
				<input id="interviewerguidname" name="interviewerguidname" title="" class="inputselectstyle" onclick="choosemaininterviewerTree('#interviewerguid','#interviewerguidname');"/>
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








<!-- 面试结果窗口 -->
<div id="addAuditionResultWindow" title="面试结果信息" style="display:none;">
	<form action="audition/saveResultByMyCandidatesGuid.do" id="addAuditionResultForm" class="form" method="post">
		<input type="hidden" id="auditionrecordguid" name="auditionrecordguid" value=""/>
		<input type="hidden" id="mycandidatesguid" name="mycandidatesguid" value=""/>
		<input type="hidden" id="moditimestamp" name="moditimestamp"/>
		<input id="modiuser" name="modiuser" type="hidden" value="" />
		<input id="modimemo" name="modimemo" type="hidden" value="" />
		<ul style="display:none">
			<li>
                <span>&nbsp;</span>
                <label for="isrelease">
               		<input id="isrelease" name="isrelease" type="checkbox" class="checkboxstyle" onclick="checkisRelease(this);" />是否发布
                </label>
            </li>
        </ul>
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
				<textarea id="resultcontent" name="resultcontent"  rows="5" cols="40" class="{required:true,maxlength:100} areastyle"/></textarea>
			</li>
		</ul>
	</form>
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
			    <input id="assesslevelname" name="assesslevelname" class="{required:true,maxlength:4} inputselectstyle" onclick="chooseOptionTree('#assesslevel','#assesslevelname','ASSESSLEVEL');"/>
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



<!-- 忽略窗口 -->
<div id="loseWindow" title="忽略信息" style="display:none;">
	<form action="" id="loseForm" class="form" method="post">
		<ul>
			<li>
                <span>&nbsp;</span>
                <label for="isstate">
               		<input id="isstate" name="isstate" type="checkbox" class="checkboxstyle" onclick="checkisRelease(this);" />是否加入黑名单
                </label>
            </li>
        </ul>
        <ul id="bz" style="display:none">
			<li>
				<span>忽略原因：</span>
				<textarea id="modimemo" name="modimemo" rows="3" cols="40" style="width:250px;" class="areastyle"></textarea>
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





<!-- 选择待安排的面试-->
<div id="anpai" title="选择待安排的面试" style="display:none;overflow:hidden;">
	
</div>

<!-- 选择发布的数据-->
<div id="fabu" title="选择待发布的面试" style="display:none;overflow:hidden;">
	
</div>

<!-- 选择待安排的面试-->
<div id="interview" title="直接面试通过" style="display:none;overflow:hidden;">
	
</div>


<!-- 选择直接录用的数据-->
<div id="interviewWindow" title="录用人员信息" style="display:none;">
	<form action="resume/saveOrUpdateMycandidatesByIntervie.do" id="interviewForm"  method="post" class="form">
		<input type="hidden" id="t_recommenduserguid" name="recommenduserguid" />
		<input id="t_webuserguid" name="webuserguid" type="hidden" value=""/>
		<input type="hidden" id="t_mark" name="mark" value=""/>
		<input id="t_keyword" name="keyword" type="hidden" value=""/>
		<input id="t_photo" name="photo" type="hidden" value=""/>
		<input id="t_modtime" name="modtime" type="hidden" value=""/>
		<input id="t_createtime" name="createtime" type="hidden" value=""/>
		<input type="hidden" id="t_recruitpostguid" name="recruitpostguid" value=""/>
		<input id="t_resumefilename" name="resumefilename" type="hidden" />
		<input id="t_resumefilePath" name="resumefilePath" type="hidden" />
		<ul>
		    <li>
           		<span><em class="red">* </em>姓名：</span>
			    <input id="t_name" name="name" class="{required:true,maxlength:25} inputstyle" onblur="inputName();"/>
           	</li>
      	</ul>
      	<ul>
			<li>
			    <span><em class="red">* </em>邮箱：</span>
			    <input id="t_email" name="email" class="{required:true,maxlength:25,email:true} inputselectstyle" onclick="chooseemailTree('#t_email');" />
			    <div class="search-trigger" onclick="chooseemailTree('#t_email');" />
		    </li>
       	</ul>
      	<ul>
       		<li>
			    <span><em class="red">* </em>性别：</span>
			    <input id="t_sex" name="sex" type="hidden" value=""/>
			    <input id="t_sexname" name="sexname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#t_sex','#t_sexname','SEX');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#t_sex','#t_sexname','SEX');"/>
			</li>
		</ul>	
		<ul>
			<li>
			    <span><em class="red">* </em>出生日期：</span>
			    <input id="t_birthday" name="birthday" class="{required:true,dateISO:true} inputstyle inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#t_birthday').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>学历情况：</span>
			    <input id="t_culture" name="culture" type="hidden" value=""/>
			    <input id="t_culturename" name="culturename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#t_culture','#t_culturename','CULTURE');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#t_culture','#t_culturename','CULTURE');"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>手机：</span>
			    <input id="t_mobile" name="mobile" class="{required:true,maxlength:25} inputstyle"/>
			</li>
		</ul>
		<ul>
           	<li>
			    <span><em class="red">* </em>工作年限：</span>
			    <input id="t_workage" name="workage" type="hidden" value=""/>
			    <input id="t_workagename" name="workagename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#t_workage','#t_workagename','WORKAGE');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#t_workage','#t_workagename','WORKAGE');"/>
			</li>
       	</ul>
        <ul>
          	<li>
			    <span><em class="red">* </em>家庭地址：</span>
			    <input id="t_homeplace" name="homeplace" class="{maxlength:50,required:true} inputstyle"/>
			</li>
       </ul>
       <ul>
			<li>
				<span>备注：</span>
				<textarea id="t_modimemo" name="modimemo" rows="3" cols="40" style="width:250px;" class="areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>




<!-- 简历预览 -->
<div id="resumeWindow" class="chooseTree" style="background:#f8f8f8;border:1px solid #CCCCCC;width:400px;padding:10px;">


</div>



<!-- 邀请他人来反馈面试结果-->
<div id="editMainInterviewer" title="邀请他人来反馈面试结果" style="display:none;overflow:hidden;">
	
</div>


<!-- 选择推荐的数据-->
<div id="recommendWindow" title="推荐信息(加*列必填)" style="display:none;overflow:hidden;">
	
</div>


<!-- 体检报告 -->
<div id="myExaminationRecordWindow" title="上传体检报告" style="display:none;">
	<form action="" id="myExaminationRecordForm"  method="post" class="form">
		<input id="filepath" name="filepath" type="hidden" value="">
		<ul>
            <li>
                <span><em class="red">* </em>体检报告：</span>
                <input id="recordfilepath" name="recordfilepath"  class="{required:true} inputstyle" />
            </li>
		</ul>
	</form>
</div>



</body>
</html>