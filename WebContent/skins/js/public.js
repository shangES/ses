$(function(){
	$.ajaxSetup({
		cache : false,
		error : function(){
			
		},
		beforeSend : function(){
			showLoading();
		},
		complete : function(XMLHttpRequest,textStatus){
			 //通过XMLHttpRequest取得响应头，sessionstatus，
			 var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); 
			 //如果超时就处理 ，指定要跳转的页面
             if(sessionstatus=="timeout"){ 
            	 alert('页面已经过期，请重新登陆系统！');
            	 return;
             } 
		
			if (loading)
				loading.hide();
		}
	});
	
	//计算高度
	_autoHeight();
	
});









/**
 * iframe自适应页面高度
 */
function _autoHeight(){
	if($(window.parent.document).find("iframe").length<=0)
		return;
	
	$(window.parent.document).find("iframe").height(450);
	var height = document.documentElement.scrollHeight || document.body.scrollHeight;
	$(window.parent.document).find("iframe").height(height);
	
	//调用上级
	if(window.parent.minWidth&&window.parent.minWidth!=null){
		
		//这里要计算消息框的位置
		if(window.parent.posationMsg)
		window.parent.posationMsg();
	}else{
		if(window.parent._autoHeight)
			window.parent._autoHeight();
	}
}

/**
 * 滚动条回到顶部
 */
function _goTop(){
	if($(window.parent.document).find("iframe").length<=0)
		return;
	
	//调用上级
	if(window.parent.goBodyTop&&window.parent.goBodyTop!=null){
		window.parent.goBodyTop();
	}else{
		if(window.parent._goTop)
			window.parent._goTop();
	}
}







/**
 * 系统等待处理
 */
var loading = null;
function showLoading(){
	// 等待loading
	var $g = $(document.body);
	if (!loading) {
		loading = $("<span class='ui-state-default ui-corner-all loadingbox' style='border:1px solid #336699;'><label>系统正在处理,请稍候...</label></span>").appendTo(document.body);
		loading.css("position", "absolute")
				.css("top",$g.height() / 2 - $(".loadingbox").outerHeight()/2)
				.css("left",$g.width() / 2 - $(".loadingbox").outerWidth()/2);
	}
	loading.show();
}

function hidenLoading(){
	if(loading&&loading!=null)
	loading.hide();
}










/**
 * 扩展html
 */
jQuery.fn.outerHTML = function() {
    // IE, Chrome & Safari will comply with the non-standard outerHTML, all others (FF) will have a fall-back for cloning
    return (!this.length) ? this : (this[0].outerHTML ||
    (function(el) {
        var div = document.createElement('div');
        div.appendChild(el.cloneNode(true));
        var contents = div.innerHTML;
        div = null;
        return contents;
    })(this[0]));

};





/**
 * 日期的格式化
 * @param format
 * @returns
 */
Date.prototype.format = function(format) {   
	 var o = {   
	 "M+" : this.getMonth()+1, // month
	 "d+" : this.getDate(),    // day
	 "h+" : this.getHours(),   // hour
	 "m+" : this.getMinutes(), // minute
	 "s+" : this.getSeconds(), // second
	 "q+" : Math.floor((this.getMonth()+3)/3), // quarter
	 "S" : this.getMilliseconds() // millisecond
	 };   
	 if(/(y+)/.test(format)) format=format.replace(RegExp.$1,   
	 (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	 for(var k in o)if(new RegExp("("+ k +")").test(format))   
	 format = format.replace(RegExp.$1,   
	  RegExp.$1.length==1 ? o[k] :    
	  ("00"+ o[k]).substr((""+ o[k]).length));   
	 return format;
};


/**
 * js获取系统时间
 * 格式如：2011-01-01
 * @returns {String}
 */
function getCurentDateYMD(){
	var d = new Date();
	return d.format("yyyy-MM-dd");
}


/**
 * js获取系统时间
 * 格式如：2011-01-01 24:00:00
 * @returns {String}
 */
function getCurentDateYMDHMS(){
	var d = new Date();
	return d.format("yyyy-MM-dd hh:mm:00");
}

/**
 * js获取系统时间
 * 格式如：01-01 24:00
 * @returns {String}
 */
function getCurentDateDHMS(){
	var d = new Date();
	return d.format("MM-dd hh:mm");
}






/**
 * 根据state把表单置为
 * 可编辑=false
 * 不可编辑=true
 * @param state
 */
function formDisabled(state,id){
	if(!id)
		id=".form";
	$(id+" input,"+id+" select,"+id+" textarea").each(function(index,item){
		$(item).attr('readonly',state);
		if(state){
			$(item).addClass("disabled");
		}else{
			$(item).removeClass("disabled");
		}
	});
	$(id+" .select-trigger,.date-trigger,.search-trigger").each(function(index,item){
		if(state){
			$(item).addClass("triggerDisabled");
		}else{
			$(item).removeClass("triggerDisabled");
		}
	});
	
}








/****
 * 表格的皮肤
 * 统计使用此方法
 */
function getGridSkin(){
	return "mac";
}
/****
 * 表格每页面显示条数
 */
function getGridSize(){
	return 14;
}






/**
 * 折叠
 */
function chevronUpDown(id,state){
	if(state){
		$(id).slideDown();
	}else{
		$(id).slideUp();
	}
}



//======================================================
//=================弹出选择==========================
//======================================================

/**
 * 邦定tree的关闭事件
 * 手动输入非选择，置空
 * 选择修改名称，回复节点的名称
 */
function bindTreeMousedownEvent(id,name,tree,treeinstance){
	$("body").bind("mousedown",function(event){
		if (!(event.target.id == $(name).attr("id") || event.target.id == tree || $(event.target).parents("#"+tree).length>0)) {
			//非选择置空
			//选择后修改回复
			if(treeinstance!=null){
				var idVal=$(id).val();
				if(idVal==null||idVal==''||idVal=='null'){
					
				}else{
					var node=treeinstance.getNodeByParam("id", idVal);
					if(node==null){
						$(name).val(null);
					}else if(node.name!=$(name).val()){
						$(name).val(node.name);
					}
				}
			}
			
			$("#"+tree).hide();
		}
	}); 
}



//=======================================================
//================分页标签=========================
//=======================================================
function renderPageContaint(id,data,page,reload_P,reload_N){
	var content=$(id);
	var htm='';
	htm+='<div class="ui-state-default" style="border:0px;">';
	htm+='<table width="100%" height="30px;" align="center">';
	htm+='<tr>';
	htm+='<td width="60">';
	if(page.prePageNo>0){
		htm+='<a  href="javascript:'+reload_P+'">&nbsp;&nbsp;上页</a>';
	}
	htm+='</td>';
	htm+='<td align="center">';
	htm+='第'+page.pageno+'/'+page.total+'页';
	htm+='</td>';
	htm+='<td width="60" align="right">';
	if(page.nextPageNo<=page.total){
		htm+='<a href="javascript:'+reload_N+'">下页&nbsp;&nbsp;</a>';
	}
	htm+='</td>';
	htm+='</tr>';
	htm+='</table>';
	htm+='</div>';
	content.html(htm);
}





/**
 * 设置弹出树的位置
 * 这里要考虑页面出滚动条
 * 还要考虑弹出超页面高度向上
 */
function setTreePosation(tree,cityObj,cityOffset){
	tree.css({"left":cityOffset.left + "px"});
	if(cityOffset.top+250<=$(document.body).outerHeight()){
		tree.css({"top":cityOffset.top + cityObj.outerHeight()+"px"});
	}else
		tree.css({"top":cityOffset.top -200+"px"});
}


//======================================================
//=================弹出选择end==========================
//======================================================








//=======================================
//-===============全部公司树==============
//========================================
var allCompanyTree=null;
function chooseAllCompanyTree(id,name,callback){
	//只读
	if($(name).attr("readonly")){
		return;
	}
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!allCompanyTree){
		//树的层
		allCompanyTree = $('<div id="allCompanyTree" class="chooseTree" style="width:350px;"><div class="panel" style="width:350px;height:300px;"><ul id="allCompanyTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
		
		
		
		//Tree构造
		$.getJSON("company/buildAllCompanyTree.do",{valid:1}, function(data) {
			var setting = {callback: {beforeClick: function(treeId, treeNode){
				if (treeNode.id!=null) {
					$(id).val(treeNode.id);
					$(name).val(treeNode.name);
					//关闭
					allCompanyTree.hide();
					$(name).focus();
					//回调
					if(callback){
						callback(treeNode);
					}
				}else{
					$(id).val(null);
					$(name).val(null);
				}
			}}};
			var instance=$.fn.zTree.init($("#allCompanyTree_ul"),setting, data);
			//邦定关闭事件
			bindTreeMousedownEvent(id,name,"allCompanyTree",instance);
		});
	}else{
		if(allCompanyTree.css("display")!='none')
			return;
	}
	
	
	//树的位置
	allCompanyTree.show();
	setTreePosation(allCompanyTree,cityObj,cityOffset);
}





//=======================================
//-===============有权限的公司树==============
//========================================
var myHasCompanyTree=null;
function chooseMyHasCompanyTree(id,name,callback){
	
	//只读
	if($(name).attr("readonly")){
		return;
	}
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!myHasCompanyTree){
		//树的层
		myHasCompanyTree = $('<div id="myHasCompanyTree" class="chooseTree" style="width:350px;"><div class="panel" style="width:350px;height:300px;"><ul id="myHasCompanyTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
		
		
		
		//Tree构造
		$.getJSON("company/buildMyHasCompanyTree.do",{valid:1}, function(data) {
			var setting = {callback: {beforeClick: function(treeId, treeNode){
				if (treeNode.id!=null) {
					$(id).val(treeNode.id);
					$(name).val(treeNode.name);
					//关闭
					myHasCompanyTree.hide();
					$(name).focus();
					//回调
					if(callback){
						callback(treeNode);
					}
				}else{
					$(id).val(null);
					$(name).val(null);
				}
			}}};
			var instance=$.fn.zTree.init($("#myHasCompanyTree_ul"),setting, data);
			//邦定关闭事件
			bindTreeMousedownEvent(id,name,"myHasCompanyTree",instance);
		});
	}else{
		if(myHasCompanyTree.css("display")!='none')
			return;
	}
	
	
	//树的位置
	myHasCompanyTree.show();
	setTreePosation(myHasCompanyTree,cityObj,cityOffset);
}





//=======================================
//-===============公司下的部门树树==============
//========================================
var oneCompanyDeptTree=null;
function chooseOneCompanyDeptTree(id,name,companyid,callback){
	//只读
	if($(name).attr("readonly")){
		return;
	}
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!oneCompanyDeptTree){
		//树的层
		oneCompanyDeptTree = $('<div id="oneCompanyDeptTree" class="chooseTree" style="width:350px;"><div class="panel" style="width:350px;height:300px;"><ul id="oneCompanyDeptTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
		
		//邦定搜索事件
		key = $(name);
		key.bind("keyup", searchOneDeptTreeNode);
		key.bind("blur", selectOneDeptTreeNode);
	}
	
	
	//Tree构造
	$.getJSON("department/buildOneCompanyDeptTree.do",{companyid:companyid}, function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(id).val(treeNode.id);
				$(name).val(treeNode.name);
				//关闭
				oneCompanyDeptTree.hide();
				$(name).focus();
				//回调
				if(callback){
					callback(treeNode);
				}
			}else{
				$(id).val(null);
				$(name).val(null);
			}
		}},view: {
			fontCss: getFontCss
		}};
		var instance=$.fn.zTree.init($("#oneCompanyDeptTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"oneCompanyDeptTree",instance);
	});
	
	//树的位置
	oneCompanyDeptTree.show();
	setTreePosation(oneCompanyDeptTree,cityObj,cityOffset);
}




//节点搜索变色
function searchOneDeptTreeNode(e) {
	if(!nodeState){
		nodeState=true;
		var zTree = $.fn.zTree.getZTreeObj("oneCompanyDeptTree_ul");
		var value="";
		value = $.trim(key.val());
		
		updateOneDeptTreeNodes(false);
		if (value === ""){
			nodeState=false;
			return;
		}
		nodeList = zTree.getNodesByParamFuzzy("name", value);
		updateOneDeptTreeNodes(true);
		nodeState=false;
	}
}
function updateOneDeptTreeNodes(highlight) {
	if(nodeList.length<=0)
		return;
	var zTree = $.fn.zTree.getZTreeObj("oneCompanyDeptTree_ul");
	for( var i=0, l=nodeList.length; i<l; i++) {
		var node=nodeList[i];
		node.highlight = highlight;
		zTree.updateNode(nodeList[i]);
	}
}



//节点搜索定位
function selectOneDeptTreeNode(){
	var zTree = $.fn.zTree.getZTreeObj("oneCompanyDeptTree_ul");
	var value="";
	value = $.trim(key.val());
	
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





//=======================================
//-===============具有权限部門樹==============
//========================================
//搜索营业点
var myDeptTree=null;
function chooseMyDeptTree(id,name,callback){
	//只读
	if($(name).attr("readonly")){
		return;
	}
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!myDeptTree){
		//树的层
		myDeptTree = $('<div id="myDeptTree" class="chooseTree" style="width:350px;"><div class="panel" style="width:350px;height:300px;"><ul id="myDeptTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
		
		
		
		//Tree构造
		$.getJSON("department/buildMyDepartmentTree.do",{valid:1}, function(data) {
			var setting = {callback: {beforeClick: function(treeId, treeNode){
				if (treeNode.id!=null) {
					if(treeNode.code=='dept'){
						$(id).val(treeNode.id);
						$(name).val(treeNode.name);
						//公司ID
						$("#companyid").val(treeNode.ename);
						$("#companyname").val(treeNode.marker);
						
						$("#companycode").val(treeNode.companycode);
						$("#deptcode").val(treeNode.deptcode);
						
						
						//关闭
						myDeptTree.hide();
					}else if(treeNode.code=='company'&&treeNode.isaudit){
						$(id).val(null);
						$(name).val(null);
						
						$("#companyid").val(treeNode.id);
						$("#companyname").val(treeNode.name);
						
						//关闭
						myDeptTree.hide();
					}
					
					$(name).focus();
					//回调
					if(callback){
						callback(treeNode);
					}
				}else{
					$(id).val(null);
					$(name).val(null);
				}
			}},
			view: {
				fontCss: getFontCss
			}};
			var instance=$.fn.zTree.init($("#myDeptTree_ul"),setting, data);
			//邦定关闭事件
			bindTreeMousedownEvent(id,name,"myDeptTree",instance);
		});
		
		
		//邦定搜索事件
		key = $(name);
		key.bind("keyup", searchMyDeptTreeNode);
		key.bind("blur", selectMyDeptTreeNode);
	}else{
		if(myDeptTree.css("display")!='none')
			return;
	}
	
	
	
	
	
	//树的位置
	myDeptTree.show();
	setTreePosation(myDeptTree,cityObj,cityOffset);
}


//搜索节点变量
var nodeList = [];
var key;
var nodeState=false;
//节点搜索变色
function searchMyDeptTreeNode(e) {
	if(!nodeState){
		nodeState=true;
		var zTree = $.fn.zTree.getZTreeObj("myDeptTree_ul");
		var value="";
		value = $.trim(key.val());
		
		updateMyDeptTreeNodes(false);
		if (value === ""){
			nodeState=false;
			return;
		}
		nodeList = zTree.getNodesByParamFuzzy("name", value);
		updateMyDeptTreeNodes(true);
		nodeState=false;
	}
}
function updateMyDeptTreeNodes(highlight) {
	if(nodeList.length<=0)
		return;
	var zTree = $.fn.zTree.getZTreeObj("myDeptTree_ul");
	for( var i=0, l=nodeList.length; i<l; i++) {
		var node=nodeList[i];
		node.highlight = highlight;
		zTree.updateNode(nodeList[i]);
	}
}


function getFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#ff0000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}


//节点搜索定位
function selectMyDeptTreeNode(){
	var zTree = $.fn.zTree.getZTreeObj("myDeptTree_ul");
	var value="";
	value = $.trim(key.val());
	
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




//======================================================
//===============多选部门树=============================
//======================================================
var myMultipleDeptTree=null;
var myMultipleDeptTreeInstance=null;
function chooseMyMultipleDeptTree(id,name){
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!myMultipleDeptTree){
		//树的层
		var htm='<div id="myMultipleDeptTree" class="chooseTree"  style="width:350px;">';
		htm+='<div class="panel" style="width:350px;height:300px;">';
		htm+='<ul id="myMultipleDeptTree_ul" class="ztree"></ul>';
		htm+='</div>';
		htm+='<div class="pagelabel">';
		htm+='<div class="ui-state-default" style="border:0px;border-top:1px solid #cccccc;">';
		htm+='<table height="30px;" align="center">';
		htm+='<tr>';
		htm+='<td align="right">';
		htm+='<a class="ui-button ui-button-text-icons" href="javascript:chooseMyMultipleDeptTreeOk(\''+id+'\',\''+name+'\');"><span class="ui-icon ui-icon-check"></span><span class="ui-button-text">确定</span></a>';
		htm+='<a class="ui-button ui-button-text-icons" href="javascript:chooseMyMultipleDeptTreeCancle(\''+id+'\',\''+name+'\');"><span class="ui-icon ui-icon-cancel"></span><span class="ui-button-text">清空</span></a>';
		htm+='</td>';
		htm+='</tr>';
		htm+='</table>';
		htm+='</div>';
		htm+='</div>';
		htm+='</div>';
		//渲染
		myMultipleDeptTree = $(htm).appendTo(document.body);
		
		//Tree构造
		$.getJSON("department/buildMyDepartmentTree.do",{valid:1}, function(data) {
			var setting = {check: {
				//chkboxType:{"Y":"Y","N":"N"},
				enable: true
			},
			view: {
				fontCss: getFontCss
			}};
			myMultipleDeptTreeInstance=$.fn.zTree.init($("#myMultipleDeptTree_ul"),setting, data);
			//邦定关闭事件
			bindTreeMousedownEvent(id,name,"myMultipleDeptTree",null);
			
			//邦定搜索事件
			key = $(name);
			key.bind("keyup", searchMyMultipleDeptTreeNode);
	    });
	}else{
		if(myMultipleDeptTree.css("display")!='none')
			return;
	}
	
	
	
	//树的位置
	myMultipleDeptTree.show();
	setTreePosation(myMultipleDeptTree,cityObj,cityOffset);
}


//确定
function chooseMyMultipleDeptTreeOk(id,name){
	var nodes=myMultipleDeptTreeInstance.getCheckedNodes(true);
	var company_ids=new Array();
	var dept_ids=new Array();
	
	var names=new Array();
	
	for(var i=0;i<nodes.length;i++){
		var node=nodes[i];
		if(node.id!=null&&node.id!=''&&node.id!='null'){
			if(node.code=='dept'){
				dept_ids.push("'"+node.id+"'");
			}else{
				company_ids.push("'"+node.id+"'");
			}
			names.push(node.name);
		}
	}
	
	$("#companyid").val(company_ids.toString());
	$(id).val(dept_ids.toString());
	$(name).val(names.toString());
	
	myMultipleDeptTree.hide();
}

//取消
function chooseMyMultipleDeptTreeCancle(id,name){
	$("#companyid").val(null);
	$(id).val(null);
	$(name).val(null);
	myMultipleDeptTree.hide();
}






//节点搜索变色
function searchMyMultipleDeptTreeNode(e) {
	if(!nodeState){
		nodeState=true;
		var zTree = $.fn.zTree.getZTreeObj("myMultipleDeptTree_ul");
		var value="";
		value = $.trim(key.val());
		
		updateMyMultipleDeptTreeNodes(false);
		if (value === ""){
			nodeState=false;
			return;
		}
		nodeList = zTree.getNodesByParamFuzzy("name", value);
		updateMyMultipleDeptTreeNodes(true);
		nodeState=false;
	}
}
function updateMyMultipleDeptTreeNodes(highlight) {
	if(nodeList.length<=0)
		return;
	var zTree = $.fn.zTree.getZTreeObj("myMultipleDeptTree_ul");
	for( var i=0, l=nodeList.length; i<l; i++) {
		var node=nodeList[i];
		node.highlight = highlight;
		zTree.updateNode(nodeList[i]);
	}
}

















//====================================================
//====选项树 针对没有构建类型的选项 这里传入选项类型=====
//====================================================
var optionTree=null;
function chooseOptionTree(id,name,type){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!optionTree){
		//树的层
		optionTree = $('<div id="optionTree" class="chooseTree"><div class="panel"><ul id="optionTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
	}else
		$("#optionTree_ul").html(null);
	
	//Tree构造
	$.getJSON("system/buildOptionListTree.do",{code:type}, function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(id).val(treeNode.id);
				$(name).val(treeNode.name);
				$(name).focus();
				optionTree.hide();
			}else{
				$(id).val(null);
				$(name).val(null);
			}
		}}};
		$.fn.zTree.init($("#optionTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"optionTree",null);
    });
	
	//树的位置
	optionTree.show();
	setTreePosation(optionTree,cityObj,cityOffset);
}






//====================================================
//====多选项树 针对没有构建类型的选项 这里传入选项类型=====
//====================================================
var multipleOptionTree=null;
var multipleOptionTreeInstance=null;
function chooseMyMultipleOptionTree(id,name,type){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	//确定//取消
	var buttons='<a class="ui-button ui-button-text-icons" href="javascript:chooseMyMultipleOptionTreeOk(\''+id+'\',\''+name+'\');"><span class="ui-icon ui-icon-check"></span><span class="ui-button-text">确定</span></a>';
	buttons+='<a class="ui-button ui-button-text-icons" href="javascript:chooseMyMultipleOptionTreeCancle(\''+id+'\',\''+name+'\');"><span class="ui-icon ui-icon-cancel"></span><span class="ui-button-text">清空</span></a>';
	
	
	if (!multipleOptionTree){
		var htm='<div id="multipleOptionTree" class="chooseTree">';
		htm+='<div class="panel">';
		htm+='<ul id="multipleOptionTree_ul" class="ztree"></ul>';
		htm+='</div>';
		htm+='<div class="pagelabel">';
		htm+='<div class="ui-state-default" style="border:0px;border-top:1px solid #cccccc;">';
		htm+='<table height="30px;" align="center">';
		htm+='<tr>';
		htm+='<td align="right" id="multipleOptionTree_toolbar">';
		
		htm+='</td>';
		htm+='</tr>';
		htm+='</table>';
		htm+='</div>';
		htm+='</div>';
		htm+='</div>';
		
		//树的层
		multipleOptionTree = $(htm).appendTo(document.body);
	}
	$("#multipleOptionTree_toolbar").html(buttons);
	
	//Tree构造
	$.getJSON("system/buildOptionListTree.do",{code:type}, function(data) {
		var setting = {check: {
			//chkboxType:{"Y":"Y","N":"N"},
			enable: true
		}};
		multipleOptionTreeInstance=$.fn.zTree.init($("#multipleOptionTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"multipleOptionTree",null);
	});
	
	//树的位置
	multipleOptionTree.show();
	setTreePosation(multipleOptionTree,cityObj,cityOffset);
}

//确定
function chooseMyMultipleOptionTreeOk(id,name){
	var nodes=multipleOptionTreeInstance.getCheckedNodes(true);
	var ids=new Array();
	var names=new Array();
	
	for(var i=0;i<nodes.length;i++){
		var node=nodes[i];
		if(node.id!=null&&node.id!=''&&node.id!='null'){
			ids.push("'"+node.id+"'");
			names.push(node.name);
		}
		
	}
	
	//设置值
	$(id).val(ids.toString());
	$(name).val(names.toString());
	
	multipleOptionTree.hide();
}

//取消
function chooseMyMultipleOptionTreeCancle(id,name){
	$(id).val(null);
	$(name).val(null);
	multipleOptionTree.hide();
}






















//=======================================
//-===============公司下的部门多选树==============
//========================================
var OneCompanyMultipleDeptTree=null;
var OneCompanyMultipleDeptTreeInstance=null;
function chooseOneCompanyMultipleDeptTree(id,name,companyid){
	//只读
	if($(name).attr("readonly")){
		return;
	}
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!OneCompanyMultipleDeptTree){
		//树的层
		var htm='<div id="OneCompanyMultipleDeptTree" class="chooseTree"  style="width:350px;">';
		htm+='<div class="panel" style="width:350px;height:300px;">';
		htm+='<ul id="OneCompanyMultipleDeptTree_ul" class="ztree"></ul>';
		htm+='</div>';
		htm+='<div class="pagelabel">';
		htm+='<div class="ui-state-default" style="border:0px;border-top:1px solid #cccccc;">';
		htm+='<table height="30px;" align="center">';
		htm+='<tr>';
		htm+='<td align="right">';
		htm+='<a class="ui-button ui-button-text-icons" href="javascript:chooseOneCompanyMultipleDeptTreeOk(\''+id+'\',\''+name+'\',\''+companyid+'\');"><span class="ui-icon ui-icon-check"></span><span class="ui-button-text">确定</span></a>';
		htm+='<a class="ui-button ui-button-text-icons" href="javascript:chooseOneCompanyMultipleDeptTreeCancle(\''+id+'\',\''+name+'\',\''+companyid+'\');"><span class="ui-icon ui-icon-cancel"></span><span class="ui-button-text">清空</span></a>';
		htm+='</td>';
		htm+='</tr>';
		htm+='</table>';
		htm+='</div>';
		htm+='</div>';
		htm+='</div>';
		
		//渲染
		OneCompanyMultipleDeptTree = $(htm).appendTo(document.body);
		
		//Tree构造
		$.getJSON("department/buildMyMultipleDepartmentTree.do",{companyid:companyid}, function(data) {
			var setting = {check: {
				//chkboxType:{"Y":"Y","N":"N"},
				enable: true
			},
			view: {
				fontCss: getFontCss
			}};
			OneCompanyMultipleDeptTreeInstance=$.fn.zTree.init($("#OneCompanyMultipleDeptTree_ul"),setting, data);
			//邦定关闭事件
			bindTreeMousedownEvent(id,name,"OneCompanyMultipleDeptTree",null);
			
			//邦定搜索事件
			key = $(name);
			key.bind("keyup", searchOneCompanyMultipleDeptTreeNode);
	    });
	}else{
		if(OneCompanyMultipleDeptTree.css("display")!='none')
			return;
	}
	
	//树的位置
	OneCompanyMultipleDeptTree.show();
	setTreePosation(OneCompanyMultipleDeptTree,cityObj,cityOffset);
}


//确定
function chooseOneCompanyMultipleDeptTreeOk(id,name,companyid){
	var nodes=OneCompanyMultipleDeptTreeInstance.getCheckedNodes(true);
	var dept_ids=new Array();
	var names=new Array();
	
	for(var i=0;i<nodes.length;i++){
		var node=nodes[i];
		if(node.id!=null&&node.id!=''&&node.id!='null'){
			if(node.code=='dept'){
				dept_ids.push("'"+node.id+"'");
			}
			names.push(node.name);
		}
	}
	
	$("#companyid").val(companyid.toString());
	$(id).val(dept_ids.toString());
	$(name).val(names.toString());
	
	OneCompanyMultipleDeptTree.hide();
}

//取消
function chooseOneCompanyMultipleDeptTreeCancle(id,name,companyid){
	$("#companyid").val(null);
	$(id).val(null);
	$(name).val(null);
	OneCompanyMultipleDeptTree.hide();
}

//节点搜索变色
function searchOneCompanyMultipleDeptTreeNode(e) {
	if(!nodeState){
		nodeState=true;
		var zTree = $.fn.zTree.getZTreeObj("OneCompanyMultipleDeptTree_ul");
		var value="";
		value = $.trim(key.val());
		
		updateOneCompanyMultipleDeptTreeNodes(false);
		if (value === ""){
			nodeState=false;
			return;
		}
		nodeList = zTree.getNodesByParamFuzzy("name", value);
		updateOneCompanyMultipleDeptTreeNodes(true);
		nodeState=false;
	}
}

function updateOneCompanyMultipleDeptTreeNodes(highlight) {
	if(nodeList.length<=0)
		return;
	var zTree = $.fn.zTree.getZTreeObj("OneCompanyMultipleDeptTree_ul");
	for( var i=0, l=nodeList.length; i<l; i++) {
		var node=nodeList[i];
		node.highlight = highlight;
		zTree.updateNode(nodeList[i]);
	}
	
}



//=======================================
//-===============全部体检机构树==============
//========================================
var allPartnerTree=null;
function chooseAllPartnerTree(id,name,callback){
		//只读
		if($(name).attr("readonly")){
			return;
		}
		var cityObj = $(name);
		var cityOffset = $(name).offset();
		
		if (!allPartnerTree){
			//树的层
			allPartnerTree = $('<div id="allPartnerTree" class="chooseTree" style="width:230px;"><div class="panel" style="width:200px;height:220px;"><ul id="allPartnerTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
			
			
			
			//Tree构造
			$.getJSON("thirdpartner/buildPartnerCheckTree.do",{valid:1}, function(data) {
				var setting = {callback: {beforeClick: function(treeId, treeNode){
					if (treeNode.id!=null) {
						$(id).val(treeNode.id);
						$(name).val(treeNode.name);
						//关闭
						allPartnerTree.hide();
						$(name).focus();
						//回调
						if(callback){
							callback(treeNode);
						}
					}else{
						$(id).val(null);
						$(name).val(null);
					}
				}}};
				var instance=$.fn.zTree.init($("#allPartnerTree_ul"),setting, data);
				//邦定关闭事件
				bindTreeMousedownEvent(id,name,"allPartnerTree",instance);
			});
		}else{
			if(allPartnerTree.css("display")!='none')
				return;
		}
		
		
		//树的位置
		allPartnerTree.show();
		setTreePosation(allPartnerTree,cityObj,cityOffset);
	}


