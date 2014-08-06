//====================================================
//===========公司職務樹===============================
//====================================================
var jobTree=null;
function chooseJobTree(id,name,companyid){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!jobTree){
		//树的层
		jobTree = $('<div id="jobTree" class="chooseTree"><div class="panel"><ul id="jobTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
	}else
		$("#jobTree_ul").html(null);
	
	//Tree构造
	$.getJSON("company/buildJobTree.do",{companyid:companyid}, function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(id).val(treeNode.id);
				$(name).val(treeNode.name);
				$(name).focus();
				jobTree.hide();
			}else{
				$(id).val(null);
				$(name).val(null);
			}
		}}};
		$.fn.zTree.init($("#jobTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"jobTree",null);
    });
	
	//树的位置
	jobTree.show();
	setTreePosation(jobTree,cityObj,cityOffset);
}



//===========公司职级樹===============================
//====================================================
var rankTree=null;
function chooseRankTree(id,name,companyid){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!rankTree){
		//树的层
		rankTree = $('<div id="rankTree" class="chooseTree"><div class="panel"><ul id="rankTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
	}else
		$("#rankTree_ul").html(null);
	
	//Tree构造
	$.getJSON("company/buildRankTree.do",{companyid:companyid}, function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(id).val(treeNode.id);
				$(name).val(treeNode.name);
				$(name).focus();
				rankTree.hide();
			}else{
				$(id).val(null);
				$(name).val(null);
			}
		}}};
		$.fn.zTree.init($("#rankTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"rankTree",null);
  });
	
	//树的位置
	rankTree.show();
	setTreePosation(rankTree,cityObj,cityOffset);
}




//====================================================
//===========部门岗位树===============================
//====================================================
var postTree=null;
function choosePostTree(id,name,deptid,callback){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!postTree){
		//树的层
		postTree = $('<div id="postTree" class="chooseTree"><div class="panel"><ul id="postTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
	}else
		$("#postTree_ul").html(null);
	
	//Tree构造
	$.getJSON("department/buildPostTree.do",{deptid:deptid}, function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(id).val(treeNode.id);
				$(name).val(treeNode.name);
				$(name).focus();
				
				//回调
				if(callback){
					callback(treeNode);
				}
				
				postTree.hide();
			}else{
				$(id).val(null);
				$(name).val(null);
			}
		}}};
		$.fn.zTree.init($("#postTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"postTree",null);
  });
	
	//树的位置
	postTree.show();
	setTreePosation(postTree,cityObj,cityOffset);
}


//====================================================
//===========自己部门以及子部门下的岗位树===============================
//====================================================
var myPostTree=null;
function chooseMyPostTree(id,name,deptid,callback){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!myPostTree){
		//树的层
		myPostTree = $('<div id="myPostTree" class="chooseTree"><div class="panel"><ul id="myPostTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
	}else
		$("#myPostTree_ul").html(null);
	
	//Tree构造
	$.getJSON("department/buildMyPostTreeByDept.do",{deptid:deptid}, function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(id).val(treeNode.id);
				$(name).val(treeNode.name);
				$(name).focus();
				
				//回调
				if(callback){
					callback(treeNode);
				}
				
				myPostTree.hide();
			}else{
				$(id).val(null);
				$(name).val(null);
			}
		}}};
		$.fn.zTree.init($("#myPostTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"myPostTree",null);
	});
	
	//树的位置
	myPostTree.show();
	setTreePosation(myPostTree,cityObj,cityOffset);
}









//====================================================
//===========岗位编制树===============================
//====================================================
var quotaTree=null;
function chooseQuotaTree(id,name,postid,callback){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!quotaTree){
		//树的层
		quotaTree = $('<div id="quotaTree" class="chooseTree"><div class="panel"><ul id="quotaTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
	}else
		$("#quotaTree_ul").html(null);
	
	//Tree构造
	$.getJSON("quota/buildMyQuotaTree.do",{postid:postid}, function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(id).val(treeNode.id);
				$(name).val(treeNode.name);
				$(name).focus();
				quotaTree.hide();
				
				//回调
				if(callback){
					callback(treeNode);
				}
			}else{
				$(id).val(null);
				$(name).val(null);
			}
		}}};
		$.fn.zTree.init($("#quotaTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"quotaTree",null);
	});
	
	//树的位置
	quotaTree.show();
	setTreePosation(quotaTree,cityObj,cityOffset);
}



//二级部门树
var pDeptTree=null;
function choosePDeptTree(id,name,deptid,callback){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!pDeptTree){
		//树的层
		pDeptTree = $('<div id="pDeptTree" class="chooseTree"><div class="panel"><ul id="pDeptTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
	}else
		$("#pDeptTree_ul").html(null);
	
	//Tree构造
	$.getJSON("department/buildPDeptTree.do",{deptid:deptid}, function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(id).val(treeNode.id);
				$(name).val(treeNode.name);
				$(name).focus();
				
				//回调
				if(callback){
					callback(treeNode);
				}
				
				pDeptTree.hide();
				
			}else{
				$(id).val(null);
				$(name).val(null);
			}
		}}};
		$.fn.zTree.init($("#pDeptTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"pDeptTree",null);
	});
	
	//树的位置
	pDeptTree.show();
	setTreePosation(pDeptTree,cityObj,cityOffset);
}




//======================================
//===========员工树=================
//======================================
var employeeTree=null;
function chooseEmployeeTree(id,name,workstate){
	//只读
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!employeeTree){
		//树的层
		var htm='<div id="employeeTree" class="chooseTree">';
		htm+='<div class="panel">';
		htm+='<ul id="employeeTree_ul" class="ztree"></ul>';
		htm+='</div>';
		htm+='<div class="pagelabel" id="myCardnoPage">';
		
		htm+='</div>';
		htm+='</div>';
		employeeTree=$(htm).appendTo(document.body);
		
		//邦定搜索事件
		$(name).bind("keyup",function(){
			//加载数据
			loadEmployeeData(id,name,workstate,1);
		}); 
		//加载数据
		loadEmployeeData(id,name,workstate,1);
	}
	
	//树的位置
	employeeTree.show();
	setTreePosation(employeeTree,cityObj,cityOffset);
}

//加载数据
function loadEmployeeData(id,name,workstate,pageno){
	var grid={page:{pagesize:8,pageno:pageno},parameters:{workstate:workstate,name:$(name).val()}};
	$.ajax({  
      url: "employee/searchEmployeeTree.do",  
      contentType: "application/json; charset=utf-8",  
      type: "POST",  
      dataType: "json",  
      data: JSON.stringify(grid),
      success: function(result) { 
      	var data=result.dataList;
      	var page=result.page;
      	//Tree构造
      	var setting = {callback: {beforeClick: function(treeId, treeNode){
				if (treeNode.id!=null) {
					//$(id).val(treeNode.id);
					$(name).val(treeNode.name);
					$(name).focus();
					
					
					//事件回调
					if(employeeTreeCallback)
						employeeTreeCallback(treeNode.id);
					
					//关闭树
					employeeTree.hide();
				}else{
					//$(id).val(null);
					$(name).val(null);
					
				}
				
			}}};
      	var instance= $.fn.zTree.init($("#employeeTree_ul"),setting, data);
			//邦定关闭事件
			bindTreeMousedownEvent(id,name,"employeeTree",null);
			
			//分页
			var reload_P="loadEmployeeData('"+id+"','"+name+"','"+workstate+"',"+page.prePageNo+");";
			var reload_N="loadEmployeeData('"+id+"','"+name+"','"+workstate+"',"+page.nextPageNo+");";
			renderPageContaint("#myCardnoPage",data,page,reload_P,reload_N);
      }
  });
}

//===========主面试官树=================
//======================================
var interviewerTree=null;
var callbackInterviewerTree;
function chooseinterviewerTree(id,name,deptid,callback){
	
	//只读
	if($(name).attr("readonly")){
		return;
	}
	callbackInterviewerTree=callback;
	
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!interviewerTree){
		//树的层
		var htm='<div id="interviewerTree" class="chooseTree">';
		htm+='<div class="panel">';
		htm+='<ul id="interviewerTree_ul" class="ztree"></ul>';
		htm+='</div>';
		htm+='<div class="pagelabel" id="myCardnoPageinterviewer">';
		
		htm+='</div>';
		htm+='</div>';
		interviewerTree=$(htm).appendTo(document.body);
		
	}
	
	//邦定搜索事件
	$(name).bind("keyup",function(){
		//加载数据
		loadinterviewerData(id,name,deptid,1);
	}); 
	//加载数据
	loadinterviewerData(id,name,deptid,1);
	
	//树的位置
	interviewerTree.show();
	setTreePosation(interviewerTree,cityObj,cityOffset);
}


//加载数据
function loadinterviewerData(id,name,deptid,pageno){
	var grid={page:{pagesize:8,pageno:pageno},parameters:{deptid:deptid,name:$(name).val()}};
	$.ajax({  
      url: "employee/searchUserTree.do",
      contentType: "application/json; charset=utf-8",
      type: "POST",  
      dataType: "json",  
      data: JSON.stringify(grid),
      success: function(result) { 
      	var data=result.dataList;
      	var page=result.page;
      	//Tree构造
      	var setting = {callback: {beforeClick: function(treeId, treeNode){
				if (treeNode.id!=null) {
					$(id).val(treeNode.id);
					$(name).val(treeNode.name);
					$(name).focus();
					
					
					//事件回调
					if(callbackInterviewerTree&&callbackInterviewerTree!=null)
						callbackInterviewerTree(treeNode);
					
					//关闭树
					interviewerTree.hide();
				}else{
					$(id).val(null);
					$(name).val(null);
					
				}
				
			}}};
      	var instance= $.fn.zTree.init($("#interviewerTree_ul"),setting, data);
			//邦定关闭事件
			bindTreeMousedownEvent(id,name,"interviewerTree",null);
			
			//分页
			var reload_P="loadinterviewerData('"+id+"','"+name+"','"+deptid+"',"+page.prePageNo+");";
			var reload_N="loadinterviewerData('"+id+"','"+name+"','"+deptid+"',"+page.nextPageNo+");";
			renderPageContaint("#myCardnoPageinterviewer",data,page,reload_P,reload_N);
      }
  });
}


//===========面试官(抄送)树=================
//======================================
var ids=null;
var names=null;
var maininterviewerTree=null;
var maininterviewerTreeInstance=null;
function choosemaininterviewerTree(id,name){
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!maininterviewerTree){
		ids=new Array();
		names=new Array();
		
		//树的层
		var htm='<div id="maininterviewerTree" class="chooseTree">';
		/*
		htm+='<div class="pagelabel ui-state-default" style="border:0px;text-align:right;padding:2px;">';
		htm+='<a class="ui-button ui-button-text-icons" href="javascript:chooseMaininterviewerTreeOk(\''+id+'\',\''+name+'\');"><span class="ui-icon ui-icon-check"></span><span class="ui-button-text">确定</span></a>';
		htm+='<a class="ui-button ui-button-text-icons" href="javascript:chooseMaininterviewerTreeClear(\''+id+'\',\''+name+'\');"><span class="ui-icon ui-icon-close"></span><span class="ui-button-text">清空</span></a>';
		htm+='<a class="ui-button ui-button-text-icons" href="javascrip:chooseMaininterviewerTreeCancle(\''+id+'\',\''+name+'\');"><span class="ui-icon ui-icon-cancel"></span><span class="ui-button-text">取消</span></a>';
		htm+='</div>';
		*/
		htm+='<div class="panel" >';
		htm+='<ul id="maininterviewerTree_ul" class="ztree"></ul>';
		htm+='</div>';
		
		htm+='<div id="myCardnoPageMaininterviewer" class="pagelabel">';
		
		htm+='</div>';
		htm+='</div>';
		maininterviewerTree=$(htm).appendTo(document.body);
		
		//邦定搜索事件
		$(name).bind("keyup",function(){
			//加载数据
			loadmaininterviewerData(id,name,1);
		}); 
		
	}
	
	//树的位置
	//加载数据
	loadmaininterviewerData(id,name,1);
	maininterviewerTree.show();
	setTreePosation(maininterviewerTree,cityObj,cityOffset);
}


//加载数据
function loadmaininterviewerData(id,name,pageno){
	var grid={page:{pagesize:8,pageno:pageno},parameters:{name:$(name).val()}};
	$.ajax({  
      url: "employee/searchUserTree.do",
      contentType: "application/json; charset=utf-8",
      type: "POST",  
      dataType: "json",  
      data: JSON.stringify(grid),
      success: function(result) { 
      	var data=result.dataList;
      	var page=result.page;
      	//Tree构造
      	var setting = {
	      		check: {
						chkboxType:{"Y":"Y","N":"N"},
						enable: true
					},callback: {
						onCheck:function(event, treeId, treeNode){
							if(treeNode.id!=null&&treeNode.id!=''){
								var isexit=false;
								$("#employeeids input").each(function(){
									var id=$(this).val();
									if(id==treeNode.id){
										isexit=true;
									}
								});
								if(!isexit){
									var htm='';
									htm+='<label id="emp_'+treeNode.id+'">';
									htm+='<input type="checkbox" class="checkboxstyle" value="'+treeNode.id+'" checked="true" onclick="removeSelected(this,\'#emp_'+treeNode.id+'\');"/>'+treeNode.ename;
									htm+='&nbsp;&nbsp;';
									htm+='</label>';
									$("#employeeids").append(htm);
								}
								$(name).val(null);
							}
						}
					}
	      	};
      	
      	
      	
      	maininterviewerTreeInstance=$.fn.zTree.init($("#maininterviewerTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"maininterviewerTree",null);
		
		//分页
		var reload_P="loadmaininterviewerData('"+id+"','"+name+"',"+page.prePageNo+");";
		var reload_N="loadmaininterviewerData('"+id+"','"+name+"',"+page.nextPageNo+");";
		renderPageContaint("#myCardnoPageMaininterviewer",data,page,reload_P,reload_N);
      }
  });
}


//删除选择的ID
function removeSelected(el,id){
	if(!$(el).attr("checked")){
		$(id).remove();
	}
}



//确定
function chooseMaininterviewerTreeOk(id,name){
	//如果 选择全部传null
	$(id).val(ids.toString());
	$(name).val(names.toString());
	
	maininterviewerTree.hide();
}

//清空
function chooseMaininterviewerTreeClear(id,name){
	ids=new Array();
	names=new Array();
	$(name).removeAttr("title");
	
	$(id).val(null);
	$(name).val(null);
	
	loadmaininterviewerData(id,name,1);
}


//取消
function chooseMaininterviewerTreeCancle(id,name){
	Ids=new Array();
	Names=new Array();
	$(name).removeAttr("title");
	
	$(id).val(null);
	$(name).val(null);
	maininterviewerTree.hide();
}




//=====岗位编制树=========
var quotaBudgettypeTree=null;
function chooseBudgettypeTree(id,name,companyid,callback){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!quotaBudgettypeTree){
		//树的层
		quotaBudgettypeTree = $('<div id="quotaBudgettypeTree" class="chooseTree"><div class="panel"><ul id="quotaBudgettypeTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
	}else
		$("#quotaBudgettypeTree_ul").html(null);
	
	//Tree构造
	$.getJSON("quota/buildQuotaTreeByCompanyid.do",{companyid:companyid}, function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(id).val(treeNode.id);
				$(name).val(treeNode.name);
				$(name).focus();
				
				//回调
				if(callback)
					callback();
				quotaBudgettypeTree.hide();
			}else{
				$(id).val(null);
				$(name).val(null);
			}
		}}};
		$.fn.zTree.init($("#quotaBudgettypeTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"quotaBudgettypeTree",null);
	});
	
	//树的位置
	quotaBudgettypeTree.show();
	setTreePosation(quotaBudgettypeTree,cityObj,cityOffset);
}


//面试地点树
var addressTree=null;
function chooseAddressTree(id,name){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!addressTree){
		//树的层
		addressTree = $('<div id="addressTree" class="chooseTree"><div class="panel"><ul id="addressTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
	}else
		$("#addressTree_ul").html(null);
	
	//Tree构造
	$.getJSON("auditionaddress/buildAllAuditionAddress.do",function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(id).val(treeNode.id);
				$(name).val(treeNode.name);
				$(name).focus();
				addressTree.hide();
			}else{
				$(id).val(null);
				$(name).val(null);
			}
		}}};
		$.fn.zTree.init($("#addressTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"addressTree",null);
	});
	
	//树的位置
	addressTree.show();
	setTreePosation(addressTree,cityObj,cityOffset);
}


//=====工作地点树=========
var workplaceTree=null;
function chooseWorkplaceTree(id,name){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!workplaceTree){
		//树的层
		workplaceTree = $('<div id="workplaceTree" class="chooseTree"><div class="panel"><ul id="workplaceTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
	}else
		$("#workplaceTree_ul").html(null);
	
	//Tree构造
	$.getJSON("recruitment/buildAllWorkPlace.do",function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(id).val(treeNode.id);
				$(name).val(treeNode.name);
				$(name).focus();
				workplaceTree.hide();
			}else{
				$(id).val(null);
				$(name).val(null);
			}
		}}};
		$.fn.zTree.init($("#workplaceTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"workplaceTree",null);
	});
	
	//树的位置
	workplaceTree.show();
	setTreePosation(workplaceTree,cityObj,cityOffset);
}

//=====职位类别树(单选)=========
var categoryTree=null;
function chooseCategoryTree(id,name){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!categoryTree){
		//树的层
		categoryTree = $('<div id="categoryTree" class="chooseTree"><div class="panel"><ul id="categoryTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
	}else
		$("#categoryTree_ul").html(null);
	
	//Tree构造
	$.getJSON("recruitment/buildAllCategory.do",function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(id).val(treeNode.id);
				$(name).val(treeNode.name);
				$(name).focus();
				categoryTree.hide();
			}else{
				$(id).val(null);
				$(name).val(null);
			}
		}}};
		$.fn.zTree.init($("#categoryTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"categoryTree",null);
	});
	
	//树的位置
	categoryTree.show();
	setTreePosation(categoryTree,cityObj,cityOffset);
}




//=====职位类别树(多选)=========
var multipleCategoryTree=null;
var multipleCategoryTreeInstance=null;
function chooseMyMultipleCategoryTree(id,name){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	//确定//取消
	var buttons='<a class="ui-button ui-button-text-icons" href="javascript:chooseMyMultipleCategoryTreeOk(\''+id+'\',\''+name+'\');"><span class="ui-icon ui-icon-check"></span><span class="ui-button-text">确定</span></a>';
	buttons+='<a class="ui-button ui-button-text-icons" href="javascript:chooseMyMultipleCategoryTreeCancle(\''+id+'\',\''+name+'\');"><span class="ui-icon ui-icon-cancel"></span><span class="ui-button-text">清空</span></a>';
	
	if (!multipleCategoryTree){
		var htm='<div id="multipleCategoryTree" class="chooseTree">';
		htm+='<div class="panel">';
		htm+='<ul id="multipleCategoryTree_ul" class="ztree"></ul>';
		htm+='</div>';
		htm+='<div class="pagelabel">';
		htm+='<div class="ui-state-default" style="border:0px;border-top:1px solid #cccccc;">';
		htm+='<table height="30px;" align="center">';
		htm+='<tr>';
		htm+='<td align="right" id="multipleCategoryTree_toolbar">';
		
		htm+='</td>';
		htm+='</tr>';
		htm+='</table>';
		htm+='</div>';
		htm+='</div>';
		htm+='</div>';
		
		//树的层
		multipleCategoryTree = $(htm).appendTo(document.body);
	}
	$("#multipleCategoryTree_toolbar").html(buttons);
	
	//Tree构造
	$.getJSON("recruitment/buildCategoryTree.do", function(data) {
		var setting = {check: {
			//chkboxType:{"Y":"Y","N":"N"},
			enable: true
		}};
		multipleCategoryTreeInstance=$.fn.zTree.init($("#multipleCategoryTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"multipleCategoryTree",null);
	});
	
	//树的位置
	multipleCategoryTree.show();
	setTreePosation(multipleCategoryTree,cityObj,cityOffset);
}


//确定
function chooseMyMultipleCategoryTreeOk(id,name){
	var nodes=multipleCategoryTreeInstance.getCheckedNodes(true);
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
	
	multipleCategoryTree.hide();
}

//取消
function chooseMyMultipleCategoryTreeCancle(id,name){
	$(id).val(null);
	$(name).val(null);
	multipleCategoryTree.hide();
}



//====================================================
//===========招聘计划樹===============================
//====================================================
var recruitprogramTree=null;
function chooseRecruitprogramTree(id,name){
	//只读
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!recruitprogramTree){
		//树的层
		var htm='<div id="recruitprogramTree" class="chooseTree">';
		htm+='<div class="panel">';
		htm+='<ul id="recruitprogramTree_ul" class="ztree"></ul>';
		htm+='</div>';
		htm+='<div class="pagelabel" id="myCardnoPage">';
		
		htm+='</div>';
		htm+='</div>';
		recruitprogramTree=$(htm).appendTo(document.body);
		
		//邦定搜索事件
		$(name).bind("keyup",function(){
			//加载数据
			loadRecruitprogramData(id,name,1);
		}); 
	}
	
	//加载数据
	loadRecruitprogramData(id,name,1);
	
	//树的位置
	recruitprogramTree.show();
	setTreePosation(recruitprogramTree,cityObj,cityOffset);
}


//加载数据
function loadRecruitprogramData(id,name,pageno){
	var companyid=$("#companyid").val();
	var deptid=$("#deptid").val();
	var grid={page:{pagesize:8,pageno:pageno},parameters:{companyid:companyid,deptid:deptid,name:$(name).val()}};
	$.ajax({  
      url: "recruitprogram/searchRecruitprogramTree.do",  
      contentType: "application/json; charset=utf-8",  
      type: "POST",  
      dataType: "json",  
      data: JSON.stringify(grid),
      success: function(result) { 
      	var data=result.dataList;
      	var page=result.page;
      	//Tree构造
      	var setting = {callback: {beforeClick: function(treeId, treeNode){
				if (treeNode.id!=null) {
					$(id).val(treeNode.id);
					$(name).val(treeNode.name);
					$(name).focus();
					
					//事件回调
					recruitprogramTreeCallback(treeNode.id);
					
					//关闭树
					recruitprogramTree.hide();
				}else{
					$(id).val(null);
					$(name).val(null);
					
				}
				
			}}};
      	var instance= $.fn.zTree.init($("#recruitprogramTree_ul"),setting, data);
			//邦定关闭事件
			bindTreeMousedownEvent(id,name,"recruitprogramTree",null);
			
			//分页
			var reload_P="loadRecruitprogramData('"+id+"','"+name+"','"+page.prePageNo+");";
			var reload_N="loadRecruitprogramData('"+id+"','"+name+"','"+page.nextPageNo+");";
			renderPageContaint("#myCardnoPage",data,page,reload_P,reload_N);
      }
  });
}


//部门岗位树
var mypostTree=null;
function buildMyPostTree(id,name){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!mypostTree){
		//树的层
		mypostTree = $('<div id="mypostTree" class="chooseTree"><div class="panel"><ul id="mypostTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
	}else
		$("#mypostTree_ul").html(null);
	
	//Tree构造
	$.getJSON("department/buildMyPostTree.do",{valid:1}, function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(id).val(treeNode.id);
				$(name).val(treeNode.name);
				$(name).focus();
				
				
				mypostTree.hide();
			}else{
				$(id).val(null);
				$(name).val(null);
			}
		}}};
		$.fn.zTree.init($("#mypostTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"mypostTree",null);
  });
	
	//树的位置
	mypostTree.show();
	setTreePosation(mypostTree,cityObj,cityOffset);
}

