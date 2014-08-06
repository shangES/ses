package com.mk.recruitment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mk.framework.grid.GridServerHandler;
import com.mk.recruitment.entity.About;
import com.mk.recruitment.entity.AboutContent;
import com.mk.recruitment.entity.Carousel;
import com.mk.recruitment.entity.CarouselContent;
import com.mk.recruitment.entity.Category;
import com.mk.recruitment.entity.News;
import com.mk.recruitment.entity.NewsContent;
import com.mk.recruitment.entity.NewsModule;
import com.mk.recruitment.entity.PostContent;
import com.mk.recruitment.entity.RecruitPost;
import com.mk.recruitment.entity.WorkPlace;

public interface RecruitmentDao {
	// ======================职位类别======================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countCategory(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Category> searchCategory(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertCategory(Category model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateCategory(Category model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Category getCategoryById(String categoryguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delCategoryById(String categoryguid);

	/**
	 * 得到所有的职位类别
	 * 
	 * @param state
	 * @return
	 */
	List<Category> getAllCategory(@Param(value = "valid") Integer valid);
	
	// ======================工作地点======================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countWorkPlace(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<WorkPlace> searchWorkPlace(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertWorkPlace(WorkPlace model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateWorkPlace(WorkPlace model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	WorkPlace getWorkPlaceById(String workplaceguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delWorkPlaceById(String workplaceguid);

	/**
	 * 得到所有的工作地点
	 * 
	 * @param state
	 * @return
	 */
	List<WorkPlace> getAllWorkPlace(@Param(value = "valid") Integer valid);

	// ======================招聘职位======================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countRecruitPost(GridServerHandler grid);
	
	/**
	 * 内部职位统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countInternalPost(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<RecruitPost> searchRecruitPost(GridServerHandler grid);
	
	/**
	 * 内部职位
	 * 
	 * @param grid
	 * @return
	 */
	List<RecruitPost> searchInternalPost(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertRecruitPost(RecruitPost model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateRecruitPost(RecruitPost model);

	/**
	 * 删除
	 * 
	 * @param recruitpostguid
	 */
	void delRecruitPostByRecruitPostId(String recruitpostguid);

	/**
	 * 得到
	 * 
	 * @param recruitpostguid
	 * @return
	 */
	RecruitPost getRecruitPostByRecruitPostId(String recruitpostguid);

	/**
	 * 得到
	 * 
	 * @param postname
	 * @return
	 */
	List<RecruitPost> getRecruitPostByRecruitPostName(String postname);

	/**
	 * 得到全部
	 * 
	 * @return
	 */
	List<RecruitPost> getAllRecruitPost();

	/**
	 * 得到招聘专员下的招聘职位
	 * 
	 * @param valid
	 * @return
	 */
	List<RecruitPost> getRecruitPostByUser(@Param(value = "userguid") String userguid);
	
	
	// =====================我的竞聘======================
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countMyRecruitpost(GridServerHandler grid);
	
	/**
	 * 得到
	 * 
	 * @param grid
	 * @return
	 */
	List<RecruitPost> searchMyRecruitpost(GridServerHandler grid);
	
	

	// ======================职位介绍======================

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertPostContent(PostContent model);

	/**
	 * 删除
	 * 
	 * @param recruitpostguid
	 */
	void delPostContentByRecruitPostId(String recruitpostguid);

	/**
	 * 得到
	 * 
	 * @param recruitpostguid
	 * @return
	 */
	PostContent getPostContentByRecruitPostId(String recruitpostguid);

	// ------------------------轮播图片--------------------
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countCarousel(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<Carousel> searchCarousel(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertCarousel(Carousel model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateCarousel(Carousel model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	Carousel getCarouselById(String carouselguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delCarouselById(String carouselguid);

	// ------------------------轮播图文--------------------
	/**
	 * 保存
	 * 
	 * @param carouselContent
	 */
	void insertCarouselContent(CarouselContent carouselContent);

	/**
	 * 删除
	 * 
	 * @param carouselguid
	 */
	void delCarouselContentById(String carouselguid);

	/***
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	CarouselContent getCarouselContentById(String carouselguid);

	// ------------------------咨询内容--------------------
	/**
	 * 统计
	 * 
	 * @param grid
	 * @return
	 */
	Integer countNews(GridServerHandler grid);

	/**
	 * 搜索
	 * 
	 * @param grid
	 * @return
	 */
	List<News> searchNews(GridServerHandler grid);

	/**
	 * 保存
	 * 
	 * @param model
	 */
	void insertNews(News model);

	/**
	 * 修改
	 * 
	 * @param model
	 */
	void updateNews(News model);

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	News getNewsById(String newsguid);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delNewsById(String newsguid);

	// ------------------------资讯正文--------------------
	/**
	 * 保存
	 * 
	 * @param newsContent
	 */
	void insertNewsContent(NewsContent newsContent);

	/**
	 * 删除
	 * 
	 * @param newsguid
	 */
	void delNewsContentById(String newsguid);

	/***
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	NewsContent getNewsContentById(String newsguid);

	// ================咨询模块===================

	/**
	 * 所有咨询模块
	 * 
	 * @return
	 */
	List<NewsModule> getAllNewsModule();

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	NewsModule getNewsModuleById(String moduleguid);

	// ================关于华数===================
	/**
	 * 所有
	 * 
	 * @return
	 */
	List<About> getAllAbout();

	/**
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	About getAboutById(String aboutguid);

	// ------------------------关于华数正文--------------------
	/**
	 * 保存
	 * 
	 * @param aboutContent
	 */
	void insertAboutContent(AboutContent aboutContent);

	/**
	 * 删除
	 * 
	 * @param newsguid
	 */
	void delAboutContentById(String aboutguid);

	/***
	 * 得到
	 * 
	 * @param id
	 * @return
	 */
	AboutContent getAboutContentById(String aboutguid);

	/**
	 * 统计当前职位投递简历次数
	 * 
	 * @param recruitpostguid
	 * @return
	 */
	Integer countMyCandidatesByRecruitPostGuid(String recruitpostguid);

	/**
	 * 统计当前职位昨日投递简历次数
	 * 
	 * @param recruitpostguid
	 * @param time
	 * @return
	 */
	Integer countMyCandidatesByRecruitPostGuidAndTime(@Param(value = "recruitpostguid") String recruitpostguid, @Param(value = "_datas") String _datas, @Param(value = "_datae") String _datae);

	// ------------------------我的收藏--------------------
	/**
	 * 删除
	 * 
	 * @param myfavoritesguid
	 */
	void delMyFavoritesByRecruitPostGuid(String recruitpostguid);
}
