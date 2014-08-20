package icbc.cmis.base;

import javax.swing.*;
import javax.swing.event.*;
/**
 * 
 *   @(#) Console.java	1.0 05/13/2000
 *   Copyright (c) 1999 EChannel R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/13/2000
 *   @author  ZhongMingChang
 *   
 */
public class Console {
	private static AwtConsoleFrame awtConsoleFrame = null;
	private static boolean awtOrSwing = true;		//default to awt

	public static int lineCount = 0;
	static int showLines = 200;

	public static boolean traceToFile = false;
	public static String fileName = "";
/**
 * Console constructor comment.
 */
public Console() {
	super();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return com.ecc.echannels.desktop.editor.ConsoleFrame
 */
protected static AwtConsoleFrame getAwtConsoleFrame() {
	if(!Trace.enableTraces){
		awtConsoleFrame = null;
		return null;
	}
	if( awtConsoleFrame == null )
	{
		awtConsoleFrame = new AwtConsoleFrame();
//		awtConsoleFrame.show();
	}
	return awtConsoleFrame;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-17 10:16:44)
 */
public static void reset()
{
	lineCount = 0;
	showLines = 200;

	traceToFile = false;

	getAwtConsoleFrame().reset();
	getAwtConsoleFrame().dispose();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-24 12:47:21)
 * @param title java.lang.String
 */
static void setTitle(String title)
{
	getAwtConsoleFrame().setTitle(title);
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param msg java.lang.String
 */
public static void showMessage(String msg)//"appserver\t"+null+"\t"+(""+"\tTID=???\t"+“[时分秒]”+"CmisReqServlet.doPost() begin")
{
	try
	{

		if (lineCount > showLines)
		{
			lineCount = 1;
			getAwtConsoleFrame().reset();

			getAwtConsoleFrame().showMessage(msg);
		}
		else
		{
			getAwtConsoleFrame().showMessage(msg);
			lineCount++;
		}
		//System.out.println( msg );
	}
	catch (Exception e)
	{
		System.out.println("Exception from Console.showMessage( String ): " + e);
	}
}
}
