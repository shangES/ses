package com.mk.system.entity;

import java.io.Serializable;

public class Function implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String funid;
	private String funpid;
	private Integer orderid;
	private String labelname;
	private Integer funtype;
	private String javaevent;
	private String rmk;
	private String titlepicture;

	public String getFunid() {
		return funid;
	}

	public void setFunid(String funid) {
		this.funid = funid;
	}

	public String getFunpid() {
		return funpid;
	}

	public void setFunpid(String funpid) {
		this.funpid = funpid;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getLabelname() {
		return labelname;
	}

	public void setLabelname(String labelname) {
		this.labelname = labelname;
	}

	public Integer getFuntype() {
		return funtype;
	}

	public void setFuntype(Integer funtype) {
		this.funtype = funtype;
	}

	public String getJavaevent() {
		return javaevent;
	}

	public void setJavaevent(String javaevent) {
		this.javaevent = javaevent;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	public String getTitlepicture() {
		return titlepicture;
	}

	public void setTitlepicture(String titlepicture) {
		this.titlepicture = titlepicture;
	}

	@Override
	public String toString() {
		return "Function [funid=" + funid + ", funpid=" + funpid + ", orderid=" + orderid + ", labelname=" + labelname + ", funtype=" + funtype + ", javaevent=" + javaevent + ", rmk=" + rmk
				+ ", titlepicture=" + titlepicture + "]";
	}

}
