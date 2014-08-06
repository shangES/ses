package com.mk.mycandidates.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.employee.BirthdayUtil;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.framework.grid.util.StringUtils;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.mycandidates.service.MyCandidatesService;
import com.mk.mycandidates.service.RecommendService;
import com.mk.todo.entity.TodoPam;

@Controller
public class MyCandidatesAction {
	@Autowired
	private MyCandidatesService myCandidatesService;
	
	@Autowired
	private RecommendService recommendservice;

	/**
	 * 搜索(简历管理)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/mycandidates/searchMyCandidatesAndResume.do")
	@ResponseBody
	public void searchMyCandidatesAndResume(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		// 年龄转生日=========
		BirthdayUtil.fromBirthdayToAge(grid);

		myCandidatesService.searchMyCandidatesAndResume(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("简历信息");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * 简历筛选
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/mycandidates/searchMycandidatesByFilter.do")
	@ResponseBody
	public void searchMycandidatesByFilter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		// 年龄转生日=========
		BirthdayUtil.fromBirthdayToAge(grid);

		myCandidatesService.searchMycandidatesByFilter(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("简历信息");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * 搜索(部门筛选)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/mycandidates/searchMyCandidatesAndResumeByDept.do")
	@ResponseBody
	public void searchMyCandidatesAndResumeByDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		// 年龄转生日=========
		BirthdayUtil.fromBirthdayToAge(grid);

		myCandidatesService.searchMyCandidatesAndResumeByDept(grid);
		grid.printLoadResponseText();
	}

	/**
	 * 搜索(部门简历)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/mycandidates/searchMyCandidatesByDept.do")
	@ResponseBody
	public void searchMyCandidatesByDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		// 年龄转生日=========
		BirthdayUtil.fromBirthdayToAge(grid);
		myCandidatesService.searchMyCandidatesByDept(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("简历信息");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * 搜索(安排面试的数据(根据招聘专员负责的部门)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/mycandidates/searchMycandidatesByAudition.do")
	@ResponseBody
	public void searchMycandidatesByAudition(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		myCandidatesService.searchMycandidatesByAudition(grid);
		grid.printLoadResponseText();
	}

	/**
	 * 待发布的面试情况
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/mycandidates/searchMycandidatesByResult.do")
	@ResponseBody
	public void searchMycandidatesByResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		myCandidatesService.searchMycandidatesByResult(grid);
		grid.printLoadResponseText();
	}

	/**
	 * 邀请他人来反馈面试结果(部门简历)
	 * 
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/mycandidates/searchMycandidatesByMainInterviewer.do")
	@ResponseBody
	public void searchMycandidatesByMainInterviewer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		myCandidatesService.searchMycandidatesByMainInterviewer(grid);
		grid.printLoadResponseText();
	}

	/**
	 * 面试人员列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/mycandidates/searchCandidatesPerson.do")
	@ResponseBody
	public void searchCandidatesPerson(HttpServletRequest request, HttpServletResponse response) {
		GridServerHandler grid = new GridServerHandler(request, response);
		myCandidatesService.searchCandidatesPerson(grid);
		grid.printLoadResponseText();
	}

	/**
	 * 修改应聘状态 （单个）
	 * 
	 * @param mycandidatesguid
	 * @param state
	 * @param recommendpostguid
	 * @param matchmemo
	 * @throws Exception
	 */
	@RequestMapping("/mycandidates/updateMyCandidatesByState.do")
	@ResponseBody
	public void updateMyCandidatesByState(String mycandidatesguid, Integer state, String matchmemo, String memo, String recommendguid) throws Exception {
		myCandidatesService.updateMyCandidatesByState(mycandidatesguid, state, matchmemo, memo, recommendguid);
	}

	/**
	 * 修改应聘状态 （批量）
	 * 
	 * @param ids
	 * @param state
	 * @param recommendpostguid
	 * @param matchmemo
	 * @param recommendcompanyid
	 * @param recommenddeptid
	 * @throws Exception
	 */
	@RequestMapping("/mycandidates/updateBatchCandidatesstate.do")
	@ResponseBody
	public void updateBatchCandidatesstate(String ids, Integer state, String thirdpartnerguid,Integer isemail,Integer ismsg) throws Exception {
		myCandidatesService.updateBatchCandidatesstate(ids, state, thirdpartnerguid,isemail,ismsg);
	}

	/**
	 * 得到数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/mycandidates/getMyCandidatesById.do")
	@ResponseBody
	public MyCandidates getMyCandidatesById(String id) {
		return myCandidatesService.getMyCandidatesById(id);
	}

	/**
	 * 得到数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/mycandidates/getMyCandidatesByIdAndRecommendGuid.do")
	@ResponseBody
	public MyCandidates getMyCandidatesByIdAndRecommendGuid(String id, String recommendguid) {
		return myCandidatesService.getMyCandidatesByIdAndRecommendGuid(id, recommendguid);
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	@RequestMapping("/mycandidates/delMyCandidatesById.do")
	@ResponseBody
	public void delMyCandidatesById(String ids) {
		myCandidatesService.delMyCandidatesById(ids);
	}

	/**
	 * 修改mark
	 * 
	 * @param ids
	 * @param state
	 */
	@RequestMapping("/mycandidates/updateResumeMarkById.do")
	@ResponseBody
	public void updateResumeMarkById(String ids, Integer state) {
		myCandidatesService.updateResumeMarkById(ids, state);
	}

	/**
	 * 验证是否在面试之后的流程下
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/mycandidates/checkMyCandidatesByUserGuid.do")
	@ResponseBody
	public String checkMyCandidatesByUserGuid(String userguid, Integer state) throws Exception {
		return myCandidatesService.checkMyCandidatesByUserGuid(userguid, state);
	}

	/**
	 * 搜索(部门简历待面试结果)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/mycandidates/searchMyCandidatesByState.do")
	@ResponseBody
	public void searchMyCandidatesByState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		// 年龄转生日=========
		BirthdayUtil.fromBirthdayToAge(grid);

		myCandidatesService.searchMyCandidatesByState(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("简历信息");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * 批量保存(安排面试)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/mycandidates/saveAuditionGrid.do")
	public void saveAuditionGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		myCandidatesService.saveAuditionGrid(grid);
		grid.printSaveResponseText();
	}

	
	
	
	
	/**
	 * 保存多人推荐
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/mycandidates/saveRcommendGrid.do")
	public void saveRcommendGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		String msg=myCandidatesService.saveRcommendGrid(grid);
		if (StringUtils.isNotEmpty(msg))
			grid.printResponseText(msg);
		//grid.printSaveResponseText();
	}
	
	
	
	/**
	 * 批量发布
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/mycandidates/saveAuditionResultGrid.do")
	public void saveAuditionResultGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		myCandidatesService.saveAuditionResultGrid(grid);
		grid.printSaveResponseText();
	}

	/**
	 * 批量修改面试官
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/mycandidates/saveAuditionMainInterviewer.do")
	public void saveAuditionMainInterviewer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		myCandidatesService.saveAuditionMainInterviewer(grid);
		grid.printSaveResponseText();
	}

	/**
	 * 标记为已读
	 * 
	 * @param ids
	 */
	@RequestMapping("/mycandidates/updateByReadtype.do")
	@ResponseBody
	public void updateByReadtype(String ids, Integer readtype) {
		myCandidatesService.updateByReadtype(ids, readtype);
	}

	/**
	 * 验证是否取消 判断是否还在操作的下个流程中
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/mycandidates/checkMyCandidatesByState.do")
	@ResponseBody
	public MyCandidates checkMyCandidatesByState(String id, Integer state) throws Exception {
		return myCandidatesService.checkMyCandidatesByState(id, state);
	}

	/**
	 * 我的应聘操作历史搜索
	 * 
	 * @param mycandidatesguid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mycandidates/searchMyCandidatesHistoryByMyCandidatesGuid.do")
	@ResponseBody
	public void searchMyCandidatesHistoryByMyCandidatesGuid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		myCandidatesService.searchMyCandidatesHistoryByMyCandidatesGuid(grid);
		grid.printLoadResponseText();
	}

	/**
	 * 人才库推荐
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("mycandidates/saveMyCandidateAndRecommend.do")
	@ResponseBody
	public MyCandidates saveMyCandidateAndRecommend(MyCandidates model) throws Exception {
		myCandidatesService.saveMyCandidateAndRecommend(model);
		return model;
	}

	/**
	 * 邀请他人反馈面试结果（简历管理下）搜索
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/mycandidates/searchMycandidatesByMainInterviewerList.do")
	@ResponseBody
	public void searchMycandidatesByMainInterviewerList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		myCandidatesService.searchMycandidatesByMainInterviewerList(grid);
		grid.printLoadResponseText();
	}
	
	
	
	/**
	 * 校验是否有对应职位产生的应聘信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/mycandidates/checkMyCandidatesByRecruitpostGuid.do")
	@ResponseBody
	public String checkMyCandidatesByRecruitpostGuid(String ids) {
		return myCandidatesService.checkMyCandidatesByRecruitpostGuid(ids);
	}
	
	/**
	 * 得到链接oa的部门简历界面
	 * 
	 * 
	 * @param id
	 * @param recommendguid
	 * @return
	 */
	@RequestMapping("/mycandidates/getMyDptCandidatesByUserGuid.do")
	@ResponseBody
	public List<MyCandidates> getMyDptCandidatesByUserGuid(TodoPam pam) {
		return myCandidatesService.getMyDptCandidatesByUserGuid(pam);
	}
	
	/**
	 * 退回
	 * 
	 * @param id
	 * @param recommendguid
	 * @throws Exception 
	 */
	@RequestMapping("/mycandidates/sendback.do")
	@ResponseBody
	public void sendback(String id,String recommendguid) throws Exception{
		myCandidatesService.sendback(id,recommendguid);
	}

	
	
	
		
	/**
	 * 验证推荐是否过期(两天锁定)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/mycandidates/checkMyCandidatesByCandidatesGuid.do")
	@ResponseBody
	public String checkMyCandidatesByCandidatesGuid(String mycandidatesguid) throws Exception {
		return myCandidatesService.checkMyCandidatesByCandidatesGuid(mycandidatesguid);
	}
	
	/**
	 * 直接体检通过
	 * 
	 * @param ids
	 */
	@RequestMapping("/mycandidates/examinationMyCandidatesById.do")
	@ResponseBody
	public void examinationMyCandidatesById(String ids) {
		myCandidatesService.examinationMyCandidatesById(ids);
	}
	
	/**
	 * 自检通过
	 * 
	 * @param ids
	 */
	@RequestMapping("/mycandidates/examinationMyCandidates.do")
	@ResponseBody
	public void examinationMyCandidates(String id,String filepath,String recommenid,Integer examinationtype) {
		myCandidatesService.examinationMyCandidates(id,filepath,recommenid,examinationtype);
	}
	
}
