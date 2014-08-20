

<!DOCTYPE html>
<head>
<title>工作流页面</title>
<base href="http://localhost:8081/hrmweb/"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="skins/css/frame.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/style.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/form.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeIcons.css"/>


<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="pages/mainframe/sdmenu.js"></script>
<script type="text/javascript" src="pages/mainframe/menu.js"></script>
<script type="text/javascript" src="pages/tree.js"></script>


<script type="text/javascript">

$(document).ready(function () {
	minWidth();
	//加载菜单
   loadMyFunction();
	
	
   Array.prototype.strip = function () {            
	   if (this.length < 2) return [this[0]] || [];            
	   var arr = [];            
	   for (var i = 0; i < this.length; i++) {                
		   arr.push(this.splice(i--, 1));                
		   for (var j = 0; j < this.length; j++) {                    
			   if (this[j] == arr[arr.length - 1]) {                        
				   this.splice(j--, 1);                    
			   }                
		   }            
	   }            
	   return arr;        
   }
	
   
	//欢迎页面
   var chg=$("body").outerHeight()-220;
	if(chg<=500){
		$("#ContentFrame").height("500");
	}else
  		$("#ContentFrame").height(chg);
   $("#ContentFrame").attr("src","index.do?page=welcome");
    
});




//设置页面的最小宽度
function minWidth(){
	var pageWidth = document.documentElement.clientWidth || document.body.clientWidth;
	var minWidth = 1000;
	(pageWidth < 1000) ? $("body").width("1000px") : $("body").width("100%");
}

$(window).resize(function(){
	setTimeout(minWidth,10);
})





//滚动条回到顶部
function goBodyTop(){
	$("body,html").scrollTop(0);
}

//滚动条回到中间
function goCenter(){
	var height=$("body").outerHeight();
	$("body,html").scrollTop(height/2-240);
}

function hiddenInfo(event,obj){
	var div = document.getElementById(obj.id);
	var x=event.clientX;
	var y=event.clientY;
	var divx1 = div.offsetLeft;
	var divy1 = div.offsetTop;
	var divx2 = div.offsetLeft + div.offsetWidth;
	var divy2 = div.offsetTop + div.offsetHeight;
	
	
	if( (x < divx1 || x > divx2 || y < divy1 || y > divy2)&&x!=-1&&y!=-1){
		$("#msgPanel").hide();
		$("#notePanel").hide();
	}
}

function showInfo(){
	$("#msgtable").css({display:"block"});
}

function hiddenInfo(){
	$("#msgtable").css({display:"none"});
}
</script>




</head>

<body style="overflow:auto;" id="HOHO">
<!--Wrapper-->
<div id="Wrapper">
	<!--Left-->
	<div id="Sider">
		<div id="Menu">
			<div class="fixt"></div>
			<div class="sdmenu">
				<span id="menus" >
				
				</span>
			
			</div>
			<div class="fixb"></div>
		</div>
	</div>
	
	<!--Content-->
	<div id="Content">
		<iframe id="ContentFrame" name="ContentFrame" src="" scrolling="no" frameborder="0" width="100%" frameborder="0" height="100px"></iframe>
		<br>
		<br>
		<br>
	</div>
	
</div>


<!--Footer
<div id="Footer" class="bottom">
	<div>
	  <h1>华数人力资源管理系统&nbsp;</h1>
	  <h3>@2013-2014华数版权所有&nbsp;&nbsp;</h3>
	  <h3 style="float:right;">技术支持：浙江美科科技公司</h3>
	</div>
</div>
-->














<!-- 消息框提示 -->
<style>
#msg{
height: 31px;
position:fixed; 
right:10px; 
bottom:0px; 
cursor:pointer;
_position:absolute;
_bottom:auto;
_top:expression(eval(document.documentElement.scrollTop+document.documentElement.clientHeight-this.offsetHeight-(parseInt(this.currentStyle.marginTop,10)||0)-(parseInt(this.currentStyle.marginBottom,10)||0)));
_margin-bottom:0px;
WIDTH:350PX;
}

#msg .msgcontent{

background:#D6D6D6 url("skins/images/alert.png") no-repeat;
}


#J_BrandBar {
    background-color: #EEEEEE;
    border: 1px solid #D0D0D0;
    border-radius: 3px 3px 0 0;
    cursor: pointer;
    font-size: 12px;
    height: 28px;
    margin: 2px 5px 0;
    width: 95px;
}

.BrandFlyer {
    background: url("skins/images/SmsSend.png") no-repeat scroll 0 0 transparent;
    float: left;
    height: 20px;
    margin: 4px 0 4px 12px;
    width: 20px;
}


.tm_cmbar a{
    color: #181818;
    display: block;
    float: left;
    line-height: 20px;
    margin: 4px 4px 4px 0;
    text-decoration: none;
}

.tm_cmbar_clearfix:after, .tm_cmbar_clearfix:before {
    content: "";
    display: table;
    overflow: hidden;
}
.tm_cmbar_clearfix:after, .tm_cmbar_clearfix:before {
    content: "";
    display: table;
    overflow: hidden;
}
.tm_cmbar_clearfix:after {
    clear: both;
}


</style>







<!-- <div id="msg" onMouseOver="showInfo();" onMouseOut="hiddenInfo()"> -->
<!-- <table width="100%" style="display: none;" id="msgtable"> -->

<!-- <div id="msgPanel" style="background:#D6D6D6;position:fixed ;z-index: 888;width:240px;display:none;" onmouseout="hiddenInfo(event,this)"> -->
	<div id="msgPanel" style="background:#D6D6D6;position:fixed ;z-index: 888;width:240px;display:none;">
	<div style="padding:5px 20px;">
		<table width="100%" border="0" class="static-table-noline">
			<tr>
			
			   
			   
				 <td>
					<a href="recruitprogram.do?page=list" target="ContentFrame">招聘计划审批</a>
				 </td>
				 <td align="right" id="recruitprograms">
					0
				 </td>
			   
				
			</tr>
			<tr style="display:none">
				<td>
					<a href="mycandidates.do?page=list_dept" target="ContentFrame">待认定的简历</a>
				</td>
				<td align="right" id="affirms">
					0
				</td>
			</tr>
			<tr>
			
			   
			   
				  <td>
					<a href="mycandidates.do?page=list" target="ContentFrame">待安排的面试</a>
				  </td>
				  <td align="right" id="interviews">
					6
				  </td>
			   
			
			</tr>
			<tr style="display:none">
				<td>
					<a href="mycandidates.do?page=list_dept" target="ContentFrame">面试结果待反馈</a>
				</td>
				<td align="right" id="affirmauditionresults">
					0
				</td>
			</tr>
			<tr>
			
			   
			   
			      <td>
					<a href="mycandidates.do?page=list" target="ContentFrame">面试结果待反馈</a>
				  </td>
				  <td align="right" id="releases">
					2
				  </td>
			   
			
			</tr>
			<tr>
			
			   
			   
			      <td>
					<a href="mycandidates.do?page=list" target="ContentFrame">待安排的体检</a>
				  </td>
				  <td align="right" id="examinations">
					2
				  </td>
			   
			
			</tr>
			
			<tr>
			
			   
			   
			      <td>
					<a href="mycandidates.do?page=list" target="ContentFrame">待入职的应聘者</a>
				  </td>
				  <td align="right" id="entryoktodos">
					0
				  </td>
			   
			
			</tr>
			
			<tr>
			
			   
			      <td>
					<a href="recruitprogram.do?page=list" target="ContentFrame" style="color:#CC0000;">OA信息</a>
				  </td>
				  <td align="right" id="releases" style="color:#CC0000;">
					250
				  </td>
			   
			   
			
			</tr>
		</table>
	</div>
</div>
	
<script type="text/javascript">




//系统提示消息
function showMsg(){
	posationMsg();
	
	var panel1=$("#notePanel");
	var flag=panel1.css("display");
	
	if(flag=='block')
		panel1.toggle();
	
	
	var panel=$("#msgPanel");
	panel.toggle();
}



//计算位置
function posationMsg(){
	var cityObj = $(".msgcontent");
	var cityOffset = cityObj.offset();
	
	var panel=$("#msgPanel");
	panel.css({"left":cityOffset.left+3 + "px"});
	panel.css({"top":cityOffset.top-panel.outerHeight()-$(document).scrollTop() +"px"});
}




//获取信息
function getMsg(){
	var pam={parameters:{}};
	$.ajax({  
		url:"todo/getMsgData.do",
		contentType: "application/json; charset=utf-8",  
		type: "POST",  
		dataType: "json",  
		data: JSON.stringify(pam),
		beforeSend : null,
		success: function(data) { 
			for(var key in data){
				$("#"+key).text(data[key]);
			}
		}
	});
}

//定时刷新
var interval = setInterval(getMsg, 30000);

</script>
</body>
</html>