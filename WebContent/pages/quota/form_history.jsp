<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>编制管理</title>
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


$(document).ready(function() {
	//列表加载
    loadGrid();
	
  //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});






//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.quotaid=tid;
}









//返回
function back(){
	window.parent.convertView('');
}















//解锁
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].quotaoperateguid);
	}
	if(array.length<=0){
		alert('请选择要解锁的数据！');
		return;
	}
	if(!confirm('确认要解锁选中数据吗？')){
		return;
	}
	
	$.post("quota/delQuotaOperateById.do",{ids:array.toString()}, function() {
		mygrid.reload();
    });
}


//解锁
function delQuotaOperateById(id){
	if(!confirm('确认要解锁选中数据吗？')){
		return;
	}
	$.post("quota/delQuotaOperateById.do",{ids:id}, function() {
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
				{name : 'operatestate'},
				{name : 'quotacode'},
				{name : 'companyname'},
				{name : 'deptid'},
				{name : 'postid'},
				{name : 'rankid'},
				{name : 'deptname'},
				{name : 'startdate'},
				{name : 'postnum'},
				{name : 'budgettypename'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'quotaid' , header: "操作" , width :60 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				if(record.operatestate==3){
					var htm='';
	        		htm+='<a href="javascript:delQuotaOperateById(\''+record.quotaoperateguid+'\');" title="解锁">';
	        		htm+='解锁';
	        		htm+='</a>';
        		return htm;
				}
			}},
			{id: 'operatestate' , header: "状态" , width :60 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				if(value==3){
					return '锁定';
				}else if(value==2){
					return '追减';
				}else if(value==4){
					return '修改';
				}
				return '追加';
			}},
			{id: 'operatenum' , header: "数量",headAlign:'center',align:'center', width :120},
			{id: 'modiuser' , header: "操作人",headAlign:'center',align:'left', width :80},
			{id: 'moditimestamp' , header: "操作时间",headAlign:'center',align:'center', width :150},
			{id: 'modimemo' , header: "变更原因", width :400 ,headAlign:'center',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'quota/searchQuotaOperate.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportFileName : '编制历史信息.xls',
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
		skin:getGridSkin()
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
			表格名称
		</div>
		<div class="table-ctrl">
			<!--  <a class="btn" href="javascript:addOrEdit('');"><i class="icon icon-plus"></i><span>新增</span></a>
   			<a class="btn" href="javascript:remove();"><i class="icon icon-remove"></i><span>解锁</span></a>-->
			<a class="btn"  href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<div id="myTable" style="height:460px;margin:5px auto;">
			<div id="gridbox" ></div>
        </div>
	</div>
</div>
<iframe id="detail" name="detail" width="100%" height="100%"frameborder="0" src="" scrolling="no" style="display: none;"></iframe>




</body>
</html>
