// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   QueryManageEnp.java

package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.Connection;
import java.util.Hashtable;

// Referenced classes of package icbc.cmis.util:
//            QueryNormalEnp

public class QueryManageEnp extends QueryNormalEnp
{

    public QueryManageEnp()
    {
    }

    public String getWhere(Connection conn, Employee employee, Hashtable paras)
    {
        String area = (String)paras.get("area");
        if(area == null)
            area = employee.getMdbSID();
        String where = String.valueOf(String.valueOf((new StringBuffer(" ta200011001 in (select ta20001L001 from ta20001L where ta20001L003='1' and ta20001L002 = '")).append(area).append("')")));
        return where;
    }
}
