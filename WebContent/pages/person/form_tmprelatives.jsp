<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<form action="myperson/saveOrUpdateTmpRelatives.do" method="post" id="myTmpRelatives_${ordernum}" >
	<input id="relativesguid_${ordernum}" name="relativesguid" type="hidden" />
	<input id="mycandidatesguid_${ordernum}" name="mycandidatesguid" type="hidden" value="${mycandidatesguid }"/>	
		
	<div id="" class="formDiv">
		<ul>
			<li>
				<span><em class="red">* </em>亲属姓名：</span>
				<input  id="employeename_${ordernum}" name="employeename" class="{required:true,maxlength:50} inputstyle"/>
			</li>
			<li>
				<span><em class="red">* </em>公司：</span>
				<input  id="companyname_${ordernum}" name="companyname" class="{required:true,maxlength:50} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>部门：</span>
				<input id="deptname_${ordernum}" name="deptname"  class="{required:true,maxlength:50} inputstyle"/>
			</li>
			<li>
				<span><em class="red">* </em>岗位：</span>
				<input id="postname_${ordernum}" name="postname"  class="{required:true,maxlength:50} inputstyle"/>
			</li>
		</ul>
		
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn btn-red" href="javascript:editTmpRelatives('#myTmpRelatives_${ordernum}');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveTmpRelatives('#myTmpRelatives_${ordernum}',${ordernum});"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delTmpRelatives('#myTmpRelatives_${ordernum}',${ordernum});"><i class="icon icon-remove"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>



