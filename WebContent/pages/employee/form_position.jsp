<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>员工任职</title>
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
var edit=(${param.edit==true}&&${position==true});


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
function loadData(tid) {
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("employee/getPositionById.do", {id:tid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#myForm #' + key);
			        if(el)el.val(data[key]);
			    }
				
				
				//是否占编
				if(data.isstaff==1){
					$("#isstaff").attr("checked",true);
					$("#isstaff").val(data.isstaff);
					$("#myQuota").show();
				}else{
					$("#isstaff").attr("checked",false);
					$("#myQuota").hide();
				}
			}
		});
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
		if(obj.isstaff!=1||(obj.isstaff==1&&(obj.enddate!=null&&obj.enddate!='')))
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
		
		
		//页面保存过
    	window.parent.callbackPageState(true);
  	});
}








//表单
var myForm=null;
function openForm(tid){
	if(edit){
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
				    	$("#formWindow").dialog("close");
				        mygrid.reload();
				        
				        
				      //页面保存过
				    	window.parent.callbackPageState(true);
				    });
				}
				 
				//加载数据
				loadData(tid);
				
				formDisabled(!edit);
				formDisabled(true,"#myCompany");
			}
		});
	}else{
		$("#formWindow").dialog({
			autoOpen: true,
			modal: true,
			resizable:false,
			width:470,
			buttons: {
				"关闭": function() {
					$(this).dialog("close");
				}
			},
			open:function(){
				//重置表单
				resetMyForm();
				 
				//加载数据
				loadData(tid);
				
				formDisabled(!edit);
				formDisabled(true,"#myCompany");
			}
		});
	}
	
}

//重置表单
function resetMyForm(){
	$("#myForm").clearForm();
	$("#isstaff").attr("checked",true);
	$("#isstaff").val(1);
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







//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.employeeid="${param.id}";
}



//部门选择回调
function callbackDept(){
	$("#jobid").val(null);
	$("#jobname").val(null);
	$("#postid").val(null);
	$("#postname").val(null);
	$("#quotaid").val(null);
	$("#quotaname").val(null);
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
				{name : 'postionguid'},
				{name : 'employeeid'},
				{name : 'companyid'},
				{name : 'deptid'},
				{name : 'postid'},
				{name : 'jobid'},
				{name : 'rankid'},
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
				{name : 'rankname'},
				{name : 'quotaname'},
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'isstaffname' , header: "编制", width :50 ,headAlign:'center',align:'center'},
			{id: 'companyname' , header: "公司名称" , width :250 ,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门名称" , width :300 ,headAlign:'center',align:'left'},
			{id: 'jobname' , header: "职务名称" , width :120 ,headAlign:'center',align:'left'},
			{id: 'rankname' , header: "职级名称" , width :120 ,headAlign:'center',align:'left'},
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
		exportURL : 'employee/searchPosition.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=1;
			mygrid.parameters=pam;
		},
		exportFileName : '员工任职信息表.xls',
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
		customRowAttribute : function(record,rn,grid){
			//生效状态
			if(record.isstaff==-1){
				return 'style="background:#edf4fe;"';
			}
		},
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
<body>

<div class="table">
	<div class="table-bar">
		<div class="table-title">
			任职信息
		</div>
		<div class="table-ctrl">
			<span id="group" class="gruop_hidden">
				<a class="btn" href="javascript:expGrid();"><i class="icon icon-download"></i><span>导出</span></a>
				<a class="btn" href="javascript:openForm(null);"><i class="icon icon-plus"></i><span>新增</span></a>
			<!-- 	<a class="btn"  href="javascript:validPosition(0);"><i class="icon icon-trash"></i><span>失效</span></a>
		   		<a class="btn"  href="javascript:validPosition(1);"><i class="icon icon-repeat"></i><span>恢复</span></a> -->
		   		
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







<!-- 表单 -->
<div id="formWindow" title="员工任职信息" style="display:none;">
	<form action="employee/saveOrUpdatePosition.do" method="post" id="myForm" class="form">
		<input id="employeeid" name="employeeid" type="hidden" value=""/>
		<input id="postionguid" name="postionguid" type="hidden" value=""/>
		<input id="state" name="state" type="hidden" value="1"/>
		<input id="modiuser" name="modiuser" type="hidden" value="${userid }"/>
		<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
		<input id="modimemo" name="modimemo" type="hidden" value=""/>
		<input id="memo" name="memo" type="hidden" value=""/>
		<ul>
			<li>
                <span>&nbsp;</span>
                <label for="isstaff">
               		<input id="isstaff" name="isstaff" type="checkbox" class="checkboxstyle" onclick="checkisStaff(this);"/>是否占编
                </label>
            </li>
        </ul>
		<ul id="myCompany">
			<li>
                <span>公司名称：</span>
                <input id="companyid" name="companyid" type="hidden" value=""/>
			    <input id="companyname" name="companyname" class="inputstyle disabled" disabled="true"/>
            </li>
        </ul>
        <ul>
			<li>
			    <span><em class="red">* </em>部门名称：</span>
			    <input id="deptid" name="deptid" type="hidden" value=""/>
			    <input id="deptname" name="deptname" class="{required:true} inputselectstyle" onclick="chooseMyDeptTree('#deptid','#deptname',callbackDept);"/>
			    <div class="search-trigger" onclick="chooseMyDeptTree('#deptid','#deptname',callbackDept);"/>
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
        <ul id="myQuota">
			<li>
			    <span>岗位编制：</span>
			    <input id="quotaid" name="quotaid" type="hidden" value=""/>
			    <input id="quotaname" name="quotaname" class="inputselectstyle" onclick="chooseQuotaTree('#quotaid','#quotaname',$('#postid').val());"/>
			    <div class="search-trigger" onclick="chooseQuotaTree('#quotaid','#quotaname',$('#postid').val());"/>
			</li>
			<li>
			    <span>职级：</span>
			     <input id="rankid" name="rankid" type="hidden" value=""/>
			    <input id="rankname" name="rankname" class="inputselectstyle" onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());"/>
			    <div class="select-trigger" onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());" />
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
			    <span>结束时间：</span>
			    <input id="enddate" name="enddate" class="{dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#enddate').focus();"/>
			</li>
		</ul>
	</form>
</div>


</body>
</html>
