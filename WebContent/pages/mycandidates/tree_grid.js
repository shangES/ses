

//==============================================================================
//面试官
var gridinterviewerTree=null;
function chooseinterviewerGuidTree(name){
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!gridinterviewerTree){
		//树的层
		var htm='<div id="gridinterviewerTree" class="chooseTree">';
		htm+='<div class="panel">';
		htm+='<ul id="gridinterviewerTree_ul" class="ztree"></ul>';
		htm+='</div>';
		htm+='<div id="myGridinterviewerTree" class="pagelabel">';
		
		htm+='</div>';
		htm+='</div>';
		gridinterviewerTree=$(htm).appendTo(document.body);
		
		
		//邦定关闭事件
		$("body").bind("mousedown",function(event){
			if (!(event.target.id == "gridinterviewerTree" || $(event.target).parents("#gridinterviewerTree").length>0)||event.target.className=='gt-editor-text') {
				gridinterviewerTree.hide();
			}
		});
	}
	
	//点击事件树展开
	$(".gt-editor-text").bind("click",function(event){
		gridinterviewerTree.show();
	});
	
	
	//加载数据
	loadGridinterviewerTreeData(1);
	
	//树的位置
	gridinterviewerTree.show();
	
	//计算位置
	gridinterviewerTree.css({"left":cityOffset.left + "px"});
	if(cityOffset.top+240<=$(document.body).outerHeight()){
		gridinterviewerTree.css({"top":cityOffset.top + cityObj.outerHeight()-$(document).scrollTop()+"px"});
	}else
		gridinterviewerTree.css({"top":cityOffset.top -230-$(document).scrollTop()+"px"});
}


//加载数据
function loadGridinterviewerTreeData(pageno){
	//使树显示
	if(gridinterviewerTree!=null)
		gridinterviewerTree.show();
	
	var grid={page:{pagesize:9,pageno:pageno},parameters:{name:$('.gt-editor-text').val()}};
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
					$('.gt-editor-text').val(treeNode.ename);
					mygrid.activeRecord.maininterviewerguid=treeNode.id;
					
					
					//面试官置值
					mygrid.forEachRow(function(row,record,i,grid){
						if(record.maininterviewerguidname==null&&record.maininterviewerguid==null){
							record.maininterviewerguidname=treeNode.ename;
							record.maininterviewerguid=treeNode.id;
						}
							
					});
					mygrid.refresh();
					
				}else{
					$('.gt-editor-text').val(null);
					mygrid.activeRecord.maininterviewerguid=null;
				}
				
				//关闭树
				mygrid.endEdit();
				gridinterviewerTree.hide();
			}}};
        	$.fn.zTree.init($("#gridinterviewerTree_ul"),setting, data);
			
			//分页
			var reload_P="loadGridinterviewerTreeData("+page.prePageNo+");";
			var reload_N="loadGridinterviewerTreeData("+page.nextPageNo+");";
			renderPageContaint("#myGridinterviewerTree",data,page,reload_P,reload_N);
        }
    });
}














//面试官(抄送)
var ids=null;
var names=null;
var mainInterviewerGuidTree=null;
var mainInterviewerGuidTreeInstance=null;
function chooseMainInterviewerGuidTree(name){
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!mainInterviewerGuidTree){
		ids=new Array();
		names=new Array();
		
		//树的层
		var htm='<div id="mainInterviewerGuidTree" class="chooseTree">';
		htm+='<div class="pagelabel ui-state-default" style="border:0px;text-align:right;padding:2px;">';
		htm+='<a class="ui-button ui-button-text-icons" href="javascript:chooseMainInterviewerGuidTreeOk(\''+name+'\');"><span class="ui-icon ui-icon-check"></span><span class="ui-button-text">确定</span></a>';
		htm+='<a class="ui-button ui-button-text-icons" href="javascript:chooseMainInterviewerGuidTreeClear(\''+name+'\');"><span class="ui-icon ui-icon-close"></span><span class="ui-button-text">清空</span></a>';
		htm+='<a class="ui-button ui-button-text-icons" href="javascrip:chooseMainInterviewerGuidTreeCancle(\''+name+'\');"><span class="ui-icon ui-icon-cancel"></span><span class="ui-button-text">取消</span></a>';
		htm+='</div>';
		
		htm+='<div class="panel" style="width:250px;height:180px;">';
		htm+='<ul id="mainInterviewerGuidTree_ul" class="ztree"></ul>';
		htm+='</div>';
		
		htm+='<div id="myMainInterviewerGuidTree" class="pagelabel">';
		
		htm+='</div>';
		htm+='</div>';
		mainInterviewerGuidTree=$(htm).appendTo(document.body);
		
		//邦定搜索事件
		$(name).bind("keyup",function(){
			//加载数据
			loadMainInterviewerData(1);
		}); 
		
	}
	
	//加载数据
	loadMainInterviewerData(1);
	
	//显示
	mainInterviewerGuidTree.show();
	
	//计算位置
	mainInterviewerGuidTree.css({"left":cityOffset.left + "px"});
	if(cityOffset.top+240<=$(document.body).outerHeight()){
		mainInterviewerGuidTree.css({"top":cityOffset.top + cityObj.outerHeight()-$(document).scrollTop()+"px"});
	}else
		mainInterviewerGuidTree.css({"top":cityOffset.top -230-$(document).scrollTop()+"px"});
	
	//setTreePosation(maininterviewerTree,cityObj,cityOffset);
}



function loadMainInterviewerData(pageno){
	var grid={page:{pagesize:8,pageno:pageno},parameters:{name:$('.gt-editor-text').val()}};
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
								//$('.gt-editor-text').val(treeNode.name);
								ids.push(treeNode.id);
								names.push(treeNode.name);
								//$('.gt-editor-text').val(names.toString());
							}
						}
					}
	      	};
      	
      	
      	
      	mainInterviewerGuidTreeInstance=$.fn.zTree.init($("#mainInterviewerGuidTree_ul"),setting, data);
		//邦定关闭事件
      	$("body").bind("mousedown",function(event){
			if (!(event.target.id == "mainInterviewerGuidTree" || $(event.target).parents("#mainInterviewerGuidTree").length>0)) {
				mainInterviewerGuidTree.hide();
			}
		});
		
		//分页
		var reload_P="loadMainInterviewerData("+page.prePageNo+");";
		var reload_N="loadMainInterviewerData("+page.nextPageNo+");";
		renderPageContaint("#myMainInterviewerGuidTree",data,page,reload_P,reload_N);
      }
  });
}

//确定
function chooseMainInterviewerGuidTreeOk(name){
	$('.gt-editor-text').val(names.toString());
	mygrid.activeRecord.userguid=ids.toString();
	mainInterviewerGuidTree.hide();
}



//清空
function chooseMainInterviewerGuidTreeClear(name){
	$('.gt-editor-text').val(null);
	mygrid.activeRecord.userguid=null;
	loadMainInterviewerData(1);
}

//取消
function chooseMainInterviewerGuidTreeCancel(name){
	$('.gt-editor-text').val(null);
	mygrid.activeRecord.userguid=null;
	mainInterviewerGuidTree.hide();
}


//面试地点选项树
var addressGuidTree=null;
function chooseAddressGridTree(name){
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!addressGuidTree){
		//树的层
		addressGuidTree=$('<div id="addressGuidTree" class="chooseTree"><div class="panel"><ul id="addressGuidTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
		
		//Tree构造
		$.getJSON("auditionaddress/buildAllAuditionAddress.do", function(data) {
			var setting = {callback: {beforeClick: function(treeId, treeNode){
				if (treeNode.id!=null) {
					$('.gt-editor-text').val(treeNode.name);
					mygrid.activeRecord.auditionaddress=treeNode.name;
					
					
					//面试地点置值
					mygrid.forEachRow(function(row,record,i,grid){
						if(record.auditionaddress==null&&record.auditionaddressguid==null){
							record.auditionaddress=treeNode.name;
						}
					});
					mygrid.refresh();
					
				}else{
					$('.gt-editor-text').val(null);
					mygrid.activeRecord.auditionaddress=null;
				}
				//关闭树
				mygrid.endEdit();
				addressGuidTree.hide();
			}}};
			$.fn.zTree.init($("#addressGuidTree_ul"),setting, data);
	    });
		
		//邦定关闭事件
		$("body").bind("mousedown",function(event){
			if (!(event.target.id == "addressGuidTree" || $(event.target).parents("#addressGuidTree").length>0)||event.target.className=='gt-editor-text') {
				addressGuidTree.hide();
			}
		}); 
	}
	
	$(".gt-editor-text").bind("click",function(event){
		addressGuidTree.show();
	});
	
	//树的位置
	addressGuidTree.show();
	setTreePosation(addressGuidTree,cityObj,cityOffset);
}














//==============================================================================
//推荐到
var userTree=null;
function chooseUserGuidTree(name){
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!userTree){
		//树的层
		var htm='<div id="userTree" class="chooseTree">';
		htm+='<div class="panel">';
		htm+='<ul id="userTree_ul" class="ztree"></ul>';
		htm+='</div>';
		htm+='<div id="myUserTree" class="pagelabel">';
		
		htm+='</div>';
		htm+='</div>';
		userTree=$(htm).appendTo(document.body);
		
		//邦定关闭事件
		$("body").bind("mousedown",function(event){
			if (!(event.target.id == "userTree" || $(event.target).parents("#userTree").length>0)||event.target.className=='gt-editor-text') {
				userTree.hide();
			}
		});
	}
	
	//点击事件树展开
	$(".gt-editor-text").bind("click",function(event){
		userTree.show();
	});
	
	
	//加载数据
	loadGridUserTreeData(1);
	
	//树的位置
	userTree.show();
	
	//计算位置
	userTree.css({"left":cityOffset.left + "px"});
	if(cityOffset.top+240<=$(document.body).outerHeight()){
		userTree.css({"top":cityOffset.top + cityObj.outerHeight()-$(document).scrollTop()+"px"});
	}else
		userTree.css({"top":cityOffset.top -230-$(document).scrollTop()+"px"});
}


//加载数据
function loadGridUserTreeData(pageno){
	//使树显示
	if(userTree!=null)
		userTree.show();
	
	var grid={page:{pagesize:9,pageno:pageno},parameters:{name:$('.gt-editor-text').val()}};
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
					$('.gt-editor-text').val(treeNode.ename);
					mygrid.activeRecord.userguid=treeNode.id;
					mygrid.activeRecord.recommendcompanyid=treeNode.pId;
					mygrid.activeRecord.recommenddeptid=treeNode.marker;
					mygrid.activeRecord.recommendcompanyname=treeNode.companyname;
					mygrid.activeRecord.recommenddeptname=treeNode.deptname;
				}else{
					$('.gt-editor-text').val(null);
					mygrid.activeRecord.userguid=null;
					mygrid.activeRecord.recommendcompanyid=null;
					mygrid.activeRecord.recommenddeptid=null;
					mygrid.activeRecord.recommendcompanyname=null;
					mygrid.activeRecord.recommenddeptname=null;
				}
				
				//mygrid.refresh();
				
				//关闭树
				mygrid.endEdit();
				userTree.hide();
			}}};
      	$.fn.zTree.init($("#userTree_ul"),setting, data);
			
			//分页
			var reload_P="loadGridUserTreeData("+page.prePageNo+");";
			var reload_N="loadGridUserTreeData("+page.nextPageNo+");";
			renderPageContaint("#myUserTree",data,page,reload_P,reload_N);
      }
  });
}






//选择岗位
var postGuidTree=null;
function choosePostGridTree(name){
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!postGuidTree){
		//树的层
		postGuidTree=$('<div id="postGuidTree" class="chooseTree"><div class="panel"><ul id="postGuidTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
		
		//邦定关闭事件
		$("body").bind("mousedown",function(event){
			if (!(event.target.id == "postGuidTree" || $(event.target).parents("#postGuidTree").length>0)||event.target.className=='gt-editor-text') {
				postGuidTree.hide();
			}
		}); 
	}
	
	$(".gt-editor-text").bind("click",function(event){
		postGuidTree.show();
	});
	
	var deptid=mygrid.activeRecord.recommenddeptid;
	
	//Tree构造
	$.getJSON("department/buildMyPostTreeByDept.do",{deptid:deptid}, function(data) {
		var setting = {callback: {beforeClick: function(treeId, treeNode){
			
			if (treeNode.id!=null) {
				$('.gt-editor-text').val(treeNode.name);
				mygrid.activeRecord.recommendpostguid=treeNode.id;
				
			}else{
				$('.gt-editor-text').val(null);
				mygrid.activeRecord.recommendpostguid=null;
			}
			//关闭树
			mygrid.endEdit();
			postGuidTree.hide();
		}}};
		$.fn.zTree.init($("#postGuidTree_ul"),setting, data);
    });
	
	
	
	//树的位置
	postGuidTree.show();
	setTreePosation(postGuidTree,cityObj,cityOffset);
}


//推荐岗位 
var postTree = null;
function choosePostGuidTree(name){
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!postTree){
		//树的层
		var htm='<div id="postTree" class="chooseTree">';
		htm+='<div class="panel">';
		htm+='<ul id="postTree_ul" class="ztree"></ul>';
		htm+='</div>';
		htm+='<div id="myPostTree" class="pagelabel">';
		
		htm+='</div>';
		htm+='</div>';
		postTree=$(htm).appendTo(document.body);
		
		//邦定关闭事件
		$("body").bind("mousedown",function(event){
			if (!(event.target.id == "postTree" || $(event.target).parents("#postTree").length>0)||event.target.className=='gt-editor-text') {
				postTree.hide();
			}
		});
	}
	
	//点击事件树展开
	$(".gt-editor-text").bind("click",function(event){
		postTree.show();
	});
	
	
	//加载数据
	loadGridPostTreeData(1);
	
	//树的位置
	postTree.show();
	
	//计算位置
	postTree.css({"left":cityOffset.left + "px"});
	if(cityOffset.top+240<=$(document.body).outerHeight()){
		postTree.css({"top":cityOffset.top + cityObj.outerHeight()-$(document).scrollTop()+"px"});
	}else
		postTree.css({"top":cityOffset.top -230-$(document).scrollTop()+"px"});
}


//岗位树
function loadGridPostTreeData(pageno){
	//使树显示
	if(postTree!=null)
		postTree.show();
	
	var grid={page:{pagesize:9,pageno:pageno},parameters:{name:$('.gt-editor-text').val()}};
	$.ajax({  
      url: "employee/searchPostTree.do",  
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
					$('.gt-editor-text').val(treeNode.name);
					mygrid.activeRecord.recommendpostguid=treeNode.id;
					mygrid.activeRecord.name=treeNode.name;
				}else{
					$('.gt-editor-text').val(null);
					mygrid.activeRecord.recommendpostguid=null;
				}
				
				//mygrid.refresh();
				
				//关闭树
				mygrid.endEdit();
				postTree.hide();
			}}};
      	$.fn.zTree.init($("#postTree_ul"),setting, data);
			
			//分页
			var reload_P="loadGridPostTreeData("+page.prePageNo+");";
			var reload_N="loadGridPostTreeData("+page.nextPageNo+");";
			renderPageContaint("#myPostTree",data,page,reload_P,reload_N);
      }
  });
}





//面试结果选项树
var resultGridTree=null;
function chooseResultGridTree(name){
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!resultGridTree){
		//树的层
		resultGridTree=$('<div id="resultGridTree" class="chooseTree"><div class="panel"><ul id="resultGridTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
		
		//Tree构造
		$.getJSON("system/buildOptionListTree.do", {code:'RESULTTYPE'}, function(data) {
			var setting = {callback: {beforeClick: function(treeId, treeNode){
				if (treeNode.id!=null) {
					$('.gt-editor-text').val(treeNode.name);
					mygrid.activeRecord.resulttype=treeNode.id;
				}else{
					$('.gt-editor-text').val(null);
					mygrid.activeRecord.resulttype=null;
				}
				//关闭树
				mygrid.endEdit();
				resultGridTree.hide();
			}}};
			$.fn.zTree.init($("#resultGridTree_ul"),setting, data);
	    });
		
		//邦定关闭事件
		$("body").bind("mousedown",function(event){
			if (!(event.target.id == "resultGridTree" || $(event.target).parents("#resultGridTree").length>0)||event.target.className=='gt-editor-text') {
				resultGridTree.hide();
			}
		}); 
	}
	
	$(".gt-editor-text").bind("click",function(event){
		resultGridTree.show();
	});
	
	//使树显示
	if(resultGridTree!=null)
		resultGridTree.show();
	
	
	//树的位置
	resultGridTree.show();
	setTreePosation(resultGridTree,cityObj,cityOffset);
}


//面试评语树
var contentGridTree=null;
function chooseContentGridTree(name){
	var cityObj = $(name);
	var cityOffset = $(name).offset();
	
	if (!contentGridTree){
		//树的层
		contentGridTree=$('<div id="contentGridTree" class="chooseTree"><div class="panel"><ul id="contentGridTree_ul" class="ztree"></ul></div></div>').appendTo(document.body);
		
		//Tree构造
		$.getJSON("system/buildOptionListTree.do", {code:'RESULTCONTENT'}, function(data) {
			var setting = {callback: {beforeClick: function(treeId, treeNode){
				if (treeNode.id!=null) {
					$('.gt-editor-text').val(treeNode.name);
					mygrid.activeRecord.resultcontent=treeNode.id;
				}else{
					$('.gt-editor-text').val(null);
					mygrid.activeRecord.resultcontent=null;
				}
				//关闭树
				mygrid.endEdit();
				contentGridTree.hide();
			}}};
			$.fn.zTree.init($("#contentGridTree_ul"),setting, data);
	    });
		
		//邦定关闭事件
		$("body").bind("mousedown",function(event){
			if (!(event.target.id == "contentGridTree" || $(event.target).parents("#contentGridTree").length>0)||event.target.className=='gt-editor-text') {
				contentGridTree.hide();
			}
		}); 
		
	}
	
	$(".gt-editor-text").bind("click",function(event){
		contentGridTree.show();
	});
	
	//使树显示
	if(contentGridTree!=null)
		contentGridTree.show();
	
	//树的位置
	contentGridTree.show();
	setTreePosation(contentGridTree,cityObj,cityOffset);
}
