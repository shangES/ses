package com.mk.resume.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.ReadEmail51JobFileUtils;
import com.mk.ReadEmailExcelFileUtils;
import com.mk.ReadEmailZlZPFileUtils;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.mail.MailReceiveService;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.NameConvertCodeService;
import com.mk.recruitment.dao.WebUserDao;
import com.mk.recruitment.entity.WebUser;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.Resume;
import com.mk.resume.entity.ResumeEamil;
import com.mk.resume.entity.ResumeEamilFile;

@Service
public class ResumeEamilService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private MailReceiveService mailReceiveService;
	@Autowired
	private NameConvertCodeService nameConvertCodeService;

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @throws IOException
	 */
	@Transactional(readOnly = true)
	public void searchResumeEamil(GridServerHandler grid) throws IOException {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countResumeEamil(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<ResumeEamil> list = mapper.searchResumeEamil(grid);
		for (ResumeEamil model : list) {
			model.convertOneCodeToName(mapper);
			data.add(JSONUtils.Bean2JSONObject(model));
		}

		grid.setData(data);

	}

	/**
	 * 保存或修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateResumeEamil(ResumeEamil model) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		WebUserDao webUserDao = sqlSession.getMapper(WebUserDao.class);
		if (StringUtils.isEmpty(model.getResumeeamilguid())) {
			// 用户保存
			WebUser webuser = new WebUser(model.getEmail(), model.getSubject());
			webuser.setWebuserguid(UUIDGenerator.randomUUID());
			webUserDao.insertWebUser(webuser);

			// 时间
			model.setModtime(new Timestamp(System.currentTimeMillis()));
			model.setResumeeamilguid(UUIDGenerator.randomUUID());
			model.setWebuserguid(webuser.getWebuserguid());
			mapper.insertResumeEamil(model);
		} else {
			mapper.updateResumeEamil(model);
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public ResumeEamil getResumeEamilById(String id) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);

		ResumeEamil model = mapper.getResumeEamilById(id);
		if (model != null) {
			List<ResumeEamilFile> files = mapper.getResumeEamilFileByResumeeamilId(id);
			model.setResumeeamilfiles(files);
		}
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	public void delResumeEamilById(String ids) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delResumeEamilFileByResumeeamilId(id);
			mapper.delResumeEamilById(id);

		}
	}

	/**
	 * 同步邮件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void refreshResumeEamil() throws Exception {
		// 同步邮件
		mailReceiveService.refreshResumeEamil(nameConvertCodeService, sqlSession);
	}

	/**
	 * 得到
	 * 
	 * @param email
	 * @return
	 */
	public String getResumeEamilByEmail(String email, String id) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		StringBuffer msg = new StringBuffer();
		List<ResumeEamil> list = mapper.getResumeEamilByEmail(email, id);
		if (!list.isEmpty()) {
			msg.append("【");
			msg.append(list.get(0).getEmail());
			msg.append("】");
		}
		return msg.toString();
	}

	/**
	 * 得到
	 * 
	 * @param email
	 * @return
	 */
	public ResumeEamilFile getResumeEamilFileById(String id) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		return mapper.getResumeEamilFileById(id);
	}

	/**
	 * 标记为已读
	 * 
	 * @param ids
	 */
	@Transactional
	public void updateByReadtype(String ids, Integer readtype) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);

		if (StringUtils.isNotEmpty(ids)) {
			String[] obj = ids.split(",");
			for (String resumeeamilguid : obj) {
				ResumeEamil model = mapper.getResumeEamilById(resumeeamilguid);
				if (model != null) {
					model.setReadtype(readtype);
					mapper.updateResumeEamil(model);
				}
			}
		}

	}

	/**
	 * 简历导入
	 * 
	 * @param file
	 * @throws IOException
	 */
	@Transactional
	public void resumeUpload(FileItem item) throws IOException {
		WebUser user = null;
		Document doc = Jsoup.parse(item.getInputStream(), "GBK", "GBK");
		Elements table = doc.select("body>table");
		//System.out.println(table);
		// 简历类别，前程无忧，智联招聘
		String resumetype = "";
		if (table.select("table[width=778][align=center]") != null) {
			//前程无忧
			Elements themtable = table.select("table[width=778][align=center]");
			if (themtable.size() > 0) {
				String job51model = "http://img01.51jobcdn.com/im/2009/resumetemplate/logo.gif";
				Elements imgelements = themtable.get(0).select("img");
				for(Element element : imgelements){
					//获取s元素rc里的值
					if(element.attr("src").replaceAll(" ", "").equals(job51model)){
						resumetype = "前程无忧";
					}
				}
			}
		}
		if(table.select("table[width=600]") != null){
			//智联招聘
			Elements themtable = table.select("table[width=600]");
			if(themtable.size() > 0){
				for(Element tableelement : themtable){
					if(tableelement.select("td[align=right]") != null){
						Elements tdelements = tableelement.select("td[align=right]");
						if(tdelements.size() > 0){
							for(Element element : tdelements){
								if(element.text().replaceAll(" ", "").equals("智联招聘")){
									resumetype = "智联招聘";
								}
							}
						}
					}
				}
			}
		}
		//System.out.println("====resumetype===="+resumetype);
		if (resumetype.equals("前程无忧")) {
			//Document job51doc = Jsoup.parse(item.getInputStream(), "GBK", "GBK");
			//Elements job51table = table.select("body>table");
			//System.out.println(job51table);
			// 前程无忧
			ReadEmail51JobFileUtils readEmail51JobFileUtil = new ReadEmail51JobFileUtils();
			if (table.select("table[class=table_set]").size() > 0) {
				user = readEmail51JobFileUtil.save(nameConvertCodeService, sqlSession, doc, null);
			} else if (table.select("table[class=v_table02]").size() > 0) {
				user = readEmail51JobFileUtil.saveAnalysisResumeModel_3(nameConvertCodeService, sqlSession, doc, null);
			} else if (table.select("table[class=table_set]").size() <= 0 && table.select("table[class=v_table02]").size() <= 0) {
				user = readEmail51JobFileUtil.saveAnalysisResumeModel_2(nameConvertCodeService, sqlSession, doc, null);
			}
		}
		if (resumetype.equals("智联招聘")) {
			// 智联招聘
			ReadEmailZlZPFileUtils readEmailZlZPFileUtils = new ReadEmailZlZPFileUtils();
			user = readEmailZlZPFileUtils.save(nameConvertCodeService, sqlSession, doc.html(), null, null);
		}
	}
	
	
	
	/**
	 * 简历批量导入
	 * 
	 * @param file
	 * @throws IOException
	 */
	@Transactional
	public void resumeImport(File file) throws IOException {
		WebUser user = null;
		String str = file.toString();
		int index = str.lastIndexOf(".");
		String suffixname = str.substring(index+1, str.length());
		Document doc = null;
		String resumetype = "";
		Elements table = null;
		if(!suffixname.equals("htm")){
			doc = Jsoup.parse(file, "GBK", "GBK");
			table = doc.select("body>table");
			// 简历类别，前程无忧，智联招聘
			if (table.select("table[width=778][align=center]") != null && table.select("table[width=778][align=center]").size() > 0) {
				//前程无忧
				Elements themtable = table.select("table[width=778][align=center]");
				if (themtable.size() > 0) {
					String job51model = "http://img01.51jobcdn.com/im/2009/resumetemplate/logo.gif";
					Elements imgelements = themtable.get(0).select("img");
					for(Element element : imgelements){
						//获取s元素rc里的值
						if(element.attr("src").replaceAll(" ", "").equals(job51model)){
							resumetype = "前程无忧";
						}
					}
				}
			}else	if(table.select("table[width=600]") != null && table.select("table[width=600]").size() >0){
				//智联招聘
				Elements themtable = table.select("table[width=600]");
				if(themtable.size() > 0){
					for(Element tableelement : themtable){
						if(tableelement.select("td[align=right]") != null){
							Elements tdelements = tableelement.select("td[align=right]");
							if(tdelements.size() > 0){
								for(Element element : tdelements){
									if(element.text().replaceAll(" ", "").equals("智联招聘")){
										resumetype = "智联招聘";
									}
								}
							}
						}
					}
				}
			}
		}else{
			doc = Jsoup.parse(file, "UTF-8", "UTF-8");
			resumetype = "智联招聘";
		}
		//System.out.println("====resumetype===="+resumetype);
		if (resumetype.equals("前程无忧")) {
			//Document job51doc = Jsoup.parse(item.getInputStream(), "GBK", "GBK");
			//Elements job51table = table.select("body>table");
			//System.out.println(job51table);
			// 前程无忧
			ReadEmail51JobFileUtils readEmail51JobFileUtil = new ReadEmail51JobFileUtils();
			if (table.select("table[class=table_set]").size() > 0) {
				user = readEmail51JobFileUtil.save(nameConvertCodeService, sqlSession, doc, null);
			} else if (table.select("table[class=v_table02]").size() > 0) {
				user = readEmail51JobFileUtil.saveAnalysisResumeModel_3(nameConvertCodeService, sqlSession, doc, null);
			} else if (table.select("table[class=table_set]").size() <= 0 && table.select("table[class=v_table02]").size() <= 0) {
				user = readEmail51JobFileUtil.saveAnalysisResumeModel_2(nameConvertCodeService, sqlSession, doc, null);
			}
		}else	if (resumetype.equals("智联招聘")) {
			// 智联招聘
			ReadEmailZlZPFileUtils readEmailZlZPFileUtils = new ReadEmailZlZPFileUtils();
			//System.out.println("=========="+str);
			if(!suffixname.equals("htm"))
				user = readEmailZlZPFileUtils.save(nameConvertCodeService, sqlSession, doc.html(), null, null);
			else
				user = readEmailZlZPFileUtils.savediv(nameConvertCodeService, sqlSession, doc.html(), null, null);
		}
	}
	
	/**
	 * excel解析
	 * 
	 * @param file
	 * @throws IOException 
	 */
	@Transactional
	public String analysisExcel(File file) throws IOException{
		ReadEmailExcelFileUtils excel = new ReadEmailExcelFileUtils();
		return excel.analysisExcel(file, sqlSession,nameConvertCodeService);
	}

}
