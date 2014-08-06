<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<form action="myperson/saveOrUpdateTmpRecommend.do" method="post" id="myTmpRecommend_${ordernum}" >
	<input id="recommendguid_${ordernum }" name="recommendguid" type="hidden" value=""/>
	<input id="mycandidatesguid_${ordernum }" name="mycandidatesguid" type="hidden" value="${mycandidatesguid }"/>
			
	<div id="" class="formDiv">
		<ul>
			<li>
				<span><em class="red">* </em>姓名：</span>
				<input  id="name_${ordernum }" name="name"  class="{required:true,maxlength:20} inputstyle"/>
			</li>
			<li>
			    <span><em class="red">* </em>性别：</span>
			    <input id="sex_${ordernum}" name="sex" type="hidden" />
			    <input id="sexname_${ordernum}" name="sexname"  class="{required:true} inputselectstyle" onclick="chooseOptionTree('#sex_${ordernum}','#sexname_${ordernum}','SEX');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#sex_${ordernum}','#sexname_${ordernum}','SEX');"/>
			</li>
		</ul>
		<ul>
		<li>
				<span><em class="red">* </em>推荐岗位：</span>
				<input  id="recommendpostname_${ordernum}" name="recommendpostname"  class="{required:true,maxlength:10} inputstyle"/>
			</li>
			<li>
	             <span><em class="red">* </em>工作年限：</span>
	             <input id="workage_${ordernum}" name="workage" type="hidden"/>
				<input id="workagename_${ordernum}"  name="workagename" class="{required:true} inputselectstyle"  onclick="chooseOptionTree('#workage_${ordernum}','#workagename_${ordernum}','WORKAGE');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#workage_${ordernum}','#workagename_${ordernum}','WORKAGE');"/>
	         </li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>联系电话：</span>
				<input  id="mobile_${ordernum}" name="mobile"   class="{required:true,number:true,maxlength:50} inputstyle"/>
			</li>
			<li>
				<span>电子邮箱：</span>
				<input id="email_${ordernum}" name="email" class="{maxlength:50,email:true} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>目前状况：</span>
				<input id="situation_${ordernum}" name="situation"  class="{maxlength:50} inputstyle"/>
			</li>
		</ul>
		
		<div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn btn-red" href="javascript:editTmpRecommend('#myTmpRecommend_${ordernum}');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveTmpRecommend('#myTmpRecommend_${ordernum}',${ordernum});"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delTmpRecommend('#myTmpRecommend_${ordernum}',${ordernum});"><i class="icon icon-remove"></i><span>删除</span></a>
				</div>
			</div>
		</div>
		
	</div>
</form>


