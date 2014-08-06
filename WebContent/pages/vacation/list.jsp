<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>请假管理</title>
<base href="${baseUrl }"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" href="skins/css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="skins/css/form.css" type="text/css" media="all" />
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

$(document).ready(function () {
	
    //列表加载
    loadGrid();
    
    
  //日期
   $(".datepicker").datepicker({
       dateFormat: 'yy-mm-dd'
   });
    
    
    //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});









//导出
function expGrid(){
	$("#dialog_exp").dialog({
		autoOpen: true,
		modal: true,
		model:true,
		resizable:false,
		width:400,
		height:130,
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







//部门选择回调
function callbackDept(){
	$("#postid").val(null);
	$("#postname").val(null);
}











//公司回调
function callBackCompany(){
	$("#deptid").val(null);
	$("#deptname").val(null);
	
	//部门选择回调
	callbackDept();
}




//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.vacationid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
 	$.post("vacation/delVacation.do",{ids:array.toString()}, function() {
		mygrid.reload();
  	});
}











//搜索
var searchForm=null;
function searchGrid(){
	$("#search").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
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









//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.userguid='${userid}';
	pam.companyid=$("#companyid").val();
	pam.deptid=$("#deptid").val();
	pam.postid=$("#postid").val();
	pam.name=$("#name").val();
	pam.vacationtype=$("#vacationtype").val();
	pam.startdate=$("#startdate").val();
	pam.enddate=$("#enddate").val();
	pam.annualyear=$("#annualyear").val();
}








//员工树选择回调
function employeeTreeCallback(id){
	$.getJSON("employee/getEmployeeById.do", {id:id}, function(data) {
		if(data!=null){
			$("#name").val(data.name);
		}
		});
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








</script>




<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'vacationid'},
				{name : 'employeeid'},
				{name : 'reason'},
				{name : 'vacationtype'},
				{name : 'startdate'},
				{name : 'datenumber'},
				{name : 'enddate'},
				{name : 'memo'},
				{name : 'modiuser'},
				{name : 'moditimestamp'},
				{name : 'modimemo'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'annualyear' , header: "年度" , width :60,headAlign:'center',align:'center'},
			{id: 'companyname' , header: "公司名称" , width :200,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门名称" , width :150,headAlign:'center',align:'left'},
			{id: 'postname' , header: "岗位名称" , width :120,headAlign:'center',align:'left'},
			{id: 'jobnumber' , header: "员工工号" , width :140,headAlign:'center',align:'left'},
			{id: 'name' , header: "请假人" , width :80,headAlign:'center',align:'left'},
			{id: 'cardnumber' , header: "身份证" , width :180,headAlign:'center',align:'left',hidden:true},
			{id: 'vacationtypename' , header: "请假类型" , width :80,headAlign:'center',align:'center'},
			{id: 'datenumber' , header: "请假天数" , width :80,headAlign:'center',align:'right'},
			{id: 'startdate' , header: "请假开始时间" , width :120 ,headAlign:'center',align:'center'},
			{id: 'enddate' , header: "请假结束时间" , width :120 ,headAlign:'center',align:'center'},
			{id: 'reason' , header: "请假原因" , width :150,headAlign:'center',align:'left'},
			{id: 'memo' , header: "备注" , width :200,headAlign:'left',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'vacation/searchVacation.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'vacation/searchVacation.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=$('input[name="xls"]:checked').val();
			mygrid.parameters=pam;
		},
		exportFileName : '请假信息表.xls',
		width: "99.8%",//"100%", // 700,
		height: "100%",  //"100%", // 330,
		container : 'gridbox', 
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		stripeRows: false,
		remoteFilter:true,
		showIndexColumn:true,
		selectRowByCheck:true,
		replaceContainer : true,
		showGridMenu : true,
		allowFreeze	: true ,
		allowHide	: true ,
		allowGroup	: true ,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'nav | pagesize  state',
		pageSize:size,
		skin:getGridSkin(),
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				convertView('vacation.do?page=form&id='+record.vacationid+'&edit=${edit}');
			}
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}

</script>

</head>
<body>

<div class="sort">
	<div class="sort-title">
		<h3>请假管理</h3>
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
					<a class="btn" href="javascript:searchGrid();" style="display:${search?'':'none'}"><i class="icon icon-search"></i><span>搜索</span></a>
					<a class="btn" href="javascript:convertView('vacation.do?page=import');" style="display:${imp?'':'none'}"><i class="icon icon-qrcode"></i><span>导入</span></a>
					<a class="btn" href="javascript:expGrid();" style="display:${exp?'':'none'}"><i class="icon icon-download" ></i><span>导出</span></a>
					<a class="btn" href="javascript:convertView('vacation.do?page=form&edit=true');" style="display:${add?'':'none'}"><i class="icon icon-plus"></i><span>新增</span></a>
					<a class="btn" href="javascript:remove();" style="display:${del?'':'none'}"><i class="icon icon-remove" ></i><span>删除</span></a>
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
		<ul id="myCompany">
			<li>
                <span>公司名称：</span>
                <input id="companyid" name="companyid" type="hidden" value=""/>
			    <input id="companyname" name="companyname" class="inputselectstyle" onclick="chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
			    <div class="search-trigger" onclick="chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
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
			    <span>岗位名称：</span>
			    <input id="postid" name="postid" type="hidden" value=""/>
			    <input id="postname" name="postname" class="inputselectstyle" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
			    <div class="search-trigger" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
			</li>
        </ul>
		<ul>
			<li>
				<span>姓名：</span>
				<input id="name" name="name" class="inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>年度：</span>
				<input id="annualyear" name="annualyear" class="inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>请假类型：</span>
				<input id="vacationtype" name="vacationtype" type="hidden" value=""/>
				<input id="vacationtypename" name="vacationtypename" class="inputselectstyle" onclick="chooseOptionTree('#vacationtype','#vacationtypename','VACATIONTYPE');"/>
				<div class="select-trigger" onclick="chooseOptionTree('#vacationtype','#vacationtypename','VACATIONTYPE');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>请假时间从：</span>
				<input id="startdate" name="startdate" class="{dateISO:true} inputselectstyle datepicker"/>
				<div class="date-trigger" onclick="$('#startdate').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>至时间：</span>
				<input id="enddate" name="enddate" class="{dateISO:true} inputselectstyle datepicker"/>
				<div class="date-trigger" onclick="$('#enddate').focus();"/>
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






</body>
</html>