<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>校招筛选</title>
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

<link rel="stylesheet" type="text/css" href="plugin/swfupload/css/default.css"/>

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

<script type="text/javascript" src="plugin/swfupload/js/swfupload.js"></script>
<script type="text/javascript" src="plugin/swfupload/js/swfupload.swfobject.js"></script>
<script type="text/javascript" src="plugin/swfupload/js/swfupload.queue.js"></script>
<script type="text/javascript" src="plugin/swfupload/js/fileprogress.js"></script>
<script type="text/javascript" src="plugin/swfupload/js/handlers.js"></script>

<script type="text/javascript" src="pages/tree.js"></script>
<script type="text/javascript" src="pages/resume/uploadlist.js"></script>

<script type="text/javascript">
var recommend='${recommend}';
var recruitpostguid='${param.recruitpostguid}';
var today='${param.today}';

$(document).ready(function () {
    //列表加载
    loadGrid();
    
    initManyUpload('${baseUrl }');
    
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
    
    $("body").bind('mouseover',function(){
    	$("#resumeWindow").hide();
    });
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




//操作推荐
var matchForm=null;
function openMacthWindow(index,mycandidatesid,state,single,webuserguid){
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
			openRecMacth(index,array.toString());
			
			//openMacth(index,mycandidatesid,state,array);
		}
	});
	
}




//
function openRecMacth(index,mycandidatesguids){
	var y=150;
	if(index!=null){
		var cityOffset = $("tr[__gt_ds_index__="+index+"]").offset();
		y=cityOffset.top;
	}
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;
	$("#recommendWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:750,
		height:530,
		position:[x,y],
		open:function(){
			var url='mycandidates.do?page=list_recommend&mycandidatesguid='+mycandidatesguids;
			var htm='<iframe id="myRecommend" name="myRecommend" width="100%" height="100%" frameborder="0" src="'+url+'" scrolling="no" ></iframe>';
			$("#recommendWindow").html(htm);
		},
		close:function(){
			$("#recommendWindow").html(null);
		}
	});
	
}








function openMacth(index,mycandidatesid,state,array){
	var y=150;
	if(index!=null){
		var cityOffset = $("tr[__gt_ds_index__="+index+"]").offset();
		y=cityOffset.top;
	}
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;
	$("#matchWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		position:[x,y],
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
			$("#state").val(1);
			
			//默认是发送短信和邮件
			$("input[name='isemail']").attr("checked", true);
			$("input[name='ismsg']").attr("checked", true);
			
			$("#isemail").val(1);
			$("#ismsg").val(1);
			
			$("#matchForm #arrayList").val(array.toString());
				
			//校验
			if(matchForm==null)
				matchForm=$("#matchForm").validate({submitHandler:function(form) {
				    	$(form).ajaxSubmit(function(data) {
				    		alert("操作成功!");
				    		mygrid.reload();
				    		$("#matchWindow").dialog("close");
				        });
					}
				});
		}
	});
}



















//忽略
function updateCandidatesstate(mycandidatesid,state){
	if(state!=1){
		var prompt=(state==0?"忽略":"黑名单");
		if(!confirm("确认要"+prompt+"选中数据吗？")){
			return;
		}
	}
	
	$.getJSON("mycandidates/updateMyCandidatesByState.do", {mycandidatesguid:mycandidatesid,state:state}, function(data) {
		alert("操作成功！");
		mygrid.reload();
	});
}





























//评价信息窗口
var addResumeAssessForm=null;
function openResumeAssess(index,id){
	var y=100;
	if(index!=null){
		var cityOffset = $("tr[__gt_ds_index__="+index+"]").offset();
		y=cityOffset.top;
	}
	var $g = $(document.body);
	var x=$g.width() / 2 - 480/2;
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



//发送短信
function sendMsg(){
	var array=new Array();
	var namearray=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].mobile);
		namearray.push(cords[i].name);
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



//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.name=$("#name").val();
	pam.nativeplace=$("#nativeplace").val();
	pam.culture=$("#culture").val();
	pam.workage=$("#workage").val();
	pam.candidatesstate=$("#candidatesstate").val();
	pam.candidatestype=$("#candidatestype").val();
	pam.num=1;
	pam.posttype=2;
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
	pam.recruitpostguid=recruitpostguid;
	if(today!=null&&today!=''){
		pam.stoday=today+" 01:00:00";
		pam.etoday=today+" 23:59:59";
	}
}









//打勾是否拉入黑名单
function checkisRelease(el){
	var state=$(el).attr("checked");
	$(el).val(state?-1:0);
}



//打勾发送邮件
function checkisEmail(el){
	var state=$(el).attr("checked");
	$(el).val(state?1:0);
}

//打勾发送短信
function checkisMsg(el){
	var state=$(el).attr("checked");
	$(el).val(state?1:0);
}


//忽略
var loseForm=null;
function loseCandidatesstate(mycandidatesid){
	$("#loseWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		buttons: {
			"确定": function() {
				if(loseForm.form){
					var state=$("#isstate").val();
					updateCandidatesstate(mycandidatesid,state)
					$("#loseWindow").dialog("close");
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#loseForm").clearForm();
			$("#isstate").val(0);
			//校验
			if(loseForm==null)
				loseForm=$("#loseForm").validate();
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


//用户回调
function callbackUser(){
	var id=$("#userguid").val();
	if(id!=null&&id!=''&&id!='null'){
		$.getJSON("employee/getPositionByUserId.do", {id:id}, function(data) {
			if(data!=null){
				$("#recommendcompanyid").val(data.companyid);
				$("#recommendcompanyname").val(data.companyname);
				$("#recommenddeptid").val(data.deptid);
				$("#recommenddeptname").val(data.deptname);
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
		array_mycandidatesguid.push(cords[i].mycandidatesguid);
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
	//检验是否已在推荐后的流程中了
	$.getJSON("mycandidates/checkMyCandidatesByUserGuid.do",{userguid:webuserguid,state:state}, function(data) {
		if(data!=null&&data!=""){
			alert(data);
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
	
}


//简历单个导入
function impHTML(){
	var y=150;
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;
	$("#impHTML").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:500,
		height:150,
		position:[x,y],
		buttons: {
			"确定": function() {
				$("#uploadFile").submit();
				$(this).dialog("close");
		},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			$("#uploadFile").resetForm();
			//加载表单验证
		    $("#uploadFile").validate({debug:true,submitHandler: function(form) {
					$(form).ajaxSubmit(function(data) {
						mygrid.reload();
					});
				}
			});
		}
	});
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
				{name : 'countrecommend'},
				{name : 'countresumeassess'},
				{name : 'countsend'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'candidatesstate' , header: "操作" , width :180,headAlign:'center',align:'left',renderer:function(value,record,colObj,grid,colNo,rowNo){
			//状态(0:不匹配,1:正常,2:匹配推荐,3:未认定,4:认定面试,5:按排面试,6:复试,7:面试通过,8:面试未通过,9:待定,10:待入职,11:已经入职)
				var htm='&nbsp;';
					htm+='<a href="javascript:openResumeAssess('+rowNo+',\''+record.webuserguid+'\')">';
					htm+='评价('+record.countresumeassess+')';
					htm+='</a>';
					htm+='&nbsp;';
					htm+='&nbsp;';
				if(value==0||value==3||value==-1){
					htm+='<a href="javascript:updateCandidatesstate(\''+record.mycandidatesguid+'\',1)">';
					htm+='正常';
					htm+='</a>';
				}else if(value==1){
					htm+='<a href="javascript:updateCandidatesstate(\''+record.mycandidatesguid+'\',-1)">';
					htm+='黑名单';
					htm+='</a>';
					htm+='&nbsp;';
					htm+='&nbsp;';
					htm+='<a href="javascript:updateCandidatesstate(\''+record.mycandidatesguid+'\',0)">';
					htm+='忽略';
					htm+='</a>';
					htm+='&nbsp;';
					htm+='&nbsp;';
					//htm+='<a href="javascript:openMacthWindow(\''+record.mycandidatesguid+'\',2,true,\''+record.webuserguid+'\')">';
					htm+='<a href="javascript:onRecommend('+rowNo+',\''+record.mycandidatesguid+'\',\''+record.webuserguid+'\',2)">';
					htm+='推荐('+record.countrecommend+')';
					htm+='</a>';
				}
				return htm;
			}},
			{id: 'candidatesstatename' , header: "状态" , width :80 ,headAlign:'center',align:'center'},			
			{id: 'name' , header: "姓名" , width :80 ,headAlign:'center',align:'left',renderer:function(value,record,colObj,grid,colNo,rowNo){
				var htm='&nbsp;';
				htm+='<a href="javascript:void(0);" onmouseover="overPerson(this,\''+record.webuserguid+'\');" onmouseout="outPerson();">'+value+'('+record.countsend+')'+'</a>';
				htm+='&nbsp;';
				htm+='&nbsp;';
				return htm;
			}},
			{id: 'sexname' , header: "性别" , width :60 ,headAlign:'center',align:'center'},
			{id: 'postname' , header: "职位名称" , width :160,headAlign:'center',align:'left'},
			{id: 'birthdayname' , header: "年龄" , width :50,headAlign:'center',align:'center',number:true},
			{id: 'culturename' , header: "学历" , width :100 ,headAlign:'center',align:'center'},
			{id: 'workagename' , header: "工作年限" , width :100 ,headAlign:'center',align:'center'},
			{id: 'mobile' , header: "手机号码" , width :90 ,headAlign:'center',align:'center'},
			{id: 'school' , header: "学校" , width :120 ,headAlign:'center',align:'left'},
			{id: 'specialty' , header: "专业" , width :100 ,headAlign:'center',align:'left'},
			{id: 'salary' , header: "期望月薪" , width :80 ,headAlign:'center',align:'right'},
			{id: 'nativeplace' , header: "籍贯" , width :100 ,headAlign:'center',align:'left'},
			//{id: 'candidatestime' , header: "投递时间" , width :120,headAlign:'center',headAlign:'center',align:'center'},
			{id: 'mark' , header: "人才库" , width :50 ,headAlign:'center',align:'center',renderer:function(value,record,colObj,grid,colNo,rowNo){
				var htm='';
				if(value==1){
					htm+='<img src="skins/images/vcard.png" title="已经纳入人才库"/>';
				}
				return htm;
			}},
			{id: 'candidatestypename' , header: "来源渠道" , width :80 ,headAlign:'center',align:'left'}
		];
	var gridOption={
		id : grid_demo_id,
		loadURL :'mycandidates/searchMycandidatesByFilter.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'mycandidates/searchMycandidatesByFilter.do?export=true',
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
		showIndexColumn:true,
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
			if(record.readtype==0){
				return 'style="font-weight:bold;background:#ffffec;"';
			}
		},
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				convertView('mycandidates.do?page=form&id='+record.webuserguid+'&recruitpostguid='+record.recruitpostguid+'&mycandidatesguid='+record.mycandidatesguid+"&resumeeamilguid="+record.resumeeamilguid);
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


</script>

</head>
<body onclick="outPerson();">


<div class="sort">
	<div class="sort-title">
		<h3>校招筛选</h3>
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
					<span id="b4">
						<div id="checkbox" style="float:left;">
							<label for="myrankvalid">
								<input id="myrankvalid" type="checkbox" class="checkboxstyle" onclick="mygrid.reload();" checked="true"/> 只显示正常的信息
							</label>
							&nbsp;
							&nbsp;
						</div>
					</span>
					<a class="btn" href="javascript:showResume();"><i class="icon icon-eye-open"></i><span>查看</span></a>
					<a class="btn" href="javascript:searchGrid();" style="display:${search?'':'none' }"><i class="icon icon-search"></i><span>搜索</span></a>
					<!--  <a class="btn" href="javascript:impHTML();"><i class="icon icon icon-qrcode"></i><span>导入(htm)</span></a>-->
					<a class="btn" href="javascript:convertView('mycandidates.do?page=import');" style="display:${imp?'':'none'}"><i class="icon icon-qrcode"></i><span>导入(xls)</span></a>
					<a class="btn" href="javascript:impHTMLList();" style="display:${imp?'':'none'}><i class="icon icon icon-qrcode"></i><span>批量导入(htm)</span></a>
					<a class="btn" href="javascript:convertView('resume.do?page=recommend/form_2');" style="display:${imp?'':'none'}><i class="icon icon icon-qrcode"></i><span>导入(word)</span></a>
					<a class="btn" href="javascript:expGrid();" style="display:${exp?'':'none' }"><i class="icon icon-list-alt"></i><span>导出</span></a>
					<a class="btn" href="javascript:sendMsg();"><i class="icon icon-envelope"></i><span>发送短信</span></a>
					<a class="btn" href="javascript:openMacthWindow(null,null,2,false,null);" id="recommendButton" style="display:${recommend?'':'none' }"><i class="icon icon-hand-left"></i><span>推荐</span></a>
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
	<form action="" id="searchForm" class="form">
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
				<input id="name" name="name" class=" inputstyle"/>
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
			    <span>籍贯：</span>
			    <input id="nativeplace" name="nativeplace" class="inputstyle"/>
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
		<!--  <ul>
			<li>
				<span>工作地点：</span>
				<input id="workplace" name="workplace" class="inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>居住地：</span>
				<input id="homeplace" name="homeplace" class="inputstyle"/>
			</li>
		</ul>-->
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



<!-- 推荐-->
<div id="matchWindow" title="推荐信息" style="display:none;">
	<form action="mycandidates/saveOrUpdateRecommend.do" method="post" id="matchForm" class="form">
		<input id="recommendguid" name="recommendguid" type="hidden" value=""/>
		<input id="mycandidatesguid" name="mycandidatesguid" type="hidden" value=""/>
		<input id="modiuser" name="modiuser" type="hidden" value=""/>
		<input id="state" name="state" type="hidden" value="1"/>
		<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
		<input id="arrayList" name="arrayList" type="hidden" value=""/>
		<input id="matchstate" name="matchstate" type="hidden" value=""/>
		<ul>
			<li>
			    <span><em class="red">* </em>推荐公司：</span>
			    <input id="recommendcompanyid" name="recommendcompanyid" type="hidden" value=""/>
			    <input id="recommendcompanyname" name="recommendcompanyname" class="{required:true} inputstyle disabled" readonly="true"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>推荐部门：</span>
			    <input id="recommenddeptid" name="recommenddeptid" type="hidden" value=""/>
			    <input id="recommenddeptname" name="recommenddeptname" class="{required:true} inputstyle disabled" readonly="true"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">*</em>推荐到：</span>
				<input id="userguid" name="userguid" type="hidden" value=""/>
				<input id="username" name="username" class="{required:true} inputselectstyle" onclick="chooseinterviewerTree('#userguid','#username',$('#recommenddeptid').val(),callbackUser);"/>
				<div class="select-trigger" onclick="chooseinterviewerTree('#userguid','#username',$('#recommenddeptid').val(),callbackUser);"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">*</em>推荐岗位：</span>
				<input id="recommendpostguid" name="recommendpostguid" type="hidden" value=""/>
				<input id="recommendpostname" name="recommendpostname" class="{required:true} inputselectstyle" onclick="chooseMyPostTree('#recommendpostguid','#recommendpostname',$('#recommenddeptid').val());"/>
				<div class="select-trigger" onclick="chooseMyPostTree('#recommendpostguid','#recommendpostname',$('#recommenddeptid').val());"/>
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
               		<input id="ismsg" name="ismsg" type="checkbox" class="checkboxstyle"  checked="true" onclick="checkisMsg(this);" />是否发送短信
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
	</form>
</div>














<!-- 简历预览 -->
<div id="resumeWindow" class="chooseTree" style="background:#f8f8f8;border:1px solid #CCCCCC;width:400px;padding:10px;">


</div>



<!-- 选择推荐的数据-->
<div id="recommendWindow" title="推荐信息" style="display:none;overflow:hidden;">
	
</div>



<!-- 选择导入HTML -->
<div id="impHTML" style="display:none;" title="选择HTML简历">
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<form id="uploadFile" action="resume/resumeUpload.do" method="post"  style="text-align: center;" enctype="multipart/form-data">
					<input type="hidden" id="table" name="table" value="tmp"/>
				    <input id="filepath" type="file" name="filepath" style="width:100%;height:25px;line-height:25px;padding-left:5px;" size="65"/>
			   	</form>
			</td>
		</tr>
	</table>
</div>





<!-- 批量上传窗口 -->
<div id="uploadManyWindow" style="display:none;background:#eee;" title="选择上传文件（注：仅限于html文件）" >
	<div style="width:100%;">
		<div class="toolbar" style="height:30px;">
			<span class="title">
				<span id="spanButtonPlaceholder" style="width:100px;height:20px;"></span>
			</span>
			<span class="more">
				<input id="btnUpload" type="button" value="上传文件" class="btns" />
				<input id="btnCancel" type="button" value="全部取消" disabled="disabled" class="btns" />
			</span>
		</div>
		<div style="height:250px;overflow-x:hidden;overflow-y:auto;background:#fff;">
			<form id="form1" action="uploadFileList.do" method="post" enctype="multipart/form-data">
				<table id="idFileList" class="upload-table" border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr class="uploadTitle">
							<th>文件名</th>
							<th width="80px">文件大小</th>
							<th width="150px">状态</th>
							<th width="35px">&nbsp;</th>
						</tr>
					</thead>
					<tbody>
					
					</tbody>
				</table>
			</form>
		</div>
		<div class="toolbar">
			等待上传 <span id="idFileListCount">0</span> 个 ，成功上传 <span id="idFileListSuccessUploadCount">0</span> 个
		</div>
		<div id="divSWFUploadUI" style="visibility: hidden;"></div>
		<noscript style="display: block; margin: 10px 25px; padding: 10px 15px;">
			很抱歉，相片上传界面无法载入，请将浏览器设置成支持JavaScript。
		</noscript>
		<div id="divLoadingContent" class="content" style="display: none;">
			上传界面正在载入，请稍后...
		</div>
		<div id="divLongLoading" class="content" style="display: none;">
			上传界面载入失败，请确保浏览器已经开启对JavaScript的支持，并且已经安装可以工作的Flash插件版本。
		</div>
		<div id="divAlternateContent" class="content" style="display: none;">
			很抱歉，相片上传界面无法载入，请安装或者升级您的Flash插件。
			请访问： <a href="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" target="_blank">Adobe网站</a> 获取最新的Flash插件。
		</div>
	</div>
</div>



</body>
</html>