package com.mk;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.constance.Constance;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.recruitment.dao.WebUserDao;
import com.mk.recruitment.entity.WebUser;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.Resume;

public class ReadEmailPersonFileUtils {
	
	
	@Transactional
	public WebUser save(SqlSession sqlSession,String subject, String email,String eamilguid,String username) {
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		
		
		WebUser user = webUserDao.checkWebUserByEmail(null, email);
		if (user == null) {
			// 保存外网用户
			if(username.length()>50){
				username=username.substring(0,50);
			}
			user = new WebUser(email, username);
			user.setMobile("无");
			user.setWebuserguid(UUIDGenerator.randomUUID());
			//System.out.println(user.toString());
			webUserDao.insertWebUser(user);
		}
		
		// 简历信息
		Resume resume = resumeDao.getResumeById(user.getWebuserguid());
		// 如果簡歷存在以最新的為準
		boolean isnew = false;
		if (resume == null) {
			isnew = true;
			resume = new Resume();
			resume.setWebuserguid(user.getWebuserguid());
			String name=user.getUsername();
			if(name.length()>25){
				name=name.substring(0,25);
			}
			resume.setName(name);
			resume.setEmail(user.getEmail());
			resume.setCreatetime(new Timestamp(System.currentTimeMillis()));
		}
		
		resume.setModtime(new Timestamp(System.currentTimeMillis()));
		
		if (isnew)
			resumeDao.insertResume(resume);
		else
			resumeDao.updateResume(resume);
		
		// 实例化
		MyCandidates model = new MyCandidates();
		if(subject.length()>100){
			subject=subject.substring(0,100);
		}
		model.setRecruitpostname(subject.replaceAll(" ", ""));
		model.setMycandidatesguid(UUIDGenerator.randomUUID());
		model.setWebuserguid(user.getWebuserguid());
		model.setCandidatesstate(Constance.CandidatesState_One);
		model.setCandidatestype(Constance.User3);
		model.setProgress(Constance.VALID_NO);
		model.setReadtype(Constance.VALID_NO);
		model.setResumeeamilguid(eamilguid);
		
		//过滤掉一周以内投递过的相同简历
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		model.setTempdate(format.format(System.currentTimeMillis()));
		List<MyCandidates> candidateslist = myCandidatesDao.checkResume(model);
		if(candidateslist != null&&!candidateslist.isEmpty()){
			//System.out.println("===一周内以投递===");
			return null;
		}
		
		//操作时间
		model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		// 投递时间
		model.setCandidatestime(new Date(System.currentTimeMillis()));
		myCandidatesDao.insertMyCandidates(model);
		
		return user;
	}
}
