// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   QueryCustom.java

package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.Connection;
import java.util.Hashtable;

// Referenced classes of package icbc.cmis.util:
//            QueryNormalEnp

public class QueryCustom extends QueryNormalEnp
{

    public QueryCustom()
    {
    }

    public String getWhere(Connection conn, Employee employee, Hashtable paras)
    {
        String where = "";
        where = super.getWhere(conn, employee, paras);
        String querycusttype = (String)paras.get("querycusttype");
        if(querycusttype.equals("0"))
            where = String.valueOf(String.valueOf(where)).concat(" and ta200011001 in (select ta300003001 from ta300003 where ta300003016 <> '05' )");
        else
            where = String.valueOf(String.valueOf(where)).concat(" and ta200011001 in (select ta300003001 from ta300003 where ta300003016 = '05' )");
        return where;
    }
}
