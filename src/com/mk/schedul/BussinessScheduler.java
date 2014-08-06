package com.mk.schedul;

import java.sql.Date;
import java.util.Calendar;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.mk.audition.service.AuditionRecordService;
import com.mk.framework.constance.Constance;
import com.mk.framework.grid.util.DateUtil;
import com.mk.mycandidates.service.RecommendService;
import com.mk.person.service.PersonService;

public class BussinessScheduler extends QuartzJobBean {
	private AuditionRecordService auditionRecordService;
	private RecommendService recommendService;
	private PersonService personService;

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public AuditionRecordService getAuditionRecordService() {
		return auditionRecordService;
	}

	public void setAuditionRecordService(AuditionRecordService auditionRecordService) {
		this.auditionRecordService = auditionRecordService;
	}

	public RecommendService getRecommendService() {
		return recommendService;
	}

	public void setRecommendService(RecommendService recommendService) {
		this.recommendService = recommendService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {

		try {
			Date date = new Date(System.currentTimeMillis());
			Calendar ca = Calendar.getInstance();
			ca.setTime(date);
			ca.add(Calendar.DATE, Constance.RETERENCETIME);
			StringBuffer _datas = new StringBuffer();
			_datas.append(DateUtil.formatDateYMD(ca.getTime()));
			_datas.append(" 23:59:59");

			recommendService.sendMessageToRecommender();
			auditionRecordService.sendMessageToInterviewer();
			personService.sendMessageToReterence(_datas.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
