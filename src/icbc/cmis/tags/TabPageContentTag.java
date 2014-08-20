package icbc.cmis.tags;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import icbc.cmis.base.CmisConstance;
/**
 * Title:        cmis
 * Description:  cmis3
 * Copyright:    Copyright (c) 2001
 * Company:      icbc
 * @author icbc
 * @version 1.0
 * ZYZ200704修改，增加infoid属性，支持从语言包中读取info字符串
 * ZYZ200704修改，增加para属性，支持info字符串变量替换
 */

public class TabPageContentTag extends TagSupport {
	private String width = "100%";
	private String height = "100%";
	private String align = "left";
	private String valign = "top";
	private String info = "";

	private String sDef;
	private String infoID = "";

	private String para = "";
	private static String sPageDef = "icbc.cmis.icbc_cmis";
	private static String sPageItem = "PAGEDEF";

	public TabPageContentTag() {
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public void setValign(String valign) {
		this.valign = valign;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setInfoid(String infoid) {
		this.infoID = infoid;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public int doStartTag() throws javax.servlet.jsp.JspException {
		String webBasePath = (String)CmisConstance.getParameterSettings().get("webBasePath");
		try {
			String sInfo = ""; //最终输出info

			if (!infoID.equals("")) {
				if (infoID.equals(sPageItem)) {
					//如果是特定翻页信息，则从共用语言包获取字符串
					try {
						String language = "";
						try {
							HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
							icbc.cmis.base.CMisSessionMgr sm = new icbc.cmis.base.CMisSessionMgr(request);
							language = (String)sm.getSessionData("LangCode");
							//language = (String)pageContext.getSession().getAttribute("LangCode");
						} catch (Exception eee) {
							language = "zh_CN";
						}
						String sStr = MuiTagBase.getStr(sPageDef, language, sPageItem);
						StringTokenizer token = new StringTokenizer(para, "|");
						while (token.hasMoreTokens()) {
							String temp = token.nextToken();
							sStr = this.replaceOnce(sStr, "$", temp);
						}
						sInfo = sStr;
					} catch (Exception e) {
						Debug.println("!Error Tag:: " + sDef + ":" + infoID);
					}

				} else {
					//其他情况则从私用语言包获取字符串
					try {
						String language = "";
						try {
							HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
							icbc.cmis.base.CMisSessionMgr sm = new icbc.cmis.base.CMisSessionMgr(request);
							language = (String)sm.getSessionData("LangCode");
							//language = pageContext.getRequest().getParameter("LangCode");
						} catch (Exception eee) {
							language = "zh_CN";
						}
						MuiTagParent tagParent = (MuiTagParent)findAncestorWithClass(this, MuiTagParent.class);
						sDef = tagParent.gotDef();
						String sStr = MuiTagBase.getStr(sDef, language, infoID);
						StringTokenizer token = new StringTokenizer(para, "|");
						while (token.hasMoreTokens()) {
							String temp = token.nextToken();
							sStr = this.replaceOnce(sStr, "$", temp);
						}
						sInfo = sStr;
					} catch (Exception e) {
						Debug.println("!Error Tag:: " + sDef + ":" + infoID);
					}
				}
			} else {
				sInfo = info;
			}

			JspWriter out = this.pageContext.getOut();
			out.println("        <td background=\"" + webBasePath + "/images/p7.gif\" width=\"12\"></td>");
			out.println("        <td>" + sInfo + "</td>");
			out.println("      </tr>");
			out.println("    </table>");
			out.println("<!---------------------end of tabpage bar-------------------------------->");
			out.println("    </td>");
			out.println("    <td background=\"" + webBasePath + "/images/p8.gif\" width=\"7\"></td>");
			out.println("    <td width=\"7\"><img src=\"" + webBasePath + "/images/p9.gif\" width=\"7\" height=\"32\"></td>");
			out.println("  </tr>");
			out.println("  <tr height=\"7\"> ");
			out.println("    <td width=\"7\" class=\"td1\"></td>");
			out.println("    <td class=\"td1\"></td>");
			out.println("    <td width=\"7\" class=\"td1\"></td>");
			out.println("    <td width=\"7\" background=\"" + webBasePath + "/images/p11.gif\"></td>");
			out.println("  </tr>");
			out.println("  <tr> ");
			out.println("    <td width=\"7\" class=\"td1\"></td>");
			out.println("    <td align=\"" + align + "\" valign=\"" + valign + "\">");
			out.println("<!---------------------begin of tabpage content-------------------------------->");
		} catch (Exception ex) {
			throw new JspException("FrameWorkTag doInitBody() error." + ex.getMessage());
		}
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws javax.servlet.jsp.JspException {
		String baseWebPath = (String)CmisConstance.getParameterSettings().get("webBasePath");
		try {
			JspWriter out = this.pageContext.getOut();
			out.println("<!---------------------end of tabpage content-------------------------------->");
			out.println("    </td>");
			out.println("    <td width=\"7\" class=\"td1\"></td>");
			out.println("    <td width=\"7\" background=\"" + baseWebPath + "/images/p11.gif\"></td>");
			out.println("  </tr>");
			out.println("  <tr height=\"7\"> ");
			//out.println("    <td width=\"7\"  class=\"td1\"></td>");
			out.println(
				"    <td width=\"7\"   class=\"td1\"><img src=\"" + baseWebPath + "/images/p10.gif\" width=\"7\" height=\"7\"></td>");
			out.println("    <td class=\"td1\"></td>");
			out.println(
				"    <td width=\"7\"   class=\"td1\"><img src=\"" + baseWebPath + "/images/p10.gif\" width=\"7\" height=\"7\"></td>");
			out.println("    <td width=\"7\"><img src=\"" + baseWebPath + "/images/p12.gif\"></td>");
			out.println("  </tr>");
			out.println("</table>");
		} catch (Exception ex) {
			throw new JspException("FrameWorkTag doAfterBody() error." + ex.getMessage());
		}
		return EVAL_PAGE;
	}

	public int findStr(StringBuffer strb, String str, int begin) {
		int strlen = str.length();
		int ret = -1;
		for (int i = begin; i <= strb.length() - strlen; i++) {
			if (strb.substring(i, i + strlen).equals(str)) {
				ret = i;
				break;
			}
		}
		return ret;
	}

	public String replaceOnce(String strb, String src, String des) {
		if (src.equals(des)) {
			return strb;
		}
		int i, j;
		int srclen = src.length();
		StringBuffer strb2 = new StringBuffer(strb);
		j = 0;
		if ((i = this.findStr(strb2, src, j)) != -1) {
			strb2.delete(i, i + srclen);
			strb2.insert(i, des);
			j = i + des.length();
		}
		return strb2.toString();
	}
}