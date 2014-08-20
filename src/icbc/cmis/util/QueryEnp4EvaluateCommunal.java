// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   QueryEnp4EvaluateCommunal.java

package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.Connection;
import java.util.Hashtable;

// Referenced classes of package icbc.cmis.util:
//            QueryNormalEnp

public class QueryEnp4EvaluateCommunal extends QueryNormalEnp
{

    public QueryEnp4EvaluateCommunal()
    {
    }

    public String getWhere(Connection conn, Employee employee, Hashtable paras)
    {
        String where = "";
        String tt = null;
        String areaCode = employee.getMdbSID();
        String employeeCode = employee.getEmployeeCode();
        String employeeClass = employee.getEmployeeClass();
        switch(Integer.parseInt(employeeClass))
        {
        case 6: // '\006'
            where = String.valueOf(String.valueOf((new StringBuffer("ta200011014 in ('L8510','L8520','L8530','L8540','L8550','L8560','L8590','M8910','M8920','M8930','M8940','M8950','M8990') and ta200011063='")).append(areaCode).append("'  and ta200011059='1' and ta200011001 in ( select ma300004001 from ma300004 where ma300004002='").append(employeeCode).append("' )")));
            break;

        case 7: // '\007'
        case 8: // '\b'
            where = String.valueOf(String.valueOf((new StringBuffer("ta200011014 in ('L8510','L8520','L8530','L8540','L8550','L8560','L8590','M8910','M8920','M8930','M8940','M8950','M8990') and ta200011001 in (select ta200012001 from ta200012  where ta200012005='1' and ( ta200012006='")).append(employeeCode).append("' or ( ta200012003='").append(employeeCode).append("' and ta200012006 is null )))").append(" and ta200011059='1'")));
            break;
        }
        return where;
    }
}
