package com.mk.schedul;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.mk.mycandidates.service.MyCandidatesService;

public class MyCandidatesScheduler extends QuartzJobBean{
	private MyCandidatesService myCandidatesService;
	
	public MyCandidatesService getMyCandidatesService() {
		return myCandidatesService;
	}

	public void setMyCandidatesService(MyCandidatesService myCandidatesService) {
		this.myCandidatesService = myCandidatesService;
	}
	
	//解锁其他应聘信息
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		
		try {
			//解锁
			myCandidatesService.unlockMyCandidates();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
