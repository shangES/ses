package com.mk.report.dao;

import java.util.List;

import com.mk.framework.grid.GridServerHandler;
import com.mk.report.entity.PCD0300;
import com.mk.todo.entity.TodoPam;

public interface ReportDao {

	/**
	 * PCD_0101 传媒花名册
	 * 
	 * @param grid
	 */
	void searchPCD0101(GridServerHandler grid);

	/**
	 * PCD_0102 集团花名册
	 * 
	 * @param grid
	 */
	void searchPCD0102(GridServerHandler grid);

	/**
	 * PCD_0103 新入职人员报表
	 * 
	 * @param grid
	 */
	void searchPCD0103(GridServerHandler grid);

	/**
	 * PCD_0104 离职人员报表
	 * 
	 * @param grid
	 */
	void searchPCD0104(GridServerHandler grid);

	/**
	 * PCD_0105 异动人员报表
	 * 
	 * @param grid
	 */
	void searchPCD0105(GridServerHandler grid);
	
	/**
	 * PCD_0107 员工生日报表
	 * 
	 * @param grid
	 */
	void searchPCD0107(GridServerHandler grid);
	
	/**
	 * PCD_0111 工龄报表
	 * 
	 * @param grid
	 */
	void searchPCD0111(GridServerHandler grid);

	
	/**
	 * PCD_0113 待转正员工报表
	 * 
	 * @param grid
	 */
	void searchPCD0113(GridServerHandler grid);
	
	/**
	 * PCD_0201 员工类别统计表(分性别员工数量统计表)
	 * 
	 * @param grid
	 */
	void searchPCD0201(GridServerHandler grid);
	
	/**
	 * PCD_0202 员工类别统计表(分学历员工数量统计表)
	 * 
	 * @param grid
	 */
	void searchPCD0202(GridServerHandler grid);
	
	/**
	 * PCD_0203 员工类别统计表(分年龄段员工数量统计表)
	 * 
	 * @param grid
	 */
	void searchPCD0203(GridServerHandler grid);
	
	/**
	 * PCD_0204 员工类别统计表(分工龄段员工数量统计表)
	 * 
	 * @param grid
	 */
	void searchPCD0204(GridServerHandler grid);
	
	/**
	 * PCD_0205 员工类别统计表(分政治面貌员工数量统计表)
	 * 
	 * @param grid
	 */
	void searchPCD0205(GridServerHandler grid);
	
	/**
	 * PCD_0206 员工类别统计表(分职称认证员工数量统计表)
	 * 
	 * @param grid
	 */
	void searchPCD0206(GridServerHandler grid);
	
	/**
	 * PCD_0207 员工类别统计表(分职务员工数量统计表)
	 * 
	 * @param grid
	 */
	void searchPCD0207(GridServerHandler grid);
	
	/**
	 * PCD_0208 员工类别统计表(分性别及婚姻状况员工数量统计表)
	 * 
	 * @param grid
	 */
	void searchPCD0208(GridServerHandler grid);
	
	/**
	 * 渠道统计表
	 * 
	 * @param grid
	 */
	void searchPCD0304(GridServerHandler grid);

	/**
	 *  总体编制情况表
	 * 
	 * @param grid
	 */
	void searchPCD0301(GridServerHandler grid);

	
	/**
	 * PCD_0302 增编统计表
	 * 
	 * @param grid
	 */
	void searchPCD0302(GridServerHandler grid);

	
	/**
	 * PCD_0303 招聘计划表
	 * 
	 * @param grid
	 */
	void searchPCD0303(GridServerHandler grid);

	
	/**
	 * PCD_0305 招聘专员推进情况汇总表
	 * 
	 * @param grid
	 */
	void searchPCD0305(GridServerHandler grid);

	
	/**
	 *  PCD_0306 岗位推进情况表
	 * 
	 * @param grid
	 */
	void searchPCD0306(GridServerHandler grid);
	
	/**
	 *  PCD_0307 面试官面试情况表
	 * 
	 * @param grid
	 */
	void searchPCD0307(GridServerHandler grid);

	
	/**
	 * 得到招聘计划统计表
	 * 
	 * @param pam
	 */
	List<PCD0300> searchPCD0300(TodoPam pam);
	
	/**
	 *  PCD_0308 总体编制情况表
	 * 
	 * @param grid
	 */
	void searchPCD0308(GridServerHandler grid);
}
