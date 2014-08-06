<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<!DOCTYPE html>
<html>
<head>
<title>分职称认证员工数量统计表</title>
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
    searchGrid();
    
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
	mygrid.exportGrid('xls');
}

//打印
function printGrid(){
	mygrid.printGrid();
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
					$(this).dialog("close");
		    		mygrid.loadURL="report/searchPCD0206.do";
		    		mygrid.reload();
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






//公司回调
function callBackCompany(){
	$("#deptid").val(null);
	$("#deptname").val(null);
	
}




//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.userguid='${userid}';
	var companyid=$("#companyid").val();
	if(companyid!=null&&companyid!=''&&companyid!='null'){
		pam.companyid="'"+companyid+"'";
	}
	pam.deptid=$("#deptid").val();
	pam.workstate=1;
}





</script>




<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=13;
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'DEPTID'},
				{name : 'DEPTNAME'},
				{name : 'EMPLOYEE_COUNT'},
				{name : 'AUTHNAME_1'},
				{name : 'AUTHNAME_2'},
				{name : 'AUTHNAME_3'},
				{name : 'AUTHNAME_4'},
				{name : 'AUTHNAME_5'},
				{name : 'AUTHNAME_6'},
				{name : 'AUTHNAME_7'},
				{name : 'AUTHNAME_8'},
				{name : 'AUTHNAME_9'}
			]
		};
		var colsOption = [
			{id: 'DEPTNAME' , header: "部门名称" , width :150 ,headAlign:'center',align:'left'},
			${colsOption}
			{id: 'EMPLOYEE_COUNT' , header: "总人数" , width :120 ,headAlign:'center',align:'center'}
		];
	var gridOption={
		id : grid_demo_id,
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'report/searchPCD0206.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=$('input[name="xls"]:checked').val();
			mygrid.parameters=pam;
		},
		exportFileName : '分职称认证员工数量统计表.xls',
		width: "99.8%",//"100%", // 700,
		height: "100%",  //"100%", // 330,
		container : 'gridbox', 
		customHead : 'myHead1',
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
		skin:getGridSkin()
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}

</script>

</head>
<body>

<div class="sort">
	<div class="sort-title">
		<h3>分职称认证员工数量统计表</h3>
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
					<a class="btn" href="javascript:expGrid();"><i class="icon icon-list-alt"></i><span>导出</span></a>
					<a class="btn" href="javascript:printGrid();"><i class="icon icon-print"></i><span>打印</span></a>
				</div>
			</div>
			<div class="table-wrapper">
				<div id="myTable" style="height:550px;margin:5px auto;">
					<table id="myHead1" style="display:none">
						<tr>
							<td rowspan="2" columnId='DEPTNAME' align="center">部门名称</td>
							<td align="center" colspan="${optionNum }">专业技术职称</td>
							<td rowspan="2" columnId='EMPLOYEE_COUNT' align="center">总人数</td>
						</tr>
						<tr>
							<c:forEach items="${options}" var="item" varStatus="idx">
								<td columnId='AUTHNAME_${idx.index+1 }' align="center">${item.name}</td>
							</c:forEach>
						</tr>
					</table>
					<div id="gridbox" ></div>
                </div>
			</div>
		</div>
	</div>
</div>



<!-- 搜索 -->
<div id="search" title="搜索条件设置" style="display:none;">
	<form action="" id="searchForm" class="form">
		<ul>
			<li>
                <span><em class="red">* </em>公司名称：</span>
                <input id="companyid" name="companyid" type="hidden" value=""/>
			    <input id="companyname" name="companyname" class="{required:true} inputselectstyle" onclick="chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
			    <div class="search-trigger" onclick="chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
            </li>
		</ul>
		<ul>
			<li>
			    <span>部门名称：</span>
			    <input id="deptid" name="deptid" type="hidden" value=""/>
			    <input id="deptname" name="deptname" class="inputselectstyle" onclick="chooseOneCompanyMultipleDeptTree('#deptid','#deptname',$('#companyid').val());"/>
			    <div class="search-trigger" onclick="chooseOneCompanyMultipleDeptTree('#deptid','#deptname',$('#companyid').val());"/>
			</li>
		</ul>
        <ul>
			<li>
				<span>员工状态：</span>
                <input id="workstate" name="workstate" type="hidden" value=""/>
			    <input id="workstatename" name="workstatename" class="inputselectstyle" onclick="chooseMyMultipleOptionTree('#workstate','#workstatename','WORKSTATE');"/>
			    <div class="select-trigger" onclick="chooseMyMultipleOptionTree('#workstate','#workstatename','WORKSTATE');"/>
            </li>
        </ul>
	</form>
</div>









</body>
</html>