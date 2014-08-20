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

import icbc.cmis.base.TranFailException;

/*************************************************************
 * 
 * <b>创建日期: </b> 2004-9-21<br>
 * <b>标题: </b>外系统身份验证接口<br>
 * <b>类描述: </b><br>
 * 	该接口用于描述CM2002响应外系统请求时，对请求的身份验证。<br>
 * <p>Copyright: Copyright (c)2004</p>
 * <p>Company: </p>
 * 
 * @author YangGuangrun
 * 
 * @version 
 * 
 * @since 
 * 
 * @see 
 * 
 *************************************************************/

public interface IDAuthenticate {
	/**
	 * <b>功能描述: </b><br>
	 * <p>身份验证接口方法，该方法将根据用户标识和验证码验证用户身份是否有效
	 *  如果有效返回true,否则返回false</p>
	 * @param ID
	 * @param verifyStr
	 * @return
	 * @throws TranFailException
	 *  
	 */
	public boolean isAuthenticated(Object ID,Object verifyStr) throws TranFailException;
}
