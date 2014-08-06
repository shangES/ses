<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<c:forEach items="${resumefiles }" var="item" varStatus="idx">
<form action="resume/saveOrUpdateResumeFile.do" method="post" id="myResumeFile_${idx.index}">
	<input id="resumefileguid_${idx.index}" name="resumefileguid" type="hidden" value="${item.resumefileguid }"/>
	<input id="webuserguid_${idx.index}" name="webuserguid" type="hidden" value="${item.webuserguid }"/>
	<input id="modtime_${idx.index}" name="modtime" type="hidden" value="${item.modtime }"/>
	<input id="resumefilesize_${idx.index}" name="resumefilesize" type="hidden" value="${item.resumefilesize }"/>
	
		
	<div id="" class="formDiv">
		<ul>
			<li>
	             <span><em class="red">* </em>附件名称：</span>
	             <input id="resumefilename_${idx.index}" name="resumefilename" class="{maxlength:50} inputstyle " style="width:570px;" value="${item.resumefilename }"/>
	         </li>
		</ul>
		<ul>
			<li>
	             <span><em class="red">* </em>路径：</span>
	             <input id="resumefilepath_${idx.index}" name="resumefilepath" class="inputstyle" style="width:570px;" value="${item.resumefilepath }"/>
	             <em id="resumefilepath_${idx.index}_statu"></em>
	         </li>
		</ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<a class="btn" href="downloadDocument.do?id=${item.resumefileguid }"><i class="icon icon-arrow-down"></i><span>下载</span></a>
					<!--  <a class="btn" href="javascript:editResumeFile('#myResumeFile_${idx.index}');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveResumeFile('#myResumeFile_${idx.index}',${idx.index});"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delResumeFile('#myResumeFile_${idx.index}',${idx.index});"><i class="icon icon-check"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>

<script type="text/javascript">
resumefileFormNum++;

$("#myResumeFile_${idx.index}").validate({debug:true,submitHandler: function(form) {
		$(form).ajaxSubmit(function(data) {
			alert("操作成功!");
			//formDisabled(true,"#myResumeFile_"+resumefileForm);
		});
	}
});

//不可修改
//formDisabled(true,"#myResumeFile_${idx.index}");

//上传控件
initAjaxFileUpload(${idx.index});

</script>

</c:forEach>
