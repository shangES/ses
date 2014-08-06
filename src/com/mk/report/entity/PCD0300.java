package com.mk.report.entity;

import java.text.NumberFormat;

import com.mk.framework.constance.Constance;

public class PCD0300 {
	private String CODE;
	private String NAME;
	private int SUMNUMBER1;
	private int SUMNUMBER2;
	private int SUMNUMBER3;
	private int THISMONTH_1;
	private int THISMONTH_2;
	private int THISMONTH_3;
	private int SUMNUMBER_0;
	private int SUMNUMBER_1;
	private int SUMNUMBER_2;
	private int SUMNUMBER_3;
	private int SUMNUMBER_4;
	private int SUMNUMBER_5;
	private int SUMNUMBER_6;
	private int SUMNUMBER_7;
	private int SUMNUMBER_8;
	private int SUMNUMBER_9;
	private int SUMNUMBER_10;
	private int SUMNUMBER_11;

	// 当月完成率
	private String monthrate;
	// 全部完成率
	private String yearrate;

	public String getMonthrate() {
		return monthrate;
	}

	public void setMonthrate(String monthrate) {
		this.monthrate = monthrate;
	}

	public String getYearrate() {
		return yearrate;
	}

	public void setYearrate(String yearrate) {
		this.yearrate = yearrate;
	}

	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		CODE = cODE;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public int getSUMNUMBER_1() {
		return SUMNUMBER_1;
	}

	public void setSUMNUMBER_1(int sUMNUMBER_1) {
		SUMNUMBER_1 = sUMNUMBER_1;
	}

	public int getSUMNUMBER_2() {
		return SUMNUMBER_2;
	}

	public void setSUMNUMBER_2(int sUMNUMBER_2) {
		SUMNUMBER_2 = sUMNUMBER_2;
	}

	public int getSUMNUMBER_3() {
		return SUMNUMBER_3;
	}

	public void setSUMNUMBER_3(int sUMNUMBER_3) {
		SUMNUMBER_3 = sUMNUMBER_3;
	}

	public int getTHISMONTH_1() {
		return THISMONTH_1;
	}

	public void setTHISMONTH_1(int tHISMONTH_1) {
		THISMONTH_1 = tHISMONTH_1;
	}

	public int getTHISMONTH_2() {
		return THISMONTH_2;
	}

	public void setTHISMONTH_2(int tHISMONTH_2) {
		THISMONTH_2 = tHISMONTH_2;
	}

	public int getTHISMONTH_3() {
		return THISMONTH_3;
	}

	public void setTHISMONTH_3(int tHISMONTH_3) {
		THISMONTH_3 = tHISMONTH_3;
	}

	public int getSUMNUMBER_0() {
		return SUMNUMBER_0;
	}

	public void setSUMNUMBER_0(int sUMNUMBER_0) {
		SUMNUMBER_0 = sUMNUMBER_0;
	}

	public int getSUMNUMBER_4() {
		return SUMNUMBER_4;
	}

	public void setSUMNUMBER_4(int sUMNUMBER_4) {
		SUMNUMBER_4 = sUMNUMBER_4;
	}

	public int getSUMNUMBER_5() {
		return SUMNUMBER_5;
	}

	public void setSUMNUMBER_5(int sUMNUMBER_5) {
		SUMNUMBER_5 = sUMNUMBER_5;
	}

	public int getSUMNUMBER_6() {
		return SUMNUMBER_6;
	}

	public void setSUMNUMBER_6(int sUMNUMBER_6) {
		SUMNUMBER_6 = sUMNUMBER_6;
	}

	public int getSUMNUMBER_7() {
		return SUMNUMBER_7;
	}

	public void setSUMNUMBER_7(int sUMNUMBER_7) {
		SUMNUMBER_7 = sUMNUMBER_7;
	}

	public int getSUMNUMBER_8() {
		return SUMNUMBER_8;
	}

	public void setSUMNUMBER_8(int sUMNUMBER_8) {
		SUMNUMBER_8 = sUMNUMBER_8;
	}

	public int getSUMNUMBER_9() {
		return SUMNUMBER_9;
	}

	public void setSUMNUMBER_9(int sUMNUMBER_9) {
		SUMNUMBER_9 = sUMNUMBER_9;
	}

	public int getSUMNUMBER_10() {
		return SUMNUMBER_10;
	}

	public void setSUMNUMBER_10(int sUMNUMBER_10) {
		SUMNUMBER_10 = sUMNUMBER_10;
	}

	public int getSUMNUMBER_11() {
		return SUMNUMBER_11;
	}

	public void setSUMNUMBER_11(int sUMNUMBER_11) {
		SUMNUMBER_11 = sUMNUMBER_11;
	}

	public int getSUMNUMBER1() {
		return SUMNUMBER1;
	}

	public void setSUMNUMBER1(int sUMNUMBER1) {
		SUMNUMBER1 = sUMNUMBER1;
	}

	public int getSUMNUMBER2() {
		return SUMNUMBER2;
	}

	public void setSUMNUMBER2(int sUMNUMBER2) {
		SUMNUMBER2 = sUMNUMBER2;
	}

	public int getSUMNUMBER3() {
		return SUMNUMBER3;
	}

	public void setSUMNUMBER3(int sUMNUMBER3) {
		SUMNUMBER3 = sUMNUMBER3;
	}

	/**
	 * 翻译
	 * 
	 * 
	 */
	public void convertOneCodeToName() {
		NumberFormat numberFormat = NumberFormat.getInstance();   
	    // 设置精确到个位   
	    numberFormat.setMaximumFractionDigits(0);
		int num=this.getTHISMONTH_1()-this.getTHISMONTH_2();
		if(num!=0){
			String monthrate=numberFormat.format((float)(this.getTHISMONTH_3())/num*100);
			this.setMonthrate(monthrate);
			//this.setMonthrate(this.getTHISMONTH_3()/num*100);
		}else{
			this.setMonthrate(Constance.BISHU);
		}

		int num1=this.getSUMNUMBER1()-this.getSUMNUMBER2();
		if(num1!=0){
			String yearrate=numberFormat.format((float)(this.getSUMNUMBER3())/num1*100);
			this.setYearrate(yearrate);
			//this.setYearrate(this.getSUMNUMBER3()/num1*100);
		}else{
			this.setYearrate(Constance.BISHU);
		}
	}

}
