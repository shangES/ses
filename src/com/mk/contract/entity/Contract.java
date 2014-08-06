package com.mk.contract.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.OrgPathUtil;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.employee.dao.EmployeeDao;
import com.mk.employee.entity.Employee;
import com.mk.employee.entity.Position;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.util.StringUtils;
import com.mk.fuzhu.service.NameConvertCodeService;
import com.mk.quota.dao.QuotaDao;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class Contract {
	private String contractid;
	private String employeeid;
	private String contractcode;
	private Integer contracttype;
	private Date signdate;
	private Date startdate;
	private Date enddate;
	private Integer hourssystem;
	private String content;
	private Integer state;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;

	private String companyid;
	
	// 临时
	private String contracttypename;
	private String hourssystemname;

	private String jobnumber;
	private String companyname;
	private String deptid;
	private String deptname;
	private String name;
	private String postid;
	private String postname;

	// 数据导入
	private Integer checkstate;
	private String msg;

	// 员工任职
	private Position position;

	public Contract() {
		
	}

	public Integer getCheckstate() {
		return checkstate;
	}

	public void setCheckstate(Integer checkstate) {
		this.checkstate = checkstate;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getJobnumber() {
		return jobnumber;
	}

	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getContracttypename() {
		return contracttypename;
	}

	public void setContracttypename(String contracttypename) {
		this.contracttypename = contracttypename;
	}

	public String getHourssystemname() {
		return hourssystemname;
	}

	public void setHourssystemname(String hourssystemname) {
		this.hourssystemname = hourssystemname;
	}

	public String getContractid() {
		return contractid;
	}

	public void setContractid(String contractid) {
		this.contractid = contractid;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getContractcode() {
		return contractcode;
	}

	public void setContractcode(String contractcode) {
		this.contractcode = contractcode;
	}

	public Integer getContracttype() {
		return contracttype;
	}

	public void setContracttype(Integer contracttype) {
		this.contracttype = contracttype;
	}

	public Date getSigndate() {
		return signdate;
	}

	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Integer getHourssystem() {
		return hourssystem;
	}

	public void setHourssystem(Integer hourssystem) {
		this.hourssystem = hourssystem;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getModiuser() {
		return modiuser;
	}

	public void setModiuser(String modiuser) {
		this.modiuser = modiuser;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getModitimestamp() {
		return moditimestamp;
	}

	public void setModitimestamp(Timestamp moditimestamp) {
		this.moditimestamp = moditimestamp;
	}

	public String getModimemo() {
		return modimemo;
	}

	public void setModimemo(String modimemo) {
		this.modimemo = modimemo;
	}

	/**
	 * 单个翻译
	 * 
	 * @param optionDao
	 * @param emDao
	 * @param companyDao
	 * @param departmentDao
	 * @param postDao
	 */
	public void convertOneCodeToName(OptionDao optionDao, EmployeeDao emDao, CompanyDao companyDao, DepartmentDao departmentDao, PostDao postDao,QuotaDao quotaDao) {
		// 合同类型
		if (this.getContracttype() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.CONTRACTTYPE, this.getContracttype());
			if (opt != null)
				this.setContracttypename(opt.getName());
		}

		// 合同工时
		if (this.getHourssystem() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.HOURSSYSTEM, this.getHourssystem());
			if (opt != null)
				this.setHourssystemname(opt.getName());
		}
		
		if (this.getEmployeeid() != null) {
			List<Position> positions = emDao.getPositionByEmployeeIdAndIsstaff(this.getEmployeeid());
			Employee employee = emDao.getEmployeeById(this.getEmployeeid());
			if (!positions.isEmpty()) {
				Position position = positions.get(0);
				position.convertOneCodeToName(companyDao, departmentDao, postDao,quotaDao, optionDao);
				this.setPosition(position);

				// 公司部门岗位
				if (this.getCompanyid() != null) {
					Company company = OrgPathUtil.getOneCompanyFullPath(this.getCompanyid(), companyDao);
					if (company != null)
						this.setCompanyname(company.getCompanyfullname());
				}else
					this.setCompanyname(position.getCompanyname());
				
				//this.setCompanyname(position.getCompanyname());
				this.setDeptname(position.getDeptname());
				this.setPostname(position.getPostname());

				if (employee != null) {
					this.setName(employee.getName());
					this.setJobnumber(employee.getJobnumber());
				}
			}
		}

	}

	/**
	 * 翻译
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param postMap
	 * @param contracttypeMap
	 * @param hourssystemMap
	 * @param emDao
	 */
	public void convertManyCodeToName(Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, Post> postMap, Map<String, String> jobMap, Map<String, String> rankMap, Map<String, String> quotaMap,
			Map<Integer, String> contracttypeMap, Map<Integer, String> hourssystemMap, EmployeeDao mapper) {

		// 合同类型
		if (this.getContracttype() != null) {
			String name = contracttypeMap.get(this.getContracttype());
			this.setContracttypename(name);
		}

		// 合同工时
		if (this.getHourssystem() != null) {
			String name = contracttypeMap.get(this.getHourssystem());
			this.setHourssystemname(name);
		}
		
		

		// 员工任职
		Employee employee = mapper.getEmployeeById(this.getEmployeeid());
		List<Position> positions = mapper.getPositionByEmployeeIdAndIsstaff(this.getEmployeeid());
		if (!positions.isEmpty()) {
			Position position = positions.get(0);
			position.convertManyCodeToName(companyMap, deptMap, jobMap,rankMap, postMap, quotaMap);
			this.setPosition(position);

			// 公司部门岗位
			//公司
			if (this.getCompanyid() != null) {
				Company company = companyMap.get(this.getCompanyid());
				if (company != null)
					this.setCompanyname(company.getCompanyfullname());
			}else
				this.setCompanyname(position.getCompanyname());
			
			//
			this.setDeptname(position.getDeptname());
			this.setPostname(position.getPostname());

			if (employee != null) {
				this.setName(employee.getName());
				this.setJobnumber(employee.getJobnumber());
			}
		}

	}

	/**
	 * 导入
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public Contract(Map<String, Company> companyMap, Map<String, Department> deptMap, NameConvertCodeService nameService, Map<String, Integer> columnTitle, HSSFRow row) {
		HSSFCell cell = null;
		msg = "";

		// 缓存列
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (int m = row.getFirstCellNum(); m <= row.getLastCellNum(); m++) {
			cell = row.getCell(m);
			if (cell != null) {
				map.put(m, Constance.getCellValue(cell).trim());
			}
		}

		// 用户
		UserContext uc = ContextFacade.getUserContext();
		this.setModiuser(uc.getUsername());

		this.setState(Constance.VALID_YES);

		// 姓名// 工号
		String name = map.get(columnTitle.get("姓名"));
		String jobnumber = map.get(columnTitle.get("工号"));
		if (StringUtils.isNotEmpty(jobnumber)) {
			// 工号要存在
			Employee model = nameService.getEmployeeByJobnumber(jobnumber, name);
			if (model != null) {
				this.setEmployeeid(model.getEmployeeid());
				this.setJobnumber(model.getJobnumber());
				this.setName(model.getName());

				this.setCompanyname(model.getCompanyname());
				this.setDeptname(model.getDeptname());
				this.setPostname(model.getPostname());
			} else {
				msg += "员工信息不存在;";
			}
		} else
			msg += "[工号]不能为空；";

		// 合同编号
		String contractcode = map.get(columnTitle.get("合同编号"));
		if (StringUtils.isNotEmpty(contractcode)) {
			this.setContractcode(contractcode);
		}

		// 合同类型
		String contracttypename = map.get(columnTitle.get("合同类型"));
		if (StringUtils.isNotEmpty(contracttypename)) {
			this.setContracttypename(contracttypename);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.CONTRACTTYPE, contracttypename);
			if (code == null) {
				msg += "[合同类型：" + contracttypename + "]不能转成系统内码；";
			} else
				this.setContracttype(code);
		} else
			msg += "[合同类型]不能为空；";

		// 签订日期
		String signdate = map.get(columnTitle.get("签订日期"));
		if (StringUtils.isNotEmpty(signdate)) {
			try {
				this.setSigndate(Date.valueOf(signdate));
			} catch (Exception e) {
				msg += "[签订日期：" + signdate + "]转换日期错误；";
			}
		} else {
			msg += "[签订日期]不能为空；";
		}

		// 生效日期
		String startdate = map.get(columnTitle.get("生效日期"));
		if (StringUtils.isNotEmpty(startdate)) {
			try {
				this.setStartdate(Date.valueOf(startdate));
			} catch (Exception e) {
				msg += "[生效日期：" + startdate + "]转换日期错误；";
			}
		} else {
			msg += "[生效日期]不能为空；";
		}

		// 终止日期
		String enddate = map.get(columnTitle.get("终止日期"));
		if (StringUtils.isNotEmpty(enddate)) {
			try {
				this.setEnddate(Date.valueOf(enddate));
			} catch (Exception e) {
				msg += "[终止日期：" + enddate + "]转换日期错误；";
			}
		}

		// 合同工时
		String hourssystemname = map.get(columnTitle.get("合同工时"));
		if (StringUtils.isNotEmpty(hourssystemname)) {
			this.setHourssystemname(hourssystemname);
			Integer code = nameService.getOptionCodeByTypeAndName(OptionConstance.HOURSSYSTEM, hourssystemname);
			if (code == null) {
				msg += "[合同工时：" + hourssystemname + "]不能转成系统内码；";
			} else
				this.setHourssystem(code);
		} else
			msg += "[合同工时]不能为空；";

		// 合同内容
		String content = map.get(columnTitle.get("合同内容"));
		if (StringUtils.isNotEmpty(content)) {
			this.setContent(content);
		}

		// 备注
		String memo = map.get(columnTitle.get("备注"));
		if (StringUtils.isNotEmpty(memo)) {
			this.setMemo(memo);
		}

		// 异常
		if (StringUtils.isNotEmpty(this.getMsg()))
			this.setCheckstate(Constance.VALID_YES);
	}

	/**
	 * 批量导入
	 * 
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param hourssystemMap
	 * @param contracttypeMap
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public Contract(Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, String> postMap, Map<String, Integer> hourssystemMap, Map<String, Integer> contracttypeMap,
			NameConvertCodeService nameService, Map<String, Integer> columnTitle, HSSFRow row) {
		HSSFCell cell = null;
		msg = "";

		// 缓存列
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (int m = row.getFirstCellNum(); m <= row.getLastCellNum(); m++) {
			cell = row.getCell(m);
			if (cell != null) {
				map.put(m, Constance.getCellValue(cell).trim());
			}
		}

		// 用户
		UserContext uc = ContextFacade.getUserContext();
		this.setModiuser(uc.getUsername());

		this.setState(Constance.VALID_YES);

		// 姓名// 工号
		String name = map.get(columnTitle.get("姓名"));
		String jobnumber = map.get(columnTitle.get("工号"));
		if (StringUtils.isNotEmpty(jobnumber)) {
			// 工号要存在
			Employee model = nameService.getEmployeeByJobnumber(jobnumber, name);
			if (model != null) {
				this.setEmployeeid(model.getEmployeeid());
				this.setJobnumber(model.getJobnumber());
				this.setName(model.getName());

				this.setCompanyname(model.getCompanyname());
				this.setDeptname(model.getDeptname());
				this.setPostname(model.getPostname());
			} else {
				msg += "员工信息不存在;";
			}
		} else
			msg += "[工号]不能为空；";

		// 合同编号
		String contractcode = map.get(columnTitle.get("合同编号"));
		if (StringUtils.isNotEmpty(contractcode)) {
			this.setContractcode(contractcode);
		}

		// 合同类型
		String contracttypename = map.get(columnTitle.get("合同类型"));
		if (StringUtils.isNotEmpty(contracttypename)) {
			this.setContracttypename(contracttypename);
			Integer code = contracttypeMap.get(contracttypename);
			if (code == null) {
				msg += "[合同类型：" + contracttypename + "]不能转成系统内码；";
			} else
				this.setContracttype(code);
		} else
			msg += "[合同类型]不能为空；";

		// 签订日期
		String signdate = map.get(columnTitle.get("签订日期"));
		if (StringUtils.isNotEmpty(signdate)) {
			try {
				this.setSigndate(Date.valueOf(signdate));
			} catch (Exception e) {
				msg += "[签订日期：" + signdate + "]转换日期错误；";
			}
		} else {
			msg += "[签订日期]不能为空；";
		}

		// 生效日期
		String startdate = map.get(columnTitle.get("生效日期"));
		if (StringUtils.isNotEmpty(startdate)) {
			try {
				this.setStartdate(Date.valueOf(startdate));
			} catch (Exception e) {
				msg += "[生效日期：" + startdate + "]转换日期错误；";
			}
		} else {
			msg += "[生效日期]不能为空；";
		}

		// 终止日期
		String enddate = map.get(columnTitle.get("终止日期"));
		if (StringUtils.isNotEmpty(enddate)) {
			try {
				this.setEnddate(Date.valueOf(enddate));
			} catch (Exception e) {
				msg += "[终止日期：" + enddate + "]转换日期错误；";
			}
		}

		// 合同工时
		String hourssystemname = map.get(columnTitle.get("合同工时"));
		if (StringUtils.isNotEmpty(hourssystemname)) {
			this.setHourssystemname(hourssystemname);
			Integer code = hourssystemMap.get(hourssystemname);
			if (code == null) {
				msg += "[合同工时：" + hourssystemname + "]不能转成系统内码；";
			} else
				this.setHourssystem(code);
		} else
			msg += "[合同工时]不能为空；";

		// 合同内容
		String content = map.get(columnTitle.get("合同内容"));
		if (StringUtils.isNotEmpty(content)) {
			this.setContent(content);
		}

		// 备注
		String memo = map.get(columnTitle.get("备注"));
		if (StringUtils.isNotEmpty(memo)) {
			this.setMemo(memo);
		}

		// 异常
		if (StringUtils.isNotEmpty(this.getMsg()))
			this.setCheckstate(Constance.VALID_YES);
	}

	@Override
	public String toString() {
		return "Contract [contractid=" + contractid + ", employeeid=" + employeeid + ", contractcode=" + contractcode + ", contracttype=" + contracttype + ", signdate=" + signdate + ", startdate="
				+ startdate + ", enddate=" + enddate + ", hourssystem=" + hourssystem + ", content=" + content + ", state=" + state + ", memo=" + memo + ", modiuser=" + modiuser + ", moditimestamp="
				+ moditimestamp + ", modimemo=" + modimemo + ", contracttypename=" + contracttypename + ", hourssystemname=" + hourssystemname + ", jobnumber=" + jobnumber + ", companyid="
				+ companyid + ", companyname=" + companyname + ", deptid=" + deptid + ", deptname=" + deptname + ", name=" + name + ", postid=" + postid + ", postname=" + postname + ", checkstate="
				+ checkstate + ", msg=" + msg + ", position=" + position + ", getCheckstate()=" + getCheckstate() + ", getMsg()=" + getMsg() + ", getJobnumber()=" + getJobnumber()
				+ ", getCompanyid()=" + getCompanyid() + ", getCompanyname()=" + getCompanyname() + ", getDeptid()=" + getDeptid() + ", getDeptname()=" + getDeptname() + ", getName()=" + getName()
				+ ", getPostid()=" + getPostid() + ", getPostname()=" + getPostname() + ", getPosition()=" + getPosition() + ", getContracttypename()=" + getContracttypename()
				+ ", getHourssystemname()=" + getHourssystemname() + ", getContractid()=" + getContractid() + ", getEmployeeid()=" + getEmployeeid() + ", getContractcode()=" + getContractcode()
				+ ", getContracttype()=" + getContracttype() + ", getSigndate()=" + getSigndate() + ", getStartdate()=" + getStartdate() + ", getEnddate()=" + getEnddate() + ", getHourssystem()="
				+ getHourssystem() + ", getContent()=" + getContent() + ", getState()=" + getState() + ", getMemo()=" + getMemo() + ", getModiuser()=" + getModiuser() + ", getModitimestamp()="
				+ getModitimestamp() + ", getModimemo()=" + getModimemo() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
