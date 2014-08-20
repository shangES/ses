package icbc.cmis.util;

/**
 * 此处插入类型说明。
 * 创建日期：(2002-6-24 19:45:34)
 * @author：Administrator
 * errorCode xdtzUTIL300-xdtzUTIL303
 */
import icbc.cmis.base.*;
import java.sql.*;
import icbc.cmis.operation.*;
import icbc.missign.*;
import java.util.*;
public class PasswordChangeOp extends CmisOperation
{
    private SqlTool tool= null;
    private String webBasePath = (String)CmisConstance.getParameterSettings().get("webBasePath");
/**
 * PasswordChangeOp 构造子注解。
 */
public PasswordChangeOp()
{
	super();

}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-3 19:45:34)
 */
public void execute() throws Exception
{

    try
        {
        String old_passwd= "";
        try
            {
            old_passwd= getStringAt("oldpassword");
        }
        catch (Exception e)
            {
            old_passwd= "";
        }
        String new_passwd= getStringAt("newpasswd1");
        String modeFlag= getStringAt("modeFlag");
        String account= (String) getSessionData("EmployeeCode");
        String area_code= (String) getSessionData("AreaCode");
        int ret= updatePwd(old_passwd, new_passwd, account, area_code,modeFlag);
        //System.out.println(new Integer(ret).toString());
        //int ret= 1;
        if (ret == 1)
            { //success
            if (modeFlag.equals("1"))//密码过期
                {
                setReplyPage("/icbc/cmis/login.jsp");
            }
            else
                {
                setFieldValue("okTitle", "操作成功！");
                setFieldValue("okMsg", "密码修改成功！");
                setFieldValue("okReturn", webBasePath+"/main.jsp");
                setReplyPage("/icbc/cmis/ok.jsp");
            }
        }
        else if (ret == 0)
            { //failed
            setErrorCode("xdtzUTIL301");
            setErrorDispMsg("交易失败，修改密码错误！");
            setErrorLocation("PasswordChangeOp.updatePwd(String,String,Stringk,String)");
            setErrorMessage("交易失败，修改密码错误！");
            setReplyPage("/icbc/cmis/error.jsp");
        }
        else if (ret == 2)
            { //oldpwd is wrong
            setErrorCode("xdtzUTIL302");
            //setErrorDispMsg("111111");
            setErrorDispMsg("交易失败，原密码不正确！");
            setErrorLocation("PasswordChangeOp.updatePwd(String,String,Stringk,String)");
            setErrorMessage("交易失败，原密码不正确！");
            setReplyPage("/icbc/cmis/error.jsp");
        }
        setOperationDataToSession();
    }
    catch (TranFailException e)
        {
        setErrorCode(e.getErrorCode());
        setErrorDispMsg(e.getDisplayMessage());
        setErrorLocation(e.getErrorLocation());
        setErrorMessage(e.getErrorMsg());
        setReplyPage("/icbc/cmis/error.jsp");
        setOperationDataToSession();
    }
    catch (Exception e)
        {
        setErrorCode("xdtz22126");
        setErrorDispMsg("交易平台错误：交易处理失败");
        setErrorLocation("PasswordChangeOp.execute()");
        setErrorMessage(e.getMessage());
        setReplyPage("/icbc/cmis/error.jsp");
        setOperationDataToSession();
    }
    
   
}
public void initSqlTool() throws Exception
{
    try
        {
        tool= new SqlTool(this);

    }
    catch (Exception e)
        {
        throw new TranFailException(
            "xdtzUTIL303",
            "PasswordChangeOp.initSqlTool();",
            e.getMessage(),
            "密码修改错误");
    }
}
/**
 * Insert the method's description here.
 * Creation date: (2002-06-24 20:49:48)
 * @return java.lang.String
 * @param ori java.lang.String
 * 给输入的时间（yyyyMMdd）加90天，延长口令使用期
 */
private String timeAdder(String ori)
{
    String ret;

    Calendar cal= Calendar.getInstance();
    cal.set(
        Integer.parseInt(ori.substring(0, 4)),
        Integer.parseInt(ori.substring(4, 6)),
        Integer.parseInt(ori.substring(6)));
    cal.add(Calendar.DAY_OF_YEAR, 90);
    String year= String.valueOf(cal.get(Calendar.YEAR));
    String month= String.valueOf(cal.get(Calendar.MONTH));
    String day= String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    if (month.length() < 2)
        {
        month= "0" + month;
    }
    if (day.length() < 2)
        {
        day= "0" + day;
    }
    return year + month + day;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2002-1-11 14:42:46)
 * @return int(0,updatefail
			   1,success
			   2,old_passwd error)
 */
public int updatePwd(
    String old_passwd,
    String new_passwd,
    String account,
    String area_code,
    String mode_flag)
    throws TranFailException
{
    int ret= -1;
    try
        {
        initSqlTool();
        tool.getConn("missign");
        tool.setAutoCommit(false);

//			Aking commented at 03/06/03
//        String sql=
//            "select employee_passwd, employee_useful from mag_employee where employee_code = '"
//                + account
//                + "' and mdb_sid ='"
//                + area_code
//                + "'";
        String sql=
            "select employee_passwd, employee_useful from mag_employee where employee_code = ? and mdb_sid =?";
        Vector vValue = new Vector();
        vValue.add(account);
        vValue.add(area_code);
        ResultSet rs= tool.executeQuery(sql, vValue);
        if (rs.next())
            {
            String old_pass_md5= rs.getString("employee_passwd");
            //String pwTimeLim= rs.getString("employee_useful");
            if (true/*Encode.checkPassword(account, old_passwd, old_pass_md5)*/)
                {
                String new_pass_md5= Encode.getMd5(account, new_passwd);
                String sql1= "";
                vValue.clear();
                if (mode_flag.equals("1"))
                    { //密码过期,更改密码到期时间
//                    sql1=
//                        "update mag_employee set employee_passwd = '"
//                            + new_pass_md5
//                            + "',employee_useful='"
//                            + timeAdder(getCurrentDateTime("yyyyMMdd"))
//                            + "' where employee_code = '"
//                            + account
//                            + "' and mdb_sid ='"
//                            + area_code
//                            + "'";
                    sql1=
                        "update mag_employee set employee_passwd = ?,employee_useful=? where employee_code = ? and mdb_sid =?";
					vValue.add(new_pass_md5);
					vValue.add(timeAdder(getCurrentDateTime("yyyyMMdd")));
					vValue.add(account);
					vValue.add(area_code);
                }
                else
                    {
//                    sql1=
//                        "update mag_employee set employee_passwd = '"
//                            + new_pass_md5
//                            + "' where employee_code = '"
//                            + account
//                            + "' and mdb_sid ='"
//                            + area_code
//                            + "'";
                    sql1=
                        "update mag_employee set employee_passwd = ? where employee_code = ? and mdb_sid =?";
					vValue.add(new_pass_md5);
					vValue.add(account);
					vValue.add(area_code);
                }
                if (tool.executeUpdate(sql1, vValue) != 0)
                    {
                    Employee emp= (Employee) getSessionData("Employee");
                    emp.setEmployeePasswd(new_pass_md5);
                    updateSessionData("Employee", emp);
                    tool.commit();
                    ret= 1;
                }
                else
                    {
                    tool.rollback();
                    ret= 0;
                }
            }
            else
                {
                ret= 2;
            }
        }
        tool.closeconn();
        return ret;
    }
    catch (Exception e)
        {
        try
            {
            tool.rollback();
        }
        catch (Exception e1)
            {
        }
        try
            {
            tool.closeconn();
        }
        catch (Exception e2)
            {
            tool= null;
        }
        throw new TranFailException(
            "xdtzUTIL300",
            "PasswordChangeOp.updatePwd(String,String,String,String)",
            e.getMessage(),
            "修改密码错误！");
    }
}
}
