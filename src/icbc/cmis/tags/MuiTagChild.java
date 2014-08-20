package icbc.cmis.tags;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * 子标记，绘制标记
 * @author zjfh-zhangyz
 * 2007-4-4 / 9:02:44
 *
 */

public class MuiTagChild extends TagSupport {
	private String sDef;
	private String sItem;
	private String sMark = "";

	public MuiTagChild() {
		super();
	}

	public int doStartTag() throws JspTagException {
		try {
			//从session中取得定义的language
			String language = "";
			try {
				HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
				icbc.cmis.base.CMisSessionMgr sm = new icbc.cmis.base.CMisSessionMgr(request);
				language = (String)sm.getSessionData("LangCode");
				//language = (String)pageContext.getSession().getAttribute("LangCode");
			} catch (Exception eee) {
				language = "zh_CN";
			}

			//找到第一个祖先标记，取得resource定义
			MuiTagParent tagParent = (MuiTagParent)findAncestorWithClass(this, MuiTagParent.class);
			this.sDef = tagParent.gotDef();
			String strOutBody = MuiTagBase.getStr(sDef, language, sItem);

			//输出
			StringBuffer strbuf = new StringBuffer();
			JspWriter out = pageContext.getOut();
			out.print(strbuf.append(sMark).append(strOutBody).append(sMark).toString());
		} catch (Exception e) {
			Debug.println("!Error Tag:: " + sDef + ":" + sItem);
		}
		return (SKIP_BODY);
	}

	public void setItem(String tItem) {
		this.sItem = tItem;
	}

	public void setMark(String tMark) {
		this.sMark = tMark;
	}

	public void setExp(String tExp) {

	}
}