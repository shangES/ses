package icbc.cmis.service;

/**
 * Insert the type's description here.
 * Creation date: (2001-1-2 14:22:34)
 * @author: Administrator
 */

import com.ibm.mq.*;

import icbc.cmis.service.MQConnectionService;
import icbc.cmis.base.*;

public class MQResource {

    private java.lang.String resourceID;
    protected java.lang.String hostName = "127.0.0.1";
    protected int port = 1414;
    protected java.lang.String channel;
    protected java.lang.String sendToQ;
    protected java.lang.String replyToQ;

    protected int maxConnections = 20;

    java.util.Vector connections = new java.util.Vector();
    private java.lang.String qManagerName;
    private int charSet = 936;
    protected int getMessageOptions = MQC.MQGMO_WAIT;
    protected int putMessageOptions = MQC.MQPMO_NO_SYNCPOINT;
    protected int replyToQOptions = MQC.MQOO_INPUT_SHARED;
    protected int sendToQOptions = MQC.MQOO_OUTPUT;
    protected int timeOut = 1000;
/**
 * MQResource constructor comment.
 */
public MQResource() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 14:40:32)
 * @return java.lang.String
 */
public java.lang.String getChannel() {
	return channel;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-11 11:21:30)
 * @return int
 */
public int getCharSet() {
	return charSet;
}
public int getGetMessageOptions()
{
		return getMessageOptions;
			
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 14:39:54)
 * @return java.lang.String
 */
public java.lang.String getHostName() {
	return hostName;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 19:53:35)
 * @return int
 */
public int getMaxConnections() {
	return maxConnections;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-3-20 13:45:31)
 * @param options java.lang.String
 */ 
public int getMQOpenOption(String options)
{

	String[] opNames = 
		{
			"MQOO_INPUT_AS_Q_DEF", 
			"MQOO_INPUT_SHARED", 
			"MQOO_INPUT_EXCLUSIVE", 
			"MQOO_BROWSE", 
			"MQOO_OUTPUT", 
			"MQOO_SAVE_ALL_CONTEXT", 
			"MQOO_ALTERNATE_USER_AUTHORITY", 
			"MQOO_FAIL_IF_QUIESCING", 
			"MQOO_PASS_IDENTITY_CONTEXT", 
			"MQOO_PASS_ALL_CONTEXT", 
			"MQOO_SET_IDENTITY_CONTEXT", 
			"MQOO_SET_ALL_CONTEXT", 
			"MQOO_INQUIRE", 
			"MQOO_SET"}; 

	int[] values = 
		{
			MQC.MQOO_INPUT_AS_Q_DEF, 
			MQC.MQOO_INPUT_SHARED, 
			MQC.MQOO_INPUT_EXCLUSIVE, 
			MQC.MQOO_BROWSE, 
			MQC.MQOO_OUTPUT, 
			MQC.MQOO_SAVE_ALL_CONTEXT, 
			MQC.MQOO_ALTERNATE_USER_AUTHORITY, 
			MQC.MQOO_FAIL_IF_QUIESCING, 
			MQC.MQOO_PASS_IDENTITY_CONTEXT, 
			MQC.MQOO_PASS_ALL_CONTEXT, 
			MQC.MQOO_SET_IDENTITY_CONTEXT, 
			MQC.MQOO_SET_ALL_CONTEXT, 
			MQC.MQOO_INQUIRE, 
			MQC.MQOO_SET}; 

	int opts = 0;
	for (int i = 0; i < opNames.length; i++)
	{
		if (options.indexOf(opNames[i]) != -1)
			opts = opts + values[i];
	}

	return opts;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 14:40:12)
 * @return int
 */
public int getPort() {
	return port;
}
public int getPutMessageOptions()
{
	return putMessageOptions;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-10 13:54:24)
 * @return java.lang.String
 */
public java.lang.String getQManagerName() {
	return qManagerName;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 17:06:32)
 * @return java.lang.String
 */
public java.lang.String getReplyToQ() {
	return replyToQ;
}
public int getReplyToQOpenOptions() {
	
	return replyToQOptions;
	
	}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 14:39:25)
 * @return java.lang.String
 */
public java.lang.String getResourceID() {
	return resourceID;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 14:41:08)
 * @return java.lang.String
 */
public java.lang.String getSendToQ() {
	return sendToQ;
}
public int getSendToQOpenOptions()
{
		return sendToQOptions;
}/**
 * Insert the method's description here.
 * Creation date: (2001-1-4 16:41:17)
 */
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 14:40:32)
 * @param newChannel java.lang.String
 */
public void setChannel(java.lang.String newChannel) {
	channel = newChannel;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-11 11:21:30)
 * @param newCharSet int
 */
public void setCharSet(int newCharSet) {
	charSet = newCharSet;
}
public void setGetMessageOptions(String options)
{

	String[] optionNames = 
		{
			"MQGMO_WAIT", 
			"MQGMO_NO_WAIT", 
			"MQGMO_SYNCPOINT", 
			"MQGMO_NO_SYNCPOINT", 
			"MQGMO_BROWSE_FIRST", 
			"MQGMO_BROWSE_NEXT", 
			"MQGMO_BROWSE_MSG_UNDER_CURSOR", 
			"MQGMO_MSG_UNDER_CURSOR", 
			"MQGMO_LOCK", 
			"MQGMO_UNLOCK", 
			"MQGMO_ACCEPT_TRUNCATED_MSG", 
			"MQGMO_FAIL_IF_QUIESCING", 
			"MQGMO_CONVERT", 
			"MQGMO_NONE"}; 

	int[] values = 
		{
			MQC.MQGMO_WAIT, 
			MQC.MQGMO_NO_WAIT, 
			MQC.MQGMO_SYNCPOINT, 
			MQC.MQGMO_NO_SYNCPOINT, 
			MQC.MQGMO_BROWSE_FIRST, 
			MQC.MQGMO_BROWSE_NEXT, 
			MQC.MQGMO_BROWSE_MSG_UNDER_CURSOR, 
			MQC.MQGMO_MSG_UNDER_CURSOR, 
			MQC.MQGMO_LOCK, 
			MQC.MQGMO_UNLOCK, 
			MQC.MQGMO_ACCEPT_TRUNCATED_MSG, 
			MQC.MQGMO_FAIL_IF_QUIESCING, 
			MQC.MQGMO_CONVERT, 
			MQC.MQGMO_NONE};

		int opt = 0;

		for( int i=0; i< optionNames.length; i++)
		{
			if( options.indexOf(optionNames[i] )!= -1)
				opt = opt + values[i];
		}
		if( opt != 0)
			this.getMessageOptions = opt;
			
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 14:39:54)
 * @param newHostName java.lang.String
 */
public void setHostName(java.lang.String newHostName) {
	hostName = newHostName;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 19:53:35)
 * @param newMaxConnections int
 */
public void setMaxConnections(int newMaxConnections) {
	maxConnections = newMaxConnections;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 14:40:12)
 * @param newPort int
 */
public void setPort(int newPort) {
	port = newPort;
}
public void setPutMessageOptions(String options)
{

	String[] optionNames = 
		{
			"MQPMO_SYNCPOINT", 
			"MQPMO_NO_SYNCPOINT", 
			"MQPMO_NO_CONTEXT", 
			"MQPMO_DEFAULT_CONTEXT", 
			"MQPMO_PASS_IDENTITY_CONTEXT", 
			"MQPMO_PASS_ALL_CONTEXT", 
			"MQPMO_SET_IDENTITY_CONTEXT", 
			"MQPMO_SET_ALL_CONTEXT", 
			"MQPMO_ALTERNATE_USER_AUTHORITY", 
			"MQPMO_FAIL_IF_QUIESCING", 
			"MQPMO_NONE"}; 

	int[] values = 
		{
			MQC.MQPMO_SYNCPOINT, 
			MQC.MQPMO_NO_SYNCPOINT, 
			MQC.MQPMO_NO_CONTEXT, 
			MQC.MQPMO_DEFAULT_CONTEXT, 
			MQC.MQPMO_PASS_IDENTITY_CONTEXT, 
			MQC.MQPMO_PASS_ALL_CONTEXT, 
			MQC.MQPMO_SET_IDENTITY_CONTEXT, 
			MQC.MQPMO_SET_ALL_CONTEXT, 
			MQC.MQPMO_ALTERNATE_USER_AUTHORITY, 
			MQC.MQPMO_FAIL_IF_QUIESCING, 
			MQC.MQPMO_NONE};

		int opt = 0;

		for( int i=0; i<optionNames.length; i++)
		{
			if( options.indexOf(optionNames[i]) != -1 )
				opt = opt + values[i];
		}
		if( opt != 0)
			this.putMessageOptions = opt;
			

}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-10 13:54:24)
 * @param newQManagerName java.lang.String
 */
public void setQManagerName(java.lang.String newQManagerName) {
	qManagerName = newQManagerName;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 17:06:32)
 * @param newReplyToQ java.lang.String
 */
public void setReplyToQ(java.lang.String newReplyToQ) {
	replyToQ = newReplyToQ;
}
public void setReplyToQOpenOptions(String options) {
	
	this.replyToQOptions = getMQOpenOption( options );
	
	}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 14:39:25)
 * @param newResourceID java.lang.String
 */
public void setResourceID(java.lang.String newResourceID) {
	resourceID = newResourceID;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 14:41:08)
 * @param newSendToQ java.lang.String
 */
public void setSendToQ(java.lang.String newSendToQ) {
	sendToQ = newSendToQ;
}
public void setSendToQOpenOptions(String options)
{
		this.sendToQOptions = getMQOpenOption( options );
}
public void setTimeOut(String tm)
{
	try{
		this.timeOut = new Integer(tm).intValue();
	}catch(Exception e)
	{
	}
}
}
