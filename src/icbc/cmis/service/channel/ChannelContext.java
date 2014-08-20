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

package icbc.cmis.service.channel;

import icbc.cmis.base.KeyedDataCollection;

/**
 * 
 * <b>创建日期: </b> 2004-10-21<br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * 渠道上下文接口，它定义了从渠道上下文获取和设置请求对象、返回对象、
 * 请求数据、Session数据的接口方法<br>
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

public interface ChannelContext {

	/**
	 * 取交易处理器类型
	 */
	public abstract java.lang.Object getChannelDriver();

	/**
	 * 取渠道请求
	 */
	public abstract java.lang.Object getChannelRequest();

	/**
	 * 取渠道交易请求的响应对象
	 */
	public abstract java.lang.Object getChannelResponse();

	/**
	 * 取渠道交易请求会话
	 */
	public abstract java.lang.Object getChannelSession();

	/**
	 * 取渠道请求类型
	 */
	public abstract java.lang.String getDeviceType();

	/**
	 * 取渠道请求交易数据
	 */
	public abstract KeyedDataCollection getRequestData() throws Exception;

	/**
	 * 设置交易处理器类型
	 */
	public abstract void setChannelDriver(java.lang.Object channelDriver);

	/**
	 * 设置渠道交易请求会话
	 */
	public abstract void setChannelSession(java.lang.Object channelSession);

	/**
	 * 设置渠道请求类型
	 */
	public abstract void setDeviceType(java.lang.String type);

	/**
	 * 设置渠道请求交易数据
	 */
	public abstract void setRequestData(KeyedDataCollection kColl);
	
}
