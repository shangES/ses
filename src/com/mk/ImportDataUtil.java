package com.mk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.mk.addresslist.entity.AddressList;
import com.mk.company.entity.Company;
import com.mk.contract.entity.Contract;
import com.mk.department.entity.Department;
import com.mk.department.service.DepartmentService;
import com.mk.department.service.PostService;
import com.mk.employee.entity.Annual;
import com.mk.employee.entity.Employee;
import com.mk.employee.entity.Vacation;
import com.mk.framework.constance.Constance;
import com.mk.framework.constance.OptionConstance;
import com.mk.fuzhu.service.NameConvertCodeService;
import com.mk.quota.entity.Quota;
import com.mk.recruitprogram.entity.RecruitProgram;
import com.mk.resume.entity.Resume;

public class ImportDataUtil {

	/**
	 * 五种数据导入解析
	 * 
	 * @param nameService
	 * @param sheet
	 * @param sheetName
	 * @param columnTitle
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<?> start(NameConvertCodeService nameService, HSSFSheet sheet, String sheetName, Map<String, Integer> columnTitle,DepartmentService departmentService,PostService postService) {
		HSSFRow row = null;
		List data = new ArrayList();

		// 有权限的公司
		Map<String, Company> companyMap = nameService.getHasCompany();
		// 有权限的部门
		Map<String, Department> deptMap = nameService.getHasDepartment();

		// 导入 的条数是否过多
		if (sheet.getLastRowNum() > Constance.ConvertCodeNum) {
			if (sheetName.startsWith("员工")) {
				// 職務
				Map<String, String> jobMap = nameService.getAllJobMap();
				// 职级
				Map<String, String> rankMap = nameService.getAllRankMap();
				// 崗位
				Map<String, String> postMap = nameService.getAllPostMap();
				// 編制
				Map<String, String> quotaMap = nameService.getAllQuotaMap();
				// 状态
				Map<String, Integer> stateMap = nameService.getOptionMapByTypeCode(OptionConstance.WORKSTATE);
				// 性别
				Map<String, Integer> sexMap = nameService.getOptionMapByTypeCode(OptionConstance.SEX);
				// 文化程度
				Map<String, Integer> cultureMap = nameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
				// 民族
				Map<String, Integer> nationMap = nameService.getOptionMapByTypeCode(OptionConstance.NATION);
				// 血型
				Map<String, Integer> bloodtypeMap = nameService.getOptionMapByTypeCode(OptionConstance.BLOODTYPE);
				// 政治面貌
				Map<String, Integer> politicsMap = nameService.getOptionMapByTypeCode(OptionConstance.POLITICS);
				// 婚姻状况
				Map<String, Integer> marriedMap = nameService.getOptionMapByTypeCode(OptionConstance.MARRIED);
				// 员工类别
				Map<String, Integer> classificationMap = nameService.getOptionMapByTypeCode(OptionConstance.CLASSIFICATION);
				// 用工性质
				Map<String, Integer> employtypeMap = nameService.getOptionMapByTypeCode(OptionConstance.EMPLOYTYPE);
				// 离职原因
				Map<String, Integer> resignationreasonMap = nameService.getOptionMapByTypeCode(OptionConstance.RESIGNATIONREASON);
				// 紧急联系人关系
				Map<String, Integer> contactrelationshipMap = nameService.getOptionMapByTypeCode(OptionConstance.RELATIONSHIP);
				// 保密
				Map<String, Integer> secrecyMap = nameService.getOptionMapByTypeCode(OptionConstance.SECRETLEVEL);
				// 户籍类型
				Map<String, Integer> domicilplaceMap = nameService.getOptionMapByTypeCode(OptionConstance.DOMICILPLACE);

				// 行
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					Employee model = new Employee(companyMap, deptMap, jobMap, rankMap, postMap, quotaMap, stateMap, sexMap, cultureMap, nationMap, bloodtypeMap, politicsMap, marriedMap,
							classificationMap, employtypeMap, resignationreasonMap, contactrelationshipMap, secrecyMap, domicilplaceMap, nameService, columnTitle, row,departmentService,postService);
					data.add(model);
				}
			} else if (sheetName.startsWith("通讯录")) {
				// 性别
				Map<String, Integer> sexMap = nameService.getOptionMapByTypeCode(OptionConstance.SEX);
				// 崗位
				Map<String, String> postMap = nameService.getAllPostMap();
				// 行
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					AddressList model = new AddressList(companyMap, deptMap, postMap, sexMap, nameService, columnTitle, row);
					data.add(model);
				}
			} else if (sheetName.startsWith("请假")) {
				// 请假类型
				Map<String, Integer> vacationtypeMap = nameService.getOptionMapByTypeCode(OptionConstance.VACATIONTYPE);

				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					Vacation model = new Vacation(vacationtypeMap, nameService, columnTitle, row);
					data.add(model);
				}
			} else if (sheetName.startsWith("年休假")) {
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					Annual model = new Annual(nameService, columnTitle, row);
					data.add(model);
				}
			} else if (sheetName.startsWith("合同")) {
				// 合同类型
				Map<String, Integer> contracttypeMap = nameService.getOptionMapByTypeCode(OptionConstance.CONTRACTTYPE);
				// 合同工时
				Map<String, Integer> hourssystemMap = nameService.getOptionMapByTypeCode(OptionConstance.HOURSSYSTEM);
				// 崗位
				Map<String, String> postMap = nameService.getAllPostMap();
				// 行
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					Contract model = new Contract(companyMap, deptMap, postMap, hourssystemMap, contracttypeMap, nameService, columnTitle, row);
					data.add(model);
				}
			}else if(sheetName.startsWith("招聘计划")){
				// 崗位
				Map<String, String> postMap = nameService.getAllPostMap();
				// 职级
				Map<String, String> rankMap = nameService.getAllRankMap();
				// 編制
				Map<String, String> quotaMap = nameService.getAllQuotaMap();
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					RecruitProgram model = new RecruitProgram(companyMap, deptMap,rankMap,postMap, nameService,quotaMap,columnTitle, row);
					data.add(model);
				}
			}else if (sheetName.startsWith("编制")) {
				// 崗位
				Map<String, String> postMap = nameService.getAllPostMap();
				// 編制
				Map<String, String> budgettypeMap = nameService.getAllQuotaBudgettypeMap();
				// 行
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					Quota model = new Quota(companyMap, deptMap,postMap, budgettypeMap,nameService, columnTitle, row);
					data.add(model);
				}
			}else if (sheetName.startsWith("简历")) {
				// 文化程度
				Map<String, Integer> cultureMap = nameService.getOptionMapByTypeCode(OptionConstance.CULTURE);
				//性别
				Map<String, Integer> sexMap = nameService.getOptionMapByTypeCode(OptionConstance.SEX);
				// 工作年限
				Map<String, Integer> workageMap = nameService.getOptionMapByTypeCode(OptionConstance.WORKAGE);
				// 行
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					Resume model = new Resume(cultureMap,sexMap,workageMap,nameService, columnTitle, row);
					data.add(model);
				}
			}
		} else {
			// 行
			if (sheetName.startsWith("员工")) {
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					Employee model = new Employee(companyMap, deptMap, nameService, columnTitle, row,departmentService,postService);
					data.add(model);
				}
			} else if (sheetName.startsWith("通讯录")) {
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					AddressList model = new AddressList(companyMap, deptMap, nameService, columnTitle, row);
					data.add(model);
				}
			} else if (sheetName.startsWith("请假")) {
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					Vacation model = new Vacation(nameService, columnTitle, row);
					data.add(model);
				}
			} else if (sheetName.startsWith("年休假")) {
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					Annual model = new Annual(nameService, columnTitle, row);
					data.add(model);
				}
			}else if (sheetName.startsWith("合同")) {
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					Contract model = new Contract(companyMap, deptMap, nameService, columnTitle, row);
					data.add(model);
				}
			}else if(sheetName.startsWith("招聘计划")){
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					RecruitProgram model = new RecruitProgram(companyMap, deptMap, nameService, columnTitle, row);
					data.add(model);
				}
			}else if (sheetName.startsWith("编制")) {
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					Quota model = new Quota(companyMap, deptMap, nameService, columnTitle, row);
					data.add(model);
				}
			}else if (sheetName.startsWith("简历")) {
				for (int i = Integer.valueOf(sheet.getFirstRowNum() + 2); i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if (row == null)
						continue;
					Resume model = new Resume(nameService, columnTitle, row);
					data.add(model);
				}
			}
		}
		return data;
	}
}
