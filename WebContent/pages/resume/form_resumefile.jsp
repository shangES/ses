<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<form action="resume/saveOrUpdateResumeFile.do" method="post" id="myResumeFile_${ordernum}">
	<input id="resumefileguid_${ordernum }" name="resumefileguid" type="hidden" value=""/>
	<input id="webuserguid_${ordernum }" name="webuserguid" type="hidden" value="${resumeguid }"/>
	<input id="modtime_${ordernum }" name="modtime" type="hidden" value=""/>
	<input id="resumefilesize_${ordernum }" name="resumefilesize" type="hidden" value="0"/>
	
		
	<div id="" class="formDiv">
		<ul>
			<li>
	             <span><em class="red">* </em>附件名称：</span>
	             <input id="resumefilename_${ordernum }" name="resumefilename" class="{required:true,maxlength:50} inputstyle " style="width:570px;"/>
	         </li>
		</ul>
		<ul>
			<li>
	             <span><em class="red">* </em>路径：</span>
	             <input id="resumefilepath_${ordernum }" name="resumefilepath" class="{required:true} inputstyle" style="width:570px;"/>
	             <em id="resumefilepath_${ordernum }_statu"></em>
	         </li>
		</ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<a class="btn" style="display:none" id="download_${ordernum}" href="javascript:downLoadFile(${ordernum },'${baseUrl }');"><i class="icon icon-arrow-down"></i><span>下载</span></a>
					<!--  <a class="btn" href="javascript:editResumeFile('#myResumeFile_${ordernum }');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveResumeFile('#myResumeFile_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delResumeFile('#myResumeFile_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>
