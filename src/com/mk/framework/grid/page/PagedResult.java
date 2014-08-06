package com.mk.framework.grid.page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hyong
 *
 */
public class PagedResult {
	
	private Page pageInfo;
	private List dataList;
	private boolean requirtSearchCount;
	
	PagedResult(){
		pageInfo = new Page();
		pageInfo.setTotalrows(0);
		pageInfo.init();
		dataList = new ArrayList();
	}
	
	public Page getPageInfo() {
		if(pageInfo == null){
			pageInfo = new Page();
			pageInfo.setTotalrows(0);
			pageInfo.init();
		}
		return pageInfo;
	}
	public void setPageInfo(Page pageInfo) {
		this.pageInfo = pageInfo;
	}
	public List getDataList() {
		return dataList;
	}
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	public boolean isRequirtSearchCount() {
		return requirtSearchCount;
	}

	public void setRequirtSearchCount(boolean requirtSearchCount) {
		this.requirtSearchCount = requirtSearchCount;
	}
	
	
}
