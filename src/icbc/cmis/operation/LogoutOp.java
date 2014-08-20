package icbc.cmis.operation;

/**
 * 此处插入类型说明。
 * 创建日期：(2002-1-2 13:41:00)
 * @author：Administrator
 */
public class LogoutOp extends CmisOperation {
/**
 * LogoutOp 构造子注解。
 */
public LogoutOp() {
	super();
}
/**
 * 此处插入方法说明。
 * 创建日期：(2002-1-2 13:41:24)
 */
public void execute() throws icbc.cmis.base.TranFailException{
	try {
		clearSession();
		setReplyPage("/icbc/cmis/LogoutSuccess.jsp");
	} catch (Exception e) {
		System.out.println("ERR-LogoutOp.execut():\n"+e.toString());
		System.out.println("ERR-LogoutOp.execut()-printTrace:");
		e.printStackTrace();
		setReplyPage("/icbc/cmis/LogoutSuccess.jsp");
	}
}
}