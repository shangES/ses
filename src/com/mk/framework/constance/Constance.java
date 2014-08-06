package com.mk.framework.constance;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.DataFormatter;

import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;

public class Constance {

	public static final Integer EXCEL_SHEET_DATASIZE = 65536;
	public static final Integer funTypeButton = 1;
	public static Integer funTypeMenu = 0;

	// ReadType(0:未读,1:已读)
	public static Integer readtype_Y = 1;
	public static Integer readtype_N = 0;
	
	// ExaminationType(0:合格,1:不合格)
	public static Integer ExaminationType_Zero = 0;
	public static Integer ExaminationType_One = 1;

	// 系統密碼
	public static String pwd = "123456";

	// 状态(-2 锁定,-1，黑名单，0:不匹配,1:正常,2:匹配推荐,3:未认定,4:认定面试,5:按排面试,6:复试,7:面试通过,8:面试未通过,9:待定,10:待体检,11:安排体检,12:体检通过,13:体检未通过,14:待入职,15:已经入职)
	public static final Integer CandidatesState_BlackLock = -2;
	public static final Integer CandidatesState_Blacklist = -1;
	public static final Integer CandidatesState_Zero = 0;
	public static final Integer CandidatesState_One = 1;
	public static final Integer CandidatesState_Two = 2;
	public static final Integer CandidatesState_Three = 3;
	public static final Integer CandidatesState_Four = 4;
	public static final Integer CandidatesState_Five = 5;
	public static final Integer CandidatesState_Six = 6;
	public static final Integer CandidatesState_Seven = 7;
	public static final Integer CandidatesState_Eight = 8;
	public static final Integer CandidatesState_Nine = 9;
	public static final Integer CandidatesState_Ten = 10;
	public static final Integer CandidatesState_Eleven = 11;
	public static final Integer CandidatesState_Twelve = 12;
	public static final Integer CandidatesState_Thirteen = 13;
	public static final Integer CandidatesState_Fourteen = 14;
	public static final Integer CandidatesState_Fifteen = 15;

	// 待入职人才信息状态
	public static final Integer ExaminationState_One = 1;
	public static final Integer ExaminationState_Two = 2;
	public static final Integer ExaminationState_Three = 3;
	public static final Integer ExaminationState_Four = 4;
	public static final Integer ExaminationState_Five = 5;

	// 面试结果
	public static final Integer ResultType_One = 1;
	public static final Integer ResultType_Two = 2;
	public static final Integer ResultType_Three = 3;
	public static final Integer ResultType_Four = 4;

	// 招聘计划
	public static final Integer State_Tobe = -1;
	public static final Integer State_Fail = 0;
	public static final Integer State_Normal = 1;
	public static final Integer State_Process = 2;
	public static final Integer State_Release = 3;

	// 前辍
	public static final String quota_prefix = "BZ_";
	public static final String recruitprogram_prefix = "JH_";

	// 角色名称
	public static final String RoleName = "招聘专员";
	public static final String RoleName1 = "部门认定";
	public static final String RoleName2 = "面试官";
	
	
	// 表名
	public static final String recommendTable = "J_Recommend";
	public static final String auditionRecordTable = "J_AuditionRecord";
	public static final String HrRecommendTable = "h_r_recommend";
	public static final String HrWorkexperienceTable = "h_r_workexperience";

	// 历史信息
	public static final String hulv = "过程时忽略";
	public static final String quxiao = "过程时取消";
	public static final String ZhengChang = "过程时返回正常";

	// 编制、招聘计划操作
	public static final Integer OperateState_Add = 1;
	public static final Integer OperateState_Del = 2;
	public static final Integer OperateState_Lock = 3;
	public static final Integer OperateState_edit = 4;

	// 是否系统管理员
	public static final Integer isAdmin1 = 1;
	public static final Integer isAdmin0 = 0;
	
	// 社会招聘/校园招聘
	public static final Integer SOCIETY = 1;
	public static final Integer SCHOOL = 2;

	// 系统管理员(1:注册用户,2内部用户,3:邮箱投递,4:智联招聘,5:前程无忧,6:内部竞聘,7入职推荐,8:其他)
	public static final Integer User5 = 5;
	public static final Integer User4 = 4;
	public static final Integer User3 = 3;
	public static final Integer User2 = 2;
	public static final Integer User1 = 1;
	public static final Integer User0 = 0;
	public static final Integer User6 = 6;
	public static final Integer User7 = 7;
	public static final Integer User8 = 8;

	// 图文發布
	public static final Integer Isaudited_Release = 0;

	// 有效/无效
	public static final Integer VALID_YES = 1;
	public static final Integer VALID_NO = 0;

	// 状态(-1:待生效,0:正常,1:删除)
	public static final Integer STATE_Del = 0;
	public static final Integer STATE_Normal = 1;
	public static final Integer STATE_TODO = -1;

	// 状态(1:正常,0:试用,-1:离职)
	public static final Integer WORKSTATE_Departure = -1;
	public static final Integer WORKSTATE_Try = 0;
	public static final Integer WORKSTATE_Normal = 1;

	// 操作日志
	public static final String add = "新增";
	public static final String edit = "修改";
	public static final String del = "删除";
	public static final String imp = "导入";
	public static final String refresh = "刷新";

	// 转折的数量
	public static final int ConvertCodeNum = 25;

	// 是否占编制(0:不占编制,1:占编制)
	public static final Integer IsStaff_Yes = 1;
	public static final Integer IsStaff_No = 0;
	public static final Integer IsStaff_Todo = -1;

	// 1:体检机构 2:猎头公司
	public static final Integer THIRDPARTNERTYPE_FIRST = 1;
	public static final Integer THIRDPARTNERTYPE_SECOND = 2;

	// 失效时间2天
	public static final Integer FAILURETIME = -2;
	public static final Integer VALIDTIME = 2;
	
	//一个月发短信邮件(入职)
	public static final Integer RETERENCETIME = -30;
	
	//首页消息框最新信息
	public static final Integer NewsNum1 = 1;
	public static final Integer NewsNum2 = 2;
	public static final Integer NewsNum3 = 3;
	public static final Integer NewsNum4 = 4;
	public static final Integer NewsNum5 = 5;
	public static final Integer NewsNum6 = 6;
	
	//华数传媒网络有限公司ID
	public static final String COMPANYID="00001";
	//华数传媒网络有限公司ID
	public static final String COMPANYNAME="华数传媒网络有限公司";
	//华数传媒网络有限公司人力资源部门id
	public static final String DEPTID="DD131";
	//华数传媒网络有限公司ID
	public static final String WORDNAME="简历word导入";
	
	//图片/下载路径外网查找
	public static final String PROJECTNAMEOUTER ="hrmweb_www";
	
	//部门认定
	public static final String DEPARTMENTBRANCH = "部门认定";
	
	//面试官
	public static final String INTERVIEWER="面试官";
	
	//百分比
	public static final String baifenbi="0.00";
	//百分比
	public static final String BISHU="0";
	
	/**
	 * 得到Cell的值
	 * 
	 * @param cell
	 * @return
	 */
	public static final String getIsStaffName(int isstaff) {
		if (Constance.IsStaff_Yes == isstaff)
			return "占编";
		else if (Constance.IsStaff_No == isstaff)
			return "兼任";
		return "异动";
	}

	/**
	 * 得到Cell的值
	 * 
	 * @param cell
	 * @return
	 */
	public static final String getCellValue(HSSFCell cell) {
		if (cell == null)
			return "";

		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: {
			// 有一点要注意的是，数值型和日期型都是「CELL_TYPE_NUMERIC」。
			// 到底是数值型还是日期型，可以通过「org.apache.poi.hssf.usermodel.HSSFDateUtil」类的「isCellDateFormatted」方法来检证。
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				double d = cell.getNumericCellValue();
				return new java.sql.Date(HSSFDateUtil.getJavaDate(d).getTime()) + "";
			}
			DataFormatter format = new DataFormatter();
			return format.formatCellValue(cell);
		}
		case HSSFCell.CELL_TYPE_STRING: {
			return cell.getStringCellValue();
		}
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case HSSFCell.CELL_TYPE_BLANK:
			return "";
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() + "";
		case HSSFCell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue() + "";
		}
		return "";
	}

	/**
	 * 组装带有顺序的CODE
	 * 
	 * @param parentCode
	 * @param maxCode
	 * @return
	 */
	public static String setSortCode(String pid, String maxid) {
		StringBuffer id = new StringBuffer();
		int count = 0;
		if (pid == null || pid.equals("")) {
			count = (maxid == null ? 1 : Integer.parseInt(maxid) + 1);
		} else {
			if (maxid == null)
				count = 1;
			else {
				String tmpId = maxid.substring(maxid.lastIndexOf("_") + 1, maxid.length());
				count = Integer.parseInt(tmpId) + 1;
			}
		}
		if (count >= 0 && count <= 9)
			id.append("000").append(count);
		else if (count > 9 && count <= 99)
			id.append("00").append(count);
		else
			id.append("0").append(count);
		if (pid != null && !pid.equals(""))
			return pid + "_" + id.toString();
		return id.toString();
	}

	/**
	 * 初始化页面参数 检查页面是否传来用户和部门（这里要区分系统管理员）
	 * 
	 * @param grid
	 * @param sysmapper
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final void initAdminPam(Map map) {
		UserContext uc = ContextFacade.getUserContext();
		map.put("admin", uc.isAdmin());
		map.put("userguid", uc.getUserId());
		map.put("username", uc.getUsername());
	}

}
