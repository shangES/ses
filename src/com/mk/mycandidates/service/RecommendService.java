package com.mk.mycandidates.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.addresslist.dao.AddressListDao;
import com.mk.addresslist.entity.AddressList;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
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
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.mycandidates.dao.MyCandidatesDao;
import com.mk.mycandidates.entity.Bussiness;
import com.mk.mycandidates.entity.MyCandidates;
import com.mk.mycandidates.entity.MyCandidatesHistory;
import com.mk.mycandidates.entity.Recommend;
import com.mk.quota.dao.QuotaDao;
import com.mk.resume.dao.ResumeDao;
import com.mk.resume.entity.Resume;
import com.mk.system.dao.OptionDao;
import com.mk.system.dao.SystemDao;
import com.mk.system.entity.OptionList;
import com.mk.system.entity.Role;
import com.mk.system.entity.UserManageRange;
import com.mk.system.entity.UserRole;

@Service
public class RecommendService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CodeConvertNameService codeConvertNameService;
	@Autowired
	private MyCandidatesService myCandidatesService;
	@Autowired
	private MailSendService mailSendService;
	@Autowired
	private SendMessageService sendMessageService;

	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional
	public void searchRecommend(GridServerHandler grid) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		List<JSONObject> data = new ArrayList<JSONObject>();

		// 统计
		Integer count = mapper.countRecommend(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<Recommend> list = mapper.searchRecommend(grid);

		if (list.size() > Constance.ConvertCodeNum) {
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部門
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();
			// 崗位
			Map<String, Post> postMap = codeConvertNameService.getAllPostMap();
			// 用户列表
			Map<String, String> userMap = codeConvertNameService.getAllUserMap();

			for (Recommend model : list) {
				model.convertManyCodeToName(companyMap, deptMap, postMap, userMap);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		} else {
			for (Recommend model : list) {
				model.convertOneCodeToName(departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, optionDao);
				data.add(JSONUtils.Bean2JSONObject(model));
			}
		}
		grid.setData(data);
	}

	/**
	 * 保存
	 * 
	 * @param model
	 * @throws Exception
	 */
	@Transactional
	public void saveOrUpdateRecommend(Recommend model) throws Exception {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		UserContext uc = ContextFacade.getUserContext();
		Role role = systemDao.getRoleByRoleName(Constance.DEPARTMENTBRANCH);
		
		
		if (StringUtils.isNotEmpty(model.getArrayList())) {
			String[] obj = model.getArrayList().split(",");

			for (String id : obj) {
				// 得到我的应聘
				MyCandidates mycandidate = mapper.getMyCandidatesById(id);
				if (mycandidate != null) {

					// 推荐匹配
					if (model.getMatchstate().equals(Constance.CandidatesState_Two)) {
						// 判断状态做推荐操作
						if (mycandidate.getCandidatesstate().equals(Constance.CandidatesState_One)) {
							// 删除有效的和转推荐的
							mapper.delRecommendByCandidatesGuidAndState(id);

							// 做推荐操作 保存推荐信息
							model.setRecommendguid(UUIDGenerator.randomUUID());
							model.setModiuser(uc.getUsername());
							model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
							model.setMycandidatesguid(id);
							mapper.insertRecommend(model);
							
							// 回写我的应聘的匹配时间、匹配备注、匹配人
							mycandidate.setMatchuser(uc.getUsername());
							mycandidate.setMatchtime(new Timestamp(System.currentTimeMillis()));
							mycandidate.setMatchmemo(model.getModimemo());

						} else {
							// 做转推荐操作 新增
							model.setRecommendguid(UUIDGenerator.randomUUID());
							model.setModiuser(uc.getUsername());
							model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
							model.setMycandidatesguid(id);
							mapper.insertRecommend(model);
						}
						
						//角色赋权
						if(role!=null){
							//部门认定赋权
							UserRole userRole = systemDao.getUserRoleByRoleAndUsergGuid(role.getRoleid(), model.getUserguid());
							if(userRole == null){
								userRole = new UserRole();
								userRole.setUserguid(model.getUserguid());
								userRole.setRoleid(role.getRoleid());
								systemDao.insertUserRole(userRole);
							}
						}
						
						//判断推荐者是否有华数公司的权限 无增加公司权限
						UserManageRange userManagerange = systemDao.getUserManageRangeByUserIdAndCompanyId(Constance.COMPANYID,model.getUserguid());
						if(userManagerange==null){
							UserManageRange manageRange=new UserManageRange();
							manageRange.setCompanyid(Constance.COMPANYID);
							manageRange.setUserguid(model.getUserguid());
							manageRange.setIsdefault(Constance.VALID_YES);
							systemDao.insertUserManageRange(manageRange);
						}

						// 发邮件信息给推荐的领导
						if(model.getIsemail()!=null){
							//判断是否需要发邮件
							if(model.getIsemail().equals(Constance.VALID_YES)){
								 model.convertOneCodeToName(departmentDao, companyDao,postDao, systemDao, employeeDao, quotaDao,optionDao);
								 Resume resume =resumeDao.getResumeById(mycandidate.getWebuserguid());
								 if(resume!=null){
									 mailSendService.sendTuiJianMailTo(mycandidate,resume, model);
								 }
							}
						}
						
						//如果不发送短信保存business表，表示已发送过
						if(model.getIsmsg()==null){
							Bussiness bussiness=new Bussiness();
							bussiness.setBussinessguid(UUIDGenerator.randomUUID());
							bussiness.setOperateguid(model.getRecommendguid());
							bussiness.setTablename(Constance.recommendTable);
							mapper.insertBussiness(bussiness);
						}

						// 认定
					} else if (model.getMatchstate().equals(Constance.CandidatesState_Four)) {
						
						System.out.println("1111");
						
						// 得到所有的推荐信息
						List<Recommend> list = mapper.getRecommendByCandidatesGuid(id);
						if (!list.isEmpty()) {
							for (Recommend recommend : list) {
								if (recommend.getRecommendguid().equals(model.getRecommendguid())) {
									System.out.println("2222");
									
									// 修改推荐表
									recommend.setRecommendcompanyid(model.getRecommendcompanyid());
									recommend.setRecommenddeptid(model.getRecommenddeptid());
									recommend.setRecommendpostguid(model.getRecommendpostguid());
									recommend.setRecommendpostname(model.getRecommendpostname());
									recommend.setUserguid(model.getUserguid());
									recommend.setAuditiontime(model.getAuditiontime());
									mapper.updateRecommend(recommend);
								} else {
									System.out.println("3333");
									
									recommend.setState(Constance.VALID_NO);
									mapper.updateRecommend(recommend);
								}
							}
						}

						// 回写我的应聘的认定时间、认定备注、认定人
						mycandidate.setHoldmemo(model.getModimemo());
						mycandidate.setHolduser(uc.getUsername());
						mycandidate.setHoldtime(new Timestamp(System.currentTimeMillis()));
					}

					// 保存操作历史
					MyCandidatesHistory myCandidatesHistory = new MyCandidatesHistory();
					myCandidatesHistory.setMycandidateshistoryguid(UUIDGenerator.randomUUID());
					myCandidatesHistory.setModiuser(uc.getUsername());
					myCandidatesHistory.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					myCandidatesHistory.setMycandidatesguid(id);
					OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CANDIDATESSTATE, model.getMatchstate());
					if (opt != null) {
						myCandidatesHistory.setModimemo(opt.getName());
					}
					mapper.insertMyCandidatesHistory(myCandidatesHistory);

					// 回写我的应聘
					mycandidate.setCandidatesstate(model.getMatchstate());
					mycandidate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
					mapper.updateMyCandidates(mycandidate);

				}
			}
		}
	}

	/**
	 * 保存(直接认定)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@Transactional
	public void saveOrUpdateRecommendByAffirm(Recommend model) throws Exception {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		UserContext uc = ContextFacade.getUserContext();
		if (StringUtils.isNotEmpty(model.getArrayList())) {
			String[] obj = model.getArrayList().split(",");

			for (String id : obj) {
				// 得到我的应聘
				MyCandidates mycandidate = mapper.getMyCandidatesById(id);

				mycandidate.setHoldmemo(model.getModimemo());
				mycandidate.setHolduser(uc.getUsername());
				mycandidate.setHoldtime(new Timestamp(System.currentTimeMillis()));
				mycandidate.setMatchuser(uc.getUsername());
				mycandidate.setMatchmemo(model.getModimemo());
				mycandidate.setMatchtime(new Timestamp(System.currentTimeMillis()));

				// 保存推荐
				model.setRecommendguid(UUIDGenerator.randomUUID());
				model.setModiuser(uc.getUsername());
				model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				model.setMycandidatesguid(id);
				mapper.insertRecommend(model);

				// 保存操作历史
				MyCandidatesHistory myCandidatesHistory = new MyCandidatesHistory();
				myCandidatesHistory.setMycandidateshistoryguid(UUIDGenerator.randomUUID());
				myCandidatesHistory.setModiuser(uc.getUsername());
				myCandidatesHistory.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				myCandidatesHistory.setMycandidatesguid(id);
				OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CANDIDATESSTATE, model.getMatchstate());
				if (opt != null) {
					myCandidatesHistory.setModimemo(opt.getName());
				}
				mapper.insertMyCandidatesHistory(myCandidatesHistory);

				// 回写我的应聘
				mycandidate.setCandidatesstate(model.getMatchstate());
				mycandidate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				mapper.updateMyCandidates(mycandidate);
			}
		}

	}

	/**
	 * 得到数据
	 * 
	 * @param id
	 * @return
	 */
	public Recommend getRecommendById(String id) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		Recommend model = mapper.getRecommendById(id);
		if (model != null) {
			model.convertOneCodeToName(departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, optionDao);
			return model;
		}
		return null;
	}

	/**
	 * 得到数据
	 * 
	 * @param id
	 * @return
	 */
	public Recommend getRecommendByCandidatesGuidAndState(String id) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		List<Recommend> list = mapper.getRecommendByCandidatesGuidAndState(id);
		if (list != null) {
			Recommend model = list.get(0);
			model.convertOneCodeToName(departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, optionDao);
			return model;
		}
		return null;
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public List<Recommend> getRecomendListByCandidatesGuid(String id) {
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);
		AddressListDao addressListDao = sqlSession.getMapper(AddressListDao.class);

		List<Recommend> list = mapper.getRecommendByCandidatesGuid(id);
		if (!list.isEmpty()) {
			for (Recommend model : list) {
				model.convertOneCodeToName(departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, optionDao);
				if (StringUtils.isNotEmpty(model.getEmployeeid())) {
					List<AddressList> addressLists = addressListDao.getAddressListByEmployeeId(model.getEmployeeid());
					for (AddressList addressList : addressLists) {
						model.setAddresslistguid(addressList.getAddresslistguid());
					}
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * 发短信给推荐的人
	 * 
	 */
	@Transactional
	public void sendMessageToRecommender() throws Exception{
		MyCandidatesDao mapper = sqlSession.getMapper(MyCandidatesDao.class);
		ResumeDao resumeDao = sqlSession.getMapper(ResumeDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
		PostDao postDao = sqlSession.getMapper(PostDao.class);
		EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
		SystemDao systemDao = sqlSession.getMapper(SystemDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		OptionDao optionDao = sqlSession.getMapper(OptionDao.class);

		// 取当前1小时 前的没发短信的推荐信息
		List<Recommend> list = mapper.getRecommendByToRecommender();
		
		Map<String, List<Recommend>> userMap = new HashMap<String, List<Recommend>>();
		if (!list.isEmpty()) {
			for (Recommend model : list) {
				List<Recommend> tmplist = userMap.get(model.getUserguid());
				if (tmplist == null) {
					tmplist = new ArrayList<Recommend>();
					tmplist.add(model);
					userMap.put(model.getUserguid(), tmplist);
				} else {
					tmplist.add(model);
				}
			}
			
			//发送短信
			for(String key:userMap.keySet()){
				List<Recommend> tmplist=userMap.get(key);
				String deptUserMobile=null;
				String deptUsername=null;
				StringBuffer msg = new StringBuffer();
				for(Recommend recommend:tmplist){
					MyCandidates model = mapper.getMyCandidatesById(recommend.getMycandidatesguid());
					Resume resume = resumeDao.getResumeById(model.getWebuserguid());
					if (resume != null) {
						 msg.append(resume.getName());
						 msg.append("，");
					 }
					
					recommend.convertOneCodeToName(departmentDao, companyDao, postDao, systemDao, employeeDao, quotaDao, optionDao);
					deptUserMobile=recommend.getUsermobile();
					deptUsername=recommend.getUsername();
					
					//回写短信发送
					Bussiness bussiness=new Bussiness();
					bussiness.setBussinessguid(UUIDGenerator.randomUUID());
					bussiness.setOperateguid(recommend.getRecommendguid());
					bussiness.setTablename(Constance.recommendTable);
					mapper.insertBussiness(bussiness);
				}
				
				if (msg.length() > 0) {
					 msg.setLength(msg.length() - 1);
					 msg.append("(");
					 msg.append(tmplist.size());
					 msg.append("人)");
				}
				
				//发送短信
				sendMessageService.sendTuiJianMsgTo(msg.toString(),deptUsername,deptUserMobile);
			}
		}
		
		
		

	}

}
