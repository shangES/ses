package icbc.cmis.mgr;

/**
 * Insert the type's description here.
 * Creation date: (2001-11-21 23:48:47)
 * @author: Administrator
 */
public class MonitorUserInfo {
	private String userName = null;
	private String userPass = null;
	private boolean isMgr = false;
	private String serverName = null;
	private boolean isMemTimeMonit = false;
	private boolean isCtxTimeMonit = false;
	private ReloadSettingsDlg reloadSettingsDlg  = null;
/**
 * MonitorUserInfo constructor comment.
 */
public MonitorUserInfo() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:53:08)
 */
public boolean  getIsCtxTimeMonit() {
	return this.isCtxTimeMonit;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:53:08)
 */
public boolean  getIsMemTimeMonit() {
	return this.isMemTimeMonit;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-23 10:56:03)
 * @param frame com.ecc.echannels.util.MonitorCtxTreeFrame
 */
public ReloadSettingsDlg getMonitorReloadSettingDlg() {
	return this.reloadSettingsDlg;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:53:08)
 */
public String  getServerName() {
	return this.serverName;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:53:08)
 */
public String  getUserName() {
	return this.userName;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:53:08)
 */
public String  getUserPass() {
	return this.userPass;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:53:08)
 */
public boolean  getUserType() {
	return this.isMgr;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:53:08)
 */
public void setIsCtxTimeMonit(boolean tf) {
	this.isCtxTimeMonit = tf;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:53:08)
 */
public void setIsMemTimeMonit(boolean tf) {
	this.isMemTimeMonit = tf;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-23 10:56:03)
 * @param frame com.ecc.echannels.util.MonitorCtxTreeFrame
 */
public void setMonitorReloadSettingDlg(ReloadSettingsDlg dlg) {
	this.reloadSettingsDlg = dlg;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:53:08)
 */
public void setServerName(String serverName1) {
	this.serverName = serverName1;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:53:08)
 */
public void setUserName(String userName1) {
	this.userName = userName1;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:53:08)
 */
public void setUserPass(String userPass1) {
	this.userPass = userPass1;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:53:08)
 */
public void setUserType(boolean tf) {
	this.isMgr = tf;
}
}
