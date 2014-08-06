<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>面试地点管理</title>
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





//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.auditionaddressguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
 	$.post("audition/delAuditionAddressById.do",{ids:array.toString()}, function() {
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








var state = 1;
//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.state=$("#myvalid").attr("checked")?1:0;
	pam.auditionaddress=$("#auditionaddress").val();
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
				{name : 'auditionaddressguid'},
				{name : 'auditionaddress'},
				{name : 'state'},
				{name : 'memo'},
				{name : 'modiuser'},
				{name : 'moditimestamp'},
				{name : 'modimemo'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'state' , header: "状态" , width :80,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
			if(value==0){
				return '<font color="#ff0000">失效</font>';
			}
			return '正常';
			}},
			{id: 'auditionaddress' , header: "面试地点" , width :120,headAlign:'center',align:'left'},
			{id: 'memo' , header: "备注" , width :300,headAlign:'center',align:'center'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'audition/searchAuditionAddress.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		}, 
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
				convertView('auditionaddress.do?page=form&id='+record.auditionaddressguid);
			}
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}

//失效还原
function validContract(valid){
	var prompt=(valid==0?"失效":"恢复");
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		if(obj.state!=valid)
			array.push(obj.auditionaddressguid);
	}
	if(array.length<=0){
		alert("请选择要"+prompt+"的数据！");
		return;
	}
	if(!confirm("确认要"+prompt+"选中数据吗？")){
		return;
	}
   	$.post("audition/validContractById.do",{ids:array.toString(),state:valid}, function() {
		mygrid.reload();
    });
}

</script>

</head>
<body>

<div class="sort">
	<div class="sort-title">
		<h3>面试地点管理</h3>
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
						<label for="myvalid">
							<input id="myvalid" type="checkbox" class="checkboxstyle" onclick="mygrid.reload();" checked="true" /> 只显示当前有效的地点
							&nbsp;
							&nbsp;
						</label>
					</div>
					<a class="btn" href="javascript:convertView('auditionaddress.do?page=form');" ><i class="icon icon-plus"></i><span>新增</span></a>
					<a  id="valid" class="btn"  href="javascript:validContract(0);" ><i class="icon icon-trash"></i><span>失效</span></a>
					<a class="btn" id="revert"  href="javascript:validContract(1);"><i class="icon icon-retweet"></i><span>恢复</span></a>
					<a class="btn" href="javascript:remove();" ><i class="icon icon-remove" ></i><span>删除</span></a>
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
                <span><em class="red">* </em>面试地点：</span>
                <input id="auditionaddress" name="auditionaddress" class="{required:true,maxlength:16} inputstyle" style="width:200px;"/>
            </li>
        </ul>
	</form>
</div>


</body>
</html>