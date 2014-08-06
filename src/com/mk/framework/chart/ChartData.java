package com.mk.framework.chart;

import java.util.List;

public class ChartData {
	private List<ChartModel> list;
	private String name;
	private String color;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<ChartModel> getList() {
		return list;
	}

	public void setList(List<ChartModel> list) {
		this.list = list;
	}

}
