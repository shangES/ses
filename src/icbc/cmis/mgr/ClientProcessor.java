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
public interface ClientProcessor {

/**
 * This method was created in VisualAge.
 */
void closeSocket(java.net.Socket socket );
void newPackageReceived(String pkg, java.io.DataOutputStream outputStream);
void showMessage(String msg);
}
