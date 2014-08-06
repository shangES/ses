package com.mk.schedul;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.mk.resume.service.ResumeEamilService;

public class BusinessProcessCheckScheduler extends QuartzJobBean {
	private ResumeEamilService resumeEamilService;

	private static int searchIndexFlag = 0;// 由doBuildIndex修改，其他方法不要修改此参数

	public synchronized void doBuildIndex() throws Exception {
		if (searchIndexFlag > 0) {
			return;// 保证同一时刻只有一个定时器运行,通过这种方式保证每次定时时间到时，只执行一个线程
		}
		searchIndexFlag = 1;// 锁定
		resumeEamilService.refreshResumeEamil();
		searchIndexFlag = 0;// 锁定解除
	}

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) {
		try {
			doBuildIndex();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			searchIndexFlag = 0;// 锁定解除
		}
	}

	public ResumeEamilService getResumeEamilService() {
		return resumeEamilService;
	}

	public void setResumeEamilService(ResumeEamilService resumeEamilService) {
		this.resumeEamilService = resumeEamilService;
	}

}
