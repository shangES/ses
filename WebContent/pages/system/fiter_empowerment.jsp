<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
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
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
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

<script type="text/javascript">
$(document).ready(function() {
	
	//tab页
	loadTab();
	
	//加载布局
	$('#myContent').layout({
        west: {size:"300",resizable:false,spacing_open:0,spacing_closed:0}
    });
	$('#myWest').layout({
        north: {size:"28",resizable:false,spacing_open:0,spacing_closed:0}
    });
	
	//列表加载
    loadGrid();
	
  	//加载树
   buildMyUserTree();
  	
});



//tab页
 var tabIndex=0;
function loadTab(){
	$("#mytab").tabs({
    	select: function(event, ui) {
    		tabIndex=ui.index;
    		$(".gruop_hidden").hide();
    		$("#group"+tabIndex).show();
    		$("#tab"+tabIndex).show();
    		mygrid.reload();
    	}
    });
} 




//树
var zTree;
//选中节点
var selectNode=null;
var userid=null;
function buildMyUserTree(){
	var setting = {
		async: {
			enable: true,
			url: getUrl
		},
		callback:{
			beforeClick: function(treeId, treeNode){
				selectNode=treeNode;
				userid=treeNode.id;
				//只能新增
				if(treeNode.code!='user'){
					$('#b1').show();
					$(".save").hide();
				}else if(treeNode.code=='user'){
					$('#b1').hide();
					$("#group5").show();
					$(".save").show();
					
					mygrid.reload();
					//取数据
					loadData(treeNode.id);
					
				}
			}
		},
		onAsyncSuccess: onAsyncSuccess,
		onAsyncError: onAsyncError,
		view: {
			fontCss: getDeptFontCss
		}
	};
	$.getJSON("system/buildMyDepartmentTree.do",function(tdata) {
		zTree = $.fn.zTree.init($("#tree"),setting, tdata);
		
		//邦定定位事件
    	$("#myMarker").bind("keyup",markTreeNode);
    	$("#myMarker").bind("blur",markSelectTreeNode);
    });
}


//异步数据
function getUrl(treeId, treeNode) {
	return "system/asyncUserTree.do?deptid=" + treeNode.id;
}
function onAsyncSuccess(event, treeId, treeNode, msg) {
	if(!msg || msg.length == 0) {return;}
	zTree.updateNode(treeNode);
}
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
}

//======================
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

//==========================================






//取数据
 function loadData(tid) {
	$("#myForm").clearForm();
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("system/getUserById.do", {id:tid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#' + key);
			        if(el) 
			            el.val(data[key]);
			    }
			}
		});
} 


//新增
var add=false;
function addNode(){
	$('#b1').hide();
	formDisabled(false);
	add=true;
	
	$('#state').val(1);
	
	//所属组织
	var srcNode=selectNode;
	if(srcNode!=null&&srcNode.code=="user")
		srcNode=srcNode.getParentNode();
}





//导出
function expGrid(){
	window.open("system/exportUser.do");
}

</script>




<script type="text/javascript">
//新增筛选列表的数据
var add=false;
var filterguid;
var filterForm=null
function addOrUpdateFilter(state,filterguid){
	add=state;
	$("#addFilterWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:500,
		buttons: {
			"确定": function() {
				$("#filterForm input[id=filterguid]").val(filterguid);
				saveFilter();
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			$("#filterForm").clearForm();
			formDisabled(false,"#filterForm");
			if(!add){
				loadFilter(filterguid);
			}
			filterForm=$("#filterForm").validate({submitHandler: function(form) {
		    	$(form).ajaxSubmit(function(data) {
		    		mygrid.reload();
		    		$("#addFilterWindow").dialog("close");
		        });
			}
			});
		}
	});
}


//通过列表的id加载数据
function loadFilter(filterguid){
	$.getJSON("system/getResumeFilterById.do",{id:filterguid},function(data){
		for(var key in data){
			if($('#filterForm input[id='+key+']')){
				$('#filterForm input[id='+key+']').val(data[key]);
			}
		}    				
	});
}


//保存
function saveFilter(){
	$("#filterForm").submit();
}








//全选
function selectAll(el){
	var input=$(el);
	$("input[name='mychecked']").attr("checked",input.attr("checked"));
	
}


//保存赋权(用户赋予筛选条件)
function saveUserFilter(){
	var userguid= userid;
	if(userguid==null||userguid=='')
		return;
	
	var array=new Array();
	$("input[name='mychecked']:checked").each(function(){
		array.push($(this).val());
	});
	
	//参数
	var data={userguid:userguid,list:array};
	$.ajax({  
        url: "system/saveUserFilter.do",  
        contentType: "application/json; charset=utf-8",  
        type: "POST",  
        dataType: "json",  
        data: JSON.stringify(data),
        success: function(result) { 
        	alert('授权成功！');
        }
    });
}


</script>


<script type="text/javascript">
//筛选列表
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'filterguid'},
				{name : 'filterconditions'},
				{name : 'code'},
				{name : 'tablecolumnname'}
			]
		};
	var colsOption = [
			{id: 'filterguid' ,headAlign:'center',align:'center',width :50,sortable:false,hdRenderer:function(header,colObj,grid){
				return '<input type="checkbox" onclick="selectAll(this);"/>';
			},renderer:function(value,record,colObj,grid,colNo,rowNo){
				var htm='&nbsp;';
				htm+='<label for="'+record.filterguid+'">';
				if(record.checked==true)
   			  		htm+='<input name="mychecked" value="'+record.filterguid+'" type="checkbox" checked/>';
  			  	else
   			  		htm+='<input name="mychecked" value="'+record.filterguid+'" type="checkbox"/>';
   			  	htm+='</label>';
 			  	return htm;
			}},
          	{id: 'tablecolumnname' , header: "列名" ,headAlign:'center',align:'left', width :120},
      	   	{id: 'code' , header: "符号",headAlign:'center',align:'center', width :70},
      	   	{id: 'filterconditions' , header: "筛选条件",headAlign:'center', width :250},
      	  	{id: 'memo' , header: "条件备注",headAlign:'center', width :250}
      	];
	var gridOption={
		id : grid_demo_id,
		loadURL : 'system/searchResumeFilter.do',
		beforeLoad:function(reqParam){
			reqParam['parameters']={'userid':userid};
		},
		width:'99.5%',
		height:"500",  //"100%", // 330,
		container : 'gridbox',
		autoLoad:true,
		selectRowByCheck:true,
		replaceContainer : true,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'nav | pagesize  state',
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		pageSize:size,
		skin:getGridSkin(),
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				addOrUpdateFilter(false,record.filterguid);
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
					表格名称
				</div>
				<div class="table-ctrl">
					<!-- 基本信息-->
					<span id="group0" class="gruop_hidden">
					   	<span id="b1">
					   		<a class="btn" href="javascript:expGrid();"><i class="icon icon-download"></i><span>导出</span></a>
				   		</span>
				   	</span>
				   	<!-- 筛选赋权-->
				   	<span id="group5" class="gruop_hidden" style="display:none;">
						<a class="btn save" href="javascript:addOrUpdateFilter(true);" ><i class="icon icon-plus"></i><span>新增</span></a> 
						<a class="btn save" href="javascript:saveUserFilter();"><i class="icon icon-check"></i><span>保存</span></a>
				   	</span>
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
					<div class="ui-layout-center" style="border:0px;">
						<ul id="tree" class="ztree"></ul>
					</div>
				</div>
				<div class="ui-layout-center">
					<div id="mytab">
						<ul>
							<li><a href="#tab0">筛选赋权</a></li>
						</ul>
						<div id="tab0">
							<div style="padding-top:5px;">
								<div id="myTable" style="margin:0px auto;height:500px;">
									<div id="gridbox" ></div>
				                </div>
			                </div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		
		
		
<!-- 添加窗口 -->
<div id="addFilterWindow" title="添加筛选列表" style="display:none">
	<form action="system/saveOrUpdateFilter.do" id="filterForm"  method="post" class="form">
		<input type="hidden" id="filterguid" name="filterguid" value=""/>
		<ul>
			<li>
				<span><em class="red">* </em>列名：</span>
				<input id="tablecolumnname" name="tablecolumnname" class="{required:true,maxlength:100} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>符号：</span>
				<input id="code" name="code" class="{required:true,maxlength:25} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>筛选条件：</span>
				<input  id="filterconditions" name="filterconditions" class="{required:true,maxlength:100} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>条件备注：</span>
			    <textarea id="memo" name="memo" rows="3" cols="40" class="{required:true,maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>		
		
		
		
		
		
</body>
</html>
