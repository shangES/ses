<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<c:forEach items="${tmpRelativeslist }" var="item" varStatus="idx">
<form action="myperson/saveOrUpdateTmpRelatives.do" method="post" id="myTmpRelatives_${idx.index}" >
	<input id="relativesguid_${idx.index}" name="relativesguid" type="hidden" value="${item.relativesguid }"/>
	<input id="mycandidatesguid_${idx.index}" name="mycandidatesguid" type="hidden" value="${item.mycandidatesguid }"/>	
		
	<div id="" class="formDiv">
		<ul>
			<li>
				<span><em class="red">* </em>亲属姓名：</span>
				<input  id="employeename_${idx.index}" name="employeename" value="${item.employeename }" class="{required:true,maxlength:50} inputstyle"/>
			</li>
			<li>
				<span><em class="red">* </em>公司：</span>
				<input  id="companyname_${idx.index}" name="companyname" value="${item.companyname }" class="{required:true,maxlength:50} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>部门：</span>
				<input id="deptname_${idx.index}" name="deptname" value="${item.deptname }" class="{required:true,maxlength:50} inputstyle"/>
			</li>
			<li>
				<span><em class="red">* </em>岗位：</span>
				<input id="postname_${idx.index}" name="postname" value="${item.postname }" class="{required:true,maxlength:50} inputstyle"/>
			</li>
		</ul>
		
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn btn-red" href="javascript:editTmpRelatives('#myTmpRelatives_${idx.index}');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveTmpRelatives('#myTmpRelatives_${idx.index}',${idx.index});"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delTmpRelatives('#myTmpRelatives_${idx.index}',${idx.index});"><i class="icon icon-remove"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
relativesNum++;

$("#myTmpRelatives_${idx.index}").validate({debug:true,submitHandler: function(form) {
		$(form).ajaxSubmit(function(data) {
			alert("操作成功!");
			//formDisabled(true,"#myTmpRelatives_"+relativesForm);
		});
	}
});

//不可修改
//formDisabled(true,"#myTmpRelatives_${idx.index}");

</script>

</c:forEach>

