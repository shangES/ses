<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>OA信息管理</title>
<base href="${baseUrl }" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="skins/css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="skins/css/form.css" type="text/css" media="all" />
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css" />
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeIcons.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/default/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/green/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/mac/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/china/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/vista/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/calendar/calendar-blue.css" />


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
  
    //加载表单验证
    $("#formWindow").validate({submitHandler:function(form) {
	    	$(form).ajaxSubmit(function(data) {
	    		if(data != null){
	    			$("#formWindow").dialog("close");
		    		mygrid.reload();
	    		}
	        });
		}
	});
    
	
  	//关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});



//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
 	pam.companyname=$("#companyname_1").val();
	pam.deptname=$("#deptname_1").val();
	pam.postname=$("#postname_1").val();
	pam.rankname=$("#rankname_1").val();
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




//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].recruitprogramauditguid);
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

//审核
//部门选择回调
function callbackDept(){
	$("#postid").val(null);
	$("#postname").val(null);
	$("#quotaid").val(null);
	$("#quotaname").val(null);
}



//公司回调
function callBackCompany(){
	$("#deptid").val(null);
	$("#deptname").val(null);
	
	//部门选择回调
	callbackDept();
}

//审核
var auditForm=null;
function audit(id){
		$("#auditWindow").dialog({
			autoOpen: true,
			modal: true,
			resizable:false,
			width:470,
			buttons: {
				"确定":function(){
					if(auditForm.form()){
						save();
					}
				},
				"取消": function() {
					$(this).dialog("close");
				}
			},
			open:function(){
				//重置表单
				$("#auditForm").clearForm();
				
				loadAuditData(id);
				
				 //加载表单验证
				if(auditForm==null)
					auditForm=$("#auditForm").validate({submitHandler:function(form) {
				    	$(form).ajaxSubmit(function(data) {
				    		alert("操作成功!");
				    		mygrid.reload();
				    		$("#auditWindow").dialog("close");
				        });
					}
				});
				 
				 
				
			}
		});
}


//取数据
function loadAuditData(id) {
	if(id!=null&&id!=''&&id!='null')
		$.getJSON("recruitprogram/getRecruitprogramauditById.do", {id:id}, function(data) {
			if(data!=null){
				for(var key in data){
					if($('#auditForm #t_'+key)){
						$('#auditForm #t_'+key).val(data[key]);
					}
				}
			}
		});
} 



//保存
function save(){
	var id=$("#t_recruitprogramauditguid").val();
	var remainnum=$("#remainnum").val();
	$.getJSON("recruitprogram/getRecruitprogramauditById.do", {id:id}, function(data) {
		if(data!=null){
			if(remainnum>data.remainnum||remainnum<=0){
				alert("对应人数输入有误,请重新输入!");
				$("#remainnum").val(null);
				$("#remainnum").focus();
				return;
			}else{
				$('#auditForm').submit();
			}
		}
	});
	
	
	
}



//校验所输入的数字要小于
function checkRemainNum(){
	var id=$("#t_recruitprogramauditguid").val();
	var remainnum=$("#remainnum").val();
	$.getJSON("recruitprogram/getRecruitprogramauditById.do", {id:id}, function(data) {
		if(data!=null){
			if(remainnum>data.remainnum||remainnum<=0){
				alert("对应人数输入有误,请重新输入!");
				$("#remainnum").val(null);
				$("#remainnum").focus();
				return;
			}
		}
	});
}






//OA信息同步
function refresh(){
	$.post("recruitprogram/refreshRecruitProgramAudit.do",function() {
		alert("OA信息同步成功！");
		mygrid.reload();
    });
}



//OA信息
function load(id){
	window.open('http://125.210.208.58/workflow/request/ViewRequest.jsp?requestid='+id+'&isovertime=0')
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
					{name : 'quotaname'},
					{name : 'startdate'},
					{name : 'enddate'},
					{name : 'auditresult'}
			]
		};
		var colsOption = [
		      	{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
		      	{id:'state',header: "操作" , width :70,headAlign:'center',align:'center',renderer:function(value,record,colObj,grid,colNo,rowNo){
		      		var htm='&nbsp;';
		      		/*htm+='<a href="javascript:load(\''+record.recruitprogramauditguid+'\')">';
					htm+='OA信息';
					htm+='</a>';
					htm+='&nbsp;&nbsp;&nbsp;';*/
		      		if(record.remainnum>0){
		      			htm+='<a href="javascript:audit(\''+record.recruitprogramauditguid+'\')">';
						htm+='审核';
						htm+='</a>';
						htm+='&nbsp;';
					}
					return htm;
		      	}},
		    	{id: 'companyname' , header: "公司" , width :200,headAlign:'center',align:'left'},
		    	{id: 'deptname' , header: "部门" , width :200,headAlign:'center',align:'left'},
		    	{id: 'postname' , header: "岗位" , width :150,headAlign:'center',align:'left'},
		    	{id: 'rankname' , header: "职级" , width :100,headAlign:'center',align:'center'},
		    	{id: 'postnum' , header: "招聘人数" , width :80,headAlign:'center',align:'center'},
		    	{id: 'remainnum' , header: "可对应人数" , width :80,headAlign:'center',align:'center'},
		    	{id: 'startdate' , header: "开始时间" , width :120,headAlign:'center',align:'center'},
		    	{id: 'enddate' , header: "结束时间" , width :120,headAlign:'center',align:'center'}
		    	//{id: 'auditresult' , header: "审核结果" , width :250 ,headAlign:'center',align:'left'}
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
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				//修改
				convertView('recruitprogram.do?page=recruitprogramaudit/tab&id='+record.recruitprogramauditguid+'&interfacecode='+record.interfacecode);
			}
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}



function pageResize(height){
	$("#myTable").height(height);
	//计算高度
	_autoHeight();
}



</script>

<script type="text/javascript">
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

//重置表单
function resetMyForm(){
	$("#myForm").clearForm();
}

//公司回调
function callBackCompany_1(){
	$("#deptid_1").val(null);
	$("#deptname_1").val(null);
	//部门选择回调
	callbackDept_1();
}

//部门选择回调
function callbackDept_1(){
	$("#postid_1").val(null);
	$("#postname_1").val(null);
	
	
	
}

</script>
</head>
<body>

<div class="sort">
	<div class="sort-title">
		<h3>OA信息管理</h3>
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
					<a class="btn" href="javascript:refresh();"><i class="icon icon-refresh"></i><span>OA信息同步</span></a>
					<a class="btn" href="javascript:searchGrid();" style="display:${search?'':'none'}"><i class="icon icon-search"></i><span>搜索</span></a>
					<a class="btn" href="javascript:expGrid();" style="display:${exp?'':'none'}"><i class="icon icon-download"></i><span>导出</span></a>
					<a class="btn" href="javascript:remove();" style="display:${del?'':'none'}"><i class="icon icon-remove"></i><span>删除</span></a>
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
	<iframe id="detail" name="detail" width="100%" height="100%"frameborder="0" src="" scrolling="no" style="display: none;"></iframe>


<!-- 导出 -->
<div id="dialog_exp" style="display: none;" title="数据导出">
	<table width="100%" height="100%" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td>&nbsp; &nbsp; &nbsp; &nbsp; <label for="xls1"> 
				<input id="xls1" type="radio" name="xls" value="0" checked="true" class="checkboxstyle" />导出本页
			</label> &nbsp; &nbsp; 
			<label for="xls2"> 
				<input id="xls2" type="radio" name="xls" value="1" class="checkboxstyle" />全部导出
			</label>
			</td>
		</tr>
	</table>
</div>


<!-- 表单 -->
<div id="search" title="搜索条件设置" style="display:none;">
	<form action="" id="searchForm" class="form">
		<ul class="deptidLi">
			<li>
	            <span>公司名称：</span>
	            <input id="companyid_1" name="companyid_1" type="hidden" value=""/>
			    <input id="companyname_1" name="companyname_1" class="inputselectstyle" onclick="chooseMyHasCompanyTree('#companyid_1','#companyname_1',callBackCompany_1);"/>
			    <div class="search-trigger" onclick="chooseMyHasCompanyTree('#companyid_1','#companyname_1',callBackCompany_1);"/>
	         </li>
		</ul>
		<ul>
			<li>
			    <span>部门名称：</span>
			    <input id="deptid_1" name="deptid_1" type="hidden" value=""/>
			    <input id="deptname_1" name="deptname_1" class="inputselectstyle" onclick="chooseOneCompanyDeptTree('#deptid_1','#deptname_1',$('#companyid_1').val(),callbackDept_1);"/>
			    <div class="search-trigger" onclick="chooseOneCompanyDeptTree('#deptid_1','#deptname_1',$('#companyid_1').val(),callbackDept_1);"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>岗位：</span>
				<input id="postid_1" name="postid_1" type="hidden" value=""/>
			    <input id="postname_1" name="postname_1" class="inputselectstyle" onclick="choosePostTree('#postid_1','#postname_1',$('#deptid_1').val());"/>
			    <div class="select-trigger" onclick="choosePostTree('#postid_1','#postname_1',$('#deptid_1').val());"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>职级：</span>
				<input id="rankid_1" name="rankid_1" type="hidden" value=""/>
				<input id="rankname_1" name="rankname_1" class="inputselectstyle"  onclick="chooseRankTree('#rankid_1','#rankname_1',$('#companyid_1').val());"/>
				<div class="search-trigger" onclick="chooseRankTree('#rankid_1','#rankname_1',$('#companyid_1').val());"/>
			</li>
		</ul>
	</form>
</div>


<!-- 审核 -->
<div id="auditWindow"  title="OA审核" style="display:none;">
	<form action="recruitprogram/saveOrUpdateRecruitProgramAudit.do" id="auditForm"  class="form" method="post">
		<input type="hidden" id="t_recruitprogramguid" name="recruitprogramguid" value=""/>
		<input type="hidden" id="t_recruitprogramauditguid" name="recruitprogramauditguid" value=""/>
		<input type="hidden" id="t_postnum" name="postnum" value=""/>
		<input type="hidden" id="t_interfacecode" name="interfacecode" value=""/>
		<input type="hidden" id="t_state" name="state" value="1"/>
		<input type="hidden" id="t_startdate" name="startdate" value=""/>
		<input type="hidden" id="t_enddate" name="enddate" value=""/>
		<input type="hidden" id="t_auditresult" name="auditresult" value=""/>
		<input type="hidden" id="interfacecode" name="interfacecode" value="">
		<ul class="deptidLi">
			<li>
                <span><em class="red">* </em>公司名称：</span>
                <input id="companyid" name="companyid" type="hidden" value=""/>
			    <input id="companyname" name="companyname" class="{required:true} inputselectstyle" onclick="chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
			    <div class="search-trigger" onclick="chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
            </li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>部门名称：</span>
			    <input id="deptid" name="deptid" type="hidden" value=""/>
			    <input id="deptname" name="deptname" class="{required:true} inputselectstyle" onclick="chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val(),callbackDept);"/>
			    <div class="search-trigger" onclick="chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val(),callbackDept);"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>岗位：</span>
				<input id="postid" name="postid" type="hidden" value=""/>
			    <input id="postname" name="postname" class="{required:true} inputselectstyle" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
			    <div class="select-trigger" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>编制类型：</span>
				<input id="quotaid" name="quotaid" type="hidden"/>
				<input id="quotaname" name="quotaname" class="{required:true} inputselectstyle"  <div class="search-trigger" onclick="chooseQuotaTree('#quotaid','#quotaname',$('#postid').val());"/>
		    	<div class="search-trigger" onclick="chooseQuotaTree('#quotaid','#quotaname',$('#postid').val());"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>职级：</span>
				<input id="rankid" name="rankid" type="hidden" value=""/>
				<input id="rankname" name="rankname" class="{required:true} inputselectstyle"  onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());"/>
				<div class="search-trigger" onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>对应人数：</span>
				<input id="remainnum" name="remainnum" class="{required:true,number:true,maxlength:38}  inputstyle"  onblur="checkRemainNum();"/>
			</li>
		</ul>
	</form>
</div>
</body>
</html>
