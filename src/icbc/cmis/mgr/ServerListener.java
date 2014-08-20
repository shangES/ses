package icbc.cmis.mgr;

/**
 * 
 *   @(#) *.java	1.0 05/13/2000
 *   Copyright (c) 1999 Nantian R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/13/2000
 *   @author  ZhongMingChang
 *   
 */
public interface ServerListener
{
void newSocketAccepted(java.net.Socket socket);
	void showMessage(String msg);
}
