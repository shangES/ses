<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>第三方机构管理</title>
<base href="${baseUrl }"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" href="skins/css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="skins/css/form.css" type="text/css" media="all" />
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css" />
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeIcons.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/gt_grid.css" />


<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<!-- <script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script> -->
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

<script type="text/javascript" src="pages/treedrag.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	//tab页
	loadTab();
	
	
	//加载布局
	$('#myContent').layout({
        west: {size:"300",resizable:false,spacing_open:0,spacing_closed:0}
    });
	
	//加载数据
	loadGrid();
	
	//加载人员管理
	loadGridManage();
	
	
  	//加载树
    buildThirdpartnerTree();
  	
  	
    //加载表单验证
    $("#myForm").validate();
    $('#myForm').ajaxForm(function(data) {
    	
        //节点处理
    	var nameValue=data.thirdpartnername;
        
    	//新增
    	if(add){
    		var newNode = {id:data.thirdpartnerguid, name:nameValue,iconSkin:'dept',ename:'dept',code:data.thirdpartytype};
    		zTree.addNodes(selectNode, newNode);
        }else{
        	selectNode.name = nameValue;
   			zTree.updateNode(selectNode);
   			zTree.selectNode(selectNode);
        }
    	add=false;
    	
    	formDisabled(true);
    	//取消状态
    	onCancel();
    });
    
    formDisabled(false);
  
  
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
    		$("#tab"+tabIndex).show();
    		$("#group"+tabIndex).show();
    		
    		if(selectNode!=null){
    			if(selectNode.ename=='dept'){
    				//打开人员管理
        			if(tabIndex==1){
        				mygridManage.reload();
        			}else if(tabIndex==2){
        				//测评信息管理
        				mygrid.reload();
        			}
    			}
    		}
    		
    		
   		}
    });
}







//树
var zTree;
//选中节点
var selectNode=null;
function buildThirdpartnerTree(){
	//配置
	var setting = {callback:{beforeClick: function(treeId, treeNode) {
			$("#myForm").clearForm();
			selectNode=treeNode;
    		formDisabled(true);
    		if(treeNode.ename==null){
    			$('#b1').hide();
    			$('#b2').hide();
    			
    			//点击全部机构时隐藏所有按钮
    			$('#group0').hide();
    			$('#group1').hide();
    			$('#group2').hide();
    			return;
    		}
    		
    		
    		
    		//出现按钮组一
    		$('#b1').show();
    		$('#b2').hide();
    		
    		if(selectNode!=null){
    			if(treeNode.ename!='dept'){
        			//只能新增
        			$('#new').show();
        			$('#edit').hide();
        			$('#del').hide();
        			$('.post').hide();
        		}else if(treeNode.ename=='dept'){
        			$('#edit').show();
        			$('#new').hide();
        			$('#del').show();
        			
        			$('.post').show();
        			
        			loadData(treeNode.id);
        		}else{
        			$('#edit').hide();
        			$('#new').hide();
        			$('#del').hide();
        		}
    		}
    		
    		
    	}
  	}};
	
    $.getJSON("thirdpartner/buildThirdPartnerCheckTree.do", function(tdata) {
    	zTree = $.fn.zTree.init($("#tree"),setting, tdata);
    	
    });
}







//基本信息数据加载
function loadData(tid){
	$("#myForm").clearForm();
	if(tid!=null&&tid!=''&&tid!='null'){
		$.getJSON("thirdpartner/getThirdPartnerById.do", {id:tid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#' + key);
			        if(el) 
			            el.val(data[key]);
			    }
				
				//控制tab
				if(selectNode!=null){
					$("#group"+tabIndex).show();
					if(tabIndex==1){
						mygridManage.reload();
					}else if(tabIndex==2){
						mygrid.reload();
					}
				}
				
			}
		});
	}
		
}







//新增
var add=false;
function addNode(){
	$('#b1').hide();
	$('#b2').show();
	$("#myForm").clearForm();
	
	$("#thirdpartytype").val(selectNode.code);
	formDisabled(false);
	add=true;
	
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
	//基本信息数据加载
	if(selectNode!=null){
		if(selectNode.ename=='dept'){
			loadData(selectNode.id);
			$('#b1').show();
			$('#b2').hide();
			
			$('#del').show();
			$('#edit').show();
			$('#new').show();
		}else{
			$('#b1').show();
			$('#b2').hide();
			
			$('#new').show();
			$('#edit').hide();
			$('#del').hide();
		}
		
	}	
	formDisabled(true);
}






//删除
function delNode(){
	if(!confirm('确认要删除吗？')){
		return;
	}
	$.post("thirdpartner/delThirdPartnerById.do",{id:selectNode.id}, function() {
		zTree.removeNode(selectNode);
		$("#myForm").clearForm();
			
 		$('#b1').show();
		$('#b2').hide();
		
		$('#del').hide();
		$('#edit').hide();
		$('#new').hide();
 		formDisabled(false);
    });
}
















//通过测评信息选项列表的id加载数据
function loadThirdAssess(thirdpartyassessguid){
	formDisabled(false);
	$.getJSON("thirdpartyassess/getThirdPartyAssessById.do",{id:thirdpartyassessguid},function(data){
		for(var key in data){
			if($('#optionListForm input[id='+key+']')){
				$('#optionListForm input[id='+key+']').val(data[key]);
			}
			if($('#optionListForm textarea[id='+key+']')){
				$('#optionListForm textarea[id='+key+']').val(data[key]);
			}
		}    				
	});
}










//通过人员管理选项列表的id加载数据
function loadWebUser(userguid){
	formDisabled(false);
	$.getJSON("recruitment/getWebUserById.do",{id:userguid},function(data){
		for(var key in data){
			if($('#webUserListForm input[id='+key+']')){
				$('#webUserListForm input[id='+key+']').val(data[key]);
			}
			if($('#webUserListForm textarea[id='+key+']')){
				$('#webUserListForm textarea[id='+key+']').val(data[key]);
			}
		}    				
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


//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.thirdpartnerguid=selectNode.id;
	pam.level=0;
}




//
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'thirdpartyassessguid'},
				{name : 'thirdpartyguid'},
				{name : 'year'},
				{name : 'thirdpartyassesslevelname'},
				{name : 'thirdpartyassessresult'},
				{name : 'thirdpartyassessdate'},
				{name : 'rmk'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'thirdpartyassesslevelname' , header: "等级" , width :100 ,headAlign:'center',align:'center'},
			{id: 'year' , header: "年度" , width :99,headAlign:'center',align:'center'},
			{id: 'thirdpartyassessdate' , header: "时间" , width :140,headAlign:'center',align:'left'},
			{id: 'thirdpartyassessresult' , header: "结果" , width :390,headAlign:'center',align:'left'},
			{id: 'rmk' , header: "备注" , width :290,headAlign:'center',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'thirdpartyassess/getAllThirdPartyAssess.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		}, 
		width: "99.8%",//"100%", // 700,
		height: "460",  //"100%", // 330,
		container : 'gridbox', 
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		autoLoad:false,
		stripeRows: false,
		remoteFilter:true,
		showIndexColumn:false,
		selectRowByCheck:true,
		replaceContainer : true,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'nav | pagesize  state',
		pageSize:size,
		skin:getGridSkin(),
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				addOrUpdateThirdAssess(false,record.thirdpartyassessguid);
			}
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}




//删除
function removethirdpartyassess(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].thirdpartyassessguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	$.post("thirdpartyassess/delThirdPartyAssessById.do",{ids:array.toString()}, function() {
		mygrid.reload();
	}); 
}








//新增测评信息选项列表的数据
var optionListForm=null
function addOrUpdateThirdAssess(state,thirdpartyassessguid){
	formDisabled(false);
	
	if(selectNode!=null||state==false){
		$("#addOptionListWindow").dialog({
			autoOpen: true,
			modal: true,
			resizable:false,
			width:500,
			buttons: {
				"确定": function() {
					if(optionListForm.form()){
						$("#optionListForm").submit();
						$("#addOptionListWindow").dialog("close");
					}
				},
				"取消": function() {
					$("#optionListForm").clearForm();
					$(this).dialog("close");
				}
			},open:function(){
				$("#optionListForm").clearForm();
				if(selectNode!=null){
					$("#optionListForm input[id=thirdpartnerguid]").val(selectNode.id);
				}
					
				
				
				//加载数据
				if(!state){
					loadThirdAssess(thirdpartyassessguid);
				}
				
				//表单校验
				if(optionListForm==null)
					optionListForm=$("#optionListForm").validate({submitHandler: function(form) {
					    	$(form).ajaxSubmit(function(data) {
					    		mygrid.reload();
					    		$("#addOptionListWindow").dialog("close");
					        });
						}
					});
			}
		});
	}else{
		alert("请选择所属体检机构或者猎头公司！");
	}
}





//检查年份
function checkYear(){
	var thirdpartnerguid=selectNode.id
	var num=$("#year").val();
	var thirdpartyassessguid=$("#thirdpartyassessguid").val();
	if (num!=null&&num!=""){
		$.getJSON("thirdpartyassess/checkYear.do",{num:num,thirdpartyassessguid:thirdpartyassessguid,thirdpartnerguid:thirdpartnerguid}, function(data) {
			if(data!=null&&data!=""){
				alert(data);
				$("#year").val(null);
			}
		});
	}
}
</script>









<script type="text/javascript">
//人员管理
var mygridManage=null;
function loadGridManage(){
	var size=getGridSize();
	var grid_demo_id = "myGridManag";
	var dsOption= {
			fields :[
				{name : 'webuserguid'},
				{name : 'thirdpartnerguid'},
				{name : 'username'},
				{name : 'email'},
				{name : 'password'},
				{name : 'isadmin'},
				{name : 'valid'},
				{name : 'code'},
				{name : 'mobile'},
				{name : 'modtime'},
				{name : 'rmk'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'username' , header: "用户名" , width :120 ,headAlign:'center',align:'left'},
			{id: 'email' , header: "邮箱" , width :180,headAlign:'center',align:'left'},
			//{id: 'password' , header: "密码" , width :100,headAlign:'center',align:'left'},
			{id: 'mobile' , header: "电话" , width :140,headAlign:'center',align:'left'},
			{id: 'rmk' , header: "备注" , width :300,headAlign:'center',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'recruitment/searchWebUser.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		}, 
		width: "99.8%",//"100%", // 700,
		height: "460",  //"100%", // 330,
		container : 'manage', 
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		autoLoad:false,
		stripeRows: false,
		remoteFilter:true,
		showIndexColumn:false,
		selectRowByCheck:true,
		replaceContainer : true,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'nav | pagesize  state',
		pageSize:size,
		skin:getGridSkin(),
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				addOrUpdateWebUser(false,record.webuserguid);
			}
		}
	};
	mygridManage=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygridManage) );
}







//删除
function removeWebUser(){
	var array=new Array();
	var cords=mygridManage.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].webuserguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	$.post("recruitment/delWebUserById.do",{ids:array.toString()}, function() {
		mygridManage.reload();
	}); 
}



//新增人员管理选项列表的数据
var webUserListForm=null
function addOrUpdateWebUser(state,userguid){
	formDisabled(false);
	
	if(selectNode!=null||state==false){
		$("#addWebUserListWindow").dialog({
			autoOpen: true,
			modal: true,
			resizable:false,
			width:500,
			buttons: {
				"确定": function() {
					if(webUserListForm.form()){
						$("#webUserListForm").submit();
					}
				},
				"取消": function() {
					$("#webUserListForm").clearForm();
					$(this).dialog("close");
				}
			},open:function(){
				$("#webUserListForm").clearForm();
				
				if(selectNode!=null){
					$("#webUserListForm input[id=valid]").val(1);
					$("#webUserListForm input[id=isadmin]").val(selectNode.code);
					$("#webUserListForm input[id=thirdpartnerguid]").val(selectNode.id);
				}
				
				//加载数据
				if(!state){
					loadWebUser(userguid);
				}
				
				//表单验证
				if(webUserListForm==null)
					webUserListForm=$("#webUserListForm").validate({submitHandler: function(form) {
					    	$(form).ajaxSubmit(function(data) {
					    		mygridManage.reload();
					    		$("#addWebUserListWindow").dialog("close");
					        });
						}
					});
			}
		});
	}else{
		alert("请选择所属体检机构或者猎头公司！");
	}
}







//判断登录名是否存在
function checkLoginName(){
	var email=$("#webUserListForm input[id=email]").val();
	var webuserguid=$("#webuserguid").val();
	if (email!=null&&email!=""){
		$.getJSON("recruitment/checkWebUserByEmail.do",{webuserguid:webuserguid,name:email}, function(data) {
			if(data!=null&&data!=""){
				alert(data);
				$("#webUserListForm input[id=email]").val(null);
			}
		});
	}
}
</script>
</head>
<body>


<div class="sort">
	<div class="sort-title">
		<h3>第三方机构管理</h3>
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
				        <span id="b1" style="display:none;">
				        	<a id="new" class="btn" href="javascript:addNode();" style="display:none;"><i class="icon icon-plus"></i><span>新增</span></a>
							<a id="edit" class="btn" href="javascript:editNode()" style="display:none;"><i class="icon icon-pencil"></i><span>修改</span></a>
							<a id="del"  class="btn" href="javascript:delNode()"  style="display:none;"><i class="icon icon-remove"></i><span>删除</span></a>
				   		</span>
				   		
				    	<span id="b2" style="display:none;">
				     		<a class="btn"  href="javascript:save();"><i class="icon icon-check"></i><span>保存</span></a>
							<a class="btn"  href="javascript:onCancel();"><i class="icon icon-share"></i><span>取消</span></a>
				   		</span>
			   		</span>
			   		
			   		<span id="group1" class="gruop_hidden"  style="display:none;">
			            <a class="btn post" href="javascript:addOrUpdateWebUser(true);"><i class="icon icon-plus"></i><span>新增</span></a>
						<a class="btn post" href="javascript:removeWebUser();"><i class="icon icon-remove"></i><span>删除</span></a>
			   		</span>
			   		
			   		<span id="group2" class="gruop_hidden" style="display:none;">
			   			<a class="btn post" href="javascript:addOrUpdateThirdAssess(true);"><i class="icon icon-plus"></i><span>新增</span></a>
						<a class="btn post" href="javascript:removethirdpartyassess();"><i class="icon icon-remove"></i><span>删除</span></a>
			   		</span>
			   		
				</div>
			</div>
			<div class="table-wrapper" id="myContent" style="height:550px;">
				<div class="ui-layout-west" style="overflow:auto;">
					<ul id="tree" class="ztree"></ul>
				</div>
				<div class="ui-layout-center" style="overflow:hidden;">
					<div id="mytab">
						<ul>
							<li><a href="#tab0">基本信息</a></li>
							<li><a href="#tab1">用户管理</a></li>
							<li><a href="#tab2">测评信息</a></li>
						</ul>
						 <div id="tab0">
					        <form action="thirdpartner/saveOrUpdateThirPartner.do" method="post" id="myForm" class="form">
								<input id="thirdpartnerguid" name="thirdpartnerguid" type="hidden" value=""/> 
								 <input type="hidden" id="thirdpartytype" name="thirdpartytype" value=""/>
								
								<fieldset>
									<legend>基本信息</legend>
									<ul>
										<li>
										    <span><em class="red">* </em>机构名称：</span>
										    <input id="thirdpartnername" name="thirdpartnername" class="{required:true,maxlength:16} inputstyle"/>
										</li>
									 </ul>
						            <ul>
						            	<li>
						            		<span><em class="red">* </em>负责人：</span>
						            		 <input id="name" name="name" class="{required:true,maxlength:50} inputstyle"/>
						            	</li>
						            </ul>
						            <ul>
						            	<li>
						            		<span><em class="red">* </em>负责邮箱：</span>
						            		 <input id="email" name="email" class="{required:true,maxlength:50,email:true} inputstyle"/>
						            	</li>
						            </ul>
						            <ul>
						            	<li>
						            		<span><em class="red">* </em>负责电话：</span>
						            		 <input id="mobile" name="mobile" class="{required:true,maxlength:20} inputstyle"/>
						            	</li>
						            </ul>
						            <ul>
										<li>
										    <span>备注：</span>
										     <textarea id="rmk" name="rmk" rows="3" cols="40" class="{maxlength:500} areastyle" ></textarea>
										</li>
						            </ul>
									</fieldset>
					       	</form>
				    	</div> 
					    <div id="tab1"  style="display:none;">
					    	<div style="padding-top:5px;">
								<div id="manage" ></div>
			                </div>
					    </div>
					    <div id="tab2" style="display:none;">
							<div style="padding-top:5px;">
								<div id="gridbox" ></div>
			                </div>
				    	</div>
			  		</div>
			  	</div>
			</div>
		</div>
	</div>
</div>













<!-- 添加窗口 -->
<div id="addOptionListWindow" title="添加测评信息" style="display:none">
	<form action="thirdpartyassess/saveOrUpThirdPartyAssess.do" method="post" id="optionListForm" class="form">
		<input id="thirdpartyassessguid" name="thirdpartyassessguid" type="hidden" value=""/>
		<input id="thirdpartnerguid" name="thirdpartnerguid"  type="hidden" value=""/>
		<ul>
			<li>
				<span><em class="red">* </em>年度：</span>
				<input id="year" name="year" class="{required:true,number:true,maxlength:4,minlength:4} inputstyle" onblur="checkYear();"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>测评等级：</span>
				<input type="hidden" id="thirdpartyassesslevel" name="thirdpartyassesslevel" value=""/>
				<input id="thirdpartyassesslevelname" name="thirdpartyassesslevelname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#thirdpartyassesslevel','#thirdpartyassesslevelname','THIRDPARTYSSESSLEVEL');"/>
				<div class="select-trigger" onclick="chooseOptionTree('#thirdpartyassesslevel','#thirdpartyassesslevelname','THIRDPARTYSSESSLEVEL');"></div>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>测评结果：</span>
				<textarea id="thirdpartyassessresult" name="thirdpartyassessresult" rows="3" cols="40" class="{required:true,maxlength:500} areastyle" ></textarea>
			</li>
		</ul>
	</form>
</div>















<!-- 添加窗口 -->
<div id="addWebUserListWindow" title="添加人员管理信息" style="display:none">
	<form action="recruitment/saveOrUpdateWebUser.do" method="post" id="webUserListForm" class="form">
		<input id="thirdpartnerguid" name="thirdpartnerguid" type="hidden" value=""/> 
		<input id="webuserguid" name="webuserguid" type="hidden" value=""/>
		<input type="hidden" id="valid" name="valid" value="1"/>
		<input type="hidden" id="isadmin" name="isadmin" value=""/>
		<input type="hidden" id="code" name="code" value=""/>
		<ul>
			<li>
			    <span><em class="red">* </em>用户名：</span>
				<input id="username" name="username" class="{required:true,maxlength:20} inputstyle" />
			</li>
		 </ul>
	          <ul>
			<li id="loginnamePanel">
			    <span><em class="red">* </em>邮箱：</span>
				<input id="email" name="email" class="{required:true,maxlength:50,email:true} inputstyle"  onblur="checkLoginName();"/>
			</li>
	          </ul>
	          <ul>
			<li id="myPassword">
				<span><em class="red">* </em>密码：</span>
				<input type="password" id="password" name="password" class="{required:true,maxlength:10} inputstyle"  />
			</li>
	          </ul>
	          <ul>
			<li>
				<span><em class="red">* </em>联系电话：</span>
				<input id="mobile" name="mobile" class="{required:true,maxlength:20} inputstyle"  />
			</li>
	          </ul>
	           <ul>
			<li>
				<span>备注：</span>
				<textarea id="rmk" name="rmk" rows="3" cols="40" class="{maxlength:500} areastyle"></textarea>
			</li>
		</ul>
    </form>
</div>








</body>
</html>