package com.mk.framework.grid.model;


public class ColumnDefine {
	private String id;
	private boolean isCheckColumn;
	private boolean frozen;
	private boolean hidden;
	private boolean editable;
	private String align;
	private String header;
	private Editor editor;
	private String width;

	public ColumnDefine() {
		width = "100";
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCheckColumn(boolean isCheckColumn) {
		this.isCheckColumn = isCheckColumn;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public Editor getEditor() {
		return editor;
	}

	public void setEditor(Editor editor) {
		this.editor = editor;
	}

	public String getWidth() {
		return width;
	}

	public String getId() {
		return id;
	}

	public boolean isCheckColumn() {
		return isCheckColumn;
	}

	public boolean isHidden() {
		return hidden;
	}

	public boolean isEditable() {
		return editable;
	}

	public String getAlign() {
		return align;
	}

	public String getHeader() {
		return header;
	}

}
