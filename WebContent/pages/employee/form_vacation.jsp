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
		$.getJSON("vacation/getVacationById.do", {id:id}, function(data) {
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
					$("#myForm").submit();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#myForm").clearForm();
			if(id!=null&&id!=''){
				loadData(id);
				if(!edit){
					formDisabled(true);
				}else{
					formDisabled(false);
				}
				
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
	$('#myForm').submit();
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
			{id: 'companyname' , header: "公司名称" , width :160,headAlign:'center',align:'left',hidden:true},
			{id: 'deptname' , header: "部门名称" , width :220,headAlign:'center',align:'left',hidden:true},
			{id: 'postname' , header: "岗位名称" , width :100,headAlign:'center',align:'left',hidden:true},
			{id: 'jobnumber' , header: "员工工号" , width :140,headAlign:'center',align:'left',hidden:true},
			{id: 'name' , header: "请假人" , width :80,headAlign:'center',align:'left',hidden:true},
			{id: 'cardnumber' , header: "身份证" , width :180,headAlign:'center',align:'left',hidden:true},
			{id: 'vacationtypename' , header: "请假类型" , width :120,headAlign:'center',align:'center'},
			{id: 'datenumber' , header: "请假天数" , width :80,headAlign:'center',align:'right'},
			{id: 'startdate' , header: "请假开始时间" , width :100 ,headAlign:'center',align:'center'},
			{id: 'enddate' , header: "请假结束时间" , width :100 ,headAlign:'center',align:'center'},
			{id: 'reason' , header: "请假原因" , width :150,headAlign:'center',align:'center'},
			{id: 'memo' , header: "备注" , width :300,headAlign:'left',align:'left'}
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
			pam.expAll=1;
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
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'nav | pagesize  state',
		pageSize:size,
		skin:getGridSkin(),
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				addORUpdateVacation(record.vacationid);
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
			请假信息
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




<div id="addVacationWindow" class="table-wrapper" style="display:none" title="请假信息">
		<form action="vacation/saveOrUpdateVacation.do" method="post" id="myForm" class="form">
			<input type="hidden" id="vacationid" name="vacationid" value=""/>
			<input type="hidden" id="employeeid" name="employeeid" value=""/>
			<input type="hidden" id="modiuser" name="modiuser" value=""/>
			<input type="hidden" id="moditimestamp" name="moditimestamp" value=""/>
			<input type="hidden" id="modimemo" name="modimemo" value=""/>
			<ul>
				<li>
					<span><em class="red">* </em>年度：</span>
					<input id="annualyear" name="annualyear" class="{required:true,maxlength:4,minlength:4,number:true} inputstyle" value=""  onblur="javascript:checkYear();"/>
				</li>
			</ul>
			<ul>
				<li>
					<span><em class="red">* </em>请假类型：</span>
					<input id="vacationtype" name="vacationtype" type="hidden" value=""/>
					<input id="vacationtypename" name="vacationtypename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#vacationtype','#vacationtypename','VACATIONTYPE');"/>
					<div class="select-trigger" onclick="chooseOptionTree('#vacationtype','#vacationtypename','VACATIONTYPE');"/>
				</li>
			</ul>
			<ul>
				<li>
					<span><em class="red">* </em>请假天数：</span>
					<input id="datenumber" name="datenumber" class="{required:true,maxlength:4} inputstyle" value=""/>
				</li>
			</ul>
			<ul>
				<li>
					<span><em class="red">* </em>请假开始时间：</span>
					<input id="startdate" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
					<div class="date-trigger" onclick="$('#startdate').focus();"/>
				</li>
			</ul>
			<ul>
				<li>
					<span>请假结束时间：</span>
					<input id="enddate" name="enddate" class="{dateISO:true} inputselectstyle datepicker"/>
					<div class="date-trigger" onclick="$('#enddate').focus();"/>
				</li>
			</ul>
			<ul>
				<li>
					<span>请假原因：</span>
					<textarea id="reason" name="reason"  rows="3" cols="40" class="{maxlength:500} areastyle"></textarea>
				</li>
			</ul>
			<ul>
				<li>
					<span>备注：</span>
					<textarea id="memo" name="memo" rows="3" cols="40" class="{maxlength:500} areastyle"></textarea>
				</li>
			</ul>
		</form>
</div>
</body>
</html>
