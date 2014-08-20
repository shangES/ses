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

import java.util.*;
import javax.servlet.http.*;

/**
 * 
 * <b>创建日期: </b> 2004-9-17><br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * 会话管理器，用于存储会话实体，并对回话的有效性进行校验<br>
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

public class SessionManager {
	private static Hashtable sEntries = new Hashtable(); //session实体集合
	private SessionEntry sEntry = null; //session实体
	private ChannelContext channelContext = null; 

	/**
	 * <b>构造函数</b><br>
	 * @param request
	 */
	public SessionManager(ChannelContext channelContext) {
		this.channelContext = channelContext;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p>判断当前用户会话是否有效</p>
	 * @param authenticater ID认证校验器
	 * @return boolean 会话是否有效
	 *  
	 */
	public boolean isValid(IDAuthenticate authenticater) throws TranFailException  {
		if(authenticater==null)
			return true;
		Hashtable sessionData=(Hashtable)channelContext.getChannelSession();
		String cur_authId =(String)sessionData.get("AuthId");
		String cur_verifyStr =(String)sessionData.get("VerifyStr");
		String cur_type = (String)sessionData.get("SysID");
		
		long sys_timeStamp = System.currentTimeMillis();
		
		Long cur_timeStamp = new Long(sys_timeStamp);
		if (cur_authId == null ){
			return false;
		}
		
		if (sEntries.containsKey(cur_authId)) {
			sEntry = (SessionEntry) sEntries.get(cur_authId);
			Long timeStamp = sEntry.getTimeStamp();
			if (cur_timeStamp.longValue() - timeStamp.longValue()
				> getMaxTimeInterval()) {
				if (authenticater.isAuthenticated(cur_authId,cur_verifyStr)) {
					sEntry.setTimeStamp(cur_timeStamp);
					return true;
				} else {
					sEntries.remove(cur_authId);
					return false;
				}
			} else {
				return true;
			}
		} else {
			if (authenticater.isAuthenticated(cur_authId,cur_verifyStr)) {
				sEntry =
					new SessionEntry(cur_type, cur_timeStamp, cur_authId,cur_verifyStr);
				sEntries.put(cur_authId, sEntry);
				return true;
			} else {
				return false;
			}
		}
		
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p>获取系统的会话超时允许的最大时间间隔 </p>
	 * @return
	 *  
	 */
	private long getMaxTimeInterval() {
		return Long.valueOf((String)CM2002Service.getSettings().get("SessionMaxTimeInterval")).longValue();
	}

}
