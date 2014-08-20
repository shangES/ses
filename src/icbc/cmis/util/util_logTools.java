package icbc.cmis.util;

/*************************************************************
 * 
 * <b>创建日期: </b> 2004-7-16<br>
 * <b>标题: </b>记录和打印异常类<br>
 * <b>类描述: </b><br>
 * <br>分5个级别记录信息
 * <p>Copyright: Copyright (c)2004</p>
 * <p>Company: </p>
 * 
 * @author zhouxj
 * 
 * @version 1.03
 * 
 * @since 
 * 
 * @see 
 * 
 *************************************************************/

public class util_logTools {

	private static int level = 1; //输出信息记录级别,1-5分别为debug,info,warn,error,fatal
	private static boolean jspinfoFlag=true;	//是否向jsp页面输出
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param i
	 * @param msg
	 *  
	 */

	private static void msgout(int i, String msg) {
		
		if (i >= level) {
			System.out.println(msg);
		}
	}
	/**
	 * <b>功能描述: </b><br>
	 * <p> 根据级别输出异常类的信息</p>
	 * @param i
	 * @param msg
	 *  
	 */
	private static void msgout(int i, Exception e) {
		
		if (i >= level) {
			e.printStackTrace();
		}
	}
	/**
	 * <b>功能描述: </b><br>
	 * <p>输出调试信息，信息级别1 </p>
	 */
	public static void debug(Exception e) {
		msgout(1, e);
	}
	/**
	 * <b>功能描述: </b><br>
	 * <p> 输出一般信息，信息级别2</p>
	 *  
	 */
	public static void info(Exception e) {

		msgout(2, e);
	}
	/**
	 * <b>功能描述: </b><br>
	 * <p> 输出警告信息，信息级别3</p>
	 *  
	 */
	public static void warn(Exception e) {

		msgout(3, e);
	}
	/**
	 * <b>功能描述: </b><br>
	 * <p> 输出错误信息，信息级别4</p>
	 *  
	 */
	public static void error(Exception e) {

		msgout(4, e);
	}
	/**
	 * <b>功能描述: </b><br>
	 * <p> 输出严重错误信息，信息级别5</p>
	 *  
	 */
	public static void fatal(Exception e) {

		msgout(5, e);
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p>输出调试信息，信息级别1 </p>
	 */
	public static void debug(String msg) {
		msgout(1, msg);
	}
	/**
	 * <b>功能描述: </b><br>
	 * <p> 输出一般信息，信息级别2</p>
	 *  
	 */
	public static void info(String msg) {

		msgout(2, msg);
	}
	/**
	 * <b>功能描述: </b><br>
	 * <p> 输出警告信息，信息级别3</p>
	 *  
	 */
	public static void warn(String msg) {

		msgout(3, msg);
	}
	/**
	 * <b>功能描述: </b><br>
	 * <p> 输出错误信息，信息级别4</p>
	 *  
	 */
	public static void error(String msg) {

		msgout(4, msg);
	}
	/**
	 * <b>功能描述: </b><br>
	 * <p> 输出严重错误信息，信息级别5</p>
	 *  
	 */
	public static void fatal(String msg) {

		msgout(5, msg);
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p>取得记录信息级别 </p>
	 * @return
	 *  
	 */
	public static int getLevel() {
		return level;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> 设置记录信息的级别</p>
	 * @param i
	 *  
	 */
	private static void setLevel(int i) {
		level = i;		//注意不允许再程序中任意改变输出信息的级别，否则会影响其他程序的输出形式。
	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p>向JSP页面输出的信息 </p>
	 * @param str
	 * @return 返回信息
	 *  
	 */
	public static String jspinfo(String str) {

		if (jspinfoFlag == true) {
			return str;
		}
		return "";
	}
}
