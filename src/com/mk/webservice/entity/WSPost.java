package com.mk.webservice.entity;

public class WSPost {
	private String interfacecode;
	private String postname;

	public String getInterfacecode() {
		return interfacecode;
	}

	public void setInterfacecode(String interfacecode) {
		this.interfacecode = interfacecode;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	@Override
	public String toString() {
		return "WSPost [interfacecode=" + interfacecode + ", postname=" + postname + "]";
	}

}
