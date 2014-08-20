package icbc.cmis.mgr;

/**
 * This type was created in VisualAge.
 */
public class ServerReadThread implements Runnable {
	private java.net.Socket socket = null;
	private ServerProcessor serverProcessor = null;

	private java.io.DataInputStream inStream = null;
	private java.io.DataOutputStream outStream = null;
	static int instanceCount = 0;
	public int sessionID;

	private String clientHostName = "";

	public boolean isStop = false;

	public Thread runningThread = null;
/**
 * ReadThread constructor comment.
 */
public ServerReadThread() {
	super();

	sessionID = instanceCount++;

}
/**
 * ReadThread constructor comment.
 */
public ServerReadThread(java.net.Socket socket, ServerProcessor serverProcessor) {
	super();

	
	this.socket = socket;
	this.serverProcessor = serverProcessor;

	this.clientHostName = socket.getInetAddress().getHostAddress();

//	this.clientHostName = socket.getInetAddress().getHostName();

	this.serverProcessor.showMessage("Connection from: " + clientHostName );

	try{
		
		inStream = new java.io.DataInputStream( socket.getInputStream());
		outStream = new java.io.DataOutputStream( socket.getOutputStream());
	}catch(java.io.IOException e)
	{
		System.out.println(e.toString());
		serverProcessor.showMessage(e.toString());
	}
	
	sessionID = instanceCount++;

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
		runningThread.interrupt();
		inStream.close();
		outStream.close();
		socket.close();
	}catch(Exception e)
	{

	}
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return 
 */
public String getClientHostName() {
	return clientHostName;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.io.DataOutputStream
 */
public java.io.DataOutputStream getOutputStream() {
	return outStream;
}
/**
 * run method comment.
 */
public void run() {

	try {

		while (!isStop) {

			int packageLen = inStream.readInt();

			if (isStop) {
				return;
			}

		//	if (packageLen > 4096 || packageLen < 0) //innormal data package!!!
	/*		if (packageLen > 10240 || packageLen < 0) //innormal data package!!!
				{
				while (true) {

					try {
						inStream.readByte();
					}
					catch (java.io.IOException ee) {
						break;
					}
				}

				continue;
			}
*/
			byte[] buffer = new byte[packageLen];

			int offset = 0;
			while (offset < packageLen) {
				offset = offset + inStream.read(buffer, offset, packageLen - offset);
				if (isStop) {
					return;
				}
			}
			//String msg = bufferedReader.readLine();

			String msg = new String(buffer);

			if (msg == null) {
				serverProcessor.closeSocket(sessionID, socket);
				serverProcessor.showMessage("Connection from [ " + this.clientHostName + "] Closed!");
				return;

			}

			serverProcessor.newPackageReceived(sessionID, msg, outStream);
		}
	}
	catch (java.io.IOException e) {
		//System.out.println(e.toString());

		if (isStop)
			return;	
	
			serverProcessor.closeSocket(sessionID, socket);
			serverProcessor.showMessage("Connection from [ " + this.clientHostName + "] Closed!");
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
