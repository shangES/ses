package com.mk.framework.grid.model;

public class ColumnInfo implements IModel {

	private String id;
	private int width;
	private String header;
	private String fieldIndex;
	private String sortOrder;
	private boolean hidden;
	private boolean exportable;
	private boolean printable;
	private boolean isnumber;

	public ColumnInfo() {

	}

	public boolean isIsnumber() {
		return isnumber;
	}

	public void setIsnumber(boolean isnumber) {
		this.isnumber = isnumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFieldIndex() {
		return fieldIndex;
	}

	public void setFieldIndex(String fieldIndex) {
		this.fieldIndex = fieldIndex;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isExportable() {
		return exportable;
	}

	public void setExportable(boolean exportable) {
		this.exportable = exportable;
	}

	public boolean isPrintable() {
		return printable;
	}

	public void setPrintable(boolean printable) {
		this.printable = printable;
	}
}
