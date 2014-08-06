package com.mk.recruitment.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.util.ReportUtil;
import com.mk.recruitment.entity.Carousel;
import com.mk.recruitment.service.CarouselService;

@Controller
public class CarouselAction {
	@Autowired
	private CarouselService carouselService;

	/**
	 * 搜索
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/searchCarousel.do")
	public void searchCarousel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridServerHandler grid = new GridServerHandler(request, response);
		carouselService.searchCarousel(grid);
		if (Boolean.valueOf(grid.getParameter("export"))) {
			ReportUtil report = new ReportUtil();
			report.setTitle("轮播图片");
			report.reportGrid(grid);
		} else
			grid.printLoadResponseText();

	}

	/**
	 * 保存or修改选项类型
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/recruitment/saveOrUpdateCarousel.do")
	@ResponseBody
	public Carousel saveOrUpdateCarousel(Carousel model) {
		carouselService.saveOrUpdateCarousel(model);
		return model;
	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/recruitment/getCarouselById.do")
	@ResponseBody
	public Carousel getCarouselById(String id) {
		Carousel model = carouselService.getCarouselById(id);
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/delCarouselById.do")
	@ResponseBody
	public void delCarouselById(String ids) throws Exception {
		carouselService.delCarouselById(ids);
	}

	/**
	 * 失效/还原
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/validCarouselById.do")
	@ResponseBody
	public void validCarouselById(String ids, Integer state) throws Exception {
		carouselService.validCarouselById(ids, state);
	}

	/**
	 * 审核发布
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/recruitment/auditCarouselById.do")
	@ResponseBody
	public void auditCarouselById(String ids,Integer state) throws Exception {
		carouselService.auditCarouselById(ids,state);
	}
}
