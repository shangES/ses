package icbc.cmis.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import oracle.jdbc.driver.OracleTypes;

import icbc.cmis.base.MuiTranFailException;
import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.CmisOperation;
import icbc.cmis.service.CmisDao;

/*************************************************************
 * 
 * <b>创建日期: </b> 2005-4-25<br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * <br>
 * <p>Copyright: Copyright (c)2005</p>
 * <p>Company: </p>
 * 
 * @author 胡晓忠
 * 
 * @version 
 * 
 * @since 
 * 
 * @see 
 * 
 *************************************************************/

public class Util_ChooseRateTypeDAO extends CmisDao {

	/**
	 * <b>构造函数</b><br>
	 * 
	 */
	public Util_ChooseRateTypeDAO(icbc.cmis.operation.CmisOperation op) {
		super(op);
	}

	public Vector getRateType(String flag) throws Exception {
		Vector rateTypeVector = new Vector();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			this.getConnection();

			if (flag.equals("0"))
				sql =
					(String) "select dict_code,dict_name from ida200261031 where dict_code in (select distinct substr(dict_code,1,2) from ida200261031 where dict_rate_code is not null and dict_code not like '5%' and lang_code='"+langCode+"' and dict_code not like '6%') and lang_code='"+langCode+"'";
			else
				sql =
					(String) "select dict_code,dict_name from ida200261031 where dict_code in (select distinct substr(dict_code,1,2) from ida200261031 where dict_rate_code is not null and dict_code not like '5%' and lang_code='"+langCode+"' and dict_code not like '6%') and lang_code='"+langCode+"'";
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String[] row = { rs.getString(1), rs.getString(2)};
				rateTypeVector.add(row);
			}
		} catch (TranFailException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new MuiTranFailException(
				"099993",
				"icbc.cmis.util.UtilChooseRateTypeDAO",
				ex.getMessage() + sql,
				langCode);
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
		return rateTypeVector;

	}

	public Vector getRateTime(String rateCode) throws Exception {
		Vector rateTimeVector = new Vector();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			this.getConnection();
			//stmt = (PreparedStatement) conn.createStatement();
			sql =
				"select dict_code,dict_name from imag_rate_term where dict_code in (select dict_term from ida200261031 where dict_code like ?||'%' and lang_code='"+langCode+"' and  dict_rate_code is not null) and lang_code='"+langCode+"'";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, rateCode);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String[] row = { rs.getString(1), rs.getString(2)};
				rateTimeVector.add(row);
			}
		} catch (TranFailException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new MuiTranFailException(
				"099993",
				"icbc.cmis.util.UtilChooseRateTypeDAO",
				ex.getMessage() + sql,
				langCode);
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
		return rateTimeVector;

	}
	
	private String langCode = "zh_CN";
  
	/**
	 *  
	 * Description  : 取语言标志
	 * CreationDate : 2007-6-7 10:32:08
	 * @author     : weihb
	 *   
	 * @param langCode
	 */
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	
	} 
}
