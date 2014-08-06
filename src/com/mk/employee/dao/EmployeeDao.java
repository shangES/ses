package com.mk.employee.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.department.entity.Post;
import com.mk.employee.entity.Annual;
import com.mk.employee.entity.Certification;
import com.mk.employee.entity.Eduexperience;
import com.mk.employee.entity.Employee;
import com.mk.employee.entity.Family;
import com.mk.employee.entity.HrRecommend;
import com.mk.employee.entity.HrRelatives;
import com.mk.employee.entity.Position;
import com.mk.employee.entity.Train;
import com.mk.employee.entity.Vacation;
import com.mk.employee.entity.Workexperience;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.tree.TreePageGrid;
import com.mk.webservice.entity.WSEmployee;

public interface EmployeeDao {

	// ==================员工信息==================
	/**
	 * 统计
	 */
	Integer countEmployee(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Employee> searchEmployee(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertEmployee(Employee model);

	/**
	 * 更新
	 * 
	 * @param model
	 */
	void updateEmployee(Employee model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Employee getEmployeeById(String employeeid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delEmployeeById(String employeeid);

	/**
	 * 身份证统计
	 * 
	 * @param grid
	 * @return
	 */
	int countEmployeeTree(TreePageGrid grid);

	/**
	 * 身份证搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Employee> searchEmployeeTree(TreePageGrid grid);

	/**
	 * 用户统计
	 * 
	 * @param grid
	 * @return
	 */
	int countUserTree(TreePageGrid grid);

	/**
	 * 用户搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Employee> searchUserTree(TreePageGrid grid);

	/**
	 * 得到
	 * 
	 * @return
	 */
	List<Employee> getEmployeeByJobnumber(@Param(value = "employeeid") String employeeid, @Param(value = "jobnumber") String jobnumber);

	/**
	 * 得到
	 * 
	 * @param employeeid
	 * @param cardnumber
	 * @return
	 */
	List<Employee> getEmployeeByCardnumber(@Param(value = "employeeid") String employeeid, @Param(value = "cardnumber") String cardnumber);

	/**
	 * 得到所有的
	 * 
	 * @return
	 */
	List<Employee> getAllEmployee();

	// =================员工任职=====================

	/**
	 * 统计
	 */
	Integer countPosition(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Position> searchPosition(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertPosition(Position model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updatePosition(Position model);

	/**
	 * 删除
	 * 
	 * @param postionguid
	 */
	void delPositionById(String postionguid);

	/**
	 * 删除
	 * 
	 * @param employeeid
	 */
	void delPositionByEmployeeId(String employeeid);

	/**
	 * 得到
	 * 
	 * @param postionguid
	 * @return
	 */
	Position getPositionById(String postionguid);

	/**
	 * 得到
	 * 
	 * @param employeeid
	 * @param timestamp
	 * @return
	 */
	List<Position> getPositionByEmployeeIdAndIsstaff(String employeeid);

	/**
	 * 得到
	 * 
	 * @param deptcode
	 * @return
	 */
	List<Position> getPositionByDeptCode(String deptcode);

	/**
	 * 校验索引
	 * 
	 * @param employeeid
	 * @param companyid
	 * @param deptid
	 * @param postid
	 * @return
	 */
	List<Position> checkIndexCompanyDeptPost(Position model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Position getPositionByUserId(@Param(value = "userguid") String userguid);

	// =================教育經歷=====================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countEduexperience(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Eduexperience> searchEduexperience(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertEduexperience(Eduexperience model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateEduexperience(Eduexperience model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Eduexperience getEduexperienceById(String eduexperienceid);

	/**
	 * 得到
	 * 
	 * @param employeeid
	 * @return
	 */
	List<Eduexperience> getEduexperienceByEmployeeId(String employeeid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delEduexperienceById(String eduexperienceid);

	/**
	 * 删除
	 * 
	 * @param employeeid
	 */
	void delEduexperienceByEmployeeId(String employeeid);

	// =================培训经历====================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countTrain(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Train> searchTrain(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertTrain(Train model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateTrain(Train model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Train getTrainById(String trainid);

	/**
	 * 得到
	 * 
	 * @param employeeid
	 * @return
	 */
	List<Train> getTrainByEmployeeId(String employeeid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delTrainById(String trainid);

	/**
	 * 删除
	 * 
	 * @param employeeid
	 */
	void delTrainByEmployeeId(String employeeid);

	// =================职称认证====================
	/**
	 * 所有的
	 * 
	 * @return
	 */
	List<Certification> getAllCertification();

	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countCertification(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Certification> searchCertification(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertCertification(Certification model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateCertification(Certification model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Certification getCertificationById(String certificationid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delCertificationById(String certificationid);

	/**
	 * 删除
	 * 
	 * @param employeeid
	 */
	void delCertificationByEmployeeId(String employeeid);

	// =================工作经历====================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countWorkexperience(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Workexperience> searchWorkexperience(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertWorkexperience(Workexperience model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateWorkexperience(Workexperience model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Workexperience getWorkexperienceById(String workexperienceid);

	/**
	 * 得到
	 * 
	 * @param employeeid
	 * @return
	 */
	List<Workexperience> getWorkexperienceByEmployeeId(String employeeid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delWorkexperienceById(String workexperienceid);

	/**
	 * 删除
	 * 
	 * @param employeeid
	 */
	void delWorkexperienceByEmployeeId(String employeeid);

	// ======================员工请假=====================
	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Vacation> searchVacation(GridServerHandler grid);

	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countVacation(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertVacation(Vacation model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateVacation(Vacation model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delVacation(String id);

	/**
	 * 得打
	 * 
	 * @param id
	 * @return
	 */
	Vacation getVacationById(String id);

	// =================家属状况====================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countFamily(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Family> searchFamily(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertFamily(Family model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateFamily(Family model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Family getFamilyById(String familyid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delFamilyById(String familyid);

	/**
	 * 删除
	 * 
	 * @param employeeid
	 */
	void delFamilyByEmployeeId(String employeeid);

	// =================员工公司亲属关系===================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countHrRelatives(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<HrRelatives> searchHrRelatives(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param hrRelatives
	 */
	void insertHrRelatives(HrRelatives hrRelatives);

	/**
	 * 修改
	 * 
	 * @param hrRelatives
	 */
	void updateHrRelatives(HrRelatives hrRelatives);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	HrRelatives getHrRelativesById(String relativesguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delHrRelativesById(String relativesguid);

	/**
	 * 删除
	 * 
	 * @param employeeid
	 */
	void delHrRelativesByEmployeeId(String employeeid);

	// =================推荐信息===================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countHrRecommend(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<HrRecommend> searchHrRecommend(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param hrRelatives
	 */
	void insertHrRecommend(HrRecommend model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delHrRecommendById(String recommendguid);

	/**
	 * 删除
	 * 
	 * @param employeeid
	 */
	void delHrRecommendByEmployeeId(String employeeid);

	/**
	 * 总条数
	 * 
	 * @return
	 */
	Integer countPostTree(TreePageGrid grid);

	/**
	 * 岗位
	 * 
	 * @param postname
	 * @return
	 */
	List<Post> getPostByName(@Param(value = "postname") String postname);

	/**
	 * 
	 * 
	 * @param grid
	 * @return
	 */
	List<String> searchPostTree(TreePageGrid grid);

	// =================后面加的===================
	/**
	 * 根据员工号查询
	 * 
	 * @param jbonum
	 * @return
	 */
	Position getEmployeeByJobnum(@Param(value = "jobnum") String jobnum);

	/**
	 * 
	 * 得到入职推荐的推荐人且是未发邮件和短信的
	 * 
	 * @param nowdate
	 * @return
	 */
	List<HrRecommend> getHrRecommendByInduction(String nowdate);

	/**
	 * 
	 * 得到入职推荐的证明人且是未发邮件和短信的
	 * 
	 * @param nowdate
	 * @return
	 */
	List<Workexperience> getWorkexperienceByInduction(String nowdate);

	/**
	 * 得到
	 * 
	 * @param companyid
	 * @param deptid
	 * @param postid
	 * @return
	 */
	List<WSEmployee> getWSEmployeeList(@Param(value = "jobnumber") String jobnumber);

	/**
	 * 得到
	 * 
	 * @param postid
	 * @return
	 */
	List<WSEmployee> getWSEmployeeListByPostId(@Param(value = "postid") String postid);

	/**
	 * 得到
	 * 
	 * @param jobnumber
	 * @return
	 */
	List<WSEmployee> getWSEmployeeListByDepartment(@Param(value = "deptid") String deptid);

	// ======================员工年休假=====================
	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Annual> searchAnnual(GridServerHandler grid);

	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countAnnual(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertAnnual(Annual model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateAnnual(Annual model);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delAnnualById(String id);

	
	/**
	 * 删除
	 * 
	 * @param employeeid
	 */
	void delAnnualByEmployeeId(String employeeid);
	
	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Annual getAnnualById(String id);
	
	
	/**
	 * 求已休假数
	 * 
	 * @param employeeid
	 * @return
	 */
	double countdaysByEmployeeIdForAnnual(@Param(value = "employeeid") String employeeid,@Param(value = "year") String year);
	
	/**
	 * 得到
	 * 
	 * @param employeeid
	 * @param year
	 * @return
	 */
	Annual getAnnualByEmployeeIdAndYear(@Param(value = "employeeid") String employeeid,@Param(value = "year") Integer year,@Param(value = "annualguid") String annualguid);
}
