package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.CmisConstance;
import java.sql.*;
import java.util.Vector;
import java.util.Hashtable;
import icbc.missign.Employee;
import icbc.cmis.util.QueryNormalEnp;

/**
 * Title: 选择银行数据库操作.<br>
 * Description: Insert type's descript.<br>
 * Copyright: Copyright (c) 2003<br>
 * Creation date: (2003-11-10 9:16:18)<br>
 * @company: 中国工商银行杭州软件研发部<br>
 * @author: Yongcheng Shang<br>
 * @version: 1.0<br>
 */
public class ChooseBankDAO extends CmisDao {
	//final static int QUERY_LIMIT = 200;

	public ChooseBankDAO(icbc.cmis.operation.CmisOperation op) {
		super(op);
	}
public String[] genSQL(
    String queryType,
    Employee employee,
    String bankcode,
    String shortname,
    Hashtable paras)
    throws TranFailException {
    String sql[] = { "", "" };
    String where = "";

    if (bankcode.length() > 0) {
        where += (" and bankcode like '%" + bankcode + "%'");
    }

    if (shortname.length() > 0) {
        where += (" and shortname like '%" + shortname + "%'");
    }

    //根据情况加where条件

    if (where.startsWith(" and")) {
        where = where.substring(4);
    }

    sql[0] = "select count(*) from bank where " + where;
    //    sql[1] = "select * from (select TA200011001, TA200011003, TA200011005, TA200011037,RANK() OVER (ORDER BY ta200011001) rnk FROM ta200011 "
    //           + " where " + where2 + ") WHERE rnk >= ? AND rnk <= ? ";
    //updated by ChenJ 20030526 for prepareStmt
    //		sql[1] = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ("
    //					 + "select TA200011001, TA200011003, TA200011005, TA200011037 FROM ta200011 "
    //					 + " where " + where2 + ") a  WHERE ROWNUM <= ?) WHERE rnum >= ?";
    sql[1] =
        "SELECT bankcode, bankvest, shortname, fullname, province, rnum, "
            + "(case when bankvest is not null then (select dict_name from dfinancialorg where dict_code = bankvest) else '-' end) bankvestname, "
            + "(case when province is not null then (select paravalue from genpara where paratype = '01' and paracode = province and app='bms') else '-' end) provincename, "
            + "isicbc"
            + " FROM ( SELECT a.*, ROWNUM rnum FROM ("
            + "select bankcode, bankvest, shortname, fullname, province, isicbc FROM bank "
            + " where "
            + where
            + ") a  WHERE ";
    //sql = "select bankcode,bankvest,(case when bankvest is not null then (select paravalue from genpara where paratype = '12' and paracode = bankvest) else 'void' end) bankvest2,shortname,fullname,(case when province is not null then (select paravalue from genpara where paratype = '01' and paracode = province) else 'void' end) province2 from bank" + " where rownum <= " + QUERY_LIMIT + " and " + sql_inq_bank;            
    //ROWNUM <= ?) WHERE rnum >= ?";

    //		System.out.println(sql[0]);
    //		System.out.println(sql[1]);
    return sql;
}
public int getRecordCount(String sql) throws TranFailException {
    Statement stmt = null;
    ResultSet rs = null;
    int ret = 0;
    try {
        this.getConnection("missign");
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
        this.getConnection("missign");

        //pstmt = conn.prepareStatement(sql);
        stmt = conn.createStatement();
        //pstmt.setInt(2,begin);
        //pstmt.setInt(1,end);
        rs = stmt.executeQuery(sql);
        int i = 0;
        while (rs.next()) {
            /** bankcode, 
                bankvest, 
                shortname, 
                fullname,
                province,
                rnum,
                bankvestname, 
                provincename, 
                isicbc 
             */
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
