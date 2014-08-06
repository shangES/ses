<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>简历管理</title>
<base href="${baseUrl }"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="skins/css/style.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/form.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css"/>

<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>


<script type="text/javascript">
var pageState=false;
var ids='${param.ids}';
var names='${param.names}';
var mycandidatesguids='${param.mycandidatesguids}';
var now_candidatesguid;
var now_id;


$(document).ready(function() {
	 
// 只有一条数据下一页隐藏
	 if(ids!=null&&ids!=""&&ids!="null"){	 
		 var array = ids.split(",");
		 if(array.length != 1){
			 $("#gotoNext").show();
		 }
	 }

//加载第一个
	if(ids!=null&&ids!=""&&ids!="null"){
		var obj2 = ids.split(",");
		var obj3 = mycandidatesguids.split(",");
		var id = obj2[0];
		var mycandidatesguid=obj3[0];
	    now_id = obj2[0];
		now_candidatesguid=obj3[0];
		
		$("#detail").attr("src","resume/getResumeStaticById.do?id="+id+"&mycandidatesguid="+mycandidatesguid);
	}
	

	//加载
	var htm='';
	if (names!=null&&names!=""&&names!="null") {
		var obj = names.split(",");
		for (var i=0;i<obj.length;i++) {
			var name=obj[i];
			htm+='<li class="ui-state-default ui-corner-top"><a href="#tab0">';
			htm+=name;
			htm+='</a></li>';
		}
		$("#tabul").append(htm);
	}
	
	
	
	
	
	//加载
	$("#mytab").tabs({
    	select: function(event, ui) {
    		tabIndex=ui.index;
    		//刷新
    		if(ids!=null&&ids!=""&&ids!="null"){
    			var obj2 = ids.split(",");
    			var obj3 = mycandidatesguids.split(",");
    			for(var j=0;j<obj2.length;j++){
    				var id = obj2[j];
    				var mycandidatesguid=obj3[j];
    				if(tabIndex==j){
    					now_id = id;
        				now_candidatesguid=mycandidatesguid;
    					$("#detail").attr("src","resume/getResumeStaticById.do?id="+id+'&mycandidatesguid='+mycandidatesguid);
    				}
    			}
    		}
    	}
    });
	




	
});


//下一个
function gotoNext(){
	if(ids!=null&&ids!=""&&ids!="null"){
		var obj2 = ids.split(",");
		var obj3 = mycandidatesguids.split(",");
		for(var j=0;j<obj2.length;j++){
		if(obj2[j]==now_id&&obj3[j]==now_candidatesguid&&obj2[j+1]!=null&&obj2[j+1]!=""){
			var next_id=obj2[j+1];
			var next_mycandidatesguid=obj3[j+1];
			$("#mytab").tabs( "select" , j+1);
			$("#detail").attr("src","resume/getResumeStaticById.do?id="+next_id+'&mycandidatesguid='+next_mycandidatesguid);
			now_id = obj2[j+1];
			now_candidatesguid=obj3[j+1];
			return;
		}else if(j==obj2.length-1){
		     alert("最后一个");
		}
	}
}
}



//切换视图
function convertView(url){
	window.parent.convertView(url);
}





//页面保存状态
function callbackPageState(state){
	pageState=state;
}



</script>


</head>
<body>

	
<div class="sort-cont sort-table">
	<div id="mytab">
		<ul id="tabul">
			
		</ul>
	
		<div id="tab0">
			<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" scrolling="no" src="" ></iframe>
		    <p id='gotoNext' style=" float: right; display: none;"><a class="btn" href="javascript:gotoNext();"><i class="icon icon-chevron-right"></i><span>下一页</span></a>&nbsp;&nbsp;&nbsp;</p>
		</div>
	</div>
</div>


</body>
</html>