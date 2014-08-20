package icbc.cmis.util;

import java.sql.CallableStatement;
import java.sql.SQLException;

import oracle.jdbc.internal.OracleTypes;

import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.CmisOperation;
import icbc.cmis.service.CmisDao;

/*************************************************************
 * 
 * <b>创建日期: </b> 2005-11-29<br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * <br>
 * <p>Copyright: Copyright (c)2005</p>
 * <p>Company: </p>
 * 
 * @author Administrator
 * 
 * @version 
 * 
 * @since 
 * 
 * @see 
 * 
 *************************************************************/

public class Util_ControlDao extends CmisDao {

	/**
	 * <b>构造函数</b><br>
	 * @param op
	 */
	public Util_ControlDao(CmisOperation op) {
		super(op);
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param customercode
	 * @param areacode
	 * @param tradecode
	 * @param formercode
	 * @param opflag
	 * @param yewukind
	 * @param flag
	 * @return
	 *  
	 */
	public String[] check(
		String customercode,
		String areacode,
		String tradecode,
		String formercode,
		String opflag,
		String yewukind)
		throws TranFailException, SQLException {
		
        String[] str=new String[2];
		try {
			this.getConnection();
			CallableStatement cStmt =
				conn.prepareCall(
					"call pack_public_use.proc_control(?,?,?,?,?,?,?,?)");
			cStmt.setString(1, customercode);
			cStmt.setString(2, areacode);
			cStmt.setString(3, tradecode);
			cStmt.setString(4, formercode);
			cStmt.setString(5, opflag);
			cStmt.setString(6, yewukind);			
			cStmt.registerOutParameter(7, OracleTypes.NUMBER);
			cStmt.registerOutParameter(8, OracleTypes.VARCHAR);
			cStmt.execute();

			if ("1".equals(cStmt.getString(7))) {
				str[0]="false";
			} else {
				str[0]="true";                
			}
            str[1]=cStmt.getString(8);
			cStmt.close();

		} catch (Exception e) {
			throw new TranFailException(
				"Util_ControlDao",
				"icbc.cmis.util.Util_ControlDao.check()",
				e.getMessage(),
				"调用公用控制函数出错！");
		} finally {
			this.closeConnection();
		}

		return str;

	}

}
