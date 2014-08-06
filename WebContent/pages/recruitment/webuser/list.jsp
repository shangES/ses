<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>外网人员管理</title>
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

$(document).ready(function() {
	//列表加载
    loadGrid();
    
	
	
  //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});







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
	pam.username=$("#username").val();
	pam.valid=$("#valid").attr("checked")?1:0;
}









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








//失效、还原
function validWebUser(valid){
	var prompt=(valid==0?"失效":"恢复");
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		if(obj.valid!=valid)
			array.push(obj.webuserguid);
	}
	if(array.length<=0){
		alert("请选择要"+prompt+"的数据！");
		return;
	}
	if(!confirm("确认要"+prompt+"选中数据吗？")){
		return;
	}
   	$.post("recruitment/validWebUserById.do",{ids:array.toString(),state:valid}, function() {
		mygrid.reload();
    });
}










//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].webuserguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	$.post("recruitment/delWebUserById.do",{ids:array.toString()}, function() {
		mygrid.reload();
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





//失效、还原
function validOneWebUser(id,valid){
	var prompt=(valid==0?"失效":"恢复");
	var array=new Array();
	array.push(id);
	if(!confirm("确认要"+prompt+"选中数据吗？")){
		return;
	}
   	$.post("recruitment/validWebUserById.do",{ids:array.toString(),state:valid}, function() {
		mygrid.reload();
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
				{name : 'webuserguid'},
				{name : 'username'},
				{name : 'thirdpartnerguid'},
				{name : 'password'},
				{name : 'isadmin'},
				{name : 'valid'},
				{name : 'email'},
				{name : 'mobile'},
				{name : 'modtime'},
				{name : 'rmk'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'webuserguid' , header: "操作" , width :80 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				var htm='';
        			htm+='<a href="javascript:convertView(\'webUser.do?page=form&edit=${edit}&id='+record.webuserguid+'\');" class="ui-button ui-button-icon-only" title="修改"><span class="ui-icon ui-icon-pencil"></span>&nbsp;</a>';
       			if(record.valid==1)
	        		htm+='<a href="javascript:validOneWebUser(\''+record.webuserguid+'\',0);" class="ui-button ui-button-icon-only" title="置为无效"><span class="ui-icon ui-icon-trash"></span>&nbsp;</a>';
	        	else if(record.valid==0)
	        		htm+='<a href="javascript:validOneWebUser(\''+record.webuserguid+'\',1);" class="ui-button ui-button-icon-only" title="还原"><span class="ui-icon ui-icon-check"></span>&nbsp;</a>';
				return htm;
			}},
			{id: 'valid' , header: "当前状态" , width :80,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				if(value==0){
					return '<font color="#ff0000">失效</font>';
				}
				return '正常';
			}},
			{id: 'username' , header: "用户名" , width :110,headAlign:'center',align:'left'},
			{id: 'email' , header: "登录名" , width :180,headAlign:'center',align:'left'},
			{id: 'isadmin' , header: "系统管理员" , width :150,headAlign:'center',align:'left',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				if(value==2){
					return '第三方机构';
				}else if(value==3){
					return '猎头';
				}
				return '注册用户';
			}},
			{id: 'mobile' , header: "联系电话" , width :100,headAlign:'center',align:'left'},
			{id: 'modtime' , header: "注册时间" , width :120 ,headAlign:'center',align:'center'},
			{id: 'rmk' , header: "备注" , width :230 ,headAlign:'center',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'recruitment/searchWebUser.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'recruitment/searchWebUser.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=1;
			mygrid.parameters=pam;
		},
		exportFileName : '外网人员表.xls',
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
				convertView('webUser.do?page=form&id='+record.webuserguid+'&edit=${edit}');
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
		<h3>外网人员管理</h3>
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
					<div style="float:left;">
						<label for="valid">
							<input id="valid" type="checkbox" class="checkboxstyle" onclick="mygrid.reload();" checked="true"/>只显示当前有效数据
						</label>
						&nbsp;
						&nbsp;
					</div>
					<a class="btn" href="javascript:searchGrid();" style="display:${search?'':'none'}"><i class="icon icon-search"></i><span>搜索</span></a>
					<a class="btn" href="javascript:expGrid();" style="display:${exp?'':'none'}"><i class="icon icon-download" ></i><span>导出</span></a>
					<a class="btn" href="javascript:convertView('webUser.do?page=form&edit={edit}');"  style="display:${add?'':'none'}"><i class="icon icon-plus"></i><span>新增</span></a>
					<a class="btn"  href="javascript:validWebUser(0);" style="display:${valid?'':'none'}" ><i class="icon icon-trash"></i><span>失效</span></a>
		   			<a class="btn"  href="javascript:validWebUser(1);" style="display:${revert?'':'none'}" ><i class="icon icon-retweet"></i><span>恢复</span></a>
					<!-- <a class="btn" href="javascript:remove();" style="display:${del?'':'none'}"><i class="icon icon-remove"></i><span>删除</span></a>-->
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
				<span>用户名：</span>
				<input id="username" name="username" class="inputstyle"/>
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
