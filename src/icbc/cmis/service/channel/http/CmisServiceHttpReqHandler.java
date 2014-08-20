////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2004 ICBC
//
//ALL RIGHTS RESERVED BY ICBC CORPORATION, THIS PROGRAM
//MUST BE USED SOLELY FOR THE PURPOSE FOR WHICH IT WAS  
//FURNISHED BY ICBC CORPORATION, NO PART OF THIS PROGRAM
//MAY BE REPRODUCED OR DISCLOSED TO OTHERS, IN ANY FORM
//WITHOUT THE PRIOR WRITTEN PERMISSION OF ICBC CORPORATION.
//USE OF COPYRIGHT NOTICE DOES NOT EVIDENCE PUBLICATION
//OF THE PROGRAM
//
//			ICBC CONFIDENTIAL AND PROPRIETARY
//
////////////////////////////////////////////////////////////////////////////
//

package icbc.cmis.service.channel.http;

import icbc.cmis.base.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import icbc.cmis.operation.*;
import icbc.cmis.service.channel.CM2002Service;
import icbc.cmis.service.channel.ChannelContext;
import icbc.cmis.service.channel.ServiceHandler;
import icbc.cmis.service.channel.SessionManager;

import javax.servlet.*;

/**
 * 
 * <b>创建日期: </b> 2004-9-17><br>
 * <b>标题: </b>CmisServiceReqHandler<br>
 * <b>类描述: </b><br>
 * 该类用于对外系统向CM2002系统发送请求时，CM2002进行交易处理的Handler类<br>
 * <p>Copyright: Copyright (c)2004</p>
 * <p>Company: </p>
 * 
 * @author YangGuangRun
 * 
 * @version 
 * 
 * @since 
 * 
 * @see 
 * 
 */

public class CmisServiceHttpReqHandler extends ServiceHandler{
	/**
	 * <b>构造函数</b><br>
	 * 
	 */
	public CmisServiceHttpReqHandler() {
		super();
	}

	/** 
	 * <b>功能描述: </b><br>
	 * <p>HTTP渠道，交易结果处理返回</p>
	 * @see icbc.cmis.service.channel.Handler#processReply(icbc.cmis.service.channel.ChannelContext)
	 * @param channelContext
	 * 
	 */
	public void processReply(ChannelContext channelContext) {
		
		HttpServletRequest req=(HttpServletRequest) channelContext.getChannelRequest();
		HttpServletResponse rep=(HttpServletResponse) channelContext.getChannelResponse();
		String page=null;
		try {
			KeyedDataCollection opData=channelContext.getRequestData();
			
			if(opData.isElementExist("TurnPage")){
				java.text.DecimalFormat fmt=new java.text.DecimalFormat("#");
				try{
					int totalRows=Integer.valueOf((String)opData.getValueAt("RowCount")).intValue();
					long beginRow=Long.valueOf((String)opData.getValueAt("BeginRow")).longValue();
					long endRow=Long.valueOf((String)opData.getValueAt("EndRow")).longValue();
					if(totalRows<endRow-beginRow+1){
						opData.setValueAt("EndRow",fmt.format((new Long(beginRow+totalRows-1)).longValue()));
					}
				}catch (CTEObjectNotFoundException e){
					
				}
			}
			page=(String)opData.getValueAt("ReplyPage");
			if (page.trim().startsWith("DirectOutput")) {
				icbc.cmis.base.Trace.trace(
					"CmisServiceReqHandler",
					0,
					0,
					"",
					"processReply page:DirectOutput");
				String encoding =
					(String) CmisConstance.getParameterSettings().get(
						"encoding");
				rep.setContentType("text/xml; charset=" + encoding);
				PrintWriter o = rep.getWriter();
				o.println(page.substring(12, page.length()));
				o.flush();
				o.close();
				return;
			} else {
				String transNo=(String)opData.getValueAt("TransNo");
				String fmtDefName=CM2002Service.getOperationRepFormat(transNo);
				String encoding =
					(String) CmisConstance.getParameterSettings().get(
						"encoding");
				rep.setContentType("text/html; charset=" + encoding);
				PrintWriter o = rep.getWriter();
				o.println(CM2002Service.format(opData,fmtDefName));
				o.flush();
				o.close();
			}
		} catch (Throwable e) {
			return;
		}
	}

	/** 
	 * <b>功能描述: </b><br>
	 * <p>HTTP渠道交易异常处理方法	</p>
	 * @see icbc.cmis.service.channel.Handler#handleException(icbc.cmis.service.channel.ChannelContext, java.lang.Exception)
	 * @param channelContext
	 * @param e
	 * 
	 */
	public void handleException(ChannelContext channelContext, Exception exception) {
		HttpServletResponse rep=(HttpServletResponse) channelContext.getChannelResponse();
		
		try {
			KeyedDataCollection opData=channelContext.getRequestData();
			
			String reqSeq = (String)opData.getValueAt("reqSeq");
			
			String encoding =
						(String) CmisConstance.getParameterSettings().get(
							"encoding");
					rep.setContentType("text/html; charset=" + encoding);
					PrintWriter o;
			o = rep.getWriter();
			o.println(CM2002Service.handleException((String)opData.getValueAt("SerialNo"),
			(String)opData.getValueAt("TransNo"),exception,reqSeq));
			o.flush();
			o.close();
		} catch (IOException e1) {
			icbc.cmis.base.Trace.trace(
				"",
				0,
				0,
				"",
				"CmisServiceReqServlet.handleException() exception," + e1.toString());
		} catch (Exception e) {
			icbc.cmis.base.Trace.trace(
				"",
				0,
				0,
				"",
				"CmisServiceReqServlet.handleException() exception," + e.toString());
		}
	}

}