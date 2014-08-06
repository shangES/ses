package com.mk.recruitment.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.grid.GridServerHandler;
import com.mk.framework.grid.json.JSONObject;
import com.mk.framework.grid.page.PageUtils;
import com.mk.framework.grid.util.JSONUtils;
import com.mk.framework.grid.util.StringUtils;
import com.mk.framework.utils.UUIDGenerator;
import com.mk.recruitment.dao.RecruitmentDao;
import com.mk.recruitment.entity.Carousel;
import com.mk.recruitment.entity.CarouselContent;

@Service
public class CarouselService {
	@Autowired
	private SqlSession sqlSession;
	/**
	 * 搜索
	 * 
	 * @param grid
	 */
	@Transactional(readOnly = true)
	public void searchCarousel(GridServerHandler grid) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		List<JSONObject> data = new ArrayList<JSONObject>();
		// 统计
		Integer count = mapper.countCarousel(grid);
		PageUtils.setTotalRows(grid, count);

		// 搜索
		List<Carousel> list = mapper.searchCarousel(grid);
		for (Carousel model : list) {
			data.add(JSONUtils.Bean2JSONObject(model));
		}
		grid.setData(data);
	}

	/**
	 * 保存或修改
	 * 
	 * @param model
	 */
	@Transactional
	public void saveOrUpdateCarousel(Carousel model) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		if (StringUtils.isEmpty(model.getCarouselguid())) {
			//发布时间
			model.setModtime(new Date(System.currentTimeMillis()));
			model.setCarouselguid(UUIDGenerator.randomUUID());
			mapper.insertCarousel(model);
		} else {
			mapper.updateCarousel(model);
		}

		// 保存正文
		CarouselContent carouselContent = model.getCarouselcontent();
		mapper.delCarouselContentById(model.getCarouselguid());
		if (carouselContent != null && StringUtils.isNotEmpty(carouselContent.getCarouselcontent())) {
			carouselContent.setCarouselguid(model.getCarouselguid());
			mapper.insertCarouselContent(carouselContent);
		}

	}

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	public Carousel getCarouselById(String id) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		Carousel model = mapper.getCarouselById(id);
		if (model != null) {
			// 正文
			CarouselContent carouselContent = mapper.getCarouselContentById(id);
			model.setCarouselcontent(carouselContent);
		}
		return model;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transactional
	public void delCarouselById(String ids) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			mapper.delCarouselContentById(id);
			mapper.delCarouselById(id);
		}
	}

	/**
	 * 失效还原
	 * 
	 * @param ids
	 * @param state
	 */
	@Transactional
	public void validCarouselById(String ids, Integer state) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		String[] obj = ids.split(",");
		for (String id : obj) {
			Carousel model = mapper.getCarouselById(id);
			if (model != null) {
				model.setValid(state);
				mapper.updateCarousel(model);
			}
		}

	}

	/**
	 * 审核发布
	 * 
	 * @param ids
	 */
	@Transactional
	public void auditCarouselById(String ids,Integer state) {
		RecruitmentDao mapper = sqlSession.getMapper(RecruitmentDao.class);
		UserContext uc = ContextFacade.getUserContext();
		String[] obj = ids.split(",");
		for (String id : obj) {
			Carousel model = mapper.getCarouselById(id);
			if (model != null) {
				model.setIsaudited(state);
				model.setAudituser(null);
				if(state==0)
					model.setAudituser(uc.getUserId());
				mapper.updateCarousel(model);
			}
		}

	}

}
