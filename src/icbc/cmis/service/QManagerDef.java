package icbc.cmis.service;

/**
 * Insert the type's description here.
 * Creation date: (2001-4-9 15:12:54)
 * @author: rainko
 */
public class QManagerDef {

	private String hostName = "";
	private int port = 1414;

	private String qmanagerName = "";
	
	private String channelName = "";
	private int charSet = 936;
	
/**
 * QManagerDef constructor comment.
 */
public QManagerDef() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-27 17:44:29)
 * @param channelName java.lang.String
 */
public void setChannelName(String channelName1) {channelName = channelName1;}/**
 * Insert the method's description here.
 * Creation date: (2001-12-27 17:44:56)
 * @param charSet java.lang.String
 */
public void setCharSet(int charSet1) {charSet = charSet1;}/**
 * Insert the method's description here.
 * Creation date: (2001-12-27 17:45:18)
 * @param hostName java.lang.String
 */
public void setHostName(String hostName1) {hostName = hostName1;}/**
 * Insert the method's description here.
 * Creation date: (2001-12-27 17:45:18)
 * @param hostName java.lang.String
 */
public void setPort(int intP) {
	port = intP;
}/**
 * Insert the method's description here.
 * Creation date: (2001-12-27 17:45:18)
 * @param hostName java.lang.String
 */
public void setQManagerName(String qManagerName1) {
	qmanagerName = qManagerName1;
}
}