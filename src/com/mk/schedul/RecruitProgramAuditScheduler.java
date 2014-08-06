package com.mk.schedul;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.mk.recruitprogram.service.RecruitProgramAuditService;

public class RecruitProgramAuditScheduler extends QuartzJobBean {

	private RecruitProgramAuditService recruitProgramAuditService;

	public RecruitProgramAuditService getRecruitProgramAuditService() {
		return recruitProgramAuditService;
	}

	public void setRecruitProgramAuditService(RecruitProgramAuditService recruitProgramAuditService) {
		this.recruitProgramAuditService = recruitProgramAuditService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		try {
			recruitProgramAuditService.refreshRecruitProgramAudit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
