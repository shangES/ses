// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   util_EnpQueryBack.java

package icbc.cmis.util;

import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.CmisOperation;

public class util_EnpQueryBack extends CmisOperation
{

    public util_EnpQueryBack()
    {
    }

    public void execute()
        throws TranFailException
    {
        try
        {
            String enp_code = getStringAt("EnpCode");
            String enp_name = getStringAt("EnpName");
            String return_page = (String)getSessionData("CmisEnpReturnPage");
            String operationName = (String)getSessionData("operationName");
            addSessionData("CmisEnpCode", enp_code);
            addSessionData("CmisEnpName", enp_name);
            setReplyPage(return_page);
        }
        catch(TranFailException e)
        {
            setErrorCode(e.getErrorCode());
            setErrorCode(e.getErrorCode());
            setErrorDispMsg(e.getDisplayMessage());
            setErrorLocation(e.getErrorLocation());
            setErrorMessage(e.getErrorMsg());
            setReplyPage("/icbc/cmis/error.jsp");
        }
        catch(Exception e)
        {
            setErrorCode("xdtz22126");
            setErrorDispMsg("交易平台错误：客户登录失败");
            setErrorLocation("FACustomerQueryOp.execute()");
            setReplyPage("/icbc/cmis/error.jsp");
            setErrorMessage(e.getMessage());
            setReplyPage("/icbc/cmis/error.jsp");
        }
    }
}
