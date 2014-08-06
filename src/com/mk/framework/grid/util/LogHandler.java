package com.mk.framework.grid.util;


public class LogHandler {
	
	public static boolean ignore = true;
	
	public static void log(String type , Object obj,String msg,Throwable t){
		if (LogHandler.ignore) return;
		type=type.toUpperCase();
		System.out.print("*** "+type+" *** : ");
		if (obj!=null){
			System.out.println();
			System.out.print("*   Class : "+obj.getClass().getName()+"   Object : "+obj);
		}
		if (StringUtils.isNotEmpty(msg)){
			if (obj!=null){
				System.out.println();
				System.out.print("*   ");
			}
			System.out.print(msg);
		}
		if (t!=null){
			if (obj!=null || StringUtils.isNotEmpty(msg)){
				System.out.println();
				System.out.print("*   ");
			}
			System.out.println("*   StackTrace :");
			t.printStackTrace(System.out);
		}else{
			System.out.println();
		}
	}
	
	public static void error(Object obj,Throwable t){
		log("ERROR",obj,null,t);
	}
	
	public static void error(Throwable t){
		log("ERROR",null,null,t);
	}
	public static void error(String msg){
		log("ERROR",null,msg,null);
	}
	
	public static void debug(String msg){
		log("DEBUG",null,msg,null);
	}
	
	public static void print(String msg){
		System.out.println(msg);
	}
	
	public static void info(String msg){
		log("INFO",null,msg,null);
	}	
	
	public static void warn(Object obj,Throwable t){
		log("WARN",obj,null,t);
	}
	
	public static void warn(Throwable t){
		log("WARN",null,null,t);
	}
	public static void warn(String msg){
		log("WARN",null,msg,null);
	}
		
}
