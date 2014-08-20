package icbc.cmis.mgr;

/**
 * This type was created in VisualAge.
 */
public class ServerListenThread implements Runnable {

	private java.net.ServerSocket srvSocket = null;
	private ServerListener serverListener = null;
	private int port = 1888;

	private Thread thread = null;

	private boolean isStop = false;
/**
 * ListenThread constructor comment.
 */
public ServerListenThread() {
	super();
}
/**
 * ListenThread constructor comment.
 */
public ServerListenThread(ServerListener serverListener, int port)throws java.io.IOException {
	super();
	this.serverListener = serverListener;
	this.port = port;
	try{
		srvSocket = new java.net.ServerSocket(port);
		thread = new Thread( this );
		thread.setName("Server listen thread");
		thread.start();
		
	}catch( java.io.IOException e)
	{
		
		System.out.println(e.toString());
		serverListener.showMessage( e.toString());
		throw e;
	}

}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 */
public void close()
{
	try{
		isStop = true;
		this.thread.interrupt();
//		thread.stop();
		this.srvSocket.close();
	}catch(Exception e)
	{
		System.out.println("Exception from ServerListenThread.close(): " + e);
	}
}
/**
 * run method comment.
 */
public void run()
{
	try
	{
		serverListener.showMessage("Listen Thread: Listen on port [ " + this.port + " ]...");
		while (!isStop)
		{
			java.net.Socket socket = srvSocket.accept();
			if (serverListener != null && socket != null)
			{
				//inform the serverProcessor that a new Client was connect to the server
				serverListener.newSocketAccepted(socket);
				//create the read thread to process the message from client
			}
		}
	}
	catch (java.io.IOException e)
	{
		try
		{
			System.out.println( "Listen thread closed!\n Exception: " + e);
			srvSocket.close();
		}
		catch (Exception e1)
		{
		}
		System.out.println(e.toString());
	}
	try
	{
		srvSocket.close();
		System.out.println( "Listen thread closed!");
	}
	catch (Exception e)
	{
	}
}
}
