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
public class IDAndCryptoConfig extends CmisOperation
{
    private SqlTool tool= null;
/**
 * PasswordChangeOp 构造子注解。
 */
public IDAndCryptoConfig()
{
    super();

}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-3 19:45:34)
 */
public void execute() throws Exception
{
    try{
        
        String id= getStringAt("id_no");
        String idType= getStringAt("id_type");
        String account= (String) getSessionData("EmployeeCode");
        String crypto = (String) this.getSessionData("pwdCrypto");
        //String area_code= (String) getSessionData("AreaCode");
        IDAndCryptoConfigDAO dao = new IDAndCryptoConfigDAO(this);
        dao.updateIDAndCrypto(account,id,idType,crypto);

//        this.setFieldValue("okTitle","操作成功！");
//        this.setFieldValue("okMsg","设置证件类型和证件号码成功！");
//        this.setFieldValue("okReturn",(String)CmisConstance.getParameterSettings().get("webBasePath") + "/login.jsp");
        this.setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GB2312\"?><success />");
    }
    catch (TranFailException e)
        {
//        setErrorCode(e.getErrorCode());
//        setErrorDispMsg(e.getDisplayMessage());
//        setErrorLocation(e.getErrorLocation());
//        setErrorMessage(e.getErrorMsg());
        String msg = "DirectOutput<?xml version=\"1.0\" encoding=\"GB2312\"?><error>";
        msg += "出错位置： " + e.getErrorLocation() + "\n出错内容：" + e.getErrorMsg();
        msg += "</error>";
        setReplyPage(msg);
        setOperationDataToSession();
    }
    catch (Exception e)
        {
//        setErrorCode("xdtz22126");
//        setErrorDispMsg("交易平台错误：交易处理失败");
//        setErrorLocation("IDAndTypeConfig.execute()");
//        setErrorMessage(e.getMessage());
        String msg = "DirectOutput<?xml version=\"1.0\" encoding=\"GB2312\"?><error>";
        msg += "IDAndTypeConfig.execute()" + "  " + e.getMessage();
        msg += "</error>";
        setReplyPage(msg);
        setOperationDataToSession();
    }
}

}
