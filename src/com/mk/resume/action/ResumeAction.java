package com.mk.resume.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mk.audition.entity.AuditionRecord;
import com.mk.audition.service.AuditionRecordService;
import com.mk.employee.BirthdayUtil;
import com.mk.framework.constance.Constance;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.spring.SpringContextHolder;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.tree.TreePageGrid;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.mycandidates.entity.Recommend;
import com.mk.mycandidates.service.MyCandidatesService;
import com.mk.mycandidates.service.RecommendService;
import com.mk.person.entity.ExaminationRecord;
import com.mk.person.service.ExaminationRecordService;
import com.mk.recruitment.service.RecruitPostService;
import com.mk.resume.entity.EducationExperience;
import com.mk.resume.entity.InteriorRecommend;
import com.mk.resume.entity.ProjectExperience;
import com.mk.resume.entity.Resume;
import com.mk.resume.entity.ResumeAssess;
import com.mk.resume.entity.ResumeEamilFile;
import com.mk.resume.entity.ResumeFile;
import com.mk.resume.entity.TrainingExperience;
import com.mk.resume.entity.WorkExperience;
import com.mk.resume.service.EducationExperienceService;
import com.mk.resume.service.ProjectExperienceService;
import com.mk.resume.service.ResumeAssessService;
import com.mk.resume.service.ResumeEamilService;
import com.mk.resume.service.ResumeFileService;
import com.mk.resume.service.ResumeService;
import com.mk.resume.service.TrainingExperienceService;
import com.mk.resume.service.WorkExperienceService;
import com.mk.resume.tree.ResumeListTree;

@Controller
public class ResumeAction {
	@Autowired
	private ResumeService resumeService;
	@Autowired
	private EducationExperienceService educationExperienceService;
	@Autowired
	private ProjectExperienceService projectExperienceService;
	@Autowired
	private TrainingExperienceService trainingExperienceService;
	@Autowired
	private WorkExperienceService workExperienceService;
	@Autowired
	private ResumeFileService resumeFileService;
	@Autowired
	private MyCandidatesService myCandidatesService;
	@Autowired
	private RecommendService recommendService;
	@Autowired
	private ResumeAssessService resumeAssessService;
	@Autowired
	private AuditionRecordService auditionRecordService;
	@Autowired
	private ExaminationRecordService examinationRecordService;
	@Autowired
	private ResumeEamilService resumeEamilService;
	@Autowired
	private RecruitPostService recruitPostService;

	/**
	 * 得到招聘专员树
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/buildRecruiterTree.do")
	@ResponseBody
	public List<TreeNode> buildRecruiterTree() throws Exception {
		return resumeService.buildRecruiterTree();
	}

	/**
	 * 得到职务树
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resume/buildPostNameTree.do")
	@ResponseBody
	public List<TreeNode> buildPostNameTree(String userguid) throws Exception {
		return resumeService.buildPostNameTree(userguid);
	}

	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/searchResume.do")
	@ResponseBody
	public void searchResume(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		// 年龄转生日=========
		BirthdayUtil.fromBirthdayToAge(grid);
		resumeService.searchResume(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("简历信息");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();

	}
	
	/**
	 * 批量保存(简历)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/saveResumeGrid.do")
	public void saveResumeGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		String msg = resumeService.saveResumeGrid(grid);
		if (StringUtils.isNotEmpty(msg))
			grid.printResponseText(msg);
	}
	
	/**
	 * 批量保存(简历筛选)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/saveResumeGridByFiter.do")
	public void saveResumeGridByFiter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		String msg = resumeService.saveResumeGridByFiter(grid);
		if (StringUtils.isNotEmpty(msg))
			grid.printResponseText(msg);
	}
	

	/**
	 * 保存or修改选项类型
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/resume/saveOrUpdateResume.do")
	@ResponseBody
	public Resume saveOrUpdateResume(HttpServletRequest request,Resume model) {
		resumeService.saveOrUpdateResume(request,model);
		return model;
	}

	/**
	 * 保存内部推荐的
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/resume/saveOrUpdateRecommendResume.do")
	@ResponseBody
	public Resume saveOrUpdateRecommendResume(Resume model) {
		resumeService.saveOrUpdateRecommendResume(model);
		return model;
	}

	/**
	 * 直接录用
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/resume/saveOrUpdateMycandidatesByIntervie.do")
	@ResponseBody
	public Resume saveOrUpdateMycandidatesByIntervie(Resume model) {
		resumeService.saveOrUpdateMycandidatesByIntervie(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/resume/getResumeById.do")
	@ResponseBody
	public Resume getResumeById(HttpServletRequest request, String id) throws IOException {
		Resume model = resumeService.getResumeById(id);
		if(model != null){
			String str = this.changePath(request, model.getPhoto());
			model.setPhoto(str);
		}
		return model;
	}

	/**
	 * 物理删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/resume/delResumeById.do")
	@ResponseBody
	public void delResumeById(HttpServletRequest request, String ids) throws Exception {
		resumeService.delResumeyId(request, ids);
	}

	/**
	 * 逻辑删除
	 * 
	 * @param ids
	 * @param param
	 */
	@RequestMapping("/resume/updateResumeById.do")
	@ResponseBody
	public void updateResumeById(String ids, Integer mark) {
		resumeService.updateResumeById(ids, mark);
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/resume/getResumeByUserId.do")
	@ResponseBody
	public Resume getResumeByUserId(String id) {
		Resume model = resumeService.getResumeByUserId(id);
		return model;
	}

	/**
	 * 我的简历预览
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resume/getResumeStaticById.do")
	public ModelAndView getResumeStaticById(HttpServletRequest request,ModelMap map, String id, String mycandidatesguid) throws Exception {
		if (StringUtils.isNotEmpty(id)) {
			Resume model = resumeService.getResumeById(id);
			if(model != null){
				String str = this.changePath(request, model.getPhoto());
				model.setPhoto(str);
			}
			List<EducationExperience> educationexperiences = educationExperienceService.getEducationExperienceListByResumeGuid(id);
			List<ProjectExperience> projectexperiences = projectExperienceService.getProjectExperienceListByResumeGuid(id);
			List<TrainingExperience> trainingexperiences = trainingExperienceService.getTrainingExperienceListByResumeGuid(id);
			List<WorkExperience> workexperiences = workExperienceService.getWorkExperienceListByResumeGuid(id);
			List<ResumeFile> resumefiles = resumeFileService.getResumeFileListByResumeGuid(id);

			// 投递历史记录
			List<MyCandidates> mycandidateslist = myCandidatesService.getMyCandidatesByWebUserGuid(id, mycandidatesguid);
			map.put("mycandidateslist", mycandidateslist);

			// 评价信息
			List<ResumeAssess> assesslist = resumeAssessService.getResumeAssessByWebUserId(id);
			if (!assesslist.isEmpty()) {
				map.put("assesslist", assesslist);
				map.put("assesslistsize", assesslist.size());
			} else {
				map.put("assesslistsize", 0);
			}

			// 应聘信息
			if (StringUtils.isNotEmpty(mycandidatesguid)) {
				MyCandidates mycandidates = myCandidatesService.getMyCandidatesById(mycandidatesguid);
				map.put("mycandidates", mycandidates);
				
				
				
				//内部推荐人
				InteriorRecommend interiorRecommend=resumeService.getInteriorRecommendById(mycandidatesguid);
				if(interiorRecommend!=null){
					map.put("interiorRecommend", interiorRecommend);
				}
				
				
				// 推荐信息
				List<Recommend> recommends = recommendService.getRecomendListByCandidatesGuid(mycandidatesguid);
				if (!recommends.isEmpty()) {
					for(Recommend recommend : recommends){
						List<AuditionRecord> auditionrecord = auditionRecordService.getAuditionRecordAndResultByRecommendGuid(recommend.getRecommendguid());
						List<ExaminationRecord> examinationrecord =examinationRecordService.getExaminationsByRecommendGuid(recommend.getRecommendguid());
						recommend.setRecordResult(auditionrecord);
						recommend.setExaminations(examinationrecord);
					}
					
					map.put("recommends", recommends);
					map.put("recommendsize", recommends.size());
				} else {
					map.put("recommendsize", 0);
				}
			}

			map.put("assesslist", assesslist);
			map.put("educationexperiences", educationexperiences);
			map.put("projectexperiences", projectexperiences);
			map.put("trainingexperiences", trainingexperiences);
			map.put("workexperiences", workexperiences);
			map.put("resumefiles", resumefiles);
			map.put("resume", model);

		}
		return new ModelAndView("/pages/resume/static.jsp");
	}
	
	/**
	 * 简历打印
	 * 
	 * @param map
	 * @param id
	 * @return
	 */
	@RequestMapping("/resume/resumePrint.do")
	public ModelAndView resumePrint(ModelMap map , String id){
		if (StringUtils.isNotEmpty(id)) {
			Resume model = resumeService.getResumeById(id);
			List<EducationExperience> educationexperiences = educationExperienceService.getEducationExperienceListByResumeGuid(id);
			List<ProjectExperience> projectexperiences = projectExperienceService.getProjectExperienceListByResumeGuid(id);
			List<TrainingExperience> trainingexperiences = trainingExperienceService.getTrainingExperienceListByResumeGuid(id);
			List<WorkExperience> workexperiences = workExperienceService.getWorkExperienceListByResumeGuid(id);
			List<ResumeFile> resumefiles = resumeFileService.getResumeFileListByResumeGuid(id);
			
			map.put("educationexperiences", educationexperiences);
			map.put("projectexperiences", projectexperiences);
			map.put("trainingexperiences", trainingexperiences);
			map.put("workexperiences", workexperiences);
			map.put("resumefiles", resumefiles);
			map.put("resume", model);
		}
		return new ModelAndView("/pages/resume/print.jsp");
	}

	/**
	 * 根据名称得到
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/resume/getResumeByName.do")
	@ResponseBody
	public Resume getResumeByName(String name) {
		Resume model = resumeService.getResumeByName(name);
		return model;
	}

	/**
	 * 文件的下载
	 * 
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/resume/DownLoadresumeeamilfile.do")
	public void DownLoadresumeeamilfile(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResumeEamilFile model = resumeEamilService.getResumeEamilFileById(id);
		if (model == null) {
			response.sendError(404, "File not found!");
			return;
		}

		ServletContext sc = request.getSession().getServletContext();
		String sourceFile = sc.getRealPath(model.getFilepath());
		File f = new File(sourceFile);
		if (!f.exists()) {
			response.sendError(404, "File not found!");
			return;
		}

		// 设置输出的格式
		response.reset();
		response.setContentType("bin");
		String filename = model.getFilename();

		String bmfileName = new String(filename.getBytes("GBK"), "ISO8859_1");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + bmfileName + "\"");
		// 循环取出流中的数据
		byte[] b = new byte[102400];
		int len;
		FileInputStream in = new FileInputStream(f);
		while ((len = in.read(b)) > 0) {
			response.getOutputStream().write(b, 0, len);
		}
		in.close();
	}

	/**
	 * 投递记录
	 * 
	 * @param map
	 * @param recruitpostguid
	 * @param webuserguid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resume/getResumeHistoryById.do")
	public ModelAndView getResumeHistoryById(ModelMap map, String mycandidatesguid) throws Exception {
		if (StringUtils.isNotEmpty(mycandidatesguid)) {
			// 推荐记录
			List<Recommend> recommendslist = recommendService.getRecomendListByCandidatesGuid(mycandidatesguid);
			map.put("recommendslist", recommendslist);

			// 面试记录
			List<AuditionRecord> auditionRecordslist = auditionRecordService.getAuditionRecordAndResultByMycandidatesguid(mycandidatesguid);
			map.put("auditionRecordslist", auditionRecordslist);

			// 体检记录
			List<ExaminationRecord> examinationRecordslist = examinationRecordService.getExaminationsByMyCandidatesid(mycandidatesguid);
			map.put("examinationRecordslist", examinationRecordslist);

			return new ModelAndView("/pages/resume/form_history.jsp");

		}

		return null;
	}

	/**
	 * 简历查看悬浮框
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/resume/getResumeByWebuserGuid.do")
	@ResponseBody
	public ModelMap getResumeByWebuserGuid(HttpServletRequest request,ModelMap map, String webuserguid) throws Exception {

		// 简历信息
		Resume resume = resumeService.getResumeById(webuserguid);
		if(resume != null){
			String str = this.changePath(request, resume.getPhoto());
			resume.setPhoto(str);
		}
		map.put("resume", resume);

		// 工作经历
		List<WorkExperience> workexperiences = workExperienceService.getWorkExperienceListByResumeGuid(webuserguid);
		map.put("workexperiences", workexperiences);

		// 评价信息
		List<ResumeAssess> assesslists = resumeAssessService.getResumeAssessByWebUserId(webuserguid);
		map.put("assesslists", assesslists);

		return map;
	}

	/**
	 * 人才库查看简历
	 * 
	 * @param map
	 * @param webuserguid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resume/getMyStaticResumeByWebuserguid.do")
	public ModelAndView getMyStaticResumeByWebuserguid(HttpServletRequest request,ModelMap map, String webuserguid) throws Exception {
		if (StringUtils.isNotEmpty(webuserguid)) {
			Resume model = resumeService.getResumeById(webuserguid);
			if(model != null){
				String str = this.changePath(request, model.getPhoto());
				model.setPhoto(str);
			}
			List<EducationExperience> educationexperiences = educationExperienceService.getEducationExperienceListByResumeGuid(webuserguid);
			List<ProjectExperience> projectexperiences = projectExperienceService.getProjectExperienceListByResumeGuid(webuserguid);
			List<TrainingExperience> trainingexperiences = trainingExperienceService.getTrainingExperienceListByResumeGuid(webuserguid);
			List<WorkExperience> workexperiences = workExperienceService.getWorkExperienceListByResumeGuid(webuserguid);

			map.put("educationexperiences", educationexperiences);
			map.put("projectexperiences", projectexperiences);
			map.put("trainingexperiences", trainingexperiences);
			map.put("workexperiences", workexperiences);
			map.put("resume", model);

			return new ModelAndView("/pages/resume/static_resume.jsp");
		}
		return null;
	}
	
	/**
	 * 选择图片下载路径，不在内网，到外网查找
	 * 
	 * @param request
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String changePath(HttpServletRequest request,String path) throws IOException{
		String tempPath = "";
		if(StringUtils.isEmpty(path))
			return null;
		//例子：http://localhost:port/project/filepath
		//  http://localhost:port
		String physicalpath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		String realphysicalpath = SpringContextHolder.getApplicationContext().getResource("/").getFile().getAbsolutePath() + "/";
		//file.toString===http://localhost:port/project/filepath
		File file = new File(realphysicalpath+path);
		if(file.exists()){
			//内网路径查找
			//项目名：/project/
			String filepath = physicalpath+"/"+request.getSession().getServletContext().getContextPath()+"/";
			tempPath = filepath+path;
		}else{
			//外网
			String outerfilepath = realphysicalpath.replaceAll(request.getSession().getServletContext().getContextPath().substring(1, request.getSession().getServletContext().getContextPath().length()), Constance.PROJECTNAMEOUTER);
			File outerfile = new File(outerfilepath+path);
			if(outerfile.exists()){
				tempPath = physicalpath+"/"+Constance.PROJECTNAMEOUTER+"/"+path;
			}else
				tempPath=null;
		}
		return tempPath;
	}

	/**
	 * 简历信息树
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resume/buildResumesListTree.do")
	@ResponseBody
	public TreePageGrid buildResumesListTree(@RequestBody TreePageGrid grid) {
		List<Resume> data = resumeService.searchResume(grid);
		ResumeListTree tree = new ResumeListTree();
		List<TreeNode> nodes = tree.doBuild(data);
		grid.setDataList(nodes);
		return grid;
	}
}
