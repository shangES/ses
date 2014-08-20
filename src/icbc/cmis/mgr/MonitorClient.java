package icbc.cmis.mgr;

/**
 * Insert the type's description here.
 * Creation date: (2001-4-1 23:58:38)
 * @author: Administrator
 */
public class MonitorClient implements ClientProcessor, Runnable,MonitorClientInterface {
	String server = "127.0.0.1";
	int port = 1800;
	private static ClientReadThread clientThread = null;
	private static MonitorClient monitorClient = null;

	boolean isStop = false;
	private static Thread conTryThread = null;

	private int retryInterval = 100000;
	private String mgrName = null;
	private String mgrPass = null;
	
/**
 * Insert the method's description here.
 * Creation date: (2002-4-22 16:16:28)
 * @param serverName java.lang.String
 * @param port int
 * @param retryInterval int
 * @param mgrName java.lang.String
 * @param mgrPass java.lang.String
 */
public MonitorClient(String serverName, int port1, int retryInterval1, String mgrName1, String mgrPass1) {
	try{
		server = serverName;
		port = port1;
		retryInterval = retryInterval1;
		mgrName = mgrName1;
		mgrPass = mgrPass1;
	    if( this.clientThread != null )
			clientThread.close();

	    if( monitorClient != null )
			monitorClient.isStop = true;
		
		if( conTryThread != null)
		{
			conTryThread.interrupt();
		}

		icbc.cmis.base.CmisConstance.monitor = null;
		monitorClient = null;
		icbc.cmis.base.CmisConstance.monitor = this;
		monitorClient = this;


		System.out.println("CMIS Monitor client started......");
		//try to connection to server
		conTryThread = new Thread( this );
	
		conTryThread.setName("CMIS Monitor Client connection try thread");
		conTryThread.start();
	
		//	conTryThread.setDaemon( true );
	}catch(Exception e){
		System.out.println("Start MonitorClient failure,Exception:\n"+e.getMessage());
	}
}
public void closeSocket(java.net.Socket socket)
{
	//the monitor server was off, so we have to try to connection to it

	try
	{
		if (this.clientThread != null)
			clientThread.close();
	}
	catch (Exception e)
	{
	}
//	com.ecc.echannels.base.ICBCINBSConstants.monitor = this;
//	monitorClient = this;

	System.out.println("CMIS Monitor client connection closed!");

	if( !isStop)
	{
		//try to connection to monitor server
		Thread aThread = new Thread(this);
		conTryThread = aThread;
		aThread.start();
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-20 13:30:15)
 * @return java.lang.String
 */
public String getMgrName() {
	return mgrName;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-20 13:30:37)
 * @return java.lang.String
 */
public String getMgrPass() {
	return mgrPass;
}
public void newPackageReceived(String pkg, java.io.DataOutputStream outputStream) {

	MoniterPackageProcessor processor = new MoniterPackageProcessor(this, pkg );
	Thread thread = new Thread(processor);
	thread.start();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-22 11:48:14)
 * @param t java.lang.Thread
 */
public void reStartThread(Thread t) {
	if(conTryThread != null){
		try{
			conTryThread.destroy();
			conTryThread = null;
		}catch(Exception e){
			conTryThread = null;
		}
	}
	conTryThread = t;
	conTryThread.start();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-2 0:08:19)
 */
public void run()
{
	while (!isStop)
	{
		try
		{
			clientThread = new ClientReadThread(server, port, this);

			clientThread.thread.setPriority(Thread.MAX_PRIORITY);
			
			conTryThread = null;
			System.out.println("CMIS Monitor client connect to server " + server + ":" + port );			
			break;
		}
		catch (Exception e)
		{
			try
			{
				conTryThread.sleep(this.retryInterval);  //try to connect to monitor server every 1 sec.
			}
			catch (Exception ee)
			{
			}
		}
	}

}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-2 0:27:13)
 * @param msg java.lang.String
 */
public synchronized void sendMessageToServer(String msg)
{
	try
	{
	    if( clientThread != null)
	    	clientThread.sentdata(msg);
	}
	catch (Exception e)
	{
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-2 0:24:48)
 * @param sessionId java.lang.String
 */
public void sessionRemoved(String sessionId) {
	
		icbc.cmis.base.CmisConstance.logonNumsReduce();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-2 0:02:16)
 * @param msg java.lang.String
 */
public void showMessage(String msg) {}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-2 0:22:23)
 * @param sessionId java.lang.String
 */
public void userLogoff(String sessionId)
{
	try
	{
		if (this.clientThread != null)
			clientThread.sentdata("1000" + sessionId);
	}
	catch (Exception e)
	{
	}
}
}
