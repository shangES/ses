<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<form action="resume/saveOrUpdateWorkExperience.do" method="post" id="myWorkExperience_${ordernum}" >
	<input id="workexperienceguid_${ordernum }" name="workexperienceguid" type="hidden" value=""/>
	<input id="webuserguid_${ordernum }" name="webuserguid" type="hidden" value="${webuserguid }"/>
	<input id="modtime_${ordernum }" name="modtime" type="hidden" value=""/>
	<input id="rmk_${ordernum }" name="rmk" type="hidden" value="" />
		
	<div id="" class="formDiv">
		<ul>
			<li>
	             <span><em class="red">* </em>最近工作单位：</span>
	             <input id="workunit_${ordernum }" name="workunit" class="{required:true,maxlength:50} inputstyle" />
	         </li>
	         <li>
			    <span><em class="red">* </em>职位：</span>
			    <input id="posation_${ordernum }" name="posation" class="{required:true,maxlength:12} inputstyle" />
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>开始时间：</span>
			    <input id="Wstartdate_${ordernum }" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Wstartdate_${ordernum }').focus();"/>
			</li>
		    <li>
			    <span>结束时间：</span>
			    <input id="Wenddate_${ordernum }" name="enddate" class="{dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Wenddate_${ordernum }').focus();"/>
			</li>
		</ul>
		
		<ul>
			<li>
	             <span>证明人：</span>
	             <input id="references_${ordernum }" name="references" class="{maxlength:12} inputstyle" />
	         </li>
	         <li>
			    <span>证明人电话：</span>
			    <input id="referencesphone_${ordernum }" name="referencesphone" class="{maxlength:25} inputstyle" />
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
					<!--  <a class="btn" href="javascript:editWorkExperience('#myWorkExperience_${ordernum }');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveWorkExperience('#myWorkExperience_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delWorkExperience('#myWorkExperience_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>


