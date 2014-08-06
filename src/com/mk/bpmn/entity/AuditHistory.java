package com.mk.bpmn.entity;

import java.sql.Timestamp;

import org.activiti.engine.task.Task;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.util.DateUtil;
import com.mk.framework.utils.UUIDGenerator;

public class AuditHistory {

	private String audithisid;
	private String processinstanceid;
	private String taskid;
	private String taskname;
	private Timestamp excutedate;
	private String applycompanyid;
	private String applycompanyname;
	private String applydeptid;
	private String applydeptname;
	private String applyuserid;
	private String applyusername;
	private String actname;
	private String opinion;

	public AuditHistory() {

	}

	/**
	 * 实始化
	 * 
	 * @param businessKey
	 * @param task
	 * @param commit
	 * @param result
	 */
	public AuditHistory(String businessKey, Task task, String result) {
		UserContext uc = ContextFacade.getUserContext();

		this.setAudithisid(UUIDGenerator.randomUUID());
		this.setProcessinstanceid(businessKey);
		if (task != null) {
			this.setTaskid(task.getId());
			this.setTaskname(task.getName());
		} else {
			this.setTaskid("未知");
			this.setTaskname("未知");
		}
		this.setExcutedate(new Timestamp(System.currentTimeMillis()));
		this.setApplycompanyid(uc.getCompanyid());
		this.setApplycompanyname(uc.getCompanyname());
		this.setApplyuserid(uc.getUserId());
		this.setApplyusername(uc.getUsername());
		this.setApplydeptid(uc.getDeptid());
		this.setApplydeptname(uc.getDeptname());
		this.setActname(task.getName());
		this.setOpinion(result);
	}

	public String getApplycompanyid() {
		return applycompanyid;
	}

	public void setApplycompanyid(String applycompanyid) {
		this.applycompanyid = applycompanyid;
	}

	public String getApplycompanyname() {
		return applycompanyname;
	}

	public void setApplycompanyname(String applycompanyname) {
		this.applycompanyname = applycompanyname;
	}

	public String getApplydeptid() {
		return applydeptid;
	}

	public void setApplydeptid(String applydeptid) {
		this.applydeptid = applydeptid;
	}

	public String getApplydeptname() {
		return applydeptname;
	}

	public void setApplydeptname(String applydeptname) {
		this.applydeptname = applydeptname;
	}

	public String getAudithisid() {
		return audithisid;
	}

	public void setAudithisid(String audithisid) {
		this.audithisid = audithisid;
	}

	public String getProcessinstanceid() {
		return processinstanceid;
	}

	public void setProcessinstanceid(String processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getExcutedateText() {
		return DateUtil.formatDateMDHHmm(excutedate);
	}

	public String getApplyuserid() {
		return applyuserid;
	}

	public void setApplyuserid(String applyuserid) {
		this.applyuserid = applyuserid;
	}

	public String getApplyusername() {
		return applyusername;
	}

	public void setApplyusername(String applyusername) {
		this.applyusername = applyusername;
	}

	public String getActname() {
		return actname;
	}

	public void setActname(String actname) {
		this.actname = actname;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getExcutedate() {
		return excutedate;
	}

	public void setExcutedate(Timestamp excutedate) {
		this.excutedate = excutedate;
	}

	@Override
	public String toString() {
		return "AuditHistory [audithisid=" + audithisid + ", processinstanceid=" + processinstanceid + ", taskid=" + taskid + ", taskname=" + taskname + ", excutedate=" + excutedate
				+ ", applycompanyid=" + applycompanyid + ", applycompanyname=" + applycompanyname + ", applydeptid=" + applydeptid + ", applydeptname=" + applydeptname + ", applyuserid="
				+ applyuserid + ", applyusername=" + applyusername + ", actname=" + actname + ", opinion=" + opinion + "]";
	}

}
