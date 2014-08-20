/*
 * $Id: ListTag.java,v 1.4 2001/10/26 22:55:55 ro89390 Exp $
 * Copyright 2000 Sun Microsystems, Inc. All rights reserved.
 * Copyright 2000 Sun Microsystems, Inc. Tous droits r?erv?.
 */

package icbc.cmis.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;

import java.io.IOException;
import java.util.Iterator;
import java.util.Collection;

import icbc.cmis.base.*;
import icbc.cmis.operation.*;

/**
 *
 * This class allows you to retrieve a parameter from the request and output it to the page
 * This should allow you to avoid expressions for extracting parameter info.
 *
 */

public class ListTag extends BodyTagSupport {
	private String webBasePath =
		(String) CmisConstance.getParameterSettings().get("webBasePath");
	private KeyedDataCollection context;
	private IndexedDataCollection jspicoll;
	private KeyedDataCollection jspkcoll;
	private TabPageContentTag TabPageContentTag;

	//    private Iterator iterator;

	private int startIndex = -1; //起始记录号
	private int endIndex = -1; //终止记录号
	private int size = 15; //每页记录数
	private int listSize; //列表记录总数
	private int currentID; //当前记录号
	private String collectionId = "operationData";
	private String indexName;
	private String scope;
	private String alignstring = "center";
	private Object currentItem = null;
	private String returnPage = null;
	private boolean hasFooter = true;
	private String formName = null;
	private String footerAlign = "right";
	private String footerValign = "bottom";
	private int footerSize = 750;

	protected boolean hasFirstForm = true;
	protected boolean hasLastForm = true;
	protected boolean hasNextForm = true;
	protected boolean hasPrevForm = true;

	private int prevFORMstartIndex = 1;
	private int nextFORMstartIndex = 1;
	private int firstFORMstartIndex = 1;
	private int lastFORMstartIndex = 1;

	public ListTag() {
		super();
	}

	public void setcollectionid(String cId) {
		this.collectionId = cId;
	}

	public void setindexname(String indexName) {
		this.indexName = indexName;
	}

	public void setScope(String scope) {
		this.scope = scope.toLowerCase();
	}

	public void setAlign(String alignString) {
		this.alignstring = alignString;
	}

	public int getStartIndex() {
		return (this.startIndex);
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setreturnpage(String returnpage) {
		this.returnPage = returnpage;
	}

	public void sethasfooter(boolean hasfooter) {
		this.hasFooter = hasfooter;
	}

	public void setfootersize(int footersize) {
		this.footerSize = footersize;
	}

	public void setfooteralign(String fa) {
		this.footerAlign = fa;
	}

	public void setfootervalign(String fva) {
		this.footerValign = fva;
	}

	public int getCurrentID() {
		return this.currentID;
	}
	public void setformname(String formname) {
		this.formName = formname;
	}

	public int doStartTag() throws JspTagException {

		//   若在listInfoTag中已经定义过indexname,size等信息，
		//   则取过来用
		ListInfoTag listInfoTag =
			(ListInfoTag) findAncestorWithClass(this, ListInfoTag.class);
		if (listInfoTag != null) {
			this.size = listInfoTag.getSize();
			this.indexName = listInfoTag.getIndexName();
		}

		String beginpositionName = "ListTagBeginPosition" + indexName;

		if (scope.equals("request")) {
			context =
				(KeyedDataCollection) pageContext.getRequest().getAttribute(
					collectionId);
		} else if (scope.equals("session")) {
			context =
				(KeyedDataCollection) pageContext.getSession().getAttribute(
					collectionId);
		} else if (scope.equals("page")) {
			context =
				(KeyedDataCollection) pageContext.getAttribute(collectionId);
		}
		if (context == null)
			throw new JspTagException(
				"ListTag: Collection "
					+ collectionId
					+ " not found in "
					+ scope
					+ " scope.");

		try {
			//add by zjfh-shanhy at 2007/04/04,start
			//修改原则：如果客户未设置newQuery标志，则处理逻辑同原处理逻辑；否则按新的处理方法进行处理
			String isNewQuery = "false";
			if (context.isElementExist("newQuery")) {
				isNewQuery = (String) context.getValueAt("newQuery");
			}
			
			if (isNewQuery.equalsIgnoreCase("true")) {
				startIndex = 0;
				endIndex = size;
		//add by zjfh-shanhy at 2007/04/04,end
			} else{
				//获取起始记录位置
				if (context.isElementExist(beginpositionName)) {
					startIndex =
						Integer.parseInt(
							(String) context.getValueAt(beginpositionName));
					endIndex = startIndex + size;
				} else {
					startIndex = 0;
					endIndex = size;
				}				
			}

			currentID = startIndex;

			if (context.isElementExist(indexName)) {
				jspicoll =
					(IndexedDataCollection) context.getElement(indexName);
				listSize = jspicoll.getSize();
				//commented by zjfh-shanhy at 2007/04/04, start
				//剔除数组越界的情况
				//if (currentID == listSize || currentID == endIndex) {
				//commented by zjfh-shanhy at 2007/04/04, end
				//currentID == listSize ---> currentID >= listSize
				if (currentID >= listSize || currentID == endIndex) {
					return (SKIP_BODY);
				} else {
					currentItem =
						(KeyedDataCollection) jspicoll.getElement(currentID);
					currentID++;
				}
			} else
				return (SKIP_BODY);

		} catch (Exception ee) {
			//            System.err.println("ListTag: Error printing attribute: " + ee);
			throw new JspTagException("dostart读元素出错" + this.indexName);
		}

		if (formName != null)
			writeFormHead();

		writeTrHead();
		return (EVAL_BODY_TAG);
	}
	// process the body again until the specified length with the next item if it exists
	public int doAfterBody() throws JspTagException {
		try {
			writeTrTail();
			if (currentID == listSize || currentID == endIndex) {
				if (currentID == listSize)
					hasNextForm = false;
				else
					hasNextForm = true;
				return (SKIP_BODY);
			} else {
				currentItem =
					(KeyedDataCollection) jspicoll.getElement(currentID);
				currentID++;
				writeTrHead();
			}
		} catch (Exception ee) {
			//            System.err.println("ListTag: Error printing attribute: " + ee);
			throw new JspTagException("aftertag读元素出错" + this.indexName);
		}
		return (EVAL_BODY_TAG);
	}

	/**
	 *
	 * Meant for use by ListItem tags
	 *
	 */

	public Object getCurrentItem() {
		return currentItem;
	}

	public int doEndTag() throws JspTagException {
		JspWriter pageout = pageContext.getOut();
		ListTailTag listtailtag =
			(ListTailTag) findAncestorWithClass(this, ListTailTag.class);
		try {
			BodyContent body = getBodyContent();
			if (body != null) {
				JspWriter out = body.getEnclosingWriter();
				out.print(body.getString());
			}
			//            if( hasFooter )
			if (listtailtag == null)
				writeListFooter(pageout, this);
			else
				listtailtag.setListTag(this);
		} catch (IOException ioe) {
			//            System.err.println("Error handling items tag: " + ioe);
			throw new JspTagException("IO出错" + ioe);
		}
		return EVAL_PAGE;
	}

	public void writeListFooter(JspWriter out, Object tagstart)
		throws JspTagException {
		//设置前、后页
		prevFORMstartIndex = startIndex - size;
		nextFORMstartIndex = startIndex + size;

		if (nextFORMstartIndex > listSize)
			nextFORMstartIndex = listSize;

		if (prevFORMstartIndex < 0)
			hasPrevForm = false;
		else
			hasPrevForm = true;

		if (nextFORMstartIndex < listSize)
			hasNextForm = true;
		else
			hasNextForm = false;

		//设置起始、终止页
		if (prevFORMstartIndex > 0) {
			hasFirstForm = true;
			firstFORMstartIndex = 0;
		} else
			hasFirstForm = false;

		if (nextFORMstartIndex < (listSize - size)) {
			hasLastForm = true;
			lastFORMstartIndex = listSize % size;
			if (lastFORMstartIndex == 0)
				lastFORMstartIndex = listSize - size;
			else
				lastFORMstartIndex = (listSize / size) * size;
		} else
			hasLastForm = false;

		try {
			out.println(
				"<!----------------------------------------begin of listfooter content------------------------>");
			if (formName != null) {
				out.println("</form>");
			}
			out.println("<tr>");
			out.println("<td colspan=\"20\">");
			writeJAVASCRIPT(out);
			out.println(
				"<table  width=\""
					+ this.footerSize
					+ "\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >");
			out.println("<tr>");
			out.println(
				"<td  class=\"td1\" align=\""
					+ this.footerAlign
					+ "\" valign=\""
					+ this.footerValign
					+ "\">");

			if (hasFooter) {
				out.println(
					"<form name=\"formtag2509\" method=\"GET\" action=\""
						+ webBasePath
						+ "/servlet/icbc.cmis.servlets.CmisReqServlet\" >");
				out.println(
					"<input type=hidden name=\"operationName\" value=\"ListFooter\">");
				out.println(
					"<input type=hidden name=\"r_replyPage_formtag2509\" value=\""
						+ context.getValueAt("replyPage")
						+ "\">");
				out.println(
					"<input type=hidden name=\"opDataUnclear\" value=\"true\">");
				out.println(
					"<input type=hidden name=\"operationStatus\" value=\"\">");
				//add by zjfh-shanhy at 2007/04/04,start
				out.println(
					"<input type=hidden name=\"newQuery\" value=\"false\">");
				//add by zjfh-shanhy at 2007/04/04,end
				out.println(
					"<input type=hidden name=\"ListTagBeginPosition"
						+ this.indexName
						+ "\" value=\""
						+ startIndex
						+ "\">");

				if (hasFirstForm)
					out.print(
						"<img src=\""
							+ webBasePath
							+ "/images/first_page.gif\" border=\"0\" width=\"48\" style=\"cursor:hand\" onClick=\"return changeform2509( 0 )\">");
				if (hasPrevForm)
					out.print(
						"<img src=\""
							+ webBasePath
							+ "/images/pre_page.gif\" border=\"0\" width=\"48\" style=\"cursor:hand\" onClick=\"return changeform2509( 1 )\">");
				if (hasNextForm)
					out.print(
						"<img src=\""
							+ webBasePath
							+ "/images/next_page.gif\" border=\"0\" width=\"48\" style=\"cursor:hand\" onClick=\"return changeform2509( 2 )\">");
				if (hasLastForm)
					out.print(
						"<img src=\""
							+ webBasePath
							+ "/images/last_page.gif\" border=\"0\" width=\"48\" style=\"cursor:hand\" onClick=\"return changeform2509( 3 )\">");
			}
			//处理附加的按钮
			AppendButtonTag buttontag =
				(AppendButtonTag) findAncestorWithClass(
					(BodyTagSupport) tagstart,
					AppendButtonTag.class);
			while (true) {
				if (buttontag == null)
					break;
				if (listSize > 0 || buttontag.getConfirm()) {
					out.print(
						"<A href=\""
							+ buttontag.getHref()
							+ "\"><img src=\""
							+ buttontag.getImagesrc()
							+ "\" border=\"0\" "
							+ (buttontag.getOnclick() == null
								? ""
								: "onClick=\"" + buttontag.getOnclick() + "\"")
							+ (buttontag.getTitle() == null
								? ""
								: "title=\"" + buttontag.getTitle() + "\"")
							+ "></a>");
				}
				buttontag =
					(AppendButtonTag) findAncestorWithClass(buttontag,
						AppendButtonTag.class);
			}

			if (hasFooter) {
				//若未设置返回页面，则返回主页
				if (returnPage.equals(null)
					|| returnPage.equals("")
					|| returnPage.equals("home"))
					out.print(
						"<a href=\""
							+ webBasePath
							+ "/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.servlets.CmisMain\"><img src=\""
							+ webBasePath
							+ "/images/home.gif\" width=\"48\" height=\"24\" border=\"0\" onclick = \"javascript:history.back();\"></a>");
				else if (returnPage.equals("historyback")) {
					out.print(
						"<a href=\"javascript:window.history.go(-1)\"><img src=\""
							+ webBasePath
							+ "/images/cancel.gif\" width=\"48\" height=\"24\" border=\"0\"></a>");
				} else if (returnPage.equals("call_history")) {
					out.print(
						"<a href=\""
							+ webBasePath
							+ "/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=MA_TermDealOP&operationStatus=initH\"><img src=\""
							+ webBasePath
							+ "/images/return.gif\" border=\"0\" width=\"48\" style=\"cursor:hand\" ></a>");
				} else
					out.print(
						"<img src=\""
							+ webBasePath
							+ "/images/return.gif\" border=\"0\" width=\"48\" style=\"cursor:hand\" onClick=\"javascript:returnform2509();\">");

				out.println("</form>");
			}
			out.println("</td>");
			out.println("</tr>");
			out.println("</table>");

			out.println("</td>");
			out.println("</tr>");
			out.println(
				"<!----------------------------------------end of listfooter content------------------------>");

		} catch (Exception ex) {
			throw new JspTagException(
				"ListTag writeListFooter() error." + ex.getMessage());
		}
		return;
	}

	private void writeJAVASCRIPT(JspWriter out) throws JspTagException {

		try {
			out.println("  <script language=\"JavaScript\"> ");
			out.println("   function changeform2509( turnPageFlag ){   ");
			out.println(
				"   formtag2509.operationName.value=\"icbc.cmis.util.ListFooter\"; ");
			out.println("   formtag2509.opDataUnclear.value=\"true\"; ");
			out.println("   if( turnPageFlag == 0 ) "); //第一页
			out.println(
				"   formtag2509.ListTagBeginPosition"
					+ this.indexName
					+ ".value=\""
					+ firstFORMstartIndex
					+ "\"; ");
			out.println("   else if( turnPageFlag == 1) "); //上一页
			out.println(
				"   formtag2509.ListTagBeginPosition"
					+ this.indexName
					+ ".value=\""
					+ prevFORMstartIndex
					+ "\"; ");
			out.println("   else if( turnPageFlag == 2) "); //下一页
			out.println(
				"   formtag2509.ListTagBeginPosition"
					+ this.indexName
					+ ".value=\""
					+ nextFORMstartIndex
					+ "\"; ");
			out.println("   else if( turnPageFlag == 3) "); //最后页
			out.println(
				"   formtag2509.ListTagBeginPosition"
					+ this.indexName
					+ ".value=\""
					+ lastFORMstartIndex
					+ "\"; ");
			out.println("   document.formtag2509.submit();                 ");
			out.println("   }                 ");
			out.println("   function returnform2509( ){   ");
			out.println(
				"    formtag2509.operationName.value=\""
					+ context.getValueAt("operationName")
					+ "\"; ");
			out.println(
				"    formtag2509.operationStatus.value=\""
					+ returnPage
					+ "\"; ");
			out.println("    formtag2509.opDataUnclear.value=\"true\"; ");
			//    out.println("   formtag2509.ListTagBeginPosition.value=\"0\"; ");
			out.println("    document.formtag2509.submit();                 ");
			out.println("   }                 ");
			out.println("   </script> ");
		} catch (Exception ex) {
			throw new JspTagException(
				"ListTag writeListFooter() error." + ex.getMessage());
		}
		return;
	}

	private void writeFormHead() throws JspTagException {

		JspWriter pageout = pageContext.getOut();
		try {
			pageout.println(
				"    <form name=\""
					+ this.formName
					+ "\" method=\"post\" action=\""
					+ webBasePath
					+ "/servlet/icbc.cmis.servlets.CmisReqServlet\">");
		} catch (Exception ex) {
			throw new JspTagException(
				"ListTag writeFormHead() error." + ex.getMessage());
		}
		return;
	}
	private void writeTrHead() throws JspTagException {

		JspWriter pageout = pageContext.getOut();
		try {

			if ((this.currentID + 1) % 2 == 0)
				pageout.println(
					"    <tr align=\"" + alignstring + "\" bgColor=\"\">");
			else
				pageout.println(
					"    <tr align=\""
						+ alignstring
						+ "\" bgColor=\"#e6e6e6\">");
		} catch (Exception ex) {
			throw new JspTagException(
				"ListTag writeListFooter() error." + ex.getMessage());
		}
		return;
	}
	private void writeTrTail() throws JspTagException {

		JspWriter pageout = pageContext.getOut();
		try {
			pageout.println("    </tr>");
		} catch (Exception ex) {
			throw new JspTagException(
				"ListTag writeListFooter() error." + ex.getMessage());
		}
		return;
	}
	public void release() {
		super.release();
	}

}
