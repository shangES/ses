package com.mk.resume.service;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.addresslist.dao.AddressListDao;
import com.mk.company.dao.CompanyDao;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.employee.dao.EmployeeDao;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.mail.MailSendService;
import com.mk.framework.mail.SendMessageService;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.tree.TreePageGrid;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.mycandidates.entity.Recommend;
import com.mk.quota.dao.QuotaDao;
import com.mk.recruitment.dao.RecruitmentDao;
import com.mk.recruitment.dao.WebUserDao;
import com.mk.recruitment.entity.RecruitPost;
import com.mk.recruitment.entity.WebUser;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.InteriorRecommend;
import com.mk.resume.entity.Relatives;
import com.mk.resume.entity.Resume;
import com.mk.resume.entity.ResumeEamil;
import com.mk.resume.entity.ResumeFile;
import com.mk.resume.tree.RecruiterTree;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.User;

@Service
public class ResumeService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CodeConvertNameService codeConvertNameService;
	@Autowired
	private MailSendService mailSendService;
	@Autowired
	private SendMessageService sendMessageService;
	
	/**
	 * 得到招聘专员树
	 * 
	 * @return
	 */
	public List<TreeNode> buildRecruiterTree() {
		SystemDao mapper = sqlSession.getMapper(SystemDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		// 得到招聘专员角色下的员工
		List<User> list = mapper.getRecruiterUser(Constance.RoleName);
		if (!list.isEmpty()) {
			for (User user : list) {
				user.convertOneCodeToName(employeeDao, companyDao, departmentDao, postDao, quotaDao, optionDao, null);
			}
		} else {
			return null;
		}
		// 成树
		RecruiterTree tree = new RecruiterTree();
		return tree.dobuild(list);
	}

	/**
	 * 得到招聘专员树
	 * 
	 * @return
	 */
	public List<TreeNode> buildPostNameTree(String userguid) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);

		// 得到招聘专员下的职位
		List<RecruitPost> list = mapper.getRecruitPostByUser(userguid);
		if (list.isEmpty()) {
			return null;
		}
		// 成树
		RecruiterTree tree = new RecruiterTree();
		return tree.dobuildPostNameTree(list);
	}

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchResume(GridServerHandler grid) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();

		// 统计
		Integer count = mapper.countResume(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<Resume> list = mapper.searchResume(grid);
		// 批量
		if (list.size() > Constance.ConvertCodeNum) {
			// 来源
			Map<Integer, String> resumetypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RESUMETYPE);
			// 最高学历
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);
			// 工作年限
			Map<Integer, String> workageMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.WORKAGE);
			for (Resume model : list) {
				model.convertManyCodeToName(resumetypeMap, cultureMap, sexMap, workageMap, myCandidatesDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (Resume model : list) {
				model.convertOneCodeToName(optionDao, myCandidatesDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}

		grid.setData(data);

	}
	
	
	/**
	 * 批量保存
	 * 
	 * @param grid
	 * @return
	 */
	@Transactional
	public String saveResumeGrid(GridServerHandler grid) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		WebUserDao webuserdao = sqlSession.getMapper(WebUserDao.class);

		// 添加的行
		List<Object> addList = grid.getInsertedRecords(Resume.class);
		if (!addList.isEmpty()) {
			for (Object obj : addList) {
				// 简历信息
				Resume model = (Resume) obj;
				WebUser user = webuserdao.checkWebUserByEmail(null, model.getEmail());
				if(user==null){
					// 注册用户
					WebUser webuser = new WebUser(model.getEmail(), model.getName());
					webuser.setWebuserguid(UUIDGenerator.randomUUID());
					if(StringUtils.isNotEmpty(model.getMobile())){
						webuser.setMobile(model.getMobile());
					}
					webuserdao.insertWebUser(webuser);
					
					//简历信息
					model.setWebuserguid(webuser.getWebuserguid());
					model.setModtime(new Timestamp(System.currentTimeMillis()));
					model.setCreatetime(new Timestamp(System.currentTimeMillis()));
					mapper.insertResume(model);
				}else{
					Resume resume = mapper.getResumeById(user.getWebuserguid());
					if(resume!=null){
						model.setWebuserguid(resume.getWebuserguid());
						model.setCreatetime(resume.getCreatetime());
						model.setModtime(new Timestamp(System.currentTimeMillis()));
						mapper.updateResume(model);
					}
				}
			}
		}

		if (addList.size() > 0)
			return "共 " + addList.size() + " 条数据，成功导入！";
		return null;
	}
	
	
	
	/**
	 * 批量保存(简历筛选)
	 * 
	 * @param grid
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public String saveResumeGridByFiter(GridServerHandler grid) throws Exception {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		WebUserDao webuserdao = sqlSession.getMapper(WebUserDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		SystemDao systemDao= sqlSession.getMapper(SystemDao.class);
		UserContext uc = ContextFacade.getUserContext();
		
		// 添加的行
		List<Object> addList = grid.getInsertedRecords(Resume.class);
		if (!addList.isEmpty()) {
			for (Object obj : addList) {
				// 简历信息
				Resume model = (Resume) obj;
				WebUser user = webuserdao.checkWebUserByEmail(null, model.getEmail());
				if(user==null){
					// 注册用户
					WebUser webuser = new WebUser(model.getEmail(), model.getName());
					webuser.setWebuserguid(UUIDGenerator.randomUUID());
					if(StringUtils.isNotEmpty(model.getMobile())){
						webuser.setMobile(model.getMobile());
					}
					webuserdao.insertWebUser(webuser);
					
					//简历信息
					model.setWebuserguid(webuser.getWebuserguid());
					model.setModtime(new Timestamp(System.currentTimeMillis()));
					model.setCreatetime(new Timestamp(System.currentTimeMillis()));
					mapper.insertResume(model);
					
					//发送短信
					mailSendService.sendResumeMailTo(model.getName(), model.getCreatetime(), model.getEmail());
					sendMessageService.sendResumeAssessMsgTo(model.getName(), model.getCreatetime(), model.getMobile(), model.getEmail());
				}else{
					Resume resume = mapper.getResumeById(user.getWebuserguid());
					if(resume!=null){
						model.setWebuserguid(resume.getWebuserguid());
						model.setCreatetime(resume.getCreatetime());
						model.setModtime(new Timestamp(System.currentTimeMillis()));
						mapper.updateResume(model);
					}
				}
				
				//保存应聘
				MyCandidates myCandidate = new MyCandidates();
				myCandidate.setMycandidatesguid(UUIDGenerator.randomUUID());
				myCandidate.setCandidatestime(new Date(System.currentTimeMillis()));
				myCandidate.setCandidatesstate(Constance.CandidatesState_One);
				myCandidate.setWebuserguid(model.getWebuserguid());
				myCandidate.setCandidatestype(Constance.User8);
				myCandidate.setProgress(Constance.VALID_YES);
				myCandidate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				myCandidate.setReadtype(Constance.VALID_NO);
				User dbuser = systemDao.getUserByUserId(uc.getUserId());
				if(dbuser!=null){
					myCandidate.setEmployeeid(dbuser.getEmployeeid());
				}
				myCandidatesDao.insertMyCandidates(myCandidate);
			}
		}

		if (addList.size() > 0)
			return "共 " + addList.size() + " 条数据，成功导入！";
		return null;
	}
	

	/**
	 * 保存或修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateResume(HttpServletRequest request,Resume model) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		// 修改时间
		model.setModtime(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isEmpty(model.getWebuserguid())) {
			model.setCreatetime(new Timestamp(System.currentTimeMillis()));
			model.setWebuserguid(UUIDGenerator.randomUUID());
			mapper.insertResume(model);
		} else {
			if(StringUtils.isNotEmpty(model.getPhoto())){
				String photo=model.getPhoto();
				photo=photo.substring(photo.indexOf("u"),photo.length());
				model.setPhoto(photo.replaceAll(" ", ""));
			}
			mapper.updateResume(model);
		}

	}

	/**
	 * 保存内部推荐
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateRecommendResume(Resume model) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		WebUserDao userdao = sqlSession.getMapper(WebUserDao.class);
		MyCandidatesDao mycandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		boolean flag = true;

		WebUser user = userdao.checkWebUserByEmail(null, model.getEmail());

		if (user == null) {
			System.out.println("注册用户=============新增");
			
			// 注册用户
			WebUser webuser = new WebUser(model.getEmail(), model.getName());
			webuser.setWebuserguid(UUIDGenerator.randomUUID());
			webuser.setMobile(model.getMobile());
			userdao.insertWebUser(webuser);

			// 保存
			saveResume(webuser, mapper, model, mycandidatesDao, flag, null);
		} else {
			List<Resume> list = mapper.getResumeByWebuserId(user.getWebuserguid());
			if (!list.isEmpty()) {
				flag = false;
				Resume resume = list.get(0);
				saveResume(user, mapper, model, mycandidatesDao, flag, resume);
			} else {
				saveResume(user, mapper, model, mycandidatesDao, flag, null);
			}

		}
	}

	/**
	 * 直接录用
	 * 
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateMycandidatesByIntervie(Resume model) {
		RecruitmentDao recruitmentDao = sqlSession.getMapper(RecruitmentDao.class);
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		WebUserDao userdao = sqlSession.getMapper(WebUserDao.class);
		MyCandidatesDao mycandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);

		WebUser user = userdao.checkWebUserByEmail(null, model.getEmail());

		if (user == null) {
			// 注册用户保存
			WebUser webuser = new WebUser(model.getEmail(), model.getName());
			webuser.setWebuserguid(UUIDGenerator.randomUUID());
			webuser.setMobile(model.getMobile());
			userdao.insertWebUser(webuser);

			// 保存简历
			model.setWebuserguid(webuser.getWebuserguid());
			model.setModtime(new Timestamp(System.currentTimeMillis()));
			model.setCreatetime(new Timestamp(System.currentTimeMillis()));
			mapper.insertResume(model);

			// 保存附件、应聘信息、推荐信息
			saveResumeByIntervie(model, mapper, mycandidatesDao, false, recruitmentDao);

		} else {
			Resume resume = mapper.getResumeById(user.getWebuserguid());
			if (resume != null) {
				// 修改简历
				model.setCreatetime(resume.getCreatetime());
				model.setWebuserguid(resume.getWebuserguid());
				model.setModtime(new Timestamp(System.currentTimeMillis()));
				mapper.updateResume(model);
			}

			// 保存附件以及应聘、推荐信息
			saveResumeByIntervie(model, mapper, mycandidatesDao, true, recruitmentDao);

		}

	}

	/**
	 * 保存附件、我的应聘、以及推荐信息
	 * 
	 * @param webuser
	 * @param mapper
	 * @param model
	 * @param myCandidatesDao
	 */
	@Transactional
	public void saveResumeByIntervie(Resume model, ResumeDao mapper, MyCandidatesDao myCandidatesDao, boolean flag, RecruitmentDao recruitmentDao) {
		// 是否有在流程中的应聘信息
		MyCandidates myCandidates = myCandidatesDao.getMyCandidatesByWebUserGuid(model.getWebuserguid());

		// 保存附件
		if (StringUtils.isNotEmpty(model.getResumefilename())) {
			String resumefileguid = UUIDGenerator.randomUUID();
			Timestamp filetime = new Timestamp(System.currentTimeMillis());
			ResumeFile resumeFile = new ResumeFile(resumefileguid, model.getWebuserguid(), model.getResumefilename(), model.getResumefilePath(), null, filetime);
			mapper.insertResumeFile(resumeFile);
		}

		if (myCandidates != null) {
			if (flag) {
				myCandidates.setCandidatesstate(Constance.CandidatesState_Seven);
				myCandidates.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				myCandidatesDao.updateMyCandidates(myCandidates);

				// 推荐信息忽略
				List<Recommend> list = myCandidatesDao.getRecommendByCandidatesGuid(myCandidates.getMycandidatesguid());

				if (!list.isEmpty()) {
					for (Recommend recommend : list) {
						recommend.setState(Constance.VALID_NO);
						myCandidatesDao.updateRecommend(recommend);
					}
				}

				// 保存推荐信息
				Recommend recommend = new Recommend();
				recommend.setRecommendguid(UUIDGenerator.randomUUID());
				recommend.setMycandidatesguid(myCandidates.getMycandidatesguid());
				recommend.setState(Constance.VALID_YES);
				recommend.setRecommendcompanyid(model.getRecommendcompanyid());
				recommend.setRecommenddeptid(model.getRecommenddeptid());
				recommend.setRecommendpostguid(model.getRecommendpostguid());
				recommend.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				myCandidatesDao.insertRecommend(recommend);
			}
		} else {
			// 无应聘信息(推荐之后的)做新增
			MyCandidates myCandidate = new MyCandidates();
			if (flag) {
				List<ResumeEamil> resumelist = mapper.getResumeEamilByWebUserId(model.getWebuserguid());
				if (!resumelist.isEmpty()) {
					ResumeEamil resumeEmail = resumelist.get(0);
					myCandidate.setResumeeamilguid(resumeEmail.getResumeeamilguid());
				}
			}

			myCandidate.setMycandidatesguid(UUIDGenerator.randomUUID());
			myCandidate.setCandidatestime(new Date(System.currentTimeMillis()));
			myCandidate.setCandidatesstate(Constance.CandidatesState_Seven);
			myCandidate.setWebuserguid(model.getWebuserguid());
			myCandidate.setCandidatestype(Constance.User2);
			myCandidate.setProgress(Constance.VALID_YES);
			myCandidate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			myCandidate.setReadtype(Constance.VALID_YES);
			myCandidatesDao.insertMyCandidates(myCandidate);

			// 保存推荐信息
			Recommend recommend = new Recommend();
			recommend.setRecommendguid(UUIDGenerator.randomUUID());
			recommend.setMycandidatesguid(myCandidate.getMycandidatesguid());
			recommend.setState(Constance.VALID_YES);
			recommend.setRecommendcompanyid(model.getRecommendcompanyid());
			recommend.setRecommenddeptid(model.getRecommenddeptid());
			recommend.setRecommendpostguid(model.getRecommendpostguid());
			recommend.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			myCandidatesDao.insertRecommend(recommend);
		}
	}

	/**
	 * 保存/修改简历、附件、我的应聘
	 * 
	 * @param webuser
	 * @param mapper
	 * @param model
	 * @param myCandidatesDao
	 */
	@Transactional
	public void saveResume(WebUser webuser, ResumeDao mapper, Resume model, MyCandidatesDao myCandidatesDao, boolean flag, Resume resume) {
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		if (flag) {
			// 保存简历
			model.setWebuserguid(webuser.getWebuserguid());
			model.setModtime(new Timestamp(System.currentTimeMillis()));
			model.setCreatetime(new Timestamp(System.currentTimeMillis()));
			mapper.insertResume(model);
		} else {
			// 修改简历
			model.setWebuserguid(resume.getWebuserguid());
			model.setModtime(new Timestamp(System.currentTimeMillis()));
			model.setCreatetime(new Timestamp(System.currentTimeMillis()));
			mapper.updateResume(model);
		}
		
		//保存亲属表
		List<Relatives> list = model.getRelatives();
		mapper.delRelativesByWebUserId(model.getWebuserguid());
		if (list != null && !list.isEmpty()) {
			for (Relatives relatives : list) {
				if(StringUtils.isNotEmpty(relatives.getCompanyname())){
					String employeename=relatives.getEmployeename();
					if(StringUtils.isNotEmpty(employeename)&&employeename.indexOf("(")>0){
						relatives.setEmployeename(employeename.substring(0,employeename.indexOf("(")));
					}
					relatives.setWebuserguid(model.getWebuserguid());
					relatives.setRelativesguid(UUIDGenerator.randomUUID());
					mapper.insertRelatives(relatives);
				}
			}
		}
		
		

		// 保存附件
		if (StringUtils.isNotEmpty(model.getResumefilename())) {
			String resumefileguid = UUIDGenerator.randomUUID();
			Timestamp filetime = new Timestamp(System.currentTimeMillis());
			ResumeFile resumeFile = new ResumeFile(resumefileguid, model.getWebuserguid(), model.getResumefilename(), model.getResumefilePath(), null, filetime);
			//resumeFile.setContent(model.getContent());
			mapper.insertResumeFile(resumeFile);
		}
		
		//保存邮箱简历
		if(StringUtils.isNotEmpty(model.getContent())){
			ResumeEamil resumeeamil=new ResumeEamil();
			resumeeamil.setResumeeamilguid(UUIDGenerator.randomUUID());
			resumeeamil.setInterfacecode(UUIDGenerator.randomUUID());
			resumeeamil.setWebuserguid(model.getWebuserguid());
			resumeeamil.setContent(model.getContent());
			resumeeamil.setReadtype(Constance.VALID_NO);
			resumeeamil.setEmail(model.getEmail());
			resumeeamil.setModtime(new Timestamp(System.currentTimeMillis()));
			resumeeamil.setSubject(Constance.WORDNAME);
			mapper.insertResumeEamil(resumeeamil);
		}
		

		// 保存我的应聘
		UserContext uc = ContextFacade.getUserContext();
		MyCandidates myCandidate = new MyCandidates();
		myCandidate.setMycandidatesguid(UUIDGenerator.randomUUID());
		if(StringUtils.isNotEmpty(model.getRecruitpostguid())){
			myCandidate.setRecruitpostguid(model.getRecruitpostguid());
		}
		myCandidate.setCandidatesstate(Constance.VALID_YES);
		myCandidate.setWebuserguid(webuser.getWebuserguid());
		myCandidate.setProgress(Constance.VALID_YES);
		myCandidate.setCandidatestime(new Date(System.currentTimeMillis()));
		myCandidate.setReadtype(Constance.VALID_NO);
		if(model.getCandidatestype()!=null){
			User dbuser = systemDao.getUserByUserId(uc.getUserId());
			if(dbuser!=null){
				myCandidate.setEmployeeid(dbuser.getEmployeeid());
			}
			myCandidate.setCandidatestype(model.getCandidatestype());
		}else{
			myCandidate.setCandidatestype(Constance.User2);
		}
		myCandidate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		myCandidatesDao.insertMyCandidates(myCandidate);
		
		
		
		//保存我的推荐(内部推荐)
		if(myCandidate.getCandidatestype().equals(Constance.User2)){
			InteriorRecommend interiorRecommend=new InteriorRecommend();
			interiorRecommend.setMycandidatesguid(myCandidate.getMycandidatesguid());
			interiorRecommend.setWebuserguid(model.getWebuserguid());
			interiorRecommend.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			interiorRecommend.setUserguid(uc.getUserId());
			mapper.insertInteriorRecommend(interiorRecommend);
		}
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Resume getResumeById(String id) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		Resume model = mapper.getResumeById(id);
		if (model != null) {
			model.convertOneCodeToName(optionDao, myCandidatesDao);
		}
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	public void delResumeyId(HttpServletRequest request, String ids) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delEducationExperienceByWebuserId(id);
			mapper.delProjectExperienceByWebuserId(id);
			mapper.delTrainingExperienceByWebuserId(id);
			mapper.delWorkExperienceByWebuserId(id);
			mapper.delResumeAssessByWebuserId(id);
			List<ResumeFile> listResumeFile = mapper.getAllResumeFileByWebuserId(id);
			if (!listResumeFile.isEmpty()) {
				for (ResumeFile model : listResumeFile) {
					ServletContext sc = request.getSession().getServletContext();
					String sourceFile = sc.getRealPath(model.getResumefilepath());
					File file = new File(sourceFile);
					file.delete();
				}
			}
			mapper.delResumeFileByWebuserId(id);
			mapper.delResumeById(id);
			myCandidatesDao.delMyCandidatesByUserId(id);
		}
	}

	/**
	 * 逻辑删除
	 * 
	 * @param ids
	 * @param param
	 */
	@Transactional
	public void updateResumeById(String ids, Integer mark) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);

		if (StringUtils.isNotEmpty(ids)) {
			String[] obj = ids.split(",");
			for (String resumeguid : obj) {
				Resume model = mapper.getResumeById(resumeguid);
				if (model != null) {
					model.setMark(mark);
					mapper.updateResume(model);
				}
			}
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Resume getResumeByUserId(String id) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		List<Resume> list = mapper.getResumeByWebuserId(id);
		if (!list.isEmpty()) {
			Resume model = list.get(0);
			if(model!=null){
				List<Relatives> relatives = mapper.getRelativesByWebUserId(model.getWebuserguid());
				model.setRelatives(relatives);
				model.convertOneCodeToName(optionDao, myCandidatesDao);
			}
			return model;
		}
		return null;
	}

	/**
	 * 根据名称得到
	 * 
	 * @param name
	 * @return
	 */
	public Resume getResumeByName(String name) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		List<Resume> list = mapper.getResumeByName(name);
		if (!list.isEmpty()) {
			Resume model = list.get(0);
			if(model!=null){
				List<Relatives> relatives = mapper.getRelativesByWebUserId(model.getWebuserguid());
				model.setRelatives(relatives);
				model.convertOneCodeToName(optionDao, myCandidatesDao);
			}
			return model;
		}
		return null;
	}

	/**
	 * 简历信息树
	 * 
	 * @param grid
	 * @return
	 */
	public List<Resume> searchResume(TreePageGrid grid) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		MyCandidatesDao myCandidatesDao = sqlSession.getMapper(MyCandidatesDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();

		// 统计
		Integer count = mapper.countResumeTree(grid);
		grid.getPage().setTotalrows(count);
		grid.getPage().init();

		// 搜索
		List<Resume> list = mapper.searchResumeTree(grid);
		// 批量
		if (list.size() > Constance.ConvertCodeNum) {
			// 来源
			Map<Integer, String> resumetypeMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.RESUMETYPE);
			// 最高学历
			Map<Integer, String> cultureMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
			// 性别
			Map<Integer, String> sexMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.SEX);
			// 工作年限
			Map<Integer, String> workageMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.WORKAGE);
			for (Resume model : list) {
				model.convertManyCodeToName(resumetypeMap, cultureMap, sexMap, workageMap, myCandidatesDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (Resume model : list) {
				model.convertOneCodeToName(optionDao, myCandidatesDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}

		return list;
	}

	
	
	/**
	 * 得到内部推荐信息
	 * 
	 * 
	 * @param mycandidatesguid
	 * @return
	 */
	public InteriorRecommend getInteriorRecommendById(String mycandidatesguid) {
		ResumeDao mapper = sqlSession.getMapper(ResumeDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		AddressListDao addressListDao = sqlSession.getMapper(AddressListDao.class);
		MyCandidatesDao myCandidatesDao=sqlSession.getMapper(MyCandidatesDao.class);
		InteriorRecommend model = mapper.getInteriorRecommendById(mycandidatesguid);
		if(model!=null){
			model.convertOneCodeToName(departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, optionDao,addressListDao,myCandidatesDao,mapper);
		}
		return model;
	}
}
