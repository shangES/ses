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
	pam.employeeid="${param.id}";
	pam.state=$("#valid").attr("checked")?1:0;
}









//得到数据
function loadContract(id){
	if(id!=null&&id!=''&&id!='null')
		$.getJSON("contract/getContractById.do", {id:id}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#' + key);
			        if(el) 
			            el.val(data[key]);
			    }
				
			}
		});
}









//新增或修改
var addContractForm=null;
function addOrEdit(id){
	$("#addContractWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		buttons: {
			"确定": function() {
				if(addContractForm.form){
					var startdate=$("#startdate").val();
					var enddate=$("#enddate").val();
					var contracttypename=$("#contracttypename").val();
					if((contracttypename!=''&&contracttypename!='无固定期限劳动合同')&&(enddate==null||enddate=='')){
						 alert("请输入到期时间!");
						 $("#contracttypename").focus();
						 return;
					}
					
					if(startdate!=null&&startdate!=''&&enddate!=null&&enddate!=''){
						 var sd = new  Date(Date.parse(startdate.replace(/-/g,"/")));
						 var ed = new  Date(Date.parse(enddate.replace(/-/g,"/")));
						 
						 if (sd -ed>0) {
							 alert("到期时间要大于生效日期");
							 $("#enddate").focus();
						 }else{
							 $("#addContractForm").submit();
						 }
					}else{
						$("#addContractForm").submit();
					}
					
					
					
					
					
					
					
					
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#addContractForm").clearForm();
			if(id!=null&&id!=''){
				loadContract(id);
				if(!edit){
					formDisabled(true);
				}else{
					formDisabled(false);
				}
				
			}else{
				$("#employeeid").val(tid);
				$("#state").val(1);
				$("#modiuser").val("${username}");
			}
			addContractForm=$("#addContractForm").validate({submitHandler: function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		mygrid.reload();
			    		$("#addContractWindow").dialog("close");
			        });
				}
			});
		}
	});
}








//导出
function expGrid(){
	mygrid.exportGrid('xls');
}








//返回
function back(){
	window.parent.convertView('');
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






//终止合同
var endContractForm=null;
function endContract(valid){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		if(obj.state!=valid&&obj.state!=0)
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
					var enddate=$('#e_enddate').val();
					var memo=$('#e_memo').val();
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
			{id: 'state' , header: "操作" , width :80 ,headAlign:'center',align:'center',hidden:${param.edit!=true},renderer:function(value ,record,colObj,grid,colNo,rowNo){
				var htm='';
	        		htm+='<a href="javascript:addOrEdit(\''+record.contractid+'\');" class="ui-button ui-button-icon-only" title="修改"><span class="ui-icon ui-icon-pencil"></span>&nbsp;</a>';
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
			{id: 'companyname' , header: "公司名称" , width :180 ,headAlign:'center',align:'left'},
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
				addOrEdit(record.contractid);
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
			合同信息
		</div>
		<div class="table-ctrl">
			
			<span id="group" class="gruop_hidden">
				<div style="float:left;">
					<label for="valid">
						<input id="valid" type="checkbox" class="checkboxstyle" onclick="mygrid.reload();" checked="true"/> 只显示当前有效合同
					</label>
				&nbsp;
				&nbsp;
				</div>
			
				<a class="btn" href="javascript:expGrid();"><i class="icon icon-download"></i><span>导出</span></a>
				<a class="btn" href="javascript:addOrEdit('');"><i class="icon icon-plus"></i><span>新增</span></a>
				<!--  <a class="btn"  href="javascript:validContract(0);"><i class="icon icon-trash"></i><span>失效</span></a>
		   		<a class="btn"  href="javascript:validContract(1);"><i class="icon icon-repeat"></i><span>恢复</span></a>-->
	   			<a class="btn"  href="javascript:endContract(2);"><i class="icon icon-stop"></i><span>终止合同</span></a>
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

<!-- 合同信息窗口 -->
<div id="addContractWindow" title="合同信息" style="display:none;">
	<form action="contract/saveOrUpdateContract.do" id="addContractForm" class="form" method="post">
		<input type="hidden" id="contractid" name="contractid" value=""/>
		<input type="hidden" id="employeeid" name="employeeid" value=""/>
		<input type="hidden" id="state" name="state" value="1"/>
		<input id="modiuser" name="modiuser" type="hidden" value="${username}" />
		<input id="modimemo" name="modimemo" type="hidden" value="" />
		<input id="memo" name="memo" type="hidden" value="" />
		
		<ul>
			<li>
                <span><em class="red">* </em>公司：</span>
                <input id="companyid" name="companyid" type="hidden" value=""/>
			    <input id="companyname" name="companyname" class="{required:true} inputselectstyle" onclick="chooseMyHasCompanyTree('#companyid','#companyname');"/>
			    <div class="search-trigger" onclick="chooseMyHasCompanyTree('#companyid','#companyname');"/>
            </li>
		</ul>
		
	    <ul>
			<li>
				<span>合同编号：</span>
				<input id="contractcode" name="contractcode" class="{maxlength:10} inputstyle" />
			</li>
		</ul>
	    
		<ul>
			<li>
				<span><em class="red">* </em>合同类型：</span>
				<input id="contracttype" name="contracttype" type="hidden" value=""/>
			    <input id="contracttypename" name="contracttypename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#contracttype','#contracttypename','CONTRACTTYPE');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#contracttype','#contracttypename','CONTRACTTYPE');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>签订日期：</span>
				<input id="signdate" name="signdate" class="{required:true,dateISO:true}  inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#signdate').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>生效日期：</span>
				<input id="startdate" name="startdate" class="{required:true,dateISO:true}  inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#startdate').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>到期时间：</span>
				<input id="enddate" name="enddate" class="{dateISO:true}  inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#enddate').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>合同工时：</span>
				<input id="hourssystem" name="hourssystem" type="hidden" value=""/>
			    <input id="hourssystemname" name="hourssystemname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#hourssystem','#hourssystemname','HOURSSYSTEM');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#hourssystem','#hourssystemname','HOURSSYSTEM');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>合同内容：</span>
				<textarea id="content" name="content" rows="3" cols="40" class="{maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>

<div id="endContract" title="合同终止时间设置" style="display:none;">
	<form action="" id="endContractForm" class="form">
		<ul>
			<li>
				<span><em class="red">* </em>到期时间：</span>
				<input id="e_enddate" name="e_enddate" class="{dateISO:true,required:true}  inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#e_enddate').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>备注：</span>
				<textarea id="e_memo" name="e_memo" rows="3" cols="40" class="{maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>

</body>
</html>
