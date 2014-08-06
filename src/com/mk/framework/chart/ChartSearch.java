package com.mk.framework.chart;

public class ChartSearch {
	private String type;
	private boolean legendEnabled;
	private String legendPosition;
	private boolean s3d;
	private String cylinder;
	private String color;

	private String id;
	private boolean isonly;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isLegendEnabled() {
		return legendEnabled;
	}

	public void setLegendEnabled(boolean legendEnabled) {
		this.legendEnabled = legendEnabled;
	}

	public String getLegendPosition() {
		return legendPosition;
	}

	public void setLegendPosition(String legendPosition) {
		this.legendPosition = legendPosition;
	}

	public boolean isS3d() {
		return s3d;
	}

	public void setS3d(boolean s3d) {
		this.s3d = s3d;
	}

	public boolean isIsonly() {
		return isonly;
	}

	public void setIsonly(boolean isonly) {
		this.isonly = isonly;
	}

	public String getCylinder() {
		return cylinder;
	}

	public void setCylinder(String cylinder) {
		this.cylinder = cylinder;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "ChartSearch [type=" + type + ", legendEnabled=" + legendEnabled + ", legendPosition=" + legendPosition + ", s3d=" + s3d + ", cylinder=" + cylinder + ", color=" + color + ", id=" + id
				+ ", isonly=" + isonly + "]";
	}

}
