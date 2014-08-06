<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>年休假管理</title>
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



//取数据
function loadData(id) {
	$.getJSON("annual/getAnnualById.do", {id:id}, function(data) {
		if(data!=null){
			for(var key in data){
				if($('#myForm #'+key)){
					$('#myForm #'+key).val(data[key]);
				}
			}
		}
	});
}







//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.employeeid=tid;
}










//新增or修改
var myForm=null;
function addORUpdateVacation(id){
	$("#addVacationWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		buttons: {
			"确定": function() {
				if(myForm.form){
					save();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#myForm").clearForm();
			if(id!=null&&id!=''){
				loadData(id);
			}else{
				$("#employeeid").val(tid);
			}
			myForm=$("#myForm").validate({submitHandler: function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		mygrid.reload();
			    		$("#addVacationWindow").dialog("close");
			        });
				}
			});
		}
	});
}









//保存
function save(){
	var year=$("#annualyear").val();
	var annualguid=$("#annualguid").val();
	
	if(year!=null&&year!=''&&year!='null'){
		$.getJSON("annual/getAnnualByEmployeeIdAndYear.do", {id:tid,year:year,annualguid:annualguid}, function(data) {
			if(data!=null){
				alert("此年度休假天数已存在,请重新填写!");
				$("#annualyear").val(null);
				return;
			}else{
				$('#myForm').submit();
			}
		});
	}	
}







//导出
function expGrid(){
	mygrid.exportGrid('xls');
}






//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.annualguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
 	$.post("annual/delAnnualById.do",{ids:array.toString()}, function() {
		mygrid.reload();
  	});
}


//年度唯一对应此人
function checkYear(){
	var year=$("#annualyear").val();
	var annualguid=$("#annualguid").val();
	
	if(year!=null&&year!=''&&year!='null'){
		$.getJSON("annual/getAnnualByEmployeeIdAndYear.do", {id:tid,year:year,annualguid:annualguid}, function(data) {
			if(data!=null){
				alert("此年度休假天数已存在,请重新填写!");
				$("#annualyear").val(null);
				return;
			}
		});
	}
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
			//{id: 'companyname' , header: "公司名称" , width :160,headAlign:'center',align:'left'},
			//{id: 'deptname' , header: "部门名称" , width :220,headAlign:'center',align:'left'},
			//{id: 'postname' , header: "岗位名称" , width :100,headAlign:'center',align:'left'},
			//{id: 'jobnumber' , header: "员工工号" , width :140,headAlign:'center',align:'left'},
			{id: 'name' , header: "休假人" , width :80,headAlign:'center',align:'left'},
			{id: 'offnumday' , header: "年休假天数" , width :100,headAlign:'center',align:'right'},
			{id: 'alreadynumberday' , header: "已休假天数" , width :100,headAlign:'center',align:'right'},
			{id: 'leavenumberday' , header: "剩余休假天数" , width :100,headAlign:'center',align:'right'}
			
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'annual/searchAnnual.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'annual/searchAnnual.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=1;
			mygrid.parameters=pam;
		},
		exportFileName : '年休假信息表.xls',
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
				addORUpdateVacation(record.annualguid);
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
			年休假信息
		</div>
		<div class="table-ctrl">
			
			<span id="group" class="gruop_hidden">
				<a class="btn" href="javascript:expGrid();"><i class="icon icon-download"></i><span>导出</span></a>
				<a class="btn" href="javascript:addORUpdateVacation()"><i class="icon icon-plus"></i><span>新增</span></a>
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




<div id="addVacationWindow" class="table-wrapper" style="display:none" title="年休假信息">
		<form action="annual/saveOrUpdateAnnual.do" method="post" id="myForm" class="form">
			<input type="hidden" id="annualguid" name="annualguid" value=""/>
			<input type="hidden" id="employeeid" name="employeeid" value=""/>
			<input type="hidden" id="modiuser" name="modiuser" value=""/>
			<input type="hidden" id="moditimestamp" name="moditimestamp" value=""/>
			<ul>
				<li>
					<span><em class="red">* </em>年度：</span>
					<input id="annualyear" name="annualyear" class="{required:true,maxlength:4,minlength:4,number:true} inputstyle" value=""  onblur="javascript:checkYear();"/>
				</li>
			</ul>
			<ul>
				<li>
					<span><em class="red">* </em>可休年休天数：</span>
					<input id="offnumday" name="offnumday" class="{required:true,maxlength:10,number:true} inputstyle" value=""/>
				</li>
			</ul>
		</form>
</div>
</body>
</html>
