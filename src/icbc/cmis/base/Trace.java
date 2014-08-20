package icbc.cmis.base;

/**
 * 
 *   @(#) Trace.java	1.0  
 *   Copyright (c) 2002 ECC All Rights Reserved.
 *  
 *  
 *   @version 1.0 (2002-1-14 14:17:10)
 *   @author  ZhongMingChang
 * 
 */
public class Trace {

	public static final int Display = 1;
	public static final int Information = 2;
	public static final int Warning = 4;
	public static final int Error = 8;
	public static final int Severe = 16;
	public static final int Debug = 32;
	public static final int VTF = 64;
	public static final int All = 127;

	
	public static final int High = 256;
	public static final int Medium = 512;
	public static final int Low = 1024;
	
	public static final int AllLevels = 1792;

	protected static boolean useLog = false;

	public static boolean traceToFile = false;
	public static boolean enableTraces = true;
	public static boolean showStack;
	private static Trace traceInstance = null;
	private static String traceMethodPath = null;

	
/**
 * Trace constructor comment.
 */
public Trace() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-14 14:17:36)
 * @return boolean
 */
public static boolean doTrace(String source, int type, int level) {
	return true;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-18 13:56:16)
 * @return java.lang.String
 */
public static String getCurrentTimeString() {

	java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("MM/dd HH:mm:ss:SSS");
	java.util.Date date = java.util.Calendar.getInstance().getTime();
	return "[" + fmt.format(date) + "] ";
	
	//return "[" + CmisConstance.getWorkDate("MM/dd HH:mm:ss:SSS") + "] ";
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-17 10:13:19)
 */
public static void reset()
{
	try{
		String et = (String)icbc.cmis.base.CmisConstance.getParameterSettings().get("enableTracer");
		if(et!= null && et.equals("true")){
			enableTraces = true;
		}else{
			enableTraces = false;
			return;
		}
		Console.reset();
		String traceToDisplay  = (String)icbc.cmis.base.CmisConstance.getParameterSettings().get("traceToDisplay");
		if( traceToDisplay.equals("yes"))
		{
			Console.getAwtConsoleFrame().show();
			Console.getAwtConsoleFrame().setVisible(true);
	
		}
		String disLines = (String)icbc.cmis.base.CmisConstance.getParameterSettings().get("displayNumberOfLines");
		Console.showLines = Integer.parseInt(disLines);
		
	}catch(Exception e)
	{
		System.out.println("***exception:"+e.toString());
		e.printStackTrace();
	}
	
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-24 12:46:43)
 * @param title java.lang.String
 */
public static void setConsoleTitle(String title)
{
	Console.setTitle(title);
}
public static final void trace(String source, int type, int level, java.lang.String tid, java.lang.String msg)//"",0,0,"","CmisReqServlet.doPost() begin"
{
	if(source == null) source = "";
	if(tid == null) tid = "";
	if(msg == null) msg = "";
	if( !enableTraces )
		return;
	if( ! doTrace(source, type, level ) )//true不需要写吧?
		return;
	String tmp = "";
	if( tid == null)
		tmp = source + "\tTID=???\t" + getCurrentTimeString() + msg;//""+"\tTID=???\t"+“[时分秒]”+"CmisReqServlet.doPost() begin"
	else
		tmp = source + "\tTID=" + tid + "\t" + getCurrentTimeString() + msg;
	Console.showMessage("appServer\t"+CmisConstance.appServerID+"\t"+tmp);//"appserver\t"+null+"\t"+(""+"\tTID=???\t"+“[时分秒]”+"CmisReqServlet.doPost() begin")
}
}
