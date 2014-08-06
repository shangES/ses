<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>工作经历</title>
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
var tid='${param.id}';
var edit=${param.edit==true};



$(document).ready(function() {
	//列表加载
    loadGrid();
	
    editState();
    
  //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
       	beforeShow: function(input, inst) {
   			if($(input).attr("readonly"))
   				inst.inline=true;
   		}
    });
});

function editState(){
	if(!edit){
		$("#group").hide();
	}
}


//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.workexperienceid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
 	$.post("employee/delWorkexperienceById.do",{ids:array.toString()}, function() {
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


//导出
function expGrid(){
	mygrid.exportGrid('xls');
}







//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.employeeid="${param.id}";
}


//得到数据
function loadWorkexperience(id){
	if(id!=null&&id!=''&&id!='null')
		$.getJSON("employee/getWorkexperienceById.do", {id:id}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#' + key);
			        if(el) 
			            el.val(data[key]);
			    }
				
			}
		});
}

//新增或修改
var addWorkexperienceForm=null;
function addOrEdit(id){
	$("#addWorkexperienceWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		buttons: {
			"确定": function() {
				if(addWorkexperienceForm.form){
					$("#addWorkexperienceForm").submit();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#addWorkexperienceForm").clearForm();
			if(id!=null&&id!=''){
				loadWorkexperience(id);
				if(!edit){
					formDisabled(true);
				}else{
					formDisabled(false);
				}
			}else{
				$("#employeeid").val(tid);
			}
			addWorkexperienceForm=$("#addWorkexperienceForm").validate({submitHandler: function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		mygrid.reload();
			    		$("#addWorkexperienceWindow").dialog("close");
			        });
				}
			});
		}
	});
}


//返回
function back(){
	window.parent.convertView('');
}


</script>


<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'workexperienceid'},
				{name : 'employeeid'},
				{name : 'workunit'},
				{name : 'startdate'},
				{name : 'enddate'},
				{name : 'job'},
				{name : 'description'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'workunit' , header: "单位" , width :200 ,headAlign:'center',align:'left'},
			{id: 'startdate' , header: "时间起点" , width :110 ,headAlign:'center',align:'center'},
			{id: 'enddate' , header: "终止日期" , width :110 ,headAlign:'center',align:'center'},
			{id: 'job' , header: "职务" , width :140 ,headAlign:'center',align:'left'},
			{id: 'description' , header: "描述" , width :450 ,headAlign:'center',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'employee/searchWorkexperience.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'employee/searchWorkexperience.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=1;
			mygrid.parameters=pam;
		},
		exportFileName : '工作经历信息表.xls',
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
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				addOrEdit(record.workexperienceid);
			}
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}

</script>
</head>
<body>

<div class="table">
	<div class="table-bar">
		<div class="table-title">
			工作经历信息
		</div>
		<div class="table-ctrl">
			<span id="group" class="gruop_hidden">
				<a class="btn" href="javascript:expGrid();"><i class="icon icon-download"></i><span>导出</span></a>
				<a class="btn" href="javascript:addOrEdit('');"><i class="icon icon-plus"></i><span>新增</span></a>
				<a class="btn" href="javascript:remove();"><i class="icon icon-remove"></i><span>删除</span></a>
			</span>
			
			<a class="btn"  href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<div id="myTable" style="height:460px;margin:5px auto;">
			<div id="gridbox" ></div>
        </div>
	</div>
</div>

<!-- 工作经历信息窗口 -->
<div id="addWorkexperienceWindow" title="工作经历信息" style="display:none;">
	<form action="employee/saveOrUpdateWorkexperience.do" id="addWorkexperienceForm" class="form" method="post">
		<input type="hidden" id="workexperienceid" name="workexperienceid" value=""/>
		<input type="hidden" id="employeeid" name="employeeid" value=""/>
		<input type="hidden" id="email" name="email" value=""/>
		<input type="hidden" id="reporterphone" name="reporterphone" value=""/>
		<input type="hidden" id="reporter" name="reporter" value=""/>
		<ul>
			<li>
				<span><em class="red">* </em>单位：</span>
				<input  id="workunit" name="workunit"  class="{required:true,maxlength:30} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>时间起点：</span>
				<input id="startdate" name="startdate" class="{required:true,dateISO:true}  inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#startdate').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>终止日期：</span>
				<input id="enddate" name="enddate" class="{required:true,dateISO:true}  inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#enddate').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>担任职务：</span>
				<input id="job" name="job"  class="{maxlength:20} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>描述：</span>
			    <textarea id="description" name="description" rows="3" cols="40" class="{maxlength:100} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>


</body>
</html>
