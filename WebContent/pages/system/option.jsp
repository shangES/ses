<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>选项管理</title>
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
$(document).ready(function () {
    
	//tab页
	loadTab();
	
	
	//加载布局
	$('#myContent').layout({
        west: {size:"300",resizable:false,spacing_open:0,spacing_closed:0}
    });
	
    //类型树
    buildOptionTypeTree();
    
    //选项的列表加载
    loadGrid();
    
 	//把表单置为不可编辑
    formDisabled(true,"#OptionTypeForm");
 	
   $("#OptionTypeForm").validate({submitHandler: function(form) {
    	$(form).ajaxSubmit(function(data) {
    		 formDisabled(true,"#OptionTypeForm");
    		 $("#b2").hide();
    		 $("#b1").show();
        });
	}
	});
    
    //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});


//选项类型树
var typeid=null;
function buildOptionTypeTree(){
	var setting = {callback:{beforeClick: function (treeId, treeNode){
			if(treeNode.id!=null){
				formDisabled(true,"#OptionTypeForm");//设置为只读
				typeid=treeNode.id;
				
				$("#b0").show();
				
    			//选项列表
	    		mygrid.reload();
				//加载选项类型信息
				loadOptionType(typeid);
			}
				loadTab(treeNode);
	    	}
	  	}
	};
    $.getJSON("system/buildOptionTypesTree.do", function(data) {
    	var zTree = $.fn.zTree.init($("#tree"),setting, data);
    });
}







//通过id加载选项类型的数据
var optionTypeid;
function loadOptionType(optionTypeid){
	$.getJSON("system/getOptionTypeById.do",{id:optionTypeid},function(data){
		refreshForm(data);		    				
	});
}







//刷新Form
function refreshForm(data){
	$("#OptionTypeForm").clearForm();
	for(var key in data){
		var el = $("#"+key);
		if(el)
			el.attr('value',data[key]);
		if(key=='reserved'){
			data[key]==1?$("#reserved").attr('checked',true):false;
		}
	}
	
	if(tabIndex==0){
		$("#tab1").show();
		$("#b0").show();
	}else if(tabIndex==1){
		$("#tab1").show();
		$("#b1").show();
	}
}








//tab页
var tabIndex=0;
var rootNode;
function loadTab(rootNode){
	$("#mytab").tabs({
    	select: function(event, ui) {
    		tabIndex=ui.index;
    		$(".gruop_hidden").hide();
    		$("#group"+tabIndex).show();
    		$("#tab"+tabIndex).show();
    		
    		//刷新关系
    		if(rootNode!=null){
    			if(tabIndex==0){
    				$("#tab1").show();
    				$("#b0").show();
    			}else if(tabIndex==1){
    				$("#tab1").show();
    				$("#b1").show();
    			}
    		}else{
    			if(tabIndex==0){
    				$("#tab1").show();
    				$("#b0").hide();
    			}else if(tabIndex==1){
    				$("#tab1").show();
    				$("#b1").hide();
    			}
    		}
    	}
    });
}









//修改选项类型的数据
function updateNode(){
	$('#b1').hide();
	$('#b2').show();
	formDisabled(false,"#OptionTypeForm");
}







//保存选项类型的数据
function saveOptionType(){
	var reservedId=null;
	if($("#OptionTypeForm #reserved").attr("checked")){
		reservedId=1;
	}else{
		reservedId=2;
	}
	$("#OptionTypeForm #reserved").val(reservedId);
	$("#OptionTypeForm").submit();
}










//取消修改选项类型的数据
function cancelOptionType(){
	$('#b2').hide();
	$('#b1').show();
	formDisabled(true,"#OptionTypeForm");
	loadOptionType(treeNode.id);
}

//是否默认选项 ----optionlist
function checkOptionListmrxx(el){
	var checked=$(el).attr("checked");
	$(el).val(checked?1:0);	
}

//是否系统保留----optionlist
function checkkOptionListxtbl(el){
	var checked=$(el).attr("checked");
	$(el).val(checked?1:0);	
}











//新增选项列表的数据
var add=false;
var OptionListguid;
var optionListForm=null
function addOrUpdateOptionList(state,OptionListguid){
	add=state;
	$("#addOptionListWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:500,
		buttons: {
			"确定": function() {
				$("#optionListForm input[id=optiontypeguid]").val(typeid);
					saveOptionList();
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#optionListForm").clearForm();
			if(!add){
				loadOptionList(OptionListguid);
			}
			
			optionListForm=$("#optionListForm").validate({submitHandler: function(form) {
		    	$(form).ajaxSubmit(function(data) {
		    		mygrid.reload();
		    		$("#addOptionListWindow").dialog("close");
		        });
			}
			});
		}
	});
}







//代码不能重复
function saveOptionList(){
	var optionid=$("#optionListForm input[id=optionid]").val();
	var optiontypeguid=$("#optionListForm input[id=optiontypeguid]").val();
	var code=$("#optionListForm input[id=code]").val();
	$.post("system/checkOptionList.do",{optionid:optionid,optiontypeguid:optiontypeguid,code:code},function(msg){
		if(msg){
			alert("代码重复！");
			$("#optionListForm input[id=code]").val(null);
		}else{
			$("#optionListForm").submit();
		}
	});
}








//通过选项列表的id加载数据
function loadOptionList(OptionListguid){
	$.getJSON("system/getOptionListByListId.do",{id:OptionListguid},function(data){
		for(var key in data){
			if($('#optionListForm input[id='+key+']')){
				$('#optionListForm input[id='+key+']').val(data[key]);
			}
			if($('#optionListForm textarea[id='+key+']')){
				$('#optionListForm textarea[id='+key+']').val(data[key]);
			}
			
			$("#optionListForm input[id=isdefault]").attr("checked",data.isdefault==1);
			$("#optionListForm input[id=reserved]").attr("checked",data.reserved==1);
		}    				
	});
}










//删除选项列表数据
function delOptionList(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.optionid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
   	$.post("system/delOptionListById.do",{ids:array.toString()}, function() {
		mygrid.reload();
    });
}
</script>




<script type="text/javascript">
//选项列表
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var wh=$(document.body).outerWidth()-360;
	$("#myTable").css({width:wh,height:495});
	
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'optionid'},
				{name : 'optiontypeguid'},
				{name : 'code'},
				{name : 'name'},
				{name : 'isdefault'},
				{name : 'dorder'},
				{name : 'reserved'},
				{name : 'rmk'}
			]
		};
	var colsOption = [
					{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center',filterable:false},
	              	{id: 'dorder' , header: "排序号" ,headAlign:'center',align:'center', width :50},
	          	   	{id: 'code' , header: "代码",headAlign:'center',align:'center', width :80},
	          	   	{id: 'name' , header: "名称",headAlign:'center', width :250},
	          	   	{id: 'isdefault' , header: "是默认选项" ,headAlign:'center', align:'center', width :80},
	          	   	{id: 'reserved' , header: "是否系统保留" ,headAlign:'center', align:'center', width :80},
	          	   	{id: 'rmk' , header: "备注" ,headAlign:'left', width :200}
	          	];
	var gridOption={
		id : grid_demo_id,
		loadURL : 'system/getOptionListByOptionTypeId.do',
		beforeLoad:function(reqParam){
			reqParam['parameters']={'id':typeid};
		},
		saveURL :'system/saveOrUpdateOptionList.do',
		width:'100%',
		height:"100%",  //"100%", // 330,
		container : 'gridbox',
		autoLoad:false,
		stripeRows: false,
		showIndexColumn:true,
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
				addOrUpdateOptionList(false,record.optionid);
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
		<h3>选项管理</h3>
	</div>
	
	<div class="sort-cont sort-table">
		<div class="table">
			<div class="table-bar">
				<div class="table-title">
					表格名称
				</div>
				<div class="table-ctrl">
					<span id="group0" class="gruop_hidden">
						<span id="b0" style="display:none;">
							<a  class="btn" href="javascript:addOrUpdateOptionList(true);"><i class="icon icon-plus"></i><span>新增</span></a>
				   			<a  class="btn" href="javascript:delOptionList();"><i class="icon icon-trash"></i><span>删除</span></a>
				   		</span>
					</span>
					<span id="group1" class="gruop_hidden">
					   	<span id="b1" style="display:none;">
							<a id="update" class="btn" href="javascript:updateNode();"><i class="icon icon-pencil"></i><span>修改</span></a>
				   		</span>
				   		<span id="b2" style="display:none;">
							<a class="btn" href="javascript:saveOptionType();"><i class="icon icon-check"></i><span>保存</span></a>
							<a class="btn" href="javascript:cancelOptionType();"><i class="icon icon-share"></i><span>取消</span></a>
				   		</span>
				   	</span>
				</div>
			</div>
			<div class="table-wrapper" id="myContent" style="height:550px;">
				<div class="ui-layout-west" style="overflow:auto;">
					<ul id="tree" class="ztree"></ul>
				</div>
				<div class="ui-layout-center">
					<div id="mytab">
						<ul>
							<li><a href="#tab0">选项列表</a></li>
							<li><a href="#tab1">选项类型</a></li>
						</ul>
						<div id="tab0">
							<div style="padding-top:5px;">
								<div id="myTable" style="margin:0px auto;">
									<div id="gridbox" ></div>
				                </div>
			                </div>
						</div>
						<div id="tab1">
							<form action="system/saveOrUpdateOptionType.do" method="post" id="OptionTypeForm" class="form">
								<input id="optiontypeguid" name="optiontypeguid" type="hidden"/>
								<fieldset>
								<legend>基本信息</legend>
								<ul>
									<li>
										<span>&nbsp;</span>
				    					<label>
				    					<input type="checkbox" id="reserved" name="reserved" class="{required:true,maxlength:38}" value="" class="checkboxstyle" />是否系统保留
				    					</label>
									</li>
								</ul>
								<ul>
									<li>
										<span><em class="red">* </em>排序号：</span>
										<input id="dorder" name="dorder" class="{required:true,maxlength:38,number:true} inputstyle" value="" />
									</li>
								</ul>
								<ul>
									<li>
				   	 				<span><em class="red">* </em>代码：</span>
				    				<input id="code" name="code" class="{required:true,maxlength:60} inputstyle" value="" />
									</li>
								</ul>
								<ul>
									<li>
	                 					<span><em class="red">* </em>名称：</span>
	                 					<input id="name" name="name" class="{required:true,maxlength:60} inputstyle"/>
	           						</li>
	        					</ul>
								<ul>
									<li>
										<span>备注：</span>
				    					<textarea id="rmk" name="rmk" rows="3" cols="40" class="{maxlength:500} areastyle"></textarea>
									</li>
								</ul>
	       						</fieldset>
	       					</form>
						</div>
					</div>
				</div>	
			</div>
		</div>
	</div>
</div>


<!-- 添加窗口 -->
<div id="addOptionListWindow" title="添加选项列表" style="display:none">
	<form action="system/saveOrUpdateOptionList.do" id="optionListForm"  method="post" class="form">
		<input type="hidden" id="optionid" name="optionid" value=""/>
		<input type="hidden" id="optiontypeguid" name="optiontypeguid" value=""/>
		<ul>
			<li>
				<span>&nbsp;</span>
				<label>
					<input type="checkbox" id="isdefault" name="isdefault"  value="0" class="checkboxstyle" onclick="checkOptionListmrxx(this)"/>是默认选项
				</label>
				<label>
					<input type="checkbox" id="reserved" name="reserved"  value="0" class="checkboxstyle" onclick="checkkOptionListxtbl(this)"/>是否系统保留
				</label>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>代码：</span>
				<input  id="code" name="code" value="" class="{required:true,maxlength:4} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>名称：</span>
				<input id="name" name="name" value="" class="{required:true,maxlength:30} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>排序号：</span>
				<input  id="dorder" name="dorder" value="" class="{required:true,maxlength:19} inputstyle"/>
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