<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<form action="myperson/saveOrUpdateTmpFamily.do" method="post" id="myTmpFamily_${ordernum}" >
	<input id="familyid_${ordernum }" name="familyid" type="hidden" value=""/>
	<input id="mycandidatesguid_${ordernum }" name="mycandidatesguid" type="hidden" value="${mycandidatesguid }"/>
			
	<div id="" class="formDiv">
		<ul>
			<li>
				<span><em class="red">* </em>姓名：</span>
				<input  id="name_${ordernum }" name="name"  class="{required:true,maxlength:20} inputstyle"/>
			</li>
			<li>
			    <span><em class="red">* </em>家属关系：</span>
			    <input id="contactrelationship_${ordernum }" name="contactrelationship" type="hidden" value=""/>
			    <input id="contactrelationshipname_${ordernum }" name="contactrelationshipname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#contactrelationship_${ordernum }','#contactrelationshipname_${ordernum }','RELATIONSHIP');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#contactrelationship_${ordernum }','#contactrelationshipname_${ordernum }','RELATIONSHIP');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>单位：</span>
				<input  id="workunit_${ordernum }" name="workunit"  class="{required:true,maxlength:30} inputstyle"/>
			</li>
			<li>
				<span>担任职务：</span>
				<input id="job_${ordernum }" name="job" class="{maxlength:50} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>联系电话：</span>
				<input  id="mobile_${ordernum }" name="mobile"  class="{required:true,number:true,maxlength:50} inputstyle"/>
			</li>
		</ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn btn-red" href="javascript:editTmpFamily('#myTmpFamily_${ordernum }');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveTmpFamily('#myTmpFamily_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delTmpFamily('#myTmpFamily_${ordernum }',${ordernum });"><i class="icon icon-remove"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>


