<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<form action="myperson/saveOrUpdateTmpProjectExperience.do" method="post" id="myProExperience_${ordernum}" >
	<input id="projectexperienceguid_${ordernum }" name="projectexperienceguid" type="hidden" value=""/>
	<input type="hidden" id="mycandidatesguid" name="mycandidatesguid" value="${mycandidatesguid}"/>
	<input id="modtime_${ordernum }" name="modtime" type="hidden" value=""/>
		
	<div id="" class="formDiv">
		<ul>
			<li>
	             <span><em class="red">* </em>项目名称：</span>
	             <input id="itemname_${ordernum }" name="itemname" class="{required:true,maxlength:50} inputstyle" style="width:570px;"/>
	         </li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>开始时间：</span>
			    <input id="Pstartdate_${ordernum }" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Pstartdate_${ordernum }').focus();"/>
			</li>
		    <li>
			    <span>结束时间：</span>
			    <input id="Penddate_${ordernum }" name="enddate" class="{dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Penddate_${ordernum }').focus();"/>
			</li>
		</ul>
		<ul>
            <li>
                 <span><em class="red">* </em>职责描述：</span>
                 <textarea id="jobdescription_${ordernum }" name="jobdescription"  rows="5" style="width:570px;" class="{required:true,maxlength:500} areastyle"></textarea>
            </li>
        </ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn btn-red" href="javascript:editProExperience('#myProExperience_${ordernum }');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveProExperience('#myProExperience_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delProExperience('#myProExperience_${ordernum }',${ordernum });"><i class="icon icon-remove"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>


