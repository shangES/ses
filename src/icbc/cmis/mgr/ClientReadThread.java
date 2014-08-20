package icbc.cmis.mgr;

/**
 * This type was created in VisualAge.
 */
public class ClientReadThread implements Runnable {
	private java.net.Socket socket = null;
	private ClientProcessor clientProcessor = null;
/*	private java.io.InputStreamReader inputStreamReader = null;
	private java.io.OutputStreamWriter outputStreamWriter = null;
	private java.io.BufferedReader bufferedReader = null;
	*/
	private java.io.DataInputStream inStream = null;
	private java.io.DataOutputStream outStream = null;

	public Thread thread = null;

	private boolean isStop = false;
/**
 * ReadThread constructor comment.
 */
public ClientReadThread() {
	super();


}
/**
 * ReadThread constructor comment.
 */
public ClientReadThread(String hostName, int port, ClientProcessor clientProcessor) throws java.io.IOException
{
	super();
	try
	{

		
		socket = new java.net.Socket(hostName, port);
		this.clientProcessor = clientProcessor;
		inStream = new java.io.DataInputStream(socket.getInputStream());
		outStream = new java.io.DataOutputStream(socket.getOutputStream());

		thread = new Thread( this );
		thread.setName("Client read thread");

		thread.setDaemon(true);
		thread.start();
		
	}
	catch (java.io.IOException e)
	{
//		System.out.println(e.toString());
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
		thread.interrupt();
	}catch(Exception e)
	{
		System.out.println("Exception from ClientReadThread.close(): " + e);
	}
}
/**
 * run method comment.
 */
public void run() {

	try{
		clientProcessor.showMessage("Read Thread: Start!");

		while( !isStop )
		{
			
			int packageLen = inStream.readInt();
	
			if( isStop ) //we have been interupted
			{
				inStream.close();
				outStream.close();
				this.socket.close();

				System.out.println("monitor Client service stoped.");
				return;
			}
			if( packageLen > 12048 || packageLen < 0 ) //it's a invalid status
			{
				clientProcessor.closeSocket( socket);
				clientProcessor.showMessage("Thread Closed!");
				return;
			}
			
			byte[] buffer = new byte[packageLen];

			int offset = 0;
			while(offset < packageLen )
			{
				offset = offset + inStream.read(buffer, offset, packageLen - offset );
				if( isStop ) //we have been interupted
				{

					inStream.close();
					outStream.close();
					this.socket.close();
					
				System.out.println("monitor Client service stoped.");
				return;
				}
			}
			//String msg = bufferedReader.readLine();

			String msg = new String( buffer );

			if( msg == null)
			{
				clientProcessor.closeSocket( socket);
				clientProcessor.showMessage("Thread Closed!");
				return;
				
			}

			clientProcessor.newPackageReceived(msg, outStream );
		}

					System.out.println("monitor Client service stoped.");	
	}catch(java.io.IOException e)
	{
//		System.out.println(e.toString());
		clientProcessor.closeSocket( socket);
		clientProcessor.showMessage("Thread Closed!");
		return;
	}	
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param data java.lang.String
 */
public void sentdata(String data ) throws java.io.IOException{

	//first write the length of this package
	byte[] buf = data.getBytes("GB2312");
		
	outStream.writeInt(buf.length);
	outStream.write(buf, 0, buf.length);
	outStream.flush();
		
	
}
}
