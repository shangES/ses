package icbc.cmis.base;

import javax.servlet.http.HttpSession;
import javax.servlet.http.*;

/**
 * Insert the type's description here.
 * Creation date: (2001-12-24 11:13:20)
 * @author: Administrator
 */
public class CMisSessionMgr{
	private HttpSession session = null;
	private HttpServletRequest httpRequest = null;
	private KeyedDataCollection sessionData = null;
	private boolean isChanged = false;
	private boolean isCreate = false;
/**
 * ICBCMisSessionMgr constructor comment.
 */
public CMisSessionMgr(HttpServletRequest request) { 
	httpRequest = request;
	creatSession(false);
}
/**
 * ICBCMisSessionMgr constructor comment.
 */
public CMisSessionMgr(HttpServletRequest request,boolean isCreate1) { 
	httpRequest = request;
	creatSession(isCreate1);
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-24 11:16:06)
 * @param dataName java.lang.String
 * @param dataValue java.lang.String
 */
public void addSessionData(String dataName, Object dataValue)throws TranFailException {
	isChanged = true;
	try{
		if(dataName == null || dataName.trim().length() == 0){
			throw new TranFailException("xdtz22129","CmisSessionMgr.addSessionData(String dataName, Object dataValue)","操作session数据失败,数据名称为空","操作session数据失败,数据名称为空");
		}
		dataName = dataName.trim();
		if(sessionData == null){
		 	throw new TranFailException("xdtz22118","CMisSessionMgr.addSessionData(String dataName, Object dataValue)","session 超时，请重新登录!");
	 	}
		try{
			sessionData.addElement(dataName,dataValue);
		}catch(CTEObjectExistingException e){
			sessionData.setValueAt(dataName,dataValue);
		}
	}catch(TranFailException ee){
		throw ee;
	}
	catch(Exception ee){
		CmisConstance.pringMsg("@@Exception-addSessionData:"+dataName+",value:"+dataValue);
		throw new TranFailException("xdtz22129","CmisSessionMgr.addSessionData(String dataName, Object dataValue)","操作session数据[dataName:"+dataName+"]失败,Exception:"+ee.getMessage(),"操作session数据[dataName:"+dataName+"]失败");
	}
	

}
public static boolean checkSession(HttpServletRequest request) {
	HttpSession sin = null;
	boolean isTimeout = false;
	try{
		sin = request.getSession(false);
		icbc.cmis.base.Trace.trace("",0,0,"","CmisSessionMgr.checkSession()-session is:"+(sin==null?"null":"not null"));
		if(sin == null){
			return false;
		}
		if(sin.isNew()){
			CmisConstance.pringMsg("<WARING>:SESSION BY INVALIDATE BECAUSE SESSION IS NEW IN CHECKSESSION FUNCTION!");
			isTimeout = true;
			KeyedDataCollection tmpKData = (KeyedDataCollection)sin.getAttribute("sessionKCData");
			if(tmpKData == null){
				try{
					CmisConstance.pringMsg("<WARING>:SESSION BY INVALIDATE BECAUSE SESSION IS NEW AND SESSION-DATA IS NULL IN CHECKSESSION FUNCTION!");
					CmisConstance.logonNumsReduce();
					sin.invalidate();
				}catch(Exception eee){}
				return false;
			}
			//icbc.cmis.base.Trace.trace("",0,0,"","CmisSessionMgr.checkSession()-session is new:"+isTimeout);
			
		}else{
			
			KeyedDataCollection tmpKData = (KeyedDataCollection)sin.getAttribute("sessionKCData");
			if(tmpKData == null){
				try{
					CmisConstance.pringMsg("<WARING>:SESSION BY INVALIDATE BECAUSE SESSION-DATA IS NULL IN CHECKSESSION FUNCTION!");
					CmisConstance.logonNumsReduce();
					sin.invalidate();
				}catch(Exception eee){}
				return false;
			}
			
			{
				isTimeout = false;
				String lgin = null;
				try{
					lgin = (String)tmpKData.getValueAt("Login");
				}catch(Exception eeee){
					lgin = null;
				}
				if(lgin == null){
					CmisConstance.pringMsg("<WARING>:SESSION BY INVALIDATE BECAUSE LOGIN-MARK IS NOT EXISTING IN CHECKSESSION FUNCTION!");
					isTimeout = true;
				}
				icbc.cmis.base.Trace.trace("",0,0,"","CmisSessionMgr.checkSession()-isLogin:"+isTimeout+",and Login value:"+lgin);
			
			}
			{
				boolean enable = ((String)CmisConstance.getParameterSettings().get("enableSessionCtl")).equals("true");
				
				if(enable){
					long curSysTime = System.currentTimeMillis();
					long wasInteval = (long)sin.getMaxInactiveInterval();
					String tmpStr = (String)sin.getAttribute("selfLastAccesTime");
					if(tmpStr == null || tmpStr.trim().length() == 0){
						CmisConstance.pringMsg("<WARING>:SESSION BY INVALIDATE BECAUSE selfLastAccesTime is invalid IN CHECKSESSION FUNCTION!");
						isTimeout = true;
					}else{
						long lastAccTime = Long.valueOf(tmpStr).longValue();					
						if((curSysTime - lastAccTime)>=(wasInteval*1000)){
							CmisConstance.pringMsg("<WARING>:SESSION BY INVALIDATE BECAUSE IS TIMEOUT(JUDGE BY APP PROGRAM)G IN CHECKSESSION FUNCTION!");
							isTimeout = true;
						}else{
							isTimeout = false;
						}
						sin.setAttribute("selfLastAccesTime",String.valueOf(curSysTime));
					}
				}
				
			}
		}
		try{
			if(isTimeout){
				try{
					sin.removeAttribute("sessionKCData");
				}catch(Exception er){}
				sin.invalidate();
			}
		}catch(Exception e1){}
		
	}catch(Exception e){
		if(sin != null){
			try{
				sin.removeAttribute("sessionKCData");
			}catch(Exception eee){}
			try{
				CmisConstance.pringMsg("<WARING>:SESSION BY INVALIDATE BECAUSE EXCEPTION IN CHECKSESSION FUNCTION!\nEXCEPTION:"+e.toString());
				sin.invalidate();
			}catch(Exception eee){}
			CmisConstance.logonNumsReduce();
		}
		return false;
	}catch(Throwable e){
		if(sin != null){
			try{
				sin.removeAttribute("sessionKCData");
			}catch(Exception eee){}
			try{
				CmisConstance.pringMsg("<WARING>:SESSION BY INVALIDATE BECAUSE THROWABLE-EXCEPTION IN CHECKSESSION FUNCTION!\nTHROWABLE:"+e.toString());
				sin.invalidate();
			}catch(Exception eee){}
			CmisConstance.logonNumsReduce();
		}
		return false;
	}
	if(isTimeout) {
		CmisConstance.logonNumsReduce();
		return false;
	}
		else return true;
	
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-24 11:22:13)
 */
private void creatSession(boolean isCreate1){
	try{
		isCreate = isCreate1;
		if(isCreate){
			sessionData = new KeyedDataCollection();
			sessionData.setName("sessionKCData");
		}else{
			initSession();
		}
	}catch(Exception e){
		session = null;
		sessionData = null;
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-24 11:25:50)
 * @return java.lang.Object
 * @param dataName java.lang.String
 */
public Object getSessionData(String dataName)throws TranFailException {
	Object o = null;
			
	try{
		dataName = dataName.trim();
		o = sessionData.getValueAt(dataName);
	}catch(CTEObjectNotFoundException e){
		o = null;
	}catch(Exception e){
	}
	return o;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-24 11:28:07)
 */
private void initSession() throws TranFailException{
	try{
		session = httpRequest.getSession(false);
	 	icbc.cmis.base.Trace.trace("",0,0,"","CmisSessionMgr.initSession()-session is:"+(session==null?"null":"not null"));
	 	if(session == null){
		 	throw new TranFailException("xdtz22118","CMisSessionMgr.initsession()","session 超时，请重新登录!");
	 	}
	 	sessionData = (KeyedDataCollection)session.getAttribute("sessionKCData");
	 	//	icbc.cmis.base.Trace.trace("",0,0,"","CmisSessionMgr.initSession()-sessionKCData is:"+sessionData);
	 	
	}catch(TranFailException e){
		if(session != null){
			try{
				session.invalidate();
			}catch(Exception ee){
				session = null;
			}
		}
		throw e;
		
	}
	catch(Exception e){
		if(session != null){
			try{
				session.invalidate();
			}catch(Exception ee){
				session = null;
			}
		}
		throw new TranFailException("xdtz22118","CMisSessionMgr.initsession()","session 超时，请重新登录!");
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-24 11:25:50)
 * @return java.lang.Object
 * @param dataName java.lang.String
 */
public void removeAllSessionData()throws TranFailException {
	icbc.cmis.base.Trace.trace("",0,0,"","CmisSessionMgr.removeAllSessionData()");
	isChanged = true;
	if(sessionData == null){
		 throw new TranFailException("xdtz22118","CMisSessionMgr.removeAllSessionData()","session 超时，请重新登录!");
	 }
	try{
		
		////////////////////////////////////////////////////////////////////////////////////////////
		/*
		 * 修改原因：为了防止CM2002与CTP之间Session数据的不同步。改变清空Session数据的方式。
		 * 修改时间: 2004-08-28
		 * 修改人:YangGuangRun
		 * 
		 */ 
		//sessionData = null;
		//sessionData = new KeyedDataCollection();
		//sessionData.setName("sessionKCData");
		sessionData.clear();
		////////////////////////////////////////////////////////////////////////////////////////////
		
	}catch(Exception e){
		CmisConstance.pringMsg("@@removeAllSessionData Exception@@");
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-24 11:25:50)
 * @return java.lang.Object
 * @param dataName java.lang.String
 */
public void removeSession() throws TranFailException{
	try{
		session.invalidate();
		isChanged = false;
		CmisConstance.logonNumsReduce();
		session = null;
		sessionData = null;
	}catch(Exception e){
		CmisConstance.pringMsg("@@removeSession Exception:\n"+e.toString());
	}
	
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-24 11:16:31)
 * @param dataName java.lang.String
 */
public void removeSessionData(String dataName) throws TranFailException{
	icbc.cmis.base.Trace.trace("",0,0,"","CmisSessionMgr.removeSessionData()-dataName is:"+dataName);
	isChanged = true;
	if(sessionData == null){
		 throw new TranFailException("xdtz22118","CMisSessionMgr.removeSessionData(String dataName)","session 超时，请重新登录!");
	 }
	try{
		dataName = dataName.trim();
		sessionData.removeElement(dataName);
	}catch(Exception e){
		CmisConstance.pringMsg("@@removeSessionData: "+dataName+",Exception: "+e.getMessage());
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2002-5-27 21:27:17)
 */
public void sessionCommit()throws TranFailException {
	try{
		if(isCreate){
			HttpSession s = null;
			try{
				s = httpRequest.getSession(false);
				if(s != null){
					s.invalidate();
					s = null;
				}
			}catch(Exception ee){
				s = null;
			}
			if(sessionData == null){
		 		throw new TranFailException("xdtz22118","CMisSessionMgr.sessionCommit()","session 超时，请重新登录!");
	 		 }
			session = httpRequest.getSession(true);
			if(session == null) throw new Exception("创建session 失败!");
			session.setAttribute("selfLastAccesTime",String.valueOf(System.currentTimeMillis()));
			session.setAttribute("sessionKCData",sessionData);
			
		
		}else{
			 if(session == null || sessionData == null){
		 		throw new TranFailException("xdtz22118","CMisSessionMgr.sessionCommit()","session 超时，请重新登录!");
	 		 }
	
			if(isChanged){
				session.setAttribute("sessionKCData",sessionData);
			}
		}
	}catch(Exception e){
		throw new SignOnException("xdtz","CmisSessionMgr.getSessionData()",e.getMessage(),e.getMessage());
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-24 11:17:17)
 * @param dataName java.lang.String
 * @param newValue java.lang.String
 */
public void updateSessionData(String dataName, Object newValue) throws TranFailException{
	if(sessionData == null){
		 throw new TranFailException("xdtz22118","updateSessionData(String dataName, Object newValue)","session 超时，请重新登录!");
	 }

	try{
		if(dataName == null || dataName.trim().length() == 0){
			throw new TranFailException("xdtz22129","CmisSessionMgr.updateSessionData(String dataName, Object newValue)","更新session数据失败,数据名称为空","更新session数据失败,数据名称为空");
	 
		}
		dataName = dataName.trim();
		try{
			sessionData.setValueAt(dataName,newValue);
		}catch(CTEObjectNotFoundException e){
			sessionData.addElement(dataName,newValue);
		}
		isChanged = true;
	}catch(TranFailException ee){
		throw ee;
	}
	catch(Exception ee){
		throw new TranFailException("xdtz22129","CmisSessionMgr.updateSessionData(String dataName, Object newValue)","更新session数据失败，Exception:"+ee.getMessage(),"更新session数据[dataName:"+dataName+"]失败");
	}
}
	
	/////////////////////////////////////////////////////////////////////////////////////
	/*
	 * 修改原因：由于框架整合时需要访问CM2002里的Session数据，所以添加该方法
	 * 修改时间：2004-07-02
	 * 修改人：杨光润
	 */
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @return
	 *  
	 */
	public KeyedDataCollection getSessionData() {
		return sessionData;
	}
	/////////////////////////////////////////////////////////////////////////////////////
}
