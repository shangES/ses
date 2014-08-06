<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>部门管理</title>
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
<script type="text/javascript" src="pages/department/post.js"></script>

<script type="text/javascript" src="pages/tree.js"></script>
<script type="text/javascript" src="pages/treedrag.js"></script>

<script type="text/javascript">

var edit='${edit}';

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
	
  	//加载树
    buildDepartmentTree();
  	
  	//获取岗位信息
  	loadGrid();
    
  //加载表单验证
    $("#myForm").validate();
    $('#myForm').ajaxForm(function(data) {
        //节点处理
    	var nameValue=data.deptname;
    	
    	if(add){//新增
    		var newNode = {id:data.deptid, name:nameValue,iconSkin:"dept",code:"dept",state:1,ename:data.companyid,deptcode:data.deptcode};
       		zTree.addNodes(selectNode, newNode);
        }else{
        	selectNode.name = nameValue;
   			zTree.updateNode(selectNode);
   			zTree.selectNode(selectNode);
        }
    	add=false;
    	//不可编辑
    	formDisabled(true);
    	//取消状态
    	onCancel();
    });
    
  	//把表单置为不可编辑
    formDisabled(true);
  	

    //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
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
				
    		
    		

			//控制tab
			if(selectNode!=null&&selectNode.code=='dept'){
				if(tabIndex==1){
					mygrid.reload();
				}
			}else{
				$("#group"+tabIndex).hide();
			}
    	}
    });
}







//树
var zTree;
//选中节点
var selectNode=null;
function buildDepartmentTree(){
//配置
var setting = {
	edit: {
		drag: {
			autoExpandTrigger: true,
			prev: dropPrev,
			inner: dropInner,
			next: dropNext
		},
		enable: true,
		showRemoveBtn: false,
		showRenameBtn: false
	},
	callback:{
		beforeDrag: beforeDrag,
		beforeDrop: beforeDrop,
		beforeDragOpen: beforeDragOpen,
		onDrag: onDrag,
		onDrop: onDrop,
		beforeClick: function(treeId, treeNode) {
			
		$("#myForm").clearForm();
		selectNode=treeNode;
		formDisabled(true);
		
		//节点权限
		//公司节点且有权限
		if(treeNode.code!='dept'&&!treeNode.isaudit){
			$('#b1').hide();
			$('#b2').hide();
			return;
		}
		
		//出现按钮组一
		$('#b1').show();
		$('#b2').hide();

		//只能新增
		if(treeNode.code!='dept'){
			if(${add==true})
				$('#new').show();
			$('#edit').hide();
			$('#del').hide();
			$("#recover").hide();
			$("#remove").hide();
			$(".post").hide();
		}else if(treeNode.code=='dept'){
			//有效的
			if(treeNode.state==1){
				if(${add==true})
					$('#new').show();
				if(${edit==true})
					$('#edit').show();
				if(${del==true})
					$('#del').show();
				$("#recover").hide();
				$("#remove").hide();
			}else{
				$('#edit').hide();
				$('#new').hide();
				$('#del').hide();
				if(${revert==true})
					$("#recover").show();
				if(${valid==true})
					$("#remove").show();
			}
			$(".post").show();
			//取数据
			loadData(treeNode.id);
			
		}else{
			$('#edit').hide();
			$('#new').hide();
			$('#del').hide();
			$("#recover").hide();
			$("#remove").hide();
		}

	}},
	view: {
		fontCss: getDeptFontCss
	}};
	var myvalid=$("#myvalid").attr("checked")?1:0;
    $.getJSON("department/buildMyDepartmentTree.do",{valid:myvalid}, function(tdata) {
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


//节点排序
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	if(treeNodes.lenght>0){
		alert("请选择1个节点进行排序！");
		return;
	}
	if(targetNode==null	)
		return;
	selectNode=treeNodes[0];
	$.post("department/orderDepartment.do",{id:selectNode.id,targetid:targetNode.id,moveType:moveType}, function() {
		
		
    });
}
//==========================================









//参数设置
var pam=null;
function initPostPagePam(){
	pam={};
	pam.expAll=0;
	if(selectNode!=null){
		pam.deptid=selectNode.id;
	}
}


//加载数据
function loadData(tid){
	$("#myForm").clearForm();
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("department/getDepartmentById.do", {id:tid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#' + key);
			        if(el) 
			            el.val(data[key]);
			    }
				
				
				//控制tab
				if(selectNode!=null&&selectNode.code=='dept'){
					$("#group"+tabIndex).show();
					if(tabIndex==1){
						mygrid.reload();
					}
				}else{
					$("#group"+tabIndex).hide();
				}
			}
		});
}


//新增
var add=false;
function addNode(){
	$('#b1').hide();
	$('#b2').show();
	$("#myForm").clearForm();
	formDisabled(false);
	add=true;
	
	$("#myForm #state").val(1);
	$("#modiuser").val("${username}");
	
	
	
	//设置上级
	if(selectNode!=null&&selectNode.code=='dept'){
		var pdeptid=selectNode.id;
		if(pdeptid!=null&&pdeptid!=''){
			$('#pdeptid').val(pdeptid);
		}
		$("#companyid").val(selectNode.ename);
	}else{
		$("#companyid").val(selectNode.id);
	}
}






//编辑
function editNode(){
	$('#b1').hide();
	$('#b2').show();
	formDisabled(false);
}


//保存
function save(){
	$('#myForm').submit();
}





//取消
function onCancel(){
	//取数据
	if(selectNode!=null){
		if(selectNode.code=='dept'){
			loadData(selectNode.id);
			$('#b1').show();
			$('#b2').hide();
			
			$('#del').show();
			$('#edit').show();
			$('#new').show();
			$("#recover").hide();
			$("#remove").hide();
		}else{
			$('#b1').show();
			$('#b2').hide();
			
			$('#new').show();
			$('#edit').hide();
			$('#del').hide();
			$("#recover").hide();
			$("#remove").hide();
		}
	}
	formDisabled(true);
}






//删除
function delNode(){
	
	$.post("employee/getPositionByDeptCode.do",{deptcode:selectNode.deptcode},function(data){
		if(!data){
			if(!confirm('确认要删除吗？')){
				return;
			}
			$.post("department/delDepartmentByDepartmentId.do",{id:selectNode.id}, function() {
				zTree.removeNode(selectNode);
				$("#myForm").clearForm();
					
		 		$('#b1').show();
				$('#b2').hide();
				
				$('#del').hide();
				$('#edit').hide();
				$('#new').hide();
		 		formDisabled(true);
		    });
		}else{
			alert("部门下面有员工任职信息,不能删除");
		}
	});
}





//无效
function validDepartment(valid){
	if(selectNode==null)
		return;
	if(!confirm('确认要把选中的部门置为无效吗？')){
		return;
	}
	$.post("department/validDepartmentById.do",{Departmentid:selectNode.id,valid:valid}, function() {
		selectNode.iconSkin = "remove";
		selectNode.state = 0;
		zTree.updateNode(selectNode);
		
		$('#b1').show();
		$('#b2').hide();
		
		$('#del').hide();
		$('#edit').hide();
		$('#new').hide();
		$("#recover").show();
		$("#remove").show();
		formDisabled(true);
  });
}





//还原
function recoverDepartment(valid){
	if(selectNode==null)
		return;
	$.post("department/validDepartmentById.do",{Departmentid:selectNode.id,valid:valid}, function() {
		
		selectNode.iconSkin = "dept";
		selectNode.state = 1;
		zTree.updateNode(selectNode);
		
		$('#b1').show();
		$('#b2').hide();
		
		$('#del').show();
		$('#edit').show();
		$('#new').show();
		$("#remove").hide();
		$("#recover").hide();
		formDisabled(true);
  });
}





//导出部门树
function expTree(){
	window.open("department/exportDepartment.do");
}
</script>

<script type="text/javascript">
//岗位列表
var mygrid=null;
function loadGrid(){
	var size=20;
	var wh=$(document.body).outerWidth()-360;
	$("#myTable").css({width:wh,height:460});
	
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'postid'},
				{name : 'companyid'},
				{name : 'deptid'},
				{name : 'jobid'},
				{name : 'postname'},
				{name : 'postcode'},
				{name : 'postduty'},
				{name : 'state'},
				{name : 'interfacecode'},
			    {name : 'memo'},
				{name : 'modiuser'},
			   	{name : 'moditimestamp'},
				{name : 'modimemo'}
			]
		};
	var colsOption = [
				{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center',filterable:false},
		        {id: 'state', header: "操作", width :60 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
		        	var htm='';
		        	htm+='<a href="javascript:saveOrUpdatePost(false,\''+record.postid+'\');" class="ui-button ui-button-icon-only" title="修改"><span class="ui-icon ui-icon-pencil"></span>&nbsp;</a>';
					if(value==1)
		        		htm+='<a href="javascript:recoverPost(\''+record.postid+'\',0);" class="ui-button ui-button-icon-only" title="置为无效"><span class="ui-icon ui-icon-trash"></span>&nbsp;</a>';
		        	else if(value==0)
		        		htm+='<a href="javascript:recoverPost(\''+record.postid+'\',1);" class="ui-button ui-button-icon-only" title="还原"><span class="ui-icon ui-icon-check"></span>&nbsp;</a>';
					return htm;
				}},
              	{id: 'postname' , header: "岗位名称" ,headAlign:'center',align:'left', width :120},
          		{id: 'jobidname' , header: "职务",headAlign:'center',align:'center', width :80},
          	  	{id: 'postduty' , header: "描述",headAlign:'left', width :200},
          		{id: 'memo' , header: "备注" ,headAlign:'left', width :200}
          	];
	var gridOption={
			id : grid_demo_id,
			loadURL : 'department/searchPost.do',
			beforeLoad:function(reqParam){
				initPostPagePam();
				reqParam['parameters']=pam;
			},
			exportURL : 'department/searchPost.do?export=true',
			beforeExport:function(){
				initPostPagePam();
				pam.expAll=$('input[name="xls"]:checked').val();
				mygrid.parameters=pam;
			},
			exportFileName : '岗位信息表.xls',
			width:'100%',
			height:"460",  //"100%", // 330,
			container : 'gridbox',
			autoLoad:false,
			stripeRows: false,
			showIndexColumn:false,
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
					saveOrUpdatePost(false,record.postid);
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
		<h3>部门管理</h3>
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
				
					<span id="group0" class="gruop_hidden">
						<div style="float:left;">
							<label for="myvalid">
								<input id="myvalid" type="checkbox" class="checkboxstyle" onclick="javascript:buildDepartmentTree();" checked="true"/> 只显示当前有效的部门
							</label>
							&nbsp;
							&nbsp;
						</div>
						<a id="exp" class="btn" href="javascript:expTree();" style="display:${exp?'':'none'}"><i class="icon icon-download"></i><span>导出</span></a>
					   	<span id="b1" style="display:none;">
							<a id="new" class="btn" href="javascript:addNode();" style="display:none;"><i class="icon icon-plus"></i><span>新增</span></a>
							<a id="edit" class="btn" href="javascript:editNode()" style="display:none;"><i class="icon icon-pencil"></i><span>修改</span></a>
							<a id="del"  class="btn"  href="javascript:validDepartment(0);" style="display:none;"><i class="icon icon-trash"></i><span>失效</span></a>
							<a id="recover" class="btn" href="javascript:recoverDepartment(1);" style="display:none;"><i class="icon icon-repeat"></i><span>恢复</span></a>
							<a id="remove"  class="btn" href="javascript:delNode();" style="display:none;"><i class="icon icon-remove"></i><span>删除</span></a>
				   		</span>
				   		<span id="b2" style="display:none;">
							<a class="btn"  href="javascript:save();"><i class="icon icon-check"></i><span>保存</span></a>
							<a class="btn"  href="javascript:onCancel();"><i class="icon icon-share"></i><span>取消</span></a>
				   		</span>
				   	</span>
				   	<span id="group1" class="gruop_hidden" style="display:none;">
				   		<a class="btn post" href="javascript:expPost();" style="display:none;"><i class="icon icon-download"></i><span>导出</span></a>
				   		<a class="btn post" href="javascript:saveOrUpdatePost(true);"  style="display:none;"><i class="icon icon-plus"></i><span>新增</span></a>
				   		<a class="btn post" href="javascript:invalidOrReductionPostById(0);" style="display:none;"><i class="icon icon-trash"></i><span>失效</span></a>
				   		<a class="btn post" href="javascript:invalidOrReductionPostById(1);" style="display:none;"><i class="icon icon-repeat"></i><span>恢复</span></a>
				   		<a class="btn post" href="javascript:delPostById();" style="display:none;"><i class="icon icon-remove"></i><span>删除</span></a>
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
				<div class="ui-layout-center" style="overflow:hidden;">
					<div id="mytab">
						<ul>
							<li><a href="#tab0">基本信息</a></li>
							<li style="display:none;"><a href="#tab1">岗位信息</a></li>
						</ul>
						<div id="tab0">
					        <form action="department/saveOrUpdateDepartment.do" method="post" id="myForm" class="form">
								<input id="deptid" name="deptid" type="hidden" value=""/>
								<input id="pdeptid" name="pdeptid" type="hidden" value=""/>
								<input id="companyid" name="companyid" type="hidden" value="" />
								<input id="deptcode" name="deptcode" type="hidden" value=""/>
								<input id="state" name="state" type="hidden" value="1" />
								<input id="modiuser" name="modiuser" type="hidden" value="${userid}" />
								<input id="modimemo" name="modimemo" type="hidden" value="" />
								
								<fieldset>
									<legend>基本信息</legend>
									<ul>
						            	<li>
						            		<span><em class="red">* </em>部门名称：</span>
										    <input id="deptname" name="deptname" class="{required:true,maxlength:40} inputstyle"/>
					                    </li>
						            </ul>
									<ul>
						            	<li>
						            		<span>代码：</span>
										    <input id="interfacecode" name="interfacecode" class="{maxlength:30} inputstyle"/>
					                    </li>
						            </ul>
						            <ul>
										<li>
										    <span>部门类型：</span>
										    <input id="depttype" name="depttype" type="hidden" value="" />
										    <input id="depttypename" name="depttypename" class="inputselectstyle" onclick="chooseOptionTree('#depttype','#depttypename','H_O_DEPARTMENT_DEPTTYPE');"/>
										    <div class="select-trigger" onclick="chooseOptionTree('#depttype','#depttypename','H_O_DEPARTMENT_DEPTTYPE');"/>
										</li>
						            </ul>
						            <ul>
										<li>
							                <span>部门体系：</span>
							                <input id="assesshierarchy" name="assesshierarchy" type="hidden" value=""/>
										    <input id="assesshierarchyname" name="assesshierarchyname" class="inputselectstyle" onclick="chooseOptionTree('#assesshierarchy','#assesshierarchyname','ASSESSHIERARCHY');"/>
							           		 <div class="search-trigger" onclick="chooseOptionTree('#assesshierarchy','#assesshierarchyname','ASSESSHIERARCHY');"/>
							            </li>
						            </ul>
						            <ul>
										<li>
										    <span><em class="red">* </em>部门职能：</span>
										    <input id="deptfunction" name="deptfunction" class="{required:true,maxlength:500} inputstyle"/>
										</li>
						            </ul>
						            <ul>
										<li>
										    <span><em class="red">* </em>部门职责：</span>
										    <input id="deptduty" name="deptduty" class="{required:true,maxlength:500} inputstyle"/>
										</li>
						            </ul>
						            <ul>
										<li>
										    <span>部门简介：</span>
									     	<textarea id="description" name="description" rows="3" cols="40" class="{maxlength:500} areastyle" ></textarea>
										</li>
						            </ul>
						            <ul>
										<li>
										    <span>备注：</span>
										     <textarea id="memo" name="memo" rows="3" cols="40" class="{maxlength:500} areastyle" ></textarea>
										</li>
						            </ul>
								</fieldset>
					       </form>
   							</div> 
				        <div id="tab1" style="display:none;">
				        	<div style="padding-top:5px;">
								<div id="myTable" style="margin:0px auto;">
									<div id="gridbox" ></div>
				                </div>
			                </div>
					    </div>
				    </div>
				</div>
				
			</div>
		</div>
	</div>
</div>


<!--  
	<div id="postWindow" title="岗位信息">
		<form action="department/saveOrUpdatePost.do" method="post" id="postForm" class="form">
			<input type="hidden" id="postid" name="postid" value=""/>
			<input type="hidden" id="companyid" name="companyid" value=""/>
			<input type="hidden" id="deptid" name="deptid" value=""/>
			<input type="hidden" id="state" name="state" value="1"/>
			<input type="hidden" id="modiuser" name="modiuser" value=""/>
			<input type="hidden" id="modimemo" name="modimemo" value=""/>
			<input type="hidden" id="moditimestamp" name="moditimestamp" value=""/>
			<input type="hidden" id="interfacecode" name="interfacecode" value=""/>
			<input type="hidden" id="postcode" name="postcode" value=""/>
			<input type="hidden" id="memo" name="memo" value=""/>
			
			<ul>
				<li>
					<span><em class="red">* </em>岗位名称：</span>
					<input id="postname" name="postname" class="{required:true,maxlength:30} inputstyle" value=""  onblur="checkPostname();"/>
				</li>
			</ul>
			<ul>
				<li>
					<span>职务：</span>
					<input id="jobid" name="jobid" type="hidden" class="inputstyle"/>
					<input id="jobidname" name="jobidname" class="inputselectstyle" onclick="chooseJobTree('#jobid','#jobidname',selectNode.ename);"/>
					<div class="search-trigger" onclick="chooseJobTree('#jobid','#jobidname',selectNode.ename);"/>
				</li>
			</ul>
			<ul>
				<li>
					<span>描述：</span>
					<textarea id="postduty" name="postduty" rows="3" cols="40" class="{maxlength:500} areastyle" ></textarea>
				</li>
			</ul>
		</form>
	</div>
-->



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

</body>
</html>