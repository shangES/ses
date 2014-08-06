<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<c:forEach items="${tmpFamilylist }" var="item" varStatus="idx">
<form action="myperson/saveOrUpdateTmpFamily.do" method="post" id="myTmpFamily_${idx.index}" >
	<input id="familyid_${idx.index}" name="familyid" type="hidden" value="${item.familyid }"/>
	<input id="mycandidatesguid_${idx.index}" name="mycandidatesguid" type="hidden" value="${item.mycandidatesguid }"/>	
		
	<div id="" class="formDiv">
		<ul>
			<li>
				<span><em class="red">* </em>姓名：</span>
				<input  id="name_${idx.index}" name="name"  value="${item.name }" class="{required:true,maxlength:20} inputstyle"/>
			</li>
			<li>
			    <span><em class="red">* </em>家属关系：</span>
			    <input id="contactrelationship_${idx.index}" name="contactrelationship" type="hidden" value="${item.contactrelationship }"/>
			    <input id="contactrelationshipname_${idx.index}" name="contactrelationshipname" value="${item.contactrelationshipname }"  class="{required:true} inputselectstyle" onclick="chooseOptionTree('#contactrelationship_${idx.index}','#contactrelationshipname_${idx.index}','RELATIONSHIP');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#contactrelationship_${idx.index}','#contactrelationshipname_${idx.index}','RELATIONSHIP');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>单位：</span>
				<input  id="workunit_${idx.index}" name="workunit" value="${item.workunit }" class="{required:true,maxlength:30} inputstyle"/>
			</li>
			<li>
				<span>担任职务：</span>
				<input id="job_${idx.index}" name="job" value="${item.job }" class="{maxlength:50} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>联系电话：</span>
				<input  id="mobile_${idx.index}" name="mobile"  value="${item.mobile }" class="{required:true,number:true,maxlength:50} inputstyle"/>
			</li>
		</ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn btn-red" href="javascript:editTmpFamily('#myTmpFamily_${idx.index}');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveTmpFamily('#myTmpFamily_${idx.index}',${idx.index});"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delTmpFamily('#myTmpFamily_${idx.index}',${idx.index});"><i class="icon icon-remove"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
familyNum++;

$("#myTmpFamily_${idx.index}").validate({debug:true,submitHandler: function(form) {
		$(form).ajaxSubmit(function(data) {
			alert("操作成功!");
			//formDisabled(true,"#myTmpFamily_"+familyForm);
		});
	}
});

//不可修改
//formDisabled(true,"#myTmpFamily_${idx.index}");

//日期选择
initDatePicker();
</script>

</c:forEach>

