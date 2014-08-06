package com.mk.framework.grid.model;

public class Editor {
	private String type;

	public Editor() {

		this.setType("text");
	}

	public Editor(String str) {

		this.setType(str);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
