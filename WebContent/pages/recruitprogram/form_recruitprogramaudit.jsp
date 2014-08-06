<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>招聘计划OA审批管理</title>
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
    
    //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
       	beforeShow: function(input, inst) {
   			if($(input).attr("readonly"))
   				inst.inline=true;
   		}
    });
});

//取数据
function loadData(rid) {
	if(rid!=null&&rid!=''&&rid!='null')
		$.getJSON("recruitprogram/getRecruitprogramauditById.do", {id:rid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#myForm #' + key);
			        if(el)el.val(data[key]);
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
		array.push(obj.recruitprogramauditguid);
	}
	
	
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
 	$.post("recruitprogram/delRecruitprogramauditById.do",{ids:array.toString()}, function() {
		mygrid.reload();
  	});
}








//表单
var myForm=null;
var rid=null;
function openForm(rid){
		$("#formWindow").dialog({
			autoOpen: true,
			modal: true,
			resizable:false,
			width:470,
			buttons: {
				"确定":function(){
					if(myForm.form()){
						$("#recruitprogramguid").val(tid);
						$('#myForm').submit();
					}
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
					myForm=$("#myForm").validate();
				}
				
			    $('#myForm').ajaxForm(function(data) {
			    	$("#formWindow").dialog("close");
			        mygrid.reload();
			    });
				
				//加载数据
				loadData(rid);
			}
		});
}




//重置表单
function resetMyForm(){
	$("#myForm").clearForm();
}


//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.recruitprogramguid=tid;
}



//部门选择回调
function callbackDept(){
	$("#postid").val(null);
	$("#postname").val(null);
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
					{name : 'recruitprogramguid'},
					{name : 'recruitprogramauditguid'},
					{name : 'recruitprogramguid'},
					{name : 'companyid'},
					{name : 'deptid'},
					{name : 'postid'},
					{name : 'rankid'},
					{name : 'postnum'},
					{name : 'startdate'},
					{name : 'enddate'},
					{name : 'auditresult'}
			]
		};
		var colsOption = [
		      	{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
		    	{id: 'companyname' , header: "公司" , width :200,headAlign:'center',align:'left'},
		    	{id: 'deptname' , header: "部门" , width :200,headAlign:'center',align:'left'},
		    	{id: 'postname' , header: "岗位" , width :150,headAlign:'center',align:'left'},
		    	{id: 'rankname' , header: "职级" , width :100,headAlign:'center',align:'center'},
		    	{id: 'postnum' , header: "招聘人数" , width :80,headAlign:'center',align:'left'},
		    	{id: 'startdate' , header: "开始时间" , width :120,headAlign:'center',align:'center'},
		    	{id: 'enddate' , header: "结束时间" , width :120,headAlign:'center',align:'center'},
		    	{id: 'auditresult' , header: "审核结果" , width :250 ,headAlign:'center',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'recruitprogram/searchRecruitProgramAudit.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'recruitprogram/searchRecruitProgramAudit.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=1;
			mygrid.parameters=pam;
		},
		exportFileName : '招聘计划OA审批.xls',
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
		},
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				//修改
				openForm(record.recruitprogramauditguid);
			}
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}

//同步员工
function refresh(){
	$.post("recruitprogram/refreshRecruitProgramAudit.do",{id:tid},function() {
		alert("OA信息同步成功！");
		mygrid.reload();
    });
}
</script>
</script>
</head>
<body>

<div class="table">
	<div class="table-bar">
		<div class="table-title">
			招聘计划OA审批管理
		</div>
		<div class="table-ctrl">
			<span id="group" class="gruop_hidden">
				<!--  <a class="btn" href="javascript:refresh();"><i class="icon icon-refresh"></i><span>OA信息同步</span></a>-->
				<a class="btn" href="javascript:expGrid();"><i class="icon icon-download"></i><span>导出</span></a>
				<!--  <a class="btn" href="javascript:openForm(null);"><i class="icon icon-plus"></i><span>新增</span></a>-->
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
<div id="formWindow" title="招聘计划OA审批" style="display:none;">
	<form action="recruitprogram/saveOrUpdateRecruitProgramAudit.do" method="post" id="myForm" class="form">
		<input id="recruitprogramauditguid" name="recruitprogramauditguid" type="hidden" value=""/>
		<input id="recruitprogramguid" name="recruitprogramguid" type="hidden" value=""/>
		
		<ul>
			<li>
                <span><em class="red">* </em>公司名称：</span>
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
				<span>岗位名称：</span>
                <input id="postid" name="postid" type="hidden" value=""/>
			    <input id="postname" name="postname" class="inputselectstyle" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
			    <div class="search-trigger" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
            </li>
        </ul>
        <ul>
			<li>
			    <span>职级：</span>
			    <input id="rankid" name="rankid" type="hidden" value=""/>
			    <input id="rankname" name="rankname" class="inputselectstyle" onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());"/>
			    <div class="select-trigger" onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());" />
			</li>
        </ul>
        <ul>
        	<li>
        		<span><em class="red">* </em>招聘人数：</span>
        		<input id="postnum" name="postnum" class="{required:true} inputstyle" value=""/>
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
		<ul>
			<li>
				<span>审核结果：</span>
				<textarea id="auditresult" name="auditresult" rows="3" cols="40" style="width:250px;" class="{maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>


</body>
</html>
