<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>通讯录数据导入</title>
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
$(document).ready(function() {
	//加载数据
    loadGrid();
	
  //默认exls导入
    checkUploadForm();
    impXLS();
	
});






//导入层
function impXLS(){
	$("#impXLS").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:500,
		height:150,
		buttons: {
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			//清空记录
			mygrid.cleanContent();
			$("#error").html(null);
		}
	});
}





//exls校验
function checkUploadForm(){
	$('#uploadFile').ajaxForm({
		dataType :'json',
		success: function(file) {
			//清空记录
			mygrid.cleanContent();
			
			//渲染sheet
			var names=file.sheetNames;
			if(names!=null){
				var containt=$('#sheetnames');
				var htm='';
				for(var i=0;i<names.length;i++){
					var name=names[i];
					htm+='<li onclick="callbackSheetName(\''+name+'\');">';
					htm+='<span class="ui-button ui-button-text-icons">';
					htm+='<span class="ui-icon ui-icon-script"></span>';
					htm+='<span class="ui-button-text">';
					htm+=name;
					htm+='</span>';
					htm+='</span>';
					htm+='</li>';
				}
				containt.html(htm);
				
				//选择导入的sheet
				$("#selectSheet").dialog({
					autoOpen: true,
					modal: true,
					resizable:false,
					width:400,
					buttons: {
						"关闭": function() {
							$(this).dialog("close");
						}
					},
					close:function(){
						
						$("#impXLS").dialog("close");
						$("#error").html(null);
						$("#sheetname").val(null);
						$("#uploadFile").resetForm();
					}
				});
				return;
			}else
				$("#selectSheet").dialog("close");
			
			//检查数据有多少条出错记录
			var impData=file.data;
			var err=0;
			
			
			
			//检查
			for(var i=0;i<impData.length;i++){
				var obj=impData[i];
				if(obj.checkstate==1)
					err++;
				
			}
			
				//设置gird数据
				dataOk(impData,err);
		}
	}); 
}



//设置gird数据
function dataOk(impData,err){
	if(err>0)
		$('#error').html('有 '+err+' 条记录无法导入，请修正后重新导入！');
	mygrid.setContent(impData);
}






function callbackSheetName(name){
	$("#sheetname").val(name);
	$("#uploadFile").submit();
}




//保存导入
function saveImpGrid(){
	$("#save").hide();
	var error=0;
	var array=new Array();
	mygrid.forEachRow(function(row,record,i,grid){
		if(record.checkstate==1)
			error++;
		else
			array.push(record);
	});
	
	if(error>0){
		alert('有 '+error+' 条数据错误，请修正后再导入！');
		$("#save").show();
	}else if(array.length>=1){
		mygrid.setInsertedRecords(array);
		mygrid.save(true);
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
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'employeeid'},
				{name : 'name'},
				{name : 'sex'},
				{name : 'birthday'},
				{name : 'cardnumber'},
				{name : 'culture'},
				{name : 'nation'},
				{name : 'mobile'},
				{name : 'residenceplace'},
				{name : 'homephone'},
				{name : 'homeplace'},
				{name : 'bloodtype'},
				{name : 'domicilplace'},
				{name : 'nativeplace'},
				{name : 'politics'},
				{name : 'married'},
				{name : 'privateemail'},
				{name : 'photo'},
				{name : 'joinworkdate'},
				{name : 'joingroupdate'},
				{name : 'workstate'},
				{name : 'jobnumber'},
				{name : 'secrecy'},
				{name : 'dorder'},
				{name : 'classification'},
				{name : 'employtype'},
				{name : 'checknumber'},
				{name : 'joindate'},
				{name : 'startdate'},
				{name : 'enddate'},
				{name : 'officephone'},
				{name : 'email'},
				{name : 'shortphone'},
				{name : 'officeaddress'},
				{name : 'studymonth'},
				{name : 'officialdateplan'},
				{name : 'officialdate'},
				{name : 'officialmemo'},
				{name : 'resignationdate'},
				{name : 'resignationreason'},
				{name : 'interfacecode'},
				{name : 'contactphone'},
				{name : 'contactrelationship'},
				{name : 'contactname'},
				{name : 'memo'},
				{name : 'modiuser'},
				{name : 'moditimestamp'},
				{name : 'modimemo'},
				{name : 'secrecyname'},
				{name : 'sexname'},
				{name : 'culturename'},
				{name : 'nationname'},
				{name : 'bloodtypename'},
				{name : 'politicsname'},
				{name : 'marriedname'},
				{name : 'resignationreasonname'},
				{name : 'classificationname'},
				{name : 'employtypename'},
				{name : 'contactrelationshipname'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'checkstate', header: " ", width :30 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				if(value==1){
					return '<span class="ui-button ui-button-icon-only" title="'+record.msg+'"><span class="ui-icon ui-icon-close"></span>&nbsp;</span>';
				}
				return '';
			},resizable:false},
			{id: 'deptname' , header: "部门名称", width :300 ,headAlign:'center',align:'left'},
			{id: 'postname' , header: "岗位名称", width :120 ,headAlign:'center',align:'left'},
			{id: 'name' , header: "姓名", width :100 ,headAlign:'center',align:'left'},
			{id: 'jobnumber' , header: "工号", width :90 ,headAlign:'center',align:'left'},
			{id: 'extphone' , header: "办公电话", width :100 ,headAlign:'center',align:'left'},
			{id: 'innerphone' , header: "内网电话" , width :80 ,headAlign:'center',align:'left'},
			{id: 'mobilephone' , header: "手机", width :120 ,headAlign:'center',align:'left'},
			{id: 'email' , header: "邮箱", width :120 ,headAlign:'center',align:'left'},
			{id: 'companyname' , header: "公司名称", width :250 ,headAlign:'center',align:'left'},
			{id: 'officeaddress' , header: "办公地址", width :220 ,headAlign:'center',align:'left'},
			{id: 'memo' , header: "备注", width :250 ,headAlign:'center',align:'left'},
			{id: 'msg' , header: "问题描述" , width :700 ,headAlign:'center',headAlign:'left',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		saveURL :'addresslist/saveAddressListGrid.do',
		width: "99.9%",//"100%", // 700,
		height: "100%",  //"100%", // 330,
		container : 'gridbox', 
		stripeRows: false,
		showIndexColumn:true,
		selectRowByCheck:true,
		replaceContainer : true,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'state',
		skin:getGridSkin(),
		pageSize:1000,
		onComplete:function(){
		},
		saveResponseHandler:function(response,requestParameter){
			alert(response.text);
			$("#save").show();
			window.parent.convertView(null);
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}




//选择部门回调
function callBackOrg(treeNode){
	$("#orgguid").val(treeNode.pId);
}
</script>

</head>
<body>


<div class="sort">
	<div class="sort-title">
		<h3>通讯录管理</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>
	
	<div class="sort-cont sort-table">
		<div class="table">
			<div class="table-bar">
				<div class="table-title">
					通讯录数据导入
				</div>
				<div class="table-ctrl">
					<a class="btn" href="javascript:impXLS();"><i class="icon icon-list-alt"></i><span>选择数据</span></a>
					<a class="btn" href="javascript:saveImpGrid();"><i class="icon icon-check"></i><span>确定导入</span></a>
					<a class="btn" href="javascript:window.parent.convertView('');"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
				</div>
			</div>
			<div class="table-wrapper">
				<div id="myTable" style="height:600px;margin:5px auto;">
					<div id="gridbox" ></div>
					
					
					<!-- 错误提示 -->
					<div style="position: absolute;left:30px;bottom:20px;z-index:100;" > 
						信息:<span id="error"></span>
					</div>
                </div>
			</div>
		</div>
	</div>
</div>








<!-- 选择导入XLS -->
<div id="impXLS" style="display:none;" title="选择Microsoft Excel 工作表">
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<form id="uploadFile" action="yewu/imp.do" method="post" enctype="multipart/form-data">
			 		<input id="sheetname" type="hidden" name="sheetname"/>
			       	<input id="file" type="file" name="file[]" style="width:100%;height:25px;line-height:25px;padding-left:5px;" size="65" onchange="$('#uploadFile').submit();" />
			   	</form>
			</td>
		</tr>
	</table>
</div>



<!-- 选择导入的sheet -->
<div id="selectSheet" style="display:none;overflow:hidden;" title="选择导入的工作表" class="chooseApp">
  	<ul id="sheetnames">
  	
  	</ul>
</div>



<!-- 选择导入的组织 -->
<div id="selectRepeatOrg" style="display:none;overflow:hidden;" title="选择导入的组织">
  	
  	
</div>



</body>
</html>