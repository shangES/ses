<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<c:forEach items="${tmpRecommendlist }" var="item" varStatus="idx">
<form action="myperson/saveOrUpdateTmpRecommend.do" method="post" id="myTmpRecommend_${idx.index}" >
	<input id="recommendguid_${idx.index}" name="recommendguid" type="hidden" value="${item.recommendguid }"/>
	<input id="mycandidatesguid_${idx.index}" name="mycandidatesguid" type="hidden" value="${item.mycandidatesguid }"/>	
		
	<div id="" class="formDiv">
		<ul>
			<li>
				<span><em class="red">* </em>姓名：</span>
				<input  id="name_${idx.index}" name="name"  value="${item.name }" class="{required:true,maxlength:50} inputstyle"/>
			</li>
			<li>
			    <span><em class="red">* </em>性别：</span>
			    <input id="sex_${idx.index}" name="sex" type="hidden" value="${item.sex }"/>
			    <input id="sexname_${idx.index}" name="sexname" value="${item.sexname }"  class="{required:true} inputselectstyle" onclick="chooseOptionTree('#sex_${idx.index}','#sexname_${idx.index}','SEX');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#sex_${idx.index}','#sexname_${idx.index}','SEX');"/>
			</li>
		</ul>
		<ul>
		<li>
				<span><em class="red">* </em>推荐岗位：</span>
				<input  id="recommendpostname_${idx.index}" name="recommendpostname"  value="${item.recommendpostname }" class="{required:true,maxlength:10} inputstyle"/>
			</li>
			<li>
	             <span><em class="red">* </em>工作年限：</span>
	             <input id="workage_${idx.index}" name="workage" type="hidden" value="${item.workage }" />
				<input id="workagename_${idx.index}"  value="${item.workagename }"  name="workagename" class="{required:true} inputselectstyle"  onclick="chooseOptionTree('#workage_${idx.index}','#workagename_${idx.index}','WORKAGE');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#workage_${idx.index}','#workagename_${idx.index}','WORKAGE');"/>
	         </li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>联系电话：</span>
				<input  id="mobile_${idx.index}" name="mobile"  value="${item.mobile }" class="{required:true,number:true,maxlength:50} inputstyle"/>
			</li>
			<li>
				<span>电子邮箱：</span>
				<input id="email_${idx.index}" name="email"  value="${item.email }" class="{maxlength:50,email:true} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>目前状况：</span>
				<input id="situation_${idx.index}" name="situation" value="${item.situation }" class="{maxlength:50} inputstyle"/>
			</li>
		</ul>
		
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn btn-red" href="javascript:editTmpRecommend('#myTmpRecommend_${idx.index}');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveTmpRecommend('#myTmpRecommend_${idx.index}',${idx.index});"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delTmpRecommend('#myTmpRecommend_${idx.index}',${idx.index});"><i class="icon icon-remove"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
recommendNum++;

$("#myTmpRecommend_${idx.index}").validate({debug:true,submitHandler: function(form) {
		$(form).ajaxSubmit(function(data) {
			alert("操作成功!");
			//formDisabled(true,"#myTmpRecommend_"+recommendForm);
		});
	}
});

//不可修改
//formDisabled(true,"#myTmpRecommend_${idx.index}");

//日期选择
initDatePicker();
</script>

</c:forEach>

