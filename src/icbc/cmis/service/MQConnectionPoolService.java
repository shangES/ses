package icbc.cmis.service;

import java.util.*;

import icbc.cmis.base.*;
public class MQConnectionPoolService {

	private Hashtable mqResources= new Hashtable(20);

	private Hashtable qmanagers= new Hashtable();
	public static int mqConnCount = 0;
	public static int inUsedMqConnCount = 0;

/**
 * MQConnectionPoolService constructor comment.
 */
public MQConnectionPoolService() {
	super();


}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-2 14:36:49)
 * @param resource com.icbc.inbs.service.MQResource
 */
public void addMQResource(MQResource resource) {

	this.mqResources.put(resource.getResourceID(), resource);
		
}
public void addQManager(QManagerConnectionManager qmanager)
{
	this.qmanagers.put(qmanager.qmanagerName, qmanager);
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-25 9:23:17)
 * @param resourceName java.lang.String
 */
public MQConnectionService getMQConnection(String resourceName)
	throws TranFailException {
	MQResource resource= (MQResource) this.mqResources.get(resourceName);

	QManagerConnectionManager qdf= (QManagerConnectionManager) this.qmanagers.get(resource.getQManagerName());

	if (resource == null || qdf == null)
	{
		throw new TranFailException(
			"xdtz22114", 
			"MQConnectionPoolService.getMQConnection(String)", 
			"MQ Resource [" + resourceName + "]not found in file icbccmis.xml"); 
	}

	MQConnectionService mqConnection = null;

	try
	{
		QManagerConnection qmgrc = null;
		try{
			qmgrc = qdf.getQManagerConnection();
			if(qmgrc != null){
				synchronized (this) {
					MQConnectionPoolService.inUsedMqConnCount++;
				}
			}
		}catch(Exception eqc){
			throw eqc;
		}
		mqConnection = new MQConnectionService(qmgrc, resource);
		mqConnection.establishConnection();
		mqConnection.setTimeout(resource.timeOut);
		return mqConnection;
	}
	catch(com.ibm.mq.MQException mqe)
	{
		if( mqe.reasonCode == 2009 )  //qmanager was broken
		{
			qdf.removeQManagerConnection(mqConnection.getQManagerConnection());
			throw new TranFailException("xdtz22115", "ECCMQConnectionPool.getMQConnection(String)", mqe.getMessage());			
			
		}
		throw new TranFailException("xdtz22115", "ECCMQConnectionPool.getMQConnection(String)", mqe.getMessage());
		
	}
	catch(TranFailException ee)
	{
		throw ee;
	}
	catch (Exception e)
	{
		throw new TranFailException("xdtz22115", "ECCMQConnectionPool.getMQConnection(String)", e.getMessage());
	}

}
public void releaseMQConnection(MQConnectionService connection) {

	try
	{
		if(connection != null){
			QManagerConnection mqConnection =connection.getQManagerConnection();
	  
			connection.closeConnection();
			connection = null;
	   	 	if(mqConnection != null){
				mqConnection.setIsInUse(false);
				synchronized (this) {
					MQConnectionPoolService.inUsedMqConnCount--;
				}
	   	 	}else{
		   	 	System.out.println("Warnning: can't free the MQConnection!connection.getQManagerConnection() == null");
	   	 	}
		}else{
			System.out.println("Warnning: can't free the MQConnection!connection == null");
		}

	}
	catch (Exception e)
	{
		CmisConstance.pringMsg("Warnning: can't free the MQConnection!\nException: " + e.getMessage());
	}
}
	//////////////////////////////////////////////////////////////
	//
	//	修改原因：为了在MQ监听程序，获取所有MQ资源列表
	//	修改人：YangGuangRun
	//	修改时间：2004-10-22
	//////////////////////////////////////////////////////////////
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @return
	 *  
	 */
	public Hashtable getMqResources() {
		return mqResources;
	}
	//////////////////////////////////////////////////////////////
}
