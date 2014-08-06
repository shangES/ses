package com.mk.todo.dao;

import java.sql.Date;
import java.util.List;

import com.mk.employee.entity.Employee;
import com.mk.framework.chart.ChartModel;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.person.entity.Person;
import com.mk.recruitprogram.entity.RecruitProgram;
import com.mk.recruitprogram.entity.RecruitProgramAudit;
import com.mk.todo.entity.TodoPam;

public interface TodoDao {

	/**
	 * 将要过生日的员工
	 * 
	 * @param day
	 * @return
	 */
	List<Employee> getEmployeeBirthdayTodo(TodoPam pam);

	/**
	 * 异动待生效的员工
	 * 
	 * @return
	 */
	List<Employee> getEmployeePosationTodo(TodoPam pam);

	/**
	 * 合同将要到期的员工
	 * 
	 * @return
	 */
	List<Employee> getEmployeeContractTodo(TodoPam pam);

	/**
	 * 待转正的员工
	 * 
	 * @return
	 */
	List<Employee> getEmployeeZhuZhengTodo(TodoPam pam);

	/**
	 * 待安排面试
	 * 
	 * @return
	 */
	List<MyCandidates> getInterviewTodo(TodoPam pam);

	/**
	 * 待安排的体检
	 * 
	 * @return
	 */
	List<MyCandidates> getExaminationTodo(TodoPam pam);

	/**
	 * 待入职的员工
	 * 
	 * @return
	 */
	List<Person> getEntryTodo(TodoPam pam);

	/**
	 * 面试人员列表
	 * 
	 * @return
	 */
	List<MyCandidates> getAuditionTodo(TodoPam pam);

	/**
	 * 面试结果
	 * 
	 * @param pam
	 * @return
	 */
	List<MyCandidates> getAuditionRecordTodo(TodoPam pam);

	/**
	 * 待发布的面试结果
	 * 
	 * 
	 * @param pam
	 * @return
	 */
	List<MyCandidates> getReleasesTodo(TodoPam pam);

	/**
	 * 待认定的面试
	 * 
	 * @param pam
	 * @return
	 */
	List<MyCandidates> getAffirmMyCandidatesTodo(TodoPam pam);

	/**
	 * 面試結果反饋
	 * 
	 * @param pam
	 * @return
	 */
	List<MyCandidates> getAffirmAuditionResultsTodo(TodoPam pam);

	/**
	 * 待确定入职的员工
	 * 
	 * @param pam
	 * @return
	 */
	List<MyCandidates> getEntryOkTodo(TodoPam pam);
	
	/**
	 * OA招聘计划结果
	 * 
	 * 
	 * @param pam
	 * @return
	 */
	List<RecruitProgramAudit> getRecruitProgramAuditTodo(TodoPam pam);

	
	/**
	 * 来源渠道统计
	 * 
	 * @param state
	 * @return
	 */
	List<ChartModel> loadCountMyCandidatesTypeChart();
	
	/**
	 * 编制情况统计
	 * 
	 * @param state
	 * @return
	 */
	List<ChartModel> loadmyBZQKChartBody();
	
	/**
	 * 占编情况统计
	 * 
	 * @param state
	 * @return
	 */
	List<ChartModel> loadmyZBQKChartBody();

	
	/**
	 * 求总投递数
	 * 
	 * @param pam
	 * @return
	 */
	Integer countAllMyCandidates(TodoPam pam);
	
	
	
	/**
	 * 求当日投递数
	 * 
	 * @param pam
	 * @return
	 */
	Integer countMyCandidatesByToday(TodoPam pam);
	
	
	
	/**
	 * 求当日推荐数
	 * 
	 * @param pam
	 * @return
	 */
	Integer countMyCandidatesByRecommend();

	
	/**
	 * 求最小的日期
	 * 
	 * @return
	 */
	Date getMinTime();

	/**
	 * 总的推荐数
	 * 
	 * 
	 * @return
	 */
	Integer countRecommends();
	
	/**
	 * 招聘计划统计图
	 * 
	 * @return
	 */
	List<ChartModel> loadmyRecuritprogramChartBody();
	
	/**
	 * 得到OA上拿的招聘计划
	 * 
	 * @param pam
	 * @return
	 */
	List<RecruitProgram> getRecruitProgram(TodoPam pam);
}
