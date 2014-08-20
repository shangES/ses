package icbc.cmis.service;

import com.ibm.mq.MQQueueManager;

import icbc.cmis.base.TranFailException;

import java.io.IOException;

import com.ibm.mq.MQC;
import com.ibm.mq.MQMD;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQEnvironment;
import java.util.Hashtable;
import com.ibm.mq.MQPutMessageOptions;
import java.util.Vector;
import com.ibm.mq.MQException;
import java.util.Enumeration;
import com.ibm.mq.MQQueue;
import java.lang.reflect.Field;
import com.ibm.mq.MQGetMessageOptions;
import java.io.PrintStream;
/**
 * Insert the type's description here.
 * Creation date: (2001-12-24 13:55:11)
 * @author: rainko
 */
public class MQConnectionService {
    private QManagerConnection qmanagerConnection = null;
    private MQQueue sendToQ = null;
    private MQQueue receiveQ = null;
    private java.lang.String channel;
    private int charSet;
    private MQGetMessageOptions dlo;
    private int GetMessageOptionsOptions;
    private MQGetMessageOptions gmo;
    private java.lang.String hostName;
    private boolean inUse;
    private int persistence;

    /*private MQQueue systemCommandQueue= null;
    private MQQueue channelQueue= null;
    private MQQueue DeadLetterQ= null;*/

    private MQPutMessageOptions pmo;
    private int portNumber;
    private int PutMessageOptionsOptions;
    private java.lang.String QMgrName;
    private MQQueueManager QueueManagerReceive;
    private MQQueueManager QueueManagerSend;
    private int receiveReturnCode;
    private java.lang.String ReplyToQName;
    private int ReplyToQOpenOptions;
    private MQResource resource = null;
    private java.lang.String SendToQName;
    private int SendToQOpenOptions;
    private boolean server;
    private boolean synchronousMode;
    int timeOut = 100000;
    private int Timeout; /**
    * ccReceiveData method comment.
    */

/**
 * MQConnectionService constructor comment.
 */
public MQConnectionService() {
	super();
}
public MQConnectionService(QManagerConnection connection, MQResource resource) {
	super();

	qmanagerConnection = connection;
	this.resource = resource;	
	
}
public void closeConnection() throws Exception 
{
	Exception e1 = null;
	try{
		this.sendToQ.close();
	}catch(Exception e){
		e1 = e;
	}
	
	try{
		this.receiveQ.close();
	}catch(Exception e){
		if(e1 != null)
			e1 = e;
	}

	sendToQ = null;
	receiveQ = null;
	if(e1 != null){
		throw e1;
	}
		
}
public void establishConnection() throws Exception {

	MQQueueManager qmanager= this.qmanagerConnection.getQManager();

	//accessQueue(SendToQName, SendToQOpenOptions);
	this.sendToQ= 
		qmanager.accessQueue(resource.getSendToQ(), resource.getSendToQOpenOptions()); 
	this.receiveQ= 
		qmanager.accessQueue(resource.getReplyToQ(), resource.getReplyToQOpenOptions()); 
}
public boolean getAutomaticConnectionEstablishment() {
	return false;
}
public java.lang.String getChannelName() {
	return channel;
}
public int getCharSet() {
	return charSet;
}
public int getGetMessageOptionsOptions()
{
	return gmo.options;
}
public java.lang.String getHostName() {
	return hostName;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-26 13:48:02)
 * @return int
 */ 
public synchronized boolean getInUse()
{
	return inUse;
}
public int getPersistence() {
	return persistence;
}
public int getPortNumber() {
	return portNumber;
}
public int getPutMessageOptionsOptions()
{
	return pmo.options;
}
public QManagerConnection getQManagerConnection() {
	return qmanagerConnection;
}
public java.lang.String getQMgrName() {
	return QMgrName;
}
public java.lang.String getReplyToQName() {
	return ReplyToQName;
}
public int getReplyToQOpenOptions() {
	return ReplyToQOpenOptions;
}
public java.lang.String getSendToQName() {
	return SendToQName;
}
public int getSendToQOpenOptions() {
	return SendToQOpenOptions;
}
public int getTimeout() {
	return Timeout;
}
public void initInstance()throws Exception
{
	if (!server)
	{
		MQEnvironment.hostname= hostName;
		MQEnvironment.channel= channel;
		MQEnvironment.port= portNumber;
	}
	try
	{
		QueueManagerSend= new MQQueueManager(QMgrName);
		QueueManagerReceive= new MQQueueManager(QMgrName);
		pmo= new MQPutMessageOptions();
		gmo= new MQGetMessageOptions();
		pmo.options= PutMessageOptionsOptions;
		gmo.options= GetMessageOptionsOptions;
	}
	catch (MQException mqexception)
	{
		throw mqexception; 
		//throw returnException(mqexception);
	}
	/*if (deadLetterQName != null)
	{
		try
		{
			DeadLetterQManager= new MQQueueManager(QMgrName);
		}
		catch (MQException mqexception1)
		{
			throw returnException(mqexception1);
		}
		dlo= new MQGetMessageOptions();
		dlo.options= DeadLetterQOpenOptions;
	}*/
	/*)if (channelQName != null)
		try
		{
			ChannelQManager= new MQQueueManager(QMgrName);
		}
		catch (MQException mqexception2)
		{
			throw returnException(mqexception2);
		}
	if (automaticConnectionEstablishment)
		try
		{
			establishConnection();
		}
		catch (Exception _ex)
		{
			signalEvent("closed");
		}*/
}/**
 * isFree method comment.
 */
public boolean isFree() {
	return false;
}
public boolean isOffline() {
	return false;
}
public boolean isServer() {
	return server;
}
public boolean isSynchronousMode() {
	return synchronousMode;
}
public Object receive(byte[] arg1)  throws icbc.cmis.base.TranFailException  {
	return receive(arg1, timeOut);
}
public Object receive(byte[] arg1, long arg2) throws icbc.cmis.base.TranFailException {

	try{
		MQMessage mqmessage = new MQMessage();
		mqmessage.correlationId = arg1;

		MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = resource.getGetMessageOptions();

		if (arg2 > 0)
			gmo.waitInterval =(int) arg2;
		else
			gmo.waitInterval = -1;

		receiveQ.get(mqmessage, gmo);
		int len  = mqmessage.getMessageLength();
		
		if (len <= 0){

			throw new icbc.cmis.base.TranFailException("xdtz22105", "MQReceiveService.receive(byte[] arg1, long arg2)", "No Message avalable!");
		}
		
		byte[] recMsg = new byte[len];
		mqmessage.readFully(recMsg);
		return new String(recMsg);

		
	}catch(icbc.cmis.base.TranFailException e){
		throw e;
	}
	catch(Exception e)
	{
	//	e.printStackTrace();
		throw new icbc.cmis.base.TranFailException("xdtz22105","MQConnectionService.receive(byte[] arg1, long arg2)",e.getMessage());
	}
}
////////////////////////////////////////////////////////////////
//
//	修改原因：为了MQ监听程序能够按顺序读取消息
//	修改人：杨光润
//	修改时间：2004-10-22
////////////////////////////////////////////////////////////////
public MQMessage receive()throws MQException {
	return receive(timeOut);
}

public MQMessage receive(long timeout) throws MQException{
	
	MQMessage mqmessage = new MQMessage();
	MQGetMessageOptions gmo = new MQGetMessageOptions();
	gmo.options = resource.getGetMessageOptions();
	if (timeout > 0)
		gmo.waitInterval =(int) timeout;
	else
		gmo.waitInterval = -1;

	try {
		receiveQ.get(mqmessage, gmo);
	} catch (MQException e) {
		if(e.reasonCode==2033)
			mqmessage=null;
		if(e.reasonCode==2009){
			throw e;
		}
	}
	return mqmessage;
}
/////////////////////////////////////////////////////////

public byte[] send(MQMessage mqmessage) throws icbc.cmis.base.TranFailException {

	try
	{
		MQPutMessageOptions pmo= new MQPutMessageOptions();
		pmo.options= resource.getPutMessageOptions();

		sendToQ.put(mqmessage, pmo);
		return mqmessage.messageId;

	}
	catch (MQException e)
	{
	//	e.printStackTrace();
		throw new icbc.cmis.base.TranFailException("xdtz22106","MQConnectionService.receive(byte[] arg1, long arg2)",e.getMessage());
	
	}

}
public byte[] send(Object message) throws icbc.cmis.base.TranFailException {
	try{
		MQPutMessageOptions pmo= new MQPutMessageOptions();
		pmo.options= resource.getPutMessageOptions();
		this.sendToQ.put((MQMessage) message, pmo);

		return ((MQMessage) message).messageId;
	}
	catch (MQException e)
	{
	//	e.printStackTrace();
		throw new icbc.cmis.base.TranFailException("xdtz22106","MQConnectionService.receive(byte[] arg1, long arg2)",e.getMessage());
	
	}


}
public byte[] send(String arg1) throws IOException,icbc.cmis.base.TranFailException{
		
	try
	{
		MQMessage message = new MQMessage();
		message.write(arg1.getBytes());

	//	message.persistence = getPersistence();
	//	message.expiry = ;
	//	message.priority = tranInfo.msgPriority;

		MQPutMessageOptions pmo= new MQPutMessageOptions();
		pmo.options= resource.getPutMessageOptions();

		sendToQ.put(message, pmo);
		icbc.cmis.base.Trace.trace("MQSendMsg",0,0,"",arg1);
		return message.messageId;

	}
	catch (MQException e)
	{
		
	//	e.printStackTrace();
		throw new icbc.cmis.base.TranFailException("xdtz22106","MQConnectionService.receive(byte[] arg1, long arg2)",e.getMessage());
	
	}
}
public void setChannelName(java.lang.String newChannelName) {
	channel= newChannelName;
}
public void setCharSet(int newCharSet) {
	charSet = newCharSet;
}
public void setGetMessageOptionsOptions(int i)
{
	gmo.options= i;
}
public void setHostName(java.lang.String newHostName) {
	hostName = newHostName;
}
public void setInUse(boolean arg1) {
	qmanagerConnection.setIsInUse(arg1);
}
public void setPersistence(int newPersistence) {
	persistence = newPersistence;
}
public void setPortNumber(int newPortNumber) {
	portNumber = newPortNumber;
}
public void setPutMessageOptionsOptions(int i)
{
	pmo.options= i;
}
public void setQMgrName(java.lang.String newQMgrName) {
	QMgrName = newQMgrName;
}
public void setReplyToQName(java.lang.String newReplyToQName) {
	ReplyToQName = newReplyToQName;
}
public void setReplyToQOpenOptions(int newReplyToQOpenOptions) {
	ReplyToQOpenOptions = newReplyToQOpenOptions;
}
public void setSendToQName(java.lang.String newSendToQName) {
	SendToQName = newSendToQName;
}
public void setSendToQOpenOptions(int newSendToQOpenOptions) {
	SendToQOpenOptions = newSendToQOpenOptions;
}
public void setServer(boolean newServer) {
	server = newServer;
}
public void setSynchronousMode(boolean newSynchronousMode) {
	synchronousMode = newSynchronousMode;
}
public void setTimeout(int newTimeout) {
	Timeout = newTimeout;
}
}
