package icbc.cmis.tags;

import java.util.Locale;
import java.util.ResourceBundle;

import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * 父标记，用来定义当前页面的resource 
 * @author zjfh-zhangyz
 * 2007-4-4 / 9:02:01
 *
 */

public class MuiTagParent extends TagSupport {
	private String sDef;//定义resource

	public int doStartTag() throws JspTagException {
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public void setDef(String tDef) {
		this.sDef = tDef;
	}

	/**
	 * 返回给子标记当前的定义
	 * @return
	 */
	public String gotDef() {
		return this.sDef;
	}
}