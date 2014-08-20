package icbc.cmis.service;

import java.sql.SQLException;

/**
 * @author xgl
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CmisSQLException extends SQLException {

	/**
	 * Constructor for CmisSQLException.
	 * @param reason
	 * @param SQLState
	 * @param vendorCode
	 */
	private int errorCode = 123456;
	private String sqlState = null;
	/**
	 * Constructor for CmisSQLException.
	 */
	public CmisSQLException(int errCode,String sqlState,String errorMsg) {
		super(errorMsg);
		errorCode = errCode;
		sqlState = sqlState;
	}
   public int getErrorCode(){
   	 return errorCode;
   }
	public String getSQLState(){
		return sqlState;
	}
}
