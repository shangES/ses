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

package icbc.cmis.service.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * <b>创建日期: </b> 2004-9-21<br>
 * <b>标题: </b>CM2002服务请求代理类<br>
 * <b>类描述: </b><br>
 * 该类用于提供给第外系统，外系统将通过它请求CM2002的交易。<br>
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

public class CmisServiceHttpReqProxy {
	
	/**服务器IP或主机名*/
	String host=null;
	
	/**服务器端口号*/
	String hostPort=null;
	
	/**系统标识ID*/
	String systemTypeID=null;
	
	/**柜员(用户)标识*/
	String authId=null;
	
	/**用户校验串（密码）*/
	String verifyStr=null;
	
	/**CM2002服务程序,通常为Servlet URL,
	 * 譬如"/icbc/cmis/servlet/CmisServiceReqServlet"*/
	String responsor=null;
	
	/**请求序号**/
	String serialNo=null;
	
	
	/**
	 * <b>构造函数</b><br>
	 * 
	 */
	public CmisServiceHttpReqProxy(String serialNo,String host,String responsor,String systemTypeID,String authId,String verifyStr ) {
		
		this(serialNo,host,"80",responsor,systemTypeID,authId,verifyStr);
	}
	
	/**
	 * <b>构造函数</b><br>
	 * 
	 */
	public CmisServiceHttpReqProxy(String serialNo,String host,String hostPort,String responsor,String systemTypeID,String authId,String verifyStr ) {
		setReqSerialNo(serialNo);
		setHost(host);
		setHostPort(hostPort);
		setResponsor(responsor);
		this.systemTypeID=systemTypeID;
		setAuthId(authId);
		setVerifyStr(verifyStr);
	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p>直接转发HTTP客户端的HTTP请求 </p>
	 * @param req
	 * @return
	 *  
	 */
	public String requestOperation(HttpServletRequest req){
		HashMap param=new HashMap();
		
		String opName="";
		for(Enumeration enumerator=req.getParameterNames();enumerator.hasMoreElements();){
			String parmName=(String)enumerator.nextElement();
			String[] parmValue=req.getParameterValues(parmName);
			if(parmName.equals("TransNo"))
				opName=parmValue[0];
			else
				param.put(parmName,parmValue);
		}
		
		return requestOperation(opName,param);
	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p>向CM2002发送请求，并返回CM2002返回的结果XML串</p>
	 * @param opName
	 * @param param
	 *  
	 */
	public String requestOperation(String opName,HashMap param){
		if(!getResponsor().startsWith("/"))
			setResponsor("/"+getResponsor());
		String serviceUrl = "http://"+getHost()+":"+getHostPort()+getResponsor()+"?TransNo="+
							opName+"&SerialNo="+serialNo+"&SysID="+systemTypeID+"&AuthId="+authId+"&VerifyStr="+verifyStr;
		
		String urlParm="";
		for(Iterator iterator=param.keySet().iterator();iterator.hasNext();){
			String paramName=(String)iterator.next();
			String[] paramValue=(String[])param.get(paramName);
			if(paramValue.length==1)
				urlParm=urlParm+"&"+paramName+"="+paramValue[0];
			else
				for(int i=0;i<paramValue.length;i++){
					urlParm=urlParm+"&"+paramName+"="+paramValue[i];
				}
		}
		serviceUrl=serviceUrl+urlParm;
		
		System.out.println(serviceUrl);
		
		
		StringBuffer strRet = new StringBuffer();
		try {
			URL url = new URL(serviceUrl); 
			URLConnection connection = url.openConnection();
			connection.setDoInput(true);
			String line;
			
			BufferedReader in =
				new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			for (strRet.append("");
				(line = in.readLine()) != null;
				strRet.append(line));
			in.close();
		} catch (IOException ex) {
	
		}
		System.out.println(strRet.toString());
		return  strRet.toString();
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @return
	 *  
	 */
	public String getHost() {
		return host;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @return
	 *  
	 */
	public String getHostPort() {
		return hostPort;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @return
	 *  
	 */
	public String getResponsor() {
		return responsor;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param string
	 *  
	 */
	public void setHost(String string) {
		host = string;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param string
	 *  
	 */
	public void setHostPort(String string) {
		hostPort = string;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param string
	 *  
	 */
	public void setResponsor(String string) {
		responsor = string;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param string
	 *  
	 */
	public void setSystemTypeID(String string) {
		systemTypeID = string;
	}
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @return
	 *  
	 */
	public String getAuthId() {
		return authId;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @return
	 *  
	 */
	public String getVerifyStr() {
		return verifyStr;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param string
	 *  
	 */
	public void setAuthId(String string) {
		authId = string;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param string
	 *  
	 */
	public void setVerifyStr(String string) {
		verifyStr = string;
	}
	
	public void setReqSerialNo(String reqSerialNo){
		this.serialNo=reqSerialNo;
	}
	
	public String getReqSerialNo(){
		return serialNo;
	}
	
}
