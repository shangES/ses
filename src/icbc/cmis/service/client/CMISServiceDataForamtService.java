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

import icbc.cmis.base.XMLReader;

/**
 * 
 * <b>创建日期: </b> 2004-10-21<br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * 数据化格式服务，该服务主要用于数据格式定义文件的初始化，服务初始化完成之后<br>
 * 对数据进行格式化和反格式化时就不需要再次读取格式定义文件。<br>
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

public class CMISServiceDataForamtService {
	
	private static XMLReader xmlReader=null;
	/**初始化标志，true 已经初始化，false 还未初始化*/
	private static boolean isInitialized=false;
		
	private CMISServiceDataForamtService(){
	}
	
	
	/**
	 * <b>功能描述: </b><br>
	 * <p>对服务进行初始化 </p>
	 * @param xmlFileName
	 * @throws Exception
	 *  
	 */
	public static void initFormatServiece(String xmlFileName)throws Exception{
		getXMLReader().loadXMLFile(xmlFileName);
		isInitialized=true;
	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p>获取XMLReader,通长需要在完成初始化调用后在进行调用 </p>
	 * @return
	 *  
	 */
	public static XMLReader getXMLReader(){
		if(xmlReader==null)
			xmlReader=new XMLReader();
		return xmlReader;
	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p>判断服务是否已经被初始化 </p>
	 * @return
	 *  
	 */
	public static boolean isInitialized(){
		return isInitialized;
	}
}
