<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<form action="person/saveOrUpdateTmpWorkExperience.do" method="post" id="myWorkExperience_${ordernum}" >
	<input id="workexperienceid_${ordernum }" name="workexperienceid" type="hidden" value=""/>
	<input type="hidden" id="mycandidatesguid" name="mycandidatesguid" value="${mycandidatesguid}"/>	
	<div id="" class="formDiv">
		<ul>
			<li>
	             <span><em class="red">* </em>单位：</span>
	             <input id="workunit_${ordernum }" name="workunit" class="{required:true,maxlength:50} inputstyle" />
	         </li>
	         <li>
			    <span><em class="red">* </em>担任职务：</span>
			    <input id="job_${ordernum }" name="job" class="{required:true,maxlength:12} inputstyle" />
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>起始日期：</span>
			    <input id="Wstartdate_${ordernum }" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Wstartdate_${ordernum }').focus();"/>
			</li>
		    <li>
			    <span>终止日期：</span>
			    <input id="Wenddate_${ordernum }" name="enddate" class="{dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Wenddate_${ordernum }').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
	             <span><em class="red">* </em>汇报人：</span>
	             <input id="reporter_${ordernum }" name="reporter" class="{required:true,maxlength:50} inputstyle" />
	         </li>
	         <li>
				<span>汇报人邮箱：</span>
				<input id="email_${ordernum}" name="email" class="{maxlength:50,email:true} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>汇报人电话：</span>
			    <input id="reporterphone_${ordernum }" name="reporterphone" class="{required:true,number:true,maxlength:50} inputstyle"/>
			</li>
			<li>
				 <span>薪资：</span>
				 <input id="money_${ordernum }" name="money" class="{maxlength:50} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
	             <span>离职原因：</span>
	             <input id="resignationreason_${ordernum }" name="resignationreason" type="hidden" value="" />
				<input id="resignationreasonname_${ordernum}" name="resignationreasonname" class="inputselectstyle"  onclick="chooseOptionTree('#resignationreason_${ordernum}','#resignationreasonname_${ordernum}','RESIGNATIONREASON');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#resignationreason_${ordernum}','#resignationreasonname_${ordernum}','RESIGNATIONREASON');"/>
	         </li>
		</ul>
		<ul>
            <li>
                 <span><em class="red">* </em>任职经历描述：</span>
                 <textarea id="description_${ordernum }" name="description"  rows="5" style="width:570px;" class="{required:true,maxlength:500} areastyle"></textarea>
            </li>
        </ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn" href="javascript:editWorkExperience('#myWorkExperience_${ordernum }');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveWorkExperience('#myWorkExperience_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delWorkExperience('#myWorkExperience_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>


