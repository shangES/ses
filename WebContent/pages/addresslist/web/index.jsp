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
<link rel="stylesheet" type="text/css" href="pages/addresslist/css/web.css"/>

<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeIcons.css"/>

<style type="text/css">
.ui-layout-pane {
	border: 0px;
}
</style>

<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="pages/addresslist/css/web.js"></script>
<script type="text/javascript" src="skins/js/jquery.layout.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>


<script type="text/javascript">
var searchContentWS="${param.searchContentWS}";



$(document).ready(function() {
	//加载布局
	$('body').layout({
        north: {size:"72",resizable:false,spacing_open:0,spacing_closed:0},
        south: {size:"21",resizable:false,spacing_open:0,spacing_closed:0}
    });
	$('#myContent').layout({
        west: {size:"230",resizable:false,spacing_open:0,spacing_closed:0}
    });
	
	$('#myWest').layout({
        north: {size:"31",resizable:false,spacing_open:0,spacing_closed:0},
        south: {size:"23",resizable:false,spacing_open:0,spacing_closed:0}
    });
	$('#myCenter').layout({
        north: {size:"31",resizable:false,spacing_open:0,spacing_closed:0}
    });
	
	
	
	//树的展示
	buildDepartmentTree();
	
	
	//展示数据
	loadData();
	
	
	//判断用户是否同步
	var userid='${userid}';
	if(userid==null||userid==''||userid=='null'){
		alert("登录用户信息不存在，请联系系统管理员！");
	}
	
});





//树
var zTree;
//选中节点
var selectNode=null;
var url="pages/addresslist/web/phone.htm";
function buildDepartmentTree(){
	//配置
	var setting = {
				callback:{
					beforeClick: function(treeId, treeNode) {
						selectNode=treeNode;
						
						
						//点击
						if(selectNode!=null&&selectNode.id!=null){
							var hasurl=$("#detail").attr("src");
							var companycode=selectNode.companycode;
							var deptcode=selectNode.deptcode;
							
							var content = document.getElementById('detail');
							if (content != null && content.contentWindow!=null&& content.contentWindow.reloadWebGrid != null)
								content.contentWindow.reloadWebGrid(companycode,deptcode,null);
							else
								content.contentWindow.location.href=encodeURI("address.do?page=list&companycode="+companycode+"&deptcode="+deptcode);
						}else{
							
							$("#detail").attr("src",url);
							
						}
					}
				},
				view: {
					fontCss: getDeptFontCss
				}
			};
		
    $.getJSON("webaddresslist/buildMyDepartmentTree.do", function(tdata) {
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
var nodeList = [];
var key;
var nodeState=false;
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

//全部关闭
var state=true;
function expandNode() {
	state=!state;
	zTree.expandAll(state);
}
//===============
	
	
	
//定位部门
function makerTreePosation(treeid){
	var nodes = zTree.getNodesByParam("id", treeid, null);
	zTree.selectNode(nodes[0]);
}

	
	
//从wasu搜索得到通讯录数据
function loadData(){
	if(searchContentWS!=null&&searchContentWS!=""&&searchContentWS!='null'){
		search();
	}
}	
	
	

	
//员工搜索
function search(){
	var hasurl=$("#detail").attr("src");
	
	var companycode="";
	var deptcode="";
	
	/*if(selectNode!=null&&selectNode.id!=null){
		companycode=selectNode.companycode;
		deptcode=selectNode.deptcode;
	}*/
	var content = document.getElementById('detail');
	var name=$("#myname").val();
	
	if (content != null && content.contentWindow!=null&& content.contentWindow.reloadWebGrid != null)
		content.contentWindow.reloadWebGrid(companycode,deptcode,name);
	else
		content.contentWindow.location.href=encodeURI("address.do?page=list&companycode="+companycode+"&deptcode="+deptcode+"&name="+name);
}



//回车搜索
function formSubmit(e){
	if(e.keyCode==13){
		search();
	}
}




//切换视图
function convertView(){
	$("#detail").attr("src",url);
}
	
</script>



</head>
<body>

<div class="ui-layout-north top">
	<table width="100%" cellspacing="0" cellpadding="0" border="0" class="logo">
        <tbody>
	        <tr>
	            <td align="right">
	                
	            </td>
	        </tr>
	    </tbody>
    </table>
</div>
<div class="ui-layout-center box" id="myContent" style="overflow:hidden;">
	<div class="ui-layout-west" id="myWest">
		<div class="ui-layout-north left-top">
			<img width="16" height="16" src="pages/addresslist/css/images/inco-shu.gif">
			<span class="font-01">公司/部门</span>
			<a href="javascript:expandNode();" style="margin-left:110px;">折叠</a>
		</div>
		<div class="ui-layout-center" style="background:#fff;overflow-x:hidden;overflow-y:scroll;border:1px solid #888;border-top:0px;">
			<ul id="tree" class="ztree">
				<img src="skins/images/ajax-loader-small.gif"/>
			</ul>
		</div>
		<div class="ui-layout-south">
			 <table width="100%" height="100%" border="0">
				<tr>
					<td>
						<input class="myMarker" id="myMarker" style="width:206px;border:1px solid #888888;"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="ui-layout-center" id="myCenter" style="padding-left:5px;">
		<div class="ui-layout-north">
			<table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" >
				<tr>
					<td style="width:25px;">
		                <img width="24" height="23" src="pages/addresslist/css/images/inco-pp.gif">
		            </td>
		            <td style="width:420px;">
		                <input class="input" id="myname" value="${param.searchContentWS}" style="width:415px;" onkeypress="formSubmit(event);">
		            </td>
	                <td style="width:65px;" align="right">
	                    <a href="javascript:search();" >
	                        <img width="62" height="23" border="0" src="pages/addresslist/css/images/b-suosou.gif">
	                    </a>
	                </td>
	                <td align="right">
	                    <a target="detail" href="address.do?page=selfedit">
	                        <img width="120" height="23" border="0" src="pages/addresslist/css/images/p-modify.gif">
	                    </a>
	                </td>
	            </tr>
            </table>
		</div>
		<div class="ui-layout-center" style="background:#ffffff;overflow:hidden;">
			<iframe id="detail" name="detail" width="100%" height="100%" src="pages/addresslist/web/phone.htm" frameborder="0" scrolling="no"></iframe>
		</div>
	</div>
</div>		
<div class="ui-layout-south">
	<table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" class="bottom-bg">
		<tbody>
			<tr>
			    <td width="">&nbsp;</td>
			    <td width="200" valign="bottom" background="pages/addresslist/css/images/bottom-pic.gif" align="right" style="background-repeat:no-repeat; background-position:right;font-size:12px;" class="yejiao">
			    	版权所有 &copy; 2009- 2014 华数
		        </td>
		    </tr>
		</tbody>
	</table>
</div>
		





</body>
</html>