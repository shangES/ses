package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.CmisConstance;
import icbc.cmis.bift.util.Util;

import java.sql.*;
import java.util.Vector;
import java.util.Hashtable;
import icbc.missign.Employee;
/**
 * Title: 选择银行数据库操作.<br>
 * Description: Insert type's descript.<br>
 * Copyright: Copyright (c) 2003<br>
 * Creation date: (2003-11-10 9:16:18)<br>
 * @company: 中国工商银行杭州软件研发部<br>
 * @author: Yongcheng Shang<br>
 * @version: 1.0<br>
 */
public class util_ChooseBankDAO extends CmisDao {
	//final static int QUERY_LIMIT = 200;

	public util_ChooseBankDAO(icbc.cmis.operation.CmisOperation op) {
		super(op);
	}
public String[] genSQL(
    String queryType,
    Employee employee,
    String ciscode,
    String shortname,
    Hashtable paras)
    throws TranFailException {
    String sql[] = { "", "" };
    String where = "";

    if (ciscode.length() > 0) {
        where += (" and ciscode like '%" + ciscode + "%'");
    }

    if (shortname.length() > 0) {
        where += (" and shortname like '%" + shortname + "%'");
    }

    //根据情况加where条件

    if (where.startsWith(" and")) {
        where = where.substring(4);
    }

    sql[0] = "select count(*) from bank,ta200011 where ta200011.ta200011001=bank.bankvest and " + where;
    sql[1] =
        "SELECT ciscode, bankvest, shortname, fullname, province,rnum, "
            + "bankvestname,"
            + "(case when province is not null then (select paravalue from genpara where app='bms' and paratype = '01' and paracode = province) else 'void' end) provincename,TA200011014 "
            + " FROM ( SELECT a.*, ROWNUM rnum FROM ("
            + "select ciscode, bankvest, shortname, fullname, province,ta200011.ta200011004 bankvestname,TA200011014 FROM bank,TA200011 "
            + " where ta200011.ta200011001=bank.bankvest and "
            + where
            + ") a  WHERE ";

    return sql;
}
public int getRecordCount(String sql) throws TranFailException {
    Statement stmt = null;
    ResultSet rs = null;
    int ret = 0;
    try {
        this.getConnection();
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        rs.next();
        ret = rs.getInt(1);
    } catch (TranFailException ex) {
        throw ex;
    } catch (Exception ex) {
        throw new TranFailException(
            "cmisutil214",
            this.getClass().getName() + ".getRecordCount(String)",
            ex.getMessage() + sql,
            "查询记录条数错误！");
    } finally {
        if (rs != null)
            try {
                rs.close();
            } catch (Exception ex) {
            };
        if (stmt != null)
            try {
                stmt.close();
            } catch (Exception ex) {
            };
        this.closeConnection();
    }
    return ret;
}
public Vector query(String sql, int begin, int end) throws TranFailException {
    Vector ret = new Vector();
    //PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
        this.getConnection();

        //pstmt = conn.prepareStatement(sql);
        stmt = conn.createStatement();
        //pstmt.setInt(2,begin);
        //pstmt.setInt(1,end);
        Util.debug(this.getClass().getName(),sql);
        rs = stmt.executeQuery(sql);
        int i = 0;
        while (rs.next()) {
            /** bankvest, ciscode, shortname, fullname, institution, bankvestname, provincename */
            String[] row =
                {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9)};

            ret.add(row);
            i++;
        }
    } catch (TranFailException ex) {
        throw ex;
    } catch (Exception ex) {
        throw new TranFailException(
            "cmisutil215",
            this.getClass().getName() + ".query(String, int, int)",
            ex.getMessage() + sql,
            "产生银行列表错误！");
    } finally {
        if (rs != null)
            try {
                rs.close();
            } catch (Exception ex) {
            };
        //			if(pstmt != null) try {pstmt.close();} catch (Exception ex) {};
        if (stmt != null)
            try {
                stmt.close();
            } catch (Exception ex) {
            };
        this.closeConnection();
    }
    return ret;
}
}
