package com.mk.schedul;

import java.sql.Date;
import java.util.Calendar;

import org.apache.ibatis.session.SqlSession;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.mk.framework.constance.Constance;
import com.mk.framework.grid.util.DateUtil;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.mycandidates.service.MyCandidatesService;

public class TestScheduler extends QuartzJobBean {

	private MyCandidatesService myCandidatesService;
	
	public MyCandidatesService getMyCandidatesService() {
		return myCandidatesService;
	}

	public void setMyCandidatesService(MyCandidatesService myCandidatesService) {
		this.myCandidatesService = myCandidatesService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		
		try {
//			Date date = new Date(System.currentTimeMillis());
//			Calendar ca = Calendar.getInstance();
//			ca.setTime(date);
//			ca.add(Calendar.DATE, Constance.FAILURETIME);
//			StringBuffer _datas = new StringBuffer();
//			_datas.append(DateUtil.formatDateYMD(ca.getTime()));
//			_datas.append(" 23:59:59");
//			//推荐失效
//			myCandidatesService.updateInvalidMyCandidatesByState(_datas.toString());
			//应聘信息是否锁定
			myCandidatesService.lockMyCandidates();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}