

//============招聘专员树========================================
var recruiterTree=null;
function chooseRecruiterTree(id,name,callback){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!recruiterTree){
		//树的层
		recruiterTree = $('<div id="recruiterTree" class="chooseTree"><div class="panel"><ul id="recruiterTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
	}else
		$("#recruiterTree_ul").html(null);
	
	//Tree构造
	$.getJSON("resume/buildRecruiterTree.do", function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(id).val(treeNode.id);
				$(name).val(treeNode.name);
				
				$(name).focus();
				
				//回调
				if(callback){
					callback(treeNode);
				}
				recruiterTree.hide();
			}else{
				$(id).val(null);
				$(name).val(null);
			}
		}}};
		$.fn.zTree.init($("#recruiterTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(id,name,"recruiterTree",null);
	});
	
	//树的位置
	recruiterTree.show();
	setTreePosation(recruiterTree,cityObj,cityOffset);
}

//职务树
var PostNameTree=null;
function choosePostNameTree(name,userguid,callback){
	//不可编辑不能选择
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!PostNameTree){
		//树的层
		PostNameTree = $('<div id="PostNameTree" class="chooseTree"><div class="panel"><ul id="PostNameTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
	}else
		$("#PostNameTree_ul").html(null);
	
	//Tree构造
	$.getJSON("resume/buildPostNameTree.do",{userguid:userguid}, function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			if (treeNode.id!=null) {
				$(name).val(treeNode.name);
				$(name).focus();
				
				if(callback){
					callback(treeNode.id);
				}
				
				PostNameTree.hide();
			}else{
				$(name).val(null);
			}
		}}};
		$.fn.zTree.init($("#PostNameTree_ul"),setting, data);
		//邦定关闭事件
		bindTreeMousedownEvent(name,name,"PostNameTree",null);
	});
	
	//树的位置
	PostNameTree.show();
	setTreePosation(PostNameTree,cityObj,cityOffset);
}

//邮箱用户树
var emailTree=null;
function chooseemailTree(name,emailTreeCallback){
	//只读
	if($(name).attr("readonly")){
		return;
	}
	
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!emailTree){
		//树的层
		var htm='<div id="emailTree" class="chooseTree">';
		htm+='<div class="panel">';
		htm+='<ul id="emailTree_ul" class="ztree"></ul>';
		htm+='</div>';
		htm+='<div class="pagelabel" id="myEmailPage">';
		
		htm+='</div>';
		htm+='</div>';
		emailTree=$(htm).appendTo(document.body);
		
		//邦定搜索事件
		$(name).bind("keyup",function(){
			//加载数据
			loadEmailData(name,1);
		}); 
	}
	
	//加载数据
	loadEmailData(name,1);
	
	//树的位置
	emailTree.show();
	setTreePosation(emailTree,cityObj,cityOffset);
}


//加载数据
function loadEmailData(name,pageno){
	var grid={page:{pagesize:8,pageno:pageno},parameters:{name:$(name).val()}};
	$.ajax({  
      url: "recruitment/searchEmailTree.do",  
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
					$(name).val(treeNode.name);
					$(name).focus();
					
					//事件回调
					if(emailTreeCallback)
						emailTreeCallback(treeNode.id);
					
					//关闭树
					emailTree.hide();
				}else{
					$(name).val(null);
				}
				
			}}};
	      	var instance= $.fn.zTree.init($("#emailTree_ul"),setting, data);
			//邦定关闭事件
			bindTreeMousedownEvent(name,name,"emailTree",null);
			
			//分页
			var reload_P="loadEmailData('"+name+"',"+page.prePageNo+");";
			var reload_N="loadEmailData('"+name+"',"+page.nextPageNo+");";
			renderPageContaint("#myEmailPage",data,page,reload_P,reload_N);
      }
  });
}