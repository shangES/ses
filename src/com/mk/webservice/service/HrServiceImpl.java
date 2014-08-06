package com.mk.webservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Position;
import com.mk.quota.dao.QuotaDao;
import com.mk.webservice.entity.WSCompany;
import com.mk.webservice.entity.WSDepartment;
import com.mk.webservice.entity.WSEmployee;
import com.mk.webservice.entity.WSPost;
import com.mk.webservice.entity.WSPostNum;

@Component
@WebService
public class HrServiceImpl implements HrService {
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 组织架构
	 */
	public List<WSCompany> getAllCompanyDeptList(Integer valid) {
		CompanyDao mapper = sqlSession.getMapper(CompanyDao.class);
		DepartmentDao departmentDao = sqlSession.getMapper(DepartmentDao.class);

		List<Company> companys = mapper.getAllCompanys(valid);
		List<Department> depts = departmentDao.getAllDepartment(valid);

		// 缓存公司
		Map<String, List<WSDepartment>> map = new HashMap<String, List<WSDepartment>>();
		for (Department dept : depts) {
			// 构造
			WSDepartment model = new WSDepartment();
			model.setDeptid(model.getDeptid());
			model.setPdeptid(model.getPdeptid());
			model.setCompanyid(model.getCompanyid());
			model.setDeptname(model.getDeptname());
			model.setDepttype(model.getDepttype());
			model.setInterfacecode(model.getInterfacecode());
			model.setState(model.getState());

			// ======
			List<WSDepartment> tmpList = map.get(dept.getCompanyid());
			if (tmpList == null) {
				tmpList = new ArrayList<WSDepartment>();
				tmpList.add(model);
				map.put(dept.getCompanyid(), tmpList);
			} else {
				tmpList.add(model);
			}
		}

		// 构建目录
		List<WSCompany> data = new ArrayList<WSCompany>();
		for (Company company : companys) {
			WSCompany model = new WSCompany();
			// 构造
			model.setCompanyid(model.getCompanyid());
			model.setPcompanyid(model.getPcompanyid());
			model.setCompanyname(model.getCompanyname());
			model.setCompanytype(model.getCompanytype());
			model.setInterfacecode(model.getInterfacecode());
			model.setState(model.getState());

			// ======
			List<WSDepartment> hasList = map.get(company.getCompanyid());
			model.setDepts(hasList);

			data.add(model);
		}

		return data;
	}

	@Override
	public List<WSEmployee> getWSEmployeeList(String jobnumber) {
		EmployeeDao employeedao=sqlSession.getMapper(EmployeeDao.class);
		List<WSEmployee> wsemployees=employeedao.getWSEmployeeList(jobnumber);
		return wsemployees;
	}

	@Override
	public List<WSEmployee> getWSEmployeeListByPostId(String postid) {
		EmployeeDao employeedao=sqlSession.getMapper(EmployeeDao.class);
		List<WSEmployee> wsemployees=employeedao.getWSEmployeeListByPostId(postid);
		return wsemployees;
	}

	/**
	 * 根据岗位代码返回部门名称
	 * 
	 */
	@Override
	public List<WSDepartment> getWSDepartmentByPostId(String interfacecode) {
		DepartmentDao mapper = sqlSession.getMapper(DepartmentDao.class);
		List<Department> depts=mapper.getWSDepartmentByPostId(interfacecode);
		List<WSDepartment> data = new ArrayList<WSDepartment>();
		for(Department model:depts){
			WSDepartment wsdepartment=new WSDepartment();
			wsdepartment.setCompanyid(model.getCompanyid());
			wsdepartment.setDeptid(model.getDeptid());
			wsdepartment.setPdeptid(model.getPdeptid());
			wsdepartment.setDeptname(model.getDeptname());
			wsdepartment.setDepttype(model.getDepttype());
			wsdepartment.setState(model.getState());
			wsdepartment.setInterfacecode(model.getInterfacecode());
			data.add(wsdepartment);
		}
		return data;
	}

	
	/**
	 * 通过员工工号查当前部门与二级部门下的所有人的信息
	 * 
	 */
	@Override
	public List<WSEmployee> getWSEmployeeListByDepartment(String jobnumber) {
		EmployeeDao employeedao=sqlSession.getMapper(EmployeeDao.class);
		Position position=employeedao.getEmployeeByJobnum(jobnumber);
		 if(position!=null){
			 List<WSEmployee> list = employeedao.getWSEmployeeListByDepartment(position.getDeptid());
			 return list;
		 }
		return null;
	}

	@Override
	public List<WSPostNum> getNumByPostCode(String code) {
		QuotaDao mapper=sqlSession.getMapper(QuotaDao.class);
		int employeenum = mapper.countEmployeeByPostCode(code);
		List<WSPostNum> list = mapper.countQuotaByPostCode(code);
		for(WSPostNum wspostnum:list){
			wspostnum.setEmployeenum(employeenum);
		}
		return list;
	}

	
	/**
	 * 根据部门编码返回该部门下（包括下级部门）所有的岗位编码
	 * 
	 * 
	 */
	@Override
	public List<WSPost> getPostCodeByDeptCode(String deptcode) {
		DepartmentDao mapper = sqlSession.getMapper(DepartmentDao.class);
		PostDao postDao=sqlSession.getMapper(PostDao.class);
		List<WSPost> data = new ArrayList<WSPost>();
		
		Department dept = mapper.getDepartmentByPostcode(deptcode);
		if(dept!=null){
			List<Post> posts = postDao.getPostByDeptCode(dept.getDeptcode());
			if(!posts.isEmpty()&&posts!=null){
				for(Post post:posts){
					WSPost wsPost=new WSPost();
					wsPost.setInterfacecode(post.getInterfacecode());
					wsPost.setPostname(post.getPostname());
					data.add(wsPost);
				}
				return data;
			}
		}
		return data;
	}

}
