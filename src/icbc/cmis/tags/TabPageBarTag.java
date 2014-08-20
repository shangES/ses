package icbc.cmis.tags;
import icbc.cmis.base.CmisConstance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * Title:        cmis
 * Description:  cmis3
 * Copyright:    Copyright (c) 2001
 * Company:      icbc
 * @author icbc
 * @version 1.0
 * ZYZ200704修改，增加titleid属性，支持从语言包中读取title字符串
 */

public class TabPageBarTag extends TagSupport {
	private String webBasePath = (String)CmisConstance.getParameterSettings().get("webBasePath");
	private String title = "";
	private String url = "#";
	private String onclick;
	private boolean selected = false;
	private int ind = 1; //use to get the images name
	private static String[][] images = { { "/images/p1.gif", "/images/p4.gif" }, {
			"/images/p2.gif", "/images/p5.gif" }, {
			"/images/p3.gif", "/images/p6.gif" }
	};

	private String sDef;
	private String titleID = "";

	public TabPageBarTag() {
		super();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitleid(String titleid) {
		this.titleID = titleid;
	}

	public void setUrl(String url) {
		if (url != null) {
			this.url = url;
		}
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public void setSelected(boolean b) {
		this.selected = b;
		if (this.selected) {
			ind = 0;
		}
	}

	public int doStartTag() throws javax.servlet.jsp.JspException {
		try {
			String sTitle = ""; //最终输入title

			if (!titleID.equals("")) {
				try {
					String language = "";
					//如果取地区失败，则强制为中文
					try {
						HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
						icbc.cmis.base.CMisSessionMgr sm = new icbc.cmis.base.CMisSessionMgr(request);
						language = (String)sm.getSessionData("LangCode");
						//language = (String)pageContext.getSession().getAttribute("LangCode");
					} catch (Exception eee) {
						language = "zh_CN";
					}
					MuiTagParent tagParent = (MuiTagParent)findAncestorWithClass(this, MuiTagParent.class);
					sDef = tagParent.gotDef();
					sTitle = MuiTagBase.getStr(sDef, language, titleID);
				} catch (Exception e) {
					Debug.println("!Error Tag:: " + sDef + ":" + titleID);
				}
			} else {
				sTitle = title;
			}

			JspWriter out = this.pageContext.getOut();
			out.println("      <td background=\"" + webBasePath + images[0][ind] + "\" width=\"12\"></td>");
			out.print("        <td background=\"" + webBasePath + images[1][ind] + "\" align=\"center\">");
			if (!selected) {
				out.print("<a href=\"" + url + "\" ");
				if (onclick != null) {
					out.print("onclick=\"" + onclick + "\" ");
				}
				out.print(">" + sTitle + "</a>");
			} else {
				out.print(sTitle);
			}
			out.println("</td>");
			out.println("        <td width=\"12\" background=\"" + webBasePath + images[2][ind] + "\"></td>");

		} catch (Exception ex) {
			throw new JspException("TabPageBarTag doStartTag() error." + ex.getMessage());
		}
		return SKIP_BODY;
	}
}