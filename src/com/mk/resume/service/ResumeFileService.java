package com.mk.resume.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.HttpRequestHandler;

import com.mk.framework.grid.json.HTTP;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.Resume;
import com.mk.resume.entity.ResumeFile;

@Service
public class ResumeFileService {

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 保存以及修改
	 * 
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateResumeFile(ResumeFile model) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		if (StringUtils.isEmpty(model.getResumefileguid())) {
			model.setResumefileguid(UUIDGenerator.randomUUID());
			model.setModtime(new Timestamp(System.currentTimeMillis()));
			mapper.insertResumeFile(model);
		} else {
			mapper.updateResumeFile(model);
		}
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void delResumeFileById(HttpServletRequest request,String ids) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			ResumeFile model = mapper.getResumeFileById(id);
			if(model!=null){
				ServletContext sc = request.getSession().getServletContext();
				String sourceFile = sc.getRealPath(model.getResumefilepath());
				File file = new File(sourceFile);
				file.delete();
				mapper.delResumeFileById(id);
			}
		}
	}

	/**
	 * 得到
	 * 
	 * @return
	 */
	public List<ResumeFile> getResumeFileListByResumeGuid(String resumeguid) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		return mapper.getAllResumeFileByWebuserId(resumeguid);
	}

	/**
	 * 得到ResumeFile
	 * 
	 * @param resumeguid
	 * @return
	 */
	public ResumeFile getResumeFileListByResumeFileId(String resumeguid) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		return mapper.getResumeFileById(resumeguid);
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public List<ResumeFile> getAllResumeFileByResumeId(String id) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		List<ResumeFile> data = mapper.getAllResumeFileByWebuserId(id);
		return data;
	}
}
