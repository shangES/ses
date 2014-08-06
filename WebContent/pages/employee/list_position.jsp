<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>员工兼任</title>
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
<script type="text/javascript" src="skins/js/jquery.layout.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>

<script type="text/javascript" src="plugin/grid/calendar/calendar.js"></script>
<script type="text/javascript" src="plugin/grid/calendar/calendar-en.js"></script>
<script type="text/javascript" src="plugin/grid/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="plugin/grid/gt_grid_all.js"></script>
<script type="text/javascript" src="plugin/grid/gt_msg_cn.js"></script>

<script type="text/javascript" src="pages/tree.js"></script>

<script type="text/javascript">
var pageState=false;

$(document).ready(function () {
	//加载布局
	$('body').layout({
        north: {size:"55",resizable:false,spacing_open:0,spacing_closed:0}
    });
    
	
    //列表加载
    loadGrid();
    
    
    //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd'
    });
    
});





//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.postionguid);
	}
	
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
 	$.post("employee/delPositionById.do",{ids:array.toString()}, function() {
		mygrid.reload();
  	});
}







//表单
var myForm=null;

function openForm(tid){
	$("#formWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		buttons: {
			"确定":function(){
				$('#myForm').submit();
			},
			"重置":function(){
				//重置表单
				resetMyForm();
			},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			//重置表单
			resetMyForm();
			 //加载表单验证
			if(myForm==null){
			    $("#myForm").validate();
			    $('#myForm').ajaxForm(function(data) {
			    	if(data!=null&&data!=''){
			    		alert(data);
			    		return;
			    	}
			    	alert("保存成功！");
			    	$("#formWindow").dialog("close");
			        mygrid.reload();
			        pageState=true;
			    });
			}
			 
			//加载数据
			loadData(tid);
		}
	});
}

//重置表单
function resetMyForm(){
	$("#myForm").clearForm();
	$("#isstaff").val(0);
	$("#state").val(1);
	$("#modiuser").val("${username }");
	$("#employeeid").val("${param.id}");
}


//打勾
function checkisStaff(el){
	var state=$(el).attr("checked");
	$(el).val(state?1:0);
	
	if(state){
		$("#myQuota").show();
	}else{
		$("#myQuota").hide();
	}
}



//页面状态
function getPageState(){
	return pageState;
}










//取数据
function loadData(tid) {
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("employee/getPositionById.do", {id:tid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#myForm #' + key);
			        if(el)el.val(data[key]);
			    }
			}
		});
}


//公司回调
function callBackCompany(){
	$("#deptid").val(null);
	$("#deptname").val(null);
	
	
	$("#jobid").val(null);
	$("#jobname").val(null);
	
	//部门选择回调
	callbackDept();
}


//部门选择回调
function callbackDept(){
	$("#postid").val(null);
	$("#postname").val(null);
}







//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.userguid='${userid}';
	pam.employeeid="${param.id}";
	pam.isstaff=0;
}

</script>




<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'postionguid'},
				{name : 'employeeid'},
				{name : 'companyid'},
				{name : 'deptid'},
				{name : 'postid'},
				{name : 'jobid'},
				{name : 'quotaid'},
				{name : 'isstaff'},
				{name : 'startdate'},
				{name : 'enddate'},
				{name : 'state'},
				{name : 'memo'},
				{name : 'modiuser'},
				{name : 'moditimestamp'},
				{name : 'modimemo'},
				{name : 'companyname'},
				{name : 'deptname'},
				{name : 'postname'},
				{name : 'jobname'},
				{name : 'quotaname'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'isstaffname' , header: "编制", width :60 ,headAlign:'center',align:'center'},
			{id: 'companyname' , header: "公司名称" , width :250 ,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门名称" , width :300 ,headAlign:'center',align:'left'},
			{id: 'jobname' , header: "职务名称" , width :120 ,headAlign:'center',align:'left'},
			{id: 'postname' , header: "岗位名称" , width :120 ,headAlign:'center',align:'left'},
			{id: 'quotaname' , header: "岗位编制" , width :80 ,headAlign:'center',align:'left'},
			{id: 'startdate' , header: "开始时间" , width :90 ,headAlign:'center',align:'center'},
			{id: 'enddate' , header: "结束时间" , width :90 ,headAlign:'center',align:'center'},
			{id: 'memo' , header: "备注" , width :200 ,headAlign:'center',headAlign:'left',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'employee/searchPosition.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		width: "99.5%",//"100%", // 700,
		height: "98.5%",  //"100%", // 330,
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
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				//修改
				openForm(record.postionguid);
			}
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}
</script>

</head>
<body style="width:100%;height:450px;">
<div class="ui-layout-north" style="border:0px;">
	<div class="table-bar">
		<div class="table-title">
			员工兼任列表
		</div>
		<div class="table-ctrl">
			<a class="btn" href="javascript:openForm(null);"><i class="icon icon-plus"></i><span>新增</span></a>
			<a class="btn" href="javascript:remove();"><i class="icon icon-trash"></i><span>删除</span></a>
		</div>
	</div>
</div>
	
<div class="ui-layout-center" style="overflow:hidden;border:0px;">
	<div id="gridbox" ></div>
</div>









<!-- 表单 -->
<div id="formWindow" title="员工兼任信息" style="display:none;">
	<form action="employee/saveOrUpdatePosition.do" method="post" id="myForm" class="form">
		<input id="employeeid" name="employeeid" type="hidden" value=""/>
		<input id="postionguid" name="postionguid" type="hidden" value=""/>
		<input id="quotaid" name="quotaid" type="hidden" value=""/>
		<input id="rankid" name="rankid" type="hidden" value=""/>
		<input id="isstaff" name="isstaff" type="hidden" value="0"/>
		<input id="state" name="state" type="hidden" value="1"/>
		<input id="enddate" name="enddate" type="hidden" value=""/>
		<input id="modiuser" name="modiuser" type="hidden" value="${userid }"/>
		<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
		<input id="modimemo" name="modimemo" type="hidden" value=""/>
		<ul id="myCompany">
			<li>
                <span><em class="red">* </em>公司名称：</span>
                <input id="companyid" name="companyid" type="hidden" value=""/>
			    <input id="companyname" name="companyname" class="{required:true} inputselectstyle" onclick="chooseAllCompanyTree('#companyid','#companyname',callBackCompany);"/>
			    <div class="search-trigger" onclick="chooseAllCompanyTree('#companyid','#companyname',callBackCompany);"/>
            </li>
        </ul>
        <ul>
			<li>
			    <span>部门名称：</span>
			    <input id="deptid" name="deptid" type="hidden" value=""/>
			    <input id="deptname" name="deptname" class="inputselectstyle" onclick="chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val(),callbackDept);"/>
			    <div class="search-trigger" onclick="chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val(),callbackDept);"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span>职务名称：</span>
			    <input id="jobid" name="jobid" type="hidden" value=""/>
			    <input id="jobname" name="jobname" class="inputselectstyle" onclick="chooseJobTree('#jobid','#jobname',$('#companyid').val());"/>
			    <div class="search-trigger" onclick="chooseJobTree('#jobid','#jobname',$('#companyid').val());"/>
			</li>
        </ul>
        <ul>
			<li>
				<span>岗位名称：</span>
                <input id="postid" name="postid" type="hidden" value=""/>
			    <input id="postname" name="postname" class="inputselectstyle" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
			    <div class="search-trigger" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
            </li>
        </ul>
        <ul>
			<li>
			    <span><em class="red">* </em>开始时间：</span>
			    <input id="startdate" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#startdate').focus();"/>
			</li>
		</ul>
        <ul>
			<li>
			    <span>备注：</span>
			    <textarea id="memo" name="memo"  rows="3" cols="40" class="{maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>

