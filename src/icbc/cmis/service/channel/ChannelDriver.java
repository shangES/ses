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
 * 交易驱动器接口，它定义交易驱动器实现交易数据解析的接口方法，<br>
 * 及创建渠道上下文的方法接口<br>
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

public interface ChannelDriver {
	
	/**
	 * <b>功能描述: </b><br>
	 * <p>将不同渠道传入的请求数据解析为KeyedDataCollection结构的数据 </p>
	 * @param channelContext
	 * @return
	 * @throws Exception
	 *  
	 */
	public KeyedDataCollection parseRequestData(ChannelContext channelContext ) throws Exception;
	
	/**
	 * <b>功能描述: </b><br>
	 * <p>创建渠道的交易上下文。 </p>
	 * @param req
	 * @param resp
	 * @param driver
	 * @return
	 * @throws Exception
	 *  
	 */
	public ChannelContext creatChannelContext(Object req,Object resp,Object driver) throws Exception;
}
