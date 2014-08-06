<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>华数帐务管理系统V2.0--用户登陆</title>
<base href="${baseUrl }"/>
<link rel="stylesheet" type="text/css"  href="pages/login/login.css" />
<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/jquery.cookie.js"></script>
<script type="text/javascript">
var cookieName='mycookie';
var cookiePWD='mycookiePwd';
$(document).ready(function(){
   	var val = $.cookie(cookieName);
	if (val)$("#userName").attr('value',val);

	var pwd=$.cookie(cookiePWD);
	if(pwd!=null){
		$("#password").attr('value',pwd);
		$('#lock').attr('checked',true);
	}

	if('${param.error}'=='true')
		$('#ErrorMsg').css({'display':''});


	//检查ＩＥ版本
	if($.browser.msie&&$.browser.version==6){
		$('#ieVersion').css('display','');
	}
	
	//换背景图
	var bg='url(./pages/login/img/4.gif)';
	$('#lg_bg').css('background-image',bg);

});
function checkUserName(){
	var userName=$('#userName').attr('value');
	var pwd=$('#password').attr('value');
	if(userName==null||userName==''||pwd==null||pwd=='') {
		$('#ErrorMsg').css('display','');
		$('#ErrorMsg').html("用户名或密码不能为空!");
		return false;
	}else{
		$.cookie(cookieName, userName, {expires: 100});
		if($('#lock').attr('checked'))
			$.cookie(cookiePWD, pwd, {expires: 100});
		else
			$.cookie(cookiePWD,null);
		return true;
	}
}
function formSubmit(e){
	if(e.keyCode==13&&checkUserName()){
		$("form:first").submit();
	}
}
function Submit(e){
	if(checkUserName())
		$("form:first").submit();
	
}
</script>
</head>
<body>
<div id="ieVersion" style="width:100%;height:30px;line-height:30px;text-align:center;color:red;background:#eee;display:none;">
	您的浏览器版本过低，为了您能更好的体验本系统，请您把IE版本更新至IE8！！如需帮助请联系管理员．
</div>
<div class="wp header clearfix">
	<img src="./pages/login/img/logo.gif"></img>
</div>

<form name="loginForm" name="loginForm" action="j_spring_security_check" method="post" onkeypress="formSubmit(event);">
<div id="lg_bg" class="login-frame clearfix">
<div class="login">
	<ul id="login-nav" class="login-nav login-nav-user">
		<li class="user"><a href="#">用户登陆</a></li>
	</ul>
	<div class="form-list">
	<ul>
		<li class="tips-li">
			<div class="tips-error" id="ErrorMsg" style="display:none;">用户名或密码错误，请重新登录</div>
		</li>
		<li>
			<label class="tit" for="txtName">用户名</label>
			<div class="form-right">
				<input name="j_username" id="userName" tabindex="1" class="input-1" maxlength="20" type="text"/>
			</div>
		</li>
		<li>
			<label class="tit" for="txtPass">密码</label>
			<div class="form-right">
				<span id="securityContainer"></span>
				<input name="j_password" id="password" tabindex="2" class="input-1"	maxlength="20" type="password" />
			</div>
		</li>
		<li>
			<label for="lock" class="tit"><input id="lock" type="checkbox" style="vertical-align: middle" title="为了您的信息安全，请不要在网吧或公用电脑上使用。 "/>记住密码</label>
		</li>
		<li class="btn-li clearfix">
			<input name="BtnLogin" id="BtnLogin" class="login-submit" value="登&nbsp;&nbsp;录" type="button" onclick="Submit(event);return false;"/>
		</li>
	</ul>
	</div>
</div>
</div>
</form>
<div class="footv4">
<div class="ftul">
<a href="#">分辨率:1024*768以上</a>|
<a href="#">浏览器IE8及以上版本</a>|
<a href="#">flash10.0及以上版本</a>|
<a href="swf/数据模板.xls" style="color:#fd6c01">业务数据模板下载</a>|
<a href="swf/券卡数据模板.xls" style="color:#fd6c01">券卡数据模板下载</a>|
<a href="#">系统帮助</a>
</div>
</div>
</body>
</html>
