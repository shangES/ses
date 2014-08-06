package com.mk.employee.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.employee.entity.Certification;
import com.mk.employee.service.CertificationService;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;

@Controller
public class CertificationAction {
	@Autowired
	private CertificationService certificationService;
	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/searchCertification.do")
	public void searchCertification(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);

		certificationService.searchCertification(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("职称认证信息表");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();

	}

	/**
	 * 保存
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/saveOrUpdateCertification.do")
	@ResponseBody
	public Certification saveOrUpdateCertification(Certification model) throws Exception {
		certificationService.saveOrUpdateCertification(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/getCertificationById.do")
	@ResponseBody
	public Certification getCertificationById(String id) throws Exception {
		Certification model = certificationService.getCertificationById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/employee/delCertificationById.do")
	@ResponseBody
	public void delCertificationById(String ids) throws Exception {
		certificationService.delCertificationById(ids);
	}
	
}
