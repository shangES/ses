package com.mk.report.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.report.service.ReportService;
import com.mk.report.util.ReportPCD0201;
import com.mk.report.util.ReportPCD0202;
import com.mk.report.util.ReportPCD0203;
import com.mk.report.util.ReportPCD0204;
import com.mk.report.util.ReportPCD0205;
import com.mk.report.util.ReportPCD0206;
import com.mk.report.util.ReportPCD0207;
import com.mk.report.util.ReportPCD0208;

@Controller
public class ReportAction {
	@Autowired
	private ReportService reportService;

	/**
	 * PCD_0101 传媒花名册
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0101.do")
	public void searchPCD0101(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0101(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("传媒花名册");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0102 集团花名册
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0102.do")
	public void searchPCD_0102(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0102(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("集团花名册");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0103 新入职人员报表
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0103.do")
	public void searchPCD_0103(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0103(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("新入职人员报表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0104 离职人员报表
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0104.do")
	public void searchPCD_0104(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0104(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("离职人员报表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0105 异动人员报表
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0105.do")
	public void searchPCD_0105(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0105(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("异动人员报表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0107 员工生日报表
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0107.do")
	public void searchPCD_0107(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0107(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("员工生日报表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0111 工龄报表
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0111.do")
	public void searchPCD_0111(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0111(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("工龄报表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0113 待转正员工报表
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0113.do")
	public void searchPCD_0113(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0113(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("待转正员工报表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0201 员工类别统计表(分性别员工数量统计表)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0201.do")
	public void searchPCD_0201(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0201(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportPCD0201 report = new ReportPCD0201();
			report.setTitle("分性别员工数量统计表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0202 员工类别统计表(分学历员工数量统计表)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0202.do")
	public void searchPCD_0202(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0202(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportPCD0202 report = new ReportPCD0202();
			report.setTitle("分学历员工数量统计表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0203 员工类别统计表(分年龄段员工数量统计表)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0203.do")
	public void searchPCD_0203(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0203(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportPCD0203 report = new ReportPCD0203();
			report.setTitle("分年龄段员工数量统计表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0204 员工类别统计表(分工龄段员工数量统计表)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0204.do")
	public void searchPCD_0204(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0204(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportPCD0204 report = new ReportPCD0204();
			report.setTitle("分工龄段员工数量统计表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0205 员工类别统计表(分政治面貌员工数量统计表)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0205.do")
	public void searchPCD_0205(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0205(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportPCD0205 report = new ReportPCD0205();
			report.setTitle("分政治面貌员工数量统计表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0206 员工类别统计表(分职称认证员工数量统计表)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0206.do")
	public void searchPCD_0206(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0206(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportPCD0206 report = new ReportPCD0206();
			report.setTitle("分职称认证员工数量统计表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0207 员工类别统计表(分职务员工数量统计表)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0207.do")
	public void searchPCD_0207(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0207(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportPCD0207 report = new ReportPCD0207();
			report.setTitle("分职务员工数量统计表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}

	/**
	 * PCD_0208 员工类别统计表(分性别及婚姻状况员工数量统计表)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0208.do")
	public void searchPCD_0208(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD0208(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportPCD0208 report = new ReportPCD0208();
			report.setTitle("分性别及婚姻状况员工数量统计表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}
	
	/**
	 * PCD_0301 总体编制情况表
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0301.do")
	public void searchPCD_0301(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD_0301(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("总体编制情况表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}
	
	/**
	 * PCD_0302 增编统计表
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0302.do")
	public void searchPCD_0302(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD_0302(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("增编统计表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}
	
	/**
	 * PCD_0303 招聘计划表
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0303.do")
	public void searchPCD_0303(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD_0303(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("招聘计划表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}
	
	/**
	 * PCD_0304 渠道统计表
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0304.do")
	public void searchPCD_0304(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD_0304(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("渠道统计表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}
	
	/**
	 * PCD_0305 招聘专员推进情况汇总表
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0305.do")
	public void searchPCD_0305(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD_0305(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("招聘专员推进情况汇总表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}
	
	/**
	 * PCD_0306 岗位推进情况
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0306.do")
	public void searchPCD_0306(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD_0306(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("岗位推进情况表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}
	
	/**
	 * PCD_0307 面试官面试情况表
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0307.do")
	public void searchPCD_0307(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD_0307(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("面试官面试情况表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}
	
	
	/**
	 * PCD_0308 总体编制情况表
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/report/searchPCD0308.do")
	public void searchPCD_0308(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		reportService.searchPCD_0308(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("总体编制情况表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();
	}
}
