package com.mk.framework.grid.model;

public class FieldDefine {

	private String name;

	public FieldDefine() {

	}

	public FieldDefine(String tname) {
		this.setName(tname);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
