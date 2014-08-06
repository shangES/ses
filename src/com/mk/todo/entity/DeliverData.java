package com.mk.todo.entity;

public class DeliverData {
	private int totalDeliver;
	private int todayDeliver;
	private int averageDeliver;
	private String recommendnum;
	private String sumrecommends;

	public String getSumrecommends() {
		return sumrecommends;
	}

	public void setSumrecommends(String sumrecommends) {
		this.sumrecommends = sumrecommends;
	}

	public int getTotalDeliver() {
		return totalDeliver;
	}

	public void setTotalDeliver(int totalDeliver) {
		this.totalDeliver = totalDeliver;
	}

	public int getTodayDeliver() {
		return todayDeliver;
	}

	public void setTodayDeliver(int todayDeliver) {
		this.todayDeliver = todayDeliver;
	}

	public int getAverageDeliver() {
		return averageDeliver;
	}

	public void setAverageDeliver(int averageDeliver) {
		this.averageDeliver = averageDeliver;
	}

	public String getRecommendnum() {
		return recommendnum;
	}

	public void setRecommendnum(String recommendnum) {
		this.recommendnum = recommendnum;
	}

	@Override
	public String toString() {
		return "DeliverData [totalDeliver=" + totalDeliver + ", todayDeliver=" + todayDeliver + ", averageDeliver=" + averageDeliver + ", recommendnum=" + recommendnum + "]";
	}

}
