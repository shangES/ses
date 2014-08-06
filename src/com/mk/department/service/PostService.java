package com.mk.department.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Budgetype;
import com.mk.company.entity.Company;
import com.mk.company.service.CompanyService;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.department.tree.PostTree;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.tree.TreeNode;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.fuzhu.service.CodeConvertNameService;
import com.mk.quota.dao.QuotaDao;
import com.mk.quota.entity.Quota;
import com.mk.quota.entity.QuotaOperate;
import com.mk.quota.service.QuotaService;

@Service
public class PostService {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private CodeConvertNameService codeConvertNameService;
	@Autowired
	private QuotaService quotaService;

	/**
	 * 取有效的岗位
	 * 
	 * @param state
	 * @return
	 */
	public List<Post> getHasPost(Integer state) {
		PostDao mapper = sqlSession.getMapper(PostDao.class);
		DepartmentDao deptDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);

		UserContext uc = ContextFacade.getUserContext();
		List<Post> posts = null;
		if (uc.isAdmin()) {
			posts = mapper.getAllPost(state);
		} else {
			posts = mapper.getHasPost(state, uc.getUserId());
		}
		if (posts.size() > Constance.ConvertCodeNum) {
			// 公司
			Map<String, Company> companyMap = codeConvertNameService.getAllCompanyMap();
			// 部门类型
			Map<String, Department> deptMap = codeConvertNameService.getAllDepartmentMap();

			// 职务
			Map<Integer, String> jobidMap = codeConvertNameService.getOptionMapByTypeCode(OptionConstance.JOBID);
			for (Post model : posts) {
				model.convertManyCodeToName(companyMap, deptMap, jobidMap);
			}
		} else {
			for (Post model : posts) {
				model.convertOneCodeToName(companyDao, deptDao);
			}
		}

		return posts;
	}

	/**
	 * 公司部门岗位树
	 * 
	 * @return
	 */
	public List<TreeNode> buildMyDeptPostTree(Integer state) {
		PostDao mapper = sqlSession.getMapper(PostDao.class);
		// 数据
		List<Company> compays = companyService.getHasCompanys(Constance.VALID_YES);
		List<Department> depts = departmentService.getHasDepartment(Constance.VALID_YES);
		List<Post> posts = mapper.getAllPost(state);

		// 成树
		PostTree tree = new PostTree();
		return tree.doCompanyDeptPostBuild(compays, depts, posts);
	}

	/**
	 * 加载树
	 * 
	 * @param deptid
	 * @return
	 */
	public List<TreeNode> buildPostTree(String deptid) {
		PostDao mapper = sqlSession.getMapper(PostDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);

		// 部门信息
		Department dept = departmentDao.getDepartmentByDepartmentId(deptid);
		if (dept == null)
			return null;

		// 岗位信息
		List<Post> list = mapper.getPostByDeptid(deptid);
		PostTree tree = new PostTree();

		return tree.doBulid(dept, list);
	}

	/**
	 * 加载部门以及子部门的树
	 * 
	 * @param deptid
	 * @return
	 */
	public List<TreeNode> buildMyPostTreeByDept(String deptid) {
		PostDao mapper = sqlSession.getMapper(PostDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);
		CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);

		if (StringUtils.isNotEmpty(deptid)) {
			// 部门信息
			Department dept = departmentDao.getDepartmentByDepartmentId(deptid);
			if (dept == null)
				return null;

			List<Department> depts = departmentDao.getDepartmentByDepartmentCode(dept.getDeptcode());

			// 岗位信息
			List<Post> list = mapper.getPostByDeptCode(dept.getDeptcode());
			for (Post post : list) {
				post.convertOneCodeToName(companyDao, departmentDao);
			}

			PostTree tree = new PostTree();

			return tree.doBulidByCode(deptid, depts, list);
		}

		return null;

	}

	/**
	 * 通过部门id得到岗位信息
	 * 
	 * @param deptid
	 * @return
	 */
	public void searchPost(GridServerHandler grid) {
		PostDao mapper = sqlSession.getMapper(PostDao.class);
		CompanyDao comDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao deptDao = sqlSession.getMapper(DepartmentDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();

		// 岗位的统计数量
		Integer count = mapper.countPost(grid);
		PageUtils.setTotalRows(grid, count);

		// 岗位信息
		List<Post> list = mapper.searchPost(grid);

		for (Post model : list) {
			model.convertOneCodeToName(comDao, deptDao);
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}

	/**
	 * 保存or修改岗位信息
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdatePost(Post model) {
		PostDao mapper = sqlSession.getMapper(PostDao.class);
		CompanyDao companyDao=sqlSession.getMapper(CompanyDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		UserContext uc = ContextFacade.getUserContext();
		//岗位
		if (StringUtils.isEmpty(model.getPostid())) {
			model.setPostid(UUIDGenerator.randomUUID());
			model.setModitimestamp(new Timestamp(System.currentTimeMillis()));
			model.setModiuser(uc.getUsername());
			String maxcode = mapper.getMaxPostCodeByDepartmentId(model.getDeptid());

			model.setPostcode(UUIDGenerator.format0000_ID(null, maxcode, 1));
			mapper.insertPost(model);
			
//			//保存编制默认人数为1
//			List<Budgetype> list = companyDao.getQuotaBudgettypeByCompanyId(model.getCompanyid());
//			if(list!=null&&!list.isEmpty()){
//				for(Budgetype budgetype:list){
//					//保存编制
//					Quota quota=new Quota();
//					quota.setBudgettype(budgetype.getBudgettypeid());
//					quota.setQuotaid(UUIDGenerator.randomUUID());
//					quota.setPostid(model.getPostid());
//					quota.setModiuser(uc.getUsername());
//					quota.setModitimestamp(new Timestamp(System.currentTimeMillis()));
//					quota.setBudgetdate(new Date(System.currentTimeMillis()));
//					String maxquotacode = getMaxQuotaCode();
//					quota.setQuotacode(maxquotacode);
//					quota.setState(Constance.VALID_YES);
//					quota.setBudgetnumber(Constance.VALID_YES);
//					quotaDao.insertQuota(quota);
//					
//					//保存编制操作表
//					QuotaOperate operate = new QuotaOperate();
//					operate.setQuotaoperateguid(UUIDGenerator.randomUUID());
//					operate.setQuotaid(quota.getQuotaid());
//					operate.setOperatestate(Constance.OperateState_Add);
//					operate.setModiuser(uc.getUsername());
//					operate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
//					operate.setOperatenum(quota.getBudgetnumber());
//					quotaDao.insertQuotaOperate(operate);
//				}
//			}
			
			//是否加岗位编制
			if(model.getBudgetnumber1()!=null&&StringUtils.isNotEmpty(model.getBudgettype1())){
				//保存编制
				Quota quota=new Quota();
				quota.setBudgettype(model.getBudgettype1());
				quota.setQuotaid(UUIDGenerator.randomUUID());
				quota.setPostid(model.getPostid());
				quota.setModiuser(uc.getUsername());
				quota.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				quota.setBudgetdate(new Date(System.currentTimeMillis()));
				String maxquotacode = getMaxQuotaCode();
				quota.setQuotacode(maxquotacode);
				quota.setState(Constance.VALID_YES);
				quota.setBudgetnumber(model.getBudgetnumber1());
				quotaDao.insertQuota(quota);
				
				//保存编制操作表
				QuotaOperate operate = new QuotaOperate();
				operate.setQuotaoperateguid(UUIDGenerator.randomUUID());
				operate.setQuotaid(quota.getQuotaid());
				operate.setOperatestate(Constance.OperateState_Add);
				operate.setModiuser(uc.getUsername());
				operate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				operate.setOperatenum(quota.getBudgetnumber());
				quotaDao.insertQuotaOperate(operate);
			}
			
			//保存另外一个编制
			if(model.getBudgetnumber2()!=null&&StringUtils.isNotEmpty(model.getBudgettype2())){
				//保存编制
				Quota quota=new Quota();
				quota.setBudgettype(model.getBudgettype2());
				quota.setQuotaid(UUIDGenerator.randomUUID());
				quota.setPostid(model.getPostid());
				quota.setModiuser(uc.getUsername());
				quota.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				quota.setBudgetdate(new Date(System.currentTimeMillis()));
				String maxquotacode = getMaxQuotaCode();
				quota.setQuotacode(maxquotacode);
				quota.setState(Constance.VALID_YES);
				quota.setBudgetnumber(model.getBudgetnumber2());
				quotaDao.insertQuota(quota);
				
				//保存编制操作表
				QuotaOperate operate = new QuotaOperate();
				operate.setQuotaoperateguid(UUIDGenerator.randomUUID());
				operate.setQuotaid(quota.getQuotaid());
				operate.setOperatestate(Constance.OperateState_Add);
				operate.setModiuser(uc.getUsername());
				operate.setModitimestamp(new Timestamp(System.currentTimeMillis()));
				operate.setOperatenum(quota.getBudgetnumber());
				quotaDao.insertQuotaOperate(operate);
			}
		} else {
			mapper.updatePost(model);
		}
	}

	/**
	 * 通过id得到岗位信息
	 * 
	 * @param id
	 * @return
	 */
	public Post getPostByPostId(String id) {
		PostDao mapper = sqlSession.getMapper(PostDao.class);
		CompanyDao comDao = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao deptDao = sqlSession.getMapper(DepartmentDao.class);
		Post model = mapper.getPostByPostId(id);
		model.convertOneCodeToName(comDao, deptDao);
		return model;
	}

	/**
	 * 通过id删除岗位信息
	 * 
	 * @param id
	 */
	@Transactional
	public void delPostById(String ids) {
		PostDao mapper = sqlSession.getMapper(PostDao.class);
		QuotaDao quotaDao = sqlSession.getMapper(QuotaDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			quotaDao.delQuotaOperateByPostId(id);
			quotaDao.delQuotaByPostId(id);
			mapper.delPostById(id);
		}
	}

	/**
	 * 失效or还原岗位信息
	 * 
	 * @param ids
	 * @param state
	 */
	@Transactional
	public void invalidOrReductionPostById(String ids, int state) {
		PostDao mapper = sqlSession.getMapper(PostDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.invalidOrReductionPostById(id, state);
		}
	}

	/**
	 * 验证岗位名称是否重复
	 * 
	 * @param postid
	 * @param postname
	 * @return
	 */
	public String verificationPostname(String postid, String deptid, String postname) {
		PostDao mapper = sqlSession.getMapper(PostDao.class);

		// 检查索引
		StringBuffer msg = new StringBuffer();
		List<Post> list = mapper.verificationPostname(postid, deptid, postname);

		if (list.size() != 0) {
			msg.append("岗位名称【");
			msg.append(postname);
			msg.append("】已经存在");
		}
		return msg.toString();
	}

	/**
	 * 排序
	 * 
	 * @param id
	 * @param targetid
	 * @param moveType
	 */
	@Transactional
	public void orderPost(String id, String targetid, String moveType) {
		PostDao mapper = sqlSession.getMapper(PostDao.class);

		// ========================
		// 移动的节点
		Post selectNode = mapper.getPostByPostId(id);
		if (selectNode == null)
			return;
		// 移动的目标节点
		Post targetNode = mapper.getPostByPostId(targetid);
		if (targetNode == null)
			return;

		// ========================

		// 1：生成移动节点的code
		// 确定向上移还是向下移动
		int step = 0;
		String selectNewCode = null;
		if (moveType.equals("next")) {
			step = 1;
			selectNewCode = createNetCode(targetNode, step);
		} else {
			selectNewCode = targetNode.getPostcode();
		}

		// 2:找到所有影响的node
		List<Post> list = mapper.getPostByDeptid(selectNode.getDeptid());
		Map<String, Post> postMap = new HashMap<String, Post>();
		for (Post model : list) {
			postMap.put(model.getPostid(), model);
		}

		// 3:找相同的修改
		affectNodeUpate(mapper, list, postMap, selectNewCode);

		// 4:更新移动的节点
		selectNode.setPostcode(selectNewCode);
		mapper.updatePost(selectNode);

	}

	// 影响节点
	@Transactional
	private void affectNodeUpate(PostDao mapper, List<Post> list, Map<String, Post> postMap, String selectCode) {
		for (Post model : list) {
			// 移动后的code数据库里如果已经存在
			if (model.getPostcode().equals(selectCode)) {
				String newcode = createNetCode(model, 1);

				// 递归向下寻找
				affectNodeUpate(mapper, list, postMap, newcode);

				model.setPostcode(newcode);
				mapper.updatePost(model);
			}
		}
	}

	// 生成新的companycode
	private String createNetCode(Post targetNode, int step) {
		String targetCode = targetNode.getPostcode();
		int targetCodeLg = targetCode.length();
		String maxCode = targetCode.substring(targetCodeLg - 5, targetCodeLg);

		// 生成移动后的code
		String selectCode = UUIDGenerator.format0000_ID(null, maxCode, step);
		return selectCode;
	}

	
	/**
	 * 解析岗位
	 * 
	 * @param postname
	 * @return
	 */
	@Transactional
	public Post getPostByPostName(String companyid,String deptid,String postname) {
		PostDao mapper = sqlSession.getMapper(PostDao.class);
		
		//查看当前岗位在此部门下是否存在
		List<Post> list = mapper.getPostByPostName(companyid, deptid, postname);
		if(list!=null&&!list.isEmpty()){
			Post post = list.get(0);
			return post;
		}
		
		Post post = new Post();
		UserContext uc = ContextFacade.getUserContext();
		post.setCompanyid(companyid);
		post.setDeptid(deptid);
		post.setPostname(postname);
		post.setPostid(UUIDGenerator.randomUUID());
		post.setState(Constance.VALID_YES);
		String maxcode = mapper.getMaxPostCodeByDepartmentId(deptid);
		post.setPostcode(UUIDGenerator.format0000_ID(null, maxcode, 1));
		post.setModiuser(uc.getUsername());
		post.setModitimestamp(new Timestamp(System.currentTimeMillis()));
		mapper.insertPost(post);
		return post;
	}
	
	
	/**
	 * 得到最大code
	 * 
	 * @param id
	 * @return
	 */
	public String getMaxQuotaCode() {
		QuotaDao mapper = sqlSession.getMapper(QuotaDao.class);
		// 當天最大code
		String maxcode = mapper.getMaxQuotaCode();

		if (StringUtils.isNotEmpty(maxcode)) {
			return UUIDGenerator.format0000_ID(Constance.quota_prefix, maxcode, 1);
		}
		return UUIDGenerator.format0000_ID(Constance.quota_prefix, maxcode, 1);
	}

}
