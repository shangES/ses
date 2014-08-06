package com.mk.framework.grid.util;

public class ChartUtil {

	public static String buildXML() {
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<anychart>");
		xml.append("<settings>");
		xml.append("<animation enabled=\"True\"/>");
		xml.append("</settings>");
		xml.append("<charts>");
		xml.append("<chart plot_type=\"CategorizedVertical\">");
		xml.append("<data_plot_settings default_series_type=\"Bar\" enable_3d_mode=\"true\" z_aspect=\"2.5\">");
		xml.append("<bar_series group_padding=\"0.2\">");
		xml.append("<tooltip_settings enabled=\"true\"/>");
		xml.append("</bar_series>");
		xml.append("</data_plot_settings>");
		xml.append("<chart_settings>");
		xml.append("<title enabled=\"true\">");
		xml.append("<text>Simple Single-Series Column Chart</text>");
		xml.append("</title>");
		xml.append("</chart_settings>");
		xml.append("<data>");
		xml.append("<series name=\"Series 1\">");
		
		xml.append("<point name=\"P1\" y=\"128.14\"/>");
		xml.append("<point name=\"P2\" y=\"112.61\"/>");
		xml.append("<point name=\"P3\" y=\"163.21\"/>");
		xml.append("<point name=\"P4\" y=\"229.98\"/>");
		xml.append("<point name=\"P5\" y=\"90.54\"/>");
		xml.append("<point name=\"P6\" y=\"104.19\"/>");
		xml.append("<point name=\"P7\" y=\"150.67\"/>");
		xml.append("<point name=\"P8\" y=\"120.43\"/>");
		xml.append("<point name=\"P9\" y=\"143.76\"/>");
		xml.append("<point name=\"P10\" y=\"191.34\"/>");
		xml.append("<point name=\"P11\" y=\"134.17\"/>");
		xml.append("<point name=\"P12\" y=\"145.72\"/>");
		xml.append("<point name=\"P13\" y=\"222.56\"/>");
		xml.append("<point name=\"P14\" y=\"187.12\"/>");
		xml.append("<point name=\"P15\" y=\"154.32\"/>");
		xml.append("<point name=\"P16\" y=\"133.08\"/>");
		
		xml.append("</series>");
		xml.append("</data>");
		xml.append("</chart>");
		xml.append("</charts>");
		xml.append("</anychart>");
		return null;
	}

}
