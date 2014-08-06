package com.mk.framework.tree;

import java.util.List;
import java.util.Map;

import com.mk.framework.grid.page.Page;

public class TreePageGrid {
	private List dataList;
	private Page page;
	private Map<?, ?> parameters;

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Map<?, ?> getParameters() {
		return parameters;
	}

	public void setParameters(Map<?, ?> parameters) {
		this.parameters = parameters;
	}

}
