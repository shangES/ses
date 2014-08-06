<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>合同管理</title>
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
    
	
  //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
       	beforeShow: function(input, inst) {
   			if($(input).attr("readonly"))
   				inst.inline=true;
   		}
    });
	
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
	pam.userguid='${userid}';
	pam.companyid=$("#companyid").val();
	pam.deptid=$("#deptid").val();
	pam.postid=$("#postid").val();
	pam.name=$("#name").val();
	pam.contracttype=$("#contracttype").val();
	pam.state=$("#valid").attr("checked")?1:0;
	
	pam.enddate_s=$("#enddate_s").val();
	pam.enddate_e=$("#enddate_e").val();
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
function validContract(valid){
	var prompt=(valid==0?"失效":"恢复");
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		if(obj.state!=valid)
			array.push(obj.contractid);
	}
	if(array.length<=0){
		alert("请选择要"+prompt+"的数据！");
		return;
	}
	if(!confirm("确认要"+prompt+"选中数据吗？")){
		return;
	}
   	$.post("contract/validContractById.do",{ids:array.toString(),state:valid}, function() {
		mygrid.reload();
    });
}








//终止合同
var endContractForm=null;
function endContract(valid){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		if(obj.state!=valid)
			array.push(obj.contractid);
	}
	if(array.length<=0){
		alert("请选择要终止的数据！");
		return;
	}
	if(!confirm("确认要终止选中数据吗？")){
		return;
	}
	$("#endContract").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		buttons: {
			"确定":function(){
				if(endContractForm.form()){
					var enddate=$('#enddate').val();
					var memo=$('#memo').val();
					$.post("contract/endContractById.do",{ids:array.toString(),state:valid,enddate:enddate,memo:memo}, function() {
						mygrid.reload();
				    });
					$(this).dialog("close");
				}
			},
			"重置":function(){
				$("#endContractForm").clearForm();
			},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			if(endContractForm==null)
				endContractForm=$("#endContractForm").validate();
		}
	});
}



//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].contractid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	
	$.post("contract/delContractById.do",{ids:array.toString()}, function() {
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
function validOneContract(id,valid){
	var prompt=(valid==0?"失效":"恢复");
	var array=new Array();
	array.push(id);
	if(!confirm("确认要"+prompt+"选中数据吗？")){
		return;
	}
   	$.post("contract/validContractById.do",{ids:array.toString(),state:valid}, function() {
		mygrid.reload();
    });
}





//部门选择回调
function callbackDept(){
	$("#postid").val(null);
	$("#postname").val(null);
}






//公司回调
function callBackCompany(){
	$("#deptid").val(null);
	$("#deptname").val(null);
	
	//部门选择回调
	callbackDept();
}
</script>


<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'contractid'},
				{name : 'employeeid'},
				{name : 'contractcode'},
				{name : 'startdate'},
				{name : 'enddate'},
				{name : 'contracttype'},
				{name : 'signdate'},
				{name : 'hourssystem'},
				{name : 'content'},
				{name : 'memo'},
				{name : 'state'},
				{name : 'modiuser'},
				{name : 'moditimestamp'},
				{name : 'modimemo'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'state' , header: "操作" , width :80 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				var htm='';
        			htm+='<a href="javascript:convertView(\'contract.do?page=form&edit=${edit}&id='+record.contractid+'\');" class="ui-button ui-button-icon-only" title="修改"><span class="ui-icon ui-icon-pencil"></span>&nbsp;</a>';
       			if(value==1)
	        		htm+='<a href="javascript:validOneContract(\''+record.contractid+'\',0);" class="ui-button ui-button-icon-only" title="置为无效"><span class="ui-icon ui-icon-trash"></span>&nbsp;</a>';
	        	else if(value==0||value==2)
	        		htm+='<a href="javascript:validOneContract(\''+record.contractid+'\',1);" class="ui-button ui-button-icon-only" title="还原"><span class="ui-icon ui-icon-check"></span>&nbsp;</a>';
				return htm;
			}},
			{id: 'zt' , header: "状态" , width :60 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				if(record.state==0){
					return '<font color="#fff000">失效</font>';
				}else if(record.state==1){
					return '<font color="green">正常</font>';
				}else if(record.state==2){
					return '<font color="#ff0000">终结</font>';
				}
				return value;
			}},
			{id: 'companyname' , header: "公司" , width :150,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门" , width :200,headAlign:'center',align:'left'},
			{id: 'postname' , header: "岗位" , width :100,headAlign:'center',align:'left'},
			{id: 'jobnumber' , header: "员工工号" , width :140,headAlign:'center',align:'left'},
			{id: 'name' , header: "姓名" , width :80,headAlign:'center',align:'left'},
			{id: 'contractcode' , header: "合同编号" , width :150 ,headAlign:'center',align:'left'},
			{id: 'contracttypename' , header: "合同类型" , width :150 ,headAlign:'center',align:'left'},
			{id: 'signdate' , header: "签订日期" , width :100 ,headAlign:'center',align:'center'},
			{id: 'startdate' , header: "生效日期" , width :100 ,headAlign:'center',align:'center'},
			{id: 'enddate' , header: "到期时间" , width :100 ,headAlign:'center',align:'center'},
			{id: 'hourssystemname' , header: "合同工时" , width :150 ,headAlign:'center',align:'center'},
			{id: 'content' , header: "合同内容" , width :250 ,headAlign:'center',align:'left'},
			{id: 'memo' , header: "备注" , width :250 ,headAlign:'center',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'contract/searchContract.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'contract/searchContract.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=1;
			mygrid.parameters=pam;
		},
		exportFileName : '合同信息表.xls',
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
				convertView('contract.do?page=form&id='+record.contractid+'&edit=${edit}');
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
		<h3>合同管理</h3>
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
							<input id="valid" type="checkbox" class="checkboxstyle" onclick="mygrid.reload();" checked="true"/> 只显示当前有效合同
						</label>
						&nbsp;
						&nbsp;
					</div>
					<a class="btn" href="javascript:searchGrid();" style="display:${search?'':'none'}"><i class="icon icon-search"></i><span>搜索</span></a>
			   		<a class="btn" href="javascript:convertView('contract.do?page=import');" style="display:${imp?'':'none'}" ><i class="icon icon-qrcode"></i><span>导入</span></a>
					<a class="btn" href="javascript:expGrid();" style="display:${exp?'':'none'}"><i class="icon icon-download" ></i><span>导出</span></a>
					<a class="btn" href="javascript:convertView('contract.do?page=form&edit={edit}');"  style="display:${add?'':'none'}"><i class="icon icon-plus"></i><span>新增</span></a>
					<a class="btn"  href="javascript:endContract(2);" style="display:${end?'':'none'}"><i class="icon icon-stop"></i><span>终止合同</span></a>
					<a class="btn"  href="javascript:validContract(0);" style="display:${valid?'':'none'}" ><i class="icon icon-trash"></i><span>失效</span></a>
		   			<a class="btn"  href="javascript:validContract(1);" style="display:${revert?'':'none'}" ><i class="icon icon-retweet"></i><span>恢复</span></a>
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
<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" src="" scrolling="no" style="display:none;"></iframe>


<!-- 搜索 -->
<div id="search" title="搜索条件设置" style="display:none;">
	<form action="" id="searchForm" class="form">
		<ul id="myCompany">
			<li>
                <span>公司名称：</span>
                <input id="companyid" name="companyid" type="hidden" value=""/>
			    <input id="companyname" name="companyname" class="inputselectstyle" onclick="chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
			    <div class="search-trigger" onclick="chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
            </li>
        </ul>
        <ul>
			<li>
			    <span>部门名称：</span>
			    <input id="deptid" name="deptid" type="hidden" value=""/>
			    <input id="deptname" name="deptname" class="inputselectstyle" onclick="chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val(),callbackDept);"/>
			    <div class="search-trigger" onclick="chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val(),callbackDept);"/>
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
				<span>姓名：</span>
				<input id="name" name="name" class="inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>合同类型：</span>
				<input id="contracttype" name="contracttype" type="hidden" value=""/>
			    <input id="contracttypename" name="contracttypename" class="inputselectstyle" onclick="chooseOptionTree('#contracttype','#contracttypename','CONTRACTTYPE');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#contracttype','#contracttypename','CONTRACTTYPE');"/>
			</li>
		</ul>
		<ul>
         	<li style="display:inline;">
			    <span>合同终止日期从：</span>
			    <input id="enddate_s" name="enddate_s" class="{dateISO:true} inputselectstyle datepicker" style="width:80px;"/>
			    <div class="date-trigger" onclick="$('#enddate_s').focus();"/>
			</li>
            <li style="display:inline;">
			    <span style="width:16px;">至&nbsp;</span>
			     <input id="enddate_e" name="enddate_e" class="{dateISO:true} inputselectstyle datepicker" style="width:80px;"/>
			    <div class="date-trigger" onclick="$('#enddate_e').focus();"/>
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

<div id="endContract" title="合同终止时间设置" style="display:none;">
	<form action="" id="endContractForm" class="form">
		<ul>
			<li>
				<span><em class="red">* </em>到期时间：</span>
				<input id="enddate" name="enddate" class="{dateISO:true,required:true}  inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#enddate').focus();"/>
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
