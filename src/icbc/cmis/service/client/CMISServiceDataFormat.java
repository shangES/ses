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

import java.io.IOException;

import com.ibm.mq.MQMessage;

import icbc.cmis.base.CTEInvalidRequestException;
import icbc.cmis.base.CTEObjectExistingException;
import icbc.cmis.base.CTEObjectNotFoundException;
import icbc.cmis.base.CTEUncausedException;
import icbc.cmis.base.FormatElement;
import icbc.cmis.base.KeyedDataCollection;
import icbc.cmis.base.XMLReader;

/**
 * 
 * <b>创建日期: </b> 2004-10-21<br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * <br>
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

public class CMISServiceDataFormat {
	
	XMLReader xmlReader=null;
	
	/**
	 * <b>构造函数</b><br>
	 * 
	 */
	public CMISServiceDataFormat(){
		xmlReader=CMISServiceDataForamtService.getXMLReader();
	}
	
	/**
	 * <b>构造函数</b><br>
	 * @param xmlFile
	 * @throws Exception
	 */
	public CMISServiceDataFormat(String xmlFile)throws Exception{
		xmlReader=new XMLReader();
		xmlReader.loadXMLFile(xmlFile);
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param data
	 * @param fmtDefName
	 * @param dataName
	 * @return
	 * @throws CTEUncausedException
	 * @throws CTEObjectExistingException
	 * @throws CTEObjectNotFoundException
	 * @throws CTEInvalidRequestException
	 *  
	 */
	public KeyedDataCollection unformat(String data,String fmtDefName,String dataName) 
		throws CTEUncausedException, CTEObjectExistingException, 
			CTEObjectNotFoundException, CTEInvalidRequestException{
		FormatElement fmtElement=xmlReader.readFormatElement(fmtDefName);
		KeyedDataCollection kCol=new KeyedDataCollection(dataName);
		return (KeyedDataCollection) fmtElement.unformat(data,kCol);
	}
	
	public KeyedDataCollection unformat(MQMessage data,String fmtDefName,String dataName)
		throws CTEUncausedException, CTEObjectExistingException, 
				CTEObjectNotFoundException, CTEInvalidRequestException, IOException{
		int len=data.getMessageLength();
		byte[] recMsg = new byte[len];
		data.readFully(recMsg);
		return unformat(new String(recMsg),fmtDefName,dataName);
	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param data
	 * @param fmtDefName
	 * @return
	 * @throws CTEUncausedException
	 * @throws CTEObjectExistingException
	 * @throws CTEObjectNotFoundException
	 * @throws CTEInvalidRequestException
	 *  
	 */
	public String format(KeyedDataCollection data,String fmtDefName) throws CTEUncausedException, CTEObjectExistingException, CTEObjectNotFoundException, CTEInvalidRequestException{
		FormatElement fmtElement=xmlReader.readFormatElement(fmtDefName);
		return fmtElement.format(data);
	}
	
}
