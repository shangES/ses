package icbc.cmis.service;

/**
 * Insert the type's description here.
 * Creation date: (2001-4-9 13:13:37)
 * @author: Administrator
 */

import com.ibm.mq.*;
import java.util.*;

 
import icbc.cmis.base.*;public class QManagerConnectionManager {
   
	public String hostName = "";
	public String qmanagerName = "";
	public String channelName = "";

	public int port = 1414;

	public int charSet = 936;
	


	
	
	
	//private String userId= "";
	//private String password= "";

	private Vector qManagerConnections = new Vector(10);

	
/**
 * QManagerConnection constructor comment.
 */
public QManagerConnectionManager() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-9 13:33:37)
 * @return com.icbc.inbs.service.QManagerConnection
 */
private QManagerConnection establishNewConnection() throws TranFailException {

	try
	{
		synchronized(this){
			MQConnectionPoolService.mqConnCount++;
		}
		QManagerConnection qm = new QManagerConnection();
		qm.setIsInUse(true);

		
		MQEnvironment.hostname = this.hostName; 
		MQEnvironment.channel = this.channelName; 
		MQEnvironment.port = this.port;

//		MQEnvironment.userID = getUserIDField().getText();
//		MQEnvironment.password = new String(getPasswordField().getPassword());

		MQEnvironment.CCSID = this.charSet;
	
		MQQueueManager qmanager = new MQQueueManager(this.qmanagerName);
		
		qm.setMQQueueManager( qmanager );
		
		qManagerConnections.add(qm);
		
		return qm;
	}
	catch (Exception e)
	{
		synchronized(this){
			MQConnectionPoolService.mqConnCount--;
		}
		throw new TranFailException("xdtz22115", "QManagerConnectionManager.establishNewConnection()", e.getMessage());
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-9 13:31:37)
 * @return com.icbc.inbs.service.QManagerConnection
 */
public synchronized QManagerConnection getQManagerConnection()
	throws TranFailException {

	int retryCount= 0;
	while (true)
	{
		for (int i= 0; i < this.qManagerConnections.size(); i++)
		{
			QManagerConnection qm= (QManagerConnection) qManagerConnections.elementAt(i);
			if (!qm.getIsInUse())
			{
				synchronized (this)
			{
					qm.setIsInUse(true);
					return qm;
				}
			}
		}

		if (qManagerConnections.size() < this.maxConnections)
		{
			return establishNewConnection();
		}

		else
		{
			try
			{
				Thread.currentThread().wait(600);
				retryCount++;
				if (retryCount > 10)
				{
					//ICBCINBSConstants.monitor.sendMessageToServer(" Warning the max connections to QManager:[" + this.qmanagerName + "was too small!");
					System.out.println(
						" Warning the max connections to QManager:["
							+ this.qmanagerName
							+ "was too small!"); 
					return null;
				}

			}
			catch (Exception e)
			{
			}
		}
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-9 13:32:13)
 * @param qmanager com.icbc.inbs.service.QManagerConnection
 */
public void releaseQManagerConnection(QManagerConnection qmanager)
{
	qmanager.setIsInUse(false);
	 synchronized(this){
		MQConnectionPoolService.mqConnCount--;
	 }
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-9 13:32:13)
 * @param qmanager com.icbc.inbs.service.QManagerConnection
 */
public synchronized void removeQManagerConnection(QManagerConnection qmanager)
{
	try{
		qmanager.getQManager().close();
	}catch(Exception e){
		}
	
	qManagerConnections.removeElement( qmanager );
	synchronized(this){
		MQConnectionPoolService.mqConnCount--;
	}
	
}
	public int maxConnections = 10;}