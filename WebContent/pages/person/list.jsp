<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>待入职人才管理</title>
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



//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.mycandidatesguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	$.post("person/delPersonById.do",{ids:array.toString()}, function() {
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
	pam.name=$("#name").val();
	pam.culture=$("#culture").val();
	
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
				{name : 'mycandidatesguid'},
				{name : 'companyname'},
				{name : 'deptname'},
				{name : 'name'},
				{name : 'sexname'},
				{name : 'birthday'},
				{name : 'cardnumber'},
				{name : 'culturename'},
				{name : 'nationname'},
				{name : 'mobile'},
				{name : 'addressmobile'},
				{name : 'residenceplace'},
				{name : 'homephone'},
				{name : 'bloodtypename'},
				{name : 'domicilplace'},
				{name : 'nativeplace'},
				{name : 'politicsname'},
				{name : 'marriedname'},
				{name : 'privateemail'},
				{name : 'contactname'},
				{name : 'contactphone'},
				{name : 'contactrelationshipname'},
				{name : 'joinworkdate'},
				{name : 'memo'},
				{name : 'modiuser'},
				{name : 'moditimestamp'},
				{name : 'modimemo'},
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'companyname' , header: "公司" , width :80 ,headAlign:'center',align:'center'},
			{id: 'deptname' , header: "部门名称" , width :80 ,headAlign:'center',align:'center'},
			{id: 'name' , header: "姓名" , width :80 ,headAlign:'center',align:'center'},
			{id: 'sexname' , header: "性别" , width :80 ,headAlign:'center',align:'center'},
			{id: 'birthday' , header: "出生日期" , width :80 ,headAlign:'center',align:'center'},
			{id: 'cardnumber' , header: "身份证号码" , width :80 ,headAlign:'center',align:'center'},
			{id: 'culturename' , header: "文化程度" , width :80 ,headAlign:'center',align:'center'},
			{id: 'nationname' , header: "民族" , width :80 ,headAlign:'center',align:'center'},
			{id: 'mobile' , header: "手机" , width :80 ,headAlign:'center',align:'center'},
			{id: 'addressmobile' , header: "通讯录手机" , width :80 ,headAlign:'center',align:'center'},
			{id: 'residenceplace' , header: "户口所在地" , width :80 ,headAlign:'center',align:'center'},
			{id: 'homephone' , header: "家庭电话" , width :80 ,headAlign:'center',align:'center'},
			{id: 'homeplace' , header: "家庭住址" , width :80 ,headAlign:'center',align:'center'},
			{id: 'bloodtypename' , header: "血型" , width :80 ,headAlign:'center',align:'center'},
			{id: 'domicilplace' , header: "户籍信息" , width :80 ,headAlign:'center',align:'center'},
			{id: 'nativeplace' , header: "籍贯" , width :80 ,headAlign:'center',align:'center'},
			{id: 'politicsname' , header: "政治面貌" , width :80 ,headAlign:'center',align:'center'},
			{id: 'marriedname' , header: "婚姻状况" , width :80 ,headAlign:'center',align:'center'},
			{id: 'privateemail' , header: "私人邮箱" , width :80 ,headAlign:'center',align:'center'},
			{id: 'contactname' , header: "紧急联系人" , width :80 ,headAlign:'center',align:'center'},
			{id: 'contactphone' , header: "紧急联系人电话" , width :80 ,headAlign:'center',align:'center'},
			{id: 'contactRelationshipname' , header: "与本人关系" , width :80 ,headAlign:'center',align:'center'},
			{id: 'joinworkdate' , header: "参加工作时间" , width :80 ,headAlign:'center',align:'center'},
			{id: 'memo' , header: "备注" , width :80 ,headAlign:'center',align:'center'},
			{id: 'modiuser' , header: "操作人" , width :80 ,headAlign:'center',align:'center'},
			{id: 'moditimestamp' , header: "操作时间" , width :80 ,headAlign:'center',align:'center'},
			{id: 'modimemo' , header: "操作备注" , width :80 ,headAlign:'center',align:'center'}
		];
	var gridOption={
		id : grid_demo_id,
		loadURL :'person/searchPerson.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : '',
		beforeExport:function(){
			initPagePam();
			pam.expAll=$('input[name="xls"]:checked').val();
			mygrid.parameters=pam;
		},
		exportFileName : '',
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
		toolbarContent : 'nav | pagesize | reload  state',
		pageSize:size,
		skin:getGridSkin(),
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				convertView('person.do?page=tab&id='+record.mycandidatesguid);
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
		<h3>待入职人才管理</h3>
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
					<a class="btn" href="javascript:searchGrid();"><i class="icon icon-search"></i><span>搜索</span></a>
					<a class="btn" href="#"><i class="icon icon-list-alt"></i><span>导出</span></a>
					<a class="btn" href="javascript:convertView('person.do?page=tab');"><i class="icon icon-plus"></i><span>新增</span></a>
					<a class="btn" href="javascript:remove()"><i class="icon icon-remove"></i><span>删除</span></a>
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
				<span>姓名：</span>
				<input id="name" name="name" class="{maxlength:25} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>最高学历：</span>
				<input id="culture" name="culture" type="hidden" value=""/>
				<input id="culturename" name="culturename" class="inputselectstyle" onclick="chooseOptionTree('#culture','#culturename','CULTURE');"/>
				<div class="select-trigger" onclick="chooseOptionTree('#culture','#culturename','CULTURE');"/>
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