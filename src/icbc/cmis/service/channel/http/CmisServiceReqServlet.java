package icbc.cmis.service.channel.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import icbc.cmis.base.*;
import icbc.cmis.service.channel.CM2002Service;
import icbc.cmis.service.channel.CMISChannelContext;
import icbc.cmis.service.channel.ChannelContext;
import icbc.cmis.service.channel.ChannelDriver;
import icbc.cmis.service.channel.Handler;
import icbc.cmis.service.channel.IDAuthenticate;
import icbc.cmis.service.channel.SessionManager;

/**
 * 
 * <b>创建日期: </b> 2004-9-17><br>
 * <b>标题: </b>CmisServiceReqServlet<br>
 * <b>类描述: </b>
 * 该类用于其他系统向CM2002系统发送请求时，CM2002进行响应的servlet<br>
 * <p>Copyright: Copyright (c)2004</p>
 * <p>Company：ICBC</p>
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

public class CmisServiceReqServlet extends HttpServlet 
				implements ChannelDriver {

	/**
	 * <b>构造函数</b><br>
	 * 
	 */
	public CmisServiceReqServlet() {
		super();
	}

	
	/** 
	 * <b>功能描述: </b><br>
	 * <p>	</p>
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		doPost(req, resp);
	}

	
	/** 
	 * <b>功能描述: </b><br>
	 * <p>	</p>
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		Handler requestHandle = null;
		ChannelContext channelContext=null;
		icbc.cmis.base.Trace.trace(
			"",
			0,
			0,
			"",
			"CmisServiceReqServlet.doPost() begin");
		try {

			channelContext=creatChannelContext(req,resp,this);
			
			if (!CmisConstance.isServerStarted) {
				throw new Exception();
			}
			int inteval = 30;
			try {
				inteval =
					Integer
						.valueOf(
							(String) CmisConstance.getParameterSettings().get(
								"clickMinInteval"))
						.intValue();
			} catch (Exception ei) {
				inteval = 30;
			}
			if(CM2002Service.isEnabled() && CM2002Service.getSettings().get("HttpEnabled").equals("true")){
				requestHandle = CM2002Service.createHanderler(channelContext);
				requestHandle.processRequest(channelContext);
			}else{
				TranFailException ee =
									new TranFailException(
										"00000",
										"CmisServiceReqServlet.doPost(HttpServletRequest req, HttpServletResponse rep)",
										"","CM2002对外服务接口未启用。");
				
				handleException(req,resp,ee);
			}
			icbc.cmis.base.Trace.trace(
				"",
				0,
				0,
				"",
				"CmisServiceReqServlet.doPost() end");
		} catch (Exception e) {
			requestHandle.handleException(channelContext, e);
			icbc.cmis.base.Trace.trace(
				"",
				0,
				0,
				"",
				"CmisServiceReqServlet.doPost() exception," + e.toString());
		}

	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param req
	 * @param resp
	 * @return
	 *  
	 */
	public ChannelContext creatChannelContext(Object req,Object resp,Object driver) throws Exception{
		ChannelContext channelContext=new CMISChannelContext(req,resp,driver);
		channelContext.setDeviceType("HTTP");
		return channelContext;
	}


	/** 
	 * <b>功能描述: </b><br>
	 * <p>实现Driver接口的接口方法，对交易数据进行解析	</p>
	 * @see icbc.cmis.service.channel.ChannelDriver#parseRequestData(icbc.cmis.service.channel.ChannelContext)
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * 
	 */
	public KeyedDataCollection parseRequestData(ChannelContext channelContext) throws Exception {
		HttpServletRequest req=(HttpServletRequest)channelContext.getChannelRequest();
		try {
			String opDataUnclear = null;
			KeyedDataCollection kColl = new KeyedDataCollection();

			for (Enumeration enumeration = req.getParameterNames();
				enumeration.hasMoreElements();) {
				String attrName = (String) enumeration.nextElement();
				String attrValue[] =
					(String[]) req.getParameterValues(attrName);

				if (attrValue == null) {
					attrValue = new String[1];
					attrValue[0] = "";
				}
				for (int i = 0; i < attrValue.length; i++) {
					attrValue[i] = icbc.cmis.util.Decode.decode(attrValue[i]);
				}

				if (attrValue.length == 1)
					kColl.addElement(attrName, attrValue[0]);
				else
					kColl.addElement(attrName, attrValue);
			}
			kColl.addElement("ServerAddr",req.getServerName() + ":" + req.getServerPort());
			kColl.addElement("RemoteAddr",req.getRemoteAddr());
			kColl.addElement("operationName",CM2002Service.getOperationName((String)kColl.getValueAt("TransNo")));
			return kColl;
		} catch (Exception e) {
			throw new TranFailException(
				"",
				"CmisServiceReqServlet.parseRequestData(ChannelContext channelContext)",
				e.getMessage());
		}	
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p>HTTP渠道，非交易异常处理 </p>
	 * @param req
	 * @param rep
	 * @param exception
	 *  
	 */
	private void handleException(HttpServletRequest req, HttpServletResponse rep,Exception exception){
		try {
			String encoding =
						(String) CmisConstance.getParameterSettings().get(
							"encoding");
					rep.setContentType("text/html; charset=" + encoding);
			PrintWriter o;
			o = rep.getWriter();
			o.println(CM2002Service.handleException((String)req.getParameter("SerialNo"),
			(String)req.getParameter("TransNo"),exception,"000"));
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