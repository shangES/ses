<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>系统日志管理</title>
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

<script type="text/javascript">
$(document).ready(function() {
	
	//加载系统日志信息
	loadGrid();
	
    //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});







//设置参数
function initSysloPagePam(){
	pam={};
	pam.expAll=0;
	pam.modiuser=$("#modiuser").val();
}








//删除
function delSyslog(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.logguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
   	$.post("system/delSyslog.do",{ids:array.toString()}, function() {
		mygrid.reload();
    });
}







//搜索
var searchForm=null;
function search(){
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
		
		//返回是计算的高度
		_autoHeight();
		if(url==null)
			mygrid.reload();
  }
}







//导出
function expSyslog(){
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
</script>

<script type="text/javascript">
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'logguid'},
				{name : 'subject'},
				{name : 'source'},
				{name : 'target'},
				{name : 'description'},
				{name : 'modiuser'},
				{name : 'moditimestamp'},
				{name : 'modimemo'}
			]
		};
	var colsOption = [
						{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center',filterable:false},
		              	{id: 'subject' , header: "主体" ,headAlign:'center',align:'left', width :120,hidden:true},
		          	   	{id: 'source' , header: "来源",headAlign:'center',align:'left', width :200,hidden:true},
		          	   	{id: 'target' , header: "目标" ,headAlign:'center', align:'left', width :200,hidden:true},
		          	  	{id: 'modiuser' , header: "操作人" ,headAlign:'center', align:'left', width :100},
		          	  	{id: 'moditimestamp' , header: "操作时间" ,headAlign:'center', align:'center', width :150},
		          		{id: 'description' , header: "描述" ,headAlign:'left',align:'left', width :750}
		          	];
	var gridOption={
			id : grid_demo_id,
			loadURL : 'system/searchSyslog.do',
			beforeLoad:function(reqParam){
				initSysloPagePam();
				reqParam['parameters']=pam;
			},
			exportURL : 'system/searchSyslog.do?export=true',
			beforeExport:function(){
				initSysloPagePam();
				pam.expAll=$('input[name="xls"]:checked').val();
				mygrid.parameters=pam;
			},
			exportFileName : '系统日志信息表.xls',
			width:'99.8%',
			height:"100%",  //"100%", // 330,
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
					convertView("system.do?page=form_syslog&id="+record.logguid);
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
		<h3>系统日志管理</h3>
	</div>
	
	<div class="sort-cont sort-table">
		<div class="table">
			<div class="table-bar">
				<div class="table-title">
					表格名称
				</div>
				<div class="table-ctrl">
					   	<span id="b1">
					   		<a  class="btn" href="javascript:expSyslog();"><i class="icon icon-download"></i><span>导出</span></a>
					   		<a  class="btn" href="javascript:search();"><i class="icon icon-search"></i><span>搜索</span></a>
							<a  class="btn" href="javascript:convertView('system.do?page=form_syslog');"><i class="icon icon-plus"></i><span>新增</span></a>
							<a  class="btn" href="javascript:delSyslog();"><i class="icon icon-remove"></i><span>删除</span></a>
				   		</span>
				</div>
			</div>
		<div class="table-wrapper">
				<div style="height:550px;margin:5px auto;">
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
					<span>操作人：</span>
					<input id="modiuser" name="modiuser" class="{maxlength:15} inputstyle"/>
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