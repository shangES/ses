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

package icbc.cmis.service.channel.mq;

import com.ibm.mq.MQMessage;

import icbc.cmis.base.CTEObjectNotFoundException;
import icbc.cmis.base.KeyedDataCollection;
import icbc.cmis.service.MQConnectionService;
import icbc.cmis.service.channel.CM2002Service;
import icbc.cmis.service.channel.ChannelContext;
import icbc.cmis.service.channel.ServiceHandler;

/**
 * 
 * <b>创建日期: </b> 2004-10-21<br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * MQ渠道交易处理器实现类<br>
 * <p>Copyright: Copyright (c)2004</p>
 * <p>Company: </p>
 * 
 * @author Administrator
 * 
 * @version 
 * 
 * @since 
 * 
 * @see 
 * 
 */

public class CmisServiceMQReqHandler extends ServiceHandler{

	/** 
	 * <b>功能描述: </b><br>
	 * <p>MQ渠道交易结果返回处理方法，实现接口方法</p>
	 * @see icbc.cmis.service.channel.Handler#processReply(icbc.cmis.service.channel.ChannelContext)
	 * @param channelContext
	 * 
	 */
	public void processReply(ChannelContext channelContext) {
		MQMessage req=(MQMessage)channelContext.getChannelRequest();
		String resp=(String)channelContext.getChannelResponse();
		MQConnectionService mqConn=null;
		
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
			mqConn=CM2002Service.getMqConnectionPoolService().getMQConnection(resp);
			String fmtDefName=CM2002Service.getOperationRepFormat((String)opData.getValueAt("TransNo"));
			MQMessage respMessage=new MQMessage();
			respMessage.messageId=req.messageId;
			respMessage.write(CM2002Service.format(opData,fmtDefName).getBytes());
			mqConn.send(respMessage);
			CM2002Service.getMqConnectionPoolService().releaseMQConnection(mqConn);
		} catch (Exception e) {
			handleException(channelContext,e);
		}
		
	}

	/** 
	 * <b>功能描述: </b><br>
	 * <p>MQ渠道交易异常处理方法，实现接口方法</p>
	 * @see icbc.cmis.service.channel.Handler#handleException(icbc.cmis.service.channel.ChannelContext, java.lang.Exception)
	 * @param channelContext
	 * @param e
	 * 
	 */
	public void handleException(ChannelContext channelContext, Exception exception) {
		MQMessage req=(MQMessage)channelContext.getChannelRequest();
		String resp=(String)channelContext.getChannelResponse();
		MQConnectionService mqConn=null;
		try {
			KeyedDataCollection opData=channelContext.getRequestData();			
			mqConn=CM2002Service.getMqConnectionPoolService().getMQConnection(resp);
			MQMessage respMessage=new MQMessage();
			respMessage.messageId=req.messageId;
			respMessage.write(CM2002Service.handleException((String)opData.getValueAt("SerialNo"),(String)opData.getValueAt("TransNo"),exception,"000").getBytes());
			mqConn.send(respMessage);
			CM2002Service.getMqConnectionPoolService().releaseMQConnection(mqConn);
		} catch (Exception e) {
			
		}
		
	}
	
}
