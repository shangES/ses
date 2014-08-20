package icbc.cmis.operation;

/**
 * 此处插入类型说明。
 * 创建日期：(2002-1-2 13:41:00)
 * @author：Administrator
 */
public class ExampleLogoutOp extends CmisOperation {
/**
 * LogoutOp 构造子注解。
 */
public ExampleLogoutOp() {
	super();
}
/**
 * 此处插入方法说明。
 * 创建日期：(2002-1-2 13:41:24)
 */
public void execute() throws icbc.cmis.base.TranFailException{
	try {
		try{
			this.setFieldValue("EmployeeCode",getSessionData("EmployeeCode"));
			this.setFieldValue("EmployeeName",getSessionData("EmployeeName"));
			this.setFieldValue("AreaCode",getSessionData("AreaCode"));
		}catch(Exception es){}
		clearSession();
		setReplyPage("/icbc/cmis/examplelogout.jsp");
	} catch (Exception e) {
		setReplyPage("/icbc/cmis/examplelogout.jsp");
	}
}
}
