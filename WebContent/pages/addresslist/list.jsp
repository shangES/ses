<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>通讯录管理</title>
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
$(document).ready(function() {
	
	//加载布局
	$('#myContent').layout({
        west: {size:"300",resizable:false,spacing_open:0,spacing_closed:0}
    });
	
	$('#myWest').layout({
        north: {size:"28",resizable:false,spacing_open:0,spacing_closed:0}
    });
	
	//加载信息
	loadGrid();
	
	//树的展示
	buildDepartmentTree();
	
	
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





//树
var zTree;
//选中节点
var selectNode=null;
function buildDepartmentTree(){
	//配置
	var setting = {
				callback:{
					beforeClick: function(treeId, treeNode) {
						$("#myForm").clearForm();
						selectNode=treeNode;
						
						if(!treeNode.isaudit&&treeNode.code=='company'){
							$("#group").hide();
						}else{
							$("#group").show();
						}
						
						
						mygrid.reload();
					}
				},
				view: {
					fontCss: getDeptFontCss
				}
			};

	
    $.getJSON("department/buildMyDepartmentTree.do",{valid:1}, function(tdata) {
    	zTree = $.fn.zTree.init($("#tree"),setting, tdata);
    	
    	//邦定定位事件
    	$("#myMarker").bind("keyup",markTreeNode);
    	$("#myMarker").bind("blur",markSelectTreeNode);
    });
}



//节点搜索定位
function markSelectTreeNode(){
	var value="";
	value = $.trim($("#myMarker").val());
	if (value != ""){
		nodeList = zTree.getNodesByParamFuzzy("name", value);
		if(nodeList.length<=0)
			return;
		for( var i=0, l=nodeList.length; i<l; i++) {
			var node=nodeList[i];
			if(i==0){
				zTree.selectNode(node);
			}
		}
	}
}


//节点搜索变色
function markTreeNode(e) {
	if(!nodeState){
		nodeState=true;
		var value="";
		value = $.trim($("#myMarker").val());
		
		changeNodesColor(false);
		if (value === ""){
			nodeState=false;
			return;
		}
		nodeList = zTree.getNodesByParamFuzzy("name", value);
		changeNodesColor(true);
		nodeState=false;
	}
}
function changeNodesColor(highlight) {
	if(nodeList.length<=0)
		return;
	for( var i=0, l=nodeList.length; i<l; i++) {
		var node=nodeList[i];
		node.highlight = highlight;
		zTree.updateNode(nodeList[i]);
	}
}
function getDeptFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#ff0000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

//===============






//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	
	pam.userguid='${userid}';
	pam.name=$("#name").val();
	pam.jobnumber=$("#jobnumber").val();
	pam.innerphone=$("#innerphone").val();
	
	if(selectNode!=null){
		if(selectNode.code=='dept'){
			pam.deptcode=selectNode.deptcode;
		}else if(selectNode.code='company'){
			pam.companycode=selectNode.companycode;
		}
	}
	
	
	
	
}






//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.addresslistguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
   	$.post("addresslist/delAddressListById.do",{ids:array.toString()}, function() {
		mygrid.reload();
    });
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






//部门选择回调
function callbackDept(){
	$("#postid").val(null);
}





//同步员工
function refresh(){
	$.post("addresslist/refreshAddressList.do",function() {
		alert("员工信息同步成功！");
    });
}





//切换视图
function convertView(url){
	if ($(".sort").css("display")!="none") {
		$(".sort").hide();
		$("#detail").show();
		
		if(selectNode!=null&&selectNode.code=='dept'){
			var companyid=selectNode.ename;
			var companyname=selectNode.marker;
			var deptid=selectNode.id;
			var deptname=selectNode.name;
			var companycode=selectNode.companycode;
			var deptcode=selectNode.deptcode;
			url+='&companyid='+companyid+'&deptid='+deptid+'&companycode='+companycode+'&deptcode='+deptcode+'&companyname='+companyname+'&deptname='+deptname;
		}
		
		$("#detail").attr("src",encodeURI(url));
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
				{name : 'addresslistguid'},
				{name : 'employeeid'},
				{name : 'employeecode'},
				{name : 'companyid'},
				{name : 'companycode'},
				{name : 'deptid'},
				{name : 'deptcode'},
				{name : 'postid'},
				{name : 'postcode'},
				{name : 'dorder'},
				{name : 'jobnumber'},
				{name : 'name'},
				{name : 'sex'},
				{name : 'innerphone'},
				{name : 'extphone'},
				{name : 'mobilephone'},
				{name : 'email'},
				{name : 'officeaddress'},
				{name : 'refreshtimestamp'},
				{name : 'memo'},
				{name : 'modiuser'},
				{name : 'moditimestamp'},
				{name : 'modimemo'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'dorder' , header: "排序号", width :80 ,headAlign:'center',align:'left'},
			{id: 'jobnumber' , header: "工号", width :110 ,headAlign:'center',align:'left'},
			{id: 'name' , header: "姓名", width :100 ,headAlign:'center',align:'left'},
			{id: 'sexname' , header: "性别", width :80 ,headAlign:'center',align:'center'},
			{id: 'extphone' , header: "办公电话", width :100 ,headAlign:'center',align:'left'},
			{id: 'innerphone' , header: "内网电话" , width :80 ,headAlign:'center',align:'left'},
			{id: 'mobilephone' , header: "手机", width :120 ,headAlign:'center',align:'left'},
			{id: 'mobilephone2' , header: "备用手机", width :120 ,headAlign:'center',align:'left'},
			{id: 'email' , header: "邮箱", width :200 ,headAlign:'center',align:'left'},
			{id: 'officeaddress' , header: "办公地址", width :220 ,headAlign:'center',align:'left'},
			{id: 'postname' , header: "岗位名称", width :120 ,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门名称", width :300 ,headAlign:'center',align:'left'},
			{id: 'companyname' , header: "公司名称" , width :250 ,headAlign:'center',align:'left'},
			{id: 'memo' , header: "备注", width :300 ,headAlign:'center',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'addresslist/searchAddressList.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'addresslist/searchAddressList.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=1;
			mygrid.parameters=pam;
		},
		exportFileName : '通讯录信息表.xls',
		width:'99.8%',
		height:"542",  //"100%", // 330,
		container : 'gridbox', 
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		autoLoad:false,
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
				convertView('addresslist.do?page=form&id='+record.addresslistguid);
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
					表格名称
				</div>
				<div class="table-ctrl">
					<a class="btn" href="javascript:searchGrid();" style="display:${search?'':'none'}" ><i class="icon icon-search"></i><span>搜索</span></a>
			   		<a class="btn" href="javascript:convertView('addresslist.do?page=import');" style="display:${imp?'':'none'}" ><i class="icon icon-qrcode"></i><span>导入</span></a>
				   	<a class="btn" href="javascript:expGrid();" style="display:${exp?'':'none'}" ><i class="icon icon-list-alt"></i><span>导出</span></a>
					<span id="group" style="display:none;">
				   		<a  class="btn" href="javascript:convertView('addresslist.do?page=form');" style="display:${add?'':'none'}"><i class="icon icon-plus"></i><span>新增</span></a>
						<a  class="btn" href="javascript:remove();" style="display:${del?'':'none'}" ><i class="icon icon-remove" ></i><span>删除</span></a>
					</span>
					<a class="btn" href="javascript:refresh();" style="display:${sync?'':'none'}" ><i class="icon icon-refresh"></i><span>批量同步</span></a>
				</div>
			</div>
			<div class="table-wrapper" id="myContent" style="height:550px;">
				<div class="ui-layout-west" id="myWest">
					<div class="ui-layout-north">
						<table width="100%" height="100%" border="0">
							<tr>
								<td style="padding-left:1px;">
									<input class="myMarker" id="myMarker"/>
								</td>
							</tr>
						</table>
					</div>
					<div class="ui-layout-center" style="border-right:0px;">
						<ul id="tree" class="ztree"></ul>
					</div>
				</div>
				<div class="ui-layout-center">
					<div id="myTable" style="padding:2px;margin:0px auto;">
						<div id="gridbox" ></div>
	                </div>
				</div>				
			</div>
		</div>
	</div>
</div>

<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" src="" scrolling="no" style="display:none;"></iframe>


<!-- 导出窗口 -->
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


<!-- 搜索 -->
<div id="search" title="搜索条件设置" style="display:none;">
	<form action="" id="searchForm" class="form">
		<ul>
			<li>
                <span>员工姓名：</span>
                <input id="name" name="name" class="inputstyle"/>
            </li>
        </ul>
		<ul>
			<li>
                <span>员工工号：</span>
                <input id="jobnumber" name="jobnumber" class="inputstyle"/>
            </li>
        </ul>
		<ul>
			<li>
                <span>内网电话：</span>
                <input id="innerphone" name="innerphone" class="inputstyle"/>
            </li>
        </ul>
	</form>
</div>



</body>
</html>