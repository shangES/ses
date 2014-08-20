package icbc.cmis.service;

import com.ibm.mq.*;
/**
 * Insert the type's description here.
 * Creation date: (2001-4-9 13:06:56)
 * @author: Administrator
 */
public class QManagerConnection {

	private MQQueueManager qmanager = null;
	private boolean isInUse = false;
	
/**
 * QManager constructor comment.
 */
public QManagerConnection() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-9 13:29:06)
 * @return boolean
 */
public synchronized boolean getIsInUse() {
	return isInUse;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-9 13:10:35)
 * @return com.ibm.mq.MQQueueManager
 */
public MQQueueManager getQManager() {
	return qmanager;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-9 13:29:38)
 * @param inUse boolean
 */
public synchronized void setIsInUse(boolean inUse)
{
	isInUse = inUse;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-9 14:43:18)
 * @param qm com.ibm.mq.MQQueueManager
 */
public void setMQQueueManager(MQQueueManager qm)
{
	this.qmanager = qm;

}
}