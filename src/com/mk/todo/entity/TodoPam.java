package com.mk.todo.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoPam {
	private Map parameters;
	private List data;
	
	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public TodoPam() {
		parameters = new HashMap();
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

}
