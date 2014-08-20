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
import java.util.Hashtable;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.mq.MQMessage;
import icbc.cmis.base.XMLReader;

/**
 * 
 * <b>创建日期: </b> 2004-10-24<br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * 交易结果数据包校验，也可以直接获取错误信息<br>
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

public class CmisServiceValidator {

	/**
	 * <b>功能描述: </b><br>
	 * <p>获取交易错误信息，如果交易发生错误则返回的Hashtable 中的"Error数 </p>
	 * 数据域的值为"true"否则为"false",如果为"true"，你可以进一步获取"ErrorCode"<br>
	 * 错误码和"ErrorMsg"错误信息<br>
	 * @param result
	 * @return
	 * @throws Exception
	 *  
	 */
	public static Hashtable getTransError(String result) throws Exception{
		XMLReader xmlReader=new XMLReader();
		xmlReader.loadXMLContentNew(result);
		Hashtable ret=new Hashtable();
		Node aNode=xmlReader.getNode("CM2002Err");
		if(aNode!=null){
			ret.put("Error","true");
			NodeList list=aNode.getChildNodes();
			for(int i=0;i<list.getLength();i++){
				Node tmpNode=list.item(i);
				if(isElementNode(tmpNode))
					if(tmpNode.getNodeName().equalsIgnoreCase("Error")){
						NodeList errList=tmpNode.getChildNodes();
						for(int j=0;j<errList.getLength();j++){
							Node errNode=errList.item(j);
							if(isElementNode(errNode)){
								if(errNode.getNodeName().equalsIgnoreCase("ErrNo")){
									NodeList tempList=errNode.getChildNodes();
									String value=(tempList.item(0)).getNodeValue();
									ret.put("ErrorCode",value);
								}else{
									if(errNode.getNodeName().equalsIgnoreCase("ErrMsg")){
										NodeList tempList=errNode.getChildNodes();
										String value=(tempList.item(0)).getNodeValue();
										ret.put("ErrorMsg",value);
									}
									
								}		
							}	
						}
						break;	
					}
			}
			return ret;
		}else{
			ret.put("Error","false");
			return ret;
		}
	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p>获取交易错误信息，如果交易发生错误则返回的Hashtable 中的"Error数 </p>
	 * 数据域的值为"true"否则为"false",如果为"true"，你可以进一步获取"ErrorCode"<br>
	 * 错误码和"ErrorMsg"错误信息<br>
	 * @param result
	 * @return
	 * @throws Exception
	 *  
	 */
	public static Hashtable getTransError(MQMessage result) throws Exception{
		int len=result.getMessageLength();
		byte[] recMsg = new byte[len];
		result.readFully(recMsg);
		return getTransError(new String(recMsg));
	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p>判断一个结果数包是否是一个有效的据包，是返回true,否则返回false </p>
	 * @param result
	 * @return
	 * @throws Exception
	 *  
	 */
	public static boolean isTransValid(String result) throws Exception{
		XMLReader xmlReader=new XMLReader();
		xmlReader.loadXMLContentNew(result);
		Node aNode=xmlReader.getNode("CM2002Err");
		if(aNode!=null)
			return true;
		else
			return false;
	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p>判断一个结果数包是否是一个有效的据包，是返回true,否则返回false </p>
	 * @param result
	 * @return
	 * @throws Exception
	 *  
	 */
	public static boolean isTransValid(MQMessage result) throws Exception{
		int len=result.getMessageLength();
		byte[] recMsg = new byte[len];
		result.readFully(recMsg);
		return isTransValid(new String(recMsg));
	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param tmpNode
	 * @return
	 *  
	 */
	private static boolean isElementNode(Node aNode) {
		if (aNode.getNodeType() == Node.ELEMENT_NODE)
			return true;
		else
			return false;
	}
}
