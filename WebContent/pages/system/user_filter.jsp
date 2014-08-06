<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>授权管理</title>
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

<script type="text/javascript">
$(document).ready(function() {
	
	//加载布局
	$('#myContent').layout({
    });
	
	//列表加载
    loadGrid();
});











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







//取消授权
function colse(userguid){
	if(!confirm('确认要取消授权吗？')){
		return;
	}
	
	$.post("system/delUserFilterByUserId.do",{ids:userguid}, function() {
		mygrid.reload();
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
}


</script>

<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'userguid'},
				{name : 'postname'},
				{name : 'username'},
				{name : 'companyname'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'candidatesstate' , header: "操作" , width :70,headAlign:'center',align:'center',renderer:function(value,record,colObj,grid,colNo,rowNo){
				var htm='&nbsp;';
				//授权取消
				htm+='<a href="javascript:colse(\''+record.userguid+'\');" title="取消授权">';
				htm+='取消授权';
				htm+='</a>';
				return htm;
			  }},
			{id: 'companyname' , header: "公司名称" , width :250 ,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门名称" , width :120 ,headAlign:'center',align:'left'},
			{id: 'postname' , header: "岗位名称" , width :120 ,headAlign:'center',align:'left'},
			{id: 'username' , header: "员工姓名" , width :80 ,headAlign:'center',align:'left'}
		];
	var gridOption={
		id : grid_demo_id,
		loadURL :'system/searchUserFilter.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'system/searchUserFilter.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=$('input[name="xls"]:checked').val();
			mygrid.parameters=pam;
		},
		exportFileName : '授权用户列表.xls',
		width: "99.8%",//"100%", // 700,
		height: "99.8%",  //"100%", // 330,
		container : 'gridbox', 
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		stripeRows: false,
		remoteFilter:true,
		showIndexColumn:true,
		selectRowByCheck:true,
		replaceContainer : true,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'nav | pagesize | reload  state',
		pageSize:size,
		skin:getGridSkin()
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
			<!--  <a class="btn" href="javascript:searchGrid();"><i class="icon icon-search"></i><span>搜索</span></a>-->
			<a class="btn" href="javascript:expGrid();"><i class="icon icon-list-alt"></i><span>导出</span></a>
		</div>
	</div>
	<div class="table-wrapper" id="myContent" style="height:550px;">
		<div class="ui-layout-center" style="overflow:hidden;border:0px;">
			<div id="gridbox" ></div>
		</div>
	</div>
</div>




<!-- 搜索 -->
<div id="search" title="搜索条件设置" style="display:none;">
	<form action="" id="searchForm" class="form">
		<ul>
			<li>
			    <span>公司名称：</span>
			    <input id="companyid" name="companyid" type="hidden" value=""/>
			    <input id="companyname" name="companyname" class="inputstyle disabled" disabled="true"/>
			</li>
		</ul>
		<ul>
			<li>
                <span>部门名称：</span>
                <input id="deptid" name="deptid" type="hidden" value=""/>
			    <input id="deptname" name="deptname" class="inputselectstyle" onclick="chooseMyDeptTree('#deptid','#deptname',callbackDept);"/>
			    <div class="search-trigger" onclick="chooseMyDeptTree('#deptid','#deptname',callbackDept);"/>
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





<!-- 岗位授权 -->
<div id="roleAudit" style="display:none;" title="岗位授权" >
	<ul id="roleTree" class="ztree"></ul>
</div>

<script type="text/javascript">
//岗位授权
var roleTree;
function openRole(userguid){
	$("#roleAudit").dialog({
		autoOpen: true,
		modal: true,
		model:true,
		resizable:false,
		width:400,
		height:500,
		buttons: {
			"确定": function() {
				var nodes=roleTree.getCheckedNodes(true);
				var array=new Array();
				for(var i=0;i<nodes.length;i++){
					var node=nodes[i];
					if(node.id!=null&&node.id!=''){
						
						var obj={};
						obj.roleid=node.id
						obj.userguid=userguid;
						array.push(obj);
					}
				}
				
				//参数
				var data={userguid:userguid,list:array};
				$.ajax({  
			        url: "system/saveUserRole.do",  
			        contentType: "application/json; charset=utf-8",  
			        type: "POST",  
			        dataType: "json",  
			        data: JSON.stringify(data),
			        success: function(result) { 
			        	alert('授权成功！');
			        	$("#roleAudit").dialog("close");
			        	mygrid.reload();
			        }
			    });
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},
		close: function() {
			
		},
		open:function(){
			$("#roleTree").html(null);
		    $.getJSON("system/buildRoleCheckTree.do",{userguid:userguid}, function(tdata) {
		    	//配置项
		    	var setting = {check: {
					enable: true
				}};
		    	roleTree = $.fn.zTree.init($("#roleTree"),setting, tdata);
		    });
		}
	});
}

</script>






<!-- 公司授权 -->
<div id="companyAudit" style="display:none;" title="公司授权" >
	<ul id="companyTree" class="ztree"></ul>
</div>
<script type="text/javascript">
//公司树
var companyTree;
function openCompany(userguid){
	$("#companyAudit").dialog({
		autoOpen: true,
		modal: true,
		model:true,
		resizable:false,
		width:400,
		height:500,
		buttons: {
			"确定": function() {
				var nodes=companyTree.getCheckedNodes(true);
				var array=new Array();
				for(var i=0;i<nodes.length;i++){
					var node=nodes[i];
					if(node.id!=null&&node.id!=''){
						
						var obj={};
						obj.userguid=userguid;
						obj.companyid=node.id;
						obj.isdefault=1;
						array.push(obj);
					}
				}
				
				//参数
				var data={userguid:userguid,list:array};
				$.ajax({  
			        url: "system/saveUserCompany.do",  
			        contentType: "application/json; charset=utf-8",  
			        type: "POST",  
			        dataType: "json",  
			        data: JSON.stringify(data),
			        success: function(result) { 
			        	alert('授权成功！');
			        	$("#companyAudit").dialog("close");
			        }
			    });
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},
		close: function() {
			
		},
		open:function(){
			$("#companyTree").html(null);
		    $.getJSON("system/buildCompanyCheckTree.do",{userguid:userguid}, function(tdata) {
		    	//配置项
		    	var setting = {check: {
					enable: true,
					chkboxType: { "Y": "", "N": "" }
				}};
		    	companyTree = $.fn.zTree.init($("#companyTree"),setting, tdata);
		    });
		}
	});
}
</script>







<!-- 通讯录授权 -->
<div id="addressAudit" style="display:none;" title="通讯录授权" >
	<ul id="addressTree" class="ztree"></ul>
</div>
<script type="text/javascript">
//通讯录树
var addressTree;
function openAddress(userguid){
	$("#addressAudit").dialog({
		autoOpen: true,
		modal: true,
		model:true,
		resizable:false,
		width:400,
		height:500,
		buttons: {
			"确定": function() {
				var array=new Array();
				var checknodes=addressTree.getCheckedNodes(true);
				var unchecknodes=addressTree.getCheckedNodes(false);
				
				if(unchecknodes.length==0){
					var obj={};
					obj.userguid=userguid;
					array.push(obj);
				}else{
					for(var i=0;i<checknodes.length;i++){
						var node=checknodes[i];
						if(node.id!=null&&node.id!=''){
							var obj={};
							obj.userguid=userguid;
							obj.companyid=node.id;
							array.push(obj);
						}
					}
				}
				
				
				
				//参数
				var data={userguid:userguid,list:array};
				$.ajax({  
			        url: "system/saveUserAddressCompany.do",  
			        contentType: "application/json; charset=utf-8",  
			        type: "POST",  
			        dataType: "json",  
			        data: JSON.stringify(data),
			        success: function(result) { 
			        	alert('授权成功！');
			        	$("#addressAudit").dialog("close");
			        }
			    });
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},
		close: function() {
			
		},
		open:function(){
			$("#addressTree").html(null);
		    $.getJSON("system/buildAddressCompanyCheckTree.do",{userguid:userguid}, function(tdata) {
		    	//配置项
		    	var setting = {check: {
					enable: true,
					chkboxType: { "Y": "s", "N": "" }
				}};
		    	addressTree = $.fn.zTree.init($("#addressTree"),setting, tdata);
		    });
		}
	});
}
</script>
</body>
</html>
