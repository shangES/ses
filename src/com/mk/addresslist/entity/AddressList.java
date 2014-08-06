package com.mk.addresslist.entity;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.OrgPathUtil;
import com.mk.addresslist.dao.AddressListDao;
import com.mk.company.dao.CompanyDao;
import com.mk.company.entity.Company;
import com.mk.department.dao.DepartmentDao;
import com.mk.department.dao.PostDao;
import com.mk.department.entity.Department;
import com.mk.department.entity.Post;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.custom.CustomDateSerializer;
import com.mk.framework.grid.util.StringUtils;
import com.mk.fuzhu.service.NameConvertCodeService;
import com.mk.system.dao.OptionDao;
import com.mk.system.entity.OptionList;

public class AddressList {
	private String addresslistguid;
	private String employeeid;
	private String employeecode;
	private String companyid;
	private String companycode;
	private String deptid;
	private String deptcode;
	private String postid;
	private String postcode;
	private Integer dorder;
	private String jobnumber;
	private String name;
	private Integer sex;
	private String innerphone;
	private String extphone;
	private String mobilephone;
	private String mobilephone2;
	private String email;
	private String officeaddress;
	private Timestamp refreshtimestamp;
	private String memo;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;

	// 临时
	private String sexname;
	private String companyname;
	private String deptname;
	private String postname;

	// 数据导入
	private Integer checkstate;
	private String msg;

	public AddressList() {

	}

	public String getMobilephone2() {
		return mobilephone2;
	}

	public void setMobilephone2(String mobilephone2) {
		this.mobilephone2 = mobilephone2;
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

	public Integer getDorder() {
		return dorder;
	}

	public void setDorder(Integer dorder) {
		this.dorder = dorder;
	}

	public String getSexname() {
		return sexname;
	}

	public void setSexname(String sexname) {
		this.sexname = sexname;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getAddresslistguid() {
		return addresslistguid;
	}

	public void setAddresslistguid(String addresslistguid) {
		this.addresslistguid = addresslistguid;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getEmployeecode() {
		return employeecode;
	}

	public void setEmployeecode(String employeecode) {
		this.employeecode = employeecode;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getJobnumber() {
		return jobnumber;
	}

	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getInnerphone() {
		return innerphone;
	}

	public void setInnerphone(String innerphone) {
		this.innerphone = innerphone;
	}

	public String getExtphone() {
		return extphone;
	}

	public void setExtphone(String extphone) {
		this.extphone = extphone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficeaddress() {
		return officeaddress;
	}

	public void setOfficeaddress(String officeaddress) {
		this.officeaddress = officeaddress;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getRefreshtimestamp() {
		return refreshtimestamp;
	}

	public void setRefreshtimestamp(Timestamp refreshtimestamp) {
		this.refreshtimestamp = refreshtimestamp;
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

	@Override
	public String toString() {
		return "AddressList [addresslistguid=" + addresslistguid + ", employeeid=" + employeeid + ", employeecode=" + employeecode + ", companyid=" + companyid + ", companycode=" + companycode
				+ ", deptid=" + deptid + ", deptcode=" + deptcode + ", postid=" + postid + ", postcode=" + postcode + ", jobnumber=" + jobnumber + ", name=" + name + ", sex=" + sex + ", innerphone="
				+ innerphone + ", extphone=" + extphone + ", mobilephone=" + mobilephone + ", email=" + email + ", officeaddress=" + officeaddress + ", refreshtimestamp=" + refreshtimestamp
				+ ", memo=" + memo + ", modiuser=" + modiuser + ", moditimestamp=" + moditimestamp + ", modimemo=" + modimemo + ", getAddresslistguid()=" + getAddresslistguid() + ", getEmployeeid()="
				+ getEmployeeid() + ", getEmployeecode()=" + getEmployeecode() + ", getCompanyid()=" + getCompanyid() + ", getCompanycode()=" + getCompanycode() + ", getDeptid()=" + getDeptid()
				+ ", getDeptcode()=" + getDeptcode() + ", getPostid()=" + getPostid() + ", getPostcode()=" + getPostcode() + ", getJobnumber()=" + getJobnumber() + ", getName()=" + getName()
				+ ", getSex()=" + getSex() + ", getInnerphone()=" + getInnerphone() + ", getExtphone()=" + getExtphone() + ", getMobilephone()=" + getMobilephone() + ", getEmail()=" + getEmail()
				+ ", getOfficeaddress()=" + getOfficeaddress() + ", getRefreshtimestamp()=" + getRefreshtimestamp() + ", getMemo()=" + getMemo() + ", getModiuser()=" + getModiuser()
				+ ", getModitimestamp()=" + getModitimestamp() + ", getModimemo()=" + getModimemo() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	/**
	 * 批量code转名称
	 * 
	 * 
	 * @param mapper
	 * @param companyMap
	 * @param deptMap
	 * @param jobMap
	 * @param postMap
	 * @param sexMap
	 */
	public void convertManyCodeToName(AddressListDao mapper, Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, Post> postMap, Map<Integer, String> sexMap) {
		// 公司
		if (this.getCompanyid() != null) {
			Company company = companyMap.get(this.getCompanyid());
			if (company != null)
				this.setCompanyname(company.getCompanyfullname());
		}

		// 部门
		if (this.getDeptid() != null) {
			Department dept = deptMap.get(this.getDeptid());
			if (dept != null)
				this.setDeptname(dept.getDeptfullname());
		}

		// 岗位
		if (this.getPostid() != null) {
			Post post = postMap.get(this.getPostid());
			if (post != null)
				this.setPostname(post.getPostname());
		}

		// 性别
		if (this.getSex() != null) {
			String name = sexMap.get(this.getSex());
			this.setSexname(name);
		}

	}

	/**
	 * code轉名稱
	 * 
	 * @param mapper
	 * @param companyDao
	 * @param departmentDao
	 * @param postDao
	 * @param optionDao
	 */
	public void convertOneCodeToName(AddressListDao mapper, CompanyDao companyDao, DepartmentDao departmentDao, PostDao postDao, OptionDao optionDao) {
		// 公司
		if (this.getCompanyid() != null) {
			Company company = OrgPathUtil.getOneCompanyFullPath(this.getCompanyid(), companyDao);
			if (company != null)
				this.setCompanyname(company.getCompanyfullname());
		}

		// 部门
		if (this.getDeptid() != null) {
			Department dept = OrgPathUtil.getOneDepartmentFullPath(this.getDeptid(), departmentDao);
			if (dept != null)
				this.setDeptname(dept.getDeptfullname());
		}

		// 岗位
		if (this.getPostid() != null) {
			Post post = postDao.getPostByPostId(this.getPostid());
			if (post != null)
				this.setPostname(post.getPostname());
		}

		// 性别
		if (this.getSex() != null) {
			OptionList opt = optionDao.getOptionListByTypeAndCode(OptionConstance.SEX, this.getSex());
			if (opt != null)
				this.setSexname(opt.getName());
		}

	}

	/**
	 * 通讯录信息导入 部门名称| 岗位名称 | 姓名 | 工号 | 性别 | 办公电话 | 内网电话 | 手机 | 民族 | 邮箱 | 办公地址 |
	 * 刷新时间 | 备注
	 * 
	 * @param companyMap
	 * @param deptMap
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public AddressList(Map<String, Company> companyMap, Map<String, Department> deptMap, NameConvertCodeService nameService, Map<String, Integer> columnTitle, HSSFRow row) {
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

		// 排序号
		String dorder = map.get(columnTitle.get("排序号"));
		if (StringUtils.isNotEmpty(dorder)) {
			try {
				// 定的为number形后面会出现.0截掉
				if (dorder.indexOf(".") != -1) {
					dorder = dorder.substring(0, dorder.lastIndexOf("."));
				}
				this.setDorder(Integer.valueOf(dorder));
			} catch (Exception e) {
				msg += "[排序号：" + dorder + "]转换数字错误；";
			}
		} else
			msg += "[排序号]不能为空；";

		// 公司名称
		String companyname = map.get(columnTitle.get("公司名称"));
		if (StringUtils.isNotEmpty(companyname)) {
			this.setCompanyname(companyname);
			Company company = companyMap.get(companyname);
			if (company != null) {
				this.setCompanyid(company.getCompanyid());
				this.setCompanycode(company.getCompanycode());

			} else
				msg += "[公司名称：" + companyname + "]不能转成系统内码；";

		} else
			msg += "[公司名称]不能为空；";

		// 部门名称
		String deptname = map.get(columnTitle.get("部门名称"));
		if (StringUtils.isNotEmpty(deptname)) {
			this.setDeptname(deptname);
			Department dept = deptMap.get(this.getCompanyid() + "/" + deptname);
			if (dept != null) {
				this.setDeptid(dept.getDeptid());
				this.setDeptcode(dept.getDeptcode());
			} else
				msg += "[部门名称：" + deptname + "]不能转成系统内码；";

		} else
			msg += "[部门名称]不能为空；";

		// 岗位名称
		String postname = map.get(columnTitle.get("岗位名称"));
		if (StringUtils.isNotEmpty(postname)) {
			this.setPostname(postname);
			Post post = nameService.getPostByName(this.getCompanyid(), this.getDeptid(), postname);
			if (post != null) {
				this.setPostid(post.getPostid());
				this.setPostcode(post.getPostcode());
			} else
				msg += "[岗位名称：" + postname + "]不能转成系统内码；";
		}

		// 姓名
		String name = map.get(columnTitle.get("姓名"));
		if (StringUtils.isNotEmpty(name)) {
			this.setName(name);
		} else
			msg += "[姓名]不能为空；";

		// 工号
		String jobnumber = map.get(columnTitle.get("工号"));
		if (StringUtils.isNotEmpty(jobnumber)) {
			this.setJobnumber(jobnumber);
		}

		// 性别
		String sexname = map.get(columnTitle.get("性别"));
		if (StringUtils.isNotEmpty(sexname)) {
			this.setSexname(sexname);
			Integer sex = nameService.getOptionCodeByTypeAndName(OptionConstance.SEX, sexname);
			if (sex == null) {
				msg += "[性别：" + sexname + "]不能转成系统内码；";
			} else
				this.setSex(sex);
		} else
			msg += "[性别]不能为空；";

		// 内网
		String innerphone = map.get(columnTitle.get("内网"));
		if (StringUtils.isNotEmpty(innerphone)) {
			this.setInnerphone(innerphone);
		}

		// 分机
		String extphone = map.get(columnTitle.get("分机"));
		if (StringUtils.isNotEmpty(extphone)) {
			this.setExtphone(extphone);
		}

		// 手机
		String mobilephone = map.get(columnTitle.get("手机"));
		if (StringUtils.isNotEmpty(mobilephone)) {
			this.setMobilephone(mobilephone);
		}

		// 邮箱
		String email = map.get(columnTitle.get("邮箱"));
		if (StringUtils.isNotEmpty(email)) {
			this.setEmail(email);
		}

		// 办公地址
		String officeaddress = map.get(columnTitle.get("办公地址"));
		if (StringUtils.isNotEmpty(officeaddress)) {
			this.setOfficeaddress(officeaddress);
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
	 * 通讯录信息导入 部门名称| 岗位名称 | 姓名 | 工号 | 性别 | 办公电话 | 内网电话 | 手机 | 民族 | 邮箱 | 办公地址 |
	 * 刷新时间 | 备注
	 * 
	 * @param nameService
	 * @param columnTitle
	 * @param row
	 */
	public AddressList(Map<String, Company> companyMap, Map<String, Department> deptMap, Map<String, String> postMap, Map<String, Integer> sexMap, NameConvertCodeService nameService,
			Map<String, Integer> columnTitle, HSSFRow row) {
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

		// 姓名
		String name = map.get(columnTitle.get("姓名"));
		if (StringUtils.isNotEmpty(name)) {
			this.setName(name);
		} else
			msg += "[姓名]不能为空；";

		// 工号
		String jobnumber = map.get(columnTitle.get("工号"));
		if (StringUtils.isNotEmpty(jobnumber)) {
			this.setJobnumber(jobnumber);
		}

		// 排序号
		String dorder = map.get(columnTitle.get("排序号"));
		if (StringUtils.isNotEmpty(dorder)) {
			try {
				// 定的为number形后面会出现.0截掉
				if (dorder.indexOf(".") != -1) {
					dorder = dorder.substring(0, dorder.lastIndexOf("."));
				}
				this.setDorder(Integer.valueOf(dorder));
			} catch (Exception e) {
				msg += "[排序号：" + dorder + "]转换数字错误；";
			}
		} else
			msg += "[排序号]不能为空；";

		// 公司名称
		String companyname = map.get(columnTitle.get("公司名称"));
		if (StringUtils.isNotEmpty(companyname)) {
			this.setCompanyname(companyname);
			Company company = companyMap.get(companyname);
			if (company != null) {
				this.setCompanyid(company.getCompanyid());
				this.setCompanycode(company.getCompanycode());

			} else
				msg += "[公司名称：" + companyname + "]不能转成系统内码；";

		} else
			msg += "[公司名称]不能为空；";

		// 部门名称
		String deptname = map.get(columnTitle.get("部门名称"));
		if (StringUtils.isNotEmpty(deptname)) {
			this.setDeptname(deptname);
			Department dept = deptMap.get(this.getCompanyid() + "/" + deptname);
			if (dept != null) {
				this.setDeptid(dept.getDeptid());
				this.setDeptcode(dept.getDeptcode());
			} else
				msg += "[部门名称：" + deptname + "]不能转成系统内码；";

		} else
			msg += "[部门名称]不能为空；";

		// 岗位名称
		String postname = map.get(columnTitle.get("岗位名称"));
		if (StringUtils.isNotEmpty(postname)) {
			this.setPostname(postname);
			Post post = nameService.getPostByName(this.getCompanyid(), this.getDeptid(), postname);
			if (post != null) {
				this.setPostid(post.getPostid());
				this.setPostcode(post.getPostcode());
			} else
				msg += "[岗位名称：" + postname + "]不能转成系统内码；";
		}

		// 性别
		String sexname = map.get(columnTitle.get("性别"));
		if (StringUtils.isNotEmpty(sexname)) {
			this.setSexname(sexname);
			Integer code = sexMap.get(sexname);
			if (code == null) {
				msg += "[性别：" + sexname + "]不能转成系统内码；";
			} else
				this.setSex(code);
		} else
			msg += "[性别]不能为空；";

		// 内网
		String innerphone = map.get(columnTitle.get("内网"));
		if (StringUtils.isNotEmpty(innerphone)) {
			this.setInnerphone(innerphone);
		}

		// 分机
		String extphone = map.get(columnTitle.get("分机"));
		if (StringUtils.isNotEmpty(extphone)) {
			this.setExtphone(extphone);
		}

		// 手机
		String mobilephone = map.get(columnTitle.get("手机"));
		if (StringUtils.isNotEmpty(mobilephone)) {
			this.setMobilephone(mobilephone);
		}

		// 邮箱
		String email = map.get(columnTitle.get("邮箱"));
		if (StringUtils.isNotEmpty(email)) {
			this.setEmail(email);
		}

		// 办公地址
		String officeaddress = map.get(columnTitle.get("办公地址"));
		if (StringUtils.isNotEmpty(officeaddress)) {
			this.setOfficeaddress(officeaddress);
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

}
