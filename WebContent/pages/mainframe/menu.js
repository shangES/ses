//加载菜单
var menus=null;
function loadMyFunction(){
	$.getJSON("system/getUserFunctions.do", function(data) {
		menus=data;
		renderFunction1();
	});
}

//一级菜单
function renderFunction1(){
	var htm='';
	for(var i=0;i<menus.length;i++){
		var obj=menus[i];
		if(obj.funtype==0&&obj.funpid==null){
			htm+='<div class="collapsed">';
			
			//子节点
			if(hasSecondMenu(obj.funid)){
				htm+='<span>';
				htm+='<i class="icon icon-leaf"></i>';
				htm+=obj.labelname;
				htm+='</span>';
			}else{
				htm+='<a id="'+obj.funid+'" href="javascript:onclickMyMenu(\''+obj.funid+'\',\''+obj.javaevent+'\');" style="padding-left:25px;">';
				htm+='<i class="icon icon-th-large"></i>';
				htm+=obj.labelname;
				htm+='</a>';
			}
			
			htm+=renderFunction2(obj.funid);
			htm+='</div>';
		}
	}
	$("#menus").html(htm);
	
	//菜单加上事件
	if(myMenu==null)
		myMenu= new SDMenu("menus");
	myMenu.init();
	myMenu.oneSmOnly = true;
	myMenu.remember=false;
	//myMenu.collapseAll();  
}



//二级菜单
var myMenu=null;
function renderFunction2(pid){
	var htm='<ul>';
	for(var i=0;i<menus.length;i++){
		var obj=menus[i];
		if(obj.funtype==0&&obj.funpid==pid){
			htm+='<a id="'+obj.funid+'" href="javascript:onclickMyMenu(\''+obj.funid+'\',\''+obj.javaevent+'\');" class="childer" title="'+obj.labelname+'">';
			htm+='<i class="icon icon-list-alt"></i>';
			var fname=obj.labelname;
			if(fname!=null&&fname.length>7){
				var substr=fname.substring(0,7);
				htm+=substr+"...";
			}else
				htm+=obj.labelname;
			htm+='</a>';
		}
	}
	htm+='</ul>';
	return htm;
}







//二级菜单事件
function onclickMyMenu(id,url){
	$(".current").removeClass("current");
	$("#"+id).addClass("current");
	
	
	//事件
	if(url!=null&&url!=''&&url!='null'){
		showLoading();
		$("#ContentFrame").attr("src",url);
	}
}





//检查二级菜单存在
function hasSecondMenu(pid){
	var state=false;
	for(var i=0;i<menus.length;i++){
		var obj=menus[i];
		if(obj.funtype==0&&obj.funpid==pid){
			state=true;
			break;
		}
	}
	return state;
}

